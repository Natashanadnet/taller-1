package py.edu.ucom.natasha.entities;

public class Pagos {
    private static int codigoUnico = 1;
    private int codPago;
    private String codCliente;
    private double monto;
    private int codDetalle;
    private String codEmpleado;

    public Pagos() {
    }

    public Pagos(Clientes cliente, DetalleCompra detalle, Empleados empleado) {
        this.codPago = codigoUnico;
        codigoUnico++;
        this.codCliente = cliente.getDocumento();
        this.codDetalle = detalle.getCodigo();
        this.monto = detalle.getTotal();
        this.codEmpleado = empleado.getDocumento();
    }

    public int getCodPago() {
        return codPago;
    }

    public String getCodCliente() {
        return codCliente;
    }

    public double getMonto() {
        return monto;
    }

    public int getCodDetalle() {
        return codDetalle;
    }

    public String getCodEmpleado() {
        return codEmpleado;
    }

}
