package py.edu.ucom.natasha.entities;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;

public class Pagos {
    private static int codigoUnico = 1;
    private int codPago;
    private String codCliente;
    private LocalDateTime fecha;
    private double monto;
    private int codDetalle;
    private String codEmpleado;

    public Pagos(Clientes cliente, DetalleCompra detalle, Empleados empleado) {
        this.codPago = codigoUnico;
        codigoUnico++;
        this.codCliente = cliente.getDocumento();
        this.fecha = LocalDateTime.now();
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

    @JsonProperty("fechayHora")
    @JsonSerialize(using = ToStringSerializer.class)
    public LocalDateTime getFechayHora() {
        return fecha;
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
