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
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
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
	public Response getUsers(@QueryParam("name") String name) {

		List<Usuario> usuarios = new ArrayList<Usuario>();

		if (name == null)
			// query parameter "name" not set
			usuarios.addAll(UsuarioDao.getInstance().getUsers());
		else
			// query parameter "name" set!
			for (Usuario u : UsuarioDao.getInstance().getUsers()) {
			// only add users that contain searched name
			if (u.getNombre().toLowerCase().contains(name.toLowerCase()))
			usuarios.add(u);
			}

		return Response.ok(new UsuarioList(usuarios)).build();
	}

	@POST
	@Produces(MediaType.TEXT_HTML)
	@Consumes(MediaType.APPLICATION_XML)
	public Response newUser(JAXBElement<Usuario> usuario) throws IOException, URISyntaxException {
		Usuario u = usuario.getValue();
		int generated_id = UsuarioDao.getInstance().addUser(u);

		System.out.println("Created user with id=" + generated_id + " and name=" + usuario.getName());

		// return Response.created(uriInfo.getAbsolutePath()).header("Location",
		// uriInfo.getAbsolutePath().toString())
		// .build();
		return Response.created(new URI(uriInfo.getAbsolutePath().toString() + "/" + generated_id)).build();
	}

}
