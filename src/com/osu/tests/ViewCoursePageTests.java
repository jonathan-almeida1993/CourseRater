package com.osu.tests;

import java.util.ArrayList;
import java.util.HashMap;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.osu.dao.base.impl.ReviewDAOImpl;
import com.osu.dao.base.interfaces.ReviewDAO;
import com.osu.database.pojo.ReviewPojo;
import com.osu.tests.objects.DashboardPage;
import com.osu.tests.objects.ViewCoursePage;
import com.osu.tests.support.SeleniumUtils;

public class ViewCoursePageTests extends SeleniumUtils{
	
	public HashMap<String, String> navigateToSubmitReview(){
		login();

		select(Locator.XPATH, DashboardPage.subjectDropdown, "'Subject' dropdown").selectByValue("Computer Science (CS)");
		select(Locator.XPATH, DashboardPage.courseEnabledDropdown, "'Course Number' dropdown").selectByValue("325");
		select(Locator.XPATH, DashboardPage.termEnabledDropdown, "'Term' dropdown").selectByValue("Spring 2018");
		select(Locator.XPATH, DashboardPage.professorEnabledDropdown, "'Professor' dropdown").selectByValue("Juli Schutford");

		HashMap<String, String> selections = new HashMap<String, String>();
		selections.put("Subject", "Computer Science (CS)");
		selections.put("CourseNumber", "325");
		selections.put("Term", "Spring 2018");
		selections.put("Professor", "Juli Schutford");
		
		try {
			Thread.sleep(500);
			click(Locator.XPATH, DashboardPage.searchBtn, "'Search' button", true);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		return selections;
	}

	public void navigateToCS160CP() {
		login();

		select(Locator.XPATH, DashboardPage.subjectDropdown, "'Subject' dropdown").selectByValue("Computer Science (CS)");
		select(Locator.XPATH, DashboardPage.courseEnabledDropdown, "'Course Number' dropdown").selectByValue("160");
		select(Locator.XPATH, DashboardPage.termEnabledDropdown, "'Term' dropdown").selectByValue("Fall 2018");
		select(Locator.XPATH, DashboardPage.professorEnabledDropdown, "'Professor' dropdown").selectByValue("Jennifer Parham-Mocello");

		try {
			Thread.sleep(500);
			click(Locator.XPATH, DashboardPage.searchBtn, "'Search' button", true);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	@Test(description = "Verify that all mandatory elements for View course reviews page is displayed as expected.")
	public void viewCourseTest1() {
		navigateToSubmitReview();
		//launchPage(ConfigurationProperties.getProperty("viewCourseURL"));
		
		Assert.assertTrue(isElementAvailable(Locator.XPATH, ViewCoursePage.subjectHeader, "Subject header", true), "Subject header is not displayed as expected.");
		Assert.assertTrue(isElementAvailable(Locator.XPATH, ViewCoursePage.profName, "Professor name", true), "Professor name is not displayed as expected.");
		Assert.assertTrue(isElementAvailable(Locator.XPATH, ViewCoursePage.descriptionLabel, "Description", true), "Subject description is not displayed as expected.");
		Assert.assertTrue(isElementAvailable(Locator.XPATH, ViewCoursePage.reviewsLabel, "Reviews", true), "Reviews label is not displayed as expected.");
	}
	
	@Test(description = "Verify that all mandatory elements for each review section is displayed as expected. ")
	public void viewCourseTest2() {
		navigateToSubmitReview();
		//launchPage(ConfigurationProperties.getProperty("viewCourseURL"));
		
		String baseXpath = "//div[@class='modal-header']//following-sibling::div[@class='modal-body row']";
		
		int numOfReviews = getElements(Locator.XPATH, "//div[@class='modal-header']//following-sibling::div[@class='modal-body row']").size();
		
		for(int i=0; i<numOfReviews; i++) {
			Assert.assertTrue(isElementAvailable(Locator.XPATH, baseXpath+"//strong[.='Posted By:']", "Posted by", true), "'Posted By' label is not displayed as expected for review#"+(i+1));
			Assert.assertTrue(isElementAvailable(Locator.XPATH, baseXpath+"//strong[.='Rating:']", "Rating", true), "'Rating' label is not displayed as expected for review#"+(i+1));
			Assert.assertTrue(isElementAvailable(Locator.XPATH, baseXpath+"//strong[.='Term:']", "Term", true), "'Term' label is not displayed as expected for review#"+(i+1));
			Assert.assertTrue(isElementAvailable(Locator.XPATH, baseXpath+"//strong[.='Date Posted:']", "Date Posted", true), "'Date Posted' label is not displayed as expected for review#"+(i+1));
			Assert.assertTrue(isElementAvailable(Locator.XPATH, baseXpath+"//strong[.='Grade Received:']", "Grade Received", true), "'Grade Received:' label is not displayed as expected for review#"+(i+1));
			
			Assert.assertTrue(isElementAvailable(Locator.XPATH, baseXpath+"//div[2]", "Review text", true), "Review text is not displayed as expected for review#"+(i+1));
		}
	}

	@Test(description = "Verify that if the subject is reset and all fields are empty on Search click, the search fails and the page tells the user to select a subject")
	public void viewCourseTest3() {
		navigateToSubmitReview();

		if(isElementAvailable(Locator.XPATH, ViewCoursePage.searchCourseForm, "Course Search form", true)) {
			Assert.assertTrue(isElementAvailable(Locator.XPATH, ViewCoursePage.searchBtn, "Search button", true), "The Search button is visible on the page");
			Assert.assertFalse(getElement(Locator.XPATH, ViewCoursePage.searchBtn).isEnabled(), "The Search button is disabled");
			select(Locator.XPATH, ViewCoursePage.subjectDropdown, "Subject dropdown").selectByValue("");
			Assert.assertFalse(getElement(Locator.XPATH, ViewCoursePage.subjectDropdown).isSelected(), "The subject dropdown is not selected");
			Assert.assertTrue(isElementClickable(Locator.XPATH, ViewCoursePage.searchBtn, "Search button", true), "The Search button is clickable");
			click(Locator.XPATH, ViewCoursePage.searchBtn, "Search button", true);
			Assert.assertTrue(getElement(Locator.XPATH, ViewCoursePage.fillFormAlert).getCssValue("display").equals("block"), "The Fill Form alert is enabled");
			Assert.assertTrue(getElement(Locator.XPATH, ViewCoursePage.fillFormAlert).getText().equals("Please select a subject!"), "The alert tells the user to select a subject.");
		}
	}

	@Test(description = "Verify that if only the subject is filled in on Search click, the search fails and the page tells the user to select a course number")
	public void viewCourseTest4() {
		navigateToSubmitReview();

		if(isElementAvailable(Locator.XPATH, ViewCoursePage.searchCourseForm, "Course Search form", true)) {
			Assert.assertTrue(isElementAvailable(Locator.XPATH, ViewCoursePage.searchBtn, "Search button", true), "The Search button is visible on the page");
			Assert.assertFalse(getElement(Locator.XPATH, ViewCoursePage.searchBtn).isEnabled(), "The Search button is disabled");
			select(Locator.XPATH, ViewCoursePage.subjectDropdown, "Subject dropdown").selectByValue("Statistics (ST)");
			Assert.assertTrue(isElementClickable(Locator.XPATH, ViewCoursePage.searchBtn, "Search button", true), "The Search button is clickable");
			click(Locator.XPATH, ViewCoursePage.searchBtn, "Search button", true);
			Assert.assertTrue(getElement(Locator.XPATH, ViewCoursePage.fillFormAlert).getCssValue("display").equals("block"), "The Fill Form alert is enabled");
			Assert.assertTrue(getElement(Locator.XPATH, ViewCoursePage.fillFormAlert).getText().equals("Please select a course number!"), "The alert tells the user to select a course number.");
		}
	}

	@Test(description = "Verify that if only the subject and course number fields are filled in on Search click, go to the course page and include all reviews for all terms and instructors for that course")
	public void viewCourseTest5() {
		navigateToSubmitReview();

		if(isElementAvailable(Locator.XPATH, ViewCoursePage.searchCourseForm, "Course Search form", true)) {
			Assert.assertTrue(isElementAvailable(Locator.XPATH, ViewCoursePage.searchBtn, "Search button", true), "The Search button is visible on the page");
			Assert.assertFalse(getElement(Locator.XPATH, ViewCoursePage.searchBtn).isEnabled(), "The Search button is disabled");
			select(Locator.XPATH, ViewCoursePage.subjectDropdown, "Subject dropdown").selectByValue("Statistics (ST)");
			select(Locator.XPATH, ViewCoursePage.courseEnabledDropdown, "Course dropdown").selectByValue("511");
			Assert.assertTrue(isElementClickable(Locator.XPATH, ViewCoursePage.searchBtn, "Search button", true), "The Search button is clickable");
			click(Locator.XPATH, ViewCoursePage.searchBtn, "Search button", true);
			Assert.assertTrue(isElementAvailable(Locator.XPATH, ViewCoursePage.subjectHeader, "Subject header", true), "The subject header for the Course Page is visible");
			Assert.assertTrue(getElement(Locator.XPATH, ViewCoursePage.subjectHeader).getText().contains("CS 160"), "The course header contains the correct course information");
		}
	}

	@Test(description = "Verify that if all fields except instructor are filled in on Search click, go to the course page and include all reviews for all instructors for that term for that course")
	public void viewCourseTest6() {
		navigateToSubmitReview();

		if(isElementAvailable(Locator.XPATH, ViewCoursePage.searchCourseForm, "'Search for a Course' header", true)) {
			Assert.assertTrue(isElementAvailable(Locator.XPATH, ViewCoursePage.searchBtn, "Search button", true), "The Search button is visible on the page");
			Assert.assertFalse(getElement(Locator.XPATH, ViewCoursePage.searchBtn).isEnabled(), "The Search button is disabled");
			select(Locator.XPATH, ViewCoursePage.subjectDropdown, "Subject dropdown").selectByValue("Statistics (ST)");
			select(Locator.XPATH, ViewCoursePage.courseEnabledDropdown, "Course dropdown").selectByValue("511");
			select(Locator.XPATH, ViewCoursePage.professorEnabledDropdown, "Instructor dropdown").selectByValue("Katherine Mclaughlin");
			Assert.assertTrue(isElementClickable(Locator.XPATH, ViewCoursePage.searchBtn, "Search button", true), "The Search button is clickable");
			click(Locator.XPATH, ViewCoursePage.searchBtn, "Search button", true);
			Assert.assertTrue(isElementAvailable(Locator.XPATH, ViewCoursePage.subjectHeader, "Subject header", true), "The subject header for the Course Page is visible");
			Assert.assertTrue(getElement(Locator.XPATH, ViewCoursePage.subjectHeader).getText().contains("CS 160"), "The course header contains the correct course information");
		}
	}

	@Test(description = "Verify that if all fields except term are filled in on Search click, go to the course page and include all reviews for all terms for that instructor for that course")
	public void viewCourseTest7() {
		navigateToSubmitReview();

		if(isElementAvailable(Locator.XPATH, ViewCoursePage.searchCourseForm, "'Search for a Course' header", true)) {
			Assert.assertTrue(isElementAvailable(Locator.XPATH, ViewCoursePage.searchBtn, "Search button", true), "The Search button is visible on the page");
			Assert.assertFalse(getElement(Locator.XPATH, ViewCoursePage.searchBtn).isEnabled(), "The Search button is disabled");
			select(Locator.XPATH, ViewCoursePage.subjectDropdown, "Subject dropdown").selectByValue("Statistics (ST)");
			select(Locator.XPATH, ViewCoursePage.courseEnabledDropdown, "Course dropdown").selectByValue("511");
			select(Locator.XPATH, ViewCoursePage.termEnabledDropdown, "Term dropdown").selectByValue("Fall 2018");
			Assert.assertTrue(isElementClickable(Locator.XPATH, ViewCoursePage.searchBtn, "Search button", true), "The Search button is clickable");
			click(Locator.XPATH, ViewCoursePage.searchBtn, "Search button", true);
			Assert.assertTrue(isElementAvailable(Locator.XPATH, ViewCoursePage.subjectHeader, "Subject header", true), "The subject header for the Course Page is visible");
			Assert.assertTrue(getElement(Locator.XPATH, ViewCoursePage.subjectHeader).getText().contains("CS 160"), "The course header contains the correct course information");
		}
	}

	@Test(description = "Verify that if all fields are filled in on Search click, go to the course page and include all reviews for that specific instance of the course")
	public void viewCourseTest8() {
		navigateToSubmitReview();

		if(isElementAvailable(Locator.XPATH, ViewCoursePage.searchCourseForm, "'Search for a Course' header", true)) {
			Assert.assertTrue(isElementAvailable(Locator.XPATH, ViewCoursePage.searchBtn, "Search button", true), "The Search button is visible on the page");
			Assert.assertFalse(getElement(Locator.XPATH, ViewCoursePage.searchBtn).isEnabled(), "The Search button is disabled");
			select(Locator.XPATH, ViewCoursePage.subjectDropdown, "Subject dropdown").selectByValue("Statistics (ST)");
			select(Locator.XPATH, ViewCoursePage.courseEnabledDropdown, "Course dropdown").selectByValue("511");
			select(Locator.XPATH, ViewCoursePage.termEnabledDropdown, "Term dropdown").selectByValue("Fall 2018");
			select(Locator.XPATH, ViewCoursePage.professorEnabledDropdown, "Instructor dropdown").selectByValue("Katherine Mclaughlin");
			Assert.assertTrue(isElementClickable(Locator.XPATH, ViewCoursePage.searchBtn, "Search button", true), "The Search button is clickable");
			click(Locator.XPATH, ViewCoursePage.searchBtn, "Search button", true);
			Assert.assertTrue(isElementAvailable(Locator.XPATH, ViewCoursePage.subjectHeader, "Subject header", true), "The subject header for the Course Page is visible");
			Assert.assertTrue(getElement(Locator.XPATH, ViewCoursePage.subjectHeader).getText().contains("CS 160"), "The course header contains the correct course information");
		}
	}

	@Test(description = "Verify that if the term field is reset, the other fields don't change")
	public void viewCourseTest9() {
		navigateToSubmitReview();

		if(isElementAvailable(Locator.XPATH, ViewCoursePage.searchCourseForm, "'Search for a Course' header", true)) {
			Assert.assertTrue(isElementAvailable(Locator.XPATH, ViewCoursePage.searchBtn, "Search button", true), "The Search button is visible on the page");
			Assert.assertFalse(getElement(Locator.XPATH, ViewCoursePage.searchBtn).isEnabled(), "The Search button is disabled");
			select(Locator.XPATH, ViewCoursePage.termEnabledDropdown, "Term dropdown").selectByValue("");
			Assert.assertTrue(isElementClickable(Locator.XPATH, ViewCoursePage.searchBtn, "Search button", true), "The Search button is clickable");
			Assert.assertTrue(isElementAvailable(Locator.XPATH, ViewCoursePage.subjectDropdown, "Subject dropdown", true), "The subject dropdown is still available.");
			Assert.assertTrue(isElementAvailable(Locator.XPATH, ViewCoursePage.courseEnabledDropdown, "Course dropdown", true), "The course dropdown is still enabled.");
			Assert.assertTrue(isElementAvailable(Locator.XPATH, ViewCoursePage.professorEnabledDropdown, "Instructor dropdown", true), "The instructor dropdown is still enabled.");
		}
	}

	@Test(description = "Verify that if the course number field is reset, the term and professor fields are reset and disabled")
	public void viewCourseTest10() {
		navigateToSubmitReview();

		if(isElementAvailable(Locator.XPATH, ViewCoursePage.searchCourseForm, "'Search for a Course' header", true)) {
			Assert.assertTrue(isElementAvailable(Locator.XPATH, ViewCoursePage.searchBtn, "Search button", true), "The Search button is visible on the page");
			Assert.assertFalse(getElement(Locator.XPATH, ViewCoursePage.searchBtn).isEnabled(), "The Search button is disabled");
			select(Locator.XPATH, ViewCoursePage.courseEnabledDropdown, "Course dropdown").selectByValue("");
			Assert.assertTrue(isElementClickable(Locator.XPATH, ViewCoursePage.searchBtn, "Search button", true), "The Search button is clickable");
			Assert.assertTrue(isElementAvailable(Locator.XPATH, ViewCoursePage.subjectDropdown, "Subject dropdown", true), "The subject dropdown is still available.");
			Assert.assertTrue(isElementAvailable(Locator.XPATH, ViewCoursePage.termEnabledDropdown, "Term dropdown", false), "The term dropdown is no longer enabled.");
			Assert.assertTrue(isElementAvailable(Locator.XPATH, ViewCoursePage.professorEnabledDropdown, "Instructor dropdown", false), "The instructor dropdown is no longer enabled.");
			Assert.assertTrue(isElementAvailable(Locator.XPATH, ViewCoursePage.termDisabledDropdown, "Term dropdown", true), "The term dropdown is disabled.");
			Assert.assertTrue(isElementAvailable(Locator.XPATH, ViewCoursePage.professorEnabledDropdown, "Instructor dropdown", true), "The instructor dropdown is disabled.");
		}
	}

	@Test(description = "Verify that if the subject field is reset, all 3 other fields are reset and disabled")
	public void viewCourseTest11() {
		navigateToSubmitReview();

		if(isElementAvailable(Locator.XPATH, ViewCoursePage.searchCourseForm, "'Search for a Course' header", true)) {
			Assert.assertTrue(isElementAvailable(Locator.XPATH, ViewCoursePage.searchBtn, "Search button", true), "The Search button is visible on the page");
			Assert.assertFalse(getElement(Locator.XPATH, ViewCoursePage.searchBtn).isEnabled(), "The Search button is disabled");
			select(Locator.XPATH, ViewCoursePage.subjectDropdown, "Subject dropdown").selectByValue("");
			Assert.assertTrue(isElementAvailable(Locator.XPATH, ViewCoursePage.subjectDropdown, "Subject dropdown", true), "The subject dropdown is still available.");
			Assert.assertTrue(isElementAvailable(Locator.XPATH, ViewCoursePage.courseEnabledDropdown, "Course dropdown", false), "The course dropdown is no longer enabled.");
			Assert.assertTrue(isElementAvailable(Locator.XPATH, ViewCoursePage.termEnabledDropdown, "Term dropdown", false), "The term dropdown is no longer enabled.");
			Assert.assertTrue(isElementAvailable(Locator.XPATH, ViewCoursePage.professorEnabledDropdown, "Instructor dropdown", false), "The instructor dropdown is no longer enabled.");
			Assert.assertTrue(isElementAvailable(Locator.XPATH, ViewCoursePage.courseDisabledDropdown, "Course dropdown", false), "The course dropdown is disabled.");
			Assert.assertTrue(isElementAvailable(Locator.XPATH, ViewCoursePage.termDisabledDropdown, "Term dropdown", true), "The term dropdown is disabled.");
			Assert.assertTrue(isElementAvailable(Locator.XPATH, ViewCoursePage.professorEnabledDropdown, "Instructor dropdown", true), "The instructor dropdown is disabled.");
		}
	}

	@Test(description = "Verify that if the user doesn't change any value in the course search form, the Search button is disabled")
	public void viewCourseTest12() {
		navigateToSubmitReview();

		if(isElementAvailable(Locator.XPATH, ViewCoursePage.searchCourseForm, "'Search for a Course' header", true)) {
			Assert.assertTrue(isElementAvailable(Locator.XPATH, ViewCoursePage.searchBtn, "Search button", true), "The Search button is visible on the page");
			Assert.assertFalse(getElement(Locator.XPATH, ViewCoursePage.searchBtn).isEnabled(), "The Search button is disabled");
		}
	}

	@Test(description = "Verify that if the user changes values in the search form but changes them back to the original values, the Search button is disabled again")
	public void viewCourseTest13() {
		navigateToSubmitReview();

		if(isElementAvailable(Locator.XPATH, ViewCoursePage.searchCourseForm, "'Search for a Course' header", true)) {
			Assert.assertTrue(isElementAvailable(Locator.XPATH, ViewCoursePage.searchBtn, "Search button", true), "The Search button is visible on the page");
			Assert.assertFalse(getElement(Locator.XPATH, ViewCoursePage.searchBtn).isEnabled(), "The Search button is disabled");
			select(Locator.XPATH, ViewCoursePage.subjectDropdown, "Subject dropdown").selectByValue("Statistics (ST)");
			Assert.assertTrue(getElement(Locator.XPATH, ViewCoursePage.searchBtn).isEnabled(), "The Search button is enabled after switching the subject");
			select(Locator.XPATH, ViewCoursePage.subjectDropdown, "'Subject' dropdown").selectByValue("Computer Science (CS)");
			Assert.assertTrue(getElement(Locator.XPATH, ViewCoursePage.searchBtn).isEnabled(), "The Search button is enabled after reverting the subject");
			select(Locator.XPATH, ViewCoursePage.courseEnabledDropdown, "'Course Number' dropdown").selectByValue("325");
			Assert.assertTrue(getElement(Locator.XPATH, ViewCoursePage.searchBtn).isEnabled(), "The Search button is enabled after setting the course to its previous value");
			select(Locator.XPATH, ViewCoursePage.termEnabledDropdown, "'Term' dropdown").selectByValue("Spring 2018");
			Assert.assertTrue(getElement(Locator.XPATH, ViewCoursePage.searchBtn).isEnabled(), "The Search button is enabled after setting the term to its previous value");
			select(Locator.XPATH, ViewCoursePage.professorEnabledDropdown, "'Professor' dropdown").selectByValue("Juli Schutford");
			Assert.assertFalse(getElement(Locator.XPATH, ViewCoursePage.searchBtn).isEnabled(), "The Search button is re-disabled after setting the instructor to its previous value");
		}
	}

	@Test(description = "Verify that if the user submits a review with no rating, the submission fails and they're told to provide a rating")
	public void viewCourseTest14() {
		navigateToSubmitReview();

		if(isElementAvailable(Locator.XPATH, ViewCoursePage.searchCourseForm, "'Search for a Course' header", true)) {
			click(Locator.XPATH, ViewCoursePage.createReviewBtn, "Create review button", true);
			click(Locator.XPATH, ViewCoursePage.submitReviewBtn, "Submit review button", true);
			Assert.assertTrue(getElement(Locator.XPATH, ViewCoursePage.fillReviewFormAlert).getCssValue("display").equals("block"), "The Fill Form alert is visible");
			Assert.assertTrue(getElement(Locator.XPATH, ViewCoursePage.fillReviewFormAlert).getText().equals("Please give the course a rating!"), "The Fill Form alert tells the user to give the course a rating");
			click(Locator.XPATH, ViewCoursePage.anonymousCheckbox, "Anonymous check", true);
			select(Locator.XPATH, ViewCoursePage.gradeReceivedDropdown, "'Grade Received' dropdown").selectByValue("A");
			enterText(Locator.XPATH, ViewCoursePage.reviewText, "Test", "Review text", true);
			click(Locator.XPATH, ViewCoursePage.submitReviewBtn, "Submit review button", true);
			Assert.assertTrue(getElement(Locator.XPATH, ViewCoursePage.fillReviewFormAlert).getCssValue("display").equals("block"), "The Fill Form alert is visible");
			Assert.assertTrue(getElement(Locator.XPATH, ViewCoursePage.fillReviewFormAlert).getText().equals("Please give the course a rating!"), "The Fill Form alert tells the user to give the course a rating");
		}
	}

	@Test(description = "Verify that header on reviews page correctly displays the dept, course, term and professor name as selected in the search modal.")
	public void viewCourseTest15() {
		login();
		
		//TODO - actualSelections gives an error. 
		
		/*HashMap<String, String> acutalSelections = navigateToSubmitReview();
		String subject = actualSelections.get("Subject");
		String courseNumber = actualSelections.get("CourseNumber");
		String term = actualSelections.get("Term");
		String professor = actualSelections.get("Professor");*/
		
		//courseHeader = subject
		
		//Assert.assertEquals(getText(Locator.XPATH, ViewCoursePage.courseNumberLabel, "Course Name header"), courseHeader);
	}
	
	@Test(description = "Verify that all anonymous reviews show \"Anonymous Student\" as the student's name")
	public void testReviewAnonymityOnCP1() {
		ReviewDAO dao = new ReviewDAOImpl();
		ArrayList<ReviewPojo> reviewList = dao.fetchCourseReviews(1);

		boolean anonymousAsExpected = true;
		navigateToCS160CP();
		if(isElementAvailable(Locator.XPATH, ViewCoursePage.courseRaterHeader, "Course subject header", true)) {
			for (int i = 0; i < reviewList.size(); i++) {
				String cpReviewName = "//span[@id='review" + (i+1) + "Name']";
				if (reviewList.get(i).isAnonymous() && !getElement(Locator.XPATH, cpReviewName).getText().equals("Anonymous Student")) {
					anonymousAsExpected = false;
					break;
				}
			}
		}

		Assert.assertTrue(anonymousAsExpected, "All anonymous reviews show \"Anonymous Student\" as the student's name.");
	}

	@Test(description = "Verify that when the user submits a review anonymously, their new review on the course page shows \"Anonymous Student\" for their name")
	public void testReviewAnonymityOnCP2() {
		navigateToCS160CP();
		if(isElementAvailable(Locator.XPATH, ViewCoursePage.courseRaterHeader, "'Create Review' button", true)) {
			click(Locator.XPATH, ViewCoursePage.createReviewBtn, "'Create Review' button", true);
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			click(Locator.XPATH, ViewCoursePage.anonymousCheckbox, "Anonymity checkbox", true);
			Assert.assertTrue(isElementAvailable(Locator.XPATH, ViewCoursePage.anonymousReviewBoxMessage, "Anonymous Review message", true));
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			select(Locator.XPATH, ViewCoursePage.gradeReceivedDropdown, "Grade Received").selectByValue("B");

			if(isElementClickable(Locator.XPATH, ViewCoursePage.thirdRatingStar, "Rating: Third star", true))
				click(Locator.XPATH, ViewCoursePage.thirdRatingStar, "Rating", true);

			click(Locator.XPATH, ViewCoursePage.submitReviewBtn, "'Submit Review' button", true);
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			Assert.assertTrue(getElement(Locator.XPATH, ViewCoursePage.newReviewName).getText().equals("Anonymous Student"), "The user's new anonymous review shows \"Anonymous Student\" for their name.");
		}
	}

	@Test(description = "Verify that the text in the 'Name' field of the Reiview form is their name, not their ONID")
	public void testCookieInReviewForm() {
		navigateToCS160CP();
		if(isElementAvailable(Locator.XPATH, ViewCoursePage.courseRaterHeader, "'Create Review' button", true)) {
			click(Locator.XPATH, ViewCoursePage.createReviewBtn, "'Create Review' button", true);
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			String x = getElement(Locator.XPATH, ViewCoursePage.reviewFormYourName).getText();
			Assert.assertTrue(!getElement(Locator.XPATH, ViewCoursePage.reviewFormYourName).isEnabled(), "The 'Name' field is disabled as expected");
			Assert.assertTrue(!getElement(Locator.XPATH, ViewCoursePage.reviewFormYourName).getText().equals("almeidaj"), "The name is not 'almeidaj'");
			Assert.assertTrue(getElement(Locator.XPATH, ViewCoursePage.reviewFormYourName).getText().equals("Jonathan Almeida"), "The name is Jonathan Almeida");
		}
	}

	@Test(description = "Verify that for all reviews where the user did provide a grade, the 'Grade Received' field is displayed")
	public void testGradeReceivedDisplay3() {
		ReviewDAO dao = new ReviewDAOImpl();
		ArrayList<ReviewPojo> reviewList = dao.fetchCourseReviews(1);

		boolean gradeDisplayedAsExpected = true;
		navigateToCS160CP();
		if(isElementAvailable(Locator.XPATH, ViewCoursePage.courseRaterHeader, "Course subject header", true)) {
			for (int i = 0; i < reviewList.size(); i++) {
				String cpReviewGrade = "//span[@id='review" + (i+1) + "Grade']";
				if (!reviewList.get(i).getGradeReceived().equals("N") && !isElementAvailable(Locator.XPATH, cpReviewGrade, "'Grade Received' field", true)) {
					gradeDisplayedAsExpected = false;
					break;
				}
			}
		}

		Assert.assertTrue(gradeDisplayedAsExpected, "All 'Grade Received' fields are displayed for reviews with a grade.");
	}

	@Test(description = "Verify that for all reviews where the user did not provide a grade, the 'Grade Received' field is hidden")
	public void testGradeReceivedDisplay4() {
		ReviewDAO dao = new ReviewDAOImpl();
		ArrayList<ReviewPojo> reviewList = dao.fetchCourseReviews(1);

		boolean gradeHiddenAsExpected = true;
		navigateToCS160CP();
		if(isElementAvailable(Locator.XPATH, ViewCoursePage.courseRaterHeader, "Course subject header", true)) {
			for (int i = 0; i < reviewList.size(); i++) {
				String cpReviewGrade = "//span[@id='review" + (i+1) + "Grade']";
				if (reviewList.get(i).getGradeReceived().equals("N") && isElementAvailable(Locator.XPATH, cpReviewGrade, "'Grade Received' field", true)) {
					gradeHiddenAsExpected = false;
					break;
				}
			}
		}

		Assert.assertTrue(gradeHiddenAsExpected, "All 'Grade Received' fields are hidden for reviews without a grade.");
	}

	@Test(description = "Verify that the button stays enabled when the user resets the search form and chooses the same subject, course number, and term, but leaves the instructor blank")
	public void testSearchButtonStillEnabled1() {
		if(isElementAvailable(Locator.XPATH, ViewCoursePage.searchCourseForm, "'Search for a Course' header", true)) {
			Assert.assertTrue(isElementAvailable(Locator.XPATH, ViewCoursePage.searchBtn, "Search button", true), "The Search button is visible on the page");
			Assert.assertFalse(getElement(Locator.XPATH, ViewCoursePage.searchBtn).isEnabled(), "The Search button is disabled");
			select(Locator.XPATH, ViewCoursePage.subjectDropdown, "Subject dropdown").selectByValue("Statistics (ST)");
			Assert.assertTrue(getElement(Locator.XPATH, ViewCoursePage.searchBtn).isEnabled(), "The Search button is enabled after switching the subject");
			select(Locator.XPATH, ViewCoursePage.subjectDropdown, "'Subject' dropdown").selectByValue("Computer Science (CS)");
			Assert.assertTrue(getElement(Locator.XPATH, ViewCoursePage.searchBtn).isEnabled(), "The Search button is enabled after reverting the subject");
			select(Locator.XPATH, ViewCoursePage.courseEnabledDropdown, "'Course Number' dropdown").selectByValue("325");
			Assert.assertTrue(getElement(Locator.XPATH, ViewCoursePage.searchBtn).isEnabled(), "The Search button is still enabled after setting the course to its previous value");
			select(Locator.XPATH, ViewCoursePage.termEnabledDropdown, "'Term' dropdown").selectByValue("Spring 2018");
			Assert.assertTrue(getElement(Locator.XPATH, ViewCoursePage.searchBtn).isEnabled(), "The Search button is enabled after setting the term to its previous value");
		}
	}

	@Test(description = "Verify that the button stays enabled when the user resets the search form and chooses the same subject, course number, and instructor, but leaves the instructor blank")
	public void testSearchButtonStillEnabled2() {
		if(isElementAvailable(Locator.XPATH, ViewCoursePage.searchCourseForm, "'Search for a Course' header", true)) {
			Assert.assertTrue(isElementAvailable(Locator.XPATH, ViewCoursePage.searchBtn, "Search button", true), "The Search button is visible on the page");
			Assert.assertFalse(getElement(Locator.XPATH, ViewCoursePage.searchBtn).isEnabled(), "The Search button is disabled");
			select(Locator.XPATH, ViewCoursePage.subjectDropdown, "Subject dropdown").selectByValue("Statistics (ST)");
			Assert.assertTrue(getElement(Locator.XPATH, ViewCoursePage.searchBtn).isEnabled(), "The Search button is enabled after switching the subject");
			select(Locator.XPATH, ViewCoursePage.subjectDropdown, "'Subject' dropdown").selectByValue("Computer Science (CS)");
			Assert.assertTrue(getElement(Locator.XPATH, ViewCoursePage.searchBtn).isEnabled(), "The Search button is enabled after reverting the subject");
			select(Locator.XPATH, ViewCoursePage.courseEnabledDropdown, "'Course Number' dropdown").selectByValue("325");
			Assert.assertTrue(getElement(Locator.XPATH, ViewCoursePage.searchBtn).isEnabled(), "The Search button is still enabled after setting the course to its previous value");
			select(Locator.XPATH, ViewCoursePage.professorEnabledDropdown, "'Professor' dropdown").selectByValue("Spring 2018");
			Assert.assertTrue(getElement(Locator.XPATH, ViewCoursePage.searchBtn).isEnabled(), "The Search button is enabled after setting the professor to its previous value");
		}
	}
}