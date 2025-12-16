package ec.edu.monster.vista;

import ec.edu.monster.services.FacturaService;
import ec.edu.monster.models.DetalleFacturaViewModel;
import ec.edu.monster.models.CuotaAmortizacion;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import javax.swing.table.DefaultTableModel;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.util.List;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.properties.TextAlignment;
import com.itextpdf.layout.properties.UnitValue;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 *
 * @author Dome
 */
public class TablaAmortización extends javax.swing.JPanel {
    private Home parentFrame;
    private FacturaService facturaService;
    private DetalleFacturaViewModel currentFactura;
    private List<CuotaAmortizacion> currentCuotas;
    
    public TablaAmortización(Home parent) {
        RestTemplate restTemplate = new RestTemplate();
        configureRestTemplate(restTemplate);
        this.facturaService = new FacturaService(restTemplate, "http://192.168.137.1:8081/api");
        initComponents();
        this.parentFrame = parent;
    }
    
    public TablaAmortización(Home parent, String numFactura) {
        RestTemplate restTemplate = new RestTemplate();
        configureRestTemplate(restTemplate);
        this.facturaService = new FacturaService(restTemplate, "http://192.168.137.1:8081/api");
        initComponents();
        this.parentFrame = parent;
        loadTablaAmortizacion(numFactura);
    }
    
