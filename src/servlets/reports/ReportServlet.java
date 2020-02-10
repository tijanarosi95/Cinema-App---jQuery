package servlets.reports;

import java.io.IOException;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.CommonDAO;
import dao.UserDAO;
import enums.Role;
import model.Report;
import model.User;


public class ReportServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
		
		String loggedInUser = (String) request.getSession().getAttribute("loggedInUser");
		
		if(loggedInUser == null) {
			
			request.getRequestDispatcher("./LogoutServlet").forward(request, response);
			return;
		}
		try {
			
			User loggedUser = UserDAO.getUser(loggedInUser);
			
			Map<String, Object> data = new LinkedHashMap<String, Object>();
			
			if(loggedUser == null) {
				
				request.getRequestDispatcher("./LogoutServlet").forward(request, response);
				return;
				
			}
			if(loggedUser.getRole() != Role.ADMIN) {
				
				request.getRequestDispatcher("./LogoutServlet").forward(request, response);
				return;
			}
			
			String dateFrom = request.getParameter("dateFrom");
			dateFrom = ( dateFrom != "" ? dateFrom : UserDAO.date_format.format(new Date(System.currentTimeMillis() - 72*60*60*1000)));
			
			String dateTo = request.getParameter("dateTo");
			dateTo = (dateTo != "" ? dateTo : UserDAO.date_format.format(new Date(System.currentTimeMillis() + 72*60*60*1000)));
			
			List<Report> reports = CommonDAO.getReports(dateFrom, dateTo);
			
			data.put("reports", reports);
			
			request.setAttribute("data", data);
			
			request.getRequestDispatcher("./SuccessServlet").forward(request, response);
			
			
			
		}catch(Exception ex) {
			
			ex.printStackTrace();
			
			request.getRequestDispatcher("./FailureServlet").forward(request, response);
		}
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
