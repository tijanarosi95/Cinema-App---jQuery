package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import model.Hall;
import model.Movie;
import model.Report;
import model.Seat;
import model.TypeOfProjection;

public class CommonDAO {
	
	
	public static TypeOfProjection getProjectionType(int id) throws Exception {
		
		Connection conn = ConnectionManager.getConnection();
		
		PreparedStatement pstm = null;
		
		ResultSet set = null;
		
		try {
			
			String query = "Select id, name from ProjectionType where id = ?";
			
			pstm = conn.prepareStatement(query);
			
			pstm.setInt(1, id);
			
			set = pstm.executeQuery();
			
			while(set.next()) {
				int index = 1;
				int typeID = set.getInt(index++);
				String name = set.getString(index++);
				
				return new TypeOfProjection(typeID, name);
			}
		}finally {
			try {pstm.close();}catch(Exception ex) {ex.printStackTrace();}
			try {set.close();}catch(Exception ex) {ex.printStackTrace();}
			try {conn.close();}catch(Exception ex) {ex.printStackTrace();}
		}
		return null;
	}
	
	

	public static List<TypeOfProjection> getAllTypes() throws Exception {
		
		List<TypeOfProjection> allProjectionTypes = new ArrayList<TypeOfProjection>();
		
		Connection conn = ConnectionManager.getConnection();
		
		PreparedStatement pstm = null;
		
		ResultSet set = null;
		
		try {
			
			String query = "Select id, name from ProjectionType";
			
			pstm = conn.prepareStatement(query);
			
			set = pstm.executeQuery();
			
			while(set.next()) {
				int index  = 1;
				int typeID = set.getInt(index++);
				String name = set.getString(index++);
				
				allProjectionTypes.add(new TypeOfProjection(typeID, name));
			}
		}finally {
			try {pstm.close();}catch(Exception ex) {ex.printStackTrace();}
			try {set.close();}catch(Exception ex) {ex.printStackTrace();}
			try {conn.close();}catch(Exception ex) {ex.printStackTrace();}
		}
		 
		return allProjectionTypes;
	}
	
	
	public static Hall getHall(int id) throws Exception {
		
		Connection conn = ConnectionManager.getConnection();
		
		PreparedStatement pstm = null;
		
		ResultSet set = null;
		
		try {
			String query = "Select id, name, projectionType from Hall where id = ?";
			
			pstm = conn.prepareStatement(query);
			pstm.setInt(1, id);
			set = pstm.executeQuery();
			
			while(set.next()) {
				int index = 1;
				int hallID = set.getInt(index++);
				String name = set.getString(index++);
				String loadTypes = set.getString(index++); 
				ArrayList<TypeOfProjection> types = returnTypes(Arrays.asList(loadTypes.split("\\s*,\\s*")));
				
				return new Hall(hallID, name, types);
			}
			
		}finally {
			try {pstm.close();}catch(Exception ex) {ex.printStackTrace();}
			try {set.close();}catch(Exception ex) {ex.printStackTrace();}
			try {conn.close();}catch(Exception ex) {ex.printStackTrace();}
		}
		return null;
	}
	
	
	public static List<Hall> getAllHalls() throws Exception {
		
		List<Hall> halls = new ArrayList<Hall>();
		
		Connection conn = ConnectionManager.getConnection();
		
		PreparedStatement pstm = null;
		
		ResultSet set = null;
		
		try {
			
			String query = "Select id, name, projectionType from Hall";
			
			pstm = conn.prepareStatement(query);
			
			set = pstm.executeQuery();
			
			while(set.next()) {
				int index = 1;
				int id = set.getInt(index++);
				String name = set.getString(index++);
				String loadTypes = set.getString(index++);
				ArrayList<TypeOfProjection> types = returnTypes(Arrays.asList(loadTypes.split("\\s*,\\s*")));
				halls.add(new Hall(id, name, types));
			}
			
		}finally {
			try {pstm.close();}catch(Exception ex) {ex.printStackTrace();}
			try {set.close();}catch(Exception ex) {ex.printStackTrace();}
			try {conn.close();}catch(Exception ex) {ex.printStackTrace();}
		}
		
		return halls;
	}
	
