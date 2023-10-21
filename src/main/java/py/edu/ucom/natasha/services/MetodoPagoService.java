package py.edu.ucom.natasha.services;

import java.util.List;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import py.edu.ucom.natasha.config.IDAO;
import py.edu.ucom.natasha.entities.MetodoPago;
import py.edu.ucom.natasha.repositories.MetodoPagoRepository;

@ApplicationScoped
public class MetodoPagoService implements IDAO<MetodoPago, Integer> {
    @Inject
    private MetodoPagoRepository repository;

    @Override
    public MetodoPago obtener(Integer param) {
        return this.repository.findById(param).orElse(null);
    }

    @Override
    public MetodoPago agregar(MetodoPago param) {
        return this.repository.save(param);
    }

    @Override
    public MetodoPago modificar(MetodoPago param) {
        return this.repository.save(param);
    }

    @Override
    public void eliminar(Integer param) {
        this.repository.deleteById(param);
    }

    @Override
    public List<MetodoPago> listar() {
        return this.repository.findAll();
    }

}
