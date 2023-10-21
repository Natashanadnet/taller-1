package py.edu.ucom.natasha.controllers;

import java.util.List;

import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import py.edu.ucom.natasha.entities.Cliente;
import py.edu.ucom.natasha.services.ClienteService;

@Path("/cliente")
public class ClienteResource {
    @Inject
    private ClienteService service;

    @GET
    public List<Cliente> listar() {
        return service.listar();
    }

}
