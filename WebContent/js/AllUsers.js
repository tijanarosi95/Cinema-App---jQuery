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
    
    var labelType1 = $('.type1');
    var labelType2 = $('.type2');
    
    var role1 = $('#defaultUnchecked');
    var role2 = $('#defaultChecked');
    
    var usersTable = $('#allUsersTable');
    
    
    
    $.get('UserServlet', {'loggedUser':'allRoles'}, function(data){
    	console.log(data);
    	
    	labelType1.text(data.allRoles[0]);
    	labelType2.text(data.allRoles[1]);
    	
    	role1.val(data.allRoles[0]);
    	checkUser.val(data.allRoles[0]);
    	
    	role2.val(data.allRoles[1]);
    	checkAdmin.val(data.allRoles[1]);
    	
    	
    });
    
    
    var loggedUserName;
    
    
    	$.get('UserServlet', {'loggedUser' : 'loggedInUser'}, function(data){
    	
    		loggedUserName = data.loggedInUser.username;
    	
    		console.log('userNameee ' + userName);
    	
    		if(userName == loggedUserName){
    			changeUserModal.modal('hide');    		
    			alert('You can not change your role!');
    			
    		}
    		
    		
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
    									'<td><button type="button" class="btn btn-primary" data-toggle="modal" data-target="#changeUserModal" username="' + filteredUsers[user].username  +'">Change</button></td>' +
    									'<td><button type="button" class="btn btn-danger" data-toggle="modal" data-target=#popUpModal username="' +  filteredUsers[user].username  +'">Delete</button></td>' +
    								'</tr>'	);
    			}
    			    
    		}
    		
    		
    	});
    }
    
    
    var changeUserModal = $('#changeUserModal');
    var userName;
    
    var userNameField = $('#userNameField');
    var passwordField = $('#passwordField');
    var dateField = $('#dateField');
    var checkUser = $('#userType1');
    var checkAdmin = $('#userType2');
    
    var changeUser = $('#changeUserSubmit');
    

    
    changeUserModal.on('show.bs.modal', function(event){
    	
    	var button = $(event.relatedTarget);
    	userName = button.attr('username');
    	console.log('Usernamee: ' + userName);
    	
    	
    	
    	var params = {
        		'loggedUser' : 'getUser',
        		'username' : userName
        }
    	
    	
    	$.get('UserServlet', params, function(data){
        	console.log(data.chosenUser);
        	
        	if(data.status == 'unauthenticated'){
        		console.log(data.status);
    			window.location.replace('index.html');
    			return;
        	}
        	
        	if(data.status == 'success'){
        		
        	
        		var chosenUser = data.chosenUser;
        	
        		userNameField.val(chosenUser.username);
        		userNameField.prop('disabled', true);
        		
        		passwordField.val(chosenUser.password);
        		passwordField.prop('disabled', true);
        		
        		
        		dateField.val(chosenUser.registrationDate);
        		dateField.prop('disabled', true);
        		
        		if(chosenUser.role == 'USER'){
        			checkUser.attr('checked', true);
        			
        		}else if(chosenUser.role == 'ADMIN'){
        			checkAdmin.attr('checked', true);
        		}
        			
        		var  role;
        		
        		changeUser.on('click', function(event){
        			
        			
        			role = $('.form-check-input[name=materialExample]:checked').val();
        			
        			console.log('Changed role: ' + role);
        			
        			var params = {
        					'action' : 'update',
        					'userName' : userName,
        					'role' : role
        			}
        			
        			console.log(params);
        			
        			$.post('UserServlet', params, function(data){
        				
        				console.log(data);
        				
        				if(data.status == 'unauthenticated'){
							window.location.replace('index.html');
							return;
						}
        				if(data.status == 'success'){
        					alert('You successfully update user!');
        					changeUserModal.modal('toggle');
        				}
        				getUsers();
        				
        			});
        			
        			return false;
        		});
        		
        		return false;
        	}
        });
    	
    });
    
    
  var confirmModal = $('#popUpModal');
  var modalBody = $('#modalBody');
  var btnDelete = $('#btnDeleteUser');
  
  var deleteUserName;
  
  confirmModal.on('show.bs.modal', function(event){
	 
	  var button = $(event.relatedTarget);
	  deleteUserName = button.attr('username');
	  
	  console.log('User for delete: ' + deleteUserName);
	  console.log('LoggedUserName ' + loggedUserName);
	  
	  if(deleteUserName === loggedUserName){
		  
		  modalBody.text('You can not delete yourself!');
		  btnDelete.hide();
		  
	  }else{
		  
		  modalBody.text('Are you sure you want delete this user?');
		  btnDelete.show();
		 
	  }
	  
	  
  	});
  
	  btnDelete.on('click', function(event){
		 
		  var params = {
				  'action' : 'delete',
				  'username': deleteUserName
		  }
		  
		  console.log(params);
		  
		  $.post('UserServlet', params, function(data){
			  
			  if(data.status == 'unauthenticated'){
				  window.location.replace('index.html');
				  return;
			  }
			  if(data.status == 'unauthorized'){
				  window.location.replace('index.html');
				  return;
			  }
			  if(data.status == 'success'){
				  alert('You successfully delete this user!');
				  confirmModal.modal('toggle');
				  getUsers();
			  }
		  });
		  
		  event.preventDefault();
			
		  return false;
	  });
	  

    
    
    $('th').click(function(){
		var table = $(this).parents('table').eq(0);
		var rows = table.find('tr:gt(0)').toArray().sort(comparer($(this).index()));
		this.asc = !this.asc;
		if(!this.asc){
			rows = rows.reverse();
		}
		for (var i = 0; i < rows.length; i++){
			table.append(rows[i]);
		}
	});
	
	function comparer(index){
		return function(a, b){
			var valA = getCellValue(a, index);
			var valB = getCellValue(b, index);
			
			return $.isNumeric(valA) && $.isNumeric(valB) ? valA - valB : valA.toString().localeCompare(valB);
		}
	}
	
	function getCellValue(row, index){
		return $(row).children('td').eq(index).text();
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