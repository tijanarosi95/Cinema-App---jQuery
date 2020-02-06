package servlets.projections;

import java.io.IOException;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.CommonDAO;
import dao.MovieDAO;
import dao.ProjectionDAO;
import dao.UserDAO;
import enums.Role;
import model.Hall;
import model.Movie;
import model.Projection;
import model.TypeOfProjection;
import model.User;


public class ProjectionServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		try {
			Map<String, Object> data = new LinkedHashMap<String, Object>();
		
			String action = request.getParameter("action");
		
			switch(action) {
				case("projectionTypes"):{
					data.put("projectionTypes", CommonDAO.getAllTypes());
					break;
				}
				case("halls"):{
					data.put("halls", CommonDAO.getAllHalls());
					break;
				}
				case("seats"):{
					int hallID = Integer.parseInt(request.getParameter("hallID"));
					data.put("seats", CommonDAO.getAllSeats(hallID));
					break;
				}
				case("getProjection"):{
					int id = Integer.parseInt(request.getParameter("projectionID"));
					Projection projection = ProjectionDAO.getProjection(id);
					
					data.put("selectedProjection", projection);
					break;
				}
			}
			
			request.setAttribute("data", data);
			request.getRequestDispatcher("./SuccessServlet").forward(request, response);
		
		}catch(Exception ex) {
			ex.printStackTrace();
		}
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
				
				request.getRequestDispatcher("./LogoutServlet").forward(request, response);
				return;
			}
			
			String action = request.getParameter("action");
			
			switch(action) {
				case("add"):{
				
					int movieID = Integer.parseInt(request.getParameter("movie"));
					Movie movie = MovieDAO.getMovie(movieID);
					
					int typeID = Integer.parseInt(request.getParameter("type"));
					TypeOfProjection projType = CommonDAO.getProjectionType(typeID);
					
					int hallID = Integer.parseInt(request.getParameter("hall"));
					Hall hall = CommonDAO.getHall(hallID);
					
					Date date = UserDAO.date_format.parse(request.getParameter("datetime"));
					
					double price = Double.parseDouble(request.getParameter("ticketPrice"));

					ProjectionDAO.addProjection(new Projection(1, movie, projType, hall, date, price, loggedUser, true));
					
					break;
				}
				case("delete"):{
					
					int projectionID = Integer.parseInt(request.getParameter("projID"));
					
					ProjectionDAO.deleteProjection(projectionID);
					
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
