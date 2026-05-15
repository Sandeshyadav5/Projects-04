package in.co.rays.proj4.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import in.co.rays.proj4.bean.SourceBean;
import in.co.rays.proj4.exception.ApplicationException;
import in.co.rays.proj4.exception.DatabaseException;
import in.co.rays.proj4.exception.DuplicateRecordException;
import in.co.rays.proj4.util.JDBCDataSource;

public class SourceModel {

    // ==================== NEXT PK ====================
    public Integer nextPK() throws DatabaseException {

        Connection conn = null;
        int pk = 0;

        try {
            conn = JDBCDataSource.getConnection();
            PreparedStatement pstmt =
                    conn.prepareStatement("select max(id) from st_source");

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
    public long add(SourceBean bean)
            throws ApplicationException, DuplicateRecordException {

        Connection conn = null;
        int pk = 0;

        SourceBean duplicate = findByCode(bean.getSourceCode());
        if (duplicate != null) {
            throw new DuplicateRecordException("Source Code already exists");
        }

        try {
            conn = JDBCDataSource.getConnection();
            pk = nextPK();
            conn.setAutoCommit(false);

            PreparedStatement pstmt = conn.prepareStatement(
                    "insert into st_source (id, source_code, source_name, connection_type, status, created_by, modified_by, created_datetime, modified_datetime) values (?,?,?,?,?,?,?,?,?)");

            pstmt.setInt(1, pk);
            pstmt.setString(2, bean.getSourceCode());
            pstmt.setString(3, bean.getSourceName());
            pstmt.setString(4, bean.getConnectionType());
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
            throw new ApplicationException("Exception in Add Source");
        } finally {
            JDBCDataSource.closeConnection(conn);
        }

        return pk;
    }

    // ==================== DELETE ====================
    public void delete(SourceBean bean) throws ApplicationException {

        Connection conn = null;

        try {
            conn = JDBCDataSource.getConnection();
            conn.setAutoCommit(false);

            PreparedStatement pstmt =
                    conn.prepareStatement("delete from st_source where id=?");

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
            throw new ApplicationException("Exception in Delete Source");
        } finally {
            JDBCDataSource.closeConnection(conn);
        }
    }

    // ==================== UPDATE ====================
    public void update(SourceBean bean)
            throws ApplicationException, DuplicateRecordException {

        Connection conn = null;

        SourceBean duplicate = findByCode(bean.getSourceCode());

        if (duplicate != null && duplicate.getId() != bean.getId()) {
            throw new DuplicateRecordException("Source Code already exists");
        }

        try {
            conn = JDBCDataSource.getConnection();
            conn.setAutoCommit(false);

            PreparedStatement pstmt = conn.prepareStatement(
                    "update st_source set source_code=?, source_name=?, connection_type=?, status=?, created_by=?, modified_by=?, created_datetime=?, modified_datetime=? where id=?");

            pstmt.setString(1, bean.getSourceCode());
            pstmt.setString(2, bean.getSourceName());
            pstmt.setString(3, bean.getConnectionType());
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
            throw new ApplicationException("Exception in Update Source");
        } finally {
            JDBCDataSource.closeConnection(conn);
        }
    }

    // ==================== FIND BY PK ====================
    public SourceBean findByPk(long pk) throws ApplicationException {

        SourceBean bean = null;
        Connection conn = null;

        try {
            conn = JDBCDataSource.getConnection();
            PreparedStatement pstmt =
                    conn.prepareStatement("select * from st_source where id=?");

            pstmt.setLong(1, pk);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                bean = new SourceBean();
                bean.setId(rs.getLong(1));
                bean.setSourceCode(rs.getString(2));
                bean.setSourceName(rs.getString(3));
                bean.setConnectionType(rs.getString(4));
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
    public SourceBean findByCode(String code) throws ApplicationException {

        SourceBean bean = null;
        Connection conn = null;

        try {
            conn = JDBCDataSource.getConnection();
            PreparedStatement pstmt =
                    conn.prepareStatement("select * from st_source where source_code=?");

            pstmt.setString(1, code);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                bean = new SourceBean();
                bean.setId(rs.getLong(1));
                bean.setSourceCode(rs.getString(2));
                bean.setSourceName(rs.getString(3));
                bean.setConnectionType(rs.getString(4));
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
    public List search(SourceBean bean, int pageNo, int pageSize)
            throws ApplicationException {

        StringBuffer sql =
                new StringBuffer("select * from st_source where 1=1");

        if (bean != null) {

            if (bean.getId() > 0) {
                sql.append(" and id = " + bean.getId());
            }

            if (bean.getSourceCode() != null && bean.getSourceCode().length() > 0) {
                sql.append(" and source_code like '" + bean.getSourceCode() + "%'");
            }

            if (bean.getSourceName() != null && bean.getSourceName().length() > 0) {
                sql.append(" and source_name like '" + bean.getSourceName() + "%'");
            }

            if (bean.getConnectionType() != null && bean.getConnectionType().length() > 0) {
                sql.append(" and connection_type like '" + bean.getConnectionType() + "%'");
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
                bean = new SourceBean();
                bean.setId(rs.getLong(1));
                bean.setSourceCode(rs.getString(2));
                bean.setSourceName(rs.getString(3));
                bean.setConnectionType(rs.getString(4));
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
            throw new ApplicationException("Exception in Search Source");
        } finally {
            JDBCDataSource.closeConnection(conn);
        }

        return list;
    }

    // ==================== LIST ====================
    public List list(int pageNo, int pageSize)
            throws ApplicationException {

        ArrayList list = new ArrayList();
        StringBuffer sql = new StringBuffer("select * from st_source");

        if (pageSize > 0) {
            pageNo = (pageNo - 1) * pageSize;
            sql.append(" limit " + pageNo + "," + pageSize);
        }

        Connection conn = null;
        SourceBean bean = null;

        try {
            conn = JDBCDataSource.getConnection();
            PreparedStatement pstmt =
                    conn.prepareStatement(sql.toString());

            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                bean = new SourceBean();
                bean.setId(rs.getLong(1));
                bean.setSourceCode(rs.getString(2));
                bean.setSourceName(rs.getString(3));
                bean.setConnectionType(rs.getString(4));
                bean.setStatus(rs.getString(5));
                list.add(bean);
            }

            rs.close();

        } catch (Exception e) {
            throw new ApplicationException("Exception in List Source");
        } finally {
            JDBCDataSource.closeConnection(conn);
        }

        return list;
    }
}