/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sudukopanedemo;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;

/**
 * SudokuPane包含两个部分 1. 数据面板 2. 控制面板
 *
 * @author H.W
 */
class SudokuPane extends BorderPane {

    private GridPane dataPane = new GridPane(); //数据面板
    private HBox controlPane = new HBox();    //控制面板用来存放按钮
    private Button[] options = new Button[9];
    private Button selectedButton = null; // 姑且为GUI显示方便而创建的变量，具体要不要待定
    private SudokuTextField[][] textBoxs = new SudokuTextField[9][9];  //使用订制的TextField -- SudokuTextField
    private int[][] currentData = new int[9][9];
    private int inputValue = -1;  // -1的情况下不能改变TextField的值
    private int buttonValue = 0; //在检查时使用的变量
    private int[] layoutValue = new int[81];   //从文件读入的数据
    private int currentRow = 0;   //被选中的格子的行
    private int currentColumn = 0;  //被选中的格子的列
    private int stepToWin = 0; // 完成游戏所需要的步数

    //SudokuPane构造函数
    public SudokuPane() {
        this.setCenter(dataPane);
        this.setBottom(controlPane);
        dataPane.setPadding(new Insets(15, 25, 15, 25));
        controlPane.setPadding(new Insets(10, 10, 50, 35));

        layoutValue = (new SudokuData()).getData(1);

        //对数据面板进行初始化
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                currentData[i][j] = layoutValue[j + i * 9];
                if (layoutValue[j + i * 9] != 0) {
                    textBoxs[i][j] = new SudokuTextField(Integer.toString(layoutValue[j + i * 9]));
                    textBoxs[i][j].setCursor(Cursor.HAND);
                    textBoxs[i][j].setRow(i);
                    textBoxs[i][j].setCol(j);
                    textBoxs[i][j].setInitialized(true);

                } else {
                    textBoxs[i][j] = new SudokuTextField(" ");
                    textBoxs[i][j].setRow(i);
                    textBoxs[i][j].setCol(j);
                    stepToWin++;
                }

                textBoxs[i][j].setEditable(false);

                //添加点击改变数值的动作
                textBoxs[i][j].setOnMouseClicked(e -> {
                    System.out.println("所在行数:" + ((SudokuTextField) e.getSource()).getRow());
                    System.out.println("所选为：" + ((SudokuTextField) e.getSource()).getText());
                    if (!((SudokuTextField) e.getSource()).isInitialized()) {
                        if (e.getButton() == MouseButton.PRIMARY) {
                            setBoxValue(e, false);
                            System.out.println("所在的行列：" + currentRow + " " + currentColumn);
                            if (!checkInput(currentRow, currentColumn)) {
                                ((SudokuTextField) e.getSource()).setStyle(""
                                        + "-fx-text-inner-color: red;");
                            } else {
                                ((SudokuTextField) e.getSource()).setStyle(""
                                        + "-fx-text-inner-color: green;");
                                stepToWin--;
                                System.out.println("剩余步数：" + stepToWin);
                            }
                        } else if (e.getButton() == MouseButton.SECONDARY) {
                            setBoxValue(e, true);
                        }
                    }

                });
                dataPane.add(textBoxs[i][j], j % 10, i);
            }
        }

        //对控制面板的按钮进行初始化
        for (int i = 0; i < 9; i++) {
            options[i] = new Button((i + 1) + "");
            options[i].setPadding(new Insets(6, 12, 6, 16));
            options[i].setCursor(Cursor.HAND);
            options[i].setStyle(""
                    + "-fx-background-color: none;"
            );
            controlPane.getChildren().add(options[i]);
            options[i].setOnAction(new EventHandler<ActionEvent>() {
                public void handle(ActionEvent e) {
                    getButtonValue(e);
                }
            });
        }
    }

    /**
     * 对格子赋值 该方法将在格子的事件被触发时被调用
     */
    public void getButtonValue(ActionEvent e) {
        //获取button的值，并赋予buttonValue
        //异常处理，解决第一次点击按钮但是selectedButton为null而出现的异常
        try {
            selectedButton.setStyle(""
                    + "-fx-background-color: none;"
            );
        } catch (Exception ex) {
            selectedButton = (Button) e.getSource();
        }
        Button tempbtn = (Button) e.getSource();
        selectedButton = tempbtn;
        inputValue = Integer.parseInt(tempbtn.getText());
        buttonValue = Integer.parseInt(tempbtn.getText());
        tempbtn.setStyle(""
                + "-fx-background-color:#858585 ");
        System.out.println("选中了 " + inputValue);
    }

    /**
     * 获取被被点击的按钮的值 同样在按钮事件被触发时被调用
     */
    public void setBoxValue(MouseEvent e, boolean doesWipe) {
        SudokuTextField target = (SudokuTextField) e.getSource();
        int i = target.getRow();
        int j = target.getCol();
        if (target.isFocused() && inputValue != -1 && !doesWipe) {
            System.out.println(inputValue + "");
            target.setText(inputValue + "");
            currentColumn = target.getCol();
            currentRow = target.getRow();
            selectedButton.setStyle(""
                    + "-fx-background-color: none;"
                    + "border: 2px;"
            );
            currentData[i][j] = inputValue;
            System.out.println("Input value is :" + inputValue);
            printArray(currentData);
            inputValue = -1;
        } else if(target.isFocused() && doesWipe) {
            target.setText(" ");
            currentData[i][j] = 0;
            printArray(currentData);
            inputValue = -1;
        }
}

