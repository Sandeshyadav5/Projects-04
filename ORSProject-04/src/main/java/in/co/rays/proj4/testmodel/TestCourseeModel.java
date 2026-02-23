package in.co.rays.proj4.testmodel;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import in.co.rays.proj4.bean.CourseBean;
import in.co.rays.proj4.exception.ApplicationException;
import in.co.rays.proj4.model.CourseModel;

public class TestCourseeModel {
	public static CourseModel model=new CourseModel();
	public static void main(String[] args) throws ApplicationException {
//		testAdd();
//		testUpdate();
//		testDelete();
//		testFindByPk();
		testSearch();
	}
	public static void testAdd() throws ApplicationException {
		CourseBean bean=new CourseBean();
		
		bean.setName("MCA");
		bean.setDuration("2 Year");
		bean.setDescription("student");
		bean.setCreatedBy("admin");
		bean.setModifiedBy("admin");
		bean.setCreatedDatetime(new Timestamp(new Date().getTime()));
		bean.setModifiedDatetime(new Timestamp(new Date().getTime()));
		model.add(bean);
		System.out.println("Role added Successfully");
		
	}
	public static void testUpdate() throws ApplicationException {
		CourseBean bean=new CourseBean();
		bean.setId(2);
		bean.setName("BCA");
		bean.setDuration("3 Year");
		bean.setDescription("student");
		bean.setCreatedBy("admin");
		bean.setModifiedBy("admin");
		bean.setCreatedDatetime(new Timestamp(new Date().getTime()));
		bean.setModifiedDatetime(new Timestamp(new Date().getTime()));
		model.update(bean);
		System.out.println("Role updated Successfully");
		
	}
	public static void testDelete() throws ApplicationException {
		CourseBean bean=new CourseBean();
		
		bean.setId(2);
		
		
		model.delete(bean);
		System.out.println("Role deleted Successfully");
		
	}
	public static void testFindByPk() throws ApplicationException {
	     CourseBean bean=model.findByPk(1);
	     System.out.println(bean.getId());
	     System.out.println(bean.getName());
	     System.out.println(bean.getDuration());
	     System.out.println(bean.getDescription());
	}
	public static void testSearch() throws ApplicationException {
		CourseBean bean=new CourseBean();
	   List  list=new ArrayList();
	  
	   list=model.search(bean);
	   Iterator it=list.iterator();
	   while(it.hasNext()) {
		   bean=(CourseBean)it.next();
	     System.out.println(bean.getId());
	     System.out.println(bean.getName());
	     System.out.println(bean.getDuration());
	     System.out.println(bean.getDescription());
	}}
}
