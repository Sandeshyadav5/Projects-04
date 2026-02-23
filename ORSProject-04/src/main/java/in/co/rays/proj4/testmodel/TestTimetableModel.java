package in.co.rays.proj4.testmodel;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import in.co.rays.proj4.bean.TimeTableBean;
import in.co.rays.proj4.model.TimetableModel;

public class TestTimetableModel {
	public static TimetableModel model=new TimetableModel();
	public static void main(String[] args) throws Exception {
//		testAdd();
//		testUpdate();
		testDelete();
//		testFindByPk();
//		testSearch();
		
	}
	public static void testAdd() throws  Exception {
		TimeTableBean bean=new TimeTableBean();

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		bean.setId(1);
		bean.setSemester("1st");
		bean.setDescription("hr");
		bean.setExamDate(sdf.parse("2001-06-09"));
		bean.setExamTime("21231");
		bean.setCourseId(4);
		bean.setCourseName("Mca");
		bean.setSubjectId(5);
		bean.setSubjectName("chemistry");
		bean.setCreatedBy("admin");
		bean.setModifiedBy("admin");
		bean.setCreatedDatetime(new Timestamp(new Date().getTime()));
		bean.setModifiedDatetime(new Timestamp(new Date().getTime()));
		model.add(bean);
		System.out.println("Role added Successfully");
	}
	public static void testUpdate() throws  Exception {
		TimeTableBean bean=new TimeTableBean();

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		bean.setId(2);
		bean.setSemester("2nd");
		bean.setDescription("hr");
		bean.setExamDate(sdf.parse("2001-06-09"));
		bean.setExamTime("21231");
		bean.setCourseId(4);
		bean.setCourseName("Mca");
		bean.setSubjectId(5);
		bean.setSubjectName("chemistry");
		bean.setCreatedBy("admin");
		bean.setModifiedBy("admin");
		bean.setCreatedDatetime(new Timestamp(new Date().getTime()));
		bean.setModifiedDatetime(new Timestamp(new Date().getTime()));
		
		model.update(bean);
		System.out.println("Role updated Successfully");
	}
	public static void testDelete() throws  Exception {
		TimeTableBean bean=new TimeTableBean();

		
		bean.setId(2);
		
		
		model.delete(bean);
		System.out.println("Role deleted Successfully");
	}
	public static void testFindByPk() throws  Exception {
		TimeTableBean bean=model.findByPk(1);
		System.out.println(bean.getSemester());
		System.out.println(bean.getExamTime());
		System.out.println(bean.getExamTime());
		System.out.println(bean.getCourseId());
		System.out.println(bean.getCourseName());
		System.out.println(bean.getDescription());
		System.out.println(bean.getSubjectId());
		System.out.println(bean.getSubjectName());

		
		
	}
	public static void testSearch() throws  Exception {
		TimeTableBean bean=new TimeTableBean();
		List list =new ArrayList();
		list=model.search(bean);
		Iterator it =list.iterator();
		while(it.hasNext()) {
			bean=(TimeTableBean)it.next();
		System.out.println(bean.getSemester());
		System.out.println(bean.getExamTime());
		System.out.println(bean.getExamTime());
		System.out.println(bean.getCourseId());
		System.out.println(bean.getCourseName());
		System.out.println(bean.getDescription());
		System.out.println(bean.getSubjectId());
		System.out.println(bean.getSubjectName());
		}
		
		
	}
	


}
