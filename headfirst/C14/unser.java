import java.io.*;


class Cat implements Serializable{
    int size = 10;
}

// class Dog implements Serializable{
//     int size = 20;
//     transient Cat cat1  = new Cat();
//     Cat cat2  = new Cat();
// }
class Dog implements Serializable{
    int size = 20;
    String name;
    Cat cat;
    public Dog(String n){
        name = n;
    }

    public Dog(Cat c){
        cat = c;
    }
}

public class unser{
    public static void main(String[] args) {
        try{
             FileInputStream fs = new FileInputStream("test.txt");
            ObjectInputStream os = new ObjectInputStream(fs);
            Object one = os.readObject();           //注意读出来的对象是Object类型
            Dog dog1 = (Dog) one;
            Dog dog2 = (Dog) os.readObject();
    
            System.out.println(dog1.name+" and "+dog2.name);
        }catch(Exception ex){ex.printStackTrace();}
       
    }
}