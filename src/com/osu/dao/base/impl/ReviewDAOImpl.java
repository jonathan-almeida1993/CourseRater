package com.osu.dao.base.impl;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
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
import com.osu.database.pojo.UserPojo;


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
			Date dt = new Date(obj.getDatePosted());
			preparedStatement = connect.prepareStatement(SqlConstants.INSERT_REVIEW);
			preparedStatement.setInt(1, obj.getCourseId());
			preparedStatement.setString(2, obj.getOnid());
			preparedStatement.setInt(3, obj.getRating());
			preparedStatement.setString(4, obj.getReview());
			preparedStatement.setString(5, obj.getGradeReceived());
			preparedStatement.setBoolean(6, obj.isAnonymous());
			preparedStatement.setDate(7, dt);

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
				singleReview.setReviewId(resultSet.getInt("review_id"));
				singleReview.setCourseId(resultSet.getInt("course_id"));
				singleReview.setOnid(resultSet.getString("onid"));
				singleReview.setRating(resultSet.getInt("rating"));
				singleReview.setReview(resultSet.getString("review"));
				singleReview.setGradeReceived(resultSet.getString("grade_received"));
				singleReview.setDatePosted(resultSet.getDate("created_date").getTime());
				singleReview.setAnonymous(resultSet.getBoolean("anonymous"));
				singleReview.setInstructor(resultSet.getString("instructor"));
				singleReview.setDepartment(resultSet.getString("department"));
				singleReview.setTermOffered(resultSet.getString("term_offered"));
				singleReview.setCourseNo(resultSet.getInt("course_number"));

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

		//review_id, course_id , onid, first_name, last_name, rating, review, created_date, grade_received, anonymous
		try {
			conn = getConnection();
			preparedStatement = conn.prepareStatement(SqlConstants.GET_COURSE_REVIEWS);
			preparedStatement.setInt(1, courseId);
			resultSet = preparedStatement.executeQuery();
			while(resultSet.next()) {
				ReviewPojo singleReview = new ReviewPojo();
				singleReview.setReviewId(resultSet.getInt("review_id"));
				singleReview.setCourseId(resultSet.getInt("course_id"));
				singleReview.setOnid(resultSet.getString("onid"));
				singleReview.setFirstName(resultSet.getString("first_name"));
				singleReview.setLastName(resultSet.getString("last_name"));
				singleReview.setRating(resultSet.getInt("rating"));
				singleReview.setReview(resultSet.getString("review"));
				//singleReview.setDatePosted(resultSet.getDate("created_date"));
				singleReview.setGradeReceived(resultSet.getString("grade_received"));
				singleReview.setDatePosted(resultSet.getDate("created_date").getTime());
				singleReview.setAnonymous(resultSet.getBoolean("anonymous"));
				singleReview.setThumbsUp(resultSet.getInt("upvotes"));
				singleReview.setThumbsDown(resultSet.getInt("downvotes"));
				reviewList.add(singleReview);
			}
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			DBConnectionFactory.close(resultSet, preparedStatement, conn);
		}

		return reviewList;
	}
	
	public String insertVote(ReviewPojo obj) {

		String status = CommonConstants.STATUS_JDBC_ERROR;
		Connection connect = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;

		try {
			connect = getConnection();
			preparedStatement = connect.prepareStatement(SqlConstants.INSERT_VOTE);
			preparedStatement.setInt(1, obj.getThumbsUp());
			preparedStatement.setInt(2, obj.getThumbsDown());
			preparedStatement.setInt(3, obj.getReviewId());

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
	
	public String insertUserVoteMapping(ReviewPojo obj) {

		String status = CommonConstants.STATUS_JDBC_ERROR;
		Connection connect = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;

		try {
			connect = getConnection();
			preparedStatement = connect.prepareStatement(SqlConstants.INSERT_VOTE_USER_MAPPING);
			preparedStatement.setString(1, obj.getOnid());
			preparedStatement.setInt(2, obj.getReviewId());
			preparedStatement.setInt(3, obj.getThumb());

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
	
	public String updateUserVoteMapping(ReviewPojo obj) {

		String status = CommonConstants.STATUS_JDBC_ERROR;
		Connection connect = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;

		try {
			connect = getConnection();
			preparedStatement = connect.prepareStatement(SqlConstants.UPDATE_VOTE_USER_MAPPING);
			preparedStatement.setInt(1, obj.getThumb());
			preparedStatement.setString(2, obj.getOnid());
			preparedStatement.setInt(3, obj.getReviewId());

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
	
	public ArrayList<ReviewPojo> selectUserVoteMapping(ReviewPojo obj) {

		String status = CommonConstants.STATUS_JDBC_ERROR;
		Connection connect = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		ArrayList<ReviewPojo> userVoteMappingList = new ArrayList<ReviewPojo>();
		
		try {
			connect = getConnection();
			preparedStatement = connect.prepareStatement(SqlConstants.SELECT_VOTE_USER_MAPPING);
			preparedStatement.setString(1, obj.getOnid());
			preparedStatement.setInt(2, obj.getReviewId());

			resultSet = preparedStatement.executeQuery();
			
			while(resultSet.next()) {
				ReviewPojo singleVoteInfo = new ReviewPojo();
				singleVoteInfo.setReviewId(resultSet.getInt("review_id"));
				singleVoteInfo.setOnid(resultSet.getString("onid"));
				userVoteMappingList.add(singleVoteInfo);
			}
			status = CommonConstants.STATUS_JDBC_OK;
		} catch (Exception e) {
			status = CommonConstants.STATUS_JDBC_ERROR;
			e.printStackTrace();
		} finally {
			DBConnectionFactory.close(resultSet, preparedStatement, connect);
		}
		return userVoteMappingList;
	}

	public ArrayList<ReviewPojo> fetchMyVotes(UserPojo obj) {

		String status = CommonConstants.STATUS_JDBC_ERROR;
		Connection connect = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		ArrayList<ReviewPojo> voteList = new ArrayList<ReviewPojo>();
		
		try {
			connect = getConnection();
			preparedStatement = connect.prepareStatement(SqlConstants.FETCH_MY_VOTES);
			preparedStatement.setString(1, obj.getOnid());

			resultSet = preparedStatement.executeQuery();
			while(resultSet.next()) {
				ReviewPojo singleVoteInfo = new ReviewPojo();
				singleVoteInfo.setReviewId(resultSet.getInt("review_id"));
				singleVoteInfo.setThumb(resultSet.getInt("vote"));
				singleVoteInfo.setOnid(obj.getOnid());
				voteList.add(singleVoteInfo);
			}
			status = CommonConstants.STATUS_JDBC_OK;
		} catch (Exception e) {
			status = CommonConstants.STATUS_JDBC_ERROR;
			e.printStackTrace();
		} finally {
			DBConnectionFactory.close(resultSet, preparedStatement, connect);
		}
		return voteList;
	}
}
