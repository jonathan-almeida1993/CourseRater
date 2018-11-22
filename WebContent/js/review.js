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
	//console.log("courseIdURL: " + courseIdURL);
	var courseIds = courseIdURL.split('C');
	courseIds.splice(0,1);

	//console.log(document.cookie);
	var studentNameCookie = getCookie();
	//console.log(studentNameCookie);
	$('#studentName').val(studentNameCookie);
	$('#studentName').text(studentNameCookie);
	

	$("#header").load("header.html");
	var idInstructors = [];
	var idTerms = [];
	var courseDetailsJSON = [];
	var courseDetailsJSONList = [];
	$.each(courseIds, function(index, value) {
		var courseDetails = sendDataSync("{'courseId':'"+value+"'}","fetchCourseDetails","CourseController");
		//console.log("Course details: " + courseDetails);
		courseDetailsJSON = jQuery.parseJSON(courseDetails);
		courseDetailsJSONList.push(courseDetailsJSON);
		if (!idInstructors.includes(courseDetailsJSON.instructor)) {
			idInstructors.push(courseDetailsJSON.instructor);
		}
		if (!idTerms.includes(courseDetailsJSON.termOffered)) {
			idTerms.push(courseDetailsJSON.termOffered);
		}
		/*var subjectCode = courseDetailsJSON.department.split("(")[1].slice(0,-1);
		//console.log("Subject code: " + subjectCode);*/

	});
	/*var courseDetails = sendDataSync("{'courseId':'"+courseIdURL+"'}","fetchCourseDetails","CourseController");
	//console.log("Course details: " + courseDetails);
	var courseDetailsJSON = jQuery.parseJSON(courseDetails);*/
	var subjectCode = courseDetailsJSON.department.split("(")[1].slice(0,-1);
	//console.log("Subject code: " + subjectCode);
	var termHeader = (idTerms.length > 1) ? "All Terms" : courseDetailsJSON.termOffered;
	$('#courseNameHeader').text(subjectCode + " " + courseDetailsJSON.courseNo+ " - " +courseDetailsJSON.courseName + " (" + termHeader + ")");
	if (idInstructors.length > 1) {
		$('#courseInstructorHeader').text("All Professors/Instructors");
	}
	else {
		$('#courseInstructorHeader').text(courseDetailsJSON.instructor);
	}
	//$('#termDropdownRV').val(courseDetailsJSON.termOffered);
	$('#courseDesc').text(courseDetailsJSON.courseDesc);
	idTerms.sort(function(a,b) {
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
	$.each(idTerms, function(index, value) {
		$('#termDropdownRV').append('<option value="' + value + '">' + value + '</option>');
	});
	if (idTerms.length == 1) {
		$('#termDropdownRV').val(idTerms[0]);
		$('#termDropdownRV').attr('disabled', 'disabled');
	}

	idInstructors.sort(function(a, b) {
    	var instrA = a.toLowerCase().split(' ')[1], instrB = b.toLowerCase().split(' ')[1];
    	//console.log(instrA);
    	//console.log(instrB);
    	if (instrA < instrB) return -1;
   	 	if (instrA > instrB) return 1;
   	 	return 0;
    });
	$.each(idInstructors, function(index, value) {
		$('#instructorDropdownRV').append('<option value="' + value + '">' + value + '</option>');
	});
	if (idInstructors.length == 1) {
		$('#instructorDropdownRV').val(idInstructors[0]);
		$('#instructorDropdownRV').attr('disabled', 'disabled');
	}

	//get department list
	var departmentList = sendDataSync("","fetchDepartments","CourseController");
	//console.log("Department List = "+departmentList);

	//append department list to the subject drop down

	$('#subjectDropDownCP').find('option').remove();
	$('#subjectDropDownCP').append('<option value="">Select Subject</option>').val('');

	var jsonDept = jQuery.parseJSON(departmentList);
	jsonDept.sort(function(a, b) {
		var deptA = a.department.toLowerCase(), deptB = b.department.toLowerCase();
		if (deptA < deptB) return -1;
		if (deptA > deptB) return 1;
		return 0;
	});

	$.each(jsonDept, function(index, value) {
		$('#subjectDropDownCP').append('<option value="'+value.department+'">'+value.department+'</option>');
	});
	$('#subjectDropDownCP').val(courseDetailsJSON.department);

	var dept = $('#subjectDropDownCP').val();
	var jsonData = '{"department":"'+dept+'"}';
	var courseList = sendDataSync(jsonData,"fetchCourseNo","CourseController");
	//console.log("Course List = "+courseList);
	$('#courseDropDownCP').find('option').remove();
	$('#courseDropDownCP').append('<option value="">Select Course Number</option>').val('');
	var jsonCourse = jQuery.parseJSON(courseList);
	jsonCourse.sort(function(a, b) {
    	return a.courseNo - b.courseNo;
    });
	$.each(jsonCourse, function(index, value) {
		$('#courseDropDownCP').append('<option value="'+value.courseNo+'">'+value.courseNo+'</option>');
	});
	$('#courseDropDownCP').val(courseDetailsJSON.courseNo);

	var course = $('#courseDropDownCP').val();
	var jsonData = '{"department":"'+dept+'","courseNo":"'+course+'"}';

	var termInstrList = sendDataSync(jsonData,"fetchTermInstr","CourseController");
	//console.log("Term List = "+termInstrList);

	$('#termDropDownCP').find('option').remove();
	$('#termDropDownCP').append('<option value="">All Terms</option>').val('');
	$('#instructorDropDownCP').find('option').remove();
	$('#instructorDropDownCP').append('<option value="">All Professors/Instructors</option>').val('');
	var jsonTermInstr = jQuery.parseJSON(termInstrList);
	//console.log("jsonTermInstr: " + termInstrList);

	var terms = [];
	var instructors = [];
	//console.log("course IDs: [" + courseIds + "]");
	$('#termDropDownCP').val('');
	$('#instructorDropDownCP').val('');
	//console.log(termHeader);
	//console.log($('#courseInstructorHeader').text());
	if (termHeader == "All Terms" && $('#courseInstructorHeader').text() == "All Professors/Instructors") {
		//console.log("both term and instructor empty");
		$.each(jsonTermInstr, function(index, value) {
			if (!terms.includes(value.termOffered)) {
				terms.push(value.termOffered);
			}
			if (!instructors.includes(value.instructor)) {
				instructors.push(value.instructor);
			}
		});
	}
	else if (termHeader != "All Terms" && $('#courseInstructorHeader').text() == "All Professors/Instructors") {
		//console.log("term filled, instructor empty");
		$.each(jsonTermInstr, function(index, value) {
			if (!terms.includes(value.termOffered)) {
				terms.push(value.termOffered);
				//console.log("terms now: " + terms);
			}
		});
		$.each(jsonTermInstr, function(index, value) {
			if (terms.includes(value.termOffered) && !instructors.includes(value.instructor)) {
				instructors.push(value.instructor);
				//console.log("instructors now: " + instructors);
			}
		});
	}
	else if (termHeader == "All Terms" && $('#courseInstructorHeader').text() != "All Professors/Instructors") {
		//console.log("term empty, instructor filled");
		$.each(jsonTermInstr, function(index, value) {
			if (!instructors.includes(value.instructor)) {
				instructors.push(value.instructor);
				//console.log("terms now: " + terms);
			}
		});
		$.each(jsonTermInstr, function(index, value) {
			if (instructors.includes(value.instructor) && !terms.includes(value.termOffered)) {
				terms.push(value.termOffered);
				//console.log("instructors now: " + instructors);
			}
		});
	}
	else if (termHeader != "All Terms" && $('#courseInstructorHeader').text() != "All Professors/Instructors") {
		$.each(jsonTermInstr, function(index, value) {
			if (!terms.includes(value.termOffered)) {
				terms.push(value.termOffered);
				//console.log("terms now: " + terms);
			}
		});
		$.each(jsonTermInstr, function(index, value) {
			if (terms.includes(value.termOffered) && !instructors.includes(value.instructor)) {
				instructors.push(value.instructor);
				//console.log("instructors now: " + instructors);
			}
		});
	}
	/*$.each(jsonTermInstr, function(index, value) {
		console.log(value.courseId);

		console.log(value.termOffered);
		console.log(value.instructor);
		if (courseIds.includes(value.courseId.toString())) {
			if (!terms.includes(value.termOffered)) {
				terms.push(value.termOffered);
				console.log("terms now: " + terms);
			}
		}
	});
	$.each(jsonTermInstr, function(index, value) {
		if (terms.includes(value.termOffered) && !instructors.includes(value.instructor)) {
			instructors.push(value.instructor);
			console.log("instructors now: " + instructors);
		}
	});*/
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
    	//console.log(instrA);
    	//console.log(instrB);
    	if (instrA < instrB) return -1;
   	 	if (instrA > instrB) return 1;
   	 	return 0;
    });

	$.each(terms, function(index, value) {
		$('#termDropDownCP').append('<option value="'+value+'">'+value+'</option>');
	});

	$.each(instructors, function(index, value) {
		$('#instructorDropDownCP').append('<option value="' + value + '">' + value +'</option>');
	});

	//console.log(terms);
	$('#termDropDownCP').val((idTerms.length > 1) ? "" : courseDetailsJSON.termOffered);

	//console.log(instructors);
	$('#instructorDropDownCP').val((idInstructors.length > 1) ? "" : courseDetailsJSON.instructor);

	var savedSubject = courseDetailsJSON.department;
	var savedCourseNo = courseDetailsJSON.courseNo;
	var savedTerm = (idTerms.length > 1) ? "" : courseDetailsJSON.termOffered;
	var savedInstructor = (idInstructors.length > 1) ? "" : courseDetailsJSON.instructor;
	//checkSavedQuery(savedSubject, savedCourseNo, savedTerm, savedInstructor);

	/* When the user changes the subject in the Course Search form */
	$('#subjectDropDownCP').change(function(){
		var dept = $('#subjectDropDownCP').val();
		var jsonData = '{"department":"'+dept+'"}';
		var courseList = sendDataSync(jsonData,"fetchCourseNo","CourseController");
		//console.log("Course List = "+courseList);

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
		checkSavedQuery(savedSubject, savedCourseNo, savedTerm, savedInstructor);
	});

	/* When the user changes the course number in the Course Search form */
	$('#courseDropDownCP').change(function(){
		var dept = $('#subjectDropDownCP').val();
		var course = $('#courseDropDownCP').val();
		var jsonData = '{"department":"'+dept+'","courseNo":"'+course+'"}';

		var termInstrList = sendDataSync(jsonData,"fetchTermInstr","CourseController");
		//console.log("Term List = "+termInstrList);
		//console.log("course val: " + course);
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
		//console.log("jsonTermInstr: " + termInstrList);

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
	    	//console.log(instrA);
	    	//console.log(instrB);
	    	if (instrA < instrB) return -1;
	   	 	if (instrA > instrB) return 1;
	   	 	return 0;
	    });

		$.each(terms, function(index, value) {
			$('#termDropDownCP').append('<option value="'+value+'">'+value+'</option>');
		});

		$.each(instructors, function(index, value) {
			$('#instructorDropDownCP').append('<option value="' + value + '">' + value +'</option>');
		});
		checkSavedQuery(savedSubject, savedCourseNo, savedTerm, savedInstructor);
	});

	/* When the user changes the term taken in the Course Search form */
	$('#termDropDownCP').change(function(){
		var chosenInstructor = $('#instructorDropDownCP').val();
		var instructors = [];
		courseIds = [];
		if ($('#termDropDownCP').val() == "") {
			for (i = 0; i < jsonTermInstr.length; i++) {
				if (!instructors.includes(jsonTermInstr[i].instructor)) {
					instructors.push(jsonTermInstr[i].instructor);
				}
				if (jsonTermInstr[i].instructor == chosenInstructor || chosenInstructor == "") {
					courseIds.push(jsonTermInstr[i].courseId);
				}
			}
		}
		else {
			for (i = 0; i < jsonTermInstr.length; i++) {
				//console.log("term offered: " + jsonTermInstr[i].termOffered);
				if (!instructors.includes(jsonTermInstr[i].instructor) && jsonTermInstr[i].termOffered == $('#termDropDownCP').val()) {
					instructors.push(jsonTermInstr[i].instructor);
				}
				if (jsonTermInstr[i].termOffered == $('#termDropDownCP').val() && (jsonTermInstr[i].instructor == chosenInstructor || chosenInstructor == "")) {
					courseIds.push(jsonTermInstr[i].courseId);
				}
			}
		}
		//console.log("instructors: " + instructors);
		//console.log("course ids: " + courseIds);
		$('#instructorDropDownCP').find('option').remove();
		$('#instructorDropDownCP').append('<option value="">All Professors/Instructors</option>').val('');
		$.each(instructors, function(index, value) {
			$('#instructorDropDownCP').append('<option value="' + value + '">' + value +'</option>');
		});
		if ($('#instructorDropDownCP option[value="' + chosenInstructor + '"]').length > 0) {
			$('#instructorDropDownCP').val(chosenInstructor);
		}
		if (!instructors.includes(chosenInstructor)) {
			$('#instructorDropDownCP').val("");
		}
		checkSavedQuery(savedSubject, savedCourseNo, savedTerm, savedInstructor);
	});

	/* When the user changes instructor in the Course Search form */
	$('#instructorDropDownCP').change(function(){
		var chosenTerm = $('#termDropDownCP').val();
		var terms = [];
		courseIds = [];
		if ($('#instructorDropDownCP').val() == "") {
			for (i = 0; i < jsonTermInstr.length; i++) {
				if (!terms.includes(jsonTermInstr[i].termOffered)) {
					terms.push(jsonTermInstr[i].termOffered);
				}
				if (jsonTermInstr[i].termOffered == chosenTerm || chosenTerm == "") {
					courseIds.push(jsonTermInstr[i].courseId);
				}
			}
		}
		else {
			for (i = 0; i < jsonTermInstr.length; i++) {
				//console.log("instructor: " + jsonTermInstr[i].instructor);
				if (!terms.includes(jsonTermInstr[i].termOffered) && jsonTermInstr[i].instructor == $('#instructorDropDownCP').val()) {
					terms.push(jsonTermInstr[i].termOffered);
				}
				if (jsonTermInstr[i].instructor == $('#instructorDropDownCP').val() && (jsonTermInstr[i].termOffered == chosenTerm || chosenTerm == "")) {
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
		//console.log("terms: " + terms);
		//console.log("course ids: " + courseIds);
		$('#termDropDownCP').find('option').remove();
		$('#termDropDownCP').append('<option value="">All Terms</option>').val('');
		$.each(terms, function(index, value) {
			$('#termDropDownCP').append('<option value="' + value + '">' + value +'</option>');
		});
		if ($('#termDropDownCP option[value="' + chosenTerm + '"]').length > 0) {
			$('#termDropDownCP').val(chosenTerm);
		}
		if (!terms.includes(chosenTerm)) {
			$('#termDropDownCP').val("");
		}
		//console.log("term dropdown: "+ $('#termDropDownCP').placeholder);
		checkSavedQuery(savedSubject, savedCourseNo, savedTerm, savedInstructor);
	});

	/* Action when clicking the Search Course button on the landing page */
	$('#searchCourseBtnCP').click(function() {
		//var res = validateSearchForm();
		//console.log("Search button pressed!");
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
			//console.log("json data: " + jsonData);

			// get Course ID
			//var courseId = sendDataSync(jsonData,"fetchCourseID","CourseController");
			//var jsonCourseId = jQuery.parseJSON(courseId);
			//$('#searchCourseBtn').val(jsonCourseId.courseId);
			window.location.href = 'course_page.html?courseId=' + courseIdParam;
		}
	});
	
	/* When the user changes the term taken in the Review form */
	$('#termDropdownRV').change(function(){
		var pickedInstructor = $('#instructorDropdownRV').val();
		var instructors = [];
		courseIds = [];
		if ($('#termDropdownRV').val() == "") {
			for (i = 0; i < jsonTermInstr.length; i++) {
				if (!instructors.includes(jsonTermInstr[i].instructor)) {
					instructors.push(jsonTermInstr[i].instructor);
				}
				if (jsonTermInstr[i].instructor == pickedInstructor || pickedInstructor == "") {
					courseIds.push(jsonTermInstr[i].courseId);
				}
			}
		}
		else {
			for (i = 0; i < jsonTermInstr.length; i++) {
				console.log("term offered: " + jsonTermInstr[i].termOffered);
				if (!instructors.includes(jsonTermInstr[i].instructor) && jsonTermInstr[i].termOffered == $('#termDropdownRV').val()) {
					instructors.push(jsonTermInstr[i].instructor);
				}
				if (jsonTermInstr[i].termOffered == $('#termDropdownRV').val() && (jsonTermInstr[i].instructor == pickedInstructor || pickedInstructor == "")) {
					courseIds.push(jsonTermInstr[i].courseId);
				}
			}
		}
		console.log("instructors: " + instructors);
		console.log("course ids: " + courseIds);
		$('#instructorDropdownRV').find('option').remove();
		$('#instructorDropdownRV').append('<option value="">All Professors/Instructors</option>').val('');
		$.each(instructors, function(index, value) {
			$('#instructorDropdownRV').append('<option value="' + value + '">' + value +'</option>');
		});
		if ($('#instructorDropdownRV option[value="' + pickedInstructor + '"]').length > 0) {
			$('#instructorDropdownRV').val(pickedInstructor);
		}
		if (!instructors.includes(pickedInstructor)) {
			$('#instructorDropdownRV').val("");
		}
	});

	/* When the user changes instructor in the Review form */
	$('#instructorDropdownRV').change(function(){
		var pickedTerm = $('#termDropdownRV').val();
		var terms = [];
		courseIds = [];
		if ($('#instructorDropdownRV').val() == "") {
			for (i = 0; i < jsonTermInstr.length; i++) {
				if (!terms.includes(jsonTermInstr[i].termOffered)) {
					terms.push(jsonTermInstr[i].termOffered);
				}
				if (jsonTermInstr[i].termOffered == pickedTerm || pickedTerm == "") {
					courseIds.push(jsonTermInstr[i].courseId);
				}
			}
		}
		else {
			for (i = 0; i < jsonTermInstr.length; i++) {
				console.log("instructor: " + jsonTermInstr[i].instructor);
				if (!terms.includes(jsonTermInstr[i].termOffered) && jsonTermInstr[i].instructor == $('#instructorDropdownRV').val()) {
					terms.push(jsonTermInstr[i].termOffered);
				}
				if (jsonTermInstr[i].instructor == $('#instructorDropdownRV').val() && (jsonTermInstr[i].termOffered == pickedTerm || pickedTerm == "")) {
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
		$('#termDropdownRV').find('option').remove();
		$('#termDropdownRV').append('<option value="">All Terms</option>').val('');
		$.each(terms, function(index, value) {
			$('#termDropdownRV').append('<option value="' + value + '">' + value +'</option>');
		});
		if ($('#termDropdownRV option[value="' + pickedTerm + '"]').length > 0) {
			$('#termDropdownRV').val(pickedTerm);
		}
		if (!terms.includes(pickedTerm)) {
			$('#termDropdownRV').val("");
		}
		console.log("term dropdown: "+ $('#termDropdownRV').placeholder);
	});

	//grab all reviews based on the course Ids, and put them into a list called reviesList
	var reviews = [];	// array that will hold all the reviews for the class

	$.each(courseIds, function(index, id) {
		var jsonData = '{"courseId":"' + id + '"}';
		var reviewList = sendDataSync(jsonData, "getCourseReviews", "ReviewController");
//		console.log("reviews: " + reviewList);

		//parse the string for each review and put them into correct field of a review object
		var jsonReviews = jQuery.parseJSON(reviewList);

		//add the review object into the review list and display them
		$.each(jsonReviews, function(index, value) {
			value.thumbsUp = 0;
			value.thumbsDown = 0;
			reviews.push(value);
		});
	});
	
	reviews.sort(function(a,b) {
		return b.datePosted - a.datePosted;
	});

	updateAverageRating(reviews);

	displayReviews(reviews, jsonTermInstr);
	
	$('#reviewSortSelect').val("Date");
	$('#reviewSortSelect').change(function() {
		if ($(this).val() == "Date") {
			console.log("sort reviews by date");
			reviews.sort(function(a,b) {
				return b.datePosted - a.datePosted;
			});
			console.log("reviews now sorted by date");
		}
		else if ($(this).val() == "Usefulness") {
			console.log("sort reviews by usefulness");
			reviews.sort(function(a,b) {
				return (b.thumbsUp - b.thumbsDown) - (a.thumbsUp - a.thumbsDown);
			});
			console.log("reviews now sorted by usefulness");
		}
		displayReviews(reviews, jsonTermInstr);
		console.log("resorted reviews now displayed");
	});
	
	$('.reviewThumbsUpBtn').click(function() {
		var reviewIndex = $(this).data('id')[0];
		console.log("liked review index: " + reviewIndex);
		console.log("liked review ID: " + reviews[reviewIndex].reviewId);
		if (!$(this).hasClass('thumbs-selected')) {
			reviews[reviewIndex].thumbsUp++;
			$(this).addClass('thumbs-selected');
			$(this).children('img').css('opacity', '1');
			$(this).children('span').html(reviews[reviewIndex].thumbsUp);
		}
		if ($('a[data-id="'+reviewIndex+'reviewThumbsDownBtn"]').hasClass('thumbs-selected')) {
			reviews[reviewIndex].thumbsDown--;
			$('a[data-id="'+reviewIndex+'reviewThumbsDownBtn"]').removeClass('thumbs-selected');
			$('a[data-id="'+reviewIndex+'reviewThumbsDownBtn"]').children('img').css('opacity', '0.3');
			$('a[data-id="'+reviewIndex+'reviewThumbsDownBtn"]').children('span').html(reviews[reviewIndex].thumbsDown);
		}
	});
	
	$('.reviewThumbsDownBtn').click(function() {
		var reviewIndex = $(this).data('id')[0];
		console.log("liked review index: " + reviewIndex);
		console.log("liked review ID: " + reviews[reviewIndex].reviewId);
		if (!$(this).hasClass('thumbs-selected')) {
			reviews[reviewIndex].thumbsDown++;
			$(this).addClass('thumbs-selected');
			$(this).children('img').css('opacity', '1');
			$(this).children('span').html(reviews[reviewIndex].thumbsDown);
		}
		if ($('a[data-id="'+reviewIndex+'reviewThumbsUpBtn"]').removeClass('thumbs-selected')) {
			reviews[reviewIndex].thumbsUp--;
			$('a[data-id="'+reviewIndex+'reviewThumbsUpBtn"]').removeClass('thumbs-selected');
			$('a[data-id="'+reviewIndex+'reviewThumbsUpBtn"]').children('img').css('opacity', '0.3');
			$('a[data-id="'+reviewIndex+'reviewThumbsUpBtn"]').children('span').html(reviews[reviewIndex].thumbsUp);
		}
	});

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
		//console.log("Rating: " + $('#ratingCheckbox').val());
		colorClickedStarRadioGroup(1);
	});

	$('#ratingStarChk2').click(function() {
		$('#ratingCheckbox').val("2");
		//console.log("Rating: " + $('#ratingCheckbox').val());
		colorClickedStarRadioGroup(2);
	});

	$('#ratingStarChk3').click(function() {
		$('#ratingCheckbox').val("3");
		//console.log("Rating: " + $('#ratingCheckbox').val());
		colorClickedStarRadioGroup(3);
	});

	$('#ratingStarChk4').click(function() {
		$('#ratingCheckbox').val("4");
		//console.log("Rating: " + $('#ratingCheckbox').val());
		colorClickedStarRadioGroup(4);
	});

	$('#ratingStarChk5').click(function() {
		$('#ratingCheckbox').val("5");
		//console.log("Rating: " + $('#ratingCheckbox').val());
		colorClickedStarRadioGroup(5);

	});

	/*This prevents user from submitting multiple 
	 * reviews for a given course id, which is unique for a 
	 * given combination of term and instructor.*/
	
	$('#instructorDropdownRV, #termDropdownRV').change(function(){
		//console.log(reviews);
		//console.log(courseDetailsJSONList);
		
		/*First, find the course ID for the selected combination of 
		 * instructor and term drop downs*/
		$('#submitReviewBtn').removeAttr('disabled');
		var currentInstructor = $('#instructorDropdownRV').val();
		var currentTerm = $('#termDropdownRV').val();
		var currentCourseId;
		
		$(courseDetailsJSONList).each(function(idx, obj){
			if(obj.instructor == currentInstructor && obj.termOffered == currentTerm){
				//alert(obj.courseId);
				currentCourseId = obj.courseId;
				return;
			}
		});
		
		/*Second, check if there exists a review by the logged in user for the above course ID.*/
		var cookieInfo = document.cookie.split(';');
		if(!cookieInfo[0].trim().startsWith('onid')){
			var temp = cookieInfo[1];
			cookieInfo[1] = cookieInfo[0];
			cookieInfo[0] = temp;
		}
		cookieInfo[0] = cookieInfo[0].split('=')[1];//contains onid
		cookieInfo[1] = cookieInfo[1].split('=')[1];//contains user name
		var cookieFirstName = cookieInfo[1].split('-')[0];
		var cookieLastName = cookieInfo[1].split('-')[1];
		var reviewExists = false;
		
		$(reviews).each(function(idx, obj){
			if(obj.courseId == currentCourseId){
				if(obj.onid == cookieInfo[0] || obj.firstName == cookieFirstName || obj.lastName == cookieLastName){
					reviewExists = true;
				}
			}
		});
//		console.log("course Id="+currentCourseId);
//		console.log("Review Exists? "+reviewExists);
		
		if(reviewExists){
			$('#submitReviewBtn').attr('disabled','disabled');
			alert("You have already submitted a review for this course.");
		}
		
	});
	/*This removes the disabled button property on modal hide to prevent 
	 * issues in other place. Trying to keep this functionality independent.*/
	$("#submitReviewModal").on("hidden.bs.modal", function () {
		$('#submitReviewBtn').removeAttr('disabled');
	});
	
	/*Incase of having disabled term and instructor dropdowns, trigger the onchange event,
	 * to prevent multiple reviews from being submitted.*/
	$("#submitReviewModal").on("show.bs.modal", function () {
		$('#instructorDropdownRV').change();
	});
	
	/*Multiple reviews prevention ends*/
	
	/* Action when clicking the Submit Review button */
	$('#submitReviewBtn').click(function() {
		
		if (validateSubmitReviewForm()) {
			var today = new Date();
			//console.log(today);
			var dd = today.getDate();
			var mm = today.getMonth() + 1;
			var yyyy = today.getFullYear();
			if (dd < 10) dd = '0' + dd;
			if (mm < 10) mm = '0' + mm;

			var newReview = new Object();
			//newReview.courseId = courseIds[0];
			$.each(jsonTermInstr, function(index, value) {
				if ($('#termDropdownRV').val() == value.termOffered && $('#instructorDropdownRV').val() == value.instructor) {
					newReview.courseId = value.courseId;
					return;
				}
			});
			//console.log("course ID: " + courseIds[0])
			newReview.name = $('#studentName').val();
			//console.log($('#anonymousCheck').checked);
			var anonymousCheck = document.getElementById("anonymousCheck");

			newReview.anonymous = anonymousCheck.checked;
			newReview.rating = $('#ratingCheckbox').val();
			newReview.term = $('#termDropdownRV').val();
			newReview.datePosted = today.getTime();
			//console.log("ms: " + newReview.datePosted);
			today = mm + '/' + dd + '/' + yyyy;
			if ($('#gradeDropdown').val() == "") {
				newReview.gradeReceived = "N";
			}
			else newReview.gradeReceived = $('#gradeDropdown').val();
			if ($('#reviewText').val() == "") {
				newReview.review = "*This student did not write a review in their submission.*"
			}
			else newReview.review = $('#reviewText').val();

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
				"<span id=\"newReviewName\">" +
				(newReview.anonymous ? "Anonymous Student" : newReview.name) +
				"</span>" +
				"</label><br>" +
				"<label>" +
				"<strong>Rating: </strong>" +
				"<span id=\"newReviewRating\">" +
				starHTML +
				"</span>" +
				"</label><br>" +
				"<label>" +
				"<strong>Term: </strong>" +
				"<span id=\"newReviewTerm\">" +
				newReview.term +
				"</span>" +
				"</label><br>" +
				"<label>" +
				"<strong>Date Posted: </strong>" +
				"<span id=\"newReviewDate\">" +
				today +
				"</span>" +
				"</label><br>" +
				(newReview.gradeReceived == "N" ? "" : ("<label>" +
						"<strong>Grade Received: </strong>" +
						"<span id=\"newReviewGrade\">" +
						newReview.gradeReceived +
						"</span>" +
						"</label>")) +
				"</div>" +
				"<div class=\"col-md-7\" id=\"newReviewText\">" +
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
					//console.log("border getting brighter");
				}
			});

			setTimeout(function() {
				$({alpha:1}).animate({alpha:0}, {
					duration: 1000,
					step: function() {
						$('#newReview').css("border-color", "rgba(177, 223, 187, " + this.alpha + ")");
						//console.log("border getting lighter");
					}
				});
			}, 5000);

			$('#submitReviewCourseId').val(courseIds[0]);
			//var reviewJson = newReview.serializeJSON();
			//console.log("form: " + $('#submitReviewForm'));
			//console.log(JSON.stringify(newReview));
			var status = sendDataSync(JSON.stringify(newReview),"addReview","ReviewController");
			if (status == "JDBC_OK") {
				$('#submitPendingAlert').hide();
				$('#submitReviewModal').modal("hide");
			}
			$('#createReviewBtn').attr('disabled', 'disabled');
			$('#createReviewBtn').text("Review Submitted!");
			$('#confirmCloseReviewFormAlert').hide();
			$('#gradeDropdown').val("");
			$('#ratingCheckbox').val("");
			$('#reviewText').text("");
		}
	});

	/*$('#dismissSubmitSuccessAlert').click(function () {
	var url = new URL(window.location.href);
	var courseIdURL = url.searchParams.get("courseId");
	window.location.href = "course_page.html?courseId="+courseIdURL;
});*/

