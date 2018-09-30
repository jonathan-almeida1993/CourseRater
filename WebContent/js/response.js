/*
 * Author: Jonathan Almeida
 */

$(document).ready(function() {
	var prev_highligted_row = $(this);
	
	$("button#feedbackFormSubmit").click(function(){
		success();
	});
	
});

function success() {

	var jsonData = $('form#feedbackForm').serializeJSON();
	//console.log("jsonData--" + JSON.stringify(jsonData));
	$(".loader").show();
	
	$.ajax({
		type : "POST",
		url : "DataController",
		data : {
			message:"captureResponse",
			JDATA:JSON.stringify(jsonData)
			},
		success : function(data) {
			
				$('.thankYouModal').modal('show');
				
				if ($('.thankYouModal').css('display', 'block')) {
					$('.in').css('opacity', '0.93');
				}	
				
				$(".loader").hide();
				var status=data;
				
				if(status == "true"){
					$(".thankYouModal .modal-body").html('<h4 class="alert alert-success  text-center">'
						       +'<span class="glyphicon glyphicon-thumbs-up"></span> Your response has been successfully submitted!'
						       +'</h4>'
						       +'<h5>'
						       +'Please close the browser window.'
						       +'</h5>');
				}else{
					$(".thankYouModal .modal-body").html('<h5 class="alert alert-danger  text-center">'
						       +'<span class="glyphicon glyphicon-alert"></span> There was an error in submitting you response! Please try again later.'
						       +'</h5>'
						       +'<h5>'
						       +'Please close the browser window.'
						       +'</h5>');
				}
			
			},
		error : function(xhr, ajaxOptions, throwError) {

			$(".loader").hide();
			alert(throwError+" - "+ajaxOptions+"--Please Try Again");
			
		}
	});
}


/*VALIDATION PROCEDURE:
	1.CHECK ALL SELECT
	2.CHECK ALL MANDATORY TEXTAREA
	3.CHECK ALL RADIO
*/



