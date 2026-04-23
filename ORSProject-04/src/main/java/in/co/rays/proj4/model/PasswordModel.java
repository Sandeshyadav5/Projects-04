package in.co.rays.proj4.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;


import in.co.rays.proj4.bean.PasswordBean;
import in.co.rays.proj4.exception.ApplicationException;
import in.co.rays.proj4.exception.DatabaseException;
import in.co.rays.proj4.exception.DuplicateRecordException;
import in.co.rays.proj4.util.JDBCDataSource;

public class PasswordModel  {

    // ==================== NEXT PK ====================
    public Integer nextPK() throws DatabaseException {

        Connection conn = null;
        int pk = 0;

        try {
            conn = JDBCDataSource.getConnection();
            PreparedStatement pstmt =
                conn.prepareStatement("select max(id) from st_password");

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
    public long add(PasswordBean bean) throws ApplicationException, DuplicateRecordException {

        Connection conn = null;
        int pk = 0;
        PasswordBean duplicateStatus = findByCode(bean.getResetCode());

		if (duplicateStatus != null) {
			throw new DuplicateRecordException("status Name alredy exists");

		}
        try {
            conn = JDBCDataSource.getConnection();
            pk = nextPK();
            conn.setAutoCommit(false);

            PreparedStatement pstmt = conn.prepareStatement("insert into st_password values(?, ?, ?, ?,?,?,?,?,?)");

            pstmt.setInt(1, pk);
            pstmt.setString(2, bean.getResetCode());
            pstmt.setString(3, bean.getUserName());
            pstmt.setString(4, bean.getResetToken());
            pstmt.setString(5, bean.getStatus());
        	pstmt.setString(6, bean.getCreatedBy());
			pstmt.setString(7, bean.getModifiedBy());
			pstmt.setTimestamp(8, bean.getCreatedDatetime());
			pstmt.setTimestamp(9, bean.getModifiedDatetime());

            pstmt.executeUpdate();
            conn.commit();
            pstmt.close();

        } catch (Exception e) {
            try {
                conn.rollback();
            } catch (Exception ex) {
                throw new ApplicationException("Add Rollback Exception");
            }
            throw new ApplicationException("Exception in Add EMI");
        } finally {
            JDBCDataSource.closeConnection(conn);
        }

        return pk;
    }

    // ==================== DELETE ====================
    public void delete(PasswordBean bean) throws ApplicationException {

        Connection conn = null;

        try {
            conn = JDBCDataSource.getConnection();
            conn.setAutoCommit(false);

            PreparedStatement pstmt =
                conn.prepareStatement("delete from st_password where id=?");

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
            throw new ApplicationException("Exception in Delete EMI");
        } finally {
            JDBCDataSource.closeConnection(conn);
        }
    }

    // ==================== UPDATE ====================
    public void update(PasswordBean bean) throws ApplicationException, DuplicateRecordException {

        Connection conn = null;
    	PasswordBean duplicateStatus = findByCode(bean.getResetCode());

		// Check if updated Password already exist
		if (duplicateStatus != null && duplicateStatus.getId() != bean.getId()) {

			throw new DuplicateRecordException("status is already exist");
		}

        try {
            conn = JDBCDataSource.getConnection();
            conn.setAutoCommit(false);

            PreparedStatement pstmt = conn.prepareStatement(
            		"update st_password set reset_code=?, user_name=?, reset_token=?, status=?, created_by=?, modified_by=?, created_datetime=?, modified_datetime=? where id=?");
            pstmt.setString(1, bean.getResetCode());
            pstmt.setString(2, bean.getUserName());
            pstmt.setString(3, bean.getResetToken());
            pstmt.setString(4, bean.getStatus());
        	pstmt.setString(5, bean.getCreatedBy());
			pstmt.setString(6, bean.getModifiedBy());
			pstmt.setTimestamp(7, bean.getCreatedDatetime());
			pstmt.setTimestamp(8, bean.getModifiedDatetime());
			  pstmt.setLong(9, bean.getId());

            pstmt.executeUpdate();
            conn.commit();
            pstmt.close();

        } catch (Exception e) {
            try {
                conn.rollback();
            } catch (Exception ex) {
                throw new ApplicationException("Update Rollback Exception");
            }
            throw new ApplicationException("Exception in Update EMI");
        } finally {
            JDBCDataSource.closeConnection(conn);
        }
    }

    // ==================== FIND BY PK ====================
    public PasswordBean findByPk(long pk) throws ApplicationException {

        PasswordBean bean = null;
        Connection conn = null;

        try {
            conn = JDBCDataSource.getConnection();
            PreparedStatement pstmt =
                conn.prepareStatement("select * from st_password where id=?");

            pstmt.setLong(1, pk);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
            	 bean = new PasswordBean();
                 bean.setId(rs.getLong(1));
                 bean.setResetCode(rs.getString(2));
                 bean.setUserName(rs.getString(3));
                 bean.setResetToken(rs.getString(4));
                 bean.setStatus(rs.getString(5));
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
    
    
    public PasswordBean findByCode(String Code) throws ApplicationException {

	     

    	PasswordBean bean = null;
        Connection conn = null;

        try {
            conn = JDBCDataSource.getConnection();
            PreparedStatement pstmt = conn.prepareStatement("select * from st_password where reset_code = ?");
            pstmt.setString(1, Code);

            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
            	 bean = new PasswordBean();
                 bean.setId(rs.getLong(1));
                 bean.setResetCode(rs.getString(2));
                 bean.setUserName(rs.getString(3));
                 bean.setResetToken(rs.getString(4));
                 bean.setStatus(rs.getString(5));
            }

            rs.close();
            pstmt.close();

        } catch (Exception e) {
       
            throw new ApplicationException("Exception : Exception in getting Password by name");
        } finally {
            JDBCDataSource.closeConnection(conn);
        }

        return bean;
    }

    // ==================== SEARCH ====================
    public List search(PasswordBean bean, int pageNo, int pageSize)
            throws ApplicationException {

        StringBuffer sql = new StringBuffer("select * from st_password where 1=1");

        if (bean != null) {

            if (bean.getId() > 0)
                sql.append(" and id=" + bean.getId());
            
            if (bean.getResetCode() != null && bean.getResetCode().length() > 0)
                sql.append(" and reset_code like '" + bean.getResetCode() + "%'");

            if (bean.getStatus() != null && bean.getStatus().length() > 0)
                sql.append(" and status like '" + bean.getStatus() + "%'");
        }

        if (pageSize > 0) {
            pageNo = (pageNo - 1) * pageSize;
            sql.append(" limit " + pageNo + "," + pageSize);
        }

        List<PasswordBean> list = new ArrayList<>();
        Connection conn = null;

        try {
            conn = JDBCDataSource.getConnection();
            PreparedStatement pstmt =
                conn.prepareStatement(sql.toString());

            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
            	 bean = new PasswordBean();
                 bean.setId(rs.getLong(1));
                 bean.setResetCode(rs.getString(2));
                 bean.setUserName(rs.getString(3));
                 bean.setResetToken(rs.getString(4));
                 bean.setStatus(rs.getString(5));
                bean.setCreatedBy(rs.getString(6));
                bean.setModifiedBy(rs.getString(7));
                bean.setCreatedDatetime(rs.getTimestamp(8));
                bean.setModifiedDatetime(rs.getTimestamp(9));
                list.add(bean);
            }

            rs.close();
            pstmt.close();

        } catch (Exception e) {
            throw new ApplicationException("Exception in Search EMI");
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
            new StringBuffer("select * from st_password");

        if (pageSize > 0) {
            pageNo = (pageNo - 1) * pageSize;
            sql.append(" limit " + pageNo + "," + pageSize);
        }

        Connection conn = null;
        PasswordBean bean = null;

        try {
            conn = JDBCDataSource.getConnection();
            PreparedStatement pstmt =
                conn.prepareStatement(sql.toString());

            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                bean = new PasswordBean();
                bean.setId(rs.getLong(1));
                bean.setResetCode(rs.getString(2));
                bean.setUserName(rs.getString(3));
                bean.setResetToken(rs.getString(4));
                bean.setStatus(rs.getString(5));
                list.add(bean);
            }

            rs.close();

        } catch (Exception e) {
            throw new ApplicationException("Exception in List EMI");
        } finally {
            JDBCDataSource.closeConnection(conn);
        }

        return list;
    }
}