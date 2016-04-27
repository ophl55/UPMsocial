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
		int userId_int = Integer.parseInt(user_id), friendId_int = Integer.parseInt(friend_id);
		Response res;
		if (UsuarioDao.getInstance().containsId(userId_int) && UsuarioDao.getInstance().containsId(friendId_int)) {
			// User and friend exist...
			// Remove friend from users friend list
			System.out.println("Removing friend " + friend_id + " from friendlist of user " + user_id);
			UsuarioDao.getInstance().getUser(userId_int).getAmigos().remove(friendId_int);
			res = Response.ok().build();
		} else {
			// User or friend not existing
			res = Response.noContent().build();
		}
		return res;
	}

}
