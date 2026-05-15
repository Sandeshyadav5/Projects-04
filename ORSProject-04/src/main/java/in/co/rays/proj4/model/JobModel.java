package in.co.rays.proj4.model;

import java.sql.*;
import java.util.*;

import in.co.rays.proj4.bean.JobBean;
import in.co.rays.proj4.exception.*;
import in.co.rays.proj4.util.JDBCDataSource;

public class JobModel {

    // ================= NEXT PK =================
    public Integer nextPK() throws DatabaseException {

        Connection conn = null;
        int pk = 0;

        try {
            conn = JDBCDataSource.getConnection();
            PreparedStatement pstmt = conn.prepareStatement("select max(id) from st_job");

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

    // ================= ADD =================
    public long add(JobBean bean)
            throws ApplicationException, DuplicateRecordException {

        Connection conn = null;
        int pk = 0;

        JobBean duplicate = findByCode(bean.getJobCode());
        if (duplicate != null) {
            throw new DuplicateRecordException("Job Code already exists");
        }

        try {
            conn = JDBCDataSource.getConnection();
            pk = nextPK();
            conn.setAutoCommit(false);

            PreparedStatement pstmt = conn.prepareStatement(
                    "insert into st_job (id, job_code, job_name, cron_expression, status, created_by, modified_by, created_datetime, modified_datetime) values (?,?,?,?,?,?,?,?,?)");

            pstmt.setInt(1, pk);
            pstmt.setString(2, bean.getJobCode());
            pstmt.setString(3, bean.getJobName());
            pstmt.setString(4, bean.getCronExpression());
            pstmt.setString(5, bean.getStatus());
            pstmt.setString(6, bean.getCreatedBy());
            pstmt.setString(7, bean.getModifiedBy());
            pstmt.setDate(8, new java.sql.Date(bean.getCreatedDatetime().getTime()));
            pstmt.setDate(9, new java.sql.Date(bean.getModifiedDatetime().getTime()));

            pstmt.executeUpdate();
            conn.commit();
            pstmt.close();

        } catch (Exception e) {
            try { conn.rollback(); } catch (Exception ex) {}
            throw new ApplicationException("Exception in Add Job");
        } finally {
            JDBCDataSource.closeConnection(conn);
        }

        return pk;
    }

    // ================= DELETE =================
    public void delete(JobBean bean) throws ApplicationException {

        Connection conn = null;

        try {
            conn = JDBCDataSource.getConnection();
            conn.setAutoCommit(false);

            PreparedStatement pstmt =
                    conn.prepareStatement("delete from st_job where id=?");

            pstmt.setLong(1, bean.getId());
            pstmt.executeUpdate();

            conn.commit();
            pstmt.close();

        } catch (Exception e) {
            try { conn.rollback(); } catch (Exception ex) {}
            throw new ApplicationException("Exception in Delete Job");
        } finally {
            JDBCDataSource.closeConnection(conn);
        }
    }

    // ================= UPDATE =================
    public void update(JobBean bean)
            throws ApplicationException, DuplicateRecordException {

        Connection conn = null;

        JobBean duplicate = findByCode(bean.getJobCode());
        if (duplicate != null && duplicate.getId() != bean.getId()) {
            throw new DuplicateRecordException("Job Code already exists");
        }

        try {
            conn = JDBCDataSource.getConnection();
            conn.setAutoCommit(false);

            PreparedStatement pstmt = conn.prepareStatement(
                    "update st_job set job_code=?, job_name=?, cron_expression=?, status=?, created_by=?, modified_by=?, created_datetime=?, modified_datetime=? where id=?");

            pstmt.setString(1, bean.getJobCode());
            pstmt.setString(2, bean.getJobName());
            pstmt.setString(3, bean.getCronExpression());
            pstmt.setString(4, bean.getStatus());
            pstmt.setString(5, bean.getCreatedBy());
            pstmt.setString(6, bean.getModifiedBy());
            pstmt.setDate(7, new java.sql.Date(bean.getCreatedDatetime().getTime()));
            pstmt.setDate(8, new java.sql.Date(bean.getModifiedDatetime().getTime()));
            pstmt.setLong(9, bean.getId());

            pstmt.executeUpdate();
            conn.commit();
            pstmt.close();

        } catch (Exception e) {
            try { conn.rollback(); } catch (Exception ex) {}
            throw new ApplicationException("Exception in Update Job");
        } finally {
            JDBCDataSource.closeConnection(conn);
        }
    }

    // ================= FIND BY PK =================
    public JobBean findByPk(long pk) throws ApplicationException {

        JobBean bean = null;
        Connection conn = null;

        try {
            conn = JDBCDataSource.getConnection();
            PreparedStatement pstmt =
                    conn.prepareStatement("select * from st_job where id=?");

            pstmt.setLong(1, pk);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                bean = new JobBean();
                bean.setId(rs.getLong(1));
                bean.setJobCode(rs.getString(2));
                bean.setJobName(rs.getString(3));
                bean.setCronExpression(rs.getString(4));
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

    // ================= FIND BY CODE =================
    public JobBean findByCode(String code) throws ApplicationException {

        JobBean bean = null;
        Connection conn = null;

        try {
            conn = JDBCDataSource.getConnection();
            PreparedStatement pstmt =
                    conn.prepareStatement("select * from st_job where job_code=?");

            pstmt.setString(1, code);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                bean = new JobBean();
                bean.setId(rs.getLong(1));
                bean.setJobCode(rs.getString(2));
                bean.setJobName(rs.getString(3));
                bean.setCronExpression(rs.getString(4));
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

    // ================= SEARCH =================
    public List search(JobBean bean, int pageNo, int pageSize)
            throws ApplicationException {

        StringBuffer sql = new StringBuffer("select * from st_job where 1=1");

        if (bean != null) {

            if (bean.getId() > 0)
                sql.append(" and id=" + bean.getId());

            if (bean.getJobCode() != null && bean.getJobCode().length() > 0)
                sql.append(" and job_code like '" + bean.getJobCode() + "%'");
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
                bean = new JobBean();
                bean.setId(rs.getLong(1));
                bean.setJobCode(rs.getString(2));
                bean.setJobName(rs.getString(3));
                bean.setCronExpression(rs.getString(4));
                bean.setStatus(rs.getString(5));
                list.add(bean);
            }

        } catch (Exception e) {
            throw new ApplicationException("Exception in Search Job");
        } finally {
            JDBCDataSource.closeConnection(conn);
        }

        return list;
    }

    // ================= LIST =================
    public List list(int pageNo, int pageSize)
            throws ApplicationException {

        ArrayList list = new ArrayList();
        StringBuffer sql = new StringBuffer("select * from st_job");

        if (pageSize > 0) {
            pageNo = (pageNo - 1) * pageSize;
            sql.append(" limit " + pageNo + "," + pageSize);
        }

        Connection conn = null;
        JobBean bean = null;

        try {
            conn = JDBCDataSource.getConnection();
            PreparedStatement pstmt =
                    conn.prepareStatement(sql.toString());

            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                bean = new JobBean();
                bean.setId(rs.getLong(1));
                bean.setJobCode(rs.getString(2));
                bean.setJobName(rs.getString(3));
                bean.setCronExpression(rs.getString(4));
                bean.setStatus(rs.getString(5));
                list.add(bean);
            }

        } catch (Exception e) {
            throw new ApplicationException("Exception in List Job");
        } finally {
            JDBCDataSource.closeConnection(conn);
        }

        return list;
    }
}