package resources;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
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

import dao.UsuarioDao;
import model.Usuario;
import model.UsuarioList;

@Path("/usuarios")
public class UsuarioResource {

	@Context
	UriInfo uriInfo;
	@Context
	Request request;

	@Path("{usuario}")
	@GET
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public Response getUser(@PathParam("usuario") String id) {
		int id_int = Integer.parseInt(id);
		Response res;
		Usuario usuario;
		if (UsuarioDao.getInstance().containsId(id_int)) {
			usuario = UsuarioDao.getInstance().getUser(id_int);
			res = Response.ok(usuario).build();
		} else {
			res = Response.status(Response.Status.NOT_FOUND).build();
		}
		return res;
	}

	@Path("{usuario}")
	@PUT
	@Consumes(MediaType.APPLICATION_XML)
	public Response putTodo(JAXBElement<Usuario> usuario) {
		Usuario u = usuario.getValue();
		return putAndGetResponse(u);
	}

	@Path("{usuario}")
	@DELETE
	public Response deleteUser(@PathParam("usuario") String id) {
		int id_int = Integer.parseInt(id);
		Response res;
		if (UsuarioDao.getInstance().containsId(id_int)) {
			UsuarioDao.getInstance().removeUserById(id_int);
			res = Response.ok().build();
		} else {
			// throw new RuntimeException("Delete: Tarea con id " + id + " no
			// encontrada");
			res = Response.noContent().build();
		}
		return res;
	}

	@Path("{usuario}/posiblesAmigos")
	@GET
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public Response getPosiblesAmigos(@PathParam("usuario") String id, @QueryParam("name") String name) {
		int id_int = Integer.parseInt(id);
		if (UsuarioDao.getInstance().containsId(id_int)) {
			// User exists
			List<Usuario> posiblesAmigos = new ArrayList<Usuario>();
			Usuario u = UsuarioDao.getInstance().getUser(id_int);

			for (Usuario usuario : UsuarioDao.getInstance().getUsers()) {
				// test if usuario is not the user itself (whose friends we are
				// looking for), if it is not already
				// a friend of the user and if it contains the desired name (if
				// parameter is set)
				if (usuario.getId() != u.getId() && !u.getAmigos().containsKey(usuario.getId())
						&& (name == null || usuario.getNombre().toLowerCase().contains(name.toLowerCase())))
					posiblesAmigos.add(usuario);
			}

			return Response.ok(new UsuarioList(posiblesAmigos)).build();
		} else
			// User doesn't exist
			return Response.status(Response.Status.NOT_FOUND).build();
	}

	/**
	 * Modifies the passed user, only if it exists
	 * 
	 * @param usuario
	 *            modified user object
	 * @return
	 */
	private Response putAndGetResponse(Usuario usuario) {
		Response res;
		if (UsuarioDao.getInstance().containsId(usuario.getId())) {
			UsuarioDao.getInstance().replaceUser(usuario);
			res = Response.noContent().build();
		} else {
			res = Response.status(Response.Status.NOT_FOUND).build();
		}
		return res;
	}

}
