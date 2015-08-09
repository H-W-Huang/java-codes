/*******
4项必要条件： 
1. 发声装置          Sequencer
2. 要演奏的乐曲      Sequence
3. 带有乐曲信息记录  Track
4. 乐曲音符等信息    Message

******/



import javax.sound.midi.*;

//取得Sequencer
class musicApp{
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

    void song1()
    {
        try {
            
            play(72);
            Thread.sleep(200);
            play(72);
            Thread.sleep(200);
            play(74);
            Thread.sleep(400);
            play(72);
            Thread.sleep(400);
            play(77);
            Thread.sleep(400);
            play(76);            
        } catch (InterruptedException e) {}
        
    }

    void song2()
    {
        try {
            
           Thread.sleep(800);
            play(72);
            Thread.sleep(200);
            play(72);
            Thread.sleep(200);
            play(74);
            Thread.sleep(400);
            play(72);
            Thread.sleep(400);
            play(79);
            Thread.sleep(400);
            play(77); 
        } catch (InterruptedException e) {}
            
     
    }

        void song3()
    {
        try {
            
           Thread.sleep(800);
            play(72);
            Thread.sleep(200);
            play(72);
            Thread.sleep(200);
            play(84);
            Thread.sleep(400);
            play(81);
            Thread.sleep(400);
            play(77);
            Thread.sleep(400);
            play(76);
            Thread.sleep(400);
            play(74);
        } catch (InterruptedException e) {}
            
     
    }

        void song4(){
             try {
           Thread.sleep(1200);
            play(82);
            Thread.sleep(200);
            play(82);
            Thread.sleep(200);
            play(81);
            Thread.sleep(400);
            play(77);
            Thread.sleep(400);
            play(79);
            Thread.sleep(400);
            play(77);
        } catch (InterruptedException e) {}
        }

            

            











}


public class mu{
    public static void main(String[] args)
    {
            musicApp m = new musicApp();
            m.song1();
            m.song2();
            m.song3();
            m.song4();
    }
}