package com.osu.database.pojo;

public class UserPojo extends BasePojo {

	private String onid;
	private String firstName;
	private String lastName;
	private String password;
	private String type;
	private String classStanding;
	private String major;
	private String status;
	
	
	public String getOnid() {
		return onid;
	}
	public void setOnid(String onid) {
		this.onid = onid;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getClassStanding() {
		return classStanding;
	}
	public void setClassStanding(String classStanding) {
		this.classStanding = classStanding;
	}
	public String getMajor() {
		return major;
	}
	public void setMajor(String major) {
		this.major = major;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
		
}
