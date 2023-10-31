package py.edu.ucom.natasha.controllers;

import java.util.List;

import org.eclipse.microprofile.openapi.annotations.Operation;

import jakarta.inject.Inject;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.Response;
import py.edu.ucom.natasha.entities.VentaDetalle;

import py.edu.ucom.natasha.services.VentaDetalleService;

@Path("/VentaDetalle")
public class VentaDetalleResource {
    @Inject
    public VentaDetalleService service;

    @GET
    @Operation(summary = "Listar los detalles de venta", description = "Lista todos los detalles de venta")
    public List<VentaDetalle> listar() {
        return this.service.listar();
    }

    @DELETE
    @Operation(summary = "Eliminar el detalle de venta", description = "Elimina el detalle de venta seleccionado por su ID y actualiza el precio total de la venta")
    @Path("{id}")
    public Response eliminar(Integer id) {
        return this.service.eliminarDetalle(id);
    }

    @PUT
    @Operation(summary = "Modificar detalle de venta", description = "Modifica el detalle de venta seleccionado por su ID, los cambios actualizan el stock")
    @Path("modificar/{detalleId}")
    public Response modificar(@PathParam("detalleId") Integer detalleId,
            @QueryParam("productoId") Integer productoId, @QueryParam("cantidad") Integer cantidad) {
        return this.service.modificarDetalle(detalleId, cantidad, productoId);
    }

    @GET
    @Operation(summary = "Obtener detalle de venta", description = "Busca el detalle de venta por su ID")
    @Path("{id}")
    public VentaDetalle obtener(@PathParam("id") Integer param) {
        return this.service.obtener(param);
    }

    @POST
    @Operation(summary = "Agregar nuevo detalle de venta", description = "Agrega un nuevo detalle de venta a una venta existente")
    @Path("/agregar/detalle/{ventaId}/{productoId}/{cantidad}")
    public Response crearDetalle(@PathParam("ventaId") Integer ventaId,
            @PathParam("productoId") Integer productoId, @PathParam("cantidad") Integer cantidad) {
        return this.service.agregarDetalle(ventaId, productoId, cantidad);
    }

}
