package model;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "usuarios")
public class UsuarioList {
	private List<Usuario> list;

	public UsuarioList() {
	}

	public UsuarioList(List<Usuario> list) {
		this.list = list;
	}

	@XmlElement(name = "usuario")
	public List<Usuario> getList() {
		return list;
	}

	public void setList(List<Usuario> list) {
		this.list = list;
	}

}
