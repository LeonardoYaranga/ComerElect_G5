package ec.edu.monster.controllers;

import ec.edu.monster.models.Electrodomestico;
import ec.edu.monster.services.ElectrodomesticoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Controlador REST para acceso público a electrodomésticos (catálogo)
 * Usa @RestController en lugar de @Controller para retornar JSON directamente
 */
@RestController
@RequestMapping("/api/electrodomesticos")
public class ElectrodomesticoRestController {

    private final ElectrodomesticoService electrodomesticoService;

    public ElectrodomesticoRestController(ElectrodomesticoService electrodomesticoService) {
        this.electrodomesticoService = electrodomesticoService;
    }

    /**
     * Obtiene lista de electrodomésticos para el catálogo (público)
     * GET /api/electrodomesticos
     */
    @GetMapping
    public ResponseEntity<?> listarElectrodomesticos() {
        try {
            List<Electrodomestico> electrodomesticos = electrodomesticoService.listarElectrodomesticos();
            
            Map<String, Object> respuesta = new HashMap<>();
            respuesta.put("success", true);
            respuesta.put("productos", electrodomesticos != null ? electrodomesticos : List.of());
            respuesta.put("cantidad", electrodomesticos != null ? electrodomesticos.size() : 0);
            
            return ResponseEntity.ok(respuesta);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("success", false, "error", "Error al cargar electrodomésticos: " + e.getMessage()));
        }
    }
}
