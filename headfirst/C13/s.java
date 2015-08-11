import javax.swing.*;
import java.awt.*;
import java.awt.event.*;



public class s{
    JFrame      frame;
    JPanel      panel1;
    JPanel      panel2;
    JCheckBox   check;
    JButton     button1;
    JButton     button2;
    JButton     button3;
    JTextField  field;
    JTextArea   area;
    JList list;
    String[] s= {"a","b","c","d","e","f","g","h"};

    public static void main(String[] args) {
        s gui = new s();
        gui.setupGUI();
    }

    public void setupGUI(){
        frame  = new JFrame();
        panel1   = new JPanel();
        panel2   = new JPanel();
        check  = new JCheckBox("checkbox");
        button1 = new JButton("BUTTON");
        button2 = new JButton("BUTTON");
        button3 = new JButton("BUTTON");
        field   = new JTextField("Write down something!");
        area    = new JTextArea(30,30);
        list = new JList(s);   

        //更换字体
        Font fonts = new Font("serif",Font.BOLD,28);
        button1.setFont(fonts);


        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        panel1.setBackground(Color.pink);  
        panel1.setLayout(new BoxLayout(panel1,BoxLayout.Y_AXIS));
        panel2.setLayout(new BoxLayout(panel2,BoxLayout.Y_AXIS));
        //布局管理里器控制panel，使用Y轴

        /***带有滚动条的area***/
        JScrollPane scroller = new JScrollPane(area); //首先是创建滚动条对象，将对象添加到area上
        area.setLineWrap(true);  //设置自动换行，否则会去溢出
        scroller.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        scroller.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

        /**为列表添加滚动条***/
        JScrollPane scroller1 = new JScrollPane(list); //首先是创建滚动条对象，将对象添加到area上
        scroller.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        scroller.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
    


        /**为check添加监听**/
        // check.addItemListener(this);  //(???)


        frame.getContentPane().add(BorderLayout.EAST,panel1);  //这样会把这个frame给占满
        frame.getContentPane().add(BorderLayout.WEST,panel2);  //这样会把这个frame给占满
        panel1.add(check);
        panel1.add(button1);
        panel1.add(button2);
        panel1.add(button3);
        panel1.add(scroller1);
        panel2.add(field);
        // panel2.add(area);  //添加了滚动条以后就不需要添加area了，而是添加爱scroller，而scroller导游area
        panel2.add(scroller);


        frame.setSize(500,500);
        frame.setVisible(true);
    }

    //checkbox处理事件,需要写出
    // ItemListener itemListener  = new ItemListener(){
    void itemStateChanged(ItemEvent ev)
    {
        String of = "off";
        if(check.isSelected()) of = "true";
        System.out.println("CheckBox is "+of);
    }
    // }

    //     public void valueChanged(ListSelectionEvent lse){
    //         if(!lse.getValueIsAdjusting())
    //         {
    //             String.selection = (String) list.getSelectedValue();
    //             System.out.println(selection);
    //         }
    // }
}