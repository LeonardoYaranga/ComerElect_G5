package ec.edu.monster.models;

import java.util.List;

public class FacturasResponse {
    private List<DetalleFacturaViewModel> facturas;

    public List<DetalleFacturaViewModel> getFacturas() {
        return facturas;
    }

    public void setFacturas(List<DetalleFacturaViewModel> facturas) {
        this.facturas = facturas;
    }
}