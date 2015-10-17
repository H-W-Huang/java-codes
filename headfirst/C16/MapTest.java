import java.util.*;
public class MapTest{
    public static void main(String[] args) {
        MapTest test = new MapTest();
        test.go();

    }

    void go(){
        HashMap<String,Integer> map = new HashMap<String,Integer>();

        map.put("one",1);
        map.put("two",2);
        map.put("Three",3);

        System.out.println(map);
        System.out.println(map.get("one"));
    }
}