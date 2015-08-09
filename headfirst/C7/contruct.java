import java.util.ArrayList;


class theatre{

    void fb(movie m)
    {
        System.out.println("playing "+m.title);
        //必须是含有m的行为，不能是子类有而父类没有的方法，变量
    }
}



class movie{
    String title="set";
    int rating;

    void playing(){
        System.out.println("Now playing...");
    }

    void feedback()
    {
        
    }
}


class comedy extends movie{

    String category = "comedy";
    void feedback()
    {
        System.out.println("^ _ ^");
    }
}

class tregedy extends movie{

    String category = "tregedy";

    void feedback()
    {
        System.out.println("T _ T");
    }
}

public class duotai{
    public static void main(String[] args)
    {
        movie[] movies = new movie[2];
        tregedy t =new tregedy();
        movies[0] = new comedy();  //多态的使用
        movies[1] = new tregedy();
        theatre th = new theatre();

        th.fb(movies[0]);
        movies[0].feedback();     
        movies[1].feedback();
    }
}