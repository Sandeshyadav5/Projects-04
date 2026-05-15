package in.co.rays.proj4.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import in.co.rays.proj4.bean.BaseBean;
import in.co.rays.proj4.bean.WarrantyBean;
import in.co.rays.proj4.bean.WarrantyBean;
import in.co.rays.proj4.exception.ApplicationException;
import in.co.rays.proj4.model.CollegeModel;
import in.co.rays.proj4.model.WarrantyModel;
import in.co.rays.proj4.model.MarksheetModel;
import in.co.rays.proj4.util.DataUtility;
import in.co.rays.proj4.util.PropertyReader;
import in.co.rays.proj4.util.ServletUtility;

@WebServlet(name = "WarrantyListCtl", urlPatterns = { "/ctl/WarrantyListCtl" })
public class WarrantyListCtl extends BaseCtl {

@Override
protected void preload(HttpServletRequest request, HttpServletResponse response)
		throws IOException, ServletException {
	WarrantyModel model=new WarrantyModel();

	try {
		List nameList = model.list(0, 0);
		request.setAttribute("nameList", nameList);
	} catch (ApplicationException e) {
		e.printStackTrace();
		   ServletUtility.handleException(e, request, response);
	}
}
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


	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		int pageNo = 1;
		int pageSize = DataUtility.getInt(PropertyReader.getValue("page.size"));

		WarrantyBean bean = (WarrantyBean) populateBean(request);
		WarrantyModel model = new WarrantyModel();

		try {
			List<WarrantyBean> list = model.search(bean, pageNo, pageSize);
			List<WarrantyBean> next = model.search(bean, pageNo + 1, pageSize);

			if (list == null || list.isEmpty()) {
				ServletUtility.setErrorMessage("No record found", request);
			}

			ServletUtility.setList(list, request);
			ServletUtility.setPageNo(pageNo, request);
			ServletUtility.setPageSize(pageSize, request);
			ServletUtility.setBean(bean, request);
			request.setAttribute("nextListSize", next.size());

			ServletUtility.forward(getView(), request, response);

		} catch (ApplicationException e) {
			e.printStackTrace();
			   ServletUtility.handleException(e, request, response);
			return;
		}
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {


		List list = null;
		List next = null;

		int pageNo = DataUtility.getInt(request.getParameter("pageNo"));
		int pageSize = DataUtility.getInt(request.getParameter("pageSize"));

		pageNo = (pageNo == 0) ? 1 : pageNo;
		pageSize = (pageSize == 0) ? DataUtility.getInt(PropertyReader.getValue("page.size")) : pageSize;

		WarrantyBean bean = (WarrantyBean) populateBean(request);
		WarrantyModel model = new WarrantyModel();

		String op = DataUtility.getString(request.getParameter("operation"));
		String[] ids = request.getParameterValues("ids");

		try {

			if (OP_SEARCH.equalsIgnoreCase(op) || "Next".equalsIgnoreCase(op) || "Previous".equalsIgnoreCase(op)) {

				if (OP_SEARCH.equalsIgnoreCase(op)) {
					pageNo = 1;
				} else if (OP_NEXT.equalsIgnoreCase(op)) {
					pageNo++;
				} else if (OP_PREVIOUS.equalsIgnoreCase(op) && pageNo > 1) {
					pageNo--;
				}

			} else if (OP_NEW.equalsIgnoreCase(op)) {
				ServletUtility.redirect(ORSView.WARRANTY_CTL, request, response);
				return;

			} else if (OP_DELETE.equalsIgnoreCase(op)) {
				pageNo = 1;
				if (ids != null && ids.length > 0) {
					WarrantyBean deletebean = new WarrantyBean();
					for (String id : ids) {
						deletebean.setId(DataUtility.getInt(id));
						model.delete(deletebean);
						ServletUtility.setSuccessMessage("deleted successfully", request);
					}
				} else {
					ServletUtility.setErrorMessage("Select at least one record", request);
				}

			} else if (OP_RESET.equalsIgnoreCase(op)) {
				ServletUtility.redirect(ORSView.WARRANTY_LIST_CTL, request, response);
				return;

			} else if (OP_BACK.equalsIgnoreCase(op)) {
				ServletUtility.redirect(ORSView.WARRANTY_LIST_CTL, request, response);
				return;
			}

			list = model.search(bean, pageNo, pageSize);
			next = model.search(bean, pageNo + 1, pageSize);

			if (list == null || list.size() == 0) {
				ServletUtility.setErrorMessage("No record found ", request);
			}

			ServletUtility.setList(list, request);
			ServletUtility.setPageNo(pageNo, request);
			ServletUtility.setPageSize(pageSize, request);
			ServletUtility.setBean(bean, request);
			request.setAttribute("nextListSize", next.size());

			ServletUtility.forward(getView(), request, response);
		} catch (ApplicationException e) {
			e.printStackTrace();
			   ServletUtility.handleException(e, request, response);
			return;
		}
	}

	@Override
	protected String getView() {
		return ORSView.WARRANTY_LIST_VIEW;
	}
}