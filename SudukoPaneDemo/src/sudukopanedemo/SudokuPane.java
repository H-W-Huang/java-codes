/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sudukopanedemo;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;

/**
 * SudokuPane包含两个部分 1. 数据面板 2. 控制面板
 *
 * @author H.W
 */
class SudokuPane extends BorderPane {

    private GridPane dataPane = new GridPane();
    private HBox controlPane = new HBox();
    private Button[] options = new Button[9];
    private Button selectedButton = null; // 姑且为GUI显示方便而创建的变量，具体要不要待定
    private TextField[][] textBoxs = new TextField[9][9];
    private int inputValue = -1;  // -1的情况下不能改变TextField的值

    public SudokuPane() {
        this.setCenter(dataPane);
        this.setBottom(controlPane);
        dataPane.setPadding(new Insets(15, 25, 15, 25));
        controlPane.setPadding(new Insets(10, 10, 50, 85));

        //对数据面板进行初始化
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                textBoxs[i][j] = new TextField("1");
                textBoxs[i][j].setEditable(false);
//                textBoxs[i][j].setStyle(""
//                        + "-fx-font-size: 20px;"
//                        + "-fx-font-weight: bolder;"
//                        + "-fx-color: red;"
//                        + "-fx-border: 4px;"
//                        + "-fx-border-radius: 5px;"
//                        + "-fx-cursor: pointer;"
//                );

                //添加点击改变数值的动作
                textBoxs[i][j].setOnMouseClicked(e -> {
                    TextField temp = (TextField) e.getSource();
                    if (temp.isFocused() && inputValue != -1) {
                        System.out.println(inputValue + "");
                        temp.setText(inputValue + "");
                        inputValue = -1;
                        selectedButton.setStyle(""
                                + "-fx-background-color: none;"
                                + "border: 2px;"
                                
                        );
                    }

                });
                dataPane.add(textBoxs[i][j], j % 10, i);
            }
        }

        //对控制面板的按钮进行初始化
        for (int i = 0; i < 9; i++) {
            options[i] = new Button((i + 1) + "");
            options[i].setPadding(new Insets(10, 10, 10, 10));
            options[i].setStyle(""
                    + "-fx-background-color: none;"
            );
            controlPane.getChildren().add(options[i]);
            options[i].setOnAction(new EventHandler<ActionEvent>() {
                public void handle(ActionEvent e) {
                    //获取button的值，并赋予buttonValue
                    //异常处理，解决第一次点击按钮但是selectedButton为null而出现的异常
                    try{
                       selectedButton.setStyle(""
                        + "-fx-background-color: none;"
                        ); 
                    }
                    catch(Exception ex){
                        selectedButton = (Button) e.getSource();
                    }
                    Button tempbtn = (Button) e.getSource();
                    selectedButton = tempbtn;
                    inputValue = Integer.parseInt(tempbtn.getText());
                    tempbtn.setStyle(""
                            + "-fx-background-color:#FF4747 ");
                    System.out.println(inputValue);
                }
            });
        }
    }

}
