package resources;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import javax.xml.bind.JAXBElement;

import dao.PostDao;
import model.Post;

@Path("usuarios/{usuario}/posts")
public class PostResource {

	@Context
	UriInfo uriInfo;
	@Context
	Request request;


	@Path("{post}")
	@PUT
	@Consumes(MediaType.APPLICATION_XML)
	public Response putPost(JAXBElement<Post> post) {
		Post p = post.getValue();
		return putAndGetResponse(p);
	}
	
	@Path("{post}")
	@DELETE
	public Response deleteUser(@PathParam("post") String id) {
		int id_int = Integer.parseInt(id);
		Response res;
		if (PostDao.getInstance().containsId(id_int)) {
			PostDao.getInstance().removePostById(id_int);
			res = Response.ok().build();
		} else {
			// throw new RuntimeException("Delete: Tarea con id " + id + " no
			// encontrada");
			res = Response.noContent().build();
		}
		return res;
	}
	
	/**
	 * Modifies the passed post, only if it exists
	 * 
	 * @param post
	 *            modified post object
	 * @return
	 */
	private Response putAndGetResponse(Post post) {
		Response res;
		if (PostDao.getInstance().containsId(post.getId())) {
			PostDao.getInstance().replacePost(post);
			res = Response.noContent().build();
		} else {
			res = Response.status(Response.Status.NOT_FOUND).build();
		}
		return res;
	}

}
