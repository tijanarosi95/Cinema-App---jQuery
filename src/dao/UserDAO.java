package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;

import enums.Role;
import model.User;

public class UserDAO {
	
	
	public static User getUser(String userName, String password) throws Exception{
		
		Connection conn = ConnectionManager.getConnection();
		
		PreparedStatement pstm = null;
		ResultSet set = null;
		
		try {
			String query = "Select dateRegistration, role, active from Users "
							+ "where userName = ? and password = ? and active = ?";
			 
			pstm = conn.prepareStatement(query);
			int index = 1;
			pstm.setString(index++, userName);
			pstm.setString(index++, password);
			pstm.setBoolean(index++, true);
			
			System.out.println(pstm);
			
			set = pstm.executeQuery();
			
			while(set.next()) {
				index = 1;
				Timestamp registrationDate = set.getTimestamp(index++);
				Role role = Role.valueOf(set.getString(index++));
				boolean isActive = set.getBoolean(index++);
				
				
				return new User(userName, password, registrationDate, role, isActive);
				
			}
			
		}finally {
			try{pstm.close();}catch(Exception ex) {ex.printStackTrace();}
			try {set.close();}catch(Exception ex){ex.printStackTrace();}
			try {conn.close();}catch(Exception ex) {ex.printStackTrace();}
		}
		
		return null;
	}
	
	public static User getUser(String username) throws Exception{
		
		Connection conn = ConnectionManager.getConnection();
		
		PreparedStatement pstm = null;
		ResultSet set = null;
		
		try {
			String query = "Select password, dateRegistration, role, active from Users "
							+ "where userName = ?";
			
			pstm = conn.prepareStatement(query);
			int index = 1;
			pstm.setString(index++, username);
			
			set = pstm.executeQuery();
			while(set.next()) {
				index = 1;
				String password = set.getString(index++);
				Timestamp registartionDate = set.getTimestamp(index++);
				Role role = Role.valueOf(set.getString(index++));
				boolean isActive = set.getBoolean(index++);
				
				return new User(username, password, registartionDate, role, isActive);
			}
			
			
		}finally {
			try{pstm.close();}catch(Exception ex) {ex.printStackTrace();}
			try {set.close();}catch(Exception ex){ex.printStackTrace();}
			try {conn.close();}catch(Exception ex) {ex.printStackTrace();}
		}
	return null;
	}
	
	
	public static boolean addUser(User user) throws Exception{
		
		Connection conn = ConnectionManager.getConnection();
		
		PreparedStatement pstm = null;
		
		try {
			
			String query = "Insert into Users(userName, password, dateRegistration, role, active) "
					+ "values (?, ?, ?, ?, ?)";
			
			pstm = conn.prepareStatement(query);
			int index = 1;
			pstm.setString(index++, user.getUsername());
			pstm.setString(index++, user.getPassword());
			pstm.setTimestamp(index++, user.getRegistrationDate());
			pstm.setString(index++, user.getRole().toString());
			pstm.setBoolean(index++, user.isActive());
			
			return pstm.executeUpdate() == 1;
			
		}finally {
			try {pstm.close();}catch(Exception ex) {ex.printStackTrace();}
			try {conn.close();}catch(Exception ex) {ex.printStackTrace();}
		}
		
	}
}
