/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Manage_User;

import java.io.Serializable;
import java.util.Objects;

/**
 *
 * @author nhata
 */
public class User implements Serializable{
    private String maNV;
    private String hoTen;
    private String soDt;
    private String ngayBD;
    private String taiKhoan;
    private String matKhau;

    public User(String maNV) {
        this.maNV = maNV;
    }

    public User(String maNV, String hoTen, String soDt, String ngayBD, String taiKhoan, String matKhau) {
        this.maNV = maNV;
        this.hoTen = hoTen;
        this.soDt = soDt;
        this.ngayBD = ngayBD;
        this.taiKhoan = taiKhoan;
        this.matKhau = matKhau;
    }

    public User(){
    
    }
    
    public String getMaNV() {
        return maNV;
    }

    public void setMaNV(String maNV) {
        this.maNV = maNV;
    }

    public String getHoTen() {
        return hoTen;
    }

    public void setHoTen(String hoTen) {
        this.hoTen = hoTen;
    }

    public String getSoDt() {
        return soDt;
    }

    public void setSoDt(String soDt) {
        this.soDt = soDt;
    }

    public String getNgayBD() {
        return ngayBD;
    }

    public void setNgayBD(String ngayBD) {
        this.ngayBD = ngayBD;
    }

    public String getTaiKhoan() {
        return taiKhoan;
    }

    public void setTaiKhoan(String taiKhoan) {
        this.taiKhoan = taiKhoan;
    }

    public String getMatKhau() {
        return matKhau;
    }

    public void setMatKhau(String matKhau) {
        this.matKhau = matKhau;
    }
    
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
        final User other = (User) obj;
        return Objects.equals(this.maNV, other.maNV);
    }
    @Override
    public String toString() {
        return "User{" + "maNV=" + maNV + ", hoTen=" + hoTen + ", soDt=" + soDt + ", ngayBD=" + ngayBD + ", taiKhoan=" + taiKhoan + ", matKhau=" + matKhau + '}';
    }   
}
