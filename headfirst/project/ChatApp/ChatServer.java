import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.*;
import java.util.*;
import java.net.*;


public class ChatServer{


    public static void main(String[] args) {
        ChatServer server = new ChatServer();
        server.go();
    }



    void go(){
        setupServer();
    }






    void setupServer(){
        try{
            ServerSocket ss = new ServerSocket(4848);
            //无限循环!!
            while(true){
                Socket s  = ss.accept();
                InputStreamReader stream = new InputStreamReader(s.getInputStream());  
                BufferedReader reader = new BufferedReader(stream);
                String line = reader.readLine();
                System.out.println(line);
                reader.close();
            }
        }catch(Exception ex)
        {
            ex.printStackTrace();
        }
    }
}