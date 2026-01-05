package ec.edu.monster.models;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.ArrayList;

public class ConsultarFacturasViewModel {
    private String numeroFactura;
    private String cedula;
    private LocalDateTime fechaInicio;
    private LocalDateTime fechaFin;
    private String tipoPago;
    private List<FacturaResumen> facturas = new ArrayList<>();

    // Getters and Setters
    public String getNumeroFactura() {
        return numeroFactura;
    }

    public void setNumeroFactura(String numeroFactura) {
        this.numeroFactura = numeroFactura;
    }

    public String getCedula() {
        return cedula;
    }

    public void setCedula(String cedula) {
        this.cedula = cedula;
    }

    public LocalDateTime getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(LocalDateTime fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public LocalDateTime getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(LocalDateTime fechaFin) {
        this.fechaFin = fechaFin;
    }

    public String getTipoPago() {
        return tipoPago;
    }

    public void setTipoPago(String tipoPago) {
        this.tipoPago = tipoPago;
    }

    public List<FacturaResumen> getFacturas() {
        return facturas;
    }

    public void setFacturas(List<FacturaResumen> facturas) {
        this.facturas = facturas;
    }
}