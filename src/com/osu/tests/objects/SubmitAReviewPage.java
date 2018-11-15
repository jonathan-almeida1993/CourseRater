package com.osu.tests.objects;

public class SubmitAReviewPage {

	public static final String createReviewBtn = "//button[@id='createReviewBtn']";
	
	public static final String submitAReviewHeader = "//h5[.='Submitting a Review']";
	
	public static final String nameLabel = "//label[.='Name:']";
	public static final String nameTxtBox = "//input[@id='studentName']";
	
	public static final String termTakenLabelRV = "//label[.='*Term Taken:']";
	public static final String termTakenDropdownRV = "//select[@id='termDropdownRV']";

	public static final String instructorLabelRV = "//label[.='*Instructor:']";
	public static final String instructorDropdownRV = "//select[@id='instructorDropdownRV']";
	
	public static final String gradeReceivedLabel = "//label[.='Grade Received:']";
	public static final String gradeReceivedDropdown = "//select[@id='gradeDropdown']";
	
	public static final String ratingLabel = "//label[.='*Rating:']";
	public static final String thirdRatingStar = "(//img[contains(@id, 'ratingStarChk')]/..)[3]";
	
	public static final String yourReviewLabel = "//label[.='Your Review:']";
	public static final String yourReviewTxtBox = "//textarea[@id='reviewText']";
	
	public static final String submitReviewBtn =  "//button[text()='Submit Review' and @id='submitReviewBtn']";
	public static final String closeBtn =  "//button[text()='Close']";
	
	public static final String reviewSubmitConfirmationTxt = "//div[@id='submitSuccessAlert']";
	public static final String reviewSubmitConfirmartionOkBtn = "//button[@id='dismissSubmitSuccessAlert']";

	public static final String submitAnonymouslyChkbox = "//input[@id='anonymousCheck']//following-sibling::label";
	public static final String anonymousReviewBoxMessage = "//small[@id='anonymousMsg' and .='Your review will appear as \"Anonymous\".']";
	public static final String submittingReviewTxt = "//div[@id='submitPendingAlert' and contains(.,'Submitting review...')]";
	
	public static final String closeConfirmationText = "//span[.='You have an unfinished review. Are you sure you want to close the form? Your progress will be lost.']";
	public static final String yesCloseConfirmationText = "//button[@id='yesCloseReviewFormBtn']";
	public static final String noCloseConfirmationText = "//button[@id='noCloseReviewFormBtn']";

	public static final String fillReviewFormAlert = "//div[@id='fillFormAlertRV']";

	public static final String newReviewGradeSpan = "//span[@id='newReviewGrade']";

}
