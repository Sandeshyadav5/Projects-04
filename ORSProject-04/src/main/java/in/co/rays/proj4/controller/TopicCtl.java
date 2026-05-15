package in.co.rays.proj4.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import in.co.rays.proj4.bean.BaseBean;
import in.co.rays.proj4.bean.TopicBean;
import in.co.rays.proj4.exception.ApplicationException;
import in.co.rays.proj4.exception.DuplicateRecordException;
import in.co.rays.proj4.model.TopicModel;
import in.co.rays.proj4.util.DataUtility;
import in.co.rays.proj4.util.DataValidator;
import in.co.rays.proj4.util.PropertyReader;
import in.co.rays.proj4.util.ServletUtility;

@WebServlet(name = "TopicCtl", urlPatterns = { "/ctl/TopicCtl" })
public class TopicCtl extends BaseCtl {
	@Override
	protected void preload(HttpServletRequest request, HttpServletResponse response)
	        throws ServletException, IOException {

	    TopicModel model = new TopicModel();

	    try {
	        List nameList = model.list(0, 0);

	       
	        request.setAttribute("nameList", nameList);

	    } catch (ApplicationException e) {
	        e.printStackTrace();
	        ServletUtility.handleException(e, request, response);
	    }
	}

    @Override
    protected BaseBean populateBean(HttpServletRequest request) {

        TopicBean bean = new TopicBean();

        bean.setId(DataUtility.getLong(request.getParameter("id")));
        bean.setTopicCode(DataUtility.getString(request.getParameter("topicCode")));
        bean.setTopicName(DataUtility.getString(request.getParameter("topicName")));
        bean.setPartitions(DataUtility.getInt(request.getParameter("partitions")));
        bean.setStatus(DataUtility.getString(request.getParameter("status")));

        populateDTO(bean, request);

        return bean;
    }

    @Override
    protected boolean validate(HttpServletRequest request) {

        boolean pass = true;

        if (DataValidator.isNull(request.getParameter("topicCode"))) {
            request.setAttribute("topicCode",
                    PropertyReader.getValue("error.require", "Topic Code"));
            pass = false;
        }

        if (DataValidator.isNull(request.getParameter("topicName"))) {
            request.setAttribute("topicName",
                    PropertyReader.getValue("error.require", "Topic Name"));
            pass = false;
        }

        return pass;
    }

    @Override
    protected void doGet(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        long id = DataUtility.getLong(request.getParameter("id"));

        TopicModel model = new TopicModel();

        if (id > 0) {
            try {
                TopicBean bean = model.findByPk(id);
                ServletUtility.setBean(bean, request);
            } catch (ApplicationException e) {
                e.printStackTrace();
                ServletUtility.handleException(e, request, response);
                return;
            }
        }

        ServletUtility.forward(getView(), request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        String op = DataUtility.getString(request.getParameter("operation"));

        TopicModel model = new TopicModel();
        long id = DataUtility.getLong(request.getParameter("id"));

        if (OP_SAVE.equalsIgnoreCase(op)) {

            TopicBean bean = (TopicBean) populateBean(request);

            try {
                model.add(bean);

                ServletUtility.setSuccessMessage("Topic added successfully", request);
                ServletUtility.redirect(ORSView.TOPIC_CTL, request, response);
                return;

            } catch (DuplicateRecordException e) {
                ServletUtility.setBean(bean, request);
                ServletUtility.setErrorMessage("Record already exists", request);

            } catch (ApplicationException e) {
                ServletUtility.handleException(e, request, response);
                return;
            }

        } else if (OP_UPDATE.equalsIgnoreCase(op)) {

            TopicBean bean = (TopicBean) populateBean(request);

            try {
                if (id > 0) {
                    model.update(bean);
                }

                ServletUtility.setBean(bean, request);
                ServletUtility.setSuccessMessage("Topic Updated Successfully", request);

            } catch (ApplicationException e) {
                ServletUtility.handleException(e, request, response);
                return;
            } catch (DuplicateRecordException e) {
                ServletUtility.setBean(bean, request);
                ServletUtility.setErrorMessage("Duplicate record found", request);
            }

        } else if (OP_CANCEL.equalsIgnoreCase(op)) {
            ServletUtility.redirect(ORSView.TOPIC_LIST_CTL, request, response);
            return;

        } else if (OP_RESET.equalsIgnoreCase(op)) {
            ServletUtility.redirect(ORSView.TOPIC_CTL, request, response);
            return;
        }

        ServletUtility.forward(getView(), request, response);
    }

    @Override
    protected String getView() {
        return ORSView.TOPIC_VIEW;
    }
}