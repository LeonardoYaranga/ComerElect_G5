package ec.edu.monster.vista.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import ec.edu.monster.vista.components.NavShell
import ec.edu.monster.vista.navigation.AppDestinations
import ec.edu.monster.vista.viewmodel.DashboardViewModel
import java.math.BigDecimal
import java.text.NumberFormat
import java.util.Locale
import androidx.compose.material.icons.filled.*        // Aquí están los que usamos

/**
 * Dashboard para ADMIN.
 * IMPORTANTE: No incluye NavShell, se renderiza dentro del NavShell de MainActivity.
 */
@Composable
fun Dashboard(username: String, modifier: Modifier = Modifier) {
    DashboardHomeScreen(
        username = username.ifBlank { "Usuario" },
        modifier = modifier
    )
}

@Composable
fun DashboardHomeScreen(
    username: String,
    modifier: Modifier = Modifier,
    viewModel: DashboardViewModel = viewModel()
) {
    val stats by viewModel.stats.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    val error by viewModel.error.collectAsState()

    // Cargar estadísticas al iniciar
    LaunchedEffect(Unit) {
        viewModel.cargarEstadisticas()
    }

    Scaffold(modifier = modifier) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
                .padding(16.dp)
        ) {
            // Encabezado con gradiente
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 24.dp),
                colors = CardDefaults.cardColors(
                    containerColor = Color.Transparent
                )
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(
                            brush = Brush.horizontalGradient(
                                colors = listOf(
                                    Color(0xFF667eea),
                                    Color(0xFF764ba2)
                                )
                            )
                        )
                        .padding(24.dp)
                ) {
                    Column {
                        Text(
                            text = "Dashboard",
                            style = MaterialTheme.typography.headlineMedium,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                        Text(
                            text = "Bienvenido, $username",
                            style = MaterialTheme.typography.bodyLarge,
                            color = Color.White.copy(alpha = 0.9f),
                            modifier = Modifier.padding(top = 4.dp)
                        )
                    }
                }
            }

            // Mensaje de error
            error?.let { errorMsg ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 16.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.errorContainer
                    )
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = errorMsg,
                            color = MaterialTheme.colorScheme.onErrorContainer,
                            style = MaterialTheme.typography.bodyMedium,
                            modifier = Modifier.weight(1f)
                        )
                        TextButton(onClick = { viewModel.clearError() }) {
                            Text("OK")
                        }
                    }
                }
            }

            // Estado de carga
            if (isLoading) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(400.dp),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            } else {
                // Métricas Grid
                Text(
                    text = "Ventas de Hoy",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(bottom = 16.dp)
                )

                Column(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    // Primera fila: Facturas y Productos
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        StatCard(
                            title = "Facturas Hoy",
                            value = "${stats.totalFacturas}",
                            icon = Icons.Filled.ReceiptLong,
                            containerColor = Color(0xFF2563EB), // Blue-600
                            modifier = Modifier.weight(1f)
                        )
                        StatCard(
                            title = "Total Productos",
                            value = "${stats.totalProductos}",
                            icon = Icons.Default.Inventory,
                            containerColor = Color(0xFF7C3AED), // Purple-600
                            modifier = Modifier.weight(1f)
                        )
                    }

                    // Segunda fila: Efectivo y Crédito
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        StatCard(
                            title = "Efectivo Hoy",
                            value = formatCurrency(stats.totalEfectivo),
                            icon = Icons.Default.AttachMoney,
                            containerColor = Color(0xFF16A34A), // Green-600
                            modifier = Modifier.weight(1f)
                        )
                        StatCard(
                            title = "Crédito Hoy",
                            value = formatCurrency(stats.totalCredito),
                            icon = Icons.Default.CreditCard,
                            containerColor = Color(0xFF0891B2), // Cyan-600
                            modifier = Modifier.weight(1f)
                        )
                    }

                    // Tercera fila: Total General
                    StatCard(
                        title = "Total Vendido Hoy",
                        value = formatCurrency(stats.totalGeneral),
                        icon = Icons.Default.AttachMoney,
                        containerColor = Color(0xFFEAB308), // Yellow-500
                        modifier = Modifier.fillMaxWidth(),
                        isLarge = true
                    )
                }
            }
        }
    }
}

@Composable
fun StatCard(
    title: String,
    value: String,
    icon: ImageVector,
    containerColor: Color,
    modifier: Modifier = Modifier,
    isLarge: Boolean = false
) {
    Card(
        modifier = modifier,
        colors = CardDefaults.cardColors(
            containerColor = containerColor
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(if (isLarge) 24.dp else 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = title,
                    style = if (isLarge) MaterialTheme.typography.titleMedium else MaterialTheme.typography.bodyMedium,
                    color = Color.White.copy(alpha = 0.9f),
                    fontWeight = FontWeight.Medium
                )
                Spacer(modifier = Modifier.height(if (isLarge) 12.dp else 8.dp))
                Text(
                    text = value,
                    style = if (isLarge) MaterialTheme.typography.displaySmall else MaterialTheme.typography.headlineMedium,
                    color = Color.White,
                    fontWeight = FontWeight.Bold
                )
            }
            Icon(
                imageVector = icon,
                contentDescription = title,
                tint = Color.White.copy(alpha = 0.5f),
                modifier = Modifier.size(if (isLarge) 56.dp else 40.dp)
            )
        }
    }
}

@Composable
private fun formatCurrency(amount: BigDecimal): String {
    val formatter = NumberFormat.getCurrencyInstance(Locale("es", "EC"))
    return formatter.format(amount)
}
