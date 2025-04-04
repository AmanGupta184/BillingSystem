package com.admin;

import com.formdev.flatlaf.intellijthemes.FlatLightFlatIJTheme;
import common.OpenPdf;
import java.awt.HeadlessException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.UIManager;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableModel;

/**
 *
 * @author amang
 */
public class AdminPage extends javax.swing.JFrame {

    String FullName, Age, Gender, Email, PhoneNo, Address, Salary, Roles, Username, Password, RePassword;
    String pname, pcategory, pquantity, punit, pprice;

    /**
     * Creates new form AdminPage
     */
    public AdminPage() {
        initComponents();
        setTitle("A-Mart System");
        EmployeeTable();
        ProductTable();
        PurchaseTable();
        SimpleDateFormat dFormat = new SimpleDateFormat("dd-MM-yyyy");
        Date date = new Date();
        String todayDate = dFormat.format(date);
        datetxt.setText(todayDate);
        TableActionEvent event = new TableActionEvent() {
            @Override
            public void onEdit(int row) {
                try {
                    DefaultTableModel model = (DefaultTableModel) EmployeeTable.getModel();
                    String s = model.getValueAt(row, 0).toString();
                    Integer id = Integer.parseInt(s);
                    String fullName = model.getValueAt(row, 1).toString();
                    String age = model.getValueAt(row, 2).toString();
                    String gender = model.getValueAt(row, 3).toString();
                    String phoneNo = model.getValueAt(row, 4).toString();
                    String email = model.getValueAt(row, 5).toString();
                    String roles = model.getValueAt(row, 6).toString();
                    String salary = model.getValueAt(row, 7).toString();
                    String password = model.getValueAt(row, 8).toString();
                    Class.forName("com.mysql.cj.jdbc.Driver");
                    Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/supermarketdb", "root", "root");
                    String sql = "update addemployee set FullName=?,Age=?,Gender=?,PhoneNo=?,Email=?,Roles=?,Salary=?,Password=? where id=?";
                    PreparedStatement ps = con.prepareStatement(sql);
                    ps.setString(1, fullName);
                    ps.setString(2, age);
                    ps.setString(3, gender);
                    ps.setString(4, phoneNo);
                    ps.setString(5, email);
                    ps.setString(6, roles);
                    ps.setString(7, salary);
                    ps.setString(8, password);
                    ps.setInt(9, id);
                    int i = ps.executeUpdate();
                    if (i == 1) {
                        JOptionPane.showMessageDialog(rootPane, "Updated Successfully");
                        model.setRowCount(0);
                        EmployeeTable();
                    } else {
                        JOptionPane.showMessageDialog(rootPane, "Something went wrong..");
                    }
                } catch (HeadlessException | ClassNotFoundException | SQLException e) {
                    System.out.println(e.getMessage());
                }
            }
        };
        TableActionEvent event2 = new TableActionEvent() {
            @Override
            public void onEdit(int row) {
                try {
                    DefaultTableModel model = (DefaultTableModel) inventoryTable.getModel();
                    String productId = model.getValueAt(row, 0).toString();
                    String productName = model.getValueAt(row, 1).toString();
                    String category = model.getValueAt(row, 2).toString();
                    String quantity = model.getValueAt(row, 3).toString();
                    String unit = model.getValueAt(row, 4).toString();
                    String price = model.getValueAt(row, 5).toString();
                    Class.forName("com.mysql.cj.jdbc.Driver");
                    Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/supermarketdb", "root", "root");
                    String sql = "update addproduct set ProductName=?,Category=?,Quantity=?,Unit=?,Price=? where ProductID=?";
                    PreparedStatement ps = con.prepareStatement(sql);
                    ps.setString(1, productName);
                    ps.setString(2, category);
                    ps.setString(3, quantity);
                    ps.setString(4, unit);
                    ps.setString(5, price);
                    ps.setString(6, productId);
                    int i = ps.executeUpdate();
                    if (i == 1) {
                        JOptionPane.showMessageDialog(rootPane, "Updated Successfully");
                        model.setRowCount(0);
                        ProductTable();
                    } else {
                        JOptionPane.showMessageDialog(rootPane, "Something went wrong..");
                    }
                } catch (HeadlessException | ClassNotFoundException | SQLException e) {
                    System.out.println(e.getMessage());
                }
            }
        };
        EmployeeTable.getColumnModel().getColumn(9).setCellRenderer(new TableActionCellRender());
        EmployeeTable.getColumnModel().getColumn(9).setCellEditor(new TableActionCellEditor(event));
        inventoryTable.getColumnModel().getColumn(6).setCellRenderer(new TableActionCellRender());
        inventoryTable.getColumnModel().getColumn(6).setCellEditor(new TableActionCellEditor(event2));
    }

