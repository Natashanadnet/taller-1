package py.edu.ucom.natasha.services;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.core.Response;
import py.edu.ucom.natasha.config.Globales;
import py.edu.ucom.natasha.config.IDAO;
import py.edu.ucom.natasha.entities.Cliente;
import py.edu.ucom.natasha.entities.MetodoPago;
import py.edu.ucom.natasha.entities.Producto;
import py.edu.ucom.natasha.entities.Venta;
import py.edu.ucom.natasha.entities.VentaDetalle;
import py.edu.ucom.natasha.entities.dto.ResumenVentaDTO;
import py.edu.ucom.natasha.entities.dto.VentaDetalleDTO;
import py.edu.ucom.natasha.repositories.VentaRepository;

@ApplicationScoped
public class VentaService implements IDAO<Venta, Integer> {
    @Inject
    public VentaRepository repository;
    @Inject
    public ClienteService clienteService;
    @Inject
    public MetodoPagoService metodoPagoService;
    @Inject
    public ProductoService productoService;
    @Inject
    public VentaDetalleService ventaDetalleService;

    @Override
    public Venta obtener(Integer param) {
        return this.repository.findById(param).orElse(null);
    }

    @Override
    public Venta agregar(Venta param) {
        return this.repository.save(param);
    }

    @Override
    public Venta modificar(Venta param) {
        return this.repository.save(param);
    }

    @Override
    public void eliminar(Integer param) {
        this.repository.deleteById(param);
    }

    @Override
    public List<Venta> listar() {
        return this.repository.findAll();
    }

    public ResumenVentaDTO obtenerResumen(Integer ventaId) {
        ResumenVentaDTO data = new ResumenVentaDTO();
        Venta v = this.repository.findById(ventaId).orElse(null);
        Cliente clie = v.getClienteId();
        data.setRazonSocial(clie.getNombres() + " " + clie.getApellidos());
        data.setDocumento(clie.getDocumento());
        data.setFecha(v.getFecha());
        List<VentaDetalleDTO> detalle = new ArrayList<>();
        for (VentaDetalle item : v.getVentaDetalleList()) {
            VentaDetalleDTO vdto = new VentaDetalleDTO();
            vdto.setCantidad(item.getCantidad());
            vdto.setSubtotal(item.getSubtotal());
            vdto.setDescripcion(item.getProductoId().getDescripcion());
            detalle.add(vdto);
        }
        data.setDetalle(detalle);
        return data;

    }

    public Response crearVenta(Integer clienteId, Integer metodoPagoId, Integer productoId, Integer cantidad) {
        Venta venta = new Venta();
        Timestamp fecha = new Timestamp(System.currentTimeMillis());
        int total = 0;
        Cliente cliente = this.clienteService.obtener(clienteId);
        MetodoPago metodoPago = this.metodoPagoService.obtener(metodoPagoId);
        Producto producto = this.productoService.obtener(productoId);
        if (cliente == null || metodoPago == null || producto == null) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("Los datos ingresados no se encuentran registrados")
                    .build();
        }
        venta.setClienteId(cliente);
        venta.setFecha(fecha);
        venta.setMetodoPagoId(metodoPago);
        venta.setTotal(total);

        if (!this.productoService.restarStock(cantidad, producto)) {
            return Response.status(Response.Status.NOT_FOUND).entity("El producto no se encuentra disponible").build();
        }

        try {
            this.agregar(venta);
            VentaDetalle detalle = this.ventaDetalleService.crearVentaDetalle(venta, producto, cantidad);
            venta.setTotal(detalle.getSubtotal());
            this.modificar(venta);
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

    public Response modificarVenta(Integer ventaId, Integer clienteId, Integer metodoPagoId) {
        Venta venta = this.obtener(ventaId);
        if (venta == null) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("La venta no existe")
                    .build();
        }
        if (clienteId != null) {
            Cliente cliente = this.clienteService.obtener(clienteId);
            if (cliente == null) {
                return Response.status(Response.Status.BAD_REQUEST)
                        .entity("El cliente no existe")
                        .build();

            }
            venta.setClienteId(cliente);
        }
        if (metodoPagoId != null) {
            MetodoPago metodoPago = this.metodoPagoService.obtener(metodoPagoId);
            if (metodoPago == null) {
                return Response.status(Response.Status.BAD_REQUEST)
                        .entity("El metodo de pago no existe")
                        .build();
            }
            venta.setMetodoPagoId(metodoPago);

        }
        try {
            this.modificar(venta);
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

}
