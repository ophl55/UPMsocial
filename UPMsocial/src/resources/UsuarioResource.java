package resources;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
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

@Path("usuarios")
public class UsuarioResource {

	@Context
	UriInfo uriInfo;
	@Context
	Request request;

	@Path("{usuario}")
	@GET
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public Response getUsuario(@PathParam("usuario") String id) {
		Response res;
		Usuario usuario;
		if (UsuarioDao.getInstance().getModel().containsKey(id)) {
			usuario = UsuarioDao.getInstance().getModel().get(id);
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
	public Response deleteTodo(@PathParam("usuario") String id) {
		Response res;
		if (UsuarioDao.getInstance().getModel().containsKey(id)) {
			UsuarioDao.getInstance().getModel().remove(id);
			res = Response.ok().build();
		} else {
			// throw new RuntimeException("Delete: Tarea con id " + id + " no
			// encontrada");
			res = Response.noContent().build();
		}
		return res;
	}

	private Response putAndGetResponse(Usuario usuario) {
		Response res;
		if (UsuarioDao.getInstance().getModel().containsKey(usuario.getId())) {
			res = Response.noContent().build();
		} else {
			UsuarioDao.getInstance().getModel().put(usuario.getId(), usuario);
			res = Response.created(uriInfo.getAbsolutePath()).header("Location", uriInfo.getAbsolutePath().toString())
					.build();
		}
		return res;
	}

}
