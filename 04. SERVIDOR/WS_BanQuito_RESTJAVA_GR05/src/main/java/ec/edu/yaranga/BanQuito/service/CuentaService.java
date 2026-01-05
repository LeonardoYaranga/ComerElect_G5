package ec.edu.yaranga.BanQuito.service;

import ec.edu.yaranga.BanQuito.dto.CuentaDTO;
import ec.edu.yaranga.BanQuito.model.Cliente;
import ec.edu.yaranga.BanQuito.model.Cuenta;
import ec.edu.yaranga.BanQuito.repository.ClienteRepository;
import ec.edu.yaranga.BanQuito.repository.CuentaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class CuentaService {

    private final CuentaRepository cuentaRepository;
    private final ClienteRepository clienteRepository;
    private final Random random = new Random();
    private static final int MAX_CUENTAS_POR_CLIENTE = 3;

    @Transactional
    public CuentaDTO crearCuenta(CuentaDTO dto) {
        log.info("Creando cuenta {}", dto);
        // 1. Validar cliente existe
        Cliente cliente = clienteRepository.findById(dto.getCedula())
                .orElseThrow(() -> new IllegalArgumentException("Cliente no encontrado"));

        // 2. Validar máximo 3 cuentas
        long cuentasActuales = cuentaRepository.countByClienteCedula(dto.getCedula());
        if (cuentasActuales >= MAX_CUENTAS_POR_CLIENTE) {
            throw new IllegalArgumentException("El cliente ya tiene el máximo de 3 cuentas");
        }else{
            log.info("Cliente tiene " + cuentasActuales + " cuentas");
        }

        // 3. Generar número de cuenta único
        String numCuenta = generarNumeroCuentaUnico();

        // 4. Crear cuenta
        Cuenta cuenta = new Cuenta();
        cuenta.setNumCuenta(numCuenta);
        cuenta.setCliente(cliente);
        cuenta.setSaldo(dto.getSaldo());
        cuenta = cuentaRepository.save(cuenta);

        return toDTO(cuenta);
    }

    public List<CuentaDTO> listarCuentas() {
        return cuentaRepository.findAll().stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public CuentaDTO obtenerCuenta(String numCuenta) {
        Cuenta cuenta = cuentaRepository.findById(numCuenta)
                .orElseThrow(() -> new IllegalArgumentException("Cuenta no encontrada"));
        return toDTO(cuenta);
    }

    public List<CuentaDTO> obtenerCuentasPorCliente(String cedula) {
        return cuentaRepository.findByClienteCedula(cedula).stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    @Transactional
    public void eliminarCuenta(String numCuenta) {
        if (!cuentaRepository.existsById(numCuenta)) {
            throw new IllegalArgumentException("Cuenta no encontrada");
        }
        cuentaRepository.deleteById(numCuenta);
    }

    // === MÉTODOS AUXILIARES ===

    private String generarNumeroCuentaUnico() {
        String numero;
        do {
            numero = String.format("%08d", random.nextInt(100000000));
        } while (cuentaRepository.existsById(numero));
        return numero;
    }

    private CuentaDTO toDTO(Cuenta cuenta) {
        CuentaDTO dto = new CuentaDTO();
        dto.setNumCuenta(cuenta.getNumCuenta());
        dto.setCedula(cuenta.getCliente().getCedula());
        dto.setSaldo(cuenta.getSaldo());
        return dto;
    }
}