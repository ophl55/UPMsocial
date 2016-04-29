package dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.NoSuchElementException;

import com.mysql.jdbc.Connection;

import model.Usuario;

public class UsuarioDao {

	private static UsuarioDao instance = null;

	private UsuarioDao() {

		// TODO default inserts
		// Usuario usuario = new Usuario("Usuario1");
		// addUser(usuario);
		// usuario = new Usuario("Usuario2");
		// addUser(usuario);

	}

	public static UsuarioDao getInstance() {
		if (instance == null)
			instance = new UsuarioDao();
		return instance;
	}

	/**
	 * Adds the user to the database. The generated ID is returned.
	 * 
	 * @param usuario
	 *            User to be added (id of user not important)
	 * @return The generated ID for the user as an int
	 */
	public int addUser(Usuario usuario) {
		int id = 0;

		String sql = "INSERT INTO usuario(id,name) VALUES(0,'" + usuario.getNombre() + "');";

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

		} catch (SQLException se) {
			// Handle errors for JDBC
			se.printStackTrace();
		} finally {
			// finally block used to close resources
			DBConnection.closeConnection(statement, connection);
		} // end try
		return id;
	}

	/**
	 * Removes the user from the list.
	 * 
	 * @param usuario
	 *            the user to be removed from the list
	 * @return {@code true} if the user was removed successfully
	 */
	public boolean removeUser(Usuario usuario) {
		return removeUserById(usuario.getId());
	}

	/**
	 * Removes the user from the list, searching it by id
	 * 
	 * @param id
	 *            the id of the user to delete
	 * @return {@code true} if the user is removed successfully
	 */
	public boolean removeUserById(int id) {
		String sql = "DELETE FROM usuario WHERE id = " + "'" + String.valueOf(id) + "';";

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
			return false;
		} finally {
			// finally block used to close resources
			DBConnection.closeConnection(statement, connection);
		} // end try
		return true;
	}

	/**
	 * Tests if the db contains the user.
	 * 
	 * @param usuario
	 *            the user to search for.
	 * @return {@code true} if the user is found in the db
	 */
	public boolean contains(Usuario usuario) {
		return containsId(usuario.getId());
	}

	/**
	 * 
	 * @param id
	 *            the id to be searched
	 * @return {@code true} if the id is inside the range of the db
	 */
	public boolean containsId(int id) {
		String sql = "SELECT count(*) FROM usuario WHERE id = " + "'" + String.valueOf(id) + "';";

		Statement statement = null;
		Connection connection = DBConnection.getConnection();
		try {
			System.out.println("Connecting to a selected database...");
			statement = connection.createStatement();
			System.out.println("Connected database successfully...");

			ResultSet rs = statement.executeQuery(sql);
			rs.next();
			if (rs.getInt("count(*)") > 0) {
				System.out.println("Table contains id!");
				return true;
			} else
				System.out.println("Table doesn't contain id!");
			return false;
		} catch (SQLException se) {
			// Handle errors for JDBC
			se.printStackTrace();
		} finally {
			// finally block used to close resources
			DBConnection.closeConnection(statement, connection);
		} // end try
		return false;
	}

	public Collection<Usuario> getUsers() {
		List<Usuario> l = new ArrayList<Usuario>();

		String sql = "SELECT * FROM usuario;";

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
	 * Replaces (modifies) the user with the specified id by the new user
	 * 
	 * @param usuario
	 *            the new user data
	 * @return {@code true} if the user is replaced successfully, false if the
	 *         id doesn't exist.
	 */
	public boolean replaceUser(Usuario usuario) {
		String sql = "UPDATE usuario SET " + "name = '" + usuario.getNombre() + "' " + "WHERE id = '"
				+ String.valueOf(usuario.getId()) + "';";

		Statement statement = null;
		Connection connection = DBConnection.getConnection();
		try {
			System.out.println("Connecting to a selected database...");
			statement = connection.createStatement();
			System.out.println("Connected database successfully...");

			statement.executeUpdate(sql);
			System.out.println("Updated record from the table...");
		} catch (SQLException se) {
			// Handle errors for JDBC
			se.printStackTrace();
			return false;
		} finally {
			// finally block used to close resources
			DBConnection.closeConnection(statement, connection);
		} // end try
		return true;
	}

	/**
	 * Returns the user with the passed id. If the user doesn't exist a
	 * NoSuchElementException is thrown.
	 * 
	 * @param id
	 * @return
	 * @throws NoSuchElementException
	 */
	public Usuario getUser(int id) throws NoSuchElementException {
		String sql = "SELECT * FROM usuario WHERE id = '" + String.valueOf(id) + "';";

		Statement statement = null;
		Connection connection = DBConnection.getConnection();
		try {
			System.out.println("Connecting to a selected database...");
			statement = connection.createStatement();
			System.out.println("Connected database successfully...");

			ResultSet rs = statement.executeQuery(sql);
			if (!rs.next())
				// No user returned from db
				throw new NoSuchElementException("User with id " + id + " not existing!");
			else
				return new Usuario(rs.getInt("id"), rs.getString("name"));

		} catch (SQLException se) {
			// Handle errors for JDBC
			se.printStackTrace();
		} finally {
			// finally block used to close resources
			DBConnection.closeConnection(statement, connection);
		} // end try

		return null;
	}

}
