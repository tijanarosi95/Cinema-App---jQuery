$(document).ready(function(){
	$('#logOutId').on('click', function(event){
		$.get('LogoutServlet', function(data){
			
			if(data.status == 'unauthenticated'){
				console.log(data);
				window.location.replace('index.html');
			}
		});
		
		event.preventDefault();
		return false;
	});
	
	
	var oldPassword = $('#oldPassword');
	var newPassword = $('#newPassword');
	
	var message = $('#messagePassword');
	
	var btnChange = $('#changePasswordSubmit');
	
	btnChange.on('click', function(event) {
		
		var oldOne = oldPassword.val();
		var newOne = newPassword.val();
		
		if(oldOne != password){
			
			message.text('Not matching!');
			
			event.preventDefault();
			return false;
			
		}
		
		var params = {
				
				'username' : userName,
				'newPassword' : newOne
		}
		
		console.log(newOne);
		
		$.post('AllUsersServlet', params, function(data){
			
			
			if(data.status == 'success'){
					
				alert('You successfully changed your password!');
				
				
				autoLogout();
			}
			
		});
		
		event.preventDefault();
		
		return false;
	});
	
	
	
	function autoLogout(){
		
		$.get('LogoutServlet', function(data){
			
			if(data.status == 'unauthenticated'){
				
				window.location.replace('index.html');
			}
			
		});
	}
	
	
	var userName;
	var password;
	
	
	function getLoggedInUser(){
		
		$.get('UserServlet', {'loggedUser' : 'loggedInUser'}, function(data){
			
			console.log(data);
			
			if(data.status == 'unauthenticated'){
				window.location.replace('index.html');
				return;
			}
			
			if(data.status == 'success'){
				
				console.log(data);
				
				var loggedInAdmin = data.loggedInUser;
				
				userName = loggedInAdmin.username;
				password = loggedInAdmin.password;
				
				var userNameParagraph = $('#userNameVal');
				var dateRegParagraph = $('#dateRegVal');
				
				userNameParagraph.text(loggedInAdmin.username);
				dateRegParagraph.text(loggedInAdmin.registrationDate);
			}
		});
	}
	
	
	var date = $('.date');
	
	date.datetimepicker({
		format : 'yy-m-d H:i',
		useCurrent : false,
		step : 15,
	});
	
	var dateFromInput = $('#date1');
	var dateToInput = $('#date2');
	
	var table = $('#reportTable');
	
	function getReport(){
		
		var dateFrom = dateFromInput.val();
		var dateTo = dateToInput.val();
		
		var params = {
				
				'dateFrom': dateFrom,
				'dateTo': dateTo
		}
		
		$.get('ReportServlet', params, function(data){
			
			if(data.status == 'success'){
				
				var reports = data.reports;
				
				console.log(reports);
				
				table.find('tr:gt(0)').remove();
				
				var index = 1;
				
				for(r in reports){
					
					table.append(
							'<tr>' +
								'<td>' + index++ + '</td>' +
								'<td><a href="Movie.html?id=' + reports[r].movie.idMovie  + '">' + reports[r].movie.name  +  '</a></td>' +
								'<td>' + reports[r].projectionsNum + '</td>' +
								'<td>' + reports[r].ticketsNum + '</td>' +
								'<td>' + reports[r].totalPrice + '</td>' +
						'</tr>'
					);
				}			
							
					table.append(
							'<tr>' +
								'<td></td>' +
								'<td></td>' +
								'<td id="sum1"></td>' +
								'<td id="sum2"></td>' +
								'<td id="sum3"></td>' +
							'</tr>'
				
				);
			}
		});
	
	
		var sum1 = 0;
		var sum2 = 0;
		var sum3 = 0;
		
		$('table thead').not(':first').not(':last').each(function(){
			
			sum1 += getnum($(this).find("td:eq(2)").text());
			sum2 += getnum($(this).find("td:eq(3)").text());
			sum3 += getnum($(this).find("td:eq(4)").text());
			
			
			function getnum(t){
				if(isNumeric(t)){
					return parseInt(t, 10);
				}
				return 0;
				
				function isNumeric(n){
					return !isNaN(parseFloat(n)) && isFinite(n);
				}
			}
			
			console.log(sum1);
			
			
			
		});
		
		$('#sum1').text(sum1);
		$('#sum2').text(sum2);
		$('#sum3').text(sum3);
		
	}	
		
	
	
	
	$('#datetimepicker1').on('change', function(e){
		
		dateFromInput.val($(this).val());
		
		getReport();
		
	});
	
	$('#datetimepicker2').on('change', function(e){
		
		dateToInput.val($(this).val());
		
		getReport();
	});
	
	getReport();
	
	getLoggedInUser();
});