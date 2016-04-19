package model;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class UsuarioList {

	private List<Usuario> list;

	public UsuarioList() {
	}

	public UsuarioList(List<Usuario> list) {
		this.list = list;
	}

	public List<Usuario> getList() {
		return list;
	}

	public void setList(List<Usuario> list) {
		this.list = list;
	}

}
