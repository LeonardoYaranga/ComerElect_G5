
package ec.edu.monster.vista;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.Image;
import java.io.File;

/**
 *
 * @author Dome
 */
public class Modal extends javax.swing.JDialog {

    private String seleccion = "";
    private File imagenSeleccionada = null;

    public Modal(java.awt.Frame parent, boolean modal) {
        super(parent, modal); // <-- ¡La llamada clave!
        initComponents();
        initImagenComponents(); // Inicializar componentes de imagen

        // 5. Mueve todo tu código del constructor anterior aquí
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE); // Usa JDialog.DISPOSE...
        // pack(); // pack() ya está al final de initComponents()
        setLocationRelativeTo(parent); // Centrar relativo al PADRE, no a null

    }

    public String getSeleccion() {
        return seleccion;
    }

    public String getCodigo() {
        return txt_codigo.getText();
    }

    public String getNombre() {
        return txt_nombre.getText();
    }

    public String getPrecio() {
        return txt_precio.getText();
    }

    public String getDescripcion() {
        return TADescripcion.getText();
    }

    public File getImagenSeleccionada() {
        return imagenSeleccionada;
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated
    // Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        modernPanel1 = new ec.edu.monster.vista.ModernPanel();
        lblTitle1 = new javax.swing.JLabel();
        lblTitle = new javax.swing.JLabel();
        lblTitle2 = new javax.swing.JLabel();
        lblTitle3 = new javax.swing.JLabel();
        txt_nombre = new javax.swing.JTextField();
        txt_precio = new javax.swing.JTextField();
        btn_cancel = new ec.edu.monster.vista.ModernButton();
        btn_crear = new ec.edu.monster.vista.ModernButton();
        txt_codigo = new javax.swing.JTextField();
        lblTitle4 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        TADescripcion = new javax.swing.JTextArea();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setResizable(false);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        modernPanel1.setBackground(new java.awt.Color(37, 99, 234));
        modernPanel1.setBorder(null);
        modernPanel1.setBorderColor(new java.awt.Color(37, 99, 234));
        modernPanel1.setForeground(new java.awt.Color(255, 255, 255));
        modernPanel1.setPanelBackground(new java.awt.Color(37, 99, 234));

        lblTitle1.setFont(new java.awt.Font("Britannic Bold", 0, 24)); // NOI18N
        lblTitle1.setForeground(new java.awt.Color(255, 255, 255));
        lblTitle1.setText("+  Crear Nuevo Electrodoméstico");

        javax.swing.GroupLayout modernPanel1Layout = new javax.swing.GroupLayout(modernPanel1);
        modernPanel1.setLayout(modernPanel1Layout);
        modernPanel1Layout.setHorizontalGroup(
                modernPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(modernPanel1Layout.createSequentialGroup()
                                .addGap(19, 19, 19)
                                .addComponent(lblTitle1, javax.swing.GroupLayout.PREFERRED_SIZE, 430,
                                        javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addContainerGap(138, Short.MAX_VALUE)));
        modernPanel1Layout.setVerticalGroup(
                modernPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(modernPanel1Layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(lblTitle1)
                                .addContainerGap(11, Short.MAX_VALUE)));

        lblTitle.setFont(new java.awt.Font("Britannic Bold", 0, 16)); // NOI18N
        lblTitle.setText("Código");

        lblTitle2.setFont(new java.awt.Font("Britannic Bold", 0, 16)); // NOI18N
        lblTitle2.setText("Nombre");

        lblTitle3.setFont(new java.awt.Font("Britannic Bold", 0, 16)); // NOI18N
        lblTitle3.setText("Precio");

        txt_nombre.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N

        txt_precio.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N

        btn_cancel.setBackgroundColor(new java.awt.Color(153, 153, 153));
        btn_cancel.setBorder(null);
        btn_cancel.setBorderColor(new java.awt.Color(153, 153, 153));
        btn_cancel.setBorderRadius(0);
        btn_cancel.setBorderThickness(0);
        btn_cancel.setHoverColor(new java.awt.Color(102, 102, 102));
        btn_cancel.setText("Cancelar");
        btn_cancel.setFont(new java.awt.Font("Roboto", 1, 16)); // NOI18N
        btn_cancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_cancelActionPerformed(evt);
            }
        });

        btn_crear.setBackgroundColor(new java.awt.Color(37, 99, 234));
        btn_crear.setBorder(null);
        btn_crear.setBorderColor(new java.awt.Color(37, 99, 234));
        btn_crear.setBorderRadius(0);
        btn_crear.setBorderThickness(0);
        btn_crear.setHoverColor(new java.awt.Color(17, 45, 107));
        btn_crear.setPressedColor(new java.awt.Color(17, 45, 107));
        btn_crear.setText("Crear Electrodoméstico");
        btn_crear.setFont(new java.awt.Font("Roboto", 1, 16)); // NOI18N
        btn_crear.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_crearActionPerformed(evt);
            }
        });

        txt_codigo.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N

        lblTitle4.setFont(new java.awt.Font("Britannic Bold", 0, 16)); // NOI18N
        lblTitle4.setText("Descripción");

        TADescripcion.setColumns(20);
        TADescripcion.setRows(5);
        jScrollPane1.setViewportView(TADescripcion);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
                jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(jPanel1Layout.createSequentialGroup()
                                                .addContainerGap()
                                                .addComponent(modernPanel1, javax.swing.GroupLayout.PREFERRED_SIZE,
                                                        javax.swing.GroupLayout.DEFAULT_SIZE,
                                                        javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGroup(jPanel1Layout.createSequentialGroup()
                                                .addGap(16, 16, 16)
                                                .addGroup(jPanel1Layout
                                                        .createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING,
                                                                false)
                                                        .addComponent(lblTitle2)
                                                        .addComponent(lblTitle)
                                                        .addComponent(lblTitle3)
                                                        .addComponent(lblTitle4)
                                                        .addComponent(txt_codigo)
                                                        .addComponent(txt_nombre)
                                                        .addComponent(txt_precio)
                                                        .addComponent(jScrollPane1,
                                                                javax.swing.GroupLayout.DEFAULT_SIZE, 563,
                                                                Short.MAX_VALUE))))
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(btn_cancel, javax.swing.GroupLayout.PREFERRED_SIZE, 152,
                                        javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(btn_crear, javax.swing.GroupLayout.PREFERRED_SIZE, 201,
                                        javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(28, 28, 28)));
        jPanel1Layout.setVerticalGroup(
                jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel1Layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(modernPanel1, javax.swing.GroupLayout.PREFERRED_SIZE,
                                        javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(lblTitle)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txt_codigo, javax.swing.GroupLayout.PREFERRED_SIZE, 41,
                                        javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(15, 15, 15)
                                .addComponent(lblTitle2)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(txt_nombre, javax.swing.GroupLayout.PREFERRED_SIZE, 41,
                                        javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(lblTitle3)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txt_precio, javax.swing.GroupLayout.PREFERRED_SIZE, 41,
                                        javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(lblTitle4)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE,
                                        javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 23,
                                        Short.MAX_VALUE)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(btn_cancel, javax.swing.GroupLayout.PREFERRED_SIZE,
                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(btn_crear, javax.swing.GroupLayout.PREFERRED_SIZE,
                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(23, 23, 23)));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE,
                                javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE));
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE,
                                        javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE)));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btn_crearActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_btn_crearActionPerformed
        if (txt_codigo.getText().isEmpty() || txt_nombre.getText().isEmpty() || txt_precio.getText().isEmpty()) {
            javax.swing.JOptionPane.showMessageDialog(this, "Por favor, llene todos los campos.", "Error",
                    javax.swing.JOptionPane.ERROR_MESSAGE);
            return; // No cerramos el modal si hay un error
        }

        // Marcamos que el usuario presionó "OK"
        this.seleccion = "OK";

        // Cerramos este diálogo
        this.dispose();
    }// GEN-LAST:event_btn_crearActionPerformed

    private void btn_cancelActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_btn_cancelActionPerformed
        this.seleccion = "CANCELADO";

        // Cerramos este diálogo
        this.dispose();
    }// GEN-LAST:event_btn_cancelActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        // <editor-fold defaultstate="collapsed" desc=" Look and feel setting code
        // (optional) ">
        /*
         * If Nimbus (introduced in Java SE 6) is not available, stay with the default
         * look and feel.
         * For details see
         * http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Modal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Modal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Modal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Modal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        // </editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Modal(null, true).setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextArea TADescripcion;
    private ec.edu.monster.vista.ModernButton btn_cancel;
    private ec.edu.monster.vista.ModernButton btn_crear;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblTitle;
    private javax.swing.JLabel lblTitle1;
    private javax.swing.JLabel lblTitle2;
    private javax.swing.JLabel lblTitle3;
    private javax.swing.JLabel lblTitle4;
    private ec.edu.monster.vista.ModernPanel modernPanel1;
    private javax.swing.JTextField txt_codigo;
    private javax.swing.JTextField txt_nombre;
    private javax.swing.JTextField txt_precio;
    // End of variables declaration//GEN-END:variables

    // Componentes adicionales para imagen (no generados por form editor)
    private javax.swing.JLabel lblImagen;
    private javax.swing.JTextField txt_imagenPath;
    private ec.edu.monster.vista.ModernButton btn_seleccionarImagen;

    private void initImagenComponents() {
        lblImagen = new javax.swing.JLabel();
        txt_imagenPath = new javax.swing.JTextField();
        btn_seleccionarImagen = new ec.edu.monster.vista.ModernButton();

        lblImagen.setFont(new java.awt.Font("Britannic Bold", 0, 16));
        lblImagen.setText("Imagen (Opcional)");

        txt_imagenPath.setFont(new java.awt.Font("Roboto", 0, 14));
        txt_imagenPath.setEditable(false);
        txt_imagenPath.setText("Ninguna imagen seleccionada");

        btn_seleccionarImagen.setBackgroundColor(new java.awt.Color(108, 117, 125));
        btn_seleccionarImagen.setBorderRadius(0);
        btn_seleccionarImagen.setBorderThickness(0);
        btn_seleccionarImagen.setHoverColor(new java.awt.Color(73, 80, 87));
        btn_seleccionarImagen.setText("Seleccionar");
        btn_seleccionarImagen.setFont(new java.awt.Font("Roboto", 1, 14));
        btn_seleccionarImagen.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                seleccionarImagenActionPerformed(evt);
            }
        });

        // Agregar componentes al panel
        javax.swing.GroupLayout layout = (javax.swing.GroupLayout) jPanel1.getLayout();

        // Actualizar constraints horizontales
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(layout.createSequentialGroup()
                                                .addContainerGap()
                                                .addComponent(modernPanel1, javax.swing.GroupLayout.PREFERRED_SIZE,
                                                        javax.swing.GroupLayout.DEFAULT_SIZE,
                                                        javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGroup(layout.createSequentialGroup()
                                                .addGap(16, 16, 16)
                                                .addGroup(layout
                                                        .createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING,
                                                                false)
                                                        .addComponent(lblTitle2)
                                                        .addComponent(lblTitle)
                                                        .addComponent(lblTitle3)
                                                        .addComponent(lblTitle4)
                                                        .addComponent(lblImagen)
                                                        .addComponent(txt_codigo)
                                                        .addComponent(txt_nombre)
                                                        .addComponent(txt_precio)
                                                        .addComponent(jScrollPane1,
                                                                javax.swing.GroupLayout.DEFAULT_SIZE, 563,
                                                                Short.MAX_VALUE)
                                                        .addGroup(layout.createSequentialGroup()
                                                                .addComponent(txt_imagenPath,
                                                                        javax.swing.GroupLayout.PREFERRED_SIZE, 420,
                                                                        javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                .addPreferredGap(
                                                                        javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                .addComponent(btn_seleccionarImagen,
                                                                        javax.swing.GroupLayout.PREFERRED_SIZE, 137,
                                                                        javax.swing.GroupLayout.PREFERRED_SIZE)))))
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(btn_cancel, javax.swing.GroupLayout.PREFERRED_SIZE, 152,
                                        javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(btn_crear, javax.swing.GroupLayout.PREFERRED_SIZE, 201,
                                        javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(28, 28, 28)));

        // Actualizar constraints verticales
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(modernPanel1, javax.swing.GroupLayout.PREFERRED_SIZE,
                                        javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(lblTitle)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txt_codigo, javax.swing.GroupLayout.PREFERRED_SIZE, 41,
                                        javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(15, 15, 15)
                                .addComponent(lblTitle2)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(txt_nombre, javax.swing.GroupLayout.PREFERRED_SIZE, 41,
                                        javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(lblTitle3)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txt_precio, javax.swing.GroupLayout.PREFERRED_SIZE, 41,
                                        javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(lblTitle4)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE,
                                        javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(lblImagen)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(txt_imagenPath, javax.swing.GroupLayout.PREFERRED_SIZE, 41,
                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(btn_seleccionarImagen, javax.swing.GroupLayout.PREFERRED_SIZE, 41,
                                                javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 23,
                                        Short.MAX_VALUE)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(btn_cancel, javax.swing.GroupLayout.PREFERRED_SIZE,
                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(btn_crear, javax.swing.GroupLayout.PREFERRED_SIZE,
                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(23, 23, 23)));
    }

    private void seleccionarImagenActionPerformed(java.awt.event.ActionEvent evt) {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Seleccionar Imagen del Electrodoméstico");
        FileNameExtensionFilter filter = new FileNameExtensionFilter(
                "Archivos de Imagen (*.jpg, *.jpeg, *.png)", "jpg", "jpeg", "png");
        fileChooser.setFileFilter(filter);

        int resultado = fileChooser.showOpenDialog(this);
        if (resultado == JFileChooser.APPROVE_OPTION) {
            imagenSeleccionada = fileChooser.getSelectedFile();
            txt_imagenPath.setText(imagenSeleccionada.getName());
        }
    }
}
