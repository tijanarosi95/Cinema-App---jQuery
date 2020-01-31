package servlets.movie;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import dao.MovieDAO;
import enums.Genre;
import model.Movie;



public class MovieServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		try {

			Map<String, Object> data = new LinkedHashMap<String, Object>();
			
			String param = request.getParameter("action");
			
			switch(param) {
				case("genres"):{
					data.put("genres", Genre.getGenres());
					break;
				}
				case("getMovie"):{
					int movieID = Integer.parseInt(request.getParameter("movieID"));
					Movie selectedMovie = MovieDAO.getMovie(movieID);
					
					data.put("selectedMovie", selectedMovie);
					
					break;
				}
			}
			
			request.setAttribute("data", data);
			request.getRequestDispatcher("./SuccessServlet").forward(request, response);
			
		}catch(Exception ex) {ex.printStackTrace();}
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
