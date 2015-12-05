/*
 * To change hBox license header, choose License Headers in Project Properties.
 * To change hBox template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Exercise.EX_17_10;

import java.io.File;
import javafx.geometry.Insets;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
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
    private Label numberOfPartss;
    private TextField number;
    private TextField fileName;
    private Button startButton;
    private Button fileSelectbutton;
    private File target = null;

    {
        pane1 = new BorderPane();
        pane2 = new BorderPane();
        vBox = new VBox();
        introduction = new Label("step 1:选择一个文件;\nStep2:填写需要分割的份数；\nStep 3:点击“开始”按钮以开始分割 ");
        fileEner = new Label("选择文件：");
        numberOfPartss = new Label("填写需要分割的份数：");
        number = new TextField();
        fileName = new TextField();
        startButton = new Button("开始");

        fileSelectbutton = new Button("浏览文件");

        pane1.setTop(introduction);
        pane1.setLeft(fileEner);
        pane1.setCenter(fileName);
        pane1.setRight(fileSelectbutton);
        pane2.setLeft(numberOfPartss);
        pane2.setRight(number);
        startButton.setPadding(new Insets(65, 60, 80, 65));
//        startButton.scaleXProperty();
//        startButton.scaleYProperty();

        vBox.getChildren().add(pane1);
        vBox.getChildren().add(pane2);
//        vBox.getChildren().add(startButton);
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
            int n = Integer.parseInt(number.getText());
            if (target != null && n >= 0) {
                FileDivider fileDivider = new FileDivider(target, n);
                String info = fileDivider.divider() ? "已完成" : "失败";
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
        /**
         * Shows a new file open dialog. The method doesn't return until the
         * displayed open dialog is dismissed. The return value specifies the
         * file chosen by the user or if no selection has been made.
         */
        filechooser.setTitle("选择文件...");
        target = filechooser.showOpenDialog(null);
        if (target != null) {
            fileName.setText(target.getName());
        }
    }

}
