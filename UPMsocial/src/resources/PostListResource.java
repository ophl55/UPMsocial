package resources;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import javax.xml.bind.JAXBElement;

import dao.PostDao;
import dao.UsuarioDao;
import model.Post;
import model.PostList;

@Path("/usuarios/{usuario}/posts")
public class PostListResource {

	@Context
	UriInfo uriInfo;
	@Context
	Request request;

	@GET
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public Response getPosts(
			@PathParam("usuario") int usuario, 
			@QueryParam("startDate") String startDate,
			@QueryParam("endDate") String endDate, 
			@QueryParam("start") int start, 
			@QueryParam("end") int end) {
		
		Response res;
		
		if (!UsuarioDao.getInstance().containsId(usuario)){
			res = Response.status(Response.Status.NOT_FOUND).build();			
		}
		else {
			List<Post> posts = PostDao.getInstance().getPosts(usuario, startDate, endDate, start, end);
			res = Response.ok(new PostList(posts)).build();			
		}
		return res;
	}

	@POST
	@Produces(MediaType.TEXT_HTML)
	@Consumes(MediaType.APPLICATION_XML)
	public Response newPost(JAXBElement<Post> post) throws IOException, URISyntaxException {
		Post p = post.getValue();
		int generated_id = PostDao.getInstance().addPost(p);

		return Response.created(new URI(uriInfo.getAbsolutePath().toString() + "/" + generated_id)).build();
	}

	@GET
	@Path("count")
	@Produces(MediaType.TEXT_PLAIN)
	public Response getCount(@PathParam("usuario") int usuario, 
			@QueryParam("startDate") String startDate,
			@QueryParam("endDate") String endDate) {
		
		Response res;
		int count;
		
		if (!UsuarioDao.getInstance().containsId(usuario)){
			res = Response.status(Response.Status.NOT_FOUND).build();
		} 
		else {
			count = PostDao.getInstance().getPosts(usuario, startDate, endDate, 0, 0).size();
			res = Response.ok(String.valueOf(count)).build();
		}
		return res;
	}

}
