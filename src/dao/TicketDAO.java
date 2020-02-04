package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.Date;

import model.Seat;
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
			int index = 1;
			pstm.setInt(index++, projectionID);
			//pstm.setInt(index++, seats.get(i).getSerialNumber());
			
			pstm.setString(index++, UserDAO.date_format.format(date));
			pstm.setString(index++, user.getUsername());
			pstm.setBoolean(index++, true);

			
			return pstm.executeUpdate() == 1;
			
			
			
		}finally {
			
			try {pstm.close();}catch(Exception ex) {ex.printStackTrace();}
			try {conn.close();}catch(Exception ex) {ex.printStackTrace();}
		
		}
	}
	
}
