/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package Order;
import javax.swing.*;
import Manage_DMSP.Product;
import Order.Order;
import static Manage_User.ManageUser.removeDiacriticalMarks;
import Menu.Menu;
import RWFile.RWFileIO;
import java.util.ArrayList;
import javax.swing.DefaultListModel;
import javax.swing.JOptionPane;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.Rectangle;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JScrollPane;
import java.awt.Dimension;
import java.awt.Color;
import java.awt.Component;
import javax.swing.DefaultListCellRenderer;
import java.awt.event.MouseMotionAdapter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import javax.swing.table.DefaultTableModel;
import java.text.Normalizer;
/**
 *
 * @author nhata
 */
public class OrderProcess extends javax.swing.JFrame {
    String filepr = "E:products.txt";
    String fileor = "E:order.txt";
    RWFileIO db = new RWFileIO();
    ArrayList<Order> ltOr = new ArrayList<>();
    //Arrlist temp
    ArrayList<Product> ltPrT = new ArrayList<>();
    ArrayList<Product> ltPr = new ArrayList<>();
    DefaultListModel<String> model = new DefaultListModel<>();
    //List trống
    DefaultListModel<String> modelEmpty = new DefaultListModel<>();
    DefaultTableModel tbmodel = new DefaultTableModel();
    //Tổng tiền đơn hàng
    double total = 0;
    
    public void initTable() {
        String[] columns = {"Mã sản phẩm", "Tên sản phẩm", "Số lượng mua", "Giá bán", "Tổng tiền"};
        tbmodel.setColumnIdentifiers(columns);
        tbDSSP.setModel(tbmodel);
    }
    public void HienThi(ArrayList<Product> ltPrT){
        tbmodel.setRowCount(0);        
            for (Product pr : ltPrT) {
                Object[] rowData = {pr.getMaSP(), pr.getTenSP(), pr.getSoLuongMua(), pr.getGiaBan(), pr.getTongTien()};
                tbmodel.addRow(rowData);
            }
    }
    
    
    //Đọc dữ liệu từ file và lưu vào một arrlist
    public void GetData(){
        try {
                ltPr = (ArrayList<Product>) db.readFile(filepr);//Đưa đối tượng đọc được trong file vào arrayList đã tạo
        } catch (Exception err) {
                JOptionPane.showMessageDialog(this, err.toString(),"Error",JOptionPane.ERROR_MESSAGE);
        }
    }
    public void GetDataOr(){
        try {
                ltOr = (ArrayList<Order>) db.readFile(fileor);//Đưa đối tượng đọc được trong file vào arrayList đã tạo
        } catch (Exception err) {
                JOptionPane.showMessageDialog(this, err.toString(),"Error",JOptionPane.ERROR_MESSAGE);
        }
    }
    //Đưa dữ liêu từ arrlist vào jlist
    public void TransData(){ 
        model.removeAllElements();
        for(Product o : ltPr){
            String input = removeDiacriticalMarks(txtTimKiem.getText().trim());
            String innerTS = removeDiacriticalMarks(o.getTenSP());
            String innerMS = removeDiacriticalMarks(o.getMaSP());
            if(ckTen.isSelected() == true && ckMa.isSelected() == false){
                if(innerTS.contains(input)) {
                    model.addElement(o.getTenSP());
                }
            }
            if(ckTen.isSelected() == false && ckMa.isSelected() == true){
                if(innerMS.contains(input)) {
                    model.addElement(o.getMaSP());
                }
            }
        }
    }
     
