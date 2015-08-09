    //创建动物类
    abstract class Animal{
        int size;
    
        abstract void eat(String food) throws FoodException ;
    }
    
    class FoodException extends Exception{
    
    }
    
    
    class Dog extends Animal{
    
        public Dog(int s){
            size = s ;
        }
    
    
        void eat(String food) throws FoodException{
            if(food.equals("CatFood")){ 
                throw new FoodException();  //注意！异常也是对象！
            }
            else System.out.println("Now eating dog food~");
        }  //父类的这个方法也有能抛出这个异常
    }
    
    public class e{
        public static void main(String[] args)
        {
            Dog d = new Dog(25);
            try{
                d.eat("CatFood");
            }
            catch(FoodException ex){
                System.out.println("Oh!Ma Niggar!I ain't eating Cat food!");
            }
        }
    }


    void eat(String food) throws FoodException
    public static void main(String[] args) throws FoodException{
        Dog d = new Dog(25);
        d.eat("CatFood");
    }