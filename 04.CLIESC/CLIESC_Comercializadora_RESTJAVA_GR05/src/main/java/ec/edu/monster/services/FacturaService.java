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

    public FacturaService(RestTemplate restTemplate, @Value("${factura.service.url:http://192.168.137.1:8081/api}") String baseUrl) {
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
        HttpEntity<SolicitudFactura> entity = new HttpEntity<>(solicitud);
        ResponseEntity<Map<String, Object>> response = restTemplate.exchange(
                url,
                HttpMethod.POST,
                entity,
                new ParameterizedTypeReference<Map<String, Object>>() {}
        );
        return response.getBody();
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

    public DashboardViewModel obtenerDashboard() {
        String url = baseUrl + "/facturas/dashboard";
        ResponseEntity<DashboardViewModel> response = restTemplate.exchange(
                url,
                HttpMethod.GET,
                null,
                DashboardViewModel.class
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
}