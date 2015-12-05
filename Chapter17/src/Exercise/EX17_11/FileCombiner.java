/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Exercise.EX17_11;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author H.W
 */
public class FileCombiner {

    private List<File> targets;  //这个变量是兼容Arraylist的
//    private int number;
    private String fileName;
    private String fileType;

    public FileCombiner(List<File> targets) {
        this(targets, "Default");
    }

    public FileCombiner(List<File> targets, String fileName) {
        this.targets = targets;
//        this.number = number;
        this.fileName = fileName;
        fileType = targets.get(0).getName().substring(targets.get(0).getName().lastIndexOf("."));
    }

    public FileCombiner(ArrayList<File> targets) {
        this(targets, "Default");
    }

    public FileCombiner(ArrayList<File> targets, String fileName) {
        this.targets = targets;
//        this.number = number;
        this.fileName = fileName;
        fileType = targets.get(0).getName().substring(targets.get(0).getName().lastIndexOf("."));
    }

    public boolean combine() {
        boolean isSuccessful = false;
        try (BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(fileName + fileType))) {
            for (File file : targets) {
                try (BufferedInputStream bis = new BufferedInputStream(new FileInputStream(file))) {
                    if (file != null) {
                        int data;
                        while ((data = bis.read()) != -1) {
                            bos.write(data);
                        }
                    }
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
            isSuccessful = true;
        } catch (FileNotFoundException ex) {
            Logger.getLogger(FileCombiner.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return isSuccessful;
    }

    public static void main(String[] args) {
        ArrayList<File> list = new ArrayList<>();
        list.add(new File("test0.wav"));
        list.add(new File("test1.wav"));
        list.add(new File("test2.wav"));

        FileCombiner fc = new FileCombiner(list);
        fc.combine();
    }

}
