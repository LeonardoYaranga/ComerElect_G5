package ec.edu.monster.models;

import java.math.BigDecimal;
import java.util.List;
import java.util.ArrayList;

public class DashboardViewModel {
    private int totalFacturasHoy;
    private BigDecimal totalVendidoEfectivo = BigDecimal.ZERO;
    private BigDecimal totalVendidoCredito = BigDecimal.ZERO;
    private List<ProductoMasVendido> productosMasVendidos = new ArrayList<>();
    private String nombreUsuario;

    // Getters and Setters
    public int getTotalFacturasHoy() {
        return totalFacturasHoy;
    }

    public void setTotalFacturasHoy(int totalFacturasHoy) {
        this.totalFacturasHoy = totalFacturasHoy;
    }

    public BigDecimal getTotalVendidoEfectivo() {
        return totalVendidoEfectivo;
    }

    public void setTotalVendidoEfectivo(BigDecimal totalVendidoEfectivo) {
        this.totalVendidoEfectivo = totalVendidoEfectivo;
    }

    public BigDecimal getTotalVendidoCredito() {
        return totalVendidoCredito;
    }

    public void setTotalVendidoCredito(BigDecimal totalVendidoCredito) {
        this.totalVendidoCredito = totalVendidoCredito;
    }

    public List<ProductoMasVendido> getProductosMasVendidos() {
        return productosMasVendidos;
    }

    public void setProductosMasVendidos(List<ProductoMasVendido> productosMasVendidos) {
        this.productosMasVendidos = productosMasVendidos;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }
}