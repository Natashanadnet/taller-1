package py.edu.ucom.natasha.services;

import java.util.List;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import py.edu.ucom.natasha.config.IDAO;
import py.edu.ucom.natasha.entities.Producto;
import py.edu.ucom.natasha.entities.Venta;
import py.edu.ucom.natasha.entities.VentaDetalle;
import py.edu.ucom.natasha.repositories.VentaDetalleRepository;

@ApplicationScoped
public class VentaDetalleService implements IDAO<VentaDetalle, Integer> {
    @Inject
    private VentaDetalleRepository repository;

    @Override
    public VentaDetalle obtener(Integer param) {
        return this.repository.findById(param).orElse(null);
    }

    @Override
    public VentaDetalle agregar(VentaDetalle param) {
        return this.repository.save(param);
    }

    @Override
    public VentaDetalle modificar(VentaDetalle param) {
        return this.repository.save(param);
    }

    @Override
    public void eliminar(Integer param) {
        this.repository.deleteById(param);
    }

    @Override
    public List<VentaDetalle> listar() {
        return this.repository.findAll();
    }

    public VentaDetalle crearVentaDetalle(Venta venta, Producto producto, Integer cantidad) {
        Integer subtotal = producto.getPrecioUnitario() * cantidad;
        VentaDetalle ventaDet = new VentaDetalle();
        ventaDet.setVentaId(venta);
        ventaDet.setProductoId(producto);
        ventaDet.setCantidad(cantidad);
        ventaDet.setSubtotal(subtotal);
        return this.repository.save(ventaDet);

    }

}