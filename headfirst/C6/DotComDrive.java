import java.util.Scanner;
import java.util.ListArray;


// GameHelper 用来获取玩家输入，
class GameHelper{

     int Random_number()
     {
            return (int)(Math.random()*5); //产生0～4之间的整数
     }

     String get_guess()
     {
        Scanner s = new Scanner(System.in);
        String guess= s.nextLine();
        return guess;
     }
}



//此处游戏的棋盘还是在一条线上的，用数组来储存
/****
    ComPositions : 布局的位置
    Guseed ：记录已经猜中的位置
    numberOfHit ： 猜中的次数
    setPosition ：从外部获取位置信息，并且运用到游戏布局上
    CheckYourself ： 检查用户是否猜中，给出相关信息
    
****/
class game{
    
    int[] Visited ;
    int numberOfHit =0;
    int[] ComPositions;
    //将一个数组作为参数传入
    void setPosition(int[] locs)
    {
        ComPositions=locs; //记住locs和Compositions都是指针
    }

    //检查用的方法
    void CheckYourself(String userGuess)
    {
        //先将String转化为数字
        int guess = Integer.parseInt(userGuess);
        String result = "Miss";

        //加强版for循环，和python中的 for x in a :一个意思
        //下面的cell就相当于上边的x
        for(int i=0;i<3;i++)
        {
            if(guess==ComPositions[i])
            {
                result="Hit";
                numberOfHit++;
                ComPositions[i]=-1;
                //System.out.println(result+" ");
                //如果此处这样处理，那么当没猜中的时候，想要输出miss就比较麻烦，
                //倒不如说明一个string，默认结果为miss，需要时在改动，这样反而方便
                break;
            }
        }
        System.out.println(result+" ");
        if(numberOfHit==3)
        {
            result="Enemy has been Killed!";
            System.out.println(result+" ");
        }
    }
}




public class DotComDrive{
    public static void main(String[] args)
    {
        //创建游戏
        game game1 = new game();
        boolean isAlive = true;
        int numberOfGuesses = 0;
        //int[] location = new location[3];
        //location[3]={1,2,3}  //这样的写法是错误的

        
        // 创建玩家数据
        Scanner s = new Scanner(System.in);
        

        int random_num = (int)(Math.random()*5);
        int[] location={random_num,random_num+1,random_num+2};

        //创建布局
        game1.setPosition(location);

        while(isAlive)
        {
            System.out.println("Enter your choice:");
            String uguess=s.nextLine();
            game1.CheckYourself(uguess);
            if(game1.numberOfHit==3) isAlive=false;
            numberOfGuesses++;
        }
        System.out.println("You make "+numberOfGuesses+" guesses to win the game!");
    }
}





/*****
使用了ArrayList的版本

import java.util.Scanner;
import java.util.ArrayList;

class GameHelper{

     int Random_number()
     {
            return (int)(Math.random()*5); 
     }

     String get_guess()
     {
        Scanner s = new Scanner(System.in);
        String guess= s.nextLine();
        return guess;
     }
}




class game{
    
    int[] Visited ;
    int numberOfHit =0;
    ArrayList<String> ComPositions;

    void setPosition(ArrayList<String> locs)
    {
        ComPositions=locs;
    }


    void CheckYourself(String userGuess)
    {
        String result = "Miss";

        int index = ComPositions.indexOf(userGuess);
        if(index!=-1)
        {
            ComPositions.remove(userGuess);
            numberOfHit++;
            result="Hit!";
        }
        System.out.println(result+" ");
        if(ComPositions.isEmpty())
        {
            result="Enemy has been Killed!";
            System.out.println(result+" ");
        }
    }
}




public class D{
    public static void main(String[] args)
    {

        game game1 = new game();
        boolean isAlive = true;
        int numberOfGuesses = 0;

        Scanner s = new Scanner(System.in);
        

        int random_num = (int)(Math.random()*5);
        ArrayList<String> location = new ArrayList<String>();
        location.add(Integer.toString(random_num));
        location.add(Integer.toString(random_num+1));
        location.add(Integer.toString(random_num+2));


        game1.setPosition(location);


        while(isAlive)
        {
            System.out.println("Enter your choice:");
            String uguess=s.nextLine();
            game1.CheckYourself(uguess);
            if(game1.numberOfHit==3) isAlive=false;
            numberOfGuesses++;
        }
        System.out.println("You make "+numberOfGuesses+" guesses to win the game!");
    }
}


****/