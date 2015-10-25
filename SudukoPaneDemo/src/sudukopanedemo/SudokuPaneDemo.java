package sudukopanedemo;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import CountDownTimer.TimePane;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;

public class SudokuPaneDemo extends Application {
    private int totalSec = 0; // 所使用的时间
    
    @Override
    public void start(Stage primaryStage) {
        BorderPane mainPane = new BorderPane();  //创建主面板
        TimePane timePane = new TimePane();         //创建计时器面板
        SudokuPane sudokuPane = new SudokuPane();  // 创建数独面板
        
        mainPane.setCenter(sudokuPane);
        mainPane.setTop(timePane);
        
        //为计时器面板添加动画
        EventHandler<ActionEvent> handler = e -> {
            totalSec++;
            timePane.setTime(totalSec);
        };

        Timeline animation = new Timeline(new KeyFrame(Duration.millis(1000), handler));
        animation.setCycleCount(Timeline.INDEFINITE);
        animation.play();
        
        
        
        
        Scene scene = new Scene(mainPane, 450, 600);
        //添加CSS文件
        scene.getStylesheets().add(getClass().getResource("test.css").toExternalForm());
        primaryStage.setTitle("Sukudo");
        primaryStage.setScene(scene);
//        primaryStage.setResizable(false);
        primaryStage.show();
    }
    
    
    public static void main(String[] args) {
        launch(args);
    }
}






