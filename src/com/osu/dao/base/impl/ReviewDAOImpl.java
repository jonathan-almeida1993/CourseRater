package com.osu.dao.base.impl;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.naming.NamingException;

import com.osu.common.constants.CommonConstants;
import com.osu.common.constants.SqlConstants;
import com.osu.dao.base.interfaces.CourseDAO;
import com.osu.dao.base.interfaces.ReviewDAO;
import com.osu.database.connection.DBConnectionFactory;
import com.osu.database.pojo.CoursePojo;
import com.osu.database.pojo.ReviewPojo;


public class ReviewDAOImpl implements ReviewDAO {

	Connection connection = null;

	@Override
	public Connection getConnection() throws SQLException, FileNotFoundException, ClassNotFoundException, IOException, NamingException {
		if ((connection == null) || (connection.isClosed())) {
			connection = DBConnectionFactory.getConnection();
		}
		return connection;
	}


	public String insertReview(ReviewPojo obj) {

		String status = CommonConstants.STATUS_JDBC_ERROR;
		Connection connect = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		
		try {
			connect = getConnection();
			preparedStatement = connect.prepareStatement(SqlConstants.INSERT_REVIEW);
			preparedStatement.setInt(1, obj.getCourseId());
			preparedStatement.setString(2, obj.getOnid());
			preparedStatement.setInt(3, obj.getRating());
			preparedStatement.setString(4, obj.getReview());
			preparedStatement.setString(5, obj.getGradeReceived());
			preparedStatement.setBoolean(6, obj.isAnonymous());
			preparedStatement.setDate(7, obj.getCreatedDate());

			int executeUpdate = preparedStatement.executeUpdate();

			if (executeUpdate > 0) {
				status = CommonConstants.STATUS_JDBC_OK;
			}

		} catch (Exception e) {
			status = CommonConstants.STATUS_JDBC_ERROR;
			e.printStackTrace();
		} finally {
			DBConnectionFactory.close(resultSet, preparedStatement, connect);
		}
		return status;
	}
	
	public ArrayList<ReviewPojo> fetchMyReviews(String onid){
		
		Connection conn = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		
		ArrayList<ReviewPojo> reviewList = new ArrayList<ReviewPojo>();
		
		//course_id , onid, rating, review, grade_received, anonymous
		try {
			conn = getConnection();
			preparedStatement = conn.prepareStatement(SqlConstants.GET_MY_REVIEWS);
			preparedStatement.setString(1, onid);
			resultSet = preparedStatement.executeQuery();
			while(resultSet.next()) {
				ReviewPojo singleReview = new ReviewPojo();
				singleReview.setCourseId(resultSet.getInt("course_id"));
				singleReview.setOnid(resultSet.getString("onid"));
				singleReview.setRating(resultSet.getInt("rating"));
				singleReview.setReview(resultSet.getString("review"));
				singleReview.setGradeReceived(resultSet.getString("grade_received"));
				singleReview.setAnonymous(resultSet.getBoolean("anonynous"));
				reviewList.add(singleReview);
			}
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			DBConnectionFactory.close(resultSet, preparedStatement, conn);
		}
		
		return reviewList;
	} 
	
	public ArrayList<ReviewPojo> fetchCourseReviews(int courseId){
		
		Connection conn = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		
		ArrayList<ReviewPojo> reviewList = new ArrayList<ReviewPojo>();
		
		//course_id , onid, rating, review, grade_received, anonymous
		try {
			conn = getConnection();
			preparedStatement = conn.prepareStatement(SqlConstants.GET_COURSE_REVIEWS);
			preparedStatement.setInt(1, courseId);
			resultSet = preparedStatement.executeQuery();
			while(resultSet.next()) {
				ReviewPojo singleReview = new ReviewPojo();
				singleReview.setCourseId(resultSet.getInt("course_id"));
				singleReview.setOnid(resultSet.getString("onid"));
				singleReview.setRating(resultSet.getInt("rating"));
				singleReview.setReview(resultSet.getString("review"));
				singleReview.setGradeReceived(resultSet.getString("grade_received"));
				singleReview.setAnonymous(resultSet.getBoolean("anonynous"));
				reviewList.add(singleReview);
			}
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			DBConnectionFactory.close(resultSet, preparedStatement, conn);
		}
		
		return reviewList;
	}

}