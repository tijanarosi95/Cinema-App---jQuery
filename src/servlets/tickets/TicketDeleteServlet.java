package servlets.tickets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.TicketDAO;
import dao.UserDAO;
import enums.Role;
import model.User;


public class TicketDeleteServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
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
			
			int ticketID = Integer.parseInt(request.getParameter("ticketID"));
			
			TicketDAO.deleteTicket(ticketID);
			
			request.getRequestDispatcher("./SuccessServlet").forward(request, response);
			
			
		}catch(Exception ex) {
			
			ex.printStackTrace();
			
			request.getRequestDispatcher("./FailureServlet").forward(request, response);
		}
	}

}
