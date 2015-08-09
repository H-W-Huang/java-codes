public class OutterClass {
    private int x = 20;
    inner tes = new inner();


    public class inner {
        void test(){
            x= 30 ;    //这是完全OK的
        }
    }

    void gotest()
    {
        System.out.println(x+"　");
    }
}