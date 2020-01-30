package servlets.projections;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.ProjectionDAO;
import model.Projection;


public class AllProjectionsServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
   


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
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
			dateFrom = (dateFrom != null ? dateFrom : "");
			
			String dateTo = request.getParameter("dateTo");
			dateTo = (dateTo != null ? dateTo : "");
			
			List<Projection> filteredProjections = ProjectionDAO.getAll(movieName, types, halls, minPrice, maxPrice, dateFrom, dateTo);
			
			Map<String, Object> data = new LinkedHashMap<String, Object>();
			
			data.put("filteredProjections", filteredProjections);
			
			request.setAttribute("data", data);
			
			request.getRequestDispatcher("./SuccessServlet").forward(request, response);
			
			
		}catch(Exception ex) {ex.printStackTrace();}
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
