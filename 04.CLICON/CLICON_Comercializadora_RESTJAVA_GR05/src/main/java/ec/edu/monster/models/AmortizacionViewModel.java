package ec.edu.monster.models;

import java.math.BigDecimal;
import java.util.List;
import java.util.ArrayList;

public class AmortizacionViewModel {
    private String numFactura;
    private BigDecimal montoTotal;
    private int plazo;
    private BigDecimal tasaInteresAnual;
    private BigDecimal cuotaFijaMensual;
    private List<CuotaAmortizacion> cuotas = new ArrayList<>();

    // Getters and Setters
    public String getNumFactura() {
        return numFactura;
    }

    public void setNumFactura(String numFactura) {
        this.numFactura = numFactura;
    }

    public BigDecimal getMontoTotal() {
        return montoTotal;
    }

    public void setMontoTotal(BigDecimal montoTotal) {
        this.montoTotal = montoTotal;
    }

    public int getPlazo() {
        return plazo;
    }

    public void setPlazo(int plazo) {
        this.plazo = plazo;
    }

    public BigDecimal getTasaInteresAnual() {
        return tasaInteresAnual;
    }

    public void setTasaInteresAnual(BigDecimal tasaInteresAnual) {
        this.tasaInteresAnual = tasaInteresAnual;
    }

    public BigDecimal getCuotaFijaMensual() {
        return cuotaFijaMensual;
    }

    public void setCuotaFijaMensual(BigDecimal cuotaFijaMensual) {
        this.cuotaFijaMensual = cuotaFijaMensual;
    }

    public List<CuotaAmortizacion> getCuotas() {
        return cuotas;
    }

    public void setCuotas(List<CuotaAmortizacion> cuotas) {
        this.cuotas = cuotas;
    }
}