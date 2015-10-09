/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sudoku;

import java.io.FileNotFoundException;
import java.util.Scanner;
import Sudo.*;

/**
 *
 * @author sicmatr1x
 */
public class Sudoku {

    /**
     * autoDeleteDraftLog 自动删除记录 在一个方砖匹配后，自动删除所有无用的记录
     */
    boolean autoDeleteDraftLog = true;
    /**
     * autoHighLight 亮显记录 以绿色高亮显示所有含该数字的方砖
     */
    boolean autoHighLight = true;
    /**
     * 自动判断错误 开启后每输入一个数字就进行错误判断
     */
    boolean autoJudge = true;
    /**
     * 隐藏计时器 游戏中隐藏计时器，游戏结束后显示时间
     */
    boolean hideTimer = false;

    /**
     * highScore 游戏最高分
     */
    int highScore;

    /**
     * The Sudoku Method 类Sudoku构造方法
     */
    Sudoku() {
        this.LoadSetData();
    }

    /**
     * The main Method 该方法为SUDOKU数独的CUI（Console User Interface，控制台用户界面）
     *
     * @param args 此参数暂未使用
     */
    public static void main(String[] args) {
        System.out.println("SUDOKU数独");
        boolean isContinue = true;
        Scanner input = new Scanner(System.in);
        int choice;
        Sudoku test = new Sudoku();

        while (isContinue) {
            System.out.println("1.开始");
            System.out.println("2.统计");
            System.out.println("3.选项");
            System.out.println("4.帮助");
            System.out.println("5.存储并退出");
            System.out.print("键入选项：");
            choice = input.nextInt();
            switch (choice) {
                case 1:
                    test.BeginGame();
                    break;
                case 2:
                    test.Statistics();
                    break;
                case 3:
                    test.Setting();
                    break;
                case 4:
                    Sudoku.HelpSudoku();
                    break;
                case 5:
                    test.SaveGame();
                    return;
            }
        }//while

    }

    /**
     * The BeginGame Method 开始游戏界面 新游
     */
    public void BeginGame() {
        System.out.println("1.新游戏");
        System.out.println("2.载入游戏");
        System.out.println("3.返回");
        System.out.print("键入选项：");
        Scanner input = new Scanner(System.in);
        int choice = input.nextInt();
        switch (choice) {
            case 1:
                this.NewGame();
                break;
            case 2:
                this.LoadGame();
                break;
            case 3:
                return;
        }
    }

    /**
     * The NewGame Method 用于开始一盘数独sudo游戏 难度分为： 简单采用挖取法挖去20-30格 普通采用挖去法挖去40-45格
     * 困难采用题库的题目 大师采用题库里的17线索题目
     * 在极限模式下将不能保存该盘游戏，并且自动判断，草稿设置为将被设置为关闭，题目难度默认为大师难度，一旦开始不可以停止
     */
    public void NewGame() {
        System.out.println("选择难度：");
        System.out.println("1.简单");
        System.out.println("2.普通");
        System.out.println("3.困难");
        System.out.println("4.极限模式");
        System.out.println("5.返回");
        System.out.print("键入选项：");
        Scanner input = new Scanner(System.in);
        int choice = input.nextInt();
        switch (choice) {

        }
    }

    public void launch(int[][] GameGrid, int[][] GameAnswerGrid, int GameLevel, int GameTime, int GameScore, int GameErrorTime) {
        Sudo game = new Sudo(GameGrid, GameAnswerGrid, GameLevel, GameTime, GameScore, GameErrorTime);
        game.launch();
    }

