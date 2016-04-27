package resources;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import javax.xml.bind.JAXBElement;

import dao.PostDao;
import model.Post;
import model.PostList;

@Path("usuarios/{usuario}/posts")
public class PostListResource {

	@Context
	UriInfo uriInfo;
	@Context
	Request request;

	@GET
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public Response getPosts(@PathParam("usuario") int usuario) {
		List<Post> posts = PostDao.getInstance().getPosts(usuario);
		System.out.println("PostListResource: getPosts() called!");
		return Response.ok(new PostList(posts)).build();
	}

	@POST
	@Produces(MediaType.TEXT_HTML)
	@Consumes(MediaType.APPLICATION_XML)
	public Response newPost(JAXBElement<Post> post) throws IOException, URISyntaxException {
		Post p = post.getValue();
		int generated_id = PostDao.getInstance().addPost(p);

		System.out.println("Created post with id=" + generated_id + " and name=" + post.getName());

		// return Response.created(uriInfo.getAbsolutePath()).header("Location",
		// uriInfo.getAbsolutePath().toString())
		// .build();
		return Response.created(new URI(uriInfo.getAbsolutePath().toString() + "/" + generated_id)).build();
	}

	@GET
	@Path("count")
	@Produces(MediaType.TEXT_PLAIN)
	  public String getCount(@PathParam("usuario") int usuario) {
	    int count = PostDao.getInstance().getPosts(usuario).size();
	    return String.valueOf(count);
	 }
}
