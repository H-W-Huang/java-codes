/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Exercise;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

/**
 *
 * @author H.W
 */
public class EX17_3 {

    public static void main(String[] args) {
//        try (DataOutputStream output = new DataOutputStream(new BufferedOutputStream(new FileOutputStream("EX17_3.dat")));) {
//            for (int i = 0; i < 50; i++) {
//                int n = (int) (Math.random() * 100);
//                output.writeInt(n);
//                System.out.println(n + " has been written!");
//            }
//        } catch (FileNotFoundException ex) {
//            ex.printStackTrace();
//        } catch (IOException ex) {
//            ex.printStackTrace();
//        }
        
        try (DataInputStream dis = new DataInputStream(new BufferedInputStream(new FileInputStream("EX17_3.dat")));) {
            int temp;
            while (dis.available()!=0) {
                System.out.println(dis.readInt());
//                System.out.println(temp);
            }
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
