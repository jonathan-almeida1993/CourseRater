package com.osu.common.constants;

public interface SqlConstants {

	String AUTHENTICATE_USER = "SELECT onid, first_name, last_name, type, class_standing, major, created_date "+
			"FROM user_master_tbl where onid = ? and password = ?";
	
	String GET_DEPARTMENTS = "SELECT DISTINCT department from course_master_tbl";
	
	String GET_COURSES = "SELECT DISTINCT course_number, course_name from course_master_tbl where department = ?";

	String GET_TERM_AND_INSTRUCTOR = "SELECT course_id, term_offered, instructor from course_master_tbl where department = ? and course_number = ?";
	
	/*String GET_TERM_PLUS_INSTRUCTOR = "SELECT term_offered from course_master_tbl where department = ? and course_number = ? and instructor = ?";
	
	String GET_INSTRUCTOR = "SELECT instructor from course_master_tbl "+
							"where department = ? and course_number = ? and term_offered = ?";
	
	String GET_INSTRUCTOR_NO_TERM = "SELECT instructor from course_master_tbl where department = ? and course_number = ?";*/
	
	String GET_COURSE_ID = "SELECT course_id from course_master_tbl " + 
			"where department = ? and course_number = ? and term_offered = ? and instructor = ?";
	
	String INSERT_REVIEW = "INSERT into review_master_tbl "+
			"(course_id, onid, rating, review, grade_received, anonymous, created_date) "+
			"values (?,?,?,?,?,?,?)";
	
	String GET_COURSE_DETAILS = "SELECT course_id, course_name, department,  course_number, term_offered, instructor, course_desc from course_master_tbl " + 
			"where course_id = ?";
	
	String GET_MY_REVIEWS = "SELECT R.review_id, R.course_id , R.onid, R.rating, R.review, R.grade_received, R.created_date, R.anonymous, C.* "+
			"FROM review_master_tbl R, course_master_tbl C WHERE onid= ? AND R.course_id = C.course_id ORDER BY R.created_date DESC";

	String GET_COURSE_REVIEWS = "SELECT R.review_id, R.course_id , R.onid, U.first_name, U.last_name, R.rating, R.review, R.grade_received, R.upvotes, R.downvotes, R.created_date, R.anonymous "+
			"FROM review_master_tbl R, user_master_tbl U WHERE (R.course_id = ?) AND (R.onid = U.onid) ORDER BY R.created_date DESC";
	
	String INSERT_VOTE = "UPDATE review_master_tbl SET upvotes = ?, downvotes = ? WHERE review_id = ?";

	String DELETE_REVIEW = "DELETE FROM review_master_tbl WHERE review_id = ?";
	String FETCH_MY_VOTES = "SELECT review_id, vote FROM user_review_vote_mapping WHERE onid=?";

	String INSERT_VOTE_USER_MAPPING = "INSERT INTO user_review_vote_mapping (onid, review_id, vote) VALUES (?,?,?)";

	String UPDATE_VOTE_USER_MAPPING = "UPDATE user_review_vote_mapping SET vote = ? WHERE onid = ? AND review_id = ?";
	
	String SELECT_VOTE_USER_MAPPING = "SELECT map_id, onid, review_id, vote FROM user_review_vote_mapping WHERE onid = ? AND review_id = ?";

}