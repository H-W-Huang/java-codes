import java.io.*;


public class butest{
    public static void main(String[] args) {
        try{
            File f = new File("teststring.txt");
            //有wirter就有reader
            FileReader reader = new FileReader(f);  //连接文件的串流
            //重点于此
            BufferedReader breader = new BufferedReader(reader);  //接入reader
            String line = null; //承接读取内容
    
            //读取文件的while循环
            while ((line=breader.readLine())!=null) {
                System.out.println(line);
            }
    
            breader.close();  //关闭的是bufferreader
        }catch(Exception ex){ex.printStackTrace();}

    }
}