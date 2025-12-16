package ec.edu.monster.vista.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Receipt
import androidx.compose.material.icons.filled.CreditCard
import androidx.compose.material.icons.filled.AccountBalanceWallet
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import ec.edu.monster.controlador.electrodomesticos.ElectrodomesticoService
import ec.edu.monster.modelo.ElectrodomesticoDTO
import ec.edu.monster.modelo.FacturaRequest
import ec.edu.monster.modelo.FacturaResponse
import ec.edu.monster.modelo.banquito.AmortizacionDTO
import ec.edu.monster.vista.viewmodel.FacturasViewModel
import kotlinx.coroutines.launch
import java.math.BigDecimal
import java.text.NumberFormat
import java.util.Locale
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.Image
import androidx.compose.material.icons.filled.BrokenImage
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import coil.compose.AsyncImage
import coil.request.ImageRequest

@Composable
fun FacturasScreen(
    onCreateFactura: () -> Unit = {},
    modifier: Modifier = Modifier,
    viewModel: FacturasViewModel = viewModel()
) {
    val facturas by viewModel.facturas.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    val error by viewModel.error.collectAsState()
    
    var showDetailsDialog by remember { mutableStateOf(false) }
    var showAmortizacionDialog by remember { mutableStateOf(false) }
    var showCreateDialog by remember { mutableStateOf(false) }
    var selectedFactura by remember { mutableStateOf<FacturaResponse?>(null) }
    var amortizacionData by remember { mutableStateOf<List<AmortizacionDTO>>(emptyList()) }
    var loadingAmortizacion by remember { mutableStateOf(false) }
    
    val scope = rememberCoroutineScope()

    // Cargar facturas al iniciar
    LaunchedEffect(Unit) {
        viewModel.cargarFacturas()
    }

    Scaffold(
        modifier = modifier,
        floatingActionButton = {
            FloatingActionButton(onClick = { showCreateDialog = true }) {
                Icon(imageVector = Icons.Default.Add, contentDescription = "Crear factura")
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            // Header con diseño mejorado
            Card(
                modifier = Modifier
                    .fillMaxWidth(),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer
                ),
                shape = MaterialTheme.shapes.large
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(20.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.Receipt,
                        contentDescription = null,
                        modifier = Modifier.size(40.dp),
                        tint = MaterialTheme.colorScheme.primary
                    )
                    Spacer(modifier = Modifier.width(16.dp))
                    Column {
                        Text(
                            text = "Facturas",
                            style = MaterialTheme.typography.headlineMedium,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onPrimaryContainer
                        )
                        Text(
                            text = "${facturas.size} facturas registradas",
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.7f)
                        )
                    }
                }
            }
            
            Spacer(modifier = Modifier.height(16.dp))

            // Mensaje de error
            error?.let { errorMsg ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                        .padding(bottom = 8.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.errorContainer
                    )
                ) {
                    Row(
                        modifier = Modifier.padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Default.Info,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.error
                        )
                        Spacer(modifier = Modifier.width(12.dp))
                        Text(
                            text = errorMsg,
                            color = MaterialTheme.colorScheme.onErrorContainer,
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }
                }
            }

            // Estado de carga
            if (isLoading) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            } else if (facturas.isEmpty()) {
                // Estado vacío
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(
                            text = "No hay facturas registradas",
                            style = MaterialTheme.typography.bodyLarge
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        Button(onClick = { viewModel.cargarFacturas() }) {
                            Text("Recargar")
                        }
                    }
                }
            } else {
                // Lista de facturas ordenadas por fecha descendente (más recientes primero)
                val facturasOrdenadas = remember(facturas) {
                    facturas.sortedByDescending { it.fecha }
                }
                
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(facturasOrdenadas) { factura ->
                        FacturaListItem(
                            factura = factura,
                            onViewDetails = {
                                selectedFactura = factura
                                showDetailsDialog = true
                            },
                            onViewAmortizacion = {
                                selectedFactura = factura
                                loadingAmortizacion = true
                                showAmortizacionDialog = true
                                scope.launch {
                                    amortizacionData = viewModel.obtenerAmortizacion(factura.numFactura)
                                    loadingAmortizacion = false
                                }
                            }
                        )
                    }
                }
            }
        }
    }

    // Modal de detalles
    if (showDetailsDialog && selectedFactura != null) {
        FacturaDetailsDialog(
            factura = selectedFactura!!,
            onDismiss = { showDetailsDialog = false }
        )
    }
    
    // Modal de tabla de amortización
    if (showAmortizacionDialog && selectedFactura != null) {
        AmortizacionDialog(
            factura = selectedFactura!!,
            amortizacionData = amortizacionData,
            isLoading = loadingAmortizacion,
            onDismiss = {
                showAmortizacionDialog = false
                amortizacionData = emptyList()
            }
        )
    }
    
    // Modal de crear factura
    if (showCreateDialog) {
        CreateFacturaDialog(
            viewModel = viewModel,
            onDismiss = { showCreateDialog = false },
            onSuccess = {
                showCreateDialog = false
                viewModel.cargarFacturas()
            }
        )
    }
}

