package py.edu.ucom.natasha.controllers;

import java.util.List;

import jakarta.inject.Inject;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.core.Response;
import py.edu.ucom.natasha.config.Globales;
import py.edu.ucom.natasha.entities.Producto;
import py.edu.ucom.natasha.entities.Venta;
import py.edu.ucom.natasha.entities.VentaDetalle;
import py.edu.ucom.natasha.services.ProductoService;
import py.edu.ucom.natasha.services.VentaDetalleService;
import py.edu.ucom.natasha.services.VentaService;

@Path("/VentaDetalle")
public class VentaDetalleResource {
    @Inject
    public VentaDetalleService service;
    @Inject
    public VentaService ventaService;
    @Inject
    public ProductoService productoService;

    @GET
    public List<VentaDetalle> listar() {
        return this.service.listar();
    }

    @DELETE
    @Path("{id}")
    public void eliminar(Integer id) {
        this.service.eliminar(id);
    }

    @POST
    public VentaDetalle agregar(VentaDetalle param) {
        return this.service.agregar(param);
    }

    @PUT
    public VentaDetalle modificar(VentaDetalle param) {
        return this.service.modificar(param);
    }

    @GET
    @Path("{id}")
    public VentaDetalle obtener(@PathParam("id") Integer param) {
        return this.service.obtener(param);
    }

    @POST
    @Path("/agregar/detalle/{ventaId}/{productoId}/{cantidad}")
    public Response crearDetalle(@PathParam("ventaId") Integer ventaId,
            @PathParam("productoId") Integer productoId, @PathParam("cantidad") Integer cantidad) {
        Venta venta = this.ventaService.obtener(ventaId);
        Producto producto = this.productoService.obtener(productoId);

        if (venta == null || producto == null) {
            return Response.status(Response.Status.NOT_FOUND).entity(Globales.CRUD.CREADO_ERR).build();
        }

        if (!this.productoService.restarStock(cantidad, producto)) {
            return Response.status(Response.Status.NOT_FOUND).entity("El producto no se encuentra disponible").build();
        }

        VentaDetalle detalle = this.service.crearVentaDetalle(venta, producto, cantidad);

        if (detalle == null) {
            return Response.status(Response.Status.NOT_FOUND).entity(Globales.CRUD.CREADO_ERR).build();
        }

        // Se modifica el total de la venta:
        int total = 0;
        List<VentaDetalle> lista = venta.getVentaDetalleList();
        for (VentaDetalle item : lista) {
            total += item.getSubtotal();
        }

        venta.setTotal(total);
        this.ventaService.modificar(venta);
        return Response.status(Response.Status.OK).entity(Globales.CRUD.CREADO_OK).build();
    }

}
