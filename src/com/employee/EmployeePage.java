package com.employee;

import com.admin.Login;
import com.formdev.flatlaf.intellijthemes.FlatLightFlatIJTheme;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import common.OpenPdf;
import java.io.FileOutputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.UIManager;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

public class EmployeePage extends javax.swing.JFrame {

    String Prid;
    Integer newQty;
    Double Uprice, ProdTot = 0.0, Total = 0.0, GrdTotal = 0.0;
    Integer AvailQty, BillId;

    /**
     * Creates new form EmployeePage
     */
    public EmployeePage() {
        initComponents();
        setTitle("A-Mart System");
        ProductTable();
        BillId = Integer.parseInt(EmployeePage.getId());
        BillIDlbl.setText("Bill ID: " + BillId);
    }

    public void ProductTable() {
        DefaultTableModel model = (DefaultTableModel) ProductTable.getModel();
        DefaultTableCellRenderer rd = new DefaultTableCellRenderer();
        rd.setHorizontalAlignment(JLabel.CENTER);
        for (int i = 0; i < 3; i++) {
            ProductTable.getColumnModel().getColumn(i).setCellRenderer(rd);
        }
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/supermarketdb", "root", "root");
            String sql = "select * from addproduct";
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                String id = rs.getString(2);
                String name = rs.getString(3);
                String category = rs.getString(4);
                String qty = rs.getString(5);
                String price = rs.getString(7);
                model.addRow(new Object[]{id, name, category, qty, price});
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void ProductSearch() {
        DefaultTableModel model = (DefaultTableModel) ProductTable.getModel();
        model.setRowCount(0);
        try {
            String ch = Searchtxt.getText();
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/supermarketdb", "root", "root");
            String sql = "select * from addproduct where Category like ? or ProductName like ?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, "%" + ch + "%");
            ps.setString(2, "%" + ch + "%");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                String id = rs.getString(2);
                String name = rs.getString(3);
                String category = rs.getString(4);
                String qty = rs.getString(5);
                String price = rs.getString(7);
                model.addRow(new Object[]{id, name, category, qty, price});
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void Update() {
        for (int j = 0; j < BillTable.getRowCount(); j++) {
            Integer q = Integer.parseInt(BillTable.getValueAt(j, 2).toString());
            newQty = AvailQty - q;
            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
                Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/supermarketdb", "root", "root");
                String sql = "update addproduct set Quantity=? where ProductID=?";
                PreparedStatement ps = con.prepareStatement(sql);
                ps.setString(1, newQty.toString());
                ps.setString(2, Prid);
                int i = ps.executeUpdate();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static String getId() {
        int id = 1;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/supermarketdb", "root", "root");
            String sql = "select max(id) from purchasestatus";
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                id = rs.getInt(1);
                id = id + 1;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return String.valueOf(id);
    }

    public boolean Validation() {
        String CustomerName = Customernametxt.getText();
        String PhoneNo = Phonenotxt.getText();
        String productName = Qtytxt.getText();
        String Qty = Qtytxt.getText();
        if (productName.isEmpty() || CustomerName.isEmpty() || PhoneNo.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Missing Information..");
            return false;
        } else if (Qty.equals("No Select")) {
            JOptionPane.showMessageDialog(this, "Please select the quantity..");
            return false;
        } else if (AvailQty <= Integer.valueOf(Qtytxt.getText())) {
            JOptionPane.showMessageDialog(this, "Not Enough In Stock");
            return false;
        } else {
            Pattern p_name = Pattern.compile("[A-Za-z\\s]+");
            Matcher m_name = p_name.matcher(CustomerName);
            if (!m_name.matches()) {
                JOptionPane.showMessageDialog(this, "Number or Special Character not allowed..");
                return false;
            } else {
                Pattern p_phoneNo = Pattern.compile("[987][0-9]{9}");
                Matcher m_phoneNo = p_phoneNo.matcher(PhoneNo);
                if (!m_phoneNo.matches()) {
                    JOptionPane.showMessageDialog(this, "Phone No must be 10 digit..");
                    return false;
                } else {
                    Pattern p_qty = Pattern.compile("[0-9]");
                    Matcher m_qty = p_qty.matcher(Qty);
                    if (!m_qty.matches()) {
                        JOptionPane.showMessageDialog(this, "quantity must be in numbers");
                        return false;
                    } else {
                        return true;
                    }
                }
            }
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        LogoutBtn = new rojeru_san.complementos.RSButtonHover();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        BillIDlbl = new javax.swing.JLabel();
        Customernametxt = new app.bolivia.swing.JCTextField();
        jLabel8 = new javax.swing.JLabel();
        Phonenotxt = new app.bolivia.swing.JCTextField();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        addToBillBtn = new rojeru_san.complementos.RSButtonHover();
        ClearBtn = new rojeru_san.complementos.RSButtonHover();
        jLabel5 = new javax.swing.JLabel();
        Searchtxt = new app.bolivia.swing.JCTextField();
        SearchBtn = new rojeru_san.complementos.RSButtonHover();
        jScrollPane1 = new javax.swing.JScrollPane();
        ProductTable = new javax.swing.JTable();
        Grdtotallbl = new javax.swing.JLabel();
        PrintBtn = new rojeru_san.complementos.RSButtonHover();
        Qtytxt = new app.bolivia.swing.JCTextField();
        unittxt = new javax.swing.JComboBox<>();
        Producttxt = new app.bolivia.swing.JCTextField();
        jScrollPane3 = new javax.swing.JScrollPane();
        BillTable = new javax.swing.JTable();
        jLabel11 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel1.setBackground(new java.awt.Color(51, 51, 255));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/usericon.png"))); // NOI18N
        jLabel1.setText("Cashier");
        jPanel1.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(1320, 0, 110, 50));

        LogoutBtn.setBackground(new java.awt.Color(255, 51, 51));
        LogoutBtn.setText("Logout");
        LogoutBtn.setColorHover(new java.awt.Color(255, 0, 0));
        LogoutBtn.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        LogoutBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                LogoutBtnActionPerformed(evt);
            }
        });
        jPanel1.add(LogoutBtn, new org.netbeans.lib.awtextra.AbsoluteConstraints(1440, 0, 100, 50));

        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jPanel1.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 0, 40, 40));

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/basketicon.png"))); // NOI18N
        jLabel3.setText("A-Mart System");
        jPanel1.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 0, 200, 50));

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1540, 50));

        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel4.setText("BILLING POINT");
        jPanel2.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(680, 10, -1, 30));

        BillIDlbl.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N
        BillIDlbl.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jPanel2.add(BillIDlbl, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 20, 190, 40));

        Customernametxt.setBackground(new java.awt.Color(240, 240, 240));
        Customernametxt.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(0, 0, 0)));
        Customernametxt.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        Customernametxt.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        Customernametxt.setPlaceholder("Enter  customer name..");
        jPanel2.add(Customernametxt, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 70, 240, 40));

        jLabel8.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N
        jLabel8.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel8.setText("Phone No:");
        jPanel2.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 140, -1, 40));

        Phonenotxt.setBackground(new java.awt.Color(240, 240, 240));
        Phonenotxt.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(0, 0, 0)));
        Phonenotxt.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        Phonenotxt.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        Phonenotxt.setPlaceholder("Enter phone no..");
        jPanel2.add(Phonenotxt, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 130, 240, 40));

        jLabel9.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N
        jLabel9.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel9.setText("Product Name:");
        jPanel2.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 200, -1, 40));

        jLabel10.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N
        jLabel10.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel10.setText("Quantity:");
        jPanel2.add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 260, -1, 50));

        addToBillBtn.setText("ADD TO BILL");
        addToBillBtn.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        addToBillBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addToBillBtnActionPerformed(evt);
            }
        });
        jPanel2.add(addToBillBtn, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 350, 180, -1));

        ClearBtn.setText("CLEAR");
        ClearBtn.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        ClearBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ClearBtnActionPerformed(evt);
            }
        });
        jPanel2.add(ClearBtn, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 350, 140, -1));

        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/billing final.png"))); // NOI18N
        jPanel2.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 430, 390, 310));

        Searchtxt.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        Searchtxt.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        Searchtxt.setPlaceholder("search by id,name");
        jPanel2.add(Searchtxt, new org.netbeans.lib.awtextra.AbsoluteConstraints(540, 140, 210, 40));

        SearchBtn.setText("SEARCH");
        SearchBtn.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        SearchBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SearchBtnActionPerformed(evt);
            }
        });
        jPanel2.add(SearchBtn, new org.netbeans.lib.awtextra.AbsoluteConstraints(780, 140, 110, 40));

        jScrollPane1.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        ProductTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Product ID", "Product Name", "Category", "Quantity", "Price"
            }
        ));
        ProductTable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                ProductTableMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(ProductTable);

        jPanel2.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(540, 210, 350, 230));

        Grdtotallbl.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        Grdtotallbl.setText("Rs");
        jPanel2.add(Grdtotallbl, new org.netbeans.lib.awtextra.AbsoluteConstraints(1170, 510, 120, 30));

        PrintBtn.setText("PRINT");
        PrintBtn.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        PrintBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                PrintBtnActionPerformed(evt);
            }
        });
        jPanel2.add(PrintBtn, new org.netbeans.lib.awtextra.AbsoluteConstraints(1150, 580, 130, 30));

        Qtytxt.setBackground(new java.awt.Color(240, 240, 240));
        Qtytxt.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(0, 0, 0)));
        Qtytxt.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        Qtytxt.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jPanel2.add(Qtytxt, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 260, 100, 40));

        unittxt.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        unittxt.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "No Select", "KG", "L", "Dozen", "Piece" }));
        jPanel2.add(unittxt, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 260, 110, 40));

        Producttxt.setEditable(false);
        Producttxt.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(0, 0, 0)));
        Producttxt.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        Producttxt.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        Producttxt.setPlaceholder("Enter product name..");
        jPanel2.add(Producttxt, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 190, 240, 40));

        BillTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Name", "Price", "Quantity", "Unit", "Total"
            }
        ));
        BillTable.setRowHeight(30);
        BillTable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                BillTableMouseClicked(evt);
            }
        });
        jScrollPane3.setViewportView(BillTable);

        jPanel2.add(jScrollPane3, new org.netbeans.lib.awtextra.AbsoluteConstraints(920, 130, 590, 360));

        jLabel11.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N
        jLabel11.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel11.setText("Name:");
        jPanel2.add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 80, 80, 40));

        getContentPane().add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 50, 1540, 740));

        setSize(new java.awt.Dimension(1555, 827));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void LogoutBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_LogoutBtnActionPerformed
        int response = JOptionPane.showConfirmDialog(this, "Do you want to Logout?", "Confirm", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
        if (response == JOptionPane.YES_OPTION) {
            setVisible(false);
            new Login().setVisible(true);
        }
    }//GEN-LAST:event_LogoutBtnActionPerformed

    private void ProductTableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_ProductTableMouseClicked
        DefaultTableModel model = (DefaultTableModel) ProductTable.getModel();
        int myIndex = ProductTable.getSelectedRow();
        Prid = model.getValueAt(myIndex, 0).toString();
        AvailQty = Integer.valueOf(model.getValueAt(myIndex, 3).toString());

        Producttxt.setText(model.getValueAt(myIndex, 1).toString());
        Uprice = Double.valueOf(model.getValueAt(myIndex, 4).toString());
    }//GEN-LAST:event_ProductTableMouseClicked

    private void PrintBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_PrintBtnActionPerformed
        DefaultTableModel model = (DefaultTableModel) ProductTable.getModel();
        String customerName = Customernametxt.getText();
        String phoneNo = Phonenotxt.getText();
        SimpleDateFormat dFormat = new SimpleDateFormat("dd-MM-yyyy");
        SimpleDateFormat dFormat2 = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        Date date = new Date();
        String todayDate = dFormat.format(date);
        String time = dFormat2.format(date);
        String path = "C:\\Users\\amang\\OneDrive\\Documents\\NetBeansProjects\\A-MartSystem\\src\\BillPdf\\";
        com.itextpdf.text.Document doc = new com.itextpdf.text.Document();
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/supermarketdb", "root", "root");
            String sql = "insert into purchasestatus(Bill_ID,CustomerName,PhoneNo,TotalPrice,Date) values(?,?,?,?,?)";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, BillId);
            ps.setString(2, customerName);
            ps.setString(3, phoneNo);
            ps.setDouble(4, GrdTotal);
            ps.setString(5, todayDate);
            int i = ps.executeUpdate();

            //create document
            PdfWriter.getInstance(doc, new FileOutputStream(path + "" + BillId + ".pdf"));
            doc.open();
            Paragraph martName = new Paragraph("                                                                    A-Mart System\n");
            doc.add(martName);
            Paragraph starLine = new Paragraph("*************************************************************************************************************\n");
            doc.add(starLine);
            Paragraph para = new Paragraph("Bill ID: " + BillId + "                                                                        Date: " + time + "\nCustomer Name: " + customerName + "\nPhone No: " + phoneNo + "\n");
            doc.add(para);
            doc.add(starLine);
            PdfPTable tb1 = new PdfPTable(5);
            tb1.addCell("Product Name");
            tb1.addCell("Price");
            tb1.addCell("Quantity");
            tb1.addCell("Unit");
            tb1.addCell("Total");
            for (int j = 0; j < BillTable.getRowCount(); j++) {
                String n = BillTable.getValueAt(j, 0).toString();
                String p = BillTable.getValueAt(j, 1).toString();
                String q = BillTable.getValueAt(j, 2).toString();
                String u = BillTable.getValueAt(j, 3).toString();
                String t = BillTable.getValueAt(j, 4).toString();
                tb1.addCell(n);
                tb1.addCell(p);
                tb1.addCell(q);
                tb1.addCell(u);
                tb1.addCell(t);
            }
            doc.add(tb1);
            doc.add(starLine);
            Paragraph FinalTotal = new Paragraph("                                                             Amount to be paid:- " + GrdTotal + "\n\n");
            doc.add(FinalTotal);
            Paragraph thankyouMsg = new Paragraph("                                                            Thank You,Please visit Again..");
            doc.add(thankyouMsg);
            Update();
            model.setRowCount(0);
            ProductTable();
            //Open PDF
            OpenPdf.openById(String.valueOf(BillId));
        } catch (Exception e) {
            e.printStackTrace();
        }
        doc.close();
    }//GEN-LAST:event_PrintBtnActionPerformed

    private void SearchBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SearchBtnActionPerformed
        ProductSearch();
    }//GEN-LAST:event_SearchBtnActionPerformed

    private void addToBillBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addToBillBtnActionPerformed
        if (Validation() == true) {
            DefaultTableModel model = (DefaultTableModel) BillTable.getModel();
            DefaultTableCellRenderer rd = new DefaultTableCellRenderer();
            rd.setHorizontalAlignment(JLabel.CENTER);
            for (int i = 0; i < 5; i++) {
                BillTable.getColumnModel().getColumn(i).setCellRenderer(rd);
            }

            String CustomerName = Customernametxt.getText();
            String PhoneNo = Phonenotxt.getText();
            String productName = Producttxt.getText();
            String Qty = Qtytxt.getText();
            String unit = unittxt.getSelectedItem().toString();
            ProdTot = Uprice * Double.valueOf(Qtytxt.getText());
            Total = Total + ProdTot;
            GrdTotal = Total+(Total*12/100);
            model.addRow(new Object[]{productName, Uprice, Qty, unit, ProdTot});
            Grdtotallbl.setText("Rs " + GrdTotal);
        }
    }//GEN-LAST:event_addToBillBtnActionPerformed

    private void ClearBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ClearBtnActionPerformed
        Customernametxt.setText("");
        Phonenotxt.setText("");
        Producttxt.setText("");
        Qtytxt.setText("");
        unittxt.setSelectedIndex(0);
        Grdtotallbl.setText("Rs ");
        DefaultTableModel model = (DefaultTableModel) BillTable.getModel();
        model.setRowCount(0);
    }//GEN-LAST:event_ClearBtnActionPerformed

    private void BillTableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_BillTableMouseClicked
        int index = BillTable.getSelectedRow();
        int a = JOptionPane.showConfirmDialog(null, "Do you want to delete this product?", "Select", JOptionPane.YES_NO_OPTION);
        if (a == 0) {
            DefaultTableModel model = (DefaultTableModel) BillTable.getModel();
            String total = model.getValueAt(index, 4).toString();
            GrdTotal = GrdTotal - Double.parseDouble(total);
            Grdtotallbl.setText("Rs " + GrdTotal);
            ((DefaultTableModel) BillTable.getModel()).removeRow(index);
        }
    }//GEN-LAST:event_BillTableMouseClicked

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(EmployeePage.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(EmployeePage.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(EmployeePage.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(EmployeePage.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        try {
            UIManager.setLookAndFeel(new FlatLightFlatIJTheme());
        } catch (Exception e) {
            e.printStackTrace();
        }

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new EmployeePage().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel BillIDlbl;
    private javax.swing.JTable BillTable;
    private rojeru_san.complementos.RSButtonHover ClearBtn;
    private app.bolivia.swing.JCTextField Customernametxt;
    private javax.swing.JLabel Grdtotallbl;
    private rojeru_san.complementos.RSButtonHover LogoutBtn;
    private app.bolivia.swing.JCTextField Phonenotxt;
    private rojeru_san.complementos.RSButtonHover PrintBtn;
    private javax.swing.JTable ProductTable;
    private app.bolivia.swing.JCTextField Producttxt;
    private app.bolivia.swing.JCTextField Qtytxt;
    private rojeru_san.complementos.RSButtonHover SearchBtn;
    private app.bolivia.swing.JCTextField Searchtxt;
    private rojeru_san.complementos.RSButtonHover addToBillBtn;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JComboBox<String> unittxt;
    // End of variables declaration//GEN-END:variables
}
