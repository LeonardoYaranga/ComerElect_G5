package ec.edu.yaranga.comerelect.controller;

import ec.edu.yaranga.comerelect.dto.FacturaRequest;
import ec.edu.yaranga.comerelect.dto.FacturaResponse;
import ec.edu.yaranga.comerelect.dto.banquito.AmortizacionDTO;
import ec.edu.yaranga.comerelect.service.FacturaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:9090")
@RequestMapping("/api/facturas")
@RequiredArgsConstructor
public class FacturaController {

    private final FacturaService facturaService;

    @PostMapping
    public FacturaResponse generarFactura(@RequestBody @Valid FacturaRequest request) {

        // Validación condicional de numeroCuotas
        if ("C".equals(request.getTipoPago())) {
            if (request.getNumeroCuotas() == null) {
                throw new IllegalArgumentException("El número de cuotas es obligatorio para pago a crédito");
            }
            if (request.getNumeroCuotas() < 3 || request.getNumeroCuotas() > 24) {
                throw new IllegalArgumentException("El número de cuotas debe estar entre 3 y 24");
            }
        } else if ("E".equals(request.getTipoPago())) {
            if (request.getNumeroCuotas() != null) {
                throw new IllegalArgumentException("El número de cuotas no debe enviarse para pago en efectivo");
            }
        }
        return facturaService.generarFactura(request);
    }

    // ÍTEM 9: Consultar una factura
    @GetMapping("/{numFactura}")
    public FacturaResponse obtenerFactura(@PathVariable String numFactura) {
        return facturaService.obtenerFactura(numFactura);
    }

    // ÍTEM 9: Listar todas
    @GetMapping
    public List<FacturaResponse> obtenerTodas() {
        return facturaService.obtenerTodas();
    }

    // ÍTEM 11: Tabla de amortización
    @GetMapping("/{numFactura}/amortizacion")
    public ResponseEntity<List<AmortizacionDTO>> obtenerAmortizacion(@PathVariable String numFactura) {
        List<AmortizacionDTO> tabla = facturaService.obtenerAmortizacion(numFactura);
        return ResponseEntity.ok(tabla);
    }
}