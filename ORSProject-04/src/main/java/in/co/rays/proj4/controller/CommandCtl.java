package in.co.rays.proj4.controller;
import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import in.co.rays.proj4.bean.BaseBean;
import in.co.rays.proj4.bean.CommandBean;
import in.co.rays.proj4.exception.ApplicationException;
import in.co.rays.proj4.exception.DuplicateRecordException;
import in.co.rays.proj4.model.CommandModel;
import in.co.rays.proj4.util.DataUtility;
import in.co.rays.proj4.util.DataValidator;
import in.co.rays.proj4.util.PropertyReader;
import in.co.rays.proj4.util.ServletUtility;

@WebServlet(name = "CommandCtl", urlPatterns = { "/ctl/CommandCtl" })
public class CommandCtl extends BaseCtl {

	@Override
	protected void preload(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		CommandModel model = new CommandModel();

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

		CommandBean bean = new CommandBean();

		bean.setId(DataUtility.getLong(request.getParameter("id")));
		bean.setCommandTest(DataUtility.getString(request.getParameter("commandTest")));
		bean.setDeviceTarget(DataUtility.getString(request.getParameter("deviceTarget")));
		bean.setExecutionTime(DataUtility.getDate(request.getParameter("executionTime")));
		bean.setStatus(DataUtility.getString(request.getParameter("status")));

		populateDTO(bean, request);

		return bean;
	}

	@Override
	protected boolean validate(HttpServletRequest request) {

		boolean pass = true;

		if (DataValidator.isNull(request.getParameter("commandTest"))) {
			request.setAttribute("commandTest",
					PropertyReader.getValue("error.require", "Command Test"));
			pass = false;
		}

		if (DataValidator.isNull(request.getParameter("deviceTarget"))) {
			request.setAttribute("deviceTarget",
					PropertyReader.getValue("error.require", "Device Target"));
			pass = false;
		}

		if (DataValidator.isNull(request.getParameter("executionTime"))) {

			request.setAttribute("executionTime",
					PropertyReader.getValue("error.require", "Execution Time"));
			pass = false;

		} else if (!DataValidator.isDate(request.getParameter("executionTime"))) {

			request.setAttribute("executionTime",
					"Execution Time is invalid");
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
		CommandModel model = new CommandModel();

		if (id > 0) {
			try {
				CommandBean bean = model.findByPk(id);
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
		CommandModel model = new CommandModel();
		long id = DataUtility.getLong(request.getParameter("id"));

		if (OP_SAVE.equalsIgnoreCase(op)) {

			CommandBean bean = (CommandBean) populateBean(request);

			try {
				model.add(bean);
				ServletUtility.setSuccessMessage("Command added successfully", request);
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

			CommandBean bean = (CommandBean) populateBean(request);

			try {

				if (id > 0) {
					model.update(bean);
				}

				ServletUtility.setBean(bean, request);
				ServletUtility.setSuccessMessage("Command Updated Successfully", request);

			} catch (DuplicateRecordException e) {

				ServletUtility.setBean(bean, request);
				ServletUtility.setErrorMessage("Command Test already exists", request);

			} catch (ApplicationException e) {

				ServletUtility.handleException(e, request, response);
				return;
			}}
		 else if (OP_CANCEL.equalsIgnoreCase(op)) {
			ServletUtility.redirect(ORSView.COMMAND_LIST_CTL, request, response);
			return;

		} else if (OP_RESET.equalsIgnoreCase(op)) {
			ServletUtility.redirect(ORSView.COMMAND_CTL, request, response);
			return;
		}

		ServletUtility.forward(getView(), request, response);
	}

	@Override
	protected String getView() {
		return ORSView.COMMAND_VIEW;
	}
}