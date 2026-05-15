package in.co.rays.proj4.controller;
import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import in.co.rays.proj4.bean.BaseBean;
import in.co.rays.proj4.bean.SourceBean;
import in.co.rays.proj4.exception.ApplicationException;
import in.co.rays.proj4.exception.DuplicateRecordException;
import in.co.rays.proj4.model.SourceModel;
import in.co.rays.proj4.util.DataUtility;
import in.co.rays.proj4.util.DataValidator;
import in.co.rays.proj4.util.PropertyReader;
import in.co.rays.proj4.util.ServletUtility;

@WebServlet(name = "SourceCtl", urlPatterns = { "/ctl/SourceCtl" })
public class SourceCtl extends BaseCtl {

	@Override
	protected void preload(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		SourceModel model = new SourceModel();

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

		SourceBean bean = new SourceBean();

		bean.setId(DataUtility.getLong(request.getParameter("id")));
		bean.setSourceCode(DataUtility.getString(request.getParameter("sourceCode")));
		bean.setSourceName(DataUtility.getString(request.getParameter("sourceName")));
		bean.setConnectionType(DataUtility.getString(request.getParameter("connectionType")));
		bean.setStatus(DataUtility.getString(request.getParameter("status")));

		populateDTO(bean, request);

		return bean;
	}

	@Override
	protected boolean validate(HttpServletRequest request) {

		boolean pass = true;

		if (DataValidator.isNull(request.getParameter("sourceCode"))) {
			request.setAttribute("sourceCode", PropertyReader.getValue("error.require", "Source Code"));
			pass = false;
		}

		if (DataValidator.isNull(request.getParameter("sourceName"))) {
			request.setAttribute("sourceName", PropertyReader.getValue("error.require", "Source Name"));
			pass = false;
		}
		if (DataValidator.isNull(request.getParameter("connectionType"))) {
			request.setAttribute("connectionType", PropertyReader.getValue("error.require", "Source Name"));
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
		SourceModel model = new SourceModel();

		if (id > 0) {
			try {
				SourceBean bean = model.findByPk(id);
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
		SourceModel model = new SourceModel();
		long id = DataUtility.getLong(request.getParameter("id"));

		if (OP_SAVE.equalsIgnoreCase(op)) {

			SourceBean bean = (SourceBean) populateBean(request);

			try {
				model.add(bean);
				ServletUtility.setSuccessMessage("Source added successfully", request);
				ServletUtility.forward(getView(), request, response);
				
				return;

			} catch (DuplicateRecordException e) {
				ServletUtility.setBean(bean, request);
				ServletUtility.setErrorMessage("Record already exists", request);

			} catch (ApplicationException e) {
				ServletUtility.handleException(e, request, response);
				return;
			}

		} else if (OP_UPDATE.equalsIgnoreCase(op)) {

			SourceBean bean = (SourceBean) populateBean(request);

			try {
				if (id > 0) {
					model.update(bean);
				}

				ServletUtility.setBean(bean, request);
				ServletUtility.setSuccessMessage("Source Updated Successfully", request);

			} catch (Exception e) {
				ServletUtility.handleException(e, request, response);
				return;
			}

		} else if (OP_CANCEL.equalsIgnoreCase(op)) {
			ServletUtility.redirect(ORSView.SOURCE_LIST_CTL, request, response);
			return;

		} else if (OP_RESET.equalsIgnoreCase(op)) {
			ServletUtility.redirect(ORSView.SOURCE_CTL, request, response);
			return;
		}

		ServletUtility.forward(getView(), request, response);
	}

	@Override
	protected String getView() {
		return ORSView.SOURCE_VIEW;
	}
}