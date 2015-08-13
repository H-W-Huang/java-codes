import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;
import java.net.*;

public class ChatClient{
    JTextField field;
    JTextArea area;



    public static void main(String[] args) {
        ChatClient client = new ChatClient();
        client.go();
    }



    void go(){
        // setConnection();
        setupGUI();
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
    }

    

    // 获取与写入分两个线程来做

    void getInformation(){
        String line = null;
        try{    
            Socket s = new Socket("172.0.0.1",4848);
            InputStreamReader stream = new InputStreamReader(s.getInputStream());
            BufferedReader reader = new BufferedReader(stream);
            line = reader.readLine();
            area.append(line);
            reader.close();
        }catch(Exception ex){
            ex.printStackTrace();
        }
    }

    void sendMessage(){
        String msg = field.getText();
        try{
            Socket s = new Socket("127.0.0.1",4848);
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

}