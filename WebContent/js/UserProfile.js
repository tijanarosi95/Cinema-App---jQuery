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

	var oldPassword = $('#oldPassword');
	var newPassword = $('#newPassword');
	
	var message = $('#messagePassword');
	
	var btnChange = $('#changePasswordSubmit');
	
	btnChange.on('click', function(event) {
		
		var oldOne = oldPassword.val();
		var newOne = newPassword.val();
		
		if(oldOne != password){
			
			message.text('Not matching!');
			
			event.preventDefault();
			return false;
			
		}
		
		var params = {
				
				'username' : userName,
				'newPassword' : newOne
		}
		
		console.log(newOne);
		
		$.post('AllUsersServlet', params, function(data){
			
			
			if(data.status == 'success'){
					
				alert('You successfully changed your password!');
				
				
				autoLogout();
			}
			
		});
		
		event.preventDefault();
		
		return false;
	});
	
	function autoLogout(){
		
		$.get('LogoutServlet', function(data){
			
			if(data.status == 'unautheticated'){
				
				window.location.replace('index.html');
			}
			
		});
	}
	
	var userName;
	var password;
	
	function getUser(){
	
		$.get('UserServlet', {'loggedUser' : 'loggedInUser'}, function(data){
			
			console.log(data);
			
			if(data.status == 'unauthenticated'){
			window.location.replace('index.html');
			return;
			}
			
			if(data.status == 'success'){
				
				console.log(data);	
				
				var loggedUser = data.loggedInUser;
				
				
				userName = loggedUser.username;
				password = loggedUser.password;
				
				var userNameParagraph = $('#userNameVal');
				var dateRegParagraph = $('#dateRegVal');
				
				userNameParagraph.text(loggedUser.username);
				dateRegParagraph.text(loggedUser.registrationDate);
			
				getTickets();
			}
		});
	
	}
	
	var table = $('#table');
	
	function getTickets(){
		
	
		var params = {
			
			'action': 'getUserTickets',
			'username': userName
		}
	
		console.log(params);
	
		$.get('TicketServlet', params, function(data){
		
			if(data.status == 'success'){
			
				var tickets = data.tickets;
			
				table.find('tr:gt(0)').remove();
			
				var index = 1;
			
				for(t in tickets){
				
					table.append(
							'<tr>' +
								'<td>' + index++ + '</td>' +
								'<td>' + '<a href="Ticket.html?id=' + tickets[t].idTicket + '">' + tickets[t].dateTimeofSale + '</a></td>' +
								'</tr>'
							);
					}
			}
		});
	}
	
	getUser();
	
});