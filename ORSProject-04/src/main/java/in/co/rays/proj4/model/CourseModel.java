package in.co.rays.proj4.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import in.co.rays.proj4.bean.CourseBean;
import in.co.rays.proj4.exception.ApplicationException;
import in.co.rays.proj4.exception.DatabaseException;
import in.co.rays.proj4.exception.DuplicateRecordException;
import in.co.rays.proj4.util.JDBCDataSource;

/**
 * Model class for Course.
 * Handles all database operations like add, update, delete, search, and fetch.
 */
public class CourseModel {

    /**
     * Generates next primary key.
     * 
     * @return next primary key
     * @throws DatabaseException if database error occurs
     */
    private Integer nextPk() throws DatabaseException {
        Connection conn = null;
        int pk = 0;
        try {
            conn = JDBCDataSource.getConnection();
            PreparedStatement pstmt = conn.prepareStatement("select max(id) from st_course");
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                pk = rs.getInt(1);
            }
            rs.close();
            pstmt.close();
        } catch (Exception e) {
            throw new DatabaseException("Exception  in getting pk");
        } finally {
            JDBCDataSource.closeConnection(conn);
        }
        return pk + 1;
    }

    /**
     * Adds a new course.
     * 
     * @param bean CourseBean containing course details
     * @return generated primary key
     * @throws ApplicationException if application error occurs
     * @throws DuplicateRecordException if course name already exists
     */
    public long add(CourseBean bean) throws ApplicationException, DuplicateRecordException {
        Connection conn = null;
        int pk = 0;
        CourseBean existName = findByName(bean.getName());
        if (existName != null) {
            throw new DuplicateRecordException("Course name already exist");
        }
        try {
            conn = JDBCDataSource.getConnection();
            pk = nextPk();
            conn.setAutoCommit(false);
            PreparedStatement pstmt = conn.prepareStatement("insert into st_course values(?, ?, ?, ?, ?, ?, ?, ?)");
            pstmt.setInt(1, pk);
            pstmt.setString(2, bean.getName());
            pstmt.setString(3, bean.getDuration());
            pstmt.setString(4, bean.getDescription());
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
                throw new ApplicationException("Exception in rollback");
            }
            throw new ApplicationException("Exception in getting add course");
        } finally {
            JDBCDataSource.closeConnection(conn);
        }
        return pk;
    }

    /**
     * Updates existing course.
     * 
     * @param bean CourseBean with updated data
     * @throws ApplicationException if error occurs
     * @throws DuplicateRecordException if duplicate course name exists
     */
    public void update(CourseBean bean) throws ApplicationException, DuplicateRecordException {
        Connection conn = null;
        CourseBean existName = findByName(bean.getName());
        if (existName != null && existName.getId() != bean.getId()) {
            throw new DuplicateRecordException("Course name already exist");
        }

        try {
            conn = JDBCDataSource.getConnection();
            conn.setAutoCommit(false);
            PreparedStatement pstmt = conn.prepareStatement(
                    "update st_course set name=?, duration=?, description=?,created_by = ?, modified_by = ?, created_datetime = ?, modified_datetime = ? where id =?");
            pstmt.setLong(9, bean.getId());
            pstmt.setString(1, bean.getName());
            pstmt.setString(2, bean.getDuration());
            pstmt.setString(3, bean.getDescription());
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
                throw new ApplicationException("Exception in rollback");
            }
            throw new ApplicationException("Exception in getting updating course");
        } finally {
            JDBCDataSource.closeConnection(conn);
        }
    }

    /**
     * Deletes course by ID.
     * 
     * @param bean CourseBean containing ID
     * @throws ApplicationException if error occurs
     */
    public void delete(CourseBean bean) throws ApplicationException {
        Connection conn = null;

        try {
            conn = JDBCDataSource.getConnection();
            conn.setAutoCommit(false);
            PreparedStatement pstmt = conn.prepareStatement("delete from st_course where id =?");
            pstmt.setLong(1, bean.getId());
            pstmt.executeUpdate();
            conn.commit();
            pstmt.close();
        } catch (Exception e) {
            try {
                conn.rollback();
            } catch (Exception ex) {
                throw new ApplicationException("Exception in rollback");
            }
            throw new ApplicationException("Exception in getting delete course");
        } finally {
            JDBCDataSource.closeConnection(conn);
        }
    }

    /**
     * Finds course by primary key.
     * 
     * @param pk primary key
     * @return CourseBean if found
     * @throws ApplicationException if error occurs
     */
    public CourseBean findByPk(long pk) throws ApplicationException {
        CourseBean bean = null;
        Connection conn = null;

        StringBuffer sql = new StringBuffer("select * from st_course where id=?");
        try {
            conn = JDBCDataSource.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql.toString());
            pstmt.setLong(1, pk);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                bean = new CourseBean();
                bean.setId(rs.getLong(1));
                bean.setName(rs.getString(2));
                bean.setDuration(rs.getString(3));
                bean.setDescription(rs.getString(4));
            }
            rs.close();
            pstmt.close();
        } catch (Exception e) {
            throw new ApplicationException("Exception  in getting pk");
        } finally {
            JDBCDataSource.closeConnection(conn);
        }
        return bean;
    }

    /**
     * Finds course by name.
     * 
     * @param name course name
     * @return CourseBean if found
     * @throws ApplicationException if error occurs
     */
    public CourseBean findByName(String name) throws ApplicationException {
        CourseBean bean = null;
        Connection conn = null;

        StringBuffer sql = new StringBuffer("select * from st_course where name=?");
        try {
            conn = JDBCDataSource.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql.toString());
            pstmt.setString(1, name);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                bean = new CourseBean();
                bean.setId(rs.getLong(1));
                bean.setName(rs.getString(2));
                bean.setDuration(rs.getString(3));
                bean.setDescription(rs.getString(4));
            }
            rs.close();
            pstmt.close();
        } catch (Exception e) {
            throw new ApplicationException("Exception  in getting name");
        } finally {
            JDBCDataSource.closeConnection(conn);
        }
        return bean;
    }

    /**
     * Returns all courses.
     * 
     * @return list of CourseBean
     * @throws ApplicationException if error occurs
     */
    public List<CourseBean> list() throws ApplicationException {
        return search(null, 0, 0);
    }

    /**
     * Searches courses based on criteria with pagination.
     * 
     * @param bean search criteria
     * @param pageNo page number
     * @param pageSize number of records per page
     * @return list of CourseBean
     * @throws ApplicationException if error occurs
     */
    public List search(CourseBean bean, int pageNo, int pageSize) throws ApplicationException {

        StringBuffer sql = new StringBuffer("select * from st_course where 1=1");
        ArrayList list = new ArrayList();
        Connection conn = null;

        if (bean != null) {

            if (bean.getId() > 0) {
                sql.append(" and id = " + bean.getId());
            }

            if (bean.getName() != null && bean.getName().length() > 0) {
                sql.append(" and name like '" + bean.getName() + "%'");
            }

            if (bean.getDuration() != null && bean.getDuration().length() > 0) {
                sql.append(" and duration like '" + bean.getDuration() + "%'");
            }

            if (bean.getDescription() != null && bean.getDescription().length() > 0) {
                sql.append(" and description like '" + bean.getDescription() + "%'");
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
                bean = new CourseBean();
                bean.setId(rs.getLong(1));
                bean.setName(rs.getString(2));
                bean.setDuration(rs.getString(3));
                bean.setDescription(rs.getString(4));
                list.add(bean);
            }
            rs.close();
            pstmt.close();
        } catch (Exception e) {
            throw new ApplicationException("Exception  in getting pk");
        } finally {
            JDBCDataSource.closeConnection(conn);
        }
        return list;
    }
}