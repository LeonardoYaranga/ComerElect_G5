# 🔧 Debug: Imágenes No se Muestran

## ✅ Cambios Aplicados

### 1. **Reemplazado `rememberAsyncImagePainter` por `AsyncImage`**

- `AsyncImage` es más robusto y maneja errores automáticamente
- Agregado manejo explícito de estados: `placeholder`, `error`

### 2. **Agregado permiso de red**

```xml
<uses-permission android:name="android.permission.ACCESS_NETWORK_STATE" />
```

### 3. **Agregado logs de debug**

En `ElectrodomesticosViewModel.load()`:

```kotlin
list.forEach { item ->
    Log.d("EIMAGEN", "Producto: ${item.nombre}, ImageURL: ${item.imageUrl ?: "SIN URL"}")
}
```

---

## 🔍 Cómo Verificar

### **Paso 1: Ver Logs en Logcat**

1. Abre Android Studio
2. Ve a la pestaña **Logcat**
3. Filtra por `EIMAGEN` (para ver datos del ViewModel Y de la UI)
4. Ejecuta la app y ve a la pantalla de Electrodomésticos
5. Verifica la salida:

#### **Logs del ViewModel (carga de datos):**

```
D/EIMAGEN: Producto: Refrigerador Samsung, ImageURL: https://kgnnvanlxeeqlpdvcylq.supabase.co/storage/v1/object/public/Arquitectura/ca3d3e62-1ba0-40a2-9143-4d535a4e83dc.jpeg
D/EIMAGEN: Producto: Lavadora LG, ImageURL: SIN URL
```

#### **Logs de la UI (carga de imágenes con Coil):**

```
D/EIMAGEN_UI: Intentando cargar imagen: https://kgnnvanlxeeqlpdvcylq.supabase.co/...
D/EIMAGEN_UI: ⏳ Iniciando carga: https://kgnnvanlxeeqlpdvcylq.supabase.co/...
D/EIMAGEN_UI: ✅ Imagen cargada exitosamente: https://kgnnvanlxeeqlpdvcylq.supabase.co/...
D/EIMAGEN_UI:    Dimensiones: 800x600
```

#### **O si hay error:**

```
E/EIMAGEN_UI: ❌ ERROR al cargar: https://kgnnvanlxeeqlpdvcylq.supabase.co/...
E/EIMAGEN_UI:    Error: Unable to resolve host "kgnnvanlxeeqlpdvcylq.supabase.co"
```

✅ **Si ves la URL completa** → El backend está enviando correctamente
❌ **Si ves "SIN URL" o null** → Problema en el backend o en el campo del JSON
⚠️ **Si ves error de Coil** → Problema de red, CORS, o URL inaccesible

---

## 🎨 Comportamiento Visual Actual

### **Con imagen válida:**

```
┌──────────────┐
│ [Loading...] │ ← CircularProgressIndicator azul mientras carga
└──────────────┘
       ↓
┌──────────────┐
│   [Imagen]   │ ← Imagen cargada con crossfade suave
└──────────────┘
```

### **Con error al cargar:**

```
┌──────────────┐
│   [❌ Ícono]  │ ← Ícono rojo semitransparente
└──────────────┘
```

### **Sin URL:**

```
┌──────────────┐
│   [🖼️ Ícono]  │ ← Ícono gris claro
└──────────────┘
```

---

## 🐛 Posibles Problemas y Soluciones

### **Problema 1: Cuadro blanco (como describes)**

#### **Causa probable:**

Coil está intentando cargar pero falla silenciosamente.

#### **Solución 1: Verificar URL es accesible**

Abre la URL en el navegador del emulador/dispositivo:

```
https://kgnnvanlxeeqlpdvcylq.supabase.co/storage/v1/object/public/Arquitectura/ca3d3e62-1ba0-40a2-9143-4d535a4e83dc.jpeg
```

✅ Si se ve → Coil debería cargarla
❌ Si no abre → Problema de red o CORS

#### **Solución 2: Verificar en Logcat si hay errores de Coil**

