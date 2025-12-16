package ec.edu.monster.vista.components

import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteScaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import ec.edu.monster.vista.navigation.AppDestinations

/**
 * NavShell es un wrapper reutilizable que provee la barra de navegación inferior (NavigationSuiteScaffold)
 * y expone el destino seleccionado al contenido. Se puede usar desde cualquier pantalla que necesite el navbar.
 */
@Composable
fun NavShell(cedula: String, content: @Composable (AppDestinations, String) -> Unit) {
    var currentDestination by androidx.compose.runtime.remember { mutableStateOf(AppDestinations.HOME) }

    NavigationSuiteScaffold(
        navigationSuiteItems = {
            AppDestinations.values().forEach { dest ->
                item(
                    icon = {
                        Icon(dest.icon, contentDescription = dest.label)
                    },
                    label = { Text(dest.label) },
                    selected = dest == currentDestination,
                    onClick = { currentDestination = dest }
                )
            }
        }
    ) {
        content(currentDestination, cedula)
    }
}
