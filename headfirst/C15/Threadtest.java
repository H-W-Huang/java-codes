// 建立thread的任务
class Tasks implements Runnable{

    public void run(){
        go();
    }

    void go(){
        maketoast();
    }//习惯上go方法内只是调用了几个方法

    void maketoast(){
        // System.out.println("Not main");
        for(int i=0;i<50;i++){
            String threadName = Thread.currentThread().getName();
            System.out.println(threadName+" is running!");
        }
    }

}

public class Threadtest{
    public static void main(String[] args) {

            Tasks t = new Tasks();
            Thread tom = new Thread(t,"Tom");
            Thread jerry = new Thread(t,"Jerry");
            tom.start();
            jerry.start();
            // System.out.println("main");

    }
}

/*****
    System.out.println("Not main");
    System.out.println("main");
    两者的输出顺序是不定的。
******/


