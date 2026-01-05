package ec.edu.monster.controllers;

import ec.edu.monster.services.ElectrodomesticoService;
import ec.edu.monster.services.FacturaService;
import ec.edu.monster.services.UsuarioService;
import ec.edu.monster.services.CarritoService;
import ec.edu.monster.views.ConsoleMenu;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

/**
 * Controlador principal de la aplicación que coordina entre servicios y vistas
 * siguiendo el patrón MVC.
 */
@Controller
public class CliConComercializadoraController {

    private final ElectrodomesticoService electrodomesticoService;
    private final FacturaService facturaService;
    private final UsuarioService usuarioService;
    private final CarritoService carritoService;

    @Autowired
    public CliConComercializadoraController(ElectrodomesticoService electrodomesticoService,
                                           FacturaService facturaService,
                                           UsuarioService usuarioService,
                                           CarritoService carritoService) {
        this.electrodomesticoService = electrodomesticoService;
        this.facturaService = facturaService;
        this.usuarioService = usuarioService;
        this.carritoService = carritoService;
    }

    /**
     * Inicia la aplicación mostrando el menú principal
     */
    public void iniciarAplicacion() {
        ConsoleMenu menu = new ConsoleMenu(electrodomesticoService, facturaService, usuarioService, carritoService);
        menu.showMenu();
    }
}