package ec.edu.yaranga.comerelect.dto;

import jakarta.validation.constraints.*;
import lombok.Data;
import java.util.List;

@Data
public class FacturaRequest {
    @NotBlank(message = "La cédula es obligatoria")
    @Size(min = 10, max = 10, message = "La cédula debe tener 10 dígitos")
    private String cedula;
    @NotBlank(message = "El tipo de pago es obligatorio")
    @Pattern(regexp = "E|C", message = "Tipo de pago debe ser 'E' (Efectivo) o 'C' (Crédito)")
    private String tipoPago; // "E" o "C"
    // Solo obligatorio si tipoPago = "C"
    @Min(value = 3, message = "El número de cuotas debe ser al menos 3")
    @Max(value = 24, message = "El número de cuotas debe ser máximo 24")
    private Integer numeroCuotas; // Solo si es "C"
    private List<DetalleRequest> detalles;

    @Data
    public static class DetalleRequest {
        @NotBlank(message = "El código del producto es obligatorio")
        private String codigo;
        @NotNull(message = "La cantidad es obligatoria")
        @Min(value = 1, message = "La cantidad debe ser al menos 1")
        private Integer cantidad;
    }
}
