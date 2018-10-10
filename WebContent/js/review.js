$(document).ready(function(){

	$('#submitReviewBtn').click(function(){
		var reviewJson = $('#submitReviewForm').serializeJSON();
		console.log(reviewJson);
		
		var status = sendDataSync(JSON.stringify(reviewJson),"addReview","ReviewController");
		alert(status);
	});
	
});