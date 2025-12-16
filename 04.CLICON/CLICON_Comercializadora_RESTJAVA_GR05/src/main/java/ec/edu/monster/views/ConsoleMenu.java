package ec.edu.monster.views;

import ec.edu.monster.models.*;
import ec.edu.monster.services.ElectrodomesticoService;
import ec.edu.monster.services.FacturaService;
import ec.edu.monster.services.UsuarioService;
import ec.edu.monster.services.CarritoService;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.stream.Collectors;

public class ConsoleMenu {

    // ANSI escape codes for colors
    private static final String RESET = "\u001B[0m";
    private static final String RED = "\u001B[31m";
    private static final String GREEN = "\u001B[32m";
    private static final String YELLOW = "\u001B[33m";
    private static final String BLUE = "\u001B[34m";
    private static final String PURPLE = "\u001B[35m";
    private static final String CYAN = "\u001B[36m";
    private static final String WHITE = "\u001B[37m";

    private final ElectrodomesticoService electrodomesticoService;
    private final FacturaService facturaService;
    private final UsuarioService usuarioService;
    private final CarritoService carritoService;
    private final Scanner scanner = new Scanner(System.in);
    private String currentCedula;
    private String currentRol;

    public ConsoleMenu(ElectrodomesticoService electrodomesticoService, FacturaService facturaService, UsuarioService usuarioService, CarritoService carritoService) {
        this.electrodomesticoService = electrodomesticoService;
        this.facturaService = facturaService;
        this.usuarioService = usuarioService;
        this.carritoService = carritoService;
    }

    public void showMenu() {
        if (!seleccionarRol()) {
            System.out.println(RED + "Selección fallida. Saliendo..." + RESET);
            return;
        }

        if ("CLIENTE".equals(currentRol)) {
            showClienteMenu();
        } else {
            showAdminMenu();
        }
    }

    private boolean seleccionarRol() {
        System.out.println(CYAN + "\n************************************************************" + RESET);
        System.out.println(CYAN + "*" + RESET + "                  " + YELLOW + "SELECCIÓN DE ROL" + RESET + "                     " + CYAN + "*" + RESET);
        System.out.println(CYAN + "************************************************************" + RESET);
        System.out.println(GREEN + "  [1]" + RESET + " Cliente");
        System.out.println(GREEN + "  [2]" + RESET + " Administrador");
        System.out.println(RED + "  [0]" + RESET + " Salir");
        System.out.println(CYAN + "************************************************************" + RESET);
        System.out.print(YELLOW + "Seleccione una opción: " + RESET);

        int option = scanner.nextInt();
        scanner.nextLine();

        switch (option) {
            case 1:
                currentRol = "CLIENTE";
                return loginCliente();
            case 2:
                currentRol = "ADMIN";
                return loginAdmin();
            case 0:
                return false;
            default:
                System.out.println(RED + "❌ Opción inválida." + RESET);
                return seleccionarRol();
        }
    }

    private boolean loginCliente() {
        System.out.println(CYAN + "\n************************************************************" + RESET);
        System.out.println(CYAN + "*" + RESET + "                  " + YELLOW + "INICIO DE SESIÓN CLIENTE" + RESET + "               " + CYAN + "*" + RESET);
        System.out.println(CYAN + "************************************************************" + RESET);
        System.out.print(YELLOW + "Usuario: " + RESET);
        String username = scanner.nextLine();
        System.out.print(YELLOW + "Contraseña: " + RESET);
        String password = scanner.nextLine();

        List<Usuario> usuarios = usuarioService.obtenerUsuarios();
        for (Usuario u : usuarios) {
            if ("CLIENTE".equals(u.getRol()) && username.equals(u.getUsername()) && password.equals(u.getPassword())) {
                currentCedula = u.getCedula();
                System.out.println(CYAN + "************************************************************" + RESET);
                System.out.println(CYAN + "*" + RESET + "     " + GREEN + "✓ Login exitoso. ¡Bienvenido " + u.getNombre() + "!" + RESET + "     " + CYAN + "*" + RESET);
                System.out.println(CYAN + "************************************************************" + RESET);
                return true;
            }
        }

        System.out.println(RED + "\n❌ Credenciales incorrectas. Acceso denegado." + RESET);
        return false;
    }

    private boolean loginAdmin() {
        System.out.println(CYAN + "\n************************************************************" + RESET);
        System.out.println(CYAN + "*" + RESET + "                  " + YELLOW + "INICIO DE SESIÓN ADMIN" + RESET + "                " + CYAN + "*" + RESET);
        System.out.println(CYAN + "************************************************************" + RESET);
        System.out.print(YELLOW + "Usuario: " + RESET);
        String usuario = scanner.nextLine();
        System.out.print(YELLOW + "Contraseña: " + RESET);
        String password = scanner.nextLine();

        List<Usuario> usuarios = usuarioService.obtenerUsuarios();
        for (Usuario u : usuarios) {
            if ("ADMIN".equals(u.getRol()) && usuario.equals(u.getUsername()) && password.equals(u.getPassword())) {
                System.out.println(CYAN + "************************************************************" + RESET);
                System.out.println(CYAN + "*" + RESET + "     " + GREEN + "✓ Login exitoso. ¡Bienvenido Administrador!" + RESET + "     " + CYAN + "*" + RESET);
                System.out.println(CYAN + "************************************************************" + RESET);
                return true;
            }
        }

        System.out.println(RED + "\n❌ Credenciales incorrectas. Acceso denegado." + RESET);
        return false;
    }
    

