package ec.edu.yaranga.BanQuito.service;

import ec.edu.yaranga.BanQuito.dto.MovimientoDTO;
import ec.edu.yaranga.BanQuito.model.Cuenta;
import ec.edu.yaranga.BanQuito.model.Movimiento;
import ec.edu.yaranga.BanQuito.repository.CuentaRepository;
import ec.edu.yaranga.BanQuito.repository.MovimientoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MovimientoService {

    private final MovimientoRepository movimientoRepository;
    private final CuentaRepository cuentaRepository;

    @Transactional
    public MovimientoDTO crearMovimiento(MovimientoDTO dto) {
        // 1. Validar cuenta existe
        Cuenta cuenta = cuentaRepository.findById(dto.getNumCuenta())
                .orElseThrow(() -> new IllegalArgumentException("Cuenta no encontrada"));

        // 2. Validar tipo
        if (!"DEP".equalsIgnoreCase(dto.getTipo()) && !"RET".equalsIgnoreCase(dto.getTipo())) {
            throw new IllegalArgumentException("Tipo debe ser DEP o RET");
        }

        // 3. Validar valor positivo
        if (dto.getValor() == null || dto.getValor().compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("El valor debe ser mayor a 0");
        }

        // 4. Validar saldo suficiente para RETIRO
        if ("RET".equalsIgnoreCase(dto.getTipo())) {
            if (cuenta.getSaldo().compareTo(dto.getValor()) < 0) {
                throw new IllegalArgumentException("Saldo insuficiente para retiro");
            }
        }

        // 5. Crear movimiento
        Movimiento movimiento = new Movimiento();
        movimiento.setCuenta(cuenta);
        movimiento.setTipo(dto.getTipo().toUpperCase());
        movimiento.setValor(dto.getValor());
        movimiento.setFecha(dto.getFecha() != null ? dto.getFecha() : LocalDate.now());
        movimiento = movimientoRepository.save(movimiento);

        // 6. Actualizar saldo
        if ("DEP".equalsIgnoreCase(dto.getTipo())) {
            cuenta.setSaldo(cuenta.getSaldo().add(dto.getValor()));
        } else {
            cuenta.setSaldo(cuenta.getSaldo().subtract(dto.getValor()));
        }
        cuentaRepository.save(cuenta);

        return toDTO(movimiento);
    }

    public List<MovimientoDTO> listarMovimientos() {
        // Usamos findAll con la opción Sort.by("fecha").descending()
        return movimientoRepository.findAll(Sort.by("fecha").descending()).stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public List<MovimientoDTO> obtenerMovimientosPorCuenta(String numCuenta) {
        return movimientoRepository.findByCuentaNumCuentaOrderByFechaDesc(numCuenta).stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public MovimientoDTO obtenerMovimiento(Integer id) {
        Movimiento movimiento = movimientoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Movimiento no encontrado"));
        return toDTO(movimiento);
    }

    @Transactional
    public void eliminarMovimiento(Integer id) {
        Movimiento movimiento = movimientoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Movimiento no encontrado"));

        // Revertir saldo
        Cuenta cuenta = movimiento.getCuenta();
        if ("DEP".equals(movimiento.getTipo())) {
            cuenta.setSaldo(cuenta.getSaldo().subtract(movimiento.getValor()));
        } else {
            cuenta.setSaldo(cuenta.getSaldo().add(movimiento.getValor()));
        }
        cuentaRepository.save(cuenta);

        movimientoRepository.delete(movimiento);
    }

    // === MÉTODOS AUXILIARES ===
    private MovimientoDTO toDTO(Movimiento m) {
        MovimientoDTO dto = new MovimientoDTO();
        dto.setCodMovimiento(m.getCodMovimiento());
        dto.setNumCuenta(m.getCuenta().getNumCuenta());
        dto.setTipo(m.getTipo());
        dto.setValor(m.getValor());
        dto.setFecha(m.getFecha());
        return dto;
    }
}