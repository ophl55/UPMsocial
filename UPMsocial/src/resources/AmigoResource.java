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
		if (UsuarioDao.getInstance().containsId(user_id) && UsuarioDao.getInstance().containsId(friend_id)) {
			// User and friend exist...
			// Remove friend from users friend list
			UsuarioDao.getInstance().getUser(user_id).getAmigos().remove(UsuarioDao.getInstance().getUser(friend_id));
			res = Response.ok().build();
		} else {
			// User or friend not existing
			res = Response.noContent().build();
		}
		return res;
	}

}
