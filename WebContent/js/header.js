$(document).ready(function() {
	alert("HERE");

	$("#header").load("header.html");
});

function logout() {
	if (typeof(Storage) !== "undefined") {
		sessionStorage.isLoggedIn = false;
		sessionStorage.pageFrom = window.location.pathname;
    //alert(sessionStorage.pageFrom);
    window.location.href="login.html";
	}
}
