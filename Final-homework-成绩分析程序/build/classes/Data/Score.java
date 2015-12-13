/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Data;


import Interface.ChartTool;
import java.util.ArrayList;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.stage.Stage;

/**
 *
 * @author H.W
 */
public class Score implements ChartTool {
    
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
        for (Integer n : scores) {
            if (getMax() < n) {
                max = n;
            }
            if (getMin() > n) {
                min = n;
            }
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
            percentageInterval[i] = (getScoreInterval()[i] * 1.0 / getSize()) * 100;
        }
    }

    /**
     * 绘制成绩分布直方图
     */
    @Override
    public BarChart<String, Number> getBarChart() {
        
        double upperBound =  0;
        final String[] intervalLabel = {"0-59", "60-69", "70-79", "80-89", "90-100"};
        double[] data = getPercentageInterval();
        //使用BarChart,需要两个轴，NumberAxis 和 CategoryAxis 
        NumberAxis yAxis = new NumberAxis(0, upperBound, 10);        //y轴
        CategoryAxis xAxis = new CategoryAxis();    //x轴

        //Number类型：The abstract class Number is the superclass of platform 
        //classes representing numeric values that are convertible to the primitive 
        //types byte, double, float, int, long, and short.注意是抽象的
        BarChart<String, Number> barChart = new BarChart<String, Number>(xAxis, yAxis);
        barChart.setTitle("成绩分布直方图");
        
        
        yAxis.setLabel("百分比");
        xAxis.setLabel("成绩区间");
        XYChart.Series series = new XYChart.Series();    //XYChart.Series也是一个数据结构

        for (int i = 0; i < data.length; i++) {
            if(Math.ceil(data[i])> upperBound ) upperBound = Math.ceil(data[i])+5;
            series.getData().add(new XYChart.Data(intervalLabel[4 - i], data[i]));
        }
        
        yAxis.setUpperBound(upperBound);

        barChart.getData().addAll(series);

        return barChart;
    }

    @Override
    public PieChart getPieChart() {
        
        PieChart pc = new PieChart();
        final String[] intervalLabel = {"0-59", "60-69", "70-79", "80-89", "90-100"};
        double[] data = getPercentageInterval();

        for (int i = 0; i < data.length; i++) {
            pc.getData().add(new PieChart.Data(intervalLabel[4 - i], data[i]));
        }
        
        return pc;
    }
    
    
    
}
