package in.co.rays.proj4.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import in.co.rays.proj4.bean.BaseBean;
import in.co.rays.proj4.bean.JobBean;
import in.co.rays.proj4.exception.ApplicationException;
import in.co.rays.proj4.model.JobModel;
import in.co.rays.proj4.util.DataUtility;
import in.co.rays.proj4.util.PropertyReader;
import in.co.rays.proj4.util.ServletUtility;

@WebServlet(name = "JobListCtl", urlPatterns = { "/ctl/JobListCtl" })
public class JobListCtl extends BaseCtl {

    // ================= PRELOAD =================
    @Override
    protected void preload(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        JobModel model = new JobModel();

        try {
            List list = model.list(0, 0);

            // 🔥 IMPORTANT: dropdown ke liye
            request.setAttribute("nameList", list);

        } catch (ApplicationException e) {
            e.printStackTrace();
            ServletUtility.handleException(e, request, response);
        }
    }

    // ================= POPULATE BEAN =================
    @Override
    protected BaseBean populateBean(HttpServletRequest request) {

        JobBean bean = new JobBean();

        bean.setId(DataUtility.getLong(request.getParameter("id")));
        bean.setJobCode(DataUtility.getString(request.getParameter("jobCode")));

        populateDTO(bean, request);

        return bean;
    }

    // ================= GET =================
    @Override
    protected void doGet(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        int pageNo = 1;
        int pageSize = DataUtility.getInt(PropertyReader.getValue("page.size"));

        JobBean bean = (JobBean) populateBean(request);
        JobModel model = new JobModel();

        try {
            List list = model.search(bean, pageNo, pageSize);
            List nextList = model.search(bean, pageNo + 1, pageSize);

            if (list == null || list.size() == 0) {
                ServletUtility.setErrorMessage("No record found", request);
            }

            ServletUtility.setList(list, request);
            ServletUtility.setPageNo(pageNo, request);
            ServletUtility.setPageSize(pageSize, request);
            ServletUtility.setBean(bean, request);

            request.setAttribute("nextListSize", nextList.size());

            ServletUtility.forward(getView(), request, response);

        } catch (ApplicationException e) {
            e.printStackTrace();
            ServletUtility.handleException(e, request, response);
        }
    }

    // ================= POST =================
    @Override
    protected void doPost(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        List list = null;
        List nextList = null;

        int pageNo = DataUtility.getInt(request.getParameter("pageNo"));
        int pageSize = DataUtility.getInt(request.getParameter("pageSize"));

        pageNo = (pageNo == 0) ? 1 : pageNo;
        pageSize = (pageSize == 0)
                ? DataUtility.getInt(PropertyReader.getValue("page.size"))
                : pageSize;

        JobBean bean = (JobBean) populateBean(request);
        JobModel model = new JobModel();

        String op = DataUtility.getString(request.getParameter("operation"));
        String[] ids = request.getParameterValues("ids");

        try {

            if (OP_SEARCH.equalsIgnoreCase(op)
                    || OP_NEXT.equalsIgnoreCase(op)
                    || OP_PREVIOUS.equalsIgnoreCase(op)) {

                if (OP_SEARCH.equalsIgnoreCase(op)) {
                    pageNo = 1;

                } else if (OP_NEXT.equalsIgnoreCase(op)) {
                    pageNo++;

                } else if (OP_PREVIOUS.equalsIgnoreCase(op) && pageNo > 1) {
                    pageNo--;
                }

            } else if (OP_NEW.equalsIgnoreCase(op)) {

                ServletUtility.redirect(ORSView.JOB_CTL, request, response);
                return;

            } else if (OP_DELETE.equalsIgnoreCase(op)) {

                pageNo = 1;

                if (ids != null && ids.length > 0) {
                    JobBean deleteBean = new JobBean();

                    for (String id : ids) {
                        deleteBean.setId(DataUtility.getLong(id));
                        model.delete(deleteBean);
                    }

                    ServletUtility.setSuccessMessage("Deleted Successfully", request);

                } else {
                    ServletUtility.setErrorMessage("Select at least one record", request);
                }

            } else if (OP_RESET.equalsIgnoreCase(op)
                    || OP_BACK.equalsIgnoreCase(op)) {

                ServletUtility.redirect(ORSView.JOB_LIST_CTL, request, response);
                return;
            }

            list = model.search(bean, pageNo, pageSize);
            nextList = model.search(bean, pageNo + 1, pageSize);

            if (list == null || list.size() == 0) {
                ServletUtility.setErrorMessage("No record found", request);
            }

            ServletUtility.setList(list, request);
            ServletUtility.setPageNo(pageNo, request);
            ServletUtility.setPageSize(pageSize, request);
            ServletUtility.setBean(bean, request);

            request.setAttribute("nextListSize", nextList.size());

            ServletUtility.forward(getView(), request, response);

        } catch (ApplicationException e) {
            e.printStackTrace();
            ServletUtility.handleException(e, request, response);
        }
    }

    // ================= VIEW =================
    @Override
    protected String getView() {
        return ORSView.JOB_LIST_VIEW;
    }
}