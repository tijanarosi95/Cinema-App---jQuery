package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import enums.Genre;
import model.Movie;

public class MovieDAO {
	
	public static Movie getMovie(int id) throws Exception{
		
		Connection conn = ConnectionManager.getConnection();
		
		PreparedStatement pstm = null;
		ResultSet set = null;
		
		try {
			String query = "Select name, director, actors, genre, duration, distributer, origin, year, description from Movies "
							+ "where id = ? and active = ?";
			
			pstm = conn.prepareStatement(query);
			int index = 1;
			pstm.setInt(index++, id);
			pstm.setBoolean(index++, true);
			
			set = pstm.executeQuery();
			
			while(set.next()) {
				index = 1;
				String name = set.getString(index++);
				String director = set.getString(index++);
				String actors = set.getString(index++);
				@SuppressWarnings("unchecked")
				ArrayList<Genre> genres = (ArrayList<Genre>) set.getArray(index++);
				int duration = set.getInt(index++);
				String distribution = set.getString(index++);
				String origin = set.getString(index++);
				int year = set.getInt(index++);
				String description = set.getString(index++);
				
				return new Movie(id, name, director, actors, genres, duration, distribution, origin, year, description, true);
				
			}
			
		}finally {
			try {pstm.close();}catch(Exception ex) {ex.printStackTrace();}
			try {set.close();}catch(Exception ex) {ex.printStackTrace();}
			try {conn.close();}catch(Exception ex) {ex.printStackTrace();}
		}
		
		return null;
	}
}
