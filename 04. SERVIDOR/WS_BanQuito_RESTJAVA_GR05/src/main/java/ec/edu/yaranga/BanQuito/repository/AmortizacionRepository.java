package ec.edu.yaranga.BanQuito.repository;

import ec.edu.yaranga.BanQuito.model.Amortizacion;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AmortizacionRepository extends JpaRepository<Amortizacion, Integer> {
    List<Amortizacion> findByCreditoIdCreditoOrderByNumCuota(Integer idCredito);
}