import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;
import java.net.*;

public class ChatClient{
    JTextField field;
    JTextArea area;
    Socket sock;    
    InputStreamReader stream;
    BufferedReader reader;
    // PrintWriter writer;
    
    public static void main(String[] args) {
        ChatClient client = new ChatClient();
        client.go();
    }



    void go(){
        // setConnection();
        setupGUI();
        // setupNetwork();
        Thread info = new Thread(new Tasks());
        info.start();
    }




    void setupGUI(){
        JFrame frame = new JFrame("Chat App");
        JPanel panel = new JPanel();
        JLabel label1 = new JLabel("Here to input:");
        field = new JTextField(20);
        area  = new JTextArea(10,40);
        JButton sendButton = new JButton("Send");


        JScrollPane scroller = new JScrollPane(area);
        scroller.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        scroller.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);


        /**添加listener***/
        sendButton.addActionListener(new SendListener());

        panel.add(scroller);
        panel.add(field);
        panel.add(sendButton);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().add(BorderLayout.CENTER,panel);

        frame.setSize(500,360);
        frame.setVisible(true);
        System.out.println("GUI established successfully!");

    }

    

    // 获取与写入分两个线程来做

    // void setupNetwork(){
    //     try{    
    //         sock = new Socket("172.0.0.1",5000);
    //         stream = new InputStreamReader(sock.getInputStream());
    //         writer = new PrintWriter(sock.getOutputStream());
    //         reader = new BufferedReader(stream);
    //         System.out.println("Network established successfully!");
    //     }catch(Exception ex){
    //         System.out.println("Network established unsuccessfully!");
    //         ex.printStackTrace();
    //     }
    // }

    void getInformation(){
        // String  line = null;
        // try{
            // while((line = reader.readLine())!=null ){
                // area.append(line+" \r\n");
            // }
        // }catch(Exception ex){
            // ex.printStackTrace();
        // }
    }

    void sendMessage(){
        String msg = field.getText();
        try{
            Socket s = new Socket("127.0.0.1",5000);
            PrintWriter writer = new PrintWriter(s.getOutputStream());
            writer.println(msg);
            writer.close();
        }catch(Exception ex)
        {
            ex.printStackTrace();
        }
    }


    /***各个子类****/
    class SendListener implements ActionListener{
        public void actionPerformed(ActionEvent ae){
            sendMessage();
        }
    }


    class Tasks implements Runnable{
        public void run(){
            getInformation();
        }
    }
}