package com.osu.tests.objects;

public class ViewCoursePage {

	public static final String courseRaterHeader = "//p[text()='Course Rater']";

	public static final String searchCourseForm = "//form[@id='searchCourseFormCP']";
	public static final String subjectLabel = "//label[text()='*Subject']";
	public static final String subjectDropdown = "//select[@id='subjectDropDownCP']";
	public static final String courseNumberLabel = "//label[text()='*Course Number']";
	public static final String courseEnabledDropdown = "//select[@id='courseDropDownCP']";
	public static final String courseDisabledDropdown = "//select[@id='courseDropDownCP' and @disabled]";
	public static final String termLabel = "//label[text()='Term']";
	public static final String termEnabledDropdown = "//select[@id='termDropDownCP']";
	public static final String termDisabledDropdown = "//select[@id='termDropDownCP' and @disabled]";
	public static final String professorLabel = "//label[text()='Professor/Instructor']";
	public static final String professorEnabledDropdown = "//select[@id='instructorDropDownCP']";
	public static final String professorDisabledDropdown = "//select[@id='instructorDropDownCP' and @disabled]";
	public static final String requiredFieldLabel = "//small[contains(.,'*Required Field')]";
	public static final String searchBtn = "//button[text()='Search']";
	public static final String fillFormAlert = "//div[@id='fillFormAlertCP']";

	public static final String subjectHeader = "//h3[@class='section-header']";
	public static final String profName = "//h4[@id='courseInstructorHeader']";
	public static final String descriptionLabel = "//h4[.='Description']";
	public static final String descriptionTxt = "//p[@id='courseDesc']";
	public static final String reviewsLabel = "//h4[.='Reviews']";

	public static final String createReviewBtn = "//button[@id='createReviewBtn']";
	public static final String reviewFormYourName = "//input[@id='studentName']";
	public static final String anonymousCheckbox = "//input[@id='anonymousCheck']//following-sibling::label";
	public static final String anonymousReviewBoxMessage = "//small[@id='anonymousMsg' and .='Your review will appear as \"Anonymous\".']";
	public static final String gradeReceivedDropdown = "//select[@id='gradeDropdown']";
	public static final String ratingCheckbox = "//span[@id='ratingCheckbox']";
	public static final String ratingLabel = "//label[.='*Rating:']";
	public static final String thirdRatingStar = "(//img[contains(@id, 'ratingStarChk')]/..)[3]";
	public static final String reviewText = "//textarea[@id='reviewText']";
	public static final String submitReviewBtn = "//button[@id='submitReviewBtn']";
	public static final String fillReviewFormAlert = "//div[@id='fillFormAlertRV']";

	public static final String newReview = "//div[@id='newReview']";
	public static final String newReviewName = "//span[@id='newReviewName']";

	public static final String courseNumberHeader = "//h3[@id='courseNameHeader']";
	
}
