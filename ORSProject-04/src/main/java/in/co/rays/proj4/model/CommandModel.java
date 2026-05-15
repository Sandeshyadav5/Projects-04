package in.co.rays.proj4.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import in.co.rays.proj4.bean.CommandBean;
import in.co.rays.proj4.exception.ApplicationException;
import in.co.rays.proj4.exception.DatabaseException;
import in.co.rays.proj4.exception.DuplicateRecordException;
import in.co.rays.proj4.util.JDBCDataSource;

public class CommandModel {

    // ==================== NEXT PK ====================
    public Integer nextPK() throws DatabaseException {

        Connection conn = null;
        int pk = 0;

        try {
            conn = JDBCDataSource.getConnection();
            PreparedStatement pstmt =
                    conn.prepareStatement("select max(id) from st_command");

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
    public long add(CommandBean bean)
            throws ApplicationException, DuplicateRecordException {

        Connection conn = null;
        int pk = 0;

        CommandBean duplicate = findByTest(bean.getCommandTest());
        if (duplicate != null) {
            throw new DuplicateRecordException("Command Test already exists");
        }

        try {
            conn = JDBCDataSource.getConnection();
            pk = nextPK();
            conn.setAutoCommit(false);

            PreparedStatement pstmt = conn.prepareStatement(
                    "insert into st_command values (?,?,?,?,?,?,?,?,?)");

            pstmt.setInt(1, pk);
            pstmt.setString(2, bean.getCommandTest());
            pstmt.setString(3, bean.getDeviceTarget());
            if (bean.getExecutionTime() != null) {

                pstmt.setDate(4,
                    new java.sql.Date(bean.getExecutionTime().getTime()));

            } else {

                pstmt.setDate(4, null);
            }
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
            	e.printStackTrace();
            	throw new ApplicationException(e.getMessage());            }
            throw new ApplicationException("Exception in Add Command");
        } finally {
            JDBCDataSource.closeConnection(conn);
        }

        return pk;
    }

    // ==================== DELETE ====================
    public void delete(CommandBean bean) throws ApplicationException {

        Connection conn = null;

        try {
            conn = JDBCDataSource.getConnection();
            conn.setAutoCommit(false);

            PreparedStatement pstmt =
                    conn.prepareStatement("delete from st_command where id=?");

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
            throw new ApplicationException("Exception in Delete Command");
        } finally {
            JDBCDataSource.closeConnection(conn);
        }
    }

    // ==================== UPDATE ====================
    public void update(CommandBean bean)
            throws ApplicationException, DuplicateRecordException {

        Connection conn = null;

        CommandBean duplicate = findByTest(bean.getCommandTest());

        if (duplicate != null && duplicate.getId() != bean.getId()) {
            throw new DuplicateRecordException("Command Test already exists");
        }

        try {
            conn = JDBCDataSource.getConnection();
            conn.setAutoCommit(false);

            PreparedStatement pstmt = conn.prepareStatement(
                    "update st_command set command_test=?, device_target=?, execution_time=?, status=?, created_by=?, modified_by=?, created_datetime=?, modified_datetime=? where id=?");

            pstmt.setString(1, bean.getCommandTest());
            pstmt.setString(2, bean.getDeviceTarget());
            pstmt.setDate(3, new java.sql.Date(bean.getExecutionTime().getTime()));
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
            throw new ApplicationException("Exception in Update Command");
        } finally {
            JDBCDataSource.closeConnection(conn);
        }
    }

    // ==================== FIND BY PK ====================
    public CommandBean findByPk(long pk) throws ApplicationException {

        CommandBean bean = null;
        Connection conn = null;

        try {
            conn = JDBCDataSource.getConnection();
            PreparedStatement pstmt =
                    conn.prepareStatement("select * from st_command where id=?");

            pstmt.setLong(1, pk);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                bean = new CommandBean();
                bean.setId(rs.getLong(1));
                bean.setCommandTest(rs.getString(2));
                bean.setDeviceTarget(rs.getString(3));
                bean.setExecutionTime(rs.getDate(4));
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
    public CommandBean findByTest(String code) throws ApplicationException {

        CommandBean bean = null;
        Connection conn = null;

        try {
            conn = JDBCDataSource.getConnection();
            PreparedStatement pstmt =
                    conn.prepareStatement("select * from st_command where command_test=?");

            pstmt.setString(1, code);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                bean = new CommandBean();
                bean.setId(rs.getLong(1));
                bean.setCommandTest(rs.getString(2));
                bean.setDeviceTarget(rs.getString(3));
                bean.setExecutionTime(rs.getDate(4));
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
    public List search(CommandBean bean, int pageNo, int pageSize)
            throws ApplicationException {

        StringBuffer sql =
                new StringBuffer("select * from st_command where 1=1");

        if (bean != null) {

            if (bean.getId() > 0) {
                sql.append(" and id = " + bean.getId());
            }

            if (bean.getCommandTest() != null && bean.getCommandTest().length() > 0) {
                sql.append(" and command_test like '" + bean.getCommandTest() + "%'");
            }

            if (bean.getDeviceTarget() != null && bean.getDeviceTarget().length() > 0) {
                sql.append(" and device_target like '" + bean.getDeviceTarget() + "%'");
            }
            if (bean.getExecutionTime() != null) {
		        sql.append(" AND execution_time = '" + new java.sql.Date(bean.getExecutionTime().getTime()) + "'");
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
                bean = new CommandBean();
                bean.setId(rs.getLong(1));
                bean.setCommandTest(rs.getString(2));
                bean.setDeviceTarget(rs.getString(3));
                bean.setExecutionTime(rs.getDate(4));
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
            throw new ApplicationException("Exception in Search Command");
        } finally {
            JDBCDataSource.closeConnection(conn);
        }

        return list;
    }

    // ==================== LIST ====================
    public List list(int pageNo, int pageSize)
            throws ApplicationException {

        ArrayList list = new ArrayList();
        StringBuffer sql = new StringBuffer("select * from st_command");

        if (pageSize > 0) {
            pageNo = (pageNo - 1) * pageSize;
            sql.append(" limit " + pageNo + "," + pageSize);
        }

        Connection conn = null;
        CommandBean bean = null;

        try {
            conn = JDBCDataSource.getConnection();
            PreparedStatement pstmt =
                    conn.prepareStatement(sql.toString());

            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                bean = new CommandBean();
                bean.setId(rs.getLong(1));
                bean.setCommandTest(rs.getString(2));
                bean.setDeviceTarget(rs.getString(3));
                bean.setExecutionTime(rs.getDate(4));
                bean.setStatus(rs.getString(5));
                list.add(bean);
            }

            rs.close();

        } catch (Exception e) {
            throw new ApplicationException("Exception in List Command");
        } finally {
            JDBCDataSource.closeConnection(conn);
        }

        return list;
    }
}