package com.osu.database.pojo;

public class QuestionOptionsPojo extends BasePojo {

	private int questionOptionId;
	private int forQuestion;
	private int optionSerialNo;
	private String optionText;
	private String optionType;

	public String getOptionType() {
		return optionType;
	}

	public void setOptionType(String optionType) {
		this.optionType = optionType;
	}

	public int getQuestionOptionId() {
		return questionOptionId;
	}

	public void setQuestionOptionId(int questionOptionId) {
		this.questionOptionId = questionOptionId;
	}

	public int getForQuestion() {
		return forQuestion;
	}

	public void setForQuestion(int forQuestion) {
		this.forQuestion = forQuestion;
	}

	public int getOptionSerialNo() {
		return optionSerialNo;
	}

	public void setOptionSerialNo(int optionSerialNo) {
		this.optionSerialNo = optionSerialNo;
	}

	public String getOptionText() {
		return optionText;
	}

	public void setOptionText(String optionText) {
		this.optionText = optionText;
	}
}