    private void showClienteMenu() {
        boolean running = true;
        while (running) {
            System.out.println("\n" + CYAN + "************************************************************" + RESET);
            System.out.println(CYAN + "*" + RESET + "          " + YELLOW + "MENÚ CLIENTE - COMERCIALIZADORA CLICON" + RESET + "       " + CYAN + "*" + RESET);
            System.out.println(CYAN + "************************************************************" + RESET);
            System.out.println(GREEN + "  [1]" + RESET + " Ver Productos");
            System.out.println(GREEN + "  [2]" + RESET + " Agregar al Carrito");
            System.out.println(GREEN + "  [3]" + RESET + " Ver Carrito");
            System.out.println(GREEN + "  [4]" + RESET + " Remover del Carrito");
            System.out.println(GREEN + "  [5]" + RESET + " Confirmar Compra");
            System.out.println(GREEN + "  [6]" + RESET + " Ver Mis Facturas");
            System.out.println(RED + "  [0]" + RESET + " Salir");
            System.out.println(CYAN + "************************************************************" + RESET);
            System.out.print(YELLOW + "Seleccione una opción: " + RESET);

            int option = scanner.nextInt();
            scanner.nextLine();

            switch (option) {
                case 1:
                    verProductos();
                    break;
                case 2:
                    agregarAlCarrito();
                    break;
                case 3:
                    verCarrito();
                    break;
                case 4:
                    removerDelCarrito();
                    break;
                case 5:
                    confirmarCompra();
                    break;
                case 6:
                    verMisFacturas();
                    break;
                case 0:
                    running = false;
                    System.out.println("\n" + CYAN + "************************************************************" + RESET);
                    System.out.println(CYAN + "*" + RESET + "     " + GREEN + "¡Gracias por usar el Sistema de Comercializadora!" + RESET + "     " + CYAN + "*" + RESET);
                    System.out.println(CYAN + "************************************************************" + RESET);
                    break;
                default:
                    System.out.println(RED + "❌ Opción inválida. Por favor, seleccione una opción del menú." + RESET);
            }
        }
    }

    private void showAdminMenu() {
        boolean running = true;
        while (running) {
            System.out.println("\n" + CYAN + "************************************************************" + RESET);
            System.out.println(CYAN + "*" + RESET + "          " + YELLOW + "SISTEMA DE COMERCIALIZADORA CLICON" + RESET + "              " + CYAN + "*" + RESET);
            System.out.println(CYAN + "************************************************************" + RESET);
            System.out.println(GREEN + "  [1]" + RESET + " Dashboard");
            System.out.println(GREEN + "  [2]" + RESET + " Gestionar Electrodomésticos");
            System.out.println(GREEN + "  [3]" + RESET + " Gestionar Facturas");
            System.out.println(RED + "  [0]" + RESET + " Salir");
            System.out.println(CYAN + "************************************************************" + RESET);
            System.out.print(YELLOW + "Seleccione una opción: " + RESET);

            int option = scanner.nextInt();
            scanner.nextLine(); // consume newline

            switch (option) {
                case 1:
                    showDashboard();
                    break;
                case 2:
                    showElectrodomesticosMenu();
                    break;
                case 3:
                    showFacturasMenu();
                    break;
                case 0:
                    running = false;
                    System.out.println("\n" + CYAN + "************************************************************" + RESET);
                    System.out.println(CYAN + "*" + RESET + "     " + GREEN + "¡Gracias por usar el Sistema de Comercializadora!" + RESET + "     " + CYAN + "*" + RESET);
                    System.out.println(CYAN + "************************************************************" + RESET);
                    break;
                default:
                    System.out.println(RED + "❌ Opción inválida. Por favor, seleccione una opción del menú." + RESET);
            }
        }
    }

