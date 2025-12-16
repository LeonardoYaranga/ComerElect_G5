package ec.edu.monster.vista.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.ui.graphics.vector.ImageVector

enum class AppDestinations(
    val label: String,
    val icon: ImageVector,
) {
    HOME("Catálogo", Icons.Default.Home),
    PRODUCTOS("Carrito", Icons.Default.ShoppingCart),
    FACTURAS("Facturas", Icons.Default.DateRange),
}
