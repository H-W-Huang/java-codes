/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Training;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * 随机读取文件
 *
 * @author H.W
 */
public class TEstRandomAcceccFile {

    public static void main(String[] args) {
        try (RandomAccessFile inout = new RandomAccessFile("inout", "rw")) {

            //清除文件（destory the old content if the file is existed!）
            inout.setLength(0);

            System.out.println("写入200个整数，从1 到 200");
            for (int i = 0; i < 200; i++) {
                inout.writeInt(i);
            }

            System.out.println("当前文件的大小");
            System.out.println(inout.length() + " bytes");
            System.out.println("通过结果也可以知道，int的数据大小为 4 个字节/个。");

            System.out.println("现在我跑到0位置，输出结果：");
            inout.seek(0);
            System.out.println("结果是：" + inout.readInt());

            System.out.println("现在我跑到16位置，输出结果：");
            inout.seek(4 * 4);
            System.out.println("结果是：" + inout.readInt());

            System.out.println("现在来修改一下4位置的结果，输出结果：");
            inout.seek(4 * 4);
            inout.writeInt(255);  //写入完毕后会自动移到下一个字节
            inout.seek(4 * 4);
            System.out.println("结果是：" + inout.readInt());

        } catch (FileNotFoundException ex) {
            Logger.getLogger(TEstRandomAcceccFile.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
