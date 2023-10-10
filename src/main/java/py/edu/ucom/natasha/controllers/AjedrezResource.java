package py.edu.ucom.natasha.controllers;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import py.edu.ucom.natasha.entities.AjedrezParam;
import py.edu.ucom.natasha.entities.Peon;

@Path("/ajedrez")
public class AjedrezResource {
    @POST
    @Path("/peon")
    public Boolean validarMovimientoPeon(AjedrezParam ajedrezParam) {
        Peon p = new Peon();

        return p.movimientoValido(ajedrezParam.getInicioX(), ajedrezParam.getInicioY(), ajedrezParam.getFinalX(),
                ajedrezParam.getFinalX());

    }

}
