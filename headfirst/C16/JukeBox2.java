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
}



public class JukeBox2{
    //歌单存储在ArrayList里
    ArrayList<Song> songlist  = new ArrayList<Song>();

    public static void main(String[] args) {
        JukeBox2 box = new JukeBox2();
        box.go();
    }

    void go(){
        getSongs();
        System.out.println(songlist);
        Collections.sort(songlist); //编译不通过的错误存在于此，传入String可以，传入Song就不行。
        ///文档中显示;implements Serializable, Comparable<String>，所以我们要给Song接入Conparable
        System.out.println(songlist);
        Collections.sort(songlist,new ArtistComparator()); //编译不通过的错误存在于此，传入String可以，传入Song就不行。
        System.out.println(songlist);
        Collections.sort(songlist,new TitleComparator()); //编译不通过的错误存在于此，传入String可以，传入Song就不行。
        System.out.println(songlist);


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