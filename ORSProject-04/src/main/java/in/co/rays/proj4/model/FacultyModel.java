package in.co.rays.proj4.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import in.co.rays.proj4.bean.CollegeBean;
import in.co.rays.proj4.bean.CourseBean;
import in.co.rays.proj4.bean.FacultyBean;
import in.co.rays.proj4.bean.SubjectBean;
import in.co.rays.proj4.exception.ApplicationException;
import in.co.rays.proj4.exception.DatabaseException;
import in.co.rays.proj4.exception.DuplicateRecordException;
import in.co.rays.proj4.util.JDBCDataSource;

/**
 * Model class for Faculty.
 * Handles database operations like add, update, delete, search, etc.
 */
public class FacultyModel {

	/**
	 * Returns next primary key from st_faculty table.
	 * 
	 * @return next primary key
	 * @throws DatabaseException if database error occurs
	 */
	public Integer nextPk() throws DatabaseException {
		Connection conn = null;
		int pk = 0;
		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement("select max(id) from st_faculty");
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
	 * Adds a new Faculty record.
	 * 
	 * @param bean FacultyBean object
	 * @return generated primary key
	 * @throws ApplicationException if application error occurs
	 * @throws DuplicateRecordException if email already exists
	 */
	public long add(FacultyBean bean) throws ApplicationException, DuplicateRecordException {
		Connection conn = null;
		int pk = 0;
		
		CollegeModel collegeModel = new CollegeModel();
		CollegeBean collegeBean = collegeModel.findByPk(bean.getCollegeId());
		bean.setCollegeName(collegeBean.getName());

		CourseModel courseModel = new CourseModel();
		CourseBean courseBean = courseModel.findByPk(bean.getCourseId());
		bean.setCoursename(courseBean.getName());

		SubjectModel subjectModel = new SubjectModel();
		SubjectBean subjectBean = subjectModel.findByPk(bean.getSubjectId());
		bean.setSubjectName(subjectBean.getName());

		FacultyBean existbean = findByEmail(bean.getEmail());

		if (existbean != null) {
			throw new DuplicateRecordException("Email Id already exists");
		}

		
		FacultyBean existEmail = findByEmail(bean.getEmail());
		if (existEmail != null) {
			throw new DuplicateRecordException("Faculty email already exist");
		}
		try {
			conn = JDBCDataSource.getConnection();
			pk = nextPk();
			conn.setAutoCommit(false);
			PreparedStatement pstmt = conn.prepareStatement(
					"insert into st_faculty values( ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
			pstmt.setInt(1, pk);
			pstmt.setString(2, bean.getFirstname());
			pstmt.setString(3, bean.getLastName());
			pstmt.setDate(4, new java.sql.Date(bean.getDob().getTime()));
			pstmt.setString(5, bean.getGender());
			pstmt.setString(6, bean.getMobileNo());
			pstmt.setString(7, bean.getEmail());
			pstmt.setLong(8, bean.getCollegeId());
			pstmt.setString(9, bean.getCollegeName());
			pstmt.setLong(10, bean.getCourseId());
			pstmt.setString(11, bean.getCoursename());
			pstmt.setLong(12, bean.getSubjectId());
			pstmt.setString(13, bean.getSubjectName());
			pstmt.setString(14, bean.getCreatedBy());
			pstmt.setString(15, bean.getModifiedBy());
			pstmt.setTimestamp(16, bean.getCreatedDatetime());
			pstmt.setTimestamp(17, bean.getModifiedDatetime());
			pstmt.executeUpdate();
			conn.commit();
			pstmt.close();
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
	 * Updates existing Faculty record.
	 * 
	 * @param bean FacultyBean object
	 * @throws ApplicationException if error occurs
	 * @throws DuplicateRecordException if duplicate email found
	 */
	public void update(FacultyBean bean) throws ApplicationException, DuplicateRecordException {
		Connection conn = null;

		CollegeModel collegeModel = new CollegeModel();
		CollegeBean collegeBean = collegeModel.findByPk(bean.getCollegeId());
		bean.setCollegeName(collegeBean.getName());

		CourseModel courseModel = new CourseModel();
		CourseBean courseBean = courseModel.findByPk(bean.getCourseId());
		bean.setCoursename(courseBean.getName());

		SubjectModel subjectModel = new SubjectModel();
		SubjectBean subjectBean = subjectModel.findByPk(bean.getSubjectId());
		bean.setSubjectName(subjectBean.getName());

		FacultyBean existEmail = findByEmail(bean.getEmail());
		if (existEmail != null && existEmail.getId() != bean.getId()) {
			throw new DuplicateRecordException("Faculty email already exist");
		}

		try {
			conn = JDBCDataSource.getConnection();
			conn.setAutoCommit(false);
			PreparedStatement pstmt = conn.prepareStatement(
					"update st_faculty set first_name=?, last_name=?, dob=?, gender=?,mobile_no=?,email=?,college_id=?,college_name=?,course_id=?,course_name=?,subject_id=?,subject_name=?,created_by = ?, modified_by = ?, created_datetime = ?, modified_datetime = ? where id=?");

			pstmt.setLong(17, bean.getId());
			pstmt.setString(1, bean.getFirstname());
			pstmt.setString(2, bean.getLastName());
			pstmt.setDate(3, new java.sql.Date(bean.getDob().getTime()));
			pstmt.setString(4, bean.getGender());
			pstmt.setString(5, bean.getMobileNo());
			pstmt.setString(6, bean.getEmail());
			pstmt.setLong(7, bean.getCollegeId());
			pstmt.setString(8, bean.getCollegeName());
			pstmt.setLong(9, bean.getCourseId());
			pstmt.setString(10, bean.getCoursename());
			pstmt.setLong(11, bean.getSubjectId());
			pstmt.setString(12, bean.getSubjectName());
			pstmt.setString(13, bean.getCreatedBy());
			pstmt.setString(14, bean.getModifiedBy());
			pstmt.setTimestamp(15, bean.getCreatedDatetime());
			pstmt.setTimestamp(16, bean.getModifiedDatetime());
			pstmt.executeUpdate();
			conn.commit();
			pstmt.close();
		} catch (Exception e) {
			try {
				conn.rollback();
			} catch (Exception ex) {
				throw new ApplicationException("Exception in Update  rollback" + ex.getMessage());
			}
			throw new ApplicationException("Exception in Update  faculty");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}

	}

	/**
	 * Deletes Faculty record.
	 * 
	 * @param bean FacultyBean containing id
	 * @throws ApplicationException if error occurs
	 */
	public void delete(FacultyBean bean) throws ApplicationException {
		Connection conn = null;
		try {
			conn = JDBCDataSource.getConnection();
			conn.setAutoCommit(false);
			PreparedStatement pstmt = conn.prepareStatement("delete from st_faculty where id=?");
			pstmt.setLong(1, bean.getId());
			pstmt.executeUpdate();
			conn.commit();
			pstmt.close();
		} catch (Exception e) {
			try {
				conn.rollback();
			} catch (Exception ex) {
				throw new ApplicationException("Exception in delete  rollback" + ex.getMessage());
			}
			throw new ApplicationException("Exception in delete  faculty");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}

	}

	/**
	 * Finds Faculty by primary key.
	 * 
	 * @param pk primary key
	 * @return FacultyBean
	 * @throws ApplicationException if error occurs
	 */
	public FacultyBean findByPk(long pk) throws ApplicationException {
		Connection conn = null;
		FacultyBean bean = null;
		StringBuffer sql = new StringBuffer("select * from  st_faculty where id =?");
		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql.toString());
			pstmt.setLong(1, pk);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				bean = new FacultyBean();
				bean.setId(rs.getLong(1));
				bean.setFirstname(rs.getString(2));
				bean.setLastName(rs.getString(3));
				bean.setDob(rs.getDate(4));
				bean.setGender(rs.getString(5));
				bean.setMobileNo(rs.getString(6));
				bean.setEmail(rs.getString(7));
				bean.setCollegeId(rs.getLong(8));
				bean.setCollegeName(rs.getString(9));
				bean.setCourseId(rs.getLong(10));
				bean.setCoursename(rs.getString(11));
				bean.setSubjectId(rs.getLong(12));
				bean.setSubjectName(rs.getString(13));
			}
			rs.close();
			pstmt.close();
		} catch (Exception e) {
			throw new ApplicationException("Exception:  Exception in getting pk");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		return bean;
	}

	/**
	 * Finds Faculty by email.
	 * 
	 * @param email faculty email
	 * @return FacultyBean
	 * @throws ApplicationException if error occurs
	 */
	public FacultyBean findByEmail(String email) throws ApplicationException {
		Connection conn = null;
		FacultyBean bean = null;
		StringBuffer sql = new StringBuffer("select * from  st_faculty where email =?");
		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql.toString());
			pstmt.setString(1, email);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				bean = new FacultyBean();
				bean.setId(rs.getLong(1));
				bean.setFirstname(rs.getString(2));
				bean.setLastName(rs.getString(3));
				bean.setDob(rs.getDate(4));
				bean.setGender(rs.getString(5));
				bean.setMobileNo(rs.getString(6));
				bean.setEmail(rs.getString(7));
				bean.setCollegeId(rs.getLong(8));
				bean.setCollegeName(rs.getString(9));
				bean.setCourseId(rs.getLong(10));
				bean.setCoursename(rs.getString(11));
				bean.setSubjectId(rs.getLong(12));
				bean.setSubjectName(rs.getString(13));
			}
			rs.close();
			pstmt.close();
		} catch (Exception e) {
			throw new ApplicationException("Exception:  Exception in getting Email");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		return bean;
	}

