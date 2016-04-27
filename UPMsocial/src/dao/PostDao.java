package dao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

import model.Post;

public class PostDao {
	private Map<Integer, Post> contentProvider = new HashMap<>();
	private int lastId = 0;

	private static PostDao instance = null;

	private PostDao() {

		Post post = new Post("Post1");
		post.setUserId(0);
		addPost(post);
		
		post = new Post("Post2");
		post.setUserId(1);
		addPost(post);
		
		post = new Post("Post3");
		post.setUserId(2);
		addPost(post);
		
		post = new Post("Post4");
		post.setUserId(0);
		addPost(post);

	}

	public static PostDao getInstance() {
		if (instance == null)
			instance = new PostDao();
		return instance;
	}

	/**
	 * Adds the post to the contentProvider and sets the generated ID and the date of the
	 * post. The generated ID is also returned.
	 * 
	 * @param post
	 *            Post to be added (id of user not important)
	 * @return The generated ID for the user as a String
	 */
	public int addPost(Post post) {
		post.setId(lastId);
		post.setDate(new Date());
		contentProvider.put(post.getId(), post);
		lastId++;
		return post.getId();
	}

	/**
	 * Removes the post from the list.
	 * 
	 * @param post
	 *            the post to be removed from the list
	 * @return {@code true} if the post was removed successfully
	 */
	public boolean removePost(Post post) {
		if (!containsId(post.getId()))
			return false;

		contentProvider.remove(post.getId());
		return true;
	}

	/**
	 * Removes the post from the list, searching it by id
	 * 
	 * @param id
	 *            the id of the post to delete
	 * @return {@code true} if the post is removed successfully
	 */
	public boolean removePostById(int id) {
		if (!containsId(id))
			return false;

		contentProvider.remove(id);
		return true;
	}

	/**
	 * Tests if the list contains the post.
	 * 
	 * @param post
	 *            the post to search for.
	 * @return {@code true} if the post is found in the list
	 */
	public boolean contains(Post post) {
		return contentProvider.containsValue(post);
	}

	/**
	 * 
	 * @param id
	 *            the id to be searched
	 * @return {@code true} if the id is inside the range of the list
	 */
	public boolean containsId(int id) {
		return contentProvider.containsKey(id);
	}

	public List<Post> getPosts(int userId) {
		List<Post> l = new ArrayList<Post>();
		Iterator<Map.Entry<Integer, Post>> entries = contentProvider.entrySet().iterator();
		while (entries.hasNext()) {
		  Map.Entry<Integer, Post> entry = entries.next();
		  int key = entry.getKey();
		  Post value = entry.getValue();
		  if (value.getUserId() == userId)
			  l.add(value);
		}
		return l;
	}

	/**
	 * Replaces (modifies) the post with the specified id by the new post
	 * 
	 * @param post
	 *            the new post data
	 * @return {@code true} if the user is replaced successfully, false if the
	 *         id doesn't exist.
	 */
	public boolean replacePost(Post post) {
		if (!containsId(post.getId()))
			return false;

		contentProvider.put(post.getId(), post);
		return true;
	}

	public Post getPost(int id) throws NoSuchElementException {
		if (!containsId(id))
			throw new NoSuchElementException("Post with id " + id + " not existing in List!");
		return contentProvider.get(id);
	}

}
