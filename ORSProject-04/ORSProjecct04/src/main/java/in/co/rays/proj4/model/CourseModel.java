package in.co.rays.proj4.model;

import java.beans.beancontext.BeanContext;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import in.co.rays.proj4.bean.CollegeBean;
import in.co.rays.proj4.bean.CourseBean;
import in.co.rays.proj4.bean.RoleBean;
import in.co.rays.proj4.exception.ApplicationException;
import in.co.rays.proj4.exception.DatabaseException;
import in.co.rays.proj4.exception.DuplicateRecordException;
import in.co.rays.proj4.util.JDBCDataSource;

public class CourseModel {
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
	public long add(CourseBean bean) throws ApplicationException, DuplicateRecordException {
		Connection conn = null;
		int pk = 0;
		CourseBean existName=findByName(bean.getName());
		if(existName!=null) {
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

	public void update(CourseBean bean) throws ApplicationException, DuplicateRecordException {
		Connection conn = null;
		CourseBean existName=findByName(bean.getName());
		if(existName!=null && existName.getId()!=bean.getId()) {
			throw new DuplicateRecordException("Course name already exist");
		}

		try {
			conn = JDBCDataSource.getConnection();
			conn.setAutoCommit(false);
			PreparedStatement pstmt = conn
					.prepareStatement("update st_course set name=?, duration=?, description=?,created_by = ?, modified_by = ?, created_datetime = ?, modified_datetime = ? where id =?");
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
	public List<RoleBean> list() throws Exception {
		return search(null, 0, 0);
	}
	public List search(CourseBean bean, int pageSize,int pageNo) throws ApplicationException {
		
		
		StringBuffer sql = new StringBuffer("select * from st_course where 1=1");
		ArrayList list=new ArrayList();
		Connection conn = null;
		if(bean!=null) {
			if(bean.getId()>0) {
				sql.append("and id like "+bean.getId()+"%");
			}
			if(bean.getName()!=null && bean.getName().length()>0) {
				sql.append("and name like "+bean.getId()+"%");
			}
			if(bean.getDuration()!=null && bean.getDuration().length()>0) {
				sql.append("and Duration like "+bean.getDuration()+"%");
			}
			if(bean.getDescription()!=null && bean.getDescription().length()>0) {
				sql.append("and Description like "+bean.getDescription()+"%");
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
