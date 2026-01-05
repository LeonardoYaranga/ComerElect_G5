package ec.edu.yaranga.comerelect.controller;

import ec.edu.yaranga.comerelect.dto.ElectrodomesticoDTO;
import ec.edu.yaranga.comerelect.dto.ResponseDto;
import ec.edu.yaranga.comerelect.service.ElectrodomesticoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:9090")
@RequestMapping("/api/electrodomesticos")
@RequiredArgsConstructor
public class ElectrodomesticoController {

    private final ElectrodomesticoService service;

    @GetMapping
    public List<ElectrodomesticoDTO> listar() {
        return service.listar();
    }

    @GetMapping("/{codigo}")
    public ElectrodomesticoDTO obtener(@PathVariable String codigo) {
        return service.obtener(codigo);
    }

    @PostMapping(consumes = {"multipart/form-data"})
    @ResponseStatus(HttpStatus.CREATED)
    public ElectrodomesticoDTO crear(
            @RequestParam("nombre") String nombre,
            @RequestParam("precio") BigDecimal precio,
            @RequestParam(value = "descripcion", required = false) String descripcion,
            @RequestParam(value = "imagen", required = false) MultipartFile imagen) {
        
        ElectrodomesticoDTO dto = new ElectrodomesticoDTO();
        dto.setNombre(nombre);
        dto.setPrecio(precio);
        dto.setDescripcion(descripcion);
        
        return service.crear(dto, imagen);
    }

    @PutMapping(value = "/{codigo}", consumes = {"multipart/form-data"})
    public ElectrodomesticoDTO actualizar(
            @PathVariable String codigo,
            @RequestParam("nombre") String nombre,
            @RequestParam("precio") BigDecimal precio,
            @RequestParam(value = "descripcion", required = false) String descripcion,
            @RequestParam(value = "imagen", required = false) MultipartFile imagen) {
        
        ElectrodomesticoDTO dto = new ElectrodomesticoDTO();
        dto.setNombre(nombre);
        dto.setPrecio(precio);
        dto.setDescripcion(descripcion);
        
        return service.actualizar(codigo, dto, imagen);
    }

    @DeleteMapping("/{codigo}")
    public ResponseEntity<ResponseDto> eliminar(@PathVariable String codigo) {
        service.eliminar(codigo);
        ResponseDto responseDto = new ResponseDto();
        responseDto.setMessage("Electrodomestico con codigo " + codigo + "eliminado exitosamente.");
        return ResponseEntity.ok(responseDto);
    }
}