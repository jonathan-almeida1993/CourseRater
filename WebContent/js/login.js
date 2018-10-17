$(document).ready(function() {
  if (typeof(Storage) !== "undefined") {
    alert(sessionStorage.pageFrom);
    if (!sessionStorage.isLoggedIn && sessionStorage.pageFrom.includes("/~habibelo/CourseRater/WebContent") && sessionStorage.pageFrom != "/~habibelo/CourseRater/WebContent/login.html") {
      $('#logoutModal').modal('show');
    }
  }
});

function validateLoginForm() {
  var onidUsername = $('#onidUsername').val();
  var onidPassword = $('#onidPassword').val();
  if (onidUsername.length == 0) {
    $('#badUsernameAlert').html("Required field!");
    $('#badUsernameAlert').css("display", "none");
  }
  if (onidPassword.length == 0) {
    $('#badPasswordAlert').html("Required field!");
    $('#badPasswordAlert').css("display", "none");
  }
}
