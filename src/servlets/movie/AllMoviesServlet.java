package servlets.movie;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.MovieDAO;
import dao.UserDAO;
import enums.Genre;
import enums.Role;
import model.Movie;
import model.User;


public class AllMoviesServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
  

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String loggedInUser = (String) request.getSession().getAttribute("loggedInUser");
		if(loggedInUser == null) {
			request.getRequestDispatcher("./LogoutServlet").forward(request, response);
			return;
		}
		try {
			User loggedUser = UserDAO.getUser(loggedInUser);
			if(loggedUser == null) {
				request.getRequestDispatcher("./LogoutServlet").forward(request, response);
				return;
			}
			
			String movieName = request.getParameter("movieName");
			movieName = (movieName != null ? movieName : "");
			
			String genre = request.getParameter("genre");
			genre = (genre != null ? genre : "");
			
			ArrayList<Genre> genres = MovieDAO.returnGenres(Arrays.asList(genre.split("\\s*,\\s*")));
			
			System.out.println("geeeenres " + genres.toString());
			
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
			
			
			List<Movie> filteredMovies = MovieDAO.getAll(movieName, genres, minDuration, maxDuration, distribution, origin, minYear, maxYear);
			
			Map<String, Object> data = new LinkedHashMap<String, Object>();
			
			data.put("filteredMovies", filteredMovies);
			
			request.setAttribute("data", data);
			
			request.getRequestDispatcher("./SuccessServlet").forward(request, response);
			
			
			
			
		}catch(Exception ex) {ex.printStackTrace();}
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String loggedInUser = (String) request.getSession().getAttribute("loggedInUser");
		if(loggedInUser == null) {
			request.getRequestDispatcher("./LogoutServlet").forward(request, response);
			return;
		}
		
		try {
			User loggedUser = UserDAO.getUser(loggedInUser);
			if(loggedUser == null) {
				request.getRequestDispatcher("./LogoutServlet").forward(request, response);
				return;
			}
			if(loggedUser.getRole() != Role.ADMIN) {
				request.getRequestDispatcher("./UnauthorizedServlet").forward(request, response);
				return;
			}
				
			String action = request.getParameter("action");
			
			switch(action) {
			
				case("add"):{
					
					String movieName = request.getParameter("movieName");
					movieName = (!"".equals(movieName)? movieName : "Empty field");
					
					String director = request.getParameter("directorName");
					director = (!"".equals(director)? director : "Empty field");
					
					String genre = request.getParameter("genre");
					genre = (!"".equals(genre)? genre : "");
					ArrayList<Genre> genres = MovieDAO.returnGenres(Arrays.asList(genre.split("\\s*,\\s*")));
					
					String actors = request.getParameter("actors");
					actors = (!"".equals(actors)? actors : "Empty field");
					
					String duration = request.getParameter("duration");
					duration = (!"".equals(duration)? duration : "1");
					int movieDuration = Integer.parseInt(duration);
					movieDuration = (movieDuration > 0 ? movieDuration : Integer.MAX_VALUE);
						
					String distribution = request.getParameter("distribution");
					distribution = (!"".equals(distribution)? distribution : "Empty field");
					
					String origin = request.getParameter("origin");
					origin = (!"".equals(origin)? origin : "Empty field");
					
					String year = request.getParameter("yearMovie");
					year = (!"".equals(year)? year : "1930");
					int yearMovie = Integer.parseInt(year);
					yearMovie = (yearMovie > 0 ? yearMovie : 1);
					
					String description = request.getParameter("description");
					description = (!"".equals(description)? description : "Empty field");
					
					
					Movie movie = new Movie(1, movieName, director, actors, genres, movieDuration, distribution,origin ,yearMovie, description, true);
					
					MovieDAO.add(movie);
					
					break;
					
				}
				
				case("update"):{
					
					int movieID = Integer.parseInt(request.getParameter("movieID"));
					Movie movie = MovieDAO.getMovie(movieID);
					
					String movieName = request.getParameter("movieName");
					movieName = (!"".equals(movieName)? movieName : "Empty field");
					
					String director = request.getParameter("director");
					director = (!"".equals(director)? director : "Empty field");
					
					String actors = request.getParameter("actors");
					actors = (!"".equals(actors)? actors: "Empty field");
					
					String genres = request.getParameter("genres");
					ArrayList<Genre> genresMovie = MovieDAO.returnGenres(Arrays.asList(genres.split("\\s*,\\s*")));
					
					
					int duration = Integer.parseInt(request.getParameter("duration"));
					duration = (duration > 0 ? duration : Integer.MAX_VALUE);
					
					String distribution = request.getParameter("distribution");
					distribution = (!"".equals(distribution)? distribution : "Empty field");
					
					int year = Integer.parseInt(request.getParameter("year"));
					year = (year > 0 ? year : 2020);
					
					String description = request.getParameter("description");
					description = (!"".equals(description)? description : "Empty field");
					
					movie.setIdMovie(movieID);
					movie.setName(movieName);
					movie.setDirector(director);
					movie.setActors(actors);
					movie.setGenres(genresMovie);
					movie.setDuration(duration);
					movie.setDistribution(distribution);
					movie.setProductionYear(year);
					movie.setDescription(description);
					
					MovieDAO.update(movie);
					break;
				}
				
				case "delete":{
					
					int movieID = Integer.parseInt(request.getParameter("movieID"));
					MovieDAO.delete(movieID);
					break;
				}
			}
			
			request.getRequestDispatcher("./SuccessServlet").forward(request, response);

		}catch(Exception ex) {
			ex.printStackTrace();
			request.getRequestDispatcher("./FailureServlet").forward(request, response);
		}	
	}

}
