package com.osu.tests.support;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.ITestContext;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;

public class BaseTest {

	protected static WebDriver driver;
	protected static WebDriverWait wait;
	protected static ExtentTest test;
	protected static ExtentReports report;
	
	@BeforeClass
	public void initializeReport() {
		report = new ExtentReports(System.getProperty("user.dir")+"//results//result.html");
		
	}
	
	@BeforeTest(groups= {"unit", "integration"})
	public void setUp(ITestContext iTestContext) throws InterruptedException {
		String testName = iTestContext.getAllTestMethods()[0].getMethodName();
		String testDescription = iTestContext.getAllTestMethods()[0].getDescription();
		
		System.out.println("-------------------------\nIn BeforeTest Running test: "+testName+" \nDescription: "+testDescription);
		
		System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir")+(System.getProperty("os.name").contains("Windows") ? "\\lib\\chromedriver.exe" : "/lib/chromedriver"));

	}
	
	@BeforeMethod(groups = {"unit", "integration"})
	public void testMethodSetUp(ITestContext iTestContext) throws InterruptedException {
		String testName = iTestContext.getAllTestMethods()[0].getMethodName();
		String testDescription = iTestContext.getAllTestMethods()[0].getDescription();

		test = report.startTest(testDescription);
		
		System.out.println("***********************\nIn BeforeMethod Running test: "+testName+" \nDescription: "+testDescription);
		
		ChromeOptions chromeOptions = new ChromeOptions();
		chromeOptions.addArguments("--headless");
		
		driver = new ChromeDriver(chromeOptions);
		wait = new WebDriverWait(driver, 5000);
		
		driver.manage().window().maximize();
	}

	/*@Test
	public void sampletest() throws InterruptedException {
		driver.get(ConfigurationProperties.getProperty("loginURL"));
		Thread.sleep(3000);
	}
	*/
	
	@AfterMethod(groups = {"unit", "integration"})
	public void cleanUpTestMethod() {
		System.out.println("*********************************\nIn the cleanUp afterMethod!");

		report.endTest(test);
		driver.quit();
	}
	
	@AfterTest(groups = {"unit", "integration"})
	public void cleanUp() {
		System.out.println("-----------------------------------\nIn the cleanUp afterTest!");
		ProcessBuilder p = new ProcessBuilder("cmd.exe", "taskkill /F /IM chromedriver.exe");
		try {
			System.out.println(new BufferedReader(new InputStreamReader(p.start().getInputStream())));
		} catch (IOException e) {
			System.out.println("Error message: "+e.getMessage());
			e.printStackTrace();
		}
	}

	@AfterSuite
	public void closeReport() {
		report.flush();
	}
	
}
