package ec.edu.yaranga.BanQuito.controller;

import ec.edu.yaranga.BanQuito.dto.AmortizacionDTO;
import ec.edu.yaranga.BanQuito.dto.CreditoDTO;
import ec.edu.yaranga.BanQuito.dto.CreditoRequest;
import ec.edu.yaranga.BanQuito.model.Amortizacion;
import ec.edu.yaranga.BanQuito.model.Credito;
import ec.edu.yaranga.BanQuito.service.CreditoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/credito")
@RequiredArgsConstructor
public class CreditoController {

    private final CreditoService creditoService;

    // ÍTEM 5
    @GetMapping("/sujeto/{cedula}")
    public ResponseEntity<Boolean> esSujetoDeCredito(@PathVariable String cedula) {
        return ResponseEntity.ok(creditoService.esSujetoDeCredito(cedula));
    }

    // ÍTEM 6
    @GetMapping("/monto-maximo/{cedula}")
    public ResponseEntity<BigDecimal> obtenerMontoMaximo(@PathVariable String cedula) {
        return ResponseEntity.ok(creditoService.calcularMontoMaximo(cedula));
    }

    // ÍTEM 7
    @PostMapping("/otorgar")
    public ResponseEntity<CreditoDTO> otorgarCredito(
            @RequestBody CreditoRequest request) {
        CreditoDTO credito = creditoService.otorgarCredito(
                request.cedula(),
                request.precioCredito(),
                request.numeroCuotas()
        );
        return ResponseEntity.ok(credito);
    }

    // ÍTEM 10
    @GetMapping("/amortizacion/{idCredito}")
    public ResponseEntity<List<AmortizacionDTO>> obtenerTabla(@PathVariable Integer idCredito) {
        return ResponseEntity.ok(creditoService.obtenerTablaAmortizacion(idCredito));
    }
}