package model;

import java.util.Map;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Usuario {
	private String id;
	private String nombre;
	private Map<String, Usuario> amigos;

	public Usuario() {
	}

	public Usuario(String id, String nombre) {
		this.id = id;
		this.nombre = nombre;
	}

	public Usuario(String id, String nombre, Map<String, Usuario> amigos) {
		this.id = id;
		this.nombre = nombre;
		this.amigos = amigos;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public Map<String, Usuario> getAmigos() {
		return amigos;
	}

	public void setAmigos(Map<String, Usuario> amigos) {
		this.amigos = amigos;
	}

	@Override
	public String toString() {
		return "Usuario [id=" + id + ", nombre=" + nombre + "]";
	}

}
