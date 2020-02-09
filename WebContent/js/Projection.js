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
	
	
	
	$.get('UserServlet', {'loggedUser' : 'loggedUserRole'}, function(data){
		
		var userRole;
		
		userRole = data.loggedUserRole;
		
		console.log(userRole + '++++');
		
		if(userRole == null){
			
			adminProfile.hide();
			logout.hide();
			ticketsDiv.hide();
			userProfile.hide();
			btnBuy.hide();
			
			
			console.log('UNDEFINESSS');
			
			getProjection();
			
			
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
			
			getTickets();

		}
		
		event.preventDefault();
		
		return false;
		
	});
	
	
	
	var projectionID =  window.location.search.split('&')[0].split('=')[1];
	
	var movieName = $('#movieLink');
	var type = $('#type');
	var date= $('#dateandtime');
	var hall = $('#hall'); 
	var price = $('#price');
	var seatsNum = $('#seatsNum');
	
	function getProjection(){
		
		$.get('ProjectionServlet', {'action': 'getFreeSeats'}, function(data){
			
			var freeSeatsNum = data.freeSeats;
			
			console.log(freeSeatsNum);
			
			for(s in freeSeatsNum){
				
				if(freeSeatsNum[projectionID]){
					
					seatsNum.text(freeSeatsNum[projectionID]);
				}
			}
			
		});
		
	
		var params = {
			
			'action' : 'getProjection',
			'projectionID' : projectionID
		}
	
		$.get('ProjectionServlet', params, function(data){
		
			console.log(data.selectedProjection);
		
			var projection = data.selectedProjection;
		
			if(data.status === 'success'){

			
				movieName.text(projection.movie.name);
			
				var movieLink = 'Movie.html?id=' + projection.movie.idMovie;
			
				movieName.attr('href', movieLink);
			
				type.text(projection.projectionType.name);
				hall.text(projection.hall.name);
				date.text(projection.dateTimeShow);
				price.text(projection.price);
				
				
				var today = new Date();
				
				var projShow = new Date(projection.dateTimeShow);
				
				if(projShow < today){
					
					btnBuy.hide();
				}
			}
		
		
		});
	
	
	}
	
	$('#btnBuy').on('click', function(event){
		
		var url = 'SecondPhase.html?id=' + projectionID;
		
		$(this).attr('href', url);
		
	});
	
	
	var allTickets = [];
	
	
	var table = $('#tableTickets');
	
	function getTickets(){
	
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
				
					allTickets.push(tickets[t]);
				
					table.append(
						'<tr>' +
							'<td>' + index++ + '</td>' +
							'<td>' + '<a href="Ticket.html?id=' + tickets[t].idTicket + '">' + tickets[t].dateTimeofSale + '</a></td>' +
							'<td>' + '<a href="User.html?username=' + tickets[t].user.username + '">' + tickets[t].user.username + '</a></td>' +
							'<td>' + '<button type="button" class="btn btn-danger" data-toggle="modal" data-target="#popupModal"'  + 
										' data-ticketID="'  +  tickets[t].idTicket  + '">Delete</button></td>' +
						'</tr>'
					);
				}
			}
		
		});
	
	}	
	
	var modal = $('#popupModal');
	var modalBody = $('#modalBody');
	var btnDeleteTicket = $('#btnDeleteTicket');
	
	var ticketID;

	
	
	modal.on('show.bs.modal', function(event){
		
		console.log(allTickets);
		
		var button = $(event.relatedTarget);
		
		ticketID = button.attr('data-ticketID');
		
		var today = new Date();
		
		var dd = String(today.getDate()).padStart(2, '0');
		var mm = String(today.getMonth() + 1).padStart(2, '0'); 
		var yyyy = today.getFullYear();
		
		var hh = String(today.getHours()).padStart(2, '0');
		var min = String(today.getMinutes()).padStart(2, '0');
		
		today = yyyy + '-' + mm + '-' + dd + ' ' + hh + ':' + min;
		
		console.log(today);
		
		for(t in allTickets){
			
			if(allTickets[t].projection.dateTimeShow < today){
				
				modalBody.text('You can not delete ticket because ticket projection is in the past!');
				
				btnDeleteTicket.hide();
			}
			if(allTickets[t].projection.dateTimeShow > today){
				
				modalBody.text('Are you sure you want delete this ticket?');
				
				btnDeleteTicket.show();
			}
		}
		
		
	});
	
	btnDeleteTicket.on('click', function(event){
		
		var params = {
				
				'ticketID': ticketID
		}
		
		$.post('TicketDeleteServlet', params, function(data){
			
			if(data.status == 'success'){
				
				alert('You successufully delete this ticket!');
				
				modal.modal('toggle');
				
				getTickets();
			}
			
			return false;
		});
		
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
	
	getProjection();
	
	
});