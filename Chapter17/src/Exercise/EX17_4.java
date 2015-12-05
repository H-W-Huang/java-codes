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
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.util.Scanner;

/**
 *
 * @author H.W
 */
public class EX17_4 {

    public static void main(String[] args) {
//        Scanner input = new Scanner(System.in);
//        try (PrintWriter pw = new PrintWriter("EX17_4.txt");) {
//            String temp;
//            while (!(temp = input.nextLine()).equals("done")) {
//                pw.append(temp+"\n");
//            }
//        } catch (Exception ex) {
//            ex.printStackTrace();
//        }
        readAndWriteAsUTF("Welcome.utf");
    }

    public static void readAndWriteAsUTF(String filename) {
        File file = new File("EX17_4.txt");
        try (
                FileOutputStream fos = new FileOutputStream(filename);
                DataOutputStream dis = new DataOutputStream(new BufferedOutputStream(fos));
                Scanner input = new Scanner(file);) {

            String temp = "";
            while (input.hasNext()) {
                temp = temp + input.nextLine() + "";
            }
            System.out.println(temp);

            dis.writeUTF(temp);
            System.out.println("文本文件大小：" + file.length() + "bytes");
            System.out.println("二进制文件大小：" + new File("Welcome.utf").length() + "bytes");
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        System.out.println("文本文件大小：" + file.length() + "bytes");
        System.out.println("二进制文件大小：" + new File("Welcome.utf").length() + "bytes");
    }

}
