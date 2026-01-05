package ec.edu.yaranga.BanQuito.model;


import jakarta.persistence.*;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "CREDITO")
@Data
public class Credito {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_credito")
    private Integer idCredito;

    @ManyToOne
    @JoinColumn(name = "cedula_cliente")
    private Cliente cliente;

    @Column(name = "monto_prestamo", precision = 12, scale = 2) //Q agarraria el precio producto
    private BigDecimal monto;

    @Column(name = "numero_cuotas")
    private Integer plazoMeses;

    @Column(name = "valor_cuota_fija", precision = 10, scale = 2)
    private BigDecimal valorCuotaFija;

    @Column(name = "tasa_interes_anual", precision = 5, scale = 2)
    private BigDecimal tasaAnual = BigDecimal.valueOf(16.00);

    @Column(name = "fecha_aprobacion")
    private LocalDate fechaOtorgamiento = LocalDate.now();

    @Column(name = "estado", length = 1)
    private String estado = "A"; // A=Activo  I=Inactivo

    @OneToMany(mappedBy = "credito", cascade = CascadeType.ALL ,orphanRemoval = true)
    private List<Amortizacion> amortizaciones = new ArrayList<>();
}