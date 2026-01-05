# 🔧 Debug: Creación de Electrodomésticos

## ✅ Sistema de Logging Implementado

Se agregó logging **completo y detallado** en toda la cadena de creación/actualización:

### **Tags de Log:**

- `ECREAR` → Logs de creación de electrodomésticos
- `EACTUALIZAR` → Logs de actualización de electrodomésticos

---

## 🔍 Cómo Ver los Logs

### **Paso 1: Abrir Logcat**

1. Android Studio → Pestaña **Logcat** (abajo)
2. Filtra por: `ECREAR` o `EACTUALIZAR`
3. Intenta crear/editar un electrodoméstico

---

## 📋 Ejemplo de Logs Exitosos

### **Al CREAR un producto:**

```
════════════════════════════════════
D/ECREAR: [ViewModel] Iniciando creación de electrodoméstico
D/ECREAR: [ViewModel] Nombre: Refrigeradora LG
D/ECREAR: [ViewModel] Precio: 1200.50
D/ECREAR: [ViewModel] Descripción: 300L con dispensador
D/ECREAR: [ViewModel] Imagen: SÍ (upload_12345.jpg, 245678 bytes)
D/ECREAR: [Service] Preparando datos multipart
D/ECREAR: [Service] - nombre: Refrigeradora LG
D/ECREAR: [Service] - precio: 1200.50
D/ECREAR: [Service] - descripcion: 300L con dispensador
D/ECREAR: [Service] - imagen: upload_12345.jpg (245678 bytes)
D/ECREAR: [Service] - imagen existe: true
D/ECREAR: [Service] - imagen legible: true
D/ECREAR: [Service] Llamando al controller (Retrofit)...
D/ECREAR: [Service] ✅ Respuesta recibida del backend
D/ECREAR: [Service] - codigo: EREF1234
D/ECREAR: [Service] - imageUrl: https://kgnnvanlxeeqlpdvcylq.supabase.co/...
D/ECREAR: [ViewModel] ✅ Creación exitosa
D/ECREAR: [ViewModel] Código asignado: EREF1234
D/ECREAR: [ViewModel] ImageURL: https://kgnnvanlxeeqlpdvcylq...
════════════════════════════════════
```

---

## ❌ Ejemplo de Logs con Error

### **Si hay error del backend:**

```
════════════════════════════════════
D/ECREAR: [ViewModel] Iniciando creación de electrodoméstico
D/ECREAR: [ViewModel] Nombre: Lavadora
D/ECREAR: [ViewModel] Precio: -50.00
D/ECREAR: [Service] Preparando datos multipart
D/ECREAR: [Service] Llamando al controller (Retrofit)...
E/ECREAR: [Service] ❌ Excepción en Service
E/ECREAR: [Service] Tipo: HttpException
E/ECREAR: [Service] Mensaje: HTTP 400 Bad Request
E/ECREAR: [ViewModel] ❌ ERROR al crear
E/ECREAR: [ViewModel] Mensaje: HTTP 400 Bad Request
E/ECREAR: [ViewModel] Tipo: HttpException
════════════════════════════════════
```

### **Usuario verá:**

Un diálogo con:

- ❌ **"Error al guardar"**
- **Mensaje**: "HTTP 400 Bad Request" (o el mensaje específico del backend)
- Botón "Entendido"

---

## 🎯 Información que Capturan los Logs

### **Nivel ViewModel:**

- ✅ Todos los datos del DTO (nombre, precio, descripción)
- ✅ Si hay imagen y su tamaño
- ✅ Código asignado por el backend
- ✅ ImageURL retornada
- ❌ Mensaje de error completo
- ❌ Tipo de excepción

### **Nivel Service:**

- ✅ Conversión a multipart (RequestBody)
- ✅ Validación de archivo (existe, legible, tamaño)
- ✅ Respuesta del backend (código, imageUrl)
- ❌ Excepción específica antes de propagarla

### **Nivel Controller (Retrofit):**

- Los logs internos de Retrofit (si hay errores HTTP)

---

## 🔍 Diagnóstico de Problemas Comunes

### **Problema 1: "HTTP 400 Bad Request"**

**Logs mostrarán:**

```
E/ECREAR: [Service] Tipo: HttpException
E/ECREAR: [Service] Mensaje: HTTP 400 Bad Request
```

**Causas:**

- Precio negativo o cero
- Nombre vacío
- Formato de datos incorrecto

**Solución:**

- Verificar validaciones en el frontend
- Revisar que el backend acepte multipart/form-data
- Verificar nombres de parámetros (`nombre`, `precio`, `descripcion`, `imagen`)

---

### **Problema 2: "FileNotFoundException" o "No such file"**

