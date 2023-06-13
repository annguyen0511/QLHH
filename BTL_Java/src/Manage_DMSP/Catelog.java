/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Manage_DMSP;

import java.io.Serializable;
import java.util.Objects;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author nhata
 */
public class Catelog implements Serializable {
    private String maDM;
    private String tenDM;
    private List<Product> ltPro;
    //get, set
    public String getMaDM() {
        return maDM;
    }

    public void setMaDM(String maDM) {
        this.maDM = maDM;
    }

    public String getTenDM() {
        return tenDM;
    }

    public void setTenDM(String tenDM) {
        this.tenDM = tenDM;
    }
    //constructor
    public Catelog(String maDM, String tenDM) {
        this.maDM = maDM;
        this.tenDM = tenDM;
    }
    public Catelog() {
    }
    
    //toString
    @Override
    public String toString() {
        return "Catelog{" + "maDM=" + maDM + ", tenDM=" + tenDM + '}';
    }
    //equals
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
        final Catelog other = (Catelog) obj;
        return Objects.equals(this.maDM, other.maDM);
    }
    //other methods
    public void addproduct(Product pr) {
        if(ltPro == null) {
            ltPro = new ArrayList<>();
        }
        else {
            ltPro.add(pr);
        }
    }
}
