$(document).ready(function(){
	
	var userProfile = $('#userProfile');
	var adminProfile = $('#adminProfile');
	var logout = $('#userLogout');
	
	var userDiv = $('#userDiv');
	
	var userRole;
	
	$.get('UserServlet', {'loggedUser': 'loggedUserRole'}, function(data){
		
		userRole = data.loggedUserRole;
		
		if(userRole == 'USER'){
			
			adminProfile.hide();
			userDiv.hide();
			
			userProfile.show();
			logout.show();
		}
		else if(userRole == 'ADMIN'){
			
			userProfile.hide();
			
			adminProfile.show();
			logout.show();
			userDiv.show();
		}
	});
	
	var ticketID =  window.location.search.split('&')[0].split('=')[1];
	
	var movieName = $('#moviename');
	var type = $('#type');
	var hall = $('#hall');
	var date = $('#dateandtime');
	var seat = $('#seat');
	var price = $('#price');
	var user = $('#user');
	
	var params = {
			
			'action': 'getTicket',
			'ticketID': ticketID
	}
	
	$.get('TicketServlet', params, function(data){
		
		if(data.status == 'success'){
			
			var ticket = data.ticket;
			
			movieName.text(ticket.projection.movie.name);
			
			url = 'Movie.html?id=' + ticket.projection.movie.idMovie;
			
			movieName.attr('href', url);
			
			type.text(ticket.projection.projectionType.name);
			
			hall.text(ticket.projection.hall.name);
			
			date.text(ticket.projection.dateTimeShow);
			
			dateTime = 'Projection.html?id=' + ticket.projection.idProjection;
			
			date.attr('href', dateTime);
			
			seat.text(ticket.seats[0].serialNumber);
			
			price.text(ticket.projection.price);
			
			userUrl = 'User.html?username=' + ticket.user.username;
			
			user.attr('href', userUrl);
			
			user.text(ticket.user.username);
			
			
		}
	});
	
});