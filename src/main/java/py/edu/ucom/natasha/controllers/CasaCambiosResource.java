package py.edu.ucom.natasha.controllers;

import java.util.List;

import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import py.edu.ucom.natasha.entities.Moneda;
import py.edu.ucom.natasha.servicios.CambiosService;
import py.edu.ucom.natasha.utils.DataSourceJSON;

@Path("cambios")
public class CasaCambiosResource {

    @Inject
    public DataSourceJSON ds;

    @Inject
    public CambiosService service;

    @GET
    public List<Moneda> obtener() {
        return ds.obtenerMonedas();
    }

    @GET
    @Path("{codigoOrigen}/{monto}/{codigoDestino}")
    public Double cambio(
            @PathParam("monto") Integer monto,
            @PathParam("codigoDestino") String codigoDestino,
            @PathParam("codigoOrigen") String codigoOrigen) {

        return this.service.realizarCambio(codigoOrigen, codigoDestino, monto);

    }
}
