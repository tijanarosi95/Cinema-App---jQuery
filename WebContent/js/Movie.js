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

	var adminForm = $('#adminChangeForm');
	var adminProfile = $('#adminProfile');
	var userLogout = $('#userLogout');
	
	var userForm = $('#userViewForm');
	var userProfile = $('#userProfile');
	var userRole;

	var btnBuyTicket = $('#liBuyTicket');

	btnBuyTicket.on('click', function(e){
		
		window.location.replace('FirstPhase.html?movieID=' + movieID);
	});
	

	
	$.get('UserServlet', {'loggedUser': 'loggedUserRole'}, function(data){
		
		
		userRole = data.loggedUserRole;
		console.log('UserRoleOnMoviePage: ' + userRole);
		
		if(userRole == null){
			adminForm.hide();
			adminProfile.hide();
			userLogout.hide();
			userProfile.hide();

			btnBuyTicket.hide();
			
			userForm.show();
			
		}else if(userRole == 'ADMIN'){
			adminForm.show();
			adminProfile.show();
			userLogout.show();

			userForm.hide();
			btnBuyTicket.hide();

		}else if(userRole == 'USER'){
			adminForm.hide();
			adminProfile.hide();

			userProfile.show();
			userLogout.show();

			btnBuyTicket.show();
		}
	});
	
	
    var selectGenre = $('#selectMovieGenre');

    selectGenre.select2({
    	tags: true,
    	tokenSeparators: [','],
    	
    });
    
    $.get('MovieServlet', {'action' : 'genres'}, function(data){

        var genres = data.genres;
        $.each(genres, function(key, value){
            selectGenre.append($('<option></option>')
                                .attr('value', key)
                                .text(value));
        });
    });
    
    

    var movieID = window.location.search.split('&')[0].split('=')[1];
    //var mode = window.location.search.split('&')[1].split('=')[1];

    console.log(movieID);
    
    
    var movieInput = $('#movieNameInput');
    var directorInput = $('#directorInput');
    var actorInput = $('#actorInput');
    var durationInput = $('#durationInput');
    var distributionInput = $('#distributionInput');
    var originInput = $('#originInput');
    var yearInput = $('#yearInput');
    var descInput = $('#descriptionInput');
    
    var changeMovie = $('#btnChangeMovie');
    
    var params = {
    		'action' : 'getMovie',
    		'movieID' : movieID,
    }
    
    function getMovie(){
    	$.get('MovieServlet', params, function(data){
    		
    		console.log(data);
    		
    		if(data.status == 'unauthenticated'){
    			window.location.replace('index.html');
    			return;
    		}
    		if(data.status == 'success'){
    			
    			
    			var chosenMovie = data.selectedMovie;
    			
    			if(userRole == 'ADMIN'){
    				
					console.log(chosenMovie);
					
					adminForm.show();
					userForm.hide();
    				
    				movieInput.val(chosenMovie.name);
    				directorInput.val(chosenMovie.director);
    				actorInput.val(chosenMovie.actors);
    				
    				var movieGenres = chosenMovie.genres;
    				
    				for(option in movieGenres){
    					var checkedOption = new Option(movieGenres[option], movieGenres[option], true, true);
    					selectGenre.append(checkedOption).trigger('change');
    				}
    				
    				durationInput.val(chosenMovie.duration);
    				distributionInput.val(chosenMovie.distribution);
    				originInput.val(chosenMovie.originCountry);
    				yearInput.val(chosenMovie.productionYear);
    				descInput.val(chosenMovie.description);
    				
    				
    				
    				var movie, director, actors, genres, duration, distribution, origin, year, description;
    				
    				
    				changeMovie.on('click', function(event){
    					
    					movie = movieInput.val();
    					director = directorInput.val();
    					actors = actorInput.val();
    					console.log(movie);
    					console.log(director);
    					console.log(actors);
    					
    					genres = selectGenre.select2('val').toString();	
    		
    					duration = durationInput.val();
    					distribution = distributionInput.val();
    					origin = originInput.val();
    					year = yearInput.val();
    					description = descInput.val();
    					
    					params = {'action' : 'update',
    							'movieID' : movieID,
    							'movieName' : movie,
    							'director' : director,
    							'actors' : actors,
    							'genres' : genres,
    							'duration': duration,
    							'distribution': distribution,
    							'origin' : origin,
    							'year': year,
    							'description': description
    							}
    					
    					console.log(params);
    					
    					if(validation()){
    					
    					$.post('AllMoviesServlet', params, function(data){
    						
    						if(data.status == 'unauthenticated'){
    							window.location.replace('index.html');
    							return;
    						}
    						if(data.status == 'success'){
    							alert('You are successfully update movie!');
    							window.location.replace('AllMovies.html');
    						}
    					});
    					
    					}
    					event.preventDefault();
    					return false;
    				});
    				
    					
    				
    				function validation(){
    					if(movie === ""){
    						$('#movieNameChange').text('You didnt enter new movie name!');
    						return false;
    					}
    					if(director === ""){
    						$('#directorChange').text('You didnt enter director!');
    						return false;
    					}
    					if(actors === ""){
    						$('#actorChange').text('You didnt enter new actors!');
    						return false;
    					}
    					if(genres === ""){
    						$('#genreChange').text('You didnt enter new genres!');
    						return false;
    					}
    					if(duration === ""){
    						$('#durationChange').text('You didnt enter duration!');
    						return false;
    					}
    					if(distribution === ""){
    						$('#distibutionChange').text('You didnt enter distribution!');
    						return false;
    					}
    					if(origin === ""){
    						$('#originChange').text('You didnt enter origin');
    						return false;
    					}
    					if(year === ""){
    						$('#yearChange').text('You didnt enter year!');
    						return false;
    					}
    					if(description === ""){
    						$('#descriptionChange').text('You didnt enter description!');
    						return false;
    					}
    					return true;
    				}
    				
    			
    			
				}else{
					
					$('#movname').text(chosenMovie.name);
					$('#director').text(chosenMovie.director);
					$('#actors').text(chosenMovie.actors);
					$('#genre').text(chosenMovie.genres);
					$('#duration').text(chosenMovie.duration);
					$('#distribution').text(chosenMovie.distribution);
					$('#year').text(chosenMovie.productionYear);
					$('#origin').text(chosenMovie.originCountry);
					$('#description').text(chosenMovie.description);
				
				}	
    		
			}
		});
    	
	}	
    
    
    getMovie();
});