/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Manage_DMSP;

//import Manage_User.User;
import java.io.Serializable;
import java.util.Objects;

/**
 *
 * @author nhata
 */
public class Product implements Serializable {
    private String maSP;
    private String tenSP;
    private Catelog dmSP;
    private String tenDM;
    private int soLuong;
    private int soLuongMua;
    private double giaBan;
    private String ngayNhap;
    private double tongTien;
    //get set

    public double getTongTien() {
        return tongTien;
    }

    public void setTongTien(int soLuongMua) {
        this.tongTien = soLuongMua * giaBan;
    }

    public int getSoLuongMua() {
        return soLuongMua;
    }

    public void setSoLuongMua(int soLuongMua) {
        this.soLuongMua = soLuongMua;
    }
    
    
    public String getMaSP() {
        return maSP;
    }

    public void setMaSP(String maSP) {
        this.maSP = maSP;
    }

    public String getTenSP() {
        return tenSP;
    }

    public String getTenDM() {
        return tenDM;
    }

    public void setTenDM(String tenDM) {
        this.tenDM = tenDM;
    }

    public void setTenSP(String tenSP) {
        this.tenSP = tenSP;
    }

    public Catelog getDmSP() {
        return dmSP;
    }

    public void setDmSP(Catelog dmSP) {
        this.dmSP = dmSP;
    }

    public int getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(int soLuong) {
        this.soLuong = soLuong;
    }

    public double getGiaBan() {
        return giaBan;
    }

    public void setGiaBan(double giaBan) {
        this.giaBan = giaBan;
    }

    public String getNgayNhap() {
        return ngayNhap;
    }

    public void setNgayNhap(String ngayNhap) {
        this.ngayNhap = ngayNhap;
    }
    //constructor
    public Product(String maSP, String tenSP, Catelog dmSP, int soLuong, double giaBan, String ngayNhap) {
        this.maSP = maSP;
        this.tenSP = tenSP;
        this.dmSP = dmSP;
        this.soLuong = soLuong;
        this.giaBan = giaBan;
        this.ngayNhap = ngayNhap;
    }
    public Product() {  
    }

    public Product(String maSP, String tenSP, String tenDM, int soLuong, double giaBan, String ngayNhap) {
        this.maSP = maSP;
        this.tenSP = tenSP;
        this.tenDM = tenDM;
        this.soLuong = soLuong;
        this.giaBan = giaBan;
        this.ngayNhap = ngayNhap;
    }

    public Product(String maSP, String tenSP, int soLuongMua, double giaBan, double tongTien) {
        this.maSP = maSP;
        this.tenSP = tenSP;
        this.soLuongMua = soLuongMua;
        this.giaBan = giaBan;
        this.tongTien = tongTien;
    }
    
    //tostring
    @Override
    public String toString() {
        return "Product{" + "maSP=" + maSP + ", tenSP=" + tenSP + ", dmSP=" + dmSP + ", soLuong=" + soLuong + ", giaBan=" + giaBan + ", ngayNhap=" + ngayNhap + '}';
    }
    //other methods
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
        final Product other = (Product) obj;
        return Objects.equals(this.maSP, other.maSP);
    }
}
