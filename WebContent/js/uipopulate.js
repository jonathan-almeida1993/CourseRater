/*
 * Author: Jonathan Almeida
 */

$(document).ready(function() {
	populateQuestions();
	
	$('.ratings').barrating('show', {
	    showSelectedRating:true,
	});

	
	$('button#submitName').click(function(){
		
		//VALIDATIONS FOR NAME FIELD
		if((!$('input#anonymousCheck').prop("checked"))&&(($.trim($('input#fnameInput').val()).length == 0) || ($.trim($('input#lnameInput').val()).length == 0))){
			
			if($.trim($('input#fnameInput').val()).length == 0){
				
				$('input#fnameInput').css('border','1px solid #a94442');
				$('.alertDiv').text('Please Enter First Name');
				$('.alertDiv').show();
				
			}else if($.trim($('input#lnameInput').val()).length == 0){
				
				$('input#lnameInput').css('border','1px solid #a94442');
				$('.alertDiv').text('Please Enter Last Name');
				$('.alertDiv').show();
				
			}
			
		}else{
		
			var anonymousFlag = 'N';
			var name = $('input#fnameInput').val()+" "+$('input#lnameInput').val();
			//alert("NAME--"+name);
			if($('input#anonymousCheck').prop("checked")){
				anonymousFlag = $('input#anonymousCheck').val();
				name = "Anonymous#"+Math.floor((Math.random() * 1000) + 1);
			}	
			//alert("ANONYMOUS CHECK--"+anonymousFlag);
			$('span#userInfo').text(name);
			
			if(!$('input#anonymousCheck').prop("checked")){
				$("div.wlcmeTxt").css("display","");
			}
			
			$('input.nameInput').each(function(){
				$(this).val(name);
			});
			
			$('input.anonymousInput').each(function(){
				$(this).val(anonymousFlag);
			});
			
			$('.userDivModal').modal('hide');
		}
	
	});
});




