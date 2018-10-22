package com.osu.tests;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.osu.tests.objects.ViewCoursePage;
import com.osu.tests.support.SeleniumUtils;

public class ViewCoursePageTests extends SeleniumUtils{
	
	@Test(description = "Verify that all mandatory elements for View course reviews page is displayed as expected.")
	public void viewCourseTest1() {
		login();
		//launchPage(ConfigurationProperties.getProperty("viewCourseURL"));
		
		Assert.assertTrue(isElementAvailable(Locator.XPATH, ViewCoursePage.subjectHeader, "Subject header", true), "Subject header is not displayed as expected.");
		//Assert.assertTrue(isElementAvailable(Locator.XPATH, ViewCoursePage.profName, "Professor name", true), "Professor name is not displayed as expected.");
		Assert.assertTrue(isElementAvailable(Locator.XPATH, ViewCoursePage.descriptionLabel, "Description", true), "Subject description is not displayed as expected.");
		Assert.assertTrue(isElementAvailable(Locator.XPATH, ViewCoursePage.reviewsLabel, "Reviews", true), "Reviews label is not displayed as expected.");
	}
	
	@Test(description = "Verify that all mandatory elements for each review section is displayed as expected. ")
	public void viewCourseTest2() {
		login();
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
	
}
