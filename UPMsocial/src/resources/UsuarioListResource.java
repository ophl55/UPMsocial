package resources;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
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

@Path("/usuarios")
public class UsuarioListResource {

	@Context
	UriInfo uriInfo;
	@Context
	Request request;

	@GET
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public Response getUsers() {
		List<Usuario> usuarios = new ArrayList<Usuario>();
		usuarios.addAll(UsuarioDao.getInstance().getUsers());
		return Response.ok(new UsuarioList(usuarios)).build();
	}

	@POST
	@Produces(MediaType.TEXT_HTML)
	@Consumes(MediaType.APPLICATION_XML)
	public Response newUser(JAXBElement<Usuario> usuario) throws IOException {
		Usuario u = usuario.getValue();
		int generated_id = UsuarioDao.getInstance().addUser(u);

		return Response.created(uriInfo.getAbsolutePath())
				.header("Location", uriInfo.getAbsolutePath().toString() + "/" + generated_id).build();
	}

}