	public static List<Hall> getHalls(TypeOfProjection type) throws Exception {
		
		List<Hall> halls = new ArrayList<Hall>();
		
		Connection conn = ConnectionManager.getConnection();
		
		PreparedStatement pstm = null;
		
		ResultSet set = null;
		
		try {
			
			String query = "Select id, name, projectionType from Hall where projectionType like ?";
			
			pstm = conn.prepareStatement(query);
			pstm.setString(1, "%" + Integer.toString(type.getId()) + "%");
			
			set = pstm.executeQuery();
			
			while(set.next()) {
				int index = 1;
				int id = set.getInt(index++);
				String name = set.getString(index++);
				String loadTypes = set.getString(index++);
				ArrayList<TypeOfProjection> types = returnTypes(Arrays.asList(loadTypes.split("\\s*,\\s*")));
				
				halls.add(new Hall(id, name, types));
			}
			
		}finally {
			try {pstm.close();}catch(Exception ex) {ex.printStackTrace();}
			try {set.close();}catch(Exception ex) {ex.printStackTrace();}
			try {conn.close();}catch(Exception ex) {ex.printStackTrace();}
		}
		
		return halls;
	}
	
	public static List<Seat> getAllSeats(int hallID) throws Exception {
		
		List<Seat> allSeats = new ArrayList<Seat>();
		
		Connection conn = ConnectionManager.getConnection();
		
		PreparedStatement pstm = null;
		
		ResultSet set = null;

		try {
			
			String query = "Select seatid, hallid from HasSeat where hallid = ?";
			
			pstm = conn.prepareStatement(query);
			pstm.setInt(1, hallID);
			
			set = pstm.executeQuery();
			
			while(set.next()) {
				int index = 1;
				int serialNum = set.getInt(index++);
				Hall hall = getHall(set.getInt(index++));
				
				allSeats.add(new Seat(serialNum, hall));
			}
			
		}finally {
			try {pstm.close();}catch(Exception ex) {ex.printStackTrace();}
			try {set.close();}catch(Exception ex) {ex.printStackTrace();}
			try {conn.close();}catch(Exception ex) {ex.printStackTrace();}
		}
		
		return allSeats;
	}
	
	public static Seat getSeat(int id, int hallID) throws Exception {
		
		Connection conn = ConnectionManager.getConnection();
		
		PreparedStatement pstm = null;
		
		ResultSet set = null;
		
		try {
			
			String query = "Select distinct serialNum from Seats where serialNum = ?";
			
			pstm = conn.prepareStatement(query);
			pstm.setInt(1, id);
			
			set = pstm.executeQuery();
			
			while(set.next()) {
				int index = 1;
				int num = set.getInt(index++);
				Hall hall = getHall(hallID);
				
				return new Seat(num, hall);
				
			}
		}finally {
			try {pstm.close();}catch(Exception ex) {ex.printStackTrace();}
			try {set.close();}catch(Exception ex) {ex.printStackTrace();}
			try {conn.close();}catch(Exception ex) {ex.printStackTrace();}
		}
		
		return null;
	}
	
	
	public static List<Seat> getBusySeats(int projID) throws Exception{
		
		List<Seat> seats = new ArrayList<Seat>();
		
		Connection conn = ConnectionManager.getConnection();
		
		PreparedStatement pstm = null;
		
		ResultSet set = null;
		
		try {
			
			String query = "Select seatID, hall from Tickets t, Projections p where p.id = t.projID"
					+ " and projID = ?";
			
			pstm = conn.prepareStatement(query);
			
			pstm.setInt(1, projID);
			
			set = pstm.executeQuery();
			
			
			while(set.next()) {
				
				int index = 1;
				
				int seatID = set.getInt(index++);
				
				Hall hall = getHall(set.getInt(index++));
				
				
				seats.add(new Seat(seatID, hall));
			}
			
		}finally {
			
			try {pstm.close();}catch(Exception ex) {ex.printStackTrace();}
			try {set.close();}catch(Exception ex) {ex.printStackTrace();}
			try {conn.close();}catch(Exception ex) {ex.printStackTrace();}
			
		}
		
		return seats;
	}
	
