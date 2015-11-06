/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CountDownTimer;

import java.util.Timer;
import java.util.TimerTask;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.layout.GridPane;


public class TimePane extends GridPane {

    private int hour = 0;
    private int minute = 0;
    private int second = 0;
//    private int totalSec = 0;
    private Label label = new Label("所用时间：");
    private Label hourLab = new Label(hour + " : ");
    private Label minLab = new Label(minute + " : ");
    private Label secLab = new Label(second + " ");

    
    public TimePane() {
        this.add(label,0,0);
        this.add(hourLab, 1, 0);
        this.add(minLab, 2, 0);
        this.add(secLab, 3, 0);
        GridPane.setMargin(label,new Insets(10,10,10,85));
    }

    /**
     * 设置时间的方法，在动画处理器（handler）中被调用
     * @param totalSec 
     */
    public void setTime(int totalSec) {
        getChildren().clear(); //清除原有的
        hour = totalSec / 3600;
        minute = (totalSec % 3600) / 60;
        second = totalSec - minute * 60 + hour * 3600;
        hourLab.setText(hour + " : ");
        minLab.setText(minute + " : ");
        secLab.setText(second + " ");
        this.add(label,0,0);
        this.add(hourLab, 1, 0);
        this.add(minLab, 2, 0);
        this.add(secLab, 3, 0);
    }


}
