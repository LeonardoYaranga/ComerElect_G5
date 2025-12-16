package ec.edu.yaranga.comerelect.dto.banquito;

import jakarta.validation.constraints.*;

import java.math.BigDecimal;

public record CreditoRequest(

        @NotBlank(message = "La cédula es obligatoria.")
        @Size(min = 10, max = 10, message = "La cédula debe tener 10 dígitos.")
        String cedula,

        @NotNull(message = "El precio del electrodoméstico es obligatorio.")
        @DecimalMin(value = "0.01", message = "El precio debe ser mayor a cero.")
        BigDecimal precioCredito,

        @NotNull(message = "El número de cuotas es obligatorio.")
        @Min(value = 3, message = "El número de cuotas debe ser al menos 3.")
        @Max(value = 24, message = "El número de cuotas debe ser de maximo 24.")
        Integer numeroCuotas

) {}