package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import model.Hall;
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
	
}