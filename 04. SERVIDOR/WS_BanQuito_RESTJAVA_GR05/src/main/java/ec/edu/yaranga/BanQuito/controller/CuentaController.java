package ec.edu.yaranga.BanQuito.controller;

import ec.edu.yaranga.BanQuito.dto.CuentaDTO;
import ec.edu.yaranga.BanQuito.dto.ResponseDto;
import ec.edu.yaranga.BanQuito.service.CuentaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cuentas")
@RequiredArgsConstructor
public class CuentaController {

    private final CuentaService cuentaService;

    @PostMapping
    public ResponseEntity<CuentaDTO> crear(@RequestBody CuentaDTO dto) {
        CuentaDTO creada = cuentaService.crearCuenta(dto);
        return ResponseEntity.ok(creada);
    }

    @GetMapping
    public ResponseEntity<List<CuentaDTO>> listar() {
        return ResponseEntity.ok(cuentaService.listarCuentas());
    }

    @GetMapping("/{numCuenta}")
    public ResponseEntity<CuentaDTO> obtener(@PathVariable String numCuenta) {
        return ResponseEntity.ok(cuentaService.obtenerCuenta(numCuenta));
    }

    @GetMapping("/cliente/{cedula}")
    public ResponseEntity<List<CuentaDTO>> obtenerPorCliente(@PathVariable String cedula) {
        return ResponseEntity.ok(cuentaService.obtenerCuentasPorCliente(cedula));
    }

    //No se puede editar parametros

    @DeleteMapping("/{numCuenta}")
    public ResponseEntity<ResponseDto> eliminar(@PathVariable String numCuenta) {
        cuentaService.eliminarCuenta(numCuenta);

        ResponseDto responseDto = new ResponseDto();
        responseDto.setMessage("Cuenta" + numCuenta + "eliminada exitosamente.");
        // Retornar 200 OK con el cuerpo JSON
        return ResponseEntity.ok(responseDto);
    }
}