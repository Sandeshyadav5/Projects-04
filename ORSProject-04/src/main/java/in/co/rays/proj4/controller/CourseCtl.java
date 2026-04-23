package in.co.rays.proj4.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import in.co.rays.proj4.bean.BaseBean;
import in.co.rays.proj4.bean.CourseBean;
import in.co.rays.proj4.exception.ApplicationException;
import in.co.rays.proj4.exception.DuplicateRecordException;
import in.co.rays.proj4.model.CourseModel;
import in.co.rays.proj4.util.DataUtility;
import in.co.rays.proj4.util.DataValidator;
import in.co.rays.proj4.util.PropertyReader;
import in.co.rays.proj4.util.ServletUtility;

/**
 * CourseCtl handles operations related to Course.
 * It manages validation, add, update, and navigation.
 * 
 * URL Mapping: /CourseCtl
 * 
 * @author 
 */
@WebServlet(name = "CourseCtl", urlPatterns = { "/ctl/CourseCtl" })
public class CourseCtl extends BaseCtl {
	private static final Logger log = Logger.getLogger(CourseCtl.class);

    /**
     * Validates input fields for Course.
     * 
     * @param request HttpServletRequest
     * @return true if valid, false otherwise
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

        if (DataValidator.isNull(request.getParameter("duration"))) {
            request.setAttribute("duration", PropertyReader.getValue("error.require", "Duration"));
            pass = false;
        }

        if (DataValidator.isNull(request.getParameter("description"))) {
            request.setAttribute("description", PropertyReader.getValue("error.require", "Description"));
            pass = false;
        }

        return pass;
    }

    /**
     * Populates CourseBean from request parameters.
     * 
     * @param request HttpServletRequest
     * @return populated CourseBean
     */
    @Override
    protected BaseBean populateBean(HttpServletRequest request) {

        CourseBean bean = new CourseBean();

        bean.setId(DataUtility.getLong(request.getParameter("id")));
        bean.setName(DataUtility.getString(request.getParameter("name")));
        bean.setDuration(DataUtility.getString(request.getParameter("duration")));
        bean.setDescription(DataUtility.getString(request.getParameter("description")));

        populateDTO(bean, request);

        return bean;
    }

    /**
     * Handles HTTP GET request.
     * Loads course data for editing if ID is provided.
     * 
     * @param request  HttpServletRequest
     * @param response HttpServletResponse
     * @throws ServletException
     * @throws IOException
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        long id = DataUtility.getLong(request.getParameter("id"));

        CourseModel model = new CourseModel();

        if (id > 0) {
            try {
                CourseBean bean = model.findByPk(id);
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
     * Handles HTTP POST request.
     * Performs operations like Save, Update, Cancel, and Reset.
     * 
     * @param request  HttpServletRequest
     * @param response HttpServletResponse
     * @throws ServletException
     * @throws IOException
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String op = DataUtility.getString(request.getParameter("operation"));

        CourseModel model = new CourseModel();

        long id = DataUtility.getLong(request.getParameter("id"));

        /**
         * Save operation
         */
        if (OP_SAVE.equalsIgnoreCase(op)) {
            CourseBean bean = (CourseBean) populateBean(request);
            try {
                long pk = model.add(bean);
                ServletUtility.setBean(bean, request);
                ServletUtility.setSuccessMessage("Course added successfully", request);
            } catch (DuplicateRecordException e) {
                ServletUtility.setBean(bean, request);
                ServletUtility.setErrorMessage("Course already exists", request);
            } catch (ApplicationException e) {
                e.printStackTrace();
                ServletUtility.handleException(e, request, response);
                return;
            }

        } 
        /**
         * Update operation
         */
        else if (OP_UPDATE.equalsIgnoreCase(op)) {
            CourseBean bean = (CourseBean) populateBean(request);
            try {
                if (id > 0) {
                    model.update(bean);
                }
                ServletUtility.setBean(bean, request);
                ServletUtility.setSuccessMessage("Course updated successfully", request);
            } catch (DuplicateRecordException e) {
                ServletUtility.setBean(bean, request);
                ServletUtility.setErrorMessage("Course already exists", request);
            } catch (ApplicationException e) {
                e.printStackTrace();
                ServletUtility.handleException(e, request, response);
                return;
            }

        } 
        /**
         * Cancel operation
         */
        else if (OP_CANCEL.equalsIgnoreCase(op)) {
            ServletUtility.redirect(ORSView.COURSE_LIST_CTL, request, response);
            return;

        } 
        /**
         * Reset operation
         */
        else if (OP_RESET.equalsIgnoreCase(op)) {
            ServletUtility.redirect(ORSView.COURSE_CTL, request, response);
            return;
        }

        ServletUtility.forward(getView(), request, response);
    }

    /**
     * Returns the view page for Course
     * 
     * @return view path
     */
    @Override
    protected String getView() {
        return ORSView.COURSE_VIEW;
    }
}