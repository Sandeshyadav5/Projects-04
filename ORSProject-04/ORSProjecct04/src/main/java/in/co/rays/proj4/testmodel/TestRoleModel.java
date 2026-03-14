package in.co.rays.proj4.testmodel;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;



import in.co.rays.proj4.bean.RoleBean;
import in.co.rays.proj4.model.RoleModel;

public class TestRoleModel {
	public static RoleModel model= new RoleModel();
	public static void main(String[] args) throws Exception {
//		testNextPk();
//        testAdd();
	testUpdate();
//		testDelete();
// 	testfindByPk();
//		testSearch();
//		testfindByName();
	}
	private static void testNextPk() throws Exception{
		RoleModel r=new RoleModel();
		int i=r.nextPk();
		System.out.println(i);
	}
	private static void testAdd() throws Exception{
		RoleBean bean=new RoleBean();
		
		bean.setName("durgesh");
		bean.setDescription("HR");
		bean.setCreatedBy("admin");
		bean.setModifiedBy("admin");
		bean.setCreatedDatetime(new Timestamp(new Date().getTime()));
		bean.setModifiedDatetime(new Timestamp(new Date().getTime()));
		long pk=model.add(bean);
		System.out.println("Role Added Successfully");
		
		
	}
	private static void testUpdate() throws Exception {
		RoleBean bean = new RoleBean();
		bean.setId(1);
		bean.setName("sandesh");
		bean.setDescription("ahir");
		bean.setCreatedBy("admin");
		bean.setModifiedBy("admin");
		bean.setCreatedDatetime(new Timestamp(new Date().getTime()));
		bean.setModifiedDatetime(new Timestamp(new Date().getTime()));
		model.update(bean);
		System.out.println("Role Updated Successfully");
	}
	private static void testDelete()throws Exception{
		RoleBean bean = new RoleBean();
		
		bean.setId(4);
		model.delete(bean);
		System.out.println(" Role Deleted Successfully");
	}
	private static void testfindByPk() throws Exception{
		RoleBean bean=model.findByPk(1L);
		
		System.out.println(bean.getId());
		System.out.println(bean.getName());
		System.out.println(bean.getDescription());
	}
	private static void testfindByName() throws Exception{
		RoleBean bean=model.findByName("");
		
		System.out.println(bean.getId());
		System.out.println(bean.getName());
		System.out.println(bean.getDescription());
	}
	private static void testSearch()throws Exception {
		RoleBean bean=new RoleBean();
		List list=new ArrayList();
		bean.setName("student");
		list=model.search(bean, 0, 0);
		Iterator it=list.iterator();
		while(it.hasNext()) {
			bean=(RoleBean)it.next();
			System.out.println(bean.getId());
			System.out.println(bean.getName());
			System.out.println(bean.getDescription());
		}
	}


}