    public void launch(int GameLevel) {
        if (GameLevel >= 3) {
            Sudo game = new Sudo();
            Sudo.GameLevel = GameLevel;
            game.launch();
        } else {
            java.io.File file = new java.io.File("map/mapdata.txt");
            try {
                Scanner fileInput = new Scanner(file);
                int SudoNum = fileInput.nextInt();//读取数独题目的数量
                int[] GameLevelTable = new int[SudoNum];//用于存放各个题目对应的难度

                if (SudoNum == 0) {
                    System.out.println("无存档");
                }

                /*读取部分*/
                for (int i = 1; i <= SudoNum; i++) {
                    GameLevelTable[i] = fileInput.nextInt();
                }
                /*选择题目*/
                System.out.print("选择一个题目：");
                Scanner input = new Scanner(System.in);
                int choice = input.nextInt();
                if (choice <= 0 || choice > SudoNum) {
                    System.out.print("请输入一个存在的题目下标");
                    return;
                }
                /*读取题目*/
                System.out.println("Loading Grid" + choice + "...");
                java.io.File Grid = new java.io.File("map/Grid" + choice + ".txt");
                System.out.println("Loading Grid" + choice + "...done");
                System.out.println("Loading GridAns" + choice + "...");
                java.io.File GridAns = new java.io.File("map/GridAns" + choice + ".txt");
                System.out.println("Loading GridAns" + choice + "...done");
                
                fileInput.close();
                /*调用Sudo内核*/
                Sudo game = new Sudo(GameLevel, 0, 0, 0);
                game.launch();
            } catch (FileNotFoundException ex) {
                System.out.println(ex);
            }
        }
    }

    /**
     * The LoadGame Method 用于从文件中载入已保存好的游戏 已保存好的游戏含有以下参数： 游戏难度：int型 游戏耗时：int型
     * 错误次数：int型 游戏得分：int型 游戏棋盘Table：用二维数组保存 游戏草稿：用三维boolean数组保存
     */
    public void LoadGame() {
        System.out.println("载入游戏：");
        java.io.File file = new java.io.File("SAVEDATA/savedata.txt");
        if (!file.exists()) {
            System.out.println("Data.txt is not exists, creat new data");
        }
        try {
            Scanner fileInput = new Scanner(file);
            int SudoNum = fileInput.nextInt();//读取数独存档的数量
            int[] GameLevelTable = new int[SudoNum];//用于存放各个存档对应的难度
            int[] GameTimeTable = new int[SudoNum];//用于存放各个存档对应的耗时
            int[] FileCreatTimeTable = new int[SudoNum];//用于存放各个存档对应的创建时间
            int[] GameScoreTable = new int[SudoNum];//用于存放各个存档的分数
            int[] GameErrorTimeTable = new int[SudoNum];//用于存放各个存档出错的次数

            if (SudoNum == 0) {
                System.out.println("无存档");
            }

            /*读取部分*/
            for (int i = 1; i <= SudoNum; i++) {
                GameLevelTable[i] = fileInput.nextInt();
                GameTimeTable[i] = fileInput.nextInt();
                FileCreatTimeTable[i] = fileInput.nextInt();
                GameScoreTable[i] = fileInput.nextInt();
                GameErrorTimeTable[i] = fileInput.nextInt();
            }
            /*打印存档列表*/
            for (int i = 1; i <= SudoNum; i++) {
                switch (GameLevelTable[i]) {
                    case 1:
                        System.out.printf("%d:简单模式 %d %d", i, GameTimeTable[i], FileCreatTimeTable[i]);
                        break;
                    case 2:
                        System.out.printf("%d:普通模式 %d %d", i, GameTimeTable[i], FileCreatTimeTable[i]);
                        break;
                    case 3:
                        System.out.printf("%d:困难模式 %d %d", i, GameTimeTable[i], FileCreatTimeTable[i]);
                        break;
                    case 4:
                        System.out.printf("%d:极限模式 %d %d", i, GameTimeTable[i], FileCreatTimeTable[i]);
                        break;
                }
            }
            fileInput.close();
            /*选择存档*/
            System.out.print("选择一个存档：");
            Scanner input = new Scanner(System.in);
            int choice = input.nextInt();
            if (choice <= 0 || choice > SudoNum) {
                System.out.print("请输入一个存在的存档下标");
                return;
            }
            /*读取存档*/
            System.out.println("Loading Grid" + choice + "...");
            java.io.File Grid = new java.io.File("SAVEDATA/Grid" + choice + ".txt");
            System.out.println("Loading Grid" + choice + "...done");
            System.out.println("Loading GridAns" + choice + "...");
            java.io.File GridAns = new java.io.File("SAVEDATA/GridAns" + choice + ".txt");
            System.out.println("Loading GridAns" + choice + "...done");
            /*调用Sudo内核*/
            //Sudo game = Sudo(Grid, GridAns, GameLevelTable[choice], GameTimeTable[choice], GameScoreTable[choice], GameErrorTimeTable[choice]);
        } catch (FileNotFoundException ex) {
            System.out.println(ex);
        }
    }

