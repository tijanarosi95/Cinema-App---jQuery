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
	
	var toThirdPhase = $('#toThirdPhase');
	
	function getSeats(){
		
		var parameters = {
				
				'action': 'getBusySeats',
				'projID': projectionID
		}
		
		var busySeats;
		
		$.get('ProjectionServlet', parameters, function(data){
			
			busySeats = data.busySeats;
			
		});		
		console.log(busySeats);
		
		var paraams = {
				
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
				
				
				
				for(seat in seats){
					
					if(busySeats.some(s => s.serialNumber === seats[seat].serialNumber)){
						
						$('#seatDiv').append(
							'<input class="single-check" name="check" type="checkbox" data-toggle="toggle" data-onstyle="info" seatnum="' +  seats[seat].serialNumber +
							'" data-offstyle="dark" data-off="' + seats[seat].serialNumber  +  '" data-on="' +  seats[seat].serialNumber   + '" disabled="disabled">' );
						
						
					
					}
					if(!busySeats.some(s => s.serialNumber === seats[seat].serialNumber)){
						
						$('#seatDiv').append(
								'<input class="single-check" name="check" type="checkbox" data-toggle="toggle" data-onstyle="info" seatnum="' +  seats[seat].serialNumber +
								'" data-offstyle="light" data-off="' + seats[seat].serialNumber  +  '" data-on="' +  seats[seat].serialNumber   + '">' );
							
						
					}
				}
				
				$('.single-check').bootstrapToggle();
				
				
				
				$('.single-check').on('change', function(event){
					
					var seatNum;
					
					var checked = [];
					
					var name = $(this).attr('name');
					
					if($('input[name=' + name  + ']:checked').length >= 1){
										
						$.each($("input[name='check']:checked"), function(){
							
							
							
							seatNum = $(this).attr('seatnum');
							
							checked.push(seatNum);
						});
						
						toThirdPhase.show();
						
						var url = 'ThirdPhase.html?id=' + projectionID + '&seat=' +  checked.toString();
						
						toThirdPhase.attr('href', url);
						
						console.log(url);
						
					}
					
					if(seatNum == null){
						
						toThirdPhase.hide();
						
					}else{
						
						toThirdPhase.show();
					}
					
					
					
					
				});
				
			}
			
		});
	}
	
	
	
});