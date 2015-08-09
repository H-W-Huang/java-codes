import javax.swing.*;
import java.awt.event.*;
import java.awt.*;


public class test implements ActionListener{
    JButton button;
    int x=70;
    int y=70;
    public static void main(String[] args) {
        test gui = new test();
        gui.go();
    }

    public void go(){
        JFrame frame = new JFrame();
        button = new JButton("click");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        button.addActionListener(this);

        Md d =new Md();


        frame.getContentPane().add(d);
        // frame.getContentPane().add(button);
        frame.setSize(300,300);
        frame.setVisible(true);
    }

    public void actionPerformed(ActionEvent event)
    {
        button.setText("I've benn clicked!");
    }

    class Md extends JPanel{
        public void paintComponent(Graphics g)
        {
            Graphics2D g2d = (Graphics2D) g;
            // int red= (int)(Math.random()*255);
            // int green= (int)(Math.random()*255);
            // int blue= (int)(Math.random()*255);
            // Color randomColor = new Color(red,green,blue);
            // g.setColor(randomColor);
            // g.fillOval(70,70,100,100);

            int red= (int)(Math.random()*255);
            int green= (int)(Math.random()*255);
            int blue= (int)(Math.random()*255);
            Color stratColor = new Color(red,green,blue);
            red= (int)(Math.random()*255);
            green= (int)(Math.random()*255);
            blue= (int)(Math.random()*255);
            Color endColor = new Color(red,green,blue);
            GradientPaint gradient = new GradientPaint(70,70,stratColor,150,150,endColor);
            g2d.setPaint(gradient);
            g2d.fillOval(70,70,100,100);
        }
    }
}