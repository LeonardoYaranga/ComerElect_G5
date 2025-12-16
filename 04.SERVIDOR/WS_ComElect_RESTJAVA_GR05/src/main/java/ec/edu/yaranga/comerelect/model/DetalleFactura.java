package ec.edu.yaranga.comerelect.model;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
@Entity
@Table(name = "DETALLE_FACTURA")
@Data
public class DetalleFactura {

    @EmbeddedId
    private DetalleFacturaId id;

    @ManyToOne
    @MapsId("numFactura")
    @JoinColumn(name = "num_factura")
    private Factura factura;

    @ManyToOne
    @MapsId("codigo")
    @JoinColumn(name = "codigo")
    private Electrodomestico electrodomestico;

    @Column(name = "cantidad")
    private Integer cantidad;

    @Column(name = "subtotal", precision = 12, scale = 2)
    private BigDecimal subtotal;
}