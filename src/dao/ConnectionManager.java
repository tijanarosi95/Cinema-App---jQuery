package dao;

import java.sql.Connection;
import java.util.Properties;

import javax.sql.DataSource;

import org.apache.commons.dbcp2.BasicDataSourceFactory;

public class ConnectionManager {
	
	private static final String DATABASE_NAME = "cinema.db";
	
	private static final String FILE_SEPARATOR = System.getProperty("file.separator");
	
	private static final String TIJANA_PATH = FILE_SEPARATOR + "home" + FILE_SEPARATOR + "tijana" +
									FILE_SEPARATOR + "SQLite" + FILE_SEPARATOR + DATABASE_NAME;
	
	private static final String PATH = TIJANA_PATH;
	
	private static DataSource dataSource;
	
	public static void open() {
		try {
			Properties dsproperties = new Properties();
			dsproperties.setProperty("driverClassName", "org.sqlite.JDBC");
			dsproperties.setProperty("url", "jdbc:sqlite:" + PATH);
			
			dataSource = BasicDataSourceFactory.createDataSource(dsproperties);
		}catch(Exception ex) {
			ex.printStackTrace();
		}
	}
	
	public static Connection getConnection() {
		try {
			return dataSource.getConnection();
		}catch(Exception ex) {
			ex.printStackTrace();
		}
		return null;
	}
	
	
}
