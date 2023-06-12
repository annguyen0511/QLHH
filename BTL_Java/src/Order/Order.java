/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Order;
import Manage_DMSP.Product;
import Manage_User.User;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Objects;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
/**
 *
 * @author nhata
 */
public class Order implements Serializable {
    private String maDH;
    private LocalDateTime ngayHT;
    private double tongTien;
    private ArrayList<Product> dsSanPham;
    private int soLuongMua;
    //get, set
    public String getMaDH() {
        return maDH;
    }

    public void setMaDH(String maDH) {
        this.maDH = maDH;
    }

    public LocalDateTime getNgayHT() {
        return ngayHT;
    }

    public void setNgayHT(LocalDateTime ngayHT) {
        this.ngayHT = ngayHT;
    }

    public double getTongTien() {
        return tongTien;
    }

    public void setTongTien(double tongTien) {
        this.tongTien = tongTien;
    }

    public ArrayList<Product> getDsSanPham() {
        return dsSanPham;
    }

    public void setDsSanPham(ArrayList<Product> dsSanPham) {
        this.dsSanPham = dsSanPham;
    }

    public int getSoLuongMua() {
        return soLuongMua;
    }

    public void setSoLuongMua(int soLuongMua) {
        this.soLuongMua = soLuongMua;
    }
    //constructor
    public Order(String maDH, LocalDateTime ngayHT, double tongTien, int soLuongMua) {
        this.maDH = taoMaDonHang();
        this.ngayHT = LocalDateTime.now();
        this.tongTien = tongTien;
        this.soLuongMua = soLuongMua;
    }
    public Order() {
        this.maDH = taoMaDonHang();
        this.ngayHT = LocalDateTime.now();
    }

    public Order(double tongTien, ArrayList<Product> dsSanPham) {
        this.maDH = taoMaDonHang();
        this.ngayHT = LocalDateTime.now();
        this.tongTien = tongTien;
        this.dsSanPham = dsSanPham;
    }
    
    //toString()
    @Override
    public String toString() {
        return "Order{" + "maDH=" + maDH + ", ngayHT=" + ngayHT + ", tongTien=" + tongTien + ", dsSanPham=" + dsSanPham + ", soLuongMua=" + soLuongMua + '}';
    }
    //equal
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Order other = (Order) obj;
        return Objects.equals(this.maDH, other.maDH);
    }
    //other methods
    public String taoMaDonHang() {
        // Lấy thời gian hiện tại
        LocalDateTime now = LocalDateTime.now();
        int randomNumber = now.getNano();
        // Định dạng mã đơn hàng từ thời gian hiện tại
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS");

        // Tạo mã đơn hàng từ timestamp và một số ngẫu nhiên
        String maDonHang = "DH" + (int) (randomNumber);

        return maDonHang;
    }
    public String getFormattedThoiGian() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        return ngayHT.format(formatter);
    }
}
