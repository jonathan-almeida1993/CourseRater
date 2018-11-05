/*my reviews:
	- valid sesssion
	- invalid session
	- all reviews should be for the logged in user

	
single course reviews:
	- all reviews should be for the single course
	- reviews fetched should contain only one review for the logged in user
	- what happens when null value for course id is passed*/

$(document).ready(function(){
	
	
});

function testAll(courseId){
	testMyReviews();
	testCourseReviews(courseId);
}

function testCourseReviews(courseId){
	testCourseReviewsSingleCourse(courseId);
}

function testCourseReviewsSingleCourse(courseId){
	var reviews = sendDataSync("{'courseId':"+courseId+"}", "getCourseReviews","ReviewController");
	reviews = jQuery.parseJSON(reviews);
	flag = false;
	$(reviews).each(function(idx, obj){
		if(obj.courseId != courseId){
			flag = true;
			return false;
		}
	});
	
	if(flag){
		console.log("CourseReviewsSingleCourse - FAILED");
	}else {
		console.log("CourseReviewsSingleCourse - PASSED");
	}
}


function testMyReviews(){
	testMyReviewsInvalidSession();
	testMyReviewsValidReviews();
}


function testMyReviewsInvalidSession(){
	//logout and terminate session
	sendDataSync("", "logout","LoginController");
	
	var expectedValue = "INVALID_SESSION";
	var actual = sendDataSync("", "getMyReviews","ReviewController");
	
	if(expectedValue == actual){
		console.log("MyReviewsInvalidSession - PASSED");
	}else {
		consol.log("MyReviewsInvalidSession - FAILED");
	}
}

function testMyReviewsValidReviews(){
	
	//Login and create session
	var user = 'almeidaj';
	loginUser(user,'test');
	
	var actual = sendDataSync("", "getMyReviews","ReviewController");
	actual = jQuery.parseJSON(actual);
	
	var flag = false;
	
	$(actual).each(function(idx, obj){
		//console.log(obj.reviewId);
		if(obj.onid != user){
			flag = true;
			return false;
		}
	});
	
	if(flag){
		console.log("MyReviewsValidReviews - FAILED");
	}else {
		console.log("MyReviewsValidReviews - PASSED");
	}
	
	//logout and terminate session
	sendDataSync("", "logout","LoginController");
}

function loginUser(username, pwd){
	var returnData="";
	$.ajax({
		type : "POST",
		url : 'LoginController',
		async:false,
		data : {
		     	onid:username,
			    password:pwd,
			    message:'validateLogin'
		},
		success : function(data) {
			returnData = data;
		},
		error : function(xhr, ajaxOptions, throwError) {
			//add appropriate error messages
		}
	});
}


