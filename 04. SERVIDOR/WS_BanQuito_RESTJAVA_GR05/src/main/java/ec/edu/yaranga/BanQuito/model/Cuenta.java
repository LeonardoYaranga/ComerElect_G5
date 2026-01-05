package ec.edu.yaranga.BanQuito.model;


import jakarta.persistence.*;
import lombok.Data;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "CUENTA")
@Data
public class Cuenta {
    @Id
    @Column(name = "num_cuenta", length = 8)
    private String numCuenta;

    @ManyToOne
    @JoinColumn(name = "cedula")
    private Cliente cliente;

    @Column(name = "saldo", precision = 10, scale = 2)
    private BigDecimal saldo;

    @OneToMany(mappedBy = "cuenta")
    private List<Movimiento> movimientos = new ArrayList<>();
}