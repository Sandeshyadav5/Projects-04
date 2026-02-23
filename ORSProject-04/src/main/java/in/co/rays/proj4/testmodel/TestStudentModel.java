package in.co.rays.proj4.testmodel;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import in.co.rays.proj4.bean.StudentBean;
import in.co.rays.proj4.exception.ApplicationException;
import in.co.rays.proj4.model.StudentModel;

public class TestStudentModel {
	public static StudentModel model =new StudentModel();
	public static void main(String[] args) throws Exception {
//		testNextPk();
//		testAdd();
//		testUpdate();
//		testDelete();
//		testFindByPk();
		testSearch();
	}
	public static void testNextPk() throws Exception {
		StudentModel r=new StudentModel();
		int i=r.nextPk();
		System.out.println("pk="+i);
	}
	public static void testAdd() throws Exception {
		StudentBean bean =new StudentBean();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

		
		bean.setFirstName("sandesh");
		bean.setLastName("ahir");
		bean.setDob(sdf.parse("2001-05-09"));
		bean.setGender("male");
		bean.setMobileNo("9328374238");
	    bean.setEmail("bfdj@gmail.com");
		bean.setCollegeId(2);
		bean.setCollegeName("IPS");
		bean.setCreatedBy("admin");
		bean.setModifiedBy("admin");
		bean.setCreatedDatetime(new Timestamp(new Date().getTime()));
		bean.setModifiedDatetime(new Timestamp(new Date().getTime()));	
		model.add(bean);
		System.out.println("student added successfully");
	}
	public static void testUpdate() throws Exception {
		StudentBean bean =new StudentBean();
       
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

		bean.setId(1);
		bean.setFirstName("durgesh");
		bean.setLastName("ahir");
		bean.setDob(sdf.parse("2001-05-09"));
		bean.setGender("male");
		bean.setMobileNo("9328374238");
	    bean.setEmail("sandy@gmail.com");
		bean.setCollegeId(1);
		bean.setCollegeName("IPS");
		bean.setCreatedBy("admin");
		bean.setModifiedBy("admin");
		bean.setCreatedDatetime(new Timestamp(new Date().getTime()));
		bean.setModifiedDatetime(new Timestamp(new Date().getTime()));
		
		model.update(bean);
		System.out.println("student Updated successfully");
	}
	public static void testDelete() throws ApplicationException {
		StudentBean bean =new StudentBean();
		
		bean.setId(2);
		model.delete(bean);
		System.out.println("college deleted successfully");
	}
	public static void testFindByPk() throws Exception {
		StudentBean bean=model.findByPk(1);
		System.out.println(bean.getFirstName());
		System.out.println(bean.getLastName());
		System.out.println(bean.getDob());
		System.out.println(bean.getGender());
		System.out.println(bean.getMobileNo());
		System.out.println(bean.getEmail());
		System.out.println(bean.getCollegeId());
		System.out.println(bean.getCollegeName());
		
	}
	public static void testSearch() throws ApplicationException {
		StudentBean bean=new StudentBean();
		List  list=new ArrayList();
		list=model.search(bean);
		Iterator it=list.iterator();
		while(it.hasNext()) {
			bean=(StudentBean)it.next();
			System.out.println(bean.getFirstName());
			System.out.println(bean.getLastName());
			System.out.println(bean.getDob());
			System.out.println(bean.getGender());
			System.out.println(bean.getMobileNo());
			System.out.println(bean.getEmail());
			System.out.println(bean.getCollegeId());
			System.out.println(bean.getCollegeName());
		}
	}
}