$('#confirmCloseReviewBtn').click(function() {
	//console.log("Close Review button pressed!");
	//console.log("grade: '" + $('#gradeDropdown').val() + "'");
	//console.log("rating: '" + $('#gradeDropdown').val() + "'");
	//console.log("grade: '" + $('#gradeDropdown').val() + "'");

	$('#fillFormAlertRV').hide();
	$('#submitPendingAlert').hide();
	$('#submitSuccessAlert').hide();
	//console.log($('#termDropdownRV').val());
	
	if ($('#termDropdownRV').val() != savedTerm || $('#instructorDropdownRV').val() != savedInstructor || $('#gradeDropdown').val() != "" || $('#ratingCheckbox').val() != "" || $('#reviewText').text() != "") {
		$('#confirmCloseReviewFormAlert').show();
	}
	else {
		var anonymousCheck = document.getElementById("anonymousCheck");
		anonymousCheck.checked = false;
		$("#anonymousMsg").css("display", "none");
		$('#confirmCloseReviewFormAlert').hide();
		$('#submitReviewModal').modal('hide');
	}
});

$('#closeReviewFormX').click(function() {
	//console.log("Close Review X pressed!");
	$('#fillFormAlertRV').hide();
	$('#submitPendingAlert').hide();
	$('#submitSuccessAlert').hide();
	if ($('#termDropdownRV').val() != savedTerm || $('#instructorDropdownRV').val() != savedInstructor || $('#gradeDropdown').val() != "" || $('#ratingCheckbox').val() != "" || $('#reviewText').text() != "") {
		$('#confirmCloseReviewFormAlert').show();
	}
	else {
		var anonymousCheck = document.getElementById("anonymousCheck");
		anonymousCheck.checked = false;
		$("#anonymousMsg").css("display", "none");
		$('#confirmCloseReviewFormAlert').hide();
		$('#submitReviewModal').modal('hide');
	}
});