@Composable
fun FacturaListItem(
    factura: FacturaResponse,
    onViewDetails: () -> Unit,
    onViewAmortizacion: () -> Unit = {}
) {
    val numberFormat = NumberFormat.getCurrencyInstance(Locale("es", "EC"))
    
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceContainerHighest
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            // Header con número y badge de tipo
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.Receipt,
                        contentDescription = null,
                        modifier = Modifier.size(24.dp),
                        tint = MaterialTheme.colorScheme.primary
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "N° ${factura.numFactura}",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary
                    )
                }
                
                // Badge de tipo de pago
                AssistChip(
                    onClick = { },
                    label = { 
                        Text(
                            text = if (factura.tipoPago == "C") "Crédito" else "Efectivo",
                            style = MaterialTheme.typography.labelMedium,
                            fontWeight = FontWeight.Bold
                        )
                    },
                    leadingIcon = {
                        Icon(
                            imageVector = if (factura.tipoPago == "C") 
                                Icons.Default.CreditCard 
                            else 
                                Icons.Default.AccountBalanceWallet,
                            contentDescription = null,
                            modifier = Modifier.size(16.dp)
                        )
                    },
                    colors = AssistChipDefaults.assistChipColors(
                        containerColor = if (factura.tipoPago == "C")
                            MaterialTheme.colorScheme.tertiaryContainer
                        else
                            MaterialTheme.colorScheme.secondaryContainer,
                        labelColor = if (factura.tipoPago == "C")
                            MaterialTheme.colorScheme.onTertiaryContainer
                        else
                            MaterialTheme.colorScheme.onSecondaryContainer
                    )
                )
            }
            
            Spacer(modifier = Modifier.height(12.dp))
            
            // Información del cliente y fecha
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Row(
                    modifier = Modifier.weight(1f),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.Person,
                        contentDescription = null,
                        modifier = Modifier.size(16.dp),
                        tint = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = factura.cedula,
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
                
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.CalendarToday,
                        contentDescription = null,
                        modifier = Modifier.size(16.dp),
                        tint = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = factura.fecha.toString(),
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
            
            Spacer(modifier = Modifier.height(12.dp))
            Divider()
            Spacer(modifier = Modifier.height(12.dp))
            
            // Total y acciones
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        text = "Total",
                        style = MaterialTheme.typography.labelMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Text(
                        text = numberFormat.format(factura.total),
                        style = MaterialTheme.typography.headlineSmall,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary
                    )
                    if (factura.descuento > BigDecimal.ZERO) {
                        Text(
                            text = "Desc: ${numberFormat.format(factura.descuento)}",
                            style = MaterialTheme.typography.labelSmall,
                            color = MaterialTheme.colorScheme.tertiary
                        )
                    }
                }
                
                Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                    // Botón de ver detalles
                    FilledTonalIconButton(
                        onClick = onViewDetails,
                        colors = IconButtonDefaults.filledTonalIconButtonColors(
                            containerColor = MaterialTheme.colorScheme.primaryContainer
                        )
                    ) {
                        Icon(
                            imageVector = Icons.Default.Info,
                            contentDescription = "Ver detalles"
                        )
                    }
                    
                    // Botón de tabla de amortización (solo para crédito)
                    if (factura.tipoPago == "C") {
                        FilledTonalIconButton(
                            onClick = onViewAmortizacion,
                            colors = IconButtonDefaults.filledTonalIconButtonColors(
                                containerColor = MaterialTheme.colorScheme.tertiaryContainer
                            )
                        ) {
                            Icon(
                                imageVector = Icons.Default.List,
                                contentDescription = "Ver tabla de amortización"
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun FacturaDetailsDialog(
    factura: FacturaResponse,
    onDismiss: () -> Unit
) {
    val numberFormat = NumberFormat.getCurrencyInstance(Locale("es", "EC"))
    val electrodomesticoService = remember { ElectrodomesticoService() }
    var productosMap by remember { mutableStateOf<Map<String, ElectrodomesticoDTO>>(emptyMap()) }
    var isLoadingProductos by remember { mutableStateOf(false) }
    
    // Cargar información de productos al abrir
    LaunchedEffect(factura.detalles) {
        isLoadingProductos = true
        try {
            val codigos = factura.detalles.map { it.codigo }
            val productos = electrodomesticoService.listar()
            productosMap = productos.filter { it.codigo in codigos }.associateBy { it.codigo }
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            isLoadingProductos = false
        }
    }
    
    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(
                text = "Detalles de Factura",
                style = MaterialTheme.typography.headlineSmall
            )
        },
        text = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .verticalScroll(rememberScrollState())
            ) {
                // Información general
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 16.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.primaryContainer
                    )
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        DetailRow("Número Factura", factura.numFactura)
                        DetailRow("Fecha", factura.fecha.toString())
                        DetailRow("Cédula", factura.cedula)
                        DetailRow("Tipo de Pago", if (factura.tipoPago == "C") "Crédito" else "Efectivo")
                        DetailRow("Descuento", numberFormat.format(factura.descuento))
                        DetailRow("Total", numberFormat.format(factura.total), isBold = true)
                    }
                }

                // Detalles de productos
                Text(
                    text = "Productos",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(bottom = 8.dp)
                )
                
                if (isLoadingProductos) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(32.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator()
                    }
                } else {
                    factura.detalles.forEach { detalle ->
                        val producto = productosMap[detalle.codigo]
                        
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(bottom = 8.dp)
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(12.dp),
                                horizontalArrangement = Arrangement.spacedBy(12.dp)
                            ) {
                                // Imagen del producto
                                Box(
                                    modifier = Modifier
                                        .size(70.dp)
                                        .clip(MaterialTheme.shapes.medium),
                                    contentAlignment = Alignment.Center
                                ) {
                                    if (!producto?.imageUrl.isNullOrBlank()) {
                                        AsyncImage(
                                            model = ImageRequest.Builder(LocalContext.current)
                                                .data(producto?.imageUrl)
                                                .crossfade(true)
                                                .build(),
                                            contentDescription = "Imagen de ${detalle.nombre}",
                                            modifier = Modifier.fillMaxSize(),
                                            contentScale = ContentScale.Crop,
                                            placeholder = rememberVectorPainter(Icons.Default.Image),
                                            error = rememberVectorPainter(Icons.Default.BrokenImage),
                                            fallback = rememberVectorPainter(Icons.Default.Image)
                                        )
                                    } else {
                                        Icon(
                                            imageVector = Icons.Default.Image,
                                            contentDescription = "Sin imagen",
                                            modifier = Modifier.size(32.dp),
                                            tint = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.5f)
                                        )
                                    }
                                }
                                
                                Column(modifier = Modifier.weight(1f)) {
                                    Text(
                                        text = detalle.nombre,
                                        style = MaterialTheme.typography.bodyLarge,
                                        fontWeight = FontWeight.SemiBold
                                    )
                                    Spacer(modifier = Modifier.height(4.dp))
                                    DetailRow("Código", detalle.codigo)
                                    DetailRow("Cantidad", detalle.cantidad.toString())
                                    DetailRow("Precio Unit.", numberFormat.format(detalle.precio))
                                    DetailRow("Subtotal", numberFormat.format(detalle.subtotal), isBold = true)
                                }
                            }
                        }
                    }
                }
            }
        },
        confirmButton = {
            TextButton(onClick = onDismiss) {
                Text("Cerrar")
            }
        }
    )
}

