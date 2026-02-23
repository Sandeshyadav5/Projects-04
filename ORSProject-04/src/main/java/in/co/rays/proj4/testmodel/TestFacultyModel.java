package in.co.rays.proj4.testmodel;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import in.co.rays.proj4.bean.FacultyBean;
import in.co.rays.proj4.exception.ApplicationException;
import in.co.rays.proj4.model.FacultyModel;

public class TestFacultyModel {
	public static  FacultyModel model=new FacultyModel();
	public static void main(String[] args) throws Exception {
//		testAdd();
//		testUpdate();
		testDelete();
//		testFindByPk();
//		testSearch();
	}
	public static void testAdd() throws Exception {
		FacultyBean bean =new FacultyBean();

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		
		bean.setFirstname("sandesh");
		bean.setLastName("yafaddfd");
		bean.setDob(sdf.parse("2001-09-05"));
		bean.setGender("Male");
		bean.setMobileNo("7435783444");
		bean.setEmail("sandy@gmail.com");
		bean.setCollegeId(1);
		bean.setCollegeName("IPS");
		bean.setCourseId(2);
		bean.setCoursename("MCA");
		bean.setSubjectId(4);
		bean.setSubjectName("Chemistry");
		bean.setCreatedBy("admin");
		bean.setModifiedBy("admin");
		bean.setCreatedDatetime(new Timestamp(new Date().getTime()));
		bean.setModifiedDatetime(new Timestamp(new Date().getTime()));
		model.add(bean);
		System.out.println("Faculty added successfully");
	}
	public static void testUpdate() throws Exception {
		FacultyBean bean =new FacultyBean();
         
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		bean.setId(1);
		bean.setFirstname("durgesh");
		bean.setLastName("yafarrggereddfd");
		bean.setDob(sdf.parse("2002-09-05"));
		bean.setGender("feMale");
		bean.setMobileNo("9835783444");
		bean.setEmail("durg5@gmail.com");
		bean.setCollegeId(1);
		bean.setCollegeName("Medicaaps");
		bean.setCourseId(1);
		bean.setCoursename("BCA");
		bean.setSubjectId(1);
		bean.setSubjectName("Physics");
		bean.setCreatedBy("admin");
		bean.setModifiedBy("admin");
		bean.setCreatedDatetime(new Timestamp(new Date().getTime()));
		bean.setModifiedDatetime(new Timestamp(new Date().getTime()));
		model.update(bean);
		System.out.println("Faculty updated successfully");
	}
	public static void testDelete() throws Exception {
		FacultyBean bean =new FacultyBean();
		
		bean.setId(2);
		model.delete(bean);
		System.out.println("Faculty deleted successfully");
	}
	public static void testFindByPk() throws Exception {
		FacultyBean bean =model.findByPk(1);
		System.out.println(bean.getId());
	   System.out.println(bean.getFirstname());
	   System.out.println(bean.getLastName());
	   System.out.println(bean.getDob());
	   System.out.println(bean.getGender());
	   System.out.println(bean.getMobileNo());
	   System.out.println(bean.getEmail());
	   System.out.println(bean.getCollegeId());
	   System.out.println(bean.getCollegeName());
	   System.out.println(bean.getCourseId());
	   System.out.println(bean.getCoursename());
	   System.out.println(bean.getSubjectId());
	   System.out.println(bean.getSubjectName());
	}
	public static void testSearch() throws Exception {
		FacultyBean bean =new FacultyBean();
		List list=new ArrayList();
		list=model.search(bean);
		Iterator it=list.iterator();
		while(it.hasNext()) {
	        bean=(FacultyBean)it.next();
		System.out.println(bean.getId());
	   System.out.println(bean.getFirstname());
	   System.out.println(bean.getLastName());
	   System.out.println(bean.getDob());
	   System.out.println(bean.getGender());
	   System.out.println(bean.getMobileNo());
	   System.out.println(bean.getEmail());
	   System.out.println(bean.getCollegeId());
	   System.out.println(bean.getCollegeName());
	   System.out.println(bean.getCourseId());
	   System.out.println(bean.getCoursename());
	   System.out.println(bean.getSubjectId());
	   System.out.println(bean.getSubjectName());
	}
	}
}
