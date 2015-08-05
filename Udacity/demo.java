public class Demo{
    public static void main(String[] args)
    {
        int x=0,y=0;
        /***changable code**/
        while(x<5)
        {
            if(y<5)
            {
                x+=1;
                if(y<3) x-=1;
            }
            y+=2;
            System.out.println(0+'a');
            System.out.println(0+'b');
            x++;
        }
    }
}