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
	public UsuarioList getUsers() {
		List<Usuario> usuarios = new ArrayList<Usuario>();
		usuarios.addAll(UsuarioDao.getInstance().getModel().values());
		return new UsuarioList(usuarios);
	}

	@POST
	@Produces(MediaType.TEXT_HTML)
	@Consumes(MediaType.APPLICATION_ATOM_XML)
	public Response newUser(JAXBElement<Usuario> usuario) throws IOException {
		Usuario u = usuario.getValue();
		UsuarioDao.getInstance().getModel().put(u.getId(), u); // TODO get
																// generated ID
																// of new user!
																// (not
																// insert it
																// with passed
																// id)

		System.out.println(UsuarioDao.getInstance().getModel().get(u.getId()));

		return Response.created(uriInfo.getAbsolutePath())
				.header("Location", uriInfo.getAbsolutePath().toString() + "/" + u.getId()).build();
	}

}
