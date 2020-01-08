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
	
	var genresSelect = $('#selectGenre');
	
	
	$.get('MovieServlet', {"action" : "genres"}, function(data){
		console.log(data);
		var genreCollection = data.genres;
		$.each(genreCollection, function(key, value){
			genresSelect.append($('<option></option>')
						.attr("value", key)
						.text(value));
		
		});
	});
	
	var durationMin = $('#durationMin');
	var durationMax = $('#durationMax');
	
	$('#sliderDuration').slider({
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
	
	var yearMin = $('#yearMin');
	var yearMax = $('#yearMax');
	
	$('#sliderYear').slider({
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
    									'<td><form><input type="submit" value="Change" class="btn btn-primary" movieId="' + filteredMovies[m].idMovie  +'"></form></td>' +
    								'</tr>'	    
					);
				}
				
			}
			
		});
		
		
		
	}
	
	 
	
	
	
	
	
	
	
getMovies();	
	
	
});