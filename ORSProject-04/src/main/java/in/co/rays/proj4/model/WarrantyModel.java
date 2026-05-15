package in.co.rays.proj4.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;


import in.co.rays.proj4.bean.WarrantyBean;
import in.co.rays.proj4.exception.ApplicationException;
import in.co.rays.proj4.exception.DatabaseException;
import in.co.rays.proj4.exception.DuplicateRecordException;
import in.co.rays.proj4.util.JDBCDataSource;

public class WarrantyModel  {

    // ==================== NEXT PK ====================
    public Integer nextPK() throws DatabaseException {

        Connection conn = null;
        int pk = 0;

        try {
            conn = JDBCDataSource.getConnection();
            PreparedStatement pstmt =
                conn.prepareStatement("select max(id) from st_warranty");

            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                pk = rs.getInt(1);
            }

            rs.close();
            pstmt.close();

        } catch (Exception e) {
            throw new DatabaseException("Exception in getting PK");
        } finally {
            JDBCDataSource.closeConnection(conn);
        }

        return pk + 1;
    }

    // ==================== ADD ====================
    public long add(WarrantyBean bean) throws ApplicationException, DuplicateRecordException {

        Connection conn = null;
        int pk = 0;
        WarrantyBean duplicateName = findByName(bean.getProductName());

		if (duplicateName != null) {
			throw new DuplicateRecordException(" Name alredy exists");

		}
        try {
            conn = JDBCDataSource.getConnection();
            pk = nextPK();
            conn.setAutoCommit(false);

            PreparedStatement pstmt = conn.prepareStatement("insert into st_warranty values(?, ?, ?, ?,?,?,?,?)");

            pstmt.setInt(1, pk);
            pstmt.setString(2, bean.getProductName());
            pstmt.setDate(3,  new java.sql.Date(bean.getStartDate().getTime()));
            pstmt.setDate(4,  new java.sql.Date(bean.getEndDate().getTime()));
        	pstmt.setString(5, bean.getCreatedBy());
			pstmt.setString(6, bean.getModifiedBy());
			pstmt.setTimestamp(7, bean.getCreatedDatetime());
			pstmt.setTimestamp(8, bean.getModifiedDatetime());

            pstmt.executeUpdate();
            conn.commit();
            pstmt.close();

        } catch (Exception e) {
            try {
                conn.rollback();
            } catch (Exception ex) {
                throw new ApplicationException("Add Rollback Exception");
            }
            throw new ApplicationException("Exception in Add Warrenty");
        } finally {
            JDBCDataSource.closeConnection(conn);
        }

        return pk;
    }

    // ==================== DELETE ====================
    public void delete(WarrantyBean bean) throws ApplicationException {

        Connection conn = null;

        try {
            conn = JDBCDataSource.getConnection();
            conn.setAutoCommit(false);

            PreparedStatement pstmt =
                conn.prepareStatement("delete from st_warranty where id=?");

            pstmt.setLong(1, bean.getId());
            pstmt.executeUpdate();

            conn.commit();
            pstmt.close();

        } catch (Exception e) {
            try {
                conn.rollback();
            } catch (Exception ex) {
                throw new ApplicationException("Delete Rollback Exception");
            }
            throw new ApplicationException("Exception in Delete Warrenty");
        } finally {
            JDBCDataSource.closeConnection(conn);
        }
    }

    // ==================== UPDATE ====================
    public void update(WarrantyBean bean) throws ApplicationException, DuplicateRecordException {

        Connection conn = null;
    	WarrantyBean duplicateName = findByName(bean.getProductName());

		// Check if updated Warranty already exist
		if (duplicateName != null && duplicateName.getId() != bean.getId()) {

			throw new DuplicateRecordException("name is already exist");
		}

        try {
            conn = JDBCDataSource.getConnection();
            conn.setAutoCommit(false);

            PreparedStatement pstmt = conn.prepareStatement(
            		"update st_warranty set product_name=?, start_date=?, end_date=?, created_by=?, modified_by=?, created_datetime=?, modified_datetime=? where id=?");
            pstmt.setString(1, bean.getProductName());
            pstmt.setDate(2, new java.sql.Date(bean.getStartDate().getTime()));
            pstmt.setDate(3, new java.sql.Date(bean.getEndDate().getTime()));
            
        	pstmt.setString(4, bean.getCreatedBy());
			pstmt.setString(5, bean.getModifiedBy());
			pstmt.setTimestamp(6, bean.getCreatedDatetime());
			pstmt.setTimestamp(7, bean.getModifiedDatetime());
			  pstmt.setLong(8, bean.getId());

            pstmt.executeUpdate();
            conn.commit();
            pstmt.close();

        } catch (Exception e) {
            try {
                conn.rollback();
            } catch (Exception ex) {
                throw new ApplicationException("Update Rollback Exception");
            }
            throw new ApplicationException("Exception in Update Warrenty");
        } finally {
            JDBCDataSource.closeConnection(conn);
        }
    }

    // ==================== FIND BY PK ====================
    public WarrantyBean findByPk(long pk) throws ApplicationException {

        WarrantyBean bean = null;
        Connection conn = null;

        try {
            conn = JDBCDataSource.getConnection();
            PreparedStatement pstmt =
                conn.prepareStatement("select * from st_warranty where id=?");

            pstmt.setLong(1, pk);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
            	  bean = new WarrantyBean();
                  bean.setId(rs.getLong(1));
                  bean.setProductName(rs.getString(2));
                  bean.setStartDate(rs.getDate(3));
                  bean.setEndDate(rs.getDate(4));
            }

            rs.close();
            pstmt.close();

        } catch (Exception e) {
            throw new ApplicationException("Exception in Find By PK");
        } finally {
            JDBCDataSource.closeConnection(conn);
        }

        return bean;
    }
    
    
    public WarrantyBean findByName(String Name) throws ApplicationException {

	     

    	WarrantyBean bean = null;
        Connection conn = null;

        try {
            conn = JDBCDataSource.getConnection();
            PreparedStatement pstmt = conn.prepareStatement("select * from st_warranty where product_name = ?");
            pstmt.setString(1, Name);

            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
            	 bean = new WarrantyBean();
                 bean.setId(rs.getLong(1));
                 bean.setProductName(rs.getString(2));
                 bean.setStartDate(rs.getDate(3));
                 bean.setEndDate(rs.getDate(4));
                 
            }

            rs.close();
            pstmt.close();

        } catch (Exception e) {
       
            throw new ApplicationException("Exception : Exception in getting Warranty by name");
        } finally {
            JDBCDataSource.closeConnection(conn);
        }

        return bean;
    }

    // ==================== SEARCH ====================
    public List search(WarrantyBean bean, int pageNo, int pageSize)
            throws ApplicationException {

        StringBuffer sql = new StringBuffer("select * from st_warranty where 1=1");

        if (bean != null) {

            if (bean.getId() > 0)
                sql.append(" and id=" + bean.getId());
            
            if (bean.getProductName() != null && bean.getProductName().length() > 0)
                sql.append(" and product_name like '" + bean.getProductName() + "%'");

           
        }

        if (pageSize > 0) {
            pageNo = (pageNo - 1) * pageSize;
            sql.append(" limit " + pageNo + "," + pageSize);
        }

        List<WarrantyBean> list = new ArrayList<>();
        Connection conn = null;

        try {
            conn = JDBCDataSource.getConnection();
            PreparedStatement pstmt =
                conn.prepareStatement(sql.toString());

            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
            	bean = new WarrantyBean();
                bean.setId(rs.getLong(1));
                bean.setProductName(rs.getString(2));
                bean.setStartDate(rs.getDate(3));
                bean.setEndDate(rs.getDate(4));
                bean.setCreatedBy(rs.getString(5));
                bean.setModifiedBy(rs.getString(6));
                bean.setCreatedDatetime(rs.getTimestamp(7));
                bean.setModifiedDatetime(rs.getTimestamp(8));
                list.add(bean);
            }

            rs.close();
            pstmt.close();

        } catch (Exception e) {
            throw new ApplicationException("Exception in Search Warrenty");
        } finally {
            JDBCDataSource.closeConnection(conn);
        }

        return list;
    }

    // ==================== LIST ====================
    public List list(int pageNo, int pageSize)
            throws ApplicationException {

        ArrayList list = new ArrayList();
        StringBuffer sql =
            new StringBuffer("select * from st_warranty");

        if (pageSize > 0) {
            pageNo = (pageNo - 1) * pageSize;
            sql.append(" limit " + pageNo + "," + pageSize);
        }

        Connection conn = null;
        WarrantyBean bean = null;

        try {
            conn = JDBCDataSource.getConnection();
            PreparedStatement pstmt =
                conn.prepareStatement(sql.toString());

            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                bean = new WarrantyBean();
                bean.setId(rs.getLong(1));
                bean.setProductName(rs.getString(2));
                bean.setStartDate(rs.getDate(3));
                bean.setEndDate(rs.getDate(4));
               
                list.add(bean);
            }

            rs.close();

        } catch (Exception e) {
            throw new ApplicationException("Exception in List Warrenty");
        } finally {
            JDBCDataSource.closeConnection(conn);
        }

        return list;
    }
}