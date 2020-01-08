package servlets.movie;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.MovieDAO;
import dao.UserDAO;
import enums.Role;
import model.Movie;
import model.User;


public class AllMoviesServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
  

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String loggedInUser = (String) request.getSession().getAttribute("loggedInUser");
		if(loggedInUser == null) {
			request.getRequestDispatcher("./FailureServlet").forward(request, response);
			return;
		}
		try {
			User loggedUser = UserDAO.getUser(loggedInUser);
			if(loggedUser == null) {
				request.getRequestDispatcher("./FailureServlet").forward(request, response);
				return;
			}
			
			String movieName = request.getParameter("movieName");
			movieName = (movieName != null ? movieName : "");
			
			String genre = request.getParameter("genre");
			genre = (genre != null ? genre : "");
			
			int minDuration = 0;
			try {
				String durationMin = request.getParameter("minDuration");
				minDuration = Integer.parseInt(durationMin);
				minDuration = (minDuration >= 0 ? minDuration : 0);
			}catch(Exception ex) {}
			
			int maxDuration = Integer.MAX_VALUE;
			try {
				String durationMax = request.getParameter("maxDuration");
				maxDuration = Integer.parseInt(durationMax);
				maxDuration = (maxDuration >= 0 ? maxDuration : Integer.MAX_VALUE);
			}catch(Exception ex) {}
			
			String distribution = request.getParameter("distribution");
			distribution = (distribution != null ? distribution : "");
			
			String origin = request.getParameter("origin");
			origin = (origin != null ? origin : "");
			
			int minYear = 0;
			try {
				String yearMin = request.getParameter("minYear");
				minYear = Integer.parseInt(yearMin);
				minYear = (minYear >= 0 ? minYear : 0);
			}catch(Exception ex){}
			
			int maxYear = Integer.MAX_VALUE;
			try {
				String yearMax = request.getParameter("maxYear");
				maxYear = Integer.parseInt(yearMax);
				maxYear = (maxYear >= 0 ? maxYear : Integer.MAX_VALUE);
			}catch(Exception ex) {}
			
			
			List<Movie> filteredMovies = MovieDAO.getAll(movieName, genre, minDuration, maxDuration, distribution, origin, minYear, maxYear);
			
			Map<String, Object> data = new LinkedHashMap<String, Object>();
			
			data.put("filteredMovies", filteredMovies);
			
			request.setAttribute("data", data);
			
			request.getRequestDispatcher("./SuccessServlet").forward(request, response);
			
			
			
			
		}catch(Exception ex) {ex.printStackTrace();}
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String loggedInUser = (String) request.getSession().getAttribute("loggedInUser");
		if(loggedInUser == null) {
			request.getRequestDispatcher("./FailureServlet").forward(request, response);
			return;
		}
		
		try {
			User loggedUser = UserDAO.getUser(loggedInUser);
			if(loggedUser == null) {
				request.getRequestDispatcher("./FailureServlet").forward(request, response);
				return;
			}
			if(loggedUser.getRole() != Role.ADMIN) {
				request.getRequestDispatcher("./UnauthorizedServlet").forward(request, response);
				return;
			}
				
			String action = request.getParameter("action");
			
			switch(action) {
				case("add"):{
				
				}
			}
			
			
			
		}catch(Exception ex) {}
		
	}

}
