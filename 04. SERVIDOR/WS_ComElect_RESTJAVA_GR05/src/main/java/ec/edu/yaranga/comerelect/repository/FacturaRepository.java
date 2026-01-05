package ec.edu.yaranga.comerelect.repository;

import ec.edu.yaranga.comerelect.model.Factura;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface FacturaRepository extends JpaRepository<Factura, String> {
    
    @Query("SELECT f FROM Factura f WHERE f.numFactura LIKE :patron ORDER BY f.numFactura DESC LIMIT 1")
    Optional<Factura> findUltimaFacturaDelAnio(@Param("patron") String patron);
}
