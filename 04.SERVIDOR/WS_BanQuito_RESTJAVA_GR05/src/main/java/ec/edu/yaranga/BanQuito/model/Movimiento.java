package ec.edu.yaranga.BanQuito.model;


import jakarta.persistence.*;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "MOVIMIENTO")
@Data
public class Movimiento {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cod_movimiento")
    private Integer codMovimiento;

    @ManyToOne
    @JoinColumn(name = "num_cuenta")
    private Cuenta cuenta;

    @Column(name = "tipo", length = 3)
    private String tipo; // DEP (Deposito), RET  (Retiro)

    @Column(name = "valor", precision = 10, scale = 2)
    private BigDecimal valor;

    @Column(name = "fecha")
    private LocalDate fecha;
}