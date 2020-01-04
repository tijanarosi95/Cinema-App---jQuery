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


    var username =  window.location.search.split('&')[0].split('=')[1];
    
    
    console.log("link: " + username);

    function getChosenUser(){
        $.get('UserServlet', {}, function(data){

            console.log(data);

            if(data.status == 'unauthenticated'){
                window.location.replace('index.html');
                return;
            }

            if(data.status == 'success'){
                
            	var selectedUser = data.chosenUser;
            	console.log(selectedUser);
            }
        });

    }
    
    getChosenUser();
});