//直接对字符串进行排序
//第二种方法
//使用自制的Comparator

import java.util.*;
import java.io.*;


class Song implements Comparable<Song>{   //注意Complemets后带数据类型
    String title;
    String artist;


    public Song(String t,String a){
        title=t;
        artist=a;
    }


    String getArtist(){
        return artist;
    }

    String getTitle(){
        return title;
    }

    public int compareTo(Song a){              //这个comparTo是我们自己为Song定义的
        return title.compareTo(a.title);     //这个comparTo是String类里的CompareTo()
    }


    //覆盖hashCode和equals
    public boolean equals(Object aSong){
        Song a = (Song) aSong;
        return getTitle().equals(a.getTitle());
    }

    public int hashCode(){
        return title.hashCode();   //注意到作为title的String也是一个对象，若两个String是一样的，那么其hashCode也是一样的
    }
}



public class Equaltest{
    //歌单存储在ArrayList里
    ArrayList<Song> songlist  = new ArrayList<Song>();

    public static void main(String[] args) {
        Equaltest test = new Equaltest();
        test.go();
    }

    void go(){
        getSongs();

        System.out.println(songlist.get(0).hashCode());
        System.out.println(songlist.get(1).hashCode());
        System.out.println(songlist.get(2).hashCode());
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
        songlist.add(new Song(token[0],token[1]));
    }



    class ArtistComparator  implements Comparator<Song>{
        public int compare(Song one,Song two)
        {
            return one.getArtist().compareTo(two.getArtist());
        }
    }

    class TitleComparator implements Comparator<Song>{
        public int compare(Song one,Song two)
        {
            return one.getTitle().compareTo(two.getTitle());
        }
    }

}