    public void EmployeeTable() {
        DefaultTableModel model = (DefaultTableModel) EmployeeTable.getModel();
        DefaultTableCellRenderer rd = new DefaultTableCellRenderer();
        JTableHeader hr = new JTableHeader();
        ((DefaultTableCellRenderer) hr.getDefaultRenderer()).setHorizontalAlignment(JLabel.CENTER);
        rd.setHorizontalAlignment(JLabel.CENTER);
        for (int i = 0; i < 10; i++) {
            EmployeeTable.getColumnModel().getColumn(i).setCellRenderer(rd);
        }
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/supermarketdb", "root", "root");
            String sql = "select * from addemployee";
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                String id = rs.getString(1);
                String name = rs.getString(2);
                String age = rs.getString(3);
                String gender = rs.getString(4);
                String phoneNo = rs.getString(5);
                String email = rs.getString(6);
                String roles = rs.getString(8);
                String salary = rs.getString(9);
                String password = rs.getString(10);
                model.addRow(new Object[]{id, name, age, gender, phoneNo, email, roles, salary, password});
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void EmployeeSearch() {
        DefaultTableModel model = (DefaultTableModel) EmployeeTable.getModel();
        model.setRowCount(0);
        try {
            String ch = txtEmployeeSearch.getText();
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/supermarketdb", "root", "root");
            String sql = "select * from addemployee where FullName like ? or Roles like ?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, "%" + ch + "%");
            ps.setString(2, "%" + ch + "%");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                String id = rs.getString(1);
                String name = rs.getString(2);
                String age = rs.getString(3);
                String gender = rs.getString(4);
                String phoneNo = rs.getString(5);
                String email = rs.getString(6);
                String roles = rs.getString(8);
                String salary = rs.getString(9);
                String password = rs.getString(10);
                model.addRow(new Object[]{id, name, age, gender, phoneNo, email, roles, salary, password});
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void ProductTable() {
        DefaultTableModel model = (DefaultTableModel) inventoryTable.getModel();
        DefaultTableCellRenderer rd = new DefaultTableCellRenderer();
        rd.setHorizontalAlignment(JLabel.CENTER);
        JTableHeader hr = new JTableHeader();
        ((DefaultTableCellRenderer) hr.getDefaultRenderer()).setHorizontalAlignment(JLabel.CENTER);
        for (int i = 0; i < 6; i++) {
            inventoryTable.getColumnModel().getColumn(i).setCellRenderer(rd);
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
                String unit = rs.getString(6);
                String price = rs.getString(7);
                model.addRow(new Object[]{id, name, category, qty, unit, price});
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void ProductSearch() {
        DefaultTableModel model = (DefaultTableModel) inventoryTable.getModel();
        model.setRowCount(0);
        try {
            String ch = InvSearchtxt.getText();
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/supermarketdb", "root", "root");
            String sql = "select * from addproduct where ProductID like ? or Category like ? or ProductName like ?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, "%" + ch + "%");
            ps.setString(2, "%" + ch + "%");
            ps.setString(3, "%" + ch + "%");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                String id = rs.getString(2);
                String name = rs.getString(3);
                String category = rs.getString(4);
                String qty = rs.getString(5);
                String unit = rs.getString(6);
                String price = rs.getString(7);
                model.addRow(new Object[]{id, name, category, qty, unit, price});
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void PurchaseTable() {
        DefaultTableModel model = (DefaultTableModel) PurchaseTable.getModel();
        DefaultTableCellRenderer rd = new DefaultTableCellRenderer();
        rd.setHorizontalAlignment(JLabel.CENTER);
        for (int i = 0; i < 5; i++) {
            PurchaseTable.getColumnModel().getColumn(i).setCellRenderer(rd);
        }
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/supermarketdb", "root", "root");
            String sql = "select * from purchasestatus";
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int id = rs.getInt(2);
                String name = rs.getString(3);
                String phoneno = rs.getString(4);
                String price = rs.getString(5);
                String date = rs.getString(6);
                model.addRow(new Object[]{id, name, phoneno, date, price});
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean EmployeeValidation() {
        FullName = fnametxt.getText();
        Age = agetxt.getText();
        Gender = gendertxt.getSelectedItem().toString();
        Email = emailtxt.getText();
        PhoneNo = phonenotxt.getText();
        Address = addresstxt.getText();
        Salary = salarytxt.getText();
        Roles = rolestxt.getSelectedItem().toString();
        Password = passwordtxt.getText();
        RePassword = repasswordtxt.getText();
        if (FullName.isEmpty() || Age.isEmpty() || Gender.isEmpty() || Email.isEmpty() || PhoneNo.isEmpty() || Address.isEmpty() || Salary.isEmpty() || Roles.isEmpty() || Password.isEmpty() || RePassword.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Fields cannot be empty..");
            return false;
        } else {
            if (!Password.equals(RePassword)) {
                JOptionPane.showMessageDialog(this, "Password must be same..");
                return false;
            } else {
                Pattern p_name = Pattern.compile("[A-Za-z\\s]+");
                Matcher m_name = p_name.matcher(FullName);
                if (!m_name.matches()) {
                    JOptionPane.showMessageDialog(this, "Number or Special Character not allowed..");
                    return false;
                } else {
                    Pattern p_age = Pattern.compile("[0-9]{2}");
                    Matcher m_age = p_age.matcher(Age);
                    if (!m_age.matches()) {
                        JOptionPane.showMessageDialog(this, "only 2 digit are allowed");
                        return false;
                    } else {
                        Pattern p_email = Pattern.compile("^[a-zA-Z0-9+_.-]+@[a-zA-Z0-9.-]+$");
                        Matcher m_email = p_email.matcher(Email);
                        if (!m_email.matches()) {
                            JOptionPane.showMessageDialog(this, "Email format not matched..");
                            return false;
                        } else {
                            Pattern p_phoneNo = Pattern.compile("[987][0-9]{9}");
                            Matcher m_phoneNo = p_phoneNo.matcher(PhoneNo);
                            if (!m_phoneNo.matches()) {
                                JOptionPane.showMessageDialog(this, "Phone No must be 10 digit..");
                                return false;
                            } else {
                                Pattern p_salary = Pattern.compile("[0-9,]+");
                                Matcher m_salary = p_salary.matcher(Salary);
                                if (!m_salary.matches()) {
                                    JOptionPane.showMessageDialog(this, "Only digits are allowed");
                                    return false;
                                } else {
                                    Pattern p_password = Pattern.compile("^(?=.*[0-9])" + "(?=.*[a-z])(?=.*[A-Z])" + "(?=.*[@#$%^&+=])" + "(?=\\S+$).{8,20}$");
                                    Matcher m_password = p_password.matcher(Password);
                                    if (!m_password.matches()) {
                                        JOptionPane.showMessageDialog(this, "password must be 8 digit");
                                        return false;
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        return true;
    }

    public boolean ProductValidation() {
        pname = Pname.getText();
        pcategory = Pcategory.getSelectedItem().toString();
        pquantity = Pquantity.getText();
        punit = Punit.getSelectedItem().toString();
        pprice = Pprice.getText();
        if (pname.isEmpty() || pquantity.isEmpty() || pprice.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Fields cannot be empty");
            return false;
        } else {
            Pattern p_name = Pattern.compile("[A-Za-z]+");
            Matcher m_name = p_name.matcher(pname);
            if (!m_name.matches()) {
                JOptionPane.showMessageDialog(this, "Only alphabhets are allowed");
                return false;
            } else {
                Pattern p_quantity = Pattern.compile("[0-9]+");
                Matcher m_quantity = p_quantity.matcher(pquantity);
                if (!m_quantity.matches()) {
                    JOptionPane.showMessageDialog(this, "Only numbers are allowed");
                    return false;
                } else {
                    Pattern p_price = Pattern.compile("[0-9]+");
                    Matcher m_price = p_price.matcher(pprice);
                    if (!m_price.matches()) {
                        JOptionPane.showMessageDialog(this, "Only numbers are allowed");
                        return false;
                    }
                }
            }
        }
        return true;
    }

    public void getDataByAsc() {
        DefaultTableModel model = (DefaultTableModel) PurchaseTable.getModel();
        DefaultTableCellRenderer rd = new DefaultTableCellRenderer();
        rd.setHorizontalAlignment(JLabel.CENTER);
        for (int i = 0; i < 5; i++) {
            PurchaseTable.getColumnModel().getColumn(i).setCellRenderer(rd);
        }
        String text = datetxt.getText();
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/supermarketdb", "root", "root");
            String sql = "select * from purchasestatus where Date like ?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, "%" + text + "%");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int id = rs.getInt(2);
                String name = rs.getString(3);
                String phoneno = rs.getString(4);
                String price = rs.getString(5);
                String date = rs.getString(6);
                model.addRow(new Object[]{id, name, phoneno, date, price});
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void getDataByDesc() {
        DefaultTableModel model = (DefaultTableModel) PurchaseTable.getModel();
        DefaultTableCellRenderer rd = new DefaultTableCellRenderer();
        rd.setHorizontalAlignment(JLabel.CENTER);
        for (int i = 0; i < 5; i++) {
            PurchaseTable.getColumnModel().getColumn(i).setCellRenderer(rd);
        }
        String text = datetxt.getText();
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/supermarketdb", "root", "root");
            String sql = "select * from purchasestatus where Date like ? order by ID DESC";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, "%" + text + "%");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int id = rs.getInt(2);
                String name = rs.getString(3);
                String phoneno = rs.getString(4);
                String price = rs.getString(5);
                String date = rs.getString(6);
                model.addRow(new Object[]{id, name, phoneno, date, price});
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void TableDetails() {
        DefaultTableModel model = (DefaultTableModel) PurchaseTable.getModel();
        model.setRowCount(0);
        DefaultTableCellRenderer rd = new DefaultTableCellRenderer();
        rd.setHorizontalAlignment(JLabel.CENTER);
        for (int i = 0; i < 5; i++) {
            PurchaseTable.getColumnModel().getColumn(i).setCellRenderer(rd);
        }
        try{
        String date = datetxt.getText();
        String incDec = incbox.getSelectedItem().toString();
        if(incDec.equals("ASC")){
            getDataByAsc();
        }else{
            getDataByDesc();
        }
        }catch(Exception e){
            e.printStackTrace();
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
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        LogoutBtn = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        addEmployeeBtn = new rojeru_san.complementos.RSButtonHover();
        addProductBtn = new rojeru_san.complementos.RSButtonHover();
        viewEmployeeBtn = new rojeru_san.complementos.RSButtonHover();
        PurchaseStatBtn = new rojeru_san.complementos.RSButtonHover();
        viewInventoryBtn = new rojeru_san.complementos.RSButtonHover();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel5 = new javax.swing.JPanel();
        jLabel17 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        gendertxt = new javax.swing.JComboBox<>();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        addresstxt = new javax.swing.JTextArea();
        jLabel12 = new javax.swing.JLabel();
        rolestxt = new javax.swing.JComboBox<>();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        passwordtxt = new javax.swing.JPasswordField();
        jLabel15 = new javax.swing.JLabel();
        repasswordtxt = new javax.swing.JPasswordField();
        fnametxt = new javax.swing.JTextField();
        emailtxt = new javax.swing.JTextField();
        agetxt = new javax.swing.JTextField();
        phonenotxt = new javax.swing.JTextField();
        salarytxt = new javax.swing.JTextField();
        addEmployee2 = new javax.swing.JButton();
        jPanel6 = new javax.swing.JPanel();
        jLabel20 = new javax.swing.JLabel();
        jLabel21 = new javax.swing.JLabel();
        jLabel22 = new javax.swing.JLabel();
        Pcategory = new javax.swing.JComboBox<>();
        jLabel23 = new javax.swing.JLabel();
        jLabel24 = new javax.swing.JLabel();
        jLabel25 = new javax.swing.JLabel();
        Punit = new javax.swing.JComboBox<>();
        Pname = new javax.swing.JTextField();
        Pquantity = new javax.swing.JTextField();
        Pprice = new javax.swing.JTextField();
        addProductBt = new javax.swing.JButton();
        jPanel8 = new javax.swing.JPanel();
        jLabel19 = new javax.swing.JLabel();
        jScrollPane5 = new javax.swing.JScrollPane();
        EmployeeTable = new javax.swing.JTable();
        txtEmployeeSearch = new javax.swing.JTextField();
        EmpSearch = new javax.swing.JButton();
        jPanel7 = new javax.swing.JPanel();
        jLabel18 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        PurchaseTable = new javax.swing.JTable();
        jLabel16 = new javax.swing.JLabel();
        incbox = new javax.swing.JComboBox<>();
        jLabel27 = new javax.swing.JLabel();
        datetxt = new javax.swing.JTextField();
        jPanel9 = new javax.swing.JPanel();
        jLabel26 = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        inventoryTable = new javax.swing.JTable();
        InvSearchtxt = new javax.swing.JTextField();
        ProdSearch = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel1.setBackground(new java.awt.Color(51, 51, 255));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/usericon.png"))); // NOI18N
        jLabel1.setText("Admin");
        jPanel1.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(1320, 0, 120, 50));

        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jPanel1.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 0, 40, 40));

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel3.setText("Welcome to A-Mart System");
        jPanel1.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 0, 280, 50));

        LogoutBtn.setBackground(new java.awt.Color(255, 0, 0));
        LogoutBtn.setForeground(new java.awt.Color(255, 255, 255));
        LogoutBtn.setText("LOGOUT");
        LogoutBtn.setBorder(null);
        LogoutBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                LogoutBtnActionPerformed(evt);
            }
        });
        jPanel1.add(LogoutBtn, new org.netbeans.lib.awtextra.AbsoluteConstraints(1429, -5, 110, 60));

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1540, 50));

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel3.setBackground(new java.awt.Color(0, 51, 102));
        jPanel3.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel4.setBackground(new java.awt.Color(102, 102, 255));
        jPanel4.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/dashboardicon.png"))); // NOI18N
        jLabel4.setText("DASHBOARD");
        jPanel4.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 0, 200, 50));

        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jPanel4.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(3, 0, 40, 50));

        jPanel3.add(jPanel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 60, 290, 50));

        addEmployeeBtn.setBackground(new java.awt.Color(0, 51, 102));
        addEmployeeBtn.setText("ADD EMPLOYEE");
        addEmployeeBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addEmployeeBtnActionPerformed(evt);
            }
        });
        jPanel3.add(addEmployeeBtn, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 150, 290, -1));

        addProductBtn.setBackground(new java.awt.Color(0, 51, 102));
        addProductBtn.setText("ADD PRODUCT");
        addProductBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addProductBtnActionPerformed(evt);
            }
        });
        jPanel3.add(addProductBtn, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 210, 290, -1));

        viewEmployeeBtn.setBackground(new java.awt.Color(0, 51, 102));
        viewEmployeeBtn.setText("VIEW EMPLOYEE");
        viewEmployeeBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                viewEmployeeBtnActionPerformed(evt);
            }
        });
        jPanel3.add(viewEmployeeBtn, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 270, 290, -1));

        PurchaseStatBtn.setBackground(new java.awt.Color(0, 51, 102));
        PurchaseStatBtn.setText("PURCHASE STATUS");
        PurchaseStatBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                PurchaseStatBtnActionPerformed(evt);
            }
        });
        jPanel3.add(PurchaseStatBtn, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 330, 290, -1));

        viewInventoryBtn.setBackground(new java.awt.Color(0, 51, 102));
        viewInventoryBtn.setText("VIEW INVENTORY");
        viewInventoryBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                viewInventoryBtnActionPerformed(evt);
            }
        });
        jPanel3.add(viewInventoryBtn, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 390, 290, -1));

        jPanel2.add(jPanel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 290, 740));

        getContentPane().add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 50, 290, 740));

        jPanel5.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel17.setFont(new java.awt.Font("Tahoma", 1, 28)); // NOI18N
        jLabel17.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel17.setText("ADD EMPLOYEE");
        jPanel5.add(jLabel17, new org.netbeans.lib.awtextra.AbsoluteConstraints(520, 30, -1, -1));

        jLabel6.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel6.setText("Full Name:");
        jPanel5.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 120, 130, 40));

        jLabel7.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel7.setText("Age:");
        jPanel5.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(690, 120, 150, 40));

        jLabel8.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel8.setText("Gender:");
        jPanel5.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 180, 130, 40));

        gendertxt.setBackground(new java.awt.Color(240, 240, 240));
        gendertxt.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        gendertxt.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "No Select", "Male", "Female", "Other" }));
        gendertxt.setBorder(null);
        jPanel5.add(gendertxt, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 180, 240, 40));

        jLabel9.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel9.setText("Phone No:");
        jPanel5.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(690, 180, 150, 40));

        jLabel10.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel10.setText("Email:");
        jPanel5.add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 250, 130, 40));

        jLabel11.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel11.setText("Address:");
        jPanel5.add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(690, 250, 150, 40));

        jScrollPane1.setBorder(null);
        jScrollPane1.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        addresstxt.setBackground(new java.awt.Color(240, 240, 240));
        addresstxt.setColumns(20);
        addresstxt.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        addresstxt.setRows(5);
        addresstxt.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true));
        jScrollPane1.setViewportView(addresstxt);

        jPanel5.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(860, 250, 280, 60));

        jLabel12.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel12.setText("Role:");
        jPanel5.add(jLabel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 340, 130, 40));

        rolestxt.setBackground(new java.awt.Color(240, 240, 240));
        rolestxt.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        rolestxt.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "No Select", "Admin", "Cashier" }));
        rolestxt.setBorder(null);
        jPanel5.add(rolestxt, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 340, 240, 40));

        jLabel13.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel13.setText("Salary:");
        jPanel5.add(jLabel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(690, 340, 150, 40));

        jLabel14.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel14.setText("Password:");
        jPanel5.add(jLabel14, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 400, 130, 40));

        passwordtxt.setBackground(new java.awt.Color(240, 240, 240));
        passwordtxt.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        passwordtxt.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        passwordtxt.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(0, 0, 0)));
        jPanel5.add(passwordtxt, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 410, 240, 40));

        jLabel15.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel15.setText("RePassword:");
        jPanel5.add(jLabel15, new org.netbeans.lib.awtextra.AbsoluteConstraints(680, 410, 170, 40));

        repasswordtxt.setBackground(new java.awt.Color(240, 240, 240));
        repasswordtxt.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        repasswordtxt.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        repasswordtxt.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(0, 0, 0)));
        jPanel5.add(repasswordtxt, new org.netbeans.lib.awtextra.AbsoluteConstraints(860, 410, 260, 40));

        fnametxt.setBackground(new java.awt.Color(240, 240, 240));
        fnametxt.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        fnametxt.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(0, 0, 0)));
        jPanel5.add(fnametxt, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 110, 240, 40));

        emailtxt.setBackground(new java.awt.Color(240, 240, 240));
        emailtxt.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        emailtxt.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(0, 0, 0)));
        jPanel5.add(emailtxt, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 250, 240, 40));

        agetxt.setBackground(new java.awt.Color(240, 240, 240));
        agetxt.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        agetxt.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(0, 0, 0)));
        jPanel5.add(agetxt, new org.netbeans.lib.awtextra.AbsoluteConstraints(860, 110, 250, 40));

        phonenotxt.setBackground(new java.awt.Color(240, 240, 240));
        phonenotxt.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        phonenotxt.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(0, 0, 0)));
        jPanel5.add(phonenotxt, new org.netbeans.lib.awtextra.AbsoluteConstraints(860, 180, 260, 40));

        salarytxt.setBackground(new java.awt.Color(240, 240, 240));
        salarytxt.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        salarytxt.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(0, 0, 0)));
        jPanel5.add(salarytxt, new org.netbeans.lib.awtextra.AbsoluteConstraints(860, 340, 260, 40));

        addEmployee2.setBackground(new java.awt.Color(0, 51, 102));
        addEmployee2.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        addEmployee2.setForeground(new java.awt.Color(255, 255, 255));
        addEmployee2.setText("ADD EMPLOYEE");
        addEmployee2.setBorder(null);
        addEmployee2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addEmployee2ActionPerformed(evt);
            }
        });
        jPanel5.add(addEmployee2, new org.netbeans.lib.awtextra.AbsoluteConstraints(500, 510, 250, 40));

        jTabbedPane1.addTab("tab1", jPanel5);

        jPanel6.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel20.setFont(new java.awt.Font("Tahoma", 1, 28)); // NOI18N
        jLabel20.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel20.setText("ADD PRODUCT");
        jPanel6.add(jLabel20, new org.netbeans.lib.awtextra.AbsoluteConstraints(520, 30, -1, -1));

        jLabel21.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel21.setText("Product Name:");
        jPanel6.add(jLabel21, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 150, 190, 40));

        jLabel22.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel22.setText("Product Category:");
        jPanel6.add(jLabel22, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 210, 220, 40));

        Pcategory.setBackground(new java.awt.Color(240, 240, 240));
        Pcategory.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        Pcategory.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "No Select", "Fruits", "Vegetables", "Dairy", "Bakery", "Beverages", "Snacks", "Toys", "Bags" }));
        jPanel6.add(Pcategory, new org.netbeans.lib.awtextra.AbsoluteConstraints(610, 210, 250, 40));

        jLabel23.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel23.setText("Product Quantity:");
        jPanel6.add(jLabel23, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 270, 230, 40));

        jLabel24.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel24.setText("Product Price:");
        jPanel6.add(jLabel24, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 390, 230, 40));

        jLabel25.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel25.setText("Product Units:");
        jPanel6.add(jLabel25, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 330, 220, 40));

        Punit.setBackground(new java.awt.Color(240, 240, 240));
        Punit.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        Punit.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "No Select", "Kilograms", "Litres", "Dozens", "Pieces" }));
        jPanel6.add(Punit, new org.netbeans.lib.awtextra.AbsoluteConstraints(610, 330, 250, 40));

        Pname.setBackground(new java.awt.Color(240, 240, 240));
        Pname.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        Pname.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(0, 0, 0)));
        jPanel6.add(Pname, new org.netbeans.lib.awtextra.AbsoluteConstraints(610, 140, 250, 40));

        Pquantity.setBackground(new java.awt.Color(240, 240, 240));
        Pquantity.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        Pquantity.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(0, 0, 0)));
        jPanel6.add(Pquantity, new org.netbeans.lib.awtextra.AbsoluteConstraints(610, 270, 250, 40));

        Pprice.setBackground(new java.awt.Color(240, 240, 240));
        Pprice.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        Pprice.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(0, 0, 0)));
        jPanel6.add(Pprice, new org.netbeans.lib.awtextra.AbsoluteConstraints(610, 380, 250, 40));

        addProductBt.setBackground(new java.awt.Color(0, 51, 102));
        addProductBt.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        addProductBt.setForeground(new java.awt.Color(255, 255, 255));
        addProductBt.setText("ADD PRODUCT");
        addProductBt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addProductBtActionPerformed(evt);
            }
        });
        jPanel6.add(addProductBt, new org.netbeans.lib.awtextra.AbsoluteConstraints(500, 480, 230, 40));

        jTabbedPane1.addTab("tab2", jPanel6);

        jPanel8.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel19.setFont(new java.awt.Font("Tahoma", 1, 28)); // NOI18N
        jLabel19.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel19.setText("VIEW EMPLOYEE");
        jPanel8.add(jLabel19, new org.netbeans.lib.awtextra.AbsoluteConstraints(520, 30, -1, -1));

        EmployeeTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Full Name", "Age", "Gender", "PhoneNo", "Email", "Roles", "Salary", "Password", "Action"
            }
        ));
        EmployeeTable.setRowHeight(30);
        jScrollPane5.setViewportView(EmployeeTable);

        jPanel8.add(jScrollPane5, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 210, 870, 370));

        txtEmployeeSearch.setBackground(new java.awt.Color(240, 240, 240));
        txtEmployeeSearch.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        txtEmployeeSearch.setText("Search by Emp Name or Role");
        txtEmployeeSearch.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(0, 0, 0)));
        jPanel8.add(txtEmployeeSearch, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 130, 260, 40));

        EmpSearch.setBackground(new java.awt.Color(0, 51, 102));
        EmpSearch.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        EmpSearch.setForeground(new java.awt.Color(255, 255, 255));
        EmpSearch.setText("Search");
        EmpSearch.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                EmpSearchActionPerformed(evt);
            }
        });
        jPanel8.add(EmpSearch, new org.netbeans.lib.awtextra.AbsoluteConstraints(680, 130, 110, 40));

        jTabbedPane1.addTab("tab4", jPanel8);

        jPanel7.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel18.setFont(new java.awt.Font("Tahoma", 1, 28)); // NOI18N
        jLabel18.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel18.setText("PURCHASE STATUS");
        jPanel7.add(jLabel18, new org.netbeans.lib.awtextra.AbsoluteConstraints(520, 30, -1, -1));

        PurchaseTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Bill ID", "Customer Name", "Phone No", "Date", "Total Price"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        PurchaseTable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                PurchaseTableMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(PurchaseTable);

        jPanel7.add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 190, 850, 310));

        jLabel16.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel16.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel16.setText("Filter By Date:");
        jPanel7.add(jLabel16, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 120, -1, 30));

        incbox.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        incbox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "ASC", "DESC" }));
        incbox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                incboxActionPerformed(evt);
            }
        });
        jPanel7.add(incbox, new org.netbeans.lib.awtextra.AbsoluteConstraints(880, 119, 170, 30));

        jLabel27.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel27.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel27.setText("Change Order by Id:");
        jPanel7.add(jLabel27, new org.netbeans.lib.awtextra.AbsoluteConstraints(670, 120, -1, 30));

        datetxt.setBackground(new java.awt.Color(240, 240, 240));
        datetxt.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        datetxt.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(0, 0, 0)));
        jPanel7.add(datetxt, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 110, 240, 40));

        jTabbedPane1.addTab("tab3", jPanel7);

        jPanel9.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel26.setFont(new java.awt.Font("Tahoma", 1, 28)); // NOI18N
        jLabel26.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel26.setText("VIEW INVENTORY");
        jPanel9.add(jLabel26, new org.netbeans.lib.awtextra.AbsoluteConstraints(520, 30, -1, -1));

        jScrollPane3.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        inventoryTable.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        inventoryTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Product ID", "Product Name", "Category", "Quantity", "Unit", "Price", "Action"
            }
        ));
        inventoryTable.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_ALL_COLUMNS);
        inventoryTable.setCellSelectionEnabled(true);
        inventoryTable.setGridColor(new java.awt.Color(0, 0, 0));
        inventoryTable.setRowHeight(30);
        jScrollPane3.setViewportView(inventoryTable);
        inventoryTable.getColumnModel().getSelectionModel().setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);

        jPanel9.add(jScrollPane3, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 180, 850, 390));

        InvSearchtxt.setBackground(new java.awt.Color(240, 240, 240));
        InvSearchtxt.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        InvSearchtxt.setText("Search by ProdID or Category");
        InvSearchtxt.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(0, 0, 0)));
        jPanel9.add(InvSearchtxt, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 120, 260, 40));

        ProdSearch.setBackground(new java.awt.Color(0, 51, 102));
        ProdSearch.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        ProdSearch.setForeground(new java.awt.Color(255, 255, 255));
        ProdSearch.setText("Search");
        ProdSearch.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ProdSearchActionPerformed(evt);
            }
        });
        jPanel9.add(ProdSearch, new org.netbeans.lib.awtextra.AbsoluteConstraints(660, 120, 100, 40));

        jTabbedPane1.addTab("tab5", jPanel9);

        getContentPane().add(jTabbedPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 10, 1250, 780));

        setSize(new java.awt.Dimension(1555, 827));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void logoutbtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_logoutbtnActionPerformed
       
    }//GEN-LAST:event_logoutbtnActionPerformed

    private void addEmployee2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addEmployee2ActionPerformed
        FullName = fnametxt.getText();
        Age = agetxt.getText();
        Gender = gendertxt.getSelectedItem().toString();
        Email = emailtxt.getText();
        PhoneNo = phonenotxt.getText();
        Address = addresstxt.getText();
        Salary = salarytxt.getText();
        Roles = rolestxt.getSelectedItem().toString();
        Password = passwordtxt.getText();
        RePassword = repasswordtxt.getText();
        if (EmployeeValidation() == true) {
            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
                Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/supermarketdb", "root", "root");
                String sql = "insert into addemployee(FullName,Age,Gender,PhoneNo,Email,Address,Roles,Salary,Password) values(?,?,?,?,?,?,?,?,?)";
                PreparedStatement ps = con.prepareStatement(sql);
                ps.setString(1, FullName);
                ps.setString(2, Age);
                ps.setString(3, Gender);
                ps.setString(4, PhoneNo);
                ps.setString(5, Email);
                ps.setString(6, Address);
                ps.setString(7, Roles);
                ps.setString(8, Salary);
                ps.setString(9, Password);
                int i = ps.executeUpdate();
                DefaultTableModel model = (DefaultTableModel) EmployeeTable.getModel();
                model.setRowCount(0);
                EmployeeTable();
                if (i == 1) {
                    JOptionPane.showMessageDialog(this, "Added Successfully");
                    fnametxt.setText((""));
                    agetxt.setText("");
                    gendertxt.setSelectedIndex(0);
                    emailtxt.setText("");
                    phonenotxt.setText("");
                    addresstxt.setText("");
                    rolestxt.setSelectedIndex(0);
                    salarytxt.setText("");
                    passwordtxt.setText("");
                    repasswordtxt.setText("");
                } else {
                    JOptionPane.showMessageDialog(this, "Some error occured..");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }//GEN-LAST:event_addEmployee2ActionPerformed

    private void addEmployee3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addProductActionPerformed

    }//GEN-LAST:event_addProductActionPerformed

    private void InvSearchBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_InvSearchBtnActionPerformed
      
    }//GEN-LAST:event_InvSearchBtnActionPerformed

    private void EmpSearchBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_EmpSearchBtnActionPerformed
       
    }//GEN-LAST:event_EmpSearchBtnActionPerformed

    private void datetxtKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_datetxtKeyReleased
       TableDetails();
    }//GEN-LAST:event_datetxtKeyReleased

    private void incboxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_incboxActionPerformed
        TableDetails();
    }//GEN-LAST:event_incboxActionPerformed

    private void PurchaseTableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_PurchaseTableMouseClicked
        int index=PurchaseTable.getSelectedRow();
        TableModel model=PurchaseTable.getModel();
        String id=model.getValueAt(index,0).toString();
        OpenPdf.openById(id);
    }//GEN-LAST:event_PurchaseTableMouseClicked

    private void viewEmployeeBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_viewEmployeeBtnActionPerformed
       jTabbedPane1.setSelectedIndex(2);
    }//GEN-LAST:event_viewEmployeeBtnActionPerformed

    private void viewInventoryBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_viewInventoryBtnActionPerformed
       jTabbedPane1.setSelectedIndex(4);
    }//GEN-LAST:event_viewInventoryBtnActionPerformed

    private void PurchaseStatBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_PurchaseStatBtnActionPerformed
        jTabbedPane1.setSelectedIndex(3);
    }//GEN-LAST:event_PurchaseStatBtnActionPerformed

    private void addProductBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addProductBtnActionPerformed
       jTabbedPane1.setSelectedIndex(1);
    }//GEN-LAST:event_addProductBtnActionPerformed

    private void addEmployeeBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addEmployeeBtnActionPerformed
        jTabbedPane1.setSelectedIndex(0);
    }//GEN-LAST:event_addEmployeeBtnActionPerformed

    private void addProductBtActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addProductBtActionPerformed
       Random ran = new Random();
        int a = ran.nextInt(100000);
        String pID = "";
        pname = Pname.getText();
        pcategory = Pcategory.getSelectedItem().toString();
        if (pcategory.equals("No Select")) {
            JOptionPane.showMessageDialog(this, "please select category..");
        } else if (pcategory.equals("Fruits")) {
            pID = "FT-" + a;
        } else if (pcategory.equals("Vegetables")) {
            pID = "VT-" + a;
        } else if (pcategory.equals("Dairy")) {
            pID = "DY-" + a;
        } else if (pcategory.equals("Bakery")) {
            pID = "BK-" + a;
        } else if (pcategory.equals("Beverages")) {
            pID = "BV-" + a;
        } else if (pcategory.equals("Snacks")) {
            pID = "SN-" + a;
        } else if (pcategory.equals("Toys")) {
            pID = "TS-" + a;
        } else if (pcategory.equals("Bags")) {
            pID = "BG-" + a;
        }
        pquantity = Pquantity.getText();
        punit = Punit.getSelectedItem().toString();
        pprice = Pprice.getText();
        if (ProductValidation() == true) {
            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
                Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/supermarketdb", "root", "root");
                String sql = "insert into addproduct(ProductID,ProductName,Category,Quantity,Unit,Price) values(?,?,?,?,?,?)";
                PreparedStatement ps = con.prepareStatement(sql);
                ps.setString(1, pID);
                ps.setString(2, pname);
                ps.setString(3, pcategory);
                ps.setString(4, pquantity);
                ps.setString(5, punit);
                ps.setString(6, pprice);
                int i = ps.executeUpdate();
                DefaultTableModel model = (DefaultTableModel) inventoryTable.getModel();
                model.setRowCount(0);
                ProductTable();
                if (i == 1) {
                    JOptionPane.showMessageDialog(this, "Added Successfully");
                    Pname.setText("");
                    Pcategory.setSelectedIndex(0);
                    Pquantity.setText("");
                    Punit.setSelectedIndex(0);
                    Pprice.setText("");
                } else {
                    JOptionPane.showMessageDialog(this, "Some error occured..");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }//GEN-LAST:event_addProductBtActionPerformed

    private void EmpSearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_EmpSearchActionPerformed
         EmployeeSearch();
    }//GEN-LAST:event_EmpSearchActionPerformed

    private void ProdSearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ProdSearchActionPerformed
         ProductSearch();
    }//GEN-LAST:event_ProdSearchActionPerformed

    private void LogoutBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_LogoutBtnActionPerformed
        int response = JOptionPane.showConfirmDialog(this, "Do you want to Logout?", "Confirm", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
        if (response == JOptionPane.YES_OPTION) {
            setVisible(false);
            new Login().setVisible(true);
        }
    }//GEN-LAST:event_LogoutBtnActionPerformed

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
            java.util.logging.Logger.getLogger(AdminPage.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(AdminPage.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(AdminPage.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(AdminPage.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
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
                new AdminPage().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton EmpSearch;
    private javax.swing.JTable EmployeeTable;
    private javax.swing.JTextField InvSearchtxt;
    private javax.swing.JButton LogoutBtn;
    private javax.swing.JComboBox<String> Pcategory;
    private javax.swing.JTextField Pname;
    private javax.swing.JTextField Pprice;
    private javax.swing.JTextField Pquantity;
    private javax.swing.JButton ProdSearch;
    private javax.swing.JComboBox<String> Punit;
    private rojeru_san.complementos.RSButtonHover PurchaseStatBtn;
    private javax.swing.JTable PurchaseTable;
    private javax.swing.JButton addEmployee2;
    private rojeru_san.complementos.RSButtonHover addEmployeeBtn;
    private javax.swing.JButton addProductBt;
    private rojeru_san.complementos.RSButtonHover addProductBtn;
    private javax.swing.JTextArea addresstxt;
    private javax.swing.JTextField agetxt;
    private javax.swing.JTextField datetxt;
    private javax.swing.JTextField emailtxt;
    private javax.swing.JTextField fnametxt;
    private javax.swing.JComboBox<String> gendertxt;
    private javax.swing.JComboBox<String> incbox;
    private javax.swing.JTable inventoryTable;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JPasswordField passwordtxt;
    private javax.swing.JTextField phonenotxt;
    private javax.swing.JPasswordField repasswordtxt;
    private javax.swing.JComboBox<String> rolestxt;
    private javax.swing.JTextField salarytxt;
    private javax.swing.JTextField txtEmployeeSearch;
    private rojeru_san.complementos.RSButtonHover viewEmployeeBtn;
    private rojeru_san.complementos.RSButtonHover viewInventoryBtn;
    // End of variables declaration//GEN-END:variables
}
