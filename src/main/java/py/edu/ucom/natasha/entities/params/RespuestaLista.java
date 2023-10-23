package py.edu.ucom.natasha.entities.params;

import java.util.List;

public class RespuestaLista<T> {
    List<T> lista;
    Integer cantidad;
    String mensaje;

    public RespuestaLista(List<T> lista) {
        this.lista = lista;
        this.cantidad = lista.size();

    }

    public List<T> getLista() {
        return lista;
    }

    public void setLista(List<T> lista) {
        this.lista = lista;
    }

    public Integer getCantidad() {
        return cantidad;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

}
