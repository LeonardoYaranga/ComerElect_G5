package ec.edu.monster.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ApiResponse {
    private boolean exito;
    private String mensaje;
    private Object data;

    public ApiResponse() {
    }

    public ApiResponse(boolean exito, String mensaje) {
        this.exito = exito;
        this.mensaje = mensaje;
    }

    public ApiResponse(boolean exito, String mensaje, Object data) {
        this.exito = exito;
        this.mensaje = mensaje;
        this.data = data;
    }

    // Getters and Setters
    public boolean isExito() {
        return exito;
    }

    public void setExito(boolean exito) {
        this.exito = exito;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}