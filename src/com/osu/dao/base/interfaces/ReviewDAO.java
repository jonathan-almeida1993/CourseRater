package com.osu.dao.base.interfaces;

import java.util.ArrayList;

import com.osu.dao.base.GenericDAO;
import com.osu.database.pojo.CoursePojo;
import com.osu.database.pojo.ReviewPojo;

public interface ReviewDAO extends GenericDAO {
	
	public String insertReview(ReviewPojo obj);
	
	public ArrayList<ReviewPojo> fetchCourseReviews(int courseId);
	
	public ArrayList<ReviewPojo> fetchMyReviews(String onid);

	public String deleteReview(int reviewId);
}
