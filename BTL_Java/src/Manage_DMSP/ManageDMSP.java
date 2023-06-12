/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package Manage_DMSP;
import Manage_DMSP.Catelog;
import static Manage_User.ManageUser.removeDiacriticalMarks;
import RWFile.RWFileIO;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.regex.Pattern;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import java.text.Normalizer;
import Manage_DMSP.Product;
import Manage_User.ManageUser;
import Menu.Menu;
import javax.swing.DefaultComboBoxModel;

/**
 *
 * @author nhata
 */
public class ManageDMSP extends javax.swing.JFrame {
    //Thao tác với danh mục
    ArrayList<Catelog> ltCate = new ArrayList<>();
    String file = "E:catelog.txt";
    RWFileIO db = new RWFileIO();
    DefaultTableModel model = new DefaultTableModel();
    //Lấy dữ liệu danh mục từ file
    public void GetData(){ // Đưa dữ liệu từ file txt vào ArrayList
        try {
            ltCate = (ArrayList<Catelog>) db.readFile(file);//Đưa đối tượng đọc được trong file vào arrayList đã tạo
        } catch (Exception err) {
            JOptionPane.showMessageDialog(this, err.toString(),"Error",JOptionPane.ERROR_MESSAGE);
        }
    }
    //Thiết lập hiển thị cho bảng danh mục
    public void initTable() {
        String[] columns = {"Mã Danh mục", "Tên danh mục"};
        model.setColumnIdentifiers(columns);
        tbDM.setModel(model);

    }
    //Thao tác với sản phẩm
    ArrayList<Product> ltPr = new ArrayList<>();
    String filepr = "E:products.txt";
    DefaultTableModel modelpr = new DefaultTableModel();
    DefaultComboBoxModel<String> cb = new DefaultComboBoxModel<>();
    DefaultComboBoxModel<String> cbs = new DefaultComboBoxModel<>();
    
