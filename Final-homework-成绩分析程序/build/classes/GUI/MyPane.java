/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import Data.Student4GUI;
import Exception.FileContentException;
import java.io.BufferedOutputStream;
import static javafx.application.Platform.exit;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import javafx.geometry.Insets;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.FileChooser;

/**
 *
 * @author H.W
 */
public class MyPane extends BorderPane {

    final private int TEXT_FILE = 0;
    final private int BINARY_FILE = 1;

    final private int FILE_NOT_FOUND = 2;  //找不到文件
    final private int FILE_ERROR = 3;      //文件错误
    final private int FILE_READING_SAVING_STATE = 4; //文件读取状态
    final private int ENCODING_ERROR = 5;     //编码错误
    final private int FILE_CONTENT_ERROR = 3;      //文件内容错误
    final private int FILE_NAME_ERROR = 6;

    private TextField searchBar;  //搜索框
    private String keyword4Search;
    private MenuBar menuBar;      //菜单栏
    private Label ScoreLabel;
    private Label StatisticLabel;
    private Label extraInfoLabel;
//    private String extraInfo;
    private File file;
    private String[] legalFileName; //合法的文件名
    private StatisticPane statisticPane = new StatisticPane();
    private ScoreListPane scoreListPane = new ScoreListPane();

    public MyPane() {
        BorderPane Top = new BorderPane();
        VBox left = new VBox();
        extraInfoLabel = new Label();
        ScoreLabel = new Label("成绩单");
        ScoreLabel.setId("ScoreLabel");
        StatisticLabel = new Label("成绩统计");
        StatisticLabel.setId("StatisticLabel");
        menuBar = setUpMenuBar();
        searchBar = new TextField();

        /**
         * 添加组件动作*
         */
        //搜索栏动作
        searchBar.setOnKeyPressed(e -> {
            if (e.getCode() == KeyCode.ENTER) {
                keyword4Search = searchBar.getText();
                search(keyword4Search);
                System.out.println(keyword4Search);
                extraInfoLabel.setText(generateExtraInfo(file));
                this.setBottom(extraInfoLabel);
            }
        });

        /**
         * 样式修饰*
         */
        Font font1 = Font.font("", FontWeight.BOLD, 24);
        Font font2 = Font.font("", FontWeight.BOLD, 24);
        ScoreLabel.setFont(font1);
        StatisticLabel.setFont(font2);

        /**
         * 添加板块并定位*
         */
        Top.setTop(menuBar);
        Top.setLeft(searchBar);
        left.getChildren().add(ScoreLabel);
        left.getChildren().add(scoreListPane);
        this.setTop(Top);
        this.setRight(new VBox(StatisticLabel, statisticPane));
        this.setLeft(left);
        this.setBottom(extraInfoLabel);

        left.setPadding(new Insets(0, 0, 0, 20));
        BorderPane.setMargin(searchBar, new Insets(10, 0, 10, 25));
        VBox.setMargin(scoreListPane, new Insets(15, 0, 10, 0));
        extraInfoLabel.setPadding(new Insets(10, 0, 10, 15));

    }

