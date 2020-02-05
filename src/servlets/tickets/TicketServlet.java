package servlets.tickets;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.CommonDAO;
import dao.TicketDAO;
import dao.UserDAO;
import enums.Role;
import model.Seat;
import model.Ticket;
import model.User;


public class TicketServlet extends HttpServlet {
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
			
			String action = request.getParameter("action");
			
			Map<String, Object> data = new LinkedHashMap<String, Object>();
			
			switch(action) {
			
				case("getProjectionTickets"):{
					
					int projID = Integer.parseInt(request.getParameter("projID"));
					
					List<Ticket> filteredTickets = TicketDAO.getAllTickets(projID);
					
					data.put("tickets", filteredTickets);
					
					break;
				}
				case("getUserTickets"):{
					
					String username = request.getParameter("username");
					
					List<Ticket> filteredTickets = TicketDAO.getAllTickets(username);
					
					data.put("tickets", filteredTickets);
					
					break;
				}
			
			}
			
			request.setAttribute("data", data);
			request.getRequestDispatcher("./SuccessServlet").forward(request, response);
						
		}catch(Exception ex) {
			ex.printStackTrace();
			request.getRequestDispatcher("./FailureServlet").forward(request, response);
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
			if(loggedUser.getRole() != Role.USER) {
				
				request.getRequestDispatcher("./LogoutServlet").forward(request, response);
				return;
			}
			
			int projectionID = Integer.parseInt(request.getParameter("projectionID"));
			
			int hallID = Integer.parseInt(request.getParameter("hallID"));
			
			String seats = request.getParameter("seats");
			
			Date today = new Date(System.currentTimeMillis());
			
			ArrayList<Seat> selectedSeats = CommonDAO.returnSeats(Arrays.asList(seats.split("\\s*,\\s*")), hallID);
			
			TicketDAO.addTicket(projectionID, selectedSeats, today, loggedUser);
			
			request.getRequestDispatcher("./SuccessServlet").forward(request, response);
			
		}catch(Exception ex) {
			ex.printStackTrace();
			request.getRequestDispatcher("./FailureServlet").forward(request, response);
		}
	}

}
