$(document).ready(function(){
	
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
		
		var jsonInstructor = jQuery.parseJSON(instructorList);
		
		$.each(jsonInstructor, function(index, value) {
			$('#instructorDropDown').append('<option value="'+value.instructor+'">'+value.instructor+'</option>');
		});
	});
	

	
});