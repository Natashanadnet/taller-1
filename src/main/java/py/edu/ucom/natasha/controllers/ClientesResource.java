package py.edu.ucom.natasha.controllers;

import java.util.List;

import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import py.edu.ucom.natasha.entities.Clientes;
import py.edu.ucom.natasha.utils.DataSourceJSON;

@Path("/clientes")
public class ClientesResource {
    @Inject
    public DataSourceJSON ds;

    @GET
    @Path("{documento}")
    public Clientes obtenerClientePorDocumento(@PathParam("documento") String param) {
        return this.ds.buscarCliente(param);

    }

    @GET
    public List<Clientes> obtenerClientes() {
        List<Clientes> lista = this.ds.obtenerClientes();
        return lista;
    }

    @POST
    public void guardarCliente(Clientes cliente) {
        try {
            this.ds.guardarClientes(cliente);
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }

    }
}
