$(document).ready(function(){
	
	var usernameInput = $('#usernameReg');
	var passwordInput = $('#passwordReg');
	var repeatedPassword = $('#passwordRepReg');
	
	var messageSpan = $('#message');
	
	$('#registerSubmitId').on('click', function(event){
		
		var username = usernameInput.val();
		var password = passwordInput.val();
		var repeatPassword = repeatedPassword.val();
		
		if(password != repeatPassword ){
	  
	        messageSpan.text('Not matching');
	        
	        event.preventDefault();
	        return false;
	       
	    }
		
		var params = {
				"username" : username,
				"password" : password,
		}
		
		$.post('RegisterServlet', params, function(data){
			console.log(data);
			
			if(data.status == 'failure'){
				messageSpan.text(data.message);
				return;
			}
			
			if(data.status == 'success'){
				
				alert('You have successfully registered!Please log in!');
				
				usernameInput.val('');
				passwordInput.val('');
				repeatedPassword.val('');
			}
		});
		
		event.preventDefault();
		return false;
		
	});
	
});