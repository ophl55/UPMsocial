package resources;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import dao.UsuarioDao;
import model.Usuario;

@Path("usuarios")
public class UsuarioResource {

	// Web API
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

}
