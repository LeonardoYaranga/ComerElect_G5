package ec.edu.yaranga.comerelect.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "FACTURA")
@Data
@ToString(exclude = "detalles")  // ROMPE CICLO
public class Factura {
    @Id
    @Column(name = "num_factura", length = 15)
    private String numFactura;

    @Column(name = "fecha")
    private LocalDate fecha = LocalDate.now();

    @Column(name = "cedula", length = 10)
    private String cedula;

    @Column(name = "total", precision = 12, scale = 2)
    private BigDecimal total;

    @Column(name = "tipo_pago", length = 1)
    private String tipoPago; // E=Efectivo, C=Crédito

    @Column(name = "descuento", precision = 12, scale = 2)
    private BigDecimal descuento = BigDecimal.ZERO;

    @Column(name = "cod_credito_ref", length = 15, nullable = true)
    private String codCreditoRef;

    @OneToMany(mappedBy = "factura", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore  // EVITA CICLO EN JSON
    private List<DetalleFactura> detalles = new ArrayList<>();
}