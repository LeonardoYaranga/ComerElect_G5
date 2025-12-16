# CLICON Comercializadora REST Java - Grupo 05

Sistema de gestión para una comercializadora que permite administrar electrodomésticos y facturas a través de una interfaz de consola.

## Arquitectura MVC

El proyecto sigue el patrón de arquitectura **Modelo-Vista-Controlador (MVC)**:

### 📁 Estructura de Directorios

```
src/main/java/ec/edu/monster/
├── controllers/
│   └── CliConComercializadoraController.java  # Controlador principal
├── views/
│   └── ConsoleMenu.java                       # Vista de consola
├── models/                                    # Modelos de datos
│   ├── ApiResponse.java
│   ├── ClienteInfo.java
│   ├── CrearFacturaViewModel.java
│   ├── CuotaAmortizacion.java
│   ├── DashboardViewModel.java
│   ├── DetalleFacturaRequest.java
│   ├── DetalleFacturaViewModel.java
│   ├── DetalleProducto.java
│   ├── Electrodomestico.java
│   ├── ErrorViewModel.java
│   ├── FacturaResumen.java
│   ├── FacturasResponse.java
│   ├── LoginViewModel.java
│   ├── ProductoCarrito.java
│   ├── ProductoMasVendido.java
│   └── SolicitudFactura.java
├── services/                                  # Lógica de negocio
│   ├── ElectrodomesticoService.java
│   └── FacturaService.java
└── CliConComercializadoraRESTJavaApp.java     # Clase principal
```

### 🏗️ Capas de la Arquitectura

#### **Modelo (Models)**
- **Propósito**: Representar los datos y la lógica de negocio
- **Ubicación**: `models/`
- **Responsabilidades**:
  - Definir estructuras de datos
  - Validaciones de negocio
  - Conversión de formatos

#### **Vista (Views)**
- **Propósito**: Presentar la información al usuario
- **Ubicación**: `views/`
- **Responsabilidades**:
  - Interfaz de usuario (consola)
  - Formateo de salida
  - Captura de entrada del usuario

#### **Controlador (Controllers)**
- **Propósito**: Coordinar la interacción entre modelo y vista
- **Ubicación**: `controllers/`
- **Responsabilidades**:
  - Gestionar el flujo de la aplicación
  - Procesar solicitudes del usuario
  - Coordinar servicios y vistas

#### **Servicios (Services)**
- **Propósito**: Contener la lógica de negocio y acceso a datos
- **Ubicación**: `services/`
- **Responsabilidades**:
  - Llamadas a APIs REST
  - Procesamiento de datos
  - Manejo de errores

## 🚀 Funcionalidades

### Gestión de Electrodomésticos
- ✅ Listar electrodomésticos (formato tabla)
- ✅ Crear electrodoméstico
- ✅ Buscar electrodoméstico
- ✅ Editar electrodoméstico
- ✅ Eliminar electrodoméstico

### Gestión de Facturas
- ✅ Crear factura
- ✅ Consultar facturas (formato tabla)
- ✅ Ver detalle de factura (formato recibo)
- ✅ Ver tabla de amortización

### Dashboard
- ✅ Estadísticas del día
- ✅ Totales por tipo de pago
- ✅ Productos más vendidos

## 🎨 Características de la Interfaz

- **Colores ANSI**: Mejor legibilidad con colores
- **Tablas ASCII**: Presentación estructurada de datos
- **Formatos de moneda**: Valores monetarios con símbolo $
- **Recibos formateados**: Facturas como recibos profesionales
- **Manejo de errores**: Mensajes de error claros y formateados
- **Compatibilidad**: Funciona en cualquier consola (sin caracteres Unicode)

## 🛠️ Tecnologías Utilizadas

- **Java 17**
- **Spring Boot 3.2.5**
- **Spring Web** (para REST clients)
- **Jackson** (para serialización JSON)
- **Maven** (gestión de dependencias)

## 📋 Requisitos

- JDK 17 o superior
- Maven 3.6+
- Conexión a internet (para APIs REST)

## 🚀 Ejecución

```bash
# Compilar y ejecutar
mvn clean compile exec:java -Dexec.mainClass="ec.edu.monster.CliConComercializadoraRESTJavaApp"
```

### Credenciales de Acceso
- **Usuario**: MONSTER
- **Contraseña**: MONSTER9

## 📝 Notas de Desarrollo

- La aplicación maneja automáticamente la codificación UTF-8 para soporte de acentos
- Las tablas utilizan caracteres ASCII para máxima compatibilidad
- Los errores se parsean automáticamente de respuestas JSON
- La arquitectura MVC facilita el mantenimiento y escalabilidad</content>
<parameter name="filePath">c:\Users\joela\Documents\01-UNIVERSIDAD\01-PARCIAL\03-PRUEBA_P1\CLICON_Comercializadora_RESTJAVA_GR05\README.md