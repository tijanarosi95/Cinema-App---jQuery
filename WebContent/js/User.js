$(document).ready(function(){
    $('#logoutUser').on('click', function(event){
        $.get('LogoutServlet', function(data){
            console.log(data);
            if(data.status == 'unauthenticated'){
                window.location.replace('index.html');
            }

        });

        event.preventDefault();
        return false;
    });
    
    
    
    

    var username =  window.location.search.split('&')[0].split('=')[1];
    
    var usernameInput = $('#userNameInput');
    var dateRegistrationInput = $('#dateInput');
    
    var role = $('#role');
  
    
    
    
    console.log("link: " + username);
    
    var params = {
    		'loggedUser' : 'getUser',
    		'username' : username,
    }

    function getChosenUser(){
        $.get('UserServlet', params, function(data){

            console.log(data);

            if(data.status == 'unauthenticated'){
                window.location.replace('index.html');
                return;
            }

            if(data.status == 'success'){
                
            	var selectedUser = data.chosenUser;
            	console.log(selectedUser);
            	
            	usernameInput.text(selectedUser.username);
            	
            	
            	dateRegistrationInput.text(selectedUser.registrationDate);
            	
            	role.text(selectedUser.role);
            	
            	
            	console.log(selectedUser.role);
            	
 	
            }
        });

    }
    
    getChosenUser();
});