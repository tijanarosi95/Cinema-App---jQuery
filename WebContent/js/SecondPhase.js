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
	
	console.log(projectionID);
	
	var movieName = $('#moviename');
	var type = $('#type');
	var hall = $('#hall');
	var dateandtime = $('#dateandtime');
	var price = $('#price');
	
	var hallID;
	
	var params = {
			'action' : 'getProjection',
			'projectionID' : projectionID
	}
	
	$.get('ProjectionServlet', params, function(data){
		
		if(data.status == 'unauthenticated'){
			window.location.replace('index.html');
			return;
		}
		if(data.status == 'success'){
			
			var projection = data.selectedProjection;
			
			movieName.text(projection.movie.name);
			
			url = 'Movie.html?id=' + projection.movie.idMovie;
			
			movieName.attr('href', url);
			
			type.text(projection.projectionType.name);
			
			hall.text(projection.hall.name);
			
			hallID = projection.hall.id;
			
			href = 'Projection.html?id=' + projectionID;
			
			dateandtime.attr('href', href);
			
			dateandtime.text(projection.dateTimeShow);
			
			price.text(projection.price);
			
			getSeats();
		}
		
	});
	
	var graph = $('#graph');
	
	function getSeats(){
		
		
		
		var paraams ={
				'action': 'seats',
				'hallID' : hallID
		}
		
		$.get('ProjectionServlet', paraams, function(data){
			
			if(data.status == 'unauthenticated'){
				window.location.replace('index.html');
				return;
			}
			if(data.status == 'success'){
				
				var seats = data.seats;
				
				for(s in seats){
					
					//for(var j = 0; j < 6; j++){
						
					graph.append(
							
							
							'<td><input class="single-check" name="check" type="checkbox" data-toggle="toggle" data-onstyle="info" ' + 
								'data-offstyle="success" data-seatID="' + seats[s].serialNumber + '"></td>' 
							
						);
					
					//}
				}
				
			}
			
		});
	}
	
	
	
});