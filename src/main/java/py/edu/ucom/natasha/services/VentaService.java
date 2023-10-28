package py.edu.ucom.natasha.services;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import py.edu.ucom.natasha.config.IDAO;
import py.edu.ucom.natasha.entities.Cliente;
import py.edu.ucom.natasha.entities.MetodoPago;
import py.edu.ucom.natasha.entities.Venta;
import py.edu.ucom.natasha.entities.VentaDetalle;
import py.edu.ucom.natasha.entities.dto.ResumenVentaDTO;
import py.edu.ucom.natasha.entities.dto.VentaDetalleDTO;
import py.edu.ucom.natasha.repositories.VentaRepository;

@ApplicationScoped
public class VentaService implements IDAO<Venta, Integer> {
    @Inject
    public VentaRepository repository;

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

    public Venta crearVenta(Cliente cliente, MetodoPago metodoPago) {
        Venta venta = new Venta();
        Timestamp fecha = new Timestamp(System.currentTimeMillis());
        int total = 0;
        venta.setClienteId(cliente);
        venta.setFecha(fecha);
        venta.setMetodoPagoId(metodoPago);
        venta.setTotal(total);
        return this.repository.save(venta);
    }

}
