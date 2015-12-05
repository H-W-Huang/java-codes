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
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FilterOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

/**
 *
 * @author H.W
 */
public class EX17_1 {

    public static void main(String[] args) {
        try (PrintWriter output=  new PrintWriter(new File("EX17_1.txt"));) {
            for (int i = 0; i < 10; i++) {
                int n = (int)(Math.random() * 100);
                output.print(n+"　");
                System.out.println(n+ " has been written!");
            }
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        try (Scanner input = new Scanner(new File("EX17_1.txt"));) {
            for (int i = 0; i < 10; i++) {
                System.out.println(input.nextInt());
            }
        }catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
