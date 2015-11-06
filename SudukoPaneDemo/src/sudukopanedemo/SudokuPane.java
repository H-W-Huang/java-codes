/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sudukopanedemo;

import CountDownTimer.TimePane;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.util.Duration;

/**
 * SudokuPane包含三个部分 1计时器面板  2.数据面板 3. 控制面板
 * 
 * 
 * 
 * @author H.W
 */
class SudokuPane extends BorderPane {

    private GridPane dataPane = new GridPane(); //数据面板
    private HBox controlPane = new HBox();    //控制面板用来存放按钮
    private Button[] options = new Button[9];
    private Button selectedButton = null; // 姑且为GUI显示方便而创建的变量，具体要不要待定
    private SudokuTextField[][] textBoxs = new SudokuTextField[9][9];  //使用订制的TextField -- SudokuTextField
    private int[][] currentData = new int[9][9];   //当前的9*9个格子数据，与显示在界面上的数值分布情况完全对应。其中“空”用0表示
    private int inputValue = -1;  // -1的情况下不能改变TextField的值
    private int buttonValue = 0; //在检查时使用的变量
    private int[] dataFromFile = new int[81];   //从文件读入的数据
    private int currentRow = 0;   //被选中的格子的行
    private int currentColumn = 0;  //被选中的格子的列
    private int stepToWin = 0; // 完成游戏所需要的步数
    private TimePane timePane = new TimePane();   //计时面板
    private int totalSec = 0; //计时器所使用的变量，储存所花费的总秒数
    private HBox hbox = new HBox(); //用来添加 返回按钮 和 计时器
    private Button returnButton = new Button("返回");
    
    //计时器所使用的处理器
    EventHandler<ActionEvent> handler = e -> {
        totalSec++;
        timePane.setTime(totalSec);
    };

    /**
     * SudokuPane构造函数 无参
     *
     * 初始化了各个GUI组件： 1. 数组格子，数独格子的动作； 2. 按钮，按钮的动作 3. 计时器面板的初始化，为其添加动画
     *
     */
    public SudokuPane(){
        this(1);
    }
    
    
    public SudokuPane(int choice) {

        // 各组件的布局
        hbox.getChildren().add(returnButton);
        hbox.getChildren().add(timePane);
        this.setTop(hbox);
        this.setCenter(dataPane);
        this.setBottom(controlPane);
        
        //返回按钮的动作 和 样式
        returnButton.setOnAction(e->{
            this.setVisible(false);
        });
        returnButton.setStyle(""+
                "-fx-background-image: none");
        
        

        //各个组件的padding设置
        dataPane.setPadding(new Insets(15, 25, 15, 25));
        controlPane.setPadding(new Insets(10, 10, 50, 35));

        //从文件中读入数据，参数i 代表第 i 项
        dataFromFile = (new SudokuData()).getData(choice);

        //为计时器添加动画
        Timeline animation = new Timeline(new KeyFrame(Duration.millis(1000), handler));
        animation.setCycleCount(Timeline.INDEFINITE);
        animation.play();

        //循环结构，为每个格子初始化
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {

                currentData[i][j] = dataFromFile[j + i * 9]; //将从文件获取的数据储存带currentData

                /**
                 *
                 * 若是数据项非0，即可将格子上的数字初始化为对应的数字 并且设置为 “初始化过” ， 从而在以后的游戏过程中，该项不可修改
                 * 若为0，则将不显示任何数字，且将剩余步数stepToWin加上1
                 */
                if (dataFromFile[j + i * 9] != 0) {
                    textBoxs[i][j] = new SudokuTextField(Integer.toString(dataFromFile[j + i * 9]));
                    textBoxs[i][j].setCursor(Cursor.HAND);    //将鼠标进入textfield后的形状变为手
                    textBoxs[i][j].setRow(i);
                    textBoxs[i][j].setCol(j);
                    textBoxs[i][j].setInitialized(true);    //判断是否初始化过

                } else {
                    textBoxs[i][j] = new SudokuTextField(" ");
                    textBoxs[i][j].setRow(i);
                    textBoxs[i][j].setCol(j);
                    stepToWin++;
                }

                //将其格子设置为不可编辑，从而只能通过内置方法修改其数值
                textBoxs[i][j].setEditable(false);

                /**
                 * 添加点击改变数值的动作 功能：通过左键点击格子来修改格子的数字，条件是该格子没有初始化过，右键点击，撤销数值 使用了
                 * lambda 表达式 使用了 lambda 表达式
                 *
                 *
                 *
                 */
                textBoxs[i][j].setOnMouseClicked(e -> {
                    System.out.println("所在行数:" + ((SudokuTextField) e.getSource()).getRow());
                    System.out.println("所选为：" + ((SudokuTextField) e.getSource()).getText());

                    //在未初始化的情况下执行下面的动作
                    if (!((SudokuTextField) e.getSource()).isInitialized()) {

                        //如果点击的是鼠标左键
                        if (e.getButton() == MouseButton.PRIMARY) {
                            setBoxValue(e, false); //修改数值，false表示将要进行的操作不是撤销（wipe）
                            System.out.println("所在的行列：" + currentRow + " " + currentColumn);

                            //检查输入，从而修改数值的样式
                            if (!checkInput(currentRow, currentColumn)) {
                                ((SudokuTextField) e.getSource()).setStyle(""
                                        + "-fx-text-inner-color: red;");
                            } else {
                                ((SudokuTextField) e.getSource()).setStyle(""
                                        + "-fx-text-inner-color: green;");
                                stepToWin--;
                                System.out.println("剩余步数：" + stepToWin);
                            }
                        } 
                        //如果点击的是右键
//                        else if (e.getButton() == MouseButton.SECONDARY) {
//                            // 还需要进一步检查
//                            setBoxValue(e, true); //修改数值，true表示将要进行的操作是撤销（wipe）
//
//                        }
                    }

                    //判断是否完成游戏，是则是使用新面板替换数独面板
                    if (IsWin(stepToWin)) {
                        this.setCenter(new Label("You Win!!"));
                        
                        animation.pause();
                    };
                });
                //在面板中添加该格子
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
            
            //为每个按钮添加动作
            options[i].setOnAction(new EventHandler<ActionEvent>() {
                public void handle(ActionEvent e) {
                    getButtonValue(e);
                }
            });
        }
    }

