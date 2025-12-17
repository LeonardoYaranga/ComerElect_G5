package ec.edu.monster.vista.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import ec.edu.monster.controlador.carrito.CarritoLocalService
import ec.edu.monster.controlador.facturas.FacturaService
import ec.edu.monster.modelo.FacturaRequest
import kotlinx.coroutines.launch
import java.text.NumberFormat
import java.util.Locale

@Composable
fun CarritoScreen(
    cedula: String
) {
    val carritoLocal = remember { CarritoLocalService.getInstance() }
    val facturaService = remember { FacturaService() }
    
    var items by remember { mutableStateOf(carritoLocal.obtenerItems()) }
    var loading by remember { mutableStateOf(false) }
    var error by remember { mutableStateOf<String?>(null) }
    var showErrorDialog by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf("") }
    val scope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }
    var showConfirmDialog by remember { mutableStateOf(false) }
    var cuotas by remember { mutableStateOf(3) } // Mínimo 3 cuotas

    // Recargar items cuando se abre la pantalla
    LaunchedEffect(Unit) {
        android.util.Log.d("CARRITO_SCREEN", "Cargando carrito local para cedula: $cedula")
        items = carritoLocal.obtenerItems()
        android.util.Log.d("CARRITO_SCREEN", "Items cargados: ${items.size} productos")
    }

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) },
        bottomBar = {
            if (items.isNotEmpty()) {
                BottomAppBar {
                    val total = items.sumOf { it.subtotal }
                    Text(
                        text = "Total: ${NumberFormat.getCurrencyInstance(Locale("es", "EC")).format(total)}",
                        style = MaterialTheme.typography.headlineSmall,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.weight(1f).padding(start = 16.dp)
                    )
                    Button(
                        onClick = { showConfirmDialog = true },
                        modifier = Modifier.padding(end = 8.dp)
                    ) {
                        Text("Pagar")
                    }
                }
            }
        }
    ) { padding ->
        Box(modifier = Modifier.fillMaxSize().padding(padding)) {
            when {
                loading -> {
                    CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                }
                error != null -> {
                    android.util.Log.e("CARRITO_SCREEN", "Error mostrando carrito: $error")
                    Column(
                        modifier = Modifier.align(Alignment.Center),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text("Error: ${error}")
                        Button(onClick = { 
                            android.util.Log.d("CARRITO_SCREEN", "Reintentando cargar carrito")
                            items = carritoLocal.obtenerItems()
                            error = null
                        }) {
                            Text("Reintentar")
                        }
                    }
                }
                items.isEmpty() -> {
                    Text(
                        text = "El carrito está vacío",
                        modifier = Modifier.align(Alignment.Center)
                    )
                }
                else -> {
                    LazyColumn(modifier = Modifier.fillMaxSize()) {
                        items(items) { item ->
                            CarritoItemCard(
                                item = item,
                                onEliminar = {
                                    android.util.Log.d("CARRITO_SCREEN", "Eliminando: ${item.producto.codigo}")
                                    carritoLocal.remover(item.producto.codigo)
                                    items = carritoLocal.obtenerItems()
                                    scope.launch {
                                        snackbarHostState.showSnackbar("Producto eliminado")
                                    }
                                }
                            )
                        }
                    }
                }
            }
        }
    }

    // Diálogo de confirmación
    if (showConfirmDialog) {
        AlertDialog(
            onDismissRequest = { showConfirmDialog = false },
            title = { Text("Confirmar compra a crédito") },
            text = {
                Column {
                    Text("¿A cuántas cuotas desea diferir el pago?")
                    Text(
                        text = "Mínimo 3 cuotas, máximo 24 cuotas",
                        style = MaterialTheme.typography.bodySmall,
                        color = Color.Gray
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    OutlinedTextField(
                        value = cuotas.toString(),
                        onValueChange = { 
                            val nuevasCuotas = it.toIntOrNull() ?: 3
                            cuotas = nuevasCuotas.coerceIn(3, 24)
                        },
                        label = { Text("Número de cuotas") },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                    )
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        scope.launch {
                            loading = true
                            try {
                                android.util.Log.d("CARRITO_SCREEN", "Creando factura...")
                                android.util.Log.d("CARRITO_SCREEN", "Cedula: $cedula, Cuotas: $cuotas")
                                
                                // Crear FacturaRequest con los items del carrito
                                val detalles = items.map { item ->
                                    FacturaRequest.DetalleRequest(
                                        codigo = item.producto.codigo,
                                        cantidad = item.cantidad
                                    )
                                }
                                
                                val facturaRequest = FacturaRequest(
                                    cedula = cedula,
                                    tipoPago = "C", // Crédito
                                    numeroCuotas = cuotas,
                                    detalles = detalles
                                )
                                
                                android.util.Log.d("CARRITO_SCREEN", "Detalles: ${detalles.size} productos")
                                
                                val factura = facturaService.generarFactura(facturaRequest)
                                
                                android.util.Log.d("CARRITO_SCREEN", "✓ Factura creada: ${factura.numFactura}")
                                
                                // Limpiar carrito
                                carritoLocal.limpiar()
                                items = emptyList()
                                
                                snackbarHostState.showSnackbar(
                                    "Compra confirmada. Factura: ${factura.numFactura}",
                                    duration = SnackbarDuration.Long
                                )
                                showConfirmDialog = false
                            } catch (e: Exception) {
                                android.util.Log.e("CARRITO_SCREEN", "✗ Error creando factura", e)
                                errorMessage = e.message ?: "Error desconocido al crear la factura"
                                showErrorDialog = true
                                showConfirmDialog = false
                            } finally {
                                loading = false
                            }
                        }
                    },
                    enabled = !loading && cuotas in 3..24
                ) {
                    if (loading) {
                        CircularProgressIndicator(
                            modifier = Modifier.size(16.dp),
                            strokeWidth = 2.dp
                        )
                    } else {
                        Text("Confirmar")
                    }
                }
            },
            dismissButton = {
                TextButton(
                    onClick = { showConfirmDialog = false },
                    enabled = !loading
                ) {
                    Text("Cancelar")
                }
            }
        )
    }
    // Diálogo de error
    if (showErrorDialog) {
        AlertDialog(
            onDismissRequest = { showErrorDialog = false },
            icon = {
                Icon(
                    imageVector = Icons.Default.Delete,
                    contentDescription = "Error",
                    tint = MaterialTheme.colorScheme.error
                )
            },
            title = { Text("Error al procesar la compra") },
            text = {
                Column {
                    Text(
                        text = errorMessage,
                        style = MaterialTheme.typography.bodyLarge
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "Por favor, verifica la información e intenta nuevamente.",
                        style = MaterialTheme.typography.bodySmall,
                        color = Color.Gray
                    )
                }
            },
            confirmButton = {
                Button(
                    onClick = { showErrorDialog = false },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.error
                    )
                ) {
                    Text("Entendido")
                }
            }
        )
    }}

@Composable
fun CarritoItemCard(
    item: CarritoLocalService.ItemCarritoLocal,
    onEliminar: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = item.producto.nombre,
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold
                )
                Text("Cantidad: ${item.cantidad}")
                Text(
                    text = "Precio unitario: ${NumberFormat.getCurrencyInstance(Locale("es", "EC")).format(item.producto.precio)}",
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.Gray
                )
                Text(
                    text = "Subtotal: ${NumberFormat.getCurrencyInstance(Locale("es", "EC")).format(item.subtotal)}",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF4CAF50)
                )
            }
            IconButton(onClick = onEliminar) {
                Icon(Icons.Default.Delete, contentDescription = "Eliminar", tint = Color.Red)
            }
        }
    }
}