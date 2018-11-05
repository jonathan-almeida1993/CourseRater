package com.osu.database.pojo;

public class ReviewPojo extends BasePojo {

	private int reviewId;
	private int courseId;
	private String onid;
	private int rating;
	private String review;
	private String gradeReceived;
	private long datePosted;
	private boolean anonymous;

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
	public int getRating() {
		return rating;
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

}
