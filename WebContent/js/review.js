$(document).ready(function(){

	var url = new URL(window.location.href);
	var courseIdURL = url.searchParams.get("courseId");
	
	$('#studentName').val(document.cookie.split('=')[1].replace('-',' '));
	
	$("#header").load("header.html");
	var courseDetails = sendDataSync("{'courseId':'"+courseIdURL+"'}","fetchCourseDetails","CourseController");
	var courseDetailsJSON = jQuery.parseJSON(courseDetails);
	$('#courseNameHeader').text(courseDetailsJSON.courseNo+ "-" +courseDetailsJSON.courseName);
	$('#courseInstructorHeader').text(courseDetailsJSON.instructor);
	$('#termTaken').val(courseDetailsJSON.termOffered);
	$('#courseDesc').text(courseDetailsJSON.courseDesc);

	/* Temporary dummy reviews. In the future, actual reviews will be retrieved in place of these. */
	var exampleRev1 = new Object();
	exampleRev1.name = "Omeed Habibelahian";
	exampleRev1.rating = 4;
	exampleRev1.term = "Fall 2017";
	exampleRev1.datePosted = "10/01/2018";
	exampleRev1.gradeReceived = "A";
	exampleRev1.reviewText = "160 was tough. They will cram a lot of information in "+
	"your head in a very short time. However, her assignments are fun, and "+
	"her approach on how to take coding and computer science is very intuitive "+
	"and interesting rather than boring. She helps a lot and cares about the "+
	"student. It may be hard but you\'ll learn the material well.";
	var jsonExampleRev1 = JSON.stringify(exampleRev1);

	var exampleRev2 = new Object();
	exampleRev2.name = "Anonymous Student";
	exampleRev2.rating = 3;
	exampleRev2.term = "Fall 2016";
	exampleRev2.datePosted = "09/30/2018";
	exampleRev2.grade = "B";
	exampleRev2.reviewText = "*This student did not write a review in their submission.*";
	var jsonExampleRev2 = JSON.stringify(exampleRev2);

	var reviews = [];		// array that will hold all the reviews for the class
	reviews.push(jsonExampleRev1, jsonExampleRev2);
	var numStars = 0;
	var starHTML = "";
	for (i = 0; i < reviews.length; i++) {
		numStars = reviews[i]['rating'];
		starHTML = "";
		for (j = 0; j < numStars; j++) {
			starHTML += "<img class=\"rating-star\" src=\"images/star-8x_full.png\">";
		}
		$('#currentReviews').html(
			$('#currentReviews').html() +
			"<div class=\"modal-body row\">" +
	      "<div class=\"col-md-3\">" +
	        "<label>" +
	          "<strong>Posted By:</strong>" +
	          "<span>" +
	            reviews[i]['name'] +
	          "</span>" +
	        "</label><br>" +
	        "<label>" +
	          "<strong>Rating:</strong>" +
	          "<span>" +
	            starHTML +
	          "</span>" +
	        "</label><br>" +
	        "<label>" +
	          "<strong>Term:</strong>" +
	          "<span>" +
	            reviews[i]['term'] +
	          "</span>" +
	        "</label><br>" +
	        "<label>" +
	          "<strong>Date Posted:</strong>" +
	          "<span>" +
	            reviews[i]['datePosted'] +
	          "</span>" +
	        "</label><br>" +
	        "<label>" +
	          "<strong>Grade Received:</strong>" +
	          "<span>" +
	            reviews[i]['grade'] +
	          "</span>" +
	        "</label>" +
	      "</div>" +
	      "<div class=\"col-md-7\">" +
	        reviews[i]['reviewText'] +
	      "</div>" +
	    "</div>" +
	    "<span class=\"modal-header\"></span>");
	}

	$('#submitReviewBtn').click(function(){
		$('#submitPendingAlert').css("display", "block");
		//$('#submitSuccessAlert').css("display", "block");

		$('#submitReviewCourseId').val(courseIdURL);
		var reviewJson = $('#submitReviewForm').serializeJSON();
		var status = sendDataSync(JSON.stringify(reviewJson),"addReview","ReviewController");
		if (status == "JDBC_OK") {
			$('#submitPendingAlert').css("display", "none");
			$('#submitSuccessAlert').css("display", "block");
		}
	});
	$('#dismissSubmitSuccessAlert').click(function () {
		var url = new URL(window.location.href);
		var courseIdURL = url.searchParams.get("courseId");
		window.location.href = "course_page.html?courseId="+courseIdURL;
	});

});


function validateSubmitReviewForm() {
	//var form = document.forms["submitReviewForm"];
	var ratingField = ('#ratingDropdown').val();
	if (ratingField == null || ratingField == "") {
		$("#fillFormAlert").html("Please give the course a rating!");
		$("#fillFormAlert").css("display", "block");
		return false;
	}
	$("#fillFormAlert").css("display", "none");
	return true;
}

function toggleAnonymousMsg() {
	var anonymousCheck = document.getElementById("anonymousCheck");
    if (anonymousCheck.checked == false) {
      $("#anonymousMsg").css("display", "none");
    }
    else {
      anonymousCheck.checked = true;
      $("#anonymousMsg").css("display", "block");
    }
}
