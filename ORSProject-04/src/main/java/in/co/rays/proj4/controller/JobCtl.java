package in.co.rays.proj4.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import in.co.rays.proj4.bean.BaseBean;
import in.co.rays.proj4.bean.JobBean;
import in.co.rays.proj4.exception.ApplicationException;
import in.co.rays.proj4.exception.DuplicateRecordException;
import in.co.rays.proj4.model.JobModel;
import in.co.rays.proj4.util.DataUtility;
import in.co.rays.proj4.util.DataValidator;
import in.co.rays.proj4.util.PropertyReader;
import in.co.rays.proj4.util.ServletUtility;

@WebServlet(name = "JobCtl", urlPatterns = { "/ctl/JobCtl" })
public class JobCtl extends BaseCtl {

    @Override
    protected BaseBean populateBean(HttpServletRequest request) {

        JobBean bean = new JobBean();

        bean.setId(DataUtility.getLong(request.getParameter("id")));
        bean.setJobCode(DataUtility.getString(request.getParameter("jobCode")));
        bean.setJobName(DataUtility.getString(request.getParameter("jobName")));
        bean.setCronExpression(DataUtility.getString(request.getParameter("cronExpression")));
        bean.setStatus(DataUtility.getString(request.getParameter("status")));

        populateDTO(bean, request);

        return bean;
    }

    @Override
    protected boolean validate(HttpServletRequest request) {

        boolean pass = true;

        if (DataValidator.isNull(request.getParameter("jobCode"))) {
            request.setAttribute("jobCode",
                    PropertyReader.getValue("error.require", "Job Code"));
            pass = false;
        }

        if (DataValidator.isNull(request.getParameter("jobName"))) {
            request.setAttribute("jobName",
                    PropertyReader.getValue("error.require", "Job Name"));
            pass = false;
        }
        if (DataValidator.isNull(request.getParameter("cronExpression"))) {
            request.setAttribute("cronExpression",
                    PropertyReader.getValue("error.require", "Cron Expression"));
            pass = false;
        }
        if (DataValidator.isNull(request.getParameter("status"))) {
         request.setAttribute("status",
                    PropertyReader.getValue("error.require", "Status"));
            pass = false;
        }

        return pass;
    }

    @Override
    protected void doGet(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        long id = DataUtility.getLong(request.getParameter("id"));

        JobModel model = new JobModel();

        if (id > 0) {
            try {
                JobBean bean = model.findByPk(id);
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

        JobModel model = new JobModel();
        long id = DataUtility.getLong(request.getParameter("id"));

        if (OP_SAVE.equalsIgnoreCase(op)) {

            JobBean bean = (JobBean) populateBean(request);

            try {
                model.add(bean);

                ServletUtility.setSuccessMessage("Job added successfully", request);
                ServletUtility.redirect(ORSView.JOB_CTL, request, response);
                return;

            } catch (DuplicateRecordException e) {
                ServletUtility.setBean(bean, request);
                ServletUtility.setErrorMessage("Record already exists", request);

            } catch (ApplicationException e) {
                ServletUtility.handleException(e, request, response);
                return;
            }

        } else if (OP_UPDATE.equalsIgnoreCase(op)) {

            JobBean bean = (JobBean) populateBean(request);

            try {
                if (id > 0) {
                    model.update(bean);
                }

                ServletUtility.setBean(bean, request);
                ServletUtility.setSuccessMessage("Job Updated Successfully", request);

            } catch (ApplicationException e) {
                e.printStackTrace();
                ServletUtility.handleException(e, request, response);
                return;

            } catch (DuplicateRecordException e) {
                ServletUtility.setBean(bean, request);
                ServletUtility.setErrorMessage("Duplicate record found", request);
            }

        } else if (OP_CANCEL.equalsIgnoreCase(op)) {

            ServletUtility.redirect(ORSView.JOB_LIST_CTL, request, response);
            return;

        } else if (OP_RESET.equalsIgnoreCase(op)) {

            ServletUtility.redirect(ORSView.JOB_CTL, request, response);
            return;
        }

        ServletUtility.forward(getView(), request, response);
    }

    @Override
    protected String getView() {
        return ORSView.JOB_VIEW;
    }
}