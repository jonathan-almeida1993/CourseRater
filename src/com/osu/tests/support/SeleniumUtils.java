package com.osu.tests.support;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.Reporter;

import com.osu.tests.objects.DashboardPage;
import com.osu.tests.objects.LoginPage;
import com.relevantcodes.extentreports.LogStatus;

public class SeleniumUtils extends BaseTest{

	public static enum Locator{
		XPATH, ID, NAME, CLASS_NAME
	}

	public static By by(Locator locator, String locatorValue) {
		By by = null;

		switch(locator) {
		case ID:
			by = By.id(locatorValue);
			break;
		case XPATH:
			by = By.xpath(locatorValue);
			break;
		case CLASS_NAME:
			by = By.className(locatorValue);
			break;
		case NAME:
			by = By.name(locatorValue);
			break;
		}

		return by;
	}

	public static void launchPage(String url) {
		driver.get(url);
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			System.err.println("Exception occured while launching page with URL "+url);
			e.printStackTrace();
		}
	} 

	public static void login() {
		launchPage(ConfigurationProperties.getProperty("loginURL"));

		if(isElementAvailable(Locator.XPATH, LoginPage.username, "Username textbox", true) && isElementAvailable(Locator.XPATH, LoginPage.password, "Password textbox", true)) {
			enterText(Locator.XPATH, LoginPage.username, ConfigurationProperties.getProperty("username"), "Username textbox", true);
			enterText(Locator.XPATH, LoginPage.password, ConfigurationProperties.getProperty("password"), "Password textbox", true);

			click(Locator.XPATH, LoginPage.loginBtn, "Login button", true);

			Assert.assertTrue(isElementAvailable(Locator.XPATH, DashboardPage.logoutBtn, "Logout button", true), "User isn't logged in by entering valid username and password");
		}
	}

	public static WebElement getElement(Locator locator, String locatorValue) {
		return driver.findElement(by(locator, locatorValue));
	}

	public static List<WebElement> getElements(Locator locator, String locatorValue) {
		return driver.findElements(by(locator, locatorValue));
	}

	public static boolean isElementAvailable(Locator locator, String locatorValue, String element, boolean check) {
		boolean available = getElements(locator, locatorValue).size()>0 ? true:false;

		if(check) {
			if(available) {
				Reporter.log(element+" is available as expected.|");
				test.log(LogStatus.PASS, element+" is available as expected. ");
			} else {
				Reporter.log(element+" is not available as expected.|");
				test.log(LogStatus.FAIL, element+" not is available as expected. ");
			}
		} else {
			if(available) {
				Reporter.log(element+" is available as expected.|");
			} else {
				Reporter.log(element+" is not available as expected.|");
			}
		}

		return available;
	}

	public static void click(Locator locator, String locatorValue, String element, boolean check) {
		if(isElementAvailable(locator, locatorValue, element, check)) {
			Reporter.log("Clicked on "+element+"|");
			test.log(LogStatus.INFO, element+" is clicked. ");
			getElement(locator, locatorValue).click();
		}
	}

	public static void enterText(Locator locator, String locatorValue, String value, String element, boolean check) {
		if(isElementAvailable(locator, locatorValue, element, check)) {
			getElement(locator, locatorValue).clear();
			getElement(locator, locatorValue).sendKeys(value);
			test.log(LogStatus.INFO, value+ " has been entered in "+element+" .");
		}
	}

	public static String getAttributeValue(Locator locator, String locatorValue, String attributeValue, String element) {
		String value = getElement(locator, locatorValue).getAttribute(attributeValue);

		test.log(LogStatus.INFO, value+" is value of "+attributeValue+" from "+element+" element. ");

		return attributeValue;
	}

	public static String getText(Locator locator, String locatorValue, String element) {
		String text = getElement(locator, locatorValue).getText();
		
		test.log(LogStatus.PASS, "Text "+text+" has been extracted from "+element+" element.");
		
		return text;
	}
	
	public static boolean isElementClickable(Locator locator, String locatorValue, String element, boolean check) {
		boolean clickable = getElement(locator, locatorValue).isEnabled();
		if(check) {
			if(clickable) {
				Reporter.log("Element "+element+" is clickable.|");
				test.log(LogStatus.PASS, "Element "+element+" is clickable.|");
			}else
				Reporter.log("Element "+element+" is not clickable.|");
				test.log(LogStatus.FAIL, "Element "+element+" is clickable.|");
		} else {
			if(clickable) {
				Reporter.log("Element "+element+" is clickable.|");
			}else
				Reporter.log("Element "+element+" is not clickable.|");
		}
		return clickable;
	}

	public static Select select(Locator locator, String locatorValue, String element) {
		Select sel = new Select(getElement(locator, locatorValue));
		sel.selectByValue(element);
		return sel;
	} 

	public static void takeScreenshot() {
		//TODO - Take screenshot
	}

}
