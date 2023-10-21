package py.edu.ucom.natasha.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import py.edu.ucom.natasha.entities.Producto;

public interface ProductoRepository extends JpaRepository<Producto, Integer> {

}
