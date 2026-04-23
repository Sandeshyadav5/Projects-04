package in.co.rays.proj4.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import in.co.rays.proj4.bean.RoleBean;
import in.co.rays.proj4.exception.ApplicationException;
import in.co.rays.proj4.exception.DatabaseException;
import in.co.rays.proj4.exception.DuplicateRecordException;
import in.co.rays.proj4.util.JDBCDataSource;

/**
 * RoleModel class handles all database operations related to Role.
 * It provides methods for CRUD operations and search functionality.
 */
public class RoleModel {

    /**
     * Generates next primary key for Role table.
     * 
     * @return next primary key
     * @throws Exception if any database error occurs
     */
    public Integer nextPk() throws Exception {
        Connection conn = null;
        int pk = 0;
        try {
            conn = JDBCDataSource.getConnection();

            PreparedStatement pstmt = conn.prepareStatement("select max(id) from st_role");
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                pk = rs.getInt(1);
            }
            rs.close();
            pstmt.close();
        } catch (Exception e) {
            throw new DatabaseException("Exception:  Exception in getting pk");
        }
        return pk + 1;
    }

    /**
     * Adds a new Role record into database.
     * 
     * @param bean RoleBean containing role data
     * @return generated primary key
     * @throws Exception if any error occurs
     */
    public long add(RoleBean bean) throws Exception {
        Connection conn = null;
        int pk = 0;

        RoleBean existName = findByName(bean.getName());
        if (existName != null) {
            throw new DuplicateRecordException("Role name already exist");
        }

        try {
            conn = JDBCDataSource.getConnection();
            conn.setAutoCommit(false);
            pk = nextPk();

            PreparedStatement pstmt = conn.prepareStatement(
                    "insert into st_role values( ?, ?, ?, ?, ?, ?, ?)");
            pstmt.setInt(1, pk);
            pstmt.setString(2, bean.getName());
            pstmt.setString(3, bean.getDescription());
            pstmt.setString(4, bean.getCreatedBy());
            pstmt.setString(5, bean.getModifiedBy());
            pstmt.setTimestamp(6, bean.getCreatedDatetime());
            pstmt.setTimestamp(7, bean.getModifiedDatetime());

            pstmt.executeUpdate();
            conn.commit();

        } catch (Exception e) {
            try {
                conn.rollback();
            } catch (Exception ex) {
                throw new ApplicationException("Add rollback exception " + ex.getMessage());
            }
            throw new ApplicationException("Exception in add faculty");
        } finally {
            JDBCDataSource.closeConnection(conn);
        }
        return pk;
    }

    /**
     * Updates an existing Role record.
     * 
     * @param bean RoleBean containing updated data
     * @throws Exception if update fails
     */
    public void update(RoleBean bean) throws Exception {
        Connection conn = null;

        RoleBean existName = findByName(bean.getName());
        if (existName != null && existName.getId() != bean.getId()) {
            throw new DuplicateRecordException("Role name already exist");
        }

        try {
            conn = JDBCDataSource.getConnection();
            conn.setAutoCommit(false);

            PreparedStatement pstmt = conn.prepareStatement(
                    "update st_role set name=?,description=?, created_by = ?, modified_by = ?, created_datetime = ?, modified_datetime = ? where id  = ?");
            pstmt.setString(1, bean.getName());
            pstmt.setString(2, bean.getDescription());
            pstmt.setString(3, bean.getCreatedBy());
            pstmt.setString(4, bean.getModifiedBy());
            pstmt.setTimestamp(5, bean.getCreatedDatetime());
            pstmt.setTimestamp(6, bean.getModifiedDatetime());
            pstmt.setLong(7, bean.getId());

            pstmt.executeUpdate();
            conn.commit();
            pstmt.close();

        } catch (Exception e) {
            try {
                conn.rollback();
            } catch (Exception e1) {
                throw new ApplicationException("Exception in Update rollback");
            }
            throw new ApplicationException("Exception in Update Role");
        } finally {
            JDBCDataSource.closeConnection(conn);
        }
    }

    /**
     * Deletes a Role record.
     * 
     * @param bean RoleBean containing ID
     * @throws ApplicationException if delete fails
     */
    public void delete(RoleBean bean) throws ApplicationException {

        Connection conn = null;

        try {
            conn = JDBCDataSource.getConnection();
            conn.setAutoCommit(false);

            PreparedStatement pstmt = conn.prepareStatement(
                    "delete from st_role where id = ?");
            pstmt.setLong(1, bean.getId());

            pstmt.executeUpdate();
            conn.commit();
            pstmt.close();

        } catch (Exception e) {
            try {
                conn.rollback();
            } catch (Exception ex) {
                throw new ApplicationException(
                        "Exception : Delete rollback exception " + ex.getMessage());
            }
            throw new ApplicationException("Exception : Exception in delete Role");
        } finally {
            JDBCDataSource.closeConnection(conn);
        }
    }

    /**
     * Finds Role by primary key.
     * 
     * @param pk primary key
     * @return RoleBean
     * @throws Exception if error occurs
     */
    public RoleBean findByPk(long pk) throws Exception {
        RoleBean bean = null;
        Connection conn = null;

        StringBuffer sql = new StringBuffer("select * from st_role where id=?");
        try {
            conn = JDBCDataSource.getConnection();

            PreparedStatement pstmt = conn.prepareStatement(sql.toString());
            pstmt.setLong(1, pk);

            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                bean = new RoleBean();
                bean.setId(rs.getLong(1));
                bean.setName(rs.getString(2));
                bean.setDescription(rs.getString(3));
                bean.setCreatedBy(rs.getString(4));
                bean.setModifiedBy(rs.getString(5));
                bean.setCreatedDatetime(rs.getTimestamp(6));
                bean.setModifiedDatetime(rs.getTimestamp(7));
            }

            rs.close();
            rs.close();
            pstmt.close();

        } catch (Exception e) {
            throw new ApplicationException("Exception: Exception in getting pk");
        } finally {
            JDBCDataSource.closeConnection(conn);
        }
        return bean;
    }

    /**
     * Finds Role by name.
     * 
     * @param name role name
     * @return RoleBean
     * @throws Exception if error occurs
     */
    public RoleBean findByName(String name) throws Exception {
        RoleBean bean = null;
        Connection conn = null;

        StringBuffer sql = new StringBuffer("select * from st_role where name=?");
        try {
            conn = JDBCDataSource.getConnection();

            PreparedStatement pstmt = conn.prepareStatement(sql.toString());
            pstmt.setString(1, name);

            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                bean = new RoleBean();
                bean.setId(rs.getLong(1));
                bean.setName(rs.getString(2));
                bean.setDescription(rs.getString(3));
                bean.setCreatedBy(rs.getString(4));
                bean.setModifiedBy(rs.getString(5));
                bean.setCreatedDatetime(rs.getTimestamp(6));
                bean.setModifiedDatetime(rs.getTimestamp(7));
            }

            rs.close();
            rs.close();
            pstmt.close();

        } catch (Exception e) {
            throw new ApplicationException("Exception: Exception in getting name");
        } finally {
            JDBCDataSource.closeConnection(conn);
        }
        return bean;
    }

    /**
     * Returns list of all Role records.
     */
    public List<RoleBean> list() throws Exception {
        return search(null, 0, 0);
    }

    /**
     * Searches Role records with pagination.
     * 
     * @param bean search criteria
     * @param pageNo page number
     * @param pageSize page size
     * @return list of RoleBean
     * @throws Exception if search fails
     */
    public List search(RoleBean bean, int pageNo, int pageSize) throws Exception {
        Connection conn = null;
        List list = new ArrayList();

        StringBuffer sql = new StringBuffer("select * from st_role where 1=1");

        if (bean != null) {

            if (bean.getId() > 0) {
                sql.append(" AND id = " + bean.getId());
            }

            if (bean.getDescription() != null && bean.getDescription().length() > 0) {
                sql.append(" AND description LIKE '" + bean.getDescription() + "%'");
            }
        }

        if (pageSize > 0) {
            pageNo = (pageNo - 1) * pageSize;
            sql.append(" limit " + pageNo + ", " + pageSize);
        }

        try {
            conn = JDBCDataSource.getConnection();

            PreparedStatement pstmt = conn.prepareStatement(sql.toString());
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                bean = new RoleBean();
                bean.setId(rs.getLong(1));
                bean.setName(rs.getString(2));
                bean.setDescription(rs.getString(3));
                bean.setCreatedBy(rs.getString(4));
                bean.setModifiedBy(rs.getString(5));
                bean.setCreatedDatetime(rs.getTimestamp(6));
                bean.setModifiedDatetime(rs.getTimestamp(7));
                list.add(bean);
            }

            rs.close();
            pstmt.close();

        } catch (Exception e) {
            throw new ApplicationException("Exception: Exception in search");
        } finally {
            JDBCDataSource.closeConnection(conn);
        }

        return list;
    }
}