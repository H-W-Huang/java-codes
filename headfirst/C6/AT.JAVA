import java.util.Scanner;

public class AT{
    public static void main(String[] args)
    {
        Scanner input = new Scanner(System.in);
        int x;
        String name;
        x = input.nextInt();
        name = input.nextLine();
        System.out.println(x+" ");
        System.out.println(name);
    }
}

// import java.io.BufferedReader;
// import java.io.InputStreamReader;

// public class ttt{
// public static void main(String[] args){

// try{
// BufferedReader br=new BufferedReader(new InputStreamReader(System.in));
// System.out.println("请输入A:"); 
// String a=br.readLine();
// int b=2;
// int x=Integer.parseInt(a)+b;
// System.out.println("a+b="+x);
// }
// catch(Exception e){
// }
// }
// }