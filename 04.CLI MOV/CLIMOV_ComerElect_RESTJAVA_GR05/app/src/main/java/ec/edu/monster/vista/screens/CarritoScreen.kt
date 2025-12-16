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
import androidx.lifecycle.viewmodel.compose.viewModel
import ec.edu.monster.controlador.carrito.CarritoService
import ec.edu.monster.modelo.ConfirmacionCarrito
import ec.edu.monster.modelo.ProductoCarrito
import ec.edu.monster.vista.viewmodel.CarritoViewModel
import kotlinx.coroutines.launch
import java.text.NumberFormat
import java.util.Locale

@Composable
fun CarritoScreen(
    cedula: String,
    viewModel: CarritoViewModel = viewModel(),
    carritoService: CarritoService = CarritoService()
) {
    val items by viewModel.items.collectAsState()
    val loading by viewModel.loading.collectAsState()
    val error by viewModel.error.collectAsState()
    val scope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }
    var showConfirmDialog by remember { mutableStateOf(false) }
    var cuotas by remember { mutableStateOf(1) }

    LaunchedEffect(Unit) {
        viewModel.cargarCarrito(cedula)
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
                        modifier = Modifier.weight(1f)
                    )
                    Button(onClick = { showConfirmDialog = true }) {
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
                    Column(
                        modifier = Modifier.align(Alignment.Center),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text("Error: ${error}")
                        Button(onClick = { viewModel.cargarCarrito(cedula) }) {
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
                                    scope.launch {
                                        try {
                                            carritoService.remover(item.codigo, cedula)
                                            viewModel.cargarCarrito(cedula)
                                            snackbarHostState.showSnackbar("Producto eliminado")
                                        } catch (e: Exception) {
                                            snackbarHostState.showSnackbar("Error: ${e.message}")
                                        }
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
            title = { Text("Confirmar compra") },
            text = {
                Column {
                    Text("¿A cuántas cuotas desea diferir el pago?")
                    Spacer(modifier = Modifier.height(8.dp))
                    OutlinedTextField(
                        value = cuotas.toString(),
                        onValueChange = { cuotas = it.toIntOrNull() ?: 1 },
                        label = { Text("Número de cuotas") },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                    )
                }
            },
            confirmButton = {
                Button(onClick = {
                    scope.launch {
                        try {
                            val confirmacion = ConfirmacionCarrito(cedula, cuotas)
                            val factura = carritoService.confirmar(confirmacion)
                            snackbarHostState.showSnackbar("Compra confirmada. Factura: ${factura.numeroFactura}")
                            showConfirmDialog = false
                            viewModel.cargarCarrito(cedula) // Recargar para limpiar
                        } catch (e: Exception) {
                            snackbarHostState.showSnackbar("Error: ${e.message}")
                        }
                    }
                }) {
                    Text("Confirmar")
                }
            },
            dismissButton = {
                Button(onClick = { showConfirmDialog = false }) {
                    Text("Cancelar")
                }
            }
        )
    }
}

@Composable
fun CarritoItemCard(
    item: ProductoCarrito,
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
                    text = item.nombre,
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold
                )
                Text("Cantidad: ${item.cantidad}")
                Text(
                    text = "Subtotal: ${NumberFormat.getCurrencyInstance(Locale("es", "EC")).format(item.subtotal)}",
                    color = Color(0xFF4CAF50)
                )
            }
            IconButton(onClick = onEliminar) {
                Icon(Icons.Default.Delete, contentDescription = "Eliminar", tint = Color.Red)
            }
        }
    }
}