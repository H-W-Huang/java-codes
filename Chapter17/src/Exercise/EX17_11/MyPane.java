/*
 * To change hBox license header, choose License Headers in Project Properties.
 * To change hBox template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Exercise.EX17_11;

import Exercise.EX_17_10.*;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import javafx.geometry.Insets;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.stage.FileChooser;

/**
 *
 * @author H.W
 */
public class MyPane extends BorderPane {

    private BorderPane pane1;
    private BorderPane pane2;

    private VBox vBox;
    private Label introduction;
    private Label fileEner;
    private TextArea fileNames;
    private Button startButton;
    private Button fileSelectbutton;
    private List<File> targets = null;

    {
        pane1 = new BorderPane();
        pane2 = new BorderPane();
        vBox = new VBox();
        introduction = new Label("step 1:选择一系列文件;\nStep2:点击“开始”按钮以开始合并 ");
        fileEner = new Label("选择文件：");
        fileNames = new TextArea();
        startButton = new Button("开始");
        fileSelectbutton = new Button("浏览文件");

        pane1.setTop(introduction);
        pane1.setLeft(fileEner);
        pane2.setLeft(fileNames);
        pane2.setRight(fileSelectbutton);
        fileNames.setMaxWidth(200);
        startButton.setPadding(new Insets(65, 60, 80, 65));
        BorderPane.setMargin(fileSelectbutton, new Insets(0, 0, 0, 10));
        

        vBox.getChildren().add(pane1);
        vBox.getChildren().add(pane2);
        this.setLeft(vBox);
        this.setRight(startButton);

        pane1.setPadding(new Insets(10, 10, 10, 10));
        pane2.setPadding(new Insets(10, 10, 10, 10));
        this.setPadding(new Insets(10, 10, 10, 10));
    }

    public MyPane() {
        fileSelectbutton.setOnAction(e -> {
            selectFile();
        });

        startButton.setOnAction(e -> {
            if (targets != null) {
                FileCombiner fc = new FileCombiner(targets);
                String info = fc.combine() ? "已完成" : "失败";
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("状态提示");
                alert.setHeaderText(info);
                alert.setContentText("已完成");
                alert.showAndWait();
            }

        });

    }

    public void selectFile() {
        FileChooser filechooser = new FileChooser(); //配合按钮使用
        filechooser.setTitle("选择文件...");
        targets = filechooser.showOpenMultipleDialog(null);
        if (targets != null) {
            String nameStrings = "所选文件如下：\n";
            for (File file : targets) {
                nameStrings += file.getName() + "\n";
            }
            fileNames.setText(nameStrings);
        }
    }

}
