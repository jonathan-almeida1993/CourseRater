package com.osu.tests;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.osu.tests.objects.DashboardPage;
import com.osu.tests.objects.SubmitAReviewPage;
import com.osu.tests.support.SeleniumUtils;
import com.relevantcodes.extentreports.LogStatus;

//@Test(groups="SubmitReview")
public class SubmitAReviewTests extends SeleniumUtils{

	public void navigateToSubmitReview(){
		login();

		select(Locator.XPATH, DashboardPage.subjectDropdown, "'Subject' dropdown").selectByValue("Computer Science (CS)");
		select(Locator.XPATH, DashboardPage.courseEnabledDropdown, "'Course Number' dropdown").selectByValue("325");
		select(Locator.XPATH, DashboardPage.termEnabledDropdown, "'Term' dropdown").selectByValue("Spring 2018");
		select(Locator.XPATH, DashboardPage.professorEnabledDropdown, "'Professor' dropdown").selectByValue("Juli Schutford");

		try {
			Thread.sleep(500);
			click(Locator.XPATH, DashboardPage.searchBtn, "'Search' button", true);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Test(description = "Verify that all the mandatory elements in 'Submit a Review' form are displayed as expected.")
	public void submitReviewTest1() {
		navigateToSubmitReview();

		Assert.assertTrue(isElementAvailable(Locator.XPATH, SubmitAReviewPage.createReviewBtn, "'Create Review' button", true), "'Submit a Review' button is not displayed as expected.");

		if(isElementAvailable(Locator.XPATH, SubmitAReviewPage.createReviewBtn, "'Create Review' button", true)) {
			click(Locator.XPATH, SubmitAReviewPage.createReviewBtn, "'Create Review' button", true);
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

			Assert.assertTrue(isElementAvailable(Locator.XPATH, SubmitAReviewPage.nameLabel, "'Name' label", true), "'Name' label isn't displayed as expected.");
			Assert.assertTrue(isElementAvailable(Locator.XPATH, SubmitAReviewPage.nameTxtBox, "'Name' textbox", true), "'Name' textbox isn't displayed as expected.");
			Assert.assertEquals(getAttributeValue(Locator.XPATH, SubmitAReviewPage.nameTxtBox, "value", "Name textbox"), "Jonathan Almeida");

			Assert.assertTrue(isElementAvailable(Locator.XPATH, SubmitAReviewPage.termTakenLabel, "'Term taken' label", true), "'Term taken' label isn't displayed as expected.");
			Assert.assertTrue(isElementAvailable(Locator.XPATH, SubmitAReviewPage.termTakenTxtBox, "'Term taken' dropdown", true), "'Term taken' dropdown isn't displayed as expected.");
			Assert.assertEquals(getAttributeValue(Locator.XPATH, SubmitAReviewPage.termTakenTxtBox, "value", "Term taken textbox"), "Spring 2018");

			Assert.assertTrue(isElementAvailable(Locator.XPATH, SubmitAReviewPage.gradeReceivedLabel, "'Grade received' label", true), "'Grade Received' label isn't displayed as expected.");
			Assert.assertTrue(isElementAvailable(Locator.XPATH, SubmitAReviewPage.gradeReceivedDropdown, "'Grade Received' dropdown", true), "'Grade Received' dropdown isn't displayed as expected.");

			Assert.assertTrue(isElementAvailable(Locator.XPATH, SubmitAReviewPage.ratingLabel, "'Rating' label", true), "'Rating' label isn't displayed as expected.");
			//Assert.assertTrue(isElementAvailable(Locator.XPATH, SubmitAReviewPage.ratingDropdown, "'Rating' dropdown", true), "'Rating' dropdown isn't displayed as expected.");

			Assert.assertEquals(getElements(Locator.XPATH, SubmitAReviewPage.thirdRatingStar).size(), 5, "Number of stars displayed for giving rating aren't 5.");

			Assert.assertTrue(isElementAvailable(Locator.XPATH, SubmitAReviewPage.yourReviewLabel, "'Your Review' label", true), "'Your Review' label isn't displayed as expected.");
			Assert.assertTrue(isElementAvailable(Locator.XPATH, SubmitAReviewPage.yourReviewTxtBox, "'Your Review' textbox", true), "'Your Review' textbox isn't displayed as expected.");

			//TODO - Add check to verify if the close button is displayed on the Submit review modal.  

			Assert.assertTrue(isElementAvailable(Locator.XPATH, SubmitAReviewPage.submitAnonymouslyChkbox, "'Submit Anonymously' checkbox", true), "'Submit Review Anonymously' checkbox hasn't been displayed as expected.");

			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			Assert.assertTrue(isElementAvailable(Locator.XPATH, SubmitAReviewPage.submitReviewBtn, "'Submit review' button", true), "'Submit Review' button isn't displayed as expected.");
			Assert.assertTrue(isElementAvailable(Locator.XPATH, SubmitAReviewPage.closeBtn, "'Close' button", true), "'Close' button isn't displayed as expected.");
		}
	}

	@Test(description = "Verify that user is able to submit a review and appropriate confirmation message is displayed.")
	public void submitReviewTest2() {
		navigateToSubmitReview();

		if(isElementAvailable(Locator.XPATH, SubmitAReviewPage.createReviewBtn, "'Create Review' button", true)) {
			click(Locator.XPATH, SubmitAReviewPage.createReviewBtn, "'Create Review' button", true);
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			Assert.assertEquals(getAttributeValue(Locator.XPATH, SubmitAReviewPage.nameTxtBox, "value", "Name textbox"), "Jonathan Almeida");
			Assert.assertEquals(getAttributeValue(Locator.XPATH, SubmitAReviewPage.termTakenTxtBox, "value", "Term taken textbox"), "Spring 2018");

			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			select(Locator.XPATH, SubmitAReviewPage.gradeReceivedDropdown, "Grade Received").selectByValue("B");
			
			if(isElementClickable(Locator.XPATH, SubmitAReviewPage.thirdRatingStar, "Rating: Third star", true))
				click(Locator.XPATH, SubmitAReviewPage.thirdRatingStar, "Rating", true);

			enterText(Locator.XPATH, SubmitAReviewPage.yourReviewTxtBox, "Sample review text", "Your Review textbox", true);

			click(Locator.XPATH, SubmitAReviewPage.submitReviewBtn, "Submit Review", true);

			if(isElementAvailable(Locator.XPATH, SubmitAReviewPage.reviewSubmitConfirmationTxt, "Submit Confirmation Text", true))
				test.log(LogStatus.PASS, "Confirmation text after submitting the review has been displayed as expected.");

			Assert.assertTrue(isElementAvailable(Locator.XPATH, SubmitAReviewPage.reviewSubmitConfirmartionOkBtn, "Submit Confirmation Ok button", true), "Submit Confirmation Ok button is not displayed as expected.");

			//TODO - Add code to verify that submitted review is displayed on the page. 
		}
	}

	@Test(description = "Verify that user is able to cancel submitting review after making selections and appropriate warning message is displayed.")
	public void submitReviewTest3() {
		login();

		navigateToSubmitReview();

		if(isElementAvailable(Locator.XPATH, SubmitAReviewPage.createReviewBtn, "'Create Review' button", true)) {
			click(Locator.XPATH, SubmitAReviewPage.createReviewBtn, "'Create Review' button", true);

			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			Assert.assertEquals(getAttributeValue(Locator.XPATH, SubmitAReviewPage.nameTxtBox, "value", "Name"), "Jonathan Almeida");
			Assert.assertEquals(getAttributeValue(Locator.XPATH, SubmitAReviewPage.termTakenTxtBox, "value", "Term taken"), "Spring 2018");
			Assert.assertTrue(isElementAvailable(Locator.XPATH, SubmitAReviewPage.gradeReceivedDropdown, "Grade Received", true));
			
			select(Locator.XPATH, SubmitAReviewPage.gradeReceivedDropdown, "Grade Received").selectByValue("A");

			if(isElementClickable(Locator.XPATH, SubmitAReviewPage.thirdRatingStar, "Rating: Third star", true))
				click(Locator.XPATH, SubmitAReviewPage.thirdRatingStar, "Rating", true);

			enterText(Locator.XPATH, SubmitAReviewPage.yourReviewTxtBox, "Sample review text for closing modal test", "Your Review textbox", true);

			click(Locator.XPATH, SubmitAReviewPage.closeBtn, "Close button", true);

			Assert.assertTrue(isElementAvailable(Locator.XPATH, SubmitAReviewPage.closeConfirmationText, "Close modal confirmation text", true));
			Assert.assertTrue(isElementAvailable(Locator.XPATH, SubmitAReviewPage.yesCloseConfirmationText, "Yes Close modal confirmation text", true));
			Assert.assertTrue(isElementAvailable(Locator.XPATH, SubmitAReviewPage.noCloseConfirmationText, "No Close modal confirmation text", true));
			
			click(Locator.XPATH, SubmitAReviewPage.yesCloseConfirmationText, "Yes Close Confirmation Text", true);
			
			Assert.assertFalse(isElementAvailable(Locator.XPATH, SubmitAReviewPage.submitAReviewHeader, "Submit A Review header", true));
		}
	}

	@Test(description = "Verify that review filled in 'Submit a review' form is cleared after cancelling submit of review and navigating away from the reviews page.")
	public void submitReviewTest4() {
		login();

		navigateToSubmitReview();
		
		if(isElementAvailable(Locator.XPATH, SubmitAReviewPage.createReviewBtn, "'Create Review' button", true)) {
			click(Locator.XPATH, SubmitAReviewPage.createReviewBtn, "'Create Review' button", true);
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			Assert.assertEquals(getAttributeValue(Locator.XPATH, SubmitAReviewPage.nameTxtBox, "value", "Name textbox"), "Jonathan Almeida");
			Assert.assertEquals(getAttributeValue(Locator.XPATH, SubmitAReviewPage.termTakenTxtBox, "value", "Term taken textbox"), "Spring 2018");
			Assert.assertTrue(isElementAvailable(Locator.XPATH, SubmitAReviewPage.submitAnonymouslyChkbox, "Submit Review Anonymously checkbox", true));
			
			click(Locator.XPATH, SubmitAReviewPage.submitAnonymouslyChkbox, "Submit Review Anonymously", true);
			Assert.assertTrue(isElementAvailable(Locator.XPATH, SubmitAReviewPage.anonymousReviewBoxMessage, "Anonymous Review message", true));
			
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			select(Locator.XPATH, SubmitAReviewPage.gradeReceivedDropdown, "Grade Received").selectByValue("B");
			
			if(isElementClickable(Locator.XPATH, SubmitAReviewPage.thirdRatingStar, "Rating: Third star", true))
				click(Locator.XPATH, SubmitAReviewPage.thirdRatingStar, "Rating", true);

			enterText(Locator.XPATH, SubmitAReviewPage.yourReviewTxtBox, "Sample review text", "Your Review textbox", true);
			
			click(Locator.XPATH, SubmitAReviewPage.closeBtn, "Close Submit Review modal", true);
			click(Locator.XPATH, SubmitAReviewPage.yesCloseConfirmationText, "Yes Cancel submitting review", true);
			
			driver.navigate().back();
			
			//TODO - The review text is still displayed even after the user clicks on back button and returns to the review page. Verify the functionality and accordingly complete the test. 
		}
	}

	@Test(description = "Verify that user is able to submit a review anonymously and that it's displayed anonymously under his reviews.")
	public void submitReviewTest5() {
		login();

		navigateToSubmitReview();
		
		if(isElementAvailable(Locator.XPATH, SubmitAReviewPage.createReviewBtn, "'Create Review' button", true)) {
			click(Locator.XPATH, SubmitAReviewPage.createReviewBtn, "'Create Review' button", true);
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			Assert.assertEquals(getAttributeValue(Locator.XPATH, SubmitAReviewPage.nameTxtBox, "value", "Name textbox"), "Jonathan Almeida");
			Assert.assertEquals(getAttributeValue(Locator.XPATH, SubmitAReviewPage.termTakenTxtBox, "value", "Term taken textbox"), "Spring 2018");
			Assert.assertTrue(isElementAvailable(Locator.XPATH, SubmitAReviewPage.submitAnonymouslyChkbox, "Submit Review Anonymously checkbox", true));
			
			click(Locator.XPATH, SubmitAReviewPage.submitAnonymouslyChkbox, "Submit Review Anonymously", true);
			Assert.assertTrue(isElementAvailable(Locator.XPATH, SubmitAReviewPage.anonymousReviewBoxMessage, "Anonymous Review message", true));
			
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			select(Locator.XPATH, SubmitAReviewPage.gradeReceivedDropdown, "Grade Received").selectByValue("B");
			
			if(isElementClickable(Locator.XPATH, SubmitAReviewPage.thirdRatingStar, "Rating: Third star", true))
				click(Locator.XPATH, SubmitAReviewPage.thirdRatingStar, "Rating", true);

			enterText(Locator.XPATH, SubmitAReviewPage.yourReviewTxtBox, "Sample review text", "Your Review textbox", true);

			click(Locator.XPATH, SubmitAReviewPage.submitReviewBtn, "Submit Review", true);

			if(isElementAvailable(Locator.XPATH, SubmitAReviewPage.reviewSubmitConfirmationTxt, "Submit Confirmation Text", true))
				test.log(LogStatus.PASS, "Confirmation text after submitting the review has been displayed as expected.");

			Assert.assertTrue(isElementAvailable(Locator.XPATH, SubmitAReviewPage.reviewSubmitConfirmartionOkBtn, "Submit Confirmation Ok button", true), "Submit Confirmation Ok button is not displayed as expected.");

			//TODO - Add code to verify that review submitted anonymously is displayed on the page as anonymous. 
		}
	}


}
