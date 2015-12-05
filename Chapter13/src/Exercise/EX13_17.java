/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Exercise;

import java.util.Scanner;

/**
 *
 * @author H.W
 */
public class EX13_17 {

}

class Complex implements Comparable<Complex> {

    double a;  //实部
    double b;  //虚部

    public Complex() {
        this(0, 0);
    }

    public Complex(double a) {
        this(a, 0);
    }

    public Complex(double a, double b) {
        this.a = a;
        this.b = b;
    }

    /**
     * *********getter and setter **************
     */
    public double getRealPart() {
        return a;
    }

    public double getImaginaryPart() {
        return b;
    }

    public Complex add(Complex another) {
        Complex temp = new Complex(this.a + another.a, this.b + another.b);
        return temp;
    }

    public Complex substract(Complex another) {
        Complex temp = new Complex(this.a - another.a, this.b - another.b);
        return temp;
    }

    public Complex multiply(Complex another) {
        Complex temp = new Complex(this.a * another.a - this.b * another.b, this.b * another.a + this.a * another.b);
        return temp;
    }                   

    public Complex divide(Complex another) {
        double dividor = another.a * another.a + another.b * another.b;
        Complex temp = new Complex((this.a * another.a + this.b * another.b) / dividor, (this.b * another.a - this.a * another.b) / dividor);
        return temp;
    }

    @Override
    public String toString() {
        String result = String.format("%.3f + %.3fi", this.a,this.b);
        return result;
    }

    @Override
    public int compareTo(Complex another) {
        double r1 = this.a * this.a + this.b * this.b;
        double r2 = another.a * another.a + another.b * another.b;
        if (r1 == r2) {
            return 0;
        } else if (r1 > r2) {
            return 1;
        } else {
            return -1;
        }
    }

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        System.out.println("Enter the first complex number:");
//        Complex one = new Complex(input.nextDouble(), input.nextDouble());
        Complex one = new Complex(3.5, 5.5);

        System.out.println("Enter the second complex:");
//        Complex two = new Complex(input.nextDouble(), input.nextDouble());
        Complex two = new Complex(-3.5, 1);

        System.out.println(one.add(two));
        System.out.println(one.substract(two));
        System.out.println(one.multiply(two));
        System.out.println(one.divide(two));
        System.out.println(one);
        System.out.println(one.compareTo(two));

    }

}
