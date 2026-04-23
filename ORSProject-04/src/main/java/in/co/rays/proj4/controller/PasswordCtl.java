
package in.co.rays.proj4.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import in.co.rays.proj4.bean.BaseBean;
import in.co.rays.proj4.bean.PasswordBean;
import in.co.rays.proj4.exception.ApplicationException;
import in.co.rays.proj4.exception.DuplicateRecordException;
import in.co.rays.proj4.model.PasswordModel;
import in.co.rays.proj4.util.DataUtility;
import in.co.rays.proj4.util.DataValidator;
import in.co.rays.proj4.util.PropertyReader;
import in.co.rays.proj4.util.ServletUtility;

@WebServlet(name = "PasswordCtl", urlPatterns = { "/ctl/PasswordCtl" })
public class PasswordCtl extends BaseCtl {

   
    // ================= Populate Bean =================
    @Override
    protected BaseBean populateBean(HttpServletRequest request) {

        PasswordBean bean = new PasswordBean();

        bean.setId(DataUtility.getLong(request.getParameter("id")));
        bean.setResetCode(DataUtility.getString(request.getParameter("code")));
        bean.setUserName(DataUtility.getString(request.getParameter("username")));
        bean.setResetToken(DataUtility.getString(request.getParameter("token")));
        bean.setStatus(DataUtility.getString(request.getParameter("status")));

        populateDTO(bean, request);

        return bean;
    }

    // ================= Validate =================
    @Override
    protected boolean validate(HttpServletRequest request) {

        boolean pass = true;

        if (DataValidator.isNull(request.getParameter("code"))) {
            request.setAttribute("code",
                    PropertyReader.getValue("error.require", "code"));
            pass = false;
        }

        if (DataValidator.isNull(request.getParameter("token"))) {
            request.setAttribute("token",
                    PropertyReader.getValue("error.require", "token"));
            pass = false;
        }
        if (DataValidator.isNull(request.getParameter("username"))) {
            request.setAttribute("username",
                    PropertyReader.getValue("error.require", "username"));
            pass = false;
        }

        if (DataValidator.isNull(request.getParameter("status"))) {
            request.setAttribute("status",
                    PropertyReader.getValue("error.require", "status"));
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

        PasswordModel model = new PasswordModel();

        if (id > 0) {
            try {
                PasswordBean bean = model.findByPk(id);
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

        PasswordModel model = new PasswordModel();
        long id = DataUtility.getLong(request.getParameter("id"));

        if (OP_SAVE.equalsIgnoreCase(op)) {

            PasswordBean bean = (PasswordBean) populateBean(request);

            try {
                model.add(bean);

                ServletUtility.setSuccessMessage("Password added successfully", request);
                ServletUtility.redirect(ORSView.PASSWORD_CTL, request, response);
                return;

            } catch (DuplicateRecordException e) {
                ServletUtility.setBean(bean, request);
                ServletUtility.setErrorMessage("Record already exists", request);

            } catch (ApplicationException e) {
                ServletUtility.handleException(e, request, response);
                return;
            }
        }else if (OP_UPDATE.equalsIgnoreCase(op)) {

            PasswordBean bean = (PasswordBean) populateBean(request);

            try {
                if (id > 0) {
                    model.update(bean);
                }

                ServletUtility.setBean(bean, request);
                ServletUtility.setSuccessMessage("PASSWORD Updated Successfully", request);

            } catch (ApplicationException e) {
                e.printStackTrace();
                ServletUtility.handleException(e, request, response);
                return;
            } catch (DuplicateRecordException e) {
                ServletUtility.setBean(bean, request);
                ServletUtility.setErrorMessage("Duplicate record found", request);
            }

        } else if (OP_CANCEL.equalsIgnoreCase(op)) {
            ServletUtility.redirect(ORSView.PASSWORD_LIST_CTL, request, response);
            return;

        } else if (OP_RESET.equalsIgnoreCase(op)) {
            ServletUtility.redirect(ORSView.PASSWORD_CTL, request, response);
            return;
        }

        ServletUtility.forward(getView(), request, response);
    }

    @Override
    protected String getView() {
        return ORSView.PASSWORD_VIEW;
    }
}