package com.osu.dao.base.interfaces;

import java.util.ArrayList; 

import com.osu.dao.base.GenericDAO;
import com.osu.database.pojo.QuestionPojo;
import com.osu.database.pojo.ResponsePojo;

public interface CodeDataDAO extends GenericDAO {
	
	public ArrayList<QuestionPojo> getFeedbackQuestions() throws Exception;
	
	public boolean insertResponse(ArrayList<ResponsePojo> userResponseList) throws Exception ;
}