$('#noCloseReviewFormBtn').click(function() {
	//console.log("Close Review Confirmation: No button pressed!");
	$('#confirmCloseReviewFormAlert').hide();
});

/*$('#yesCloseReviewFormBtn').click(function() {
	console.log("Close Review Confirmation: Yes button pressed!");
	$('#confirmCloseReviewFormAlert').hide();
	$('#termDropdownRV').val(savedTerm);
	$('#instructorDropdownRV').val(savedInstructor);
	$('#gradeDropdown').val("");
	$('#ratingCheckbox').val("");
	$('#reviewText').text("");
	$('#submitReviewModal').modal('hide');
});*/

$('#noCloseReviewFormBtn').click(function() {
	//console.log("Close Review Confirmation: No button pressed!");
	$('#confirmCloseReviewFormAlert').hide();
});

$('#yesCloseReviewFormBtn').click(function() {
	//console.log("Close Review Confirmation: Yes button pressed!");
	var anonymousCheck = document.getElementById("anonymousCheck");
	anonymousCheck.checked = false;
	$('#confirmCloseReviewFormAlert').hide();
	$('#termDropdownRV').val(savedTerm);
	$('#instructorDropdownRV').val(savedInstructor);
	$('#gradeDropdown').val("");
	$('#reviewText').text("");
	$('#ratingStarChk1').css("opacity", "0.2");
	$('#ratingStarChk2').css("opacity", "0.2");
	$('#ratingStarChk3').css("opacity", "0.2");
	$('#ratingStarChk4').css("opacity", "0.2");
	$('#ratingStarChk5').css("opacity", "0.2");
	$('#ratingStarValue').text("");
	$('#ratingCheckbox').val("");
	$('#submitReviewModal').modal('hide');
});

});

