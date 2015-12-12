/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import MyTools.Score;
import MyTools.Student4GUI;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.Scanner;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

public class StatisticPane extends GridPane {

    final private int TEXT_FILE = 0;
    final private int BINARY_FILE = 1;
    private Score scoreData;
    private Label[] SpecialScores = new Label[3];  //特殊分数，包括最高分，最低分，平均分）
    private Label[] ScoreLevels = new Label[5];   //分数分布--标签
    private Label[] label = new Label[5];
    private Label[] percentage = new Label[5];
    private TextField[] percentageField = new TextField[5];
    private Button displayBarChartBtn;
    private Button displayPieChartBtn;
    private TextField[] SpecialScoresField = new TextField[3];  //特殊分数，包括最高分，最低分，平均分）
    private TextField[] ScoreLevelsField = new TextField[5];

    {
        SpecialScores[0] = new Label("最高分");
        SpecialScores[1] = new Label("最低分");
        SpecialScores[2] = new Label("平均分");
        ScoreLevels[0] = new Label("优秀(90-100)");
        ScoreLevels[1] = new Label("良好(80-89)");
        ScoreLevels[2] = new Label("中等(70-79)");
        ScoreLevels[3] = new Label("及格(60-69)");
        ScoreLevels[4] = new Label("不及格(0-59)");

        for (int i = 0; i < SpecialScoresField.length; i++) {
            SpecialScoresField[i] = new TextField();
            SpecialScoresField[i].setEditable(false);
        }

        for (int i = 0; i < ScoreLevelsField.length; i++) {
            ScoreLevelsField[i] = new TextField();
            ScoreLevelsField[i].setEditable(false);
            label[i] = new Label(" 人   占");
            percentage[i] = new Label("%");
            percentageField[i] = new TextField();
            percentageField[i].setPrefWidth(80);
            percentageField[i].setEditable(false);
        }

        displayBarChartBtn = new Button("显示柱状分析图");
        displayPieChartBtn = new Button("显示饼状分析图");

        displayBarChartBtn.setOnAction(e->{
           this.drawBarChart();
        });
        
        displayPieChartBtn.setOnAction(e->{
            this.getPieChart();
        });
    }

    public StatisticPane() {
        scoreData = new Score();
        
        
        this.add(SpecialScores[0], 0, 1);
        this.add(SpecialScores[1], 0, 2);
        this.add(SpecialScores[2], 0, 3);
        this.add(SpecialScoresField[0], 1, 1);
        this.add(SpecialScoresField[1], 1, 2);
        this.add(SpecialScoresField[2], 1, 3);
        this.add(ScoreLevels[0], 0, 4);
        this.add(ScoreLevels[1], 0, 5);
        this.add(ScoreLevels[2], 0, 6);
        this.add(ScoreLevels[3], 0, 7);
        this.add(ScoreLevels[4], 0, 8);
        this.add(ScoreLevelsField[0], 1, 4);
        this.add(ScoreLevelsField[1], 1, 5);
        this.add(ScoreLevelsField[2], 1, 6);
        this.add(ScoreLevelsField[3], 1, 7);
        this.add(ScoreLevelsField[4], 1, 8);
        this.add(label[0], 2, 4);
        this.add(label[1], 2, 5);
        this.add(label[2], 2, 6);
        this.add(label[3], 2, 7);
        this.add(label[4], 2, 8);
        this.add(percentage[0], 4, 4);
        this.add(percentage[1], 4, 5);
        this.add(percentage[2], 4, 6);
        this.add(percentage[3], 4, 7);
        this.add(percentage[4], 4, 8);
        this.add(percentageField[0], 3, 4);
        this.add(percentageField[1], 3, 5);
        this.add(percentageField[2], 3, 6);
        this.add(percentageField[3], 3, 7);
        this.add(percentageField[4], 3, 8);
        this.add(displayBarChartBtn, 0, 11);
        this.add(displayPieChartBtn, 1, 11);

        this.setPadding(new Insets(10, 20, 20, 20));
        this.setHgap(10);
        this.setVgap(25);
        this.setAlignment(Pos.CENTER);

    }

    /**
     * @return the scoreData
     */
    public Score getScoreData() {
        return scoreData;
    }

    /**
     * 读取文件，返回一个ArrayList
     *
     * @param file
     * @return
     */
    private ArrayList<Integer> readFileContent(File file, int type) throws FileNotFoundException, IOException {
        ArrayList<Integer> data = new ArrayList<>();
        if (type == TEXT_FILE) {
            Scanner input = new Scanner(file);
            while (input.hasNext()) {
                String[] studentData = input.nextLine().split(",");
                data.add(Integer.parseInt(studentData[2]));
            }

        } else {
            ObjectInputStream ois = new ObjectInputStream(new BufferedInputStream(new FileInputStream(file)));
            try {
                ArrayList<Student4GUI> temp = (ArrayList<Student4GUI>) ois.readObject();
                for (Student4GUI s : temp) {
                    data.add(s.getScore());
                }
            } catch (ClassNotFoundException ex) {
                System.out.println("找不到该类！");
            }
        }

        return data;
    }

    /**
     * 显示来自文件的数据的统计结果
     * @param file
     * @param type
     * @throws IOException 
     */
    public void displayDataFromFile(File file, int type) throws IOException {
        if (file != null) {
            if (type == TEXT_FILE) {
                scoreData = new Score(readFileContent(file, TEXT_FILE));
            } else {
                scoreData = new Score(readFileContent(file, BINARY_FILE));
            }
            int[] scoreInterval = getScoreData().getScoreInterval();
            double[] percentages = getScoreData().getPercentageInterval();

            SpecialScoresField[0].setText(getScoreData().getMax() + "");
            SpecialScoresField[1].setText(getScoreData().getMin() + "");
            SpecialScoresField[2].setText(String.format("%.2f", getScoreData().getAverage()));

            for (int i = 0; i < ScoreLevelsField.length; i++) {
                ScoreLevelsField[i].setText(scoreInterval[i] + "");
                percentageField[i].setText(String.format("%.2f", percentages[i]));
            }
        } else {

        }
    }
    
    
    private void drawBarChart(){
        scoreData.getBarChart();
    }
    
    private void getPieChart(){
        scoreData.getPieChart();
    }
}



/**
 * 该类读取文件有两种方式，是一种是在构造对象是一起读入 另一种是另外提供一个file
 */
