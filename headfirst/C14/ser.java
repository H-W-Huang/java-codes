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



public class ser{
    public static void main(String[] args) {

        Cat cat1 = new Cat();
        Cat cat2 = new Cat();
        Dog dog1 = new Dog("Mike");
        Dog dog2 = new Dog("Jack");

        try{
            FileOutputStream fs = new FileOutputStream("test.txt");
            ObjectOutputStream obs = new ObjectOutputStream(fs);
            obs.writeObject(dog1);
            obs.writeObject(dog2);
            obs.close();
        }catch(Exception e){
            e.printStackTrace();
        }

        
        
    }
}