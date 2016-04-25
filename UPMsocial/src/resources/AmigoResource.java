package resources;

import javax.ws.rs.DELETE;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;

import dao.UsuarioDao;

@Path("/usuarios/{usuario}/amigos")
public class AmigoResource {

	@Path("{amigo}")
	@DELETE
	public Response removeFriend(@PathParam("usuario") String user_id, @PathParam("amigo") String friend_id) {
		Response res;
		if (UsuarioDao.getInstance().getModel().containsKey(user_id)
				&& UsuarioDao.getInstance().getModel().containsKey(friend_id)) {

			UsuarioDao.getInstance().getModel().get(user_id).getAmigos()
					.remove(UsuarioDao.getInstance().getModel().get(friend_id));
			res = Response.ok().build();
		} else {
			res = Response.noContent().build();
		}
		return res;
	}

}
