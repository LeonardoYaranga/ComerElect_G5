package ec.edu.monster.vista;

import ec.edu.monster.services.FacturaService;
import ec.edu.monster.models.DetalleFacturaViewModel;
import ec.edu.monster.models.DetalleProducto;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import javax.swing.table.DefaultTableModel;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;

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
public class InvoiceDetail extends javax.swing.JPanel {
    private Home parentFrame;
    private FacturaService facturaService;
    private String currentNumFactura;
    private DetalleFacturaViewModel currentFactura;
    
    public InvoiceDetail(Home parent) {
        RestTemplate restTemplate = new RestTemplate();
        configureRestTemplate(restTemplate);
        this.facturaService = new FacturaService(restTemplate, "http://localhost:8081/api");
        initComponents();
        this.parentFrame = parent;
    }
    
    public InvoiceDetail(Home parent, String numFactura) {
        RestTemplate restTemplate = new RestTemplate();
        configureRestTemplate(restTemplate);
        this.facturaService = new FacturaService(restTemplate, "http://localhost:8081/api");
        initComponents();
        this.parentFrame = parent;
        loadFactura(numFactura);
    }
    
    private void configureRestTemplate(RestTemplate restTemplate) {
        java.util.List<HttpMessageConverter<?>> converters = restTemplate.getMessageConverters();
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
        jLabel1 = new javax.swing.JLabel();
        lblTitle = new javax.swing.JLabel();
        btn_Back = new ec.edu.monster.vista.ModernButton();
        jPanel2 = new javax.swing.JPanel();
        lblTitle2 = new javax.swing.JLabel();
        NumFactura = new javax.swing.JLabel();
        modernPanel1 = new ec.edu.monster.vista.ModernPanel();
        TableProductos = new ec.edu.monster.vista.ModernTable();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        lblNumFac2 = new javax.swing.JLabel();
        lblFecha = new javax.swing.JLabel();
        lblTipoCredito = new javax.swing.JLabel();
        lblCodCredito = new javax.swing.JLabel();
        lblCedula = new javax.swing.JLabel();
        lblNombre = new javax.swing.JLabel();
        lblTitle3 = new javax.swing.JLabel();
        TableValoresFinales = new ec.edu.monster.vista.ModernTable();
        btn_print = new ec.edu.monster.vista.ModernButton();
        btn_verTabla = new ec.edu.monster.vista.ModernButton();

        setBackground(new java.awt.Color(255, 255, 255));
        setMinimumSize(new java.awt.Dimension(820, 590));
        setPreferredSize(new java.awt.Dimension(820, 590));
        setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/FACTURACELESTE.png"))); // NOI18N

        lblTitle.setFont(new java.awt.Font("Britannic Bold", 0, 28)); // NOI18N
        lblTitle.setText("Detalle de Factura");

        btn_Back.setBackgroundColor(new java.awt.Color(153, 153, 153));
        btn_Back.setBorderColor(new java.awt.Color(153, 153, 153));
        btn_Back.setBorderRadius(0);
        btn_Back.setBorderThickness(0);
        btn_Back.setHoverColor(new java.awt.Color(102, 102, 102));
        btn_Back.setPressedColor(new java.awt.Color(102, 102, 102));
        btn_Back.setText("<  Volver");
        btn_Back.setActionCommand(" Volver");
        btn_Back.setFont(new java.awt.Font("Roboto", 1, 16)); // NOI18N
        btn_Back.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_BackActionPerformed(evt);
            }
        });

        jPanel2.setBackground(new java.awt.Color(13, 110, 253));

        lblTitle2.setFont(new java.awt.Font("Britannic Bold", 0, 18)); // NOI18N
        lblTitle2.setForeground(new java.awt.Color(255, 255, 255));
        lblTitle2.setText("Factura N°");

        NumFactura.setFont(new java.awt.Font("Britannic Bold", 0, 18)); // NOI18N
        NumFactura.setForeground(new java.awt.Color(255, 255, 255));
        NumFactura.setText("FAC-2025-0003");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblTitle2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(NumFactura, javax.swing.GroupLayout.PREFERRED_SIZE, 161, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lblTitle2, javax.swing.GroupLayout.DEFAULT_SIZE, 34, Short.MAX_VALUE)
            .addComponent(NumFactura, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        modernPanel1.setBorderRadius(10);

        TableProductos.setHeaderBackground(new java.awt.Color(0, 0, 0));

        jLabel2.setFont(new java.awt.Font("Roboto", 1, 16)); // NOI18N
        jLabel2.setText("Información General");

        jLabel3.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        jLabel3.setText("Número de Factura:");

        jLabel4.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        jLabel4.setText("Fecha de Emisisón:");

        jLabel5.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        jLabel5.setText("Tipo de Pago:");

        jLabel6.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        jLabel6.setText("Código de Crédito:");

        jLabel7.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        jLabel7.setText("Cédula:");

        jLabel8.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        jLabel8.setText("Nombre:");

        jLabel10.setFont(new java.awt.Font("Roboto", 1, 16)); // NOI18N
        jLabel10.setText("Cliente");

        lblNumFac2.setFont(new java.awt.Font("Roboto", 0, 12)); // NOI18N
        lblNumFac2.setText("FAC");

        lblFecha.setFont(new java.awt.Font("Roboto", 0, 12)); // NOI18N
        lblFecha.setText("fecha");

        lblTipoCredito.setFont(new java.awt.Font("Roboto", 0, 12)); // NOI18N
        lblTipoCredito.setText("Cre");

        lblCodCredito.setFont(new java.awt.Font("Roboto", 0, 12)); // NOI18N
        lblCodCredito.setText("codigo");

        lblCedula.setFont(new java.awt.Font("Roboto", 0, 12)); // NOI18N
        lblCedula.setText("Cedula");

        lblNombre.setFont(new java.awt.Font("Roboto", 0, 12)); // NOI18N
        lblNombre.setText("name");

        lblTitle3.setFont(new java.awt.Font("Britannic Bold", 0, 18)); // NOI18N
        lblTitle3.setText("Productos");

        TableValoresFinales.setHeaderBackground(new java.awt.Color(255, 255, 255));
        TableValoresFinales.setHeaderForeground(new java.awt.Color(0, 0, 0));
        TableValoresFinales.setHoverRowColor(new java.awt.Color(255, 255, 255));

        javax.swing.GroupLayout modernPanel1Layout = new javax.swing.GroupLayout(modernPanel1);
        modernPanel1.setLayout(modernPanel1Layout);
        modernPanel1Layout.setHorizontalGroup(
            modernPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(modernPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(modernPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(TableProductos, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(modernPanel1Layout.createSequentialGroup()
                        .addGroup(modernPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(modernPanel1Layout.createSequentialGroup()
                                .addGroup(modernPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(modernPanel1Layout.createSequentialGroup()
                                        .addComponent(jLabel4)
                                        .addGap(18, 18, 18)
                                        .addComponent(lblFecha, javax.swing.GroupLayout.PREFERRED_SIZE, 161, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(modernPanel1Layout.createSequentialGroup()
                                        .addComponent(jLabel5)
                                        .addGap(18, 18, 18)
                                        .addComponent(lblTipoCredito, javax.swing.GroupLayout.PREFERRED_SIZE, 161, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(modernPanel1Layout.createSequentialGroup()
                                        .addComponent(jLabel6)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(lblCodCredito, javax.swing.GroupLayout.PREFERRED_SIZE, 161, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addComponent(jLabel2)
                                    .addGroup(modernPanel1Layout.createSequentialGroup()
                                        .addComponent(jLabel3)
                                        .addGap(18, 18, 18)
                                        .addComponent(lblNumFac2, javax.swing.GroupLayout.PREFERRED_SIZE, 161, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGap(48, 48, 48)
                                .addGroup(modernPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(modernPanel1Layout.createSequentialGroup()
                                        .addComponent(jLabel7)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(lblCedula, javax.swing.GroupLayout.PREFERRED_SIZE, 161, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(modernPanel1Layout.createSequentialGroup()
                                        .addComponent(jLabel8)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(lblNombre, javax.swing.GroupLayout.PREFERRED_SIZE, 161, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 161, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addComponent(lblTitle3, javax.swing.GroupLayout.PREFERRED_SIZE, 430, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 200, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, modernPanel1Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(TableValoresFinales, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        modernPanel1Layout.setVerticalGroup(
            modernPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(modernPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(modernPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jLabel10))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(modernPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(jLabel7)
                    .addComponent(lblNumFac2)
                    .addComponent(lblCedula))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(modernPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblNombre)
                    .addGroup(modernPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel4)
                        .addComponent(jLabel8)
                        .addComponent(lblFecha)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(modernPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(lblTipoCredito))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(modernPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(lblCodCredito))
                .addGap(26, 26, 26)
                .addComponent(lblTitle3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(TableProductos, javax.swing.GroupLayout.PREFERRED_SIZE, 184, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(TableValoresFinales, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(12, Short.MAX_VALUE))
        );

        btn_print.setBorderRadius(0);
        btn_print.setBorderThickness(0);
        btn_print.setHoverColor(new java.awt.Color(7, 81, 119));
        btn_print.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IMPRESORA.png"))); // NOI18N
        btn_print.setText("Imprimir");
        btn_print.setFont(new java.awt.Font("Roboto", 1, 16)); // NOI18N
        btn_print.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_printActionPerformed(evt);
            }
        });

        btn_verTabla.setBackgroundColor(new java.awt.Color(13, 202, 240));
        btn_verTabla.setBorderColor(new java.awt.Color(13, 202, 240));
        btn_verTabla.setBorderRadius(0);
        btn_verTabla.setBorderThickness(0);
        btn_verTabla.setHoverColor(new java.awt.Color(53, 169, 192));
        btn_verTabla.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Cuentas.png"))); // NOI18N
        btn_verTabla.setPressedColor(new java.awt.Color(53, 169, 192));
        btn_verTabla.setText("Ver Tabla de Amortización");
        btn_verTabla.setFont(new java.awt.Font("Roboto", 1, 16)); // NOI18N
        btn_verTabla.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_verTablaActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(lblTitle)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btn_Back, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btn_print, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(17, 17, 17))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(modernPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(272, 272, 272)
                .addComponent(btn_verTabla, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
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
                    .addComponent(lblTitle, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(modernPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btn_verTabla, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(47, Short.MAX_VALUE))
        );

        add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 820, 690));

        getAccessibleContext().setAccessibleName("");
        getAccessibleContext().setAccessibleDescription("");
    }// </editor-fold>//GEN-END:initComponents

    private void btn_BackActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_BackActionPerformed
        if (parentFrame != null) {
            // Llama a showPanel con una NUEVA instancia de CreateInvoice
            // ¡Recuerda pasarle 'parentFrame' a CreateInvoice también!
            parentFrame.showPanel(new CreateInvoice(parentFrame));
        }
    }//GEN-LAST:event_btn_BackActionPerformed

    private void btn_verTablaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_verTablaActionPerformed
        if (parentFrame != null && currentNumFactura != null) {
            // Llama a showPanel con una NUEVA instancia de TablaAmortizacion
            // ¡Asegúrate de pasarle 'parentFrame' a TablaAmortizacion!
            // (Y cualquier dato que necesite, como el 'idFactura')
            parentFrame.showPanel(new TablaAmortización(parentFrame, currentNumFactura));
        }
    }//GEN-LAST:event_btn_verTablaActionPerformed

    private void btn_printActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_printActionPerformed
        if (currentFactura == null) {
            JOptionPane.showMessageDialog(SwingUtilities.getWindowAncestor(this), "No hay factura cargada para imprimir.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Guardar PDF de Factura");
        fileChooser.setSelectedFile(new java.io.File("Factura_" + currentFactura.getNumFactura() + ".pdf"));
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
            Paragraph title = new Paragraph("Factura")
                    .setFontSize(20)
                    .setTextAlignment(TextAlignment.CENTER);
            document.add(title);
            
            // Información general
            document.add(new Paragraph("Número de Factura: " + currentFactura.getNumFactura()));
            document.add(new Paragraph("Fecha: " + (currentFactura.getFecha() != null ? currentFactura.getFecha() : "N/A")));
            document.add(new Paragraph("Tipo de Pago: " + ("E".equals(currentFactura.getTipoPago()) ? "Efectivo" : "Crédito")));
            if (currentFactura.getCodigoCredito() != null && !currentFactura.getCodigoCredito().isEmpty()) {
                document.add(new Paragraph("Código de Crédito: " + currentFactura.getCodigoCredito()));
            }
            
            // Información del cliente
            document.add(new Paragraph("Cliente:"));
            document.add(new Paragraph("Cédula: " + currentFactura.getClienteCedula()));
            document.add(new Paragraph("Nombre: " + currentFactura.getClienteNombre()));
            
            // Tabla de productos
            document.add(new Paragraph("\nProductos:").setFontSize(14));
            Table table = new Table(UnitValue.createPercentArray(new float[]{4, 1, 2, 2}));
            table.setWidth(UnitValue.createPercentValue(100));
            
            table.addHeaderCell(new Cell().add(new Paragraph("Producto")));
            table.addHeaderCell(new Cell().add(new Paragraph("Cantidad")));
            table.addHeaderCell(new Cell().add(new Paragraph("Precio Unit.")));
            table.addHeaderCell(new Cell().add(new Paragraph("Subtotal")));
            
            for (DetalleProducto p : currentFactura.getProductos()) {
                table.addCell(new Cell().add(new Paragraph(p.getNombre())));
                table.addCell(new Cell().add(new Paragraph(String.valueOf(p.getCantidad()))));
                table.addCell(new Cell().add(new Paragraph(String.format("$%,.2f", p.getPrecioUnitario()))));
                table.addCell(new Cell().add(new Paragraph(String.format("$%,.2f", p.getSubtotal()))));
            }
            
            document.add(table);
            
            // Totales
            document.add(new Paragraph("\nTotales:").setFontSize(14));
            BigDecimal subtotal = BigDecimal.ZERO;
            for (DetalleProducto p : currentFactura.getProductos()) {
                if (p.getSubtotal() != null) {
                    subtotal = subtotal.add(p.getSubtotal());
                }
            }
            BigDecimal iva = BigDecimal.ZERO;
            BigDecimal total = currentFactura.getTotal();
            
            document.add(new Paragraph("Subtotal: " + String.format("$%,.2f", subtotal)));
            document.add(new Paragraph("IVA (15%): " + String.format("$%,.2f", iva)));
            document.add(new Paragraph("Total: " + String.format("$%,.2f", total)).setFontSize(16));
            
            document.close();
            
            JOptionPane.showMessageDialog(SwingUtilities.getWindowAncestor(this), "PDF generado exitosamente: " + fileToSave.getAbsolutePath(), "Éxito", JOptionPane.INFORMATION_MESSAGE);
            
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(SwingUtilities.getWindowAncestor(this), "Error al generar PDF: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_btn_printActionPerformed
    
    private void loadFactura(String numFactura) {
        try {
            DetalleFacturaViewModel factura = facturaService.obtenerFacturaPorNumFactura(numFactura);
            if (factura == null) {
                JOptionPane.showMessageDialog(SwingUtilities.getWindowAncestor(this), "Factura no encontrada.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            // Poblar labels
            NumFactura.setText(factura.getNumFactura());
            lblNumFac2.setText(factura.getNumFactura());
            lblFecha.setText(factura.getFecha() != null ? new SimpleDateFormat("yyyy-MM-dd").format(java.sql.Date.valueOf(factura.getFecha())) : "N/A");
            lblTipoCredito.setText("E".equals(factura.getTipoPago()) ? "Efectivo" : "Crédito");
            lblCodCredito.setText(factura.getCodigoCredito() != null && !factura.getCodigoCredito().isEmpty() ? factura.getCodigoCredito() : "");
            lblCedula.setText(factura.getClienteCedula());
            lblNombre.setText(factura.getClienteNombre());
            
            // Guardar el número de factura actual
            currentNumFactura = factura.getNumFactura();
            currentFactura = factura;
            
            // Mostrar botón de tabla de amortización solo si es crédito
            btn_verTabla.setVisible("C".equals(factura.getTipoPago()));
            
            // Poblar tabla productos
            DefaultTableModel modelProductos = new DefaultTableModel();
            modelProductos.addColumn("Producto");
            modelProductos.addColumn("Cantidad");
            modelProductos.addColumn("Precio Unit.");
            modelProductos.addColumn("Subtotal");
            ((javax.swing.JTable) TableProductos.getViewport().getView()).setModel(modelProductos);
            
            for (DetalleProducto p : factura.getProductos()) {
                modelProductos.addRow(new Object[]{
                    p.getNombre(),
                    p.getCantidad(),
                    String.format("$%,.2f", p.getPrecioUnitario()),
                    String.format("$%,.2f", p.getSubtotal())
                });
            }
            
            // Poblar tabla valores finales
            DefaultTableModel modelValores = new DefaultTableModel();
            modelValores.addColumn("Concepto");
            modelValores.addColumn("Valor");
            ((javax.swing.JTable) TableValoresFinales.getViewport().getView()).setModel(modelValores);
            
            // Calcular subtotal sumando los subtotals de los productos
            BigDecimal subtotal = BigDecimal.ZERO;
            for (DetalleProducto p : factura.getProductos()) {
                if (p.getSubtotal() != null) {
                    subtotal = subtotal.add(p.getSubtotal());
                }
            }
            
            BigDecimal iva = BigDecimal.ZERO;
            BigDecimal total = factura.getTotal();
            
            modelValores.addRow(new Object[]{"Subtotal", String.format("$%,.2f", subtotal)});
            modelValores.addRow(new Object[]{"IVA (15%)", String.format("$%,.2f", iva)});
            modelValores.addRow(new Object[]{"Total", String.format("$%,.2f", total)});
            
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
            JOptionPane.showMessageDialog(SwingUtilities.getWindowAncestor(this), "Error al cargar factura: " + errorMsg, "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    

    


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel NumFactura;
    private ec.edu.monster.vista.ModernTable TableProductos;
    private ec.edu.monster.vista.ModernTable TableValoresFinales;
    private ec.edu.monster.vista.ModernButton btn_Back;
    private ec.edu.monster.vista.ModernButton btn_print;
    private ec.edu.monster.vista.ModernButton btn_verTabla;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JLabel lblCedula;
    private javax.swing.JLabel lblCodCredito;
    private javax.swing.JLabel lblFecha;
    private javax.swing.JLabel lblNombre;
    private javax.swing.JLabel lblNumFac2;
    private javax.swing.JLabel lblTipoCredito;
    private javax.swing.JLabel lblTitle;
    private javax.swing.JLabel lblTitle2;
    private javax.swing.JLabel lblTitle3;
    private ec.edu.monster.vista.ModernPanel modernPanel1;
    // End of variables declaration//GEN-END:variables
}
