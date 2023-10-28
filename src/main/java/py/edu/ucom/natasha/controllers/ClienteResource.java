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
import py.edu.ucom.natasha.entities.TipoDocumento;
import py.edu.ucom.natasha.services.ClienteService;
import py.edu.ucom.natasha.services.TipoDocumentoService;
import py.edu.ucom.natasha.util.CaracteresUtil;

@Path("/Cliente")
public class ClienteResource {
    // Por alguna razon del destino no me anda con una sola anotacion para ambos
    // injects.
    @Inject
    public ClienteService service;
    @Inject
    public TipoDocumentoService docuService;

    @GET
    public Response listar() {
        List<Cliente> lista = this.service.listar();
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
    public Response modificar(Cliente param) {
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
        Cliente cliente = this.service.obtener(param);
        if (cliente != null) {
            return Response.status(Response.Status.OK).entity(cliente).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).entity(Globales.CRUD.RECUPERADO_ERR).build();
        }
    }

    @POST
    @Path("/agregar/{nombre}/{apellido}/{documento}/{codDocu}/{clienteFiel}")
    public Response agregarNuevoCliente(@PathParam("nombre") String nombre, @PathParam("apellido") String apellido,
            @PathParam("documento") String documento, @PathParam("codDocu") Integer codDocu,
            @PathParam("clienteFiel") String clienteFiel) {
        nombre = CaracteresUtil.limpiarYCapitalizar(nombre);
        apellido = CaracteresUtil.limpiarYCapitalizar(apellido);
        // Validacion de clienteFiel:
        if (!clienteFiel.equals("true") && !clienteFiel.equals("false")) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("El valor de 'clienteFiel' debe ser 'true' o 'false'")
                    .build();
        }
        TipoDocumento tipoDocu = this.docuService.obtener(codDocu);

        // Validacion de tipoDocu:
        if (tipoDocu == null) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("Tipo de documento no encontrado")
                    .build();
        }

        Cliente cliente = new Cliente();
        cliente.setNombres(nombre);
        cliente.setApellidos(apellido);
        cliente.setDocumento(documento);
        cliente.setTipoDocumentoId(tipoDocu);
        cliente.setEsClienteFiel(Boolean.parseBoolean(clienteFiel));

        try {
            this.service.agregar(cliente);
            return Response.status(Response.Status.OK)
                    .entity(Globales.CRUD.CREADO_OK)
                    .build();
        } catch (Exception e) {
            // Manejar la excepción de persistencia
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(Globales.CRUD.CREADO_ERR)
                    .build();
        }
    }

}
