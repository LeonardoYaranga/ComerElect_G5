package ec.edu.monster.models;

import jakarta.validation.constraints.NotBlank;

public class LoginViewModel {
    @NotBlank(message = "El usuario es requerido")
    private String usuario;

    @NotBlank(message = "La contraseña es requerida")
    private String contrasena;

    private boolean recordarMe;

    // Getters and Setters
    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }

    public boolean isRecordarMe() {
        return recordarMe;
    }

    public void setRecordarMe(boolean recordarMe) {
        this.recordarMe = recordarMe;
    }
}