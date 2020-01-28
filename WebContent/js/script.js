$(document).ready(function(){
	
	$('.collapse').collapse();
	
	var movieName = $('#movieName');
	
	var selectType = $('#multi-select');
	var selectHall = $('#multi-select2');
	
	var select = $('.form-control');
	
	select.select2({
		width: 'element',
		tags: true,
		tokenSeparator: [',']
	});
		
	

});
