
package Ulities;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 *
 * @author Administrator
 */
public class JavaFileChecker {
    private int lines ;  //文件里的总行数
    private int emptyLines; //空行
    private int codeLines;
    private long size;
    
    public void check(File javaFile) throws FileNotFoundException{
        size = javaFile.length();
        try(Scanner reader = new Scanner(javaFile,"UTF-8")){
            while(reader.hasNext()){
                String temp = reader.nextLine();
//                System.out.println(temp);
                if(temp.matches("[\\s]{0,}")) emptyLines++;
                lines++;
            }
        }
    }


    /**
     * @return the lines
     */
    public int getLines() {
        return lines;
    }

    /**
     * @return the emptyLines
     */
    public int getEmptyLines() {
        return emptyLines;
    }

    /**
     * @return the codeLines
     */
    public int getCodeLines() {
        return codeLines;
    }

    /**
     * @return the size
     */
    public long getSize() {
        return size;
    }
}
