package com.osu.tests;

import com.osu.dao.base.impl.ReviewDAOImpl;
import com.osu.dao.base.interfaces.ReviewDAO;
import com.osu.database.pojo.ReviewPojo;
import com.osu.tests.objects.ViewCoursePage;
import com.osu.tests.support.ConfigurationProperties;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.osu.tests.objects.DashboardPage;
import com.osu.tests.objects.LoginPage;
import com.osu.tests.support.SeleniumUtils;

import java.util.ArrayList;

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

	@Test(description = "Verify that the text in the 'Name' field of the Your Review modal is their name, not their ONID")
	public void testCookieInYourReview() {
		login();
		if(isElementAvailable(Locator.XPATH, DashboardPage.courseRaterHeader, "'Course Rater' header", true)) {
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if (getElement(Locator.XPATH, DashboardPage.seeMoreReviewsBtn).getCssValue("display").equals("block")) {
				while (getElement(Locator.XPATH, DashboardPage.seeMoreReviewsBtn).getText().equals("See 3 More Reviews")) {
					System.out.println("more reviews");
					click(Locator.XPATH, DashboardPage.seeMoreReviewsBtn, "'See More Reviews' button", true);
					try {
						Thread.sleep(500);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				click(Locator.XPATH, DashboardPage.seeMoreReviewsBtn, "'See More Reviews' button", true);
				try {
					Thread.sleep(500);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				Assert.assertTrue(isElementAvailable(Locator.XPATH, "//a[@data-id='1' and @class='viewReviewBtn']", "'View' button", true));
				click(Locator.XPATH, "//a[@data-id='0' and @class='viewReviewBtn']", "'View' button", true);
				try {
					Thread.sleep(500);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				Assert.assertTrue(isElementAvailable(Locator.XPATH, DashboardPage.yourReviewModalName, "'Name' field of Your Review modal", true), "The 'Name' field of the Your Review modal is visible");
				String obj = getElement(Locator.XPATH, DashboardPage.yourReviewModalName).toString();
				String x = getElement(Locator.XPATH, DashboardPage.yourReviewModalName).getText();
				Assert.assertTrue(!getElement(Locator.XPATH, DashboardPage.yourReviewModalName).getText().equals("almeidaj"), "The name is not 'almeidaj'");
				Assert.assertTrue(getElement(Locator.XPATH, DashboardPage.yourReviewModalName).getText().equals("Jonathan Almeida"), "The name is Jonathan Almeida");
			}
			else {
				System.out.println("No reviews have been retrieved");
			}
		}
	}

	@Test(description = "Verify that if a review was submitted anonymously, the anonymity checkbox for that review is checked in the Your Review modal")
	public void testAnonymousCheckboxYV1() {
		login();
		if (isElementAvailable(Locator.XPATH, DashboardPage.courseRaterHeader, "Course subject header", true)) {
			while (getElement(Locator.XPATH, DashboardPage.seeMoreReviewsBtn).getText().equals("See 3 More Reviews")) {
				System.out.println("more reviews");
				click(Locator.XPATH, DashboardPage.seeMoreReviewsBtn, "'See More Reviews' button", true);
				try {
					Thread.sleep(500);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			click(Locator.XPATH, DashboardPage.seeMoreReviewsBtn, "'See More Reviews' button", true);
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			click(Locator.XPATH, "//a[@data-id='0' and @class='viewReviewBtn']", "'View' button", true);
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			Assert.assertTrue(getElement(Locator.XPATH, DashboardPage.yourReviewAnonymousCheck).isSelected(), "The anonymity checkbox is selected");
		}
	}

	@Test(description = "Verify that if a review was not submitted anonymously, the anonymity checkbox for that review is not checked in the Your Review modal")
	public void testAnonymousCheckboxYV2() {
		login();
		while (getElement(Locator.XPATH, DashboardPage.seeMoreReviewsBtn).getText().equals("See 3 More Reviews")) {
			System.out.println("more reviews");
			click(Locator.XPATH, DashboardPage.seeMoreReviewsBtn, "'See More Reviews' button", true);
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		click(Locator.XPATH, DashboardPage.seeMoreReviewsBtn, "'See More Reviews' button", true);
		if (isElementAvailable(Locator.XPATH, DashboardPage.courseRaterHeader, "Course subject header", true)) {
			click(Locator.XPATH, "//a[@data-id='2' and @class='viewReviewBtn']", "'View' button", true);
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			Assert.assertTrue(!getElement(Locator.XPATH, DashboardPage.yourReviewAnonymousCheck).isSelected(), "The anonymity checkbox is selected");
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
