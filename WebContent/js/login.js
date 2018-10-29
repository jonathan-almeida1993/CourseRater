$(document).ready(function() {
  /* When the login page loads AFTER logging out, display the logout success alert. */
  if (typeof(Storage) !== "undefined") {
    if (sessionStorage.isLoggedIn == 0 && sessionStorage.pageFrom != "login.html") {
      $('#logoutModal').modal('show');
    }
  }
});
