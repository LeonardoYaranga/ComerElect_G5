package ec.edu.yaranga.comerelect.service;

import ec.edu.yaranga.comerelect.dto.ElectrodomesticoDTO;
import ec.edu.yaranga.comerelect.exception.ResourceNotFoundException;
import ec.edu.yaranga.comerelect.model.Electrodomestico;
import ec.edu.yaranga.comerelect.repository.ElectrodomesticoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ElectrodomesticoService {

    private final ElectrodomesticoRepository repository;
    private final SupabaseStorageService supabaseStorageService;
    private final Random random = new Random();

    // === LISTAR TODOS ===
    public List<ElectrodomesticoDTO> listar() {
        return repository.findAll().stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    // === OBTENER UNO POR CÓDIGO ===
    public ElectrodomesticoDTO obtener(String codigo) {
        Electrodomestico entity = repository.findById(codigo)
                .orElseThrow(() -> new ResourceNotFoundException("Electrodoméstico no encontrado: " + codigo));
        return toDTO(entity);
    }

    // ================== CREAR ==================
    public ElectrodomesticoDTO crear(ElectrodomesticoDTO dto, MultipartFile imagen) {
        validarCampos(dto);
        validarNombreUnico(dto.getNombre(), null); // null = no hay código aún

        String codigo = generarCodigoUnico(dto.getNombre());
        Electrodomestico entity = new Electrodomestico();
        entity.setCodigo(codigo);
        entity.setNombre(dto.getNombre());
        entity.setPrecio(dto.getPrecio());
        entity.setDescripcion(dto.getDescripcion());

        // Procesar imagen si se envió
        if (imagen != null && !imagen.isEmpty()) {
            try {
                String fileId = supabaseStorageService.uploadImage(imagen);
                String imageUrl = supabaseStorageService.buildPublicUrl(fileId);
                entity.setImageUrl(imageUrl);
            } catch (IOException e) {
                throw new RuntimeException("Error al procesar la imagen: " + e.getMessage());
            }
        }

        entity = repository.save(entity);
        return toDTO(entity);
    }

    // ================== EDITAR ==================
    public ElectrodomesticoDTO actualizar(String codigo, ElectrodomesticoDTO dto, MultipartFile imagen) {
        Electrodomestico existente = repository.findById(codigo)
                .orElseThrow(() -> new ResourceNotFoundException("Electrodoméstico no encontrado: " + codigo));

        validarCampos(dto);
        validarNombreUnico(dto.getNombre(), codigo); // Excluir el actual

        existente.setNombre(dto.getNombre());
        existente.setPrecio(dto.getPrecio());
        existente.setDescripcion(dto.getDescripcion());

        // Procesar nueva imagen si se envió
        if (imagen != null && !imagen.isEmpty()) {
            // Eliminar imagen anterior si existe
            if (existente.getImageUrl() != null) {
                String oldFileId = supabaseStorageService.extractFileIdFromUrl(existente.getImageUrl());
                supabaseStorageService.deleteImage(oldFileId);
            }

            // Subir nueva imagen
            try {
                String fileId = supabaseStorageService.uploadImage(imagen);
                String imageUrl = supabaseStorageService.buildPublicUrl(fileId);
                existente.setImageUrl(imageUrl);
            } catch (IOException e) {
                throw new RuntimeException("Error al procesar la imagen: " + e.getMessage());
            }
        }
        // código NO cambia

        existente = repository.save(existente);
        return toDTO(existente);
    }

    // ================== VALIDACIONES ==================
    private void validarCampos(ElectrodomesticoDTO dto) {
        if (dto.getNombre() == null || dto.getNombre().trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre es obligatorio");
        }
        if (dto.getPrecio() == null || dto.getPrecio().compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("El precio debe ser mayor a 0");
        }
    }

    private void validarNombreUnico(String nombre, String codigoActual) {
        if (repository.existsByNombreAndCodigoNot(nombre.trim(), codigoActual)) {
            throw new IllegalArgumentException("Ya existe un electrodoméstico con el nombre: " + nombre);
        }
    }

    // ================== CÓDIGO ÚNICO ==================
    private String generarCodigoUnico(String nombre) {
        String base = nombre.trim().toUpperCase().replaceAll("[^A-Z]", "");
        String letras = base.length() >= 3 ? base.substring(0, 3) : String.format("%-3s", base).replace(' ', 'X');
        String codigo;
        do {
            int num = 1000 + random.nextInt(9000);
            codigo = "E" + letras + num;
        } while (repository.existsById(codigo));
        return codigo;
    }

    // === ELIMINAR ===
    public void eliminar(String codigo) {
        Electrodomestico entity = repository.findById(codigo)
                .orElseThrow(() -> new RuntimeException("Electrodoméstico no encontrado: " + codigo));

        // Eliminar imagen de Supabase si existe
        if (entity.getImageUrl() != null) {
            String fileId = supabaseStorageService.extractFileIdFromUrl(entity.getImageUrl());
            supabaseStorageService.deleteImage(fileId);
        }

        repository.deleteById(codigo);
    }

    // === MAPPER: Entity → DTO ===
    private ElectrodomesticoDTO toDTO(Electrodomestico e) {
        ElectrodomesticoDTO dto = new ElectrodomesticoDTO();
        dto.setCodigo(e.getCodigo());
        dto.setNombre(e.getNombre());
        dto.setPrecio(e.getPrecio());
        dto.setDescripcion(e.getDescripcion());
        dto.setImageUrl(e.getImageUrl());
        return dto;
    }

    // === MAPPER: DTO → Entity ===
    private Electrodomestico toEntity(ElectrodomesticoDTO dto) {
        Electrodomestico entity = new Electrodomestico();
        entity.setCodigo(dto.getCodigo());
        entity.setNombre(dto.getNombre());
        entity.setPrecio(dto.getPrecio());
        entity.setDescripcion(dto.getDescripcion());
        entity.setImageUrl(dto.getImageUrl());
        return entity;
    }
}