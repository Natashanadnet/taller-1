package py.edu.ucom.natasha.entities;

import java.util.HashMap;
import java.util.Map;

public class DetalleCompra {
    private static int codigoUnico = 1;
    private int codigoCompra;
    private HashMap<String, Double> detalles;
    private double total;
    private Clientes cliente;

    public DetalleCompra(Clientes cliente) {
        this.detalles = new HashMap<>();
        this.codigoCompra = codigoUnico;
        codigoUnico++;
        this.cliente = cliente;
    }

    public void agregarProductos(Productos producto, int cantidad) {
        double subtotal = producto.getPrecio() * cantidad;
        detalles.put(producto.getNombre(), subtotal);
    }

    public double getTotal() {
        this.total = 0.0;
        for (Map.Entry<String, Double> entrada : detalles.entrySet()) {
            double subtotal = entrada.getValue();
            total += subtotal;
        }
        return total;
    }

    public int getCodigo() {
        return codigoCompra;
    }

    public Clientes getCliente() {
        return cliente;
    }

    public HashMap<String, Double> getListado() {
        return detalles;
    }

}
