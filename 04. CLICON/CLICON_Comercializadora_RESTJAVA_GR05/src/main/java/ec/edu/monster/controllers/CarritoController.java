package ec.edu.monster.controllers;

import ec.edu.monster.models.ItemCarrito;
import ec.edu.monster.services.CarritoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/carrito")
public class CarritoController {

    @Autowired
    private CarritoService carritoService;

    @PostMapping("/agregar")
    public void agregar(@RequestParam String cedula, @RequestParam String codigo, @RequestParam int cantidad) {
        carritoService.agregar(cedula, codigo, cantidad);
    }

    @GetMapping
    public List<ItemCarrito> obtener(@RequestParam String cedula) {
        return carritoService.obtener(cedula);
    }

    @DeleteMapping("/remover/{codigo}")
    public void remover(@PathVariable String codigo, @RequestParam String cedula) {
        carritoService.remover(cedula, codigo);
    }

    @PostMapping("/confirmar")
    public Map<String, Object> confirmar(@RequestParam String cedula, @RequestParam int numeroCuotas) {
        return carritoService.confirmar(cedula, numeroCuotas);
    }
}