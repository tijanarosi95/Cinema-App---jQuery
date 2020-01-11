$(document).ready(function(){
	$('#logoutMovies').on('click', function(event){
		$.get('LogoutServlet', function(data){
			console.log(data);
			if(data.status == 'unauthenticated'){
				window.location.replace('index.html');
				return;
			}
		});
		
		event.preventDefault();
		return false;
	});
	
	var movieInput = $('#movieNameInput');
	
	var genresSelect = $('.selectGenre');
	
	
	$.get('MovieServlet', {"action" : "genres"}, function(data){
		console.log(data);
		var genreCollection = data.genres;
		$.each(genreCollection, function(key, value){
			genresSelect.append($('<option></option>')
						.attr("value", key)
						.text(value));
		
		});
	});
	
	var sliderDuration = $('#sliderDuration');
	
	var durationMin = $('#durationMin');
	var durationMax = $('#durationMax');
	
	sliderDuration.slider({
		range: true,
		min: 0,
		max: 500,
		values:[90, 250],
		slide: function(event, ui){
			durationMin.val(ui.values[0]),
			durationMax.val(ui.values[1]);
		}
	});
	
	var distributionInput = $('#distributerInput');
	
	var originInput = $('#originCountryInput');
	
	var sliderYear = $('#sliderYear');
	var yearMin = $('#yearMin');
	var yearMax = $('#yearMax');
	
	sliderYear.slider({
		range: true,
		min: 1930,
		max: 2020,
		values: [1950, 2010],
		slide: function(event, ui){
			yearMin.val(ui.values[0]),
			yearMax.val(ui.values[1]);
		}
	});
	
	var moviesTable = $('#moviesTableId');
	
	function getMovies(){
		var movieName = movieInput.val();
		var genres = genresSelect.val();
		console.log(genres + " ----");
		var minDuration = durationMin.val();
		var maxDuration = durationMax.val();
		var distribution = distributionInput.val();
		var origin = originInput.val();
		var minYear = yearMin.val();
		var maxYear = yearMax.val();
		
		var params = {
				"movieName": movieName,
				"genre": genres,
				"minDuration": minDuration,
				"maxDuration": maxDuration,
				"distribution": distribution,
				"origin": origin,
				"minYear": minYear,
				"maxYear": maxYear		
		}
		
		console.log(params);
		
		$.get('AllMoviesServlet', params, function(data){
			console.log(data.status);
			
			if(data.status == 'unauthenticated'){
				window.location.replace('index.html');
				return;
			}
			
			if(data.status == 'success'){
				
				console.log(data);
				
				moviesTable.find("tr:gt(0)").remove();
				
				console.log(data.filteredMovies);
				
				var filteredMovies = data.filteredMovies;
				
				var index = 1;
				
				for(m in filteredMovies){
					
					moviesTable.append(
									'<tr>' +
										'<td>' + index++ + '</td>' +
										'<td><a href="Movie.html?id=' + filteredMovies[m].idMovie + '">' + filteredMovies[m].name + '</a></td>' +
    									'<td>' +  filteredMovies[m].genres + '</td>' +
    									'<td>' + filteredMovies[m].duration + '</td>' +
    									'<td>' + filteredMovies[m].distribution + '</td>' +
    									'<td>' + filteredMovies[m].originCountry + '</td>' +
    									'<td>' + filteredMovies[m].productionYear + '</td>' +
										'<td><a href="Movie.html?id=' + filteredMovies[m].idMovie +  '"><input type="submit" value="Change" class="btn btn-primary"/></a></td>' + 
										'<td><form><input type="submit" value="Delete" class="btn btn-primary delMovie"/>' +
										'</form></td>' +
    								'</tr>'	    
					);
				}
				
			}
			
		});	
	}
	
	var modalAdd = $('#addMovieModal');
	
	modalAdd.on('show.bs.modal', function(event){
		
		var button = $(event.relatedTarget);
	});
	
	var movieName = $('#movie-name');
	var selectedGenre = $('#selectedGenre');
	var directorName = $('#director-name');
	var actorsName = $('#actors-name');
	var durationNum = $('#durationNum');
	var distributionName= $('#distributionName');
	var originName = $('#origin-name');
	var year = $('#year-input');
	var descName = $('#desc-name');
	
	var addMovie = $('#addMovieSubmit');
	
	var movieNameInput;
	var directorNameInput;
	var genre;
	var actors;
	var duration;
	var distribution;
	var origin;
	var yearMovie;
	var description;
	
	
	addMovie.on('click', function(event){
		movieNameInput = movieName.val();
		directorNameInput = directorName.val();
		genre = selectedGenre.val();
		actors = actorsName.val();
		duration = durationNum.val();
		distribution = distributionName.val();
		origin = originName.val();
		yearMovie = year.val();
		description = descName.val();
		
		
		params = {
				'action' : 'add',
				'movieName' : movieNameInput,
				'directorName' : directorNameInput,
				'genre' : genre,
				'actors' : actors,
				'duration' : duration,
				'distribution' : distribution,
				'origin' : origin,
				'yearMovie' : yearMovie,
				'description' : description
		}
		
		console.log(params);
		
		if(validation()){
		
		$.post('AllMoviesServlet', params, function(data){
			
			console.log(data.status);
			
			if(data.status == 'unauthenticated'){
				window.location.replace('index.html');
				return;
			}
			if(data.status == 'unauthorized'){
				window.location.replace('index.html');
			}
			if(data.status == 'success'){
				alert('You successfully added new movie!');
				getMovies();
			}
				
		});
		
		}
		
		event.preventDefault();
		
		return false;
		
		//modalAdd.modal('toggle');
		
	});
	
	
	moviesTable.on('click', 'input.delMovie', function(event){
		
		var movieID = $(this).attr('movieId');
		console.log("movie id: " + movieID);
		
		$.get('ShowMovieServlet', {'movieID': movieID}, function(data){
			
			console.log(data);
			
			if(data.status == 'unauthenticated'){
				window.location.replace('index.html');
				return;
			}
			if(data.status == 'success'){
				
				var selectedMovie = data.selectedMovie;
				
				console.log(selectedMovie);
			}
		});
		
		
		return false;
	});
	

	function validation(){
		
		if(movieNameInput === ""){
			$('#messageName').text('You must input name!');
			return false;
		}
		if(directorNameInput === ""){
			$('#messageDir').text('You must input director!');
			return false;
		}
		if(duration === ""){
			('#messageDur').text('You must input duration!');
			return false;
		}
		if(actors === ""){
			$('#messageAct').text('You must input actors!');
			return false;
		}
		if(distribution === ""){
			$('#messageDist').text('You must input distribution!');
			return false;
		}
		if(origin === ""){
			$('#messageOrigin').text('You must input origin!');
			return false;
		}
		if(yearMovie === ""){
			$('#messageYear').text('You must input year!');
			return false;
		}
		if(description === ""){
			$('#messageDesc').text('You must input description!');
			return false;
		}
		return true;
	}
	
movieInput.on('keyup', function(event){
	
	getMovies();
	
	event.preventDefault();
	return false;
});

genresSelect.on('change', function(event){
	
	getMovies();
	
	event.preventDefault();
	return false;
});

sliderDuration.on('slide', function(event){
	
	getMovies();
	
	//event.preventDefault();
	//return false;
});

distributionInput.on('keyup', function(event){
	
	getMovies();
	
	event.preventDefault();
	return false;
});

originInput.on('keyup', function(event){
	
	getMovies();
	
	event.preventDefault();
	return false;
});

sliderYear.on('slide', function(event){
	
	getMovies();
	
	//event.preventDefault();
	//return false;
});

getMovies();	
	
});