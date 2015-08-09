import java.util.ArrayList;


    class theatre{
    
        void fb(movie m)
        {
            System.out.println("playing "+m.title);
            //必须是含有m的行为，不能是子类有而父类没有的方法，变量
        }
    }



    class movie{
        String title;
        int rating;
    
        //构造函数，参数设为String类型,一旦自己写了一个构造器，编译器将不再自行添加构造函数，因此无参数的也要写上
        public movie(){};
    
        public movie(String t)  
        {
            title = t;
        }
    
        void playing(){
            System.out.println("Now playing...");
        }
    
        void feedback()
        {
            
        }
    }
    
    
    class comedy extends movie{
    
        String category = "comedy";
    
        public comedy(){
            System.out.println("You won't miss a chance to have fun.---Comedy");
        }

        public comedy(String title){
            super("Mr. bean");  //调用父类的构造器
        }

        public comedy(int time)
        {
            this();
            System.out.println("The movie cost "+time+" mins to finish.");
        }
    
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
        comedy  c =new comedy(90);
        movies[0] = new comedy("Mr. bean");  //多态的使用
        movies[1] = new tregedy();
        theatre th = new theatre();

        th.fb(movies[0]);
        movies[0].feedback();     
        movies[1].feedback();
    }
}