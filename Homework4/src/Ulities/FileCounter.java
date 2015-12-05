package Ulities;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * **
 * 使用File类型就可以实现对文件的读写或者
 *
 * @author Administrator
 */
public class FileCounter {
    private int fileAmount;
    private int javafileAmount;
    private int totalLines;
    private int totalEmptyLines;
    private int totalCodeLines;
    private long totalSize ;

    /**
     * 
     * @param inputDir 
     */
    public void openDirectory(File inputDir) {
        if (inputDir.isDirectory()) {
            listAllFilesAndDirctory(inputDir);
        }
        else{
            System.out.println("请输入一个目录。");
        }
        System.out.println("总行数："+totalLines);
    }


    /**
     * 参数是一个目录; 将该目录下所有的文件或者子目录装入String[]数组里; 递归实现 深度优先
     *
     * @param dir
     */
    private void listAllFilesAndDirctory(File dir) {
        //当File不是目录而是文件时，直接输出文件名
        if (!dir.isDirectory()) {
//            System.out.println(dir.getName());
            count(dir);
        } else {
            //若File是目录时，输出目录名，获取其下的Files，然后递归操作
//            System.out.println("本目录名为：" + dir.getName());
            File[] dirs = dir.listFiles();
            for (int i = 0; i < dirs.length; i++) {
                listAllFilesAndDirctory(dirs[i]);
            }
        }
    }

    /**
     * 统计文件个数 其中会对java进行统计
     *
     * @param file
     */
    private void count(File file) {
        
        if (file.getName().toLowerCase().endsWith(".java")) {
            JavaFileChecker checker = new JavaFileChecker();
            javafileAmount++;
            try {
                System.out.println("文件名："+file.getName());
                checker.check(file);
                System.out.println("行数:"+checker.getLines());
            } catch (FileNotFoundException ex) {
                System.out.println("Can't open the file.");
            }
            totalLines += checker.getLines();
            totalEmptyLines += checker.getEmptyLines();
            totalSize+=checker.getSize();
            totalCodeLines += checker.getLines() - checker.getEmptyLines();
        }
        
        fileAmount++;
    }

    
    /********* getters and setters ********/
    /**
     * @return the fileAmount
     */
    public int getFileAmount() {
        return fileAmount;
    }

    /**
     * @return the javafileAmount
     */
    public int getJavafileAmount() {
        return javafileAmount;
    }

    /**
     * @return the totalLines
     */
    public int getTotalLines() {
        return totalLines;
    }

    /**
     * @return the totalEmptyLines
     */
    public int getTotalEmptyLines() {
        return totalEmptyLines;
    }

    /**
     * @return the totalCodeLines
     */
    public int getTotalCodeLines() {
        return totalCodeLines;
    }

    /**
     * @return the totalSize
     */
    public long getTotalSize() {
        return totalSize;
    }
}
