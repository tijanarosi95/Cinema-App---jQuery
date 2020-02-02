package servlets.projections;

import java.io.IOException;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.ProjectionDAO;
import dao.UserDAO;
import model.Projection;


public class AllProjectionsServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
   


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String action = request.getParameter("action");
		
		Map<String, Object> data = new LinkedHashMap<String, Object>();
		
		switch(action) {
		
			case("getAll"):{
			
				try {
					String movieName = request.getParameter("movieName");
					movieName = (movieName != null ? movieName : "");
			
					String types = request.getParameter("types");
					types = (types != null ? types : "");
			
					String halls = request.getParameter("halls");
					halls = (halls != null ? halls : "");
			
					double minPrice = 0;
					try {
						minPrice = Double.parseDouble(request.getParameter("minPrice"));
						minPrice = (minPrice >= 0 ? minPrice : 0);
					}catch(Exception ex) {}
			
					double maxPrice = Double.MAX_VALUE;
					try {
						maxPrice = Double.parseDouble(request.getParameter("maxPrice"));
						maxPrice = (maxPrice >= 0 ? maxPrice : Double.MAX_VALUE);
					}catch(Exception ex) {}
			
					String dateFrom = request.getParameter("dateFrom");
					dateFrom = (dateFrom != "" ? dateFrom : UserDAO.date_format.format(new Date(System.currentTimeMillis() - 24*60*60*1000)));
			
					String dateTo = request.getParameter("dateTo");
					dateTo = (dateTo != "" ? dateTo : UserDAO.date_format.format(new Date(System.currentTimeMillis() + 24*60*60*1000)));
			
					List<Projection> filteredProjections = ProjectionDAO.getAll(movieName, types, halls, minPrice, maxPrice, dateFrom, dateTo);
						
			
					data.put("filteredProjections", filteredProjections);
			

				}catch(Exception ex) {ex.printStackTrace();}
				break;
			}
			case("getSome"):{
				try {
					int movieID = Integer.parseInt(request.getParameter("movieID"));
					String currentDateTime = UserDAO.date_format.format(new Date(System.currentTimeMillis()));
					List<Projection> projections = ProjectionDAO.getSome(movieID, currentDateTime);
					
					data.put("projections", projections);
					
				}catch(Exception ex) {ex.printStackTrace();}
				break;
			}
		}
		
		request.setAttribute("data", data);
		
		request.getRequestDispatcher("./SuccessServlet").forward(request, response);
		
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
