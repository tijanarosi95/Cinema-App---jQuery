//accordion
$('.collapse').collapse();

//multiselect
$(document).ready(function(){
    $('#multi-select').multiselect();
});

$(document).ready(function(){
    $('#multi-select2').multiselect();
});

//jquery's range slider
$(document).ready(function(){
    $('#slider-range').slider({
        range : true,
        min : 0,
        max : 800,
        values : [150, 500],
        slide : function(event, ui){
            $('#amount').val(ui.values[0] + " - " + ui.values[1] + " din");
        }
    });
    $('#amount').val($('#slider-range').slider("values", 0) + " - " + $('#slider-range').slider("values", 1));
});

//jquery's datetime picker
$(function () {
    
    $('#datetimepicker1').datetimepicker({ 
        useCurrent: false,
        step : 5});

    $('#datetimepicker1').on('change', function(){
        var selected = $(this).val();
        //alert(selected);
        $('#date1').val(selected);
    });
     
    $('#datetimepicker2').datetimepicker({
        useCurrent: false,
        step: 5
    });

    $('#datetimepicker2').on('change', function(){
        var selected = $(this).val();
        $('#date2').val(selected);
    }); 
   
});

//jquery input matching, isn't working :(
$('#password','#passwordConfirm').on('keyup', function(){
    if($('#password').val() == $('#passwordConfirm').val()){
        $('#message').html('Matching').css('color', 'green');
    }else{
        $('#message').html('Not matching').css('color', 'red');
    }
});
