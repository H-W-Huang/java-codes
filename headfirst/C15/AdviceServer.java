import java.io.*;
import java.net.*;
public class AdviceServer{
    String[] toastlist = {"good morning","good night","Congratulations!!"};

    public static void main(String[] args) {
        AdviceServer server = new AdviceServer();
        server.go();
    }

    void go(){
        try{
            ServerSocket serverSocket = new ServerSocket(5000); //在此处设定端口号

            //服务器进入无线循环来等待客服端的请求
            while(true){
                Socket socket = serverSocket.accept();   //又新建了一个socket对象
                PrintWriter writer = new PrintWriter(socket.getOutputStream());
                String toast = getToast();
                writer.println(toast);     
                writer.close(); 
                System.out.println(toast); 
                } 
            }catch(Exception ex){
                ex.printStackTrace();
        }
    }

    private String getToast(){
        int index = (int)(Math.random()*toastlist.length);
        return toastlist[index];
    }


}