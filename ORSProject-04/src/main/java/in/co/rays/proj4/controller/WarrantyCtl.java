
package in.co.rays.proj4.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import in.co.rays.proj4.bean.BaseBean;
import in.co.rays.proj4.bean.WarrantyBean;
import in.co.rays.proj4.exception.ApplicationException;
import in.co.rays.proj4.exception.DuplicateRecordException;
import in.co.rays.proj4.model.WarrantyModel;
import in.co.rays.proj4.util.DataUtility;
import in.co.rays.proj4.util.DataValidator;
import in.co.rays.proj4.util.PropertyReader;
import in.co.rays.proj4.util.ServletUtility;

@WebServlet(name = "WarrantyCtl", urlPatterns = { "/ctl/WarrantyCtl" })
public class WarrantyCtl extends BaseCtl {

   
    // ================= Populate Bean =================
    @Override
    protected BaseBean populateBean(HttpServletRequest request) {

        WarrantyBean bean = new WarrantyBean();

        bean.setId(DataUtility.getLong(request.getParameter("id")));
        bean.setProductName(DataUtility.getString(request.getParameter("productName")));
        bean.setStartDate(DataUtility.getDate(request.getParameter("Start")));
        bean.setEndDate(DataUtility.getDate(request.getParameter("End")));

        populateDTO(bean, request);

        return bean;
    }

    // ================= Validate =================
    @Override
    protected boolean validate(HttpServletRequest request) {

        boolean pass = true;

        if (DataValidator.isNull(request.getParameter("productName"))) {
            request.setAttribute("productName",
                    PropertyReader.getValue("error.require", "productName"));
            pass = false;
        }

        if (!DataValidator.isDate(request.getParameter("Start"))) {
            request.setAttribute("Start", "Invalid Start Date");
            pass = false;
        }

        if (!DataValidator.isDate(request.getParameter("End"))) {
            request.setAttribute("End", "Invalid End Date");
            pass = false;
        }

        

        return pass;
    }

    // ================= GET =================
    @Override
    protected void doGet(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        long id = DataUtility.getLong(request.getParameter("id"));

        WarrantyModel model = new WarrantyModel();

        if (id > 0) {
            try {
                WarrantyBean bean = model.findByPk(id);
                ServletUtility.setBean(bean, request);
            } catch (ApplicationException e) {
                e.printStackTrace();
                ServletUtility.handleException(e, request, response);
                return;
            }
        }

        ServletUtility.forward(getView(), request, response);
    }

    // ================= POST =================
    @Override
    protected void doPost(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        String op = DataUtility.getString(request.getParameter("operation"));

        WarrantyModel model = new WarrantyModel();
        long id = DataUtility.getLong(request.getParameter("id"));

        if (OP_SAVE.equalsIgnoreCase(op)) {

            WarrantyBean bean = (WarrantyBean) populateBean(request);

            try {
                model.add(bean);

                ServletUtility.setSuccessMessage("Warranty added successfully", request);
                ServletUtility.redirect(ORSView.WARRANTY_CTL, request, response);
                return;

            } catch (DuplicateRecordException e) {
                ServletUtility.setBean(bean, request);
                ServletUtility.setErrorMessage("Record already exists", request);

            } catch (ApplicationException e) {
                ServletUtility.handleException(e, request, response);
                return;
            }
        }else if (OP_UPDATE.equalsIgnoreCase(op)) {

            WarrantyBean bean = (WarrantyBean) populateBean(request);

            try {
                if (id > 0) {
                    model.update(bean);
                }

                ServletUtility.setBean(bean, request);
                ServletUtility.setSuccessMessage("WARRANTY Updated Successfully", request);

            } catch (ApplicationException e) {
                e.printStackTrace();
                ServletUtility.handleException(e, request, response);
                return;
            } catch (DuplicateRecordException e) {
                ServletUtility.setBean(bean, request);
                ServletUtility.setErrorMessage("Duplicate record found", request);
            }

        } else if (OP_CANCEL.equalsIgnoreCase(op)) {
            ServletUtility.redirect(ORSView.WARRANTY_LIST_CTL, request, response);
            return;

        } else if (OP_RESET.equalsIgnoreCase(op)) {
            ServletUtility.redirect(ORSView.WARRANTY_CTL, request, response);
            return;
        }

        ServletUtility.forward(getView(), request, response);
    }

    @Override
    protected String getView() {
        return ORSView.WARRANTY_VIEW;
    }
}