/**
 * 检查函数，每一次填入数值时将调用该函数。类型为布尔型 能够判断的条件是 该行/列/宫已经填充完毕 检查分为三个部分： 行检查（检查每一行是否合法）
 * 列检查（检查每一列是否合法） 宫检查
 */
public boolean checkInput(int currentRow, int currentColumn) {
        return isRowLegal(currentRow, currentColumn) && isColLegal(currentRow, currentColumn)
                && isUnitLegal(currentRow, currentColumn);

    }

    /**
     * 行检查
     *
     * @param i
     * @param j
     * @return
     */
    public boolean isRowLegal(int currentRow, int currentColumn) {
        for (int val = 0; val < 9; val++) {
            if (val == currentColumn) {
                continue;
            }
            if (currentData[currentRow][val] == buttonValue) {
                System.out.println(currentData[currentRow][val] + " ");
                System.out.println("row-bad");
                return false;
            }

        }
        return true;
    }

    /**
     * 列检查
     *
     * @param i
     * @param j
     * @return
     */
    public boolean isColLegal(int currentRow, int currentColumn) {
        for (int val = 0; val < 9; val++) {
            if (val == currentRow) {
                continue;
            }
            if (currentData[val][currentColumn] == buttonValue) {
                System.out.println(currentData[val][currentColumn] + " ");
                System.out.println("col - bad");
                return false;
            }

        }
        return true;
    }

    /**
     *
     * @return
     */
    public boolean isUnitLegal(int currentRow, int currentColumn) {
        int row = (currentRow / 3) * 3;
        int col = (currentColumn / 3) * 3;
        for (int k = row; k < row + 3; k++) {
            for (int l = col; l < col + 3; l++) {
                if (k == currentRow && l == currentColumn) {

                } else if (currentData[k][l] == buttonValue) {
                    System.out.println(k + " " + l + ":" + currentData[k][l] + " ");
                    System.out.println("unit - bad");
                    return false;
                }
            }
        }
        return true;
    }

    //检查结束游戏所需要的步数是否为0 
    public boolean IsWin(int stepToWin) {
        return stepToWin == 0 ? true : false;
    }

    /**
     * 测试用函数,输出 当前状态的二维数组
     */
    public void printArray(int[][] a) {
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                System.out.print(a[i][j] + " ");
            }
            System.out.println("");
        }
    }

}
