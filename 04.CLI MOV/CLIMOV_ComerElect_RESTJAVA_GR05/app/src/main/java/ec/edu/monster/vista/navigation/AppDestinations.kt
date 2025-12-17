package ec.edu.monster.vista.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.icons.filled.Store
import androidx.compose.ui.graphics.vector.ImageVector

enum class AppDestinations(
    val label: String,
    val icon: ImageVector,
) {
    HOME("Inicio", Icons.Default.Home),
    CATALOGO("Catálogo", Icons.Default.Store),
    CARRITO("Carrito", Icons.Default.ShoppingCart),
    FACTURAS("Facturas", Icons.Default.DateRange),
}

// Destinos específicos para ADMIN
enum class AppDestinationsAdmin(
    val label: String,
    val icon: ImageVector,
) {
    HOME("Dashboard", Icons.Default.Home),
    PRODUCTOS("Productos", Icons.Default.Store),
    FACTURAS("Facturas", Icons.Default.DateRange),
}
