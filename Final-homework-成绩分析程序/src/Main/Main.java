/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Main;

import MyTools.Student;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Scanner;
import jdk.nashorn.internal.parser.Lexer;

/**
 *
 * @author H.W
 */
public class Main {
    
    public static void main(String[] args) {
        
        ArrayList<Student> list = new ArrayList<>();
        
        File file = new File("test.txt");
        try(Scanner input = new Scanner(file);){
            while(input.hasNext()){
                String[] data = input.nextLine().split(",");
                list.add(new Student(data[0], data[1], Integer.parseInt(data[2])));
            }
        }catch(FileNotFoundException ex){
            ex.printStackTrace();
        }
        
        System.out.println("文件内容如下:");
        for( Student s :list){
            System.out.println(s);
        }
        
         
    }
}
