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
	
	
	//var reviewList
	//grab all reviews based on the course Ids, and put them into a list called reviesList
	var reviews = [];	// array that will hold all the reviews for the class
	
	var jsonData = '{"courseId":"' + courseIdURL + '"}';
	var reviewList = sendDataSync(jsonData, "getCourseReviews", "ReviewController");
	
	//parse the string for each review and put them into correct field of a review object
	var jsonReviews = jQuery.parseJSON(reviewList);
	
	//add the review object into the review list and display them 
	$.each(jsonReviews, function(index, value) {
		reviews.push(value);
	});
	
//	/* Temporary dummy reviews. In the future, actual reviews will be retrieved in place of these. */
	var exampleRev1 = new Object();
	exampleRev1.onid = "Omeed Habibelahian";
	exampleRev1.anonymous = 0;
	exampleRev1.rating = 4;
	exampleRev1.createdDate = "10/01/2018";
	exampleRev1.gradeReceived = "A";
	exampleRev1.review = "160 was tough. They will cram a lot of information in your head in a very short time. However, her assignments are fun, and her approach on how to take coding and computer science is very intuitive and interesting rather than boring. She helps a lot and cares about the student. It may be hard but you'll learn the material well.";
	
	var exampleRev2 = new Object();
	exampleRev2.onid = "Anonymous Student";
	exampleRev2.anonymous = 1;
	exampleRev2.rating = 3;
	exampleRev2.createdDate = "09/30/2018";
	exampleRev2.gradeReceived = "B";
	exampleRev2.review = "";
	
	//var reviews = [];		// array that will hold all the reviews for the class
	//reviews.push(exampleRev1, exampleRev2);
	updateAverageRating(reviews);
	
	var reviewDiv = "";
	var starHTML = "";
	numStars = 0;
	for (i = 0; i < reviews.length; i++) {
		numStars = reviews[i].rating;
		starHTML = "";
		for (j = 0; j < numStars; j++) {
			starHTML += "<img class=\"rating-star\" src=\"images/star-8x_full.png\">";
		}
		if (reviews[i].anonymous) {
			reviews[i].onid = "Anonymous Student";
		}
		if (reviews[i].review == "") {
			reviews[i].review = "*This student did not write a review in their submission.*";
		}
	
		$('#reviews').append(
			"<div class=\"modal-body row\">" +
			"<div class=\"col-md-3\">" +
			"<label>" +
			"<strong>Posted By: </strong>" +
			"<span>" +
			reviews[i].onid +
			"</span>" +
			"</label><br>" +
			"<label>" +
			"<strong>Rating: </strong>" +
			"<span>" +
			starHTML +
			"</span>" +
			"</label><br>" +
			"<label>" +
			"<strong>Term: </strong>" +
			"<span>" +
			reviews[i].term +
			"</span>" +
			"</label><br>" +
			"<label>" +
			"<strong>Date Posted: </strong>" +
			"<span>" +
			reviews[i].createdDate +
			"</span>" +
			"</label><br>" +
			"<label>" +
			"<strong>Grade Received: </strong>" +
			"<span>" +
			reviews[i].gradeReceived +
			"</span>" +
			"</label>" +
			"</div>" +
			"<div class=\"col-md-7\">" +
			reviews[i].review +
			"</div>" +
			"</div>" +
			"<span + class=\"modal-header\"></span>"
		);
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
		} else if (status == "INVALID_SESSION"){
			window.location.href= 'login.html';
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

function updateAverageRating(reviews) {
	var averageRating = 0;
	var ratingSum = 0;
	console.log(reviews.length);
	for (i = 0; i < reviews.length; i++) {
		ratingSum += parseInt(reviews[i].rating);
	}
	console.log(ratingSum);
	averageRating = parseFloat((ratingSum/reviews.length).toString().substring(0,4));
	console.log(averageRating);
	var numStars = averageRating;
	var avgStarSpanHTML = "";
	while (numStars > 0) {
		if (numStars >= 1) {
			avgStarSpanHTML += "<img class=\"rating-star\" src=\"images/star-8x_full.png\">";
		}
		else if (numStars >= 0.625 && numStars < 1) {
			avgStarSpanHTML += "<img class=\"rating-star\" src=\"images/star-8x_three-quarter.png\">";
		}
		else if (numStars >= 0.375 && numStars < 0.625) {
			avgStarSpanHTML += "<img class=\"rating-star\" src=\"images/star-8x_half.png\">";
		}
		else if (numStars < 0.375) {
			avgStarSpanHTML += "<img class=\"rating-star\" src=\"images/star-8x_quarter.png\">";
		}
		numStars--;
	}
	$('#averageRatingStars').html(avgStarSpanHTML);
	$('#averageRatingValue').html(averageRating + "/5");
}
