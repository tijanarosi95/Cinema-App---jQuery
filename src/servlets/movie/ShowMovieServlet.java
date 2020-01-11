package servlets.movie;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.MovieDAO;
import dao.UserDAO;
import model.Movie;
import model.User;


public class ShowMovieServlet extends HttpServlet {
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
			
			Map<String, Object> data = new LinkedHashMap<String, Object>();
			
			String movieID = request.getParameter("movieID");
			int movieid = Integer.parseInt(movieID);
			
			Movie selectedMovie = MovieDAO.getMovie(movieid);
			
			data.put("selectedMovie", selectedMovie);
			data.put("loggedInUser", loggedUser);
			
			request.setAttribute("data", data);
			
			request.getRequestDispatcher("./SuccessServlet").forward(request, response);
			
			
		}catch(Exception ex) {ex.printStackTrace();}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
