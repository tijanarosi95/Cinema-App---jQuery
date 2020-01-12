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
    var mode = window.location.search.split('&')[1].split('=')[1];

    console.log(movieID);
    console.log(mode);
    
    var movieInput = $('#movieNameInput');
    var directorInput = $('#directorInput');
    var actorInput = $('#actorInput');
    var durationInput = $('#durationInput');
    var distributionInput = $('#distributionInput');
    var originInput = $('#originInput');
    var yearInput = $('#yearInput');
    var descInput = $('#descriptionInput');
    
    var changeMovie = $('#btnChangeMovie');
    
    function getMovie(){
    	$.get('ShowMovieServlet', {'movieID' : movieID}, function(data){
    		
    		console.log(data);
    		
    		if(data.status == 'unauthenticated'){
    			window.location.replace('index.html');
    			return;
    		}
    		if(data.status == 'success'){
    			
    			var loggedUser = data.loggedInUser;
    			var chosenMovie = data.selectedMovie;
    			
    			if(loggedUser.role == 'ADMIN' && mode == 'change'){
    				
    				console.log(chosenMovie);
    				
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
    				
    			}if(loggedUser.role == 'ADMIN' && mode == 'show'){
    				
    				console.log(chosenMovie);
    				
    				movieInput.val(chosenMovie.name);
    				movieInput.prop('disabled', true);
    				
    				directorInput.val(chosenMovie.director);
    				directorInput.prop('disabled', true);
    				
    				actorInput.val(chosenMovie.actors);
    				actorInput.prop('disabled', true);
    				
    				var movieGenres = chosenMovie.genres;
    				
    				for(option in movieGenres){
    					var checkedOption = new Option(movieGenres[option], movieGenres[option], true, true);
    					selectGenre.append(checkedOption).trigger('change');
    				}
    				selectGenre.prop('disabled', true);
    				
    				durationInput.val(chosenMovie.duration);
    				durationInput.prop('disabled', true);
    				
    				distributionInput.val(chosenMovie.distribution);
    				distributionInput.prop('disabled', true);
    				
    				originInput.val(chosenMovie.originCountry);
    				originInput.prop('disabled', true);
    				
    				yearInput.val(chosenMovie.productionYear);
    				yearInput.prop('disabled', true);
    				
    				descInput.val(chosenMovie.description);
    				descInput.prop('disabled', true);
    				
    				changeMovie.hide();
    			}
    			
    			
    		}
    		
    	});
    	
    	
    }
    
    getMovie();
});