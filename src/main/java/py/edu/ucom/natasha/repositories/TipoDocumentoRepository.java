package py.edu.ucom.natasha.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import py.edu.ucom.natasha.entities.TipoDocumento;

public interface TipoDocumentoRepository extends JpaRepository<TipoDocumento, Integer> {

}
