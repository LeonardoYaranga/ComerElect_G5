package ec.edu.monster.vista.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.icons.filled.Receipt
import androidx.compose.material.icons.filled.Store
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import ec.edu.monster.controlador.carrito.CarritoLocalService
import ec.edu.monster.controlador.facturas.FacturaService
import kotlinx.coroutines.launch
import java.text.NumberFormat
import java.util.Locale

@Composable
fun DashboardCliente(
    cedula: String,
    nombreCompleto: String,
    onNavigateToCarrito: () -> Unit = {},
    onNavigateToCatalogo: () -> Unit = {},
    onNavigateToFacturas: () -> Unit = {}
) {
    val scope = rememberCoroutineScope()
    val carritoLocal = remember { CarritoLocalService.getInstance() }
    val facturaService = remember { FacturaService() }
    
    var cantidadItemsCarrito by remember { mutableStateOf(0) }
    var totalCarrito by remember { mutableStateOf(0.0) }
    var cantidadFacturas by remember { mutableStateOf(0) }
    var loading by remember { mutableStateOf(true) }
    var error by remember { mutableStateOf<String?>(null) }

    LaunchedEffect(Unit) {
        scope.launch {
            try {
                loading = true
                error = null
                
                // Obtener información del carrito LOCAL
                try {
                    val carrito = carritoLocal.obtenerItems()
                    cantidadItemsCarrito = carrito.size
                    totalCarrito = carrito.sumOf { it.subtotal.toDouble() }
                } catch (e: Exception) {
                    // Si hay error, asumir que está vacío
                    cantidadItemsCarrito = 0
                    totalCarrito = 0.0
                }
                
                // Obtener facturas
                try {
                    val facturas = facturaService.obtenerPorCedula(cedula)
                    cantidadFacturas = facturas.size
                } catch (e: Exception) {
                    cantidadFacturas = 0
                }
                
            } catch (e: Exception) {
                error = e.message
            } finally {
                loading = false
            }
        }
    }

    Scaffold { padding ->
        if (loading) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                item {
                    // Título de bienvenida
                    Text(
                        text = "¡Bienvenido, $nombreCompleto!",
                        style = MaterialTheme.typography.headlineMedium,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "Resumen de tu cuenta",
                        style = MaterialTheme.typography.bodyLarge,
                        color = Color.Gray
                    )
                }

                item {
                    Divider()
                }

                // Card de Carrito
                item {
                    DashboardCard(
                        title = "Mi Carrito",
                        icon = Icons.Default.ShoppingCart,
                        iconTint = Color(0xFF4CAF50),
                        info = buildString {
                            append("$cantidadItemsCarrito productos\n")
                            append("Total: ${NumberFormat.getCurrencyInstance(Locale("es", "EC")).format(totalCarrito)}")
                        },
                        onClick = onNavigateToCarrito
                    )
                }

                // Card de Catálogo
                item {
                    DashboardCard(
                        title = "Ver Catálogo",
                        icon = Icons.Default.Store,
                        iconTint = Color(0xFF2196F3),
                        info = "Explora nuestros productos disponibles",
                        onClick = onNavigateToCatalogo
                    )
                }

                // Card de Facturas
                item {
                    DashboardCard(
                        title = "Mis Facturas",
                        icon = Icons.Default.Receipt,
                        iconTint = Color(0xFFFF9800),
                        info = "$cantidadFacturas facturas registradas",
                        onClick = onNavigateToFacturas
                    )
                }

                if (error != null) {
                    item {
                        Card(
                            modifier = Modifier.fillMaxWidth(),
                            colors = CardDefaults.cardColors(
                                containerColor = Color(0xFFFFEBEE)
                            )
                        ) {
                            Text(
                                text = "Nota: Algunos datos no se pudieron cargar",
                                modifier = Modifier.padding(16.dp),
                                color = Color(0xFFC62828)
                            )
                        }
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DashboardCard(
    title: String,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    iconTint: Color,
    info: String,
    onClick: () -> Unit
) {
    Card(
        onClick = onClick,
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = icon,
                contentDescription = title,
                tint = iconTint,
                modifier = Modifier.size(48.dp)
            )
            Spacer(modifier = Modifier.width(16.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = title,
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = info,
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.Gray
                )
            }
        }
    }
}
