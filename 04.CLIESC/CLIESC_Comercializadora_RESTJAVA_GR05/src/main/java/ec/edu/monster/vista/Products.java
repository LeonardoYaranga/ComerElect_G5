package ec.edu.monster.vista;

import ec.edu.monster.models.Electrodomestico;
import ec.edu.monster.services.ElectrodomesticoService;
import ec.edu.monster.services.CarritoLocalService;
import ec.edu.monster.models.CarritoItem;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.net.URL;
import java.util.List;
import java.util.ArrayList;
import javax.imageio.ImageIO;
import java.math.BigDecimal;

/**
 *
 * @author Dome
 */
public class Products extends javax.swing.JPanel {

    private HomeCliente parentFrame;
    private ElectrodomesticoService electrodomesticoService;
    private CarritoLocalService carritoService;
    private List<Electrodomestico> productos = new ArrayList<>();
    private String cedulaUsuario = "1234567890"; // TODO: Obtener de sesión

    public Products(HomeCliente parent) {
        this.parentFrame = parent;

        // Inicializar servicios
        org.springframework.web.client.RestTemplate restTemplate = new org.springframework.web.client.RestTemplate();
        this.electrodomesticoService = new ElectrodomesticoService(restTemplate, "http://192.168.137.1:8081/api");

        // Usar servicio local de carrito (singleton)
        this.carritoService = CarritoLocalService.getInstance();

        initComponents();

        // Configurar el panel de productos con scroll
        pnlProducts.setLayout(new GridLayout(0, 3, 20, 20)); // 3 columnas, spacing de 20px

        // Cargar productos al inicializar
        cargarProductos();
    }

    // </editor-fold>

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated
    // Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel17 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        lblTitle = new javax.swing.JLabel();
        pnlProducts = new javax.swing.JPanel();
        PanelNewProduct = new ec.edu.monster.vista.ModernPanel();
        lblNameProduct = new javax.swing.JLabel();
        lblDescriptionProduct = new javax.swing.JLabel();
        lblPriceProduct = new javax.swing.JLabel();
        lblCodeProduct = new javax.swing.JLabel();
        lblImageProduct = new javax.swing.JLabel();
        btn_dashboard = new ec.edu.monster.vista.ModernButton();

