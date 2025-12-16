package ec.edu.monster.models;

import java.math.BigDecimal;
import java.util.List;
import java.util.ArrayList;
import jakarta.validation.constraints.*;

public class CrearFacturaViewModel {
    @NotBlank(message = "La cédula es requerida")
    @Size(min = 10, max = 10, message = "La cédula debe tener 10 dígitos")
    @Pattern(regexp = "^\\d{10}$", message = "La cédula debe contener solo números")
    private String cedula;

    private ClienteInfo cliente;

    private List<ProductoCarrito> productosCarrito = new ArrayList<>();

    @NotBlank(message = "Debe seleccionar un tipo de pago")
    private String tipoPago;

    @Min(value = 3, message = "El número de cuotas debe estar entre 3 y 24")
    @Max(value = 24, message = "El número de cuotas debe estar entre 3 y 24")
    private Integer numeroCuotas;

    private BigDecimal subtotal;
    private BigDecimal iva;
    private BigDecimal descuento;
    private BigDecimal total;

    private boolean esSujetoCredito;
    private BigDecimal montoMaximoCredito;
    private BigDecimal cuotaMensual;
    private BigDecimal tasaInteresAnual;

    // Getters and Setters
    public String getCedula() {
        return cedula;
    }

    public void setCedula(String cedula) {
        this.cedula = cedula;
    }

    public ClienteInfo getCliente() {
        return cliente;
    }

    public void setCliente(ClienteInfo cliente) {
        this.cliente = cliente;
    }

    public List<ProductoCarrito> getProductosCarrito() {
        return productosCarrito;
    }

    public void setProductosCarrito(List<ProductoCarrito> productosCarrito) {
        this.productosCarrito = productosCarrito;
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

    public BigDecimal getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(BigDecimal subtotal) {
        this.subtotal = subtotal;
    }

    public BigDecimal getIva() {
        return iva;
    }

    public void setIva(BigDecimal iva) {
        this.iva = iva;
    }

    public BigDecimal getDescuento() {
        return descuento;
    }

    public void setDescuento(BigDecimal descuento) {
        this.descuento = descuento;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    public boolean isEsSujetoCredito() {
        return esSujetoCredito;
    }

    public void setEsSujetoCredito(boolean esSujetoCredito) {
        this.esSujetoCredito = esSujetoCredito;
    }

    public BigDecimal getMontoMaximoCredito() {
        return montoMaximoCredito;
    }

    public void setMontoMaximoCredito(BigDecimal montoMaximoCredito) {
        this.montoMaximoCredito = montoMaximoCredito;
    }

    public BigDecimal getCuotaMensual() {
        return cuotaMensual;
    }

    public void setCuotaMensual(BigDecimal cuotaMensual) {
        this.cuotaMensual = cuotaMensual;
    }

    public BigDecimal getTasaInteresAnual() {
        return tasaInteresAnual;
    }

    public void setTasaInteresAnual(BigDecimal tasaInteresAnual) {
        this.tasaInteresAnual = tasaInteresAnual;
    }
}