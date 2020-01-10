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

});