package com.osu.tests.objects;

public class SubmitAReviewPage {

	public static final String createReviewBtn = "//button[@id='createReviewBtn']";
	
	public static final String submitAReviewHeader = "//h5[.='Submitting a Review']";
	
	public static final String nameLabel = "//label[.='Name']";
	public static final String nameTxtBox = "//input[@id='studentName']";
	
	public static final String termTakenLabel = "//label[.='Term Taken']";
	public static final String termTakenTxtBox = "//input[@id='termTaken']";
	
	public static final String gradeReceivedLabel = "//label[.='Grade Received']";
	public static final String gradeReceivedDropdown = "//select[@id='gradeDropdown']";
	
	public static final String ratingLabel = "//label[.='Rating (required)']";
	public static final String ratingDropdown = "//select[@id='ratingDropdown']";
	
	public static final String yourReviewLabel = "//label[.='Your Review:']";
	public static final String yourReviewTxtBox = "//textarea[@id='reviewText']";
	
	public static final String submitReviewBtn =  "//button[text()='Submit Review' and @id='submitReviewBtn']";
	public static final String closeBtn =  "//button[text()='Close']";
	
	public static final String reviewSubmitConfirmationTxt = "//div[@id='submitSuccessAlert']";
	public static final String reviewSubmitConfirmartionOkBtn = "//button[@id='dismissSubmitSuccessAlert']";

	public static final String submitAnonymouslyChkbox = "//label[contains(text(),\"I'd like to submit my review anonymously.\")]";
}
