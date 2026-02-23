package in.co.rays.proj4.testmodel;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import in.co.rays.proj4.bean.CollegeBean;
import in.co.rays.proj4.exception.ApplicationException;
import in.co.rays.proj4.model.CollegeModel;

public class TestCollegeModel {
	public static CollegeModel model = new CollegeModel();
	public static void main(String[] args) throws Exception {
//	    testAdd();
//    	testUpdate();
//		testDelete();
//testFindBypk();
		testSearch();
		
	}
	public static void testAdd() throws ApplicationException {
		CollegeBean bean =new CollegeBean();
		
		bean.setId(1);
		bean.setName("Medicaps University");
		bean.setAddress("Indore");
		bean.setState("madhya pradesh");
		bean.setCity("Indore");
		bean.setPhoneNo("6364823462");
		bean.setCreatedBy("admin");
		bean.setModifiedBy("admin");
		bean.setCreatedDatetime(new Timestamp(new Date().getTime()));
		bean.setModifiedDatetime(new Timestamp(new Date().getTime()));
		model.add(bean);
		System.out.println("College added successfully");
	}
	public static void  testUpdate() throws Exception {
       CollegeBean bean =new CollegeBean();
		bean.setId(3);
		bean.setName("IPS");
		bean.setAddress("Rau");
		bean.setState("madhya pradesh");
		bean.setCity("Indore");
		bean.setPhoneNo("7345983455");
		bean.setCreatedBy("admin");
		bean.setModifiedBy("admin");
		bean.setCreatedDatetime(new Timestamp(new Date().getTime()));
		bean.setModifiedDatetime(new Timestamp(new Date().getTime()));
	
		model.update(bean);
		System.out.println("College updated successfully");
	}
	public static void testDelete() throws Exception {
		CollegeBean bean =new CollegeBean();
		bean.setId(1);
		model.delete(bean);
		System.out.println("college deleted successfully");
	}
	public static void testFindBypk() throws ApplicationException {
		CollegeBean  bean=model.findByPk(2);
		System.out.println(bean.getName());
		System.out.println(bean.getAddress());
		System.out.println(bean.getState());
		System.out.println(bean.getCity());
		System.out.println(bean.getPhoneNo());
		
	}
	public static void testSearch() throws ApplicationException {
		CollegeBean bean=new CollegeBean();
		List list=new ArrayList();
		bean.setName("Medicaps University");
		list=model.search(bean);
		Iterator it=list.iterator();
		while(it.hasNext()) {
			bean=(CollegeBean)it.next();
			System.out.println(bean.getName());
			System.out.println(bean.getAddress());
			System.out.println(bean.getState());
			System.out.println(bean.getCity());
			System.out.println(bean.getPhoneNo());
		}
		
	}
	}

