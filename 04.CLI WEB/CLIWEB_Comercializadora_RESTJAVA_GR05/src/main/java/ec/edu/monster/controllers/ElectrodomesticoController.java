package ec.edu.monster.controllers;

import ec.edu.monster.models.ApiResponse;
import ec.edu.monster.models.Electrodomestico;
import ec.edu.monster.services.ElectrodomesticoService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/electrodomesticos")
public class ElectrodomesticoController {

    private final ElectrodomesticoService electrodomesticoService;

    public ElectrodomesticoController(ElectrodomesticoService electrodomesticoService) {
        this.electrodomesticoService = electrodomesticoService;
    }

    /**
     * Verifica si el usuario es ADMIN
     */
    private boolean esAdmin(HttpSession session) {
        String rol = (String) session.getAttribute("Rol");
        return "ADMIN".equals(rol);
    }

    @GetMapping
    public String listar(Model model) {
        try {
            List<Electrodomestico> electrodomesticos = electrodomesticoService.listarElectrodomesticos();
            model.addAttribute("electrodomesticos", electrodomesticos != null ? electrodomesticos : List.of());
        } catch (Exception ex) {
            model.addAttribute("error", "Error al cargar electrodomésticos: " + ex.getMessage());
            model.addAttribute("electrodomesticos", List.of());
        }
        return "electrodomesticos/index";
    }

    @GetMapping("/crear")
    public String crear(HttpSession session, Model model, RedirectAttributes redirectAttributes) {
        if (!esAdmin(session)) {
            redirectAttributes.addFlashAttribute("error", "No tienes permisos para acceder a esta sección");
            return "redirect:/dashboard";
        }
        model.addAttribute("electrodomestico", new Electrodomestico());
        return "electrodomesticos/crear";
    }

    @PostMapping("/crear")
    public String crear(HttpSession session,
                       @RequestParam("codigo") String codigo,
                       @RequestParam("nombre") String nombre,
                       @RequestParam("precio") String precio,
                       @RequestParam(value = "descripcion", required = false) String descripcion,
                       @RequestParam(value = "imagen", required = false) org.springframework.web.multipart.MultipartFile imagen,
                       Model model, RedirectAttributes redirectAttributes) {
        if (!esAdmin(session)) {
            redirectAttributes.addFlashAttribute("error", "No tienes permisos para realizar esta acción");
            return "redirect:/dashboard";
        }
        try {
            ApiResponse respuesta = electrodomesticoService.crearElectrodomestico(codigo, nombre, precio, descripcion, imagen);
            if (respuesta != null && respuesta.isExito()) {
                redirectAttributes.addFlashAttribute("mensaje", "Electrodoméstico creado exitosamente");
                return "redirect:/electrodomesticos";
            } else {
                model.addAttribute("error", respuesta != null ? respuesta.getMensaje() : "Error desconocido");
                return "electrodomesticos/crear";
            }
        } catch (Exception ex) {
            model.addAttribute("error", "Error al crear electrodoméstico: " + ex.getMessage());
            return "electrodomesticos/crear";
        }
    }

    @GetMapping("/editar/{codigo}")
    public String editar(HttpSession session,
                        @PathVariable String codigo,
                        Model model,
                        RedirectAttributes redirectAttributes) {
        if (!esAdmin(session)) {
            redirectAttributes.addFlashAttribute("error", "No tienes permisos para acceder a esta sección");
            return "redirect:/dashboard";
        }
        try {
            Electrodomestico electrodomestico = electrodomesticoService.obtenerElectrodomesticoPorCodigo(codigo);
            if (electrodomestico == null) {
                return "redirect:/electrodomesticos";
            }
            model.addAttribute("electrodomestico", electrodomestico);
            return "electrodomesticos/editar";
        } catch (Exception ex) {
            model.addAttribute("error", "Error: " + ex.getMessage());
            return "redirect:/electrodomesticos";
        }
    }

    @PostMapping("/editar/{codigo}")
    public String editar(HttpSession session,
                        @PathVariable String codigo,
                        @RequestParam("nombre") String nombre,
                        @RequestParam("precio") String precio,
                        @RequestParam(value = "descripcion", required = false) String descripcion,
                        @RequestParam(value = "imagen", required = false) org.springframework.web.multipart.MultipartFile imagen,
                        Model model, RedirectAttributes redirectAttributes) {
        if (!esAdmin(session)) {
            redirectAttributes.addFlashAttribute("error", "No tienes permisos para realizar esta acción");
            return "redirect:/dashboard";
        }
        try {
            ApiResponse respuesta = electrodomesticoService.actualizarElectrodomesticoPorCodigo(codigo, nombre, precio, descripcion, imagen);
            if (respuesta != null && respuesta.isExito()) {
                redirectAttributes.addFlashAttribute("mensaje", "Electrodoméstico actualizado exitosamente");
                return "redirect:/electrodomesticos";
            } else {
                model.addAttribute("error", respuesta != null ? respuesta.getMensaje() : "Error desconocido");
                // Reload the electrodomestico
                Electrodomestico electrodomestico = electrodomesticoService.obtenerElectrodomesticoPorCodigo(codigo);
                model.addAttribute("electrodomestico", electrodomestico);
                return "electrodomesticos/editar";
            }
        } catch (Exception ex) {
            model.addAttribute("error", "Error al actualizar: " + ex.getMessage());
            Electrodomestico electrodomestico = electrodomesticoService.obtenerElectrodomesticoPorCodigo(codigo);
            model.addAttribute("electrodomestico", electrodomestico);
            return "electrodomesticos/editar";
        }
    }

    @PostMapping("/eliminar")
    public String eliminar(HttpSession session,
                          @RequestParam String codigo,
                          RedirectAttributes redirectAttributes) {
        if (!esAdmin(session)) {
            redirectAttributes.addFlashAttribute("error", "No tienes permisos para realizar esta acción");
            return "redirect:/dashboard";
        }
        try {
            ApiResponse respuesta = electrodomesticoService.eliminarElectrodomesticoPorCodigo(codigo);
            if (respuesta != null && respuesta.isExito()) {
                redirectAttributes.addFlashAttribute("mensaje", "Electrodoméstico eliminado exitosamente");
            } else {
                redirectAttributes.addFlashAttribute("error", respuesta != null ? respuesta.getMensaje() : "Error desconocido");
            }
        } catch (Exception ex) {
            redirectAttributes.addFlashAttribute("error", "Error: " + ex.getMessage());
        }
        return "redirect:/electrodomesticos";
    }

    @GetMapping(value = "/buscar", produces = "application/json")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> buscarPorCodigo(@RequestParam String codigo) {
        try {
            Electrodomestico electrodomestico = electrodomesticoService.obtenerElectrodomesticoPorCodigo(codigo);
            Map<String, Object> response = new HashMap<>();
            if (electrodomestico != null) {
                Map<String, Object> data = new HashMap<>();
                data.put("codigo", electrodomestico.getCodigo());
                data.put("nombre", electrodomestico.getNombre());
                data.put("precio", electrodomestico.getPrecio());
                data.put("descripcion", electrodomestico.getDescripcion());
                data.put("imageUrl", electrodomestico.getImageUrl());
                response.put("success", true);
                response.put("data", data);
            } else {
                response.put("success", false);
                response.put("message", "Producto no encontrado");
            }
            return ResponseEntity.ok(response);
        } catch (Exception ex) {
            Map<String, Object> response = new HashMap<>();
            response.put("success", false);
            response.put("message", ex.getMessage());
            return ResponseEntity.ok(response);
        }
    }
}