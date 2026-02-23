package in.co.rays.proj4.testmodel;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import in.co.rays.proj4.bean.SubjectBean;
import in.co.rays.proj4.exception.ApplicationException;
import in.co.rays.proj4.model.SubjectModel;

public class TestSubjectModel {
	public static SubjectModel model=new SubjectModel();
	public static void main(String[] args) throws ApplicationException {
//		testAdd();
//		testUpdate();
//		testDelete();
//		testFindByPk();
		testSearch();
		
	}
	public static void testAdd() throws ApplicationException {
		SubjectBean bean= new SubjectBean();
		bean.setName("Physics");
		bean.setCourseId(1);
		bean.setCourseName("BCA");
		bean.setDescription("student");
		bean.setCreatedBy("admin");
		bean.setModifiedBy("admin");
		bean.setCreatedDatetime(new Timestamp(new Date().getTime()));
		bean.setModifiedDatetime(new Timestamp(new Date().getTime()));
		model.add(bean);
		System.out.println("Role added Successfully");
	
	}
	public static void testUpdate() throws ApplicationException {
		SubjectBean bean= new SubjectBean();
		bean.setId(2);
		bean.setName("chemistry");
		bean.setCourseId(1);
		bean.setCourseName("MCA");
		bean.setDescription("Student");
		bean.setCreatedBy("admin");
		bean.setModifiedBy("admin");
		bean.setCreatedDatetime(new Timestamp(new Date().getTime()));
		bean.setModifiedDatetime(new Timestamp(new Date().getTime()));
		
		model.update(bean);
		System.out.println("Role updated Successfully");
	
	}
	public static void testDelete() throws ApplicationException {
		SubjectBean bean= new SubjectBean();
		bean.setId(3);
		
		
		model.delete(bean);
		System.out.println("Role deleted Successfully");
	
	}
	public static void testFindByPk() throws ApplicationException {
		SubjectBean bean=model.findByPk(1);
		System.out.println(bean.getId());
		System.out.println(bean.getName());
		System.out.println(bean.getCourseId());
		System.out.println(bean.getCourseName());
		System.out.println(bean.getDescription());
	
	}
	public static void testSearch() throws ApplicationException {
		SubjectBean bean= new SubjectBean();
		List list =new ArrayList();
		list=model.search(bean);
		Iterator it=list.iterator();
		while(it.hasNext()) {
			bean=(SubjectBean)it.next();
		System.out.println(bean.getId());
		System.out.println(bean.getName());
		System.out.println(bean.getCourseId());
		System.out.println(bean.getCourseName());
		System.out.println(bean.getDescription());
		}
	}
}
