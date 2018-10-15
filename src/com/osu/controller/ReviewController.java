package com.osu.controller;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.google.gson.Gson;
import com.osu.common.constants.CommonConstants;
import com.osu.dao.base.impl.CourseDAOImpl;
import com.osu.dao.base.impl.ReviewDAOImpl;
import com.osu.dao.base.interfaces.CourseDAO;
import com.osu.dao.base.interfaces.ReviewDAO;
import com.osu.database.pojo.CoursePojo;
import com.osu.database.pojo.ReviewPojo;


public class ReviewController extends HttpServlet {
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		String message = null;
		String jsonData = null;
		message = request.getParameter("message");
		jsonData = request.getParameter("JDATA");
		ReviewDAO dao = null;

		System.out.println("ReviewController:doPost @@@@@ message :: " + message + " jdata :: " + jsonData);
		
		if (null != message && CommonConstants.OP_ADD_REVIEW.equalsIgnoreCase(message)) {
			
			Gson gson = new Gson();
			dao = new ReviewDAOImpl();
			ReviewPojo reviewObj = gson.fromJson(jsonData, ReviewPojo.class);
			HttpSession session = request.getSession(false);
			reviewObj.setOnid((String)session.getAttribute("user"));
			System.out.println(reviewObj.isAnonymous());
			String status = dao.insertReview(reviewObj);
			response.getWriter().write(status);

		}
		System.out.println("ReviewController:doPost Exiting...");
	}
}


