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
import py.edu.ucom.natasha.services.ClienteService;

@Path("/Cliente")
public class ClienteResource {
    // Por alguna razon del destino no me anda con una sola anotacion para ambos
    // injects.
    @Inject
    public ClienteService service;

    @GET
    @Operation(summary = "Listar clientes", description = "Obtener lista de todos los clientes")
    public Response listar() {
        List<Cliente> lista = this.service.listar();
        if (lista != null) {
            return Response.status(Response.Status.OK).entity(lista).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).entity(Globales.CRUD.LISTADO_ERR).build();
        }

    }

    @DELETE
    @Operation(summary = "Eliminar cliente", description = "Elimina el cliente seleccionado si no tiene ventas")
    @Path("{id}")
    public Response eliminar(Integer id) {
        return this.service.eliminarCliente(id);
    }

    @PUT
    @Operation(summary = "Modificar cliente", description = "Modifica el cliente seleccionado")
    @Path("/modificar/{id}")
    public Response modificar(@PathParam("id") Integer clienteId, @QueryParam("nombre") String nombre,
            @QueryParam("apellido") String apellido, @QueryParam("documento") String documento,
            @QueryParam("codDocu") Integer codDocu, @QueryParam("clienteFiel") String clienteFiel) {
        return this.service.modificarCliente(clienteId, nombre, apellido, documento, codDocu, clienteFiel);
    }

    @GET
    @Operation(summary = "Buscar cliente", description = "Busca el cliente por su ID")
    @Path("{id}")
    public Response obtener(@PathParam("id") Integer param) {
        Cliente cliente = this.service.obtener(param);
        if (cliente != null) {
            return Response.status(Response.Status.OK).entity(cliente).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).entity(Globales.CRUD.RECUPERADO_ERR).build();
        }
    }

    @POST
    @Operation(summary = "Crear cliente", description = "Agrega nuevo cliente a la base de datos")
    @Path("/agregar/{nombre}/{apellido}/{documento}/{codDocu}/{clienteFiel}")
    public Response agregarNuevoCliente(@PathParam("nombre") String nombre, @PathParam("apellido") String apellido,
            @PathParam("documento") String documento, @PathParam("codDocu") Integer codDocu,
            @PathParam("clienteFiel") String clienteFiel) {
        return this.service.nuevoCliente(nombre, apellido, documento, codDocu, clienteFiel);
    }

}
