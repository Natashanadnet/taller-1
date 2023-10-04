package py.edu.ucom.natasha.controllers;

import java.util.List;

import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import py.edu.ucom.natasha.entities.Moneda;
import py.edu.ucom.natasha.utils.DataSourceJSON;

@Path("cambios")
public class CasaCambiosResource {
    @Inject
    public DataSourceJSON ds;

    @GET
    public List<Moneda> Obtener() {
        return ds.ObtenerMonedas();

    }
}
