package py.edu.ucom.natasha.controllers;

import java.util.List;

import jakarta.inject.Inject;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import py.edu.ucom.natasha.entities.MetodoPago;
import py.edu.ucom.natasha.services.MetodoPagoService;

@Path("/metodo-pago")
public class MetodoPagoResource {
    @Inject
    public MetodoPagoService service;

    @GET
    public List<MetodoPago> listar() {
        return this.service.listar();
    }

    @DELETE
    @Path("{id}")
    public void eliminar(Integer id) {
        this.service.eliminar(id);
    }

    @POST
    public MetodoPago agregar(MetodoPago param) {
        return this.service.agregar(param);
    }

    @PUT
    public MetodoPago modificar(MetodoPago param) {
        return this.service.modificar(param);
    }

    @GET
    @Path("{id}")
    public MetodoPago obtener(@PathParam("id") Integer param) {
        return this.service.obtener(param);
    }

}
