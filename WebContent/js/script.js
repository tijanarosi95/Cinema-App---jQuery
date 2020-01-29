$(document).ready(function(){
	
	$('.collapse').collapse();
	
	var movieName = $('#movieName');
	
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
	
	var dateFrom = $('#date1');
	var dateTo = $('#date2');
	
	
	date.datetimepicker({
		useCurrent : false,
		step : 5,
	});
	
	
	var projectionTable = $('#projectionTable');

});
