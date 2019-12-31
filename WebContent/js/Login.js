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
				userNameInput.val('');
				
				return;
			}
			if(data.status == 'success'){
				
				window.location.replace('');
			}
		});
		
		event.preventDefault();
		
		return false;
		
	});
	
	
});