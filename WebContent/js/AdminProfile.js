$(document).ready(function(){
	$('#logOutId').on('click', function(event){
		$.get('LogoutServlet', function(data){
			
			if(data.status == 'unauthenticated'){
				console.log(data);
				window.location.replace('index.html');
			}
		});
		
		event.preventDefault();
		return false;
	});
	
	$('#allUsersId').on('click', function(event){
		
		window.location.replace('AllUsers.html');
	});
	
	function getLoggedInUser(){
		
		$.get('UserServlet', {'loggedUser' : 'loggedInUser'}, function(data){
			
			console.log(data);
			
			if(data.status == 'unauthenticated'){
				window.location.replace('index.html');
				return;
			}
			
			if(data.status == 'success'){
				
				console.log(data);
				
				var loggedInAdmin = data.loggedInUser;
				
				var userNameParagraph = $('#userNameVal');
				var dateRegParagraph = $('#dateRegVal');
				
				userNameParagraph.text(loggedInAdmin.username);
				dateRegParagraph.text(loggedInAdmin.registrationDate);
			}
		});
	}
	
	getLoggedInUser();
});