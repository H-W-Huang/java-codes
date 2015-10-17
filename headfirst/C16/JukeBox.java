//直接对字符串进行排序

import java.util.*;
import java.io.*;


public class JukeBox{
    //歌单存储在ArrayList里
    ArrayList<String> songlist  = new ArrayList<String>();
    int[] x = {4,2,7,9,1};
    // int[] b = new int[5];
    ArrayList<Integer> a = new ArrayList<Integer>();  //注意了，主类型与对用对象的转换


    public static void main(String[] args) {
        JukeBox box = new JukeBox();
        box.go();
    }

    void go(){
        getSongs();
        for(int i:x) a.add(i);
        System.out.println(songlist);
        //sort()放在Collections里
        Collections.sort(songlist);
        Collections.sort(a);
        System.out.println(songlist);
        HashSet<String> songset = new HashSet<String>();
        songset.addAll(songlist);
        System.out.println(songset);
    }   
    
    //文件读入
    void getSongs(){
        try{
            String line = null;
            File file = new File("songs.txt");
            FileReader fr  = new FileReader(file);
            BufferedReader reader = new BufferedReader(fr);
            while((line=reader.readLine())!=null){
                // songlist.add(line);,处于设计上的考虑，将添加歌曲单度作为一方法
                addSong(line);
            }
        }catch(Exception ex){
            ex.printStackTrace();
        }
    } 

    void addSong(String songInfo){
        String token[] = songInfo.split("/");
        songlist.add(token[0]);
    }
}