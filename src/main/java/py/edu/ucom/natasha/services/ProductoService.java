package py.edu.ucom.natasha.services;

import java.util.List;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import py.edu.ucom.natasha.config.IDAO;
import py.edu.ucom.natasha.entities.Producto;
import py.edu.ucom.natasha.repositories.ProductoRepository;

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

    public void cambiarStock(Integer cantidad, Producto producto) {

        producto.setStock(cantidad);
        this.modificar(producto);

    }

}
