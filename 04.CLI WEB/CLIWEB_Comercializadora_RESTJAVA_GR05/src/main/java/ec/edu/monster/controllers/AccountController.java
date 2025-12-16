package ec.edu.monster.controllers;

import ec.edu.monster.models.LoginViewModel;
import ec.edu.monster.models.Usuario;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.List;

@Controller
public class AccountController {

    private static final List<Usuario> USUARIOS_QUEMADOS = new ArrayList<>();

    static {
        // Admin
        USUARIOS_QUEMADOS.add(new Usuario("MONSTER", "1111111111", "Administrador", "MONSTER9", "ADMIN"));
        // Clientes - Cédulas de la tabla de clientes
        USUARIOS_QUEMADOS.add(new Usuario("JOEL", "0102030405", "Juan Pérez", "JOEL9", "CLIENTE"));
        USUARIOS_QUEMADOS.add(new Usuario("DOME", "0203040506", "María López", "DOME9", "CLIENTE"));
        USUARIOS_QUEMADOS.add(new Usuario("LEO", "0304050607", "Carlos Gómez", "LEO9", "CLIENTE"));
    }

    @GetMapping("/")
    public String home() {
        return "redirect:/login";
    }

    @GetMapping("/login")
    public String login(Model model) {
        model.addAttribute("loginViewModel", new LoginViewModel());
        return "login";
    }

    @PostMapping("/login")
    public String login(@Valid LoginViewModel model,
                       BindingResult bindingResult,
                       HttpSession session,
                       RedirectAttributes redirectAttributes) {
        
        if (bindingResult.hasErrors()) {
            return "login";
        }

        String usuario = model.getUsuario();
        String contrasena = model.getContrasena();

        if (usuario == null || usuario.isEmpty() || contrasena == null || contrasena.isEmpty()) {
            redirectAttributes.addFlashAttribute("error", "Usuario y contraseña son requeridos");
            return "redirect:/login";
        }

        // Buscar usuario con credenciales correctas
        Usuario usuarioEncontrado = USUARIOS_QUEMADOS.stream()
                .filter(u -> u.getUsuario().equals(usuario) && u.getContrasena().equals(contrasena))
                .findFirst()
                .orElse(null);

        if (usuarioEncontrado != null) {
            session.setAttribute("Usuario", usuarioEncontrado.getNombre());
            session.setAttribute("Cedula", usuarioEncontrado.getCedula());
            session.setAttribute("Rol", usuarioEncontrado.getRol());
            return "redirect:/dashboard";
        } else {
            redirectAttributes.addFlashAttribute("error", "Usuario o contraseña incorrectos");
            return "redirect:/login";
        }
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/login";
    }
}