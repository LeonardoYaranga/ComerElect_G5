package ec.edu.monster.models;

public class ItemCarrito {
    private String codigo;
    private int cantidad;
    private double subtotal;

    public ItemCarrito() {}

    public ItemCarrito(String codigo, int cantidad, double subtotal) {
        this.codigo = codigo;
        this.cantidad = cantidad;
        this.subtotal = subtotal;
    }

    // Getters and Setters
    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public double getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(double subtotal) {
        this.subtotal = subtotal;
    }
}