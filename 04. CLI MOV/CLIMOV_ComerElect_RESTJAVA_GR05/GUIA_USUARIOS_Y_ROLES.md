# SISTEMA DE USUARIOS Y ROLES - APP MÓVIL

## 📋 USUARIOS HARDCODED

Los siguientes usuarios están predefinidos en el sistema (igual que en la app web Java):

### 👤 ADMINISTRADOR

- **Username**: MONSTER
- **Cédula**: 1111111111
- **Contraseña**: MONSTER9
- **Rol**: ADMIN
- **Nombre**: Administrador

### 👥 CLIENTES

1. **JOEL**

   - Cédula: 0102030405
   - Contraseña: JOEL9
   - Rol: CLIENTE

2. **DOME**

   - Cédula: 0203040506
   - Contraseña: DOME9
   - Rol: CLIENTE

3. **LEO**
   - Cédula: 0304050607
   - Contraseña: LEO9
   - Rol: CLIENTE

---

## 🏗️ ARQUITECTURA IMPLEMENTADA

### 1. Modelo de Datos

**Ubicación**: `modelo/auth/Usuario.kt`

```kotlin
data class Usuario(
    val username: String,
    val cedula: String,
    val password: String,
    val rol: Rol,
    val nombreCompleto: String
)

enum class Rol {
    ADMIN,
    CLIENTE
}
```

### 2. Servicio de Autenticación

**Ubicación**: `controlador/auth/UserService.kt`

**Métodos disponibles**:

- `login(usernameOrCedula, password): Usuario?` - Valida credenciales
- `obtenerUsuarioPorCedula(cedula): Usuario?` - Busca por cédula
- `obtenerTodosLosUsuarios(): List<Usuario>` - Lista todos (debug)

**Ejemplo de uso**:

```kotlin
val userService = UserService()
val usuario = userService.login("MONSTER", "MONSTER9")
if (usuario != null) {
    // Login exitoso
    println("Bienvenido ${usuario.nombreCompleto}, rol: ${usuario.rol}")
}
```

### 3. Gestor de Sesión

**Ubicación**: `controlador/auth/SessionManager.kt`

**Métodos principales**:

- `guardarSesion(usuario: Usuario)` - Guarda sesión completa
- `getCedula(): String?` - Obtiene cédula
- `getUsername(): String?` - Obtiene username
- `getRol(): Rol?` - Obtiene rol
- `getNombreCompleto(): String?` - Obtiene nombre
- `esAdmin(): Boolean` - Verifica si es ADMIN
- `esCliente(): Boolean` - Verifica si es CLIENTE
- `hayUsuarioLogueado(): Boolean` - Verifica sesión activa
- `clearSession()` - Cierra sesión

**Ejemplo de uso**:

```kotlin
val sessionManager = SessionManager(context)

// Guardar sesión después del login
sessionManager.guardarSesion(usuario)

// Verificar rol
if (sessionManager.esAdmin()) {
    // Mostrar opciones de ADMIN
} else if (sessionManager.esCliente()) {
    // Mostrar opciones de CLIENTE
}

// Obtener datos
val cedula = sessionManager.getCedula()
val nombre = sessionManager.getNombreCompleto()
```

---

## 🎨 FLUJO DE LOGIN

### LoginScreen

**Ubicación**: `vista/screens/LoginScreen.kt`

1. Usuario ingresa username/cédula y contraseña
2. Se valida con `userService.login()`
3. Si es válido, se devuelve el Usuario
4. MainActivity guarda la sesión con `sessionManager.guardarSesion()`

### MainActivity

**Ubicación**: `MainActivity.kt`

```kotlin
val sessionManager = SessionManager(this)
val userService = UserService()

if (!sessionManager.hayUsuarioLogueado()) {
    LoginScreen(
        onLoginSuccess = { cedula ->
            val usuario = userService.obtenerUsuarioPorCedula(cedula)
            if (usuario != null) {
                sessionManager.guardarSesion(usuario)
            }
        }
    )
} else {
    // Mostrar app principal
}
```

---

## 🔐 CONTROL DE ACCESO POR ROL

### Implementación en Composables

```kotlin
@Composable
fun PantallaConValidacion(sessionManager: SessionManager) {
    if (sessionManager.esAdmin()) {
        // Vista para ADMIN
        AdminContent()
    } else {
        // Vista para CLIENTE
        ClienteContent()
    }
}
```

### Ejemplo: ElectrodomesticosScreen

```kotlin
@Composable
fun ElectrodomesticosScreen(
    sessionManager: SessionManager,
    modifier: Modifier = Modifier
) {
    val esAdmin = sessionManager.esAdmin()

    Scaffold(
        floatingActionButton = {
            // Solo ADMIN puede crear productos
            if (esAdmin) {
                FloatingActionButton(onClick = { /* Crear producto */ }) {
                    Icon(Icons.Default.Add, "Crear")
                }
            }
        }
    ) {
        LazyColumn {
            items(productos) { producto ->
                ProductoCard(
                    producto = producto,
                    // Solo ADMIN puede editar/eliminar
                    mostrarAcciones = esAdmin,
                    onEditar = { /* Editar */ },
                    onEliminar = { /* Eliminar */ }
                )
            }
        }
    }
}
```

