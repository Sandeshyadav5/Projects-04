package in.co.rays.proj4.controller;
import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import in.co.rays.proj4.bean.BaseBean;
import in.co.rays.proj4.bean.EmojiBean;
import in.co.rays.proj4.exception.ApplicationException;
import in.co.rays.proj4.exception.DuplicateRecordException;
import in.co.rays.proj4.model.EmojiModel;
import in.co.rays.proj4.util.DataUtility;
import in.co.rays.proj4.util.DataValidator;
import in.co.rays.proj4.util.PropertyReader;
import in.co.rays.proj4.util.ServletUtility;

@WebServlet(name = "EmojiCtl", urlPatterns = { "/ctl/EmojiCtl" })
public class EmojiCtl extends BaseCtl {

	@Override
	protected void preload(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		EmojiModel model = new EmojiModel();

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

		EmojiBean bean = new EmojiBean();

		bean.setId(DataUtility.getLong(request.getParameter("id")));
		bean.setReactionCode(DataUtility.getString(request.getParameter("reactionCode")));
		bean.setUserName(DataUtility.getString(request.getParameter("userName")));
		bean.setEmojiType(DataUtility.getString(request.getParameter("emojiType")));
		bean.setStatus(DataUtility.getString(request.getParameter("status")));

		populateDTO(bean, request);

		return bean;
	}

	@Override
	protected boolean validate(HttpServletRequest request) {

		boolean pass = true;

		if (DataValidator.isNull(request.getParameter("reactionCode"))) {
			request.setAttribute("reactionCode",
					PropertyReader.getValue("error.require", "Reaction Code"));
			pass = false;
		}

		if (DataValidator.isNull(request.getParameter("userName"))) {
			request.setAttribute("userName",
					PropertyReader.getValue("error.require", "User Name"));
			pass = false;
		}

		if (DataValidator.isNull(request.getParameter("emojiType"))) {

			request.setAttribute("emojiType",
					PropertyReader.getValue("error.require", "Emoji Type"));
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
		EmojiModel model = new EmojiModel();

		if (id > 0) {
			try {
				EmojiBean bean = model.findByPk(id);
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
		EmojiModel model = new EmojiModel();
		long id = DataUtility.getLong(request.getParameter("id"));

		if (OP_SAVE.equalsIgnoreCase(op)) {

			EmojiBean bean = (EmojiBean) populateBean(request);

			try {
				model.add(bean);
				ServletUtility.setSuccessMessage("Emoji added successfully", request);
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

			EmojiBean bean = (EmojiBean) populateBean(request);

			try {

				if (id > 0) {
					model.update(bean);
				}

				ServletUtility.setBean(bean, request);
				ServletUtility.setSuccessMessage("Emoji Updated Successfully", request);

			} catch (DuplicateRecordException e) {

				ServletUtility.setBean(bean, request);
				ServletUtility.setErrorMessage("Emoji Code already exists", request);

			} catch (ApplicationException e) {

				ServletUtility.handleException(e, request, response);
				return;
			}}
		 else if (OP_CANCEL.equalsIgnoreCase(op)) {
			ServletUtility.redirect(ORSView.EMOJI_LIST_CTL, request, response);
			return;

		} else if (OP_RESET.equalsIgnoreCase(op)) {
			ServletUtility.redirect(ORSView.EMOJI_CTL, request, response);
			return;
		}

		ServletUtility.forward(getView(), request, response);
	}

	@Override
	protected String getView() {
		return ORSView.EMOJI_VIEW;
	}
}