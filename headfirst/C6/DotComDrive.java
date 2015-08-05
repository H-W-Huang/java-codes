import java.util.Scanner;
import java.util.ListArray;


// GameHelper ������ȡ������룬
class GameHelper{

     int Random_number()
     {
            return (int)(Math.random()*5); //����0��4֮�������
     }

     String get_guess()
     {
        Scanner s = new Scanner(System.in);
        String guess= s.nextLine();
        return guess;
     }
}



//�˴���Ϸ�����̻�����һ�����ϵģ�������������
/****
    ComPositions : ���ֵ�λ��
    Guseed ����¼�Ѿ����е�λ��
    numberOfHit �� ���еĴ���
    setPosition �����ⲿ��ȡλ����Ϣ���������õ���Ϸ������
    CheckYourself �� ����û��Ƿ���У����������Ϣ
    
****/
class game{
    
    int[] Visited ;
    int numberOfHit =0;
    int[] ComPositions;
    //��һ��������Ϊ��������
    void setPosition(int[] locs)
    {
        ComPositions=locs; //��סlocs��Compositions����ָ��
    }

    //����õķ���
    void CheckYourself(String userGuess)
    {
        //�Ƚ�Stringת��Ϊ����
        int guess = Integer.parseInt(userGuess);
        String result = "Miss";

        //��ǿ��forѭ������python�е� for x in a :һ����˼
        //�����cell���൱���ϱߵ�x
        for(int i=0;i<3;i++)
        {
            if(guess==ComPositions[i])
            {
                result="Hit";
                numberOfHit++;
                ComPositions[i]=-1;
                //System.out.println(result+" ");
                //����˴�����������ô��û���е�ʱ����Ҫ���miss�ͱȽ��鷳��
                //������˵��һ��string��Ĭ�Ͻ��Ϊmiss����Ҫʱ�ڸĶ���������������
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
        //������Ϸ
        game game1 = new game();
        boolean isAlive = true;
        int numberOfGuesses = 0;
        //int[] location = new location[3];
        //location[3]={1,2,3}  //������д���Ǵ����

        
        // �����������
        Scanner s = new Scanner(System.in);
        

        int random_num = (int)(Math.random()*5);
        int[] location={random_num,random_num+1,random_num+2};

        //��������
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
ʹ����ArrayList�İ汾

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