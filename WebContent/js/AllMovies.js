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
	
	$('.collapse').collapse();
	
	var movieInput = $('#movieNameInput');
	
	var genresSelect = $('.selectGenre');
	
	genresSelect.select2({
		width: 'element',
		tags: true,
    	tokenSeparators: [','],
	});
	
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
		var genres = genresSelect.val().toString();
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
										'<td><a href="Movie.html?id=' + filteredMovies[m].idMovie + '&mode=show">' + filteredMovies[m].name + '</a></td>' +
    									'<td>' +  filteredMovies[m].genres + '</td>' +
    									'<td>' + filteredMovies[m].duration + '</td>' +
    									'<td>' + filteredMovies[m].distribution + '</td>' +
    									'<td>' + filteredMovies[m].originCountry + '</td>' +
    									'<td>' + filteredMovies[m].productionYear + '</td>' +
										'<td><a href="Movie.html?id=' + filteredMovies[m].idMovie +  '&mode=change"><input type="submit" value="Change" class="btn btn-primary"/></a></td>' + 
										'<td><button type="button" class="btn btn-danger" data-toggle="modal" data-target="#popupModal" data-movieID="' + filteredMovies[m].idMovie + '">Delete' +
										'</button></td>' +
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
	
	
	var movieNameInput, directorNameInput, genre,actors, duration, distribution, origin, yearMovie, description;

		
	addMovie.on('click', function(event){
		movieNameInput = movieName.val();
		directorNameInput = directorName.val();
		genre = selectedGenre.val().toString();
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
				
				modalAdd.modal('toggle');
				
				modalAdd.on('hidden.bs.modal', function(event){
					$(this).find('form').trigger('reset');
					selectedGenre.val(null).trigger('change');
				});
				
				getMovies();
			}
				
		});
		
		}
		
		return false;
		
	});
	
	var confirmModal = $('#popupModal');
	var modalBody = $('#modalBody');
	var deleteMovie = $('#btnDeleteMovie');
	
	var movieID;
	
	confirmModal.on('show.bs.modal', function(event){
		
		var button = $(event.relatedTarget);
		movieID = button.attr('data-movieID');
		
		console.log("movie id: " + movieID);
		
		
		
		modalBody.text('Are you sure you want to delete this movie?');
		
		
		
	});
	
	deleteMovie.on('click', function(event){
			
		var params = {
					'action' : 'delete',
					'movieID' : movieID,
					}
			
		console.log(params);
			
		$.post('AllMoviesServlet', params, function(data){
				
			if(data.status == 'unauthenticated'){
				window.location.replace('index.html');
				return;
			}
			if(data.status == 'unauthorized'){
				window.location.replace('index.html');
				return;
			}
			if(data.status == 'success'){
				alert('You successfully delete this movie!');
				confirmModal.modal('toggle');
				getMovies();
			}
				
		});
		
		event.preventDefault();
		
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