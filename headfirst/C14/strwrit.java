import java.io.*;
public class strwrit{
    public static void main(String[] args) {
        try{
            FileWriter writer = new FileWriter("String.txt");
            writer.write("Hey,world!");
            writer.close();// 别忘了关闭
        }catch(Exception ex){ex.printStackTrace();}
    }
}