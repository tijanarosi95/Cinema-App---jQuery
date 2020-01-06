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
    
    var usersTable = $('#allUsersTable');
    
    
    
    $.get('UserServlet', {'loggedUser':'allRoles'}, function(data){
    	console.log(data);
    	
    	labelType1.text(data.allRoles[0]);
    	labelType2.text(data.allRoles[1]);
    	
    	role1.val(data.allRoles[0]);
    	
    	role2.val(data.allRoles[1]);
    	
    	
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
    	
    	console.log("params: " + params.userNameFilter);
    	console.log("params2: " + params.typeOfUser);
    	
    	$.get('AllUsersServlet', params, function(data){
    		console.log(data);
    		
    		if(data.status == 'unauthenticated'){
    			console.log(data.status);
    			window.location.replace('index.html');
    			return;
    		}
    		if(data.status == 'success'){
    			console.log(data.status);
    			
    			usersTable.find('tr:gt(0)').remove();
    			
    			var filteredUsers = data.filteredUsers;
    			
    			//userNameInput.val('');
    			//chosenRadioButton.attr('checked', false);
    			
    			console.log(filteredUsers);
    			
    			var index = 1;
    			
    			for(user in filteredUsers){
    				
    				usersTable.append(
    								'<tr>' +
    									'<td>' + index++ + '</td>' +
    									'<td><a href="User.html?username=' + filteredUsers[user].username + '">' + filteredUsers[user].username + '</a></td>' +
    									'<td>' + '<p>' + filteredUsers[user].registrationDate + '</p>' + '</td>' +
    									'<td>' + filteredUsers[user].role + '</td>' +
    									'<td><form><input type="submit" value="Change" class="btn btn-primary" userId="' + filteredUsers[user].username  +'"></form></td>' +
    								'</tr>'	);
    			}
    			    
    		}
    		
    		
    	});
    }
    
    
    

    userNameInput.on('keyup', function(event){
    	getUsers();
    	
    	event.preventDefault();
    	return false;
    });
    
    chosenRadioButtom.on('change', function(event){
    	getUsers();
    	
    	event.preventDefault();
    	return false;
    });
    
    
   getUsers();
        
});