    private void showDashboard() {
        try {
            List<DetalleFacturaViewModel> facturas = facturaService.listarFacturas();
            if (facturas != null) {
                LocalDate hoy = LocalDate.now();
                List<DetalleFacturaViewModel> facturasHoy = facturas.stream()
                        .filter(f -> f.getFecha().equals(hoy))
                        .collect(Collectors.toList());

                System.out.println(CYAN + "📊 ESTADÍSTICAS DEL DÍA" + RESET);
                System.out.println("   " + WHITE + "Fecha: " + hoy + RESET);
                System.out.println();
                System.out.println("   " + PURPLE + "📄 Total facturas hoy:" + RESET + " " + GREEN + facturasHoy.size() + RESET);
                
                BigDecimal totalEfectivo = facturasHoy.stream()
                        .filter(f -> "E".equals(f.getTipoPago()))
                        .map(DetalleFacturaViewModel::getTotal)
                        .reduce(BigDecimal.ZERO, BigDecimal::add);
                System.out.println("   " + PURPLE + "💵 Total vendido en efectivo:" + RESET + " " + GREEN + String.format("$%,.2f", totalEfectivo) + RESET);

                BigDecimal totalCredito = facturasHoy.stream()
                        .filter(f -> "C".equals(f.getTipoPago()))
                        .map(DetalleFacturaViewModel::getTotal)
                        .reduce(BigDecimal.ZERO, BigDecimal::add);
                System.out.println("   " + PURPLE + "💳 Total vendido a crédito:" + RESET + " " + YELLOW + String.format("$%,.2f", totalCredito) + RESET);
                
                BigDecimal totalGeneral = totalEfectivo.add(totalCredito);
                System.out.println("   " + CYAN + "━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━" + RESET);
                System.out.println("   " + PURPLE + "💰 TOTAL GENERAL:" + RESET + " " + GREEN + String.format("$%,.2f", totalGeneral) + RESET);

                // Productos más vendidos
                Map<String, Integer> productosVendidosMap = facturas.stream()
                        .flatMap(f -> f.getProductos().stream())
                        .collect(Collectors.groupingBy(
                                p -> p.getNombre(),
                                Collectors.summingInt(p -> p.getCantidad())
                        ));

                System.out.println();
                System.out.println(CYAN + "🏆 TOP 5 PRODUCTOS MÁS VENDIDOS" + RESET);
                productosVendidosMap.entrySet().stream()
                        .sorted((a, b) -> Integer.compare(b.getValue(), a.getValue()))
                        .limit(5)
                        .forEach(e -> System.out.println("   " + GREEN + "▸" + RESET + " " + e.getKey() + ": " + YELLOW + e.getValue() + " unidades" + RESET));
            }
        } catch (Exception ex) {
            System.out.println(RED + "❌ Error al cargar datos: " + ex.getMessage() + RESET);
        }
        System.out.println(CYAN + "************************************************************" + RESET);
    }

    private void showElectrodomesticosMenu() {
        boolean back = false;
        while (!back) {
            System.out.println("\n" + CYAN + "************************************************************" + RESET);
            System.out.println(CYAN + "*" + RESET + "          " + YELLOW + "GESTIÓN DE ELECTRODOMÉSTICOS" + RESET + "                " + CYAN + "*" + RESET);
            System.out.println(CYAN + "************************************************************" + RESET);
            System.out.println(GREEN + "  [1]" + RESET + " Listar electrodomésticos");
            System.out.println(GREEN + "  [2]" + RESET + " Crear electrodoméstico");
            System.out.println(GREEN + "  [3]" + RESET + " Buscar electrodoméstico");
            System.out.println(GREEN + "  [4]" + RESET + " Editar electrodoméstico");
            System.out.println(GREEN + "  [5]" + RESET + " Eliminar electrodoméstico");
            System.out.println(RED + "  [0]" + RESET + " Volver al menú principal");
            System.out.println(CYAN + "************************************************************" + RESET);
            System.out.print(YELLOW + "Seleccione una opción: " + RESET);

            int option = scanner.nextInt();
            scanner.nextLine();

            switch (option) {
                case 1:
                    listarElectrodomesticos();
                    break;
                case 2:
                    crearElectrodomestico();
                    break;
                case 3:
                    buscarElectrodomestico();
                    break;
                case 4:
                    editarElectrodomestico();
                    break;
                case 5:
                    eliminarElectrodomestico();
                    break;
                case 0:
                    back = true;
                    break;
                default:
                    System.out.println(RED + "❌ Opción inválida. Por favor, seleccione una opción del menú." + RESET);
            }
        }
    }

    private void listarElectrodomesticos() {
        try {
            List<Electrodomestico> electrodomesticos = electrodomesticoService.listarElectrodomesticos();
            if (electrodomesticos != null && !electrodomesticos.isEmpty()) {
                System.out.println(CYAN + "\nLista de Electrodomésticos:" + RESET);
                System.out.println("+------------+----------------------------------+--------------+");
                System.out.println("| " + YELLOW + "Código" + RESET + "     | " + YELLOW + "Nombre" + RESET + "                           | " + YELLOW + "Precio" + RESET + "       |");
                System.out.println("+------------+----------------------------------+--------------+");
                for (Electrodomestico e : electrodomesticos) {
                    System.out.printf("| " + GREEN + "%-10s" + RESET + " | %-32s | %s%,10.2f" + RESET + " |%n", 
                        e.getCodigo(), 
                        truncate(e.getNombre(), 32), 
                        CYAN,
                        e.getPrecio());
                }
                System.out.println("+------------+----------------------------------+--------------+");
            } else {
                System.out.println(YELLOW + "No hay electrodomésticos registrados." + RESET);
            }
        } catch (Exception ex) {
            System.out.println(RED + "Error al cargar electrodomésticos: " + ex.getMessage() + RESET);
        }
    }