    /**
     *
     */
    public void SaveGame() {
        System.out.println("保存游戏：");
        java.io.File file = new java.io.File("SAVEDATA/savedata.txt");
        if (!file.exists()) {
            System.out.println("Data.txt is not exists, creat new data");
        }
        /*????*/
    }

    /**
     * The Statistics Method 用于从文件中加载分数与对应分数的难度等级
     */
    public void Statistics() {
        System.out.println("统计：");
    }

    /**
     * The Setting Method 选项设置
     * 可以修改autoDeleteDraftLog，autoHighLight，hideTimer三个属性
     */
    public void Setting() {
        System.out.println("游戏选项：");
        System.out.println("1.自动删除记录");
        System.out.println("2.使用自动高亮");
        System.out.println("3.隐藏计时器");
        System.out.println("4.自动判断");
        System.out.println("5.返回");
        System.out.print("键入选项：");
        Scanner input = new Scanner(System.in);
        int choice = input.nextInt();
        switch (choice) {
            case 1:
                System.out.print("键入布尔值：");
                this.autoDeleteDraftLog = input.nextBoolean();
                break;
            case 2:
                System.out.print("键入布尔值：");
                this.autoHighLight = input.nextBoolean();
                break;
            case 3:
                System.out.print("键入布尔值：");
                this.hideTimer = input.nextBoolean();
                break;
            case 4:
                System.out.println("该功能默认开启，在极限模式下该功能将被强制关闭");
                break;
            case 5:
                return;
        }
        this.SaveSetData();
    }

    /**
     * The LoadMainData Method 加载设置数据
     */
    public void LoadSetData() {
        System.out.println("Loading Setting...");
        java.io.File file = new java.io.File("Setting.txt");
        if (!file.exists()) {
            System.out.println("Data.txt is not exists, creat new data");
        }

        try {
            Scanner fileInput = new Scanner(file);
            System.out.println("Loading Setting:");
            this.autoDeleteDraftLog = fileInput.nextBoolean();
            System.out.println("autoDeleteDraftLog =" + this.autoDeleteDraftLog);
            this.autoHighLight = fileInput.nextBoolean();
            System.out.println("autoHighLight =" + this.autoHighLight);
            this.autoJudge = fileInput.nextBoolean();
            System.out.println("autoJudge =" + this.autoJudge);
            this.hideTimer = fileInput.nextBoolean();
            System.out.println("hideTimer =" + this.hideTimer);
            this.highScore = fileInput.nextInt();
            System.out.println("highScore =" + this.highScore);
            fileInput.close();
            System.out.println("Loading Setting...done");
        } catch (FileNotFoundException ex) {
            System.out.println(ex);
        }
    }

    /**
     * The SaveSetData Method 存储设置数据
     */
    public void SaveSetData() {
        System.out.println("Seaving Setting...");
        java.io.File file = new java.io.File("Setting.txt");
        if (!file.exists()) {
            System.out.println("Data.txt is not exists, creat new data");
        }

        try {
            java.io.PrintWriter output = new java.io.PrintWriter(file);
            output.println(this.autoDeleteDraftLog);
            System.out.println("autoDeleteDraftLog =" + this.autoDeleteDraftLog);
            output.println(this.autoHighLight);
            System.out.println("autoHighLight =" + this.autoHighLight);
            output.println(this.autoJudge);
            System.out.println("autoJudge =" + this.autoJudge);
            output.println(this.hideTimer);
            System.out.println("hideTimer =" + this.hideTimer);
            output.println(this.highScore);
            System.out.println("highScore =" + this.highScore);
            output.close();
            System.out.println("Seaving Setting...done");
        } catch (FileNotFoundException ex) {
            System.out.println(ex);
        }
    }

    /**
     * 游戏帮助 给予基本的规则说明与定理
     */
    public static void HelpSudoku() {
        System.out.println("帮助：");
    }
}
