package ec.edu.yaranga.BanQuito.repository;

import ec.edu.yaranga.BanQuito.model.Movimiento;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface MovimientoRepository extends JpaRepository<Movimiento, Integer> {
    List<Movimiento> findByCuentaNumCuentaOrderByFechaDesc(String numCuenta);
    List<Movimiento> findByCuentaNumCuentaAndFechaBetweenOrderByFechaDesc(
            String numCuenta, LocalDate inicio, LocalDate fin);
}
