package servlets.user;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.UserDAO;
import model.User;


@SuppressWarnings("serial")
public class LogInServlet extends HttpServlet {

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String userName = request.getParameter("username");
		String password = request.getParameter("password");
		
		try {
			
			User loggedInUser = UserDAO.getUser(userName, password);
			
			
			if(loggedInUser == null) {
				request.getRequestDispatcher("./FailureServlet").forward(request, response);
				return;
			}
			
			request.getSession().setAttribute("loggedInUser", loggedInUser.getUsername());
			
			request.getRequestDispatcher("./SuccessServlet").forward(request, response);
			
		}catch(Exception ex) {
			
			ex.printStackTrace();
		}
	}

}
