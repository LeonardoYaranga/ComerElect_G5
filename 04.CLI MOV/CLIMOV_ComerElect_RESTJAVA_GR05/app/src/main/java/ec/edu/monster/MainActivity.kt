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
import ec.edu.monster.vista.screens.CatalogoScreen
import ec.edu.monster.vista.screens.CarritoScreen
import ec.edu.monster.vista.screens.LoginScreen
import ec.edu.monster.vista.components.NavShell
import ec.edu.monster.vista.navigation.AppDestinations
import ec.edu.monster.vista.theme.Comercializadora_ElecrodomesticosTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val sessionManager = SessionManager(this)
        setContent {
            Comercializadora_ElecrodomesticosTheme {
                    val cedula = sessionManager.getCedula()
                    val loggedIn = rememberSaveable { mutableStateOf(cedula != null) }
                    val usernameState = rememberSaveable { mutableStateOf("") }

                    if (!loggedIn.value) {
                        LoginScreen(
                            onLoginSuccess = { cedula ->
                                sessionManager.saveCedula(cedula)
                                loggedIn.value = true
                            }
                        )
                    } else {
                        NavShell(cedula = cedula ?: "") { currentDestination, ced ->
                            when (currentDestination) {
                                AppDestinations.HOME -> CatalogoScreen(cedula = ced)
                                AppDestinations.PRODUCTOS -> CarritoScreen(cedula = ced)
                                AppDestinations.FACTURAS -> FacturasScreen(cedula = ced)
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
    NavShell { currentDestination ->
        when (currentDestination) {
            AppDestinations.HOME -> Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                Greeting(name = "Android", modifier = Modifier.padding(innerPadding))
            }
            AppDestinations.PRODUCTOS -> androidx.compose.material3.Text("Productos (preview)")
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