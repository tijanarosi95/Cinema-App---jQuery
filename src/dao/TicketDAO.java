package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import model.Projection;
import model.Seat;
import model.Ticket;
import model.User;

public class TicketDAO {
	
	public static boolean addTicket(int projectionID, ArrayList<Seat> seats, Date date, User user) throws Exception {
		
		Connection conn = ConnectionManager.getConnection();
		
		PreparedStatement pstm = null;
		
		try {
			
			conn.setAutoCommit(false);
			conn.commit();
					
			String query = "Insert into Tickets(projID, seatID, date, user, active) values (?, ?, ?, ?, ?)";
			
	
			pstm = conn.prepareStatement(query);
			
			
			for(int i = 0; i < seats.size(); i++) {
				int index = 1;
				pstm.setInt(index++, projectionID);
				pstm.setInt(index++, seats.get(i).getSerialNumber());
				pstm.setString(index++, UserDAO.date_format.format(date));
				pstm.setString(index++, user.getUsername());
				pstm.setBoolean(index++, true);

				pstm.addBatch();
				
			}
			
			pstm.executeBatch();
			
			conn.commit();
			
			return true;
			
		}catch(Exception ex){
			try {conn.rollback();}catch(Exception ex1) {ex1.printStackTrace();}
			
			throw ex;
		}finally {
			try {conn.setAutoCommit(true);}catch(Exception ex1) {ex1.printStackTrace();}
			
			try {pstm.close();}catch(Exception ex) {ex.printStackTrace();}
			try {conn.close();}catch(Exception ex) {ex.printStackTrace();}
		
		}
	}
	
	
	public static Ticket getTicket(int ticketID) throws Exception {
		
		ArrayList<Seat> seats = new ArrayList<Seat>();
		
		Connection conn = ConnectionManager.getConnection();
		
		PreparedStatement pstm = null;
		
		ResultSet set = null;
		
		try {
			
			String query = "Select t.id, t.projID, p.hall, t.seatID, t.date, t.user, t.active from Tickets t, Projections p"
					+ " where t.id = ? and p.id = t.projID";
			
			pstm = conn.prepareStatement(query);
			
			pstm.setInt(1, ticketID);
			
			set = pstm.executeQuery();
			
			while(set.next()) {
				
				int index = 1;
				int id = set.getInt(index++);
				Projection projection = ProjectionDAO.getProjection(set.getInt(index++));
				int hallID = set.getInt(index++);
				Seat seat = CommonDAO.getSeat(set.getInt(index++), hallID);
				Date date = UserDAO.date_format.parse(set.getString(index++));
				User user = UserDAO.getUser(set.getString(index++));
				boolean isActive = set.getBoolean(index++);
				
				seats.add(seat);
				
				return new Ticket(id, projection, seats, date, user, isActive);
				
			}
		}finally {
			try {pstm.close();}catch(Exception ex) {ex.printStackTrace();}
			try {set.close();}catch(Exception ex) {ex.printStackTrace();}
			try {conn.close();}catch(Exception ex) {ex.printStackTrace();}
			
		}
		return null;
	}
	
	public static List<Ticket> getAllTickets(int projectionID) throws Exception {
		
		List<Ticket> tickets = new ArrayList<Ticket>();
		ArrayList<Seat> seats = new ArrayList<Seat>();
		
		Connection conn = ConnectionManager.getConnection();
		
		PreparedStatement pstm = null;
		
		ResultSet set = null;
		
		try {
			
			String query = "Select t.id, t.projID, p.hall, t.seatID, t.date, t.user, t.active from Tickets t, Projections p "
					+ "where t.projID = ? and p.id = t.projID";
			
			pstm = conn.prepareStatement(query);
			
			pstm.setInt(1, projectionID);
			
			set = pstm.executeQuery();
			
			while(set.next()) {
				
				int index = 1;
				
				int id = set.getInt(index++);
				System.out.println(id + "----");
				Projection projection = ProjectionDAO.getProjection(set.getInt(index++));
				int hallID = set.getInt(index++);
				Seat seat = CommonDAO.getSeat(set.getInt(index++), hallID);
				Date date = UserDAO.date_format.parse(set.getString(index++));
				User user = UserDAO.getUser(set.getString(index++));
				boolean isActive = set.getBoolean(index++);
				
				seats.add(seat);
				tickets.add(new Ticket(id, projection, seats, date, user, isActive));
				
			}
			
		}finally {
			try {pstm.close();}catch(Exception ex) {ex.printStackTrace();}
			try {set.close();}catch(Exception ex) {ex.printStackTrace();}
			try {conn.close();}catch(Exception ex) {ex.printStackTrace();}
		}
		return tickets;
	}
	
	public static List<Ticket> getAllTickets(String username) throws Exception {
		
		List<Ticket> tickets = new ArrayList<Ticket>();
		ArrayList<Seat> seats = new ArrayList<Seat>();
		
		Connection conn = ConnectionManager.getConnection();
		
		PreparedStatement pstm = null;
		
		ResultSet set = null;
		
		try {
			
			String query = "Select t.id, t.projID, p.hall, t.seatID, t.date, t.user, t.active from Tickets t, Projections p"
					+ " where t.user like ? and p.id = t.projID order by t.date DESC";
			
			pstm = conn.prepareStatement(query);
			
			pstm.setString(1, "%" + username + "%");
			
			set = pstm.executeQuery();
			
			while(set.next()) {
				
				int index = 1;
				
				int id = set.getInt(index++);
				Projection projection = ProjectionDAO.getProjection(set.getInt(index++));
				int hallID = set.getInt(index++);
				Seat seat = CommonDAO.getSeat(set.getInt(index++), hallID);
				Date date = UserDAO.date_format.parse(set.getString(index++));
				User user = UserDAO.getUser(set.getString(index++));
				boolean isActive = set.getBoolean(index++);
				
				seats.add(seat);
				tickets.add(new Ticket(id, projection, seats, date, user, isActive));
			}
			
		}finally {
			try {pstm.close();}catch(Exception ex) {ex.printStackTrace();}
			try {set.close();}catch(Exception ex) {ex.printStackTrace();}
			try {conn.close();}catch(Exception ex) {ex.printStackTrace();}
		}
		
		return tickets;
	}
	
	public static boolean deleteTicket(int id) throws Exception {
		
		Connection conn = ConnectionManager.getConnection();
		
		PreparedStatement pstm = null;
		
		try {
			
			String query = "Delete from Tickets where id = ?";
			
			pstm = conn.prepareStatement(query);
			
			pstm.setInt(1, id);
			
			return pstm.executeUpdate() == 1;
			
		}finally {
			
			try {pstm.close();}catch(Exception ex) {ex.printStackTrace();}
			try {conn.close();}catch(Exception ex) {ex.printStackTrace();}
		}
	}
	
}
