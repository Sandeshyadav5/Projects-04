package in.co.rays.proj4.controller;
import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import in.co.rays.proj4.bean.BaseBean;
import in.co.rays.proj4.bean.RoleBean;
import in.co.rays.proj4.model.RoleModel;
import in.co.rays.proj4.util.DataUtility;
import in.co.rays.proj4.util.PropertyReader;
import in.co.rays.proj4.util.ServletUtility;

/**
 * RoleListCtl is responsible for handling Role List operations such as:
 * searching, pagination, deletion, and navigation.
 * 
 * It interacts with RoleModel to fetch and manipulate Role data
 * and forwards results to the Role List view.
 * 
 * Supported operations:
 * - Search
 * - Next / Previous Pagination
 * - Delete
 * - Reset
 * - New / Back navigation
 * 
 * @author 
 */
@WebServlet(name = "RoleListCtl", urlPatterns = { "/ctl/RoleListCtl" })
public class RoleListCtl extends BaseCtl {
	private static final Logger log = Logger.getLogger(RoleListCtl.class);

	/**
	 * Preloads Role list for dropdown or reference use.
	 * 
	 * @param request HttpServletRequest object
	 */
	 @Override
	    protected void preload(HttpServletRequest request, HttpServletResponse response)
	    		throws IOException, ServletException {
		RoleModel roleModel = new RoleModel();

		try {
			List roleList = roleModel.list();
			request.setAttribute("roleList", roleList);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Populates RoleBean with request parameters.
	 * 
	 * @param request HttpServletRequest object
	 * @return populated BaseBean (RoleBean)
	 */
	@Override
	protected BaseBean populateBean(HttpServletRequest request) {

		RoleBean bean = new RoleBean();

		bean.setName(DataUtility.getString(request.getParameter("name")));
		bean.setId(DataUtility.getLong(request.getParameter("roleId")));

		return bean;
	}

	/**
	 * Handles GET request.
	 * Loads initial Role list with default pagination.
	 * 
	 * @param request  HttpServletRequest object
	 * @param response HttpServletResponse object
	 * @throws ServletException if servlet error occurs
	 * @throws IOException      if I/O error occurs
	 */
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		int pageNo = 1;
		int pageSize = DataUtility.getInt(PropertyReader.getValue("page.size"));

		RoleBean bean = (RoleBean) populateBean(request);
		RoleModel model = new RoleModel();

		try {
			List<RoleBean> list = model.search(bean, pageNo, pageSize);
			List<RoleBean> next = model.search(bean, pageNo + 1, pageSize);

			if (list == null || list.isEmpty()) {
				ServletUtility.setErrorMessage("No record found", request);
			}

			ServletUtility.setList(list, request);
			ServletUtility.setPageNo(pageNo, request);
			ServletUtility.setPageSize(pageSize, request);
			ServletUtility.setBean(bean, request);
			request.setAttribute("nextListSize", next.size());

			ServletUtility.forward(getView(), request, response);

		} catch (Exception e) {
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

		RoleBean bean = (RoleBean) populateBean(request);
		RoleModel model = new RoleModel();

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
			 * Redirect to New Role form
			 */
			} else if (OP_NEW.equalsIgnoreCase(op)) {
				ServletUtility.redirect(ORSView.ROLE_CTL, request, response);
				return;

			/**
			 * Delete selected records
			 */
			} else if (OP_DELETE.equalsIgnoreCase(op)) {
				pageNo = 1;

				if (ids != null && ids.length > 0) {
					RoleBean deletebean = new RoleBean();

					for (String id : ids) {
						deletebean.setId(DataUtility.getInt(id));
						model.delete(deletebean);
					}
					ServletUtility.setSuccessMessage("Data is deleted successfully", request);

				} else {
					ServletUtility.setErrorMessage("Select at least one record", request);
				}

			/**
			 * Reset operation
			 */
			} else if (OP_RESET.equalsIgnoreCase(op)) {
				ServletUtility.redirect(ORSView.USER_LIST_CTL, request, response);
				return;

			/**
			 * Back operation
			 */
			} else if (OP_BACK.equalsIgnoreCase(op)) {
				ServletUtility.redirect(ORSView.USER_LIST_CTL, request, response);
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

		} catch (Exception e) {
			e.printStackTrace();
			ServletUtility.handleException(e, request, response);
			return;
		}
	}

	/**
	 * Returns Role List view page.
	 * 
	 * @return view path
	 */
	@Override
	protected String getView() {
		return ORSView.ROLE_LIST_VIEW;
	}
}