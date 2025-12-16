package ec.edu.monster.models;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class FacturaResumen {
    private String numFactura;
    private LocalDateTime fecha;
    private String cedula;
    private BigDecimal total;
    private String tipoPago;
    private String codCreditoRef;

    // Getters and Setters
    public String getNumFactura() {
        return numFactura;
    }

    public void setNumFactura(String numFactura) {
        this.numFactura = numFactura;
    }

    public LocalDateTime getFecha() {
        return fecha;
    }

    public void setFecha(LocalDateTime fecha) {
        this.fecha = fecha;
    }

    public String getCedula() {
        return cedula;
    }

    public void setCedula(String cedula) {
        this.cedula = cedula;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    public String getTipoPago() {
        return tipoPago;
    }

    public void setTipoPago(String tipoPago) {
        this.tipoPago = tipoPago;
    }

    public String getCodCreditoRef() {
        return codCreditoRef;
    }

    public void setCodCreditoRef(String codCreditoRef) {
        this.codCreditoRef = codCreditoRef;
    }

    // Computed property
    public boolean isTieneCredito() {
        return codCreditoRef != null && !codCreditoRef.isEmpty();
    }
}