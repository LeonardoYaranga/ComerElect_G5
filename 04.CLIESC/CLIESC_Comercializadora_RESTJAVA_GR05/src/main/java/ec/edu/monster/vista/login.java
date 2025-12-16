package ec.edu.monster.vista;

import java.awt.Color;
import javax.swing.JOptionPane;

/**
 *
 * @author Dome
 */
public class login extends javax.swing.JFrame {

    int xMouse, yMouse;
    public login() {
        initComponents();
        this.setLocationRelativeTo(null);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        Background = new javax.swing.JPanel();
        lblSullivan = new javax.swing.JLabel();
        lblTarea = new javax.swing.JLabel();
        header = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        lblTitle = new javax.swing.JLabel();
        modernButton2 = new ec.edu.monster.vista.ModernButton();
        lblSullivan1 = new javax.swing.JLabel();
        lblTarea1 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        lblUser = new javax.swing.JLabel();
        tbx_user = new javax.swing.JTextField();
        separatorUser = new javax.swing.JSeparator();
        separatorPass = new javax.swing.JSeparator();
        jpfpassword = new javax.swing.JPasswordField();
        lblPassword = new javax.swing.JLabel();
        logoeubank = new javax.swing.JLabel();
        btn_Ingresar = new ec.edu.monster.vista.ModernButton();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        setLocationByPlatform(true);
        setUndecorated(true);
        setResizable(false);
        setSize(new java.awt.Dimension(800, 500));

        Background.setBackground(new java.awt.Color(204, 153, 255));
        Background.setToolTipText("");
        Background.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        Background.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lblSullivan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/java.png"))); // NOI18N
        Background.add(lblSullivan, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 50, 50, 50));

