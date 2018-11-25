package com.osu.tests;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.osu.tests.support.ConfigurationProperties;
import com.osu.tests.support.SeleniumUtils;

public class CoursesTests extends SeleniumUtils{

	private List<String> getSubjects(){
		ConfigurationProperties.setPropertiesFile("\\src\\com\\osu\\tests\\course.properties");

		return Arrays.asList(ConfigurationProperties.getProperty("subjects").split(", "));
	}

	private HashMap<String, String> getSubjectCourseCodes(){
		List<String> subjects = getSubjects();
		HashMap<String, String> expectedSubjectCodes = new HashMap<>();

		for(String subject : subjects) 
			expectedSubjectCodes.put(subject, subject.substring(subject.indexOf("(")).replace(")", "").replace("(", ""));

		return expectedSubjectCodes;
	}

	private String getSubjectCourseCode(String subject) {
		return getSubjectCourseCodes().get(subject);
	}

	private HashMap<String, String> getSubjectCourseNumber(){
		List<String> subjectCourseCodes = new ArrayList<>(getSubjectCourseCodes().values());
		HashMap<String, String> courseNumbers = new HashMap<>();

		for(String subjectCourseCode : subjectCourseCodes) 
			courseNumbers.put(subjectCourseCode, ConfigurationProperties.getProperty(subjectCourseCode));

		return courseNumbers;
	}

	private String getSubjectTerms(String subjectCode, String courseNumber) {
		return ConfigurationProperties.getProperty(subjectCode+courseNumber);
	}

	private String getSubjectInstructors(String subjectCode, String courseNumber) {
		return ConfigurationProperties.getProperty(subjectCode+courseNumber+"I");
	}

	@Test(description = "Validate that all subjects are displayed as expected.")
	public void courseTest1() {
		login();

		List<String> expectedSubjects = getSubjects();
		List<String> actualSubjects = new ArrayList<>();

		String baseXpath = "//select[@id='subjectDropDown']/option";
		int numOfActualOptions = getElements(Locator.XPATH, baseXpath).size();

		for(int i=2; i<=numOfActualOptions; i++)
			actualSubjects.add(getText(Locator.XPATH, baseXpath+"["+i+"]", "Subject: "));

		try {
			Assert.assertEquals(numOfActualOptions, expectedSubjects.size());

			for (String subject : expectedSubjects) 
				Assert.assertTrue(actualSubjects.contains(subject), subject+" is not present in the list of expected subjects");
		} catch (AssertionError e) {
			System.err.println("Number of actual subjects is not equal to number of expected subjects.");
		}
	}

	@Test(description = "Validate that the course numbers corresponding to the subjects are displayed as expected.")
	public void courseTest2() {
		login();

		List<String> expectedSubjects = getSubjects();

		for(String subject : expectedSubjects) {
			click(Locator.XPATH, "//select[@id='subjectDropDown']/option[.='"+subject+"']", "Subject: "+subject, true);

			String baseXpath = "//select[@id='courseDropDown']/option";
			int numOfActualCourseNumbers = getElements(Locator.XPATH, baseXpath).size();

			List<String> expectedCourseNumbers = Arrays.asList(getSubjectCourseNumber().get(getSubjectCourseCode(subject)).split(", "));

			try {
				Assert.assertEquals(numOfActualCourseNumbers-1, expectedCourseNumbers.size());

				for(int i=2; i<=numOfActualCourseNumbers; i++) {
					String actualCourseNumber = getText(Locator.XPATH, baseXpath+"["+i+"]", "Course Number");
					Assert.assertTrue(expectedCourseNumbers.contains(actualCourseNumber), subject+": "+actualCourseNumber+" is not present in the list of expected course numbers");
				}
			} catch (AssertionError e) {
				System.err.println("Number of actual course numbers for "+subject+" is not equal to number of expected course numbers.");
			}	
		}
	}

	@Test(description = "Validate that the terms corresponding to the selected subjects are displayed as expected.")
	public void courseTest3() {
		login();

		List<String> expectedSubjects = getSubjects();

		for(String subject : expectedSubjects) {
			click(Locator.XPATH, "//select[@id='subjectDropDown']/option[.='"+subject+"']", "Subject: "+subject, true);

			String courseCode = getSubjectCourseCode(subject);
			List<String> expectedCourseNumbers = Arrays.asList(getSubjectCourseNumber().get(courseCode).split(", "));

			for(String courseNumber : expectedCourseNumbers) {
				click(Locator.XPATH, "//select[@id='courseDropDown']/option[.='"+courseNumber+"']", "Subject: "+subject, true);

				String baseXpath = "//select[@id='termDropDown']/option";
				int numOfActualTerms = getElements(Locator.XPATH, baseXpath).size();

				List<String> expectedTerms = Arrays.asList(getSubjectTerms(courseCode, courseNumber).split(", "));

				try {
					Assert.assertEquals(expectedTerms.size(), numOfActualTerms-1);
					for(int j=1; j<=expectedTerms.size(); j++) {
						String actualTerm = getText(Locator.XPATH, baseXpath+"["+(j+1)+"]", "Term");
						Assert.assertTrue(expectedTerms.contains(actualTerm));
						System.out.println(courseCode+courseNumber+": "+actualTerm);
					}	
				} catch(AssertionError e) {
					System.err.println("Number of actual terms for "+subject+" "+courseNumber+" is not as expected.");
				}
			}	
		}
	}

	@Test(description = "Validate that the instructors corresponding to the selected subjects are displayed as expected.")
	public void courseTest4() {
		login();

		List<String> expectedSubjects = getSubjects();

		for(String subject : expectedSubjects) {
			click(Locator.XPATH, "//select[@id='subjectDropDown']/option[.='"+subject+"']", "Subject: "+subject, true);

			String courseCode = getSubjectCourseCode(subject);
			List<String> expectedCourseNumbers = Arrays.asList(getSubjectCourseNumber().get(courseCode).split(", "));

			for(String courseNumber : expectedCourseNumbers) {
				click(Locator.XPATH, "//select[@id='courseDropDown']/option[.='"+courseNumber+"']", "Subject: "+subject, true);

				String baseXpath = "//select[@id='instructorDropDown']/option";
				int numOfActualInstructors = getElements(Locator.XPATH, baseXpath).size();

				List<String> expectedInstructors = Arrays.asList(getSubjectInstructors(courseCode, courseNumber).split(", "));

				try {
					Assert.assertEquals(expectedInstructors.size(), numOfActualInstructors-1);
					for(int j=1; j<=expectedInstructors.size(); j++) {
						String actualInstructor = getText(Locator.XPATH, baseXpath+"["+(j+1)+"]", "Instructor");
						Assert.assertTrue(expectedInstructors.contains(actualInstructor));
						System.out.println(courseCode+courseNumber+": "+actualInstructor);
					}	
				} catch(AssertionError e) {
					System.err.println("Number of actual instructors for "+subject+" "+courseNumber+" is not as expected.");
				}
			}	
		}
	}
}