    private void crearElectrodomestico() {
        System.out.println("\nCrear Electrodoméstico:");
        System.out.print("Código: ");
        String codigo = scanner.nextLine();
        System.out.print("Nombre: ");
        String nombre = scanner.nextLine();
        System.out.print("Precio: ");
        BigDecimal precio = scanner.nextBigDecimal();
        scanner.nextLine(); // consume newline
        System.out.print("Descripción: ");
        String descripcion = scanner.nextLine();

        Electrodomestico electrodomestico = new Electrodomestico();
        electrodomestico.setCodigo(codigo);
        electrodomestico.setNombre(nombre);
        electrodomestico.setPrecio(precio);
        electrodomestico.setDescripcion(descripcion);

        try {
            Electrodomestico creado = electrodomesticoService.crearElectrodomestico(electrodomestico);
            if (creado != null) {
                System.out.println(GREEN + "✓ Electrodoméstico creado exitosamente." + RESET);
                System.out.println("  Código: " + creado.getCodigo());
                System.out.println("  Nombre: " + creado.getNombre());
                System.out.println("  Precio: $" + String.format("%.2f", creado.getPrecio()));
            }
        } catch (Exception ex) {
            System.out.println(RED + "✗ Error: " + ex.getMessage() + RESET);
        }
    }

    private void buscarElectrodomestico() {
        System.out.print("\nCódigo del electrodoméstico: ");
        String codigo = scanner.nextLine();
        try {
            Electrodomestico electrodomestico = electrodomesticoService.obtenerElectrodomesticoPorCodigo(codigo);
            if (electrodomestico != null) {
                System.out.println("Código: " + electrodomestico.getCodigo());
                System.out.println("Nombre: " + electrodomestico.getNombre());
                System.out.println("Precio: " + electrodomestico.getPrecio());
                System.out.println("Descripción: " + electrodomestico.getDescripcion());
            } else {
                System.out.println("Electrodoméstico no encontrado.");
            }
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
        }
    }

    private void editarElectrodomestico() {
        System.out.print("\nCódigo del electrodoméstico a editar: ");
        String codigo = scanner.nextLine();
        try {
            Electrodomestico existente = electrodomesticoService.obtenerElectrodomesticoPorCodigo(codigo);
            if (existente != null) {
                System.out.println("Datos actuales:");
                System.out.println("Nombre: " + existente.getNombre());
                System.out.println("Precio: " + existente.getPrecio());
                System.out.println("Descripción: " + existente.getDescripcion());

                System.out.print("Nuevo nombre (dejar vacío para mantener): ");
                String nombre = scanner.nextLine();
                if (!nombre.isEmpty()) existente.setNombre(nombre);

                System.out.print("Nuevo precio (0 para mantener): ");
                BigDecimal precio = scanner.nextBigDecimal();
                if (precio.compareTo(BigDecimal.ZERO) > 0) existente.setPrecio(precio);
                scanner.nextLine();

                System.out.print("Nueva descripción (dejar vacío para mantener): ");
                String descripcion = scanner.nextLine();
                if (!descripcion.isEmpty()) existente.setDescripcion(descripcion);

                try {
                    Electrodomestico actualizado = electrodomesticoService.actualizarElectrodomesticoPorCodigo(codigo, existente);
                    if (actualizado != null) {
                        System.out.println(GREEN + "✓ Electrodoméstico actualizado exitosamente." + RESET);
                        System.out.println("  Código: " + actualizado.getCodigo());
                        System.out.println("  Nombre: " + actualizado.getNombre());
                        System.out.println("  Precio: $" + String.format("%.2f", actualizado.getPrecio()));
                    }
                } catch (Exception e) {
                    System.out.println(RED + "✗ Error: " + e.getMessage() + RESET);
                }
            } else {
                System.out.println("Electrodoméstico no encontrado.");
            }
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
        }
    }

    private void eliminarElectrodomestico() {
        System.out.print("\nCódigo del electrodoméstico a eliminar: ");
        String codigo = scanner.nextLine();
        try {
            Electrodomestico eliminado = electrodomesticoService.eliminarElectrodomesticoPorCodigo(codigo);
            if (eliminado != null) {
                System.out.println(GREEN + "✓ Electrodoméstico eliminado exitosamente." + RESET);
                System.out.println("  Código: " + eliminado.getCodigo());
                System.out.println("  Nombre: " + eliminado.getNombre());
            }
        } catch (Exception ex) {
            System.out.println(RED + "✗ Error: " + ex.getMessage() + RESET);
        }
    }

    private void showFacturasMenu() {
        boolean back = false;
        while (!back) {
            System.out.println("\n" + CYAN + "************************************************************" + RESET);
            System.out.println(CYAN + "*" + RESET + "                " + YELLOW + "GESTIÓN DE FACTURAS" + RESET + "                     " + CYAN + "*" + RESET);
            System.out.println(CYAN + "************************************************************" + RESET);
            System.out.println(GREEN + "  [1]" + RESET + " Crear factura");
            System.out.println(GREEN + "  [2]" + RESET + " Consultar facturas");
            System.out.println(GREEN + "  [3]" + RESET + " Ver detalle de factura");
            System.out.println(GREEN + "  [4]" + RESET + " Ver amortización");
            System.out.println(RED + "  [0]" + RESET + " Volver al menú principal");
            System.out.println(CYAN + "************************************************************" + RESET);
            System.out.print(YELLOW + "Seleccione una opción: " + RESET);

            int option = scanner.nextInt();
            scanner.nextLine();

            switch (option) {
                case 1:
                    crearFactura();
                    break;
                case 2:
                    consultarFacturas();
                    break;
                case 3:
                    verDetalleFactura();
                    break;
                case 4:
                    verAmortizacion();
                    break;
                case 0:
                    back = true;
                    break;
                default:
                    System.out.println(RED + "❌ Opción inválida. Por favor, seleccione una opción del menú." + RESET);
            }
        }
    }

