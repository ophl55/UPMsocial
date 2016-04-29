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
import dao.UsuarioDao;
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
		Response res;
		Post p = post.getValue();
		
		if (!UsuarioDao.getInstance().containsId(p.getUserId()) || !PostDao.getInstance().containsId(p.getId()))
			res = Response.status(Response.Status.NOT_FOUND).build();
		else 
			res = Response.status(Response.Status.NO_CONTENT).build();
		
		return res;
	}
	
	@Path("{post}")
	@DELETE
	public Response deletePost(
			@PathParam("usuario") int usuarioId,
			@PathParam("post") int postId) {
		
		Response res;
		
		
		if (!UsuarioDao.getInstance().containsId(usuarioId) || !PostDao.getInstance().containsId(postId))
			res = Response.status(Response.Status.NOT_FOUND).build();
		else {
			PostDao.getInstance().removePostById(postId);
			res = Response.status(Response.Status.NO_CONTENT).build();
		}
		
		return res;
	}
}
