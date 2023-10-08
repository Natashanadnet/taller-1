package py.edu.ucom.natasha.servicios;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import py.edu.ucom.natasha.entities.Moneda;
import py.edu.ucom.natasha.utils.DataSourceJSON;

@ApplicationScoped
public class CambiosService {

    @Inject
    public DataSourceJSON ds;

    public Double realizarCambio(String codigoOrigen, String codigoDestino, Integer monto) {
        Double cambio = 0.0;
        Moneda m = this.ds.buscarMonedas(codigoOrigen);

        if (m.getCambios().containsKey(codigoDestino)) {
            Double valorCambio = m.getCambios().get(codigoDestino);
            cambio = monto * valorCambio;
        }

        return cambio;

    }

}