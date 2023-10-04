package py.edu.ucom.natasha.servicios;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import py.edu.ucom.natasha.entities.Libros;
import py.edu.ucom.natasha.entities.Usuarios;
import py.edu.ucom.natasha.utils.DataSourceJSON;

@ApplicationScoped
public class PrestamoService {

    @Inject
    private DataSourceJSON ds;

    public Map<String, String> prestar(String documento, String isbn) {
        Map<String, String> respuesta = new HashMap<>();
        Libros libro = this.ds.buscarLibro(isbn);
        Usuarios usuario = this.ds.buscarUsuario(documento);
        if (libro.getPrestado()) {
            respuesta.put("mensaje", "El libro con ISBN :" + isbn + " ya se encuentra prestado.");
            return respuesta;
        }
        // 1 cambiar de estado el libro
        libro.setPrestado(true);
        // 2 agregar libro al listado de
        this.ds.actualizarLibro(libro);
        usuario.getLibrosPrestados().add(libro);
        this.ds.actualizarUsuario(usuario);
        respuesta.put("mensaje", "El libro con ISBN :" + isbn + " prestado con exito.");

        return respuesta;
    }

    public Map<String, String> devolver(String documento, String isbn) {

        Map<String, String> respuesta = new HashMap<>();
        Libros libro = this.ds.buscarLibro(isbn);
        Usuarios usuario = this.ds.buscarUsuario(documento);

        // eliminar libro prestado
        List<Libros> lista = usuario.getLibrosPrestados();
        for (Libros item : lista) {
            if (item.getISBN().equals(isbn)) {
                int indice = lista.indexOf(item);
                lista.remove(indice);
            }
        }
        // 1 cambiar de estado el libro
        libro.setPrestado(false);
        // 2 agregar libro al listado de
        this.ds.actualizarLibro(libro);

        this.ds.actualizarUsuario(usuario);
        respuesta.put("mensaje", "El libro con ISBN :" + isbn + " fue devuelto con exito.");

        return respuesta;

    }

}
