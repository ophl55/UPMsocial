package model;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Usuario {
	private String id; // TODO id überhaupt benötigt?
	private String nombre;
	private List<Usuario> amigos;

	public Usuario() {
	}

	public Usuario(String id, String nombre) {
		this.id = id;
		this.nombre = nombre;
	}

	public Usuario(String id, String nombre, List<Usuario> amigos) {
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

	public void setAmigos(List<Usuario> amigos) {
		this.amigos = amigos;
	}

	public List<Usuario> getAmigos() {
		return amigos;
	}

	@Override
	public String toString() {
		return "Usuario [id=" + id + ", nombre=" + nombre + "]";
	}

}
