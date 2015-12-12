/*******
4项必要条件： 
1. 发声装置          Sequencer
2. 要演奏的乐曲      Sequence
3. 带有乐曲信息记录  Track
4. 乐曲音符等信息    Message






******/
import javax.sound.midi.*;

//取得Sequencer

public class musicApp {
    public static void main(String[] args)
    {
        //创建musicApp对象
        musicApp m1 = new musicApp();
        musicApp m2 = new musicApp();
        try {
            System.out.println("Happy birthday!");
            //1.play(76);
            //hread.sleep(400);
            //1.play(77);
            //hread.sleep(400);
            //1.play(79);
            //hread.sleep(400);
            //1.play(76);
            //hread.sleep(380);
            //1.play(74);
            //hread.sleep(800);
            //1.play(79);
            //hread.sleep(800);
            //1.play(76);
            //hread.sleep(400);
            //1.play(72);
            //hread.sleep(400);
            //1.play(76);
            //hread.sleep(400);
            //1.play(77);
            //hread.sleep(380);
            //1.play(74);
// 76,77,79,76,74,79,76,72,76,77,74
            /****birthday song  ***/
            m1.play(72);
            Thread.sleep(200);
            m1.play(72);
            Thread.sleep(200);
            m1.play(74);
            Thread.sleep(400);
            m1.play(72);
            Thread.sleep(400);
            m1.play(77);
            Thread.sleep(400);
            m1.play(76);

            Thread.sleep(800);
            m2.play(72);
            Thread.sleep(200);
            m2.play(72);
            Thread.sleep(200);
            m2.play(74);
            Thread.sleep(400);
            m2.play(72);
            Thread.sleep(400);
            m2.play(79);
            Thread.sleep(400);
            m2.play(77);
            

            Thread.sleep(800);
            m2.play(72);
            Thread.sleep(200);
            m2.play(72);
            Thread.sleep(200);
            m2.play(84);
            Thread.sleep(400);
            m2.play(81);
            Thread.sleep(400);
            m2.play(77);
            Thread.sleep(400);
            m2.play(76);
            Thread.sleep(400);
            m2.play(74);

            Thread.sleep(1200);
            m2.play(82);
            Thread.sleep(200);
            m2.play(82);
            Thread.sleep(200);
            m2.play(81);
            Thread.sleep(400);
            m2.play(77);
            Thread.sleep(400);
            m2.play(79);
            Thread.sleep(400);
            m1.play(77);

            /******************/

            m1.play(88);
            Thread.sleep(800);
            m1.play(88);
            Thread.sleep(800);
            m1.play(88);
            Thread.sleep(200);
            m1.play(86);
            Thread.sleep(200);
            m1.play(84);
            Thread.sleep(200);
            m1.play(86);
            Thread.sleep(200);
            m1.play(84);
            Thread.sleep(200);
            m1.play(86);
            Thread.sleep(200);
            m1.play(81);






        } catch (InterruptedException e) {}
        
    }


    public void play(int x){
        try{
            //发声装置
            Sequencer player = MidiSystem.getSequencer();
            player.open();

            //演奏的乐曲 
            Sequence seq = new Sequence(Sequence.PPQ,4);   //奇怪的参数

            //乐曲信息记录
            Track track = seq.createTrack();

            ShortMessage a = new ShortMessage();
            a.setMessage(144,1,x,100);   //参数决定声音
            MidiEvent noteON = new MidiEvent(a,1);
            track.add(noteON);

            ShortMessage b = new ShortMessage();
            b.setMessage(128,1,x,100);   //参数决定声音
            MidiEvent noteOff = new MidiEvent(b,50);   //16是音长
            track.add(noteOff);

            player.setSequence(seq);
            player.start();
        }catch(Exception ex)
        {
            ex.printStackTrace();
        }
    }
}