package py.edu.ucom.natasha.entities;

import java.util.List;

public class Usuarios {
    public String nombre;
    public String documento;
    public List<Libros> librosPrestados;

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDocumento() {
        return documento;
    }

    public void setDocumento(String documento) {
        this.documento = documento;
    }

    public List<Libros> getLibrosPrestados() {
        return librosPrestados;
    }

    public void setLibrosPrestados(List<Libros> librosPrestados) {
        this.librosPrestados = librosPrestados;
    }

}
