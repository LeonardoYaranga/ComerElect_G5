package ec.edu.monster.models;

public class Usuario {
    private String username;
    private String cedula;
    private String nombre;
    private String password;
    private String rol; // "ADMIN" o "CLIENTE"

    public Usuario() {}

    public Usuario(String username, String cedula, String nombre, String password, String rol) {
        this.username = username;
        this.cedula = cedula;
        this.nombre = nombre;
        this.password = password;
        this.rol = rol;
    }

    // Getters and Setters
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getCedula() {
        return cedula;
    }

    public void setCedula(String cedula) {
        this.cedula = cedula;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }
}