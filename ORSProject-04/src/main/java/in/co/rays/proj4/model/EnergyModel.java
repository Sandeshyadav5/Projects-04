package in.co.rays.proj4.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import in.co.rays.proj4.bean.EnergyBean;
import in.co.rays.proj4.exception.ApplicationException;
import in.co.rays.proj4.exception.DatabaseException;
import in.co.rays.proj4.exception.DuplicateRecordException;
import in.co.rays.proj4.util.JDBCDataSource;

public class EnergyModel {

	// ==================== NEXT PK ====================
	public Integer nextPK() throws DatabaseException {

		Connection conn = null;
		int pk = 0;

		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement("select max(id) from st_energy");

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
	public long add(EnergyBean bean) throws ApplicationException, DuplicateRecordException {

		Connection conn = null;
		int pk = 0;

		EnergyBean duplicate = findByCode(bean.getEnergyCode());
		if (duplicate != null) {
			throw new DuplicateRecordException("Energy Code already exists");
		}

		try {
			conn = JDBCDataSource.getConnection();
			pk = nextPK();
			conn.setAutoCommit(false);

			PreparedStatement pstmt = conn.prepareStatement("insert into st_energy values (?,?,?,?,?,?,?,?,?)");

			pstmt.setInt(1, pk);
			pstmt.setString(2, bean.getEnergyCode());
			pstmt.setString(3, bean.getDeviceName());
			pstmt.setLong(4, bean.getUnitsConsumed());
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
				throw new ApplicationException(e.getMessage());
			}
			throw new ApplicationException("Exception in Add Energy");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}

		return pk;
	}

	// ==================== DELETE ====================
	public void delete(EnergyBean bean) throws ApplicationException {

		Connection conn = null;

		try {
			conn = JDBCDataSource.getConnection();
			conn.setAutoCommit(false);

			PreparedStatement pstmt = conn.prepareStatement("delete from st_energy where id=?");

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
			throw new ApplicationException("Exception in Delete Energy");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
	}

	// ==================== UPDATE ====================
	public void update(EnergyBean bean) throws ApplicationException, DuplicateRecordException {

		Connection conn = null;

		EnergyBean duplicate = findByCode(bean.getEnergyCode());

		if (duplicate != null && duplicate.getId() != bean.getId()) {
			throw new DuplicateRecordException("Energy Code already exists");
		}

		try {
			conn = JDBCDataSource.getConnection();
			conn.setAutoCommit(false);

			PreparedStatement pstmt = conn.prepareStatement(
					"update st_energy set energy_code=?, device_name=?, units_consumed=?, status=?, created_by=?, modified_by=?, created_datetime=?, modified_datetime=? where id=?");

			pstmt.setString(1, bean.getEnergyCode());
			pstmt.setString(2, bean.getDeviceName());
			pstmt.setLong(3, bean.getUnitsConsumed());
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
			throw new ApplicationException("Exception in Update Energy");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
	}

	// ==================== FIND BY PK ====================
	public EnergyBean findByPk(long pk) throws ApplicationException {

		EnergyBean bean = null;
		Connection conn = null;

		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement("select * from st_energy where id=?");

			pstmt.setLong(1, pk);
			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {
				bean = new EnergyBean();
				bean.setId(rs.getLong(1));
				bean.setEnergyCode(rs.getString(2));
				bean.setDeviceName(rs.getString(3));
				bean.setUnitsConsumed(rs.getLong(4));
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
	public EnergyBean findByCode(String code) throws ApplicationException {

		EnergyBean bean = null;
		Connection conn = null;

		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement("select * from st_energy where energy_code=?");

			pstmt.setString(1, code);
			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {
				bean = new EnergyBean();
				bean.setId(rs.getLong(1));
				bean.setEnergyCode(rs.getString(2));
				bean.setDeviceName(rs.getString(3));
				bean.setUnitsConsumed(rs.getLong(4));
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
	public List search(EnergyBean bean, int pageNo, int pageSize) throws ApplicationException {

		StringBuffer sql = new StringBuffer("select * from st_energy where 1=1");

		if (bean != null) {

			if (bean.getId() > 0) {
				sql.append(" and id = " + bean.getId());
			}

			if (bean.getEnergyCode() != null && bean.getEnergyCode().length() > 0) {
				sql.append(" and energy_code like '" + bean.getEnergyCode() + "%'");
			}

			if (bean.getDeviceName() != null && bean.getDeviceName().length() > 0) {
				sql.append(" and device_name like '" + bean.getDeviceName() + "%'");
			}
			if (bean.getUnitsConsumed() > 0) {
				sql.append(" AND units_consumed = '" + bean.getUnitsConsumed() + "%'");
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
			PreparedStatement pstmt = conn.prepareStatement(sql.toString());

			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {
				bean = new EnergyBean();
				bean.setId(rs.getLong(1));
				bean.setEnergyCode(rs.getString(2));
				bean.setDeviceName(rs.getString(3));
				bean.setUnitsConsumed(rs.getLong(4));
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
			throw new ApplicationException("Exception in Search Energy");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}

		return list;
	}

	// ==================== LIST ====================
	public List list(int pageNo, int pageSize) throws ApplicationException {

		ArrayList list = new ArrayList();
		StringBuffer sql = new StringBuffer("select * from st_energy");

		if (pageSize > 0) {
			pageNo = (pageNo - 1) * pageSize;
			sql.append(" limit " + pageNo + "," + pageSize);
		}

		Connection conn = null;
		EnergyBean bean = null;

		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql.toString());

			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {
				bean = new EnergyBean();
				bean.setId(rs.getLong(1));
				bean.setEnergyCode(rs.getString(2));
				bean.setDeviceName(rs.getString(3));
				bean.setUnitsConsumed(rs.getLong(4));
				bean.setStatus(rs.getString(5));
				list.add(bean);
			}

			rs.close();

		} catch (Exception e) {
			throw new ApplicationException("Exception in List Energy");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}

		return list;
	}
}