/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Exercise;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author H.W
 */
public class EX17_10 {

    public static void main(String[] args) {
        FileDivider FD = new FileDivider(new File("test.wav"), 5);
        FD.divider();
    }
}

class FileDivider {

    private long fileSize;
    private int numbersOfPieces;
    private File target;

    public FileDivider(File target, int numbersOfPieces) {
        this.numbersOfPieces = numbersOfPieces;
        this.target = target;
        this.fileSize = target.length();
    }

    public void divider() {
        int count = 0;
        long sizeOfOnePiece = (int) Math.ceil(1.0 * fileSize / numbersOfPieces);
        System.out.println("文件大小：" + fileSize + " bytes.");
        System.out.println("切割后文件大小：" + sizeOfOnePiece + " bytes");
        try (/*RandomAccessFile rafReader = new RandomAccessFile(target, "r");*/
                BufferedInputStream bis = new BufferedInputStream(new FileInputStream(target))) {
            while (count < numbersOfPieces) {
                
                
                System.out.println("Dealing with piece " + (count + 1) + "");
                
                
                try (/*RandomAccessFile rafWriter = new RandomAccessFile(count + ".", "rw");*/
                        BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(count+".wav"))) {
                    long writtenByte = 0;
                    int data = 0;
                    while (writtenByte < sizeOfOnePiece && (data = bis.read())!= -1) {
                        writtenByte++;
                        bos.write(data);
                    }
                }
                
                count++;
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

}

/**
 * 不要用随机读取而是用BufferedInputStream就可以了
 */