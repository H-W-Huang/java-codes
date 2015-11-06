package sudukopanedemo;

import java.io.File;
import java.io.InputStreamReader;
import java.util.Scanner;


/**
 * 数独数据类
 * 用于从文件里获取数据
 * 
 * @author H.W
 */
public class SudokuData {

    InputStreamReader  in = new InputStreamReader(this .getClass().getResourceAsStream("Data/SetIn.dat"));
//    File file = new File("src/Data/SetIn.dat");
    
    
    /**
     * 从文件中获取数据的方法
     * n表示第n个数据
     * 一行表示1个数据
     * 所以 i 行就对应第i行数据
     * 
     * @param n
     * @return 
     */
    public  int[] getData(int n) {
        int[] a = new int[81];

        try {
            Scanner input = new Scanner(in);
            for (int i = 1; i < n; i++) {
                input.nextLine();
            }
            for (int i = 0; i < 81; i++) {
                a[i] = input.nextInt();
            }
            input.close();
        } catch (Exception ex) {
            System.out.println("读取错误");
        }
        return a;
    }

}
