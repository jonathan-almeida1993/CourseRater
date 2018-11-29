package com.osu.controller;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.google.gson.Gson;
import com.mysql.cj.Session;
import com.osu.common.constants.CommonConstants;
import com.osu.dao.base.impl.CourseDAOImpl;
import com.osu.dao.base.impl.ReviewDAOImpl;
import com.osu.dao.base.interfaces.CourseDAO;
import com.osu.dao.base.interfaces.ReviewDAO;
import com.osu.database.pojo.CoursePojo;
import com.osu.database.pojo.ReviewPojo;
import com.osu.database.pojo.UserPojo;


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
            if(session != null) {
                reviewObj.setOnid((String)session.getAttribute("user"));
                System.out.println(reviewObj.isAnonymous());
                String status = dao.insertReview(reviewObj);
                response.getWriter().write(status);
            }else {
                response.getWriter().write("INVALID_SESSION");
            }
        }else if(null != message && CommonConstants.OP_GET_MY_REVIEWS.equalsIgnoreCase(message)) {
			
			Gson gson = new Gson();
			dao = new ReviewDAOImpl();
			String onid = null;
			HttpSession session = request.getSession(false);
			if(session != null) {
				onid = (String)session.getAttribute("user");
				ArrayList<ReviewPojo> myReviews = dao.fetchMyReviews(onid);
				response.getWriter().write(gson.toJson(myReviews));
			}else {
				response.getWriter().write("INVALID_SESSION");
			}

		}else if(null != message && CommonConstants.OP_GET_COURSE_REVIEWS.equalsIgnoreCase(message)) {
			
			Gson gson = new Gson();
			dao = new ReviewDAOImpl();
			CoursePojo coursePojo = gson.fromJson(jsonData, CoursePojo.class);
			ArrayList<ReviewPojo> courseReviews = dao.fetchCourseReviews(coursePojo.getCourseId());
			response.getWriter().write(gson.toJson(courseReviews));
		}else if(null != message && CommonConstants.OP_ADD_VOTE.equalsIgnoreCase(message)) {
			
			Gson gson = new Gson();
			dao = new ReviewDAOImpl();
			ReviewPojo reviewPojo = gson.fromJson(jsonData, ReviewPojo.class);
			System.out.println(reviewPojo.getOnid());
			System.out.println(reviewPojo.getReviewId());
			System.out.println(reviewPojo.getThumbsUp());
			System.out.println(reviewPojo.getThumbsDown());
			System.out.println(reviewPojo.getThumb());
			String status = dao.insertVote(reviewPojo);
			
			if(status.equals(CommonConstants.STATUS_JDBC_OK)) {
				System.out.println("Insertion of vote count was successful");
				ArrayList<ReviewPojo> userVoteMapping = dao.selectUserVoteMapping(reviewPojo);
				if(userVoteMapping.size()==0) {
					System.out.println("User Vote Mapping does not exist");
					status = dao.insertUserVoteMapping(reviewPojo);
					if(status.equals(CommonConstants.STATUS_JDBC_OK)) 
						System.out.println("User Vote Mapping inserted successfully");
				}else {
					System.out.println("User Vote Mapping exists");
					status = dao.updateUserVoteMapping(reviewPojo);
					if(status.equals(CommonConstants.STATUS_JDBC_OK)) 
						System.out.println("User Vote Mapping updated successfully");
				}
			}
			response.getWriter().write(status);
		}else if(null != message && CommonConstants.OP_GET_USER_THUMBS.equalsIgnoreCase(message)) {
			
			Gson gson = new Gson();
			dao = new ReviewDAOImpl();
			UserPojo userPojo = gson.fromJson(jsonData, UserPojo.class);
			ArrayList<ReviewPojo> myVotes = dao.fetchMyVotes(userPojo);
			response.getWriter().write(gson.toJson(myVotes));
		}
		System.out.println("ReviewController:doPost Exiting...");
	}
}


