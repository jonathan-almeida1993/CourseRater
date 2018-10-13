package com.osu.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.google.gson.Gson;
import com.osu.common.constants.CommonConstants;
import com.osu.dao.base.impl.CourseDAOImpl;
import com.osu.dao.base.interfaces.CourseDAO;
import com.osu.database.pojo.CoursePojo;


public class CourseController extends HttpServlet {
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		String message = null;
		String jsonData = null;
		message = request.getParameter("message");
		jsonData = request.getParameter("JDATA");
		CourseDAO dao = null;

		System.out.println("CourseController:doPost @@@@@ message :: " + message + " jdata :: " + jsonData);
		
		if (null != message && CommonConstants.OP_FETCH_DEPT.equalsIgnoreCase(message)) {

			Gson gson = new Gson();
			dao = new CourseDAOImpl();
			ArrayList<CoursePojo> deptList = dao.fetchDepartments();
			String jsonString = gson.toJson(deptList);
			response.getWriter().write(jsonString);

		}else if(null != message && CommonConstants.OP_FETCH_COURSENO.equalsIgnoreCase(message)) {
		
			Gson gson = new Gson();
			CoursePojo searchKey = gson.fromJson(jsonData, CoursePojo.class);
			dao = new CourseDAOImpl();
			ArrayList<CoursePojo> courseNoList = dao.fetchCourses(searchKey);
			String jsonString = gson.toJson(courseNoList);
			response.getWriter().write(jsonString);
		
		}else if(null != message && CommonConstants.OP_FETCH_TERM.equalsIgnoreCase(message)) {
			
			Gson gson = new Gson();
			CoursePojo searchKey = gson.fromJson(jsonData, CoursePojo.class);
			dao = new CourseDAOImpl();
			ArrayList<CoursePojo> termList = dao.fetchTerm(searchKey);
			String jsonString = gson.toJson(termList);
			response.getWriter().write(jsonString);
			
		}else if(null != message && CommonConstants.OP_FETCH_INSTRUCTOR.equalsIgnoreCase(message)) {
			
			Gson gson = new Gson();
			CoursePojo searchKey = gson.fromJson(jsonData, CoursePojo.class);
			dao = new CourseDAOImpl();
			ArrayList<CoursePojo> instructorList = dao.fetchInstructors(searchKey);
			String jsonString = gson.toJson(instructorList);
			response.getWriter().write(jsonString);
			
		}else if(null != message && CommonConstants.OP_FETCH_COURSEID.equalsIgnoreCase(message)) {
			
			Gson gson = new Gson();
			CoursePojo searchKey = gson.fromJson(jsonData, CoursePojo.class);
			dao = new CourseDAOImpl();
			ArrayList<CoursePojo> courseIdList = dao.fetchCourseId(searchKey);
			System.out.println(courseIdList.get(0).getCourseId());
			String jsonString = gson.toJson(courseIdList.get(0));
			response.getWriter().write(jsonString);
			
		}
		System.out.println("CourseController:doPost Exiting...");
	}
}


