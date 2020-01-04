package servlets.user;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.UserDAO;
import enums.Role;
import model.User;


@SuppressWarnings("serial")
public class UserServlet extends HttpServlet {


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		@SuppressWarnings("unused")
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
			}
			
			request.setAttribute("data", data);
			request.getRequestDispatcher("./SuccessServlet").forward(request, response);
			
			
		}catch(Exception ex) {
			ex.printStackTrace();
		}
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
