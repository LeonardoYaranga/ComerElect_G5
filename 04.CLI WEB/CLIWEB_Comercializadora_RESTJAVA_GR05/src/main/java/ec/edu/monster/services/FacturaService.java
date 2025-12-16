package ec.edu.monster.services;

import ec.edu.monster.models.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;

@Service
public class FacturaService {

    private final RestTemplate restTemplate;
    private final String baseUrl;

    public FacturaService(RestTemplate restTemplate, @Value("${factura.service.url:http://192.168.137.1:8080/api}") String baseUrl) {
        this.restTemplate = restTemplate;
        this.baseUrl = baseUrl;
    }

    public List<DetalleFacturaViewModel> listarFacturas() {
        String url = baseUrl + "/facturas";
        ResponseEntity<List<DetalleFacturaViewModel>> response = restTemplate.exchange(
                url,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<DetalleFacturaViewModel>>() {}
        );
        return response.getBody();
    }

    public Map<String, Object> crearFactura(SolicitudFactura solicitud) {
        String url = baseUrl + "/facturas";
        System.out.println("[DEBUG] FacturaService - URL: " + url);
        System.out.println("[DEBUG] FacturaService - Solicitud: " + solicitud.getCedula() + ", TipoPago: " + solicitud.getTipoPago() + ", Cuotas: " + solicitud.getNumeroCuotas());
        
        try {
            HttpEntity<SolicitudFactura> entity = new HttpEntity<>(solicitud);
            ResponseEntity<Map<String, Object>> response = restTemplate.exchange(
                    url,
                    HttpMethod.POST,
                    entity,
                    new ParameterizedTypeReference<Map<String, Object>>() {}
            );
            System.out.println("[DEBUG] FacturaService - Status Code: " + response.getStatusCode());
            System.out.println("[DEBUG] FacturaService - Response Body: " + response.getBody());
            return response.getBody();
        } catch (Exception ex) {
            System.err.println("[ERROR] FacturaService - Error al crear factura: " + ex.getClass().getName());
            System.err.println("[ERROR] FacturaService - Mensaje: " + ex.getMessage());
            ex.printStackTrace();
            throw ex;
        }
    }

    public DetalleFacturaViewModel obtenerFacturaPorNumFactura(String numFactura) {
        String url = baseUrl + "/facturas/" + numFactura;
        ResponseEntity<DetalleFacturaViewModel> response = restTemplate.exchange(
                url,
                HttpMethod.GET,
                null,
                DetalleFacturaViewModel.class
        );
        return response.getBody();
    }

    public List<FacturaResumen> obtenerFacturasPorCedula(String cedula) {
        String url = baseUrl + "/facturas/cliente/" + cedula;
        ResponseEntity<List<FacturaResumen>> response = restTemplate.exchange(
                url,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<FacturaResumen>>() {}
        );
        return response.getBody();
    }

    public List<CuotaAmortizacion> obtenerAmortizacion(String numFactura) {
        String url = baseUrl + "/facturas/" + numFactura + "/amortizacion";
        ResponseEntity<List<CuotaAmortizacion>> response = restTemplate.exchange(
                url,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<CuotaAmortizacion>>() {}
        );
        return response.getBody();
    }

    public Map<String, Object> validarCliente(String cedula) {
        String url = baseUrl + "/facturas/validar-cliente";
        HttpEntity<String> entity = new HttpEntity<>(cedula);
        ResponseEntity<Map<String, Object>> response = restTemplate.exchange(
                url,
                HttpMethod.POST,
                entity,
                new ParameterizedTypeReference<Map<String, Object>>() {}
        );
        return response.getBody();
    }
}