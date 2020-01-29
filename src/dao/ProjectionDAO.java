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
			
			String query = "Select id, movieid, type, hall, datetime, price, user, active from Projections p, Movies m "
					+ "where p.movieid = m.id and and m.name like ? or type like ? or hall like ? and price >= ? and "
					+ "price <= ? and datetime >= ? and datetime <= ?";
			
			pstm = conn.prepareStatement(query);
			int index = 1;
			pstm.setString(index++, "%" + movieName + "%");
			pstm.setString(index++, "%" + type + "%");
			pstm.setString(index++, "%" + hall + "%");
			pstm.setDouble(index++, priceMin );
			pstm.setDouble(index++, priceMax);
			pstm.setString(index++, "%" + dateFrom + "%");
			pstm.setString(index++, "%" + dateTo + "%");
			
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
				boolean isActive = set.getBoolean(index++);
				
				filteredProjections.add(new Projection(id, movie, typeProjection, hallProjection, showingDate, projectionPrice, admin, isActive));

			}
			
		}finally {
			try {pstm.close();}catch(Exception ex) {ex.printStackTrace();}
			try {set.close();}catch(Exception ex) {ex.printStackTrace();}
			try {conn.close();}catch(Exception ex) {ex.printStackTrace();}
		}
		
		return filteredProjections;
	}
}
