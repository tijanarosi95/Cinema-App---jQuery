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
    
    var label1 = $('#check1');
    var label2 = $('#check2');
    
    $.get('UserServlet', {'loggedUser' : 'allRoles'}, function(data){
    	
    	label1.text(data.allRoles[0]);
    	label2.text(data.allRoles[1]);
    });
    
    

    var username =  window.location.search.split('&')[0].split('=')[1];
    
    var usernameInput = $('#userNameInput');
    var dateRegistrationInput = $('#dateInput');
    
    var checkedUser = $('#checkbox1');
    var checkedAdmin = $('#checkbox2');
    
    var btnChange = $('#btnChangeUser');
    
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
            	
            	usernameInput.val(selectedUser.username);
            	usernameInput.prop("disabled", true);
            	
            	dateRegistrationInput.val(selectedUser.registrationDate);
            	dateRegistrationInput.prop("disabled", true);
            	
            	btnChange.hide();
            	
            	console.log(selectedUser.role);
            	
            	if(selectedUser.role == 'USER'){
            		checkedUser.attr("checked", true);
            		
            	}else{
            		checkedAdmin.attr("checked", true);
            		
            	}
            	
            	
            	
            	
            }
        });

    }
    
    getChosenUser();
});