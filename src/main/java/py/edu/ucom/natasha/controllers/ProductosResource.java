package py.edu.ucom.natasha.controllers;

import java.util.List;

import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import py.edu.ucom.natasha.entities.Productos;
import py.edu.ucom.natasha.utils.DataSourceJSON;

@Path("/productos")
public class ProductosResource {
    @Inject
    public DataSourceJSON ds;

    @GET
    @Path("{codigo}")
    public Productos obtenerProductoPorCodigo(@PathParam("codigo") String param) {
        int codigo = Integer.parseInt(param);
        return this.ds.buscarProducto(codigo);

    }

    @GET
    public List<Productos> obtenerProductos() {
        List<Productos> lista = this.ds.obtenerProductos();
        return lista;
    }

    @POST
    public void guardarProducto(Productos producto) {
        try {
            this.ds.guardarProductos(producto);
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }

    }

}
