package py.edu.ucom.natasha.controllers;

import java.util.List;

import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import py.edu.ucom.natasha.entities.Pagos;
import py.edu.ucom.natasha.utils.DataSourceJSON;

@Path("/pagos")
public class PagosResource {
    @Inject
    public DataSourceJSON ds;

    @GET
    @Path("{codigo}")
    public Pagos obtenerPagoPorDocumento(@PathParam("codigo") String param) {
        int codigo = Integer.parseInt(param);
        return this.ds.buscarPago(codigo);

    }

    @GET
    public List<Pagos> obtenerPagos() {
        List<Pagos> lista = this.ds.obtenerPagos();
        return lista;
    }

    @POST
    public void guardarPago(Pagos pago) {
        try {
            this.ds.guardarPagos(pago);
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }

    }

}
