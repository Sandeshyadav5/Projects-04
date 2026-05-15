


package in.co.rays.proj4.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import in.co.rays.proj4.bean.BaseBean;
import in.co.rays.proj4.bean.RoleBean;
import in.co.rays.proj4.bean.UserBean;
import in.co.rays.proj4.model.RoleModel;
import in.co.rays.proj4.model.UserModel;
import in.co.rays.proj4.util.DataUtility;
import in.co.rays.proj4.util.DataValidator;
import in.co.rays.proj4.util.PropertyReader;
import in.co.rays.proj4.util.ServletUtility;

/**
 * LoginCtl handles user authentication and session management.
 * It supports operations like Sign In, Sign Up, and Logout.
 * 
 * URL Mapping: /LoginCtl
 * 
 * This controller validates login credentials, authenticates users,
 * manages session, and redirects to appropriate views.
 * 
 * @author 
 */
@WebServlet(name = "LoginCtl", urlPatterns = { "/LoginCtl" })
public class LoginCtl extends BaseCtl {
	private static final Logger log = Logger.getLogger(LoginCtl.class);

    /** Operation for registration redirect */
    public static final String OP_REGISTER = "Register";

    /** Operation for login (sign in) */
    public static final String OP_SIGN_IN = "Sign In";

    /** Operation for signup */
    public static final String OP_SIGN_UP = "Sign Up";

    /** Operation for logout */
    public static final String OP_LOG_OUT = "Logout";

    /**
     * Validates login form input.
     * Skips validation for Sign Up and Logout operations.
     * 
     * @param request HttpServletRequest
     * @return true if valid, false otherwise
     */
    @Override
    protected boolean validate(HttpServletRequest request) {

        boolean pass = true;

        String op = request.getParameter("operation");

        // Skip validation for signup and logout
        if (OP_SIGN_UP.equals(op) || OP_LOG_OUT.equals(op)) {
            return pass;
        }

        if (DataValidator.isNull(request.getParameter("login"))) {
            request.setAttribute("login", PropertyReader.getValue("error.require", "Login Id"));
            pass = false;
        } else if (!DataValidator.isEmail(request.getParameter("login"))) {
            request.setAttribute("login", PropertyReader.getValue("error.email", "Login "));
            pass = false;
        }

        if (DataValidator.isNull(request.getParameter("password"))) {
            request.setAttribute("password", PropertyReader.getValue("error.require", "Password"));
            pass = false;
        }

        return pass;
    }

    /**
     * Populates UserBean from request parameters.
     * 
     * @param request HttpServletRequest
     * @return populated UserBean
     */
    @Override
    protected BaseBean populateBean(HttpServletRequest request) {
        UserBean bean = new UserBean();
        bean.setId(DataUtility.getLong(request.getParameter("id")));
        bean.setLogin(DataUtility.getString(request.getParameter("login")));
        bean.setPassword(DataUtility.getString(request.getParameter("password")));
        return bean;
    }

    /**
     * Handles HTTP GET request.
     * Used for logout operation and loading login page.
     * 
     * @param request  HttpServletRequest
     * @param response HttpServletResponse
     * @throws ServletException
     * @throws IOException
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();

        String op = DataUtility.getString(request.getParameter("operation"));

        // Logout operation
        if (OP_LOG_OUT.equals(op)) {
            session.invalidate();
            ServletUtility.setSuccessMessage("Logout Successful!", request);
            ServletUtility.forward(getView(), request, response);
            return;
        }

        ServletUtility.forward(getView(), request, response);
    }

    /**
     * Handles HTTP POST request.
     * Performs Sign In and Sign Up operations.
     * 
     * @param request  HttpServletRequest
     * @param response HttpServletResponse
     * @throws ServletException
     * @throws IOException
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();

        String op = DataUtility.getString(request.getParameter("operation"));

        UserModel model = new UserModel();
        RoleModel role = new RoleModel();

        /**
         * Sign In operation
         */
        if (OP_SIGN_IN.equalsIgnoreCase(op)) {

            UserBean bean = (UserBean) populateBean(request);

            try {
                bean = model.authenticate(bean.getLogin(), bean.getPassword());

                if (bean != null) {

                    // Set user in session
                    session.setAttribute("user", bean);

                    // Fetch and set role
                    RoleBean rolebean = role.findByPk(bean.getRoleId());
                    if (rolebean != null) {
                        session.setAttribute("role", rolebean.getName());
                    }

                    ServletUtility.redirect(ORSView.WELCOME_CTL, request, response);
                    return;

                } else {
                    bean = (UserBean) populateBean(request);
                    ServletUtility.setBean(bean, request);
                    ServletUtility.setErrorMessage("Invalid LoginId And Password", request);
                }

            } catch (Exception e) {
                e.printStackTrace();
                ServletUtility.handleException(e, request, response);
                return;
            }

        } 
        /**
         * Sign Up operation (redirect to registration)
         */
        else if (OP_SIGN_UP.equalsIgnoreCase(op)) {
            ServletUtility.redirect(ORSView.USER_REGISTRATION_CTL, request, response);
            return;
        }

        ServletUtility.forward(getView(), request, response);
    }

    /**
     * Returns the login view page.
     * 
     * @return view path
     */
    @Override
    protected String getView() {
        return ORSView.LOGIN_VIEW;
    }
}