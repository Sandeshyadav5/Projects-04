package in.co.rays.proj4.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import in.co.rays.proj4.bean.CollegeBean;
import in.co.rays.proj4.exception.ApplicationException;
import in.co.rays.proj4.exception.DatabaseException;
import in.co.rays.proj4.util.JDBCDataSource;

public class CollegeModel {
	public Integer nextPk() throws DatabaseException {
		Connection conn=null;
		int pk=0;
		try {
			conn=JDBCDataSource.getConnection();
			PreparedStatement pstmt=conn.prepareStatement("select max(id) from st_college");
			ResultSet rs=pstmt.executeQuery();
			while(rs.next()) {
				pk=rs.getInt(1);
			}
			rs.close();
			pstmt.close();
		} catch (Exception e) {
			throw new DatabaseException("Exception : Exception in getting PK");
		}finally {
			JDBCDataSource.closeConnection(conn);
		}
		return pk +1;
	}
	public long add(CollegeBean bean) throws ApplicationException {
		Connection conn=null;
		int pk=0;
		try {
			conn=JDBCDataSource.getConnection();
			pk=nextPk();
			conn.setAutoCommit(false);
			PreparedStatement pstmt=conn.prepareStatement("insert into st_college value(?,?,?,?,?,?,?,?,?,?)");
			pstmt.setInt(1, pk);
			pstmt.setString(2, bean.getName());
			pstmt.setString(3, bean.getAddress());
			pstmt.setString(4, bean.getState());
			pstmt.setString(5, bean.getCity());
			pstmt.setString(6, bean.getPhoneNo());
			pstmt.setString(7, bean.getCreatedBy());
			pstmt.setString(8, bean.getModifiedBy());
			pstmt.setTimestamp(9, bean.getCreatedDatetime());
			pstmt.setTimestamp(10, bean.getModifiedDatetime());
			
			pstmt.executeUpdate();
			conn.commit();
			pstmt.close();
		} catch (Exception e) {
			try {
				conn.rollback();
			} catch (Exception e2) {
			throw new ApplicationException("Add rollback exception " + e2.getMessage());
			}
			throw new ApplicationException("Exception in add User");
		}finally {
			JDBCDataSource.closeConnection(conn);
		}
		
		return pk;
	}
	public void update(CollegeBean bean) throws ApplicationException {
		Connection conn=null;
		try {
		conn =JDBCDataSource.getConnection();
		conn.setAutoCommit(false);
		PreparedStatement pstmt =conn.prepareStatement("update st_college set name=?,address=?,state=?,city=?,  phone_no = ?, created_by = ?, modified_by = ?, created_datetime = ?, modified_datetime = ? where id = ?");
		pstmt.setLong(10, bean.getId());
		pstmt.setString(1, bean.getName());
		pstmt.setString(2, bean.getAddress());
		pstmt.setString(3, bean.getState());
		pstmt.setString(4, bean.getCity());
		pstmt.setString(5, bean.getPhoneNo());
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
			} catch (Exception e2) {
			throw new ApplicationException("update rollback exception " + e2.getMessage());

			}
			throw new ApplicationException("Exception in update User");
		}finally {
			JDBCDataSource.closeConnection(conn);
		}
		
	}
	public void delete (CollegeBean bean) throws ApplicationException {
		Connection conn =null;
		try {
			conn=JDBCDataSource.getConnection();
			conn.setAutoCommit(false);
			PreparedStatement pstmt=conn.prepareStatement("delete from st_college where id  =?");
			pstmt.setLong(1, bean.getId());
			pstmt.executeUpdate();
			conn.commit();
			pstmt.close();
		}catch (Exception e) {
			try {
				conn.rollback();
			} catch (Exception e2) {
			throw new ApplicationException("delete rollback exception " + e2.getMessage());

			}
			throw new ApplicationException("Exception in delete User");
		}finally {
			JDBCDataSource.closeConnection(conn);
		}
		
	}
	public CollegeBean findByPk(long pk) throws ApplicationException {
		CollegeBean bean=null;
		Connection conn=null;
		StringBuffer sql=new StringBuffer("select * from st_college where id=?");
		try {
			conn =JDBCDataSource.getConnection();
			PreparedStatement pstmt=conn.prepareStatement(sql.toString());
			pstmt.setLong(1, pk);
			ResultSet rs=pstmt.executeQuery();
			while (rs.next()) {
				bean=new CollegeBean();
				bean.setId(rs.getLong(1));
				bean.setName(rs.getString(2));
				bean.setAddress(rs.getString(3));
				bean.setState(rs.getString(4));
				bean.setCity(rs.getString(5));
				bean.setPhoneNo(rs.getString(6));
				bean.setCreatedBy(rs.getString(7));
				bean.setModifiedBy(rs.getString(8));
				bean.setCreatedDatetime(rs.getTimestamp(9));
				bean.setModifiedDatetime(rs.getTimestamp(10));
			}
			rs.close();
			pstmt.close();
		} catch (Exception e) {
			throw new ApplicationException("Exception: Exception in getting college by pk");
		}finally {
			JDBCDataSource.closeConnection(conn);
		}
		
		return bean;
		
	}
	public List search(CollegeBean bean) throws ApplicationException {
		StringBuffer sql  =new StringBuffer("select * from st_college where 1=1");
	ArrayList list=new ArrayList();
	Connection  conn=null;
	try {
		 
				conn =JDBCDataSource.getConnection();
				PreparedStatement pstmt=conn.prepareStatement(sql.toString());
				ResultSet rs=pstmt.executeQuery();
				while (rs.next()) {
					bean=new CollegeBean();
					bean.setId(rs.getLong(1));
					bean.setName(rs.getString(2));
					bean.setAddress(rs.getString(3));
					bean.setState(rs.getString(4));
					bean.setCity(rs.getString(5));
					bean.setPhoneNo(rs.getString(6));
					bean.setCreatedBy(rs.getString(7));
					bean.setModifiedBy(rs.getString(8));
					bean.setCreatedDatetime(rs.getTimestamp(9));
					bean.setModifiedDatetime(rs.getTimestamp(10));
					list.add(bean);
				}
				rs.close();
				pstmt.close();
	} catch (Exception e) {
	    throw new  ApplicationException("Exception: Exception in search college");
	}finally {
		JDBCDataSource.closeConnection(conn);
	}
	
	return list;
	
	
	}
}