@Composable
fun DetailRow(label: String, value: String, isBold: Boolean = false) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 2.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = "$label:",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        Text(
            text = value,
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = if (isBold) FontWeight.Bold else FontWeight.Normal
        )
    }
}

// Data class para manejar productos seleccionados con cantidad
data class ProductoSeleccionado(
    val producto: ElectrodomesticoDTO,
    var cantidad: Int = 1
)

@Composable
fun CreateFacturaDialog(
    viewModel: FacturasViewModel,
    onDismiss: () -> Unit,
    onSuccess: () -> Unit
) {
    var cedula by remember { mutableStateOf("") }
    var tipoPago by remember { mutableStateOf("E") } // E = Efectivo, C = Crédito
    var numeroCuotas by remember { mutableStateOf("") }
    var productosSeleccionados by remember { mutableStateOf<List<ProductoSeleccionado>>(emptyList()) }
    var showProductSelector by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf<String?>(null) }
    var isCreating by remember { mutableStateOf(false) }
    
    val scope = rememberCoroutineScope()
    val numberFormat = NumberFormat.getCurrencyInstance(Locale("es", "EC"))
    
    // Calcular totales
    val subtotal = remember(productosSeleccionados) {
        productosSeleccionados.sumOf { 
            (it.producto.precio ?: BigDecimal.ZERO).multiply(BigDecimal(it.cantidad))
        }
    }
    
    val descuento = remember(subtotal, tipoPago) {
        if (tipoPago == "E") {
            subtotal.multiply(BigDecimal("0.33")) // 33% descuento en efectivo
        } else {
            BigDecimal.ZERO
        }
    }
    
    val total = remember(subtotal, descuento) {
        subtotal.subtract(descuento)
    }
    
    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(
                text = "Crear Nueva Factura",
                style = MaterialTheme.typography.headlineSmall
            )
        },
        text = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .verticalScroll(rememberScrollState())
            ) {
                // Error message
                errorMessage?.let { error ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 16.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = MaterialTheme.colorScheme.errorContainer
                        )
                    ) {
                        Text(
                            text = error,
                            color = MaterialTheme.colorScheme.onErrorContainer,
                            modifier = Modifier.padding(12.dp),
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }
                }
                
                // Cédula
                OutlinedTextField(
                    value = cedula,
                    onValueChange = { cedula = it },
                    label = { Text("Cédula del Cliente") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 8.dp),
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                )
                
                // Tipo de Pago
                Text(
                    text = "Tipo de Pago",
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(bottom = 4.dp)
                )
                
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 8.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    FilterChip(
                        selected = tipoPago == "E",
                        onClick = { tipoPago = "E" },
                        label = { Text("Efectivo (33% desc.)") },
                        modifier = Modifier.weight(1f)
                    )
                    FilterChip(
                        selected = tipoPago == "C",
                        onClick = { tipoPago = "C" },
                        label = { Text("Crédito") },
                        modifier = Modifier.weight(1f)
                    )
                }
                
                // Número de cuotas (solo si es crédito)
                if (tipoPago == "C") {
                    OutlinedTextField(
                        value = numeroCuotas,
                        onValueChange = { numeroCuotas = it },
                        label = { Text("Número de Cuotas (3-24)") },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 8.dp),
                        singleLine = true,
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        supportingText = { Text("Mínimo 3, Máximo 24 cuotas") }
                    )
                }
                
                Divider(modifier = Modifier.padding(vertical = 8.dp))
                
                // Productos
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 8.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Productos",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold
                    )
                    Button(
                        onClick = { showProductSelector = true },
                        modifier = Modifier.height(36.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Add,
                            contentDescription = "Agregar producto",
                            modifier = Modifier.size(18.dp)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text("Agregar")
                    }
                }
                
                // Lista de productos seleccionados
                if (productosSeleccionados.isEmpty()) {
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 8.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = MaterialTheme.colorScheme.surfaceVariant
                        )
                    ) {
                        Text(
                            text = "No hay productos agregados",
                            modifier = Modifier.padding(16.dp),
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                } else {
                    productosSeleccionados.forEachIndexed { index, productoSel ->
                        ProductoSeleccionadoItem(
                            productoSeleccionado = productoSel,
                            onCantidadChange = { newCantidad ->
                                productosSeleccionados = productosSeleccionados.toMutableList().apply {
                                    this[index] = productoSel.copy(cantidad = newCantidad)
                                }
                            },
                            onRemove = {
                                productosSeleccionados = productosSeleccionados.toMutableList().apply {
                                    removeAt(index)
                                }
                            }
                        )
                    }
                }
                
                Divider(modifier = Modifier.padding(vertical = 8.dp))
                
                // Resumen de totales
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.primaryContainer
                    )
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(
                            text = "Resumen",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(bottom = 8.dp)
                        )
                        DetailRow("Subtotal", numberFormat.format(subtotal))
                        if (tipoPago == "E") {
                            DetailRow("Descuento (33%)", "- ${numberFormat.format(descuento)}")
                        }
                        Divider(modifier = Modifier.padding(vertical = 4.dp))
                        DetailRow("Total a Pagar", numberFormat.format(total), isBold = true)
                        if (tipoPago == "E") {
                            Text(
                                text = "*Este total es aproximado",
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant,
                                modifier = Modifier.padding(top = 4.dp)
                            )
                        }
                    }
                }
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    // Validaciones
                    when {
                        cedula.isBlank() -> {
                            errorMessage = "La cédula es obligatoria"
                        }
                        productosSeleccionados.isEmpty() -> {
                            errorMessage = "Debe agregar al menos un producto"
                        }
                        tipoPago == "C" && numeroCuotas.isBlank() -> {
                            errorMessage = "El número de cuotas es obligatorio para crédito"
                        }
                        tipoPago == "C" && (numeroCuotas.toIntOrNull() ?: 0) !in 3..24 -> {
                            errorMessage = "El número de cuotas debe estar entre 3 y 24"
                        }
                        else -> {
                            // Crear request
                            isCreating = true
                            errorMessage = null
                            val request = FacturaRequest(
                                cedula = cedula,
                                tipoPago = tipoPago,
                                numeroCuotas = if (tipoPago == "C") numeroCuotas.toInt() else null,
                                detalles = productosSeleccionados.map { ps ->
                                    FacturaRequest.DetalleRequest(
                                        codigo = ps.producto.codigo,
                                        cantidad = ps.cantidad
                                    )
                                }
                            )
                            
                            viewModel.generarFactura(
                                request = request,
                                onSuccess = {
                                    isCreating = false
                                    onSuccess()
                                },
                                onError = { error ->
                                    isCreating = false
                                    errorMessage = error
                                }
                            )
                        }
                    }
                },
                enabled = !isCreating
            ) {
                if (isCreating) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(16.dp),
                        strokeWidth = 2.dp
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                }
                Text(if (isCreating) "Creando..." else "Crear Factura")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss, enabled = !isCreating) {
                Text("Cancelar")
            }
        }
    )
    
    // Selector de productos
    if (showProductSelector) {
        ProductSelectorDialog(
            productosYaSeleccionados = productosSeleccionados.map { it.producto.codigo },
            onProductoSelected = { producto ->
                productosSeleccionados = productosSeleccionados + ProductoSeleccionado(producto)
                showProductSelector = false
            },
            onDismiss = { showProductSelector = false }
        )
    }
}

