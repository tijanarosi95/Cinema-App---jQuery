$(document).ready(function(){
	
	$('.collapse').collapse();
	
	var userRole;
	
	var userProfile = $('#userProfile');
	var adminProfile = $('#adminProfile');
	var btnAddProjection = $('#liAddProjection');
	
	var btnLogIn = $('#btnLogIn');
	var btnRegister = $('#btnRegister');
	
	var logo = $('#allProjections');
	
	$.get('UserServlet', {'loggedUser': 'loggedUserRole'}, function(data){
		
		userRole = data.loggedUserRole;
		
		if(userRole == null){
			
			btnLogIn.show();
			btnRegister.show();
			
			userProfile.hide();
			adminProfile.hide();
			btnAddProjection.hide();
			
		}else if (userRole == 'ADMIN'){
			
			btnLogIn.hide();
			btnRegister.hide();
			
			userProfile.hide();
			adminProfile.show();
			btnAddProjection.show();
			
			logo.text('All Projections');
			
		}else if(userRole == 'USER'){
			
			btnLogIn.hide();
			btnRegister.hide();
			
			userProfile.show();
			adminProfile.hide();
			
			btnAddProjection.hide();
			
			logo.text('All Projections');
		}
		
	});
	
	var selectedType = $('#selectedType');
	var selectedHall = $('#selectedHall');
	var dateTime = $('#dateTimeInput');
	var price = $('#priceID');
	
	var modal = $('#addProjModal');
	var btnAddSubmit = $('#addProjSubmit');
	
	var selectMovie = $('#selectedMovie');
	
	
	var params = {
			
			'movieName' : '',
			'genre': '',
			'minDuration': '',
			'maxDuration': '',
			'distribution': '',
			'origin': '',
			'minYear': '',
			'maxYear': ''
	}
	
	$.get('AllMoviesServlet', params, function(data){
		
		if(data.status == 'success'){
			
			var allMovies = data.filteredMovies;
			
			for(movie in allMovies){
				
				var option = new Option(allMovies[movie].name, allMovies[movie].idMovie);
				
				selectMovie.append(option);
			}
		}
		
	});
	
	var movie, type, hall, datetime, ticketPrice;
	
	btnAddSubmit.on('click', function(event){
		
		movie = selectMovie.val();
		type = selectedType.val();
		hall = selectedHall.val();
		datetime = dateTime.val();
		ticketPrice = price.val();
		
		var params = {
				
				'action': 'add',
				'movie' : movie,
				'type': type,
				'hall': hall,
				'datetime': datetime,
				'ticketPrice': ticketPrice
		}
		
		console.log(params);
		
		if(isValid()){
			
			if(isFree()){
			
				$.post('ProjectionServlet', params, function(data){
				
					
					if(data.status == 'unauthenticated'){
						
						window.location.replace('index.html');
						return;
					}
					if(data.status == 'success'){
						
						alert('You successfully added new projection!');
						
						modal.modal('toggle');
						
						modal.on('hidden.bs.modal', function(event){
							
							$(this).find('form').trigger('reset');
							
							getProjections();
						});
					}
					
					
				});
			
			}
		}
		
		
	});
	
	function isValid(){
		
		var today = new Date();
		
		var dd = String(today.getDate()).padStart(2, '0');
		var mm = String(today.getMonth() + 1).padStart(2, '0'); 
		var yyyy = today.getFullYear();
		
		var hh = String(today.getHours()).padStart(2, '0');
		var min = String(today.getMinutes()).padStart(2, '0');
		
		today = yyyy + '-' + mm + '-' + dd + ' ' + hh + ':' + min;
		
		
		console.log(today);
		
		for(h in hallsCollection){
			
			console.log(hallsCollection[h]);
			
			if(hallsCollection[h].id == hall){
				
				var hallsTypes = hallsCollection[h].projectionTypes;
				
				console.log(hallsTypes);
				
			}
		}
		
		if(!hallsTypes.some(t => t.id === Number(type))){
			
			$('#messageType').text('This projection type does not exist in selected hall!');
			
			return false;
		}
		

		if(datetime < today){
			
			$('#messageDate').text('Date can not be in the past!');
			
			return false;
		}
		if(datetime === ""){
			
			$('#messageDate').text('Date can not be empty!');
			
			return false;
		}
		if(price === ""){
			
			$('#messagePrice').text('Price can not be empty!');
			
			return false;
		}
		
		return true;
	}
	
	
	
	function isFree(){
		
		for(p in allProjections){
			
			if(allProjections[p].hall.id == hall && 
					allProjections[p].dateTimeShow.split(' ')[0] == datetime.split(' ')[0]){
				
				var newProjDate = new Date(datetime);
				
				var movieDuration = allProjections[p].movie.duration;
				
				var startAt = new Date(allProjections[p].dateTimeShow);
				
				var finishedAt = new Date(startAt.getTime() + allProjections[p].movie.duration * 60000);
				
				if(newProjDate <= finishedAt && newProjDate >= startAt){
					
					$('#messageDate').text('There is already projection in entered time!');
					
					return false;
				}
				
				if(new Date(newProjDate.getTime() + movieDuration * 60000) <= finishedAt &&
						new Date(newProjDate.getTime() + movieDuration * 60000) >= startAt){
					
					console.log(movieDuration);
					
					$('#messageDate').text('There is already projection in entered time!');
					
					return false;
				}
			}
		}
		
		return true;
		
	}
	
	var movieNameInput = $('#movieName');
	
	var selectType = $('.multi-select');
	var selectHall = $('.multi-select2');
	
	var select = $('.form-control1');
	
	select.select2({
		width: 'element',
		tokenSeparators: [',']
	});
		
	
	$.get('ProjectionServlet', {'action' : 'projectionTypes'}, function(data){
		console.log(data.projectionTypes);
		var typesCollection = data.projectionTypes;
		for(type in typesCollection){
			var option = new Option(typesCollection[type].name, typesCollection[type].id, false, false);
			selectType.append(option);
		}
		
	});
	
	var hallsCollection;
	
	$.get('ProjectionServlet', {'action': 'halls'}, function(data){
		console.log(data.halls);
		hallsCollection = data.halls;
		for(hall in hallsCollection){
			var option = new Option(hallsCollection[hall].name, hallsCollection[hall].id, false, false);
			selectHall.append(option);
		}
	});
	
	var sliderPrice = $('#slider-range');
	
	var priceMin = $('#priceMin');
	var priceMax = $('#priceMax');
	
	sliderPrice.slider({
		range : true,
		min : 0,
		max : 800,
		values : [150, 550],
		slide : function(event, ui){
			priceMin.val(ui.values[0]);
			priceMax.val(ui.values[1]);
		}
	});
	
	var date = $('.date');
	
	var dateFromInput = $('#date1');
	var dateToInput = $('#date2');
	
	
	date.datetimepicker({
		format : 'yy-m-d H:i',
		useCurrent : false,
		step : 15,
	});
	
	var allProjections = [];
	
	var projectionTable = $('#projectionTable');
	
	function getProjections(){
		
		var movieName = movieNameInput.val();
		var types = selectType.val().toString();
		var halls = selectHall.val().toString();
		var minPrice = priceMin.val();
		var maxPrice = priceMax.val();
		var dateFrom = dateFromInput.val();
		var dateTo = dateToInput.val();
		
		var params = {
				'action': 'getAll',
				'movieName' : movieName,
				'types' : types,
				'halls' : halls,
				'minPrice' : minPrice,
				'maxPrice' : maxPrice,
				'dateFrom' : dateFrom,
				'dateTo' : dateTo
		}
		
		console.log(params);
		
		$.get('AllProjectionsServlet', params, function(data){
			
			
			if(data.status == 'success'){
				
				projectionTable.find('tr:gt(0)').remove();
				 
				var filteredProjections = data.filteredProjections;
				
				var index =1;
				
				var today = new Date();
				
				var dd = String(today.getDate()).padStart(2, '0');
				var mm = String(today.getMonth() + 1).padStart(2, '0'); 
				var yyyy = today.getFullYear();

				today = yyyy + '-' + mm + '-' + dd;
				
				console.log(today);
				
				console.log(userRole);
				
				for(p in filteredProjections){
					
					allProjections.push(filteredProjections[p]);
					
					var dateProjection = filteredProjections[p].dateTimeShow.split(' ')[0];
					
					if(userRole === 'ADMIN'){
						
						projectionTable.append(
								
								'<tr>' +
									'<td>' + index++ + '</td>' +
									'<td><a href="Movie.html?id=' + filteredProjections[p].movie.idMovie + '">' + filteredProjections[p].movie.name + '</a></td>' +
									'<td>' + filteredProjections[p].projectionType.name + '</td>' +
									'<td>' + filteredProjections[p].hall.name + '</td>' +
									'<td>'  + '<a href="Projection.html?id=' + filteredProjections[p].idProjection + '">' + filteredProjections[p].dateTimeShow + '</a>' + '</td>' +
									'<td>' + filteredProjections[p].price + '</td>' +
									'<td>' +  '<button type="button" class="btn btn-danger" data-toggle="modal" data-target="#popupModal"' +
												' data-projID="' + filteredProjections[p].idProjection + '">Delete</button></td>' +
								'</tr>'
								
							);
						
					}else if(dateProjection === today){
						
						projectionTable.append(
								
								'<tr>' +
									'<td>' + index++ + '</td>' +
									'<td><a href="Movie.html?id=' + filteredProjections[p].movie.idMovie + '">' + filteredProjections[p].movie.name + '</a></td>' +
									'<td>' + filteredProjections[p].projectionType.name + '</td>' +
									'<td>' + filteredProjections[p].hall.name + '</td>' +
									'<td>'  + '<a href="Projection.html?id=' + filteredProjections[p].idProjection + '">' + filteredProjections[p].dateTimeShow + '</a></td>' +
									'<td>' + filteredProjections[p].price + '</td>' +
								'</tr>'
								
							);
					} 
				}
			}
			
		});
		
	}
	
	var confirmModal = $('#popupModal');
	var modalBody = $('#modalBody');
	var btnDelete = $('#btnDeleteProjection');
	
	var projectionID;
	
	confirmModal.on('show.bs.modal', function(event){
		
		var button = $(event.relatedTarget);
		
		projectionID = button.attr('data-projID');
		
		console.log(projectionID);
		
		modalBody.text('Are you sure you want delete this projection?');
		
	});
	
	
	
	btnDelete.on('click', function(event){
		
		console.log(projectionID + "+++");
		
		var params = {
				
				'action': 'delete',
				'projID': projectionID
		}
		
		$.post('ProjectionServlet', params, function(data){
			
			if(data.status == 'success'){
				
				alert('You successfully delete projection!');
				
				confirmModal.modal('toggle');
				
				getProjections();
			}
			
			return false;
		});
		
	});
	
	
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
	
	
	
	movieNameInput.on('keyup', function(event){
		
		getProjections();
		
		event.preventDefault();
		return false;
	});
	
	selectType.on('change', function(event){
		
		getProjections();
		
		event.preventDefault();
		return false;
		
	});
	
	selectHall.on('change', function(event){
		
		getProjections();
		
		event.preventDefault();
		return false;
	});
	
	sliderPrice.on('slide', function(event){
		
		getProjections();
		
	});
	
	$('#datetimepicker1').on('change', function(event){
		
		dateFromInput.val($(this).val());
		
		
		getProjections();
		
		event.preventDefault();
		return false;
		
	});
	
	$('#datetimepicker2').on('change', function(event){
		
		dateToInput.val($(this).val());
		
		getProjections();
		
		event.preventDefault();
		return false;
	});
	
	$('#datetimeAdd').on('change', function(event){
		
		dateTime.val($(this).val());
		
		event.preventDefault();
	});

	getProjections();
});
