package servlets.projections;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.CommonDAO;
import dao.ProjectionDAO;
import model.Projection;


public class ProjectionServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		try {
			Map<String, Object> data = new LinkedHashMap<String, Object>();
		
			String action = request.getParameter("action");
		
			switch(action) {
				case("projectionTypes"):{
					data.put("projectionTypes", CommonDAO.getAllTypes());
					break;
				}
				case("halls"):{
					data.put("halls", CommonDAO.getAllHalls());
					break;
				}
				case("getProjection"):{
					int id = Integer.parseInt(request.getParameter("projectionID"));
					Projection projection = ProjectionDAO.getProjection(id);
					
					data.put("selectedProjection", projection);
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
