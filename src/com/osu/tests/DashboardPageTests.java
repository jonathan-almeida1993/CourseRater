package com.osu.tests;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.osu.tests.objects.DashboardPage;
import com.osu.tests.objects.LoginPage;
import com.osu.tests.support.SeleniumUtils;

public class DashboardPageTests extends SeleniumUtils{

	@Test(description="Verify the header on Dashboard page")
	public void dashboardPageTest1() {
		login();

		Assert.assertTrue(isElementAvailable(Locator.XPATH, DashboardPage.osuLogo, "'OSU' logo", true), "OSU logo hasn't been displayed as expected on the Dashboard page.");
		Assert.assertTrue(isElementAvailable(Locator.XPATH, DashboardPage.courseRaterHeader, "'Course Rater' header", true), "'Course Rater' header hasn't been displayed as expected on the Dashboard page.");
	}

	@Test(description="Verify if the subject dropdowns is enabled while other dropdowns are disabled by default")
	public void dashboardPageTest2() {
		login();

		Assert.assertTrue(isElementClickable(Locator.XPATH, DashboardPage.subjectDropdown, "'Subject' dropdown", true), "Subject dropdown is not enabled as expected.");
		Assert.assertTrue(!isElementClickable(Locator.XPATH, DashboardPage.courseDisabledDropdown, "'Course' dropdown", true), "Course Number dropdown is not disabled as expected.");
		Assert.assertTrue(!isElementClickable(Locator.XPATH, DashboardPage.termDisabledDropdown, "'Term' dropdown", true), "Term dropdown is not disabled as expected.");
		Assert.assertTrue(!isElementClickable(Locator.XPATH, DashboardPage.professorDisabledDropdown, "'Professor' dropdown", true), "Professor/Instructor dropdown is not disabled as expected.");
	}

	@Test(description="Verify if 'Find a course' section has all mandatory elements")
	public void dashboardPageTest3() {
		login();

		Assert.assertTrue(isElementAvailable(Locator.XPATH, DashboardPage.findACourseHeader, "'Find a Course' header", true), "'Find a course' header isn't displayed as expected.");
		Assert.assertTrue(isElementAvailable(Locator.XPATH, DashboardPage.subjectLabel, "'Subject' label", true), "'Subject' label isn't displayed as expected.");
		Assert.assertTrue(isElementAvailable(Locator.XPATH, DashboardPage.courseNumberLabel, "'Course' label", true), "'Course Number' label isn't displayed as expected.");
		Assert.assertTrue(isElementAvailable(Locator.XPATH, DashboardPage.termLabel, "'Term' label", true), "'Term' label isn't displayed as expected.");
		Assert.assertTrue(isElementAvailable(Locator.XPATH, DashboardPage.professorLabel, "'Professor' label", true), "'Professor' label isn't displayed as expected.");
		Assert.assertTrue(isElementAvailable(Locator.XPATH, DashboardPage.requiredFieldLabel, "'Required' label", true), "'* Required Fields' label isn't displayed as expected.");
		Assert.assertTrue(isElementAvailable(Locator.XPATH, DashboardPage.searchBtn, "'Submit' button", true), "'Submit' button isn't displayed as expected.");
	}

	@Test(description="Verify if 'Your recent reviews' section has the date, course, rating and action header.", enabled = false)
	public void dashboardPageTest4() {
		//launchPage(ConfigurationProperties.getProperty("DashboardURL"));
		login();

		Assert.assertTrue(isElementAvailable(Locator.XPATH, DashboardPage.yourRecentReviewsLabel, "'Your Recent Reviews' label", true), "'Your Recent Reviews' header isn't displayed as expected.");
		Assert.assertTrue(isElementAvailable(Locator.XPATH, DashboardPage.dateHeaderLabel, "'Date' header label", true), "'Date' label isn't displayed as expected.");
		Assert.assertTrue(isElementAvailable(Locator.XPATH, DashboardPage.courseHeaderLabel, "'Course' header label", true), "'Course' label isn't displayed as expected.");
		Assert.assertTrue(isElementAvailable(Locator.XPATH, DashboardPage.ratingHeaderLabel, "'Rating' header label", true), "'Rating' label isn't displayed as expected.");
		Assert.assertTrue(isElementAvailable(Locator.XPATH, DashboardPage.actionHeaderLabel, "'Action' header label", true), "'Action' label isn't displayed as expected.");
	}

	@Test(description="Verify if reviews are displayed in the 'Your Recent Reviews' table", enabled = false)
	public void dashboardPageTest5() {
		login();

		int numOfRows = getElements(Locator.XPATH, "//table[@id='recentReviewsTable']//tbody//td[@scope='row']").size();

		for(int i=1; i<=numOfRows; i++) {
			Assert.assertTrue(isElementAvailable(Locator.XPATH, "//table[@id='recentReviewsTable']//tbody//td[@id='recentDate"+i+"']", "'Date for row '+i", true), "Recent date for row "+i+" is not displayed as expected.");
			Assert.assertTrue(isElementAvailable(Locator.XPATH, "//table[@id='recentReviewsTable']//tbody//td[@id='recentCourse"+i+"']", "Course for row "+i, true), "Recent course for row "+i+" is not displayed as expected.");
			Assert.assertTrue(isElementAvailable(Locator.XPATH, "//table[@id='recentReviewsTable']//tbody//td[@id='recentRating"+i+"']", "Rating for row "+i, true), "Recent rating for row "+i+" is not displayed as expected.");
			//TODO - Fix the following xpath to reflect 'View Actions' element
			Assert.assertTrue(isElementAvailable(Locator.XPATH, "//table[@id='recentReviewsTable']//tbody//td[@id='recentDate"+i+"']", "View Actions for row "+i, true), "Recent View Action button for row "+i+" is not displayed as expected.");			
		}
	}

	@Test(description = "Verify that the user is logged out by clicking on 'Log out' button")
	public void dashboardPageTest6() {
		login();

		if(isElementAvailable(Locator.XPATH, DashboardPage.courseRaterHeader, "'Course Rater' header", true)){
			Assert.assertTrue(isElementAvailable(Locator.XPATH, DashboardPage.logoutBtn, "Logout button", true), "Log out button");
			click(Locator.XPATH, DashboardPage.logoutBtn, "'Logout' button", true);

			Assert.assertTrue(isElementAvailable(Locator.XPATH, LoginPage.logoutConfirmationText, "Logout confirmation text", true), "User is not logged out or logout confirmation text has not been displayed as expected.");
		}
	}

	/*@Test(description="Verify that user is navigated to the dashboard page when the user clicks on Course Rater header")
	public void test7(){
		launchPage(ConfigurationProperties.getProperty("DashboardURL"));
		
		Assert.assertTrue(isElementAvailable(Locator.XPATH, DashboardPage.osuLogo), arg1);
	}

	@Test(description="Verify that user is navigated to the OSU page when the user clicks on Oregon State University header")
	public void test8(){
		launchPage(ConfigurationProperties.getProperty("DashboardURL"));
	}*/

}
