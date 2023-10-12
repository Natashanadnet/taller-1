package py.edu.ucom.natasha.entities;

import java.time.LocalDateTime;

public class Pagos {
    private static int codigoUnico = 1;
    private int codigoCompra;
    private Clientes cliente;
    private LocalDateTime fecha;
    private double monto;
    private DetalleCompra detalle;
    private Empleados empleado;

    public Pagos(Clientes cliente, DetalleCompra detalle, Empleados empleado) {
        this.codigoCompra = codigoUnico;
        codigoUnico++;
        this.cliente = cliente;
        this.fecha = LocalDateTime.now();
        this.detalle = detalle;
        this.monto = detalle.getTotal();
        this.empleado = empleado;
    }

    public int getCodigo() {
        return codigoCompra;
    }

    public Clientes getCliente() {
        return cliente;
    }

    public LocalDateTime getFechayHora() {
        return fecha;
    }

    public double getMonto() {
        return monto;
    }

    public DetalleCompra getDetalle() {
        return detalle;
    }

    public Empleados getEmpleado() {
        return empleado;
    }

}
