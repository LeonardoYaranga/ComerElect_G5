package ec.edu.yaranga.BanQuito.repository;
import ec.edu.yaranga.BanQuito.model.Cuenta;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CuentaRepository extends JpaRepository<Cuenta, String> {
    long countByClienteCedula(String cedula);
    List<Cuenta> findByClienteCedula(String cedula);
}