package dao;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;

import model.Usuario;

public class UsuarioDao {

	private Map<Integer, Usuario> contentProvider = new HashMap<>();
	private int lastId = 0;

	private static UsuarioDao instance = null;

	private UsuarioDao() {

		Usuario usuario = new Usuario("Usuario1");
		addUser(usuario);
		usuario = new Usuario("Usuario2");
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
	public int addUser(Usuario usuario) {
		usuario.setId(lastId);
		contentProvider.put(usuario.getId(), usuario);
		lastId++;
		return usuario.getId();
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
		if (!containsId(id))
			return false;

		contentProvider.remove(id);
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
		return contentProvider.containsValue(usuario);
	}

	/**
	 * 
	 * @param id
	 *            the id to be searched
	 * @return {@code true} if the id is inside the range of the list
	 */
	public boolean containsId(int id) {
		return contentProvider.containsKey(id);
	}

	public Collection<Usuario> getUsers() {
		return contentProvider.values();
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
		if (!containsId(usuario.getId()))
			return false;

		contentProvider.put(usuario.getId(), usuario);
		return true;
	}

	public Usuario getUser(int id) throws NoSuchElementException {
		if (!containsId(id))
			throw new NoSuchElementException("User with id " + id + " not existing in List!");
		return contentProvider.get(id);
	}

}
