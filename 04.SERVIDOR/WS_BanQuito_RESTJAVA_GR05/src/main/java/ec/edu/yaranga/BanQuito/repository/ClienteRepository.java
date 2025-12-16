package ec.edu.yaranga.BanQuito.repository;

import ec.edu.yaranga.BanQuito.model.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClienteRepository extends JpaRepository<Cliente, String> {
}
