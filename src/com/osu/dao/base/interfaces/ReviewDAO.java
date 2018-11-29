package com.osu.dao.base.interfaces;

import java.util.ArrayList; 

import com.osu.dao.base.GenericDAO;
import com.osu.database.pojo.CoursePojo;
import com.osu.database.pojo.ReviewPojo;
import com.osu.database.pojo.UserPojo;

public interface ReviewDAO extends GenericDAO {
	
	public String insertReview(ReviewPojo obj);
	
	public ArrayList<ReviewPojo> fetchCourseReviews(int courseId);
	
	public ArrayList<ReviewPojo> fetchMyReviews(String onid);

	public String deleteReview(int reviewId);
	
	public String insertVote(ReviewPojo obj);

	public ArrayList<ReviewPojo> fetchMyVotes(UserPojo obj);
	
	public String insertUserVoteMapping(ReviewPojo obj);
	
	public String updateUserVoteMapping(ReviewPojo obj);

	public ArrayList<ReviewPojo> selectUserVoteMapping(ReviewPojo obj);
}