function populateQuestions(){
	var counter = 65;
	var qIndex;
	var jsonDataFromServer = sendData("getFeedbackQuestions", null, "DataController");
	console.log("JSON DATA---"+jsonDataFromServer);
	jsonDataFromServer = jQuery.parseJSON(jsonDataFromServer);
	
	$.each(jsonDataFromServer,function(idx, obj) {
		
		if(!(obj.questionOptions == undefined)){//SubQuestions exists
			
			var tr = $('<tr class="trheader"/>');
			
			tr.append("<th>"+String.fromCharCode(counter)+"</th>");
			
			qIndex=String.fromCharCode(counter);
			counter++;
			
			tr.append('<th data-title="Questions" colspan="3">'+obj.questionText
					+'<input type="hidden" name = "response[][userName]" class="nameInput" value="">'
					+'<input type="hidden" name = "response[][anonymous]" class="anonymousInput" value=""></th>'
					+'<input type="hidden" name = "response[][questionId]" value="'+obj.questionId+'">');
			$('#questionTable tbody').append(tr);
			
			if(obj.questionType == "RATING_TEXTBOX"){
				
				$.each(obj.questionOptions,function(idx, optn){
					
					var tr_rating_textbox = $('<tr />');
					
					if(optn.optionType=="TEXTBOX"){
						tr_rating_textbox.append('<td></td>');
						
						tr_rating_textbox.append('<td data-title="Category"><b>'+optn.optionText+'</b></td>');
						
						tr_rating_textbox.append('<td data-title="Comments" colspan="2">'
								+'<input type="hidden" name = "response[][questionOptions[][questionOptionId]]" value="'+optn.questionOptionId+'">'
								+'<textarea rows="2" cols="3" maxlength="200"'
								+' class="form-control" name="response[][questionOptions[][responseText]]" ></textarea></td>');
					}
					else{
						tr_rating_textbox.append('<td>'+optn.optionSerialNo+'</td>');
					
						tr_rating_textbox.append('<td data-title="Category">'+optn.optionText+'</td>');
					
						tr_rating_textbox.append('<td data-title="Ratings"><input type="hidden" name = "response[][questionOptions[][questionOptionId]]" value="'+optn.questionOptionId+'">'
								+'<div class="input select rating-g">'
								+'<select class="ratings" name="response[][questionOptions[][rating]]" id=select#'+qIndex+'#'+optn.optionSerialNo+'>'
								+'<option value=""></option>'
								+'<option value="1">1</option>'
								+'<option value="2">2</option>'
								+'<option value="3">3</option>'
								+'<option value="4">4</option>'
								+'<option value="5">5</option>'
								+'</select></div><div class="clearfix"></div></td>'
								+'<td data-title="Comments"><textarea rows="2" cols="3" maxlength="200"'
								+' class="form-control" name="response[][questionOptions[][responseText]]" ></textarea></td>');	
					}
					
					$('#questionTable tbody').append(tr_rating_textbox);
				});
				//TO ADD SPACE AFTER ONE SECTION
				$('#questionTable tbody').append('<tr><td colspan="4"></td></tr>');
				
			}else if(obj.questionType == "RADIO"){
				
				var tr_radio = $('<tr />');
				tr_radio.append('<td></td>');
				var str = '<td colspan="3">';
				
				$.each(obj.questionOptions,function(idx, optn){
					//str = str + '<label class="radio-inline"><input type="radio" name="response[][rating]" value="'+optn.optionText+'">'+optn.optionText+'</label>';
					str = str + '<label class="radio-inline"><input type="radio" id=radio#'+qIndex+' class="RADIO" name="response[][optionR]" value="'+optn.optionText+'">'+optn.optionText+'</label>';
				});
				
				str = str + '</td>';
				tr_radio.append(str);
				
				$('#questionTable tbody').append(tr_radio);
				//TO ADD SPACE AFTER ONE SECTION
				$('#questionTable tbody').append('<tr><td colspan="4"></td></tr>');
								
			}else if(obj.questionType == "RADIO2"){
				
				var tr_radio = $('<tr />');
				tr_radio.append('<td></td>');
				var str = '<td colspan="3">';
				
				$.each(obj.questionOptions,function(idx, optn){
					str = str + '<label class="radio-inline"><input type="radio" id=radio#'+qIndex+' class="RADIO2" name="response[][optionR2]" value="'+optn.optionText+'">'+optn.optionText+'</label>';
				});
				
				str = str + '</td>';
				tr_radio.append(str);
				
				$('#questionTable tbody').append(tr_radio);
				//TO ADD SPACE AFTER ONE SECTION
				$('#questionTable tbody').append('<tr><td colspan="4"></td></tr>');
								
			}else if(obj.questionType == "RADIO_TEXTBOX"){
				
				var tr_radio_textbox = $('<tr />');
				tr_radio_textbox.append('<td></td>');
				var str = '<td colspan="2">';
				
				$.each(obj.questionOptions,function(idx, optn){
					str = str + '<div class="radio"><label><input type="radio" id=radiotxt#'+qIndex+' class="RADIO_TEXTBOX" name="response[][optionRT]" value="'+optn.optionText+'">'+optn.optionText+'</label></div>';
				});
				
				str = str + '<div><strong>Note:</strong> Any other suggestions Please elaborate in comment box.</div></td>';
				tr_radio_textbox.append(str);
				
				tr_radio_textbox.append('<td data-title="Comments"><textarea rows="2" cols="3" maxlength="200"'
						+' class="form-control" name="response[][responseText]" ></textarea></td>');
				
				$('#questionTable tbody').append(tr_radio_textbox);
				//TO ADD SPACE AFTER ONE SECTION
				$('#questionTable tbody').append('<tr><td colspan="4"></td></tr>');

			}
		}else{//Only primary questions
			
			if(obj.questionType == "RATING"){

				var tr_rating = $('<tr class="trheader" />');
				tr_rating.append('<td>'+String.fromCharCode(counter)+'</td>');
				qIndex=String.fromCharCode(counter);
				counter++;
				
				tr_rating.append('<td data-title="Questions">'+obj.questionText+'</td>');
				
				tr_rating.append('<td data-title="Ratings" colspan="2">'
						+'<input type="hidden" name = "response[][userName]" class="nameInput" value="">'
						+'<input type="hidden" name = "response[][anonymous]" class="anonymousInput" value="">'
						+'<input type="hidden" name = "response[][questionId]" value="'+obj.questionId+'">'
						+'<div class="input select rating-g">'
						+'<select class="ratings" name="response[][rating]" id=select#'+qIndex+'>'
						+'<option value=""></option>'
						+'<option value="1">1</option>'
						+'<option value="2">2</option>'
						+'<option value="3">3</option>'
						+'<option value="4">4</option>'
						+'<option value="5">5</option>'
						+'<option value="6">6</option>'
						+'<option value="7">7</option>'
						+'<option value="8">8</option>'
						+'<option value="9">9</option>'
						+'<option value="10">10</option>'
						+'</select></div><div class="clearfix"></div></td>');
				$('#questionTable tbody').append(tr_rating);
				//TO ADD SPACE AFTER ONE SECTION
				
				$('#questionTable tbody').append('<tr><td colspan="4"></td></tr>');
				
			}else if(obj.questionType == "TEXTBOX"){
				
				var tr_textbox = $('<tr class="trheader" />');
				tr_textbox.append('<td>'+String.fromCharCode(counter)+'</td>');
				qIndex=String.fromCharCode(counter);
				counter++;
				
				tr_textbox.append('<td colspan="2" data-title="Questions">'+obj.questionText+'</td>');
				
				tr_textbox.append('<td data-title="Comments">'
						+'<input type="hidden" name = "response[][userName]" class="nameInput" value="">'
						+'<input type="hidden" name = "response[][anonymous]" class="anonymousInput" value="">'
						+'<input type="hidden" name = "response[][questionId]" value="'+obj.questionId+'">'
						+'<textarea rows="2" cols="3" maxlength="200"'
						+'class="form-control mandatory" name="response[][responseText]" id=textarea#'+qIndex+'></textarea></td>');
				
				$('#questionTable tbody').append(tr_textbox);
				//TO ADD SPACE AFTER ONE SECTION
				$('#questionTable tbody').append('<tr><td colspan="4"></td></tr>');
				
			}
		}//End of primary question section
	});
}

function sendData(message, data, serverUrl){
  	var returnData = null;
	
  	$.ajax({
		type : "POST",
		url : serverUrl,
		async:false,
		data : {
			message:message,
			JDATA:data
			},
			
		success : function(data) {
			returnData = data;
			},
			
		error : function(xhr, ajaxOptions, throwError) {
			alert("Please Try Again");
		}
	});
  	
	return returnData;
}

