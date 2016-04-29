package dao;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.mysql.jdbc.Connection;

public class DBConnection {

	private static DBConnection instance = null;
	public static final String URL = "jdbc:mysql://localhost:3306/myUPMsocial";
	public static final String USER = "root";
	public static final String PASSWORD = "t00r";
	public static final String DRIVER_CLASS = "com.mysql.jdbc.Driver";

	// private constructor
	private DBConnection() {
		try {
			// Load MySQL Java driver
			Class.forName(DRIVER_CLASS);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	private Connection createConnection() {

		Connection connection = null;
		try {
			// Establish Java MySQL connection
			connection = (Connection) DriverManager.getConnection(URL, USER, PASSWORD);
		} catch (SQLException e) {
			System.out.println("ERROR: Unable to Connect to Database.\n");
			e.printStackTrace();
		}
		return connection;
	}

	public static Connection getConnection() {
		if (instance == null)
			instance = new DBConnection();
		return instance.createConnection();
	}
	
	public static void closeConnection(Statement stmt, Connection conn){
		//finally block used to close resources
	      try{
	         if(stmt!=null)
	            conn.close();
	      } catch(SQLException se){
	      }// do nothing
	      try{
	         if(conn!=null)
	            conn.close();
	      } catch(SQLException se){
	         se.printStackTrace();
	      }//end finally try
	   System.out.println("Connection closed!");
	}

	// To test database
	public static void main(String[] args) {
		String select = "select * from usuario;";
		Statement statement;
		try {
			statement = getConnection().createStatement();
			ResultSet result = statement.executeQuery(select);
			while (result.next()) {
				String userId = result.getString("id");
				String userName = result.getString("name");
				System.out.println("User-ID: " + userId + "\tUsername: " + userName);
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
