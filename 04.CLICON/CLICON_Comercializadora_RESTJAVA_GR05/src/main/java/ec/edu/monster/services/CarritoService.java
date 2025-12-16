package ec.edu.monster.services;

import ec.edu.monster.models.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;

@Service
public class CarritoService {

    private final Map<String, List<ItemCarrito>> carritos = new HashMap<>();

    @Autowired
    private ElectrodomesticoService electrodomesticoService;

    @Autowired
    private FacturaService facturaService;

    public void agregar(String cedula, String codigo, int cantidad) {
        Electrodomestico producto = electrodomesticoService.obtenerElectrodomesticoPorCodigo(codigo);
        if (producto == null) {
            throw new RuntimeException("Producto no encontrado");
        }
        double subtotal = producto.getPrecio().doubleValue() * cantidad;

        carritos.putIfAbsent(cedula, new ArrayList<>());
        List<ItemCarrito> items = carritos.get(cedula);

        // Verificar si ya existe, actualizar cantidad y subtotal
        Optional<ItemCarrito> existing = items.stream().filter(i -> i.getCodigo().equals(codigo)).findFirst();
        if (existing.isPresent()) {
            existing.get().setCantidad(existing.get().getCantidad() + cantidad);
            existing.get().setSubtotal(existing.get().getSubtotal() + subtotal);
        } else {
            items.add(new ItemCarrito(codigo, cantidad, subtotal));
        }
    }

    public List<ItemCarrito> obtener(String cedula) {
        return carritos.getOrDefault(cedula, new ArrayList<>());
    }

    public void remover(String cedula, String codigo) {
        List<ItemCarrito> items = carritos.get(cedula);
        if (items != null) {
            items.removeIf(i -> i.getCodigo().equals(codigo));
        }
    }

    public Map<String, Object> confirmar(String cedula, int numeroCuotas) {
        List<ItemCarrito> items = obtener(cedula);
        if (items.isEmpty()) {
            throw new RuntimeException("Carrito vacío");
        }

        SolicitudFactura solicitud = new SolicitudFactura();
        solicitud.setCedula(cedula);
        solicitud.setTipoPago("C");
        solicitud.setNumeroCuotas(numeroCuotas);

        List<DetalleFacturaRequest> detalles = new ArrayList<>();
        for (ItemCarrito item : items) {
            DetalleFacturaRequest detalle = new DetalleFacturaRequest();
            detalle.setCodigo(item.getCodigo());
            detalle.setCantidad(item.getCantidad());
            detalles.add(detalle);
        }
        solicitud.setDetalles(detalles);

        Map<String, Object> response = facturaService.crearFactura(solicitud);
        // Limpiar carrito
        carritos.remove(cedula);
        return response;
    }
}