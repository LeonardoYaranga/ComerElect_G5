package ec.edu.monster.vista;

import ec.edu.monster.vista.Home;
import ec.edu.monster.services.FacturaService;
import ec.edu.monster.models.DashboardViewModel;
import ec.edu.monster.models.ProductoMasVendido;
import ec.edu.monster.models.DetalleFacturaViewModel;
import ec.edu.monster.models.DetalleProducto;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 *
 * @author Dome
 */
public class Dashboard extends javax.swing.JPanel {
    private Home parentFrame;
    private FacturaService facturaService;
    
    /**
     * Creates new form Clients
     */
    public Dashboard(Home parent) {
        RestTemplate restTemplate = new RestTemplate();
        configureRestTemplate(restTemplate);
        this.facturaService = new FacturaService(restTemplate, "http://localhost:8081/api");
        this.parentFrame = parent;
        initComponents();
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
    
    private void cargarDatos() {
        try {
            List<DetalleFacturaViewModel> facturas = facturaService.listarFacturas();
            if (facturas != null) {
                LocalDate today = LocalDate.now();
                
                // Calcular métricas
                int totalFacturasHoy = 0;
                BigDecimal totalEfectivo = BigDecimal.ZERO;
                BigDecimal totalCredito = BigDecimal.ZERO;
                
                Map<String, Integer> productoCount = new HashMap<>();
                Map<String, String> productoNombre = new HashMap<>();
                
                for (DetalleFacturaViewModel factura : facturas) {
                    // Contar facturas de hoy
                    if (factura.getFecha() != null && factura.getFecha().equals(today)) {
                        totalFacturasHoy++;
                    }
                    
                    // Sumar totales por tipo de pago
                    if ("E".equals(factura.getTipoPago())) {
                        totalEfectivo = totalEfectivo.add(factura.getTotal() != null ? factura.getTotal() : BigDecimal.ZERO);
                    } else if ("C".equals(factura.getTipoPago())) {
                        totalCredito = totalCredito.add(factura.getTotal() != null ? factura.getTotal() : BigDecimal.ZERO);
                    }
                    
                    // Contar productos vendidos
                    if (factura.getProductos() != null) {
                        for (DetalleProducto p : factura.getProductos()) {
                            String codigo = p.getCodigo();
                            int cantidad = p.getCantidad();
                            productoCount.put(codigo, productoCount.getOrDefault(codigo, 0) + cantidad);
                            productoNombre.put(codigo, p.getNombre());
                        }
                    }
                }
                
                // Poblar labels
                lbl_invoicestoday.setText(String.valueOf(totalFacturasHoy));
                lbl_cashtoday.setText(String.format("$%,.2f", totalEfectivo));
                lbl_credittoday.setText(String.format("$%,.2f", totalCredito));
                lbl_totaltoday.setText(String.format("$%,.2f", totalEfectivo.add(totalCredito)));
                
                // Encontrar el producto más vendido
                if (!productoCount.isEmpty()) {
                    String topCodigo = productoCount.entrySet().stream()
                        .max(Map.Entry.comparingByValue())
                        .get().getKey();
                    String nombre = productoNombre.get(topCodigo);
                    int cantidad = productoCount.get(topCodigo);
                    lblElecMasVendido.setText("1. " + nombre + " (" + cantidad + " vendidos)");
                } else {
                    lblElecMasVendido.setText("No hay datos disponibles");
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
            JOptionPane.showMessageDialog(SwingUtilities.getWindowAncestor(this), "Error al cargar datos del dashboard: " + errorMsg, "Error", JOptionPane.ERROR_MESSAGE);
        }
    }


    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        lblTitle = new javax.swing.JLabel();
        Contenedor_InvoiceToday = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        lbl_invoicestoday = new javax.swing.JLabel();
        Contenedor_CashToday = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        lbl_cashtoday = new javax.swing.JLabel();
        Contenedor_CreditToday = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        lbl_credittoday = new javax.swing.JLabel();
        Contenedor_TotalToday = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        lbl_totaltoday = new javax.swing.JLabel();
        lblTitle1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        modernButton1 = new ec.edu.monster.vista.ModernButton();
        modernButton2 = new ec.edu.monster.vista.ModernButton();
        btnSearchInvoice = new ec.edu.monster.vista.ModernButton();
        jPanel2 = new javax.swing.JPanel();
        lblTitle2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        lblElecMasVendido = new javax.swing.JLabel();

        setBackground(new java.awt.Color(255, 255, 255));
        setMaximumSize(new java.awt.Dimension(890, 590));
        setPreferredSize(new java.awt.Dimension(890, 590));
        setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/CASA.png"))); // NOI18N

        lblTitle.setFont(new java.awt.Font("Britannic Bold", 0, 28)); // NOI18N
        lblTitle.setText("Dashboard");

        Contenedor_InvoiceToday.setBackground(new java.awt.Color(13, 110, 253));

        jLabel4.setFont(new java.awt.Font("Roboto", 1, 18)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/FACTURA.png"))); // NOI18N
        jLabel4.setText("Facturas Hoy");

        lbl_invoicestoday.setFont(new java.awt.Font("Roboto", 1, 20)); // NOI18N
        lbl_invoicestoday.setForeground(new java.awt.Color(255, 255, 255));
        lbl_invoicestoday.setText("0");

        javax.swing.GroupLayout Contenedor_InvoiceTodayLayout = new javax.swing.GroupLayout(Contenedor_InvoiceToday);
        Contenedor_InvoiceToday.setLayout(Contenedor_InvoiceTodayLayout);
        Contenedor_InvoiceTodayLayout.setHorizontalGroup(
            Contenedor_InvoiceTodayLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(Contenedor_InvoiceTodayLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lbl_invoicestoday, javax.swing.GroupLayout.PREFERRED_SIZE, 147, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(17, Short.MAX_VALUE))
        );
        Contenedor_InvoiceTodayLayout.setVerticalGroup(
            Contenedor_InvoiceTodayLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(Contenedor_InvoiceTodayLayout.createSequentialGroup()
                .addComponent(jLabel4)
                .addGap(18, 18, 18)
                .addComponent(lbl_invoicestoday, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        Contenedor_CashToday.setBackground(new java.awt.Color(24, 135, 84));
        Contenedor_CashToday.setPreferredSize(new java.awt.Dimension(169, 40));

        jLabel5.setFont(new java.awt.Font("Roboto", 1, 18)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/BILLETE.png"))); // NOI18N
        jLabel5.setText("Efectivo Hoy");

        lbl_cashtoday.setFont(new java.awt.Font("Roboto", 1, 20)); // NOI18N
        lbl_cashtoday.setForeground(new java.awt.Color(255, 255, 255));
        lbl_cashtoday.setText("$ 0.00");

        javax.swing.GroupLayout Contenedor_CashTodayLayout = new javax.swing.GroupLayout(Contenedor_CashToday);
        Contenedor_CashToday.setLayout(Contenedor_CashTodayLayout);
        Contenedor_CashTodayLayout.setHorizontalGroup(
            Contenedor_CashTodayLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(Contenedor_CashTodayLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lbl_cashtoday, javax.swing.GroupLayout.PREFERRED_SIZE, 147, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 16, Short.MAX_VALUE))
            .addComponent(jLabel5, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        Contenedor_CashTodayLayout.setVerticalGroup(
            Contenedor_CashTodayLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(Contenedor_CashTodayLayout.createSequentialGroup()
                .addComponent(jLabel5)
                .addGap(18, 18, 18)
                .addComponent(lbl_cashtoday, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 19, Short.MAX_VALUE))
        );

        Contenedor_CreditToday.setBackground(new java.awt.Color(13, 202, 240));
        Contenedor_CreditToday.setPreferredSize(new java.awt.Dimension(169, 40));

        jLabel7.setFont(new java.awt.Font("Roboto", 1, 18)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(255, 255, 255));
        jLabel7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/TARJETA.png"))); // NOI18N
        jLabel7.setText("Crédito Hoy");

        lbl_credittoday.setFont(new java.awt.Font("Roboto", 1, 20)); // NOI18N
        lbl_credittoday.setForeground(new java.awt.Color(255, 255, 255));
        lbl_credittoday.setText("$ 0.00");

        javax.swing.GroupLayout Contenedor_CreditTodayLayout = new javax.swing.GroupLayout(Contenedor_CreditToday);
        Contenedor_CreditToday.setLayout(Contenedor_CreditTodayLayout);
        Contenedor_CreditTodayLayout.setHorizontalGroup(
            Contenedor_CreditTodayLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(Contenedor_CreditTodayLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lbl_credittoday, javax.swing.GroupLayout.PREFERRED_SIZE, 147, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(Contenedor_CreditTodayLayout.createSequentialGroup()
                .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 166, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 3, Short.MAX_VALUE))
        );
        Contenedor_CreditTodayLayout.setVerticalGroup(
            Contenedor_CreditTodayLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(Contenedor_CreditTodayLayout.createSequentialGroup()
                .addComponent(jLabel7)
                .addGap(18, 18, 18)
                .addComponent(lbl_credittoday, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        Contenedor_TotalToday.setBackground(new java.awt.Color(254, 193, 7));
        Contenedor_TotalToday.setPreferredSize(new java.awt.Dimension(169, 40));

        jLabel6.setFont(new java.awt.Font("Roboto", 1, 18)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(255, 255, 255));
        jLabel6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/DINERO.png"))); // NOI18N
        jLabel6.setText("Total Hoy");

        lbl_totaltoday.setFont(new java.awt.Font("Roboto", 1, 20)); // NOI18N
        lbl_totaltoday.setForeground(new java.awt.Color(255, 255, 255));
        lbl_totaltoday.setText("$ 0.00");

        javax.swing.GroupLayout Contenedor_TotalTodayLayout = new javax.swing.GroupLayout(Contenedor_TotalToday);
        Contenedor_TotalToday.setLayout(Contenedor_TotalTodayLayout);
        Contenedor_TotalTodayLayout.setHorizontalGroup(
            Contenedor_TotalTodayLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(Contenedor_TotalTodayLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(Contenedor_TotalTodayLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lbl_totaltoday, javax.swing.GroupLayout.PREFERRED_SIZE, 147, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        Contenedor_TotalTodayLayout.setVerticalGroup(
            Contenedor_TotalTodayLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(Contenedor_TotalTodayLayout.createSequentialGroup()
                .addComponent(jLabel6)
                .addGap(18, 18, 18)
                .addComponent(lbl_totaltoday, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        lblTitle1.setFont(new java.awt.Font("Britannic Bold", 0, 22)); // NOI18N
        lblTitle1.setText("Accesos Rápidos");

        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Cohete.png"))); // NOI18N

        modernButton1.setBackgroundColor(new java.awt.Color(255, 255, 255));
        modernButton1.setBorderColor(new java.awt.Color(13, 110, 253));
        modernButton1.setBorderRadius(5);
        modernButton1.setForegroundColor(new java.awt.Color(13, 110, 253));
        modernButton1.setHoverColor(new java.awt.Color(210, 234, 246));
        modernButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/CREARCOLOR.png"))); // NOI18N
        modernButton1.setPressedColor(new java.awt.Color(51, 153, 255));
        modernButton1.setText("Crear Nueva Factura");
        modernButton1.setToolTipText("");
        modernButton1.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        modernButton1.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        modernButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                modernButton1ActionPerformed(evt);
            }
        });

        modernButton2.setBackgroundColor(new java.awt.Color(255, 255, 255));
        modernButton2.setBorderColor(new java.awt.Color(24, 135, 84));
        modernButton2.setBorderRadius(5);
        modernButton2.setForegroundColor(new java.awt.Color(24, 135, 84));
        modernButton2.setHoverColor(new java.awt.Color(237, 249, 237));
        modernButton2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ELECTRODOMESTICOVERDE.png"))); // NOI18N
        modernButton2.setText("Ver Electrodomésticos");
        modernButton2.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        modernButton2.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        modernButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                modernButton2ActionPerformed(evt);
            }
        });

        btnSearchInvoice.setBackgroundColor(new java.awt.Color(255, 255, 255));
        btnSearchInvoice.setBorderColor(new java.awt.Color(13, 202, 240));
        btnSearchInvoice.setBorderRadius(5);
        btnSearchInvoice.setForegroundColor(new java.awt.Color(13, 202, 240));
        btnSearchInvoice.setHoverColor(new java.awt.Color(242, 252, 255));
        btnSearchInvoice.setIcon(new javax.swing.ImageIcon(getClass().getResource("/FACTURACELESTE.png"))); // NOI18N
        btnSearchInvoice.setText("Consultar Facturas");
        btnSearchInvoice.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        btnSearchInvoice.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        btnSearchInvoice.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSearchInvoiceActionPerformed(evt);
            }
        });

        jPanel2.setBackground(new java.awt.Color(0, 0, 0));

        lblTitle2.setFont(new java.awt.Font("Britannic Bold", 0, 22)); // NOI18N
        lblTitle2.setForeground(new java.awt.Color(255, 255, 255));
        lblTitle2.setText("Electrodoméstico Más Vendido");

        jLabel3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/TROFEO.png"))); // NOI18N

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblTitle2)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(jLabel3)
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addComponent(lblTitle2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        lblElecMasVendido.setFont(new java.awt.Font("Roboto", 0, 18)); // NOI18N
        lblElecMasVendido.setText("1. ");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(lblElecMasVendido, javax.swing.GroupLayout.PREFERRED_SIZE, 765, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addGroup(jPanel1Layout.createSequentialGroup()
                            .addGap(30, 30, 30)
                            .addComponent(Contenedor_InvoiceToday, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(28, 28, 28)
                            .addComponent(Contenedor_CashToday, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(28, 28, 28)
                            .addComponent(Contenedor_CreditToday, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(Contenedor_TotalToday, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(jPanel1Layout.createSequentialGroup()
                            .addGap(18, 18, 18)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addGroup(jPanel1Layout.createSequentialGroup()
                                    .addComponent(jLabel1)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                    .addComponent(lblTitle))
                                .addGroup(jPanel1Layout.createSequentialGroup()
                                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(lblTitle1))
                                .addGroup(jPanel1Layout.createSequentialGroup()
                                    .addComponent(modernButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 244, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGap(21, 21, 21)
                                    .addComponent(modernButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 244, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGap(18, 18, 18)
                                    .addComponent(btnSearchInvoice, javax.swing.GroupLayout.PREFERRED_SIZE, 244, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))))
                .addContainerGap(31, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lblTitle, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(Contenedor_CashToday, javax.swing.GroupLayout.DEFAULT_SIZE, 119, Short.MAX_VALUE)
                    .addComponent(Contenedor_CreditToday, javax.swing.GroupLayout.DEFAULT_SIZE, 119, Short.MAX_VALUE)
                    .addComponent(Contenedor_TotalToday, javax.swing.GroupLayout.DEFAULT_SIZE, 119, Short.MAX_VALUE)
                    .addComponent(Contenedor_InvoiceToday, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(53, 53, 53)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblTitle1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(modernButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(modernButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnSearchInvoice, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(37, 37, 37)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(lblElecMasVendido)
                .addContainerGap(238, Short.MAX_VALUE))
        );

        add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 820, 690));
    }// </editor-fold>//GEN-END:initComponents

    private void modernButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_modernButton1ActionPerformed
        if (parentFrame != null) {

            CreateInvoice nuevoPanel = new CreateInvoice(parentFrame);
            parentFrame.showPanel(nuevoPanel);

        } else {
            System.err.println("Error: Dashboard no está asociado a un Home.");
        }
    }//GEN-LAST:event_modernButton1ActionPerformed

    private void modernButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_modernButton2ActionPerformed
        if (parentFrame != null) {

            Appliance nuevoPanel = new Appliance();
            parentFrame.showPanel(nuevoPanel);

        } else {
            System.err.println("Error: Dashboard no está asociado a un Home.");
        }
    }//GEN-LAST:event_modernButton2ActionPerformed

    private void btnSearchInvoiceActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSearchInvoiceActionPerformed
        if (parentFrame != null) {

            SearchInvoice nuevoPanel = new SearchInvoice();
            parentFrame.showPanel(nuevoPanel);

        } else {
            System.err.println("Error: Dashboard no está asociado a un Home.");
        }
    }//GEN-LAST:event_btnSearchInvoiceActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel Contenedor_CashToday;
    private javax.swing.JPanel Contenedor_CreditToday;
    private javax.swing.JPanel Contenedor_InvoiceToday;
    private javax.swing.JPanel Contenedor_TotalToday;
    private ec.edu.monster.vista.ModernButton btnSearchInvoice;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JLabel lblElecMasVendido;
    private javax.swing.JLabel lblTitle;
    private javax.swing.JLabel lblTitle1;
    private javax.swing.JLabel lblTitle2;
    private javax.swing.JLabel lbl_cashtoday;
    private javax.swing.JLabel lbl_credittoday;
    private javax.swing.JLabel lbl_invoicestoday;
    private javax.swing.JLabel lbl_totaltoday;
    private ec.edu.monster.vista.ModernButton modernButton1;
    private ec.edu.monster.vista.ModernButton modernButton2;
    // End of variables declaration//GEN-END:variables
}
