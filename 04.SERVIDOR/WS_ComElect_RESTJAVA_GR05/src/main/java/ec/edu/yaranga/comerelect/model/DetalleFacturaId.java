package ec.edu.yaranga.comerelect.model;


import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Data;
import java.io.Serializable;

@Embeddable
@Data
public class DetalleFacturaId implements Serializable {
    @Column(name = "num_factura", length = 15)
    private String numFactura;

    @Column(name = "codigo", length = 10)
    private String codigo;
}