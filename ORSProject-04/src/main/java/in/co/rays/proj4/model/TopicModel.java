package in.co.rays.proj4.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import in.co.rays.proj4.bean.TopicBean;
import in.co.rays.proj4.exception.ApplicationException;
import in.co.rays.proj4.exception.DatabaseException;
import in.co.rays.proj4.exception.DuplicateRecordException;
import in.co.rays.proj4.util.JDBCDataSource;

public class TopicModel {

    // ==================== NEXT PK ====================
    public Integer nextPK() throws DatabaseException {

        Connection conn = null;
        int pk = 0;

        try {
            conn = JDBCDataSource.getConnection();
            PreparedStatement pstmt =
                conn.prepareStatement("select max(id) from st_topic");

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
    public long add(TopicBean bean) throws ApplicationException, DuplicateRecordException {

        Connection conn = null;
        int pk = 0;

        TopicBean duplicate = findByCode(bean.getTopicCode());
        if (duplicate != null) {
            throw new DuplicateRecordException("Topic Code already exists");
        }

        try {
            conn = JDBCDataSource.getConnection();
            pk = nextPK();
            conn.setAutoCommit(false);

            PreparedStatement pstmt = conn.prepareStatement(
                "insert into st_topic values(?,?,?,?,?,?,?,?,?)");

            pstmt.setInt(1, pk);
            pstmt.setString(2, bean.getTopicCode());
            pstmt.setString(3, bean.getTopicName());
            pstmt.setInt(4, bean.getPartitions());
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
            throw new ApplicationException("Exception in Add Topic");
        } finally {
            JDBCDataSource.closeConnection(conn);
        }

        return pk;
    }

    // ==================== DELETE ====================
    public void delete(TopicBean bean) throws ApplicationException {

        Connection conn = null;

        try {
            conn = JDBCDataSource.getConnection();
            conn.setAutoCommit(false);

            PreparedStatement pstmt =
                conn.prepareStatement("delete from st_topic where id=?");

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
            throw new ApplicationException("Exception in Delete Topic");
        } finally {
            JDBCDataSource.closeConnection(conn);
        }
    }

    // ==================== UPDATE ====================
    public void update(TopicBean bean) throws ApplicationException, DuplicateRecordException {

        Connection conn = null;

        TopicBean duplicate = findByCode(bean.getTopicCode());
        if (duplicate != null && duplicate.getId() != bean.getId()) {
            throw new DuplicateRecordException("Topic Code already exists");
        }

        try {
            conn = JDBCDataSource.getConnection();
            conn.setAutoCommit(false);

            PreparedStatement pstmt = conn.prepareStatement(
                "update st_topic set topic_code=?, topic_name=?, partitions=?, status=?, created_by=?, modified_by=?, created_datetime=?, modified_datetime=? where id=?");

            pstmt.setString(1, bean.getTopicCode());
            pstmt.setString(2, bean.getTopicName());
            pstmt.setInt(3, bean.getPartitions());
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
            throw new ApplicationException("Exception in Update Topic");
        } finally {
            JDBCDataSource.closeConnection(conn);
        }
    }

    // ==================== FIND BY PK ====================
    public TopicBean findByPk(long pk) throws ApplicationException {

        TopicBean bean = null;
        Connection conn = null;

        try {
            conn = JDBCDataSource.getConnection();
            PreparedStatement pstmt =
                conn.prepareStatement("select * from st_topic where id=?");

            pstmt.setLong(1, pk);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                bean = new TopicBean();
                bean.setId(rs.getLong(1));
                bean.setTopicCode(rs.getString(2));
                bean.setTopicName(rs.getString(3));
                bean.setPartitions(rs.getInt(4));
                bean.setStatus(rs.getString(5));
                bean.setCreatedBy(rs.getString(6));
                bean.setModifiedBy(rs.getString(7));
                bean.setCreatedDatetime(rs.getTimestamp(8));
                bean.setModifiedDatetime(rs.getTimestamp(9));
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
    public TopicBean findByCode(String code) throws ApplicationException {

        TopicBean bean = null;
        Connection conn = null;

        try {
            conn = JDBCDataSource.getConnection();
            PreparedStatement pstmt =
                conn.prepareStatement("select * from st_topic where topic_code=?");

            pstmt.setString(1, code);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                bean = new TopicBean();
                bean.setId(rs.getLong(1));
                bean.setTopicCode(rs.getString(2));
                bean.setTopicName(rs.getString(3));
            }

            rs.close();
            pstmt.close();

        } catch (Exception e) {
            throw new ApplicationException("Exception in Find By Code");
        } finally {
            JDBCDataSource.closeConnection(conn);
        }

        return bean;
    }

    // ==================== SEARCH ====================
    public List search(TopicBean bean, int pageNo, int pageSize)
            throws ApplicationException {

        StringBuffer sql = new StringBuffer("select * from st_topic where 1=1");

        if (bean != null) {

            if (bean.getId() > 0)
                sql.append(" and id=" + bean.getId());

            if (bean.getTopicCode() != null && bean.getTopicCode().length() > 0)
                sql.append(" and topic_code like '" + bean.getTopicCode() + "%'");

            if (bean.getTopicName() != null && bean.getTopicName().length() > 0)
                sql.append(" and topic_name like '" + bean.getTopicName() + "%'");
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
                bean = new TopicBean();
                bean.setId(rs.getLong(1));
                bean.setTopicCode(rs.getString(2));
                bean.setTopicName(rs.getString(3));
                bean.setPartitions(rs.getInt(4));
                bean.setStatus(rs.getString(5));
                list.add(bean);
            }

            rs.close();
            pstmt.close();

        } catch (Exception e) {
            throw new ApplicationException("Exception in Search Topic");
        } finally {
            JDBCDataSource.closeConnection(conn);
        }

        return list;
    }

    // ==================== LIST ====================
    public List list(int pageNo, int pageSize)
            throws ApplicationException {

        ArrayList list = new ArrayList();
        StringBuffer sql = new StringBuffer("select * from st_topic");

        if (pageSize > 0) {
            pageNo = (pageNo - 1) * pageSize;
            sql.append(" limit " + pageNo + "," + pageSize);
        }

        Connection conn = null;
        TopicBean bean = null;

        try {
            conn = JDBCDataSource.getConnection();
            PreparedStatement pstmt =
                conn.prepareStatement(sql.toString());

            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                bean = new TopicBean();
                bean.setId(rs.getLong(1));
                bean.setTopicCode(rs.getString(2));
                bean.setTopicName(rs.getString(3));
                bean.setPartitions(rs.getInt(4));
                bean.setStatus(rs.getString(5));
                list.add(bean);
            }

            rs.close();

        } catch (Exception e) {
            throw new ApplicationException("Exception in List Topic");
        } finally {
            JDBCDataSource.closeConnection(conn);
        }

        return list;
    }
}