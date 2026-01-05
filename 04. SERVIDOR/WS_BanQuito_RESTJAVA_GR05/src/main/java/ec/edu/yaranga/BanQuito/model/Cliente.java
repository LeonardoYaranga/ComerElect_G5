package ec.edu.yaranga.BanQuito.model;


import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "CLIENTE")
@Data
public class Cliente {
    @Id
    @Column(name = "cedula", length = 10)
    private String cedula;

    @Column(name = "nombre", length = 100)
    private String nombre;

    @Column(name = "fecha_nacimiento")
    private LocalDate fechaNacimiento;

    @Column(name = "estado_civil", length = 1)
    private String estadoCivil; // C=Casado, S=Soltero

    @OneToMany(mappedBy = "cliente")
    private List<Cuenta> cuentas = new ArrayList<>();

    @OneToMany(mappedBy = "cliente")
    private List<Credito> creditos = new ArrayList<>();
}