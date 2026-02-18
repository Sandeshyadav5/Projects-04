package in.co.rays.proj4.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.mysql.cj.jdbc.JdbcConnection;

import in.co.rays.proj4.bean.RoleBean;
import in.co.rays.proj4.util.JDBCDataSource;

public class RoleModel {
	public Integer nextPk() throws Exception{
		Connection conn=null;
		int pk=0;
		
		conn=JDBCDataSource.getConnection();
		
		PreparedStatement pstmt=conn.prepareStatement("select max(id) from st_role");
		ResultSet rs=pstmt.executeQuery();
		
		while (rs.next()) {
			pk =rs.getInt(1);
		}
		rs.close();
		
		JDBCDataSource.closeConnection(conn);
		return pk +1;
	}
	public long add(RoleBean bean) throws Exception {
		Connection conn=null;
		int pk=0;
		
		pk=nextPk();
		conn=JDBCDataSource.getConnection();
		conn.setAutoCommit(false);
		PreparedStatement pstmt=conn.prepareStatement("insert into st_role values(?,?,?,?,?,?,?)");
		pstmt.setInt(1, pk);
		pstmt.setString(2, bean.getName());
		pstmt.setString(3, bean.getDescription());
		pstmt.setString(4, bean.getCreatedBy());
		pstmt.setString(5, bean.getModifiedBy());
		pstmt.setTimestamp(6, bean.getCreatedDatetime());
		pstmt.setTimestamp(7, bean.getModifiedDatetime());
		pstmt.executeUpdate();
		conn.commit();
		JDBCDataSource.closeConnection(conn);
		return pk;
	
		
	}
	public void update (RoleBean bean) throws Exception{
		Connection conn=null;
		
		conn=JDBCDataSource.getConnection();
	    conn.setAutoCommit(false);
	    PreparedStatement pstmt=conn.prepareStatement("update st_role set name=?,description=?, created_by=?, modified_by=?,created_datetime = ?, modified_datetime = ? where id  = ?");
	    pstmt.setString(1, bean.getName());
	    pstmt.setString(2, bean.getDescription());
	    pstmt.setString(3, bean.getCreatedBy());
	    pstmt.setString(4, bean.getModifiedBy());
	    pstmt.setTimestamp(5, bean.getCreatedDatetime());
	    pstmt.setTimestamp(6, bean.getModifiedDatetime());
	    pstmt.setLong(7, bean.getId());
	    pstmt.executeUpdate();
	    conn.commit();
	    pstmt.close();
	    JDBCDataSource.closeConnection(conn);
	}
	public void delete(RoleBean bean)throws Exception{
		Connection conn =null;
		conn=JDBCDataSource.getConnection();
		conn.setAutoCommit(false);
		PreparedStatement pstmt=conn.prepareStatement("delete from st_role where id=? ");
		pstmt.setLong(1, bean.getId());
		pstmt.executeUpdate();
		 conn.commit();
		    pstmt.close();
		    JDBCDataSource.closeConnection(conn);
	}
	public RoleBean findByPk(long pk)throws Exception{
		RoleBean bean=null;
		Connection conn =null;
		
		StringBuffer  sql=new StringBuffer("select * from st_role where id=?");
		conn=JDBCDataSource.getConnection();
		PreparedStatement pstmt=conn.prepareStatement(sql.toString());
		pstmt.setLong(1, pk);
		ResultSet rs=pstmt.executeQuery();
		while(rs.next()) {
			bean=new RoleBean();
			bean.setId(rs.getLong(1));
			bean.setName(rs.getString(2));
			bean.setDescription(rs.getString(3));
			bean.setCreatedBy(rs.getString(4));
			bean.setModifiedBy(rs.getString(5));
			bean.setCreatedDatetime(rs.getTimestamp(6));
			bean.setModifiedDatetime(rs.getTimestamp(7));
		}
		rs.close();
		pstmt.close();
		JDBCDataSource.closeConnection(conn);
		return bean;
		
	}
	



}
