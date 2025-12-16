package ec.edu.yaranga.comerelect.service;

import ec.edu.yaranga.comerelect.dto.banquito.AmortizacionDTO;
import ec.edu.yaranga.comerelect.dto.banquito.ClienteDTO;
import ec.edu.yaranga.comerelect.dto.banquito.CreditoDTO;
import ec.edu.yaranga.comerelect.dto.banquito.CreditoRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.List;

@Service
public class BanQuitoClient {

    private final RestTemplate restTemplate;
    private final String baseUrl;

    public BanQuitoClient(@Value("${banquito.url}") String baseUrl) {
        this.restTemplate = new RestTemplate();
        this.baseUrl = baseUrl;
    }

    public boolean esSujetoDeCredito(String cedula) {
        String url = baseUrl + "/sujeto/" + cedula;
        ResponseEntity<Boolean> response = restTemplate.getForEntity(url, Boolean.class);
        return Boolean.TRUE.equals(response.getBody());
    }

    public BigDecimal obtenerMontoMaximo(String cedula) {
        String url = baseUrl + "/monto-maximo/" + cedula;
        ResponseEntity<BigDecimal> response = restTemplate.getForEntity(url, BigDecimal.class);
        return response.getBody();
    }

    public CreditoDTO otorgarCredito(CreditoRequest request) {
        String url = baseUrl + "/otorgar";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<CreditoRequest> entity = new HttpEntity<>(request, headers);
        ResponseEntity<CreditoDTO> response = restTemplate.postForEntity(url, entity, CreditoDTO.class);
        return response.getBody();
    }

    public List<AmortizacionDTO> obtenerTablaAmortizacion(Integer idCredito) {
        String url = baseUrl + "/amortizacion/" + idCredito;
        ResponseEntity<AmortizacionDTO[]> response =
                restTemplate.getForEntity(url, AmortizacionDTO[].class);
        return List.of(response.getBody());
    }

    public ClienteDTO obtenerCliente(String cedula) {
        String url = baseUrl.replace("/credito", "/clientes") + "/" + cedula;
        ResponseEntity<ClienteDTO> response = restTemplate.getForEntity(url, ClienteDTO.class);
        return response.getBody();
    }
}