$(document).ready(function(){
	
	$('#logoutUser').on('click', function(event){
		$.get('LogoutServlet', function(data){
			
			if(data.status == 'unauthenticated'){
				console.log(data);
				window.location.replace('index.html');
			}
		});
		
		event.preventDefault();
		return false;
	});
	
	var adminProfile = $('#adminProfile');
	var logout = $('#Logout');
	
	var userProfile = $('#userProfile');
	
	var btnBuy = $('#liBuyTicket');
	
	var ticketsDiv = $('#ticketsDiv');
	
	var userRole;
	
	$.get('UserServlet', {'loggedUser' : 'loggedUserRole'}, function(data){
		
		userRole = data.loggedUserRole;
		
		if(userRole == null){
			
			adminProfile.hide();
			logout.hide();
			
			userProfile.hide();
			btnBuy.hide();
			ticketsDiv.hide();
			
		}else if (userRole == 'USER'){
			
			adminProfile.hide();
			
			logout.show();
			userProfile.show();
			btnBuy.show();
			ticketsDiv.hide();
			
		}else if(userRole == 'ADMIN'){
			
			adminProfile.show();
			logout.show();
			ticketsDiv.show();
			
			userProfile.hide();
			btnBuy.hide();
	
		}
		
	});
	
	
	
	
	var projectionID =  window.location.search.split('&')[0].split('=')[1];
	
	var movieName = $('#movieLink');
	var type = $('#type');
	var date= $('#dateandtime');
	var hall = $('#hall'); 
	var price = $('#price');
	
	var params = {
			
			'action' : 'getProjection',
			'projectionID' : projectionID
	}
	
	$.get('ProjectionServlet', params, function(data){
		
		console.log(data.selectedProjection);
		
		var projection = data.selectedProjection;
		
		if(data.status === 'success'){
			
			for(img in images){
				
				
				
				if(images[img].id == projection.movie.idMovie){
					
					console.log(images[img].image);
					
					$('#bcgImage').css('background-image', images[img].image);
				}
			}
			
			movieName.text(projection.movie.name);
			
			var movieLink = 'Movie.html?id=' + projection.movie.idMovie;
			
			movieName.attr('href', movieLink);
			
			type.text(projection.projectionType.name);
			hall.text(projection.hall.name);
			date.text(projection.dateTimeShow);
			price.text(projection.price);
		}
		
		
	});
	
	$('#btnBuy').on('click', function(event){
		
		var url = 'SecondPhase.html?id=' + projectionID;
		
		$(this).attr('href', url);
		
	});
	
	
	var table = $('#tableTickets');
	
	var parameters = {
			
			'action': 'getProjectionTickets',
			'projID': projectionID
	}
	
	console.log(parameters);
	
	$.get('TicketServlet', parameters, function(data){
		
		if(data.status == 'unauthenticated'){
			
			window.location.replace('index.html');
			return;
		}
		if(data.status == 'success'){
			
			var tickets = data.tickets;
			
			
			table.find('tr:gt(0)').remove();
			
			var index = 1;
			
			for(t in tickets){
				
				table.append(
						'<tr>' +
							'<td>' + index++ + '</td>' +
							'<td>' + '<a href="Ticket.html?id=' + tickets[t].idTicket + '">' + tickets[t].dateTimeofSale + '</a></td>' +
							'<td>' + '<a href="User.html?username=' + tickets[t].user.username + '">' + tickets[t].user.username + '</a></td>' +
						'</tr>'
				);
			}
		}
		
	});
	
	return false;
	
});