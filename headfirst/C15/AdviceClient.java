import java.io.*;
import java.net.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;


public class AdviceClient{
    JFrame frame ;
    JPanel panel ;
    JTextField field;
    JButton button;

    public static void main(String[] args) {
        AdviceClient ac = new AdviceClient();
        ac.go();
    }

    void setupGUI()
    {
        frame = new JFrame("Today's Toast");
        panel = new JPanel();
        field = new JTextField("");
        button = new JButton("Get today's toast!");

        Font font = new Font("sanserif",Font.BOLD,26);
        field.setFont(font);

        panel.setLayout(new BoxLayout(panel,BoxLayout.Y_AXIS));
        panel.add(field);
        panel.add(button);

        button.addActionListener(new ToastListener());

        frame.getContentPane().add(BorderLayout.CENTER,panel);
        frame.setSize(400,200);
        frame.setVisible(true);

    }

    void getToast(){
        try{
            Socket s = new Socket("127.0.0.1",5000);
            InputStreamReader stream = new InputStreamReader(s.getInputStream());
            BufferedReader reader = new BufferedReader(stream);
            String toast = reader.readLine();
            System.out.println("Toast:"+toast);
            field.setText(toast);
            reader.close();
        }catch(Exception ex){
            ex.printStackTrace();
        }
    }

    void go(){
        setupGUI();
    }


    class ToastListener implements ActionListener{
        public void actionPerformed(ActionEvent ae){
             getToast();
        }
    }
}