    private void crearFactura() {
        System.out.println("\nCrear Factura:");
        System.out.print("Cédula del cliente: ");
        String cedula = scanner.nextLine();

        System.out.print("Tipo de pago (Efectivo/Credito): ");
        String tipoPago = scanner.nextLine();
        String tipoPagoSOAP = "Efectivo".equalsIgnoreCase(tipoPago) ? "E" : "C";

        String numeroCuotasStr = null;
        if ("C".equals(tipoPagoSOAP)) {
            System.out.print("Número de cuotas: ");
            numeroCuotasStr = scanner.nextLine();
        }

        // Agregar productos
        CrearFacturaViewModel factura = new CrearFacturaViewModel();
        factura.setCedula(cedula);
        factura.setTipoPago(tipoPago);
        if (numeroCuotasStr != null) {
            factura.setNumeroCuotas(Integer.parseInt(numeroCuotasStr));
        }

        boolean agregarMas = true;
        while (agregarMas) {
            System.out.print("Código del producto: ");
            String codigo = scanner.nextLine();
            System.out.print("Cantidad: ");
            int cantidad = scanner.nextInt();
            scanner.nextLine();

            ProductoCarrito producto = new ProductoCarrito();
            producto.setCodigo(codigo);
            producto.setCantidad(cantidad);
            factura.getProductosCarrito().add(producto);

            System.out.print("¿Agregar otro producto? (s/n): ");
            String respuesta = scanner.nextLine();
            agregarMas = "s".equalsIgnoreCase(respuesta);
        }

        try {
            SolicitudFactura solicitud = new SolicitudFactura();
            solicitud.setCedula(cedula);
            solicitud.setTipoPago(tipoPagoSOAP);
            if (numeroCuotasStr != null) {
                solicitud.setNumeroCuotas(Integer.parseInt(numeroCuotasStr));
            }
            solicitud.setDetalles(factura.getProductosCarrito().stream().map(p -> {
                DetalleFacturaRequest d = new DetalleFacturaRequest();
                d.setCodigo(p.getCodigo());
                d.setCantidad(p.getCantidad());
                return d;
            }).collect(Collectors.toList()));

            Map<String, Object> respuesta = facturaService.crearFactura(solicitud);
            if (respuesta != null && respuesta.containsKey("numFactura")) {
                System.out.println("Factura creada exitosamente. Número: " + respuesta.get("numFactura"));
            } else {
                System.out.println("Error al crear factura.");
            }
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
        }
    }

    private void consultarFacturas() {
        System.out.println("\nConsultar Facturas:");
        System.out.print("Número de factura (dejar vacío para todas): ");
        String numFactura = scanner.nextLine();
        System.out.print("Cédula (dejar vacío para todas): ");
        String cedula = scanner.nextLine();

        try {
            List<FacturaResumen> facturas;
            if (!numFactura.isEmpty()) {
                DetalleFacturaViewModel factura = facturaService.obtenerFacturaPorNumFactura(numFactura);
                facturas = factura != null ? List.of(convertToResumen(factura)) : List.of();
            } else if (!cedula.isEmpty()) {
                facturas = facturaService.obtenerFacturasPorCedula(cedula);
            } else {
                List<DetalleFacturaViewModel> fullFacturas = facturaService.listarFacturas();
                facturas = fullFacturas != null ? fullFacturas.stream().map(this::convertToResumen).collect(Collectors.toList()) : List.of();
            }

            if (facturas.isEmpty()) {
                System.out.println(YELLOW + "No se encontraron facturas." + RESET);
            } else {
                System.out.println("\n+----------------+-------------+--------------+--------------+");
                System.out.println("| " + YELLOW + "Num. Factura" + RESET + "   | " + YELLOW + "Fecha" + RESET + "       | " + YELLOW + "Total" + RESET + "        | " + YELLOW + "Tipo Pago" + RESET + "    |");
                System.out.println("+----------------+-------------+--------------+--------------+");
                for (FacturaResumen f : facturas) {
                    String fechaStr = f.getFecha() != null ? f.getFecha().toLocalDate().toString() : "N/A";
                    System.out.printf("| " + GREEN + "%-14s" + RESET + " | %-11s | %s%,10.2f" + RESET + " | %-12s |%n", 
                        f.getNumFactura(), 
                        fechaStr, 
                        CYAN,
                        f.getTotal(),
                        f.getTipoPago());
                }
                System.out.println("+----------------+-------------+--------------+--------------+");
            }
        } catch (Exception ex) {
            System.out.println(RED + "Error: " + ex.getMessage() + RESET);
        }
    }