    private void configureRestTemplate(RestTemplate restTemplate) {
        List<HttpMessageConverter<?>> converters = restTemplate.getMessageConverters();
        for (HttpMessageConverter<?> converter : converters) {
            if (converter instanceof StringHttpMessageConverter) {
                ((StringHttpMessageConverter) converter).setDefaultCharset(StandardCharsets.UTF_8);
            }
        }
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        btn_Back = new ec.edu.monster.vista.ModernButton();
        btn_print = new ec.edu.monster.vista.ModernButton();
        jLabel1 = new javax.swing.JLabel();
        lblTitle = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        lblTitle2 = new javax.swing.JLabel();
        modernPanel1 = new ec.edu.monster.vista.ModernPanel();
        lblNumFac2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        lblMontoTotal = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        lblPlazo = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        lblTasaAnual = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        lblCuotaFija = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        lblTitle3 = new javax.swing.JLabel();
        NumFactura1 = new javax.swing.JLabel();
        modernPanel2 = new ec.edu.monster.vista.ModernPanel();
        modernTable1 = new ec.edu.monster.vista.ModernTable();

        setBackground(new java.awt.Color(255, 255, 255));
        setMinimumSize(new java.awt.Dimension(820, 590));
        setPreferredSize(new java.awt.Dimension(820, 590));
        setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        btn_Back.setBackgroundColor(new java.awt.Color(153, 153, 153));
        btn_Back.setBorderColor(new java.awt.Color(153, 153, 153));
        btn_Back.setBorderRadius(0);
        btn_Back.setBorderThickness(0);
        btn_Back.setHoverColor(new java.awt.Color(102, 102, 102));
        btn_Back.setPressedColor(new java.awt.Color(102, 102, 102));
        btn_Back.setText("<   Volver a Factura");
        btn_Back.setActionCommand(" Volver");
        btn_Back.setFont(new java.awt.Font("Roboto", 1, 16)); // NOI18N
        btn_Back.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_BackActionPerformed(evt);
            }
        });

        btn_print.setBorderRadius(0);
        btn_print.setBorderThickness(0);
        btn_print.setHoverColor(new java.awt.Color(7, 81, 119));
        btn_print.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IMPRESORA.png"))); // NOI18N
        btn_print.setText("Imprimir Tabla");
        btn_print.setFont(new java.awt.Font("Roboto", 1, 16)); // NOI18N
        btn_print.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_printActionPerformed(evt);
            }
        });

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Cuentas.png"))); // NOI18N

        lblTitle.setFont(new java.awt.Font("Britannic Bold", 0, 28)); // NOI18N
        lblTitle.setText("Tabla de Amortización");

        jPanel2.setBackground(new java.awt.Color(0, 0, 0));

        lblTitle2.setFont(new java.awt.Font("Britannic Bold", 0, 18)); // NOI18N
        lblTitle2.setForeground(new java.awt.Color(255, 255, 255));
        lblTitle2.setText("Detalle de Cuotas");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblTitle2)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lblTitle2, javax.swing.GroupLayout.DEFAULT_SIZE, 34, Short.MAX_VALUE)
        );

        modernPanel1.setBorderRadius(5);

        lblNumFac2.setFont(new java.awt.Font("Roboto", 0, 12)); // NOI18N
        lblNumFac2.setText("FAC");

        jLabel3.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        jLabel3.setText("Número de Factura:");

        lblMontoTotal.setFont(new java.awt.Font("Roboto", 0, 12)); // NOI18N
        lblMontoTotal.setText("Monto");

        jLabel4.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        jLabel4.setText("Monto Total:");

        lblPlazo.setFont(new java.awt.Font("Roboto", 0, 12)); // NOI18N
        lblPlazo.setText("Plazo");
        lblPlazo.setToolTipText("");

        jLabel5.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        jLabel5.setText("Plazo:");

        lblTasaAnual.setFont(new java.awt.Font("Roboto", 0, 12)); // NOI18N
        lblTasaAnual.setText("Tasa");

        jLabel6.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        jLabel6.setText("Tasa de Interés Anual:");

        jPanel3.setBackground(new java.awt.Color(207, 226, 255));

        jLabel2.setFont(new java.awt.Font("Roboto", 1, 12)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(75, 93, 150));
        jLabel2.setText("$ Cuota Fija Mensual: $");

        lblCuotaFija.setFont(new java.awt.Font("Roboto", 1, 12)); // NOI18N
        lblCuotaFija.setForeground(new java.awt.Color(75, 93, 150));
        lblCuotaFija.setText("0");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(lblCuotaFija, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(lblCuotaFija, javax.swing.GroupLayout.DEFAULT_SIZE, 26, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout modernPanel1Layout = new javax.swing.GroupLayout(modernPanel1);
        modernPanel1.setLayout(modernPanel1Layout);
        modernPanel1Layout.setHorizontalGroup(
            modernPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(modernPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(modernPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(modernPanel1Layout.createSequentialGroup()
                        .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addContainerGap())
                    .addGroup(modernPanel1Layout.createSequentialGroup()
                        .addGroup(modernPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel3)
                            .addComponent(lblNumFac2, javax.swing.GroupLayout.PREFERRED_SIZE, 161, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 63, Short.MAX_VALUE)
                        .addGroup(modernPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel4)
                            .addComponent(lblMontoTotal, javax.swing.GroupLayout.PREFERRED_SIZE, 161, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(modernPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(modernPanel1Layout.createSequentialGroup()
                                .addGap(21, 21, 21)
                                .addComponent(jLabel5)
                                .addGap(123, 123, 123))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, modernPanel1Layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(lblPlazo, javax.swing.GroupLayout.PREFERRED_SIZE, 161, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(28, 28, 28)
                        .addGroup(modernPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel6)
                            .addComponent(lblTasaAnual, javax.swing.GroupLayout.PREFERRED_SIZE, 161, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(33, 33, 33))))
        );
        modernPanel1Layout.setVerticalGroup(
            modernPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(modernPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(modernPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(lblPlazo)
                    .addGroup(modernPanel1Layout.createSequentialGroup()
                        .addGroup(modernPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel4)
                            .addComponent(jLabel5)
                            .addComponent(jLabel6))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(modernPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblMontoTotal)
                            .addComponent(lblTasaAnual)))
                    .addGroup(modernPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblNumFac2)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jPanel4.setBackground(new java.awt.Color(13, 202, 240));

        lblTitle3.setFont(new java.awt.Font("Britannic Bold", 0, 18)); // NOI18N
        lblTitle3.setForeground(new java.awt.Color(255, 255, 255));
        lblTitle3.setText("Factura N°");

        NumFactura1.setFont(new java.awt.Font("Britannic Bold", 0, 18)); // NOI18N
        NumFactura1.setForeground(new java.awt.Color(255, 255, 255));
        NumFactura1.setText("FAC-2025-0003");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblTitle3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(NumFactura1, javax.swing.GroupLayout.PREFERRED_SIZE, 161, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lblTitle3, javax.swing.GroupLayout.DEFAULT_SIZE, 34, Short.MAX_VALUE)
            .addComponent(NumFactura1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        modernPanel2.setBorderRadius(5);

        modernTable1.setHeaderBackground(new java.awt.Color(0, 0, 0));

        javax.swing.GroupLayout modernPanel2Layout = new javax.swing.GroupLayout(modernPanel2);
        modernPanel2.setLayout(modernPanel2Layout);
        modernPanel2Layout.setHorizontalGroup(
            modernPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(modernPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(modernTable1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        modernPanel2Layout.setVerticalGroup(
            modernPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(modernPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(modernTable1, javax.swing.GroupLayout.PREFERRED_SIZE, 369, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(18, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(14, 14, 14)
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(lblTitle)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btn_Back, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btn_print, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(modernPanel2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jPanel2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(modernPanel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(btn_Back, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btn_print, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel1)
                    .addComponent(lblTitle, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(modernPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(modernPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(45, Short.MAX_VALUE))
        );

        add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 820, 690));

        getAccessibleContext().setAccessibleName("");
        getAccessibleContext().setAccessibleDescription("");
    }// </editor-fold>//GEN-END:initComponents

    private void btn_BackActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_BackActionPerformed
        if (parentFrame != null) {
            // ¡Recuerda pasar el 'parentFrame' y el ID de la factura!
            parentFrame.showPanel(new InvoiceDetail(parentFrame /*, idFactura */));
        }
    }//GEN-LAST:event_btn_BackActionPerformed

    private void btn_printActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_printActionPerformed
        if (currentFactura == null || currentCuotas == null || currentCuotas.isEmpty()) {
            JOptionPane.showMessageDialog(SwingUtilities.getWindowAncestor(this), 
                "No hay datos de amortización cargados para imprimir.", 
                "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Guardar PDF de Tabla de Amortización");
        fileChooser.setSelectedFile(new java.io.File("TablaAmortizacion_" + currentFactura.getNumFactura() + ".pdf"));
        FileNameExtensionFilter filter = new FileNameExtensionFilter("Archivos PDF", "pdf");
        fileChooser.setFileFilter(filter);
        
        int userSelection = fileChooser.showSaveDialog(SwingUtilities.getWindowAncestor(this));
        if (userSelection != JFileChooser.APPROVE_OPTION) {
            return;
        }
        
        java.io.File fileToSave = fileChooser.getSelectedFile();
        if (!fileToSave.getName().toLowerCase().endsWith(".pdf")) {
            fileToSave = new java.io.File(fileToSave.getAbsolutePath() + ".pdf");
        }
        
        try {
            PdfWriter writer = new PdfWriter(fileToSave.getAbsolutePath());
            PdfDocument pdfDoc = new PdfDocument(writer);
            Document document = new Document(pdfDoc);
            
            // Título
            Paragraph title = new Paragraph("Tabla de Amortización")
                    .setFontSize(20)
                    .setTextAlignment(TextAlignment.CENTER);
            document.add(title);
            
            // Información de la factura (usando datos del API)
            document.add(new Paragraph("Factura N°: " + currentFactura.getNumFactura()));
            document.add(new Paragraph("Monto Total: " + String.format("$%,.2f", currentFactura.getTotal())));
            document.add(new Paragraph("Plazo: " + currentCuotas.size() + " meses"));
            document.add(new Paragraph("Tasa de Interés Anual: 15%"));
            
            // Cuota fija viene del API
            BigDecimal cuotaFija = currentCuotas.get(0).getValorCuota();
            document.add(new Paragraph("Cuota Fija Mensual: " + String.format("$%,.2f", cuotaFija)));
            
            // Tabla de amortización (usando datos del API)
            document.add(new Paragraph("\nTabla de Amortización:").setFontSize(14));
            Table table = new Table(UnitValue.createPercentArray(new float[]{1, 2, 2, 2, 2}));
            table.setWidth(UnitValue.createPercentValue(100));
            
            table.addHeaderCell(new Cell().add(new Paragraph("N° Cuota")));
            table.addHeaderCell(new Cell().add(new Paragraph("Valor Cuota")));
            table.addHeaderCell(new Cell().add(new Paragraph("Interés")));
            table.addHeaderCell(new Cell().add(new Paragraph("Capital")));
            table.addHeaderCell(new Cell().add(new Paragraph("Saldo Pendiente")));
            
            // Agregar cada cuota desde los datos del API
            for (CuotaAmortizacion cuota : currentCuotas) {
                table.addCell(new Cell().add(new Paragraph(String.valueOf(cuota.getNumeroCuota()))));
                table.addCell(new Cell().add(new Paragraph(cuota.getValorCuota() != null ? 
                    String.format("$%,.2f", cuota.getValorCuota()) : "$0.00")));
                table.addCell(new Cell().add(new Paragraph(cuota.getInteres() != null ? 
                    String.format("$%,.2f", cuota.getInteres()) : "$0.00")));
                table.addCell(new Cell().add(new Paragraph(cuota.getCapital() != null ? 
                    String.format("$%,.2f", cuota.getCapital()) : "$0.00")));
                table.addCell(new Cell().add(new Paragraph(cuota.getSaldoPendiente() != null ? 
                    String.format("$%,.2f", cuota.getSaldoPendiente()) : "$0.00")));
            }
            
            // Calcular totales
            BigDecimal totalValorCuota = BigDecimal.ZERO;
            BigDecimal totalInteres = BigDecimal.ZERO;
            BigDecimal totalCapital = BigDecimal.ZERO;
            
            for (CuotaAmortizacion cuota : currentCuotas) {
                totalValorCuota = totalValorCuota.add(cuota.getValorCuota() != null ? cuota.getValorCuota() : BigDecimal.ZERO);
                totalInteres = totalInteres.add(cuota.getInteres() != null ? cuota.getInteres() : BigDecimal.ZERO);
                totalCapital = totalCapital.add(cuota.getCapital() != null ? cuota.getCapital() : BigDecimal.ZERO);
            }
            
            // Fila de totales
            table.addCell(new Cell().add(new Paragraph("TOTAL").setBold()));
            table.addCell(new Cell().add(new Paragraph(String.format("$%,.2f", totalValorCuota)).setBold()));
            table.addCell(new Cell().add(new Paragraph(String.format("$%,.2f", totalInteres)).setBold()));
            table.addCell(new Cell().add(new Paragraph(String.format("$%,.2f", totalCapital)).setBold()));
            table.addCell(new Cell().add(new Paragraph("-")));
            
            document.add(table);
            
            document.close();
            
            JOptionPane.showMessageDialog(SwingUtilities.getWindowAncestor(this), 
                "PDF generado exitosamente: " + fileToSave.getAbsolutePath(), 
                "Éxito", JOptionPane.INFORMATION_MESSAGE);
            
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(SwingUtilities.getWindowAncestor(this), 
                "Error al generar PDF: " + ex.getMessage(), 
                "Error", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_btn_printActionPerformed
    
    private void loadTablaAmortizacion(String numFactura) {
        try {
            // 1. Obtener detalles de la factura
            DetalleFacturaViewModel factura = facturaService.obtenerFacturaPorNumFactura(numFactura);
            if (factura == null) {
                JOptionPane.showMessageDialog(SwingUtilities.getWindowAncestor(this), 
                    "La factura no existe.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            // 2. Validar que sea una factura a crédito
            if (factura.getTipoPago() == null || !factura.getTipoPago().equalsIgnoreCase("C")) {
                JOptionPane.showMessageDialog(SwingUtilities.getWindowAncestor(this), 
                    "Esta factura no tiene tabla de amortización disponible.\nSolo las facturas a crédito tienen amortización.", 
                    "No Aplica", JOptionPane.INFORMATION_MESSAGE);
                return;
            }
            
            // 3. Obtener tabla de amortización desde el API REST
            List<CuotaAmortizacion> cuotas = facturaService.obtenerAmortizacion(numFactura);
            
            if (cuotas == null || cuotas.isEmpty()) {
                JOptionPane.showMessageDialog(SwingUtilities.getWindowAncestor(this), 
                    "No hay cuotas de amortización disponibles para esta factura.", 
                    "Sin Datos", JOptionPane.WARNING_MESSAGE);
                return;
            }
            
            // 4. Poblar información de la factura en los labels
            NumFactura1.setText(factura.getNumFactura());
            lblNumFac2.setText(factura.getNumFactura());
            lblMontoTotal.setText(String.format("$%,.2f", factura.getTotal()));
            
            // Obtener el plazo del número de cuotas
            int plazo = cuotas.size();
            lblPlazo.setText(plazo + " meses");
            lblTasaAnual.setText("15%"); // Tasa fija del sistema
            
            // La cuota fija viene en los datos del API
            BigDecimal cuotaFija = cuotas.get(0).getValorCuota();
            lblCuotaFija.setText(String.format("$%,.2f", cuotaFija));
            
            // 5. Poblar tabla con los datos recibidos del API
            DefaultTableModel model = new DefaultTableModel();
            model.addColumn("N° Cuota");
            model.addColumn("Valor Cuota");
            model.addColumn("Interés");
            model.addColumn("Capital");
            model.addColumn("Saldo Pendiente");
            ((javax.swing.JTable) modernTable1.getViewport().getView()).setModel(model);
            
            // 6. Agregar cada cuota a la tabla
            for (CuotaAmortizacion cuota : cuotas) {
                model.addRow(new Object[]{
                    cuota.getNumeroCuota(),
                    String.format("$%,.2f", cuota.getValorCuota() != null ? cuota.getValorCuota() : BigDecimal.ZERO),
                    String.format("$%,.2f", cuota.getInteres() != null ? cuota.getInteres() : BigDecimal.ZERO),
                    String.format("$%,.2f", cuota.getCapital() != null ? cuota.getCapital() : BigDecimal.ZERO),
                    String.format("$%,.2f", cuota.getSaldoPendiente() != null ? cuota.getSaldoPendiente() : BigDecimal.ZERO)
                });
            }
            
            // 7. Guardar datos para el PDF
            currentFactura = factura;
            currentCuotas = cuotas;
            
        } catch (org.springframework.web.client.HttpClientErrorException.NotFound ex) {
            JOptionPane.showMessageDialog(SwingUtilities.getWindowAncestor(this), 
                "La factura no existe o no tiene tabla de amortización.", 
                "No Encontrada", JOptionPane.ERROR_MESSAGE);
        } catch (org.springframework.web.client.ResourceAccessException ex) {
            JOptionPane.showMessageDialog(SwingUtilities.getWindowAncestor(this), 
                "No se pudo conectar con el servidor. Verifique su conexión.", 
                "Error de Conexión", JOptionPane.ERROR_MESSAGE);
        } catch (org.springframework.web.client.HttpServerErrorException ex) {
            JOptionPane.showMessageDialog(SwingUtilities.getWindowAncestor(this), 
                "Error al obtener la tabla de amortización. Intente más tarde.", 
                "Error del Servidor", JOptionPane.ERROR_MESSAGE);
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
                "Error al cargar tabla de amortización: " + errorMsg, 
                "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    

    


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel NumFactura1;
    private ec.edu.monster.vista.ModernButton btn_Back;
    private ec.edu.monster.vista.ModernButton btn_print;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JLabel lblCuotaFija;
    private javax.swing.JLabel lblMontoTotal;
    private javax.swing.JLabel lblNumFac2;
    private javax.swing.JLabel lblPlazo;
    private javax.swing.JLabel lblTasaAnual;
    private javax.swing.JLabel lblTitle;
    private javax.swing.JLabel lblTitle2;
    private javax.swing.JLabel lblTitle3;
    private ec.edu.monster.vista.ModernPanel modernPanel1;
    private ec.edu.monster.vista.ModernPanel modernPanel2;
    private ec.edu.monster.vista.ModernTable modernTable1;
    // End of variables declaration//GEN-END:variables
}
