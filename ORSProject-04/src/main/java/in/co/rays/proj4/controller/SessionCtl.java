package in.co.rays.proj4.controller;
import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import in.co.rays.proj4.bean.BaseBean;
import in.co.rays.proj4.bean.SessionBean;
import in.co.rays.proj4.exception.ApplicationException;
import in.co.rays.proj4.exception.DuplicateRecordException;
import in.co.rays.proj4.model.SessionModel;
import in.co.rays.proj4.util.DataUtility;
import in.co.rays.proj4.util.DataValidator;
import in.co.rays.proj4.util.PropertyReader;
import in.co.rays.proj4.util.ServletUtility;

@WebServlet(name = "SessionCtl", urlPatterns = { "/ctl/SessionCtl" })
public class SessionCtl extends BaseCtl {

	@Override
	protected void preload(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		SessionModel model = new SessionModel();

		try {
			List list = model.list(0, 0);
			request.setAttribute("nameList", list);

		} catch (ApplicationException e) {
			e.printStackTrace();
			ServletUtility.handleException(e, request, response);
		}
	}

	@Override
	protected BaseBean populateBean(HttpServletRequest request) {

		SessionBean bean = new SessionBean();

		bean.setId(DataUtility.getLong(request.getParameter("id")));
		bean.setSessionCode(DataUtility.getString(request.getParameter("sessionCode")));
		bean.setUserName(DataUtility.getString(request.getParameter("userName")));
		bean.setLoginTime(DataUtility.getDate(request.getParameter("loginTime")));
		bean.setStatus(DataUtility.getString(request.getParameter("status")));

		populateDTO(bean, request);

		return bean;
	}

	@Override
	protected boolean validate(HttpServletRequest request) {

		boolean pass = true;

		if (DataValidator.isNull(request.getParameter("sessionCode"))) {
			request.setAttribute("sessionCode", PropertyReader.getValue("error.require", "Session Code"));
			pass = false;
		}

		if (DataValidator.isNull(request.getParameter("userName"))) {
			request.setAttribute("userName", PropertyReader.getValue("error.require", "User Name"));
			pass = false;
		}
		if (DataValidator.isNull(request.getParameter("loginTime"))) {
			request.setAttribute("loginTime", PropertyReader.getValue("error.require", "Login Time"));
			pass = false;
		}
		if (DataValidator.isNull(request.getParameter("status"))) {
			request.setAttribute("status", PropertyReader.getValue("error.require", "status"));
			pass = false;
		}

		return pass;
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		long id = DataUtility.getLong(request.getParameter("id"));
		SessionModel model = new SessionModel();

		if (id > 0) {
			try {
				SessionBean bean = model.findByPk(id);
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
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String op = DataUtility.getString(request.getParameter("operation"));
		SessionModel model = new SessionModel();
		long id = DataUtility.getLong(request.getParameter("id"));

		if (OP_SAVE.equalsIgnoreCase(op)) {

			SessionBean bean = (SessionBean) populateBean(request);

			try {
				model.add(bean);
				ServletUtility.setSuccessMessage("Session added successfully", request);
				ServletUtility.forward(getView(), request, response);
				
				return;

			} catch (DuplicateRecordException e) {
				ServletUtility.setBean(bean, request);
				ServletUtility.setErrorMessage("Record already exists", request);

			} catch (ApplicationException e) {
				ServletUtility.handleException(e, request, response);
				return;
			}

		}  else if (OP_UPDATE.equalsIgnoreCase(op)) {

			SessionBean bean = (SessionBean) populateBean(request);

			try {

				if (id > 0) {
					model.update(bean);
				}

				ServletUtility.setBean(bean, request);
				ServletUtility.setSuccessMessage("Session Updated Successfully", request);

			} catch (DuplicateRecordException e) {

				ServletUtility.setBean(bean, request);
				ServletUtility.setErrorMessage("Session Code already exists", request);

			} catch (ApplicationException e) {

				ServletUtility.handleException(e, request, response);
				return;
			}}
		 else if (OP_CANCEL.equalsIgnoreCase(op)) {
			ServletUtility.redirect(ORSView.SESSION_LIST_CTL, request, response);
			return;

		} else if (OP_RESET.equalsIgnoreCase(op)) {
			ServletUtility.redirect(ORSView.SESSION_CTL, request, response);
			return;
		}

		ServletUtility.forward(getView(), request, response);
	}

	@Override
	protected String getView() {
		return ORSView.SESSION_VIEW;
	}
}