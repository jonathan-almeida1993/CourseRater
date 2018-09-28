package com.osu.database.pojo;

public class SubQuestResponsePojo extends BasePojo {
	private int questionOptionId;
	private int rating;
	private String responseText;

	public int getQuestionOptionId() {
		return questionOptionId;
	}

	public void setQuestionOptionId(int questionOptionId) {
		this.questionOptionId = questionOptionId;
	}

	public int getRating() {
		return rating;
	}

	public void setRating(int rating) {
		this.rating = rating;
	}

	public String getResponseText() {
		return responseText;
	}

	public void setResponseText(String responseText) {
		this.responseText = responseText;
	}
}
/*
------->{userName=Jonathan Almeida, anonymous=Y, questionId=1, questionOptions=[{questionOptionId=1, rating=1, responseText=}, {questionOptionId=2, rating=1, responseText=}, {questionOptionId=3, rating=1, responseText=}, {questionOptionId=4, rating=1, responseText=}, {questionOptionId=5, rating=1, responseText=}, {questionOptionId=6, rating=1, responseText=}, {questionOptionId=7, rating=1, responseText=}]}
------->{userName=Jonathan Almeida, anonymous=Y, questionId=2, questionOptions=[{questionOptionId=8, rating=1, responseText=}, {questionOptionId=9, rating=1, responseText=}, {questionOptionId=10, rating=1, responseText=}, {questionOptionId=11, rating=1, responseText=}, {questionOptionId=12, rating=1, responseText=}, {questionOptionId=13, rating=1, responseText=}, {questionOptionId=14, rating=1, responseText=}]}
*/
