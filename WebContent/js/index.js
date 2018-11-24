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

$(document).ready(function() {

  //get department list
  var departmentList = sendDataSync("","fetchDepartments","CourseController");
  console.log("Department List = "+departmentList);

  //append department list to the subject drop down

  $('#subjectDropDown').find('option').remove();
  $('#subjectDropDown').append('<option value="">Select Subject</option>').val('');

  var jsonDept = jQuery.parseJSON(departmentList);
  jsonDept.sort(function(a, b) {
	 var deptA = a.department.toLowerCase(), deptB = b.department.toLowerCase();
	 if (deptA < deptB) return -1;
	 if (deptA > deptB) return 1;
	 return 0;
  });
  var jsonTermInstr = '';
  var courseIds = [];

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
    $('#courseDropDown').append('<option value="">Select Course Number</option>').val('');

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
    jsonCourse.sort(function(a, b) {
    	return a.courseNo - b.courseNo;
    });
    $.each(jsonCourse, function(index, value) {
      $('#courseDropDown').append('<option value="'+value.courseNo+'">'+value.courseNo+'</option>');
    });
  });

  /* When the user changes the course number in the Course Search form */
  $('#courseDropDown').change(function(){
    var dept = $('#subjectDropDown').val();
    var course = $('#courseDropDown').val();
    var jsonData = '{"department":"'+dept+'","courseNo":"'+course+'"}';

    var termInstrList = sendDataSync(jsonData,"fetchTermInstr","CourseController");
    console.log("Term List = "+termInstrList);

    $('#termDropDown').find('option').remove();
    $('#termDropDown').append('<option value="">All Terms</option>').val('');
    $('#instructorDropDown').find('option').remove();
    $('#instructorDropDown').append('<option value="">All Professors/Instructors</option>').val('');

    //enable or disable dropdowns
    if(course == ''){
      $('#termDropDown').attr('disabled','disabled');
      $('#instructorDropDown').attr('disabled','disabled');
    }else{
      $('#termDropDown').removeAttr('disabled');
      $('#instructorDropDown').removeAttr('disabled');
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
    terms.sort(function(a,b) {
		  var termA = a.toLowerCase().split(' ')[0];
		  var termB = b.toLowerCase().split(' ')[0];
		  var yearA = parseInt(a.toLowerCase().split(' ')[1]);
		  var yearB = parseInt(b.toLowerCase().split(' ')[1]);
		  if (yearA > yearB) return -1;
		  if (yearA < yearB) return 1;
		  if (termA == 'fall' || termB == 'winter') return -1;
		  if (termA == 'winter' || termB == 'fall') return 1;
		  if (termA == 'summer' && termB == 'spring') return -1;
		  if (termA == 'spring' && termB == 'summer') return 1;
    });
    instructors.sort(function(a, b) {
    	var instrA = a.toLowerCase().split(' ')[1], instrB = b.toLowerCase().split(' ')[1];
    	console.log(instrA);
    	console.log(instrB);
    	if (instrA < instrB) return -1;
   	 	if (instrA > instrB) return 1;
   	 	return 0;
    });

    $.each(terms, function(index, value) {
      $('#termDropDown').append('<option value="'+value+'">'+value+'</option>');
    });

    $.each(instructors, function(index, value) {
      $('#instructorDropDown').append('<option value="' + value + '">' + value +'</option>');
    });
  });

  /* When the user changes the term taken in the Course Search form */
  $('#termDropDown').change(function(){
	var savedInstructor = $('#instructorDropDown').val();
	var instructors = [];
	courseIds = [];
    if ($('#termDropDown').val() == "") {
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
        	if (!instructors.includes(jsonTermInstr[i].instructor) && jsonTermInstr[i].termOffered == $('#termDropDown').val()) {
        		instructors.push(jsonTermInstr[i].instructor);
        	}
        	if (jsonTermInstr[i].termOffered == $('#termDropDown').val() && (jsonTermInstr[i].instructor == savedInstructor || savedInstructor == "")) {
        		courseIds.push(jsonTermInstr[i].courseId);
        	}
        }
    }
    instructors.sort(function(a, b) {
    	var instrA = a.toLowerCase().split(' ')[1], instrB = b.toLowerCase().split(' ')[1];
    	console.log(instrA);
    	console.log(instrB);
    	if (instrA < instrB) return -1;
   	 	if (instrA > instrB) return 1;
   	 	return 0;
    });
    console.log("instructors: " + instructors);
    console.log("course ids: " + courseIds);
    $('#instructorDropDown').find('option').remove();
    $('#instructorDropDown').append('<option value="">All Professors/Instructors</option>').val('');
    $.each(instructors, function(index, value) {
        $('#instructorDropDown').append('<option value="' + value + '">' + value +'</option>');
    });
    if ($('#instructorDropDown option[value="' + savedInstructor + '"]').length > 0) {
        $('#instructorDropDown').val(savedInstructor);
    }
  });

  /* When the user changes instructor in the Course Search form */
  $('#instructorDropDown').change(function(){
	  var savedTerm = $('#termDropDown').val();
  	  var terms = [];
  	  courseIds = [];
	  if ($('#instructorDropDown').val() == "") {
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
	        	if (!terms.includes(jsonTermInstr[i].termOffered) && jsonTermInstr[i].instructor == $('#instructorDropDown').val()) {
	        		terms.push(jsonTermInstr[i].termOffered);
	        	}
	        	if (jsonTermInstr[i].instructor == $('#instructorDropDown').val() && (jsonTermInstr[i].termOffered == savedTerm || savedTerm == "")) {
	        		courseIds.push(jsonTermInstr[i].courseId);
	        	}
	        }

	  }
	  terms.sort(function(a,b) {
		  var termA = a.toLowerCase().split(' ')[0];
		  var termB = b.toLowerCase().split(' ')[0];
		  var yearA = parseInt(a.toLowerCase().split(' ')[1]);
		  var yearB = parseInt(b.toLowerCase().split(' ')[1]);
		  if (yearA > yearB) return -1;
		  if (yearA < yearB) return 1;
		  if (termA == 'fall' || termB == 'winter') return -1;
		  if (termA == 'winter' || termB == 'fall') return 1;
		  if (termA == 'summer' && termB == 'spring') return -1;
		  if (termA == 'spring' && termB == 'summer') return 1;
	  });
      console.log("terms: " + terms);
      console.log("course ids: " + courseIds);
	  $('#termDropDown').find('option').remove();
      $('#termDropDown').append('<option value="">All Terms</option>').val('');
      $.each(terms, function(index, value) {
          $('#termDropDown').append('<option value="' + value + '">' + value +'</option>');
      });
	  if ($('#termDropDown option[value="' + savedTerm + '"]').length > 0) {
		  $('#termDropDown').val(savedTerm);
	  }
  });

  /* Action when clicking the Search Course button on the landing page */
  $('#searchCourseBtn').click(function() {
    //var res = validateSearchForm();
    console.log("Search button pressed!");
    if(validateSearchForm()){
    	var dept = $('#subjectDropDown').val();
        var course = $('#courseDropDown').val();
        var term = $('#termDropDown').val() == "" ? "ALL" : $('#termDropDown').val();
        var instructor = $('#instructorDropDown').val() == "" ? "ALL" : $('#instructorDropDown').val();
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

  console.log(document.cookie);
  var studentNameCookie = getCookie();
  console.log(studentNameCookie);
  
  var reviewList = sendDataSync("", "getMyReviews", "ReviewController");
  console.log("MyReviews: " + reviewList);
  
  var reviews = jQuery.parseJSON(reviewList);
  
  //reviews.push(exampleRev1, exampleRev2, exampleRev3, exampleRev4);
  /*reviews.sort(function(a,b) {
		return b.datePosted - a.datePosted;
  });*/
  
  var numStars = 0;
  var starHTML = "";
  var numReviewsShown = 0;
  var numReviewsDeleted = 0;
  var row = 0;
  for (i = 0; i < reviews.length; i++) {
    numStars = reviews[i].rating;
    starHTML = "";
    for (j = 0; j < numStars; j++) {
      starHTML += "<img class=\"rating-star\" src=\"images/star-8x_full.png\">";
    }
	if (numReviewsShown < 3) {
		row = $("<tr id=\"row" + i + "YV\">");
		console.log("show " + i);
		numReviewsShown++;
	}
	else {
		console.log("hide " + i);
		row = $("<tr class=\"hidden-review\" id=\"row" + i + "YV\">");
	}
    row.append("<td>" + new Date(reviews[i].datePosted).toLocaleDateString() +"</td>");
    row.append("<td>" + starHTML + "</td>");
    row.append("<td>" + reviews[i].department.split("(")[1].slice(0,-1) + " " + reviews[i].courseNo + " (" + reviews[i].termOffered + ")</td>");
    row.append("<td>" + reviews[i].instructor + "</td>");
    row.append("<td><a href=\"#\" data-toggle=\"modal\" data-id=\"" + i + "\" data-target=\"#yourReviewModal\" class=\"viewReviewBtn\">View &nbsp</a><a href=\"#\" data-toggle=\"modal\" data-id=\"" + i + "\" data-target=\"#deleteReviewModal\" class=\"deleteReviewBtn\">Delete</a></td>");
    $("#recentReviewTable tbody").append(row);

  }
  if(numReviewsShown == 0){
	  $('#recentReviewTable').empty();
	  $('#recentReviewTable').append("No course reviews have been submitted.");		
  }
  
  manageSeeMoreReviewsBtn(reviews, numReviewsShown);

  $('#seeMoreReviewsBtn').click(function() {
    for (i = 0; i < 3; i++) {
    	console.log("show " + (i+numReviewsShown));
		$('#row' + (i+numReviewsShown) + 'YV').removeClass("hidden-review");
		//numReviewsShown++;
		//if (i+numReviewsShown == reviews.length) break;
    }
    numReviewsShown += 3;
    manageSeeMoreReviewsBtn(reviews, numReviewsShown);
  });

	$('.viewReviewBtn').click(function() {
		$('#chosenReviewId').val($(this).data('id'));
		console.log("chosen id: " + $('#chosenReviewId').val());
	});
	
	$('.deleteReviewBtn').click(function() {
		$('#chosenReviewId').val($(this).data('id'));
		console.log("chosen id: " + $('#chosenReviewId').val());
	});
	
	$('#noConfirmDeleteReviewBtn').click(function() {
		$('#deleteReviewModal').modal('hide');
	})
	
	$('#yesConfirmDeleteReviewBtn').click(function() {
		$('#deleteReviewModal').modal('hide');
		console.log("ID of review being deleted: " + reviews[parseInt($('#chosenReviewId').val())].reviewId);
		var status = sendDataSync("{'reviewId':'"+reviews[parseInt($('#chosenReviewId').val())].reviewId+"'}", 'deleteReview', 'ReviewController');
		console.log("delete review status = "+status);
		if (status == "JDBC_OK") {
			$('#row' + $('#chosenReviewId').val() + 'YV').addClass("hidden-review");
			numReviewsDeleted++;
			var removed = reviews.splice(parseInt($('#chosenReviewId').val()) - numReviewsDeleted, 1);
			console.log("removed: " + removed);
			console.log("now number of reviews: " + reviews.length);
			if (reviews.length == 0) {
				$('#recentReviewTable').empty();
				$('#recentReviewTable').append("No course reviews have been submitted.");	
			}
		}
		// code connecting to back-end to delete review
		//var status = sendDataSync('{"reviewId":'+parseInt($('#chosenReviewId').val())+'}','deleteReview','ReviewController');
	});

	$('#yourReviewModal').on('shown.bs.modal', function() {
		//var chosenReviewId = $('#chosenReviewId').val();
		var i = $('#chosenReviewId').val();
		//console.log("id: " + chosenReviewId);
		var subjectCode = reviews[i].department.split("(")[1].slice(0,-1);
		$('#modalTitleYV').html("Your Review for " + subjectCode + " " + reviews[i].courseNo);
		$('#yourNameYV').val(studentNameCookie);
		$('#yourNameYV').text(studentNameCookie);
		console.log(reviews[i].anonymous);
		$('#anonymousCheckYV').prop("checked", reviews[i].anonymous);
		$('#emptyTermYV').text(reviews[i].termOffered);
		$('#emptyInstructorYV').text(reviews[i].instructor);
		console.log("grade value: " + reviews[i].gradeReceived);
		console.log("grade shown: " + (reviews[i].gradeReceived == "N" ? "" : reviews[i].gradeReceived));
		$('#gradeDropdownYV').val((reviews[i].gradeReceived == "N" ? "" : reviews[i].gradeReceived));
		
		colorClickedStarRadioGroup(reviews[i].rating);
		$('#reviewTextYV').val((reviews[i].review == "*This student did not write a review in their submission.*" ? "[No review text]" : reviews[i].review));
	});
	
	$('#deleteReviewModal').on('shown.bs.modal', function() {
		var i = $('#chosenReviewId').val();
	});

	$('#closeYourReviewFormX').click(function() {
		console.log("Close Your Review X pressed!");
		$('#fillFormAlertYV').hide();
		$('#submitPendingAlertYV').hide();
		// if contents have changed, give confirmation alert
		//else:
		$('#confirmCloseReviewFormAlertYV').hide();
		$('#yourReviewModal').modal('hide');
	});

	$('#confirmCloseYourReviewBtn').click(function() {
		console.log("YV Close Review button pressed!");
		$('#fillFormAlertYV').hide();
		$('#submitPendingAlertYV').hide();
		// if contents have changed, give confirmation alert
		//else:
		$('#confirmCloseReviewFormAlertYV').hide();
		$('#yourReviewModal').modal('hide');
	});

	$('#noCloseReviewFormBtnYV').click(function() {
		console.log("YV Close Review Confirmation: No button pressed!");
		$('#confirmCloseReviewFormAlertYV').hide();
	});

	$('#yesCloseReviewFormBtnYV').click(function() {
		console.log("YV Close Review Confirmation: Yes button pressed!");
		$('#confirmCloseReviewFormAlertYV').hide();
		$('#yourReviewModal').modal('hide');
	});
});

function manageSeeMoreReviewsBtn(reviews, numReviewsShown) {
	console.log("total reviews: " + reviews.length);
	console.log("reviews shown: " + numReviewsShown);
	if (reviews.length - numReviewsShown >= 3) {
		$('#seeMoreReviewsBtn').html("See 3 More Reviews");
	}
	else if (reviews.length - numReviewsShown > 0 && reviews.length - numReviewsShown < 3) {
		$('#seeMoreReviewsBtn').html("See All Reviews");
	}
	else if (reviews.length - numReviewsShown <= 0) {
		$('#seeMoreReviewsBtn').css("display", "none");
	}
}

/* Course Search Form Validation */
function validateSearchForm() {
  //var form = document.forms["searchCourseForm"];
  var subjectField = $('#subjectDropDown').val();
  var courseNumberField = $('#courseDropDown').val();
  var termField = $('#termDropDown').val();
  var instructorField = $('#instructorDropDown').val();
  if (subjectField == "") {
    $("#fillFormAlert").html("Please select a subject.");
    $("#fillFormAlert").css("display", "block");
    return false;
  }
  else if (courseNumberField == "") {
    $("#fillFormAlert").html("Please select a course number.");
    $("#fillFormAlert").css("display", "block");
    return false;
  }
  /*else if (termField == null || termField == "") {
    $("#fillFormAlert").html("Please select a term!");
    $("#fillFormAlert").css("display", "block");
    return false;
  }
  else if (instructorField == null || instructorField == "") {
    $("#fillFormAlert").html("Please select an instructor!");
    $("#fillFormAlert").css("display", "block");
    return false;
  }*/
  $("#fillFormAlert").css("display", "none");
  return true;
}

function colorClickedStarRadioGroup(numChecked) {
	if (numChecked > 0) {
		for (i = 1; i <= numChecked; i++) {
			var starID = "#ratingStarChkYV" + i.toString();
			$(starID).css("opacity", "1");
		}
		for (i = numChecked + 1; i <= 5; i++) {
			var starID = "#ratingStarChkYV" + i.toString();
			$(starID).css("opacityYV", "0.2");
		}
	}
	else {
		for (i = 0; i <= 5; i++) {
			var starID = "#ratingStarChk" + i.toString();
			$(starID).css("opacity", "0.2");
		}
	}
}

function getCookie() {
	var value = "; " + document.cookie;
	var parts = value.split("; ");
	var name = "";
	console.log(parts);
	for (i = 0; i < parts.length; i++) {
		if (parts[i].substring(0,4) == "user") {
			name = parts[i].substring(5).replace('-', ' ');
			break;
		}
	}
	console.log(name);
	return name;
}
