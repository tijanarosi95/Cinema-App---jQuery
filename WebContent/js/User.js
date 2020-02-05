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
    
    var table = $('#table');
    
    function getUserTickets (){
    	
    	var params = {
    			
    			'action': 'getUserTickets',
    			'username': username
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
    
    getChosenUser();
    
    getUserTickets();
});