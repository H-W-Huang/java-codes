/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Test;

public class ExtendsTest {

    public static void main(String[] args) {
        Object o = new Student();
        Student a = new Graduate();

        System.out.println(((Person) o).getScore()); // 
        System.out.println(a.getScore()); // 等价于 System.out.println(a.toString());

    }
}

class Person extends Object {

    @Override
    public String toString() {
        return "A person";
        
    }
            

    public int getScore() {
        return 70;
    }
}


class Student extends Person {

    int score;

    public Student() {
        score = 80;
    }

//    @Override
//    public String toString() {
//        return "A Student";
//    }
    
    @Override
    public int getScore() {
        return score;
    }
}

class Graduate extends Student {

    @Override
    public String toString() {
        return "A Student";
    }

//    @Override
//    public int getScore() {
//        return 90;
//    }
}

/**
 * 多态
 *  引用变量为 Object 类型 ，所指向的对象为 Gradute （Object子类），
 *  若调用的是 （父类含有的） Overrided 的 方法，那么调用的是该继承树最后出现过的Override的这个方法（即最新版）
 *  所能调用到方法的新旧程度由 引用变量所指向的 对象的类型确定 ： 拿 getScore()方法为例子 
 *  Object o = new Student();
 *  Student a = new Graduate();
 *  
 * System.out.println(((Student)o).getScore()); // ---> 80
 * System.out.println(a.getScore()); // ---> 90
 * （ 如果Gradute类没有覆盖getScore()方法的话，就沿着继承树，以Gradute为起点，向上寻找最新的方法）
 * 
 * 在此也反映了一个问题
 * 
 * 在对o调用getScore()  （Object子类所新增的方法）方法时，若没有类型转换，那么就会编译错误
 * 此时getScore()方法的新旧程度有所转换的类型决定 （其实也就是由所引用的对象的类型决定，毕竟强制类型转换只能转换为对应的类型(或者是其父类)）
 * 
 * 
 * 
 * 
 *  
 * 
 */
