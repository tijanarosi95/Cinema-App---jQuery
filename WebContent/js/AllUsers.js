$(document).ready(function(){
    $('#logout').on('click', function(event){
        $.get('LogoutServlet', function(data){
            if(data.status == 'unauthenticated'){
				console.log(data);
				window.location.replace('index.html');
			}
        });
        event.preventDefault();
		return false;
    });

    var userNameInput = $('#userNameInput');
    var chosenRadioButtom = $('.custom-control-input');
    
    var labelType1 = $('#type1');
    var labelType2 = $('#type2');
    
    var role1 = $('#defaultUnchecked');
    var role2 = $('#defaultChecked');
    
    
    
    $.get('UserServlet', {'loggedUser':'allRoles'}, function(data){
    	console.log(data);
    	
    	labelType1.text(data.allRoles[0]);
    	labelType2.text(data.allRoles[1]);
    	
    	role1.val(data.allRoles[0]);
    	
    	role2.val(data.allRoles[1]);
    	
    	$('.custom-control-input').change(function(){
    		if ($(this).is(':checked')) {
    			alert($(this).val());
    			
    		  }
    		
    	});
    	
    	$('.form-control').change(function(){
    		alert($(this).val());
    		
    	});
    	
    });
    
    function getUsers(){
    	var usernameFilter = userNameInput.val();
    	var typeOfUserButton = $('.custom-control-input[name=defaultRadios]:checked').val();
    	console.log(usernameFilter);
    	console.log(typeOfUserButton);
    	
    	var params = {
    			"userNameFilter" : usernameFilter,
    			"typeOfUser" : typeOfUserButton,
    			
    	};
    	
    	$.get('AllUsersServlet', params, function(data){
    		console.log(data);
    	});
    }
    
    

    userNameInput.on('keyup', function(event){
    	getUsers();
    	
    	event.preventDefault();
    	return false;
    });
    
    chosenRadioButtom.on('keyup', function(event){
    	getUsers();
    	
    	event.preventDefault();
    	return false;
    });
    
    
   
        
});