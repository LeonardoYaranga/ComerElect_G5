package ec.edu.yaranga.comerelect.service;

import ec.edu.yaranga.comerelect.config.SupabaseConfig;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class SupabaseStorageService {

    private final SupabaseConfig supabaseConfig;
    private final RestTemplate restTemplate = new RestTemplate();

    /**
     * Sube una imagen a Supabase Storage y retorna el ID del archivo
     */
    public String uploadImage(MultipartFile file) throws IOException {
        if (file == null || file.isEmpty()) {
            throw new IllegalArgumentException("El archivo no puede estar vacío");
        }

        // Validar tipo de archivo (solo imágenes)
        String contentType = file.getContentType();
        if (contentType == null || !contentType.startsWith("image/")) {
            throw new IllegalArgumentException("Solo se permiten archivos de imagen");
        }

        // Generar nombre único para el archivo
        String originalFilename = file.getOriginalFilename();
        String extension = originalFilename != null && originalFilename.contains(".")
                ? originalFilename.substring(originalFilename.lastIndexOf("."))
                : ".jpg";
        String fileId = UUID.randomUUID().toString() + extension;

        // Construir URL del endpoint de Supabase Storage
        String uploadUrl = String.format("%s/storage/v1/object/%s/%s",
                supabaseConfig.getUrl(),
                supabaseConfig.getBucket(),
                fileId);

        // Preparar headers
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + supabaseConfig.getKey());
        headers.setContentType(MediaType.parseMediaType(contentType));

        // Crear request con el contenido del archivo
        HttpEntity<byte[]> requestEntity = new HttpEntity<>(file.getBytes(), headers);

        try {
            // Realizar petición POST a Supabase
            ResponseEntity<String> response = restTemplate.exchange(
                    uploadUrl,
                    HttpMethod.POST,
                    requestEntity,
                    String.class
            );

            if (response.getStatusCode().is2xxSuccessful()) {
                log.info("Imagen subida exitosamente con ID: {}", fileId);
                return fileId;
            } else {
                throw new RuntimeException("Error al subir imagen a Supabase: " + response.getStatusCode());
            }
        } catch (Exception e) {
            log.error("Error al subir imagen a Supabase", e);
            throw new RuntimeException("Error al subir imagen: " + e.getMessage());
        }
    }

    /**
     * Construye la URL pública de la imagen a partir del ID del archivo
     */
    public String buildPublicUrl(String fileId) {
        if (fileId == null || fileId.isEmpty()) {
            return null;
        }
        return String.format("%s/storage/v1/object/public/%s/%s",
                supabaseConfig.getUrl(),
                supabaseConfig.getBucket(),
                fileId);
    }

    /**
     * Elimina una imagen de Supabase Storage
     */
    public void deleteImage(String fileId) {
        if (fileId == null || fileId.isEmpty()) {
            return;
        }

        // Construir URL del endpoint de Supabase Storage
        String deleteUrl = String.format("%s/storage/v1/object/%s/%s",
                supabaseConfig.getUrl(),
                supabaseConfig.getBucket(),
                fileId);

        // Preparar headers
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + supabaseConfig.getKey());

        HttpEntity<Void> requestEntity = new HttpEntity<>(headers);

        try {
            // Realizar petición DELETE a Supabase
            ResponseEntity<String> response = restTemplate.exchange(
                    deleteUrl,
                    HttpMethod.DELETE,
                    requestEntity,
                    String.class
            );

            if (response.getStatusCode().is2xxSuccessful()) {
                log.info("Imagen eliminada exitosamente: {}", fileId);
            } else {
                log.warn("No se pudo eliminar la imagen: {}", response.getStatusCode());
            }
        } catch (Exception e) {
            log.error("Error al eliminar imagen de Supabase", e);
            // No lanzamos excepción para no interrumpir otras operaciones
        }
    }

    /**
     * Extrae el ID del archivo desde una URL completa
     */
    public String extractFileIdFromUrl(String imageUrl) {
        if (imageUrl == null || imageUrl.isEmpty()) {
            return null;
        }
        // Extraer el último segmento de la URL
        String[] parts = imageUrl.split("/");
        return parts[parts.length - 1];
    }
}