    //Lấy dữ liệu các sản phẩm từ file
    public void GetDataPr(){ // Đưa dữ liệu từ file txt vào ArrayList
        try {
            ltPr = (ArrayList<Product>) db.readFile(filepr);//Đưa đối tượng đọc được trong file vào arrayList đã tạo
        } catch (Exception err) {
            JOptionPane.showMessageDialog(this, err.toString(),"Error",JOptionPane.ERROR_MESSAGE);
        }
    }
    //Thiết lập hiển thị cho bảng sản phẩm
    public void initTablePr() {
        String[] columns = {"Mã sản phẩm", "Tên sản phẩm", "Số lượng", "Giá bán", "Ngày nhập", "Danh mục"};
        modelpr.setColumnIdentifiers(columns);
        tbProduct.setModel(modelpr);
    }
    //Thao tác với ComboBox
    //------------------------------
    public void GetDataCb(){
//        ArrayList<String> ar = new ArrayList<>();
//        for(Catelog o : ltCate) {
//            ar.add(o.getTenDM());
//        }
        for(Catelog o : ltCate){
            cb.addElement(o.getTenDM());
        }
        cbDanhMuc.setModel(cb);
    }
    public void AddCb(Catelog ct){
        cb.addElement(ct.getTenDM());
        cbs.addElement(ct.getTenDM()); 
    }
    public void RemoveCb(Catelog ct){
        cb.removeElement(ct.getTenDM());
        cbs.removeElement(ct.getTenDM());
    }
    public void GetDataCbs(){
        for(Catelog o : ltCate){
            cbs.addElement(o.getTenDM());
        }
        cbChoose.setModel(cbs);
    }
    //------------------------------
    //Thêm sản phẩm
    public void AddPr(){
        String maSP = txtMaSP.getText().trim();
        String tenSP = txtTenSP.getText().trim();
        int soLuong = (int) txtSoLuong.getValue();
        double giaBan = Double.parseDouble(txtGiaBan.getText().trim());
        String ngayN = txtNgayNhap.getText().trim();
        String danhMuc = (String) cbDanhMuc.getSelectedItem(); //
        String giaBant = txtGiaBan.getText();
        
        Product pr = new Product(maSP, tenSP, danhMuc, soLuong, giaBan, ngayN);
        try {
            if(maSP.equals("") || tenSP.equals("") || soLuong <= 0 || giaBant.equals("")  || ngayN.equals("")) {
                JOptionPane.showMessageDialog(null, "Nhập đủ thông tin", "Error", JOptionPane.WARNING_MESSAGE);
            }
            else {
                try {
                    double number = Double.parseDouble(giaBant);
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "Giá bán phải là số !!", "Error", JOptionPane.WARNING_MESSAGE);
                }
                boolean isExist = false;
                for(Product o: ltPr){
                    if(pr.getMaSP().equals(o.getMaSP()) || pr.getTenSP().equals(o.getTenSP())) {
                        isExist = true;
                        break;
                    }
                }
                if(isExist == true) {
                    JOptionPane.showMessageDialog(null, "Sản phẩm đã tồn tại", "Error", JOptionPane.WARNING_MESSAGE);
                }
                else {
                    ltPr.add(pr);
                    
                    db.saveFile(filepr, ltPr);
                    HienThiPr(ltPr);
                    //Fix lỗi hiển thị
                    txtMaSP.setText("");
                    txtTenSP.setText("");
                    txtSoLuong.setValue(0);
                    txtGiaBan.setText("");
                    txtNgayNhap.setText("");
                }
            }
        } catch (Exception err) {
            //System.out.println(err.toString());
            JOptionPane.showMessageDialog(this, err.toString(),"Error",JOptionPane.ERROR_MESSAGE);
        }
    }
    
    
   
    /**
     * Creates new form ManageDMSP
     */
    public ManageDMSP() {
        initComponents();
        GetData();
        initTable();
        HienThi(ltCate);
        GetDataPr();
        GetDataCb();
        GetDataCbs();
        initTablePr();
        HienThiPr(ltPr);
    }
    //Thêm danh mục
    public void AddCate(){
        String maDM = txtMaDM.getText().trim();
        String tenDM = txtTenDM.getText().trim();
        
        Catelog cate = new Catelog(maDM,tenDM);
        try{
            if(maDM.equals("") || tenDM.equals(""))
                JOptionPane.showMessageDialog(null, "Nhập đủ thông tin", "Error", JOptionPane.WARNING_MESSAGE);
            else {
                boolean isExist = false;
                for(Catelog o: ltCate){
                    if(cate.getMaDM().equals(o.getMaDM()) || cate.getTenDM().equals(o.getTenDM())) {
                        isExist = true;
                        break;
                    }
                }
                if(isExist == true) {
                    JOptionPane.showMessageDialog(null, "Danh mục đã tồn tại", "Error", JOptionPane.WARNING_MESSAGE);
                }
                else{
                    ltCate.add(cate);
                    db.saveFile(file, ltCate);
                    HienThi(ltCate);
                    //GetDataCb();
                    AddCb(cate);
                    
                    //Fix lỗi hiển thị
                    txtMaDM.setText("");
                    txtTenDM.setText("");
 
                }
            }
        } catch (Exception err) {
            //System.out.println(err.toString());
            JOptionPane.showMessageDialog(this, err.toString(),"Error",JOptionPane.ERROR_MESSAGE);
        }
    }
    //Hiển thị các danh mục ra bảng
    public void HienThi(ArrayList<Catelog> ltCate){
        model.setRowCount(0);
        for (Catelog o : ltCate){         
            model.addRow(new Object[]{
                o.getMaDM(), o.getTenDM()
            });
        }
    }
    //Hiển thị các sản phẩm ra bảng
    public void HienThiPr(ArrayList<Product> ltPr){
        modelpr.setRowCount(0);
        for (Product p : ltPr){         
            modelpr.addRow(new Object[]{
               p.getMaSP(), p.getTenSP(), p.getSoLuong(),p.getGiaBan(), p.getNgayNhap(), p.getTenDM()
            });
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

        jToggleButton1 = new javax.swing.JToggleButton();
        jDesktopPane1 = new javax.swing.JDesktopPane();
        jLabel2 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        btnBack = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        txtMaDM = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        txtTenDM = new javax.swing.JTextField();
        btnAdd = new javax.swing.JButton();
        btnUpdate = new javax.swing.JButton();
        btnDelete = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tbDM = new javax.swing.JTable();
        btnSearch = new javax.swing.JButton();
        txtSearch = new javax.swing.JTextField();
        btnViewAll = new javax.swing.JButton();
        jSeparator1 = new javax.swing.JSeparator();
        btnReset = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        txtMaSP = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        txtTenSP = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        txtSoLuong = new javax.swing.JSpinner();
        jLabel9 = new javax.swing.JLabel();
        txtGiaBan = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        txtNgayNhap = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        cbDanhMuc = new javax.swing.JComboBox<>();
        btnThem = new javax.swing.JButton();
        bntSua = new javax.swing.JButton();
        btnXoa = new javax.swing.JButton();
        btnRes = new javax.swing.JButton();
        btnView = new javax.swing.JButton();
        cbChoose = new javax.swing.JComboBox<>();
        btnLoc = new javax.swing.JButton();
        txtTimKiem = new javax.swing.JTextField();
        btnTimKiem = new javax.swing.JButton();
        jSeparator2 = new javax.swing.JSeparator();
        jScrollPane2 = new javax.swing.JScrollPane();
        tbProduct = new javax.swing.JTable();
        ckChoose = new javax.swing.JCheckBox();

        jToggleButton1.setText("jToggleButton1");

        javax.swing.GroupLayout jDesktopPane1Layout = new javax.swing.GroupLayout(jDesktopPane1);
        jDesktopPane1.setLayout(jDesktopPane1Layout);
        jDesktopPane1Layout.setHorizontalGroup(
            jDesktopPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );
        jDesktopPane1Layout.setVerticalGroup(
            jDesktopPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );

        jLabel2.setText("jLabel2");

        jLabel5.setText("jLabel5");

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        btnBack.setText("Back");
        btnBack.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBackActionPerformed(evt);
            }
        });

        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel3.setText("QUẢN LÝ DANH MỤC VÀ SẢN PHẨM");

        jTabbedPane1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 204)));

        jLabel1.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel1.setText("Mã danh mục");

        txtMaDM.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N

        jLabel4.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel4.setText("Tên danh mục");

        txtTenDM.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N

        btnAdd.setText("Thêm");
        btnAdd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddActionPerformed(evt);
            }
        });

        btnUpdate.setText("Sửa");
        btnUpdate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUpdateActionPerformed(evt);
            }
        });

        btnDelete.setText("Xóa");
        btnDelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeleteActionPerformed(evt);
            }
        });

        tbDM.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        tbDM.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbDMMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tbDM);

        btnSearch.setText("Tìm kiếm");
        btnSearch.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSearchActionPerformed(evt);
            }
        });

        btnViewAll.setText("Hiển thị tất cả");
        btnViewAll.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnViewAllActionPerformed(evt);
            }
        });

        jSeparator1.setOrientation(javax.swing.SwingConstants.VERTICAL);

        btnReset.setText("Reset");
        btnReset.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnResetActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1)
                    .addComponent(jLabel4)
                    .addComponent(btnAdd))
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(37, 37, 37)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txtMaDM)
                            .addComponent(txtTenDM, javax.swing.GroupLayout.PREFERRED_SIZE, 181, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(3, 3, 3)
                        .addComponent(btnUpdate)
                        .addGap(18, 18, 18)
                        .addComponent(btnDelete)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 172, Short.MAX_VALUE)
                        .addComponent(btnReset, javax.swing.GroupLayout.PREFERRED_SIZE, 72, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(32, 32, 32)))
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(48, 48, 48)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(txtSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 166, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnSearch)
                        .addGap(97, 97, 97)
                        .addComponent(btnViewAll)))
                .addGap(45, 45, 45))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(48, 48, 48)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel1)
                            .addComponent(txtMaDM, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(23, 23, 23)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel4)
                            .addComponent(txtTenDM, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(46, 46, 46)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnAdd)
                            .addComponent(btnUpdate)
                            .addComponent(btnDelete)
                            .addComponent(btnReset)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(30, 30, 30)
                        .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 443, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(19, 19, 19)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnSearch)
                            .addComponent(txtSearch, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnViewAll))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(47, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Danh mục", jPanel1);

        jLabel6.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel6.setText("Mã sản phẩm");

        txtMaSP.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N

        jLabel7.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel7.setText("Tên sản phẩm");

        txtTenSP.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N

        jLabel8.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel8.setText("Số lượng");

        txtSoLuong.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N

        jLabel9.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel9.setText("Giá bán");

        txtGiaBan.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N

        jLabel10.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel10.setText("Ngày nhập");

        txtNgayNhap.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N

        jLabel11.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel11.setText("Danh mục");

        cbDanhMuc.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        cbDanhMuc.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        btnThem.setText("Thêm");
        btnThem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThemActionPerformed(evt);
            }
        });

        bntSua.setText("Sửa");
        bntSua.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bntSuaActionPerformed(evt);
            }
        });

        btnXoa.setText("Xóa");
        btnXoa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnXoaActionPerformed(evt);
            }
        });

        btnRes.setText("Reset");

        btnView.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        btnView.setText("View all");
        btnView.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnViewActionPerformed(evt);
            }
        });

        cbChoose.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        cbChoose.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        btnLoc.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        btnLoc.setText("Lọc");
        btnLoc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLocActionPerformed(evt);
            }
        });

        txtTimKiem.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        txtTimKiem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtTimKiemActionPerformed(evt);
            }
        });

        btnTimKiem.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        btnTimKiem.setText("Tìm kiếm");
        btnTimKiem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTimKiemActionPerformed(evt);
            }
        });

        jSeparator2.setOrientation(javax.swing.SwingConstants.VERTICAL);

        tbProduct.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        tbProduct.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbProductMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(tbProduct);

        ckChoose.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(26, 26, 26)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnRes)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addGroup(jPanel2Layout.createSequentialGroup()
                            .addComponent(btnThem)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 29, Short.MAX_VALUE)
                            .addComponent(bntSua)
                            .addGap(26, 26, 26)
                            .addComponent(btnXoa))
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel2Layout.createSequentialGroup()
                            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jLabel6)
                                .addComponent(jLabel7)
                                .addComponent(jLabel8)
                                .addComponent(jLabel9)
                                .addComponent(jLabel10)
                                .addComponent(jLabel11))
                            .addGap(33, 33, 33)
                            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(cbDanhMuc, 0, 152, Short.MAX_VALUE)
                                .addComponent(txtMaSP)
                                .addComponent(txtTenSP)
                                .addComponent(txtGiaBan)
                                .addComponent(txtNgayNhap)
                                .addComponent(txtSoLuong, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addGap(31, 31, 31)
                .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(50, 50, 50)
                .addComponent(ckChoose, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(cbChoose, javax.swing.GroupLayout.PREFERRED_SIZE, 174, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnLoc)
                        .addGap(61, 61, 61)
                        .addComponent(txtTimKiem, javax.swing.GroupLayout.PREFERRED_SIZE, 174, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnTimKiem)
                        .addGap(18, 18, 18)
                        .addComponent(btnView))
                    .addComponent(jScrollPane2))
                .addContainerGap(19, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(ckChoose, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel6)
                                .addComponent(txtMaSP, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(cbChoose, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(btnLoc)
                                .addComponent(txtTimKiem, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(btnTimKiem)
                                .addComponent(btnView)))
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel7)
                                    .addComponent(txtTenSP, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(21, 21, 21)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel8)
                                    .addComponent(txtSoLuong, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel9)
                                    .addComponent(txtGiaBan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel10)
                                    .addComponent(txtNgayNhap, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel11)
                                    .addComponent(cbDanhMuc, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(38, 38, 38)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(btnThem)
                                    .addComponent(bntSua)
                                    .addComponent(btnXoa))
                                .addGap(18, 18, 18)
                                .addComponent(btnRes))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGap(24, 24, 24)
                                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 478, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(26, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Sản phẩm", jPanel2);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(btnBack)
                        .addGap(291, 291, 291)
                        .addComponent(jLabel3)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap(16, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(btnBack))
                .addGap(18, 18, 18)
                .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 561, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnUpdateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUpdateActionPerformed
        // TODO add your handling code here:
        Catelog cate = new Catelog();
        int choose = tbDM.getSelectedRow();
        try {
            if(choose == -1) {
                JOptionPane.showMessageDialog(this, "Chọn danh mục muốn !!","Error",JOptionPane.ERROR_MESSAGE);
            } else {
                cate = ltCate.get(choose);
                String nameCate = cate.getTenDM();
                cate.setMaDM(txtMaDM.getText());
                cate.setTenDM(txtTenDM.getText());
                //boolean check = false;
                int temp1 = 0;
                int temp2 = 0;
                for(Catelog o : ltCate) {
                    if (cate.getMaDM().equals(o.getMaDM())){
                        //check = true;
                        temp1++;
                    }
                    if (cate.getTenDM().equals(o.getTenDM())){
                        //check = true;
                        temp2++;
                    }
                }
                    if (temp1 >= 2 || temp2 >= 2) {
                    JOptionPane.showMessageDialog(this, "Đã tồn tại danh mục này !!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    ltCate.set(choose, cate);
                    //Đổi tên danh mục cho các sản phẩm có tên danh mục đã sửa
                    //-------------------------------------------------------
                    for(Product o : ltPr){
                        if(nameCate.equals(o.getTenDM())){
                            o.setTenDM(txtTenDM.getText());                 
                        }
                    }
                    int index = cb.getIndexOf(nameCate);
                    //Thay đổi nội dung item của ComboBox tương ứng với danh mục
                    if(index != -1){
                        String newName = txtTenDM.getText();
                        cb.removeElementAt(index);
                        cb.insertElementAt(newName, index);
                        cbs.removeElementAt(index);
                        cbs.insertElementAt(newName, index);
                        HienThiPr(ltPr);
                    }
                    //------------------------------------------------------
                    JOptionPane.showMessageDialog(this, "Cập nhật danh mục!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
                    db.saveFile(file, ltCate);
                    db.saveFile(filepr, ltPr);
                    HienThi(ltCate);
                    HienThiPr(ltPr);
                    //fix hiển thị
                    txtMaDM.setText("");
                    txtTenDM.setText("");
                }
                }
        } catch (Exception err) {
            JOptionPane.showMessageDialog(this, err.toString(),"Error",JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_btnUpdateActionPerformed

    private void btnAddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddActionPerformed
        // TODO add your handling code here:
        AddCate();
    }//GEN-LAST:event_btnAddActionPerformed

    private void tbDMMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbDMMouseClicked
        // TODO add your handling code here:
        Catelog cate = new Catelog();
        int choose = tbDM.getSelectedRow();
        if(choose != -1) {
            cate = ltCate.get(choose);
            txtMaDM.setText(cate.getMaDM());
            txtTenDM.setText(cate.getTenDM());  
        }
    }//GEN-LAST:event_tbDMMouseClicked

    private void btnDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteActionPerformed
        // TODO add your handling code here:
        Catelog cate = new Catelog();
        int choose = tbDM.getSelectedRow();//gán dòng muốn xóa vào một biến del kiểu int
        try {
            cate = ltCate.get(choose);
            if(choose == -1) {//Khi chưa chọn dòng nào
                JOptionPane.showMessageDialog(this, " Chọn danh mục muốn xóa !!","Error",JOptionPane.ERROR_MESSAGE);
            }
            else {
                int confirm = JOptionPane.showConfirmDialog(this,"Xác nhận xoá","Xác nhận",JOptionPane.YES_NO_OPTION);
                if(confirm == JOptionPane.YES_OPTION) {
                    ltCate.remove(choose);
                    //Thực hiện copy những sản phẩm cần xóa ra một array list và khi hoàn thành quá trình duyệt sẽ tiến hành xóa những sản phẩm đó
                    List<Product> productsToRemove = new ArrayList<>();
                    for(Product o : ltPr){
                        if(cate.getTenDM().equals(o.getTenDM())){
                            productsToRemove.add(o);
                        }
                    }
                    ltPr.removeAll(productsToRemove);
                    RemoveCb(cate);
                    db.saveFile(filepr, ltPr);
                    HienThiPr(ltPr);
                    //              
                    db.saveFile(file, ltCate);
                    HienThi(ltCate);
                    
                    JOptionPane.showMessageDialog(this, "Xoá thành công!","Information",JOptionPane.INFORMATION_MESSAGE);
                    //fix hiển thị
                    txtMaDM.setText("");
                    txtTenDM.setText("");
                }
            }
        } catch (Exception err) {
            JOptionPane.showMessageDialog(this, err.toString(),"Error",JOptionPane.ERROR_MESSAGE);
            //System.out.println(err.toString());
        }
    }//GEN-LAST:event_btnDeleteActionPerformed

    private void btnResetActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnResetActionPerformed
        // TODO add your handling code here:
        txtMaDM.setText("");
        txtTenDM.setText("");
    }//GEN-LAST:event_btnResetActionPerformed

    private void btnSearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSearchActionPerformed
        // TODO add your handling code here:
        ArrayList<Catelog> ltCateS = new ArrayList<>();
        ArrayList<Catelog> check = new ArrayList<>();
        Catelog cate = new Catelog();
        String choose = txtSearch.getText();
        if(choose.equals("")){
            JOptionPane.showMessageDialog(this, "Nhập tên tìm kiếm","Error",JOptionPane.ERROR_MESSAGE);
        } else {
            for(Catelog o : ltCate){
                String ten = removeDiacriticalMarks(o.getTenDM());
                String tenIP = removeDiacriticalMarks(choose);
                if(ten.contains(tenIP)) {
                    ltCateS.add(o);
                }
            }
            HienThi(ltCateS);
            txtSearch.setText("");
        }     
    }//GEN-LAST:event_btnSearchActionPerformed

    private void btnViewAllActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnViewAllActionPerformed
        // TODO add your handling code here:
        HienThi(ltCate);
    }//GEN-LAST:event_btnViewAllActionPerformed

    private void txtTimKiemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtTimKiemActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtTimKiemActionPerformed

    private void tbProductMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbProductMouseClicked
        // TODO add your handling code here:
        Product us = new Product();
        int choose = tbProduct.getSelectedRow();
        if(choose != -1) {
            us = ltPr.get(choose);
            txtMaSP.setText(us.getMaSP());
            txtTenSP.setText(us.getTenSP());
            txtSoLuong.setValue(us.getSoLuong());
            txtGiaBan.setText(String.valueOf(us.getGiaBan()));
            txtNgayNhap.setText(us.getNgayNhap());
            cbDanhMuc.setSelectedItem(us.getTenDM());   
        }
    }//GEN-LAST:event_tbProductMouseClicked

    private void btnThemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThemActionPerformed
        // TODO add your handling code here:
        AddPr();
    }//GEN-LAST:event_btnThemActionPerformed

    private void bntSuaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bntSuaActionPerformed
        Product us = new Product();
        int choose = tbProduct.getSelectedRow();
        try {
            if(choose == -1) {
                JOptionPane.showMessageDialog(this, "Chọn tài khoản muốn sửa !!","Error",JOptionPane.ERROR_MESSAGE);
            } else {
                us = ltPr.get(choose);
                us.setMaSP(txtMaSP.getText());
                us.setTenSP(txtTenSP.getText());
                us.setSoLuong((int)txtSoLuong.getValue());
                us.setNgayNhap(txtNgayNhap.getText());
                us.setGiaBan(Double.parseDouble(txtGiaBan.getText().trim()));
                us.setTenDM((String) cbDanhMuc.getSelectedItem());
                //boolean check = false;
                int temp1 = 0;
                int temp2 = 0;
                for(Product o : ltPr) {
                    if (us.getMaSP().equals(o.getMaSP())){
                        //check = true;
                        temp1++;
                    }
                    if (us.getTenSP().equals(o.getTenSP())){
                        //check = true;
                        temp2++;
                    }
                }
                System.out.print(temp1+ "\t" + temp2 );
                
                    if (temp1 >= 2 || temp2 >= 2) {
                        JOptionPane.showMessageDialog(this, "Đã tồn tại sản phẩm !!","Thông báo",JOptionPane.INFORMATION_MESSAGE);
                    }
                    else{
                    ltPr.set(choose, us);
                    JOptionPane.showMessageDialog(this, "Cập nhật sản phẩm thành công!","Thông báo",JOptionPane.INFORMATION_MESSAGE);
                    db.saveFile(filepr, ltPr);
                    HienThiPr(ltPr);
                    //fix hiển thị
                    txtMaSP.setText("");
                    txtTenSP.setText("");
                    txtSoLuong.setValue(0);
                    txtNgayNhap.setText("");
                    txtGiaBan.setText("");
                    }
            }
        } catch (Exception err) {
            JOptionPane.showMessageDialog(this, err.toString(),"Error",JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_bntSuaActionPerformed

    private void btnXoaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnXoaActionPerformed
        Product us = new Product();
        int choose = tbProduct.getSelectedRow();//gán dòng muốn xóa vào một biến del kiểu int
        try {           
            if(choose == -1) {//Khi chưa chọn dòng nào
                JOptionPane.showMessageDialog(this, " Chọn sản phẩm cần xoá","Error",JOptionPane.ERROR_MESSAGE);
            }
            else {
                us = ltPr.get(choose);
                int confirm = JOptionPane.showConfirmDialog(this,"Xác nhận xoá","Xác nhận",JOptionPane.YES_NO_OPTION);
                if(confirm == JOptionPane.YES_OPTION) {
                    ltPr.remove(choose);
                    db.saveFile(filepr, ltPr);
                    HienThiPr(ltPr);
                    JOptionPane.showMessageDialog(this, "Xoá thành công!","Information",JOptionPane.INFORMATION_MESSAGE);
                    //fix hiển thị
                    txtMaSP.setText("");
                    txtTenSP.setText("");
                    txtSoLuong.setValue(0);
                    txtNgayNhap.setText("");
                    txtGiaBan.setText(""); 
                }
            }
        } catch (Exception err) {
            JOptionPane.showMessageDialog(this, err.toString(),"Error",JOptionPane.ERROR_MESSAGE);
            //System.out.println(err.toString());
        }
    }//GEN-LAST:event_btnXoaActionPerformed

    private void btnViewActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnViewActionPerformed
        HienThiPr(ltPr);
    }//GEN-LAST:event_btnViewActionPerformed

    private void btnLocActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLocActionPerformed
        ArrayList<Product> ltPrF = new ArrayList<>();
        ArrayList<Product> check = new ArrayList<>();
        Product pr = new Product();
        String choose = (String) cbChoose.getSelectedItem();       
            for(Product o : ltPr){
                if(o.getTenDM().equals(choose)){
                    ltPrF.add(o);
                }
            }
            HienThiPr(ltPrF);
            txtSearch.setText("");
    }//GEN-LAST:event_btnLocActionPerformed

    private void btnTimKiemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTimKiemActionPerformed
        if(ckChoose.isSelected() == true){
            ArrayList<Product> ltPrS = new ArrayList<>();
            ArrayList<Product> check = new ArrayList<>();
            String cbchoose = (String) cbChoose.getSelectedItem();//--> Đưa ra danh mục được chọn
            Product pr = new Product();
            String choose = txtTimKiem.getText();
            if (choose.equals("")) {
                JOptionPane.showMessageDialog(this, "Nhập tên tìm kiếm", "Error", JOptionPane.ERROR_MESSAGE);
            } else {
                for (Product o : ltPr) {
                    //Xử lý tên sản phẩm tìm kiếm và tên sản phẩm trong arraylist
                    String ten = removeDiacriticalMarks(o.getTenSP());
                    String tenIP = removeDiacriticalMarks(choose);
                    //Xử lý danh mục trong combobox và danh mục của sản phẩm
                    String catepr = o.getTenDM();
                    if (ten.contains(tenIP) && cbchoose.equals(catepr)) {
                        ltPrS.add(o);
                    }
                }
                HienThiPr(ltPrS);
                txtSearch.setText("");
            }     
        }
        else{
            ArrayList<Product> ltPrS = new ArrayList<>();
            ArrayList<Product> check = new ArrayList<>();
            Product pr = new Product();
            String choose = txtTimKiem.getText().trim();
            if (choose.equals("")) {
                JOptionPane.showMessageDialog(this, "Nhập tên tìm kiếm", "Error", JOptionPane.ERROR_MESSAGE);
            } else {
                for (Product o : ltPr) {
                    String ten = removeDiacriticalMarks(o.getTenSP());
                    String tenIP = removeDiacriticalMarks(choose);
                    if (ten.contains(tenIP)) {
                        ltPrS.add(o);
                    }
                }
                HienThiPr(ltPrS);
                txtSearch.setText("");
            }     
        }
    }//GEN-LAST:event_btnTimKiemActionPerformed

    private void btnBackActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBackActionPerformed
        Menu gd = new Menu();//Khởi tạo một đối tượng đại diện cho cửa sổ menu
        gd.setVisible(true);//Hiển thị giao diện menu
        gd.setLocationRelativeTo(null);//Giao diện menu được hiển thị ở giữa màn hình
        ManageDMSP.this.dispose();//Đóng cửa sổ đăng nhập
    }//GEN-LAST:event_btnBackActionPerformed

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
            java.util.logging.Logger.getLogger(ManageDMSP.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ManageDMSP.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ManageDMSP.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ManageDMSP.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new ManageDMSP().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton bntSua;
    private javax.swing.JButton btnAdd;
    private javax.swing.JButton btnBack;
    private javax.swing.JButton btnDelete;
    private javax.swing.JButton btnLoc;
    private javax.swing.JButton btnRes;
    private javax.swing.JButton btnReset;
    private javax.swing.JButton btnSearch;
    private javax.swing.JButton btnThem;
    private javax.swing.JButton btnTimKiem;
    private javax.swing.JButton btnUpdate;
    private javax.swing.JButton btnView;
    private javax.swing.JButton btnViewAll;
    private javax.swing.JButton btnXoa;
    private javax.swing.JComboBox<String> cbChoose;
    private javax.swing.JComboBox<String> cbDanhMuc;
    private javax.swing.JCheckBox ckChoose;
    private javax.swing.JDesktopPane jDesktopPane1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JToggleButton jToggleButton1;
    private javax.swing.JTable tbDM;
    private javax.swing.JTable tbProduct;
    private javax.swing.JTextField txtGiaBan;
    private javax.swing.JTextField txtMaDM;
    private javax.swing.JTextField txtMaSP;
    private javax.swing.JTextField txtNgayNhap;
    private javax.swing.JTextField txtSearch;
    private javax.swing.JSpinner txtSoLuong;
    private javax.swing.JTextField txtTenDM;
    private javax.swing.JTextField txtTenSP;
    private javax.swing.JTextField txtTimKiem;
    // End of variables declaration//GEN-END:variables
}
