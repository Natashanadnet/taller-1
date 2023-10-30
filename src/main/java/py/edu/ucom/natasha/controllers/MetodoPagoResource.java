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
import py.edu.ucom.natasha.entities.MetodoPago;
import py.edu.ucom.natasha.entities.params.ObtenerDatosError;
import py.edu.ucom.natasha.entities.params.RespuestaLista;
import py.edu.ucom.natasha.services.MetodoPagoService;

@Path("/metodo-pago")
public class MetodoPagoResource {
    @Inject
    public MetodoPagoService service;

    @GET
    @Operation(summary = "Listar metodos de pago", description = "Muestra todos los metodos de pagos")
    public RespuestaLista<MetodoPago> listar() {
        RespuestaLista<MetodoPago> respuesta = new RespuestaLista<>(this.service.listar());
        respuesta.setMensaje(Globales.CRUD.LISTADO_OK);
        return respuesta;
    }

    @DELETE
    @Operation(summary = "Eliminar metodo de pago", description = "Elimina el metodo de pago seleccionado por su ID")
    @Path("{id}")
    public void eliminar(Integer id) {
        this.service.eliminar(id);
    }

    @POST
    @Operation(summary = "Agregar metodo de pago", description = "Crea nuevo metodo de pago")
    public MetodoPago agregar(MetodoPago param) {
        return this.service.agregar(param);
    }

    @PUT
    @Operation(summary = "Modificar metodo de pago", description = "Modifica metodo de pago seleccionado por su ID")
    @Path("/modificar/{id}")
    public Response modificarMetodoPago(@PathParam("id") Integer id, @QueryParam("codigo") String codigo,
            @QueryParam("descripcion") String descripcion) {
        MetodoPago metodoPago = this.service.obtener(id);
        if (metodoPago == null) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(Globales.CRUD.MODIFICADO_ERR)
                    .build();
        }
        if (codigo != null) {
            metodoPago.setCodigo(codigo);
        }
        if (descripcion != null) {
            metodoPago.setDescripcion(descripcion);
        }

        try {
            this.service.modificar(metodoPago);
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
    @Operation(summary = "Buscar metodo de pago por ID", description = "Busca el metodo de pago seleccionado por su ID")
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
    @Operation(summary = "Buscar metodo de pago por codigo", description = "Busca el metodo de pago de acuerdo al codigo ingresado")
    @Path("/codigo/{cod}")
    public Response buscarPorCodigo(@PathParam("cod") String param) throws Exception {
        List<MetodoPago> entity = this.service.buscarPorCodigo(param);

        return Response.ok(entity).build();
    }

    @GET
    @Operation(summary = "Sumar ID's", description = "Suma todos los ID's de los metodos de pago")
    @Path("/sum/")
    public Response sumaId() throws Exception {
        Long entity = this.service.sumIds();

        return Response.ok(entity).build();
    }

}
