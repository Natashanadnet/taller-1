package py.edu.ucom.natasha.services;

import java.util.List;

import jakarta.enterprise.context.ApplicationScoped;
import py.edu.ucom.natasha.config.IDAO;
import py.edu.ucom.natasha.entities.Cliente;

@ApplicationScoped
public class ClienteService implements IDAO<Cliente, Integer> {

    @Override
    public Cliente obtener(Integer param) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'obtener'");
    }

    @Override
    public Cliente agregar(Cliente param) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'agregar'");
    }

    @Override
    public Cliente modificar(Cliente param) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'modificar'");
    }

    @Override
    public void eliminar(Integer param) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'eliminar'");
    }

    @Override
    public List<Cliente> listar() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'listar'");
    }

}
