package com.osu.tests.objects;

public class DashboardPage {

	public static final String osuLogo = "//img[@src='images/OSU_horizontal_1C_B.png']";
	public static final String courseRaterHeader = "//p[text()='Course Rater']";
	public static final String homeBtnLink = "//a[@id='index-link']";
	public static final String yourReviewsLink = "//a[@id='your-reviews-link']";
	public static final String logoutBtn = "//a[contains(@class,'btn-logout')]";
	
	public static final String findACourseHeader = "//h3[text()='Find a Course']";
	public static final String subjectLabel = "//label[text()='*Subject']";
	public static final String subjectDropdown = "//select[@id='subjectDropDown']";
	public static final String courseNumberLabel = "//label[text()='*Course Number']";
	public static final String courseEnabledDropdown = "//select[@id='courseDropDown']";
	public static final String courseDisabledDropdown = "//select[@id='courseDropDown' and @disabled]";
	public static final String termLabel = "//label[text()='Term']";
	public static final String termEnabledDropdown = "//select[@id='termDropDown']";
	public static final String termDisabledDropdown = "//select[@id='termDropDown' and @disabled]";
	public static final String professorLabel = "//label[text()='Professor/Instructor']";
	public static final String professorEnabledDropdown = "//select[@id='instructorDropDown']";
	public static final String professorDisabledDropdown = "//select[@id='instructorDropDown' and @disabled]";
	public static final String requiredFieldLabel = "//small[contains(.,'*Required Field')]";
	public static final String searchBtn = "//button[text()='Search']";
	public static final String fillFormAlert = "//div[@id='fillFormAlert']";
	
	public static final String yourRecentReviewsLabel = "//h3[text()='Your Recent Reviews']";
	public static final String dateHeaderLabel = "//table[@id='recentReviewTable']//th[.='Date']";
	public static final String courseHeaderLabel = "//table[@id='recentReviewTable']//th[.='Course']";
	public static final String ratingHeaderLabel = "//table[@id='recentReviewTable']//th[.='Rating']";
	public static final String actionHeaderLabel = "//table[@id='recentReviewTable']//th[.='Action']";
	public static final String recentReviewTableBody = "//table[@id='recentReviewTable']//tbody";

	public static final String yourReviewModalName = "//input[@id='yourNameYV']";
	public static final String yourReviewAnonymousCheck = "//input[@id='anonymousCheckYV']";
	public static final String seeMoreReviewsBtn = "//button[@id='seeMoreReviewsBtn']";
	public static final String noConfirmDeleteReviewBtn = "//button[@id='noConfirmDeleteReviewBtn']";
	public static final String yesConfirmDeleteReviewBtn = "//button[@id='yesConfirmDeleteReviewBtn']";
	
}
