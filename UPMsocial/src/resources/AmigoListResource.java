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
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import javax.xml.bind.JAXBElement;

import dao.AmigoDao;
import dao.UsuarioDao;
import model.Post;
import model.PostList;
import model.Usuario;
import model.UsuarioList;

@Path("/usuarios/{usuario}/amigos")
public class AmigoListResource {

	@Context
	UriInfo uriInfo;
	@Context
	Request request;

	@GET
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public Response getAmigos(@PathParam("usuario") String id, @QueryParam("name") String name,
			@QueryParam("start") int start, @QueryParam("end") int end) {
		int id_int = Integer.parseInt(id);
		if (UsuarioDao.getInstance().containsId(id_int)) {
			// User exists
			List<Usuario> amigos = new ArrayList<Usuario>();

			amigos.addAll(AmigoDao.getInstance().getAmigos(id_int, name, start, end));

			return Response.ok(new UsuarioList(amigos)).build();
		} else
			// User doesn't exist
			return Response.status(Response.Status.NOT_FOUND).build();
	}

	@POST
	@Produces(MediaType.TEXT_HTML)
	@Consumes(MediaType.APPLICATION_XML)
	public Response addAmigo(@PathParam("usuario") String id, JAXBElement<Usuario> amigo)
			throws IOException, URISyntaxException {
		System.out.println("Adding friend " + id + ":\t " + amigo.getValue().toString());
		int id_int = Integer.parseInt(id);
		int friend_id = amigo.getValue().getId();
		if (UsuarioDao.getInstance().containsId(id_int) && UsuarioDao.getInstance().containsId(friend_id)) {

			AmigoDao.getInstance().addFriend(id_int, friend_id);

			return Response.created(new URI(uriInfo.getAbsolutePath().toString() + "/" + friend_id)).build();
		} else
			return Response.status(Response.Status.NOT_FOUND).build();
	}

	@Path("posts")
	@GET
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public Response getPostsOfAmigos(@PathParam("usuario") String id, @QueryParam("dt") String date,
			@QueryParam("content") String content, @QueryParam("start") int start, @QueryParam("end") int end) {
		int id_int = Integer.parseInt(id);
		if (UsuarioDao.getInstance().containsId(id_int)) {
			// User exists

			List<Post> posts = new ArrayList<>();
			// get all matching posts of friends
			posts.addAll(AmigoDao.getInstance().getPostsOfAmigo(id_int, date, content, start, end));

			return Response.ok(new PostList(posts)).build();
		} else
			// User doesn't exist
			return Response.status(Response.Status.NOT_FOUND).build();
	}

}
