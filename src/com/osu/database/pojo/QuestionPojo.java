package com.osu.database.pojo;

import java.util.ArrayList;

public class QuestionPojo extends BasePojo {

	private int questionId;
	private int serialNo;
	private String questionType;
	private String questionText;
	private ArrayList<QuestionOptionsPojo> questionOptions;

	public int getQuestionId() {
		return questionId;
	}

	public void setQuestionId(int questionId) {
		this.questionId = questionId;
	}

	public int getSerialNo() {
		return serialNo;
	}

	public void setSerialNo(int serialNo) {
		this.serialNo = serialNo;
	}

	public String getQuestionType() {
		return questionType;
	}

	public void setQuestionType(String questionType) {
		this.questionType = questionType;
	}

	public String getQuestionText() {
		return questionText;
	}

	public void setQuestionText(String questionText) {
		this.questionText = questionText;
	}

	public ArrayList<QuestionOptionsPojo> getQuestionOptions() {
		return questionOptions;
	}

	public void setQuestionOptions(ArrayList<QuestionOptionsPojo> questionOptions) {
		this.questionOptions = questionOptions;
	}

}
