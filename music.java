/***
1. 播放一系列音
2. 为添加监听
3. 加入图像界面
***/

import javax.sound.midi.*;
import javax.swing.*;
import java.io.*;
import java.awt.*;

public class music{
    PaintMusic ml;    //这里将paint放在外边，因为可能接下来的处理方法会用到该变量
    JFrame frame;


    public static void main(String[] args) {
        music ma = new music();
        ma.go();
    }

    void go(){
        setupGUI();
        


        /***music相关的写在这***/
        /***需要写在try-catch块里***/
        try{
            //首先是Sequencer
            Sequencer sequencer = MidiSystem.getSequencer();   //注意是调用方法获取对象，而不是直接创建对象 
            //一系列操作
            // int[] eventsIWant={127};
            sequencer.addControllerEventListener(ml,new int[]{127});   //重点，添加监听者（监听的是Sequencer），而且不是ActionEvent巍峨是ControlerEvent

            sequencer.open();
            
            //接着是Sequence
            Sequence seq = new Sequence(Sequence.PPQ,4); 
            //再来是Track（需要将Track写入sequence）
            Track track = seq.createTrack();

            int r = 0;
            int[] song = {76,77,79,76,74,79,76,72,76,77,74,74};
            // int[] tempo={40,40,40,40,20,50,50,50,50,50,50,50};
            for(int i=0;i<11;i+=1){
                r = (int) ((Math.random()*80)+1);
                track.add(makeEvent(144,1,song[i],100,i)) ;//add的是MidiEvent类型的对象引用
                track.add(makeEvent(176,1,127,0,i));
                track.add(makeEvent(128,1,song[i],100,i+2));
            }
            sequencer.setSequence(seq);
            sequencer.setTempoInBPM(30);
            sequencer.start();
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    // 建立界面的方法
    void setupGUI()
    {
        ml = new PaintMusic();
        frame = new JFrame("View your music!");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
        frame.getContentPane().add(ml);
        frame.setSize(300,300);
        frame.setVisible(true);
    }



    //触发实际
    public MidiEvent makeEvent(int comd,int chan,int one,int two,int tick)
    {
        MidiEvent event = null;  //MidiEventc存在于包里
        try{
            //Track的信息在这里填充
            ShortMessage a =new ShortMessage();
            a.setMessage(comd,chan,one,two);
            event = new MidiEvent(a,tick);
        } catch(Exception e){}

        return event;
    }

    //画板,
    class PaintMusic extends JPanel implements ControllerEventListener{
        boolean msg = false;

        //这个就是ControllerEventListener里的一个方法，唏嘘在这里完成
        public void controlChange(ShortMessage event){
            msg = true;
            repaint();
        }

        public void paint(Graphics g)
        {
            if(msg){
                Graphics2D g2d = (Graphics2D) g;
                int red= (int)(Math.random()*255);
                int green= (int)(Math.random()*255);
                int blue= (int)(Math.random()*255);
                g.setColor(new Color(red,green,blue));
    
                int ht = (int)((Math.random()*120)+10);
                int width = (int)((Math.random()*120)+10);
                int  x   = (int)((Math.random()*40)+10);
                int  y   = (int)((Math.random()*40)+10);
                g.fillOval(x,y,ht,width);
    
                msg = false;
            }//end if
        }
    }
}