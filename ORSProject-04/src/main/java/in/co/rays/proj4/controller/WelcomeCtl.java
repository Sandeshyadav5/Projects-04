package in.co.rays.proj4.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import in.co.rays.proj4.util.ServletUtility;

/**
 * WelcomeCtl is a Controller class responsible for handling
 * the Welcome (Home) page of the application.
 * 
 * It simply forwards the request to the Welcome view.
 * No business logic or data processing is performed here.
 * 
 * This acts as the entry point or landing page of the system.
 * 
 * @author 
 */
@WebServlet(name = "WelcomeCtl", urlPatterns = { "/WelcomeCtl" })
public class WelcomeCtl extends BaseCtl {
	private static final Logger log = Logger.getLogger(WelcomeCtl.class);

	/**
	 * Handles GET request.
	 * Forwards the request to the Welcome view page.
	 * 
	 * @param request  HttpServletRequest object
	 * @param response HttpServletResponse object
	 * @throws ServletException if servlet error occurs
	 * @throws IOException      if I/O error occurs
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		ServletUtility.forward(getView(), request, response);
	}

	/**
	 * Returns Welcome view page.
	 * 
	 * @return view path
	 */
	@Override
	protected String getView() {
		return ORSView.WELCOME_VIEW;
	}
}