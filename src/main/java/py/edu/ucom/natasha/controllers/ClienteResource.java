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
import py.edu.ucom.natasha.entities.TipoDocumento;
import py.edu.ucom.natasha.entities.Venta;
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
        Cliente cliente = this.service.obtener(id);
        List<Venta> ventas = cliente.getVentaList();
        if (ventas.isEmpty()) {
            this.service.eliminar(id);
            return Response.status(Response.Status.OK)
                    .entity(Globales.CRUD.ELIMINADO_OK)
                    .build();
        }

        return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                .entity(Globales.CRUD.ELIMINADO_ERR)
                .build();
    }

    @PUT
    @Operation(summary = "Modificar cliente", description = "Modifica el cliente seleccionado")
    @Path("/modificar/{id}")
    public Response modificarCliente(@PathParam("id") Integer clienteId, @QueryParam("nombre") String nombre,
            @QueryParam("apellido") String apellido, @QueryParam("documento") String documento,
            @QueryParam("codDocu") Integer codDocu, @QueryParam("clienteFiel") String clienteFiel) {
        Cliente cliente = this.service.obtener(clienteId);

        if (cliente == null) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(Globales.CRUD.MODIFICADO_ERR)
                    .build();
        }

        if (nombre != null) {
            cliente.setNombres(CaracteresUtil.limpiarYCapitalizar(nombre));
        }

        if (apellido != null) {
            cliente.setApellidos(CaracteresUtil.limpiarYCapitalizar(apellido));
        }

        if (documento != null) {
            cliente.setDocumento(documento);
        }

        if (codDocu != null) {
            TipoDocumento tipoDocu = this.docuService.obtener(codDocu);
            if (tipoDocu == null) {
                return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                        .entity(Globales.CRUD.MODIFICADO_ERR)
                        .build();
            }
            cliente.setTipoDocumentoId(tipoDocu);
        }

        if (clienteFiel != null) {
            cliente.setEsClienteFiel(Boolean.parseBoolean(clienteFiel));
        }

        try {
            this.service.modificar(cliente);
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
