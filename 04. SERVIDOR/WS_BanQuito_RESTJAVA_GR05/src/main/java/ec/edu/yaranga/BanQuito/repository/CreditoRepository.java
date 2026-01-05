package ec.edu.yaranga.BanQuito.repository;

import ec.edu.yaranga.BanQuito.model.Credito;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CreditoRepository extends JpaRepository<Credito, Integer> {
    boolean existsByClienteCedulaAndEstado(String cedula, String estado);
    List<Credito> findByClienteCedula(String cedula);
}
