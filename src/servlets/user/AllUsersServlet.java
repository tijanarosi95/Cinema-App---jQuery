package servlets.user;

import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.UserDAO;
import model.User;


public class AllUsersServlet extends HttpServlet {

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
			
			String userNameFilter = request.getParameter("userNameFilter");
			userNameFilter = (userNameFilter != null ? userNameFilter : "");
			
			String typeOfUser = request.getParameter("typeOfUser");
			typeOfUser = (typeOfUser != null ? typeOfUser : "");
			
			List<User> filteredUsers = UserDAO.getAll(userNameFilter, typeOfUser);
			
			Map<String, Object> data = new LinkedHashMap<String, Object>();
			
			data.put("filteredUsers", filteredUsers);
			
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
			
			String username = request.getParameter("username");
			String newPassword = request.getParameter("newPassword");
			
			invalidate(username, request);
			
			UserDAO.updatePassword(username, newPassword);
			
			request.getRequestDispatcher("./SuccessServlet").forward(request, response);
			
		}catch(Exception ex) {
			ex.printStackTrace();
			request.getRequestDispatcher("./FailureServlet").forward(request, response);
		}
	}
	
	private void invalidate(String userName, HttpServletRequest request) {
		
		ServletContext context = request.getSession().getServletContext();
		
		@SuppressWarnings("unchecked")
		HashMap<String, HttpSession> map = (HashMap<String, HttpSession>) context.getAttribute("usersSessions");
		
		HttpSession userSession = map.get(userName);
		
		if(userSession != null) {
			
			map.remove(userName);
			userSession.invalidate();
		}
		
		return;
	}

}
