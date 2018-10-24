$(document).ready(function(){

	/* Load the main page header */
	$('#header').load("header.html");
	$('#confirmCloseModal').modal({ show: false });

	/* When the login page loads AFTER logging out, display the logout success alert. */
	if (typeof(Storage) !== "undefined") {
    if (sessionStorage.isLoggedIn == 0 && sessionStorage.pageFrom != "login.html") {
      $('#logoutModal').modal('show');
    }
  }


	// Get the name of the page the user is currently on
	var urlPieces = window.location.pathname.split("/");
	var currentPage = urlPieces[urlPieces.length - 1];
	console.log("current page: " + currentPage);

	/**************************************/
	/* IF THE USER IS ON THE LANDING PAGE */
	/**************************************/
	if (currentPage == "index.html") {
		//get department list
		var departmentList = sendDataSync("","fetchDepartments","CourseController");
		console.log("Department List = "+departmentList);

		//append department list to the subject drop down

		$('#subjectDropDown').find('option').remove();
		$('#subjectDropDown').append('<option value="">Select Department</option>').val('');

		var jsonDept = jQuery.parseJSON(departmentList);

		$.each(jsonDept, function(index, value) {
			$('#subjectDropDown').append('<option value="'+value.department+'">'+value.department+'</option>');
		});

		/* When the user changes the subject in the Course Search form */
		$('#subjectDropDown').change(function(){
			var dept = $('#subjectDropDown').val();
			var jsonData = '{"department":"'+dept+'"}';
			var courseList = sendDataSync(jsonData,"fetchCourseNo","CourseController");
			console.log("Course List = "+courseList);

			$('#courseDropDown').find('option').remove();
			$('#courseDropDown').append('<option value="">Select Course</option>').val('');

			//enable or disable dropdowns
			if(dept == ''){
				$('#courseDropDown').attr('disabled','disabled');
			}else{
				$('#courseDropDown').removeAttr('disabled');
			}
			$('#termDropDown').attr('disabled','disabled');
			$('#instructorDropDown').attr('disabled','disabled');
			$('#instructorDropDown').val('');
			$('#termDropDown').val('');

			var jsonCourse = jQuery.parseJSON(courseList);

			$.each(jsonCourse, function(index, value) {
				$('#courseDropDown').append('<option value="'+value.courseNo+'">'+value.courseNo+'</option>');
			});
		});

		/* When the user changes the course number in the Course Search form */
		$('#courseDropDown').change(function(){
			var dept = $('#subjectDropDown').val();
			var course = $('#courseDropDown').val();
			var jsonData = '{"department":"'+dept+'","courseNo":"'+course+'"}';

			var termList = sendDataSync(jsonData,"fetchTerm","CourseController");
			console.log("Term List = "+termList);

			$('#termDropDown').find('option').remove();
			$('#termDropDown').append('<option value="">Select Term</option>').val('');

			//enable or disable dropdowns
			if(course == ''){
				$('#termDropDown').attr('disabled','disabled');
			}else{
				$('#termDropDown').removeAttr('disabled');
			}
			$('#instructorDropDown').attr('disabled','disabled');
			$('#instructorDropDown').val('');

			var jsonTerm = jQuery.parseJSON(termList);

			$.each(jsonTerm, function(index, value) {
				$('#termDropDown').append('<option value="'+value.termOffered+'">'+value.termOffered+'</option>');
			});
		});

		/* When the user changes the term taken in the Course Search form */
		$('#termDropDown').change(function(){
			var dept = $('#subjectDropDown').val();
			var course = $('#courseDropDown').val();
			var term = $('#termDropDown').val();
			var jsonData = '{"department":"'+dept+'","courseNo":"'+course+'","termOffered":"'+term+'"}';

			var instructorList = sendDataSync(jsonData,"fetchInstructor","CourseController");
			console.log("Instructor List = "+instructorList);

			$('#instructorDropDown').find('option').remove();
			$('#instructorDropDown').append('<option value="">Select Instructor</option>').val('');

			//enable or disable dropdowns
			if(term == ''){
				$('#instructorDropDown').attr('disabled','disabled');
			}else{
				$('#instructorDropDown').removeAttr('disabled');
			}

			var jsonInstructor = jQuery.parseJSON(instructorList);

			$.each(jsonInstructor, function(index, value) {
				$('#instructorDropDown').append('<option value="'+value.instructor+'">'+value.instructor+'</option>');
			});
		});

		/* When the user changes instructor in the Course Search form */
		$('#instructorDropDown').change(function(){
			var dept = $('#subjectDropDown').val();
			var course = $('#courseDropDown').val();
			var term = $('#termDropDown').val();
			var instructor = $('#instructorDropDown').val();

			var jsonData = '{"department":"'+dept+'","courseNo":"'+course+'","termOffered":"'+term+'","instructor":"'+instructor+'"}';

			//get Course ID
			var courseId = sendDataSync(jsonData,"fetchCourseID","CourseController");
			//alert("Course ID = "+courseId);

			var jsonCourseId = jQuery.parseJSON(courseId);
			$('#searchCourseBtn').val(jsonCourseId.courseId);
		});

		/* Action when clicking the Search Course button on the landing page */
		$('#searchCourseBtn').click(function() {
			var res = validateSearchForm();
			if(res){
				window.location.href = 'course_page.html?courseId='+$('#searchCourseBtn').val();
			}
		});
	}

	/*************************************/
	/* IF THE USER IS ON A COURSE'S PAGE */
	/*************************************/
	else if (currentPage.includes("course_page.html")) {
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
		var starHTML = "";
		var reviewDiv = "";
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
			          reviews[i]['name'] +
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
			          reviews[i]['term'] +
			        "</span>" +
			      "</label><br>" +
			      "<label>" +
			        "<strong>Date Posted: </strong>" +
			        "<span>" +
			          reviews[i]['datePosted'] +
			        "</span>" +
			      "</label><br>" +
			      "<label>" +
			        "<strong>Grade Received: </strong>" +
			        "<span>" +
			          reviews[i]['grade'] +
			        "</span>" +
			      "</label>" +
			    "</div>" +
			    "<div class=\"col-md-7\">" +
			      reviews[i]['reviewText'] +
			    "</div>" +
			  "</div>" +
			  "<span + class=\"modal-header\"></span>"
			);
		}

		/* Action when clicking the Submit Review button */
		$('#submitReviewBtn').click(function() {
			if ($('#ratingDropdown').val() == "" || $('#ratingDropdown').val() == null) {
				$("#fillFormAlert").html("Please give the course a rating!");
				$("#fillFormAlert").css("display", "block");
			}
			else {
				$("#fillFormAlert").css("display", "none");
				$('#submitPendingAlert').css("display", "block");
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
					          reviews[i]['name'] +
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
					          reviews[i]['term'] +
					        "</span>" +
					      "</label><br>" +
					      "<label>" +
					        "<strong>Date Posted: </strong>" +
					        "<span>" +
					          reviews[i]['datePosted'] +
					        "</span>" +
					      "</label><br>" +
					      "<label>" +
					        "<strong>Grade Received: </strong>" +
					        "<span>" +
					          reviews[i]['grade'] +
					        "</span>" +
					      "</label>" +
					    "</div>" +
					    "<div class=\"col-md-7\">" +
					      reviews[i]['reviewText'] +
					    "</div>" +
					  "</div>" +
					  "<span + class=\"modal-header\"></span>"
					);
				}

				//$('#submitSuccessAlert').css("display", "block");

				$('#submitReviewCourseId').val(courseIdURL);
				var reviewJson = $('#submitReviewForm').serializeJSON();
				var status = sendDataSync(JSON.stringify(reviewJson),"addReview","ReviewController");
				if (status == "JDBC_OK") {
					//$('#submitPendingAlert').css("display", "none");
					//$('#submitSuccessAlert').css("display", "block");
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
			if ($('#gradeDropdown').val() != "" || $('#ratingDropdown').val() != "" ||
					$('#reviewText').text() != "") {
				$('#confirmCloseReviewFormAlert').show();
			}
			else {
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
	}

});

/* Course Search Form Validation */
function validateSearchForm() {
  //var form = document.forms["searchCourseForm"];
  var subjectField = $('#subjectDropDown').val();
  var courseNumberField = $('#courseDropDown').val();
  var termField = $('#termDropDown').val();
  var instructorField = $('#instructorDropDown').val();
  if (subjectField == null || subjectField == "") {
    $("#fillFormAlert").html("Please select a subject!");
    $("#fillFormAlert").css("display", "block");
    return false;
  }
  else if (courseNumberField == null || courseNumberField == "") {
    $("#fillFormAlert").html("Please select a course number!");
    $("#fillFormAlert").css("display", "block");
    return false;
  }
  else if (termField == null || termField == "") {
    $("#fillFormAlert").html("Please select a term!");
    $("#fillFormAlert").css("display", "block");
    return false;
  }
  else if (instructorField == null || instructorField == "") {
    $("#fillFormAlert").html("Please select an instructor!");
    $("#fillFormAlert").css("display", "block");
    return false;
  }
  $("#fillFormAlert").css("display", "none");
  return true;
}

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

/* Action on clicking the logout button */
function logout() {
	if (typeof(Storage) !== "undefined") {
		sessionStorage.isLoggedIn = 0;
		var urlPieces = window.location.pathname.split("/");
		sessionStorage.pageFrom = urlPieces[pathname.length-1];
    //alert(sessionStorage.pageFrom);
    window.location.href="login.html";
	}
}

/* Login Form Validation */
function validateLoginForm() {
  var onidUsername = $('#onidUsername').val();
  var onidPassword = $('#onidPassword').val();
  if (onidUsername.length == 0) {
    $('#badUsernameAlert').html("Required field!");
    $('#badUsernameAlert').css("display", "none");
  }
  if (onidPassword.length == 0) {
    $('#badPasswordAlert').html("Required field!");
    $('#badPasswordAlert').css("display", "none");
  }
}
