import java.util.ArrayList;
import java.io.BufferedReader;
public class EnemyShip{

    String name;
    ArrayList<String> locationCells; 

    void setName(String n){   
        name =n;
    }
    void setLocation(ArrayList<String> loc)
    {
        locationCells = loc;
    }

    void checkyourself(String userGuess)
    {
        String result= "Miss!";
        int index = locationCells.indexOf(userGuess);
        if(index!=-1)
        {
            locationCells.remove(index);
            if(locationCells.isEmpty())
            {
                result = "Kill!";
                System.out.println(name + "has been sunk!");
            }
            else result = "Hit!" ;
        }

    }

}


public class Helper{

    private static final String alphabet = "abcdef" ; 
    private int gridLength = 7 ;                      
    private int gridSize = gridLength * gridLength;   
    private int[] grid =int[gridLength];              
    private int comCount = 0 ;                        



    public String getUserInput(String prompt)
    {
        String inputLine = null ; 
        System.out.println(prompt + " ");  
        try{
            BufferedReader is = new BufferedReader(new InputStreamReader(System.in));
            inputLine = is.readLine();
            if(inputLine.length() == 0 ) return null
        }catch(IOException e){
            System.out.println("IOException :"+e);
        }
        return inputLine.toLowerCase();  
    }



    public ArrayList<String> placeShip(int shipSize)
    {
        ArrayList<String> alphaCells = new ArrayList<String>();
        String[] alphacoords = new String[shipSize];  
        String temp = null ;
        int[] coords = new int[shipSize];             

        int attempts = 0 ;                          
        boolean success = 0; 
        int location;                                 

        shipSize++;
        int increament = 1;                           
     if((shipSize%2)==1)                        
        {
            increament = gridLength;                  
        }

        while( ! success && attempts++ < 200)         
        {
            location = (int)(Math.random()*gridSize);    
            int x = 0;                                   
            success = true ;                             
            while(success && x < shipSize)
            {
                // gird--长度为49
                if( grid[location]== 0 )     
                {
                    coords[x++] = location ;
                    location += increament ;  
                    if((location >= gridSize) ||(x > 0 && (location % gridLength==0)))

                        success = false ; 
                } //end if
                else 
                {
                    //找到可用位置
                    success = true ;
                }//end else 
            }// end while
        }// end while 

        int x=0;        
        int row = 0;    
        int column = 0;
        while(x< shipSize)
        {
            grid[coords[x]] = 1;   
            row = (int)(coords / gridLength);
            column = coords[x] % gridLength ;
            temp = String.valueOf(alphabet.charAt(column));   
            alphaCells.add(temp.concat(Interger.toString(row)));
        }
    }
}


public ShipBust{

    private Helper h =new Helper();
    private ArrayList<EnemyShip> shipList = new ArrayList<EnemyShip>; 
    private numberOfGuess = 0;



    private setupShips()
    {
        
        EnemyShip ship1 = new EnemyShip();
        EnemyShip ship2 = new EnemyShip();
        EnemyShip ship3 = new EnemyShip();

        
        ship1.setName("A");
        ship2.setName("B");
        ship3.setName("C");


        shipLsit.add(ship1);
        shipLsit.add(ship2);
        shipLsit.add(ship3);

        
        System.out.println("Your Goal is to sink these ships.");
        System.out.println("They are "+ship1.name+" "+ship2.name+" "+ship3.name+" ");
        System.out.println("Your Goal is to sink these ships.");

        //船舰布局
        for(ArrayList<EnemyShip> s : shipList )
        {
            ArrayList<String> newlocation = h.placeShip(3);
            s.setLocation();
        }

    }



    private void startGame()
    {
        while(!ShipLsit.isEmpty())
        {
            System.out.println("Enter your Guess.");
            /* user enter guess */
            numberOfGuess++;
        }


        finishGame();
    }



    private void checkUserGuess(String userGuess)
    {
        
        String result = "Miss!" ;
        for(EnemyShip s : shipList)
        {
            if(result.equals("Hit!"))
                break;
            if(result.equals("Kill!"))
                shipLsit.remove(s);
        }
        System.out.println(result);
    }



   
    private void finishGame()
    {
        System.out.println("You win! All the ships have been sunk!");
    }
}


public class R{
    public static void main(String[] args)
    {
        ShipBust game1 = new ShipBust();
        game1.setupShips();
        game1.startGame();
    }
}