        setBackground(new java.awt.Color(255, 255, 255));
        setMinimumSize(new java.awt.Dimension(820, 590));
        setPreferredSize(new java.awt.Dimension(820, 590));
        setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel17.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        jLabel17.setForeground(new java.awt.Color(102, 102, 102));
        jLabel17.setText("Selecciona los productos que deseas comprar");
        add(jLabel17, new org.netbeans.lib.awtextra.AbsoluteConstraints(490, 30, -1, -1));

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Bolsa Azul.png"))); // NOI18N
        add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 10, -1, -1));

        lblTitle.setFont(new java.awt.Font("Britannic Bold", 0, 28)); // NOI18N
        lblTitle.setText("Catálogo de Productos");
        add(lblTitle, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 20, -1, -1));

        pnlProducts.setBackground(new java.awt.Color(245, 241, 248));
        pnlProducts.setAutoscrolls(true);

        PanelNewProduct.setBorderRadius(5);

        lblNameProduct.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        lblNameProduct.setText("Cocina Extrema");

        lblDescriptionProduct.setFont(new java.awt.Font("Roboto", 0, 10)); // NOI18N
        lblDescriptionProduct.setForeground(new java.awt.Color(102, 102, 102));
        lblDescriptionProduct.setText("Descripcion nuevo producto");

        lblPriceProduct.setFont(new java.awt.Font("Roboto", 1, 16)); // NOI18N
        lblPriceProduct.setForeground(new java.awt.Color(0, 102, 204));
        lblPriceProduct.setText("$97.00");

        lblCodeProduct.setFont(new java.awt.Font("Roboto", 0, 10)); // NOI18N
        lblCodeProduct.setForeground(new java.awt.Color(102, 102, 102));
        lblCodeProduct.setText("Código: ECOC2041");

        lblImageProduct.setText(".");

        btn_dashboard.setBackgroundColor(new java.awt.Color(13, 110, 253));
        btn_dashboard.setBorder(null);
        btn_dashboard.setBorderColor(new java.awt.Color(13, 110, 253));
        btn_dashboard.setBorderRadius(5);
        btn_dashboard.setBorderThickness(0);
        btn_dashboard.setHoverColor(new java.awt.Color(18, 63, 129));
        btn_dashboard.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Carrito Blanco.png"))); // NOI18N
        btn_dashboard.setPressedColor(new java.awt.Color(18, 63, 129));
        btn_dashboard.setText("Agregar al Carrito");
        btn_dashboard.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        btn_dashboard.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_dashboardActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout PanelNewProductLayout = new javax.swing.GroupLayout(PanelNewProduct);
        PanelNewProduct.setLayout(PanelNewProductLayout);
        PanelNewProductLayout.setHorizontalGroup(
                PanelNewProductLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(PanelNewProductLayout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(PanelNewProductLayout
                                        .createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(PanelNewProductLayout.createSequentialGroup()
                                                .addComponent(lblPriceProduct, javax.swing.GroupLayout.PREFERRED_SIZE,
                                                        103, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED,
                                                        javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addComponent(lblCodeProduct)
                                                .addGap(19, 19, 19))
                                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, PanelNewProductLayout
                                                .createSequentialGroup()
                                                .addGroup(PanelNewProductLayout
                                                        .createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                                        .addComponent(btn_dashboard,
                                                                javax.swing.GroupLayout.Alignment.LEADING,
                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                        .addComponent(lblImageProduct,
                                                                javax.swing.GroupLayout.Alignment.LEADING,
                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING,
                                                                PanelNewProductLayout.createSequentialGroup()
                                                                        .addGroup(PanelNewProductLayout
                                                                                .createParallelGroup(
                                                                                        javax.swing.GroupLayout.Alignment.LEADING)
                                                                                .addComponent(lblNameProduct,
                                                                                        javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                        219,
                                                                                        javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                                .addComponent(lblDescriptionProduct,
                                                                                        javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                        272,
                                                                                        javax.swing.GroupLayout.PREFERRED_SIZE))
                                                                        .addGap(0, 34, Short.MAX_VALUE)))
                                                .addContainerGap()))));
        PanelNewProductLayout.setVerticalGroup(
                PanelNewProductLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(PanelNewProductLayout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(lblImageProduct, javax.swing.GroupLayout.PREFERRED_SIZE, 116,
                                        javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(lblNameProduct, javax.swing.GroupLayout.PREFERRED_SIZE, 24,
                                        javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(lblDescriptionProduct)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(PanelNewProductLayout
                                        .createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                        .addComponent(lblCodeProduct)
                                        .addComponent(lblPriceProduct, javax.swing.GroupLayout.PREFERRED_SIZE, 30,
                                                javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED,
                                        javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(btn_dashboard, javax.swing.GroupLayout.PREFERRED_SIZE,
                                        javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addContainerGap()));

        javax.swing.GroupLayout pnlProductsLayout = new javax.swing.GroupLayout(pnlProducts);
        pnlProducts.setLayout(pnlProductsLayout);
        pnlProductsLayout.setHorizontalGroup(
                pnlProductsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(pnlProductsLayout.createSequentialGroup()
                                .addGap(22, 22, 22)
                                .addComponent(PanelNewProduct, javax.swing.GroupLayout.PREFERRED_SIZE,
                                        javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addContainerGap(470, Short.MAX_VALUE)));
        pnlProductsLayout.setVerticalGroup(
                pnlProductsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(pnlProductsLayout.createSequentialGroup()
                                .addGap(17, 17, 17)
                                .addComponent(PanelNewProduct, javax.swing.GroupLayout.PREFERRED_SIZE,
                                        javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addContainerGap(340, Short.MAX_VALUE)));

        add(pnlProducts, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 70, -1, -1));

        getAccessibleContext().setAccessibleName("");
        getAccessibleContext().setAccessibleDescription("");
    }// </editor-fold>//GEN-END:initComponents

    /**
     * Carga los productos desde el API en segundo plano
     */
    private void cargarProductos() {
        SwingWorker<List<Electrodomestico>, Void> worker = new SwingWorker<List<Electrodomestico>, Void>() {
            @Override
            protected List<Electrodomestico> doInBackground() throws Exception {
                return electrodomesticoService.listarElectrodomesticos();
            }

            @Override
            protected void done() {
                try {
                    productos = get();
                    if (productos != null && !productos.isEmpty()) {
                        renderizarProductos();
                    } else {
                        mostrarMensaje("No hay productos disponibles", "info");
                    }
                } catch (Exception e) {
                    mostrarMensaje("Error al cargar productos: " + e.getMessage(), "error");
                    e.printStackTrace();
                }
            }
        };
        worker.execute();
    }

    /**
     * Renderiza dinámicamente las tarjetas de productos
     */
    private void renderizarProductos() {
        pnlProducts.removeAll(); // Limpiar productos anteriores

        for (Electrodomestico producto : productos) {
            JPanel tarjeta = crearTarjetaProducto(producto);
            pnlProducts.add(tarjeta);
        }

        pnlProducts.revalidate();
        pnlProducts.repaint();
    }

    /**
     * Crea una tarjeta visual para un producto
     */
    private JPanel crearTarjetaProducto(Electrodomestico producto) {
        ModernPanel tarjeta = new ModernPanel();
        tarjeta.setPanelBackground(Color.WHITE);
        tarjeta.setBorderRadius(5);
        tarjeta.setPreferredSize(new Dimension(250, 280));
        tarjeta.setLayout(new BorderLayout());

        // Panel principal con todos los elementos
        JPanel contenido = new JPanel();
        contenido.setLayout(new BoxLayout(contenido, BoxLayout.Y_AXIS));
        contenido.setBackground(Color.WHITE);
        contenido.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Imagen del producto
        JLabel lblImagen = new JLabel();
        lblImagen.setHorizontalAlignment(JLabel.CENTER);
        lblImagen.setPreferredSize(new Dimension(230, 116));
        lblImagen.setMaximumSize(new Dimension(230, 116));
        lblImagen.setMinimumSize(new Dimension(230, 116));

        // Placeholder mientras carga la imagen
        lblImagen.setIcon(new ImageIcon(getClass().getResource("/Bolsa Azul.png")));

        // Cargar imagen desde URL si está disponible
        if (producto.getImageUrl() != null && !producto.getImageUrl().isEmpty()) {
            cargarImagenDesdeURL(producto.getImageUrl(), lblImagen);
        }
        contenido.add(lblImagen);
        contenido.add(Box.createRigidArea(new Dimension(0, 5)));

        // Nombre del producto
        JLabel lblNombre = new JLabel(producto.getNombre());
        lblNombre.setFont(new Font("Roboto", Font.BOLD, 14));
        lblNombre.setAlignmentX(Component.LEFT_ALIGNMENT);
        contenido.add(lblNombre);
        contenido.add(Box.createRigidArea(new Dimension(0, 5)));

        // Descripción del producto
        String descripcion = producto.getDescripcion() != null ? producto.getDescripcion() : "Sin descripción";
        if (descripcion.length() > 40) {
            descripcion = descripcion.substring(0, 37) + "...";
        }
        JLabel lblDescripcion = new JLabel(descripcion);
        lblDescripcion.setFont(new Font("Roboto", Font.PLAIN, 10));
        lblDescripcion.setForeground(new Color(102, 102, 102));
        lblDescripcion.setAlignmentX(Component.LEFT_ALIGNMENT);
        contenido.add(lblDescripcion);
        contenido.add(Box.createRigidArea(new Dimension(0, 5)));

        // Panel para precio y código
        JPanel pnlInfo = new JPanel();
        pnlInfo.setLayout(new FlowLayout(FlowLayout.LEFT, 0, 0));
        pnlInfo.setBackground(Color.WHITE);
        pnlInfo.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel lblPrecio = new JLabel("$" + producto.getPrecio().toString());
        lblPrecio.setFont(new Font("Roboto", Font.BOLD, 16));
        lblPrecio.setForeground(new Color(0, 102, 204));
        pnlInfo.add(lblPrecio);

        pnlInfo.add(Box.createHorizontalStrut(10));

        JLabel lblCodigo = new JLabel("Código: " + producto.getCodigo());
        lblCodigo.setFont(new Font("Roboto", Font.PLAIN, 10));
        lblCodigo.setForeground(new Color(102, 102, 102));
        pnlInfo.add(lblCodigo);

        contenido.add(pnlInfo);
        contenido.add(Box.createRigidArea(new Dimension(0, 8)));

        // Botón agregar al carrito
        ModernButton btnAgregar = new ModernButton();
        btnAgregar.setText("Agregar al Carrito");
        btnAgregar.setIcon(new ImageIcon(getClass().getResource("/Carrito Blanco.png")));
        btnAgregar.setFont(new Font("Roboto", Font.BOLD, 12));
        btnAgregar.setBackgroundColor(new Color(13, 110, 253));
        btnAgregar.setBorderColor(new Color(13, 110, 253));
        btnAgregar.setBorderRadius(5);
        btnAgregar.setHoverColor(new Color(18, 63, 129));
        btnAgregar.setPressedColor(new Color(18, 63, 129));
        btnAgregar.setMaximumSize(new Dimension(230, 35));
        btnAgregar.setPreferredSize(new Dimension(230, 35));
        btnAgregar.setAlignmentX(Component.LEFT_ALIGNMENT);

        // Evento del botón
        btnAgregar.addActionListener(evt -> abrirModalAgregarAlCarrito(producto));

        contenido.add(btnAgregar);

        tarjeta.add(contenido, BorderLayout.CENTER);

        return tarjeta;
    }

    /**
     * Carga una imagen desde URL de forma asíncrona
     */
    private void cargarImagenDesdeURL(String imageUrl, JLabel lblImagen) {
        SwingWorker<ImageIcon, Void> worker = new SwingWorker<ImageIcon, Void>() {
            @Override
            protected ImageIcon doInBackground() throws Exception {
                URL url = new URL(imageUrl);
                BufferedImage img = ImageIO.read(url);
                if (img != null) {
                    // Escalar la imagen para que quepa en el label
                    Image scaledImg = img.getScaledInstance(230, 116, Image.SCALE_SMOOTH);
                    return new ImageIcon(scaledImg);
                }
                return null;
            }

            @Override
            protected void done() {
                try {
                    ImageIcon icon = get();
                    if (icon != null) {
                        lblImagen.setIcon(icon);
                    }
                } catch (Exception e) {
                    // Si falla, mantener el placeholder
                    System.err.println("Error al cargar imagen: " + e.getMessage());
                }
            }
        };
        worker.execute();
    }

    /**
     * Abre el modal para agregar un producto al carrito
     */
    private void abrirModalAgregarAlCarrito(Electrodomestico producto) {
        java.awt.Window parentWindow = javax.swing.SwingUtilities.getWindowAncestor(this);

        if (parentWindow instanceof java.awt.Frame) {
            java.awt.Frame frame = (java.awt.Frame) parentWindow;

            // Crear el modal con el nombre del producto
            ModalProduct modal = new ModalProduct(frame, true, producto.getNombre());
            modal.setVisible(true);

            String resultado = modal.getSeleccion();

            if (resultado.equals("OK")) {
                try {
                    int cantidad = Integer.parseInt(modal.getCantidad());
                    if (cantidad <= 0) {
                        mostrarMensaje("La cantidad debe ser mayor a 0", "error");
                        return;
                    }
                    agregarAlCarrito(producto, cantidad);
                } catch (NumberFormatException e) {
                    mostrarMensaje("Cantidad inválida", "error");
                }
            }
        }
    }

    /**
     * Agrega un producto al carrito usando el servicio local
     */
    private void agregarAlCarrito(Electrodomestico producto, int cantidad) {
        try {
            // Crear item del carrito
            CarritoItem item = new CarritoItem(
                    producto.getCodigo(),
                    producto.getNombre(),
                    producto.getPrecio(),
                    cantidad);

            // Agregar al carrito local
            carritoService.agregarProducto(item);

            // Mostrar mensaje de éxito
            mostrarMensaje(producto.getNombre() + " agregado al carrito", "success");

            // Actualizar badge del carrito
            actualizarBadgeCarrito();
        } catch (Exception e) {
            mostrarMensaje("Error al agregar producto: " + e.getMessage(), "error");
            e.printStackTrace();
        }
    }

    /**
     * Actualiza el badge del carrito en el HomeCliente
     */
    private void actualizarBadgeCarrito() {
        // TODO: Implementar cuando HomeCliente tenga el método
        // parentFrame.actualizarBadgeCarrito();
    }

    /**
     * Muestra un mensaje al usuario
     */
    private void mostrarMensaje(String mensaje, String tipo) {
        int tipoMensaje = JOptionPane.INFORMATION_MESSAGE;
        String titulo = "Información";

        if (tipo.equals("error")) {
            tipoMensaje = JOptionPane.ERROR_MESSAGE;
            titulo = "Error";
        } else if (tipo.equals("success")) {
            tipoMensaje = JOptionPane.INFORMATION_MESSAGE;
            titulo = "Éxito";
        }

        JOptionPane.showMessageDialog(this, mensaje, titulo, tipoMensaje);
    }

    private void btn_dashboardActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_btn_dashboardActionPerformed
        java.awt.Window parentWindow = javax.swing.SwingUtilities.getWindowAncestor(this);

        // 2. Nos aseguramos de que sea un Frame (lo cual es, es tu Home)
        if (parentWindow instanceof java.awt.Frame) {
            java.awt.Frame parentFrame = (java.awt.Frame) parentWindow;

            // 3. Creamos el modal, pasándole el Frame padre y 'true' para que sea modal
            ModalProduct product = new ModalProduct(parentFrame, true);

            // 4. Mostramos el modal.
            // El código se detendrá aquí hasta que el usuario cierre el diálogo.
            product.setVisible(true);

            // 5. (IMPORTANTE) Recogemos los datos DESPUÉS de que el modal se cierra.
            String resultado = product.getSeleccion(); // Vemos si el usuario presionó "Crear" o "Cancelar"

            // 6. Verificamos si el usuario le dio a "Crear" (revisarás esto en el
            // Modal.java)
            if (resultado.equals("OK")) {
                // El usuario le dio a "Crear", obtenemos los datos
                String cantidad = product.getCantidad();

                System.out.println("Agregado: " + cantidad);

            } else {
                // El usuario le dio a "Cancelar"
                System.out.println("Operación cancelada.");
            }
        }
    }// GEN-LAST:event_btn_dashboardActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private ec.edu.monster.vista.ModernPanel PanelNewProduct;
    private ec.edu.monster.vista.ModernButton btn_dashboard;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel lblCodeProduct;
    private javax.swing.JLabel lblDescriptionProduct;
    private javax.swing.JLabel lblImageProduct;
    private javax.swing.JLabel lblNameProduct;
    private javax.swing.JLabel lblPriceProduct;
    private javax.swing.JLabel lblTitle;
    private javax.swing.JPanel pnlProducts;
    // End of variables declaration//GEN-END:variables
}
