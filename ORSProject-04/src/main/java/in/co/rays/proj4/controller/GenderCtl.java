package in.co.rays.proj4.controller;
import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import in.co.rays.proj4.bean.BaseBean;
import in.co.rays.proj4.bean.GenderBean;
import in.co.rays.proj4.exception.ApplicationException;
import in.co.rays.proj4.exception.DuplicateRecordException;
import in.co.rays.proj4.model.GenderModel;
import in.co.rays.proj4.util.DataUtility;
import in.co.rays.proj4.util.DataValidator;
import in.co.rays.proj4.util.PropertyReader;
import in.co.rays.proj4.util.ServletUtility;

@WebServlet(name = "GenderCtl", urlPatterns = { "/ctl/GenderCtl" })
public class GenderCtl extends BaseCtl {

	@Override
	protected void preload(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		GenderModel model = new GenderModel();

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

		GenderBean bean = new GenderBean();

		bean.setId(DataUtility.getLong(request.getParameter("id")));
		bean.setGenderCode(DataUtility.getString(request.getParameter("genderCode")));
		bean.setGenderType(DataUtility.getString(request.getParameter("genderType")));
		bean.setDescription(DataUtility.getString(request.getParameter("description")));
		bean.setStatus(DataUtility.getString(request.getParameter("status")));

		populateDTO(bean, request);

		return bean;
	}

	@Override
	protected boolean validate(HttpServletRequest request) {

		boolean pass = true;

		if (DataValidator.isNull(request.getParameter("genderCode"))) {
			request.setAttribute("genderCode",
					PropertyReader.getValue("error.require", "Gender Code"));
			pass = false;
		}

		if (DataValidator.isNull(request.getParameter("genderType"))) {
			request.setAttribute("genderType",
					PropertyReader.getValue("error.require", "Gender Type"));
			pass = false;
		}

		if (DataValidator.isNull(request.getParameter("description"))) {

			request.setAttribute("description",
					PropertyReader.getValue("error.require", "Description"));
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
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		long id = DataUtility.getLong(request.getParameter("id"));
		GenderModel model = new GenderModel();

		if (id > 0) {
			try {
				GenderBean bean = model.findByPk(id);
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
		GenderModel model = new GenderModel();
		long id = DataUtility.getLong(request.getParameter("id"));

		if (OP_SAVE.equalsIgnoreCase(op)) {

			GenderBean bean = (GenderBean) populateBean(request);

			try {
				model.add(bean);
				ServletUtility.setSuccessMessage("Gender added successfully", request);
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

			GenderBean bean = (GenderBean) populateBean(request);

			try {

				if (id > 0) {
					model.update(bean);
				}

				ServletUtility.setBean(bean, request);
				ServletUtility.setSuccessMessage("Gender Updated Successfully", request);

			} catch (DuplicateRecordException e) {

				ServletUtility.setBean(bean, request);
				ServletUtility.setErrorMessage("Gender Code already exists", request);

			} catch (ApplicationException e) {

				ServletUtility.handleException(e, request, response);
				return;
			}}
		 else if (OP_CANCEL.equalsIgnoreCase(op)) {
			ServletUtility.redirect(ORSView.GENDER_LIST_CTL, request, response);
			return;

		} else if (OP_RESET.equalsIgnoreCase(op)) {
			ServletUtility.redirect(ORSView.GENDER_CTL, request, response);
			return;
		}

		ServletUtility.forward(getView(), request, response);
	}

	@Override
	protected String getView() {
		return ORSView.GENDER_VIEW;
	}
}