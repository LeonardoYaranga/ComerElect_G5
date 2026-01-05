package ec.edu.monster.vista;

import ec.edu.monster.models.CarritoItem;
import ec.edu.monster.services.CarritoLocalService;
import ec.edu.monster.services.FacturaService;
import ec.edu.monster.models.SolicitudFactura;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.List;
import java.util.Map;
import java.math.BigDecimal;

/**
 *
 * @author Dome
 */
public class MyCart extends javax.swing.JPanel {
    private HomeCliente parentFrame;
    private CarritoLocalService carritoService;
    private FacturaService facturaService;
    private String cedulaUsuario = "1234567890"; // TODO: Obtener de sesión

    public MyCart(HomeCliente parent) {
        this.parentFrame = parent;

        // Usar servicio local de carrito (singleton)
        this.carritoService = CarritoLocalService.getInstance();

        // Inicializar servicio de facturas
        org.springframework.web.client.RestTemplate restTemplate = new org.springframework.web.client.RestTemplate();
        this.facturaService = new FacturaService(restTemplate, "http://192.168.137.1:8081/api");

        initComponents();

        // Configurar tabla
        configurarTabla();

        // Configurar combobox de cuotas
        configurarCombobox();

        // Cargar carrito
        cargarCarrito();
    }

    // </editor-fold>

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated
    // Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel17 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        lblTitle = new javax.swing.JLabel();
        PanelCart = new ec.edu.monster.vista.ModernPanel();
        lblNameProduct = new javax.swing.JLabel();
        lblTotal = new javax.swing.JLabel();
        btn_confirmBuy = new ec.edu.monster.vista.ModernButton();
        lblTitle1 = new javax.swing.JLabel();
        lblNameProduct2 = new javax.swing.JLabel();
        lblCantidadProductos = new javax.swing.JLabel();
        lblNameProduct3 = new javax.swing.JLabel();
        lblCantidadProductos1 = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        combobox1 = new ec.edu.monster.vista.Combobox();
        lblNameProduct1 = new javax.swing.JLabel();
        btn_BackProducts = new ec.edu.monster.vista.ModernButton();
        PanelCart1 = new ec.edu.monster.vista.ModernPanel();
        TableProducts = new ec.edu.monster.vista.ModernTable();

