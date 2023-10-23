package py.edu.ucom.natasha.controllers;

import java.util.List;

import jakarta.inject.Inject;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import py.edu.ucom.natasha.entities.Venta;
import py.edu.ucom.natasha.entities.dto.ResumenVentaDTO;
import py.edu.ucom.natasha.services.VentaService;

@Path("/Venta")
public class VentaResource {
    @Inject
    public VentaService service;

    @GET
    public List<Venta> listar() {
        return this.service.listar();
    }

    @DELETE
    @Path("{id}")
    public void eliminar(Integer id) {
        this.service.eliminar(id);
    }

    @POST
    public Venta agregar(Venta param) {
        return this.service.agregar(param);
    }

    @PUT
    public Venta modificar(Venta param) {
        return this.service.modificar(param);
    }

    @GET
    @Path("{id}")
    public Venta obtener(@PathParam("id") Integer param) {
        return this.service.obtener(param);
    }

    @GET
    @Path("resumen/{id}")
    public ResumenVentaDTO obtenerResumen(@PathParam("id") Integer param) {
        return this.service.obtenerResumen(param);
    }

}