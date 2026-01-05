package ec.edu.yaranga.BanQuito.service;

import ec.edu.yaranga.BanQuito.dto.ClienteDTO;
import ec.edu.yaranga.BanQuito.dto.CuentaDTO;
import ec.edu.yaranga.BanQuito.model.Cliente;
import ec.edu.yaranga.BanQuito.model.Cuenta;
import ec.edu.yaranga.BanQuito.repository.ClienteRepository;
import ec.edu.yaranga.BanQuito.repository.CuentaRepository;
import ec.edu.yaranga.BanQuito.utils.CedulaValidator;
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
public class ClienteService {

    private final ClienteRepository clienteRepository;
    private final CuentaRepository cuentaRepository;
    private final Random random = new Random();

    @Transactional
    public ClienteDTO crearCliente(ClienteDTO dto) {
        // 1. Validar cédula
        log.info("Creando Cliente DTO");
        if (!CedulaValidator.esCedulaValida(dto.getCedula())) {
            throw new IllegalArgumentException("Cédula inválida");
        } else {
            log.info("Cedula valida: ");
        }

        // 2. Verificar si ya existe
        if (clienteRepository.existsById(dto.getCedula())) {
            throw new IllegalArgumentException("Cliente con cédula " + dto.getCedula() + " ya existe");
        }else {
            log.info("CLiente no repetido");
        }

        // 3. Crear cliente
        Cliente cliente = new Cliente();
        cliente.setCedula(dto.getCedula());
        cliente.setNombre(dto.getNombre());
        cliente.setFechaNacimiento(dto.getFechaNacimiento());
        cliente.setEstadoCivil(dto.getEstadoCivil());
        cliente = clienteRepository.save(cliente);

        // 4. Crear cuenta automática
        String numCuenta = generarNumeroCuentaUnico();
        Cuenta cuenta = new Cuenta();
        cuenta.setNumCuenta(numCuenta);
        cuenta.setCliente(cliente);
        cuenta.setSaldo(BigDecimal.ZERO);
        cuentaRepository.save(cuenta);
        cliente.getCuentas().add(cuenta);
        return toDTO(cliente);
    }

    public List<ClienteDTO> listarClientes() {
        return clienteRepository.findAll().stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public ClienteDTO obtenerCliente(String cedula) {
        Cliente cliente = clienteRepository.findById(cedula)
                .orElseThrow(() -> new IllegalArgumentException("Cliente no encontrado"));
        return toDTO(cliente);
    }

    @Transactional
    public ClienteDTO actualizarCliente(String cedula, ClienteDTO dto) {
        Cliente cliente = clienteRepository.findById(cedula)
                .orElseThrow(() -> new IllegalArgumentException("Cliente no encontrado"));

        cliente.setNombre(dto.getNombre());
        cliente.setFechaNacimiento(dto.getFechaNacimiento());
        cliente.setEstadoCivil(dto.getEstadoCivil());
        cliente = clienteRepository.save(cliente);

        return toDTO(cliente);
    }

    @Transactional
    public void eliminarCliente(String cedula) {
        if (!clienteRepository.existsById(cedula)) {
            throw new IllegalArgumentException("Cliente no encontrado");
        }
        clienteRepository.deleteById(cedula);
    }

    // === MÉTODOS AUXILIARES ===

    private String generarNumeroCuentaUnico() {
        String numero;
        do {
            numero = String.format("%08d", random.nextInt(100000000));
        } while (cuentaRepository.existsById(numero));
        return numero;
    }

    private ClienteDTO toDTO(Cliente cliente) {
        ClienteDTO dto = new ClienteDTO();
        dto.setCedula(cliente.getCedula());
        dto.setNombre(cliente.getNombre());
        dto.setFechaNacimiento(cliente.getFechaNacimiento());
        dto.setEstadoCivil(cliente.getEstadoCivil());

        List<CuentaDTO> cuentasDTO = cliente.getCuentas().stream()
                .map(c -> {
                    CuentaDTO cDto = new CuentaDTO();
                    cDto.setNumCuenta(c.getNumCuenta());
                    cDto.setSaldo(c.getSaldo());
                    return cDto;
                })
                .collect(Collectors.toList());
        dto.setCuentas(cuentasDTO);
        return dto;
    }
}