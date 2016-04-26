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

import dao.UsuarioDao;
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
	public Response getAmigos(@PathParam("usuario") String id) {
		int id_int = Integer.parseInt(id);
		if (UsuarioDao.getInstance().containsId(id_int)) {
			// User exists
			List<Usuario> amigos = new ArrayList<Usuario>();
			amigos.addAll(UsuarioDao.getInstance().getUser(id_int).getAmigos().values());
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
		if (UsuarioDao.getInstance().containsId(id_int)
				&& UsuarioDao.getInstance().containsId(amigo.getValue().getId())) {
			Usuario a = UsuarioDao.getInstance().getUser(amigo.getValue().getId());
			Usuario usuario = UsuarioDao.getInstance().getUser(id_int);
			usuario.getAmigos().put(a.getId(), a);

			// return Location of added Friend (actual URI + "/{user-id}")
			return Response.created(new URI(uriInfo.getAbsolutePath().toString() + "/" + a.getId())).build();
		} else
			return Response.status(Response.Status.NOT_FOUND).build();
	}

}
