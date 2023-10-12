package py.edu.ucom.natasha.utils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.enterprise.context.ApplicationScoped;
import py.edu.ucom.natasha.entities.Clientes;
import py.edu.ucom.natasha.entities.Empleados;
import py.edu.ucom.natasha.entities.Pagos;
import py.edu.ucom.natasha.entities.Productos;

@ApplicationScoped
public class DataSourceJSON {

    public String SRC_CLIENTES = "C:\\Users\\n" + //
            "atas\\OneDrive\\Documents\\Ucom Laptop\\2023\\java 2\\clase 1\\taller-1\\src\\main\\java\\py\\edu\\ucom\\n"
            + //
            "atasha\\utils\\listadoClientes.json";

    public String SRC_PRODUCTOS = "C:\\Users\\n" + //
            "atas\\OneDrive\\Documents\\Ucom Laptop\\2023\\java 2\\clase 1\\taller-1\\src\\main\\java\\py\\edu\\ucom\\n"
            + //
            "atasha\\utils\\listadoProductos.json";

    public String SRC_EMPLEADOS = "C:\\Users\\n" + //
            "atas\\OneDrive\\Documents\\Ucom Laptop\\2023\\java 2\\clase 1\\taller-1\\src\\main\\java\\py\\edu\\ucom\\n"
            + //
            "atasha\\utils\\listadoUsuarios.json";

    public String SRC_PAGOS = "C:\\Users\\n" + //
            "atas\\OneDrive\\Documents\\Ucom Laptop\\2023\\java 2\\clase 1\\taller-1\\src\\main\\java\\py\\edu\\ucom\\n"
            + //
            "atasha\\utils\\registroPagos.json";

    public List<Clientes> obtenerClientes() {
        ObjectMapper mapper = new ObjectMapper();
        List<Clientes> clientes = new ArrayList<>();
        try {
            clientes = mapper.readValue(new File(this.SRC_CLIENTES), new TypeReference<List<Clientes>>() {
            });

        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }

        return clientes;

    }

    public void guardarClientes(Clientes cliente) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            List<Clientes> lista = obtenerClientes();
            lista.add(cliente);
            mapper.writeValue(new File(this.SRC_CLIENTES), lista);
        } catch (Exception e) {
            // TODO: handle exception
        }
    }

    public List<Productos> obtenerProductos() {
        ObjectMapper mapper = new ObjectMapper();
        List<Productos> productos = new ArrayList<>();
        try {
            productos = mapper.readValue(new File(this.SRC_PRODUCTOS), new TypeReference<List<Productos>>() {
            });

        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }

        return productos;

    }

    public void guardarProductos(Productos producto) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            List<Productos> lista = obtenerProductos();
            lista.add(producto);
            mapper.writeValue(new File(this.SRC_PRODUCTOS), lista);
        } catch (Exception e) {
            // TODO: handle exception
        }
    }

    public List<Pagos> obtenerPagos() {
        ObjectMapper mapper = new ObjectMapper();
        List<Pagos> pagos = new ArrayList<>();
        try {
            pagos = mapper.readValue(new File(this.SRC_PAGOS), new TypeReference<List<Pagos>>() {
            });

        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
            System.out.println("Error al leer el archivo JSON: " + e.getMessage());
        }

        return pagos;
    }

    public void guardarPagos(Pagos pago) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            List<Pagos> lista = obtenerPagos();
            lista.add(pago);
            mapper.writeValue(new File(this.SRC_PAGOS), lista);
        } catch (Exception e) {
            // TODO: handle exception
        }
    }

    public List<Empleados> obtenerEmpleados() {
        ObjectMapper mapper = new ObjectMapper();
        List<Empleados> empleados = new ArrayList<>();
        try {
            empleados = mapper.readValue(new File(this.SRC_EMPLEADOS), new TypeReference<List<Empleados>>() {
            });

        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }

        return empleados;

    }

    public void guardarEmpleados(Empleados empleado) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            List<Empleados> lista = obtenerEmpleados();
            lista.add(empleado);
            mapper.writeValue(new File(this.SRC_EMPLEADOS), lista);
        } catch (Exception e) {
            // TODO: handle exception
        }
    }

    // BUSQUEDAS
    public Clientes buscarCliente(String documento) {
        Clientes cliente = null;
        List<Clientes> data = obtenerClientes();

        for (Clientes item : data) {
            if (item.getDocumento().equals(documento)) {
                cliente = item;
            }

        }
        return cliente;
    }

    public Productos buscarProducto(int codigo) {
        Productos producto = null;
        List<Productos> data = obtenerProductos();

        for (Productos item : data) {
            if (item.getCodigo() == codigo) {
                producto = item;
            }

        }
        return producto;
    }

    public Empleados buscarEmpleado(String documento) {
        Empleados empleado = null;
        List<Empleados> data = obtenerEmpleados();

        for (Empleados item : data) {
            if (item.getDocumento().equals(documento)) {
                empleado = item;
            }

        }
        return empleado;
    }

    public Pagos buscarPago(int codigo) {
        Pagos pago = null;
        List<Pagos> data = obtenerPagos();

        for (Pagos item : data) {
            if (item.getCodPago() == codigo) {
                pago = item;
            }

        }
        return pago;
    }

}