	public static Map<Integer, Integer> getFreeSeats() throws Exception{
		
		Map<Integer, Integer> freeSeatsNum = new LinkedHashMap<Integer, Integer>();
		
		Connection conn = ConnectionManager.getConnection();
		
		PreparedStatement pstm = null;
		
		ResultSet set = null;
		
		try {
			
			String query = "Select p.id, count(distinct h.seatid) - count(distinct t.seatID) num_free from Projections p " + 
					"left join Tickets t on p.id = t.projID left join HasSeat h on h.hallid = p.hall group by h.hallid, p.id";
			
			pstm = conn.prepareStatement(query);
			
			set = pstm.executeQuery();
			
			while(set.next()) {
				
				int index = 1;
				
				int projID = set.getInt(index++);
				
				int seatsNum = set.getInt(index++);
				
				freeSeatsNum.put(projID, seatsNum);
				
			}
			
		}finally {
			
			try {pstm.close();}catch(Exception ex) {ex.printStackTrace();}
			try {set.close();}catch(Exception ex) {ex.printStackTrace();}
			try {conn.close();}catch(Exception ex) {ex.printStackTrace();}
			
		}
		
		return freeSeatsNum;
	}
	
	public static List<Report> getReports(String dateFrom, String dateTo) throws Exception {
		
		List<Report> reports = new ArrayList<Report>();
		
		Connection conn = ConnectionManager.getConnection();
		
		PreparedStatement pstm = null;
		
		ResultSet set = null;
		
		try {
			
			String query = "Select m.id, count(distinct p.id) num_proj, count(t.id) num_tickets, count(t.id) * p.price t_price "
							+ "from Movies m left join Projections p on m.id = p.movieid " + 
									"left join Tickets t on t.projID = p.id where " +
										"p.datetime >= ? and p.datetime <= ? group by m.id";
			
			pstm = conn.prepareStatement(query);
			
			int index = 1;
			
			pstm.setString(index++, dateFrom);
			
			pstm.setString(index++, dateTo);
			
			set = pstm.executeQuery();
			
			while(set.next()) {
				
				index = 1;
				
				Movie movie = MovieDAO.getMovie(set.getInt(index++));
				int projNum = set.getInt(index++);
				int ticketsNum = set.getInt(index++);
				double t_price = set.getDouble(index++);
				
				reports.add(new Report(movie, projNum, ticketsNum, t_price));
	
			}
	
		}finally {

			try {pstm.close();}catch(Exception ex) {ex.printStackTrace();}
			try {set.close();}catch(Exception ex) {ex.printStackTrace();}
			try {conn.close();}catch(Exception ex) {ex.printStackTrace();}
		}
		
		return reports;
	}
	
	public static ArrayList<TypeOfProjection> returnTypes (List<String> dbTypes) throws Exception{
		
		ArrayList<TypeOfProjection> types = new ArrayList<TypeOfProjection>();
		ArrayList<TypeOfProjection> projectionTypes = (ArrayList<TypeOfProjection>) getAllTypes();
		
		for(String t : dbTypes) {
			for(TypeOfProjection type : projectionTypes) {
				if(t.equals(Integer.toString(type.getId()))){
					types.add(type);
				}
			}
		}
		
		return types;
	}
	
	public static ArrayList<Seat> returnSeats (List<String> dbSeats, int hallID) throws Exception {
		
		ArrayList<Seat> allSeats = (ArrayList<Seat>) getAllSeats(hallID);
		
		ArrayList<Seat> seats = new ArrayList<Seat>();
		
		for(String s : dbSeats) {
			for(Seat seat : allSeats) {
				if(s.equals(Integer.toString(seat.getSerialNumber()))) {
					
					seats.add(seat);
					System.out.println(seat.getSerialNumber());
				}
			}
		}
		
		return seats;
		
	}
	
}
