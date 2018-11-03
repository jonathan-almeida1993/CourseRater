$(document).ready(function() {

  //get department list
  var departmentList = sendDataSync("","fetchDepartments","CourseController");
  console.log("Department List = "+departmentList);

  //append department list to the subject drop down

  $('#subjectDropDown').find('option').remove();
  $('#subjectDropDown').append('<option value="">Select Department</option>').val('');

  var jsonDept = jQuery.parseJSON(departmentList);
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

  var exampleRev1 = new Object();
  exampleRev1.name = "Omeed Habibelahian";
  exampleRev1.anonymous = 0;
  exampleRev1.courseNo = "CS 290";
  exampleRev1.instructor = "Christopher Scaffidi";
  exampleRev1.rating = 3;
  exampleRev1.term = "Winter 2018";
  exampleRev1.datePosted = "08/07/2018";
  exampleRev1.grade = "B";
  exampleRev1.reviewText = "";

  var exampleRev2 = new Object();
  exampleRev2.name = "Omeed Habibelahian";
  exampleRev2.courseNo = "CS 340";
  exampleRev2.instructor = "Christopher Scaffidi";
  exampleRev2.anonymous = 0;
  exampleRev2.rating = 2;
  exampleRev2.term = "Spring 2018";
  exampleRev2.datePosted = "07/15/2018";
  exampleRev2.grade = "B";
  exampleRev2.reviewText = "";

  var exampleRev3 = new Object();
  exampleRev3.name = "Omeed Habibelahian";
  exampleRev3.courseNo = "CS 463";
  exampleRev3.instructor = "D McGrath";
  exampleRev3.anonymous = 0;
  exampleRev3.rating = 4;
  exampleRev3.term = "Spring 2018";
  exampleRev3.datePosted = "07/13/2018";
  exampleRev3.grade = "A";
  exampleRev3.reviewText = "";

  var exampleRev4 = new Object();
  exampleRev4.name = "Omeed Habibelahian";
  exampleRev4.courseNo = "CS 462";
  exampleRev4.instructor = "D McGrath";
  exampleRev4.anonymous = 0;
  exampleRev4.rating = 4;
  exampleRev4.term = "Winter 2018";
  exampleRev4.datePosted = "03/21/2018";
  exampleRev4.grade = "A";
  exampleRev4.reviewText = "";

  var reviews = [];
  reviews.push(exampleRev1, exampleRev2, exampleRev3, exampleRev4);
  var numStars = 0;
  var starHTML = "";
  var numReviewsShown = 0;
  var row = 0;
  for (i = 0; i < 3; i++) {
    numStars = reviews[i].rating;
    starHTML = "";
    for (j = 0; j < numStars; j++) {
      starHTML += "<img class=\"rating-star\" src=\"images/star-8x_full.png\">";
    }
    row = $("<tr>");
    row.append("<td>" + reviews[i].datePosted+"</td>");
    row.append("<td>" + starHTML + "</td>");
    row.append("<td>" + reviews[i].courseNo + " (" + reviews[i].term + ")</td>");
    row.append("<td>" + reviews[i].instructor + "</td>");
    row.append("<td>" + "<span>View</span><br />" + "<span>Edit</span><br />" + "<span>Delete</span><br /></td>");
    $("#recentReviewTable tbody").append(row);

    numReviewsShown++;
  }
  manageSeeMoreReviewsBtn(reviews, numReviewsShown);

  $('#seeMoreReviewsBtn').click(function() {
    for (i = 0; i < 3; i++) {
      if (i+numReviewsShown < reviews.length) {
        numStars = reviews[i+numReviewsShown].rating;
        starHTML = "";
        for (j = 0; j < numStars; j++) {
          starHTML += "<img class=\"rating-star\" src=\"images/star-8x_full.png\">";
        }
        row = $("<tr>");
        row.append("<td>" + reviews[i+numReviewsShown].datePosted+"</td>");
        row.append("<td>" + starHTML + "</td>");
        row.append("<td>" + reviews[i+numReviewsShown].courseNo + " (" + reviews[i+numReviewsShown].term + ")</td>");
        row.append("<td>" + reviews[i+numReviewsShown].instructor + "</td>");
        row.append("<td>" + "<span>View</span><br />" + "<span>Edit</span><br />" + "<span>Delete</span><br /></td>");
        $("#recentReviewTable tbody").append(row);

        numReviewsShown++;
      }
    }
    manageSeeMoreReviewsBtn(reviews, numReviewsShown);
  });
});

function manageSeeMoreReviewsBtn(reviews, numReviewsShown) {
	if (reviews.length - numReviewsShown >= 3) {
		$('#seeMoreReviewsBtn').html("See 3 More");
	}
	else if (reviews.length - numReviewsShown > 0 && reviews.length - numReviewsShown < 3) {
		$('#seeMoreReviewsBtn').html("See All");
	}
	else if (reviews.length - numReviewsShown == 0) {
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
    $("#fillFormAlert").html("Please select a subject!");
    $("#fillFormAlert").css("display", "block");
    return false;
  }
  else if (courseNumberField == "") {
    $("#fillFormAlert").html("Please select a course number!");
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
