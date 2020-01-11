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

    console.log(movieID);
    
    var movieInput = $('#movieNameInput');
    var directorInput = $('#directorInput');
    var actorInput = $('#actorInput');
    var durationInput = $('#durationInput');
    var distributionInput = $('#distributionInput');
    var originInput = $('#originInput');
    var yearInput = $('#yearInput');
    var descInput = $('#descriptionInput');
    
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
    			
    			if(loggedUser.role == 'ADMIN'){
    				
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
    				
    				$('#btnChangeUser').on('click', function(event){
    					
    					var movie = movieInput.val();
    					var director = directorInput.val();
    					var actors = actorInput.val();
    					console.log(movie);
    					console.log(director);
    					console.log(actors);
    					
    					var genres = selectGenre.select2('val').toString();	
    		
    					var duration = durationInput.val();
    					var distribution = distributionInput.val();
    					var year = yearInput.val();
    					var description = descInput.val();
    					
    					params = {'action' : 'update',
    							'movieID' : movieID,
    							'movieName' : movie,
    							'director' : director,
    							'actors' : actors,
    							'genres' : genres,
    							'duration': duration,
    							'distribution': distribution,
    							'year': year,
    							'description': description
    							}
    					
    					console.log(params);
    					
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
    					
    					event.preventDefault();
    					return false;
    				});
    				
    			}
    			
    			
    		}
    		
    	});
    	
    	
    }
    
    getMovie();
});