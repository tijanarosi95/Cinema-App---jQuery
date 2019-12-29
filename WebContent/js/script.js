//accordion
$('.collapse').collapse();

//multiselect
$(document).ready(function(){
    $('.mdb-select').multiselect({
        includeSelectAllOption : true
    });
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
    $('#datetimepicker1').datetimepicker();
     
    $('#datetimepicker2').datetimepicker({
        useCurrent: false,
        onSelect: function(dateText) {
            console.log("Selected date: " + dateText + "; input's current value: " + this.value);
        }
    });

    $("#datetimepicker1").on("dp.change", function (e) {
        $('#datetimepicker2').data("DateTimePicker").minDate(e.date);
    });
    $("#datetimepicker2").on("dp.change", function (e) {
        $('#datetimepicker1').data("DateTimePicker").maxDate(e.date);
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