	/**
	 * Returns list of all Faculty records.
	 */
	public List<FacultyBean> list() throws Exception {
		return search(null, 0, 0);
	}

	/**
	 * Searches Faculty records with filters and pagination.
	 * 
	 * @param bean filter criteria
	 * @param pageNo page number
	 * @param pageSize page size
	 * @return list of FacultyBean
	 * @throws ApplicationException if error occurs
	 */
	public List search(FacultyBean bean,int pageNo, int pageSize) throws ApplicationException {
		Connection conn = null;
		List list = new ArrayList();
		StringBuffer sql = new StringBuffer("select * from  st_faculty where 1=1");
		if (bean != null) {

		    if (bean.getId() > 0) {
		        sql.append(" AND id = " + bean.getId());
		    }

		    if (bean.getFirstname() != null && bean.getFirstname().length() > 0) {
		        sql.append(" AND first_name LIKE '" + bean.getFirstname() + "%'");
		    }

		    if (bean.getLastName() != null && bean.getLastName().length() > 0) {
		        sql.append(" AND last_name LIKE '" + bean.getLastName() + "%'");
		    }

		    if (bean.getDob() != null) {
		        sql.append(" AND dob = '" + new java.sql.Date(bean.getDob().getTime()) + "'");
		    }

		    if (bean.getGender() != null && bean.getGender().length() > 0) {
		        sql.append(" AND gender LIKE '" + bean.getGender() + "%'");
		    }

		    if (bean.getMobileNo() != null && bean.getMobileNo().length() > 0) {
		        sql.append(" AND mobileNo LIKE '" + bean.getMobileNo() + "%'");
		    }

		    if (bean.getEmail() != null && bean.getEmail().length() > 0) {
		        sql.append(" AND email LIKE '" + bean.getEmail() + "%'");
		    }

		    if (bean.getCollegeId() > 0) {
		        sql.append(" AND collegeId = " + bean.getCollegeId());
		    }

		    if (bean.getCollegeName() != null && bean.getCollegeName().length() > 0) {
		        sql.append(" AND collegeName LIKE '" + bean.getCollegeName() + "%'");
		    }

		    if (bean.getCourseId() > 0) {
		        sql.append(" AND courseId = " + bean.getCourseId());
		    }

		    if (bean.getCoursename() != null && bean.getCoursename().length() > 0) {
		        sql.append(" AND courseName LIKE '" + bean.getCoursename() + "%'");
		    }

		    if (bean.getSubjectId() > 0) {
		        sql.append(" AND subjectId = " + bean.getSubjectId());
		    }

		    if (bean.getSubjectName() != null && bean.getSubjectName().length() > 0) {
		        sql.append(" AND subjectName LIKE '" + bean.getSubjectName() + "%'");
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
				bean = new FacultyBean();
				bean.setId(rs.getLong(1));
				bean.setFirstname(rs.getString(2));
				bean.setLastName(rs.getString(3));
				bean.setDob(rs.getDate(4));
				bean.setGender(rs.getString(5));
				bean.setMobileNo(rs.getString(6));
				bean.setEmail(rs.getString(7));
				bean.setCollegeId(rs.getLong(8));
				bean.setCollegeName(rs.getString(9));
				bean.setCourseId(rs.getLong(10));
				bean.setCoursename(rs.getString(11));
				bean.setSubjectId(rs.getLong(12));
				bean.setSubjectName(rs.getString(13));
				list.add(bean);
			}
			rs.close();
			pstmt.close();
		} catch (Exception e) {
			throw new ApplicationException("Exception:  Exception in getting pk");
		} finally {

			JDBCDataSource.closeConnection(conn);
		}
		return list;
	}

}