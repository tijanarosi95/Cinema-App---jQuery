$(document).ready(function(){
	
	var userNameInput = $('#usernameId');
	var passwordInput = $('#passwordId');
	
	$('#loginSubmitId').on('click', function(event){
		
		var username = userNameInput.val();
		var password = passwordInput.val();
		console.log(username);
		console.log(password);
		
		var params = {
				"username" : username,
				"password" : password,
		}
		
		$.post('LogInServlet', params, function(data){
			
			console.log(data);
			
			if(data.status == 'failure'){
				
				userNameInput.val('');
				passwordInput.val('');
				
				
				
				return;
			}
			
			if(data.status == 'success'){
				
				getUserPage();
			}
		});
		
		
		function getUserPage(){
			$.get('UserServlet', {'loggedUser' : 'loggedUserRole'}, function(data){
				
				if(data.status == 'success'){
					
					if(data.loggedUserRole == 'ADMIN'){
						
						window.location.replace('AdminProfile.html');
					}
					else if(data.loggedUserRole == 'USER'){
						
						window.location.replace('UserProfile.html');
					}
				}
			});
		}
		
		event.preventDefault();
		
		return false;
		
	});
	
});