    /**
     * 功能：获取按钮的数值
     * 参数：事件e
     * 
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
        
        //临时按钮
        Button tempbtn = (Button) e.getSource();
        selectedButton = tempbtn;
        
        inputValue = Integer.parseInt(tempbtn.getText());
        buttonValue = Integer.parseInt(tempbtn.getText());
        tempbtn.setStyle(""
                + "-fx-text-fill "
                + ":#858585 ");
        System.out.println("选中了 " + inputValue);
    }

    /**
     * 填写格子的函数 获取被被点击的按钮的值 同样在按钮事件被触发时被调用
     */
    public void setBoxValue(MouseEvent e, boolean doesWipe) {
        //获得具体的一个格子
        SudokuTextField target = (SudokuTextField) e.getSource();
        //获取行列
        int i = target.getRow();
        int j = target.getCol();
        /**
         * 判断：
         * 只有当格子处于focus状态且输入的数值（inputValue）非-1是才可以进行 填写/修改 操作
         * Boolean变量：doseWipe，表示该操作的类型：修改/填写 或者是 撤销；
         * 
         * P.S. 所操作的格子都是未初始化过的
         */
        if (target.isFocused() && inputValue != -1 && !doesWipe) {
            System.out.println(inputValue + "");
            
            //填入数值
            target.setText(inputValue + "");
            
            //修饰数字样式
            currentColumn = target.getCol();
            currentRow = target.getRow();
            selectedButton.setStyle(""
                    + "-fx-background-color: none;"
                    + "border: 2px;"
            );
            
            //对应地修改当前数据以对应
            currentData[i][j] = inputValue;
            System.out.println("Input value is :" + inputValue);
            printArray(currentData);
            
            //输入数值后，便将inputValue设置为-1
            inputValue = -1;
        } else if (target.isFocused() && doesWipe) {
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
     * 以下三个方法用于检查所填入的 数值的合法性
     */
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
                System.out.println("行出错");
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
                System.out.println("列出错");
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
                    System.out.println("宫出错");
                    return false;
                }
            }
        }
        return true;
    } // Constructor ends.


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
