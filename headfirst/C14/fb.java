    import java.io.*;
    public class fb{
        public static void main(String[] args) {
            File dir = new File("../C14"); // = new File("c14_1");
            if(dir.isDirectory()){
                String[] dirContents = dir.list();  //一个方法搞定
                for(int i=0;i<dirContents.length;i++)
                {
                    System.out.println(dirContents[i]);
                }
            }
        }
    }