package ec.edu.monster.models;

public class Usuario {
    private String usuario;
    private String cedula;
    private String nombre;
    private String contrasena;
    private String rol; // "ADMIN" o "CLIENTE"

    public Usuario(String usuario, String cedula, String nombre, String contrasena, String rol) {
        this.usuario = usuario;
        this.cedula = cedula;
        this.nombre = nombre;
        this.contrasena = contrasena;
        this.rol = rol;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
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

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }
}
