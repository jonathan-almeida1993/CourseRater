package com.osu.database.pojo;

public class CoursePojo extends BasePojo {

	int courseId;
	String department;
	int courseNo;
	String termOffered;
	String instructor;
	String status;
	
	public int getCourseId() {
		return courseId;
	}
	public void setCourseId(int courseId) {
		this.courseId = courseId;
	}
	public String getDepartment() {
		return department;
	}
	public void setDepartment(String department) {
		this.department = department;
	}
	public int getCourseNo() {
		return courseNo;
	}
	public void setCourseNo(int courseNo) {
		this.courseNo = courseNo;
	}
	public String getTermOffered() {
		return termOffered;
	}
	public void setTermOffered(String termOffered) {
		this.termOffered = termOffered;
	}
	public String getInstructor() {
		return instructor;
	}
	public void setInstructor(String instructor) {
		this.instructor = instructor;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
	
		
}
