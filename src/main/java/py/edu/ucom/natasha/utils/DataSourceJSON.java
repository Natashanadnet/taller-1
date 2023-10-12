package py.edu.ucom.natasha.utils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.enterprise.context.ApplicationScoped;
import py.edu.ucom.natasha.entities.Clientes;
import py.edu.ucom.natasha.entities.Empleados;
import py.edu.ucom.natasha.entities.Libros;
import py.edu.ucom.natasha.entities.Pagos;
import py.edu.ucom.natasha.entities.Productos;
import py.edu.ucom.natasha.entities.Usuarios;

@ApplicationScoped
public class DataSourceJSON {
    public String SRC_USUARIOS = "C:\\Users\\n" + //
            "atas\\OneDrive\\Documents\\Ucom Laptop\\2023\\java 2\\clase 1\\taller-1\\src\\main\\java\\py\\edu\\ucom\\n"
            + //
            "atasha\\utils\\usuarios.json";

    public String SRC = "C:\\Users\\n" + //
            "atas\\OneDrive\\Documents\\Ucom Laptop\\2023\\java 2\\clase 1\\taller-1\\src\\main\\java\\py\\edu\\ucom\\n"
            + //
            "atasha\\utils\\libros.json";

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
            if (item.getCodigo() == codigo) {
                pago = item;
            }

        }
        return pago;
    }

    // BORRAR DESDE ACA

    /// actualizar libro y actualizar usuario.
    public void actualizarLibro(Libros param) {
        List<Libros> data = obtenerLibros();
        for (Libros item : data) {
            if (item.getISBN().equals(param.getISBN())) {

                if (param.getPrestado() != null) {
                    item.setPrestado(param.getPrestado());
                }
                if (param.getTitulo() != null && !param.getTitulo().isEmpty()) {
                    item.setTitulo(param.getTitulo());
                }
                if (param.getAutor() != null && !param.getAutor().isEmpty()) {
                    item.setAutor(param.getAutor());
                }
            }

        }

        try {
            ObjectMapper mapper = new ObjectMapper();
            mapper.writeValue(new File(this.SRC), data);
        } catch (Exception e) {
            // TODO: handle exception
        }

    }

    public void actualizarUsuario(Usuarios param) {
        List<Usuarios> data = obtenerUsuarios();
        for (Usuarios item : data) {
            if (item.getDocumento().equals(param.getDocumento())) {
                item.setNombre(param.getNombre());
                item.setLibrosPrestados(param.getLibrosPrestados());
                break;
            }

        }
        try {
            ObjectMapper mapper = new ObjectMapper();
            mapper.writeValue(new File(this.SRC_USUARIOS), data);
        } catch (Exception e) {
            // TODO: handle exception
        }
    }

    public void guardarUsuarios(Usuarios usuario) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            List<Usuarios> lista = obtenerUsuarios();
            usuario.setLibrosPrestados(new ArrayList<>());
            lista.add(usuario);
            mapper.writeValue(new File(this.SRC_USUARIOS), lista);
        } catch (Exception e) {
            // TODO: handle exception
        }

    }

    public List<Usuarios> obtenerUsuarios() {
        ObjectMapper mapper = new ObjectMapper();
        List<Usuarios> data = new ArrayList<>();
        try {
            data = mapper.readValue(new File(this.SRC_USUARIOS), new TypeReference<List<Usuarios>>() {
            });

        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }

        return data;
    }

    public Libros buscarLibro(String isbn) {
        Libros libro = null;
        List<Libros> data = obtenerLibros();

        for (Libros item : data) {
            if (item.getISBN().equals(isbn)) {
                libro = item;
            }

        }
        return libro;
    }

    public Usuarios buscarUsuario(String documento) {
        Usuarios usuario = null;
        List<Usuarios> data = obtenerUsuarios();

        for (Usuarios item : data) {
            if (item.getDocumento().equals(documento)) {
                usuario = item;
            }

        }
        return usuario;
    }

    public void guardarLibros(Libros libro) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            List<Libros> lista = obtenerLibros();
            lista.add(libro);
            mapper.writeValue(new File(this.SRC), lista);
        } catch (Exception e) {
            // TODO: handle exception
        }
    }

    public List<Libros> obtenerLibros() {
        ObjectMapper mapper = new ObjectMapper();
        List<Libros> libros = new ArrayList<>();
        try {
            libros = mapper.readValue(new File(this.SRC), new TypeReference<List<Libros>>() {
            });

        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }

        return libros;

    }

}
