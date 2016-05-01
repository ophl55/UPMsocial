package model;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "posts")
public class PostList {
	private List<Post> list;

	public PostList() {
	}

	public PostList(List<Post> list) {
		this.list = list;
	}

	@XmlElement(name = "post")
	public List<Post> getList() {
		return list;
	}

	public void setList(List<Post> list) {
		this.list = list;
	}

}
