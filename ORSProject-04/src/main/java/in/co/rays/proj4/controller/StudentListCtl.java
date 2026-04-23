package in.co.rays.proj4.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import in.co.rays.proj4.bean.BaseBean;
import in.co.rays.proj4.bean.StudentBean;
import in.co.rays.proj4.exception.ApplicationException;
import in.co.rays.proj4.model.StudentModel;
import in.co.rays.proj4.util.DataUtility;
import in.co.rays.proj4.util.PropertyReader;
import in.co.rays.proj4.util.ServletUtility;

/**
 * StudentListCtl is responsible for handling Student List operations such as:
 * searching, pagination, deletion, and navigation.
 * 
 * It interacts with StudentModel to fetch and manage student data
 * and forwards results to the Student List view.
 * 
 * Supported operations:
 * - Search
 * - Next / Previous Pagination
 * - Delete
 * - Reset
 * - New / Back navigation
 * 
 * It also supports filtering based on first name, last name, and email.
 * 
 * @author 
 */
@WebServlet(name = "StudentListCtl", urlPatterns = { "/ctl/StudentListCtl" })
public class StudentListCtl extends BaseCtl {
	private static final Logger log = Logger.getLogger(StudentListCtl.class);

	/**
	 * Populates StudentBean with request parameters for search criteria.
	 * 
	 * @param request HttpServletRequest object
	 * @return populated BaseBean (StudentBean)
	 */
	@Override
	protected BaseBean populateBean(HttpServletRequest request) {

		StudentBean bean = new StudentBean();

		bean.setFirstName(DataUtility.getString(request.getParameter("firstName")));
		bean.setLastName(DataUtility.getString(request.getParameter("lastName")));
		bean.setEmail(DataUtility.getString(request.getParameter("email")));

		return bean;
	}

	/**
	 * Handles GET request.
	 * Loads initial Student list with default pagination.
	 * 
	 * @param request  HttpServletRequest object
	 * @param response HttpServletResponse object
	 * @throws ServletException if servlet error occurs
	 * @throws IOException      if I/O error occurs
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		int pageNo = 1;
		int pageSize = DataUtility.getInt(PropertyReader.getValue("page.size"));

		StudentBean bean = (StudentBean) populateBean(request);
		StudentModel model = new StudentModel();

		try {
			List<StudentBean> list = model.search(bean, pageNo, pageSize);
			List<StudentBean> next = model.search(bean, pageNo + 1, pageSize);

			if (list == null || list.isEmpty()) {
				ServletUtility.setErrorMessage("No record found", request);
			}

			ServletUtility.setList(list, request);
			ServletUtility.setPageNo(pageNo, request);
			ServletUtility.setPageSize(pageSize, request);
			ServletUtility.setBean(bean, request);
			request.setAttribute("nextListSize", next.size());

			ServletUtility.forward(getView(), request, response);

		} catch (ApplicationException e) {
			e.printStackTrace();
			ServletUtility.handleException(e, request, response);
			return;
		}
	}

	/**
	 * Handles POST request.
	 * Performs operations like search, pagination, delete,
	 * reset, and navigation.
	 * 
	 * @param request  HttpServletRequest object
	 * @param response HttpServletResponse object
	 * @throws ServletException if servlet error occurs
	 * @throws IOException      if I/O error occurs
	 */
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		List list = null;
		List next = null;

		int pageNo = DataUtility.getInt(request.getParameter("pageNo"));
		int pageSize = DataUtility.getInt(request.getParameter("pageSize"));

		// Default pagination handling
		pageNo = (pageNo == 0) ? 1 : pageNo;
		pageSize = (pageSize == 0)
				? DataUtility.getInt(PropertyReader.getValue("page.size"))
				: pageSize;

		StudentBean bean = (StudentBean) populateBean(request);
		StudentModel model = new StudentModel();

		String op = DataUtility.getString(request.getParameter("operation"));
		String[] ids = request.getParameterValues("ids");

		try {

			/**
			 * Handle Search and Pagination
			 */
			if (OP_SEARCH.equalsIgnoreCase(op)
					|| "Next".equalsIgnoreCase(op)
					|| "Previous".equalsIgnoreCase(op)) {

				if (OP_SEARCH.equalsIgnoreCase(op)) {
					pageNo = 1;
				} else if (OP_NEXT.equalsIgnoreCase(op)) {
					pageNo++;
				} else if (OP_PREVIOUS.equalsIgnoreCase(op) && pageNo > 1) {
					pageNo--;
				}

			/**
			 * Redirect to New Student form
			 */
			} else if (OP_NEW.equalsIgnoreCase(op)) {
				ServletUtility.redirect(ORSView.STUDENT_CTL, request, response);
				return;

			/**
			 * Delete selected students
			 */
			} else if (OP_DELETE.equalsIgnoreCase(op)) {
				pageNo = 1;

				if (ids != null && ids.length > 0) {
					StudentBean deletebean = new StudentBean();

					for (String id : ids) {
						deletebean.setId(DataUtility.getInt(id));
						model.delete(deletebean);
					}
					ServletUtility.setSuccessMessage("Student is deleted successfully", request);

				} else {
					ServletUtility.setErrorMessage("Select at least one record", request);
				}

			/**
			 * Reset operation
			 */
			} else if (OP_RESET.equalsIgnoreCase(op)) {
				ServletUtility.redirect(ORSView.STUDENT_LIST_CTL, request, response);
				return;

			/**
			 * Back operation
			 */
			} else if (OP_BACK.equalsIgnoreCase(op)) {
				ServletUtility.redirect(ORSView.STUDENT_LIST_CTL, request, response);
				return;
			}

			// Fetch updated list
			list = model.search(bean, pageNo, pageSize);
			next = model.search(bean, pageNo + 1, pageSize);

			if (list == null || list.size() == 0) {
				ServletUtility.setErrorMessage("No record found ", request);
			}

			ServletUtility.setList(list, request);
			ServletUtility.setPageNo(pageNo, request);
			ServletUtility.setPageSize(pageSize, request);
			ServletUtility.setBean(bean, request);
			request.setAttribute("nextListSize", next.size());

			ServletUtility.forward(getView(), request, response);

		} catch (ApplicationException e) {
			e.printStackTrace();
			ServletUtility.handleException(e, request, response);
			return;
		}
	}

	/**
	 * Returns Student List view page.
	 * 
	 * @return view path
	 */
	@Override
	protected String getView() {
		return ORSView.STUDENT_LIST_VIEW;
	}
}