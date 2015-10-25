package sudukopanedemo;

import java.io.File;
import java.io.PrintWriter;
import java.io.Writer;
import java.util.Scanner;

public class SudokuData {

    File file = new File("Data/SetIn.dat");

    public  int[] getData(int n) {
        int[] a = new int[81];

        try {
            Scanner input = new Scanner(file);
            for (int i = 1; i < n; i++) {
                input.nextLine();
            }
            for (int i = 0; i < 81; i++) {
                a[i] = input.nextInt();
            }
            input.close();
        } catch (Exception ex) {
            System.out.println("wrong");
        }
        return a;
    }

}
