package com.osu.common.constants;

public interface SqlConstants {

	String AUTHENTICATE_USER = "SELECT onid, first_name, last_name, type, class_standing, major, created_date "+
			"FROM user_master_tbl where onid = ? and password = ?";
	
	String GET_DEPARTMENTS = "SELECT DISTINCT department from course_master_tbl";
	
	String GET_COURSES = "SELECT DISTINCT course_number from course_master_tbl where department = ?";

	String GET_TERM = "SELECT term_offered from course_master_tbl where department = ? and course_number = ?";
	
	String GET_INSTRUCTOR = "SELECT instructor from course_master_tbl "+
							"where department = ? and course_number = ? and term_offered = ?";
	
	String GET_COURSE_ID = "SELECT course_id from course_master_tbl " + 
			"where department = ? and course_number = ? and term_offered = ? and instructor = ?";
	
	String INSERT_REVIEW = "INSERT into review_master_tbl "+
			"(course_id, onid, rating, review, grade_received, anonymous, created_date) "+
			"values (?,?,?,?,?,?,?)";

}
