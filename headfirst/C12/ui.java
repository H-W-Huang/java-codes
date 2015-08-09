    import javax.swing.*;
    import java.awt.event.*;   //监听器所在的包
    import java.awt.*;
    
    
    public class ui implements ActionListener{       //介入监听器接口
            JFrame frame;
            JButton button;
        public static void main(String[] args) {
                
                ui gui = new ui();
                gui.go();
        }
    


        void go()
        {
            frame = new JFrame();   //创建框架
            button = new JButton("click");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  //window关闭时结束程序
            
            button.addActionListener(this); //对button添加监听器

            
            frame.setSize(300,400);
            frame.setVisible(true);
        }
    
    
        //处理事件
        public void actionPerformed(ActionEvent event)
        {
            button.setText("I've benn clicked!");
        }

        
    }