$(document).ready(function(){

	$('#submitReviewBtn').click(function(){
		var url = new URL(window.location.href);
		var courseIdURL = url.searchParams.get("courseId");
		$('#submitReviewCourseId').val(courseIdURL);
		var reviewJson = $('#submitReviewForm').serializeJSON();
		var status = sendDataSync(JSON.stringify(reviewJson),"addReview","ReviewController");
		alert(status);
	});
	
});