**Logs mostrarán:**

```
D/ECREAR: [Service] - imagen existe: false
E/ECREAR: [Service] Tipo: FileNotFoundException
```

**Causas:**

- Archivo temporal fue eliminado antes de enviarse
- URI inválido

**Solución:**

- El código ya crea archivos temporales con `deleteOnExit()`
- Verificar permisos de lectura

---

### **Problema 3: "Content-Type cannot contain wildcard subtype '\*'"**

**Logs mostrarán:**

```
E/ECREAR: [Service] Error Body: {"error":"Solicitud Inválida","message":"Content-Type cannot contain wildcard subtype '*'"}
```

**Causa:**

- El backend no acepta `image/*` como Content-Type
- Necesita tipo MIME específico: `image/jpeg`, `image/png`, etc.

**Solución:**

- ✅ Ya corregido: el código ahora detecta el tipo basado en la extensión del archivo
- Se envía `image/jpeg` para .jpg, `image/png` para .png, etc.

---

### **Problema 4: "HTTP 500 Internal Server Error"**

**Logs mostrarán:**

```
E/ECREAR: [Service] Mensaje: HTTP 500 Internal Server Error
```

**Causas:**

- Error en el backend (procesamiento de imagen, base de datos, etc.)
- Formato de datos inesperado

**Solución:**

- Revisar logs del backend (Spring Boot)
- Verificar que el backend pueda procesar multipart/form-data
- Verificar configuración de Supabase Storage

---

### **Problema 5: "Unable to resolve host"**

**Logs mostrarán:**

```
E/ECREAR: [Service] Mensaje: Unable to resolve host "192.168.137.1"
```

**Causas:**

- Backend no está corriendo
- IP incorrecta
- Sin conexión de red

**Solución:**

- Verificar que el backend esté en `http://192.168.137.1:8081`
- Ping desde el celular: `ping 192.168.137.1`
- Verificar firewall de Windows

---

## 🧪 Pasos para Debuggear

### **Paso 1: Intentar Crear/Editar**

1. Abre la app
2. Intenta crear un electrodoméstico
3. Si falla, aparecerá un diálogo de error

### **Paso 2: Ver Logs Completos**

1. Abre Logcat en Android Studio
2. Filtra por `ECREAR`
3. Lee la secuencia completa de logs

### **Paso 3: Identificar Dónde Falla**

- **Si falla en [ViewModel]** → Problema antes de llegar al Service
- **Si falla en [Service]** → Problema armando multipart o en Retrofit
- **Si no hay logs de [Service]** → Problema en ViewModel

### **Paso 4: Buscar el Error Específico**

Busca líneas con ❌ o `ERROR`:

```
E/ECREAR: [Service] ❌ Excepción en Service
E/ECREAR: [Service] Mensaje: <AQUÍ ESTÁ EL ERROR>
```

---

## 📱 Interfaz de Usuario

### **Indicadores Visuales:**

- **Loading**: Mientras procesa (spinner o botón deshabilitado)
- **Éxito**: Diálogo se cierra automáticamente y se recarga la lista
- **Error**: Aparece diálogo rojo con:
  - ❌ Ícono de error
  - Título "Error al guardar"
  - Mensaje del backend/error
  - Sugerencia de revisar logs

---

## 🎯 Checklist de Debugging

Cuando algo falla:

- [ ] Filtrar Logcat por `ECREAR` o `EACTUALIZAR`
- [ ] Verificar que aparezcan logs de **[ViewModel]**
- [ ] Verificar que aparezcan logs de **[Service]**
- [ ] Buscar líneas con ❌ o ERROR
- [ ] Copiar el mensaje de error completo
- [ ] Si es HTTP 400/500, revisar backend
- [ ] Si es "Unable to resolve host", revisar red
- [ ] Si es "FileNotFoundException", revisar imagen

---

## 🚀 Resultado Esperado

**Cuando TODO funciona:**

1. Usuario llena formulario con imagen
2. Presiona "Crear"
3. Logs muestran ✅ en [ViewModel] y [Service]
4. Diálogo se cierra
5. Lista se recarga con el nuevo producto
6. Imagen se ve correctamente

**Cuando algo falla:**

1. Usuario llena formulario
2. Presiona "Crear"
3. Logs muestran ❌ con mensaje específico
4. Aparece diálogo de error con el mensaje
5. Usuario puede corregir y reintentar

---

## 📞 Comparte los Logs

Si encuentras un error, **copia y pega** la sección completa de logs entre las líneas:

```
════════════════════════════════════
... todos los logs ...
════════════════════════════════════
```

Eso permite identificar el problema exacto! 🎯
