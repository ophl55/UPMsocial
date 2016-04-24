package resources;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.xml.bind.JAXBElement;

import dao.UsuarioDao;
import model.Usuario;
import model.UsuarioList;

@Path("/usuarios")
public class UsuarioListResource {

	@GET
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public UsuarioList getTodos() {
		List<Usuario> usuarios = new ArrayList<Usuario>();
		usuarios.addAll(UsuarioDao.getInstance().getModel().values());
		return new UsuarioList(usuarios);
	}

	@POST
	@Produces(MediaType.TEXT_HTML)
	@Consumes(MediaType.APPLICATION_ATOM_XML)
	public void newUser(JAXBElement<Usuario> usuario) throws IOException {
		Usuario u = usuario.getValue();
		UsuarioDao.getInstance().getModel().put(u.getId(), u);

		System.out.println(UsuarioDao.getInstance().getModel().get(u.getId()));

		// TODO
		// servletResponse.sendRedirect("../create_todo.html");
	}

}
