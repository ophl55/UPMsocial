package resources;

import java.io.IOException;
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
	public UsuarioList getAmigos(@PathParam("usuario") String id) {
		if (UsuarioDao.getInstance().getModel().containsKey(id)) {
			List<Usuario> amigos = new ArrayList<Usuario>();
			amigos.addAll(UsuarioDao.getInstance().getModel().get(id).getAmigos());
			return new UsuarioList(amigos);
		} else
			// TODO what to do if the requested user doesn't exist?
			return null;
	}

	@POST
	@Produces(MediaType.TEXT_HTML)
	@Consumes(MediaType.APPLICATION_ATOM_XML)
	public Response addAmigo(@PathParam("usuario") String id, JAXBElement<Usuario> amigo) throws IOException {
		if (UsuarioDao.getInstance().getModel().containsKey(id)
				&& UsuarioDao.getInstance().getModel().containsKey(amigo.getValue().getId())) {
			Usuario a = UsuarioDao.getInstance().getModel().get(amigo.getValue().getId());
			Usuario usuario = UsuarioDao.getInstance().getModel().get(id);
			usuario.getAmigos().add(a);

			// return Location of added Friend (actual URI + "/{user-id}")
			return Response.created(uriInfo.getAbsolutePath())
					.header("Location", uriInfo.getAbsolutePath().toString() + "/" + a.getId()).build();
		} else
			return Response.noContent().build();
	}

}
