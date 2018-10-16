$(document).ready(function(){

	var url = new URL(window.location.href);
	var courseIdURL = url.searchParams.get("courseId");
	
	$("#header").load("header.html");
	var courseDetails = sendDataSync("{'courseId':'"+courseIdURL+"'}","fetchCourseDetails","CourseController");
	var courseDetailsJSON = jQuery.parseJSON(courseDetails);
	$('#courseNameHeader').text(courseDetailsJSON.courseNo+ "-" +courseDetailsJSON.courseName);
	$('#courseInstructorHeader').text(courseDetailsJSON.instructor);
	$('#termTaken').val(courseDetailsJSON.termOffered);
	$('#courseDesc').text(courseDetailsJSON.courseDesc);
	
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
