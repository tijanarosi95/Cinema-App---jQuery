package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import model.Hall;
import model.Movie;
import model.Projection;
import model.TypeOfProjection;
import model.User;

public class ProjectionDAO {
	
	public static List<Projection> getAll(String movieName, String type, String hall, double priceMin, double priceMax, String dateFrom, String dateTo) throws Exception{
		
		List<Projection> filteredProjections = new ArrayList<Projection>();
		
		Connection conn = ConnectionManager.getConnection();
		
		PreparedStatement pstm = null;
		
		ResultSet set = null;
		
		try {
			
			String query = "Select p.id, p.movieid, p.type, p.hall, p.datetime, p.price, p.user, p.active from Projections p "
					+ "join Movies m on p.movieid = m.id where m.name like ? and p.type like ? and p.hall like ? and p.price >= ? and "
					+ "p.price <= ? and p.datetime >= ? and p.datetime <= ? and p.active = ? order by m.name, p.datetime";
			
			pstm = conn.prepareStatement(query);
			int index = 1;
			pstm.setString(index++, "%" + movieName + "%");
			pstm.setString(index++, "%" + type + "%");
			pstm.setString(index++, "%" + hall + "%");
			pstm.setDouble(index++, priceMin );
			pstm.setDouble(index++, priceMax);
			pstm.setString(index++, dateFrom);
			pstm.setString(index++, dateTo);
			pstm.setBoolean(index++, true);
			
			set = pstm.executeQuery();
			
			while(set.next()) {
				index = 1;
				int id = set.getInt(index++);
				Movie movie = MovieDAO.getMovie(set.getInt(index++));
				TypeOfProjection typeProjection = CommonDAO.getProjectionType(set.getInt(index++));
				Hall hallProjection = CommonDAO.getHall(set.getInt(index++));
				Date showingDate = UserDAO.date_format.parse(set.getString(index++));
				double projectionPrice = set.getDouble(index++);
				User admin = UserDAO.getUser(set.getString(index++));
				//boolean isActive = set.getBoolean(index++);
				
				filteredProjections.add(new Projection(id, movie, typeProjection, hallProjection, showingDate, projectionPrice, admin, true));

			}
			
		}finally {
			try {pstm.close();}catch(Exception ex) {ex.printStackTrace();}
			try {set.close();}catch(Exception ex) {ex.printStackTrace();}
			try {conn.close();}catch(Exception ex) {ex.printStackTrace();}
		}
		
		return filteredProjections;
	}
}
