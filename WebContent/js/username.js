$(document).ready(function() {
	
	var cookieInfo = document.cookie.split(';');
	if(!cookieInfo[0].trim().startsWith('onid')){
		var temp = cookieInfo[1];
		cookieInfo[1] = cookieInfo[0];
		cookieInfo[0] = temp;
	}
	cookieInfo[0] = cookieInfo[0].split('=')[1];//contains onid
	cookieInfo[1] = cookieInfo[1].split('=')[1];//contains user name
	cookieInfo[1] = cookieInfo[1].replace('-',' ');
	$('#firstLastName').text(''+cookieInfo[1]);

});