    /**
     * 生成菜单栏方法
     *
     * @return
     */
    public MenuBar setUpMenuBar() {
        MenuBar menuBar = new MenuBar();
        Menu menuFile = new Menu("文件");
        Menu menuTheme = new Menu("主题");
        MenuItem open = new MenuItem("打开文本文件成绩单");
        MenuItem save = new MenuItem("另存为文本文件成绩单");
        MenuItem openObjectFile = new MenuItem("打开对象文件成绩单");
        MenuItem saveObjectFile = new MenuItem("另存为对象文件成绩单");
        MenuItem exit = new MenuItem("退出");
        MenuItem defaultTheme = new MenuItem("无");
        MenuItem blueTheme = new MenuItem("蓝色");
        MenuItem greenTheme = new MenuItem("绿色");
        MenuItem redTheme = new MenuItem("红色");

        menuFile.getItems().add(open);
        menuFile.getItems().add(save);
        menuFile.getItems().add(new SeparatorMenuItem());
        menuFile.getItems().add(openObjectFile);
        menuFile.getItems().add(saveObjectFile);
        menuFile.getItems().add(new SeparatorMenuItem());
        menuFile.getItems().add(exit);
        menuTheme.getItems().add(defaultTheme);
        menuTheme.getItems().add(blueTheme);
        menuTheme.getItems().add(greenTheme);
        menuTheme.getItems().add(redTheme);
        menuBar.getMenus().add(menuFile);
        menuBar.getMenus().add(menuTheme);

        /**
         * 添加事件处理器 *
         */
        //打开文本文件
        open.setOnAction(e -> {
            file = OpenFile(TEXT_FILE);
            try {
                if (file != null) {
                    if (!fileNameChecker(file)) {
                        generateAlert(FILE_NAME_ERROR);
                        scoreListPane.setTheClass("未知");
                        scoreListPane.setLecture("未知");
                        legalFileName = null;
                    } else {
                        legalFileName = separateFileNameInfo(file);
                        scoreListPane.setTheClass(legalFileName[0]);
                        scoreListPane.setLecture(legalFileName[1]);
                    }
                    scoreListPane.displayDataFromFile(file, TEXT_FILE);
                    //当上一个语句抛出FileContentException时，下面的语句就不会执行了，然后GUI的统计面板部分就不会冒出奇奇怪怪的东西。
                    statisticPane.displayDataFromFile(file, TEXT_FILE);
                    extraInfoLabel.setText(generateExtraInfo(file));
                    this.setBottom(extraInfoLabel);
                }
            } catch (FileContentException ex) {
                generateAlert(FILE_CONTENT_ERROR);
            } catch (FileNotFoundException ex) {
                generateAlert(FILE_NOT_FOUND);
            } catch (IOException ex) {
                generateAlert(FILE_ERROR);
            } catch (ClassNotFoundException ex) {
                generateAlert(FILE_ERROR);
            }
        });

        //另存为文本文件
        save.setOnAction(e -> {
            saveIntoFile(TEXT_FILE);
        });

        //打开对象文件
        openObjectFile.setOnAction(e -> {
            file = OpenFile(BINARY_FILE);
            try {
                if (file != null) {
                    if (!fileNameChecker(file)) {
                        generateAlert(FILE_NAME_ERROR);
                        scoreListPane.setTheClass("未知");
                        scoreListPane.setLecture("未知");
                        legalFileName = null;
                    } else {
                        legalFileName = separateFileNameInfo(file);
                        scoreListPane.setTheClass(legalFileName[0]);
                        scoreListPane.setLecture(legalFileName[1]);
                    }
                    scoreListPane.displayDataFromFile(file, BINARY_FILE);
                    statisticPane.displayDataFromFile(file, BINARY_FILE);
                    extraInfoLabel.setText(generateExtraInfo(file));
                    this.setBottom(extraInfoLabel);
                }
            } catch (FileContentException ex) {
                generateAlert(FILE_CONTENT_ERROR);
            } catch (FileNotFoundException ex) {
                generateAlert(FILE_NOT_FOUND);
            } catch (IOException ex) {
                generateAlert(FILE_ERROR);
            } catch (ClassNotFoundException ex) {
                generateAlert(FILE_ERROR);
            }
        });

        //另存为对象文件
        saveObjectFile.setOnAction(e -> {
            saveIntoFile(BINARY_FILE);
        });

        //退出
        exit.setOnAction(e -> {
            exit();
        });

        defaultTheme.setOnAction(e -> {
            this.getStylesheets().clear();
        });

        blueTheme.setOnAction(e -> {
            this.getStylesheets().clear();
            this.getStylesheets().add(getClass().getResource("css/blue.css").toExternalForm());
        });
        
        greenTheme.setOnAction(e -> {
            this.getStylesheets().clear();
            this.getStylesheets().add(getClass().getResource("css/green.css").toExternalForm());
        });
        redTheme.setOnAction(e -> {
            this.getStylesheets().clear();
            this.getStylesheets().add(getClass().getResource("css/red.css").toExternalForm());
        });

        return menuBar;
    }

    /**
     * 文件打开方法，处理文本和二进制两种形式
     *
     * @param type
     * @return
     */
    private File OpenFile(int type) {
        File temp = null;
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("选择打开的文件...");
        FileChooser.ExtensionFilter extensionFilter = type == TEXT_FILE ? (new FileChooser.ExtensionFilter("文本文件", "*.txt")) : (new FileChooser.ExtensionFilter("对象文件", "*.score"));
        fileChooser.getExtensionFilters().add(extensionFilter);
        temp = fileChooser.showOpenDialog(null);
        return temp;
    }

