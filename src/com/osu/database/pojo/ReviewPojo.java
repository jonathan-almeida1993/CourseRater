package com.osu.database.pojo;

public class ReviewPojo extends BasePojo {

	private int reviewId;
	private int courseId;
	private String onid;
	private String firstName;
	private String lastName;
	private int rating;
	private String review;
	private String gradeReceived;
	private long datePosted;
	private boolean anonymous;
	private String department;
	private int courseNo;
	private String termOffered;
	private String instructor;
	private int thumbsUp;
	private int thumbsDown;
	private int thumb;
	
	
	public int getThumb() {
		return thumb;
	}
	public void setThumb(int thumb) {
		this.thumb = thumb;
	}
	public int getReviewId() {
		return reviewId;
	}
	public void setReviewId(int reviewId) {
		this.reviewId = reviewId;
	}
	public int getCourseId() {
		return courseId;
	}
	public void setCourseId(int courseId) {
		this.courseId = courseId;
	}
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
	public int getRating() {
		return rating;
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
	public void setRating(int rating) {
		this.rating = rating;
	}
	public String getReview() {
		return review;
	}
	public void setReview(String review) {
		this.review = review;
	}
	public long getDatePosted() {
		return datePosted;
	}
	public void setDatePosted(long datePosted) {
		this.datePosted = datePosted;
	}
	public String getGradeReceived() {
		return gradeReceived;
	}
	public void setGradeReceived(String gradeReceived) {
		this.gradeReceived = gradeReceived;
	}
	public boolean isAnonymous() {
		return anonymous;
	}
	public void setAnonymous(boolean anonymous) {
		this.anonymous = anonymous;
	}
	public int getThumbsUp() {
		return thumbsUp;
	}
	public void setThumbsUp(int thumbsUp) {
		this.thumbsUp = thumbsUp;
	}
	public int getThumbsDown() {
		return thumbsDown;
	}
	public void setThumbsDown(int thumbsDown) {
		this.thumbsDown = thumbsDown;
	}

}
