package ec.edu.monster.controllers;

import ec.edu.monster.models.ProductoCarrito;
import ec.edu.monster.models.SolicitudFactura;
import ec.edu.monster.services.CarritoService;
import ec.edu.monster.services.FacturaService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/carrito")
public class CarritoController {

    private final CarritoService carritoService;
    private final FacturaService facturaService;

    public CarritoController(CarritoService carritoService, FacturaService facturaService) {
        this.carritoService = carritoService;
        this.facturaService = facturaService;
    }

    /**
     * Agrega un producto al carrito
     * GET /api/carrito/agregar?cedula=xxx&codigo=yyy&cantidad=z&nombre=nnn&precio=ppp
     */
    @PostMapping("/agregar")
    public ResponseEntity<?> agregarProducto(
            @RequestParam String cedula,
            @RequestParam String codigo,
            @RequestParam int cantidad,
            @RequestParam String nombre,
            @RequestParam String precio) {
        
        try {
            ProductoCarrito producto = new ProductoCarrito();
            producto.setCodigo(codigo);
            producto.setNombre(nombre);
            producto.setCantidad(cantidad);
            producto.setPrecioUnitario(new java.math.BigDecimal(precio));
            
            carritoService.agregarProducto(cedula, producto);
            
            Map<String, Object> respuesta = new HashMap<>();
            respuesta.put("success", true);
            respuesta.put("mensaje", "Producto agregado al carrito");
            respuesta.put("carrito", carritoService.obtenerCarrito(cedula));
            
            return ResponseEntity.ok(respuesta);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("success", false, "error", e.getMessage()));
        }
    }

    /**
     * Obtiene el carrito del cliente
     * GET /api/carrito?cedula=xxx
     */
    @GetMapping
    public ResponseEntity<?> obtenerCarrito(@RequestParam String cedula) {
        try {
            List<ProductoCarrito> carrito = carritoService.obtenerCarrito(cedula);
            
            Map<String, Object> respuesta = new HashMap<>();
            respuesta.put("success", true);
            respuesta.put("items", carrito);
            respuesta.put("cantidad", carrito.size());
            respuesta.put("total", carrito.stream()
                    .map(ProductoCarrito::getSubtotal)
                    .reduce(java.math.BigDecimal.ZERO, java.math.BigDecimal::add));
            
            return ResponseEntity.ok(respuesta);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("success", false, "error", e.getMessage()));
        }
    }

    /**
     * Remueve un producto del carrito
     * DELETE /api/carrito/remover/{codigo}?cedula=xxx
     */
    @DeleteMapping("/remover/{codigo}")
    public ResponseEntity<?> removerProducto(
            @PathVariable String codigo,
            @RequestParam String cedula) {
        
        try {
            carritoService.removerProducto(cedula, codigo);
            
            Map<String, Object> respuesta = new HashMap<>();
            respuesta.put("success", true);
            respuesta.put("mensaje", "Producto removido del carrito");
            respuesta.put("carrito", carritoService.obtenerCarrito(cedula));
            
            return ResponseEntity.ok(respuesta);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("success", false, "error", e.getMessage()));
        }
    }

    /**
     * Confirma la compra y genera factura
     * POST /api/carrito/confirmar?cedula=xxx&numeroCuotas=z
     */
    @PostMapping("/confirmar")
    public ResponseEntity<?> confirmarCompra(
            @RequestParam String cedula,
            @RequestParam int numeroCuotas) {
        
        try {
            // Convertir carrito a solicitud de factura
            SolicitudFactura solicitud = carritoService.confirmarCarrito(cedula, numeroCuotas);
            
            // Crear factura en el servidor SOAP
            Map<String, Object> respuestaFactura = facturaService.crearFactura(solicitud);
            
            Map<String, Object> respuesta = new HashMap<>();
            respuesta.put("success", true);
            respuesta.put("mensaje", "Compra confirmada exitosamente");
            respuesta.put("factura", respuestaFactura);
            
            return ResponseEntity.ok(respuesta);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("success", false, "error", e.getMessage()));
        }
    }
}
