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
import py.edu.ucom.natasha.entities.MetodoPago;
import py.edu.ucom.natasha.entities.params.ObtenerDatosError;
import py.edu.ucom.natasha.entities.params.RespuestaLista;
import py.edu.ucom.natasha.services.MetodoPagoService;

@Path("/metodo-pago")
public class MetodoPagoResource {
    @Inject
    public MetodoPagoService service;

    @GET
    public RespuestaLista<MetodoPago> listar() {
        RespuestaLista<MetodoPago> respuesta = new RespuestaLista<>(this.service.listar());
        respuesta.setMensaje(Globales.CRUD.LISTADO_OK);
        return respuesta;
    }

    @DELETE
    @Path("{id}")
    public void eliminar(Integer id) {
        this.service.eliminar(id);
    }

    @POST
    public MetodoPago agregar(MetodoPago param) {
        return this.service.agregar(param);
    }

    @PUT
    public MetodoPago modificar(MetodoPago param) {
        return this.service.modificar(param);
    }

    @GET
    @Path("{id}")
    public Response obtener(@PathParam("id") Integer param) throws Exception {
        MetodoPago entity = this.service.obtener(param);
        if (entity == null) {
            ObtenerDatosError data = new ObtenerDatosError();
            data.setCodigo("0001");
            data.setMensaje(Globales.CRUD.RECUPERADO_ERR);
            return Response.status(Response.Status.NOT_FOUND).entity(data).build();
        }
        return Response.ok(entity).build();
    }

    @GET
    @Path("/codigo/{cod}")
    public Response buscarPorCodigo(@PathParam("cod") String param) throws Exception {
        List<MetodoPago> entity = this.service.buscarPorCodigo(param);

        return Response.ok(entity).build();
    }

    @GET
    @Path("/sum/")
    public Response sumaId() throws Exception {
        Long entity = this.service.sumIds();

        return Response.ok(entity).build();
    }

    // @GET
    // @Path("/paginado/{pagina}/{cantidad}")
    // public Response paginado(@PathParam("pagina") Integer pagina,
    // @PathParam("cantidad") Integer cantidad)
    // throws Exception {
    // Long entity = this.service.sumIds();

    // return Response.ok(entity).build();
    // }

}
