/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Exercise;

import com.sun.corba.se.impl.io.IIOPOutputStream;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author H.W
 */
public class EX17_6_7 {

    public static void writeCircle2File(Circle c) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream("Ex17_6.dat",true)))) {
            oos.writeObject(c);
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public static Object readObjectFromFile() {
        Object obj = null;
        try(ObjectInputStream ois = new ObjectInputStream(new BufferedInputStream(new FileInputStream("Ex17_6.dat"))) ){
            obj = ois.readObject();
        }catch(ClassNotFoundException ex){
            ex.printStackTrace();
        }catch(IOException ex){
            ex.printStackTrace();
        }
        return obj;
    }
    
    public static void main(String[] args) {
        Circle c = new Circle();
        writeCircle2File(c);
        System.out.println((Circle)readObjectFromFile());
    }
}

class Circle implements Serializable{

    private double radius;
    private Date birthday;

    public Circle() {
        this.radius = 1.0;
        this.birthday = new Date();
    }

    public double getArea() {
        return this.radius * this.radius * Math.PI;
    }

    @Override
    public String toString() {
        String result = ""
                + "This is a circlr having a radius of  "
                + this.radius
                + "cm ";
        return result;
    }
}
