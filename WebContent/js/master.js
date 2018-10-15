$(document).ready(function(){

	$("#header").load("header.html");

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

	//append course list to the course drop down
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

	//append term list to the term drop down
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

	//append instructor list to the term drop down
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

	//get Course ID
	$('#instructorDropDown').change(function(){
		var dept = $('#subjectDropDown').val();
		var course = $('#courseDropDown').val();
		var term = $('#termDropDown').val();
		var instructor = $('#instructorDropDown').val();

		var jsonData = '{"department":"'+dept+'","courseNo":"'+course+'","termOffered":"'+term+'","instructor":"'+instructor+'"}';

		var courseId = sendDataSync(jsonData,"fetchCourseID","CourseController");
		//alert("Course ID = "+courseId);

		var jsonCourseId = jQuery.parseJSON(courseId);
		$('#searchCourseBtn').val(jsonCourseId.courseId);
	});

	$('#searchCourseBtn').click(function() {
		var res = validateSearchForm();
		if(res){
			window.location.href = 'course_page.html?courseId='+$('#searchCourseBtn').val();
		}
	});



});

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
