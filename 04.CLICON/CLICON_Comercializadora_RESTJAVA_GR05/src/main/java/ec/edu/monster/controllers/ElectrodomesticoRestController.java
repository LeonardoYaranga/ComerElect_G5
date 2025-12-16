package ec.edu.monster.controllers;

import ec.edu.monster.models.Electrodomestico;
import ec.edu.monster.services.ElectrodomesticoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/electrodomesticos")
public class ElectrodomesticoRestController {

    @Autowired
    private ElectrodomesticoService electrodomesticoService;

    @GetMapping
    public List<Electrodomestico> listarElectrodomesticos() {
        return electrodomesticoService.listarElectrodomesticos();
    }
}