package in.co.rays.proj4.controller;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import in.co.rays.proj4.bean.BaseBean;
import in.co.rays.proj4.bean.RoleBean;
import in.co.rays.proj4.exception.ApplicationException;
import in.co.rays.proj4.exception.DuplicateRecordException;
import in.co.rays.proj4.model.RoleModel;
import in.co.rays.proj4.util.DataUtility;
import in.co.rays.proj4.util.DataValidator;
import in.co.rays.proj4.util.PropertyReader;
import in.co.rays.proj4.util.ServletUtility;

/**
 * RoleCtl is a Controller class responsible for handling
 * Role operations such as Add, Update, Validate, and View.
 * 
 * It interacts with RoleModel for database operations
 * and forwards data to the Role view.
 * 
 * Supported operations:
 * - Save (Add)
 * - Update
 * - Cancel
 * - Reset
 * 
 * @author 
 */
@WebServlet(name = "RoleCtl", urlPatterns = { "/ctl/RoleCtl" })
public class RoleCtl extends BaseCtl {
	private static final Logger log = Logger.getLogger(RoleCtl.class);

	/**
	 * Validates user input for Role form.
	 * 
	 * @param request HttpServletRequest object
	 * @return true if validation passes, otherwise false
	 */
	@Override
	protected boolean validate(HttpServletRequest request) {

		boolean pass = true;

		if (DataValidator.isNull(request.getParameter("name"))) {
			request.setAttribute("name", PropertyReader.getValue("error.require", "Name"));
			pass = false;
		} else if (!DataValidator.isName(request.getParameter("name"))) {
			request.setAttribute("name", "Invalid Name");
			pass = false;
		}

		if (DataValidator.isNull(request.getParameter("description"))) {
			request.setAttribute("description", PropertyReader.getValue("error.require", "Description"));
			pass = false;
		}

		return pass;
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

		bean.setId(DataUtility.getLong(request.getParameter("id")));
		bean.setName(DataUtility.getString(request.getParameter("name")));
		bean.setDescription(DataUtility.getString(request.getParameter("description")));

		populateDTO(bean, request);

		return bean;
	}

	/**
	 * Handles GET request.
	 * Loads Role data based on ID (if present) and forwards to view.
	 * 
	 * @param request  HttpServletRequest object
	 * @param response HttpServletResponse object
	 * @throws ServletException if servlet error occurs
	 * @throws IOException      if I/O error occurs
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		long id = DataUtility.getLong(request.getParameter("id"));

		RoleModel model = new RoleModel();

		if (id > 0) {
			try {
				RoleBean bean = model.findByPk(id);
				ServletUtility.setBean(bean, request);
			} catch (Exception e) {
				e.printStackTrace();
				ServletUtility.handleException(e, request, response);
				return;
			}
		}
		ServletUtility.forward(getView(), request, response);
	}

	/**
	 * Handles POST request.
	 * Performs Save, Update, Cancel, and Reset operations.
	 * 
	 * @param request  HttpServletRequest object
	 * @param response HttpServletResponse object
	 * @throws ServletException if servlet error occurs
	 * @throws IOException      if I/O error occurs
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String op = DataUtility.getString(request.getParameter("operation"));

		RoleModel model = new RoleModel();

		long id = DataUtility.getLong(request.getParameter("id"));

		/**
		 * Save (Add) Operation
		 */
		if (OP_SAVE.equalsIgnoreCase(op)) {

			RoleBean bean = (RoleBean) populateBean(request);

			try {
				long pk = model.add(bean);
				ServletUtility.setBean(bean, request);
				ServletUtility.setSuccessMessage("Data is successfully saved", request);
			} catch (DuplicateRecordException e) {
				ServletUtility.setBean(bean, request);
				ServletUtility.setErrorMessage("Role already exists", request);
			} catch (ApplicationException e) {
				e.printStackTrace();
				ServletUtility.handleException(e, request, response);
				return;
			} catch (Exception e) {
				e.printStackTrace();
			}

		/**
		 * Update Operation
		 */
		} else if (OP_UPDATE.equalsIgnoreCase(op)) {

			RoleBean bean = (RoleBean) populateBean(request);

			try {
				if (id > 0) {
					model.update(bean);
				}
				ServletUtility.setBean(bean, request);
				ServletUtility.setSuccessMessage("Data is successfully updated", request);
			} catch (DuplicateRecordException e) {
				ServletUtility.setBean(bean, request);
				ServletUtility.setErrorMessage("Role already exists", request);
			} catch (ApplicationException e) {
				e.printStackTrace();
				ServletUtility.handleException(e, request, response);
				return;
			} catch (Exception e) {
				e.printStackTrace();
			}

		/**
		 * Cancel Operation
		 */
		} else if (OP_CANCEL.equalsIgnoreCase(op)) {
			ServletUtility.redirect(ORSView.ROLE_LIST_CTL, request, response);
			return;

		/**
		 * Reset Operation
		 */
		} else if (OP_RESET.equalsIgnoreCase(op)) {
			ServletUtility.redirect(ORSView.ROLE_CTL, request, response);
			return;
		}

		ServletUtility.forward(getView(), request, response);
	}

	/**
	 * Returns the Role view page.
	 * 
	 * @return view path
	 */
	@Override
	protected String getView() {
		return ORSView.ROLE_VIEW;
	}
}