package sudukopanedemo;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class SudokuDemo extends Application {

    private StackPane mainPane;
    private SudokuPane sudokuPane;
    private StartPane startPane;

    @Override
    public void start(Stage primaryStage) {
        mainPane = new StackPane();  //创建主面板
//        sudokuPane = new SudokuPane();  // 创建数独面板       
        startPane = new StartPane();
        mainPane.getChildren().add(startPane);      //在mainPane中间放置sudokuPane


        Scene scene = new Scene(mainPane, 450, 600);
        //添加CSS文件
        scene.getStylesheets().add(getClass().getResource("SudokuCss.css").toExternalForm());
        primaryStage.setTitle("Sukudo");
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
