package com.osu.dao.base.interfaces;

import com.osu.dao.base.GenericDAO;
import com.osu.database.pojo.ReviewPojo;

public interface ReviewDAO extends GenericDAO {
	
	public String insertReview(ReviewPojo obj);
	
}
