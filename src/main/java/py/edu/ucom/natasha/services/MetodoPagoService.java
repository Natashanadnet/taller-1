package py.edu.ucom.natasha.services;

import java.util.List;
import java.util.logging.Logger;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import io.quarkus.logging.Log;
import io.quarkus.panache.common.Page;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import py.edu.ucom.natasha.config.IDAO;
import py.edu.ucom.natasha.controllers.MetodoPagoResource;
import py.edu.ucom.natasha.entities.MetodoPago;
import py.edu.ucom.natasha.repositories.MetodoPagoRepository;

@ApplicationScoped
public class MetodoPagoService implements IDAO<MetodoPago, Integer> {
    // private static final Logger Log = Logger.getLogger(MetodoPagoResource)
    @Inject
    private MetodoPagoRepository repository;

    @Override
    public MetodoPago obtener(Integer param) {
        // MetodoPago m = new MetodoPago(1, "TEST", "TEST");
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

    public List<MetodoPago> buscarPorCodigo(String cod) {
        // System.out.println(cod + "CODIGO ESPERADO");
        try {
            this.repository.findByCodigo(cod);
        } catch (Exception e) {
            e.printStackTrace();
            // TODO: handle exception
        }
        return this.repository.findByCodigo(cod);
    }

    public Long sumIds() {
        return this.repository.sumId();
    }

    // public List<MetodoPago> paginado(Integer pagina, Integer cantidad) {
    // PageRequest page = new PageR(pagina, cantidad);
    // return this.repository.findAll(Sort.by("codigo"));
    // }

}
