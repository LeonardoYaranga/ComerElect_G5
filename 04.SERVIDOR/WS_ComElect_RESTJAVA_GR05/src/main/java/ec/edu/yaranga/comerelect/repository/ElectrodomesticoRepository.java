package ec.edu.yaranga.comerelect.repository;

import ec.edu.yaranga.comerelect.model.Electrodomestico;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ElectrodomesticoRepository extends JpaRepository<Electrodomestico, String> {
    boolean existsByNombreAndCodigoNot(String nombre, String codigo);
}