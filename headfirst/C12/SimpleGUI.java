        import javax.swing.*;
        import java.awt.event.*;
        import java.awt.*; 
        
        
        
        public class SimpleGUI {
            JFrame frame;
            JButton button1 ;
            JButton button2 ;
            public static void main(String[] args) {
                SimpleGUI gui = new  SimpleGUI();
                gui.go();
            }
        
            public void go()
            {
                frame = new JFrame("TesT");
                button1= new JButton("click");
                button2= new JButton("click");
                //要在frame上显示出画板的话需要创建一个对象
                Md painting = new Md();
        
                //button2.addActionListener(this);   //想button添加监听器
        
                button1.addActionListener(new button1Listener());   //将两个内部类作为参数传进去
                button2.addActionListener(new button2Listener());   //将两个内部类作为参数传进去
        
        
        
                frame.getContentPane().add(painting,BorderLayout.CENTER);
                //BorderLayout.CENTER的使用需要导入 java.awt.*
                frame.getContentPane().add(button1,BorderLayout.WEST);
                frame.getContentPane().add(button2,BorderLayout.SOUTH);
                frame.setSize(300,300);
                frame.setVisible(true);
            }
        
            //画板类中继承
            public class Md extends JPanel{
                public void paint(Graphics g)  
                {   //注意这里的参数，只能有层序来调用
                    //要渐变效果的话，需要将Graphics转化为 Graphics2D
                    Graphics2D g2d = (Graphics2D) g;
        
                    int red = (int)(Math.random()*255);
                    int blue = (int)(Math.random()*255);
                    int green = (int)(Math.random()*255);
                    //颜色的决定使用color对象
                    Color scolor = new Color(red,green,blue);
        
        
                    red = (int)(Math.random()*255);
                    blue = (int)(Math.random()*255);
                    green = (int)(Math.random()*255);
                    Color ecolor = new Color(red,green,blue);
        
                    // int  posi1= (int)(Math.random()*300);
                    // int  posi2= (int)(Math.random()*300);
                    // int  posi3= (int)(Math.random()*300);
                    // int  posi4= (int)(Math.random()*300);
                    //创建颜料画对象
                    GradientPaint dp = new GradientPaint(70,70,scolor,150,150,ecolor);
                    g2d.setPaint(dp);
                    g2d.fillOval(70,70,100,100);
                }
            }
        
            public class button1Listener implements ActionListener{
                public void actionPerformed(ActionEvent ae){
                    button1.setText("Clicked!");
                }
            }
        
            public class button2Listener implements ActionListener{
                public void actionPerformed(ActionEvent ae){
                    frame.repaint();
                }
            }
        }       