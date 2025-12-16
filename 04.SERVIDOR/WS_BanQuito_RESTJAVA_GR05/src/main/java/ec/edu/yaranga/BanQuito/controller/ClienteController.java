package ec.edu.yaranga.BanQuito.controller;

import ec.edu.yaranga.BanQuito.dto.ClienteDTO;
import ec.edu.yaranga.BanQuito.service.ClienteService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/clientes")
@RequiredArgsConstructor
public class ClienteController {

    private final ClienteService clienteService;

    @PostMapping
    public ResponseEntity<ClienteDTO> crear(@RequestBody ClienteDTO dto) {
        ClienteDTO creado = clienteService.crearCliente(dto);
        return ResponseEntity.ok(creado);
    }

    @GetMapping
    public ResponseEntity<List<ClienteDTO>> listar() {
        return ResponseEntity.ok(clienteService.listarClientes());
    }

    @GetMapping("/{cedula}")
    public ResponseEntity<ClienteDTO> obtener(@PathVariable String cedula) {
        return ResponseEntity.ok(clienteService.obtenerCliente(cedula));
    }

    @PutMapping("/{cedula}")
    public ResponseEntity<ClienteDTO> actualizar(@PathVariable String cedula, @RequestBody ClienteDTO dto) {
        return ResponseEntity.ok(clienteService.actualizarCliente(cedula, dto));
    }

    @DeleteMapping("/{cedula}")
    public ResponseEntity<Void> eliminar(@PathVariable String cedula) {
        clienteService.eliminarCliente(cedula);
        return ResponseEntity.noContent().build();
    }
}