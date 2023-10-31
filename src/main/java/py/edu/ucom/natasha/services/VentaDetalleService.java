package py.edu.ucom.natasha.services;

import java.util.List;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.core.Response;
import py.edu.ucom.natasha.config.Globales;
import py.edu.ucom.natasha.config.IDAO;
import py.edu.ucom.natasha.entities.Producto;
import py.edu.ucom.natasha.entities.Venta;
import py.edu.ucom.natasha.entities.VentaDetalle;
import py.edu.ucom.natasha.repositories.VentaDetalleRepository;

@ApplicationScoped
public class VentaDetalleService implements IDAO<VentaDetalle, Integer> {
    @Inject
    private VentaDetalleRepository repository;
    @Inject
    private ProductoService productoService;
    @Inject
    private VentaService ventaService;

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

    public Response eliminarDetalle(Integer id) {
        VentaDetalle detalle = this.obtener(id);
        if (detalle == null) {
            return Response.status(Response.Status.NOT_FOUND).entity("No se encontro ningun detalle con ese ID")
                    .build();
        }
        Venta venta = detalle.getVentaId();
        int cantidad = detalle.getCantidad();
        Producto producto = detalle.getProductoId();
        this.productoService.sumarStock(cantidad, producto);
        try {
            this.eliminar(id);
            List<VentaDetalle> listaDet = venta.getVentaDetalleList();
            int total = 0;
            for (VentaDetalle item : listaDet) {
                total += item.getSubtotal();
            }
            venta.setTotal(total);
            this.ventaService.modificar(venta);
            return Response.status(Response.Status.OK)
                    .entity(Globales.CRUD.ELIMINADO_OK)
                    .build();
        } catch (Exception e) {
            // Manejar la excepción de persistencia
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(Globales.CRUD.ELIMINADO_ERR)
                    .build();
        }
    }

    public Response modificarDetalle(Integer ventaDetalleId, Integer cantidad, Integer productoId) {
        VentaDetalle detalle = this.obtener(ventaDetalleId);

        if (detalle == null) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("No se encontró ningún detalle con ese ID")
                    .build();
        }

        Venta venta = detalle.getVentaId();

        if (productoId != null) {
            Producto productoAnterior = detalle.getProductoId();
            this.productoService.sumarStock(detalle.getCantidad(), productoAnterior);
            detalle.setProductoId(this.productoService.obtener(productoId));
        }

        if (cantidad != null) {
            if (!this.productoService.restarStock(cantidad, detalle.getProductoId())) {
                return Response.status(Response.Status.NOT_FOUND)
                        .entity("No hay stock suficiente")
                        .build();
            }

            Producto producto = detalle.getProductoId();
            this.productoService.sumarStock(detalle.getCantidad(), producto);
            detalle.setCantidad(cantidad);
            int precio = producto.getPrecioUnitario();
            detalle.setSubtotal(precio * cantidad);
        }

        try {
            this.modificar(detalle);
            List<VentaDetalle> listaDet = venta.getVentaDetalleList();
            int total = 0;
            for (VentaDetalle item : listaDet) {
                total += item.getSubtotal();
            }
            venta.setTotal(total);
            this.ventaService.modificar(venta);
            return Response.status(Response.Status.OK)
                    .entity(Globales.CRUD.MODIFICADO_OK)
                    .build();
        } catch (Exception e) {
            // Manejar la excepción de persistencia
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(Globales.CRUD.MODIFICADO_ERR)
                    .build();
        }
    }

    public Response agregarDetalle(Integer ventaId, Integer productoId, Integer cantidad) {
        Venta venta = this.ventaService.obtener(ventaId);
        Producto producto = this.productoService.obtener(productoId);
        if (venta == null || producto == null) {
            Response.status(Response.Status.NOT_FOUND).entity("No se encuetran los datos en el registro")
                    .build();
        }
        if (!this.productoService.restarStock(cantidad, producto)) {
            return Response.status(Response.Status.NOT_FOUND).entity("El producto no se encuentra disponible").build();
        }
        try {
            this.crearVentaDetalle(venta, producto, cantidad);
            int total = 0;
            List<VentaDetalle> lista = venta.getVentaDetalleList();
            for (VentaDetalle item : lista) {
                total += item.getSubtotal();
            }

            venta.setTotal(total);
            this.ventaService.modificar(venta);
            return Response.status(Response.Status.OK).entity(Globales.CRUD.CREADO_OK).build();
        } catch (Exception e) {
            // Manejar la excepción de persistencia
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(Globales.CRUD.ELIMINADO_ERR)
                    .build();
        }

    }

}