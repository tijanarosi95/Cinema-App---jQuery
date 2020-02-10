package servlets.user;

import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.UserDAO;
import enums.Role;
import model.User;


@SuppressWarnings("serial")
public class UserServlet extends HttpServlet {


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String loggedUsername = (String) request.getSession().getAttribute("loggedInUser");
		
		if (loggedUsername == null) {
			request.getRequestDispatcher("./LogoutServlet").forward(request, response);
			return;
		}
		
		try {
			User loggedInUser = UserDAO.getUser(loggedUsername);
			if(loggedInUser == null) {
				request.getRequestDispatcher("./LogoutServlet").forward(request, response);
				return;
			}
			
			
			Map<String, Object> data = new LinkedHashMap<String, Object>();
			
			String loggedUser = (String) request.getParameter("loggedUser");
			
			
			switch(loggedUser) {
				case("loggedUserRole"):{
					data.put("loggedUserRole", loggedInUser.getRole());
					break;
				}
				case("loggedInUser"):{
					data.put("loggedInUser", loggedInUser);
					break;
				}
				case("allRoles"):{
					data.put("allRoles", Role.values());
					break;
				}
				case("getUser"):{
					String username = request.getParameter("username");
					User selectedUser = UserDAO.getUser(username);
					
					data.put("chosenUser", selectedUser);
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
		
		String loggedUserName = (String) request.getSession().getAttribute("loggedInUser");
		
		if(loggedUserName == null) {
			request.getRequestDispatcher("./LogoutServlet").forward(request, response);
			return;
		}
		try {
			User loggedUser = UserDAO.getUser(loggedUserName);
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
			
				case("update"):{
					
				User changedUser = UserDAO.getUser(request.getParameter("userName"));	
				String role = request.getParameter("role");
				Role userRole = Role.valueOf(role);
				
				changedUser.setRole(userRole);
				
				invalidate(changedUser.getUsername(), request);
				
				UserDAO.updateUser(changedUser);
				break;
				
			}
				case("delete"):{
					
				User selectedUser = UserDAO.getUser(request.getParameter("username"));
				
				invalidate(selectedUser.getUsername(), request);
				
				UserDAO.deleteUser(selectedUser);
				break;
				}
			}
			
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
