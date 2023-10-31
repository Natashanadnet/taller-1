package py.edu.ucom.natasha.services;

import java.util.List;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.core.Response;
import py.edu.ucom.natasha.config.Globales;
import py.edu.ucom.natasha.config.IDAO;
import py.edu.ucom.natasha.entities.Producto;
import py.edu.ucom.natasha.repositories.ProductoRepository;
import py.edu.ucom.natasha.util.CaracteresUtil;

@ApplicationScoped
public class ProductoService implements IDAO<Producto, Integer> {
    @Inject
    private ProductoRepository repository;

    @Override
    public Producto obtener(Integer param) {
        return this.repository.findById(param).orElse(null);
    }

    @Override
    public Producto agregar(Producto param) {
        return this.repository.save(param);
    }

    @Override
    public Producto modificar(Producto param) {
        return this.repository.save(param);
    }

    @Override
    public void eliminar(Integer param) {
        this.repository.deleteById(param);
    }

    @Override
    public List<Producto> listar() {
        return this.repository.findAll();
    }

    public Boolean restarStock(Integer cantidad, Producto producto) {
        int stockActual = producto.getStock();
        if (stockActual - cantidad >= 0) {
            producto.setStock(stockActual - cantidad);
            this.modificar(producto);
        }
        return stockActual - cantidad >= 0;
    }

    public void sumarStock(Integer cantidad, Producto producto) {
        int stockActual = producto.getStock();
        producto.setStock(stockActual + cantidad);
        this.modificar(producto);
    }

    public Response modificarProducto(Integer productoId, String codigo, Integer stock, String descripcion,
            Integer precioUnitario) {
        Producto producto = this.obtener(productoId);
        if (producto == null) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("El producto no existe")
                    .build();
        }
        if (codigo != null) {
            producto.setCodigo(codigo);
        }
        if (stock != null && stock >= 0) {
            producto.setStock(stock);
        }
        if (descripcion != null) {
            producto.setDescripcion(descripcion);
        }
        if (precioUnitario != null && precioUnitario > 0) {
            producto.setPrecioUnitario(precioUnitario);
        }
        try {
            this.modificar(producto);
            return Response.status(Response.Status.OK)
                    .entity(Globales.CRUD.MODIFICADO_OK)
                    .build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(Globales.CRUD.MODIFICADO_ERR)
                    .build();
        }
    }

    public Response nuevoProducto(String codigo, Integer stock, String descripcion, Integer precioUnitario) {
        Producto producto = new Producto();
        producto.setCodigo(codigo);
        if (stock < 0) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("El stock del producto no puede ser negativo")
                    .build();

        }
        producto.setStock(stock);
        descripcion = CaracteresUtil.limpiarYCapitalizar(descripcion);
        producto.setDescripcion(descripcion);
        if (precioUnitario < 0) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("El precio del producto no puede ser negativo")
                    .build();
        }
        producto.setPrecioUnitario(precioUnitario);
        try {
            this.agregar(producto);
            return Response.status(Response.Status.OK)
                    .entity(Globales.CRUD.CREADO_OK)
                    .build();
        } catch (Exception e) {
            // Manejar la excepción de persistencia
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(Globales.CRUD.CREADO_ERR)
                    .build();
        }

    }

}
