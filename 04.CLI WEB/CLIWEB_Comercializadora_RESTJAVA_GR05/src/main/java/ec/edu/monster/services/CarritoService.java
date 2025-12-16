package ec.edu.monster.services;

import ec.edu.monster.models.ProductoCarrito;
import ec.edu.monster.models.SolicitudFactura;
import ec.edu.monster.models.DetalleFacturaRequest;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class CarritoService {
    
    // Mapa: cedula -> lista de productos en carrito
    private final Map<String, List<ProductoCarrito>> carritos = new HashMap<>();
    
    /**
     * Agrega un producto al carrito del cliente
     */
    public void agregarProducto(String cedula, ProductoCarrito producto) {
        carritos.putIfAbsent(cedula, new ArrayList<>());
        List<ProductoCarrito> carrito = carritos.get(cedula);
        
        // Si el producto ya existe, incrementar cantidad
        Optional<ProductoCarrito> existente = carrito.stream()
                .filter(p -> p.getCodigo().equals(producto.getCodigo()))
                .findFirst();
        
        if (existente.isPresent()) {
            existente.get().setCantidad(existente.get().getCantidad() + producto.getCantidad());
        } else {
            carrito.add(producto);
        }
    }
    
    /**
     * Obtiene el carrito del cliente
     */
    public List<ProductoCarrito> obtenerCarrito(String cedula) {
        return carritos.getOrDefault(cedula, new ArrayList<>());
    }
    
    /**
     * Remueve un producto del carrito
     */
    public void removerProducto(String cedula, String codigo) {
        List<ProductoCarrito> carrito = carritos.get(cedula);
        if (carrito != null) {
            carrito.removeIf(p -> p.getCodigo().equals(codigo));
            if (carrito.isEmpty()) {
                carritos.remove(cedula);
            }
        }
    }
    
    /**
     * Limpia el carrito del cliente
     */
    public void limpiarCarrito(String cedula) {
        carritos.remove(cedula);
    }
    
    /**
     * Convierte el carrito en una SolicitudFactura para crear la factura
     * Tipo de pago será "C" (crédito) para cliente
     * Limpia el carrito después
     */
    public SolicitudFactura confirmarCarrito(String cedula, Integer numeroCuotas) {
        List<ProductoCarrito> carrito = obtenerCarrito(cedula);
        
        if (carrito.isEmpty()) {
            throw new IllegalArgumentException("El carrito está vacío");
        }
        
        // Convertir productos a detalles de factura
        List<DetalleFacturaRequest> detalles = carrito.stream()
                .map(p -> {
                    DetalleFacturaRequest d = new DetalleFacturaRequest();
                    d.setCodigo(p.getCodigo());
                    d.setCantidad(p.getCantidad());
                    return d;
                })
                .collect(Collectors.toList());
        
        // Crear solicitud con tipo "C" (crédito) para cliente
        SolicitudFactura solicitud = new SolicitudFactura();
        solicitud.setCedula(cedula);
        solicitud.setTipoPago("C");
        solicitud.setNumeroCuotas(numeroCuotas);
        solicitud.setDetalles(detalles);
        
        // Limpiar carrito
        limpiarCarrito(cedula);
        
        return solicitud;
    }
}
