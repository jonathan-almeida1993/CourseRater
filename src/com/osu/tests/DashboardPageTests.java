package com.osu.tests;

import com.osu.tests.objects.ViewCoursePage;
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

	@Test(description="Verify if 'Your recent reviews' section has the date, course, rating and action header.")
	public void dashboardPageTest4() {
		//launchPage(ConfigurationProperties.getProperty("DashboardURL"));
		login();

		Assert.assertTrue(isElementAvailable(Locator.XPATH, DashboardPage.yourRecentReviewsLabel, "'Your Recent Reviews' label", true), "'Your Recent Reviews' header isn't displayed as expected.");
		Assert.assertTrue(isElementAvailable(Locator.XPATH, DashboardPage.dateHeaderLabel, "'Date' header label", true), "'Date' label isn't displayed as expected.");
		Assert.assertTrue(isElementAvailable(Locator.XPATH, DashboardPage.courseHeaderLabel, "'Course' header label", true), "'Course' label isn't displayed as expected.");
		Assert.assertTrue(isElementAvailable(Locator.XPATH, DashboardPage.ratingHeaderLabel, "'Rating' header label", true), "'Rating' label isn't displayed as expected.");
		//TODO - Uncomment this in next sprint
		//Assert.assertTrue(isElementAvailable(Locator.XPATH, DashboardPage.actionHeaderLabel, "'Action' header label", true), "'Action' label isn't displayed as expected.");
	}

	@Test(description="Verify if reviews are displayed in the 'Your Recent Reviews' table")
	public void dashboardPageTest5() {
		login();

		int numOfRows = getElements(Locator.XPATH, "//table[@id='recentReviewTable']//tbody//tr").size();

		for(int i=1; i<=numOfRows; i++) {
			Assert.assertTrue(isElementAvailable(Locator.XPATH, "(//table[@id='recentReviewTable']//tbody//td[1])["+i+"]", "'Date for row '+i", true), "Recent date for row "+i+" is not displayed as expected.");
			Assert.assertTrue(isElementAvailable(Locator.XPATH, "(//table[@id='recentReviewTable']//tbody//td[3])["+i+"]", "Course for row "+i, true), "Recent course for row "+i+" is not displayed as expected.");
			Assert.assertTrue(isElementAvailable(Locator.XPATH, "(//table[@id='recentReviewTable']//tbody//td[2]//img[@class='rating-star'])["+i+"]", "Rating for row "+i, true), "Recent rating for row "+i+" is not displayed as expected.");
			//TODO - Fix the following xpath to reflect 'View Actions' element
			//Assert.assertTrue(isElementAvailable(Locator.XPATH, "//table[@id='recentReviewTable']//tbody//td[@id='recentDate"+i+"']", "View Actions for row "+i, true), "Recent View Action button for row "+i+" is not displayed as expected.");			
		}
	}

	@Test(description = "Verify that the user is logged out by clicking on 'Log out' button")
	public void dashboardPageTest6() {
		login();

		if(isElementAvailable(Locator.XPATH, DashboardPage.courseRaterHeader, "'Course Rater' header", true)){
			Assert.assertTrue(isElementAvailable(Locator.XPATH, DashboardPage.logoutBtn, "Logout button", true), "Log out button");
			click(Locator.XPATH, DashboardPage.logoutBtn, "'Logout' button", true);
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			Assert.assertTrue(isElementAvailable(Locator.XPATH, LoginPage.logoutConfirmationText, "Logout confirmation text", true), "User is not logged out or logout confirmation text has not been displayed as expected.");

			Assert.assertTrue(isElementAvailable(Locator.XPATH, LoginPage.loginSiteName, "Login sitename", true), "User is redirected to the login page");
			Assert.assertTrue(isElementAvailable(Locator.XPATH, LoginPage.logoutConfirmationText, "Logout message", true), "User is shown successful logout message as expected.");
		}
	}

	@Test(description = "Verify that if no fields are filled in on Search click, the search fails and the page tells the user to select a subject")
	public void dashboardPageTest7() {
		login();

		if(isElementAvailable(Locator.XPATH, DashboardPage.findACourseHeader, "'Search for a Course' header", true)) {
			Assert.assertTrue(isElementAvailable(Locator.XPATH, DashboardPage.searchBtn, "Search button", true), "The Search button is visible on the page");
			Assert.assertTrue(isElementClickable(Locator.XPATH, DashboardPage.searchBtn, "Search button", true), "The Search button is clickable");
			Assert.assertFalse(getElement(Locator.XPATH, DashboardPage.subjectDropdown).isSelected(), "The subject dropdown is not selected");
			click(Locator.XPATH, DashboardPage.searchBtn, "Search button", true);
			Assert.assertTrue(getElement(Locator.XPATH, DashboardPage.fillFormAlert).getCssValue("display").equals("block"), "The Fill Form alert is enabled");
			Assert.assertTrue(getElement(Locator.XPATH, DashboardPage.fillFormAlert).getText().equals("Please select a subject!"), "The alert tells the user to select a subject.");
		}
	}

	@Test(description = "Verify that if only the subject is filled in on Search click, the search fails and the page tells the user to select a course number")
	public void dashboardPageTest8() {
		login();

		if(isElementAvailable(Locator.XPATH, DashboardPage.findACourseHeader, "'Search for a Course' header", true)) {
			Assert.assertTrue(isElementAvailable(Locator.XPATH, DashboardPage.searchBtn, "Search button", true), "The Search button is visible on the page");
			Assert.assertTrue(isElementClickable(Locator.XPATH, DashboardPage.searchBtn, "Search button", true), "The Search button is clickable");
			select(Locator.XPATH, DashboardPage.subjectDropdown, "Subject dropdown").selectByValue("Computer Science (CS)");
			click(Locator.XPATH, DashboardPage.searchBtn, "Search button", true);
			Assert.assertTrue(getElement(Locator.XPATH, DashboardPage.fillFormAlert).getCssValue("display").equals("block"), "The Fill Form alert is enabled");
			Assert.assertTrue(getElement(Locator.XPATH, DashboardPage.fillFormAlert).getText().equals("Please select a course number!"), "The alert tells the user to select a course number.");
		}
	}

	@Test(description = "Verify that if only the subject and course number fields are filled in on Search click, go to the course page and include all reviews for all terms and instructors for that course")
	public void dashboardPageTest9() {
		login();

		if(isElementAvailable(Locator.XPATH, DashboardPage.findACourseHeader, "'Search for a Course' header", true)) {
			Assert.assertTrue(isElementAvailable(Locator.XPATH, DashboardPage.searchBtn, "Search button", true), "The Search button is visible on the page");
			Assert.assertTrue(isElementClickable(Locator.XPATH, DashboardPage.searchBtn, "Search button", true), "The Search button is clickable");
			select(Locator.XPATH, DashboardPage.subjectDropdown, "Subject dropdown").selectByValue("Computer Science (CS)");
			select(Locator.XPATH, DashboardPage.courseEnabledDropdown, "Course dropdown").selectByValue("160");
			click(Locator.XPATH, DashboardPage.searchBtn, "Search button", true);
			Assert.assertTrue(isElementAvailable(Locator.XPATH, ViewCoursePage.subjectHeader, "Subject header", true), "The subject header for the Course Page is visible");
			Assert.assertTrue(getElement(Locator.XPATH, ViewCoursePage.subjectHeader).getText().contains("CS 160"), "The course header contains the correct course information");
		}
	}

	@Test(description = "Verify that if all fields except term are filled in on Search click, go to the course page and include all reviews for all instructors for that term for that course")
	public void dashboardPageTest10() {
		login();

		if(isElementAvailable(Locator.XPATH, DashboardPage.findACourseHeader, "'Search for a Course' header", true)) {
			Assert.assertTrue(isElementAvailable(Locator.XPATH, DashboardPage.searchBtn, "Search button", true), "The Search button is visible on the page");
			Assert.assertTrue(isElementClickable(Locator.XPATH, DashboardPage.searchBtn, "Search button", true), "The Search button is clickable");
			select(Locator.XPATH, DashboardPage.subjectDropdown, "Subject dropdown").selectByValue("Computer Science (CS)");
			select(Locator.XPATH, DashboardPage.courseEnabledDropdown, "Course dropdown").selectByValue("160");
			select(Locator.XPATH, DashboardPage.professorEnabledDropdown, "Instructor dropdown").selectByValue("Jennifer Parham-Mocello");
			click(Locator.XPATH, DashboardPage.searchBtn, "Search button", true);
			Assert.assertTrue(isElementAvailable(Locator.XPATH, ViewCoursePage.subjectHeader, "Subject header", true), "The subject header for the Course Page is visible");
			Assert.assertTrue(getElement(Locator.XPATH, ViewCoursePage.subjectHeader).getText().contains("CS 160"), "The course header contains the correct course information");
		}
	}

	@Test(description = "Verify that if all fields except instructor are filled in on Search click, go to the course page and include all reviews for all terms for that instructor for that course")
	public void dashboardPageTest11() {
		login();

		if(isElementAvailable(Locator.XPATH, DashboardPage.findACourseHeader, "'Search for a Course' header", true)) {
			Assert.assertTrue(isElementAvailable(Locator.XPATH, DashboardPage.searchBtn, "Search button", true), "The Search button is visible on the page");
			Assert.assertTrue(isElementClickable(Locator.XPATH, DashboardPage.searchBtn, "Search button", true), "The Search button is clickable");
			select(Locator.XPATH, DashboardPage.subjectDropdown, "Subject dropdown").selectByValue("Computer Science (CS)");
			select(Locator.XPATH, DashboardPage.courseEnabledDropdown, "Course dropdown").selectByValue("160");
			select(Locator.XPATH, DashboardPage.termEnabledDropdown, "Term dropdown").selectByValue("Fall 2018");
			click(Locator.XPATH, DashboardPage.searchBtn, "Search button", true);
			Assert.assertTrue(isElementAvailable(Locator.XPATH, ViewCoursePage.subjectHeader, "Subject header", true), "The subject header for the Course Page is visible");
			Assert.assertTrue(getElement(Locator.XPATH, ViewCoursePage.subjectHeader).getText().contains("CS 160"), "The course header contains the correct course information");
		}
	}

	@Test(description = "Verify that if all fields are filled in on Search click, go to the course page and include all reviews for that specific instance of the course")
	public void dashboardPageTest12() {
		login();

		if(isElementAvailable(Locator.XPATH, DashboardPage.findACourseHeader, "'Search for a Course' header", true)) {
			Assert.assertTrue(isElementAvailable(Locator.XPATH, DashboardPage.searchBtn, "Search button", true), "The Search button is visible on the page");
			Assert.assertTrue(isElementClickable(Locator.XPATH, DashboardPage.searchBtn, "Search button", true), "The Search button is clickable");
			select(Locator.XPATH, DashboardPage.subjectDropdown, "Subject dropdown").selectByValue("Computer Science (CS)");
			select(Locator.XPATH, DashboardPage.courseEnabledDropdown, "Course dropdown").selectByValue("160");
			select(Locator.XPATH, DashboardPage.termEnabledDropdown, "Term dropdown").selectByValue("Fall 2018");
			select(Locator.XPATH, DashboardPage.professorEnabledDropdown, "Instructor dropdown").selectByValue("Jennifer Parham-Mocello");
			click(Locator.XPATH, DashboardPage.searchBtn, "Search button", true);
			Assert.assertTrue(isElementAvailable(Locator.XPATH, ViewCoursePage.subjectHeader, "Subject header", true), "The subject header for the Course Page is visible");
			Assert.assertTrue(getElement(Locator.XPATH, ViewCoursePage.subjectHeader).getText().contains("CS 160"), "The course header contains the correct course information");
		}
	}

	@Test(description = "Verify that if the instructor field is reset, the other fields don't change")
	public void dashboardPageTest13() {
		login();

		if(isElementAvailable(Locator.XPATH, DashboardPage.findACourseHeader, "'Search for a Course' header", true)) {
			Assert.assertTrue(isElementAvailable(Locator.XPATH, DashboardPage.searchBtn, "Search button", true), "The Search button is visible on the page");
			Assert.assertTrue(isElementClickable(Locator.XPATH, DashboardPage.searchBtn, "Search button", true), "The Search button is clickable");
			select(Locator.XPATH, DashboardPage.subjectDropdown, "Subject dropdown").selectByValue("Computer Science (CS)");
			select(Locator.XPATH, DashboardPage.courseEnabledDropdown, "Course dropdown").selectByValue("160");
			select(Locator.XPATH, DashboardPage.termEnabledDropdown, "Term dropdown").selectByValue("Fall 2018");
			select(Locator.XPATH, DashboardPage.professorEnabledDropdown, "Instructor dropdown").selectByValue("Jennifer Parham-Mocello");
			select(Locator.XPATH, DashboardPage.professorEnabledDropdown, "Professor dropdown").selectByValue("");
			Assert.assertTrue(isElementAvailable(Locator.XPATH, DashboardPage.subjectDropdown, "Subject dropdown", true), "The subject dropdown is still available.");
			Assert.assertTrue(isElementAvailable(Locator.XPATH, DashboardPage.courseEnabledDropdown, "Course dropdown", true), "The course dropdown is still enabled.");
			Assert.assertTrue(isElementAvailable(Locator.XPATH, DashboardPage.termEnabledDropdown, "Term dropdown", true), "The term dropdown is still enabled.");
		}
	}

	@Test(description = "Verify that if the term field is reset, the other fields don't change")
	public void dashboardPageTest14() {
		login();

		if(isElementAvailable(Locator.XPATH, DashboardPage.findACourseHeader, "'Search for a Course' header", true)) {
			Assert.assertTrue(isElementAvailable(Locator.XPATH, DashboardPage.searchBtn, "Search button", true), "The Search button is visible on the page");
			Assert.assertTrue(isElementClickable(Locator.XPATH, DashboardPage.searchBtn, "Search button", true), "The Search button is clickable");
			select(Locator.XPATH, DashboardPage.subjectDropdown, "Subject dropdown").selectByValue("Computer Science (CS)");
			select(Locator.XPATH, DashboardPage.courseEnabledDropdown, "Course dropdown").selectByValue("160");
			select(Locator.XPATH, DashboardPage.termEnabledDropdown, "Term dropdown").selectByValue("Fall 2018");
			select(Locator.XPATH, DashboardPage.professorEnabledDropdown, "Instructor dropdown").selectByValue("Jennifer Parham-Mocello");
			select(Locator.XPATH, DashboardPage.termEnabledDropdown, "Term dropdown").selectByValue("");
			Assert.assertTrue(isElementAvailable(Locator.XPATH, DashboardPage.subjectDropdown, "Subject dropdown", true), "The subject dropdown is still available.");
			Assert.assertTrue(isElementAvailable(Locator.XPATH, DashboardPage.courseEnabledDropdown, "Course dropdown", true), "The course dropdown is still enabled.");
			Assert.assertTrue(isElementAvailable(Locator.XPATH, DashboardPage.professorEnabledDropdown, "Instructor dropdown", true), "The instructor dropdown is still enabled.");
		}
	}

	@Test(description = "Verify that if the course number field is reset, the term and professor fields are reset and disabled")
	public void dashboardPageTest15() {
		login();

		if(isElementAvailable(Locator.XPATH, DashboardPage.findACourseHeader, "'Search for a Course' header", true)) {
			Assert.assertTrue(isElementAvailable(Locator.XPATH, DashboardPage.searchBtn, "Search button", true), "The Search button is visible on the page");
			Assert.assertTrue(isElementClickable(Locator.XPATH, DashboardPage.searchBtn, "Search button", true), "The Search button is clickable");
			select(Locator.XPATH, DashboardPage.subjectDropdown, "Subject dropdown").selectByValue("Computer Science (CS)");
			select(Locator.XPATH, DashboardPage.courseEnabledDropdown, "Course dropdown").selectByValue("160");
			select(Locator.XPATH, DashboardPage.termEnabledDropdown, "Term dropdown").selectByValue("Fall 2018");
			select(Locator.XPATH, DashboardPage.professorEnabledDropdown, "Instructor dropdown").selectByValue("Jennifer Parham-Mocello");
			select(Locator.XPATH, DashboardPage.courseEnabledDropdown, "Course dropdown").selectByValue("");
			Assert.assertTrue(isElementAvailable(Locator.XPATH, DashboardPage.subjectDropdown, "Subject dropdown", true), "The subject dropdown is still available.");
			Assert.assertTrue(isElementAvailable(Locator.XPATH, DashboardPage.termEnabledDropdown, "Term dropdown", false), "The term dropdown is no longer enabled.");
			Assert.assertTrue(isElementAvailable(Locator.XPATH, DashboardPage.professorEnabledDropdown, "Instructor dropdown", false), "The instructor dropdown is no longer enabled.");
			Assert.assertTrue(isElementAvailable(Locator.XPATH, DashboardPage.termDisabledDropdown, "Term dropdown", true), "The term dropdown is disabled.");
			Assert.assertTrue(isElementAvailable(Locator.XPATH, DashboardPage.professorEnabledDropdown, "Instructor dropdown", true), "The instructor dropdown is disabled.");
		}
	}

	@Test(description = "Verify that if the subject field is reset, all 3 other fields are reset and disabled")
	public void dashboardPageTest16() {
		login();

		if(isElementAvailable(Locator.XPATH, DashboardPage.findACourseHeader, "'Search for a Course' header", true)) {
			Assert.assertTrue(isElementAvailable(Locator.XPATH, DashboardPage.searchBtn, "Search button", true), "The Search button is visible on the page");
			Assert.assertTrue(isElementClickable(Locator.XPATH, DashboardPage.searchBtn, "Search button", true), "The Search button is clickable");
			select(Locator.XPATH, DashboardPage.subjectDropdown, "Subject dropdown").selectByValue("Computer Science (CS)");
			select(Locator.XPATH, DashboardPage.courseEnabledDropdown, "Course dropdown").selectByValue("160");
			select(Locator.XPATH, DashboardPage.termEnabledDropdown, "Term dropdown").selectByValue("Fall 2018");
			select(Locator.XPATH, DashboardPage.professorEnabledDropdown, "Instructor dropdown").selectByValue("Jennifer Parham-Mocello");
			select(Locator.XPATH, DashboardPage.subjectDropdown, "Subject dropdown").selectByValue("");
			Assert.assertTrue(isElementAvailable(Locator.XPATH, DashboardPage.subjectDropdown, "Subject dropdown", true), "The subject dropdown is still available.");
			Assert.assertTrue(isElementAvailable(Locator.XPATH, DashboardPage.courseEnabledDropdown, "Course dropdown", false), "The course dropdown is no longer enabled.");
			Assert.assertTrue(isElementAvailable(Locator.XPATH, DashboardPage.termEnabledDropdown, "Term dropdown", false), "The term dropdown is no longer enabled.");
			Assert.assertTrue(isElementAvailable(Locator.XPATH, DashboardPage.professorEnabledDropdown, "Instructor dropdown", false), "The instructor dropdown is no longer enabled.");
			Assert.assertTrue(isElementAvailable(Locator.XPATH, DashboardPage.courseDisabledDropdown, "Course dropdown", false), "The course dropdown is disabled.");
			Assert.assertTrue(isElementAvailable(Locator.XPATH, DashboardPage.termDisabledDropdown, "Term dropdown", true), "The term dropdown is disabled.");
			Assert.assertTrue(isElementAvailable(Locator.XPATH, DashboardPage.professorEnabledDropdown, "Instructor dropdown", true), "The instructor dropdown is disabled.");

		}
	}

	@Test(description = "Verify that 'See all reviews' button is displayed.")
	public void dashboardPageTest17() {
		login();

		int numOfReviews = getElements(Locator.XPATH, "//table[@id='recentReviewTable']//tbody//tr").size();

		Assert.assertEquals(numOfReviews, 3, "Three recent reviews are not displayed by default as expected.");

		Assert.assertTrue(isElementAvailable(Locator.XPATH, DashboardPage.seeAllReviewsBtn, "'See All Reviews' button", true));
	}

	@Test(description = "Verify that reviews open in a modal box upon clicking on the 'View' link")
	public void dashboardPageTest18() {
		login();

		int numOfViewLinks = getElements(Locator.XPATH, "//table[@id='recentReviewTable']//tbody//tr[not(contains(@class, 'hidden-review'))]//a[.='View']").size();

		for(int i = 1; i<=numOfViewLinks; i++) {
			String baseXpath = "//table[@id='recentReviewTable']//tbody//tr[not(contains(@class, 'hidden-review'))]";
			
			String courseName = getText(Locator.XPATH, "("+baseXpath+"//td[3])["+i+"]", "Course Name");
			String termTaken = courseName.substring(courseName.indexOf("(")).replace("(", "").replace(")", "");
			courseName = courseName.substring(0, courseName.indexOf(" ("));
			
			
			String instructorName = getText(Locator.XPATH, "("+baseXpath+"//td[4])["+i+"]", "Instructor Name");
			int rating = getElements(Locator.XPATH, "("+baseXpath+"/td[2])["+i+"]/img").size();
			
			Assert.assertTrue(isElementAvailable(Locator.XPATH, "(//table[@id='recentReviewTable']//tbody//tr[not(contains(@class, 'hidden-review'))]//a[.='View'])["+i+"]", "View link", true));

			click(Locator.XPATH, "(//table[@id='recentReviewTable']//tbody//tr[not(contains(@class, 'hidden-review'))]//a[.='View'])["+i+"]", "View link", true);
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			String baseXpath2 = "//div[@id='yourReviewModal']//div[@class='modal-content']";
			Assert.assertTrue(isElementAvailable(Locator.XPATH, baseXpath2+"//h5[contains(.,'Your Review for "+courseName+"')]", "Modal header", true));
			Assert.assertEquals(getText(Locator.XPATH, baseXpath2+"//select[@id='instructorDropdownYV' and @disabled]/option", "Instructor Name in dropdown"), instructorName);
			Assert.assertEquals(getText(Locator.XPATH, baseXpath2+"//select[@id='termDropdownYV' and @disabled]/option", "Term Taken in dropdown"), termTaken);
			//div[@id='yourReviewModal']//div[@class='modal-content']//span[@id='ratingCheckboxYV' and @disabled='disabled']//a/img[contains(@style,"opacity")]
			Assert.assertEquals(getElements(Locator.XPATH, baseXpath2+"//span[@id='ratingCheckboxYV' and @disabled='disabled']//a/img[contains(@style,'opacity')]").size(), rating);
			
			if(i%2 == 0)
				click(Locator.XPATH, DashboardPage.viewReviewCloseBtn, "'Close button'", true);
			else
				click(Locator.XPATH, DashboardPage.viewReviewCrossCloseBtn, "'X' close button", true);
		}

		//TODO - Complete this test
	}

	@Test(description = "Verify that user is able to view his other reviews apart from three of his/her default reviews by clicking on the 'See all reviews' button. ")
	public void dashboardPageTest19() {
		login();

		int numOfReviews = getElements(Locator.XPATH, "//table[@id='recentReviewTable']//tbody//tr[not(contains(@class, 'hidden-review'))]").size();

		Assert.assertEquals(numOfReviews, 3, "Three recent reviews are not displayed by default as expected.");

		Assert.assertTrue(isElementAvailable(Locator.XPATH, DashboardPage.seeAllReviewsBtn, "'See All Reviews' button", true));

		click(Locator.XPATH, DashboardPage.seeAllReviewsBtn, "'See All Reviews' button", true);
		
		int numOfViewLinks = getElements(Locator.XPATH, "//table[@id='recentReviewTable']//tbody//tr[not(contains(@class, 'hidden-review'))]//a[.='View']").size();
		
		Assert.assertTrue(numOfViewLinks>=3, "'See All Reviews' button has displayed all the reviews for the user.");
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
