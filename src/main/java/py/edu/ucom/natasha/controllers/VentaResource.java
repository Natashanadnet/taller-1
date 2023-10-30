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
    @Operation(summary = "Listar ventas", description = "Lista todas las ventas")
    public Response listar() {
        List<Venta> lista = this.service.listar();
        if (lista != null) {
            return Response.status(Response.Status.OK).entity(lista).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).entity(Globales.CRUD.LISTADO_ERR).build();
        }
    }

    @DELETE
    @Operation(summary = "Eliminar venta", description = "Elimina la venta seleccionada por su ID")
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
    @Operation(summary = "Modificar venta", description = "Modifica la venta seleccionada por su ID")
    @Path("modificar/{ventaId}")
    public Response modificarVenta(@PathParam("ventaId") Integer ventaId,
            @QueryParam("clienteId") Integer clienteId, @QueryParam("metodoPagoId") Integer metodoPagoId) {
        Venta venta = this.service.obtener(ventaId);
        if (venta == null) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(Globales.CRUD.MODIFICADO_ERR)
                    .build();
        }

        Cliente cliente = this.clieService.obtener(clienteId);
        if (cliente != null) {
            venta.setClienteId(cliente);
        }
        MetodoPago metpago = this.metPagoService.obtener(metodoPagoId);
        if (metpago != null) {
            venta.setMetodoPagoId(metpago);
        }
        try {
            this.service.modificar(venta);
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
    @Operation(summary = "Obtener venta", description = "Busca la venta seleccionada por su ID")
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
    @Operation(summary = "Obtener resumen de venta", description = "Muestra un resumen de la venta seleccionada por su ID")
    @Path("resumen/{id}")
    public ResumenVentaDTO obtenerResumen(@PathParam("id") Integer param) {
        return this.service.obtenerResumen(param);
    }

    @GET
    @Operation(summary = "Listar detalles de venta", description = "Lista todos los detalles de venta relacionados a la venta seleccionada por su ID")
    @Path("listaDetalle/{id}")
    public List<VentaDetalle> listarById(Integer id) {
        Venta venta = this.service.obtener(id);
        return venta.getVentaDetalleList();
    }

    @POST
    @Operation(summary = "Crear venta", description = "Se crea una nueva venta en la base de datos")
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

}