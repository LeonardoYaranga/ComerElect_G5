# Cambios Realizados: Soporte para Imágenes en Electrodomésticos

## 📋 Resumen

Se actualizó el módulo de Electrodomésticos para soportar subida y visualización de imágenes usando **multipart/form-data**.

---

## ✅ Archivos Modificados

### 1. **ElectrodomesticoDTO.kt**

- ✅ Agregado campo `imageUrl: String?` al modelo
- Este campo recibe la URL de la imagen desde el backend

### 2. **ElectrodomesticoController.kt** (Retrofit Interface)

- ✅ Agregados imports: `okhttp3.MultipartBody`, `okhttp3.RequestBody`
- ✅ Método `crear()` ahora usa `@Multipart` con parámetros individuales:
  - `@Part("nombre")` → RequestBody
  - `@Part("precio")` → RequestBody
  - `@Part("descripcion")` → RequestBody (opcional)
  - `@Part imagen` → MultipartBody.Part (opcional)
- ✅ Método `actualizar()` con la misma estructura multipart

### 3. **ElectrodomesticoService.kt**

- ✅ Agregados imports: `android.net.Uri`, `okhttp3.*`, `File`, `FileOutputStream`
- ✅ Método `crear()` actualizado:
  - Acepta parámetros: `dto`, `imageUri`, `imageFile`
  - Convierte campos a `RequestBody` con `toRequestBody()`
  - Crea `MultipartBody.Part` desde el archivo
- ✅ Método `actualizar()` con la misma lógica

### 4. **ElectrodomesticosViewModel.kt**

- ✅ Agregados imports: `android.net.Uri`, `java.io.File`
- ✅ Método `crear()` ahora acepta `imageFile: File?`
- ✅ Método `actualizar()` ahora acepta `imageFile: File?`

### 5. **ElectrodomesticosScreen.kt**

#### Imports nuevos:

- ✅ `android.content.Context`, `android.net.Uri`
- ✅ `androidx.activity.compose.rememberLauncherForActivityResult`
- ✅ `androidx.activity.result.contract.ActivityResultContracts`
- ✅ `androidx.compose.foundation.Image`
- ✅ `androidx.compose.material.icons.filled.Image`
- ✅ `coil.compose.rememberAsyncImagePainter`
- ✅ `java.io.File`, `java.io.FileOutputStream`

#### Lista de productos:

- ✅ Cada card ahora muestra:
  - **Imagen del producto** (80x80dp) si `imageUrl` existe
  - **Placeholder con ícono** si no hay imagen

#### CreateProductDialog:

- ✅ Agregado selector de imagen con `rememberLauncherForActivityResult`
- ✅ Preview de imagen seleccionada (120x120dp)
- ✅ Box clickeable para abrir galería
- ✅ Callback `onCreate` ahora pasa `File?` además del DTO

#### EditProductDialog:

- ✅ Muestra imagen actual del producto o placeholder
- ✅ Permite cambiar la imagen con selector
- ✅ Texto "Toca para cambiar imagen"
- ✅ Callback `onUpdate` ahora pasa `File?` además del DTO

#### Función helper:

- ✅ `uriToFile()`: Convierte URI de galería a File temporal
  - Crea archivo en `context.cacheDir`
  - Copia contenido del InputStream
  - Retorna File listo para enviar

### 6. **build.gradle.kts**

- ✅ Agregada dependencia: `implementation("io.coil-kt:coil-compose:2.5.0")`
- Esta librería carga imágenes desde URL de forma asíncrona

### 7. **AndroidManifest.xml**

- ✅ Agregados permisos:
  - `READ_EXTERNAL_STORAGE` (para Android ≤ 32)
  - `READ_MEDIA_IMAGES` (para Android ≥ 33)

---

## 🎨 Experiencia de Usuario

### Al Listar Productos:

```
┌─────────────────────────────────┐
│ [Imagen 80x80]  Refrigeradora   │
│                 Código: R001    │
│                 Descripción...  │
│                 $850.00         │
│                 [✏️] [🗑️]       │
└─────────────────────────────────┘
```

### Al Crear Producto:

1. Usuario toca FAB "+"
2. Modal se abre con campos + selector de imagen
3. Usuario toca box de imagen → se abre galería
4. Selecciona imagen → preview se muestra
5. Completa campos y presiona "Crear"
6. Se envía multipart con imagen al backend

### Al Editar Producto:

1. Usuario toca ícono de editar
2. Modal muestra datos actuales + imagen actual
3. Usuario puede tocar imagen para cambiarla
4. Al guardar, se envía nueva imagen (si se seleccionó)

---

## 🔧 Cómo Funciona el Multipart

### Backend espera:

```java
@PostMapping(consumes = {"multipart/form-data"})
crear(
    @RequestParam("nombre") String nombre,
    @RequestParam("precio") BigDecimal precio,
    @RequestParam("descripcion") String descripcion,
    @RequestParam("imagen") MultipartFile imagen
)
```

### Android envía:

```kotlin
// 1. Convertir campos a RequestBody
val nombreBody = dto.nombre.toRequestBody("text/plain".toMediaTypeOrNull())
val precioBody = dto.precio.toString().toRequestBody("text/plain".toMediaTypeOrNull())

// 2. Convertir File a MultipartBody.Part
val requestFile = imageFile.asRequestBody("image/*".toMediaTypeOrNull())
val imagePart = MultipartBody.Part.createFormData("imagen", imageFile.name, requestFile)

// 3. Enviar con Retrofit
controller.crear(nombreBody, precioBody, descripcionBody, imagePart)
```

---

## 📱 Próximos Pasos

1. **Sincronizar Gradle**:

   ```bash
   cd "d:\LS\8th\Arq\U1\PruebaPiloto\CLIMOV_ComerElect_RESTJAVA_GR05"
   .\gradlew build
   ```

2. **Ejecutar app** y probar:

   - Crear producto con imagen
   - Ver lista con imágenes cargadas
   - Editar producto y cambiar imagen

3. **Verificar backend**:
   - Confirmar que `/api/electrodomesticos` retorna `imageUrl` en el JSON
   - Confirmar que las URLs son accesibles desde el móvil

---

## 🐛 Posibles Problemas

### "No se carga la imagen"

- ✅ Verificar que `imageUrl` no esté vacío en la respuesta
- ✅ Verificar que la URL sea accesible desde el emulador/dispositivo
- ✅ Si el backend está en localhost, usar `10.0.2.2` (emulador) o IP real (dispositivo físico)

### "Error al subir imagen"

- ✅ Verificar permisos en AndroidManifest
- ✅ Verificar que el backend acepte `multipart/form-data`
- ✅ Revisar logs con tag "Upload" o similar

### "Imagen muy grande"

- Considerar comprimir antes de enviar (futura mejora)
- Límite típico: 5-10 MB

---

## 📚 Referencias

- **Coil**: https://coil-kt.github.io/coil/compose/
- **Retrofit Multipart**: https://square.github.io/retrofit/
- **ActivityResultContracts**: https://developer.android.com/training/basics/intents/result
