package com.osu.tests;

import org.openqa.selenium.Keys;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.osu.tests.objects.DashboardPage;
import com.osu.tests.objects.LoginPage;
import com.osu.tests.support.ConfigurationProperties;
import com.osu.tests.support.SeleniumUtils;

public class LoginPageTests extends SeleniumUtils{
	
	@Test(description = "Verify that OSU logo and CourseRater text is displayed", groups = {"integration"})
	public void loginPageTest1() throws InterruptedException {
		launchPage(ConfigurationProperties.getProperty("loginURL"));
		
		Assert.assertTrue(isElementAvailable(Locator.XPATH, LoginPage.osuLogo, "OSU logo", true), "OSU logo hasn't been displayed as expected.");
		Assert.assertTrue(isElementAvailable(Locator.XPATH, LoginPage.courseRaterHeader, "'Course Rater' header", true), "Course Rater header text hasn't been displayed as expected.");
	}
	
	@Test(description = "Verify that OSU logo, username, password text-boxes and sign-on button is displayed.", groups = {"unit", "integration"})
	public void loginPageTest2() {
		launchPage(ConfigurationProperties.getProperty("loginURL"));
		
		Assert.assertTrue(isElementAvailable(Locator.XPATH, LoginPage.username, "Username textbox", true), "Username textbox hasn't been displayed as expected.");
		Assert.assertTrue(isElementAvailable(Locator.XPATH, LoginPage.password, "Password textbox", true), "Password textbox hasn't been displayed as expected.");
		Assert.assertTrue(isElementAvailable(Locator.XPATH, LoginPage.loginBtn, "Login button", true), "Log In button hasn't been displayed as expected.");
	}
	
	@Test(description = "Verify that user is able to login upon entering valid username and password and clicking on Log in button.", groups = {"integration"})
	public void loginPageTest3() {
		launchPage(ConfigurationProperties.getProperty("loginURL"));
		
		if(isElementAvailable(Locator.XPATH, LoginPage.username, "Username textbox", true) && isElementAvailable(Locator.XPATH, LoginPage.password, "Password textbox", true)) {
			enterText(Locator.XPATH, LoginPage.username, ConfigurationProperties.getProperty("username"), "Username textbox", true);
			enterText(Locator.XPATH, LoginPage.password, ConfigurationProperties.getProperty("password"), "Password textbox", true);
			
			click(Locator.XPATH, LoginPage.loginBtn, "Login button", true);
			
			Assert.assertTrue(isElementAvailable(Locator.XPATH, DashboardPage.logoutBtn, "Logout button", true), "User isn't logged in by entering valid username and password");
		}
	}
	
	@Test(description = "Verify that user is able to login upon entering valid username and password and pressing enter key.", groups = {"integration"})
	public void loginPageTest4() {
		launchPage(ConfigurationProperties.getProperty("loginURL"));
		
		if(isElementAvailable(Locator.XPATH, LoginPage.username, "Username textbox", true) && isElementAvailable(Locator.XPATH, LoginPage.password, "Password textbox", true)) {
			enterText(Locator.XPATH, LoginPage.username, ConfigurationProperties.getProperty("username"), "Username textbox", true);
			enterText(Locator.XPATH, LoginPage.password, ConfigurationProperties.getProperty("password"), "Password textbox", true);
			
			Actions actions = new Actions(driver);
			actions.sendKeys(getElement(Locator.XPATH, LoginPage.loginBtn), Keys.ENTER).build().perform();;
			//wait.until(ExpectedConditions.invisibilityOf(getElement(Locator.XPATH, LoginPage.loginBtn)));
			
			Assert.assertTrue(isElementAvailable(Locator.XPATH, DashboardPage.logoutBtn, "Logout button", true), "User isn't logged in by entering valid username and password");
		}
	}

	/*@Test(dependsOnMethods="loginMethod")
	public void sampleLogintest(){
		System.out.println("Invoking test from login class with dependsonmethods = login ");
	}*/
	
	
}
