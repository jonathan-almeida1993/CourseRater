package com.osu.controller;

import java.io.IOException; 
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.osu.common.constants.CommonConstants;
import com.osu.dao.base.impl.LoginDAOImpl;
import com.osu.dao.base.interfaces.LoginDAO;
import com.osu.database.pojo.UserPojo;


public class LoginController extends HttpServlet {

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
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
			
			System.out.println("ONID = "+loginDetails.getOnid());
			System.out.println("password = "+loginDetails.getPassword());
			System.out.println("VALIDATING USER LOGIN...");
			
			UserPojo userDetails = dao.validateUserLogin(loginDetails);
			
			
			
			if(CommonConstants.STATUS_AUTH_SUCCESS.equals(userDetails.getStatus())){

				response.sendRedirect("index.html");
			
			}else{
				response.sendRedirect("login.html?status="+userDetails.getStatus());
			}
		}
		System.out.println("LoginController:doPost Exiting...");
	}
		
}

