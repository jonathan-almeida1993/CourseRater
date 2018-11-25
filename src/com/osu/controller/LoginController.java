package com.osu.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.osu.common.constants.CommonConstants;
import com.osu.dao.base.impl.LoginDAOImpl;
import com.osu.dao.base.interfaces.LoginDAO;
import com.osu.database.pojo.UserPojo;

public class LoginController extends HttpServlet {

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		String message = null;
		String jsonData = null;
		message = request.getParameter("message");
		jsonData = request.getParameter("JDATA");

		LoginDAO dao = null;
		System.out.println("\n\nLoginController:doPost @@@@@ message :: " + message + " jsonData :: " + jsonData);

		if (null != message && CommonConstants.OP_VALIDATE_lOGIN.equalsIgnoreCase(message)) {

			UserPojo loginDetails = new UserPojo();
			dao = new LoginDAOImpl();
			loginDetails.setOnid(request.getParameter("onid"));
			loginDetails.setPassword(request.getParameter("password"));

			System.out.println("ONID = " + loginDetails.getOnid());
			System.out.println("password = " + loginDetails.getPassword());
			System.out.println("VALIDATING USER LOGIN...");

			UserPojo userDetails = dao.validateUserLogin(loginDetails);

			if (CommonConstants.STATUS_AUTH_SUCCESS.equals(userDetails.getStatus())) {
				HttpSession session = request.getSession();
				// setting session to expiry in 30 mins
				session.setMaxInactiveInterval(30 * 60);
				session.setAttribute("user", loginDetails.getOnid());
				Cookie userName = new Cookie("user", userDetails.getFirstName() + "-" + userDetails.getLastName());
				Cookie userOnid = new Cookie("onid", loginDetails.getOnid());
				userName.setMaxAge(30 * 60);
				response.addCookie(userName);
				response.addCookie(userOnid);
				response.sendRedirect("index.html");

			} else {
				RequestDispatcher rd = getServletContext().getRequestDispatcher("/login.html");
				PrintWriter out = response.getWriter();
				// out.println("<font color=red>Either user name or password is wrong.</font>");
				out.println("<div class=\"alert alert-danger\" role=\"alert\"> "
						+ "Invalid Username or Password. Please try again.</div>");
				rd.include(request, response);
			}
		} else if (null != message && CommonConstants.OP_LOGOUT.equalsIgnoreCase(message)) {
			HttpSession session = request.getSession(false);
			if (session != null) {
				session.invalidate();
			}
			response.sendRedirect("login.html");
		}
		System.out.println("LoginController:doPost Exiting...");
	}

}
