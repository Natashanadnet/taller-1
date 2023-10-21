package py.edu.ucom.natasha.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import py.edu.ucom.natasha.entities.Cliente;

public interface ClienteRepository extends JpaRepository<Cliente, Integer> {

}
