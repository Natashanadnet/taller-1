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
import py.edu.ucom.natasha.entities.Cliente;
import py.edu.ucom.natasha.entities.MetodoPago;
import py.edu.ucom.natasha.entities.Producto;
import py.edu.ucom.natasha.entities.Venta;
import py.edu.ucom.natasha.entities.VentaDetalle;
import py.edu.ucom.natasha.entities.dto.ResumenVentaDTO;
import py.edu.ucom.natasha.services.ClienteService;
import py.edu.ucom.natasha.services.MetodoPagoService;
import py.edu.ucom.natasha.services.ProductoService;
import py.edu.ucom.natasha.services.VentaDetalleService;
import py.edu.ucom.natasha.services.VentaService;

@Path("/Venta")
public class VentaResource {
    @Inject
    public VentaService service;
    @Inject
    public ClienteService clieService;
    @Inject
    public MetodoPagoService metPagoService;
    @Inject
    public ProductoService produService;
    @Inject
    public VentaDetalleService detalleService;

    @GET
    public Response listar() {
        List<Venta> lista = this.service.listar();
        if (lista != null) {
            return Response.status(Response.Status.OK).entity(lista).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).entity(Globales.CRUD.LISTADO_ERR).build();
        }
    }

    @DELETE
    @Path("{id}")
    public Response eliminar(Integer id) {
        try {
            this.service.eliminar(id);
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

    @PUT
    public Response modificar(Venta param) {
        try {
            this.service.modificar(param);
            return Response.status(Response.Status.OK)
                    .entity(Globales.CRUD.MODIFICADO_OK)
                    .build();
        } catch (Exception e) {
            // Manejar la excepción de persistencia
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(Globales.CRUD.MODIFICADO_ERR)
                    .build();
        }
    }

    @GET
    @Path("{id}")
    public Response obtener(@PathParam("id") Integer param) {
        Venta venta = this.service.obtener(param);
        if (venta != null) {
            return Response.status(Response.Status.OK).entity(venta).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).entity(Globales.CRUD.RECUPERADO_ERR).build();
        }
    }

    @GET
    @Path("resumen/{id}")
    public ResumenVentaDTO obtenerResumen(@PathParam("id") Integer param) {
        return this.service.obtenerResumen(param);
    }

    @GET
    @Path("listaDetalle/{id}")
    public List<VentaDetalle> listarById(Integer id) {
        Venta venta = this.service.obtener(id);
        return venta.getVentaDetalleList();
    }

    @POST
    @Path("/agregar/venta/{clienteId}/{metodoPagoId}/{productoId}/{cantidad}")
    public Response crearVenta(@PathParam("clienteId") Integer clienteId,
            @PathParam("metodoPagoId") Integer metodoPagoId,
            @PathParam("productoId") Integer productoId, @PathParam("cantidad") Integer cantidad) {
        Cliente cliente = this.clieService.obtener(clienteId);
        MetodoPago metPago = this.metPagoService.obtener(metodoPagoId);
        Producto producto = this.produService.obtener(productoId);

        if (cliente == null || metPago == null || producto == null) {
            return Response.status(Response.Status.NOT_FOUND).entity(Globales.CRUD.CREADO_ERR).build();
        }

        if (!this.produService.restarStock(cantidad, producto)) {
            return Response.status(Response.Status.NOT_FOUND).entity("El producto no se encuentra disponible").build();
        }
        Venta venta = this.service.crearVenta(cliente, metPago);
        VentaDetalle detalle = this.detalleService.crearVentaDetalle(venta, producto, cantidad);

        if (venta == null || detalle == null) {
            return Response.status(Response.Status.NOT_FOUND).entity(Globales.CRUD.CREADO_ERR).build();
        }
        venta.setTotal(detalle.getSubtotal());
        this.service.modificar(venta);
        return Response.status(Response.Status.OK).entity(Globales.CRUD.CREADO_OK).build();
    }

    @POST
    @Path("/agregar/detalle/{ventaId}/{productoId}/{cantidad}")
    public Response crearDetalle(@PathParam("ventaId") Integer ventaId,
            @PathParam("productoId") Integer productoId, @PathParam("cantidad") Integer cantidad) {
        Venta venta = this.service.obtener(ventaId);
        Producto producto = this.produService.obtener(productoId);

        if (venta == null || producto == null) {
            return Response.status(Response.Status.NOT_FOUND).entity(Globales.CRUD.CREADO_ERR).build();
        }

        if (!this.produService.restarStock(cantidad, producto)) {
            return Response.status(Response.Status.NOT_FOUND).entity("El producto no se encuentra disponible").build();
        }

        VentaDetalle detalle = this.detalleService.crearVentaDetalle(venta, producto, cantidad);

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
        this.service.modificar(venta);
        return Response.status(Response.Status.OK).entity(Globales.CRUD.CREADO_OK).build();
    }

}