        lblTarea.setBackground(new java.awt.Color(255, 255, 255));
        lblTarea.setFont(new java.awt.Font("Britannic Bold", 0, 16)); // NOI18N
        lblTarea.setForeground(new java.awt.Color(255, 255, 255));
        lblTarea.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblTarea.setText("<html>Lindsay Barrionuevo<br>Joel Rivera<br>Leonardo Yaranga</html>");
        lblTarea.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        Background.add(lblTarea, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 110, 170, 60));

        header.setBackground(new java.awt.Color(255, 255, 255));
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

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/favicon.png"))); // NOI18N

        lblTitle.setFont(new java.awt.Font("Roboto Medium", 0, 16)); // NOI18N
        lblTitle.setForeground(new java.awt.Color(151, 90, 182));
        lblTitle.setText("INICIAR SESIÓN");

        modernButton2.setBackgroundColor(new java.awt.Color(151, 90, 182));
        modernButton2.setBorder(null);
        modernButton2.setBorderColor(new java.awt.Color(151, 90, 182));
        modernButton2.setBorderRadius(0);
        modernButton2.setBorderThickness(0);
        modernButton2.setHoverColor(new java.awt.Color(255, 51, 153));
        modernButton2.setPressedColor(new java.awt.Color(204, 0, 153));
        modernButton2.setText("X");
        modernButton2.setFont(new java.awt.Font("Roboto", 1, 24)); // NOI18N
        modernButton2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                modernButton2MouseClicked(evt);
            }
        });

        javax.swing.GroupLayout headerLayout = new javax.swing.GroupLayout(header);
        header.setLayout(headerLayout);
        headerLayout.setHorizontalGroup(
            headerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, headerLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblTitle)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 635, Short.MAX_VALUE)
                .addComponent(modernButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        headerLayout.setVerticalGroup(
            headerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(headerLayout.createSequentialGroup()
                .addGroup(headerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(modernButton2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1))
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(headerLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblTitle)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        Background.add(header, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 840, 40));

        lblSullivan1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/sulliLogo.png"))); // NOI18N
        Background.add(lblSullivan1, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 180, 240, 260));

        lblTarea1.setBackground(new java.awt.Color(255, 255, 255));
        lblTarea1.setFont(new java.awt.Font("Britannic Bold", 1, 22)); // NOI18N
        lblTarea1.setForeground(new java.awt.Color(255, 255, 255));
        lblTarea1.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lblTarea1.setText("<html>+ REST</html>");
        lblTarea1.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        Background.add(lblTarea1, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 70, 110, 30));

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        lblUser.setFont(new java.awt.Font("Roboto", 0, 24)); // NOI18N
        lblUser.setText("Usuario");

        tbx_user.setFont(new java.awt.Font("Roboto Light", 0, 18)); // NOI18N
        tbx_user.setForeground(new java.awt.Color(102, 102, 102));
        tbx_user.setBorder(null);

        separatorUser.setForeground(new java.awt.Color(51, 51, 51));

        separatorPass.setForeground(new java.awt.Color(51, 51, 51));

        jpfpassword.setFont(new java.awt.Font("Roboto Light", 0, 18)); // NOI18N
        jpfpassword.setForeground(new java.awt.Color(102, 102, 102));
        jpfpassword.setToolTipText("");
        jpfpassword.setBorder(null);

        lblPassword.setFont(new java.awt.Font("Roboto", 0, 24)); // NOI18N
        lblPassword.setText("Contraseña");

        logoeubank.setBackground(new java.awt.Color(151, 90, 182));
        logoeubank.setFont(new java.awt.Font("Roboto", 0, 22)); // NOI18N
        logoeubank.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Morado.png"))); // NOI18N

        btn_Ingresar.setBackgroundColor(new java.awt.Color(151, 90, 182));
        btn_Ingresar.setBorderColor(new java.awt.Color(151, 90, 182));
        btn_Ingresar.setBorderRadius(0);
        btn_Ingresar.setBorderThickness(0);
        btn_Ingresar.setHoverColor(new java.awt.Color(116, 68, 142));
        btn_Ingresar.setPressedColor(new java.awt.Color(102, 0, 153));
        btn_Ingresar.setText("Iniciar Sesión");
        btn_Ingresar.setFont(new java.awt.Font("Roboto", 1, 18)); // NOI18N
        btn_Ingresar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_IngresarActionPerformed(evt);
            }
        });

        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/USER.png"))); // NOI18N
        jLabel2.setText(".");

        jLabel3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/LLAVE.png"))); // NOI18N
        jLabel3.setText(".");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap(27, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(lblUser)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(separatorPass, javax.swing.GroupLayout.PREFERRED_SIZE, 400, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jpfpassword, javax.swing.GroupLayout.PREFERRED_SIZE, 400, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(tbx_user, javax.swing.GroupLayout.PREFERRED_SIZE, 400, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(23, 23, 23))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(lblPassword))
                            .addComponent(separatorUser, javax.swing.GroupLayout.PREFERRED_SIZE, 400, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE))))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(124, 124, 124)
                        .addComponent(btn_Ingresar, javax.swing.GroupLayout.PREFERRED_SIZE, 176, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(118, 118, 118)
                        .addComponent(logoeubank)))
                .addGap(0, 0, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(logoeubank, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblUser)
                    .addComponent(jLabel2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(tbx_user, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(separatorUser, javax.swing.GroupLayout.PREFERRED_SIZE, 12, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(lblPassword))
                .addGap(9, 9, 9)
                .addComponent(jpfpassword, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(separatorPass, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btn_Ingresar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(117, 117, 117))
        );

        Background.add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 60, 450, 370));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(Background, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(Background, javax.swing.GroupLayout.PREFERRED_SIZE, 471, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
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

    private void modernButton2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_modernButton2MouseClicked
        System.exit(0);
    }//GEN-LAST:event_modernButton2MouseClicked

    private void btn_IngresarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_IngresarActionPerformed
        String usuario = tbx_user.getText();
        String password = new String(jpfpassword.getPassword());
        
        if ("MONSTER".equals(usuario) && "MONSTER9".equals(password)) {
            new Home().setVisible(true);
            this.dispose();
        } else if ("JOEL".equals(usuario) && "JOEL9".equals(password)) {
            new HomeCliente().setVisible(true);
            this.dispose();
        } else {
            JOptionPane.showMessageDialog(this, "Credenciales incorrectas. Acceso denegado.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_btn_IngresarActionPerformed

    public static void main(String args[]) {

        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new login().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel Background;
    private ec.edu.monster.vista.ModernButton btn_Ingresar;
    private javax.swing.JPanel header;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPasswordField jpfpassword;
    private javax.swing.JLabel lblPassword;
    private javax.swing.JLabel lblSullivan;
    private javax.swing.JLabel lblSullivan1;
    private javax.swing.JLabel lblTarea;
    private javax.swing.JLabel lblTarea1;
    private javax.swing.JLabel lblTitle;
    private javax.swing.JLabel lblUser;
    private javax.swing.JLabel logoeubank;
    private ec.edu.monster.vista.ModernButton modernButton2;
    private javax.swing.JSeparator separatorPass;
    private javax.swing.JSeparator separatorUser;
    private javax.swing.JTextField tbx_user;
    // End of variables declaration//GEN-END:variables
}
