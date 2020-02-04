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
	
	
	$.get('UserServlet', {'loggedUser' : 'loggedUserRole'}, function(data){
		
		if(data.status == 'unauthenticated'){
			window.location.replace('index.html');
			return;
		}
	});
	
	var projectionID = window.location.search.split('&')[0].split('=')[1];
	var seats = window.location.search.split('&')[1].split('=')[1];
	
	console.log(projectionID);
	console.log(seats);
	
	var movieName = $('#moviename');
	var type = $('#type');
	var hall = $('#hall');
	var date = $('#dateandtime');
	var chosenSeats = $('#seats');
	var price = $('#price');
	
	var hallID;
	
	var params = {
			
			'action': 'getProjection',
			'projectionID': projectionID
	}
	
	
	
	$.get('ProjectionServlet', params, function(data){
		
		if(data.status == 'success'){
			
			var projection = data.selectedProjection;
			
			movieName.text(projection.movie.name);
			
			url = 'Movie.html?id=' + projection.movie.idMovie;
			
			movieName.attr('href', url);
			
			type.text(projection.projectionType.name);
			
			hall.text(projection.hall.name);
			
			hallID = projection.hall.id;
			
			href = 'Projection.html?id=' + projectionID;
			
			date.attr('href', href);
			
			date.text(projection.dateTimeShow);
			
			chosenSeats.text(seats);
			
			totalPrice = projection.price * seats.split(',').length;
			
			console.log(totalPrice);
			
			price.text(totalPrice);
		}
	});
	
	var btnBuy = $('#btnBuyTicket');
	
	
	
	btnBuy.on('click', function(event){
		
		var params = {
				
				'projectionID': projectionID,
				'seats': seats,
				'hallID': hallID
		}
		
		console.log(params);
		
		$.post('TicketServlet', params, function(data){
			
			if(data.status == 'success'){
				
				alert('Success!');
				
				window.location.replace('UserProfile.html');
			}
		});
		
		event.preventDefault();
		return false;
		
		
	});
	
	
	
});