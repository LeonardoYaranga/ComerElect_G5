package ec.edu.monster.services;

import ec.edu.monster.models.Electrodomestico;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.core.io.FileSystemResource;
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

import java.io.File;
import java.util.List;

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

    public Electrodomestico crearElectrodomestico(Electrodomestico electrodomestico) {
        String url = baseUrl + "/electrodomesticos";
        HttpEntity<Electrodomestico> entity = new HttpEntity<>(electrodomestico);
        try {
            ResponseEntity<Electrodomestico> response = restTemplate.exchange(
                    url,
                    HttpMethod.POST,
                    entity,
                    Electrodomestico.class
            );
            return response.getBody();
        } catch (HttpClientErrorException ex) {
            throw new RuntimeException("Error al crear electrodoméstico: " + ex.getMessage());
        }
    }
    
    public Electrodomestico crearElectrodomesticoConImagen(String nombre, String precio, String descripcion, File imagenFile) {
        String url = baseUrl + "/electrodomesticos";
        
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.MULTIPART_FORM_DATA);
            
            MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
            body.add("nombre", nombre);
            body.add("precio", precio);
            if (descripcion != null && !descripcion.isEmpty()) {
                body.add("descripcion", descripcion);
            }
            if (imagenFile != null) {
                body.add("imagen", new FileSystemResource(imagenFile));
            }
            
            HttpEntity<MultiValueMap<String, Object>> entity = new HttpEntity<>(body, headers);
            
            ResponseEntity<Electrodomestico> response = restTemplate.exchange(
                    url,
                    HttpMethod.POST,
                    entity,
                    Electrodomestico.class
            );
            return response.getBody();
        } catch (HttpClientErrorException ex) {
            throw new RuntimeException("Error al crear electrodoméstico: " + ex.getMessage());
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

    public Electrodomestico actualizarElectrodomesticoPorCodigo(String codigo, Electrodomestico electrodomestico) {
        String url = baseUrl + "/electrodomesticos/" + codigo;
        HttpEntity<Electrodomestico> entity = new HttpEntity<>(electrodomestico);
        try {
            ResponseEntity<Electrodomestico> response = restTemplate.exchange(
                    url,
                    HttpMethod.PUT,
                    entity,
                    Electrodomestico.class
            );
            return response.getBody();
        } catch (HttpClientErrorException ex) {
            throw new RuntimeException("Error al actualizar electrodoméstico: " + ex.getMessage());
        }
    }
    
    public Electrodomestico actualizarElectrodomesticoConImagen(String codigo, String nombre, String precio, String descripcion, File imagenFile) {
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
            if (imagenFile != null) {
                body.add("imagen", new FileSystemResource(imagenFile));
            }
            
            HttpEntity<MultiValueMap<String, Object>> entity = new HttpEntity<>(body, headers);
            
            ResponseEntity<Electrodomestico> response = restTemplate.exchange(
                    url,
                    HttpMethod.PUT,
                    entity,
                    Electrodomestico.class
            );
            return response.getBody();
        } catch (HttpClientErrorException ex) {
            throw new RuntimeException("Error al actualizar electrodoméstico: " + ex.getMessage());
        }
    }

    public Electrodomestico eliminarElectrodomesticoPorCodigo(String codigo) {
        String url = baseUrl + "/electrodomesticos/" + codigo;
        try {
            ResponseEntity<Electrodomestico> response = restTemplate.exchange(
                    url,
                    HttpMethod.DELETE,
                    null,
                    Electrodomestico.class
            );
            return response.getBody();
        } catch (HttpClientErrorException ex) {
            throw new RuntimeException("Error al eliminar electrodoméstico: " + ex.getMessage());
        }
    }
}