    //DocumentListener cho ô tìm kiếm
    public void XuLyEvtSearch(){
        //lSanPham.setModel(model);
        txtTimKiem.getDocument().addDocumentListener(new DocumentListener(){
            @Override
            public void insertUpdate(DocumentEvent e) {
                if(txtTimKiem.getText().trim().equals("")){
                    lSanPham.setModel(modelEmpty);
                    splSP.setVisible(false);
                }
                else{            
                    TransData();
                    if(model.isEmpty() == false){
                        lSanPham.setModel(model);
                        //lSanPham.setVisible(true);
                        splSP.setVisible(true);                       
                    }         
                }
            }
            @Override
            public void removeUpdate(DocumentEvent e) {
                if(txtTimKiem.getText().trim().equals("")){                 
                    lSanPham.setModel(modelEmpty);
                    splSP.setVisible(false);             
                }
                else{
                    TransData();
                    if(model.isEmpty() == false){
                        lSanPham.setModel(model);
                        //lSanPham.setVisible(true);
                        splSP.setVisible(true);                       
                    }
                }
            }
            @Override
            public void changedUpdate(DocumentEvent e) {
            }
        });
    }
    public void XuLyMouClick(){
        lSanPham.addMouseListener(new MouseAdapter(){         
        });
    }
    public void XuLyItems(){
            lSanPham.addMouseMotionListener(new MouseMotionAdapter() {
                @Override
                public void mouseMoved(MouseEvent e) {
                    // Lấy chỉ mục của mục mà chuột đang chỉ vào
                    int index = lSanPham.locationToIndex(e.getPoint());

                    // Kiểm tra xem chỉ mục có hợp lệ không
                    if (index != -1) {
                        // Đặt renderer cho JList để tô đậm nền của mục hiện tại
                        lSanPham.setCellRenderer(new DefaultListCellRenderer() {
                            @Override
                            public Component getListCellRendererComponent(JList<?> list, Object value, int idx, boolean isSelected, boolean cellHasFocus) {
                                Component renderer = super.getListCellRendererComponent(list, value, idx, isSelected, cellHasFocus);

                                // Kiểm tra xem mục hiện tại có phải là mục đang được chỉ chuột vào hay không
                                if (idx == index) {
                                    renderer.setBackground(Color.YELLOW); // Đặt màu nền tùy ý
                                } else {
                                    renderer.setBackground(list.getBackground());
                                }

                                return renderer;
                            }
                        });
                    }
                }
            });
    }
    public void KhoaChinhSua(){
        txtMaSP.setFocusable(false);
        txtTenSP.setFocusable(false);
        txtSoLuongCo.setFocusable(false);
        txtGiaBan.setFocusable(false);
        txtMaSP.setEditable(false);
        txtTenSP.setEditable(false);
        txtSoLuongCo.setEditable(false);
        txtGiaBan.setEditable(false);
    }
    public void ThemSanPham(){
        String maSP = txtMaSP.getText();
        String tenSP = txtTenSP.getText();
        String soLuongCo = txtSoLuongCo.getText();
        String giaBanText = txtGiaBan.getText();
        if (!giaBanText.isEmpty()) {
            double giaBan = Double.parseDouble(giaBanText);
            int soLuongMua =(int) spSLM.getValue();
        double tongTien = soLuongMua * giaBan;
        int slm = (int) spSLM.getValue();
            if(slm > 0){
                Product pr = new Product(maSP, tenSP, soLuongMua, giaBan, tongTien);
                ltPrT.add(pr);
                total = 0;
                for(Product o : ltPrT){
                    total += o.getTongTien();
                } 
                txtTienDH.setText(Double.toString(total));
                HienThi(ltPrT);
                spSLM.setValue(1);
            }
            else{
                JOptionPane.showMessageDialog(null, "Số lượng phải lớn hơn 0 !!", "Error", JOptionPane.WARNING_MESSAGE);
            }
        } else {
            // Hiển thị thông báo lỗi hoặc xử lý sai khi trường rỗng
            JOptionPane.showMessageDialog(null, "Chọn sản phẩm !!!!", "Error", JOptionPane.WARNING_MESSAGE);
        }
    }
    /**
     * Creates new form OrderProcess
     */
    public OrderProcess() {
        //splSP.setVisible(false);
        initComponents();
        //splSP.setPreferredSize(new Dimension(0, 0));
        //lSanPham.setVisible(false);
        //model.clear();    
        GetData();
        GetDataOr();
        initTable();
        KhoaChinhSua();
        XuLyEvtSearch();
        XuLyItems();
        HienThi(ltPrT);
        //splSP.setVisible(false);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        btnbk = new javax.swing.JButton();
        ckTen = new javax.swing.JCheckBox();
        ckMa = new javax.swing.JCheckBox();
        txtTimKiem = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        txtGiaBan = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        spSLM = new javax.swing.JSpinner();
        btnThem = new javax.swing.JButton();
        btnSua = new javax.swing.JButton();
        btnXoa = new javax.swing.JButton();
        jSeparator2 = new javax.swing.JSeparator();
        jSeparator3 = new javax.swing.JSeparator();
        jCheckBox3 = new javax.swing.JCheckBox();
        btnThanhToan = new javax.swing.JButton();
        jSeparator4 = new javax.swing.JSeparator();
        jScrollPane1 = new javax.swing.JScrollPane();
        tbDSSP = new javax.swing.JTable();
        jLabel7 = new javax.swing.JLabel();
        txtTienDH = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        lpSanPham = new javax.swing.JLayeredPane();
        splSP = new javax.swing.JScrollPane();
        lSanPham = new javax.swing.JList<>();
        txtTenSP = new javax.swing.JTextField();
        txtMaSP = new javax.swing.JTextField();
        txtSoLuongCo = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBounds(new java.awt.Rectangle(10, 10, 1000, 1000));
        addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                formMousePressed(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel1.setText("XỬ LÝ ĐƠN HÀNG");

        btnbk.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        btnbk.setText("Back");
        btnbk.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnbkActionPerformed(evt);
            }
        });

        ckTen.setText("Tên sản phẩm");

        ckMa.setText("Mã sản phẩm");

        txtTimKiem.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        txtTimKiem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtTimKiemActionPerformed(evt);
            }
        });

        jLabel2.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(153, 153, 153));
        jLabel2.setText("Mã sản phẩm");

        jLabel3.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(153, 153, 153));
        jLabel3.setText("Tên sản phẩm");

        jLabel4.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(153, 153, 153));
        jLabel4.setText("Số lượng có");

        jLabel5.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(153, 153, 153));
        jLabel5.setText("Giá bán");

        txtGiaBan.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        txtGiaBan.setForeground(new java.awt.Color(153, 153, 153));

        jLabel6.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel6.setText("Số lượng mua");

        spSLM.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N

        btnThem.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        btnThem.setText("Thêm");
        btnThem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThemActionPerformed(evt);
            }
        });

        btnSua.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        btnSua.setText("Sửa");
        btnSua.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSuaActionPerformed(evt);
            }
        });

        btnXoa.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        btnXoa.setText("Xóa");
        btnXoa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnXoaActionPerformed(evt);
            }
        });

        jCheckBox3.setText("Hóa đơn");

        btnThanhToan.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        btnThanhToan.setText("Thanh toán");
        btnThanhToan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThanhToanActionPerformed(evt);
            }
        });

        jSeparator4.setOrientation(javax.swing.SwingConstants.VERTICAL);

        tbDSSP.setModel(new javax.swing.table.DefaultTableModel(
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
        tbDSSP.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbDSSPMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tbDSSP);

        jLabel7.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel7.setText("Tổng tiền: ");

        txtTienDH.setBackground(new java.awt.Color(255, 255, 204));
        txtTienDH.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        txtTienDH.setForeground(new java.awt.Color(255, 0, 51));
        txtTienDH.setBorder(null);
        txtTienDH.setSelectedTextColor(new java.awt.Color(255, 0, 0));

        jLabel8.setBackground(new java.awt.Color(204, 255, 255));
        jLabel8.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel8.setText("Tìm kiếm");

        splSP.setOpaque(false);
        splSP.setViewportView(lSanPham);

        lSanPham.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        lSanPham.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lSanPhamMouseClicked(evt);
            }
        });
        splSP.setViewportView(lSanPham);

        txtTenSP.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        txtTenSP.setForeground(new java.awt.Color(153, 153, 153));
        txtTenSP.setOpaque(true);
        txtTenSP.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtTenSPActionPerformed(evt);
            }
        });

        txtMaSP.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        txtMaSP.setForeground(new java.awt.Color(153, 153, 153));
        txtMaSP.setOpaque(true);
        txtMaSP.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtMaSPActionPerformed(evt);
            }
        });

        txtSoLuongCo.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        txtSoLuongCo.setForeground(new java.awt.Color(153, 153, 153));
        txtSoLuongCo.setOpaque(true);
        txtSoLuongCo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtSoLuongCoActionPerformed(evt);
            }
        });

        lpSanPham.setLayer(splSP, javax.swing.JLayeredPane.PALETTE_LAYER);
        lpSanPham.setLayer(txtTenSP, javax.swing.JLayeredPane.DEFAULT_LAYER);
        lpSanPham.setLayer(txtMaSP, javax.swing.JLayeredPane.DEFAULT_LAYER);
        lpSanPham.setLayer(txtSoLuongCo, javax.swing.JLayeredPane.DEFAULT_LAYER);

        javax.swing.GroupLayout lpSanPhamLayout = new javax.swing.GroupLayout(lpSanPham);
        lpSanPham.setLayout(lpSanPhamLayout);
        lpSanPhamLayout.setHorizontalGroup(
            lpSanPhamLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(lpSanPhamLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(lpSanPhamLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtMaSP, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtTenSP, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtSoLuongCo, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(127, Short.MAX_VALUE))
            .addGroup(lpSanPhamLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(lpSanPhamLayout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(splSP, javax.swing.GroupLayout.PREFERRED_SIZE, 291, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );
        lpSanPhamLayout.setVerticalGroup(
            lpSanPhamLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(lpSanPhamLayout.createSequentialGroup()
                .addContainerGap(19, Short.MAX_VALUE)
                .addComponent(txtMaSP, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(txtTenSP, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(28, 28, 28)
                .addComponent(txtSoLuongCo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(30, 30, 30))
            .addGroup(lpSanPhamLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(lpSanPhamLayout.createSequentialGroup()
                    .addComponent(splSP, javax.swing.GroupLayout.DEFAULT_SIZE, 167, Short.MAX_VALUE)
                    .addContainerGap()))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(57, 57, 57)
                                .addComponent(btnThanhToan))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(ckTen)
                                        .addGap(18, 18, 18)
                                        .addComponent(ckMa))
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(44, 44, 44)
                                        .addComponent(txtTimKiem, javax.swing.GroupLayout.PREFERRED_SIZE, 293, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jLabel6)
                                        .addGap(12, 12, 12)
                                        .addComponent(spSLM, javax.swing.GroupLayout.PREFERRED_SIZE, 77, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(layout.createSequentialGroup()
                                        .addGap(71, 71, 71)
                                        .addComponent(btnThem))
                                    .addGroup(layout.createSequentialGroup()
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel5)
                                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addGroup(layout.createSequentialGroup()
                                                    .addComponent(jLabel2)
                                                    .addGap(2, 2, 2))
                                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                    .addComponent(jLabel3)
                                                    .addComponent(jLabel4))))
                                        .addGap(12, 12, 12)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(txtGiaBan, javax.swing.GroupLayout.PREFERRED_SIZE, 169, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(lpSanPham, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 54, Short.MAX_VALUE)
                                .addComponent(jSeparator4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(48, 48, 48)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 630, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jLabel7)
                                        .addGap(18, 18, 18)
                                        .addComponent(txtTienDH, javax.swing.GroupLayout.PREFERRED_SIZE, 143, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGap(26, 26, 26)))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jSeparator3, javax.swing.GroupLayout.PREFERRED_SIZE, 208, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jCheckBox3)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(btnSua)
                                .addGap(64, 64, 64)
                                .addComponent(btnXoa))
                            .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 208, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
            .addGroup(layout.createSequentialGroup()
                .addGap(36, 36, 36)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 1152, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(24, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(btnbk)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel1)
                        .addGap(275, 275, 275))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnbk)
                    .addComponent(jLabel1))
                .addGap(24, 24, 24)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 13, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(ckTen)
                                    .addComponent(ckMa))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txtTimKiem, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGap(26, 26, 26)
                                        .addComponent(jLabel2)
                                        .addGap(26, 26, 26)
                                        .addComponent(jLabel3)
                                        .addGap(33, 33, 33)
                                        .addComponent(jLabel4))
                                    .addGroup(layout.createSequentialGroup()
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(lpSanPham, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGap(65, 65, 65)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGap(3, 3, 3)
                                        .addComponent(jLabel5))
                                    .addComponent(txtGiaBan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(26, 26, 26)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGap(3, 3, 3)
                                        .addComponent(jLabel6))
                                    .addComponent(spSLM, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(100, 100, 100)
                                .addComponent(btnThem)
                                .addGap(18, 18, 18)
                                .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(btnSua)
                                    .addComponent(btnXoa)))
                            .addComponent(jSeparator4, javax.swing.GroupLayout.PREFERRED_SIZE, 555, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addComponent(jSeparator3, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jCheckBox3)
                        .addGap(18, 18, 18)
                        .addComponent(btnThanhToan))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 461, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(29, 29, 29)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel7)
                            .addComponent(txtTienDH, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(32, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnSuaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSuaActionPerformed
        // TODO add your handling code here:
        Product us = new Product();
        int choose = tbDSSP.getSelectedRow();
        try {
            if(choose == -1) {
                JOptionPane.showMessageDialog(this, "Chọn sản phẩm muốn sửa !!","Error",JOptionPane.ERROR_MESSAGE);
            } else {
                us = ltPrT.get(choose);
                us.setMaSP(txtMaSP.getText());
                us.setTenSP(txtTenSP.getText());
                us.setSoLuong(Integer.parseInt(txtSoLuongCo.getText()));
                us.setGiaBan(Double.parseDouble(txtGiaBan.getText()));
                us.setSoLuongMua((int)spSLM.getValue());
                
                //boolean check = false;
                if(us.getSoLuongMua() > 0){
                    us.setTongTien((int)spSLM.getValue());   
                    ltPr.set(choose, us);
                    total = 0;
                    for(Product o : ltPrT){
                        total += o.getTongTien();
                    }                 
                    JOptionPane.showMessageDialog(this, "Cập nhật sản phẩm thành công!","Thông báo",JOptionPane.INFORMATION_MESSAGE);
                    txtTienDH.setText(Double.toString(total));
                    HienThi(ltPrT);
                    txtMaSP.setText("");
                    txtTenSP.setText("");
                    txtSoLuongCo.setText("");
                    spSLM.setValue(0);
                    txtGiaBan.setText("");
                }  
                else{
                    JOptionPane.showMessageDialog(this, "Số lượng phải lớn hơn 0!","Thông báo",JOptionPane.ERROR_MESSAGE);
                }
            }
        } catch (Exception err) {
            JOptionPane.showMessageDialog(this, err.toString(),"Error",JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_btnSuaActionPerformed

    private void txtTimKiemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtTimKiemActionPerformed

    }//GEN-LAST:event_txtTimKiemActionPerformed

    private void txtSoLuongCoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtSoLuongCoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtSoLuongCoActionPerformed

    private void txtMaSPActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtMaSPActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtMaSPActionPerformed

    private void txtTenSPActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtTenSPActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtTenSPActionPerformed

    private void formMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_formMousePressed
        // TODO add your handling code here:
        splSP.setVisible(false);
    }//GEN-LAST:event_formMousePressed

    private void lSanPhamMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lSanPhamMouseClicked
        // TODO add your handling code here:
//        int index = lSanPham.locationToIndex(evt.getPoint());
//        if (index != -1) {
//            // Đặt renderer cho JList để tô đậm nền của mục hiện tại
//            lSanPham.setCellRenderer(new DefaultListCellRenderer() {
//                @Override
//                public Component getListCellRendererComponent(JList<?> list, Object value, int idx, boolean isSelected, boolean cellHasFocus) {
//                    Component renderer = super.getListCellRendererComponent(list, value, idx, isSelected, cellHasFocus);
//
//                    // Kiểm tra xem mục hiện tại có phải là mục đang được chỉ chuột vào hay không
//                    if (idx == index) {
//                        renderer.setBackground(Color.YELLOW); // Đặt màu nền tùy ý
//                    } else {
//                        renderer.setBackground(list.getBackground());
//                    }
//                    
//                    return renderer;
//                }
//            });
//        }
        if (evt.getClickCount() == 1) {
            // Lấy item được chọn
            String selectedValue = lSanPham.getSelectedValue();
            for (Product o : ltPr) {
                if (selectedValue == o.getTenSP()) {
                    txtMaSP.setText(o.getMaSP());
                    txtTenSP.setText(o.getTenSP());
                    txtSoLuongCo.setText(String.valueOf(o.getSoLuong()));
                    txtGiaBan.setText(String.valueOf(o.getGiaBan()));
                }
            }
            splSP.setVisible(false);
            //txtTimKiem.setText("");
        }
    }//GEN-LAST:event_lSanPhamMouseClicked

    private void btnThemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThemActionPerformed
        // TODO add your handling code here:
        ThemSanPham();
    }//GEN-LAST:event_btnThemActionPerformed

    private void tbDSSPMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbDSSPMouseClicked
        // TODO add your handling code here:
        Product us = new Product();
        int choose = tbDSSP.getSelectedRow();
        if(choose != -1) {
            us = ltPrT.get(choose);
            txtMaSP.setText(us.getMaSP());
            txtTenSP.setText(us.getTenSP());
            txtSoLuongCo.setText(String.valueOf(us.getSoLuong()));
            txtGiaBan.setText(String.valueOf(us.getGiaBan()));
            spSLM.setValue(us.getSoLuongMua());   
        }
    }//GEN-LAST:event_tbDSSPMouseClicked

    private void btnXoaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnXoaActionPerformed
        // TODO add your handling code here:
        Product us = new Product();
        int choose = tbDSSP.getSelectedRow();//gán dòng muốn xóa vào một biến del kiểu int
        try {
            if(choose == -1) {//Khi chưa chọn dòng nào
                JOptionPane.showMessageDialog(this, " Chọn sản phẩm cần xoá","Error",JOptionPane.INFORMATION_MESSAGE);
            }
            else {
                us = ltPrT.get(choose);
                int confirm = JOptionPane.showConfirmDialog(this,"Xác nhận xoá","Xác nhận",JOptionPane.YES_NO_OPTION);
                if(confirm == JOptionPane.YES_OPTION) {
                    ltPrT.remove(choose);
                    total = 0;
                    for(Product o : ltPrT){
                        total += o.getTongTien();
                    }  
                    HienThi(ltPrT);
                    JOptionPane.showMessageDialog(this, "Xoá thành công!","Information",JOptionPane.INFORMATION_MESSAGE);
                    txtTienDH.setText(Double.toString(total));
                    //fix hiển thị
                    txtMaSP.setText("");
                    txtTenSP.setText("");
                    txtSoLuongCo.setText("");
                    spSLM.setValue(0);
                    txtGiaBan.setText(""); 
                }
            }
        } catch (Exception err) {
            JOptionPane.showMessageDialog(this, err.toString(),"Error",JOptionPane.ERROR_MESSAGE);
            //System.out.println(err.toString());
        }
    }//GEN-LAST:event_btnXoaActionPerformed

    private void btnThanhToanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThanhToanActionPerformed
        // TODO add your handling code here:\
        try{
            if(ltPrT.isEmpty()){
                JOptionPane.showMessageDialog(this, "Chưa có sản phẩm nào !!","Error",JOptionPane.ERROR_MESSAGE);
            }
            else{
                int confirm = JOptionPane.showConfirmDialog(this,"Xác nhận thanh toán","Xác nhận",JOptionPane.YES_NO_OPTION);
                if(confirm == JOptionPane.YES_OPTION) {
                    Order odr = new Order(total, ltPrT);
                    ltOr.add(odr);
                    db.saveFile(fileor, ltOr);
                    JOptionPane.showMessageDialog(this, "Thanh toán thành công !!","Error",JOptionPane.INFORMATION_MESSAGE);
                    ltPrT.clear();
                    HienThi(ltPrT);
                    txtMaSP.setText("");
                    txtTenSP.setText("");
                    txtSoLuongCo.setText("");
                    spSLM.setValue(0);
                    txtGiaBan.setText("");
                    total = 0;
                    txtTienDH.setText(Double.toString(total));
                }
            }
        } catch(Exception err) {
            JOptionPane.showMessageDialog(this, err.toString(),"Error",JOptionPane.INFORMATION_MESSAGE);
        }
    }//GEN-LAST:event_btnThanhToanActionPerformed

    private void btnbkActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnbkActionPerformed
        // TODO add your handling code here:
        Menu gd = new Menu();//Khởi tạo một đối tượng đại diện cho cửa sổ menu
        gd.setVisible(true);//Hiển thị giao diện menu
        gd.setLocationRelativeTo(null);//Giao diện menu được hiển thị ở giữa màn hình
        OrderProcess.this.dispose();//Đóng cửa sổ đăng nhập 
    }//GEN-LAST:event_btnbkActionPerformed

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
            java.util.logging.Logger.getLogger(OrderProcess.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(OrderProcess.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(OrderProcess.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(OrderProcess.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new OrderProcess().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnSua;
    private javax.swing.JButton btnThanhToan;
    private javax.swing.JButton btnThem;
    private javax.swing.JButton btnXoa;
    private javax.swing.JButton btnbk;
    private javax.swing.JCheckBox ckMa;
    private javax.swing.JCheckBox ckTen;
    private javax.swing.JCheckBox jCheckBox3;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JSeparator jSeparator4;
    private javax.swing.JList<String> lSanPham;
    private javax.swing.JLayeredPane lpSanPham;
    private javax.swing.JSpinner spSLM;
    private javax.swing.JScrollPane splSP;
    private javax.swing.JTable tbDSSP;
    private javax.swing.JTextField txtGiaBan;
    private javax.swing.JTextField txtMaSP;
    private javax.swing.JTextField txtSoLuongCo;
    private javax.swing.JTextField txtTenSP;
    private javax.swing.JTextField txtTienDH;
    private javax.swing.JTextField txtTimKiem;
    // End of variables declaration//GEN-END:variables
}
