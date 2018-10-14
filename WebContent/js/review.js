$(document).ready(function(){

	$("#header").load("header.html");
	$('#submitReviewBtn').click(function(){
		$('#submitPendingAlert').css("display", "block");
		var url = new URL(window.location.href);
		var courseIdURL = url.searchParams.get("courseId");
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
	$("#submitPendingAlert").css("display", "block");
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