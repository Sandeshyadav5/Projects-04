package in.co.rays.proj4.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import in.co.rays.proj4.bean.SessionBean;
import in.co.rays.proj4.exception.ApplicationException;
import in.co.rays.proj4.exception.DatabaseException;
import in.co.rays.proj4.exception.DuplicateRecordException;
import in.co.rays.proj4.util.JDBCDataSource;

public class SessionModel {

    // ==================== NEXT PK ====================
    public Integer nextPK() throws DatabaseException {

        Connection conn = null;
        int pk = 0;

        try {
            conn = JDBCDataSource.getConnection();
            PreparedStatement pstmt =
                    conn.prepareStatement("select max(id) from st_session");

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
    public long add(SessionBean bean)
            throws ApplicationException, DuplicateRecordException {

        Connection conn = null;
        int pk = 0;

        SessionBean duplicate = findByCode(bean.getSessionCode());
        if (duplicate != null) {
            throw new DuplicateRecordException("Session Code already exists");
        }

        try {
            conn = JDBCDataSource.getConnection();
            pk = nextPK();
            conn.setAutoCommit(false);

            PreparedStatement pstmt = conn.prepareStatement(
                    "insert into st_session values (?,?,?,?,?,?,?,?,?)");

            pstmt.setInt(1, pk);
            pstmt.setString(2, bean.getSessionCode());
            pstmt.setString(3, bean.getUserName());
            pstmt.setDate(4, new java.sql.Date(bean.getLoginTime().getTime()));
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
            throw new ApplicationException("Exception in Add Session");
        } finally {
            JDBCDataSource.closeConnection(conn);
        }

        return pk;
    }

    // ==================== DELETE ====================
    public void delete(SessionBean bean) throws ApplicationException {

        Connection conn = null;

        try {
            conn = JDBCDataSource.getConnection();
            conn.setAutoCommit(false);

            PreparedStatement pstmt =
                    conn.prepareStatement("delete from st_session where id=?");

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
            throw new ApplicationException("Exception in Delete Session");
        } finally {
            JDBCDataSource.closeConnection(conn);
        }
    }

    // ==================== UPDATE ====================
    public void update(SessionBean bean)
            throws ApplicationException, DuplicateRecordException {

        Connection conn = null;

        SessionBean duplicate = findByCode(bean.getSessionCode());

        if (duplicate != null && duplicate.getId() != bean.getId()) {
            throw new DuplicateRecordException("Session Code already exists");
        }

        try {
            conn = JDBCDataSource.getConnection();
            conn.setAutoCommit(false);

            PreparedStatement pstmt = conn.prepareStatement(
                    "update st_session set session_code=?, user_name=?, login_time=?, status=?, created_by=?, modified_by=?, created_datetime=?, modified_datetime=? where id=?");

            pstmt.setString(1, bean.getSessionCode());
            pstmt.setString(2, bean.getUserName());
            pstmt.setDate(3, new java.sql.Date(bean.getLoginTime().getTime()));
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
            throw new ApplicationException("Exception in Update Session");
        } finally {
            JDBCDataSource.closeConnection(conn);
        }
    }

    // ==================== FIND BY PK ====================
    public SessionBean findByPk(long pk) throws ApplicationException {

        SessionBean bean = null;
        Connection conn = null;

        try {
            conn = JDBCDataSource.getConnection();
            PreparedStatement pstmt =
                    conn.prepareStatement("select * from st_session where id=?");

            pstmt.setLong(1, pk);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                bean = new SessionBean();
                bean.setId(rs.getLong(1));
                bean.setSessionCode(rs.getString(2));
                bean.setUserName(rs.getString(3));
                bean.setLoginTime(rs.getDate(4));
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

    // ==================== FIND BY CODE ====================
    public SessionBean findByCode(String code) throws ApplicationException {

        SessionBean bean = null;
        Connection conn = null;

        try {
            conn = JDBCDataSource.getConnection();
            PreparedStatement pstmt =
                    conn.prepareStatement("select * from st_session where session_code=?");

            pstmt.setString(1, code);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                bean = new SessionBean();
                bean.setId(rs.getLong(1));
                bean.setSessionCode(rs.getString(2));
                bean.setUserName(rs.getString(3));
                bean.setLoginTime(rs.getDate(4));
                bean.setStatus(rs.getString(5));
            }

            rs.close();
            pstmt.close();

        } catch (Exception e) {
            throw new ApplicationException("Exception in find by code");
        } finally {
            JDBCDataSource.closeConnection(conn);
        }

        return bean;
    }

    // ==================== SEARCH ====================
    public List search(SessionBean bean, int pageNo, int pageSize)
            throws ApplicationException {

        StringBuffer sql =
                new StringBuffer("select * from st_session where 1=1");

        if (bean != null) {

            if (bean.getId() > 0) {
                sql.append(" and id = " + bean.getId());
            }

            if (bean.getSessionCode() != null && bean.getSessionCode().length() > 0) {
                sql.append(" and session_code like '" + bean.getSessionCode() + "%'");
            }

            if (bean.getUserName() != null && bean.getUserName().length() > 0) {
                sql.append(" and session_name like '" + bean.getUserName() + "%'");
            }
            if (bean.getLoginTime() != null) {
		        sql.append(" AND login_time = '" + new java.sql.Date(bean.getLoginTime().getTime()) + "'");
		    }

          

            if (bean.getStatus() != null && bean.getStatus().length() > 0) {
                sql.append(" and status like '" + bean.getStatus() + "%'");
            }
        }

        if (pageSize > 0) {
            pageNo = (pageNo - 1) * pageSize;
            sql.append(" limit " + pageNo + "," + pageSize);
        }

        List list = new ArrayList();
        Connection conn = null;

        try {
            conn = JDBCDataSource.getConnection();
            PreparedStatement pstmt =
                    conn.prepareStatement(sql.toString());

            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                bean = new SessionBean();
                bean.setId(rs.getLong(1));
                bean.setSessionCode(rs.getString(2));
                bean.setUserName(rs.getString(3));
                bean.setLoginTime(rs.getDate(4));
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
            throw new ApplicationException("Exception in Search Session");
        } finally {
            JDBCDataSource.closeConnection(conn);
        }

        return list;
    }

    // ==================== LIST ====================
    public List list(int pageNo, int pageSize)
            throws ApplicationException {

        ArrayList list = new ArrayList();
        StringBuffer sql = new StringBuffer("select * from st_session");

        if (pageSize > 0) {
            pageNo = (pageNo - 1) * pageSize;
            sql.append(" limit " + pageNo + "," + pageSize);
        }

        Connection conn = null;
        SessionBean bean = null;

        try {
            conn = JDBCDataSource.getConnection();
            PreparedStatement pstmt =
                    conn.prepareStatement(sql.toString());

            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                bean = new SessionBean();
                bean.setId(rs.getLong(1));
                bean.setSessionCode(rs.getString(2));
                bean.setUserName(rs.getString(3));
                bean.setLoginTime(rs.getDate(4));
                bean.setStatus(rs.getString(5));
                list.add(bean);
            }

            rs.close();

        } catch (Exception e) {
            throw new ApplicationException("Exception in List Session");
        } finally {
            JDBCDataSource.closeConnection(conn);
        }

        return list;
    }
}