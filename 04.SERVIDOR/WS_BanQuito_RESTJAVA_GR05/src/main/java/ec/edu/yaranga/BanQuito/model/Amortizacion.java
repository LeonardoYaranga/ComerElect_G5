package ec.edu.yaranga.BanQuito.model;

import jakarta.persistence.*;
import lombok.Data;
import java.math.BigDecimal;

@Entity
@Table(name = "AMORTIZACION")
@Data
public class Amortizacion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_amortizacion")
    private Integer idAmortizacion;

    @ManyToOne
    @JoinColumn(name = "id_credito")
    private Credito credito;

    @Column(name = "numero_cuota")
    private Integer numCuota;

    @Column(name = "valor_cuota", precision = 12, scale = 2)
    private BigDecimal valorCuota;

    @Column(name = "interes", precision = 12, scale = 2)
    private BigDecimal interes;

    @Column(name = "capital_pagado", precision = 12, scale = 2)
    private BigDecimal capital;

    @Column(name = "saldo_pendiente", precision = 12, scale = 2)
    private BigDecimal saldo;
}