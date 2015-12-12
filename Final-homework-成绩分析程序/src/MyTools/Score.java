/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MyTools;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 *
 * @author H.W
 */
public class Score {

    private ArrayList<Integer> scores = new ArrayList<>();
    private int max = 0;
    private int min = 101;
    private int[] scoreInterval = new int[5];   //分数区间
    private double[] percentageInterval = new double[5];  //百分比区间

    public Score() {

    }

    /**
     * 直接传递Arraylist
     *
     * @param list
     */
    public Score(ArrayList<Integer> list) {
        this.scores = list;
        statistic();
    }

    /**
     * ***************getters and setters ********************
     */
    /**
     * 获取分数项数
     *
     * @return
     */
    public int getSize() {
        return scores.size();

    }

    /**
     * 获取平均分
     *
     * @return
     */
    public double getAverage() {
        double sum = 0;
        for (int n : scores) {
            sum += n;
        }
        return sum / getSize();
    }

    /**
     * @return the scoreInterval
     */
    public int[] getScoreInterval() {
        return scoreInterval;
    }

    /**
     * @return the percentageInterval
     */
    public double[] getPercentageInterval() {
        return percentageInterval;
    }

    /**
     * @return the max
     */
    public int getMax() {
        return max;
    }

    /**
     * @return the min
     */
    public int getMin() {
        return min;
    }

    /**
     * *******************************************************
     */
    public void statistic() {

        //寻找最大值,最小值
        for (Integer n: scores){
            if(getMax() < n) max = n;
            if(getMin() > n) min = n;     
        }

        for (int n : scores) {
            if (n >= 90) {
                scoreInterval[0] += 1;
            } else if (n >= 80 && n <= 89) {
                scoreInterval[1] += 1;
            } else if (n >= 70 && n <= 79) {
                scoreInterval[2] += 1;
            } else if (n >= 60 & n <= 69) {
                scoreInterval[3] += 1;
            } else {
                scoreInterval[4] += 1;
            }
        }
        for (int i = 0; i < getScoreInterval().length; i++) {
            percentageInterval[i] = (getScoreInterval()[i]*1.0 / getSize())*100;
        }
    }

}
