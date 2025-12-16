package ec.edu.monster.controllers;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ClienteController {

    @GetMapping("/productos")
    public String productos(HttpSession session, Model model) {
        String cedula = (String) session.getAttribute("Cedula");
        String usuario = (String) session.getAttribute("Usuario");
        
        if (cedula == null) {
            return "redirect:/login";
        }
        
        model.addAttribute("cedula", cedula);
        model.addAttribute("usuario", usuario);
        
        return "productos";
    }

    @GetMapping("/carrito")
    public String carrito(HttpSession session, Model model) {
        String cedula = (String) session.getAttribute("Cedula");
        String usuario = (String) session.getAttribute("Usuario");
        String rol = (String) session.getAttribute("Rol");
        
        if (cedula == null) {
            return "redirect:/login";
        }
        
        // Los clientes pueden ver el carrito
        model.addAttribute("cedula", cedula);
        model.addAttribute("usuario", usuario);
        model.addAttribute("rol", rol);
        
        return "carrito";
    }
}
