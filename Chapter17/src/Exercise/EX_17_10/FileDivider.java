/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Exercise.EX_17_10;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 *
 * @author H.W
 */
public class FileDivider {

    private long fileSize;
    private int numbersOfPieces;
    private File target;
    private String fileType;

    public FileDivider(File target, int numbersOfPieces) {
        this.numbersOfPieces = numbersOfPieces;
        this.target = target;
        this.fileSize = target.length();
    }

    public boolean divider() {
        boolean isSuccessful = false;
        int count = 0;
        long sizeOfOnePiece = (int) Math.ceil(1.0 * fileSize / numbersOfPieces);
        System.out.println("文件大小：" + fileSize + " bytes.");
        System.out.println("切割后文件大小：" + sizeOfOnePiece + " bytes");
        try (/*RandomAccessFile rafReader = new RandomAccessFile(target, "r");*/
                BufferedInputStream bis = new BufferedInputStream(new FileInputStream(target))) {
            fileType = target.getName().substring(target.getName().lastIndexOf("."));
            while (count < numbersOfPieces) {

                System.out.println("Dealing with piece " + (count + 1) + "");

                try (/*RandomAccessFile rafWriter = new RandomAccessFile(count + ".", "rw");*/
                        BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(target.getName().substring(0, target.getName().lastIndexOf("."))
                                        + count + fileType))) {
                    long writtenByte = 0;
                    int data = 0;
                    while (writtenByte < sizeOfOnePiece && (data = bis.read()) != -1) {
                        writtenByte++;
                        bos.write(data);
                    }
                }

                count++;
            }
            isSuccessful = true;
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return isSuccessful;
    }

}

/**
 * 不要用随机读取而是用BufferedInputStream就可以了
 */