Filtrar por: `coil`

```
E/coil: Failed to load image: <URL>
```

---

### **Problema 2: Supabase Storage no es accesible desde el emulador**

#### **Verificar acceso público:**

La URL de Supabase debe ser de un bucket **público**:

```
https://[proyecto].supabase.co/storage/v1/object/public/[bucket]/[archivo]
```

✅ Nota el `/public/` en la ruta

#### **Prueba desde el navegador del emulador:**

1. Abre el navegador Chrome en el emulador
2. Pega la URL completa: `https://kgnnvanlxeeqlpdvcylq.supabase.co/storage/v1/object/public/Arquitectura/ca3d3e62-1ba0-40a2-9143-4d535a4e83dc.jpeg`
3. Si NO se ve → Problema de permisos en Supabase o bucket privado
4. Si SÍ se ve → Problema en Coil (revisa logs)

#### **CORS en Supabase:**

Si los logs muestran error CORS, configura en Supabase:

1. Ve a Settings → API
2. En "CORS Allowed Origins" agrega: `*` (para desarrollo)

---

### **Problema 3: HTTPS con certificados**

#### **Si Supabase usa HTTPS pero falla:**

El emulador Android debería aceptarlo, pero si hay problemas:

1. Crea `res/xml/network_security_config.xml`:

```xml
<?xml version="1.0" encoding="utf-8"?>
<network-security-config>
    <domain-config cleartextTrafficPermitted="false">
        <domain includeSubdomains="true">supabase.co</domain>
        <trust-anchors>
            <certificates src="system" />
        </trust-anchors>
    </domain-config>
</network-security-config>
```

2. Referéncalo en `AndroidManifest.xml`:

```xml
<application
    ...
    android:networkSecurityConfig="@xml/network_security_config">
```

---

### **Problema 3: Coil no está sincronizado**

#### **Verificar build.gradle.kts:**

Debe tener:

```kotlin
implementation("io.coil-kt:coil-compose:2.5.0")
```

#### **Sincronizar Gradle:**

```bash
.\gradlew clean build --refresh-dependencies
```

---

## 🧪 Test Rápido

### **Test 1: Imagen de prueba pública**

Modifica temporalmente una URL en el backend a una imagen pública conocida:

```
https://picsum.photos/200
```

Si esta imagen SÍ se ve → Tu código está bien, problema es la URL de Supabase
Si NO se ve → Problema en Coil o permisos

---

### **Test 2: Verificar con logs personalizados**

Agrega este log en `ElectrodomesticosScreen.kt` dentro del `AsyncImage`:

```kotlin
AsyncImage(
    model = ImageRequest.Builder(LocalContext.current)
        .data(item.imageUrl)
        .crossfade(true)
        .listener(
            onStart = { Log.d("EIMAGEN", "Iniciando carga: ${item.imageUrl}") },
            onSuccess = { _, _ -> Log.d("EIMAGEN", "Cargada exitosamente: ${item.imageUrl}") },
            onError = { _, error -> Log.e("EIMAGEN", "Error al cargar: ${error.throwable.message}") }
        )
        .build(),
    ...
)
```

---

## 📋 Checklist de Verificación

- [ ] Logs muestran URL completa en `EIMAGEN`
- [ ] URL abre en navegador del dispositivo/emulador
- [ ] Permiso `INTERNET` está en Manifest
- [ ] Permiso `ACCESS_NETWORK_STATE` está en Manifest
- [ ] Gradle sincronizado con `coil-compose:2.5.0`
- [ ] `usesCleartextTraffic="true"` en application tag (para HTTP)
- [ ] No hay errores en Logcat al filtrar por `coil` o `EIMAGEN`

---

## 🎯 Siguiente Paso

**Ejecuta la app y revisa Logcat con filtro `EIMAGEN`**. Eso nos dirá si el problema es:

1. **Backend** (URL no llega o es null)
2. **Red** (URL no es accesible desde el dispositivo)
3. **Coil** (Error al cargar la imagen)

Comparte los logs y te ayudo más específicamente! 🚀
