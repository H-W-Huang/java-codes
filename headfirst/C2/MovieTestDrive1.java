class Movie{

    private String title; //����
    String genre; //����
    int rating;     //����

    void playIt(){
        System.out.println("Now playing "+title+"...");
    }

    void setTitle(String t)
    {
        title=t;
    }
}

public class MovieTestDrive1{


    public static void main(String[] args)
    {
        int x=12;
        System.out.println("x is :"+x);
        // Movie m1 = new Movie();
        // Movie m2 = new Movie();

        // m1.title="Inception";
        // m2.title="Birdman";
        // m1.genre = "Crime";
        // m2.genre = "Comedy";
        // m1.rating=5;
        // m2.rating=3;

        // m1.playIt();

        //Movie[] m;  //����Moive���飻
        //m = MOvie[2]; 
        Movie[] m = new Movie[2];
        //������Movie[]�ɲ���֮ǰ�Լ�������Ǹ�Moive������һ���µ��࣬����Moive��һ�������ࡣ
        //��������һ������Ĺ���Ҳ��������һ�����úʹ���һ������Ĺ���
        m[0] = new Movie();  //����ÿһ�����ñ�����������ҪΪ�䴴��һ�������
        m[1] = new Movie();
        //m[0].title="Inception";  //һ������������Ϊprivate��ʽ�������Ĳ�����ʵ�ֲ��ˣ��������ᱨ��
        m[0].setTitle("Inception");
        
        m[0].genre = "Crime";
        m[1].genre = "Comedy";
        m[0].rating=5;
        m[1].rating=3;
        m[0].playIt();
    }

    
}