$(document).ready(function() {
	
	$('#forgotPassword').click(function(){
		if(!IsEmail($("input#emailIdLoginForm").val())){
			utilObj.showErrorMessageAlert("Error", "Please Enter a Valid Email Id");
		}else{
			/*alert("forgot password triggered");*/
			var status = sendDataSync($("input#emailIdLoginForm").val(), "forgotPassword", "LoginController");
			if(status =='PWD_STATUS_OK'){
				utilObj.showSuccessMessageAlert("Success", "Please check the mail that has been sent to get your password");
			}else{
				utilObj.showErrorMessageAlert(status, "Please try again with a valid emailId");
			}
		}
	});
	
});

function GetURLParameter(sParam)
{
    var sPageURL = window.location.search.substring(1);
    var sURLVariables = sPageURL.split('&');
    for (var i = 0; i < sURLVariables.length; i++) 
    {
        var sParameterName = sURLVariables[i].split('=');
        if (sParameterName[0] == sParam) 
        {
            return sParameterName[1];
        }
    }
}

function validateForm(){
	
	if(!IsEmail($("input#emailIdLoginForm").val())){
		utilObj.showErrorMessageAlert("Error", "Please Enter a Valid Email Id");
		return false;
	}
	else if(($("input#passwordLoginForm").val()=="")||($("input#passwordLoginForm").val()==null))
	{
		utilObj.showErrorMessageAlert("Error", "Please Enter the Password");
		return false;
	}
	else{
		$("input#passwordLoginForm").val(CryptoJS.SHA256($('#passwordLoginForm').val()));
		return true;
	}

}

function IsEmail(email) {
	  var regex = /^([a-zA-Z0-9_.+-])+\@(([a-zA-Z0-9-])+\.)+([a-zA-Z0-9]{2,4})+$/;
	  return regex.test(email);
}
