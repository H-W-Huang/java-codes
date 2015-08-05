//创建类
class day{
    //内部变量
    int month;
    int date;


    //方法
    void add_evwnt(String even)
    {

    }

    void dispaly_date()
    {
        System.out.println(month+"/"+date+" ");
    }
}

//文件名应该与公共类的名字相同
public class daytest{
    public static void main(String[] args)
    {
        // 对象的创建
        day Today = new day();
        Today.month=8;
        Today.date=12;
        Today.dispaly_date();
    }
}