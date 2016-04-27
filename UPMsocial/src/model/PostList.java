package model;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class PostList {
	private List<Post> list;

	public PostList() {
	}

	public PostList(List<Post> list) {
		this.list = list;
	}
	
	public List<Post> getList() {
		return list;
	}

	public void setList(List<Post> list) {
		this.list = list;
	}
	
	

}
