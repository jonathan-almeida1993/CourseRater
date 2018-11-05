package com.osu.tests.objects;

public class LoginPage {

	/*OSU_LOGO, COURSE_RATER_HEADER, LOGIN_HEADER, USERNAME_TXTBOX, PASSWORD_TXTBOX, LOGIN_BTN;

	public String getXpath() {
		String xpath = null;
		switch(this) {
		case OSU_LOGO:
			xpath = osuLogo;
			break;
		case COURSE_RATER_HEADER:
			xpath =courseRaterHeader;
			break;
		case LOGIN_HEADER:
			xpath = loginHeader;
			break;
		case USERNAME_TXTBOX:
			xpath = username;
			break;
		case PASSWORD_TXTBOX:
			xpath = password;
			break;
		case LOGIN_BTN:
			xpath = loginBtn;
			break;
		}
		return xpath;
	}*/

	public static final String osuLogo = "//img[@class='login-logo' and @alt='OSU Logo']";
	public static final String courseRaterHeader = "//span[text()='Course Rater']";
	public static final String logoutConfirmationText = "//h6[.='You have been successfully logged out of your account.']";
	public static final String loginSiteName = "//span[@class='login-sitename']";
	public static final String username = "//input[@id='onidUsername']";
	public static final String password = "//input[@id='onidPassword']";
	public static final String loginBtn = "//button[@id='loginBtn']";

}
