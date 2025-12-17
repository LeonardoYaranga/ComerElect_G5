package ec.edu.monster.services;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CarritoService {

    private final RestTemplate restTemplate;
    private final String baseUrl;

    public CarritoService(RestTemplate restTemplate,
            @Value("${electrodomestico.service.url:http://192.168.137.1:8081/api}") String baseUrl) {
        this.restTemplate = restTemplate;
        this.baseUrl = baseUrl;
    }

    /**
     * Agrega un producto al carrito
     */
    public Map<String, Object> agregarProducto(String cedula, String codigo, String nombre, int cantidad,
            BigDecimal precio) {
        String url = baseUrl + "/carrito/agregar";

        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

            MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
            body.add("cedula", cedula);
            body.add("codigo", codigo);
            body.add("nombre", nombre);
            body.add("cantidad", String.valueOf(cantidad));
            body.add("precio", precio.toString());

            HttpEntity<MultiValueMap<String, String>> entity = new HttpEntity<>(body, headers);

            ResponseEntity<Map<String, Object>> response = restTemplate.exchange(
                    url,
                    HttpMethod.POST,
                    entity,
                    new ParameterizedTypeReference<Map<String, Object>>() {
                    });

            return response.getBody();
        } catch (HttpClientErrorException ex) {
            Map<String, Object> error = new HashMap<>();
            error.put("success", false);
            error.put("message", "Error al agregar producto al carrito: " + ex.getMessage());
            return error;
        } catch (Exception ex) {
            Map<String, Object> error = new HashMap<>();
            error.put("success", false);
            error.put("message", "Error de conexión: " + ex.getMessage());
            return error;
        }
    }

    /**
     * Obtiene el carrito de un cliente
     */
    public Map<String, Object> obtenerCarrito(String cedula) {
        String url = baseUrl + "/carrito?cedula=" + cedula;

        try {
            ResponseEntity<Map<String, Object>> response = restTemplate.exchange(
                    url,
                    HttpMethod.GET,
                    null,
                    new ParameterizedTypeReference<Map<String, Object>>() {
                    });

            return response.getBody();
        } catch (HttpClientErrorException ex) {
            Map<String, Object> error = new HashMap<>();
            error.put("items", List.of());
            return error;
        } catch (Exception ex) {
            Map<String, Object> error = new HashMap<>();
            error.put("items", List.of());
            return error;
        }
    }

    /**
     * Obtiene la cantidad de items en el carrito
     */
    public int obtenerCantidadItems(String cedula) {
        Map<String, Object> carrito = obtenerCarrito(cedula);
        if (carrito != null && carrito.containsKey("items")) {
            List<?> items = (List<?>) carrito.get("items");
            return items.size();
        }
        return 0;
    }

    /**
     * Remueve un producto del carrito
     */
    public Map<String, Object> removerProducto(String cedula, String codigo) {
        String url = baseUrl + "/carrito/remover/" + codigo + "?cedula=" + cedula;

        try {
            ResponseEntity<Map<String, Object>> response = restTemplate.exchange(
                    url,
                    HttpMethod.DELETE,
                    null,
                    new ParameterizedTypeReference<Map<String, Object>>() {
                    });

            return response.getBody();
        } catch (HttpClientErrorException ex) {
            Map<String, Object> error = new HashMap<>();
            error.put("success", false);
            error.put("message", "Error al remover producto: " + ex.getMessage());
            return error;
        } catch (Exception ex) {
            Map<String, Object> error = new HashMap<>();
            error.put("success", false);
            error.put("message", "Error de conexión: " + ex.getMessage());
            return error;
        }
    }

    /**
     * Confirma la compra y genera la factura
     */
    public Map<String, Object> confirmarCompra(String cedula, int numeroCuotas) {
        String url = baseUrl + "/carrito/confirmar?cedula=" + cedula + "&numeroCuotas=" + numeroCuotas;

        try {
            ResponseEntity<Map<String, Object>> response = restTemplate.exchange(
                    url,
                    HttpMethod.POST,
                    null,
                    new ParameterizedTypeReference<Map<String, Object>>() {
                    });

            return response.getBody();
        } catch (HttpClientErrorException ex) {
            Map<String, Object> error = new HashMap<>();
            error.put("success", false);

            // Intentar extraer mensaje del error
            String errorBody = ex.getResponseBodyAsString();
            if (errorBody != null && !errorBody.isEmpty()) {
                error.put("error", errorBody);
            } else {
                error.put("error", "Error al confirmar compra: " + ex.getMessage());
            }
            return error;
        } catch (Exception ex) {
            Map<String, Object> error = new HashMap<>();
            error.put("success", false);
            error.put("error", "Error de conexión: " + ex.getMessage());
            return error;
        }
    }
}
