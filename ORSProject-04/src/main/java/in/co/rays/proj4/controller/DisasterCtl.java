package in.co.rays.proj4.controller;
import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import in.co.rays.proj4.bean.BaseBean;
import in.co.rays.proj4.bean.DisasterBean;
import in.co.rays.proj4.exception.ApplicationException;
import in.co.rays.proj4.exception.DuplicateRecordException;
import in.co.rays.proj4.model.DisasterModel;
import in.co.rays.proj4.util.DataUtility;
import in.co.rays.proj4.util.DataValidator;
import in.co.rays.proj4.util.PropertyReader;
import in.co.rays.proj4.util.ServletUtility;

@WebServlet(name = "DisasterCtl", urlPatterns = { "/ctl/DisasterCtl" })
public class DisasterCtl extends BaseCtl {

	@Override
	protected void preload(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		DisasterModel model = new DisasterModel();

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

		DisasterBean bean = new DisasterBean();

		bean.setId(DataUtility.getLong(request.getParameter("id")));
		bean.setAlertType(DataUtility.getString(request.getParameter("alertType")));
		bean.setLocation(DataUtility.getString(request.getParameter("location")));
		bean.setSeverty(DataUtility.getString(request.getParameter("severty")));
		bean.setAlertTime(DataUtility.getDate(request.getParameter("alertTime")));

		populateDTO(bean, request);

		return bean;
	}

	@Override
	protected boolean validate(HttpServletRequest request) {

		boolean pass = true;

		if (DataValidator.isNull(request.getParameter("alertType"))) {
			request.setAttribute("alertType",
					PropertyReader.getValue("error.require", "Alert Type"));
			pass = false;
		}

		if (DataValidator.isNull(request.getParameter("location"))) {
			request.setAttribute("location",
					PropertyReader.getValue("error.require", "Location"));
			pass = false;
		}

		if (DataValidator.isNull(request.getParameter("severty"))) {

			request.setAttribute("severty",
					PropertyReader.getValue("error.require", "Severty"));
			pass = false;

		} 

		if (DataValidator.isNull(request.getParameter("alertTime"))) {
			request.setAttribute("alertTime",
					PropertyReader.getValue("error.require", "AlertTime"));
			pass = false;
		}

		return pass;
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		long id = DataUtility.getLong(request.getParameter("id"));
		DisasterModel model = new DisasterModel();

		if (id > 0) {
			try {
				DisasterBean bean = model.findByPk(id);
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
		DisasterModel model = new DisasterModel();
		long id = DataUtility.getLong(request.getParameter("id"));

		if (OP_SAVE.equalsIgnoreCase(op)) {

			DisasterBean bean = (DisasterBean) populateBean(request);

			try {
				model.add(bean);
				ServletUtility.setSuccessMessage("Disaster added successfully", request);
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

			DisasterBean bean = (DisasterBean) populateBean(request);

			try {

				if (id > 0) {
					model.update(bean);
				}

				ServletUtility.setBean(bean, request);
				ServletUtility.setSuccessMessage("Disaster Updated Successfully", request);
				
			} catch (DuplicateRecordException e) {

				ServletUtility.setBean(bean, request);
				ServletUtility.setErrorMessage("Disaster Code already exists", request);

			} catch (ApplicationException e) {

				ServletUtility.handleException(e, request, response);
				return;
			}}
		 else if (OP_CANCEL.equalsIgnoreCase(op)) {
			ServletUtility.redirect(ORSView.DISASTER_LIST_CTL, request, response);
			return;

		} else if (OP_RESET.equalsIgnoreCase(op)) {
			ServletUtility.redirect(ORSView.DISASTER_CTL, request, response);
			return;
		}

		ServletUtility.forward(getView(), request, response);
	}

	@Override
	protected String getView() {
		return ORSView.DISASTER_VIEW;
	}
}