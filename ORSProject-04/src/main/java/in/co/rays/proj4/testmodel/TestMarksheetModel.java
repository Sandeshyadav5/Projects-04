package in.co.rays.proj4.testmodel;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import in.co.rays.proj4.bean.MarksheetBean;
import in.co.rays.proj4.exception.ApplicationException;
import in.co.rays.proj4.exception.DatabaseException;
import in.co.rays.proj4.model.MarksheetModel;
import in.co.rays.proj4.model.UserModel;

public class TestMarksheetModel {
	public static MarksheetModel model=new MarksheetModel();
	public static void main(String[] args) throws Exception {
//		testAdd();
//		testUpdate();
//		testDelete();
//	testFindByPk();
//		testNextPk();
		testSearch();
	}
	public static void testNextPk() throws DatabaseException {
		MarksheetModel r=new MarksheetModel();
		long i = r.nextpk();
		System.out.println(i);
	}
	public static void testAdd() throws ApplicationException {
		MarksheetBean bean=new MarksheetBean();
		
		bean.setRollNo("2");
		bean.setStudentId(1);
		bean.setName("sandesh");
		bean.setPhysics(34);
		bean.setChemistry(54);
		bean.setMaths(94);
		bean.setCreatedBy("admin");
		bean.setModifiedBy("admin");
		bean.setCreatedDatetime(new Timestamp(new Date().getTime()));
		bean.setModifiedDatetime(new Timestamp(new Date().getTime()));
		model.add(bean);
		System.out.println("Role added Successfully");
	}
	public static void testUpdate() throws ApplicationException {
		MarksheetBean bean=new MarksheetBean();
		bean.setId(5);
		bean.setRollNo("24534");
		bean.setStudentId(1);
		bean.setName("durgesh");
		bean.setPhysics(34);
		bean.setChemistry(54);
		bean.setMaths(94);
		bean.setCreatedBy("admin");
		bean.setModifiedBy("admin");
		bean.setCreatedDatetime(new Timestamp(new Date().getTime()));
		bean.setModifiedDatetime(new Timestamp(new Date().getTime()));
		model.update(bean);
		System.out.println("Role Updated Successfully");
	}
	public static void testDelete() throws ApplicationException {
		MarksheetBean bean=new MarksheetBean();
		
		bean.setId(6);
		
		model.delete(bean);
		System.out.println("Role delete Successfully");
	}
	public static void testFindByPk() throws ApplicationException {
		MarksheetBean bean=model.findByPk(3);
		
		System.out.println(bean.getRollNo());
		System.out.println(bean.getName());
		System.out.println(bean.getStudentId());
		System.out.println(bean.getPhysics());
		System.out.println(bean.getChemistry());
		System.out.println(bean.getMaths());
		
		
	}
	public static void testSearch() throws ApplicationException {
		MarksheetBean bean=new MarksheetBean();
		List list=new ArrayList();
		
		list=model.search(bean);
		Iterator it=list.iterator();
		while(it.hasNext()) {
			bean=(MarksheetBean)it.next();
		System.out.println(bean.getRollNo());
		System.out.println(bean.getName());
		System.out.println(bean.getStudentId());
		System.out.println(bean.getPhysics());
		System.out.println(bean.getChemistry());
		System.out.println(bean.getMaths());
		}
		
	}

}
