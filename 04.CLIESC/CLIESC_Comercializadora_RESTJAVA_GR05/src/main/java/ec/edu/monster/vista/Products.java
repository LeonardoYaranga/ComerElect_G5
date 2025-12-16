package ec.edu.monster.vista;

/**
 *
 * @author Dome
 */
public class Products extends javax.swing.JPanel {
    
    private HomeCliente parentFrame;
    

    public Products(HomeCliente parent) {
        initComponents();

    }

    // </editor-fold>

@SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
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
                .addGroup(PanelNewProductLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(PanelNewProductLayout.createSequentialGroup()
                        .addComponent(lblPriceProduct, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(lblCodeProduct)
                        .addGap(19, 19, 19))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, PanelNewProductLayout.createSequentialGroup()
                        .addGroup(PanelNewProductLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(btn_dashboard, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(lblImageProduct, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, PanelNewProductLayout.createSequentialGroup()
                                .addGroup(PanelNewProductLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(lblNameProduct, javax.swing.GroupLayout.PREFERRED_SIZE, 219, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(lblDescriptionProduct, javax.swing.GroupLayout.PREFERRED_SIZE, 272, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(0, 34, Short.MAX_VALUE)))
                        .addContainerGap())))
        );
        PanelNewProductLayout.setVerticalGroup(
            PanelNewProductLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PanelNewProductLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblImageProduct, javax.swing.GroupLayout.PREFERRED_SIZE, 116, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblNameProduct, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblDescriptionProduct)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(PanelNewProductLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(lblCodeProduct)
                    .addComponent(lblPriceProduct, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btn_dashboard, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        javax.swing.GroupLayout pnlProductsLayout = new javax.swing.GroupLayout(pnlProducts);
        pnlProducts.setLayout(pnlProductsLayout);
        pnlProductsLayout.setHorizontalGroup(
            pnlProductsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlProductsLayout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addComponent(PanelNewProduct, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(470, Short.MAX_VALUE))
        );
        pnlProductsLayout.setVerticalGroup(
            pnlProductsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlProductsLayout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addComponent(PanelNewProduct, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(340, Short.MAX_VALUE))
        );

        add(pnlProducts, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 70, -1, -1));

        getAccessibleContext().setAccessibleName("");
        getAccessibleContext().setAccessibleDescription("");
    }// </editor-fold>//GEN-END:initComponents

    private void btn_dashboardActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_dashboardActionPerformed
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

            // 6. Verificamos si el usuario le dio a "Crear" (revisarás esto en el Modal.java)
            if (resultado.equals("OK")) {
                // El usuario le dio a "Crear", obtenemos los datos
                String cantidad = product.getCantidad();

                System.out.println("Agregado: " + cantidad);

            } else {
                // El usuario le dio a "Cancelar"
                System.out.println("Operación cancelada.");
            }
        }
    }//GEN-LAST:event_btn_dashboardActionPerformed
    
    
    
    

    


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
