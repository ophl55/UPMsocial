package dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.mysql.jdbc.Connection;

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

	public Collection<Usuario> getPosiblesAmigos(int id, String name) {
		List<Usuario> l = new ArrayList<Usuario>();

		// TODO change select
		String sql = "SELECT * FROM usuario,amigos WHERE usuario1_id='" + String.valueOf(id)
				+ "' AND usuario.id=amigos.usuario2_id ";

		if (name != null)
			sql += "AND name like '%" + name + "%' ";

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

}
