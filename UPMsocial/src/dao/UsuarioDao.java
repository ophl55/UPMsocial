package dao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.NoSuchElementException;

import model.Usuario;

public class UsuarioDao {

	// private Map<String, Usuario> contentProvider = new HashMap<>();

	private ArrayList<Usuario> contentProvider = new ArrayList<>();

	private static UsuarioDao instance = null;

	private UsuarioDao() {

		Usuario usuario = new Usuario("", "Usuario1");
		addUser(usuario);
		usuario = new Usuario("", "Usuario2");
		addUser(usuario);

	}

	public static UsuarioDao getInstance() {
		if (instance == null)
			instance = new UsuarioDao();
		return instance;
	}

	/**
	 * Adds the user to the contentProvider and sets the generated ID of the
	 * user. The generated ID is also returned.
	 * 
	 * @param usuario
	 *            User to be added (id of user not important)
	 * @return The generated ID for the user as a String
	 */
	public String addUser(Usuario usuario) {
		contentProvider.add(usuario);
		String id = Integer.toString(contentProvider.indexOf(usuario));
		usuario.setId(id);
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
		// the remove-method of ArrayList automatically returns true if removed
		// successfully,
		// else false
		return contentProvider.remove(usuario);
	}

	/**
	 * Removes the user from the list, searching it by id
	 * 
	 * @param id
	 *            the id of the user to delete
	 * @return {@code true} if the user is removed successfully
	 */
	public boolean removeUserById(String id) {
		if (!containsId(id))
			return false;

		contentProvider.remove(Integer.parseInt(id));
		return true;
	}

	/**
	 * Tests if the list contains the user.
	 * 
	 * @param usuario
	 *            the user to search for.
	 * @return {@code true} if the user is found in the list
	 */
	public boolean contains(Usuario usuario) {
		return contentProvider.contains(usuario);
	}

	/**
	 * 
	 * @param id
	 *            the id to be searched
	 * @return {@code true} if the id is inside the range of the list
	 */
	public boolean containsId(String id) {
		int id_int = Integer.parseInt(id);
		return 0 <= id_int && id_int < contentProvider.size();
	}

	public Collection<Usuario> getUsers() {
		return contentProvider;
	}

	/**
	 * Replaces (modifies) the user with the specified id by the new user
	 * 
	 * @param id
	 *            the id of the user to be replaced
	 * @param usuario
	 *            the new user data
	 * @return {@code true} if the user is replaced successfully, false if the
	 *         id doesn't exist.
	 */
	public boolean replaceUser(String id, Usuario usuario) {
		if (!containsId(id))
			return false;

		int id_int = Integer.parseInt(id);
		contentProvider.set(id_int, usuario);
		return true;
	}

	public Usuario getUser(String id) throws NoSuchElementException {
		if (!containsId(id))
			throw new NoSuchElementException("User with id " + id + " not existing in List!");
		int id_int = Integer.parseInt(id);
		return contentProvider.get(id_int);
	}

}
