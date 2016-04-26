package model;

import java.util.Map;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Usuario {
	private int id;
	private String nombre;

	private Map<Integer, Usuario> amigos;

	public Usuario() {
	}

	public Usuario(String nombre) {
		this.nombre = nombre;
	}

	public Usuario(int id, String nombre, Map<Integer, Usuario> amigos) {
		this.id = id;
		this.nombre = nombre;
		this.amigos = amigos;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public Map<Integer, Usuario> getAmigos() {
		return amigos;
	}

	public void setAmigos(Map<Integer, Usuario> amigos) {
		this.amigos = amigos;
	}

	@Override
	public String toString() {
		return "Usuario [id=" + id + ", nombre=" + nombre + "]";
	}

}
