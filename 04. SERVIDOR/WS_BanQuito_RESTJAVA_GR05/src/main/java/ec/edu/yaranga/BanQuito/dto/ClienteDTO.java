package ec.edu.yaranga.BanQuito.dto;

import lombok.Data;
import java.time.LocalDate;
import java.util.List;

@Data
public class ClienteDTO {
    private String cedula;
    private String nombre;
    private LocalDate fechaNacimiento;
    private String estadoCivil; // C=Casado, S=Soltero
    private List<CuentaDTO> cuentas;
}