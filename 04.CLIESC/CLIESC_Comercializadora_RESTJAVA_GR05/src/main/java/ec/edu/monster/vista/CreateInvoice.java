package ec.edu.monster.vista;
import ec.edu.monster.vista.Home;
import ec.edu.monster.services.FacturaService;
import ec.edu.monster.services.ElectrodomesticoService;
import ec.edu.monster.models.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import javax.swing.table.DefaultTableModel;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.ButtonGroup;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
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
public class CreateInvoice extends javax.swing.JPanel {
    
    private Home parentFrame;
    private FacturaService facturaService;
    private ElectrodomesticoService electrodomesticoService;
    private List<ProductoCarrito> productosCarrito;
    private ButtonGroup tipoPagoGroup;
    private Map<String, ImageIcon> imageCache = new ConcurrentHashMap<>();
    private ImageIcon defaultIcon;
    
    // Renderer personalizado para mostrar imágenes en la tabla
    private class ImageRenderer extends DefaultTableCellRenderer {
        @Override
        public java.awt.Component getTableCellRendererComponent(javax.swing.JTable table, Object value, 
                boolean isSelected, boolean hasFocus, int row, int column) {
            JLabel label = (JLabel) super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
            
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

    public CreateInvoice(Home parent) {
        RestTemplate restTemplate = new RestTemplate();
        configureRestTemplate(restTemplate);
        this.facturaService = new FacturaService(restTemplate, "http://localhost:8081/api");
        this.electrodomesticoService = new ElectrodomesticoService(restTemplate, "http://localhost:8081/api");
        this.productosCarrito = new ArrayList<>();
        this.tipoPagoGroup = new ButtonGroup();
        initComponents();
        initializeImageCache();
        setupTipoPago();
        setupTable();
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
        // Configurar SSL para permitir conexiones HTTPS sin verificación de certificados
        try {
            TrustManager[] trustAllCerts = new TrustManager[]{
                new X509TrustManager() {
                    public X509Certificate[] getAcceptedIssuers() { return null; }
                    public void checkClientTrusted(X509Certificate[] certs, String authType) {}
                    public void checkServerTrusted(X509Certificate[] certs, String authType) {}
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
    // </editor-fold>

@SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        lblTitle = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        jPanel2 = new javax.swing.JPanel();
        lblTitle2 = new javax.swing.JLabel();
        modernPanel1 = new ec.edu.monster.vista.ModernPanel();
        rbtnEfectivo = new javax.swing.JRadioButton();
        rbtnCredito = new javax.swing.JRadioButton();
        jLabel13 = new javax.swing.JLabel();
        txt_numCuotas = new javax.swing.JTextField();
        btn_Agregar2 = new ec.edu.monster.vista.ModernButton();
        jPanel3 = new javax.swing.JPanel();
        lblTitle3 = new javax.swing.JLabel();
        modernPanel2 = new ec.edu.monster.vista.ModernPanel();
        jLabel7 = new javax.swing.JLabel();
        txtcodproducto = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        txtCantidad = new javax.swing.JTextField();
        btn_Agregar = new ec.edu.monster.vista.ModernButton();
        TableProducts = new ec.edu.monster.vista.ModernTable();
        modernTable1 = new ec.edu.monster.vista.ModernTable();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        lblTitle4 = new javax.swing.JLabel();
        modernPanel3 = new ec.edu.monster.vista.ModernPanel();
        jLabel6 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        txtcedula1 = new javax.swing.JTextField();
        btn_GenerateInvoice = new ec.edu.monster.vista.ModernButton();
        btn_GenerateInvoice1 = new ec.edu.monster.vista.ModernButton();
        jPanel5 = new javax.swing.JPanel();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        lblcuota = new javax.swing.JLabel();

        setBackground(new java.awt.Color(255, 255, 255));
        setMinimumSize(new java.awt.Dimension(820, 590));
        setPreferredSize(new java.awt.Dimension(820, 590));
        setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/FACTURACELESTE.png"))); // NOI18N

        lblTitle.setFont(new java.awt.Font("Britannic Bold", 0, 28)); // NOI18N
        lblTitle.setText("Crear Nueva Factura");

        jSeparator1.setForeground(new java.awt.Color(102, 102, 102));

        jPanel2.setBackground(new java.awt.Color(13, 110, 253));

        lblTitle2.setFont(new java.awt.Font("Britannic Bold", 0, 18)); // NOI18N
        lblTitle2.setForeground(new java.awt.Color(255, 255, 255));
        lblTitle2.setText("Datos del Cliente");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblTitle2)
                .addContainerGap(616, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(lblTitle2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        modernPanel1.setBorderRadius(5);

        rbtnEfectivo.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        rbtnEfectivo.setText("Efectivo (33% de descuento)");

        rbtnCredito.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        rbtnCredito.setText("Crédito");
        rbtnCredito.setToolTipText("La validación de crédito se realizará automáticamente al generar la factura");

        jLabel13.setFont(new java.awt.Font("Roboto", 3, 12)); // NOI18N
        jLabel13.setText("Número de Cuotas (3-24 meses)");

        txt_numCuotas.setFont(new java.awt.Font("Roboto", 0, 12)); // NOI18N
        txt_numCuotas.setText("3");

        btn_Agregar2.setBackground(new java.awt.Color(153, 153, 153));
        btn_Agregar2.setBackgroundColor(new java.awt.Color(153, 153, 153));
        btn_Agregar2.setBorderColor(new java.awt.Color(153, 153, 153));
        btn_Agregar2.setBorderRadius(5);
        btn_Agregar2.setBorderThickness(0);
        btn_Agregar2.setHoverColor(new java.awt.Color(102, 102, 102));
        btn_Agregar2.setPressedColor(new java.awt.Color(102, 102, 102));
        btn_Agregar2.setText("X  Cancelar");
        btn_Agregar2.setAlignmentY(0.6F);
        btn_Agregar2.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N

        javax.swing.GroupLayout modernPanel1Layout = new javax.swing.GroupLayout(modernPanel1);
        modernPanel1.setLayout(modernPanel1Layout);
        modernPanel1Layout.setHorizontalGroup(
            modernPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(modernPanel1Layout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addComponent(rbtnEfectivo)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 169, Short.MAX_VALUE)
                .addComponent(rbtnCredito)
                .addGap(12, 12, 12)
                .addComponent(txt_numCuotas, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 184, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18))
            .addGroup(modernPanel1Layout.createSequentialGroup()
                .addGap(432, 432, 432)
                .addComponent(btn_Agregar2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        modernPanel1Layout.setVerticalGroup(
            modernPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(modernPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(modernPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(rbtnEfectivo)
                    .addComponent(rbtnCredito)
                    .addComponent(jLabel13)
                    .addComponent(txt_numCuotas, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(58, 58, 58)
                .addComponent(btn_Agregar2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        jPanel3.setBackground(new java.awt.Color(24, 135, 84));

        lblTitle3.setFont(new java.awt.Font("Britannic Bold", 0, 18)); // NOI18N
        lblTitle3.setForeground(new java.awt.Color(255, 255, 255));
        lblTitle3.setText("Productos");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblTitle3)
                .addContainerGap(668, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addComponent(lblTitle3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        modernPanel2.setBorderRadius(5);

        jLabel7.setFont(new java.awt.Font("Roboto", 1, 12)); // NOI18N
        jLabel7.setText("Subtotal:");

        txtcodproducto.setFont(new java.awt.Font("Roboto", 0, 16)); // NOI18N
        txtcodproducto.setBorder(javax.swing.BorderFactory.createEtchedBorder(null, java.awt.Color.lightGray));
        txtcodproducto.setCaretColor(new java.awt.Color(51, 51, 255));

        jLabel8.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        jLabel8.setText("Cantidad");

        txtCantidad.setFont(new java.awt.Font("Roboto", 0, 16)); // NOI18N
        txtCantidad.setBorder(javax.swing.BorderFactory.createEtchedBorder(null, java.awt.Color.lightGray));
        txtCantidad.setCaretColor(new java.awt.Color(51, 51, 255));

        btn_Agregar.setBackground(new java.awt.Color(24, 135, 84));
        btn_Agregar.setBackgroundColor(new java.awt.Color(24, 135, 84));
        btn_Agregar.setBorderColor(new java.awt.Color(24, 135, 84));
        btn_Agregar.setBorderRadius(5);
        btn_Agregar.setBorderThickness(0);
        btn_Agregar.setHoverColor(new java.awt.Color(17, 91, 57));
        btn_Agregar.setPressedColor(new java.awt.Color(17, 91, 57));
        btn_Agregar.setText("+  Agregar");
        btn_Agregar.setAlignmentY(0.6F);
        btn_Agregar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_AgregarActionPerformed(evt);
            }
        });

        TableProducts.setHeaderBackground(new java.awt.Color(0, 0, 0));

        modernTable1.setBackground(new java.awt.Color(226, 243, 226));
        modernTable1.setColumnHeaderView(null);
        modernTable1.setContainerBackground(new java.awt.Color(226, 243, 226));
        modernTable1.setHeaderBackground(new java.awt.Color(226, 243, 226));
        modernTable1.setHeaderForeground(new java.awt.Color(0, 0, 0));
        modernTable1.setHoverRowColor(new java.awt.Color(255, 255, 255));

        jLabel9.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        jLabel9.setText("Buscar Producto por Código");

        jLabel10.setFont(new java.awt.Font("Roboto", 1, 12)); // NOI18N
        jLabel10.setText("IVA (15%):");

        jLabel11.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        jLabel11.setText("TOTAL:");

        jLabel16.setFont(new java.awt.Font("Roboto", 1, 12)); // NOI18N
        jLabel16.setText("Descuento");

        javax.swing.GroupLayout modernPanel2Layout = new javax.swing.GroupLayout(modernPanel2);
        modernPanel2.setLayout(modernPanel2Layout);
        modernPanel2Layout.setHorizontalGroup(
            modernPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(modernPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(modernPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(modernPanel2Layout.createSequentialGroup()
                        .addGroup(modernPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(TableProducts, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(modernTable1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(modernPanel2Layout.createSequentialGroup()
                                .addGroup(modernPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txtcodproducto, javax.swing.GroupLayout.PREFERRED_SIZE, 258, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 206, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addGroup(modernPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(txtCantidad, javax.swing.GroupLayout.DEFAULT_SIZE, 118, Short.MAX_VALUE)
                                    .addComponent(jLabel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addGap(18, 18, 18)
                                .addComponent(btn_Agregar, javax.swing.GroupLayout.PREFERRED_SIZE, 113, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE)))
                        .addContainerGap())
                    .addGroup(modernPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(70, 70, 70)
                        .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(82, 82, 82)
                        .addComponent(jLabel16, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(106, 106, 106))))
        );
        modernPanel2Layout.setVerticalGroup(
            modernPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(modernPanel2Layout.createSequentialGroup()
                .addGroup(modernPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(jLabel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(modernPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtcodproducto, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtCantidad, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_Agregar, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(TableProducts, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(14, 14, 14)
                .addGroup(modernPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel11, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel16, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(modernTable1, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jPanel4.setBackground(new java.awt.Color(254, 193, 7));

        lblTitle4.setFont(new java.awt.Font("Britannic Bold", 0, 18)); // NOI18N
        lblTitle4.setForeground(new java.awt.Color(255, 255, 255));
        lblTitle4.setText("Tipo de Pago");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblTitle4)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addComponent(lblTitle4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        modernPanel3.setBorderRadius(5);

        jLabel6.setFont(new java.awt.Font("Roboto", 1, 16)); // NOI18N
        jLabel6.setText("Cédula");

        jLabel12.setFont(new java.awt.Font("Roboto", 0, 10)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(102, 102, 102));
        jLabel12.setText("La validación de crédito se realizará al generar la factura");

        txtcedula1.setFont(new java.awt.Font("Roboto", 0, 16)); // NOI18N
        txtcedula1.setBorder(javax.swing.BorderFactory.createEtchedBorder(null, java.awt.Color.lightGray));
        txtcedula1.setCaretColor(new java.awt.Color(51, 51, 255));

        javax.swing.GroupLayout modernPanel3Layout = new javax.swing.GroupLayout(modernPanel3);
        modernPanel3.setLayout(modernPanel3Layout);
        modernPanel3Layout.setHorizontalGroup(
            modernPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(modernPanel3Layout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(txtcedula1, javax.swing.GroupLayout.PREFERRED_SIZE, 294, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 69, Short.MAX_VALUE)
                .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 272, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(23, 23, 23))
        );
        modernPanel3Layout.setVerticalGroup(
            modernPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(modernPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(modernPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(modernPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(txtcedula1)
                        .addComponent(jLabel12)))
                .addContainerGap())
        );

        btn_GenerateInvoice.setBackgroundColor(new java.awt.Color(153, 153, 153));
        btn_GenerateInvoice.setBorderColor(new java.awt.Color(153, 153, 153));
        btn_GenerateInvoice.setBorderRadius(0);
        btn_GenerateInvoice.setBorderThickness(0);
        btn_GenerateInvoice.setHoverColor(new java.awt.Color(102, 102, 102));
        btn_GenerateInvoice.setPressedColor(new java.awt.Color(102, 102, 102));
        btn_GenerateInvoice.setText("X  Cancelar");
        btn_GenerateInvoice.setFont(new java.awt.Font("Roboto", 1, 16)); // NOI18N
        btn_GenerateInvoice.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_GenerateInvoiceActionPerformed(evt);
            }
        });

        btn_GenerateInvoice1.setBorderRadius(0);
        btn_GenerateInvoice1.setBorderThickness(0);
        btn_GenerateInvoice1.setHoverColor(new java.awt.Color(7, 81, 119));
        btn_GenerateInvoice1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/FACTURA.png"))); // NOI18N
        btn_GenerateInvoice1.setText("Generar Factura");
        btn_GenerateInvoice1.setFont(new java.awt.Font("Roboto", 1, 16)); // NOI18N
        btn_GenerateInvoice1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_GenerateInvoice1ActionPerformed(evt);
            }
        });

        jPanel5.setBackground(new java.awt.Color(207, 244, 252));

        jLabel14.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        jLabel14.setForeground(new java.awt.Color(0, 102, 153));
        jLabel14.setText("Cuota mensual estimada: $");

        jLabel15.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        jLabel15.setForeground(new java.awt.Color(0, 102, 153));
        jLabel15.setText("Tasa de interés: 15% anual");

        lblcuota.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        lblcuota.setForeground(new java.awt.Color(0, 102, 153));

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 184, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblcuota, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, 184, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(95, Short.MAX_VALUE))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel14)
                    .addComponent(lblcuota, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel15)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jSeparator1))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                                .addGap(15, 15, 15)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addGroup(jPanel1Layout.createSequentialGroup()
                                            .addGap(2, 2, 2)
                                            .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addComponent(modernPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addGroup(jPanel1Layout.createSequentialGroup()
                                            .addComponent(jLabel1)
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                            .addComponent(lblTitle))
                                        .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addComponent(btn_GenerateInvoice, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(btn_GenerateInvoice1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(9, 9, 9))))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(modernPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(modernPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                        .addGap(17, 17, 17)))
                .addContainerGap(30, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(lblTitle, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 3, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(modernPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(7, 7, 7)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(modernPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(modernPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(btn_GenerateInvoice, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btn_GenerateInvoice1, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(57, Short.MAX_VALUE))
        );

        add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 820, 720));

        getAccessibleContext().setAccessibleName("");
        getAccessibleContext().setAccessibleDescription("");
    }// </editor-fold>//GEN-END:initComponents
    
    private void setupTipoPago() {
        tipoPagoGroup.add(rbtnEfectivo);
        tipoPagoGroup.add(rbtnCredito);
        rbtnEfectivo.setSelected(true);
        txt_numCuotas.setEnabled(false);
        
        rbtnEfectivo.addActionListener(e -> txt_numCuotas.setEnabled(false));
        rbtnCredito.addActionListener(e -> txt_numCuotas.setEnabled(true));
    }
    
    private void setupTable() {
        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("Imagen");
        model.addColumn("Producto");
        model.addColumn("Cantidad");
        model.addColumn("Precio Unit.");
        model.addColumn("Subtotal");
        
        javax.swing.JTable table = (javax.swing.JTable) TableProducts.getViewport().getView();
        table.setModel(model);
        
        // Configurar el renderer para la columna de imagen
        table.getColumnModel().getColumn(0).setCellRenderer(new ImageRenderer());
        // Ajustar el ancho de la columna de imagen
        table.getColumnModel().getColumn(0).setPreferredWidth(70);
        table.getColumnModel().getColumn(0).setMaxWidth(70);
        table.getColumnModel().getColumn(0).setMinWidth(70);
        
        // Ajustar altura de filas para que quepan las imágenes
        table.setRowHeight(65);
    }
    
    private void updateTotals() {
        BigDecimal subtotal = BigDecimal.ZERO;
        for (ProductoCarrito p : productosCarrito) {
            subtotal = subtotal.add(p.getSubtotal());
        }
        BigDecimal iva = subtotal.multiply(new BigDecimal("0.15"));
        BigDecimal total = subtotal.add(iva);
        
        // Actualizar labels si existen, pero en el código no hay labels para mostrar los valores, solo texto.
        // Asumir que están en modernTable1 o algo, pero por ahora solo calcular.
    }

    private void btn_GenerateInvoice1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_GenerateInvoice1ActionPerformed
        String cedula = txtcedula1.getText().trim();
        if (cedula.isEmpty()) {
            JOptionPane.showMessageDialog(SwingUtilities.getWindowAncestor(this), "Ingrese cédula del cliente.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        if (productosCarrito.isEmpty()) {
            JOptionPane.showMessageDialog(SwingUtilities.getWindowAncestor(this), "Agregue al menos un producto.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        String tipoPago = rbtnEfectivo.isSelected() ? "Efectivo" : "Credito";
        String tipoPagoSOAP = rbtnEfectivo.isSelected() ? "E" : "C";
        
        Integer numeroCuotas = null;
        if ("C".equals(tipoPagoSOAP)) {
            try {
                numeroCuotas = Integer.parseInt(txt_numCuotas.getText().trim());
                if (numeroCuotas < 3 || numeroCuotas > 24) {
                    JOptionPane.showMessageDialog(SwingUtilities.getWindowAncestor(this), "Número de cuotas debe ser entre 3 y 24.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(SwingUtilities.getWindowAncestor(this), "Número de cuotas inválido.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
        }
        
        try {
            SolicitudFactura solicitud = new SolicitudFactura();
            solicitud.setCedula(cedula);
            solicitud.setTipoPago(tipoPagoSOAP);
            if (numeroCuotas != null) {
                solicitud.setNumeroCuotas(numeroCuotas);
            }
            solicitud.setDetalles(productosCarrito.stream().map(p -> {
                DetalleFacturaRequest d = new DetalleFacturaRequest();
                d.setCodigo(p.getCodigo());
                d.setCantidad(p.getCantidad());
                return d;
            }).collect(java.util.stream.Collectors.toList()));
            
            java.util.Map<String, Object> respuesta = facturaService.crearFactura(solicitud);
            if (respuesta != null && respuesta.containsKey("numFactura")) {
                String numFactura = respuesta.get("numFactura").toString();
                JOptionPane.showMessageDialog(SwingUtilities.getWindowAncestor(this), "Factura creada: " + numFactura, "Éxito", JOptionPane.INFORMATION_MESSAGE);
                
                // Mostrar detalle
                if (parentFrame != null) {
                    InvoiceDetail panelDetalle = new InvoiceDetail(parentFrame, numFactura);
                    parentFrame.showPanel(panelDetalle);
                }
            } else {
                JOptionPane.showMessageDialog(SwingUtilities.getWindowAncestor(this), "Error al crear factura.", "Error", JOptionPane.ERROR_MESSAGE);
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
            JOptionPane.showMessageDialog(SwingUtilities.getWindowAncestor(this), "Error: " + errorMsg, "Error", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_btn_GenerateInvoice1ActionPerformed

    private void btn_GenerateInvoiceActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_GenerateInvoiceActionPerformed
        if (parentFrame != null) {
            Dashboard panelDashboard = new Dashboard(parentFrame);

            parentFrame.showPanel(panelDashboard);
        } else {
            System.err.println("Error: CreateInvoice no está asociado a un Home.");
        }
    }//GEN-LAST:event_btn_GenerateInvoiceActionPerformed

    private void btn_AgregarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_AgregarActionPerformed
        String codigo = txtcodproducto.getText().trim();
        String cantidadStr = txtCantidad.getText().trim();
        
        if (codigo.isEmpty() || cantidadStr.isEmpty()) {
            JOptionPane.showMessageDialog(SwingUtilities.getWindowAncestor(this), "Ingrese código y cantidad.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        try {
            int cantidad = Integer.parseInt(cantidadStr);
            if (cantidad <= 0) {
                JOptionPane.showMessageDialog(SwingUtilities.getWindowAncestor(this), "Cantidad debe ser mayor a 0.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            Electrodomestico electro = electrodomesticoService.obtenerElectrodomesticoPorCodigo(codigo);
            if (electro == null) {
                JOptionPane.showMessageDialog(SwingUtilities.getWindowAncestor(this), "Producto no encontrado.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            // Verificar si el producto ya está en la lista
            boolean encontrado = false;
            for (ProductoCarrito pc : productosCarrito) {
                if (pc.getCodigo().equals(codigo)) {
                    pc.setCantidad(pc.getCantidad() + cantidad);
                    encontrado = true;
                    break;
                }
            }
            
            if (!encontrado) {
                ProductoCarrito pc = new ProductoCarrito();
                pc.setCodigo(codigo);
                pc.setCantidad(cantidad);
                pc.setNombre(electro.getNombre());
                pc.setPrecioUnitario(electro.getPrecio());
                pc.setImageUrl(electro.getImageUrl()); // Guardar URL de imagen
                productosCarrito.add(pc);
            }
            
            // Actualizar tabla
            DefaultTableModel model = (DefaultTableModel) ((javax.swing.JTable) TableProducts.getViewport().getView()).getModel();
            model.setRowCount(0); // Limpiar tabla
            for (ProductoCarrito pc : productosCarrito) {
                // Cargar imagen desde URL
                ImageIcon imageIcon = loadImageFromUrl(pc.getImageUrl());
                model.addRow(new Object[]{
                    imageIcon,
                    pc.getNombre(),
                    pc.getCantidad(),
                    String.format("$%,.2f", pc.getPrecioUnitario()),
                    String.format("$%,.2f", pc.getSubtotal())
                });
            }
            
            txtcodproducto.setText("");
            txtCantidad.setText("");
            updateTotals();
            
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(SwingUtilities.getWindowAncestor(this), "Cantidad inválida.", "Error", JOptionPane.ERROR_MESSAGE);
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
            JOptionPane.showMessageDialog(SwingUtilities.getWindowAncestor(this), "Error: " + errorMsg, "Error", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_btn_AgregarActionPerformed
    
    

    


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private ec.edu.monster.vista.ModernTable TableProducts;
    private ec.edu.monster.vista.ModernButton btn_Agregar;
    private ec.edu.monster.vista.ModernButton btn_Agregar2;
    private ec.edu.monster.vista.ModernButton btn_GenerateInvoice;
    private ec.edu.monster.vista.ModernButton btn_GenerateInvoice1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JLabel lblTitle;
    private javax.swing.JLabel lblTitle2;
    private javax.swing.JLabel lblTitle3;
    private javax.swing.JLabel lblTitle4;
    private javax.swing.JLabel lblcuota;
    private ec.edu.monster.vista.ModernPanel modernPanel1;
    private ec.edu.monster.vista.ModernPanel modernPanel2;
    private ec.edu.monster.vista.ModernPanel modernPanel3;
    private ec.edu.monster.vista.ModernTable modernTable1;
    private javax.swing.JRadioButton rbtnCredito;
    private javax.swing.JRadioButton rbtnEfectivo;
    private javax.swing.JTextField txtCantidad;
    private javax.swing.JTextField txt_numCuotas;
    private javax.swing.JTextField txtcedula1;
    private javax.swing.JTextField txtcodproducto;
    // End of variables declaration//GEN-END:variables
}
