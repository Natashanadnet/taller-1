package py.edu.ucom.natasha.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import py.edu.ucom.natasha.entities.MetodoPago;

public interface MetodoPagoRepository extends JpaRepository<MetodoPago, Integer> {
    public List<MetodoPago> findByCodigo(String codigo);

    @Query("SELECT SUM(m.metodoPagoId) FROM MetodoPago m")
    Long sumId();

    // otra forma:
    // @Query(nativeQuery = true, value = "SELECT SUM(metodo_pago_id) FROM
    // metodo_pago mp")
    // Long sumIdNative();
}
