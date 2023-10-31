package py.edu.ucom.natasha.services;

import java.util.List;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.core.Response;
import py.edu.ucom.natasha.config.Globales;
import py.edu.ucom.natasha.config.IDAO;
import py.edu.ucom.natasha.entities.Cliente;
import py.edu.ucom.natasha.entities.TipoDocumento;
import py.edu.ucom.natasha.entities.Venta;
import py.edu.ucom.natasha.repositories.ClienteRepository;
import py.edu.ucom.natasha.util.CaracteresUtil;

@ApplicationScoped
public class ClienteService implements IDAO<Cliente, Integer> {
    @Inject
    private ClienteRepository repository;
    @Inject
    private TipoDocumentoService docuService;

    @Override
    public Cliente obtener(Integer param) {
        return this.repository.findById(param).orElse(null);
    }

    @Override
    public Cliente agregar(Cliente param) {
        return this.repository.save(param);
    }

    @Override
    public Cliente modificar(Cliente param) {
        return this.repository.save(param);
    }

    @Override
    public void eliminar(Integer param) {
        this.repository.deleteById(param);
    }

    @Override
    public List<Cliente> listar() {
        return this.repository.findAll();
    }

    public Response modificarCliente(Integer clienteId, String nombre, String apellido, String documento,
            Integer codDocu,
            String clienteFiel) {
        Cliente cliente = this.obtener(clienteId);
        if (cliente == null) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(Globales.CRUD.MODIFICADO_ERR)
                    .build();
        }
        if (nombre != null) {
            cliente.setNombres(CaracteresUtil.limpiarYCapitalizar(nombre));
        }

        if (apellido != null) {
            cliente.setApellidos(CaracteresUtil.limpiarYCapitalizar(apellido));
        }

        if (documento != null) {
            cliente.setDocumento(documento);
        }

        if (codDocu != null) {
            TipoDocumento tipoDocu = this.docuService.obtener(codDocu);
            if (tipoDocu == null) {
                return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                        .entity(Globales.CRUD.MODIFICADO_ERR)
                        .build();
            }
            cliente.setTipoDocumentoId(tipoDocu);
        }

        if (clienteFiel != null) {
            clienteFiel = clienteFiel.toLowerCase();
            if (clienteFiel.equals("true") || clienteFiel.equals("false")) {
                cliente.setEsClienteFiel(Boolean.parseBoolean(clienteFiel));
            }

        }

        try {
            this.modificar(cliente);
            return Response.status(Response.Status.OK)
                    .entity(Globales.CRUD.MODIFICADO_OK)
                    .build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(Globales.CRUD.MODIFICADO_ERR)
                    .build();
        }

    }

    public Response eliminarCliente(Integer id) {
        Cliente cliente = this.obtener(id);
        List<Venta> ventas = cliente.getVentaList();
        if (ventas.isEmpty()) {
            try {
                this.eliminar(id);
                return Response.status(Response.Status.OK)
                        .entity(Globales.CRUD.ELIMINADO_OK)
                        .build();
            } catch (Exception e) {
                return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                        .entity(Globales.CRUD.ELIMINADO_ERR)
                        .build();
            }
        } else {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(Globales.CRUD.ELIMINADO_ERR)
                    .build();
        }

    }

    public Response nuevoCliente(String nombre, String apellido, String documento, Integer codDocu,
            String clienteFiel) {

        nombre = CaracteresUtil.limpiarYCapitalizar(nombre);
        apellido = CaracteresUtil.limpiarYCapitalizar(apellido);
        clienteFiel = clienteFiel.toLowerCase();
        // Validacion de clienteFiel:
        if (!clienteFiel.equals("true") && !clienteFiel.equals("false")) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("El valor de 'clienteFiel' debe ser 'true' o 'false'")
                    .build();
        }
        TipoDocumento tipoDocu = this.docuService.obtener(codDocu);
        if (tipoDocu == null) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("Tipo de documento no encontrado")
                    .build();
        }
        Cliente cliente = new Cliente();
        cliente.setNombres(nombre);
        cliente.setApellidos(apellido);
        cliente.setDocumento(documento);
        cliente.setTipoDocumentoId(tipoDocu);
        cliente.setEsClienteFiel(Boolean.parseBoolean(clienteFiel));

        try {
            this.agregar(cliente);
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
}
