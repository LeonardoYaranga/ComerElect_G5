
package ec.edu.monster.vista;

import javax.swing.JDialog;

/**
 *
 * @author Dome
 */
public class ModalConfirmBuy extends javax.swing.JDialog {

    private String seleccion = "";
    private HomeCliente homeCliente;
    
    public ModalConfirmBuy(java.awt.Frame parent, boolean modal, HomeCliente homeCliente) {
        super(parent, modal); // <-- ¡La llamada clave!
        this.homeCliente = homeCliente;
        initComponents();
        
        // 5. Mueve todo tu código del constructor anterior aquí
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE); // Usa JDialog.DISPOSE...
        // pack(); // pack() ya está al final de initComponents()
        setLocationRelativeTo(parent); // Centrar relativo al PADRE, no a null

    }
    
    public String getSeleccion() {
        return seleccion;
    }

    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        lblTitle = new javax.swing.JLabel();
        btn_products = new ec.edu.monster.vista.ModernButton();
        btn_myInvoices = new ec.edu.monster.vista.ModernButton();
        lblTitle1 = new javax.swing.JLabel();
        lblNumFactura = new javax.swing.JLabel();
        lblTitle2 = new javax.swing.JLabel();
        lblCodeCliente = new javax.swing.JLabel();
        lblTotal = new javax.swing.JLabel();
        lblTitle3 = new javax.swing.JLabel();
        lblTipoPago = new javax.swing.JLabel();
        lblTitle4 = new javax.swing.JLabel();
        lblNumCuotas = new javax.swing.JLabel();
        lblTitle5 = new javax.swing.JLabel();
        lblTitle6 = new javax.swing.JLabel();
        lblFecha = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setResizable(false);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        lblTitle.setFont(new java.awt.Font("Roboto", 1, 16)); // NOI18N
        lblTitle.setForeground(new java.awt.Color(51, 51, 51));
        lblTitle.setText("Número de Factura:");

        btn_products.setBackgroundColor(new java.awt.Color(153, 153, 153));
        btn_products.setBorder(null);
        btn_products.setBorderColor(new java.awt.Color(153, 153, 153));
        btn_products.setBorderRadius(0);
        btn_products.setBorderThickness(0);
        btn_products.setHoverColor(new java.awt.Color(102, 102, 102));
        btn_products.setText("Volver a Productos");
        btn_products.setFont(new java.awt.Font("Roboto", 1, 16)); // NOI18N
        btn_products.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_productsActionPerformed(evt);
            }
        });

        btn_myInvoices.setBackgroundColor(new java.awt.Color(37, 99, 234));
        btn_myInvoices.setBorder(null);
        btn_myInvoices.setBorderColor(new java.awt.Color(37, 99, 234));
        btn_myInvoices.setBorderRadius(0);
        btn_myInvoices.setBorderThickness(0);
        btn_myInvoices.setHoverColor(new java.awt.Color(17, 45, 107));
        btn_myInvoices.setPressedColor(new java.awt.Color(17, 45, 107));
        btn_myInvoices.setText("Ver mis Facturas");
        btn_myInvoices.setFont(new java.awt.Font("Roboto", 1, 16)); // NOI18N
        btn_myInvoices.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_myInvoicesActionPerformed(evt);
            }
        });

        lblTitle1.setFont(new java.awt.Font("Britannic Bold", 0, 24)); // NOI18N
        lblTitle1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Carrito Verde.png"))); // NOI18N
        lblTitle1.setText("¡Compra Confirmada!");

        lblNumFactura.setFont(new java.awt.Font("Roboto", 0, 16)); // NOI18N
        lblNumFactura.setForeground(new java.awt.Color(102, 102, 102));
        lblNumFactura.setText("FAC-2025-0013");

        lblTitle2.setFont(new java.awt.Font("Roboto", 1, 16)); // NOI18N
        lblTitle2.setForeground(new java.awt.Color(51, 51, 51));
        lblTitle2.setText("Cliente:");

        lblCodeCliente.setFont(new java.awt.Font("Roboto", 0, 16)); // NOI18N
        lblCodeCliente.setForeground(new java.awt.Color(102, 102, 102));
        lblCodeCliente.setText("0102030405");

        lblTotal.setFont(new java.awt.Font("Roboto", 0, 16)); // NOI18N
        lblTotal.setForeground(new java.awt.Color(102, 102, 102));
        lblTotal.setText("$ 97.00");

        lblTitle3.setFont(new java.awt.Font("Roboto", 1, 16)); // NOI18N
        lblTitle3.setForeground(new java.awt.Color(51, 51, 51));
        lblTitle3.setText("Total:");

        lblTipoPago.setFont(new java.awt.Font("Roboto", 0, 16)); // NOI18N
        lblTipoPago.setForeground(new java.awt.Color(102, 102, 102));
        lblTipoPago.setText("Crédito");

        lblTitle4.setFont(new java.awt.Font("Roboto", 1, 16)); // NOI18N
        lblTitle4.setForeground(new java.awt.Color(51, 51, 51));
        lblTitle4.setText("Tipo de Pago:");

        lblNumCuotas.setFont(new java.awt.Font("Roboto", 0, 16)); // NOI18N
        lblNumCuotas.setForeground(new java.awt.Color(102, 102, 102));
        lblNumCuotas.setText("6");

        lblTitle5.setFont(new java.awt.Font("Roboto", 1, 16)); // NOI18N
        lblTitle5.setForeground(new java.awt.Color(51, 51, 51));
        lblTitle5.setText("Cuotas:");

        lblTitle6.setFont(new java.awt.Font("Roboto", 1, 16)); // NOI18N
        lblTitle6.setForeground(new java.awt.Color(51, 51, 51));
        lblTitle6.setText("Fecha:");

        lblFecha.setFont(new java.awt.Font("Roboto", 0, 16)); // NOI18N
        lblFecha.setForeground(new java.awt.Color(102, 102, 102));
        lblFecha.setText("13/12/2025");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addGroup(jPanel1Layout.createSequentialGroup()
                            .addComponent(lblTitle)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(lblNumFactura))
                        .addComponent(btn_products, javax.swing.GroupLayout.DEFAULT_SIZE, 446, Short.MAX_VALUE)
                        .addComponent(btn_myInvoices, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel1Layout.createSequentialGroup()
                            .addComponent(lblTitle2)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(lblCodeCliente))
                        .addGroup(jPanel1Layout.createSequentialGroup()
                            .addComponent(lblTitle3)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(lblTotal))
                        .addGroup(jPanel1Layout.createSequentialGroup()
                            .addComponent(lblTitle4)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(lblTipoPago))
                        .addGroup(jPanel1Layout.createSequentialGroup()
                            .addComponent(lblTitle5)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(lblNumCuotas))
                        .addGroup(jPanel1Layout.createSequentialGroup()
                            .addComponent(lblTitle6)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(lblFecha)))
                    .addComponent(lblTitle1, javax.swing.GroupLayout.PREFERRED_SIZE, 332, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(30, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addComponent(lblTitle1, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblTitle)
                    .addComponent(lblNumFactura))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblTitle2)
                    .addComponent(lblCodeCliente))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblTitle3)
                    .addComponent(lblTotal))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblTitle4)
                    .addComponent(lblTipoPago))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblTitle5)
                    .addComponent(lblNumCuotas))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblTitle6)
                    .addComponent(lblFecha))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 24, Short.MAX_VALUE)
                .addComponent(btn_myInvoices, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btn_products, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(19, 19, 19))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btn_myInvoicesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_myInvoicesActionPerformed

        // Marcamos que el usuario presionó "OK"
        this.seleccion = "VER_FACTURAS";

        // Cerramos este diálogo
        this.dispose();
    }//GEN-LAST:event_btn_myInvoicesActionPerformed

    private void btn_productsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_productsActionPerformed
        this.seleccion = "VER_PRODUCTOS";
    
        // Cerramos este diálogo
        this.dispose();
    }//GEN-LAST:event_btn_productsActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(ModalConfirmBuy.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ModalConfirmBuy.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ModalConfirmBuy.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ModalConfirmBuy.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                ModalConfirmBuy dialog = new ModalConfirmBuy(new javax.swing.JFrame(), true, null);
                dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                    @Override
                    public void windowClosing(java.awt.event.WindowEvent e) {
                        System.exit(0);
                    }
                });
                dialog.setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private ec.edu.monster.vista.ModernButton btn_myInvoices;
    private ec.edu.monster.vista.ModernButton btn_products;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JLabel lblCodeCliente;
    private javax.swing.JLabel lblFecha;
    private javax.swing.JLabel lblNumCuotas;
    private javax.swing.JLabel lblNumFactura;
    private javax.swing.JLabel lblTipoPago;
    private javax.swing.JLabel lblTitle;
    private javax.swing.JLabel lblTitle1;
    private javax.swing.JLabel lblTitle2;
    private javax.swing.JLabel lblTitle3;
    private javax.swing.JLabel lblTitle4;
    private javax.swing.JLabel lblTitle5;
    private javax.swing.JLabel lblTitle6;
    private javax.swing.JLabel lblTotal;
    // End of variables declaration//GEN-END:variables
    
    

}
