package ec.edu.monster

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewScreenSizes
import ec.edu.monster.controlador.auth.SessionManager
import ec.edu.monster.controlador.auth.UserService
import ec.edu.monster.vista.screens.CatalogoScreen
import ec.edu.monster.vista.screens.CarritoScreen
import ec.edu.monster.vista.screens.LoginScreen
import ec.edu.monster.vista.screens.ElectrodomesticosScreen
import ec.edu.monster.vista.screens.Dashboard
import ec.edu.monster.vista.screens.DashboardCliente
import ec.edu.monster.vista.components.NavShell
import ec.edu.monster.vista.components.NavShellAdmin
import ec.edu.monster.vista.navigation.AppDestinations
import ec.edu.monster.vista.navigation.AppDestinationsAdmin
import ec.edu.monster.vista.screens.FacturasScreen
import ec.edu.monster.vista.theme.Comercializadora_ElecrodomesticosTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val sessionManager = SessionManager(this)
        val userService = UserService()
        
        setContent {
            Comercializadora_ElecrodomesticosTheme {
                    val hayUsuarioLogueado = sessionManager.hayUsuarioLogueado()
                    val loggedIn = rememberSaveable { mutableStateOf(hayUsuarioLogueado) }

                    if (!loggedIn.value) {
                        LoginScreen(
                            onLoginSuccess = { cedula ->
                                // Obtener usuario completo y guardar sesión
                                val usuario = userService.obtenerUsuarioPorCedula(cedula)
                                if (usuario != null) {
                                    sessionManager.guardarSesion(usuario)
                                    loggedIn.value = true
                                }
                            }
                        )
                    } else {
                        val cedula = sessionManager.getCedula() ?: ""
                        val esAdmin = sessionManager.esAdmin()
                        val nombreCompleto = sessionManager.getNombreCompleto() ?: "Usuario"
                        
                        if (esAdmin) {
                            // Navegación para ADMIN
                            NavShellAdmin(
                                cedula = cedula,
                                nombreUsuario = nombreCompleto,
                                onLogout = {
                                    sessionManager.clearSession()
                                    loggedIn.value = false
                                }
                            ) { currentDestination, ced ->
                                when (currentDestination) {
                                    AppDestinationsAdmin.HOME -> {
                                        Dashboard(username = nombreCompleto)
                                    }
                                    AppDestinationsAdmin.PRODUCTOS -> {
                                        ElectrodomesticosScreen()
                                    }
                                    AppDestinationsAdmin.FACTURAS -> {
                                        FacturasScreen(cedula = ced)
                                    }
                                }
                            }
                        } else {
                            // Navegación para CLIENTE
                            var clienteCurrentDestination = rememberSaveable { mutableStateOf(AppDestinations.HOME) }
                            
                            NavShell(
                                cedula = cedula,
                                nombreUsuario = nombreCompleto,
                                onLogout = {
                                    sessionManager.clearSession()
                                    loggedIn.value = false
                                }
                            ) { currentDestination, ced ->
                                clienteCurrentDestination.value = currentDestination
                                when (currentDestination) {
                                    AppDestinations.HOME -> {
                                        DashboardCliente(
                                            cedula = ced,
                                            nombreCompleto = nombreCompleto,
                                            onNavigateToCarrito = { 
                                                clienteCurrentDestination.value = AppDestinations.CARRITO 
                                            },
                                            onNavigateToCatalogo = { 
                                                clienteCurrentDestination.value = AppDestinations.CATALOGO 
                                            },
                                            onNavigateToFacturas = { 
                                                clienteCurrentDestination.value = AppDestinations.FACTURAS 
                                            }
                                        )
                                    }
                                    AppDestinations.CATALOGO -> {
                                        CatalogoScreen(cedula = ced)
                                    }
                                    AppDestinations.CARRITO -> {
                                        CarritoScreen(cedula = ced)
                                    }
                                    AppDestinations.FACTURAS -> {
                                        FacturasScreen(cedula = ced)
                                    }
                                }
                            }
                        }
                    }
                }
        }
    }
}

@PreviewScreenSizes
@Composable
fun Comercializadora_ElecrodomesticosApp() {
    NavShell(cedula = "1234567890") { currentDestination, ced ->
        when (currentDestination) {
            AppDestinations.HOME -> Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                Greeting(name = "Android", modifier = Modifier.padding(innerPadding))
            }
            AppDestinations.CATALOGO -> androidx.compose.material3.Text("Catálogo (preview)")
            AppDestinations.CARRITO -> androidx.compose.material3.Text("Carrito (preview)")
            AppDestinations.FACTURAS -> androidx.compose.material3.Text("Facturas (preview)")
        }
    }
}



@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    Comercializadora_ElecrodomesticosTheme {
        Greeting("Android")
    }
}