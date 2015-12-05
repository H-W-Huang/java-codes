/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Main;

import Ulities.FileCounter;
import java.io.File;
import java.util.Scanner;

/**
 *
 * @author H.W
 */
public class Main {

    public static void main(String[] args) {
        
        FileCounter fileCounter = new FileCounter();
        Scanner input = new Scanner(System.in);  //接受键盘输入的目录  
        System.out.println("输入目录：");
        File inputDir = new File(input.nextLine());     //根据输入，创建文件
        fileCounter.openDirectory(inputDir);

        
        System.out.println("所输入目录下一共有" + fileCounter.getFileAmount() + "个文件");
        System.out.println("其中Java文件一共有：" + fileCounter.getJavafileAmount() + "个");
        System.out.println("总行数：" + fileCounter.getTotalLines()+"");
        System.out.println("总代码行数：" + fileCounter.getTotalCodeLines()+"");
        System.out.println("总空行数：" + fileCounter.getTotalEmptyLines()+"");
        System.out.println("总大小：" + fileCounter.getTotalSize() + " bytes");
    }
}
