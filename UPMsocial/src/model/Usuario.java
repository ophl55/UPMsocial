package model;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Usuario {
	private String id; // TODO id überhaupt benötigt?
	private String nombre;

	public Usuario() {
	}

	public Usuario(String id, String nombre) {
		this.id = id;
		this.nombre = nombre;
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

}
