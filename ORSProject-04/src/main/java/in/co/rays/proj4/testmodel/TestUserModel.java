package in.co.rays.proj4.testmodel;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import in.co.rays.proj4.bean.UserBean;
import in.co.rays.proj4.exception.ApplicationException;
import in.co.rays.proj4.model.UserModel;

public class TestUserModel {
	public static UserModel model=new UserModel();
	public static void main(String[] args) throws Exception {
//		testNextPk();
//		testAdd();
//		testDelete();
//		testUpdate();
//		testfindByPk();
		testSearch(); 
	}
	public static void testNextPk() throws Exception {
		UserModel r=new UserModel();
		long i=r.nextPk();
		System.out.println(i);
	}
	public static void testAdd() throws Exception  {

		UserBean bean = new UserBean();

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		bean.setFirstName("yadav");
		bean.setLastName("yadav");
		bean.setLogin("sansesh@gmail.com");
		bean.setPassword("12345");
		bean.setDob(sdf.parse("2002-06-01"));
		bean.setMobileNo("9876543223");
		bean.setRoleId(1);
		bean.setGender("male");
		bean.setCreatedBy("admin");
		bean.setModifiedBy("admin");
		bean.setCreatedDatetime(new Timestamp(new Date().getTime()));
		bean.setModifiedDatetime(new Timestamp(new Date().getTime()));
		model.add(bean);
		System.out.println("user added sucessfully");
		}
	private static void testUpdate() throws Exception {
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
		UserBean bean = new UserBean();
		bean.setId(3);
		bean.setFirstName("durgesh");
		bean.setLastName("lohiya");
		bean.setLogin("durg@gmail.com");
		bean.setPassword("durg123");
		bean.setDob(sdf.parse("2001-09-05"));
		bean.setMobileNo("8966909877");
		bean.setGender("Male");
		bean.setCreatedBy("admin");
		bean.setModifiedBy("admin");
		bean.setCreatedDatetime(new Timestamp(new Date().getTime()));
		bean.setModifiedDatetime(new Timestamp(new Date().getTime()));
		model.update(bean);
		System.out.println("Role Updated Successfully");
	}
	private static void testDelete()throws Exception{
		UserBean bean = new UserBean();
		
		bean.setId(3);
		model.delete(bean);
		System.out.println("1 Role Deleted Successfully");
	}
	private static void testfindByPk() throws Exception{
		UserBean bean=model.findByPk(1L);
		
		System.out.println(bean.getFirstName());
		System.out.println(bean.getLastName());
		System.out.println(bean.getLogin());
		System.out.println(bean.getPassword());
		System.out.println(bean.getMobileNo());
		System.out.println(bean.getGender());
	}
	private static void testSearch()throws Exception {
		UserBean bean=new UserBean();
		List list=new ArrayList();
		bean.setFirstName("student");
		list=model.search(bean);
		Iterator it=list.iterator();
		while(it.hasNext()) {
			bean=(UserBean)it.next();
			System.out.println(bean.getId());
			System.out.println(bean.getFirstName());
			System.out.println(bean.getLastName());
			System.out.println(bean.getLogin());
			System.out.println(bean.getPassword());
			System.out.println(bean.getMobileNo());
			System.out.println(bean.getGender());
		}

}
	
	
}

