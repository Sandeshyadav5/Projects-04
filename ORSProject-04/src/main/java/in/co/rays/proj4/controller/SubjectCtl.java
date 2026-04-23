package in.co.rays.proj4.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import in.co.rays.proj4.bean.BaseBean;
import in.co.rays.proj4.bean.SubjectBean;
import in.co.rays.proj4.exception.ApplicationException;
import in.co.rays.proj4.exception.DuplicateRecordException;
import in.co.rays.proj4.model.CourseModel;
import in.co.rays.proj4.model.SubjectModel;
import in.co.rays.proj4.util.DataUtility;
import in.co.rays.proj4.util.DataValidator;
import in.co.rays.proj4.util.PropertyReader;
import in.co.rays.proj4.util.ServletUtility;

/**
 * SubjectCtl is a Controller class responsible for handling
 * Subject operations such as Add, Update, Validation, and View.
 * 
 * It interacts with SubjectModel and CourseModel to perform
 * database operations and preload course data for selection.
 * 
 * Supported operations:
 * - Save (Add Subject)
 * - Update Subject
 * - Cancel
 * - Reset
 * 
 * It also validates user input before processing.
 * 
 * @author 
 */
@WebServlet(name = "SubjectCtl", urlPatterns = { "/ctl/SubjectCtl" })
public class SubjectCtl extends BaseCtl {
	private static final Logger log = Logger.getLogger(SubjectCtl.class);

	/**
	 * Preloads Course list for dropdown selection.
	 * 
	 * @param request HttpServletRequest object
	 */
	 @Override
	    protected void preload(HttpServletRequest request, HttpServletResponse response)
	    		throws IOException, ServletException {
		CourseModel courseModel = new CourseModel();
		try {
			List courseList = courseModel.list();
			request.setAttribute("courseList", courseList);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Validates Subject form input fields.
	 * 
	 * @param request HttpServletRequest object
	 * @return true if validation passes, otherwise false
	 */
	@Override
	protected boolean validate(HttpServletRequest request) {

		boolean pass = true;

		if (DataValidator.isNull(request.getParameter("name"))) {
			request.setAttribute("name", PropertyReader.getValue("error.require", "Subject Name"));
			pass = false;
		}

		if (DataValidator.isNull(request.getParameter("courseId"))) {
			request.setAttribute("courseId", PropertyReader.getValue("error.require", "Course Name"));
			pass = false;
		}

		if (DataValidator.isNull(request.getParameter("description"))) {
			request.setAttribute("description", PropertyReader.getValue("error.require", "Description"));
			pass = false;
		}

		return pass;
	}

	/**
	 * Populates SubjectBean with request parameters.
	 * 
	 * @param request HttpServletRequest object
	 * @return populated BaseBean (SubjectBean)
	 */
	@Override
	protected BaseBean populateBean(HttpServletRequest request) {

		SubjectBean bean = new SubjectBean();

		bean.setId(DataUtility.getLong(request.getParameter("id")));
		bean.setName(DataUtility.getString(request.getParameter("name")));
		bean.setCourseId(DataUtility.getLong(request.getParameter("courseId")));
		bean.setDescription(DataUtility.getString(request.getParameter("description")));

		populateDTO(bean, request);

		return bean;
	}

	/**
	 * Handles GET request.
	 * Loads Subject data by ID (if present) and forwards to view.
	 * 
	 * @param request  HttpServletRequest object
	 * @param response HttpServletResponse object
	 * @throws ServletException if servlet error occurs
	 * @throws IOException      if I/O error occurs
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		long id = DataUtility.getLong(request.getParameter("id"));

		SubjectModel model = new SubjectModel();

		if (id > 0) {
			try {
				SubjectBean bean = model.findByPk(id);
				ServletUtility.setBean(bean, request);
			} catch (ApplicationException e) {
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

		SubjectModel model = new SubjectModel();

		long id = DataUtility.getLong(request.getParameter("id"));

		/**
		 * Save Operation
		 */
		if (OP_SAVE.equalsIgnoreCase(op)) {
			SubjectBean bean = (SubjectBean) populateBean(request);
			try {
				long pk = model.add(bean);
				ServletUtility.setBean(bean, request);
				ServletUtility.setSuccessMessage("Subject added successfully", request);
			} catch (DuplicateRecordException e) {
				ServletUtility.setBean(bean, request);
				ServletUtility.setErrorMessage("Subject Name already exists", request);
			} catch (ApplicationException e) {
				e.printStackTrace();
				ServletUtility.handleException(e, request, response);
				return;
			}

		/**
		 * Update Operation
		 */
		} else if (OP_UPDATE.equalsIgnoreCase(op)) {
			SubjectBean bean = (SubjectBean) populateBean(request);
			try {
				if (id > 0) {
					model.update(bean);
				}
				ServletUtility.setBean(bean, request);
				ServletUtility.setSuccessMessage("Subject updated successfully", request);
			} catch (DuplicateRecordException e) {
				ServletUtility.setBean(bean, request);
				ServletUtility.setErrorMessage("Subject Name already exists", request);
			} catch (ApplicationException e) {
				e.printStackTrace();
				ServletUtility.handleException(e, request, response);
				return;
			}

		/**
		 * Cancel Operation
		 */
		} else if (OP_CANCEL.equalsIgnoreCase(op)) {
			ServletUtility.redirect(ORSView.SUBJECT_LIST_CTL, request, response);
			return;

		/**
		 * Reset Operation
		 */
		} else if (OP_RESET.equalsIgnoreCase(op)) {
			ServletUtility.redirect(ORSView.SUBJECT_CTL, request, response);
			return;
		}

		ServletUtility.forward(getView(), request, response);
	}

	/**
	 * Returns Subject View page.
	 * 
	 * @return view path
	 */
	@Override
	protected String getView() {
		return ORSView.SUBJECT_VIEW;
	}
}