    private void verDetalleFactura() {
        System.out.print("\nNúmero de factura: ");
        String numFactura = scanner.nextLine();
        try {
            DetalleFacturaViewModel factura = facturaService.obtenerFacturaPorNumFactura(numFactura);
            if (factura == null) {
                System.out.println(RED + "Factura no encontrada." + RESET);
                return;
            }

            // Imprimir factura como recibo
            System.out.println("\n" + CYAN + "+===========================================================+" + RESET);
            System.out.println(CYAN + "|" + RESET + "                  " + YELLOW + "COMERCIALIZADORA CLICON" + RESET + "                  " + CYAN + "|" + RESET);
            System.out.println(CYAN + "+===========================================================+" + RESET);
            System.out.printf(CYAN + "|" + RESET + " " + YELLOW + "Factura:" + RESET + " %-48s " + CYAN + "|" + RESET + "%n", factura.getNumFactura());
            System.out.printf(CYAN + "|" + RESET + " " + YELLOW + "Fecha:" + RESET + "   %-48s " + CYAN + "|" + RESET + "%n", factura.getFecha());
            System.out.println(CYAN + "+===========================================================+" + RESET);
            System.out.printf(CYAN + "|" + RESET + " " + YELLOW + "Cliente:" + RESET + " %-48s " + CYAN + "|" + RESET + "%n", truncate(factura.getClienteNombre(), 48));
            System.out.printf(CYAN + "|" + RESET + " " + YELLOW + "Cédula:" + RESET + "  %-48s " + CYAN + "|" + RESET + "%n", factura.getClienteCedula());
            System.out.printf(CYAN + "|" + RESET + " " + YELLOW + "Tipo de Pago:" + RESET + " %-42s " + CYAN + "|" + RESET + "%n", "E".equals(factura.getTipoPago()) ? "Efectivo" : "Crédito");
            System.out.println(CYAN + "+===========================================================+" + RESET);
            System.out.println(CYAN + "|" + RESET + " " + YELLOW + "PRODUCTOS" + RESET + "                                                 " + CYAN + "|" + RESET);
            System.out.println(CYAN + "+===========================================================+" + RESET);
            
            for (DetalleProducto p : factura.getProductos()) {
                String productoLinea = String.format("%s x%d", truncate(p.getNombre(), 40), p.getCantidad());
                System.out.printf(CYAN + "|" + RESET + " %-44s %s%,13.2f" + RESET + " " + CYAN + "|" + RESET + "%n", productoLinea, GREEN, p.getSubtotal());
            }
            
            System.out.println(CYAN + "+===========================================================+" + RESET);
            BigDecimal subtotal = factura.getSubtotal() != null ? factura.getSubtotal() : BigDecimal.ZERO;
            BigDecimal iva = factura.getIva() != null ? factura.getIva() : BigDecimal.ZERO;
            System.out.printf(CYAN + "|" + RESET + " %-44s %s%,13.2f" + RESET + " " + CYAN + "|" + RESET + "%n", "Subtotal:", WHITE, subtotal);
            System.out.printf(CYAN + "|" + RESET + " %-44s %s%,13.2f" + RESET + " " + CYAN + "|" + RESET + "%n", "IVA (15%):", WHITE, iva);
            System.out.println(CYAN + "+===========================================================+" + RESET);
            System.out.printf(CYAN + "|" + RESET + " " + YELLOW + "%-43s" + RESET + " %s%,13.2f" + RESET + " " + CYAN + "|" + RESET + "%n", "TOTAL:", GREEN, factura.getTotal());
            System.out.println(CYAN + "+===========================================================+" + RESET);
        } catch (Exception ex) {
            System.out.println(RED + "Error: " + ex.getMessage() + RESET);
        }
    }