    /**
     * 将数据写入文件的方法，处理文本和二进制两种形式
     *
     * @param type
     */
    private void saveIntoFile(int type) {
        File temp = null;

        FileChooser fileChooser = new FileChooser();
        if (fileNameChecker(file)) {
            fileChooser.setInitialFileName(legalFileName[0] + "-" + legalFileName[1]);
        }
        fileChooser.setTitle("另存为...");
        FileChooser.ExtensionFilter extensionFilter = type == TEXT_FILE ? (new FileChooser.ExtensionFilter("文本文件", "*.txt")) : (new FileChooser.ExtensionFilter("对象文件", "*.score"));
        fileChooser.getExtensionFilters().add(extensionFilter);

        System.out.println("准备数据...");
        if (fileNameChecker(file)) {

        }
        temp = fileChooser.showSaveDialog(null);
        ArrayList<Student4GUI> data = new ArrayList<Student4GUI>();
        data = scoreListPane.getData();

        //文本文件的情况
        if (temp != null) {

            if (type == TEXT_FILE) {
                try (PrintWriter input = new PrintWriter(temp, "UTF-8");) {
                    for (Student4GUI s : data) {
                        System.out.println("写入学生：" + s);
                        input.write(s.getSchoolId() + "," + s.getName() + "," + s.getScore() + "");
                        input.println();
                    }
                } catch (FileNotFoundException ex) {
                    generateAlert(FILE_NOT_FOUND);
                } catch (UnsupportedEncodingException ex) {
                    generateAlert(ENCODING_ERROR);
                }
                generateAlert(FILE_READING_SAVING_STATE);
            } //二进制文件的情况
            //将这个ArrayList写入文件...
            else {
                try (ObjectOutputStream oos = new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream(temp)))) {
                    oos.writeObject(data);

                } catch (FileNotFoundException ex) {
                    generateAlert(FILE_NOT_FOUND);
                } catch (IOException ex) {
                    generateAlert(FILE_ERROR);
                }
                generateAlert(FILE_READING_SAVING_STATE);
            }
        }
    }

    /**
     * 搜索数据的方法，在searchBar(TextField)中被调用
     *
     * @param keyword
     */
    private void search(String keyword) {
        scoreListPane.searchData(keyword);

    }

    /**
     *
     * 生成底部文件信息方法
     *
     * @param file
     * @return
     */
    private String generateExtraInfo(File file) {
        String location = "未打开任何文件";

        if (file != null) {
            location = file.getPath();
            location += "  共" + statisticPane.getScoreData().getSize() + "人," + "显示了" + scoreListPane.getItemsSize() + "人" + "(班级：" + scoreListPane.getTheClass() + " 课程：" + scoreListPane.getLecture() + ")";
        }

        return location;
    }

    /**
     * 生成弹出窗口的方法
     *
     * @param type
     */
    private void generateAlert(int type) {
        Alert alert;
        if (type == FILE_READING_SAVING_STATE) {
            alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("状态提示");
        } else if (type == FILE_NAME_ERROR) {
            alert = new Alert(Alert.AlertType.WARNING);
        } else {
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("错误提示");
        }
        switch (type) {
            case FILE_READING_SAVING_STATE:
                alert.setContentText("成功");
                break;
            case FILE_NOT_FOUND:
                alert.setContentText("找不到文件");
                break;
            case FILE_ERROR:
                alert.setContentText("文件错误");
                break;
            case ENCODING_ERROR:
                alert.setContentText("编码错误，写出失败");
                break;
            case FILE_NAME_ERROR:
                alert.setContentText("文件名不符合格式要求，未能正确读取信息!\n文件名的格式为：\n班级名称-课程名称\n");
                break;
            default:
                alert.setContentText("出现了一些奇怪的问题!");
        }
        alert.show();
    }

    private boolean fileNameChecker(File file) {
        boolean result = false;
        if (file != null) {
            String[] temp = separateFileNameInfo(file);
            if (temp.length == 2) {
                System.out.println(temp[0] + temp[1]);
                result = true;
            }
        }
        return result;
    }

    /**
     * 分割文件名信息
     *
     * @return
     */
    private String[] separateFileNameInfo(File file) {
        String[] result = {""};
        if (file != null) {
            result = (file.getName()).substring(0, file.getName().indexOf(".")).split("-");
        }
        return result;
    }
}
