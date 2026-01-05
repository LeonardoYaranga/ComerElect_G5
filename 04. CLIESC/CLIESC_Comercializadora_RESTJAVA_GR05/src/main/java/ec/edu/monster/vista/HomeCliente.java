package ec.edu.monster.vista;

import java.awt.BorderLayout;
import java.awt.Color;

/**
 *
 * @author Dome
 */
public class HomeCliente extends javax.swing.JFrame {

    int xMouse, yMouse;
    public HomeCliente() {
        initComponents();
        
        DashboardCliente c = new DashboardCliente(this); 
        showPanel(c);
        this.setLocationRelativeTo(null);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        Background = new javax.swing.JPanel();
        header = new javax.swing.JPanel();
        btn_exit = new ec.edu.monster.vista.ModernButton();
        Menu = new javax.swing.JPanel();
        lblLogoEu = new javax.swing.JLabel();
        btn_dashboard = new ec.edu.monster.vista.ModernButton();
        btn_products = new ec.edu.monster.vista.ModernButton();
        btn_mycart = new ec.edu.monster.vista.ModernButton();
        btn_myinvoices = new ec.edu.monster.vista.ModernButton();
        btn_exitsession = new ec.edu.monster.vista.ModernButton();
        Contenedor = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        setLocationByPlatform(true);
        setUndecorated(true);
        setResizable(false);
        setSize(new java.awt.Dimension(850, 640));

        Background.setBackground(new java.awt.Color(255, 255, 255));
        Background.setToolTipText("");
        Background.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        Background.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        header.setBackground(new java.awt.Color(151, 90, 182));
        header.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseDragged(java.awt.event.MouseEvent evt) {
                headerMouseDragged(evt);
            }
        });
        header.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                headerMousePressed(evt);
            }
        });

        btn_exit.setBackgroundColor(new java.awt.Color(151, 90, 182));
        btn_exit.setBorder(null);
        btn_exit.setBorderColor(new java.awt.Color(151, 90, 182));
        btn_exit.setBorderRadius(0);
        btn_exit.setBorderThickness(0);
        btn_exit.setHoverColor(new java.awt.Color(255, 51, 51));
        btn_exit.setPressedColor(new java.awt.Color(153, 0, 0));
        btn_exit.setText("X");
        btn_exit.setFont(new java.awt.Font("Roboto", 1, 20)); // NOI18N
        btn_exit.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btn_exitMouseClicked(evt);
            }
        });
        btn_exit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_exitActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout headerLayout = new javax.swing.GroupLayout(header);
        header.setLayout(headerLayout);
        headerLayout.setHorizontalGroup(
            headerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, headerLayout.createSequentialGroup()
                .addGap(0, 965, Short.MAX_VALUE)
                .addComponent(btn_exit, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        headerLayout.setVerticalGroup(
            headerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(btn_exit, javax.swing.GroupLayout.DEFAULT_SIZE, 30, Short.MAX_VALUE)
        );

        Background.add(header, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1010, 30));

        Menu.setBackground(new java.awt.Color(251, 245, 255));

        lblLogoEu.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Morado.png"))); // NOI18N

        btn_dashboard.setBackgroundColor(new java.awt.Color(151, 90, 182));
        btn_dashboard.setBorder(null);
        btn_dashboard.setBorderColor(new java.awt.Color(151, 90, 182));
        btn_dashboard.setBorderRadius(0);
        btn_dashboard.setBorderThickness(0);
        btn_dashboard.setHoverColor(new java.awt.Color(116, 68, 142));
        btn_dashboard.setIcon(new javax.swing.ImageIcon(getClass().getResource("/CASABLANCA.png"))); // NOI18N
        btn_dashboard.setPressedColor(new java.awt.Color(116, 68, 142));
        btn_dashboard.setText("Dashboard");
        btn_dashboard.setFont(new java.awt.Font("Roboto", 1, 16)); // NOI18N
        btn_dashboard.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_dashboardActionPerformed(evt);
            }
        });

        btn_products.setBackgroundColor(new java.awt.Color(151, 90, 182));
        btn_products.setBorder(null);
        btn_products.setBorderColor(new java.awt.Color(151, 90, 182));
        btn_products.setBorderRadius(0);
        btn_products.setBorderThickness(0);
        btn_products.setHoverColor(new java.awt.Color(116, 68, 142));
        btn_products.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Bolsa Blanca.png"))); // NOI18N
        btn_products.setPressedColor(new java.awt.Color(116, 68, 142));
        btn_products.setText("Productos");
        btn_products.setFont(new java.awt.Font("Roboto", 1, 16)); // NOI18N
        btn_products.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_productsActionPerformed(evt);
            }
        });

        btn_mycart.setBackgroundColor(new java.awt.Color(151, 90, 182));
        btn_mycart.setBorder(null);
        btn_mycart.setBorderColor(new java.awt.Color(151, 90, 182));
        btn_mycart.setBorderRadius(0);
        btn_mycart.setBorderThickness(0);
        btn_mycart.setHoverColor(new java.awt.Color(116, 68, 142));
        btn_mycart.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Carrito Blanco.png"))); // NOI18N
        btn_mycart.setPressedColor(new java.awt.Color(116, 68, 142));
        btn_mycart.setText("Mi Carrito");
        btn_mycart.setFont(new java.awt.Font("Roboto", 1, 16)); // NOI18N
        btn_mycart.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_mycartActionPerformed(evt);
            }
        });

        btn_myinvoices.setBackgroundColor(new java.awt.Color(151, 90, 182));
        btn_myinvoices.setBorder(null);
        btn_myinvoices.setBorderColor(new java.awt.Color(151, 90, 182));
        btn_myinvoices.setBorderRadius(0);
        btn_myinvoices.setBorderThickness(0);
        btn_myinvoices.setHoverColor(new java.awt.Color(116, 68, 142));
        btn_myinvoices.setIcon(new javax.swing.ImageIcon(getClass().getResource("/FACTURA.png"))); // NOI18N
        btn_myinvoices.setPressedColor(new java.awt.Color(116, 68, 142));
        btn_myinvoices.setText("Mis Facturas");
        btn_myinvoices.setFont(new java.awt.Font("Roboto", 1, 16)); // NOI18N
        btn_myinvoices.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_myinvoicesActionPerformed(evt);
            }
        });

        btn_exitsession.setBackgroundColor(new java.awt.Color(151, 90, 182));
        btn_exitsession.setBorder(null);
        btn_exitsession.setBorderColor(new java.awt.Color(151, 90, 182));
        btn_exitsession.setBorderRadius(0);
        btn_exitsession.setBorderThickness(0);
        btn_exitsession.setHoverColor(new java.awt.Color(116, 68, 142));
        btn_exitsession.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ingresar.png"))); // NOI18N
        btn_exitsession.setPressedColor(new java.awt.Color(116, 68, 142));
        btn_exitsession.setText("Cerrar Sesión");
        btn_exitsession.setFont(new java.awt.Font("Roboto", 1, 18)); // NOI18N
        btn_exitsession.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btn_exitsession.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btn_exitsessionMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout MenuLayout = new javax.swing.GroupLayout(Menu);
        Menu.setLayout(MenuLayout);
        MenuLayout.setHorizontalGroup(
            MenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(btn_dashboard, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(btn_products, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, MenuLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(lblLogoEu)
                .addContainerGap())
            .addComponent(btn_mycart, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(btn_myinvoices, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(MenuLayout.createSequentialGroup()
                .addComponent(btn_exitsession, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        MenuLayout.setVerticalGroup(
            MenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(MenuLayout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addComponent(lblLogoEu, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(28, 28, 28)
                .addComponent(btn_dashboard, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btn_products, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btn_mycart, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btn_myinvoices, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 299, Short.MAX_VALUE)
                .addComponent(btn_exitsession, javax.swing.GroupLayout.PREFERRED_SIZE, 67, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(17, 17, 17))
        );

        Background.add(Menu, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 30, 190, 730));

        Contenedor.setBackground(new java.awt.Color(255, 255, 255));

        javax.swing.GroupLayout ContenedorLayout = new javax.swing.GroupLayout(Contenedor);
        Contenedor.setLayout(ContenedorLayout);
        ContenedorLayout.setHorizontalGroup(
            ContenedorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 820, Short.MAX_VALUE)
        );
        ContenedorLayout.setVerticalGroup(
            ContenedorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 730, Short.MAX_VALUE)
        );

        Background.add(Contenedor, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 30, 820, 730));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(Background, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(Background, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void headerMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_headerMousePressed
        xMouse = evt.getX();
        yMouse = evt.getY();
        
    }//GEN-LAST:event_headerMousePressed

    private void headerMouseDragged(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_headerMouseDragged
        int x = evt.getXOnScreen();
        int y = evt.getYOnScreen();
        this.setLocation(x - xMouse, y - yMouse);
    }//GEN-LAST:event_headerMouseDragged

    private void btn_exitMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_exitMouseClicked
        System.exit(0);
    }//GEN-LAST:event_btn_exitMouseClicked

    private void btn_exitsessionMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_exitsessionMouseClicked
        System.exit(0);
    }//GEN-LAST:event_btn_exitsessionMouseClicked

    private void btn_dashboardActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_dashboardActionPerformed
        showPanel(new DashboardCliente(this));
    }//GEN-LAST:event_btn_dashboardActionPerformed

    private void btn_productsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_productsActionPerformed
        showPanel(new Products(this));
    }//GEN-LAST:event_btn_productsActionPerformed

    private void btn_exitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_exitActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btn_exitActionPerformed

    private void btn_mycartActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_mycartActionPerformed
        showPanel(new MyCart(this));
    }//GEN-LAST:event_btn_mycartActionPerformed

    private void btn_myinvoicesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_myinvoicesActionPerformed
        showPanel(new MyInvoices(this));
    }//GEN-LAST:event_btn_myinvoicesActionPerformed
    
    public void showPanel(javax.swing.JPanel panel) {
        // Asegurarse de que el panel tenga el tamaño correcto
        panel.setSize(820, 690);
        panel.setLocation(0, 0);

        // Usamos el 'getter' si elegiste la Opción A, o directamente 'Contenedor' si elegiste la Opción B.
        this.getContenedor().removeAll(); // O Contenedor.removeAll()
        this.getContenedor().add(panel, java.awt.BorderLayout.CENTER); // O Contenedor.add(...)
        this.getContenedor().revalidate(); // O Contenedor.revalidate()
        this.getContenedor().repaint(); // O Contenedor.repaint()
    }
    
    public javax.swing.JPanel getContenedor() {
        return Contenedor;
    }
    
    
    public static void main(String args[]) {

        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new HomeCliente().setVisible(true);
            }
        });
    }
    
    

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel Background;
    private javax.swing.JPanel Contenedor;
    private javax.swing.JPanel Menu;
    private ec.edu.monster.vista.ModernButton btn_dashboard;
    private ec.edu.monster.vista.ModernButton btn_exit;
    private ec.edu.monster.vista.ModernButton btn_exitsession;
    private ec.edu.monster.vista.ModernButton btn_mycart;
    private ec.edu.monster.vista.ModernButton btn_myinvoices;
    private ec.edu.monster.vista.ModernButton btn_products;
    private javax.swing.JPanel header;
    private javax.swing.JLabel lblLogoEu;
    // End of variables declaration//GEN-END:variables
}
