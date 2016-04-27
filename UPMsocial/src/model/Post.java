package model;


import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Post {
	private int id;
	private String content;
	private String date;
	private int userId;
	
	public Post() {
		
	}
	
	public Post(String content) {
		this.content = content;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}
	
	
}
