package com.osu.controller;

import java.io.IOException;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.osu.common.constants.CommonConstants;
import com.osu.dao.base.impl.ReviewDAOImpl;
import com.osu.dao.base.interfaces.ReviewDAO;
import com.osu.database.pojo.ReviewPojo;


@WebServlet("/UnitTestVoting")
public class UnitTestVoting extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
		Gson gson = new Gson();
		HashMap<String, String> unitTestResults = new HashMap<String, String>();
		String status = testInsertVote();
		unitTestResults.put("INSERT_VOTE", status);
		status = testInsertUserVoteMapping();
		unitTestResults.put("INSERT_USER_VOTE_MAPPING", status);
		status = testUpdateUserVoteMapping();
		unitTestResults.put("UPDATE_USER_VOTE_MAPPING", status);
		response.getWriter().write(gson.toJson(unitTestResults));
	}
	
	public static String testInsertVote() {
		String status = "FAILED";
		ReviewDAO dao = new ReviewDAOImpl();
		ReviewPojo reviewPojo = new ReviewPojo();
		reviewPojo.setOnid("almeidaj");
		reviewPojo.setReviewId(1);
		reviewPojo.setThumbsUp(10);
		reviewPojo.setThumbsDown(7);
		if(dao.insertVote(reviewPojo).equals(CommonConstants.STATUS_JDBC_OK)) {
			status = "PASSED";
		}
		return status;
	}
	
	public static String testInsertUserVoteMapping() {
		String status = "FAILED";
		ReviewDAO dao = new ReviewDAOImpl();
		ReviewPojo reviewPojo = new ReviewPojo();
		reviewPojo.setOnid("almeidaj");
		reviewPojo.setReviewId(1);
		reviewPojo.setThumbsUp(10);
		reviewPojo.setThumbsDown(7);
		reviewPojo.setThumb(1);
		if(dao.insertUserVoteMapping(reviewPojo).equals(CommonConstants.STATUS_JDBC_OK)) {
			status = "PASSED";
		}
		return status;
	}
	
	public static String testUpdateUserVoteMapping() {
		String status = "FAILED";
		ReviewDAO dao = new ReviewDAOImpl();
		ReviewPojo reviewPojo = new ReviewPojo();
		reviewPojo.setOnid("almeidaj");
		reviewPojo.setReviewId(1);
		reviewPojo.setThumbsUp(10);
		reviewPojo.setThumbsDown(7);
		reviewPojo.setThumb(1);
		if(dao.updateUserVoteMapping(reviewPojo).equals(CommonConstants.STATUS_JDBC_OK)) {
			status = "PASSED";
		}
		return status;
	}
	

}
