package in.co.rays.proj4.testmodel;

import java.sql.Timestamp;
import java.util.Date;

import in.co.rays.proj4.bean.RoleBean;
import in.co.rays.proj4.model.RoleModel;

public class TestRoleModel {
	public static RoleModel model= new RoleModel();
	public static void main(String[] args) throws Exception {
//		testNextPk();
    //    testAdd();
//	testUpdate();
//		testDelete();
	 	testfindByPk();
	}
	private static void testNextPk() throws Exception{
		RoleModel r=new RoleModel();
		int i=r.nextPk();
		System.out.println(i);
	}
	private static void testAdd() throws Exception{
		RoleBean bean=new RoleBean();
		
		bean.setName("sandesh");
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
		bean.setName("durgesh");
		bean.setDescription("ahir");
		model.update(bean);
		System.out.println("Role Updated Successfully");
	}
	private static void testDelete()throws Exception{
		RoleBean bean = new RoleBean();
		long pk = 1L;
		bean.setId(pk);
		model.update(bean);
		System.out.println("1 Role Deleted Successfully");
	}
	private static void testfindByPk() throws Exception{
		RoleBean bean=model.findByPk(1L);
		
		System.out.println(bean.getId());
		System.out.println(bean.getName());
		System.out.println(bean.getDescription());
	}


}
