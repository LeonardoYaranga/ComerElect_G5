package ec.edu.monster.services;

import ec.edu.monster.models.ApiResponse;
import ec.edu.monster.models.Electrodomestico;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.core.io.ByteArrayResource;
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
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

@Service
public class ElectrodomesticoService {

    private final RestTemplate restTemplate;
    private final String baseUrl;

    public ElectrodomesticoService(RestTemplate restTemplate, @Value("${electrodomestico.service.url:http://192.168.137.1:8081/api}") String baseUrl) {
        this.restTemplate = restTemplate;
        this.baseUrl = baseUrl;
    }

    public List<Electrodomestico> listarElectrodomesticos() {
        String url = baseUrl + "/electrodomesticos";
        ResponseEntity<List<Electrodomestico>> response = restTemplate.exchange(
                url,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<Electrodomestico>>() {}
        );
        return response.getBody();
    }

    public ApiResponse crearElectrodomestico(String codigo, String nombre, String precio, String descripcion, MultipartFile imagen) {
        String url = baseUrl + "/electrodomesticos";
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.MULTIPART_FORM_DATA);

            MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
            body.add("codigo", codigo);
            body.add("nombre", nombre);
            body.add("precio", precio);
            if (descripcion != null && !descripcion.isEmpty()) {
                body.add("descripcion", descripcion);
            }
            
            if (imagen != null && !imagen.isEmpty()) {
                ByteArrayResource fileResource = new ByteArrayResource(imagen.getBytes()) {
                    @Override
                    public String getFilename() {
                        return imagen.getOriginalFilename();
                    }
                };
                body.add("imagen", fileResource);
            }

            HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, headers);
            
            ResponseEntity<Electrodomestico> response = restTemplate.exchange(
                    url,
                    HttpMethod.POST,
                    requestEntity,
                    Electrodomestico.class
            );
            
            if (response.getStatusCode().is2xxSuccessful()) {
                return new ApiResponse(true, "Electrodoméstico creado exitosamente");
            } else {
                return new ApiResponse(false, "Error al crear electrodoméstico");
            }
        } catch (HttpClientErrorException ex) {
            String responseBody = ex.getResponseBodyAsString();
            String message = extractMessageFromError(responseBody);
            return new ApiResponse(false, message);
        } catch (Exception ex) {
            return new ApiResponse(false, "Error: " + ex.getMessage());
        }
    }

    public Electrodomestico obtenerElectrodomesticoPorCodigo(String codigo) {
        String url = baseUrl + "/electrodomesticos/" + codigo;
        ResponseEntity<Electrodomestico> response = restTemplate.exchange(
                url,
                HttpMethod.GET,
                null,
                Electrodomestico.class
        );
        return response.getBody();
    }

    public ApiResponse actualizarElectrodomesticoPorCodigo(String codigo, String nombre, String precio, String descripcion, MultipartFile imagen) {
        String url = baseUrl + "/electrodomesticos/" + codigo;
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.MULTIPART_FORM_DATA);

            MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
            body.add("nombre", nombre);
            body.add("precio", precio);
            if (descripcion != null && !descripcion.isEmpty()) {
                body.add("descripcion", descripcion);
            }
            
            if (imagen != null && !imagen.isEmpty()) {
                ByteArrayResource fileResource = new ByteArrayResource(imagen.getBytes()) {
                    @Override
                    public String getFilename() {
                        return imagen.getOriginalFilename();
                    }
                };
                body.add("imagen", fileResource);
            }

            HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, headers);
            
            ResponseEntity<Electrodomestico> response = restTemplate.exchange(
                    url,
                    HttpMethod.PUT,
                    requestEntity,
                    Electrodomestico.class
            );
            
            if (response.getStatusCode().is2xxSuccessful()) {
                return new ApiResponse(true, "Electrodoméstico actualizado exitosamente");
            } else {
                return new ApiResponse(false, "Error al actualizar electrodoméstico");
            }
        } catch (HttpClientErrorException ex) {
            String responseBody = ex.getResponseBodyAsString();
            String message = extractMessageFromError(responseBody);
            return new ApiResponse(false, message);
        } catch (Exception ex) {
            return new ApiResponse(false, "Error: " + ex.getMessage());
        }
    }

    public ApiResponse eliminarElectrodomesticoPorCodigo(String codigo) {
        String url = baseUrl + "/electrodomesticos/" + codigo;
        ResponseEntity<ApiResponse> response = restTemplate.exchange(
                url,
                HttpMethod.DELETE,
                null,
                ApiResponse.class
        );
        return response.getBody();
    }

    private String extractMessageFromError(String responseBody) {
        String message = "Error desconocido";
        if (responseBody.contains("\"message\":\"")) {
            int start = responseBody.indexOf("\"message\":\"") + 11;
            int end = responseBody.indexOf("\"", start);
            if (end > start) {
                message = responseBody.substring(start, end);
            }
        }
        return message;
    }
}