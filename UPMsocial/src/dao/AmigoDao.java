package dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.mysql.jdbc.Connection;

import model.Post;
import model.Usuario;

public class AmigoDao {

	private static AmigoDao instance = null;

	private AmigoDao() {

	}

	public static AmigoDao getInstance() {
		if (instance == null)
			instance = new AmigoDao();
		return instance;
	}

	/**
	 * Returns a list of friends found with the matching search parameters. The
	 * id-parameter is required. All the other parameters can be either set, or
	 * be set to {@code null} or 0
	 * 
	 * @param id
	 *            the user-id
	 * @param name
	 *            the name which the returned friends should contain, or null if
	 *            not specified.
	 * @param start
	 *            first index to be returned, or 0 if not specified.
	 * @param end
	 *            last index to be returned, or 0 if not specified.
	 * @return list of friends that match the desired parameters
	 * @throws IllegalArgumentException
	 *             for bad arguments
	 */
	public Collection<Usuario> getAmigos(int id, String name, int start, int end) throws IllegalArgumentException {
		List<Usuario> l = new ArrayList<Usuario>();

		if (start > end)
			throw new IllegalArgumentException("start can't be greater than end!");

		String sql = "SELECT * FROM usuario,amigos WHERE usuario1_id='" + String.valueOf(id)
				+ "' AND usuario.id=amigos.usuario2_id ";

		if (name != null)
			sql += "AND usuario.name like '%" + name + "%' ";
		if (end != 0)
			sql += "LIMIT " + String.valueOf(start) + "," + String.valueOf(end);

		sql += ";";

		Statement statement = null;
		Connection connection = DBConnection.getConnection();
		try {
			System.out.println("Connecting to a selected database...");
			statement = connection.createStatement();
			System.out.println("Connected database successfully...");

			ResultSet rs = statement.executeQuery(sql);
			while (rs.next()) {
				l.add(new Usuario(rs.getInt("id"), rs.getString("name")));
			}
		} catch (SQLException se) {
			// Handle errors for JDBC
			se.printStackTrace();
		} finally {
			// finally block used to close resources
			DBConnection.closeConnection(statement, connection);
		} // end try

		return l;
	}

	/**
	 * Adds the passed friend to the friendlist of the user
	 * 
	 * @param user
	 *            the user-id
	 * @param friend
	 *            the friend-id
	 * @return {@code true} if the friend has been added with success
	 */
	public boolean addFriend(int user, int friend) {
		boolean success = true;
		String sql = "INSERT INTO amigos(usuario1_id,usuario2_id) VALUES('" + String.valueOf(user) + "','"
				+ String.valueOf(friend) + "');";

		Statement statement = null;
		Connection connection = DBConnection.getConnection();

		try {
			System.out.println("Connecting to a selected database...");
			statement = connection.createStatement();
			System.out.println("Connected database successfully...");

			statement.executeUpdate(sql);
		} catch (SQLException se) {
			// Handle errors for JDBC
			se.printStackTrace();
			success = false;
		} finally {
			// finally block used to close resources
			DBConnection.closeConnection(statement, connection);
		} // end try

		return success;
	}

	/**
	 * Returns a list of friends which aren't already in the friendlist of the
	 * user and which aren't the user himself.
	 * 
	 * @param id
	 *            the id of the user
	 * @param name
	 *            a name to filter the friends
	 * @return list of possible friends for the user
	 */
	public Collection<Usuario> getPosiblesAmigos(int id, String name) {
		List<Usuario> l = new ArrayList<Usuario>();

		String sql = "SELECT * FROM usuario u WHERE NOT EXISTS (SELECT * FROM usuario, amigos WHERE usuario.id=amigos.usuario1_id AND usuario.id='"
				+ String.valueOf(id) + "' AND amigos.usuario2_id=u.id) AND u.id<>'" + String.valueOf(id) + "'";

		if (name != null)
			sql += " AND name like '%" + name + "%' ";

		sql += ";";

		Statement statement = null;
		Connection connection = DBConnection.getConnection();
		try {
			System.out.println("Connecting to a selected database...");
			statement = connection.createStatement();
			System.out.println("Connected database successfully...");

			ResultSet rs = statement.executeQuery(sql);
			while (rs.next()) {
				l.add(new Usuario(rs.getInt("id"), rs.getString("name")));
			}
		} catch (SQLException se) {
			// Handle errors for JDBC
			se.printStackTrace();
		} finally {
			// finally block used to close resources
			DBConnection.closeConnection(statement, connection);
		} // end try

		return l;
	}

	/**
	 * Removes the friend from the friendlist of the user.
	 * 
	 * @param userId
	 *            the user-id
	 * @param friendId
	 *            the id of the friend to be removed
	 * @return {@code true} if the friend has been removed successfully
	 */
	public boolean removeFriend(int userId, int friendId) {
		boolean success = true;
		String sql = "DELETE FROM amigos WHERE amigos.usuario1_id='" + String.valueOf(userId)
				+ "' AND amigos.usuario2_id='" + String.valueOf(friendId) + "';";

		Statement statement = null;
		Connection connection = DBConnection.getConnection();
		try {
			System.out.println("Connecting to a selected database...");
			statement = connection.createStatement();
			System.out.println("Connected database successfully...");

			statement.executeUpdate(sql);
			System.out.println("Deleted record from the table...");
		} catch (SQLException se) {
			// Handle errors for JDBC
			se.printStackTrace();
			success = false;
		} finally {
			// finally block used to close resources
			DBConnection.closeConnection(statement, connection);
		} // end try
		return success;
	}

	/**
	 * Returns a list with posts which where posted by the friends of the user.
	 * 
	 * @param userId
	 *            the user-id
	 * @param date
	 *            date to filter the posts
	 * @param content
	 *            content filter
	 * @param start
	 *            first index to return
	 * @param end
	 *            last index to return
	 * @return list of posts
	 * @throws IllegalArgumentException
	 *             thrown if the arguments start/end are not set correctly
	 */
	public List<Post> getPostsOfAmigo(int userId, String date, String content, int start, int end)
			throws IllegalArgumentException {
		if (start > end)
			throw new IllegalArgumentException("start can't be greater than end!");

		List<Post> l = new ArrayList<Post>();

		String sql = "SELECT p.* FROM usuario u, amigos a, post p WHERE u.id='" + String.valueOf(userId)
				+ "' AND u.id=a.usuario1_id AND p.usuario_id=a.usuario2_id";

		if (content != null)
			sql += " AND content LIKE '%" + content + "%'";
		if (date != null)
			sql += " AND date = '" + date + "'";
		if (end != 0)
			sql += " LIMIT " + String.valueOf(start) + "," + String.valueOf(end);

		sql += ";";

		Statement statement = null;
		Connection connection = DBConnection.getConnection();
		try {
			System.out.println("Connecting to a selected database...");
			statement = connection.createStatement();
			System.out.println("Connected database successfully...");

			ResultSet rs = statement.executeQuery(sql);
			while (rs.next()) {
				l.add(new Post(rs.getInt("id"), rs.getString("content"), rs.getString("date"),
						rs.getInt("usuario_id")));
			}
		} catch (SQLException se) {
			// Handle errors for JDBC
			se.printStackTrace();
		} finally {
			// finally block used to close resources
			DBConnection.closeConnection(statement, connection);
		} // end try

		return l;
	}

}
