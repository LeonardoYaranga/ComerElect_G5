package ec.edu.monster.controllers;

import ec.edu.monster.models.FacturaResumen;
import ec.edu.monster.services.FacturaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/facturas")
public class FacturaController {

    @Autowired
    private FacturaService facturaService;

    @GetMapping("/cliente/{cedula}")
    public List<FacturaResumen> obtenerFacturasPorCedula(@PathVariable String cedula) {
        return facturaService.obtenerFacturasPorCedula(cedula);
    }
}