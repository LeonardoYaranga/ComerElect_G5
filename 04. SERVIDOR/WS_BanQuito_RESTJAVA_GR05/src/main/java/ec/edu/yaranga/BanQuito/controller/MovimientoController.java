package ec.edu.yaranga.BanQuito.controller;

import ec.edu.yaranga.BanQuito.dto.MovimientoDTO;
import ec.edu.yaranga.BanQuito.dto.ResponseDto;
import ec.edu.yaranga.BanQuito.service.MovimientoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/movimientos")
@RequiredArgsConstructor
public class MovimientoController {

    private final MovimientoService movimientoService;

    @PostMapping
    public ResponseEntity<MovimientoDTO> crear(@RequestBody MovimientoDTO dto) {
        MovimientoDTO creado = movimientoService.crearMovimiento(dto);
        return ResponseEntity.ok(creado);
    }

    @GetMapping
    public ResponseEntity<List<MovimientoDTO>> listar() {
        return ResponseEntity.ok(movimientoService.listarMovimientos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<MovimientoDTO> obtener(@PathVariable Integer id) {
        return ResponseEntity.ok(movimientoService.obtenerMovimiento(id));
    }

    @GetMapping("/cuenta/{numCuenta}")
    public ResponseEntity<List<MovimientoDTO>> porCuenta(@PathVariable String numCuenta) {
        return ResponseEntity.ok(movimientoService.obtenerMovimientosPorCuenta(numCuenta));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseDto> eliminar(@PathVariable Integer id) {
        movimientoService.eliminarMovimiento(id);
        ResponseDto responseDto = new ResponseDto();
        responseDto.setMessage("Movimiento" + id + "eliminado exitosamente.");
        return ResponseEntity.ok(responseDto);
    }
}