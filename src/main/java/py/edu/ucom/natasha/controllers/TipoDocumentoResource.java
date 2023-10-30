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
import py.edu.ucom.natasha.entities.TipoDocumento;
import py.edu.ucom.natasha.services.TipoDocumentoService;

@Path("/TipoDocumento")
public class TipoDocumentoResource {
    @Inject
    public TipoDocumentoService service;

    @GET
    @Operation(summary = "Listar tipos de documento", description = "Lista todos los tipos de documento")
    public List<TipoDocumento> listar() {
        return this.service.listar();
    }

    @DELETE
    @Operation(summary = "Eliminar tipo de documento", description = "Elimina el tipo de documento por ID")
    @Path("{id}")
    public void eliminar(Integer id) {
        this.service.eliminar(id);
    }

    @POST
    @Operation(summary = "Agregar tipo de documento", description = "Agrega nuevo tipo de documento a la base de datos")
    public TipoDocumento agregar(TipoDocumento param) {
        return this.service.agregar(param);
    }

    @PUT
    @Operation(summary = "Modificar tipo de documento", description = "Modifica el tipo de documento")
    public TipoDocumento modificar(TipoDocumento param) {
        return this.service.modificar(param);
    }

    @GET
    @Operation(summary = "Obtener tipo de documento", description = "Busca el tipo de documento por ID")
    @Path("{id}")
    public TipoDocumento obtener(@PathParam("id") Integer param) {
        return this.service.obtener(param);
    }

}
