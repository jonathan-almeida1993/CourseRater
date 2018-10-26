$(document).ready(function() {
  /* When the login page loads AFTER logging out, display the logout success alert. */
  if (typeof(Storage) !== "undefined") {
    if (sessionStorage.isLoggedIn == 0 && sessionStorage.pageFrom != "login.html") {
      $('#logoutModal').modal('show');
    }
  }

  $('#loginBtn').click(function() {
    if (validateLoginForm()) {
      //JAVA CODE TO LOGIN
    }
  });
});

/* Login Form Validation */
function validateLoginForm() {
	$('#badUsernameAlert').css("display", "none");
	$('#badPasswordAlert').css("display", "none");
  var onidUsername = $('#onidUsername').val();
  var onidPassword = $('#onidPassword').val();
	var formReady = true;
  if (onidUsername.length == 0) {
    $('#badUsernameAlert').html("Required field!");
    $('#badUsernameAlert').css("display", "block");
		//$('#badUsernamePopover').popover();
		$('#onidUsername').css("box-shadow", "0 0 10px red");
		$('#onidPassword').css("box-shadow", "0 0 0 black");
		formReady = false;
  }
  else if (onidPassword.length == 0) {
    $('#badPasswordAlert').html("Required field!");
    $('#badPasswordAlert').css("display", "block");
		//$('#badPasswordPopover').popover();
		$('#onidPassword').css("box-shadow", "0 0 10px red");
		$('#onidUsername').css("box-shadow", "0 0 0 black");
		formReady = false;
  }
	return formReady;
}
