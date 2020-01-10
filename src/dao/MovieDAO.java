package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
				String genres = set.getString(index++);
				ArrayList<Genre> movieGenre = returnGenres(Arrays.asList(genres.split("\\s*,\\s*")));
				int duration = set.getInt(index++);
				String distribution = set.getString(index++);
				String origin = set.getString(index++);
				int year = set.getInt(index++);
				String description = set.getString(index++);
				
				return new Movie(id, name, director, actors, movieGenre, duration, distribution, origin, year, description, true);
				
			}
			
		}finally {
			try {pstm.close();}catch(Exception ex) {ex.printStackTrace();}
			try {set.close();}catch(Exception ex) {ex.printStackTrace();}
			try {conn.close();}catch(Exception ex) {ex.printStackTrace();}
		}
		
		return null;
	}
	
	public static List<Movie> getAll(String movieName, String genre, int minDuration, int maxDuration, String distribution, String origin, int minYear, int maxYear) throws Exception{
		
		List<Movie> movies = new ArrayList<Movie>();
		
		Connection conn = ConnectionManager.getConnection();
		
		PreparedStatement pstm = null;
		
		ResultSet set = null;
		
		try {
			String query = "Select id, name, director, actors, genre, duration, distributer, origin, year, description, active "
							+ "from Movies where name like ? and genre like ? and duration >= ? "
							+ "and duration <= ? and distributer like ? and origin like ? and year >= ? and year <= ?";
			
			pstm = conn.prepareStatement(query);
			int index = 1;
			pstm.setString(index++,"%" + movieName + "%");
			pstm.setString(index++, "%" + genre + "%");
			pstm.setInt(index++, minDuration);
			pstm.setInt(index++, maxDuration);
			pstm.setString(index++, "%" + distribution + "%");
			pstm.setString(index++, "%" + origin + "%");
			pstm.setInt(index++, minYear);
			pstm.setInt(index++, maxYear);
			
			set = pstm.executeQuery();
			
			while(set.next()) {
				index = 1;
				int id = set.getInt(index++);
				String name = set.getString(index++);
				String director = set.getString(index++);
				String actors = set.getString(index++);
				String genres = set.getString(index++);
				ArrayList<Genre> movieGenre = returnGenres(Arrays.asList(genres.split("\\s*,\\s*")));
				int duration = set.getInt(index++);
				String distributionM = set.getString(index++);
				String originCountry = set.getString(index++);
				int year = set.getInt(index++);
				String description = set.getString(index++);
				boolean isActive = set.getBoolean(index++);
				
				
				
				movies.add(new Movie(id, name, director, actors, movieGenre, duration, distributionM, originCountry, year, description, isActive));
				
			}
		}finally {
			try {pstm.close();}catch(Exception ex) {ex.printStackTrace();}
			try {set.close();}catch(Exception ex) {ex.printStackTrace();}
			try {conn.close();}catch(Exception ex) {ex.printStackTrace();}
		}
		
		return movies;
	}
	
	public static boolean add(Movie movie) throws Exception {
		
		Connection conn = ConnectionManager.getConnection();
		
		PreparedStatement pstm = null;
		
		try {
			String query = "Insert into Movies(name, director, actors, genre, duration, distributer, origin, year, description, active) "
					+ "values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
			
			pstm = conn.prepareStatement(query);
			int index = 1;
			pstm.setString(index++, movie.getName());
			pstm.setString(index++, movie.getDirector());
			pstm.setString(index++, movie.getActors());
			pstm.setString(index++, movie.getGenres().get(0).toString());
			pstm.setInt(index++, movie.getDuration());
			pstm.setString(index++, movie.getDistribution());
			pstm.setString(index++, movie.getOriginCountry());
			pstm.setInt(index++, movie.getProductionYear());
			pstm.setString(index++, movie.getDescription());
			pstm.setBoolean(index++, movie.isActive());
			
			
			return pstm.executeUpdate() == 1;
			
		}finally {
			try {pstm.close();}catch(Exception ex) {ex.printStackTrace();}
			try {conn.close();}catch(Exception ex) {ex.printStackTrace();}
		}
	}
	
	public static ArrayList<Genre> returnGenres(List<String> dbGenres){
		
		ArrayList<Genre> genres = new ArrayList<Genre>();
		for(String g : dbGenres) {
			System.out.println(g);
			Genre genre = Genre.valueOf(g);
			genres.add(genre);
		}
		return genres;
	}
	

	
}
