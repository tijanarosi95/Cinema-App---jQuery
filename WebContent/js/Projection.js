$(document).ready(function(){
	
	var adminProfile = $('#adminProfile');
	var adminLogout = $('#adminLogout');
	
	var userRole;
	
	$.get('UserServlet', {'loggedUser' : 'loggedUserRole'}, function(data){
		
		userRole = data.loggedUserRole;
		
		if(userRole == null){
			
			adminProfile.hide();
			adminLogout.hide();
		}else{
			
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
	
	
});