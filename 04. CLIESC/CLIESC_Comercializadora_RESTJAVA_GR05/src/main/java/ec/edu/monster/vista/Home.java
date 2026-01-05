package ec.edu.monster.vista;

import java.awt.BorderLayout;
import java.awt.Color;

/**
 *
 * @author Dome
 */
public class Home extends javax.swing.JFrame {

    int xMouse, yMouse;
    public Home() {
        initComponents();
        
        Dashboard c = new Dashboard(this); 
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
        btn_createinvoice = new ec.edu.monster.vista.ModernButton();
        btn_searchinvoice = new ec.edu.monster.vista.ModernButton();
        btn_appliances = new ec.edu.monster.vista.ModernButton();
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

        btn_createinvoice.setBackgroundColor(new java.awt.Color(151, 90, 182));
        btn_createinvoice.setBorder(null);
        btn_createinvoice.setBorderColor(new java.awt.Color(151, 90, 182));
        btn_createinvoice.setBorderRadius(0);
        btn_createinvoice.setBorderThickness(0);
        btn_createinvoice.setHoverColor(new java.awt.Color(116, 68, 142));
        btn_createinvoice.setIcon(new javax.swing.ImageIcon(getClass().getResource("/CREARBLANCO.png"))); // NOI18N
        btn_createinvoice.setPressedColor(new java.awt.Color(116, 68, 142));
        btn_createinvoice.setText("Crear Factura");
        btn_createinvoice.setFont(new java.awt.Font("Roboto", 1, 16)); // NOI18N
        btn_createinvoice.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_createinvoiceActionPerformed(evt);
            }
        });

        btn_searchinvoice.setBackgroundColor(new java.awt.Color(151, 90, 182));
        btn_searchinvoice.setBorder(null);
        btn_searchinvoice.setBorderColor(new java.awt.Color(151, 90, 182));
        btn_searchinvoice.setBorderRadius(0);
        btn_searchinvoice.setBorderThickness(0);
        btn_searchinvoice.setHoverColor(new java.awt.Color(116, 68, 142));
        btn_searchinvoice.setIcon(new javax.swing.ImageIcon(getClass().getResource("/FACTURA.png"))); // NOI18N
        btn_searchinvoice.setPressedColor(new java.awt.Color(116, 68, 142));
        btn_searchinvoice.setText("Consultar Factura");
        btn_searchinvoice.setFont(new java.awt.Font("Roboto", 1, 16)); // NOI18N
        btn_searchinvoice.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btn_searchinvoice.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_searchinvoiceActionPerformed(evt);
            }
        });

        btn_appliances.setBackgroundColor(new java.awt.Color(151, 90, 182));
        btn_appliances.setBorder(null);
        btn_appliances.setBorderColor(new java.awt.Color(151, 90, 182));
        btn_appliances.setBorderRadius(0);
        btn_appliances.setBorderThickness(0);
        btn_appliances.setHoverColor(new java.awt.Color(116, 68, 142));
        btn_appliances.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ELECTRODOMESTICOS.png"))); // NOI18N
        btn_appliances.setPressedColor(new java.awt.Color(116, 68, 142));
        btn_appliances.setText("Electrodomésticos");
        btn_appliances.setFont(new java.awt.Font("Roboto", 1, 16)); // NOI18N
        btn_appliances.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btn_appliances.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_appliancesActionPerformed(evt);
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
            .addComponent(btn_createinvoice, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, MenuLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(lblLogoEu)
                .addContainerGap())
            .addComponent(btn_searchinvoice, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(btn_appliances, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
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
                .addComponent(btn_createinvoice, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btn_searchinvoice, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btn_appliances, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
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
        showPanel(new Dashboard(this));
    }//GEN-LAST:event_btn_dashboardActionPerformed

    private void btn_createinvoiceActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_createinvoiceActionPerformed
        showPanel(new CreateInvoice(this));
    }//GEN-LAST:event_btn_createinvoiceActionPerformed

    private void btn_exitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_exitActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btn_exitActionPerformed

    private void btn_searchinvoiceActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_searchinvoiceActionPerformed
        showPanel(new SearchInvoice());
    }//GEN-LAST:event_btn_searchinvoiceActionPerformed

    private void btn_appliancesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_appliancesActionPerformed
        showPanel(new Appliance());
    }//GEN-LAST:event_btn_appliancesActionPerformed
    
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
                new Home().setVisible(true);
            }
        });
    }
    
    

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel Background;
    private javax.swing.JPanel Contenedor;
    private javax.swing.JPanel Menu;
    private ec.edu.monster.vista.ModernButton btn_appliances;
    private ec.edu.monster.vista.ModernButton btn_createinvoice;
    private ec.edu.monster.vista.ModernButton btn_dashboard;
    private ec.edu.monster.vista.ModernButton btn_exit;
    private ec.edu.monster.vista.ModernButton btn_exitsession;
    private ec.edu.monster.vista.ModernButton btn_searchinvoice;
    private javax.swing.JPanel header;
    private javax.swing.JLabel lblLogoEu;
    // End of variables declaration//GEN-END:variables
}
