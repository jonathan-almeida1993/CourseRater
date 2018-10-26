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
	exampleRev1.anonymous = 0;
	exampleRev1.rating = 4;
	exampleRev1.term = "Fall 2017";
	exampleRev1.datePosted = "10/01/2018";
	exampleRev1.grade = "A";
	exampleRev1.reviewText = "160 was tough. They will cram a lot of information in your head in a very short time. However, her assignments are fun, and her approach on how to take coding and computer science is very intuitive and interesting rather than boring. She helps a lot and cares about the student. It may be hard but you'll learn the material well.";

	var exampleRev2 = new Object();
	exampleRev2.name = "Anonymous Student";
	exampleRev2.anonymous = 1;
	exampleRev2.rating = 3;
	exampleRev2.term = "Fall 2016";
	exampleRev2.datePosted = "09/30/2018";
	exampleRev2.grade = "B";
	exampleRev2.reviewText = "";

	var reviews = [];		// array that will hold all the reviews for the class
	reviews.push(exampleRev1, exampleRev2);
	var numStars = 0;
	var averageRating = 0;
	var avgStarSpanHTML = "";
	var ratingSum = 0;
	for (i = 0; i < reviews.length; i++) {
		ratingSum += reviews[i].rating;
	}
	averageRating = ratingSum/reviews.length;
	var numStars = averageRating;
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
			reviews[i].name = "Anonymous Student";
		}
		if (reviews[i].reviewText == "") {
			reviews[i].reviewText = "*This student did not write a review in their submission.*";
		}

		$('#reviews').append(
			"<div class=\"modal-body row\">" +
				"<div class=\"col-md-3\">" +
					"<label>" +
						"<strong>Posted By: </strong>" +
						"<span>" +
							reviews[i].name +
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
							reviews[i].datePosted +
						"</span>" +
					"</label><br>" +
					"<label>" +
						"<strong>Grade Received: </strong>" +
						"<span>" +
							reviews[i].grade +
						"</span>" +
					"</label>" +
				"</div>" +
				"<div class=\"col-md-7\">" +
					reviews[i].reviewText +
				"</div>" +
			"</div>" +
			"<span + class=\"modal-header\"></span>"
		);
	}

	/* Action when clicking the Submit Review button */
	$('#submitReviewBtn').click(function() {
		if ($('#ratingDropdown').val() == "" || $('#ratingDropdown').val() == null) {
			$("#confirmCloseReviewFormAlert").hide();
			$("#submitSuccessAlert").hide();
			$('#submitPendingAlert').hide();
			$("#fillFormAlert").html("Please give the course a rating!");
			$("#fillFormAlert").show();
		}
		else {
			$("#fillFormAlert").hide();
			$("#confirmCloseReviewFormAlert").hide();
			$("#submitSuccessAlert").hide();
			$('#submitPendingAlert').show();
			var today = new Date();
			var dd = today.getDate();
			var mm = today.getMonth() + 1;
			var yyyy = today.getFullYear();
			if (dd < 10) dd = '0' + dd;
			if (mm < 10) mm = '0' + mm;
			today = mm + '/' + dd + '/' + yyyy;

			var newReview = new Object();
			newReview.name = $('#studentName').text();
			newReview.rating = $('#ratingDropdown').val();
			newReview.term = $('#termTaken').val();
			newReview.datePosted = today;
			if ($('#gradeDropdown').val() == "") {
				newReview.grade = "N/A";
			}
			else newReview.grade = $('#gradeDropdown').val();
			if ($('#reviewText').text().length == 0) {
				newReview.reviewText = "*This student did not write a review in their submission.*"
			}
			else newReview.reviewText = $('reviewText').text();

			reviews = [];
			reviews.push(newReview, exampleRev1, exampleRev2);

			var numStars = 0;
			var starHTML = "";
			$('#reviews').html("");
			for (i = 0; i < reviews.length; i++) {
				numStars = reviews[i].rating;
				starHTML = "";
				for (j = 0; j < numStars; j++) {
					starHTML += "<img class=\"rating-star\" src=\"images/star-8x_full.png\">";
				}

				$('#reviews').append(
					"<div class=\"modal-body row\">" +
						"<div class=\"col-md-3\">" +
							"<label>" +
								"<strong>Posted By: </strong>" +
								"<span>" +
									reviews[i].name +
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
									reviews[i].datePosted +
								"</span>" +
							"</label><br>" +
							"<label>" +
								"<strong>Grade Received: </strong>" +
								"<span>" +
									reviews[i].grade +
								"</span>" +
							"</label>" +
						"</div>" +
						"<div class=\"col-md-7\">" +
							reviews[i].reviewText +
						"</div>" +
					"</div>" +
					"<span + class=\"modal-header\"></span>"
				);
			}

			$('#submitReviewCourseId').val(courseIdURL);
			var reviewJson = $('#submitReviewForm').serializeJSON();
			var status = sendDataSync(JSON.stringify(reviewJson),"addReview","ReviewController");
			if (status == "JDBC_OK") {
				$('#submitPendingAlert').hide();
				$('#submitSuccessAlert').show();
				window.location.href="course_page.html";
			}
		}
	});

	$('#dismissSubmitSuccessAlert').click(function () {
		var url = new URL(window.location.href);
		var courseIdURL = url.searchParams.get("courseId");
		window.location.href = "course_page.html?courseId="+courseIdURL;
	});

	$('#confirmCloseReviewBtn').click(function() {
		console.log("Close Review button pressed!");
		$('#fillFormAlert').hide();
		$('#submitPendingAlert').hide();
		$('#submitSuccessAlert').hide();
		if ($('#gradeDropdown').val() != "" || $('#ratingDropdown').val() != "" ||
				$('#reviewText').text() != "") {
					$('#confirmCloseReviewFormAlert').show();
		}
		else {
			$('#confirmCloseReviewFormAlert').hide();
			$('#submitReviewModal').modal('hide');
		}
	});

	$('#noCloseReviewFormBtn').click(function() {
		console.log("Close Review Confirmation: No button pressed!");
		$('#confirmCloseReviewFormAlert').hide();
	});

	$('#yesCloseReviewFormBtn').click(function() {
		console.log("Close Review Confirmation: Yes button pressed!");
		$('#confirmCloseReviewFormAlert').hide();
		/*$('#gradeDropdown').val("");
		$('#ratingDropdown').val("");
		$('#reviewText').text("");*/
		$('#submitReviewModal').modal('hide');
	});

});

/* Submit Review Form Validation */
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

/* Action on clicking the anonymous checkbox */
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
