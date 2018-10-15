package com.osu.dao.base.impl;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.naming.NamingException;

import com.osu.common.constants.SqlConstants;
import com.osu.dao.base.interfaces.CourseDAO;
import com.osu.database.connection.DBConnectionFactory;
import com.osu.database.pojo.CoursePojo;


public class CourseDAOImpl implements CourseDAO {

	Connection connection = null;

	@Override
	public Connection getConnection() throws SQLException, FileNotFoundException, ClassNotFoundException, IOException, NamingException {
		if ((connection == null) || (connection.isClosed())) {
			connection = DBConnectionFactory.getConnection();
		}
		return connection;
	}


	public ArrayList<CoursePojo> fetchDepartments() {

		Connection connect = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		ArrayList<CoursePojo> deptList = new ArrayList<CoursePojo>();
		
		try {
			connect = getConnection();
			preparedStatement = connect.prepareStatement(SqlConstants.GET_DEPARTMENTS);
			resultSet = preparedStatement.executeQuery();
			
			while(resultSet.next()){
				CoursePojo singleDept = new CoursePojo();
				singleDept.setDepartment(resultSet.getString("department"));
				deptList.add(singleDept);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBConnectionFactory.close(resultSet, preparedStatement, connect);
		}
		return deptList;
	}

	public ArrayList<CoursePojo> fetchCourses(CoursePojo obj) {

		Connection connect = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		ArrayList<CoursePojo> courseList = new ArrayList<CoursePojo>();
		
		try {
			connect = getConnection();
			preparedStatement = connect.prepareStatement(SqlConstants.GET_COURSES);
			preparedStatement.setString(1, obj.getDepartment());
			resultSet = preparedStatement.executeQuery();
			
			while(resultSet.next()){
				CoursePojo singleCourse = new CoursePojo();
				singleCourse.setDepartment(obj.getDepartment());
				singleCourse.setCourseNo(resultSet.getInt("course_number"));
				courseList.add(singleCourse);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBConnectionFactory.close(resultSet, preparedStatement, connect);
		}
		return courseList;
	}
	
	public ArrayList<CoursePojo> fetchTerm(CoursePojo obj) {

		Connection connect = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		ArrayList<CoursePojo> termList = new ArrayList<CoursePojo>();
		
		try {
			connect = getConnection();
			preparedStatement = connect.prepareStatement(SqlConstants.GET_TERM);
			preparedStatement.setString(1, obj.getDepartment());
			preparedStatement.setInt(2, obj.getCourseNo());
			resultSet = preparedStatement.executeQuery();
			
			while(resultSet.next()){
				CoursePojo singleTerm = new CoursePojo();
				singleTerm.setDepartment(obj.getDepartment());
				singleTerm.setCourseNo(obj.getCourseNo());
				singleTerm.setTermOffered(resultSet.getString("term_offered"));
				termList.add(singleTerm);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBConnectionFactory.close(resultSet, preparedStatement, connect);
		}
		return termList;
	}
	
	public ArrayList<CoursePojo> fetchInstructors(CoursePojo obj) {

		Connection connect = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		ArrayList<CoursePojo> instructorList = new ArrayList<CoursePojo>();
		
		try {
			connect = getConnection();
			preparedStatement = connect.prepareStatement(SqlConstants.GET_INSTRUCTOR);
			preparedStatement.setString(1, obj.getDepartment());
			preparedStatement.setInt(2, obj.getCourseNo());
			preparedStatement.setString(3, obj.getTermOffered());
			resultSet = preparedStatement.executeQuery();
			
			while(resultSet.next()){
				CoursePojo singleInstructor = new CoursePojo();
				singleInstructor.setDepartment(obj.getDepartment());
				singleInstructor.setCourseNo(obj.getCourseNo());
				singleInstructor.setTermOffered(obj.getTermOffered());
				singleInstructor.setInstructor(resultSet.getString("instructor"));
				instructorList.add(singleInstructor);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBConnectionFactory.close(resultSet, preparedStatement, connect);
		}
		return instructorList;
	}
	
	public ArrayList<CoursePojo> fetchCourseId(CoursePojo obj) {

		Connection connect = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		ArrayList<CoursePojo> courseIdList = new ArrayList<CoursePojo>();
		
		try {
			connect = getConnection();
			preparedStatement = connect.prepareStatement(SqlConstants.GET_COURSE_ID);
			preparedStatement.setString(1, obj.getDepartment());
			preparedStatement.setInt(2, obj.getCourseNo());
			preparedStatement.setString(3, obj.getTermOffered());
			preparedStatement.setString(4, obj.getInstructor());
			resultSet = preparedStatement.executeQuery();
			
			while(resultSet.next()){
				CoursePojo singleCourseId = new CoursePojo();
				singleCourseId.setDepartment(obj.getDepartment());
				singleCourseId.setCourseNo(obj.getCourseNo());
				singleCourseId.setTermOffered(obj.getTermOffered());
				singleCourseId.setInstructor(obj.getInstructor());
				singleCourseId.setCourseId(resultSet.getInt("course_id"));
				courseIdList.add(singleCourseId);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBConnectionFactory.close(resultSet, preparedStatement, connect);
		}
		return courseIdList;
	}
}