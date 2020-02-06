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
	
	public static List<Projection> getSome(int movieID, String currentDate) throws Exception {
		
		List<Projection> filteredProjection = new ArrayList<Projection>();
		
		Connection conn = ConnectionManager.getConnection();
		
		PreparedStatement pstm = null;
		
		ResultSet set = null;
		
		try {
			
			String query = "Select id, movieid, type, hall, datetime, price, user, active from Projections where "
									+ "movieid = ? and active = ? and datetime > ? order by datetime";
			
			pstm = conn.prepareStatement(query);
			int index = 1;
			pstm.setInt(index++, movieID);
			pstm.setBoolean(index++, true);
			pstm.setString(index++, currentDate);
			
			set = pstm.executeQuery();
			
			while(set.next()) {
				index = 1;
				int id = set.getInt(index++);
				Movie movie = MovieDAO.getMovie(set.getInt(index++));
				TypeOfProjection type = CommonDAO.getProjectionType(set.getInt(index++));
				Hall hall = CommonDAO.getHall(set.getInt(index++));
				Date date = UserDAO.date_format.parse(set.getString(index++));
				double price = set.getDouble(index++);
				User user = UserDAO.getUser(set.getString(index++));
				
				filteredProjection.add(new Projection(id, movie, type, hall, date, price, user, true));
			}
		}finally {
			try {pstm.close();}catch(Exception ex) {ex.printStackTrace();}
			try {set.close();}catch(Exception ex) {ex.printStackTrace();}
			try {conn.close();}catch(Exception ex) {ex.printStackTrace();}
		}
		
		return filteredProjection;
	}
	
	public static Projection getProjection(int id) throws Exception{
		
		Connection conn = ConnectionManager.getConnection();
		
		PreparedStatement pstm = null;
		
		ResultSet set = null;
		
		try {
			
			String query = "Select movieid, type, hall, datetime, price, user from Projections "
								+ "where id = ?";
			
			pstm = conn.prepareStatement(query);
			pstm.setInt(1, id);
			set = pstm.executeQuery();
			
			while(set.next()) {
				int index = 1;
				Movie movie = MovieDAO.getMovie(set.getInt(index++));
				TypeOfProjection type = CommonDAO.getProjectionType(set.getInt(index++));
				Hall hall = CommonDAO.getHall(set.getInt(index++));
				Date date = UserDAO.date_format.parse(set.getString(index++));
				double price = set.getDouble(index++);
				User user = UserDAO.getUser(set.getString(index++));
				
				return new Projection(id, movie, type, hall, date, price, user, true);
				
			}
			
			
		}finally {
			try {pstm.close();}catch(Exception ex) {ex.printStackTrace();}
			try {set.close();}catch(Exception ex) {ex.printStackTrace();}
			try {conn.close();}catch(Exception ex) {ex.printStackTrace();}
		}
		
		return null;
	}
	
	public static boolean addProjection(Projection p) throws Exception {
		
		Connection conn = ConnectionManager.getConnection();
		
		PreparedStatement pstm = null;
		
		try {
			
			String query = "Insert into Projections(movieid, type, hall, datetime, price, user, active) values "
								+ "(?,?,?,?,?,?,?)";
			
			pstm = conn.prepareStatement(query);
			
			int index = 1;
			
			pstm.setInt(index++, p.getMovie().getIdMovie());
			pstm.setInt(index++, p.getProjectionType().getId());
			pstm.setInt(index++, p.getHall().getId());
			pstm.setString(index++, UserDAO.date_format.format(p.getDateTimeShow()));
			pstm.setDouble(index++, p.getPrice());
			pstm.setString(index++, p.getAdmin().getUsername());
			pstm.setBoolean(index++, p.isActive());
			
			return pstm.executeUpdate() == 1;
			
		}finally {
			
			try {pstm.close();}catch(Exception ex) {ex.printStackTrace();}
			try {conn.close();}catch(Exception ex) {ex.printStackTrace();}
		}
	}
	
	public static boolean deleteProjection(int id) throws Exception {
		
		Connection conn = ConnectionManager.getConnection();
		
		PreparedStatement pstm = null;
		
		try {
			
			conn.setAutoCommit(false);
			
			conn.commit();
			
			String query = "Update Projections set active = 0 where id = ? and id = (Select distinct projID from Tickets)";
			
			pstm = conn.prepareStatement(query);
			
			pstm.setInt(1, id);
			
			if(pstm.executeUpdate() == 0) {
				
				pstm.close();
				
				query = "Delete from Projections where id = ?";
				
				pstm = conn.prepareStatement(query);
				
				int index = 1;
				
				pstm.setInt(index++, id);
				
				pstm.executeUpdate();
				
				conn.commit();
				
				return true;
			}
			
		}catch(Exception ex){
			
			try {conn.rollback();} catch (Exception ex1) {ex1.printStackTrace();}
			
			throw ex;
		}finally {
			
			try {conn.setAutoCommit(true);} catch (Exception ex1) {ex1.printStackTrace();}
			
			try {pstm.close();} catch (Exception ex1) {ex1.printStackTrace();}
			try {conn.close();} catch (Exception ex1) {ex1.printStackTrace();}
		}
		
		return false;
	}
}
