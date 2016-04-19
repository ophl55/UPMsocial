package dao;

import java.util.HashMap;
import java.util.Map;

import model.Usuario;

public class UsuarioDao {

	private Map<String, Usuario> contentProvider = new HashMap<>();

	private static UsuarioDao instance = null;

	private UsuarioDao() {

		Usuario usuario = new Usuario("1", "Usuario1"); // TODO Usuario-ID
														// überhaupt benötigt?
		contentProvider.put("1", usuario);
		usuario = new Usuario("2", "Usuario2");
		contentProvider.put("2", usuario);

	}

	public Map<String, Usuario> getModel() {
		return contentProvider;
	}

	public static UsuarioDao getInstance() {
		if (instance == null)
			instance = new UsuarioDao();
		return instance;
	}

}
