package py.edu.ucom.natasha.controllers;

import java.util.List;

import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import py.edu.ucom.natasha.entities.Usuarios;
import py.edu.ucom.natasha.utils.DataSourceJSON;

@Path("/usuarios")
public class UsuariosResource {
    @Inject
    public DataSourceJSON ds;

    @GET
    @Path("{documento}")
    public Usuarios obtenerUsuarioByDocumento(@PathParam("documento") String param) {
        return this.ds.buscarUsuario(param);

    }

    @GET
    public List<Usuarios> obtener() {
        List<Usuarios> lista = this.ds.obtenerUsuarios();
        return lista;
    }

    @POST
    public void guardar(Usuarios usuario) {
        this.ds.guardarUsuarios(usuario);
    }

}
