package com.osu.database.pojo;

import java.util.ArrayList;

public class ResponsePojo extends BasePojo {

	private String userName;
	private String anonymous;
	private int questionId;
	private String responseText;
	private int rating;
	private String optionR;// RADIO TYPE QUEST RESPONSE
	private String optionR2;// RADIO2 TYPE QUEST RESPONSE
	private String optionRT;// RADIO_TEXTBOX TYPE QUEST RESPONSE
	private ArrayList<SubQuestResponsePojo> questionOptions;

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getAnonymous() {
		return anonymous;
	}

	public void setAnonymous(String anonymous) {
		this.anonymous = anonymous;
	}

	public int getQuestionId() {
		return questionId;
	}

	public void setQuestionId(int questionId) {
		this.questionId = questionId;
	}

	public String getResponseText() {
		return responseText;
	}

	public void setResponseText(String responseText) {
		this.responseText = responseText;
	}

	public int getRating() {
		return rating;
	}

	public void setRating(int rating) {
		this.rating = rating;
	}

	public String getOptionR() {
		return optionR;
	}

	public void setOptionR(String optionR) {
		this.optionR = optionR;
	}

	public String getOptionRT() {
		return optionRT;
	}

	public void setOptionRT(String optionRT) {
		this.optionRT = optionRT;
	}

	public ArrayList<SubQuestResponsePojo> getQuestionOptions() {
		return questionOptions;
	}

	public void setQuestionOptions(ArrayList<SubQuestResponsePojo> questionOptions) {
		this.questionOptions = questionOptions;
	}

	public String getOptionR2() {
		return optionR2;
	}

	public void setOptionR2(String optionR2) {
		this.optionR2 = optionR2;
	}

}
/*
------->{userName=Jonathan Almeida, anonymous=Y, questionId=1, questionOptions=[{questionOptionId=1, rating=1, responseText=}, {questionOptionId=2, rating=1, responseText=}, {questionOptionId=3, rating=1, responseText=}, {questionOptionId=4, rating=1, responseText=}, {questionOptionId=5, rating=1, responseText=}, {questionOptionId=6, rating=1, responseText=}, {questionOptionId=7, rating=1, responseText=}]}
------->{userName=Jonathan Almeida, anonymous=Y, questionId=2, questionOptions=[{questionOptionId=8, rating=1, responseText=}, {questionOptionId=9, rating=1, responseText=}, {questionOptionId=10, rating=1, responseText=}, {questionOptionId=11, rating=1, responseText=}, {questionOptionId=12, rating=1, responseText=}, {questionOptionId=13, rating=1, responseText=}, {questionOptionId=14, rating=1, responseText=}]}
------->{userName=Jonathan Almeida, anonymous=Y, questionId=3, responseText=}
------->{userName=Jonathan Almeida, anonymous=Y, questionId=4, optionR=~ 100 (101 was just right !)}
------->{userName=Jonathan Almeida, anonymous=Y, questionId=5, optionRT=We should revert to the original Bluechip only, responseText=sdfgh}
------->{userName=Jonathan Almeida, anonymous=Y, questionId=6, responseText=}
------->{userName=Jonathan Almeida, anonymous=Y, questionId=7, responseText=}
------->{userName=Jonathan Almeida, anonymous=Y, questionId=8, responseText=}
------->{userName=Jonathan, anonymous=Y, questionId=9, rating=1}

 */
