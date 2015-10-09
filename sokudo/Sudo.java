/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sudo;

import java.util.Random;
import java.util.Scanner;

/**
 *
 * @author sicmatr1x
 */
public class Sudo {

    /**
     * GameLevel 游戏难度 1简单，2普通，3困难，4大师
     */
    int GameLevel;
    /**
     * GameTime 游戏耗时 游戏开始到现在所消耗的时间 格式：时分秒 例：25202为耗时2小时52分02秒
     */
    long GameTime;
    /**
     * ErrorTime 错误次数为一盘游戏从开始到现在匹配错误的次数
     */
    int ErrorTime;
    /**
     * GameScore 游戏得分 得分计算规则： 难度越高倍率K越高 时间由开始一定值Max随时间增长递减 单块得分=K*T(GameTime,
     * Max) 总得分GameScore=各个单块得分的和-错误次数
     */
    int GameScore;
    /**
     * HighScore游戏最高分
     */
    public static int HighScore;

    /**
     * PauseGame 游戏暂停参数，默认为不暂停
     */
    public boolean PauseGame = false;

    /**
     * GameTable 数独棋盘，用于存储经过判断的数独棋盘 棋盘参数： 行Row(0<Row<=9) 列Column(0<Column<=9)
     * 宫Nonet（以每一宫的第一个单元坐标来表示一个宫） 单元Unit[0<Row<=9][0<Column<=9]
     * GameGrid[Row][Column]
     */
    public int[][] GameGrid = new int[10][10];

    /**
     * GameAnswerTable 游戏答案，用于存储游戏的答案 数据存储格式参照GameGrid
     * GameAnswerGrid[Row][Column]
     */
    private int[][] GameAnswerGrid = new int[10][10];

    /**
     * GameDraftTable 游戏草稿 数据存储格式 行列草稿 GameDraftTable[Row][Column][Draft]
     * GameDraftTable[Row][Colunm][0]为该单元是否有草稿，
     * GameDraftTable[Row][column][X]为该单元有草稿X
     */
    public boolean[][][] GameDraftTable = new boolean[10][10][10];

    Sudo(int GameLevel, long GameTime, int ErrorTime, int GameScore) {
        this.GameLevel = GameLevel;
        this.GameTime = GameTime;
        this.ErrorTime = ErrorTime;
        this.GameScore = GameScore;
    }

