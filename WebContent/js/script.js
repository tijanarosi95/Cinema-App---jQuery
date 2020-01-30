$(document).ready(function(){
	
	$('.collapse').collapse();
	
	var movieNameInput = $('#movieName');
	
	var selectType = $('.multi-select');
	var selectHall = $('.multi-select2');
	
	var select = $('.form-control1');
	
	select.select2({
		width: 'element',
		tags: true,
		tokenSeparators: [',']
	});
		
	
	$.get('ProjectionServlet', {'action' : 'projectionTypes'}, function(data){
		console.log(data.projectionTypes);
		var typesCollection = data.projectionTypes;
		for(type in typesCollection){
			var option = new Option(typesCollection[type].name, typesCollection[type].id);
			selectType.append(option);
		}
		
	});
	
	$.get('ProjectionServlet', {'action': 'halls'}, function(data){
		console.log(data.halls);
		var hallsCollection = data.halls;
		for(hall in hallsCollection){
			var option = new Option(hallsCollection[hall].name, hallsCollection[hall].id);
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
		useCurrent : false,
		step : 5,
	});
	
	
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
			
			var list = data.filteredProjections;
			console.log(data);
			
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
				
				for(p in filteredProjections){
					
					console.log(p.dateTimeShow);
					
					if(p.dateTimeShow == today){
						
						console.log("Condition is true!");
					}
				}
			}
			
		});
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

	getProjections();
});
