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
import py.edu.ucom.natasha.services.ProductoService;

@Path("/producto")
public class ProductoResource {
    @Inject
    public ProductoService service;

    @GET
    public Response listar() {
        List<Producto> lista = this.service.listar();
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
    public Response modificar(Producto param) {
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

    @PUT
    @Path("/stock/restar/{prodId}/{cantidad}")
    public Response restarStock(@PathParam("prodId") Integer productoId, @PathParam("cantidad") Integer cantidad) {
        Producto producto = this.service.obtener(productoId);
        if (!this.service.restarStock(cantidad, producto) || producto == null) {
            return Response.status(Response.Status.NOT_FOUND).entity(Globales.CRUD.MODIFICADO_ERR).build();
        }
        return Response.status(Response.Status.OK).entity(Globales.CRUD.MODIFICADO_OK).build();
    }

    @PUT
    @Path("/stock/sumar/{prodId}/{cantidad}")
    public Response sumarStock(@PathParam("prodId") Integer productoId, @PathParam("cantidad") Integer cantidad) {
        Producto producto = this.service.obtener(productoId);
        if (producto == null) {
            return Response.status(Response.Status.NOT_FOUND).entity(Globales.CRUD.MODIFICADO_ERR).build();
        }
        this.service.sumarStock(cantidad, producto);
        return Response.status(Response.Status.OK).entity(Globales.CRUD.MODIFICADO_OK).build();
    }

    @PUT
    @Path("/stock/cambiar/{prodId}/{cantidad}")
    public Response cambiarStock(@PathParam("prodId") Integer productoId, @PathParam("cantidad") Integer cantidad) {
        Producto producto = this.service.obtener(productoId);
        if (cantidad < 0 || producto == null) {
            return Response.status(Response.Status.NOT_FOUND).entity(Globales.CRUD.MODIFICADO_ERR).build();
        }
        this.service.cambiarStock(cantidad, producto);
        return Response.status(Response.Status.OK).entity(Globales.CRUD.MODIFICADO_OK).build();
    }

    @GET
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
    public Response agregar(Producto param) {
        Producto producto = this.service.agregar(param);
        if (producto != null) {
            return Response.status(Response.Status.OK).entity(producto).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).entity(Globales.CRUD.CREADO_ERR).build();
        }
    }

}