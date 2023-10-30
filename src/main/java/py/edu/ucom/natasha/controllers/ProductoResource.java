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
import py.edu.ucom.natasha.entities.Producto;
import py.edu.ucom.natasha.services.ProductoService;
import py.edu.ucom.natasha.util.CaracteresUtil;

@Path("/producto")
public class ProductoResource {
    @Inject
    public ProductoService service;

    @GET
    @Operation(summary = "Listar productos", description = "Lista todos los productos")
    public Response listar() {
        List<Producto> lista = this.service.listar();
        if (lista != null) {
            return Response.status(Response.Status.OK).entity(lista).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).entity(Globales.CRUD.LISTADO_ERR).build();
        }
    }

    @DELETE
    @Operation(summary = "Eliminar el producto", description = "Elimina el producto seleccionado por su ID")
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
    @Operation(summary = "Modificar producto", description = "Modifica el producto seleccionado por su ID")
    @Path("/modificar/{id}")
    public Response modificarProducto(@PathParam("id") Integer productoId, @QueryParam("codigo") String codigo,
            @QueryParam("stock") Integer stock, @QueryParam("descripcion") String descripcion,
            @QueryParam("precioUnitario") Integer precioUnitario) {
        Producto producto = this.service.obtener(productoId);
        if (producto == null) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(Globales.CRUD.MODIFICADO_ERR)
                    .build();
        }
        if (codigo != null) {
            producto.setCodigo(codigo);
        }
        if (stock != null && stock >= 0) {
            producto.setStock(stock);
        }
        if (descripcion != null) {
            producto.setDescripcion(descripcion);
        }
        if (precioUnitario != null && precioUnitario >= 0) {
            producto.setPrecioUnitario(precioUnitario);
        }
        try {
            this.service.modificar(producto);
            return Response.status(Response.Status.OK)
                    .entity(Globales.CRUD.MODIFICADO_OK)
                    .build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(Globales.CRUD.MODIFICADO_ERR)
                    .build();
        }
    }

    @PUT
    @Operation(summary = "Restar Stock", description = "Resta la cantidad ingresada del stock actual")
    @Path("/stock/restar/{prodId}/{cantidad}")
    public Response restarStock(@PathParam("prodId") Integer productoId, @PathParam("cantidad") Integer cantidad) {
        Producto producto = this.service.obtener(productoId);
        if (!this.service.restarStock(cantidad, producto) || producto == null) {
            return Response.status(Response.Status.NOT_FOUND).entity(Globales.CRUD.MODIFICADO_ERR).build();
        }
        return Response.status(Response.Status.OK).entity(Globales.CRUD.MODIFICADO_OK).build();
    }

    @PUT
    @Operation(summary = "Sumar stock", description = "Suma la cantidad ingresada al stock actual")
    @Path("/stock/sumar/{prodId}/{cantidad}")
    public Response sumarStock(@PathParam("prodId") Integer productoId, @PathParam("cantidad") Integer cantidad) {
        Producto producto = this.service.obtener(productoId);
        if (producto == null) {
            return Response.status(Response.Status.NOT_FOUND).entity(Globales.CRUD.MODIFICADO_ERR).build();
        }
        this.service.sumarStock(cantidad, producto);
        return Response.status(Response.Status.OK).entity(Globales.CRUD.MODIFICADO_OK).build();
    }

    @GET
    @Operation(summary = "Obtener producto", description = "Obtiene el producto por su ID")
    @Path("{id}")
    public Response obtener(@PathParam("id") Integer param) {
        Producto producto = this.service.obtener(param);
        if (producto != null) {
            return Response.status(Response.Status.OK).entity(producto).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).entity(Globales.CRUD.RECUPERADO_ERR).build();
        }
    }

    @POST
    @Operation(summary = "Agregar producto", description = "Agrega nuevo producto a la base de datos")
    @Path("/agregar/{codigo}/{stock}/{descripcion}/{precioUnitario}")
    public Response agregarProducto(@PathParam("codigo") String codigo, @PathParam("stock") Integer stock,
            @PathParam("descripcion") String descripcion, @PathParam("precioUnitario") Integer precioUnitario) {
        Producto producto = new Producto();
        producto.setCodigo(codigo);
        producto.setStock(stock);
        producto.setDescripcion(CaracteresUtil.limpiarYCapitalizar(descripcion));
        producto.setPrecioUnitario(precioUnitario);
        try {
            this.service.agregar(producto);
            return Response.status(Response.Status.OK)
                    .entity(Globales.CRUD.CREADO_OK)
                    .build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(Globales.CRUD.CREADO_ERR)
                    .build();
        }

    }

}