@Composable
fun ProductoSeleccionadoItem(
    productoSeleccionado: ProductoSeleccionado,
    onCantidadChange: (Int) -> Unit,
    onRemove: () -> Unit
) {
    val numberFormat = NumberFormat.getCurrencyInstance(Locale("es", "EC"))
    val subtotal = (productoSeleccionado.producto.precio ?: BigDecimal.ZERO)
        .multiply(BigDecimal(productoSeleccionado.cantidad))
    
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 8.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Imagen del producto
                Box(
                    modifier = Modifier
                        .size(50.dp)
                        .clip(MaterialTheme.shapes.small),
                    contentAlignment = Alignment.Center
                ) {
                    if (!productoSeleccionado.producto.imageUrl.isNullOrBlank()) {
                        AsyncImage(
                            model = ImageRequest.Builder(LocalContext.current)
                                .data(productoSeleccionado.producto.imageUrl)
                                .crossfade(true)
                                .build(),
                            contentDescription = "Imagen de ${productoSeleccionado.producto.nombre}",
                            modifier = Modifier.fillMaxSize(),
                            contentScale = ContentScale.Crop,
                            placeholder = rememberVectorPainter(Icons.Default.Image),
                            error = rememberVectorPainter(Icons.Default.BrokenImage),
                            fallback = rememberVectorPainter(Icons.Default.Image)
                        )
                    } else {
                        Icon(
                            imageVector = Icons.Default.Image,
                            contentDescription = "Sin imagen",
                            modifier = Modifier.size(24.dp),
                            tint = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.5f)
                        )
                    }
                }
                
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = productoSeleccionado.producto.nombre,
                        style = MaterialTheme.typography.bodyMedium,
                        fontWeight = FontWeight.SemiBold,
                        maxLines = 1
                    )
                    Text(
                        text = numberFormat.format(productoSeleccionado.producto.precio),
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
                
                // Eliminar producto
                IconButton(
                    onClick = onRemove,
                    modifier = Modifier.size(32.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = "Eliminar producto",
                        tint = MaterialTheme.colorScheme.error,
                        modifier = Modifier.size(18.dp)
                    )
                }
            }
            
            Spacer(modifier = Modifier.height(8.dp))
            Divider()
            Spacer(modifier = Modifier.height(8.dp))
            
            // Controles de cantidad y subtotal
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Controles de cantidad
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text(
                        text = "Cantidad:",
                        style = MaterialTheme.typography.labelMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    
                    FilledTonalIconButton(
                        onClick = { 
                            if (productoSeleccionado.cantidad > 1) {
                                onCantidadChange(productoSeleccionado.cantidad - 1)
                            }
                        },
                        modifier = Modifier.size(36.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.KeyboardArrowDown,
                            contentDescription = "Disminuir cantidad",
                            modifier = Modifier.size(18.dp)
                        )
                    }
                    
                    Text(
                        text = "${productoSeleccionado.cantidad}",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold
                    )
                    
                    FilledTonalIconButton(
                        onClick = { onCantidadChange(productoSeleccionado.cantidad + 1) },
                        modifier = Modifier.size(36.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Add,
                            contentDescription = "Aumentar cantidad",
                            modifier = Modifier.size(18.dp)
                        )
                    }
                }
                
                // Subtotal
                Column(horizontalAlignment = Alignment.End) {
                    Text(
                        text = "Subtotal",
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Text(
                        text = numberFormat.format(subtotal),
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary
                    )
                }
            }
        }
    }
}