    /**
     * The main Method 该方法用于临时检验Sudo数独核心程序的正确性
     *
     * @param args 暂未使用
     */
    public static void main(String[] args) {
        int GameLevel = 1;
        long GameTime = 0;
        int ErrorTime = 0;
        int GameScore = 0;
        Sudo test = new Sudo(GameLevel, GameTime, ErrorTime, GameScore);
        int[][] t = {
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 1, 2, 3, 4, 5, 6, 7, 8, 9},
            {0, 2, 3, 4, 5, 6, 7, 8, 9, 1},
            {0, 3, 4, 5, 6, 7, 8, 9, 1, 2},
            {0, 4, 5, 6, 7, 8, 9, 1, 2, 3},
            {0, 5, 6, 7, 8, 9, 1, 2, 3, 4},
            {0, 6, 7, 8, 9, 1, 2, 3, 4, 5},
            {0, 7, 8, 9, 1, 2, 3, 4, 5, 6},
            {0, 8, 9, 1, 2, 3, 4, 5, 6, 7},
            {0, 9, 1, 2, 3, 4, 5, 6, 7, 8}
        };
        int[][] t2 = {
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 8, 7, 2, 1, 9, 3, 4, 6, 5},
            {0, 6, 5, 1, 2, 4, 8, 3, 7, 9},
            {0, 3, 4, 9, 6, 5, 7, 2, 8, 1},
            {0, 5, 2, 6, 3, 1, 4, 7, 9, 8},
            {0, 4, 8, 7, 9, 6, 5, 1, 3, 2},
            {0, 9, 1, 3, 7, 8, 2, 6, 5, 4},
            {0, 2, 9, 8, 4, 7, 6, 5, 1, 3},
            {0, 7, 3, 5, 8, 2, 1, 9, 4, 6},
            {0, 1, 6, 4, 5, 3, 9, 8, 2, 7}
        };
        test.GameGrid = t;
        test.showGameGrid();
        System.out.println(test.checkGameGrid());
    }

    /**
     * The Sudo Method 类Sudo的构造方法
     */
    Sudo() {

    }

    /**
     * The Sudo Method 传入一个九宫格，使用该九宫格进行游戏
     *
     * @param GameGrid 九宫格地址(size=10*10)
     */
    Sudo(int[][] GameGrid, int[][] GameAnswerGrid, int GameLevel, int GameTime, int GameScore, int GameErrorTime) {
        this.GameGrid = GameGrid;
        this.GameAnswerGrid = GameAnswerGrid;
        this.GameLevel = GameLevel;
        this.GameTime = GameTime;
        this.GameScore = GameScore;
        this.ErrorTime = GameErrorTime;
    }
    
    /**
     * The launch Method
     * 一个自动判断游戏模式的启动器
     */
    public void launch(){
        switch(this.GameLevel){
            case 1:
                System.out.println("简单模式正在施工中...");
                break;
            case 2:
                System.out.println("普通模式正在施工中...");
                break;
            case 3:
                System.out.println("困难模式");
                this.hardMode();
                break;
            case 4:
                System.out.println("极限模式正在施工中...");
                break;
        }
    }

    /**
     * The CheckGameGrid Method 用于检查九宫格是否合法 检查顺序： 1行检查（检查每一行是否合法），
     * 2列检查（检查每一列是否合法）， 3宫检查（检查每一宫是否合法）
     *
     * @return 当且仅当行列宫全为真时才返回true
     */
    public boolean checkGameGrid() {
        int FlagRow = 0;
        int FlagColunm = 0;
        int FlagNonet = 0;
        boolean[] CheckRow = new boolean[10];
        boolean[] CheckColunm = new boolean[10];
        boolean[] CheckNonet = new boolean[10];

        /*行检查，检查每行是否合法*/
        for (int Row = 1; Row < 10; Row++) {
            //System.out.println("Row"+Row);
            boolean[] flag = new boolean[10];
            for (int Colunm = 1; Colunm < 10; Colunm++) {
                //System.out.println("Colunm"+Colunm);
                if (GameGrid[Row][Colunm] != 0) {
                    if (flag[GameGrid[Row][Colunm]] == false) {
                        flag[GameGrid[Row][Colunm]] = true;
                    } else {
                        CheckRow[Row] = true;
                    }
                }
            }
        }//行检查
        for (int Row = 1; Row < 10; Row++) {
            if (!CheckRow[Row]) {
                FlagRow++;//若最后FlagRow为9的话则说明通过行检查
            }
        }

        /*列检查，检查每行是否合法*/
        for (int Colunm = 1; Colunm < 10; Colunm++) {
            boolean[] flag = new boolean[10];
            for (int Row = 1; Row < 10; Row++) {
                if (GameGrid[Row][Colunm] != 0) {
                    if (flag[GameGrid[Row][Colunm]] == false) {
                        flag[GameGrid[Row][Colunm]] = true;
                    } else {
                        CheckColunm[Colunm] = true;
                    }
                }
            }
        }//列检查
        for (int Colunm = 1; Colunm < 10; Colunm++) {
            if (!CheckColunm[Colunm]) {
                FlagColunm++;//若最后FlagColunm为9的话则说明通过行检查
            }
        }

        /*宫检查，检查每一个宫是否合法*/
        int[] point = {1, 4, 7};//宫首单元序列
        for (int Row = 0; Row < 3; Row++) {
            for (int Colunm = 0, count = 1; Colunm < 3; Colunm++, count++) {
                boolean[] flag = new boolean[10];
                for (int row = point[Row]; row < point[Row] + 3; row++) {
                    for (int colunm = point[Colunm]; colunm < point[Colunm] + 3; colunm++) {
                        //System.out.println(row+","+colunm);
                        if (GameGrid[row][colunm] != 0) {
                            if (flag[GameGrid[row][colunm]] == false) {
                                flag[GameGrid[row][colunm]] = true;
                            } else {
                                CheckNonet[count] = true;
                                //System.out.println("["+row+","+colunm+"]");/*这行可以输出宫内重复的单位*/
                            }
                        }
                    }
                }
            }
        }//宫检查
        for (int count = 1; count < 10; count++) {
            //System.out.println("CheckNonet["+count+"]="+CheckNonet[count]);/*这行可以输出是哪个宫出的问题*/
            if (!CheckNonet[count]) {
                FlagNonet++;//若最后FlagNonet为9的话则说明通过行检查
            }
        }

        //System.out.println("FlagRow=" + FlagRow);
        //System.out.println("FlagColunm=" + FlagColunm);
        //System.out.println("FlagNonet=" + FlagNonet);
        if (FlagRow == 9 && FlagColunm == 9 && FlagNonet == 9) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * The CheckGameGridByAnswer Method 用于比较游戏棋盘与答案
     *
     * @return 有一个单元格不一样就返回false
     */
    private boolean checkGameGridByAnswer() {
        for (int Row = 1; Row < 10; Row++) {
            for (int Colunm = 1; Colunm < 10; Colunm++) {
                if (this.GameGrid[Row][Colunm] != this.GameAnswerGrid[Row][Colunm]) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * The isGridFull Method 用于检查九宫格是否已经填完
     *
     * @return 若填完则返回真
     */
    public boolean isGridFull() {
        int count = 0;
        for (int row = 1; row < 10; row++) {
            for (int col = 1; col < 10; col++) {
                if (this.GameGrid[row][col] == 0) {
                    count++;
                }
            }
        }
        if (count == 0) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * The getUnit Method 用于得到指定坐标的单元数字
     *
     * @param Row 行坐标
     * @param Colunm 列坐标
     * @return 指定单元数字
     */
    public int getUnit(int Row, int Colunm) {
        return this.GameGrid[Row][Colunm];
    }

    /**
     * The setUnit Method 用于更新指定单元的内容
     *
     * @param Row 行坐标
     * @param Colunm 列坐标
     * @param num 更新内容
     */
    public void setUnit(int Row, int Colunm, int num) {
        this.GameGrid[Row][Colunm] = num;
    }

    /**
     * The showGameGrid Method 用于打印数独九宫格
     */
    public void showGameGrid() {
        for (int i = 0; i < 10; i++) {
            if (i > 0) {
                System.out.print(i);
            } else {
                System.out.print("  ");
            }
        }
        System.out.println();

        for (int row = 1; row < 10; row++) {
            for (int col = 0; col < 10; col++) {
                if (col == 0) {
                    System.out.print((row) + "|");
                } else {
                    System.out.print(this.GameGrid[row][col]);
                }
            }
            System.out.println();
        }
    }

    /**
     * The hardMode Method 困难模式直接读取数据进行
     */
    public void hardMode() {
        this.showGameGrid();

        Scanner input = new Scanner(System.in);
        int row, col, num;
        while (this.ErrorTime < 4) {
            System.out.print("输入(行 列 数字)：");
            row = input.nextInt();
            col = input.nextInt();
            num = input.nextInt();
            this.setUnit(row, col, num);
            //System.out.println("已输入"+this.getUnit(row, num));

            if (this.checkGameGridByAnswer()) {
                System.out.println("正确");
            } else {
                this.ErrorTime++;
                this.GameGrid[row][col] = 0;
                System.out.println("错误");
            }

            this.showGameGrid();

            if (this.isGridFull()) {
                break;
            }
        }
        if (this.ErrorTime >= 4) {
            System.out.println("游戏失败");
        } else {
            if (this.GameScore >= Sudo.HighScore) {
                Sudo.HighScore = this.GameScore;
                System.out.println("游戏新纪录：" + this.GameScore);
            }else{
                System.out.println("本次得分：" + this.GameScore+"游戏最高分："+Sudo.HighScore);
            }
        }
    }

    public static Random random = new Random();

    /**
     * workSpace数组 是用于便于生成数独终盘的工作数组
     */
    int[] workSpace = new int[81];

    /**
     * The generateGrid Method 随机产生一个数独
     *
     * @return 返回一个随机产生的数独
     */
    public void generateGrid() {
        for (int i = 0; i < 81; i++) {
            this.workSpace[i] = 0;
        }
        getUnit(0);
        for (int Row = 1, i = 0; Row < 10; Row++) {
            for (int Col = 1; Col < 10; Col++, i++) {
                this.GameAnswerGrid[Row][Col] = this.workSpace[i];
            }
        }
    }

    /**
     * The getUnit Method 递归产生数独位置i的值
     *
     * @param 数独位置i
     * @return 位置i是否可以填入值
     */
    private boolean getUnit(int i) {
        /* 如果已经填满81个格子则返回true */
        if (i == 81) {
            return true;
        } /* 如果位置i已经填入了合适的值则递归产生下一个位置的值 */ else if (this.workSpace[i] != 0) {
            return getUnit(i + 1);
        } /* 如果恰好需要填入位置i的值 */ else {
            /* 用数组randOrder存储每个位置可能产生的值，即为1~9 */
            int[] randOrder = new int[10];
            for (int val = 1; val < 10; val++) {
                randOrder[val] = val;
            }

            /* 将数组randOrder变为一个随机存储1~9的数组 */
            for (int val = 1; val < 10; val++) {
                int rand = random.nextInt(10);
                int tmp = randOrder[rand];
                randOrder[rand] = randOrder[val];
                randOrder[val] = tmp;
            }

            /* 在位置i随机填入一个值，并且判断是否有效 */
            for (int val = 1; val < 10; val++) {
                /* 如果在位置i填入的1~9中的某个随机数有效 */
                if (isLegal(i, randOrder[val])) {
                    /* 则将此随机值放入位置i */
                    this.workSpace[i] = randOrder[val];
                    /* 探索i的下一个位置是否能正确填入，如果可以则返回true */
                    if (getUnit(i + 1)) {
                        return true;
                    }
                }
            }
        }

        /* 如果在位置i不能填入1~9中的任何值，则需要回溯 */
        this.workSpace[i] = 0;
        return false;
    }

    /**
     * The isLegal Method 在位置i填入value数字是否有效，通过按行列和小矩阵判断
     *
     * @param 填入的位置i
     * @param 填入位置i的数字value
     * @return 在位置i填入数字value是否有效
     */
    private boolean isLegal(int i, int value) {
        /* 判断行是否有效 */
        if (!isRowLegal(i, value)) {
            return false;
        }
        /* 判断列是否有效 */
        if (!isColLegal(i, value)) {
            return false;
        }
        /* 判断小矩阵是否有效 */
        if (!isSubLegal(i, value)) {
            return false;
        }

        return true;
    }

    /**
     * The isRowLegal Method 判断在位置i填入value行规则是否满足
     *
     * @param 填入的位置i
     * @param 填入位置i的数字value
     * @return 在位置i填入数字value行规则是否有效
     */
    private boolean isRowLegal(int i, int value) {
        int row = i / 9;
        for (int val = 0; val < 9; val++) {
            if (value == this.workSpace[row * 9 + val]) {
                return false;
            }
        }
        return true;
    }

    /**
     * The isColLegal Method 判断在位置i填入value列规则是否满足
     *
     * @param 填入的位置i
     * @param 填入位置i的数字value
     * @return 在位置i填入数字value列规则是否有效
     */
    private boolean isColLegal(int i, int value) {
        int col = i % 9;
        for (int val = 0; val < 9; val++) {
            if (value == this.workSpace[val * 9 + col]) {
                return false;
            }
        }
        return true;
    }

    /**
     * The isSubLegal Method 判断在位置i填入value小矩阵规则是否满足
     *
     * @param 填入的位置i
     * @param 填入位置i的数字value
     * @return 在位置i填入数字value小矩阵规则是否有效
     */
    private boolean isSubLegal(int i, int value) {
        int row = i / 9;
        int col = i % 9;
        int xOff = row / 3 * 3;
        int yOff = col / 3 * 3;
        for (int x = 0; x < 3; x++) {
            for (int y = 0; y < 3; y++) {
                if (value == this.workSpace[(xOff + x) * 9 + yOff + y]) {
                    return false;
                }
            }
        }
        return true;
    }

}
