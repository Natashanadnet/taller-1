package py.edu.ucom.natasha.controllers;

import jakarta.inject.Inject;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import py.edu.ucom.natasha.entities.Pagos;
import py.edu.ucom.natasha.servicios.PagoService;

@Path("/tienda")
public class TiendaResource {
    @Inject
    private PagoService service;

    @POST
    @Path("/caja/{documentoCliente}/{documentoUsuario}/{codProducto}/{cantidad}")
    public Pagos generarPagos(@PathParam("documentoCliente") String docCliente,
            @PathParam("documentoUsuario") String docEmpleado, @PathParam("codProducto") String codProducto,
            @PathParam("cantidad") String cantidad) {
        return this.service.generarPago(docCliente, docEmpleado, codProducto, cantidad);

    }

}