function displayReviews(reviews, jsonTermInstr) {
	var starHTML = "";
	numStars = 0;
	for (i = 0; i < reviews.length; i++) {
		numStars = reviews[i].rating;
		starHTML = "";
		for (j = 0; j < numStars; j++) {
			starHTML += "<img class=\"rating-star\" src=\"images/star-8x_full.png\">";
		}
		if (reviews[i].anonymous) {
			reviews[i].onid = "Anonymous Student";
		}
		if (reviews[i].review == "") {
			reviews[i].review = "*This student did not write a review in their submission.*";
		}
		for (j = 0; j < jsonTermInstr.length; j++) {
			if (reviews[i].courseId == jsonTermInstr[j].courseId) {
				reviews[i].term = jsonTermInstr[j].termOffered;
				break;
			}
		}
		//console.log("date ms: " + reviews[i].datePosted);

		$('#reviews').append(
			"<div class=\"modal-body row\" id=\"review" + (i+1) + "\">" +
			"<div class=\"col-md-3\">" +
			"<label>" +
			"<strong>Posted By: </strong>" +
			"<span id=\"review" + (i+1) + "Name\">" +
			(reviews[i].anonymous ? "Anonymous Student" : reviews[i].firstName + " " + reviews[i].lastName) +
			"</span>" +
			"</label><br>" +
			"<label>" +
			"<strong>Rating: </strong>" +
			"<span id=\"review" + (i+1) + "Rating\">" +
			starHTML +
			"</span>" +
			"</label><br>" +
			"<label>" +
			"<strong>Term: </strong>" +
			"<span id=\"review" + (i+1) + "Term\">" +
			reviews[i].term +
			"</span>" +
			"</label><br>" +
			"<label>" +
			"<strong>Date Posted: </strong>" +
			"<span id=\"review" + (i+1) + "Date\">" +
			new Date(reviews[i].datePosted).toLocaleDateString() +
			"</span>" +
			"</label><br>" +
			(reviews[i].gradeReceived == "N" ? "" : ("<label>" +
			"<strong>Grade Received: </strong>" +
			"<span id=\"review" + (i+1) + "Grade\">" +
			reviews[i].gradeReceived +
			"</span>" +
			"</label>")) +
			"</div>" +
			"<div class=\"col-md-7\" id=\"review" + (i+1) + "Text\">" +
			reviews[i].review +
			"</div>" +
			"</div>" +
//			Usefulness thumbs up/down is here
			"<div class=\"modal-body-row\">" +
		    "<label class=\"col-md-2\">Was this review useful?</label>" +
		    //"<span class=\"offset-md-1\">0</span>" +
		    "<a class=\"col-md-1 offset-md-1 reviewThumbsUpBtn\" data-id=\"" + i + "reviewThumbsUpBtn\">" +
		    "<span class=\"num-thumbs-up\">" + 
		    reviews[i].thumbsUp +
		    "</span>" + "\t" +
		    "<img class=\"thumbs-image\" src=\"images/thumb-up-8x.png\">&nbsp Yes" +
		    "</a>" +
		    //"<span class=\"offset-md-1\">0</span>" +
		    "<a class=\"col-md-1 offset-md-1 reviewThumbsDownBtn\" data-id=\"" + i + "reviewThumbsDownBtn\">" +
		    "<span class=\"num-thumbs-down\">" + 
		    reviews[i].thumbsDown + 
		    "</span>" + "\t" +
		    "<img class=\"thumbs-image\" src=\"images/thumb-down-8x.png\">&nbsp No" +
		    "</a>" +
		    "</div>" +
			"<div class=\"dropdown-divider\"></div>"
		);
	}
}

