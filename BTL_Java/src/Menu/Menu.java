/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package Menu;
import Manage_DMSP.ManageDMSP;
import Manage_User.ManageUser;
import Order.ManageOrder;
import Order.OrderProcess;
import Manage_User.User;
import RWFile.RWFileIO;
import java.util.ArrayList;
import javax.swing.JOptionPane;

/**
 *
 * @author nhata
 */
public class Menu extends javax.swing.JFrame {
    ArrayList<User> ltUser;//Khởi tạo một arraylist chứa các user
    String file = "C:user.txt";
    RWFileIO db = new RWFileIO();
    private boolean check = false;
    
    public void GetData(){ // Đưa dữ liệu từ file txt vào ArrayList
        try {
            ltUser = (ArrayList<User>) db.readFile(file);//Đưa đối tượng đọc được trong file vào arrayList đã tạo
        } catch (Exception err) {
            JOptionPane.showMessageDialog(this, err.toString(),"Error",JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Creates new form Menu
     */
    public Menu() {
        initComponents();
        GetData();
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
        jLabel2 = new javax.swing.JLabel();
        btnQLND = new javax.swing.JButton();
        btnLog = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        btnQLDMSP = new javax.swing.JButton();
        btnQLDH = new javax.swing.JButton();
        btnTTDH = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        jLabel1.setText("MENU");

        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/menu.png"))); // NOI18N

        btnQLND.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        btnQLND.setText("Quản lý người dùng");
        btnQLND.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnQLNDActionPerformed(evt);
            }
        });

        btnLog.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/power.png"))); // NOI18N
        btnLog.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLogActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 78, Short.MAX_VALUE)
        );

        btnQLDMSP.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        btnQLDMSP.setText("Quản lý danh mục và sản phẩm");
        btnQLDMSP.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnQLDMSPActionPerformed(evt);
            }
        });

        btnQLDH.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        btnQLDH.setText("Quản lý đơn hàng");
        btnQLDH.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnQLDHActionPerformed(evt);
            }
        });

        btnTTDH.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        btnTTDH.setText("Thanh toán đơn hàng");
        btnTTDH.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTTDHActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(btnQLND, javax.swing.GroupLayout.PREFERRED_SIZE, 281, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jLabel2)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jLabel1)))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(btnTTDH, javax.swing.GroupLayout.DEFAULT_SIZE, 283, Short.MAX_VALUE)
                                    .addComponent(btnQLDH, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addGap(206, 206, 206)))
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnLog, javax.swing.GroupLayout.DEFAULT_SIZE, 44, Short.MAX_VALUE))
                        .addGap(14, 14, 14))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(btnQLDMSP, javax.swing.GroupLayout.PREFERRED_SIZE, 283, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel1))
                .addGap(37, 37, 37)
                .addComponent(btnQLND)
                .addGap(28, 28, 28)
                .addComponent(btnQLDMSP)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(35, 35, 35)
                        .addComponent(btnQLDH)))
                .addGap(15, 15, 15)
                .addComponent(btnTTDH)
                .addGap(33, 33, 33)
                .addComponent(btnLog)
                .addGap(14, 14, 14))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnQLNDActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnQLNDActionPerformed
        // TODO add your handling code here:
        //Xử lý sự kiện cho nút quản lý tài khoản
        String maNV = GD_DangNhap.getMaNV();
        for(User o : ltUser) {//Đọc lần lượt các đối tượng user trong listUser
            if(maNV.equals(o.getMaNV())){//so sánh mã nhân viên đăng nhập vào hệ thống lần lượt với các mã nhân viên của các đối tượng trong list
                check = true;//Gán biến xác nhận thành true
                break;
            }
        }
        if(check){ //Kiểm tra biến xác nhận
            if(maNV.equals("ADMIN")){//So sanh role với chức vụ "Master"
                ManageUser gd = new ManageUser();//Tạo mới một đối tượng đại diện cho cửa sổ quản lý tài khoản 
                gd.setVisible(true);//Hiển thị giao diện quản lý tài khoản
                gd.setLocationRelativeTo(null);//vị trí hiển thị ở giữa màn hình
                Menu.this.dispose(); //tắt cửa sổ menu
            } else {
                JOptionPane.showMessageDialog(null, "Chỉ admin mới có quyền sử dụng chức năng này !!","Error",JOptionPane.INFORMATION_MESSAGE);
            }         
        } 
    }//GEN-LAST:event_btnQLNDActionPerformed

    private void btnLogActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLogActionPerformed
        // TODO add your handling code here:
        int confirm = JOptionPane.showConfirmDialog(this,"Xác nhận đăng xuất","Xác nhận",JOptionPane.YES_NO_OPTION);
                if(confirm == JOptionPane.YES_OPTION) {
                GD_DangNhap gd = new GD_DangNhap();
                gd.setVisible(true);//Hiển thị giao diện
                gd.setLocationRelativeTo(null);
                Menu.this.dispose();//Đóng cửa sổ menu
                }
    }//GEN-LAST:event_btnLogActionPerformed

    private void btnQLDMSPActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnQLDMSPActionPerformed
        String maNV = GD_DangNhap.getMaNV();//Lấy giá trị mã nhân viên từ cửa sổ login và gán cho biến code
        for(User o : ltUser) {//Đọc lần lượt các đối tượng user trong listUser
            if(maNV.equals(o.getMaNV())){//so sánh mã nhân viên đăng nhập vào hệ thống lần lượt với các mã nhân viên của các đối tượng trong list
                check = true;//Gán biến xác nhận thành true
                break;
            }
        }
        if(check){ //Kiểm tra biến xác nhận
            if(maNV.equals("ADMIN")){//So sanh role với chức vụ "Master"
                JOptionPane.showMessageDialog(null, "Chỉ quản lý mới có quyền sử dụng chức năng này !!","Error",JOptionPane.INFORMATION_MESSAGE);
            } else {
                ManageDMSP gd = new ManageDMSP();//Khởi tạo một đối tượng đại diện cho cửa sổ menu
                gd.setVisible(true);//Hiển thị giao diện menu
                gd.setLocationRelativeTo(null);//Giao diện menu được hiển thị ở giữa màn hình
                Menu.this.dispose();//Đóng cửa sổ đăng nhập           
            }         
        } 
    }//GEN-LAST:event_btnQLDMSPActionPerformed

    private void btnQLDHActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnQLDHActionPerformed
        // TODO add your handling code here:
        String maNV = GD_DangNhap.getMaNV();//Lấy giá trị mã nhân viên từ cửa sổ login và gán cho biến code
        for(User o : ltUser) {//Đọc lần lượt các đối tượng user trong listUser
            if(maNV.equals(o.getMaNV())){//so sánh mã nhân viên đăng nhập vào hệ thống lần lượt với các mã nhân viên của các đối tượng trong list
                check = true;//Gán biến xác nhận thành true
                break;
            }
        }
        if(check){ //Kiểm tra biến xác nhận
            if(maNV.equals("ADMIN")){//So sanh role với chức vụ "Master"
                JOptionPane.showMessageDialog(null, "Chỉ quản lý mới có quyền sử dụng chức năng này !!","Error",JOptionPane.INFORMATION_MESSAGE);
            } else {
                ManageOrder gd = new ManageOrder();//Khởi tạo một đối tượng đại diện cho cửa sổ menu
                gd.setVisible(true);//Hiển thị giao diện menu
                gd.setLocationRelativeTo(null);//Giao diện menu được hiển thị ở giữa màn hình
                Menu.this.dispose();//Đóng cửa sổ đăng nhập           
            }         
        } 
    }//GEN-LAST:event_btnQLDHActionPerformed

    private void btnTTDHActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTTDHActionPerformed
        // TODO add your handling code here:
        String maNV = GD_DangNhap.getMaNV();//Lấy giá trị mã nhân viên từ cửa sổ login và gán cho biến code
        for(User o : ltUser) {//Đọc lần lượt các đối tượng user trong listUser
            if(maNV.equals(o.getMaNV())){//so sánh mã nhân viên đăng nhập vào hệ thống lần lượt với các mã nhân viên của các đối tượng trong list
                check = true;//Gán biến xác nhận thành true
                break;
            }
        }
        if(check){ //Kiểm tra biến xác nhận
            if(maNV.equals("ADMIN")){//So sanh role với chức vụ "Master"
                JOptionPane.showMessageDialog(null, "Chỉ quản lý mới có quyền sử dụng chức năng này !!","Error",JOptionPane.INFORMATION_MESSAGE);
            } else {
                OrderProcess gd = new OrderProcess();//Khởi tạo một đối tượng đại diện cho cửa sổ menu
                gd.setVisible(true);//Hiển thị giao diện menu
                gd.setLocationRelativeTo(null);//Giao diện menu được hiển thị ở giữa màn hình
                Menu.this.dispose();//Đóng cửa sổ đăng nhập           
            }         
        } 
    }//GEN-LAST:event_btnTTDHActionPerformed

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
            java.util.logging.Logger.getLogger(Menu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Menu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Menu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Menu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Menu().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnLog;
    private javax.swing.JButton btnQLDH;
    private javax.swing.JButton btnQLDMSP;
    private javax.swing.JButton btnQLND;
    private javax.swing.JButton btnTTDH;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    // End of variables declaration//GEN-END:variables
}