    private void verAmortizacion() {
        System.out.println("\n" + CYAN + "--- Ver Amortización ---" + RESET);
        System.out.print(YELLOW + "Número de factura: " + RESET);
        String numFactura = scanner.nextLine();
        try {
            List<CuotaAmortizacion> cuotas = facturaService.obtenerAmortizacion(numFactura);
            DetalleFacturaViewModel factura = facturaService.obtenerFacturaPorNumFactura(numFactura);

            if (cuotas == null || factura == null) {
                System.out.println(RED + "❌ Factura no encontrada o no tiene amortización." + RESET);
                return;
            }

            System.out.println("\n" + CYAN + "************************************************************" + RESET);
            System.out.println(CYAN + "*" + RESET + "              " + YELLOW + "TABLA DE AMORTIZACIÓN" + RESET + "                     " + CYAN + "*" + RESET);
            System.out.println(CYAN + "************************************************************" + RESET);
            System.out.println(YELLOW + "📄 Factura:" + RESET + " " + numFactura);
            System.out.println(YELLOW + "💰 Monto total:" + RESET + " " + GREEN + String.format("$%,.2f", factura.getTotal()) + RESET);
            System.out.println(YELLOW + "📅 Plazo:" + RESET + " " + cuotas.size() + " cuotas");
            System.out.println(YELLOW + "📊 Tasa interés anual:" + RESET + " 15%");
            System.out.println(YELLOW + "💵 Cuota mensual:" + RESET + " " + GREEN + (cuotas.isEmpty() ? "N/A" : String.format("$%,.2f", cuotas.get(0).getValorCuota())) + RESET);

            System.out.println("\n+-------+--------------+--------------+--------------+--------------+");
            System.out.println("| " + YELLOW + "Cuota" + RESET + " | " + YELLOW + "Capital" + RESET + "      | " + YELLOW + "Interés" + RESET + "      | " + YELLOW + "Valor Cuota" + RESET + "  | " + YELLOW + "Saldo" + RESET + "        |");
            System.out.println("+-------+--------------+--------------+--------------+--------------+");

            BigDecimal saldoPendiente = factura.getTotal();
            for (int i = 0; i < cuotas.size(); i++) {
                CuotaAmortizacion cuota = cuotas.get(i);
                cuota.setNumeroCuota(i + 1);
                cuota.setSaldoPendiente(saldoPendiente.subtract(cuota.getCapital()));
                saldoPendiente = cuota.getSaldoPendiente();
                
                // Ajustar el saldo a 0 si está muy cerca de cero (evitar -0.02, -0.01, etc.)
                if (saldoPendiente.abs().compareTo(new BigDecimal("0.10")) < 0) {
                    saldoPendiente = BigDecimal.ZERO;
                    cuota.setSaldoPendiente(BigDecimal.ZERO);
                }

                System.out.printf("| " + GREEN + "%5d" + RESET + " | %s%,10.2f" + RESET + " | %s%,10.2f" + RESET + " | %s%,10.2f" + RESET + " | %s%,10.2f" + RESET + " |%n", 
                    cuota.getNumeroCuota(),
                    CYAN, cuota.getCapital(),
                    YELLOW, cuota.getInteres(),
                    WHITE, cuota.getValorCuota(),
                    PURPLE, cuota.getSaldoPendiente());
            }
            System.out.println("+-------+--------------+--------------+--------------+--------------+");
            System.out.println(CYAN + "************************************************************" + RESET);
        } catch (Exception ex) {
            String errorMsg = ex.getMessage();
            // Parse JSON error message if present
            if (errorMsg != null && errorMsg.contains("\"message\":\"")) {
                try {
                    int start = errorMsg.indexOf("\"message\":\"") + 11;
                    int end = errorMsg.indexOf("\"", start);
                    if (end > start) {
                        errorMsg = errorMsg.substring(start, end);
                    }
                } catch (Exception e) {
                    // Keep original message if parsing fails
                }
            }
            System.out.println("\n" + RED + "************************************************************" + RESET);
            System.out.println(RED + "❌ ERROR" + RESET);
            System.out.println(YELLOW + "Mensaje: " + RESET + errorMsg);
            System.out.println(RED + "************************************************************" + RESET);
        }
    }

    private FacturaResumen convertToResumen(DetalleFacturaViewModel factura) {
        FacturaResumen resumen = new FacturaResumen();
        resumen.setNumFactura(factura.getNumFactura());
        resumen.setFecha(factura.getFecha().atStartOfDay());
        resumen.setCedula(factura.getClienteCedula());
        resumen.setTotal(factura.getTotal());
        resumen.setTipoPago("E".equals(factura.getTipoPago()) ? "Efectivo" : "Credito");
        resumen.setCodCreditoRef(factura.getCodigoCredito());
        return resumen;
    }

    private String truncate(String text, int maxLength) {
        if (text == null) return "";
        if (text.length() <= maxLength) return text;
        return text.substring(0, maxLength - 3) + "...";
    }

    // Métodos para cliente
    private void verProductos() {
        System.out.println("\n" + CYAN + "************************************************************" + RESET);
        System.out.println(CYAN + "*" + RESET + "                   " + YELLOW + "PRODUCTOS DISPONIBLES" + RESET + "                  " + CYAN + "*" + RESET);
        System.out.println(CYAN + "************************************************************" + RESET);
        try {
            List<Electrodomestico> productos = electrodomesticoService.listarElectrodomesticos();
            if (productos != null && !productos.isEmpty()) {
                System.out.println("+-------+--------------------------------+----------------+");
                System.out.println("| Código| Nombre                         | Precio         |");
                System.out.println("+-------+--------------------------------+----------------+");
                for (Electrodomestico p : productos) {
                    System.out.printf("| %-6s| %-30s | %-14s |\n",
                        p.getCodigo(),
                        p.getNombre().length() > 30 ? p.getNombre().substring(0, 30) : p.getNombre(),
                        "$" + p.getPrecio());
                }
                System.out.println("+-------+--------------------------------+----------------+");
            } else {
                System.out.println(YELLOW + "No hay productos disponibles." + RESET);
            }
        } catch (Exception e) {
            System.out.println(RED + "❌ Error al cargar productos: " + e.getMessage() + RESET);
        }
    }