/* Submit Review Form Validation */
function validateSubmitReviewForm() {
	$("#confirmCloseReviewFormAlert").hide();
	$('#submitPendingAlert').hide();
	if ($('#termDropdownRV').val() == "" || $('#termDropdownRV').val() == null) {
		$("#fillFormAlertRV").html("Please select a term.");
		$("#fillFormAlertRV").show();
		return false;
	}
	else if ($('#instructorDropdownRV').val() == "" || $('#instructorDropdownRV').val() == null) {
		$("#fillFormAlertRV").html("Please select a professor/instructor.");
		$("#fillFormAlertRV").show();
		return false;
	}
	else if ($('#ratingCheckbox').val() == "" || $('#ratingCheckbox').val() == null) {
		$("#fillFormAlertRV").html("Please give the course a rating.");
		$("#fillFormAlertRV").show();
		return false;
	}
	else {
		$("#fillFormAlertRV").hide();
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
	//console.log(anonymousCheck.checked);
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

/* Course Search Form Validation */
function validateSearchForm() {
	//var form = document.forms["searchCourseForm"];
	var subjectField = $('#subjectDropDownCP').val();
	var courseNumberField = $('#courseDropDownCP').val();
	var termField = $('#termDropDownCP').val();
	var instructorField = $('#instructorDropDownCP').val();
	if (subjectField == null || subjectField == "") {
		$("#fillFormAlertCP").html("Please select a subject.");
		$("#fillFormAlertCP").css("display", "block");
		return false;
	}
	else if (courseNumberField == null || courseNumberField == "") {
		$("#fillFormAlertCP").html("Please select a course number.");
		$("#fillFormAlertCP").css("display", "block");
		return false;
	}

	$("#fillFormAlertCP").css("display", "none");
	return true;
}

function updateAverageRating(reviews) {
	var averageRating = 0;
	var ratingSum = 0;
	//console.log(reviews.length);
	for (i = 0; i < reviews.length; i++) {
		ratingSum += parseInt(reviews[i].rating);
	}
	//console.log(ratingSum);
	averageRating = parseFloat((ratingSum/reviews.length).toString().substring(0,3));
	//console.log(averageRating);
	var numStars = averageRating;
	var avgStarSpanHTML = "";
	
	while (numStars > 0) {
		if (numStars >= 0.75) {
			avgStarSpanHTML += "<img class=\"rating-star\" src=\"images/star-8x_full.png\">";
		}
		else if (numStars >= 0.25 && numStars < 0.75) {
			avgStarSpanHTML += "<img class=\"rating-star\" src=\"images/star-8x_half.png\">";
		}
		numStars--;
	}
	

	$('#averageRatingStars').html(avgStarSpanHTML);
	$('#averageRatingValue').html(averageRating + "/5");
	if(reviews.length<=0){
		$('#averageRatingValue').html("0/5");
		$('#newReview').html("No reviews have been submitted.");
	}
	
}

function checkSavedQuery(savedSubject, savedCourseNo, savedTerm, savedInstructor) {
//	console.log($('#subjectDropDownCP').val() + ", " + savedSubject);
//	console.log($('#courseDropDownCP').val() + ", " + savedCourseNo);
//	console.log($('#termDropDownCP').val() + ", " + savedTerm);
//	console.log($('#instructorDropDownCP').val() + ", " + savedInstructor);

	if ($('#subjectDropDownCP').val() == savedSubject &&
			$('#courseDropDownCP').val() == savedCourseNo &&
			$('#termDropDownCP').val() == savedTerm &&
			$('#instructorDropDownCP').val() == savedInstructor) {
				$('#searchCourseBtnCP').addClass('disabled');
				$('#searchCourseBtnCP').prop('disabled', true);
	}
	else {
		$('#searchCourseBtnCP').removeClass('disabled');
		$('#searchCourseBtnCP').prop('disabled', false);
	}

}

function getCookie() {
	var value = "; " + document.cookie;
	var parts = value.split("; ");
	var name = "";
	//console.log(parts);
	for (i = 0; i < parts.length; i++) {
		if (parts[i].substring(0,4) == "user") {
			name = parts[i].substring(5).replace('-', ' ');
			break;
		}
	}
	//console.log(name);
	return name;
}
