package ec.edu.monster.models;

import java.util.List;
import java.util.ArrayList;

public class SolicitudFactura {
    private String cedula;
    private String tipoPago; // "E" or "C"
    private Integer numeroCuotas;
    private List<DetalleFacturaRequest> detalles = new ArrayList<>();

    // Getters and Setters
    public String getCedula() {
        return cedula;
    }

    public void setCedula(String cedula) {
        this.cedula = cedula;
    }

    public String getTipoPago() {
        return tipoPago;
    }

    public void setTipoPago(String tipoPago) {
        this.tipoPago = tipoPago;
    }

    public Integer getNumeroCuotas() {
        return numeroCuotas;
    }

    public void setNumeroCuotas(Integer numeroCuotas) {
        this.numeroCuotas = numeroCuotas;
    }

    public List<DetalleFacturaRequest> getDetalles() {
        return detalles;
    }

    public void setDetalles(List<DetalleFacturaRequest> detalles) {
        this.detalles = detalles;
    }
}