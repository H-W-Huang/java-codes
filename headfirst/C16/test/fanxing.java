



class Animal{
    void eat(){
        System.out.println("eating!");
    }
}

class Cat extends Animal{
    void control(){
        System.out.println("controling!");

    }
}

public class fanxing{
    int index =0;

    public static void main(String[] args) {
        
        fanxing test = new fanxing();
        test.go();
        
    }
    void go(){
        Animal[] a  = new Animal[5];
        addAnimal(a);
        System.out.println(a[0]);
    }


    void addAnimal(Animal[] animals){
        animals[index] = new Cat();
        index++;
    }
}       