package py.edu.ucom.natasha.controllers;

import java.util.List;

import jakarta.inject.Inject;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import py.edu.ucom.natasha.entities.Cliente;
import py.edu.ucom.natasha.services.ClienteService;

@Path("/Cliente")
public class ClienteResource {
    @Inject
    public ClienteService service;

    @GET
    public List<Cliente> listar() {
        return this.service.listar();
    }

    @DELETE
    @Path("{id}")
    public void eliminar(Integer id) {
        this.service.eliminar(id);
    }

    @POST
    public Cliente agregar(Cliente param) {
        return this.service.agregar(param);
    }

    @PUT
    public Cliente modificar(Cliente param) {
        return this.service.modificar(param);
    }

    @GET
    @Path("{id}")
    public Cliente obtener(@PathParam("id") Integer param) {
        return this.service.obtener(param);
    }

}
