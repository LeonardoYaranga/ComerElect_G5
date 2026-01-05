package ec.edu.monster.vista;

import ec.edu.monster.services.ElectrodomesticoService;
import ec.edu.monster.models.Electrodomestico;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import javax.swing.table.DefaultTableModel;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.io.File;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import javax.swing.ImageIcon;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.JLabel;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.security.cert.X509Certificate;

/**
 *
 * @author Dome
 */
public class Appliance extends javax.swing.JPanel {

    private ElectrodomesticoService electrodomesticoService;
    private Map<String, ImageIcon> imageCache = new ConcurrentHashMap<>();
    private ImageIcon defaultIcon;

    // Renderer personalizado para mostrar imágenes en la tabla
    private class ImageRenderer extends DefaultTableCellRenderer {
        @Override
        public java.awt.Component getTableCellRendererComponent(javax.swing.JTable table, Object value,
                boolean isSelected, boolean hasFocus, int row, int column) {
            JLabel label = (JLabel) super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row,
                    column);

            if (value instanceof ImageIcon) {
                label.setIcon((ImageIcon) value);
                label.setText(""); // No mostrar texto si hay imagen
                label.setHorizontalAlignment(JLabel.CENTER);
            } else {
                label.setIcon(null);
                label.setText(value != null ? value.toString() : "");
            }

