package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.text.SimpleDateFormat;

import enums.Role;
import model.User;

public class UserDAO {
	
	public static SimpleDateFormat date_format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
	
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
				
				String date = set.getString(index++);
				System.out.println("date from base  " + date);
				
				Date registrationDate = date_format.parse(date);
				
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
				Date registartionDate = date_format.parse(set.getString(index++));
				System.out.println(registartionDate);
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
	
	public static List<User> getAll(String username, String role) throws Exception{
		
		List<User> filteredUsers = new ArrayList<User>();
		
		Connection conn = ConnectionManager.getConnection();
		
		PreparedStatement pstm = null;
		ResultSet set = null;
		try {
			String query = "Select userName, password, dateRegistration, role, active from Users "
					+ " where userName like ? and role like ?";
			
			pstm = conn.prepareStatement(query);
			int index = 1;
			pstm.setString(index++, "%" + username + "%");
			pstm.setString(index++, "%" + role + "%");
			
			set = pstm.executeQuery();
			
			while(set.next()) {
				index = 1;
				String userName = set.getString(index++);
				String password = set.getString(index++);
				//throws ParseException : Unparseable date because time in my db has ms 
				Date registartionDate = date_format.parse(set.getString(index++));
				Role userRole = Role.valueOf(set.getString(index++));
				boolean isActive = set.getBoolean(index++);
				
				filteredUsers.add(new User (userName, password, registartionDate,userRole , isActive));
				
			}
			
		}finally {
			try {pstm.close();}catch(Exception ex) {ex.printStackTrace();}
			try {set.close();}catch(Exception ex) {ex.printStackTrace();}
			try {conn.close();}catch(Exception ex) {ex.printStackTrace();}
		}
		
		return filteredUsers;
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
			pstm.setString(index++, date_format.format(user.getRegistrationDate()));
			pstm.setString(index++, user.getRole().toString());
			pstm.setBoolean(index++, user.isActive());
			
			return pstm.executeUpdate() == 1;
			
		}finally {
			try {pstm.close();}catch(Exception ex) {ex.printStackTrace();}
			try {conn.close();}catch(Exception ex) {ex.printStackTrace();}
		}
		
	}
	
	public static boolean updateUser(User user) throws Exception{
		
		Connection conn = ConnectionManager.getConnection();
		
		PreparedStatement pstm = null;
		
		try {
			
			String query = "Update Users set role = ? where userName = ?";
			
			pstm = conn.prepareStatement(query);
			int index = 1;
			
			pstm.setString(index++, user.getRole().toString());
			pstm.setString(index++, user.getUsername());
			
			return pstm.executeUpdate() == 1;
			
		}finally {
			try {pstm.close();}catch(Exception ex) {ex.printStackTrace();}
			try {conn.close();}catch(Exception ex) {ex.printStackTrace();}
		}
	}
	
	public static boolean deleteUser(User user) throws Exception{
		
		Connection conn = ConnectionManager.getConnection();
	
		PreparedStatement pstm = null;
	
		try {
		
			String query = "Delete from Users where userName = ?";
			
			pstm = conn.prepareStatement(query);
			int index = 1;
			pstm.setString(index, user.getUsername());
			
			return pstm.executeUpdate() == 1;
		}finally {
			try {pstm.close();}catch(Exception ex) {ex.printStackTrace();}
			try {conn.close();}catch(Exception ex) {ex.printStackTrace();}
		}
	}
	
	public static boolean updatePassword(String username, String newPassword) throws Exception {
		
		Connection conn = ConnectionManager.getConnection();
		
		PreparedStatement pstm = null;
		
		try {
			
			String query = "Update Users SET password = ? where userName = ?";
			
			pstm = conn.prepareStatement(query);
			
			int index = 1;
			
			pstm.setString(index++, newPassword);
			pstm.setString(index++, username);
			
			return pstm.executeUpdate() == 1;
			
		}finally {
			
			try {pstm.close();}catch(Exception ex) {ex.printStackTrace();}
			try {conn.close();}catch(Exception ex) {ex.printStackTrace();}
		}
		
	}
}