@Composable
fun ProductSelectorDialog(
    productosYaSeleccionados: List<String>,
    onProductoSelected: (ElectrodomesticoDTO) -> Unit,
    onDismiss: () -> Unit
) {
    var productos by remember { mutableStateOf<List<ElectrodomesticoDTO>>(emptyList()) }
    var isLoading by remember { mutableStateOf(false) }
    var error by remember { mutableStateOf<String?>(null) }
    
    val scope = rememberCoroutineScope()
    val electrodomesticoService = remember { ElectrodomesticoService() }
    val numberFormat = NumberFormat.getCurrencyInstance(Locale("es", "EC"))
    
    // Cargar productos al abrir
    LaunchedEffect(Unit) {
        isLoading = true
        try {
            productos = electrodomesticoService.listar()
        } catch (e: Exception) {
            e.printStackTrace()
            error = "Error al cargar productos: ${e.message}"
        } finally {
            isLoading = false
        }
    }
    
    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(
                text = "Seleccionar Producto",
                style = MaterialTheme.typography.headlineSmall
            )
        },
        text = {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(400.dp)
            ) {
                when {
                    isLoading -> {
                        CircularProgressIndicator(
                            modifier = Modifier.align(Alignment.Center)
                        )
                    }
                    error != null -> {
                        Text(
                            text = error!!,
                            color = MaterialTheme.colorScheme.error,
                            modifier = Modifier.align(Alignment.Center)
                        )
                    }
                    productos.isEmpty() -> {
                        Text(
                            text = "No hay productos disponibles",
                            modifier = Modifier.align(Alignment.Center)
                        )
                    }
                    else -> {
                        LazyColumn(
                            verticalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            items(productos.filter { it.codigo !in productosYaSeleccionados }) { producto ->
                                Card(
                                    modifier = Modifier.fillMaxWidth(),
                                    onClick = { onProductoSelected(producto) }
                                ) {
                                    Row(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(12.dp),
                                        horizontalArrangement = Arrangement.spacedBy(12.dp),
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        // Imagen del producto
                                        Box(
                                            modifier = Modifier
                                                .size(60.dp)
                                                .clip(MaterialTheme.shapes.medium),
                                            contentAlignment = Alignment.Center
                                        ) {
                                            if (!producto.imageUrl.isNullOrBlank()) {
                                                AsyncImage(
                                                    model = ImageRequest.Builder(LocalContext.current)
                                                        .data(producto.imageUrl)
                                                        .crossfade(true)
                                                        .build(),
                                                    contentDescription = "Imagen de ${producto.nombre}",
                                                    modifier = Modifier.fillMaxSize(),
                                                    contentScale = ContentScale.Crop,
                                                    placeholder = rememberVectorPainter(Icons.Default.Image),
                                                    error = rememberVectorPainter(Icons.Default.BrokenImage),
                                                    fallback = rememberVectorPainter(Icons.Default.Image)
                                                )
                                            } else {
                                                Icon(
                                                    imageVector = Icons.Default.Image,
                                                    contentDescription = "Sin imagen",
                                                    modifier = Modifier.size(32.dp),
                                                    tint = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.5f)
                                                )
                                            }
                                        }
                                        
                                        Column(modifier = Modifier.weight(1f)) {
                                            Text(
                                                text = producto.nombre,
                                                style = MaterialTheme.typography.bodyLarge,
                                                fontWeight = FontWeight.SemiBold
                                            )
                                            Text(
                                                text = producto.descripcion ?: "",
                                                style = MaterialTheme.typography.bodySmall,
                                                color = MaterialTheme.colorScheme.onSurfaceVariant,
                                                maxLines = 1
                                            )
                                            Text(
                                                text = numberFormat.format(producto.precio),
                                                style = MaterialTheme.typography.bodyMedium,
                                                fontWeight = FontWeight.Bold,
                                                color = MaterialTheme.colorScheme.primary
                                            )
                                        }
                                        Icon(
                                            imageVector = Icons.Default.Add,
                                            contentDescription = "Agregar",
                                            tint = MaterialTheme.colorScheme.primary
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }
        },
        confirmButton = {
            TextButton(onClick = onDismiss) {
                Text("Cerrar")
            }
        }
    )
}

@Composable
fun AmortizacionDialog(
    factura: FacturaResponse,
    amortizacionData: List<AmortizacionDTO>,
    isLoading: Boolean,
    onDismiss: () -> Unit
) {
    val numberFormat = NumberFormat.getCurrencyInstance(Locale("es", "EC"))
    
    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Column {
                Text(
                    text = "Tabla de Amortización",
                    style = MaterialTheme.typography.headlineSmall
                )
                Text(
                    text = "Factura N° ${factura.numFactura}",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        },
        text = {
            if (isLoading) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(300.dp),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            } else if (amortizacionData.isEmpty()) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "No se pudo cargar la tabla de amortización",
                        style = MaterialTheme.typography.bodyLarge
                    )
                }
            } else {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(500.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    // Encabezado
                    item {
                        Card(
                            colors = CardDefaults.cardColors(
                                containerColor = MaterialTheme.colorScheme.primaryContainer
                            )
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(12.dp),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text(
                                    text = "Cuota",
                                    style = MaterialTheme.typography.labelMedium,
                                    fontWeight = FontWeight.Bold,
                                    modifier = Modifier.weight(0.8f)
                                )
                                Text(
                                    text = "Valor",
                                    style = MaterialTheme.typography.labelMedium,
                                    fontWeight = FontWeight.Bold,
                                    modifier = Modifier.weight(1f)
                                )
                                Text(
                                    text = "Interés",
                                    style = MaterialTheme.typography.labelMedium,
                                    fontWeight = FontWeight.Bold,
                                    modifier = Modifier.weight(1f)
                                )
                                Text(
                                    text = "Capital",
                                    style = MaterialTheme.typography.labelMedium,
                                    fontWeight = FontWeight.Bold,
                                    modifier = Modifier.weight(1f)
                                )
                                Text(
                                    text = "Saldo",
                                    style = MaterialTheme.typography.labelMedium,
                                    fontWeight = FontWeight.Bold,
                                    modifier = Modifier.weight(1f)
                                )
                            }
                        }
                    }
                    
                    // Filas de datos
                    items(amortizacionData) { cuota ->
                        Card(
                            elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(12.dp),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text(
                                    text = "${cuota.numCuota}",
                                    style = MaterialTheme.typography.bodyMedium,
                                    fontWeight = FontWeight.SemiBold,
                                    modifier = Modifier.weight(0.8f)
                                )
                                Text(
                                    text = numberFormat.format(cuota.valorCuota),
                                    style = MaterialTheme.typography.bodySmall,
                                    modifier = Modifier.weight(1f)
                                )
                                Text(
                                    text = numberFormat.format(cuota.interes),
                                    style = MaterialTheme.typography.bodySmall,
                                    color = MaterialTheme.colorScheme.error,
                                    modifier = Modifier.weight(1f)
                                )
                                Text(
                                    text = numberFormat.format(cuota.capital),
                                    style = MaterialTheme.typography.bodySmall,
                                    color = MaterialTheme.colorScheme.primary,
                                    modifier = Modifier.weight(1f)
                                )
                                Text(
                                    text = numberFormat.format(cuota.saldo),
                                    style = MaterialTheme.typography.bodySmall,
                                    fontWeight = FontWeight.SemiBold,
                                    modifier = Modifier.weight(1f)
                                )
                            }
                        }
                    }
                    
                    // Resumen al final
                    item {
                        Card(
                            modifier = Modifier.padding(top = 8.dp),
                            colors = CardDefaults.cardColors(
                                containerColor = MaterialTheme.colorScheme.secondaryContainer
                            )
                        ) {
                            Column(modifier = Modifier.padding(16.dp)) {
                                Text(
                                    text = "Resumen",
                                    style = MaterialTheme.typography.titleMedium,
                                    fontWeight = FontWeight.Bold,
                                    modifier = Modifier.padding(bottom = 8.dp)
                                )
                                DetailRow(
                                    "Total Cuotas",
                                    "${amortizacionData.size}"
                                )
                                DetailRow(
                                    "Total a Pagar",
                                    numberFormat.format(amortizacionData.sumOf { it.valorCuota }),
                                    isBold = true
                                )
                                DetailRow(
                                    "Total Intereses",
                                    numberFormat.format(amortizacionData.sumOf { it.interes })
                                )
                                DetailRow(
                                    "Total Capital",
                                    numberFormat.format(amortizacionData.sumOf { it.capital })
                                )
                            }
                        }
                    }
                }
            }
        },
        confirmButton = {
            TextButton(onClick = onDismiss) {
                Text("Cerrar")
            }
        }
    )
}
