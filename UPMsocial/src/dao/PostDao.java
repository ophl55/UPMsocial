package dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.mysql.jdbc.Connection;

import model.Post;
import dao.DBConnection;

public class PostDao {
	private SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy-MM-dd");

	private static PostDao instance = null;

	private PostDao() {
		Post post = new Post("SQL Insert");
		post.setUserId(1);
		addPost(post);
	}

	public static PostDao getInstance() {
		if (instance == null)
			instance = new PostDao();
		return instance;
	}

	/**
	 * Adds the post to the db and sets the date of the post. 
	 * The generated ID is also returned.
	 * 
	 * @param post	Post to be added (id of user not important)
	 * @return 		The generated ID for the user as a String
	 */
	public int addPost(Post post) {
		int id = 0;
		post.setDate(sdfDate.format(new Date()));
		
		String sql = "INSERT INTO post (date, content, usuario_id) VALUES(" 
				+ "'" + post.getDate() + "',"
				+ "'" + post.getContent() + "',"
				+ "'" + String.valueOf(post.getUserId()) + "');";
		
		Statement statement = null;
		Connection connection = DBConnection.getConnection();
		
		try {
			System.out.println("Connecting to a selected database...");
			statement = connection.createStatement();
			System.out.println("Connected database successfully...");

			statement.executeUpdate(sql);
			System.out.println("Inserted records into the table...");
			
			sql = "SELECT @@IDENTITY as 'id'";
			ResultSet rs = statement.executeQuery(sql);
			rs.next();
			id = rs.getInt("id");
			
		} catch(SQLException se){
		      //Handle errors for JDBC
		      se.printStackTrace();
		} finally{
			  //finally block used to close resources
			  DBConnection.closeConnection(statement, connection);
		}//end try
		return id;
	}

	/**
	 * Removes the post from the db, searching it by id
	 * 
	 * @param id	the id of the post to delete
	 * @return 		{@code true} if the post is removed successfully
	 */
	public boolean removePostById(int id) {
		
		String sql = "DELETE FROM post WHERE id = "
				+ "'" + String.valueOf(id) + "';";
		
		Statement statement = null;
		Connection connection = DBConnection.getConnection();
		try {
			System.out.println("Connecting to a selected database...");
			statement = connection.createStatement();
			System.out.println("Connected database successfully...");
		    
			statement.executeUpdate(sql);
		    System.out.println("Deleted record from the table...");
		} catch(SQLException se){
		      //Handle errors for JDBC
		      se.printStackTrace();
		      return false;
		} finally{
			  //finally block used to close resources
			  DBConnection.closeConnection(statement, connection);
		}//end try
		return true;
	}

	/**
	 * db query to check if the post with the given id exists in the db.
	 * 
	 * @param id	the id to be searched
	 * @return 		{@code true} if the id is inside the range of the list
	 */
	public boolean containsId(int id) {
		String sql = "SELECT count(*) FROM post WHERE id = "
				+ "'" + String.valueOf(id) + "';";
		
		Statement statement = null;
		Connection connection = DBConnection.getConnection();
		try {
			System.out.println("Connecting to a selected database...");
			statement = connection.createStatement();
			System.out.println("Connected database successfully...");
		    
			ResultSet rs = statement.executeQuery(sql);
			rs.next();
			if(rs.getInt("count(*)") > 0){
				System.out.println("Table contains id!");
				return true;				
			}
			else
				System.out.println("Table doesn't contain id!");
				return false;
		} catch(SQLException se){
		      //Handle errors for JDBC
		      se.printStackTrace();
		} finally{
			  //finally block used to close resources
			  DBConnection.closeConnection(statement, connection);
		}//end try
		return false;
	}
	
	/**
	 * Executes db-query to get a list of posts for a user.
	 * Only considers the searching parameters if they are set.
	 *  
	 * @param userId
	 * @param startDate	filter-param
	 * @param endDate	filter-param
	 * @param start		filter-param
	 * @param end		filter-param
	 * @return ArrayList containing all results in dependency on the filter-params
	 */
	public List<Post> getPosts(int userId, String startDate, String endDate, int start, int end) {
		List<Post> l = new ArrayList<Post>();
		
		String sql = "SELECT * FROM post WHERE "
				+ "usuario_id = '" + String.valueOf(userId) +"' ";
		if(startDate != null && endDate != null)
			sql += "&& date >= '" + startDate + "' && "
					+ "date <= '" + endDate + "' ";
		if(end != 0)
			sql += "LIMIT " + String.valueOf(start) + "," + String.valueOf(end);
		
		Statement statement = null;
		Connection connection = DBConnection.getConnection();
		try {
			System.out.println("Connecting to a selected database...");
			statement = connection.createStatement();
			System.out.println("Connected database successfully...");
		    
			ResultSet rs = statement.executeQuery(sql);
			while(rs.next()){
				l.add(new Post(
						rs.getInt("id"),
						rs.getString("content"),
						rs.getString("date"),
						rs.getInt("usuario_id")
						));				
			}
		} catch(SQLException se){
		      //Handle errors for JDBC
		      se.printStackTrace();
		} finally{
			  //finally block used to close resources
			  DBConnection.closeConnection(statement, connection);
		}//end try
		
		return l;
	}

	/**
	 * Replaces (modifies) the post with the specified id by the new post
	 * 
	 * @param post
	 *            the new post data
	 * @return {@code true} if the user is replaced successfully, false if error occurs.
	 */
	public boolean replacePost(Post post) {
		String sql = "UPDATE post SET "
				+ "content = '" + post.getContent() + "', "
				+ "date = '" + post.getDate() + "' "
				+ "usuario_id = '" + String.valueOf(post.getUserId()) + "', "
				+ "WHERE id = '" + String.valueOf(post.getId()) + "';";
		
		Statement statement = null;
		Connection connection = DBConnection.getConnection();
		try {
			System.out.println("Connecting to a selected database...");
			statement = connection.createStatement();
			System.out.println("Connected database successfully...");
		    
			statement.executeUpdate(sql);
		    System.out.println("Updated record from the table...");
		} catch(SQLException se){
		      //Handle errors for JDBC
		      se.printStackTrace();
		      return false;
		} finally{
			  //finally block used to close resources
			  DBConnection.closeConnection(statement, connection);
		}//end try
		return true;
	}
}