        setBackground(new java.awt.Color(255, 255, 255));
        setMinimumSize(new java.awt.Dimension(820, 590));
        setPreferredSize(new java.awt.Dimension(820, 590));
        setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel17.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        jLabel17.setForeground(new java.awt.Color(102, 102, 102));
        jLabel17.setText("Revisa y gestiona los productos en tu carrito");
        add(jLabel17, new org.netbeans.lib.awtextra.AbsoluteConstraints(490, 7, 320, 30));

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Carrito Verde.png"))); // NOI18N
        add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 10, -1, -1));

        lblTitle.setFont(new java.awt.Font("Britannic Bold", 0, 28)); // NOI18N
        lblTitle.setText("Mi Carrito");
        add(lblTitle, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 9, -1, 40));

        PanelCart.setBorderRadius(5);

        lblNameProduct.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        lblNameProduct.setText("Número de Cuotas:");

        lblTotal.setFont(new java.awt.Font("Roboto", 1, 22)); // NOI18N
        lblTotal.setForeground(new java.awt.Color(0, 102, 204));
        lblTotal.setText("$97.00");

        btn_confirmBuy.setBackgroundColor(new java.awt.Color(34, 196, 96));
        btn_confirmBuy.setBorder(null);
        btn_confirmBuy.setBorderColor(new java.awt.Color(34, 196, 96));
        btn_confirmBuy.setBorderRadius(5);
        btn_confirmBuy.setBorderThickness(0);
        btn_confirmBuy.setHoverColor(new java.awt.Color(0, 102, 0));
        btn_confirmBuy.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Carrito Blanco.png"))); // NOI18N
        btn_confirmBuy.setPressedColor(new java.awt.Color(0, 102, 0));
        btn_confirmBuy.setText("Confirmar Compra");
        btn_confirmBuy.setToolTipText("");
        btn_confirmBuy.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        btn_confirmBuy.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_confirmBuyActionPerformed(evt);
            }
        });

        lblTitle1.setFont(new java.awt.Font("Britannic Bold", 0, 28)); // NOI18N
        lblTitle1.setText("Resumen");

        lblNameProduct2.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        lblNameProduct2.setForeground(new java.awt.Color(102, 102, 102));
        lblNameProduct2.setText("Cantidad de artículos:");

        lblCantidadProductos.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        lblCantidadProductos.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblCantidadProductos.setText("1");

        lblNameProduct3.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        lblNameProduct3.setForeground(new java.awt.Color(102, 102, 102));
        lblNameProduct3.setText("Subtotal: ");

        lblCantidadProductos1.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        lblCantidadProductos1.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblCantidadProductos1.setText("$97.00");

        combobox1.setArrowColor(new java.awt.Color(153, 102, 255));
        combobox1.setLineColor(new java.awt.Color(153, 102, 255));
        combobox1.setFont(new java.awt.Font("Roboto", 0, 12)); // NOI18N
        combobox1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                combobox1ActionPerformed(evt);
            }
        });

        lblNameProduct1.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        lblNameProduct1.setText("Total:");

        btn_BackProducts.setBackgroundColor(new java.awt.Color(153, 153, 153));
        btn_BackProducts.setBorderColor(new java.awt.Color(153, 153, 153));
        btn_BackProducts.setBorderRadius(5);
        btn_BackProducts.setBorderThickness(0);
        btn_BackProducts.setHoverColor(new java.awt.Color(102, 102, 102));
        btn_BackProducts.setPressedColor(new java.awt.Color(102, 102, 102));
        btn_BackProducts.setText("<-  Seguir Comprando");
        btn_BackProducts.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        btn_BackProducts.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_BackProductsActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout PanelCartLayout = new javax.swing.GroupLayout(PanelCart);
        PanelCart.setLayout(PanelCartLayout);
        PanelCartLayout.setHorizontalGroup(
                PanelCartLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, PanelCartLayout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(PanelCartLayout
                                        .createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                        .addComponent(btn_BackProducts, javax.swing.GroupLayout.DEFAULT_SIZE,
                                                javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(combobox1, javax.swing.GroupLayout.Alignment.LEADING,
                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(jSeparator1)
                                        .addGroup(PanelCartLayout.createSequentialGroup()
                                                .addComponent(lblNameProduct3, javax.swing.GroupLayout.PREFERRED_SIZE,
                                                        89, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED,
                                                        javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addComponent(lblCantidadProductos1,
                                                        javax.swing.GroupLayout.PREFERRED_SIZE, 90,
                                                        javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, PanelCartLayout
                                                .createSequentialGroup()
                                                .addComponent(lblNameProduct2, javax.swing.GroupLayout.PREFERRED_SIZE,
                                                        168, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 16,
                                                        Short.MAX_VALUE)
                                                .addComponent(lblCantidadProductos,
                                                        javax.swing.GroupLayout.PREFERRED_SIZE, 61,
                                                        javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addComponent(lblTotal, javax.swing.GroupLayout.Alignment.LEADING,
                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(btn_confirmBuy, javax.swing.GroupLayout.Alignment.LEADING,
                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, PanelCartLayout
                                                .createSequentialGroup()
                                                .addGroup(PanelCartLayout
                                                        .createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                                        .addComponent(lblTitle1,
                                                                javax.swing.GroupLayout.Alignment.LEADING,
                                                                javax.swing.GroupLayout.PREFERRED_SIZE, 126,
                                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addComponent(lblNameProduct,
                                                                javax.swing.GroupLayout.Alignment.LEADING,
                                                                javax.swing.GroupLayout.PREFERRED_SIZE, 219,
                                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addComponent(lblNameProduct1,
                                                                javax.swing.GroupLayout.Alignment.LEADING,
                                                                javax.swing.GroupLayout.PREFERRED_SIZE, 219,
                                                                javax.swing.GroupLayout.PREFERRED_SIZE))
                                                .addGap(0, 0, Short.MAX_VALUE)))
                                .addGap(19, 19, 19)));
        PanelCartLayout.setVerticalGroup(
                PanelCartLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(PanelCartLayout.createSequentialGroup()
                                .addComponent(lblTitle1, javax.swing.GroupLayout.PREFERRED_SIZE, 40,
                                        javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(PanelCartLayout
                                        .createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(lblNameProduct2, javax.swing.GroupLayout.PREFERRED_SIZE, 24,
                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(lblCantidadProductos, javax.swing.GroupLayout.PREFERRED_SIZE, 24,
                                                javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(PanelCartLayout
                                        .createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(lblNameProduct3, javax.swing.GroupLayout.PREFERRED_SIZE, 24,
                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(lblCantidadProductos1, javax.swing.GroupLayout.PREFERRED_SIZE, 24,
                                                javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10,
                                        javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(lblNameProduct, javax.swing.GroupLayout.PREFERRED_SIZE, 24,
                                        javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(combobox1, javax.swing.GroupLayout.PREFERRED_SIZE,
                                        javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(lblNameProduct1, javax.swing.GroupLayout.PREFERRED_SIZE, 24,
                                        javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(lblTotal, javax.swing.GroupLayout.PREFERRED_SIZE, 30,
                                        javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(btn_confirmBuy, javax.swing.GroupLayout.PREFERRED_SIZE,
                                        javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(btn_BackProducts, javax.swing.GroupLayout.PREFERRED_SIZE,
                                        javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addContainerGap(29, Short.MAX_VALUE)));

        add(PanelCart, new org.netbeans.lib.awtextra.AbsoluteConstraints(520, 70, 280, 420));

        PanelCart1.setBorderRadius(5);

        TableProducts.setHeaderBackground(new java.awt.Color(0, 0, 0));

        javax.swing.GroupLayout PanelCart1Layout = new javax.swing.GroupLayout(PanelCart1);
        PanelCart1.setLayout(PanelCart1Layout);
        PanelCart1Layout.setHorizontalGroup(
                PanelCart1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(PanelCart1Layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(TableProducts, javax.swing.GroupLayout.DEFAULT_SIZE, 468, Short.MAX_VALUE)
                                .addContainerGap()));
        PanelCart1Layout.setVerticalGroup(
                PanelCart1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(PanelCart1Layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(TableProducts, javax.swing.GroupLayout.PREFERRED_SIZE, 517,
                                        javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addContainerGap(17, Short.MAX_VALUE)));

        add(PanelCart1, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 70, 490, 550));

        getAccessibleContext().setAccessibleName("");
        getAccessibleContext().setAccessibleDescription("");
    }// </editor-fold>//GEN-END:initComponents

    /**
     * Configura la tabla de productos
     */
    private void configurarTabla() {
        DefaultTableModel model = new DefaultTableModel(
                new Object[][] {},
                new String[] { "Producto", "Precio", "Cantidad", "Subtotal", "Acción" }) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 4; // Solo la columna de acción es editable
            }
        };
        TableProducts.getTable().setModel(model);
    }

    /**
     * Configura el combobox de cuotas
     */
    private void configurarCombobox() {
        combobox1.removeAllItems();
        combobox1.addItem("-- Selecciona --");
        combobox1.addItem("3 cuotas");
        combobox1.addItem("6 cuotas");
        combobox1.addItem("12 cuotas");
        combobox1.addItem("24 cuotas");
    }

    /**
     * Carga los items del carrito desde el servicio local
     */
    private void cargarCarrito() {
        List<CarritoItem> items = carritoService.obtenerItems();

        if (items == null || items.isEmpty()) {
            mostrarCarritoVacio();
        } else {
            mostrarCarritoConItems(items);
        }
    }

    /**
     * Muestra el carrito vacío
     */
    private void mostrarCarritoVacio() {
        DefaultTableModel model = (DefaultTableModel) TableProducts.getTable().getModel();
        model.setRowCount(0);

        lblCantidadProductos.setText("0");
        lblCantidadProductos1.setText("$0.00");
        lblTotal.setText("$0.00");

        btn_confirmBuy.setEnabled(false);
        combobox1.setEnabled(false);
    }

    /**
     * Muestra el carrito con items
     */
    private void mostrarCarritoConItems(List<CarritoItem> items) {
        DefaultTableModel model = (DefaultTableModel) TableProducts.getTable().getModel();
        model.setRowCount(0);

        BigDecimal total = BigDecimal.ZERO;

        for (CarritoItem item : items) {
            String nombre = item.getNombre();
            BigDecimal precio = item.getPrecioUnitario();
            int cantidad = item.getCantidad();
            BigDecimal subtotal = item.getSubtotal();
            total = total.add(subtotal);

            String codigo = item.getCodigo();

            model.addRow(new Object[] {
                    nombre,
                    String.format("$%.2f", precio),
                    cantidad,
                    String.format("$%.2f", subtotal),
                    codigo // Guardamos el código para referencia
            });
        }

        // Actualizar resumen
        lblCantidadProductos.setText(String.valueOf(items.size()));
        lblCantidadProductos1.setText(String.format("$%.2f", total));
        lblTotal.setText(String.format("$%.2f", total));

        btn_confirmBuy.setEnabled(true);
        combobox1.setEnabled(true);
    }

    /**
     * Elimina un producto del carrito
     */
    private void eliminarProducto(String codigo) {
        int confirm = JOptionPane.showConfirmDialog(
                this,
                "¿Estás seguro de que deseas remover este producto?",
                "Confirmar eliminación",
                JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {
            carritoService.removerProducto(codigo);
            mostrarMensaje("Producto removido del carrito", "success");
            cargarCarrito(); // Recargar el carrito
        }
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

    /**
     * Obtiene el número de cuotas seleccionado
     */
    public int getNumeroCuotas() {
        String seleccion = (String) combobox1.getSelectedItem();
        if (seleccion == null || seleccion.contains("--")) {
            return 0;
        }

        // Extraer número de la cadena "X cuotas"
        try {
            return Integer.parseInt(seleccion.split(" ")[0]);
        } catch (Exception e) {
            return 0;
        }
    }

    /**
     * Extrae el mensaje de error del JSON de respuesta
     */
    private String extraerMensajeError(Exception e) {
        String mensajeCompleto = e.getMessage();

        // Buscar el patrón {"error":"...","message":"..."}
        try {
            int inicioJson = mensajeCompleto.indexOf("{");
            if (inicioJson != -1) {
                String json = mensajeCompleto.substring(inicioJson);

                // Extraer el campo "message"
                int inicioMessage = json.indexOf("\"message\":\"");
                if (inicioMessage != -1) {
                    inicioMessage += 11; // Longitud de "message":"
                    int finMessage = json.indexOf("\"", inicioMessage);
                    if (finMessage != -1) {
                        return json.substring(inicioMessage, finMessage);
                    }
                }
            }
        } catch (Exception ex) {
            // Si falla la extracción, devolver mensaje original
        }

        return mensajeCompleto;
    }

    /**
     * Crea la factura a partir del carrito y muestra el modal
     */
    private void confirmarCompra() {
        // Validar que el carrito no esté vacío
        if (carritoService.estaVacio()) {
            mostrarMensaje("El carrito está vacío", "error");
            return;
        }

        // Obtener número de cuotas
        int numeroCuotas = getNumeroCuotas();

        // Validar selección de cuotas
        if (numeroCuotas == 0) {
            mostrarMensaje("Debes seleccionar el número de cuotas", "error");
            return;
        }

        // Crear factura usando SwingWorker para no bloquear la UI
        SwingWorker<Map<String, Object>, Void> worker = new SwingWorker<Map<String, Object>, Void>() {
            @Override
            protected Map<String, Object> doInBackground() throws Exception {
                // Convertir carrito a solicitud de factura
                String tipoPago = "C"; // C = Crédito (siempre con cuotas)
                SolicitudFactura solicitud = carritoService.convertirASolicitudFactura(
                        cedulaUsuario,
                        tipoPago,
                        numeroCuotas);

                // Crear factura en el servidor
                return facturaService.crearFactura(solicitud);
            }

            @Override
            protected void done() {
                try {
                    Map<String, Object> response = get();

                    // Vaciar el carrito después de la compra exitosa
                    carritoService.vaciarCarrito();
                    cargarCarrito(); // Actualizar vista

                    // Mostrar modal de confirmación
                    mostrarMensaje("Factura creada exitosamente", "success");

                    // Navegar a facturas o productos
                    if (parentFrame != null) {
                        parentFrame.showPanel(new MyInvoices(parentFrame));
                    }

                } catch (Exception e) {
                    String mensajeError = extraerMensajeError(e);
                    mostrarMensaje(mensajeError, "error");
                    e.printStackTrace();
                }
            }
        };
        worker.execute();
    }

    private void btn_confirmBuyActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_btn_confirmBuyActionPerformed
        confirmarCompra();
    }// GEN-LAST:event_btn_confirmBuyActionPerformed

    private void btn_BackProductsActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_btn_BackProductsActionPerformed
        if (parentFrame != null) {
            parentFrame.showPanel(new Products(parentFrame));
        } else {
            System.err.println("Error: Products no está asociado a un Home.");
        }
    }// GEN-LAST:event_btn_BackProductsActionPerformed

    private void combobox1ActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_combobox1ActionPerformed
        // TODO add your handling code here:
    }// GEN-LAST:event_combobox1ActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private ec.edu.monster.vista.ModernPanel PanelCart;
    private ec.edu.monster.vista.ModernPanel PanelCart1;
    private ec.edu.monster.vista.ModernTable TableProducts;
    private ec.edu.monster.vista.ModernButton btn_BackProducts;
    private ec.edu.monster.vista.ModernButton btn_confirmBuy;
    private ec.edu.monster.vista.Combobox combobox1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JLabel lblCantidadProductos;
    private javax.swing.JLabel lblCantidadProductos1;
    private javax.swing.JLabel lblNameProduct;
    private javax.swing.JLabel lblNameProduct1;
    private javax.swing.JLabel lblNameProduct2;
    private javax.swing.JLabel lblNameProduct3;
    private javax.swing.JLabel lblTitle;
    private javax.swing.JLabel lblTitle1;
    private javax.swing.JLabel lblTotal;
    // End of variables declaration//GEN-END:variables
}
