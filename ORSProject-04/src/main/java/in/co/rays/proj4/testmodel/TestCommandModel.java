package in.co.rays.proj4.testmodel;

import java.text.SimpleDateFormat;
import java.util.Iterator;
import java.util.List;

import in.co.rays.proj4.bean.CommandBean;
import in.co.rays.proj4.model.CommandModel;

public class TestCommandModel {

	public static void main(String[] args) throws Exception {

		 add();
		// update();
		// delete();
		// findByPk();
		// findByTest();
		// search();
//		list();

	}

	// ==================== ADD ====================

	public static void add() throws Exception {

		CommandModel model = new CommandModel();

		CommandBean bean = new CommandBean();

		bean.setCommandTest("CMD-101");
		bean.setDeviceTarget("Android");
		bean.setExecutionTime(
				new SimpleDateFormat("yyyy-MM-dd")
						.parse("2026-05-08"));
		bean.setStatus("Active");

		bean.setCreatedBy("root");
		bean.setModifiedBy("root");
		bean.setCreatedDatetime(new java.sql.Timestamp(
				System.currentTimeMillis()));
		bean.setModifiedDatetime(new java.sql.Timestamp(
				System.currentTimeMillis()));

		long pk = model.add(bean);

		System.out.println("Data Added Successfully : " + pk);
	}

	// ==================== UPDATE ====================

	public static void update() throws Exception {

		CommandModel model = new CommandModel();

		CommandBean bean = model.findByPk(1);

		bean.setCommandTest("CMD-UPDATED");
		bean.setDeviceTarget("Laptop");
		bean.setExecutionTime(
				new SimpleDateFormat("yyyy-MM-dd")
						.parse("2026-05-10"));
		bean.setStatus("Inactive");

		bean.setModifiedBy("root");
		bean.setModifiedDatetime(new java.sql.Timestamp(
				System.currentTimeMillis()));

		model.update(bean);

		System.out.println("Data Updated Successfully");
	}

	// ==================== DELETE ====================

	public static void delete() throws Exception {

		CommandModel model = new CommandModel();

		CommandBean bean = new CommandBean();

		bean.setId(1);

		model.delete(bean);

		System.out.println("Data Deleted Successfully");
	}

	// ==================== FIND BY PK ====================

	public static void findByPk() throws Exception {

		CommandModel model = new CommandModel();

		CommandBean bean = model.findByPk(1);

		if (bean != null) {

			System.out.println("ID : " + bean.getId());
			System.out.println("Command Test : "
					+ bean.getCommandTest());

			System.out.println("Device Target : "
					+ bean.getDeviceTarget());

			System.out.println("Execution Time : "
					+ bean.getExecutionTime());

			System.out.println("Status : "
					+ bean.getStatus());

		} else {

			System.out.println("Record not found");
		}
	}

	// ==================== FIND BY TEST ====================

	public static void findByTest() throws Exception {

		CommandModel model = new CommandModel();

		CommandBean bean =
				model.findByTest("CMD-101");

		if (bean != null) {

			System.out.println("ID : " + bean.getId());

			System.out.println("Command Test : "
					+ bean.getCommandTest());

			System.out.println("Device Target : "
					+ bean.getDeviceTarget());

			System.out.println("Execution Time : "
					+ bean.getExecutionTime());

			System.out.println("Status : "
					+ bean.getStatus());

		} else {

			System.out.println("Record not found");
		}
	}

	// ==================== SEARCH ====================

	public static void search() throws Exception {

		CommandModel model = new CommandModel();

		CommandBean bean = new CommandBean();

		bean.setCommandTest("CMD");

		List list = model.search(bean, 1, 10);

		Iterator it = list.iterator();

		while (it.hasNext()) {

			bean = (CommandBean) it.next();

			System.out.print(bean.getId());
			System.out.print("\t"
					+ bean.getCommandTest());

			System.out.print("\t"
					+ bean.getDeviceTarget());

			System.out.print("\t"
					+ bean.getExecutionTime());

			System.out.println("\t"
					+ bean.getStatus());
		}
	}

	// ==================== LIST ====================

	public static void list() throws Exception {

		CommandModel model = new CommandModel();

		List list = model.list(1, 10);

		Iterator it = list.iterator();

		while (it.hasNext()) {

			CommandBean bean =
					(CommandBean) it.next();

			System.out.print(bean.getId());

			System.out.print("\t"
					+ bean.getCommandTest());

			System.out.print("\t"
					+ bean.getDeviceTarget());

			System.out.print("\t"
					+ bean.getExecutionTime());

			System.out.println("\t"
					+ bean.getStatus());
		}
	}
}