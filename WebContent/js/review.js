	// When the user scrolls down 20px from the top of the document, show the button
	window.onscroll = function() {scrollFunction()};

	function scrollFunction() {
	    if (document.body.scrollTop > 20 || document.documentElement.scrollTop > 20) {
	        document.getElementById("backToTop").style.display = "block";
	    } else {
	        document.getElementById("backToTop").style.display = "none";
	    }
	}

	// When the user clicks on the button, scroll to the top of the document
	function topFunction() {
	    document.body.scrollTop = 0;
	    document.documentElement.scrollTop = 0;
	}

$(document).ready(function(){

	var url = new URL(window.location.href);
	var courseIdURL = url.searchParams.get("courseId");
	console.log("courseIdURL: " + courseIdURL);
	var courseIds = courseIdURL.split('C');
	courseIds.splice(0,1);

	console.log(document.cookie);
	$('#studentName').val(document.cookie.split('=')[1].replace('-',' '));

	$("#header").load("header.html");
	var idInstructors = [];
	var idTerms = [];
	var courseDetailsJSON = [];
	$.each(courseIds, function(index, value) {
		var courseDetails = sendDataSync("{'courseId':'"+value+"'}","fetchCourseDetails","CourseController");
		console.log("Course details: " + courseDetails);
		courseDetailsJSON = jQuery.parseJSON(courseDetails);
		if (!idInstructors.includes(courseDetailsJSON.instructor)) {
			idInstructors.push(courseDetailsJSON.instructor);
		}
		if (!idTerms.includes(courseDetailsJSON.termOffered)) {
			idTerms.push(courseDetailsJSON.termOffered);
		}
		/*var subjectCode = courseDetailsJSON.department.split("(")[1].slice(0,-1);
		console.log("Subject code: " + subjectCode);*/
		
	});
	/*var courseDetails = sendDataSync("{'courseId':'"+courseIdURL+"'}","fetchCourseDetails","CourseController");
	console.log("Course details: " + courseDetails);
	var courseDetailsJSON = jQuery.parseJSON(courseDetails);*/
	var subjectCode = courseDetailsJSON.department.split("(")[1].slice(0,-1);
	console.log("Subject code: " + subjectCode);
	if (idTerms.length > 1) {
		$('#courseNameHeader').text(subjectCode + " " + courseDetailsJSON.courseNo+ " - " +courseDetailsJSON.courseName + " (All Terms)");
	}
	else {
		$('#courseNameHeader').text(subjectCode + " " + courseDetailsJSON.courseNo+ " - " +courseDetailsJSON.courseName + " (" + courseDetailsJSON.termOffered + ")");
	}
	if (idInstructors.length > 1) {
		$('#courseInstructorHeader').text("All Professors/Instructors");
	}
	else {
		$('#courseInstructorHeader').text(courseDetailsJSON.instructor);
	}
	$('#termTaken').val(courseDetailsJSON.termOffered);
	$('#courseDesc').text(courseDetailsJSON.courseDesc);

	//get department list
	var departmentList = sendDataSync("","fetchDepartments","CourseController");
	console.log("Department List = "+departmentList);

	//append department list to the subject drop down

	$('#subjectDropDownCP').find('option').remove();
	$('#subjectDropDownCP').append('<option value="">Select Department</option>').val('');

	var jsonDept = jQuery.parseJSON(departmentList);

	$.each(jsonDept, function(index, value) {
		$('#subjectDropDownCP').append('<option value="'+value.department+'">'+value.department+'</option>');
	});
	$('#subjectDropDownCP').val(courseDetailsJSON.department);

	var dept = $('#subjectDropDownCP').val();
	var jsonData = '{"department":"'+dept+'"}';
	var courseList = sendDataSync(jsonData,"fetchCourseNo","CourseController");
	console.log("Course List = "+courseList);
	$('#courseDropDownCP').find('option').remove();
	$('#courseDropDownCP').append('<option value="">Select Course Number</option>').val('');
	var jsonCourse = jQuery.parseJSON(courseList);
	$.each(jsonCourse, function(index, value) {
		$('#courseDropDownCP').append('<option value="'+value.courseNo+'">'+value.courseNo+'</option>');
	});
	$('#courseDropDownCP').val(courseDetailsJSON.courseNo);

	var course = $('#courseDropDownCP').val();
	var jsonData = '{"department":"'+dept+'","courseNo":"'+course+'"}';

    var termInstrList = sendDataSync(jsonData,"fetchTermInstr","CourseController");
    console.log("Term List = "+termInstrList);

    $('#termDropDownCP').find('option').remove();
    $('#termDropDownCP').append('<option value="">All Terms</option>').val('');
    $('#instructorDropDownCP').find('option').remove();
    $('#instructorDropDownCP').append('<option value="">All Professors/Instructors</option>').val('');
    var jsonTermInstr = jQuery.parseJSON(termInstrList);
    console.log("jsonTermInstr: " + jsonTermInstr);
    
    var terms = [];
    var instructors = [];
	console.log("course IDs: [" + courseIds + "]");
    $.each(jsonTermInstr, function(index, value) {
    	console.log(value.courseId);
    	
    	console.log(value.termOffered);
    	console.log(value.instructor);
    	if (courseIds.includes(value.courseId.toString())) {
    		if (!terms.includes(value.termOffered)) {
    			terms.push(value.termOffered);
    			console.log("terms now: " + terms);
    		}
    		if (!instructors.includes(value.instructor)) {
    			instructors.push(value.instructor);
    		}
    	}
    });
    
    $.each(terms, function(index, value) {
        $('#termDropDownCP').append('<option value="'+value+'">'+value+'</option>');
    });
      
    $.each(instructors, function(index, value) {
        $('#instructorDropDownCP').append('<option value="' + value + '">' + value +'</option>');
    });
    
    $('#termDropDownCP').val('');
    console.log(terms);
    if (terms.length == 1) {
    	$('#termDropDownCP').val(terms[0]);
    }
    
    $('#instructorDropDownCP').val('');
    console.log(instructors);
    if (instructors.length == 1) {
    	$('#instructorDropDownCP').val(instructors[0]);
    }
    
    /*for (i = 0; i < jsonTermInstr.length; i++) {
    	console.log(jsonTermInstr[i].termOffered);
        if (!terms.includes(jsonTermInstr[i].termOffered)) {
        	terms.push(jsonTermInstr[i].termOffered);
        }
    }
    for (i = 0; i < jsonTermInstr.length; i++) {
    	console.log(jsonTermInstr[i].instructor)
        if (!instructors.includes(jsonTermInstr[i].instructor)) {
        	instructors.push(jsonTermInstr[i].instructor);
        }
    }
    
    console.log(terms);
    console.log(instructors);

    $.each(terms, function(index, value) {
      $('#termDropDownCP').append('<option value="'+value+'">'+value+'</option>');
    });
    
    $.each(instructors, function(index, value) {
      $('#instructorDropDownCP').append('<option value="' + value + '">' + value +'</option>');
    });
    
    $('#termDropDownCP').val('');
    for (i = 0; i < courseIds.length; i++) {
    	$('#termDropDownCP').val(terms[0]);
    }
    
    $('#instructorDropDownCP').val('');
    else {
    	$('#instructorDropDownCP').val(instructors[0]);
    }*/

	/* When the user changes the subject in the Course Search form */
	  $('#subjectDropDownCP').change(function(){
	    var dept = $('#subjectDropDownCP').val();
	    var jsonData = '{"department":"'+dept+'"}';
	    var courseList = sendDataSync(jsonData,"fetchCourseNo","CourseController");
	    console.log("Course List = "+courseList);

	    $('#courseDropDownCP').find('option').remove();
	    $('#courseDropDownCP').append('<option value="">Select Course Number</option>').val('');

	    //enable or disable dropdowns
	    if(dept == ''){
	      $('#courseDropDownCP').attr('disabled','disabled');
	    }else{
	      $('#courseDropDownCP').removeAttr('disabled');
	    }
	    $('#termDropDownCP').attr('disabled','disabled');
	    $('#instructorDropDownCP').attr('disabled','disabled');
	    $('#instructorDropDownCP').val('');
	    $('#termDropDownCP').val('');

	    var jsonCourse = jQuery.parseJSON(courseList);

	    $.each(jsonCourse, function(index, value) {
	      $('#courseDropDownCP').append('<option value="'+value.courseNo+'">'+value.courseNo+'</option>');
	    });
	  });

	  /* When the user changes the course number in the Course Search form */
	  $('#courseDropDown').change(function(){
		var dept = $('#subjectDropDownCP').val();
	    var course = $('#courseDropDownCP').val();
	    var jsonData = '{"department":"'+dept+'","courseNo":"'+course+'"}';

	    var termInstrList = sendDataSync(jsonData,"fetchTermInstr","CourseController");
	    console.log("Term List = "+termInstrList);

	    $('#termDropDownCP').find('option').remove();
	    $('#termDropDownCP').append('<option value="">All Terms</option>').val('');
	    $('#instructorDropDownCP').find('option').remove();
	    $('#instructorDropDownCP').append('<option value="">All Professors/Instructors</option>').val('');

	    //enable or disable dropdowns
	    if(course == ''){
	      $('#termDropDownCP').attr('disabled','disabled');
	      $('#instructorDropDownCP').attr('disabled','disabled');
	    }else{
	      $('#termDropDownCP').removeAttr('disabled');
	      $('#instructorDropDownCP').removeAttr('disabled');
	    }

	    jsonTermInstr = jQuery.parseJSON(termInstrList);
	    console.log("jsonTermInstr: " + jsonTermInstr);
	    
	    var terms = [];
	    var instructors = [];
	    courseIds = [];
	    for (i = 0; i < jsonTermInstr.length; i++) {
	    	if (!terms.includes(jsonTermInstr[i].termOffered)) {
	    		terms.push(jsonTermInstr[i].termOffered);
	    	}
	    	if (!instructors.includes(jsonTermInstr[i].instructor)) {
	    		instructors.push(jsonTermInstr[i].instructor);
	    	}
	    	courseIds.push(jsonTermInstr[i].courseId);
	    }
	    
	    $.each(terms, function(index, value) {
	      $('#termDropDownCP').append('<option value="'+value+'">'+value+'</option>');
	    });
	    
	    $.each(instructors, function(index, value) {
	      $('#instructorDropDownCP').append('<option value="' + value + '">' + value +'</option>');
	    });
	  });

	  /* When the user changes the term taken in the Course Search form */
	  $('#termDropDownCP').change(function(){
		var savedInstructor = $('#instructorDropDownCP').val();
		var instructors = [];
		courseIds = [];
	    if ($('#termDropDownCP').val() == "") {
	        for (i = 0; i < jsonTermInstr.length; i++) {
	        	if (!instructors.includes(jsonTermInstr[i].instructor)) {
	        		instructors.push(jsonTermInstr[i].instructor);
	        	}
	        	if (jsonTermInstr[i].instructor == savedInstructor || savedInstructor == "") {
	            	courseIds.push(jsonTermInstr[i].courseId);
	        	}
	        }
	    }
	    else {
	        for (i = 0; i < jsonTermInstr.length; i++) {
	        	console.log("term offered: " + jsonTermInstr[i].termOffered);
	        	if (!instructors.includes(jsonTermInstr[i].instructor) && jsonTermInstr[i].termOffered == $('#termDropDownCP').val()) {
	        		instructors.push(jsonTermInstr[i].instructor);
	        	}
	        	if (jsonTermInstr[i].termOffered == $('#termDropDownCP').val() && (jsonTermInstr[i].instructor == savedInstructor || savedInstructor == "")) {
	        		courseIds.push(jsonTermInstr[i].courseId);
	        	}
	        }
	    }
	    console.log("instructors: " + instructors);
	    console.log("course ids: " + courseIds);
	    $('#instructorDropDownCP').find('option').remove();
	    $('#instructorDropDownCP').append('<option value="">All Professors/Instructors</option>').val('');
	    $.each(instructors, function(index, value) {
	        $('#instructorDropDownCP').append('<option value="' + value + '">' + value +'</option>');
	    });
	    if ($('#instructorDropDownCP option[value="' + savedInstructor + '"]').length > 0) {
	        $('#instructorDropDownCP').val(savedInstructor);
	    }
	  });

	  /* When the user changes instructor in the Course Search form */
	  $('#instructorDropDownCP').change(function(){
		  var savedTerm = $('#termDropDownCP').val();
	  	  var terms = [];
	  	  courseIds = [];
		  if ($('#instructorDropDownCP').val() == "") {
		        for (i = 0; i < jsonTermInstr.length; i++) {
		        	if (!terms.includes(jsonTermInstr[i].termOffered)) {
		        		terms.push(jsonTermInstr[i].termOffered);
		        	}
		        	if (jsonTermInstr[i].termOffered == savedTerm || savedTerm == "") {
			        	courseIds.push(jsonTermInstr[i].courseId);
		        	}
		        }
		  }
		  else {
		        for (i = 0; i < jsonTermInstr.length; i++) {
		        	console.log("instructor: " + jsonTermInstr[i].instructor);
		        	if (!terms.includes(jsonTermInstr[i].termOffered) && jsonTermInstr[i].instructor == $('#instructorDropDownCP').val()) {
		        		terms.push(jsonTermInstr[i].termOffered);
		        	}
		        	if (jsonTermInstr[i].instructor == $('#instructorDropDownCP').val() && (jsonTermInstr[i].termOffered == savedTerm || savedTerm == "")) {
		        		courseIds.push(jsonTermInstr[i].courseId);
		        	}
		        }
		        
		  }
	      console.log("terms: " + terms);
	      console.log("course ids: " + courseIds);
		  $('#termDropDownCP').find('option').remove();
	      $('#termDropDownCP').append('<option value="">All Terms</option>').val('');
	      $.each(terms, function(index, value) {
	          $('#termDropDownCP').append('<option value="' + value + '">' + value +'</option>');
	      });
		  if ($('#termDropDownCP option[value="' + savedTerm + '"]').length > 0) {
			  $('#termDropDownCP').val(savedTerm);
		  }
	  });

	  /* Action when clicking the Search Course button on the landing page */
	  $('#searchCourseBtnCP').click(function() {
		//var res = validateSearchForm();
	    console.log("Search button pressed!");
	    if(validateSearchForm()){
	    	var dept = $('#subjectDropDownCP').val();
	        var course = $('#courseDropDownCP').val();
	        var term = $('#termDropDownCP').val() == "" ? "ALL" : $('#termDropDownCP').val();
	        var instructor = $('#instructorDropDownCP').val() == "" ? "ALL" : $('#instructorDropDownCP').val();
	        var courseIdParam = '';
	        $.each(courseIds, function(index, value) {
	        	courseIdParam += 'C' + value; 
	        });

	        var jsonData = '{"department":"'+dept+'","courseNo":"'+course+'","termOffered":"'+term+'","instructor":"'+instructor+'"}'; 
	        console.log("json data: " + jsonData);
	        
	        // get Course ID
	        //var courseId = sendDataSync(jsonData,"fetchCourseID","CourseController");
	        //var jsonCourseId = jQuery.parseJSON(courseId);
	        //$('#searchCourseBtn').val(jsonCourseId.courseId);
	      window.location.href = 'course_page.html?courseId=' + courseIdParam;
	    }
	  });

	/* Temporary dummy reviews. In the future, actual reviews will be retrieved in place of these. */
	var exampleRev1 = new Object();
	exampleRev1.name = "Omeed Habibelahian";
	exampleRev1.anonymous = 0;
	exampleRev1.rating = 4;
	exampleRev1.term = "Fall 2017";
	exampleRev1.datePosted = "10/01/2018";
	exampleRev1.gradeReceived = "A";
	exampleRev1.review = "160 was tough. They will cram a lot of information in your head in a very short time. However, her assignments are fun, and her approach on how to take coding and computer science is very intuitive and interesting rather than boring. She helps a lot and cares about the student. It may be hard but you'll learn the material well.";
	
	var exampleRev2 = new Object();
	exampleRev2.name = "Anonymous Student";
	exampleRev2.anonymous = 1;
	exampleRev2.rating = 3;
	exampleRev2.term = "Fall 2016";
	exampleRev2.datePosted = "09/30/2018";
	exampleRev2.gradeReceived = "B";
	exampleRev2.review = "";
	
	var reviews = [];		// array that will hold all the reviews for the class
	reviews.push(exampleRev1, exampleRev2);
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
			reviews[i].name = "Anonymous Student";
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
	
	$('#ratingStarChk1').hover(function() {
		$('#ratingStarValue').text("1/5");
		colorHoveredStarRadioGroup(1);
	}, function() {
		$('#ratingStarValue').text((parseInt($('#ratingCheckbox').val()) > 0) ? $('#ratingCheckbox').val() + "/5" : "");
		colorClickedStarRadioGroup(parseInt($('#ratingCheckbox').val()));
	});
	
	$('#ratingStarChk2').hover(function() {
		$('#ratingStarValue').text("2/5");
		colorHoveredStarRadioGroup(2);
	}, function() {
		$('#ratingStarValue').text((parseInt($('#ratingCheckbox').val()) > 0) ? $('#ratingCheckbox').val() + "/5" : "");
		colorClickedStarRadioGroup(parseInt($('#ratingCheckbox').val()));
	});
	
	$('#ratingStarChk3').hover(function() {
		$('#ratingStarValue').text("3/5");
		colorHoveredStarRadioGroup(3);
	}, function() {
		$('#ratingStarValue').text((parseInt($('#ratingCheckbox').val()) > 0) ? $('#ratingCheckbox').val() + "/5" : "");
		colorClickedStarRadioGroup(parseInt($('#ratingCheckbox').val()));
	});
	
	$('#ratingStarChk4').hover(function() {
		$('#ratingStarValue').text("4/5");
		colorHoveredStarRadioGroup(4);
	}, function() {
		$('#ratingStarValue').text((parseInt($('#ratingCheckbox').val()) > 0) ? $('#ratingCheckbox').val() + "/5" : "");
		colorClickedStarRadioGroup(parseInt($('#ratingCheckbox').val()));
	});
	
	$('#ratingStarChk5').hover(function() {
		$('#ratingStarValue').text("5/5");
		colorHoveredStarRadioGroup(5);
	}, function() {
		$('#ratingStarValue').text((parseInt($('#ratingCheckbox').val()) > 0) ? $('#ratingCheckbox').val() + "/5" : "");
		colorClickedStarRadioGroup(parseInt($('#ratingCheckbox').val()));
	});
	
	$('#ratingStarChk1').click(function() {
		$('#ratingCheckbox').val("1");
		console.log("Rating: " + $('#ratingCheckbox').val());
		colorClickedStarRadioGroup(1);
	});
	
	$('#ratingStarChk2').click(function() {
		$('#ratingCheckbox').val("2");
		console.log("Rating: " + $('#ratingCheckbox').val());
		colorClickedStarRadioGroup(2);
	});
	
	$('#ratingStarChk3').click(function() {
		$('#ratingCheckbox').val("3");
		console.log("Rating: " + $('#ratingCheckbox').val());
		colorClickedStarRadioGroup(3);
	});
	
	$('#ratingStarChk4').click(function() {
		$('#ratingCheckbox').val("4");
		console.log("Rating: " + $('#ratingCheckbox').val());
		colorClickedStarRadioGroup(4);
	});
	
	$('#ratingStarChk5').click(function() {
		$('#ratingCheckbox').val("5");
		console.log("Rating: " + $('#ratingCheckbox').val());
		colorClickedStarRadioGroup(5);
	
	});
	
	/* Action when clicking the Submit Review button */
	$('#submitReviewBtn').click(function() {
		if (validateSubmitReviewForm()) {
			var today = new Date();
			console.log(today);
			var dd = today.getDate();
			var mm = today.getMonth() + 1;
			var yyyy = today.getFullYear();
			if (dd < 10) dd = '0' + dd;
			if (mm < 10) mm = '0' + mm;
			today = mm + '/' + dd + '/' + yyyy;
	
			var newReview = new Object();
			newReview.courseId = courseIdURL;
			newReview.name = $('#studentName').val();
			console.log($('#anonymousCheck').checked);
			var anonymousCheck = document.getElementById("anonymousCheck");
	
			newReview.anonymous = anonymousCheck.checked;
			newReview.rating = $('#ratingCheckbox').val();
			newReview.term = $('#termTaken').val();
			newReview.datePosted = today;
			if ($('#gradeDropdown').val() == "") {
				newReview.gradeReceived = "N";
			}
			else newReview.gradeReceived = $('#gradeDropdown').val();
			if ($('#reviewText').text().length == 0) {
				newReview.review = "*This student did not write a review in their submission.*"
			}
			else newReview.review = $('reviewText').text();
	
			reviews.push(newReview);
			updateAverageRating(reviews);
	
			numStars = newReview.rating;
			starHTML = "";
			for (i = 0; i < numStars; i++) {
				starHTML += "<img class=\"rating-star\" src=\"images/star-8x_full.png\">";
			}
	
			$('#newReview').html(
				"<div class=\"modal-body row\">" +
				"<div class=\"col-md-3\">" +
				"<label>" +
				"<strong>Posted By: </strong>" +
				"<span>" +
				(newReview.anonymous ? "Anonymous Student" : newReview.name) +
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
				newReview.term +
				"</span>" +
				"</label><br>" +
				"<label>" +
				"<strong>Date Posted: </strong>" +
				"<span>" +
				newReview.datePosted +
				"</span>" +
				"</label><br>" +
				"<label>" +
				"<strong>Grade Received: </strong>" +
				"<span>" +
				(newReview.gradeReceived == "N" ? "N/A" : newReview.gradeReceived) +
				"</span>" +
				"</label>" +
				"</div>" +
				"<div class=\"col-md-7\">" +
				newReview.review +
				"</div>" +
				"</div>" +
				"<span + class=\"modal-header\"></span>"
			);
			//$('#newReview').css({"border": "3px solid #b1dfbb", "border-radius": "7px"});
			$({alpha:0}).animate({alpha:1}, {
				duration: 1000,
				step: function() {
					$('#newReview').css("border-color", "rgba(177, 223, 187, " + this.alpha + ")");
					console.log("border getting brighter");
				}
			});
	
			setTimeout(function() {
				$({alpha:1}).animate({alpha:0}, {
					duration: 1000,
					step: function() {
						$('#newReview').css("border-color", "rgba(177, 223, 187, " + this.alpha + ")");
						console.log("border getting lighter");
					}
				});
			}, 5000);
	
			$('#submitReviewCourseId').val(courseIdURL);
			//var reviewJson = newReview.serializeJSON();
			//console.log("form: " + $('#submitReviewForm'));
			console.log(JSON.stringify(newReview));
			var status = sendDataSync(JSON.stringify(newReview),"addReview","ReviewController");
			if (status == "JDBC_OK") {
				$('#submitPendingAlert').hide();
				$('#submitReviewModal').modal("hide");
			}
		}
	});
	
	/*$('#dismissSubmitSuccessAlert').click(function () {
	var url = new URL(window.location.href);
	var courseIdURL = url.searchParams.get("courseId");
	window.location.href = "course_page.html?courseId="+courseIdURL;
	});*/
	
	$('#confirmCloseReviewBtn').click(function() {
		console.log("Close Review button pressed!");
		$('#fillFormAlertCP').hide();
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
	if ($('#ratingCheckbox').val() == "" || $('#ratingCheckbox').val() == null) {
		$("#confirmCloseReviewFormAlert").hide();
		$("#submitSuccessAlert").hide();
		$('#submitPendingAlert').hide();
		$("#fillFormAlertCP").html("Please give the course a rating!");
		$("#fillFormAlertCP").show();
		return false;
	}
	else {
		$("#fillFormAlertCP").hide();
		$("#confirmCloseReviewFormAlert").hide();
		$("#submitSuccessAlert").hide();
		$('#submitPendingAlert').show();
		return true;
	}
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
	console.log(anonymousCheck.checked);
}

function colorHoveredStarRadioGroup(numHovered) {
	for (i = 1; i <= numHovered; i++) {
		var starID = "#ratingStarChk" + i.toString();
		$(starID).css("opacity", "0.4");
	}
	for (i = numHovered + 1; i <= 5; i++) {
		var starID = "#ratingStarChk" + i.toString();
		$(starID).css("opacity", "0.2");
	}
}

function colorClickedStarRadioGroup(numChecked) {
	if (numChecked > 0) {
		for (i = 1; i <= numChecked; i++) {
			var starID = "#ratingStarChk" + i.toString();
			$(starID).css("opacity", "1");
		}
		for (i = numChecked + 1; i <= 5; i++) {
			var starID = "#ratingStarChk" + i.toString();
			$(starID).css("opacity", "0.2");
		}
	}
	else {
		for (i = 0; i <= 5; i++) {
			var starID = "#ratingStarChk" + i.toString();
			$(starID).css("opacity", "0.2");
		}
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

/* Course Search Form Validation */
function validateSearchForm() {
	//var form = document.forms["searchCourseForm"];
	var subjectField = $('#subjectDropDownCP').val();
	var courseNumberField = $('#courseDropDownCP').val();
	var termField = $('#termDropDownCP').val();
	var instructorField = $('#instructorDropDownCP').val();
	if (subjectField == null || subjectField == "") {
		$("#fillFormAlertCP").html("Please select a subject!");
		$("#fillFormAlertCP").css("display", "block");
		return false;
	}
	else if (courseNumberField == null || courseNumberField == "") {
		$("#fillFormAlertCP").html("Please select a course number!");
		$("#fillFormAlertCP").css("display", "block");
		return false;
	}
	/*else if (termField == null || termField == "") {
		$("#fillFormAlertCP").html("Please select a term!");
		$("#fillFormAlertCP").css("display", "block");
		return false;
	}
	else if (instructorField == null || instructorField == "") {
		$("#fillFormAlertCP").html("Please select an instructor!");
		$("#fillFormAlertCP").css("display", "block");
		return false;
	}*/
	$("#fillFormAlertCP").css("display", "none");
	return true;
}
