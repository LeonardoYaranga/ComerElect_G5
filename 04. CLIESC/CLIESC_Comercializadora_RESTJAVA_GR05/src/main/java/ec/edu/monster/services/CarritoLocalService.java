package ec.edu.monster.services;

import ec.edu.monster.models.CarritoItem;
import ec.edu.monster.models.DetalleFacturaRequest;
import ec.edu.monster.models.SolicitudFactura;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Servicio Singleton para gestionar el carrito de compras en memoria local
 */
public class CarritoLocalService {

    private static CarritoLocalService instance;
    private Map<String, CarritoItem> items; // Key: codigo del producto

    private CarritoLocalService() {
        this.items = new HashMap<>();
    }

    /**
     * Obtiene la instancia única del servicio
     */
    public static synchronized CarritoLocalService getInstance() {
        if (instance == null) {
            instance = new CarritoLocalService();
        }
        return instance;
    }

    /**
     * Agrega un producto al carrito o incrementa la cantidad si ya existe
     */
    public void agregarProducto(CarritoItem item) {
        if (item == null || item.getCodigo() == null) {
            throw new IllegalArgumentException("El item y su código no pueden ser nulos");
        }

        String codigo = item.getCodigo();

        if (items.containsKey(codigo)) {
            // Si ya existe, incrementar la cantidad
            CarritoItem existente = items.get(codigo);
            existente.setCantidad(existente.getCantidad() + item.getCantidad());
        } else {
            // Si no existe, agregar nuevo item
            items.put(codigo, item);
        }
    }

    /**
     * Remueve un producto del carrito por su código
     */
    public void removerProducto(String codigo) {
        items.remove(codigo);
    }

    /**
     * Obtiene todos los items del carrito
     */
    public List<CarritoItem> obtenerItems() {
        return new ArrayList<>(items.values());
    }

    /**
     * Obtiene la cantidad total de items (diferentes productos)
     */
    public int obtenerCantidadItems() {
        return items.size();
    }

    /**
     * Calcula el precio total del carrito
     */
    public BigDecimal obtenerTotal() {
        return items.values().stream()
                .map(CarritoItem::getSubtotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    /**
     * Vacía completamente el carrito
     */
    public void vaciarCarrito() {
        items.clear();
    }

    /**
     * Verifica si el carrito está vacío
     */
    public boolean estaVacio() {
        return items.isEmpty();
    }

    /**
     * Convierte el carrito actual a una SolicitudFactura para enviar al servidor
     */
    public SolicitudFactura convertirASolicitudFactura(String cedula, String tipoPago, Integer numeroCuotas) {
        if (estaVacio()) {
            throw new IllegalStateException("El carrito está vacío");
        }

        SolicitudFactura solicitud = new SolicitudFactura();
        solicitud.setCedula(cedula);
        solicitud.setTipoPago(tipoPago);
        solicitud.setNumeroCuotas(numeroCuotas);

        List<DetalleFacturaRequest> detalles = new ArrayList<>();
        for (CarritoItem item : items.values()) {
            DetalleFacturaRequest detalle = new DetalleFacturaRequest();
            detalle.setCodigo(item.getCodigo());
            detalle.setCantidad(item.getCantidad());
            detalles.add(detalle);
        }
        solicitud.setDetalles(detalles);

        return solicitud;
    }
}
