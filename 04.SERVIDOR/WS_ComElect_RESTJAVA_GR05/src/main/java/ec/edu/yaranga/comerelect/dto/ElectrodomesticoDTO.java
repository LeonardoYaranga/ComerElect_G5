package ec.edu.yaranga.comerelect.dto;


import lombok.Data;
import java.math.BigDecimal;

@Data
public class ElectrodomesticoDTO {
    private String codigo;
    private String nombre;
    private BigDecimal precio;
    private String descripcion;
    private String imageUrl;
}