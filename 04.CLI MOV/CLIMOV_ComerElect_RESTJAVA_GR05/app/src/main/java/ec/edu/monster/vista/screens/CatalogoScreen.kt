package ec.edu.monster.vista.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import coil.request.ImageRequest
import ec.edu.monster.controlador.electrodomesticos.ElectrodomesticoService
import ec.edu.monster.controlador.carrito.CarritoLocalService
import ec.edu.monster.modelo.ElectrodomesticoDTO
import ec.edu.monster.vista.viewmodel.CatalogoViewModel
import kotlinx.coroutines.launch
import java.text.NumberFormat
import java.util.Locale

@Composable
fun CatalogoScreen(
    cedula: String,
    viewModel: CatalogoViewModel = viewModel(),
    electrodomesticoService: ElectrodomesticoService = ElectrodomesticoService()
) {
    val carritoLocal = remember { CarritoLocalService.getInstance() }
    val productos by viewModel.productos.collectAsState()
    val loading by viewModel.loading.collectAsState()
    val error by viewModel.error.collectAsState()
    val scope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(Unit) {
        viewModel.cargarProductos()
    }

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) }
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
                        Button(onClick = { viewModel.cargarProductos() }) {
                            Text("Reintentar")
                        }
                    }
                }
                else -> {
                    LazyColumn(modifier = Modifier.fillMaxSize()) {
                        items(productos) { producto ->
                            ProductoCard(
                                producto = producto,
                                onAgregarAlCarrito = { cantidad ->
                                    try {
                                        android.util.Log.d("CATALOGO", "Agregando producto al carrito local: ${producto.nombre}")
                                        android.util.Log.d("CATALOGO", "Codigo: ${producto.codigo}, Cantidad: $cantidad")
                                        carritoLocal.agregar(producto, cantidad)
                                        android.util.Log.d("CATALOGO", "✓ Producto agregado exitosamente")
                                        scope.launch {
                                            snackbarHostState.showSnackbar("Producto agregado al carrito")
                                        }
                                    } catch (e: Exception) {
                                        android.util.Log.e("CATALOGO", "✗ Error agregando producto", e)
                                        scope.launch {
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
}

@Composable
fun ProductoCard(
    producto: ElectrodomesticoDTO,
    onAgregarAlCarrito: (Int) -> Unit
) {
    var showDialog by remember { mutableStateOf(false) }
    var cantidad by remember { mutableStateOf(1) }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            // Imagen
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(producto.imageUrl ?: "https://via.placeholder.com/150")
                    .crossfade(true)
                    .build(),
                contentDescription = producto.nombre,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(150.dp)
                    .clip(RoundedCornerShape(8.dp)),
                contentScale = ContentScale.Crop
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Nombre
            Text(
                text = producto.nombre,
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold
            )

            // Precio
            Text(
                text = NumberFormat.getCurrencyInstance(Locale("es", "EC")).format(producto.precio),
                style = MaterialTheme.typography.titleMedium,
                color = Color(0xFF4CAF50)
            )

            // Descripción
            Text(
                text = producto.descripcion ?: "",
                style = MaterialTheme.typography.bodyMedium,
                maxLines = 2
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Botón agregar
            Button(
                onClick = { showDialog = true },
                modifier = Modifier.align(Alignment.End)
            ) {
                Icon(Icons.Default.Add, contentDescription = "Agregar")
                Text("Agregar al carrito")
            }
        }
    }

    // Diálogo para cantidad
    if (showDialog) {
        AlertDialog(
            onDismissRequest = { showDialog = false },
            title = { Text("Seleccionar cantidad") },
            text = {
                Column {
                    Text("Producto: ${producto.nombre}")
                    Spacer(modifier = Modifier.height(8.dp))
                    OutlinedTextField(
                        value = cantidad.toString(),
                        onValueChange = { cantidad = it.toIntOrNull() ?: 1 },
                        label = { Text("Cantidad") },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                    )
                }
            },
            confirmButton = {
                Button(onClick = {
                    onAgregarAlCarrito(cantidad)
                    showDialog = false
                }) {
                    Text("Agregar")
                }
            },
            dismissButton = {
                Button(onClick = { showDialog = false }) {
                    Text("Cancelar")
                }
            }
        )
    }
}