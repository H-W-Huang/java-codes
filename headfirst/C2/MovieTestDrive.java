class Movie{

    String title; //标题
    String genre; //分类
    int rating;     //评分

    void playIt(){
        System.out.println("Now playing "+title+"...");
    }
}

public class MovieTestDrive{
    public static void main(String[] args)
    {
        
        // Movie m1 = new Movie();
        // Movie m2 = new Movie();

        // m1.title="Inception";
        // m2.title="Birdman";
        // m1.genre = "Crime";
        // m2.genre = "Comedy";
        // m1.rating=5;
        // m2.rating=3;

        // m1.playIt();

        //Movie[] m;  //声明Moive数组；
        //m = MOvie[2]; 
        Movie[] m = new Movie[2];
        //次数的Movie[]可不是之前自己定义的那个Moive，它是一个新的类，关于Moive的一个数组类。
        //所以声明一个数组的过程也就是声明一个引用和创建一个对象的过程
        m[0] = new Movie();  //对于每一个引用变量，还是需要为其创建一个对象的
        m[1] = new Movie();
        m[0].title="Inception";
        m[1].title="Birdman";
        m[0].genre = "Crime";
        m[1].genre = "Comedy";
        m[0].rating=5;
        m[1].rating=3;
        m[1].playIt();
    }
}