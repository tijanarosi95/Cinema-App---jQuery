package servlets.user;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.UserDAO;
import model.User;


public class ShowUserServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
 
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String loggedUser = (String) request.getSession().getAttribute("loggedInUser");
		
		if(loggedUser == null) {
			request.getRequestDispatcher("./FailureServlet").forward(request, response);
			return;
		}
		try {
			User loggedInUser = UserDAO.getUser(loggedUser);
			if(loggedInUser == null) {
				request.getRequestDispatcher("./FailureServlet").forward(request, response);
				return;
			}
			
			Map<String, Object> data = new LinkedHashMap<String, Object>();
			
			String selectedUsername = request.getParameter("username");
			
			User selectedUser = UserDAO.getUser(selectedUsername);
			
			data.put("chosenUser", selectedUser);
			
			request.setAttribute("data", data);
			request.getRequestDispatcher("./SuccessServlet").forward(request, response);
			
		}catch(Exception ex) {}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
