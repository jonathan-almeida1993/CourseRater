package com.osu.common.constants;

public interface CommonConstants {

	String OP_VALIDATE_lOGIN = "validateLogin";
	String OP_LOGOUT = "logout";
	String STATUS_JDBC_ERROR = "JDBC_Error";
	String STATUS_JDBC_OK = "JDBC_OK";
	String STATUS_AUTH_FAILED = "AuthenticationFailed";
	String STATUS_AUTH_SUCCESS = "AuthenticationSuccess";
	
	String OP_FETCH_DEPT = "fetchDepartments";
	String OP_FETCH_COURSENO = "fetchCourseNo";
	String OP_FETCH_TERM_INSTR = "fetchTermInstr";
	String OP_FETCH_INSTRUCTOR = "fetchInstructor";
	String OP_FETCH_COURSEID = "fetchCourseID";
	String OP_FETCH_COURSE_DETAILS = "fetchCourseDetails";
	String OP_ADD_REVIEW = "addReview";
	String OP_GET_MY_REVIEWS = "getMyReviews";
	String OP_GET_COURSE_REVIEWS = "getCourseReviews";
	String OP_ADD_VOTE = "addVote";
}
