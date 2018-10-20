$(document).ready(function() {
	/* Load the main page header */
	$('#header').load("header.html");

	/* When the login page loads AFTER logging out, display the logout success alert. */
	if (typeof(Storage) !== "undefined") {
		if (sessionStorage.isLoggedIn == 0 && sessionStorage.pageFrom != "login.html") {
			$('#logoutModal').modal('show');
		}
	}
});
