

//首先是一个 父类 Animal
class Animal{

    int type =2;
    void eat(){
        System.out.println("eating...");
    }

    void makeNoise(){
        System.out.println("~~~~~");
    }
}


//创建子类
class Canine extends Animal{

    int type=1;
    //将覆盖Animal的makeNoise方法
    void makeNoise(){
        System.out.println("Auuuuu~~~~~");  //覆盖父类的方法
    }
}

class Dog extends Canine{
    boolean isFriend = true;
    void makeNoise(){
        super.makeNoise();  //只能在方法是上一个父类的情况下起作用
        System.out.println("-----");   //在父类方法的基础上追加新的内容
    }
}



public class heritage{
    public static void main(String[] args)
    {
        Dog m = new Dog();
        if(m.isFriend) System.out.println("Dogs are our friends!");
        m.eat();            //继承了父类的方法，
        m.makeNoise();          //继承了父类的方法，
        System.out.println(m.type);
    }
}