            return label;
        }
    }

    public Appliance() {
        RestTemplate restTemplate = new RestTemplate();
        configureRestTemplate(restTemplate);
        this.electrodomesticoService = new ElectrodomesticoService(restTemplate, "http://192.168.137.1:8081/api");
        initComponents();
        initializeImageCache();
        cargarDatos();
    }

    private void configureRestTemplate(RestTemplate restTemplate) {
        List<HttpMessageConverter<?>> converters = restTemplate.getMessageConverters();
        for (HttpMessageConverter<?> converter : converters) {
            if (converter instanceof StringHttpMessageConverter) {
                ((StringHttpMessageConverter) converter).setDefaultCharset(StandardCharsets.UTF_8);
            }
        }
    }

    private void initializeImageCache() {
        // Configurar SSL para permitir conexiones HTTPS sin verificación de
        // certificados
        try {
            TrustManager[] trustAllCerts = new TrustManager[] {
                    new X509TrustManager() {
                        public X509Certificate[] getAcceptedIssuers() {
                            return null;
                        }

                        public void checkClientTrusted(X509Certificate[] certs, String authType) {
                        }

                        public void checkServerTrusted(X509Certificate[] certs, String authType) {
                        }
                    }
            };

            SSLContext sc = SSLContext.getInstance("SSL");
            sc.init(null, trustAllCerts, new java.security.SecureRandom());
            HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());

            // Crear un ícono por defecto para productos sin imagen
            BufferedImage defaultImage = new BufferedImage(60, 60, BufferedImage.TYPE_INT_RGB);
            java.awt.Graphics2D g2d = defaultImage.createGraphics();
            g2d.setColor(java.awt.Color.LIGHT_GRAY);
            g2d.fillRect(0, 0, 60, 60);
            g2d.setColor(java.awt.Color.GRAY);
            g2d.drawRect(0, 0, 59, 59);
            g2d.setColor(java.awt.Color.DARK_GRAY);
            g2d.drawString("Sin", 20, 25);
            g2d.drawString("Imagen", 10, 40);
            g2d.dispose();

            defaultIcon = new ImageIcon(defaultImage);
        } catch (Exception e) {
            System.err.println("Error configurando SSL: " + e.getMessage());
            // Si hay error, usar un ícono simple
            defaultIcon = new ImageIcon();
        }
    }

    private ImageIcon loadImageFromUrl(String imageUrl) {
        if (imageUrl == null || imageUrl.trim().isEmpty()) {
            return defaultIcon;
        }

        // Verificar si ya está en cache
        if (imageCache.containsKey(imageUrl)) {
            return imageCache.get(imageUrl);
        }

        // Cargar imagen de forma síncrona con mejor manejo de errores
        try {
            System.out.println("Intentando cargar imagen desde: " + imageUrl);

            // Decodificar la URL por si tiene caracteres especiales
            String decodedUrl = java.net.URLDecoder.decode(imageUrl, StandardCharsets.UTF_8.name());
            System.out.println("URL decodificada: " + decodedUrl);

            URL url = new URL(decodedUrl);
            BufferedImage originalImage = javax.imageio.ImageIO.read(url);

            if (originalImage != null) {
                System.out.println("Imagen cargada exitosamente desde: " + decodedUrl);
                // Redimensionar imagen para que quepa en la celda (60x60 píxeles)
                Image scaledImage = originalImage.getScaledInstance(60, 60, Image.SCALE_SMOOTH);
                ImageIcon icon = new ImageIcon(scaledImage);
                imageCache.put(imageUrl, icon);
                return icon;
            } else {
                System.err.println("ImageIO.read() retornó null para: " + decodedUrl);
            }

        } catch (IOException e) {
            System.err.println("Error cargando imagen desde: " + imageUrl + " - " + e.getMessage());
            e.printStackTrace();
        } catch (Exception e) {
            System.err.println("Error inesperado cargando imagen desde: " + imageUrl + " - " + e.getMessage());
            e.printStackTrace();
        }

        // Si hay error, guardar el defaultIcon en cache para evitar reintentos
        imageCache.put(imageUrl, defaultIcon);
        return defaultIcon;
    }

    private void cargarDatos() {
        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("Imagen");
        model.addColumn("Código");
        model.addColumn("Nombre");
        model.addColumn("Precio");

        javax.swing.JTable table = (javax.swing.JTable) TableAppliance.getViewport().getView();
        table.setModel(model);

        // Configurar el renderer para la columna de imagen
        table.getColumnModel().getColumn(0).setCellRenderer(new ImageRenderer());
        // Ajustar el ancho de la columna de imagen
        table.getColumnModel().getColumn(0).setPreferredWidth(70);
        table.getColumnModel().getColumn(0).setMaxWidth(70);
        table.getColumnModel().getColumn(0).setMinWidth(70);

        try {
            List<Electrodomestico> electrodomesticos = electrodomesticoService.listarElectrodomesticos();
            if (electrodomesticos != null) {
                for (Electrodomestico e : electrodomesticos) {
                    String precio = String.format("$%,.2f", e.getPrecio());
                    // Cargar imagen desde URL
                    ImageIcon imageIcon = loadImageFromUrl(e.getImageUrl());
                    model.addRow(new Object[] { imageIcon, e.getCodigo(), e.getNombre(), precio });
                }
            }
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
            JOptionPane.showMessageDialog(SwingUtilities.getWindowAncestor(this),
                    "Error al cargar electrodomésticos: " + errorMsg, "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Generated
    // Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        lblTitle = new javax.swing.JLabel();
        btn_dashboard = new ec.edu.monster.vista.ModernButton();
        TableAppliance = new ec.edu.monster.vista.ModernTable();

        setBackground(new java.awt.Color(255, 255, 255));
        setMinimumSize(new java.awt.Dimension(820, 590));
        setPreferredSize(new java.awt.Dimension(820, 590));
        setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ELECTRODOMESTICOVERDE.png"))); // NOI18N

        lblTitle.setFont(new java.awt.Font("Britannic Bold", 0, 28)); // NOI18N
        lblTitle.setText("Catálogo de Electrodomésticos");

        btn_dashboard.setBackgroundColor(new java.awt.Color(13, 110, 253));
        btn_dashboard.setBorder(null);
        btn_dashboard.setBorderColor(new java.awt.Color(13, 110, 253));
        btn_dashboard.setBorderRadius(5);
        btn_dashboard.setBorderThickness(0);
        btn_dashboard.setHoverColor(new java.awt.Color(18, 63, 129));
        btn_dashboard.setPressedColor(new java.awt.Color(18, 63, 129));
        btn_dashboard.setText("+  Agregar Nuevo Electrodoméstico");
        btn_dashboard.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        btn_dashboard.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_dashboardActionPerformed(evt);
            }
        });

        TableAppliance.setBackground(new java.awt.Color(255, 255, 255));
        TableAppliance.setContainerBackground(new java.awt.Color(255, 255, 255));
        TableAppliance.setEvenRowColor(new java.awt.Color(239, 239, 239));
        TableAppliance.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        TableAppliance.setHeaderBackground(new java.awt.Color(0, 0, 0));
        TableAppliance.setHoverRowColor(new java.awt.Color(255, 240, 250));

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
                jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                .addGap(14, 14, 14)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                        .addComponent(TableAppliance, javax.swing.GroupLayout.DEFAULT_SIZE,
                                                javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addGroup(jPanel1Layout.createSequentialGroup()
                                                .addComponent(jLabel1)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(lblTitle)
                                                .addGap(101, 101, 101)
                                                .addComponent(btn_dashboard, javax.swing.GroupLayout.PREFERRED_SIZE,
                                                        257, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED,
                                                        javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                                .addGap(27, 27, 27)));
        jPanel1Layout.setVerticalGroup(
                jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel1Layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(jPanel1Layout
                                        .createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, 45,
                                                Short.MAX_VALUE)
                                        .addGroup(jPanel1Layout
                                                .createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                .addComponent(lblTitle, javax.swing.GroupLayout.PREFERRED_SIZE, 40,
                                                        javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addComponent(btn_dashboard, javax.swing.GroupLayout.PREFERRED_SIZE,
                                                        javax.swing.GroupLayout.DEFAULT_SIZE,
                                                        javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGap(18, 18, 18)
                                .addComponent(TableAppliance, javax.swing.GroupLayout.PREFERRED_SIZE, 568,
                                        javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)));

        add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 820, 690));

        getAccessibleContext().setAccessibleName("");
        getAccessibleContext().setAccessibleDescription("");
    }// </editor-fold>//GEN-END:initComponents

    private void btn_dashboardActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_btn_dashboardActionPerformed
        java.awt.Window parentWindow = javax.swing.SwingUtilities.getWindowAncestor(this);

        // 2. Nos aseguramos de que sea un Frame (lo cual es, es tu Home)
        if (parentWindow instanceof java.awt.Frame) {
            java.awt.Frame parentFrame = (java.awt.Frame) parentWindow;

            // 3. Creamos el modal, pasándole el Frame padre y 'true' para que sea modal
            Modal miDialogo = new Modal(parentFrame, true);

            // 4. Mostramos el modal.
            // El código se detendrá aquí hasta que el usuario cierre el diálogo.
            miDialogo.setVisible(true);

            // 5. (IMPORTANTE) Recogemos los datos DESPUÉS de que el modal se cierra.
            String resultado = miDialogo.getSeleccion(); // Vemos si el usuario presionó "Crear" o "Cancelar"

            // 6. Verificamos si el usuario le dio a "Crear" (revisarás esto en el
            // Modal.java)
            if (resultado.equals("OK")) {
                // El usuario le dio a "Crear", obtenemos los datos
                String codigo = miDialogo.getCodigo();
                String nombre = miDialogo.getNombre();
                String precioStr = miDialogo.getPrecio();
                String descripcion = miDialogo.getDescripcion();
                File imagenFile = miDialogo.getImagenSeleccionada();

                try {
                    Electrodomestico creado;

                    // Si hay imagen, usar el método con multipart/form-data
                    if (imagenFile != null) {
                        creado = electrodomesticoService.crearElectrodomesticoConImagen(
                                nombre, precioStr, descripcion, imagenFile);
                    } else {
                        // Sin imagen, usar el método tradicional con JSON
                        BigDecimal precio = new BigDecimal(precioStr);
                        Electrodomestico nuevo = new Electrodomestico();
                        nuevo.setCodigo(codigo);
                        nuevo.setNombre(nombre);
                        nuevo.setPrecio(precio);
                        nuevo.setDescripcion(descripcion);
                        creado = electrodomesticoService.crearElectrodomestico(nuevo);
                    }

                    if (creado != null) {
                        JOptionPane.showMessageDialog(SwingUtilities.getWindowAncestor(this),
                                "Electrodoméstico creado exitosamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                        cargarDatos(); // Vuelve a cargar los datos
                    } else {
                        JOptionPane.showMessageDialog(SwingUtilities.getWindowAncestor(this),
                                "Error al crear electrodoméstico.", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(SwingUtilities.getWindowAncestor(this), "Precio inválido.", "Error",
                            JOptionPane.ERROR_MESSAGE);
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
                    JOptionPane.showMessageDialog(SwingUtilities.getWindowAncestor(this), "Error: " + errorMsg, "Error",
                            JOptionPane.ERROR_MESSAGE);
                }
            } else {
                // El usuario le dio a "Cancelar"
                System.out.println("Operación cancelada.");
            }
        }
    }// GEN-LAST:event_btn_dashboardActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private ec.edu.monster.vista.ModernTable TableAppliance;
    private ec.edu.monster.vista.ModernButton btn_dashboard;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JLabel lblTitle;
    // End of variables declaration//GEN-END:variables
}
