package ec.edu.monster.vista.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteScaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import ec.edu.monster.vista.navigation.AppDestinations
import ec.edu.monster.vista.navigation.AppDestinationsAdmin

/**
 * NavShell para clientes con navegación específica.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NavShell(
    cedula: String,
    nombreUsuario: String = "Usuario",
    onLogout: () -> Unit = {},
    content: @Composable (AppDestinations, String) -> Unit
) {
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
        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text("Hola, $nombreUsuario") },
                    actions = {
                        IconButton(onClick = onLogout) {
                            Icon(
                                imageVector = Icons.Default.ExitToApp,
                                contentDescription = "Cerrar sesión"
                            )
                        }
                    }
                )
            }
        ) { innerPadding ->
            Box(
                modifier = Modifier.padding(innerPadding)
            ) {
                content(currentDestination, cedula)
            }
        }
    }
}

/**
 * NavShell para administradores con navegación específica.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NavShellAdmin(
    cedula: String,
    nombreUsuario: String = "Usuario",
    onLogout: () -> Unit = {},
    content: @Composable (AppDestinationsAdmin, String) -> Unit
) {
    var currentDestination by androidx.compose.runtime.remember { mutableStateOf(AppDestinationsAdmin.HOME) }

    NavigationSuiteScaffold(
        navigationSuiteItems = {
            AppDestinationsAdmin.values().forEach { dest ->
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
        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text("Hola, $nombreUsuario") },
                    actions = {
                        IconButton(onClick = onLogout) {
                            Icon(
                                imageVector = Icons.Default.ExitToApp,
                                contentDescription = "Cerrar sesión"
                            )
                        }
                    }
                )
            }
        ) { innerPadding ->
            Box(
                modifier = Modifier.padding(innerPadding)
            ) {
                content(currentDestination, cedula)
            }
        }
    }
}
