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

@Path("/Cliente")
public class ClienteResource {
    // Por alguna razon del destino no me anda con una sola anotacion para ambos
    // injects.
    @Inject
    public ClienteService service;
    @Inject
    public TipoDocumentoService docuService;

    @GET
    public List<Cliente> listar() {
        return this.service.listar();
    }

    @DELETE
    @Path("{id}")
    public void eliminar(Integer id) {
        this.service.eliminar(id);
    }

    @PUT
    public Cliente modificar(Cliente param) {
        return this.service.modificar(param);
    }

    @GET
    @Path("{id}")
    public Cliente obtener(@PathParam("id") Integer param) {
        return this.service.obtener(param);
    }

    @POST
    @Path("/agregar/{nombre}/{apellido}/{documento}/{codDocu}/{clienteFiel}")
    public Response agregarNuevoCliente(@PathParam("nombre") String nombre, @PathParam("apellido") String apellido,
            @PathParam("documento") String documento, @PathParam("codDocu") Integer codDocu,
            @PathParam("clienteFiel") String clienteFiel) {
        TipoDocumento tipoDocu = this.docuService.obtener(codDocu);
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
