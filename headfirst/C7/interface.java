
abstract class Animal{
    void makeNoise()
    {
        System.out.println("-~-~-~-");
    }

    abstract void eat();   //抽象方法，对于每个子类，都必须实现该方法
}

class Canine extends Animal{

    void eat()
    {
        System.out.println("eating...");
    }

    void bark()
    {
        System.out.println("Woah!!");
    }
}



class pet{
    boolean isFrendly = true;
    void bark(){
        System.out.println("master~~");
    }
}


class Dog extends Canine {
    boolean canHunt = true;
}

public class interface{
    public static void main(Stringp[] args)
    {
        Dog a = new Dog();
    }
}
