/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package RWFile;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 *
 * @author nhata
 */
public class RWFileIO {
    public void saveFile(String fileName, Object obj) throws Exception {
        FileOutputStream fo = new FileOutputStream(fileName);
        ObjectOutputStream oos = new ObjectOutputStream(fo);
        oos.writeObject(obj);
        fo.close();
        oos.close();
    }
    
    public Object readFile(String fileName) throws Exception {
        Object kq = null;
        FileInputStream fi = new FileInputStream(fileName);
        ObjectInputStream ois = new ObjectInputStream(fi);
        kq = ois.readObject();
        return kq;
    }
}
