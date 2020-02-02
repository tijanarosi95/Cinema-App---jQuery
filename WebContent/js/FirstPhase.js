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
	
	var movieID = window.location.search.split('&')[0].split('=')[1];
	console.log('Movie ID: ' + movieID);
	
	var movieName = $('#movname');
	
	var params = {
			'action' : 'getMovie',
			'movieID' : movieID
	}
	
	$.get('MovieServlet', params, function(data){
		
		if(data.status == 'unauthenticated'){
			window.location.replace('index.html');
			return;
		}
		if(data.status == 'success'){
			
			movieName.text(data.selectedMovie.name);
			
			movieLink = 'Movie.html?id=' + data.selectedMovie.idMovie;
			movieName.attr('href', movieLink);
		}
	});
	
	var table = $('#someProjections');
	
	function getProjections(){
		
		var params = {
				'action' : 'getSome',
				'movieID' : movieID
		}
	
		$.get('AllProjectionsServlet', params, function(data){
		
			console.log(data);
			
			if(data.status == 'unauthenticated'){
				window.location.replace('index.html');
				return;
			}
			if(data.status == 'success'){
				
				table.find('tr:gt(0)').remove();
				
				var projections = data.projections;
				
				var index = 1;
				
				for(p in projections){
					
					table.append(
						'<tr>' +
							'<td>' + index++ + '</td>' +
							'<td>'  + '<a href="Projection.html?id=' + projections[p].idProjection + '">' + projections[p].dateTimeShow + '</a>' + '</td>' +
							'<td>' + projections[p].projectionType.name + '</td>' +
							'<td>' + projections[p].hall.name + '</td>' +
							'<td>' + projections[p].price + '</td>' +
							'<td>' +  '<input class="single-check" type="checkbox" data-toggle="toggle" data-onstyle="info" ' + 
							'data-offstyle="light" data-projID="' +  projections[p].idProjection  + '">'  +  '</td>' +
						'</tr>'
					);
					
				}
				
				$('.single-check').bootstrapToggle({});
			}
		
		});
		
	}
	
	
	getProjections();
});