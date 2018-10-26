$(document).ready(function() {
	/* Load the main page header */
	$('#header').load("header.html");
	//$('#confirmCloseModal').modal({ show: false });

	// Get the name of the page the user is currently on
	var urlPieces = window.location.pathname.split("/");
	var currentPage = urlPieces[urlPieces.length - 1];
	console.log("current page: " + currentPage);

	$('#logoutBtn').click(logout());
});

/* Action on clicking the logout button */
function logout() {
	if (typeof(Storage) !== "undefined") {
		sessionStorage.isLoggedIn = 0;
		var urlPieces = window.location.pathname.split("/");
		sessionStorage.pageFrom = urlPieces[pathname.length-1];
    //alert(sessionStorage.pageFrom);
    window.location.href="login.html";
	}
}
