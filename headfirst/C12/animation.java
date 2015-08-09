import javax.swing.*;
import java.awt.event.*;
import java.awt.*; 



public class animation {
    JFrame frame;
    JButton button1 ;
    JButton button2 ;
    Md painting = new Md();
    int x=0,y=500; //看都有优点了么，可以直接被内部类使用1

    public static void main(String[] args) {
        animation gui = new  animation();
        gui.go();
    }

    public void go()
    {

        frame = new JFrame("TesT");
        //要在frame上显示出画板的话需要创建一个对象
        
        button1 =new JButton("CLICK!");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().add(painting,BorderLayout.CENTER);
        // frame.getContentPane().add(button1,BorderLayout.NORTH);
        frame.setSize(2000,2000);
        frame.setVisible(true);
        // button1.addActionListener(new buttonListener());
        //动画的实现需要一个循环
        //于是数学几何终于用上了
        for(int i=0;i<=5000;i++)
        {
            x+=3;y=y+(int)(Math.cos(x)*200);
            // x++;y++;
            painting.repaint();   //干脆把repaint理解为刷新好了
            try{
                Thread.sleep(10);
            }catch(Exception ex){}
        }
    }

    //画板类中继承
    //一样是一个内部类
    public class Md extends JPanel{
        public void paint(Graphics g)  
        {   //注意这里的参数，只能有层序来调用
            //要渐变效果的话，需要将Graphics转化为 Graphics2D
            // Graphics2D g2d = (Graphics2D) g;

            // int red = (int)(Math.random()*255);
            // int blue = (int)(Math.random()*255);
            // int green = (int)(Math.random()*255);
            // //颜色的决定使用color对象
            // Color scolor = new Color(20,20,20);


            // red = (int)(Math.random()*255);
            // blue = (int)(Math.random()*255);
            // green = (int)(Math.random()*255);
            // Color ecolor = new Color(20,20,20);

            // // int  posi1= (int)(Math.random()*300);
            // // int  posi2= (int)(Math.random()*300);
            // // int  posi3= (int)(Math.random()*300);
            // // int  posi4= (int)(Math.random()*300);
            // //创建颜料画对象
            // GradientPaint dp = new GradientPaint(70,70,scolor,150,150,ecolor);
            // g2d.setPaint(dp);



            //单纯这样做的话会留下痕迹。解决方法是将原来的点用背景色填充，下面两行是解决方法

            /***解决方法***/
            g.setColor(Color.white);
            g.fillRect(0,0,this.getWidth(),this.getHeight());


            /***解决方法***/
            g.setColor(Color.black);
            g.fillOval(x,y,/*离左上方x个px，y个px*/8,8);

            
        }
    }

    // public class buttonListener implements ActionListener{
    //     public void actionPerformed(ActionEvent ae){
            // for(int i=0;i<=10000;i++)
            // {
            //     x+=5;y=y+(int)(Math.sin(x)*200);
            //     painting.repaint();   //干脆把repaint理解为刷新好了
            //     try{
            //         Thread.sleep(10);
            //     }catch(Exception ex){}
            // }
    //     }
    // }
}     