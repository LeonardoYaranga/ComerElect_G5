package ec.edu.yaranga.comerelect.model;

import jakarta.persistence.*;
import lombok.Data;
import java.math.BigDecimal;

@Entity
@Table(name = "ELECTRODOMESTICO")
@Data
public class Electrodomestico {
    @Id
    @Column(name = "codigo", length = 10)
    private String codigo;

    @Column(name = "nombre", length = 100, nullable = false, unique = true)
    private String nombre;

    @Column(name = "precio", precision = 10, scale = 2, nullable = false)
    private BigDecimal precio;

    @Column(name = "descripcion", length = 255)
    private String descripcion;

    @Column(name = "image_url", length = 500)
    private String imageUrl;
}
