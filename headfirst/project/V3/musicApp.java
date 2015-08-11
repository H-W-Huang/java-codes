import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
import javax.sound.midi.*;
import java.util.*;


public class musicApp{ 
    JFrame frame ; //frame= new JFrame("BeatBox");
    JPanel panel ;
    ArrayList<JCheckBox> checkboxlist;  //pay attention

    Sequencer sequencer;
    Sequence sequence;
    Track    track;



    public static void main(String[] args) {
        musicApp m = new musicApp();
        m.setupGUI();
    }

    //乐器
    String[] instrumentNames= {"Bass Drum","Clsoed Hi-Hat","Open Hi-Hat","Acoustic Snare","Crash Cymbal","Hand Clap","High Tom","Hi Bongo","Maracas","Whistle","Low Conga","Cowbell","Vibraslap","Low-mid Tom","High Agogo","Open Hi Conga"};

    int[] instruments ={35,42,46,37,49,39,50,60,70,72,64,56,58,47,67,63};


    public void setupGUI(){
        frame= new JFrame("BeatBox");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        BorderLayout layout = new BorderLayout();

        //创建了几个panel仅仅作为背景,这就是之前提到的避免在frame上放组件的方法，从而在100%java层上操作
        JPanel background = new JPanel();
        background.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));//设定面板边缘空白
        background.setLayout(layout);

        checkboxlist = new ArrayList<JCheckBox>();
        Box buttonBox = new Box(BoxLayout.Y_AXIS);  //??

        //四个按钮,分别添加四个监听，一位接下来需要四个子类
        JButton start = new JButton("START");
        start.addActionListener(new MyStartListener());

        JButton stop = new JButton("STOP");
        stop.addActionListener(new MyStopListener());

        JButton upTempo = new JButton("Tempo UP");
        upTempo.addActionListener(new MyUpTempoListener());

        JButton downTempo = new JButton("Tempo DOWN");
        downTempo.addActionListener(new MyDownTempozListener());

        buttonBox.add(start);
        buttonBox.add(upTempo);
        buttonBox.add(downTempo);
        buttonBox.add(stop);
        //四个按钮完成.add();

        //乐器名字列表
        Box nameBox = new Box(BoxLayout.Y_AXIS);
        for(int i=0;i<16;i++)
        {
            nameBox.add(new Label(instrumentNames[i])); //???
        }

        background.add(BorderLayout.WEST,nameBox);
        background.add(BorderLayout.EAST,buttonBox);
        
        frame.getContentPane().add(background);

        GridLayout grid = new GridLayout(16,16);  //16*16的网格
        grid.setVgap(1);
        grid.setHgap(2);
        panel = new JPanel(grid);  //将grid传入！？
        background.add(BorderLayout.CENTER,panel);

        //生成复选框
        for(int i=0;i<256;i++)
        {
            JCheckBox c = new JCheckBox();
            c.setSelected(false);
            checkboxlist.add(c);
            panel.add(c);
            //做了两次添加
        }


        //midi组件
        setupMidi();




        //完成布局
        frame.setBounds(50,50,300,300);
        frame.pack();  //调整此窗口的大小，以适合其子组件的首选大小和布局。如果该窗口和/或其所有者还不可显示，则在计算首选大小之前都将变得可显示。在计算首选大小之后，将会验证该窗口。
        frame.setVisible(true);
    }

    public void setupMidi(){
        try{
            sequencer = MidiSystem.getSequencer();
            sequencer.open();
            sequence = new Sequence(Sequence.PPQ,4);
            track = sequence.createTrack();
            sequencer.setTempoInBPM(120);
        }catch(Exception ex){
            ex.printStackTrace();
        }
    }


    //重点于下面这些。将复选框状态转化为MIDI事件并加入到track.
    public void buildTrackAndStart()
    {
        int[] tracklist=null; //将要包含16个元素

        //去旧布新
        sequence.deleteTrack(track);
        track = sequence.createTrack();

        for(int i=0;i<16;i++)
        {
            tracklist = new int[16];

            int key= instruments[i];

            for(int j=0;j<16;j++)
            {
                JCheckBox jc = (JCheckBox)checkboxlist.get(j+(16*i)); //对256个框进行处理，每16个一组，一种乐器
                if(jc.isSelected())
                {
                    tracklist[j] = key;
                }else{
                    tracklist[j]=0;
                }
            }

            makeTracks(tracklist);
            track.add(makeEvent(172,1,127,0,16));
        }//end for

        track.add(makeEvent(192,9,1,0,15));
        try{
            sequencer.setSequence(sequence);
            sequencer.setLoopCount(sequencer.LOOP_CONTINUOUSLY);   //指定无穷的重复次数
            sequencer.start();                                  //开始播放
            sequencer.setTempoInBPM(120);
        }catch(Exception e){e.printStackTrace();}
    }

    public class MyStartListener implements ActionListener{
        public void actionPerformed(ActionEvent a){
            buildTrackAndStart();
        }
    }

    public class MyStopListener implements ActionListener{
        public void actionPerformed(ActionEvent a){
            sequencer.stop();
        }
    }

    public class MyUpTempoListener implements ActionListener{
        public void actionPerformed(ActionEvent a){
            float tempoFactor = sequencer.getTempoFactor();
            sequencer.setTempoFactor((float)(tempoFactor*1.03));
        }
    }

    public class MyDownTempozListener implements ActionListener{
        public void actionPerformed(ActionEvent a){
            float tempoFactor = sequencer.getTempoFactor();
            // sequencer.setTempoFactor((float)(tempoFactor*0.97));
        }
    }





    public void makeTracks(int[] list){
        for(int i =0;i<16;i++)
        {
            int key = list[i];

            if(key!=0){
                track.add(makeEvent(144,9,key,100,i));
                track.add(makeEvent(128,9,key,100,i+1));
            }
        }
    }







    public MidiEvent makeEvent(int comd,int chan,int one,int two,int tick)
    {
        MidiEvent event = null;
        try{
            ShortMessage a = new  ShortMessage();
            a.setMessage(comd,chan,one,two);
            event = new MidiEvent(a,tick);
        }catch(Exception ex){ex.printStackTrace();}
        return event;
    }   



}