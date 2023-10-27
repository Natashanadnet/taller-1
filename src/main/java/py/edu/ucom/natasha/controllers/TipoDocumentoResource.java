package py.edu.ucom.natasha.controllers;

import java.util.List;

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
    public List<TipoDocumento> listar() {
        return this.service.listar();
    }

    @DELETE
    @Path("{id}")
    public void eliminar(Integer id) {
        this.service.eliminar(id);
    }

    @POST
    public TipoDocumento agregar(TipoDocumento param) {
        return this.service.agregar(param);
    }

    @PUT
    public TipoDocumento modificar(TipoDocumento param) {
        return this.service.modificar(param);
    }

    @GET
    @Path("{id}")
    public TipoDocumento obtener(@PathParam("id") Integer param) {
        return this.service.obtener(param);
    }

}