### Ejemplo: FacturasScreen

```kotlin
@Composable
fun FacturasScreen(
    cedula: String,
    sessionManager: SessionManager
) {
    val esAdmin = sessionManager.esAdmin()

    // ADMIN ve todas las facturas
    // CLIENTE solo ve sus facturas
    LaunchedEffect(Unit) {
        if (esAdmin) {
            viewModel.cargarTodasLasFacturas()
        } else {
            viewModel.cargarFacturasPorCedula(cedula)
        }
    }
}
```

---

## 📱 PANTALLAS SEGÚN ROL

### ADMIN puede acceder a:

- ✅ Dashboard con estadísticas completas
- ✅ CRUD de Electrodomésticos (crear, editar, eliminar)
- ✅ Crear facturas manualmente
- ✅ Ver TODAS las facturas
- ✅ Gestión completa del sistema

### CLIENTE puede acceder a:

- ✅ Catálogo de productos (solo vista)
- ✅ Carrito de compras
- ✅ Ver solo SUS facturas
- ✅ Ver detalles y amortización de SUS compras

---

## 🚀 PRÓXIMOS PASOS PARA IMPLEMENTAR

### 1. Agregar SessionManager a las Screens

Pasar `SessionManager` como parámetro a las pantallas que lo necesiten:

```kotlin
NavShell(cedula = cedula) { currentDestination, ced ->
    when (currentDestination) {
        AppDestinations.HOME -> CatalogoScreen(
            cedula = ced,
            sessionManager = sessionManager
        )
        AppDestinations.PRODUCTOS -> {
            if (sessionManager.esAdmin()) {
                ElectrodomesticosScreen(sessionManager)
            } else {
                CarritoScreen(cedula = ced)
            }
        }
        AppDestinations.FACTURAS -> FacturasScreen(
            cedula = ced,
            sessionManager = sessionManager
        )
    }
}
```

### 2. Actualizar NavShell para cambiar según Rol

Modificar los destinos de navegación según el rol:

**Para ADMIN**:

- HOME → Dashboard con estadísticas
- PRODUCTOS → CRUD Electrodomésticos
- FACTURAS → Todas las facturas

**Para CLIENTE**:

- HOME → Catálogo de productos
- PRODUCTOS → Carrito de compras
- FACTURAS → Mis facturas

### 3. Agregar Botón de Logout

```kotlin
@Composable
fun TopBarWithLogout(sessionManager: SessionManager, onLogout: () -> Unit) {
    TopAppBar(
        title = { Text("Hola, ${sessionManager.getNombreCompleto()}") },
        actions = {
            IconButton(onClick = {
                sessionManager.clearSession()
                onLogout()
            }) {
                Icon(Icons.Default.ExitToApp, "Cerrar sesión")
            }
        }
    )
}
```

### 4. Validar acceso en ViewModels (opcional)

```kotlin
class ElectrodomesticosViewModel(
    private val sessionManager: SessionManager
) : ViewModel() {

    fun eliminarProducto(codigo: String) {
        if (!sessionManager.esAdmin()) {
            // Mostrar error: "No tienes permisos"
            return
        }
        // Proceder con eliminación
    }
}
```

---

## 🧪 TESTING

### Probar diferentes usuarios:

1. **Login como ADMIN**:

   - Username: MONSTER
   - Password: MONSTER9
   - Verificar: Acceso a CRUD completo

2. **Login como CLIENTE (JOEL)**:

   - Username: JOEL
   - Password: JOEL9
   - Verificar: Solo lectura, carrito activo

3. **Login por cédula**:
   - Username: 1111111111
   - Password: MONSTER9
   - Verificar: Login exitoso

---

## 📝 NOTAS IMPORTANTES

1. **Los usuarios están hardcoded** (no hay registro de nuevos usuarios)
2. **La sesión persiste** usando SharedPreferences
3. **El login acepta username O cédula** como identificador
4. **Las contraseñas son case-sensitive**
5. **El rol determina qué pantallas y acciones están disponibles**
6. **Mantener compatibilidad** con código existente usando `saveCedula()`

---

## 🔄 COMPARACIÓN CON APP WEB JAVA

| Característica     | App Web (Java)       | App Móvil (Kotlin)      |
| ------------------ | -------------------- | ----------------------- |
| Usuarios hardcoded | ✅ AccountController | ✅ UserService          |
| Sesión             | HttpSession          | SharedPreferences       |
| Validación de rol  | @SessionAttribute    | SessionManager.getRol() |
| Vistas por rol     | Thymeleaf th:if      | Composable if/when      |
| Logout             | /logout endpoint     | clearSession()          |

---

## 📦 ARCHIVOS MODIFICADOS

- ✅ `modelo/auth/Usuario.kt` - **NUEVO**
- ✅ `controlador/auth/UserService.kt` - **ACTUALIZADO**
- ✅ `controlador/auth/SessionManager.kt` - **ACTUALIZADO**
- ✅ `vista/screens/LoginScreen.kt` - **ACTUALIZADO**
- ✅ `MainActivity.kt` - **ACTUALIZADO**

---

**¡El sistema de usuarios y roles está listo para usar!** 🎉
