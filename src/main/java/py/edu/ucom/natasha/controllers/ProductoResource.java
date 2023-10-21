package py.edu.ucom.natasha.controllers;

import java.util.List;

import jakarta.inject.Inject;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import py.edu.ucom.natasha.entities.Producto;
import py.edu.ucom.natasha.services.ProductoService;

@Path("/producto")
public class ProductoResource {
    @Inject
    public ProductoService service;

    @GET
    public List<Producto> listar() {
        return this.service.listar();
    }

    @DELETE
    @Path("{id}")
    public void eliminar(Integer id) {
        this.service.eliminar(id);
    }

    @POST
    public Producto agregar(Producto param) {
        return this.service.agregar(param);
    }

    @PUT
    public Producto modificar(Producto param) {
        return this.service.modificar(param);
    }

    @GET
    @Path("{id}")
    public Producto obtener(@PathParam("id") Integer param) {
        return this.service.obtener(param);
    }

}