/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 *
 * @author H.W
 */
public class GUI extends Application {

    @Override
    public void start(Stage primaryStage) {

        MyPane pane = new MyPane();
        Scene scene = new Scene(pane, 1080, 780);
        
        
//        System.out.println(getClass().getResource("css/style1.css").toExternalForm());
        Stage seconStage  = new Stage();
        
        primaryStage.setResizable(false);
        primaryStage.setTitle("成绩分析程序");
        primaryStage.setScene(scene);
        primaryStage.show();
        
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
}
