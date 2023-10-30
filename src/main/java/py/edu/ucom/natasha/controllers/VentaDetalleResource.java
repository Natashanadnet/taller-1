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
    @Operation(summary = "Listar los detalles de venta", description = "Lista todos los detalles de venta")
    public List<VentaDetalle> listar() {
        return this.service.listar();
    }

    @DELETE
    @Operation(summary = "Eliminar el detalle de venta", description = "Elimina el detalle de venta seleccionado por su ID y actualiza el precio total de la venta")
    @Path("{id}")
    public Response eliminar(Integer id) {
        VentaDetalle detalle = this.service.obtener(id);
        if (detalle == null) {
            return Response.status(Response.Status.NOT_FOUND).entity(Globales.CRUD.ELIMINADO_ERR).build();
        }
        Venta venta = detalle.getVentaId();
        try {
            this.service.eliminar(id);
            List<VentaDetalle> listaDet = venta.getVentaDetalleList();
            int total = 0;
            for (VentaDetalle item : listaDet) {
                total += item.getSubtotal();
            }
            venta.setTotal(total);
            this.ventaService.modificar(venta);
            return Response.status(Response.Status.OK)
                    .entity(Globales.CRUD.ELIMINADO_OK)
                    .build();
        } catch (Exception e) {
            // Manejar la excepción de persistencia
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(Globales.CRUD.ELIMINADO_ERR)
                    .build();
        }

    }

    @POST
    @Operation(summary = "Crear detalle de venta", description = "Agrega nuevo detalle de venta a la base de datos")
    public VentaDetalle agregar(VentaDetalle param) {
        return this.service.agregar(param);
    }

    @PUT
    @Operation(summary = "Modificar detalle de venta", description = "Modifica el detalle de venta")
    public VentaDetalle modificar(VentaDetalle param) {
        return this.service.modificar(param);
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