    private void agregarAlCarrito() {
        System.out.println("\n" + CYAN + "************************************************************" + RESET);
        System.out.println(CYAN + "*" + RESET + "                 " + YELLOW + "AGREGAR AL CARRITO" + RESET + "                     " + CYAN + "*" + RESET);
        System.out.println(CYAN + "************************************************************" + RESET);
        System.out.print(YELLOW + "Código del producto: " + RESET);
        String codigo = scanner.nextLine();
        System.out.print(YELLOW + "Cantidad: " + RESET);
        int cantidad = scanner.nextInt();
        scanner.nextLine();

        try {
            carritoService.agregar(currentCedula, codigo, cantidad);
            System.out.println(GREEN + "✓ Producto agregado al carrito." + RESET);
        } catch (Exception e) {
            System.out.println(RED + "❌ Error: " + e.getMessage() + RESET);
        }
    }

    private void verCarrito() {
        System.out.println("\n" + CYAN + "************************************************************" + RESET);
        System.out.println(CYAN + "*" + RESET + "                      " + YELLOW + "MI CARRITO" + RESET + "                         " + CYAN + "*" + RESET);
        System.out.println(CYAN + "************************************************************" + RESET);
        try {
            List<ItemCarrito> items = carritoService.obtener(currentCedula);
            if (!items.isEmpty()) {
                System.out.println("+-------+--------+----------------+");
                System.out.println("| Código| Cantidad| Subtotal       |");
                System.out.println("+-------+--------+----------------+");
                double total = 0;
                for (ItemCarrito item : items) {
                    System.out.printf("| %-6s| %-8d| %-14.2f |\n",
                        item.getCodigo(), item.getCantidad(), item.getSubtotal());
                    total += item.getSubtotal();
                }
                System.out.println("+-------+--------+----------------+");
                System.out.println(YELLOW + "Total: $" + String.format("%.2f", total) + RESET);
            } else {
                System.out.println(YELLOW + "El carrito está vacío." + RESET);
            }
        } catch (Exception e) {
            System.out.println(RED + "❌ Error al cargar carrito: " + e.getMessage() + RESET);
        }
    }

    private void removerDelCarrito() {
        System.out.println("\n" + CYAN + "************************************************************" + RESET);
        System.out.println(CYAN + "*" + RESET + "               " + YELLOW + "REMOVER DEL CARRITO" + RESET + "                   " + CYAN + "*" + RESET);
        System.out.println(CYAN + "************************************************************" + RESET);
        System.out.print(YELLOW + "Código del producto a remover: " + RESET);
        String codigo = scanner.nextLine();

        try {
            carritoService.remover(currentCedula, codigo);
            System.out.println(GREEN + "✓ Producto removido del carrito." + RESET);
        } catch (Exception e) {
            System.out.println(RED + "❌ Error: " + e.getMessage() + RESET);
        }
    }

    private void confirmarCompra() {
        System.out.println("\n" + CYAN + "************************************************************" + RESET);
        System.out.println(CYAN + "*" + RESET + "                 " + YELLOW + "CONFIRMAR COMPRA" + RESET + "                     " + CYAN + "*" + RESET);
        System.out.println(CYAN + "************************************************************" + RESET);
        System.out.print(YELLOW + "Número de cuotas: " + RESET);
        int cuotas = scanner.nextInt();
        scanner.nextLine();

        try {
            Map<String, Object> response = carritoService.confirmar(currentCedula, cuotas);
            System.out.println(GREEN + "✓ Compra confirmada. Factura generada." + RESET);
            System.out.println("Número de factura: " + response.get("numeroFactura"));
            System.out.println("Total: $" + response.get("total"));
        } catch (Exception e) {
            String errorMsg = e.getMessage();
            // Parse JSON error message if present
            if (errorMsg != null && errorMsg.contains("\"message\":\"")) {
                try {
                    int start = errorMsg.indexOf("\"message\":\"") + 11;
                    int end = errorMsg.indexOf("\"", start);
                    if (end > start) {
                        errorMsg = errorMsg.substring(start, end);
                    }
                } catch (Exception ex) {
                    // Keep original message if parsing fails
                }
            }
            System.out.println(RED + "❌ Error: " + errorMsg + RESET);
        }
    }

    private void verMisFacturas() {
        System.out.println("\n" + CYAN + "************************************************************" + RESET);
        System.out.println(CYAN + "*" + RESET + "                   " + YELLOW + "MIS FACTURAS" + RESET + "                        " + CYAN + "*" + RESET);
        System.out.println(CYAN + "************************************************************" + RESET);
        try {
            List<FacturaResumen> facturas = facturaService.obtenerFacturasPorCedula(currentCedula);
            if (!facturas.isEmpty()) {
                System.out.println("+----------------+--------+------------+");
                System.out.println("| Número         | Total  | Fecha      |");
                System.out.println("+----------------+--------+------------+");
                for (FacturaResumen f : facturas) {
                    System.out.printf("| %-14s | %-6.2f | %-10s |\n",
                        f.getNumFactura(), f.getTotal(), f.getFecha());
                }
                System.out.println("+----------------+--------+------------+");
            } else {
                System.out.println(YELLOW + "No tienes facturas." + RESET);
            }
        } catch (Exception e) {
            System.out.println(RED + "❌ Error al cargar facturas: " + e.getMessage() + RESET);
        }
    }
}