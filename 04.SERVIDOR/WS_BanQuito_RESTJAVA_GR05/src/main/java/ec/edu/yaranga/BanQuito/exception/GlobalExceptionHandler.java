package ec.edu.yaranga.BanQuito.exception;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<Map<String, Object>> handleDataIntegrityViolation(DataIntegrityViolationException ex) {

        // 2. Comprobar si la causa es la violación de la clave foránea
        if (ex.getMessage() != null && ex.getMessage().contains("Cannot delete or update a parent row")) {

            // ---> CORRECCIÓN: Mensaje genérico y profesional <---
            String mensajeUsuario = "No es posible eliminar el registro solicitado. Existen dependencias activas (como cuentas, créditos o movimientos) que deben ser resueltas/cerradas primero.";

            Map<String, Object> response = new HashMap<>();
            response.put("error", "Conflicto de Integridad de Datos");
            response.put("message", mensajeUsuario);

            // Devolver HTTP 409 Conflict
            return new ResponseEntity<>(response, HttpStatus.CONFLICT);
        }

        // ... (el resto del código se mantiene igual para otros 500)
        Map<String, Object> errorResponse = new HashMap<>();
        errorResponse.put("error", "Error Interno de Base de Datos");
        errorResponse.put("message", "Ocurrió un error inesperado al procesar la solicitud.");
        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    // ... (el resto de sus handlers como IllegalArgumentException)
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Map<String, Object>> handleIllegalArgumentException(IllegalArgumentException ex) {
        Map<String, Object> response = new HashMap<>();
        response.put("error", "Solicitud Inválida");
        response.put("message", ex.getMessage());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST); // 400 Bad Request
    }
}