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

public class SudokuPaneDemo extends Application {
    
    
    
 
    @Override
    public void start(Stage primaryStage) {
        SudokuPane sukudoPane = new SudokuPane();
        Button bt = new Button("OK");
        
        
        
        {
            
        
        
        
        
        
        
        }
        
        Scene scene = new Scene(sukudoPane, 450, 500);
        //添加CSS文件
        scene.getStylesheets().add(getClass().getResource("test.css").toExternalForm());
        primaryStage.setTitle("Sukudo");
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.show();
    }
    
    
    

    public static void main(String[] args) {
        launch(args);
    }
}






