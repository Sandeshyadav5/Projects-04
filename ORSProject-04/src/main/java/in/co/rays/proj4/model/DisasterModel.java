package in.co.rays.proj4.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import in.co.rays.proj4.bean.DisasterBean;
import in.co.rays.proj4.exception.ApplicationException;
import in.co.rays.proj4.exception.DatabaseException;
import in.co.rays.proj4.exception.DuplicateRecordException;
import in.co.rays.proj4.util.JDBCDataSource;

public class DisasterModel {

	// ==================== NEXT PK ====================
	public Integer nextPK() throws DatabaseException {

		Connection conn = null;
		int pk = 0;

		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement("select max(id) from st_disaster");

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
	public long add(DisasterBean bean) throws ApplicationException, DuplicateRecordException {

		Connection conn = null;
		int pk = 0;

		DisasterBean duplicate = findByCode(bean.getAlertType());
		if (duplicate != null) {
			throw new DuplicateRecordException("Disaster Code already exists");
		}

		try {
			conn = JDBCDataSource.getConnection();
			pk = nextPK();
			conn.setAutoCommit(false);

			PreparedStatement pstmt = conn.prepareStatement("insert into st_disaster values (?,?,?,?,?,?,?,?,?)");

			pstmt.setInt(1, pk);
			pstmt.setString(2, bean.getAlertType());
			pstmt.setString(3, bean.getLocation());
			pstmt.setString(4, bean.getSeverty());
			pstmt.setDate(5,new java.sql.Date(bean.getAlertTime().getTime()));
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
				throw new ApplicationException(e.getMessage());
			}
			throw new ApplicationException("Exception in Add Disaster");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}

		return pk;
	}

	// ==================== DELETE ====================
	public void delete(DisasterBean bean) throws ApplicationException {

		Connection conn = null;

		try {
			conn = JDBCDataSource.getConnection();
			conn.setAutoCommit(false);

			PreparedStatement pstmt = conn.prepareStatement("delete from st_disaster where id=?");

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
			throw new ApplicationException("Exception in Delete Disaster");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
	}

	// ==================== UPDATE ====================
	public void update(DisasterBean bean) throws ApplicationException, DuplicateRecordException {

		Connection conn = null;

		DisasterBean duplicate = findByCode(bean.getAlertType());

		if (duplicate != null && duplicate.getId() != bean.getId()) {
			throw new DuplicateRecordException("Disaster Code already exists");
		}

		try {
			conn = JDBCDataSource.getConnection();
			conn.setAutoCommit(false);

			PreparedStatement pstmt = conn.prepareStatement(
					"update st_disaster set alert_type=?, location=?, severty=?, alert_time=?, created_by=?, modified_by=?, created_datetime=?, modified_datetime=? where id=?");

			pstmt.setString(1, bean.getAlertType());
			pstmt.setString(2, bean.getLocation());
			pstmt.setString(3, bean.getSeverty());
			pstmt.setDate(4,new java.sql.Date(bean.getAlertTime().getTime()));
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
			throw new ApplicationException("Exception in Update Disaster");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
	}

	// ==================== FIND BY PK ====================
	public DisasterBean findByPk(long pk) throws ApplicationException {

		DisasterBean bean = null;
		Connection conn = null;

		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement("select * from st_disaster where id=?");

			pstmt.setLong(1, pk);
			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {
				bean = new DisasterBean();
				bean.setId(rs.getLong(1));
				bean.setAlertType(rs.getString(2));
				bean.setLocation(rs.getString(3));
				bean.setSeverty(rs.getString(4));
				bean.setAlertTime(rs.getDate(5));
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
	public DisasterBean findByCode(String code) throws ApplicationException {

		DisasterBean bean = null;
		Connection conn = null;

		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement("select * from st_disaster where alert_type=?");

			pstmt.setString(1, code);
			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {
				bean = new DisasterBean();
				bean.setId(rs.getLong(1));
				bean.setAlertType(rs.getString(2));
				bean.setLocation(rs.getString(3));
				bean.setSeverty(rs.getString(4));
				bean.setAlertTime(rs.getDate(5));
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
	public List search(DisasterBean bean, int pageNo, int pageSize) throws ApplicationException {

		StringBuffer sql = new StringBuffer("select * from st_disaster where 1=1");

		if (bean != null) {

			if (bean.getId() > 0) {
				sql.append(" and id = " + bean.getId());
			}

			if (bean.getAlertType() != null && bean.getAlertType().length() > 0) {
				sql.append(" and alert_type like '" + bean.getAlertType() + "%'");
			}

			if (bean.getLocation() != null && bean.getLocation().length() > 0) {
				sql.append(" and location like '" + bean.getLocation() + "%'");
			}
			if (bean.getSeverty() != null) {
				sql.append(" AND severty = '" + bean.getSeverty() + "%'");
			}

			if (bean.getAlertTime() != null ) {
				sql.append(" and alert_time like '" +new java.sql.Date(bean.getAlertTime().getTime())+ "%'");
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
			PreparedStatement pstmt = conn.prepareStatement(sql.toString());

			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {
				bean = new DisasterBean();
				bean.setId(rs.getLong(1));
				bean.setAlertType(rs.getString(2));
				bean.setLocation(rs.getString(3));
				bean.setSeverty(rs.getString(4));
				bean.setAlertTime(rs.getDate(5));
				bean.setCreatedBy(rs.getString(6));
				bean.setModifiedBy(rs.getString(7));
				bean.setCreatedDatetime(rs.getTimestamp(8));
				bean.setModifiedDatetime(rs.getTimestamp(9));
				list.add(bean);
			}

			rs.close();
			pstmt.close();

		} catch (Exception e) {
			throw new ApplicationException("Exception in Search Disaster");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}

		return list;
	}

	// ==================== LIST ====================
	public List list(int pageNo, int pageSize) throws ApplicationException {

		ArrayList list = new ArrayList();
		StringBuffer sql = new StringBuffer("select * from st_disaster");

		if (pageSize > 0) {
			pageNo = (pageNo - 1) * pageSize;
			sql.append(" limit " + pageNo + "," + pageSize);
		}

		Connection conn = null;
		DisasterBean bean = null;

		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql.toString());

			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {
				bean = new DisasterBean();
				bean.setId(rs.getLong(1));
				bean.setAlertType(rs.getString(2));
				bean.setLocation(rs.getString(3));
				bean.setSeverty(rs.getString(4));
				bean.setAlertTime(rs.getDate(5));
				list.add(bean);
			}

			rs.close();

		} catch (Exception e) {
			throw new ApplicationException("Exception in List Disaster");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}

		return list;
	}
}