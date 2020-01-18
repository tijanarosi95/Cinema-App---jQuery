package servlets.user;

import java.io.IOException;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.UserDAO;
import enums.Role;
import model.User;


public class RegisterServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
		try {
			String username = request.getParameter("username");
			if(UserDAO.getUser(username) != null)
				throw new Exception("This username already exists!");
			if(username.equals(""))
				throw new Exception("You didn't enter username");
			
			String password = request.getParameter("password");
			if(password.equals(""))
				throw new Exception("You didn't enter password!");
			
			Date dateRegistration = new Date(System.currentTimeMillis());

			
			boolean isActive = true;
			
			User user = new User(username, password, dateRegistration, Role.USER, isActive);
			
			UserDAO.addUser(user);
			
			request.getRequestDispatcher("./SuccessServlet").forward(request, response);
			
		}catch(Exception ex) {
			String message = ex.getMessage();
			if(message == null) {
				message = "Unpredicted error happened!";
				ex.printStackTrace();
			}
			
			Map<String, Object> data = new LinkedHashMap<String, Object>();
			data.put("message", message);
			
			request.setAttribute("data", data);
			request.getRequestDispatcher("./FailureServlet").forward(request, response);
		}
		
	}

}
