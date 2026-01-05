package ec.edu.monster.vista;

import ec.edu.monster.services.FacturaService;
import ec.edu.monster.models.DetalleFacturaViewModel;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import javax.swing.table.DefaultTableModel;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import java.util.List;
import java.text.SimpleDateFormat;
import java.nio.charset.StandardCharsets;

/**
 *
 * @author Dome
 */
public class SearchInvoice extends javax.swing.JPanel {

    private FacturaService facturaService;

    public SearchInvoice() {
        RestTemplate restTemplate = new RestTemplate();
        configureRestTemplate(restTemplate);
        this.facturaService = new FacturaService(restTemplate, "http://localhost:8081/api");
        initComponents();
        loadInvoices();
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

@SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        lblTitle = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        TableInvoices = new ec.edu.monster.vista.ModernTable();

        setBackground(new java.awt.Color(255, 255, 255));
        setMinimumSize(new java.awt.Dimension(820, 590));
        setPreferredSize(new java.awt.Dimension(820, 590));
        setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        lblTitle.setFont(new java.awt.Font("Britannic Bold", 0, 28)); // NOI18N
        lblTitle.setText("Consultar Facturas");

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/FACTURACELESTE.png"))); // NOI18N

        TableInvoices.setBackground(new java.awt.Color(255, 255, 255));
        TableInvoices.setContainerBackground(new java.awt.Color(255, 255, 255));
        TableInvoices.setEvenRowColor(new java.awt.Color(245, 245, 245));
        TableInvoices.setHeaderBackground(new java.awt.Color(0, 0, 0));

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(lblTitle))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(12, 12, 12)
                        .addComponent(TableInvoices, javax.swing.GroupLayout.PREFERRED_SIZE, 794, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(14, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel1)
                    .addComponent(lblTitle, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(TableInvoices, javax.swing.GroupLayout.PREFERRED_SIZE, 579, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(53, Short.MAX_VALUE))
        );

        add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 820, 690));

        getAccessibleContext().setAccessibleName("");
        getAccessibleContext().setAccessibleDescription("");
    }// </editor-fold>//GEN-END:initComponents
    
    private void loadInvoices() {
        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("Num. Factura");
        model.addColumn("Fecha");
        model.addColumn("Total");
        model.addColumn("Tipo Pago");
        model.addColumn("Ver Detalles");
        
        ((javax.swing.JTable) TableInvoices.getViewport().getView()).setModel(model);
        
        try {
            List<DetalleFacturaViewModel> facturas = facturaService.listarFacturas();
            if (facturas != null) {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                for (DetalleFacturaViewModel f : facturas) {
                    String fecha = f.getFecha() != null ? sdf.format(java.sql.Date.valueOf(f.getFecha())) : "N/A";
                    String total = String.format("$%,.2f", f.getTotal());
                    String tipoPago = "E".equals(f.getTipoPago()) ? "Efectivo" : "Crédito";
                    model.addRow(new Object[]{f.getNumFactura(), fecha, total, tipoPago, "Ver Detalles"});
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
            JOptionPane.showMessageDialog(SwingUtilities.getWindowAncestor(this), "Error al cargar facturas: " + errorMsg, "Error", JOptionPane.ERROR_MESSAGE);
        }
        
        // Agregar listener para clics en "Ver Detalles"
        javax.swing.JTable table = (javax.swing.JTable) TableInvoices.getViewport().getView();
        table.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                int row = table.rowAtPoint(evt.getPoint());
                int col = table.columnAtPoint(evt.getPoint());
                if (col == 4 && row >= 0) { // Columna "Ver Detalles"
                    String numFactura = (String) table.getValueAt(row, 0);
                    // Navegar a InvoiceDetail
                    java.awt.Window parentWindow = javax.swing.SwingUtilities.getWindowAncestor(SearchInvoice.this);
                    if (parentWindow instanceof Home) {
                        Home home = (Home) parentWindow;
                        InvoiceDetail panelDetalle = new InvoiceDetail(home, numFactura);
                        home.showPanel(panelDetalle);
                    }
                }
            }
        });
    }
    

    


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private ec.edu.monster.vista.ModernTable TableInvoices;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JLabel lblTitle;
    // End of variables declaration//GEN-END:variables
}
