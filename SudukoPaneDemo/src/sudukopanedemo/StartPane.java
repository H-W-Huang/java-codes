/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sudukopanedemo;

import static javafx.application.Platform.exit;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;


class StartPane extends StackPane {

    private SudokuPane sudokuPane ;
    private Button startGameButton = new Button("Start");
    private Button ExitGameButton = new Button("Exit");
    private ChoiceBox cb;
    private Label choice;
    private ImageView imageView = new ImageView(new Image("sudukopanedemo/images/sudoku-icon.png"));
    private VBox vBox = new VBox();
    private HBox hbox = new HBox();
    private BorderPane bpane = new BorderPane();

    public StartPane() {

        choice = new Label("Choice:");
        ChoiceBox cb = new ChoiceBox(FXCollections.observableArrayList(
                "1", "2", "3", "4", "5","6", "7", "8", "9", "10")
        );
        

        //修改button的样式
        startGameButton.setPadding(new Insets(8, 80, 20, 80));
        ExitGameButton.setPadding(new Insets(8, 83, 20, 85));
        
        //添加按钮动作
        startGameButton.setOnAction(e->{
            try{
                sudokuPane = new SudokuPane(Integer.parseInt((String)cb.getValue()));
                
            }catch(Exception ex){
                sudokuPane = new SudokuPane(1);
                System.out.println("用户未选择关卡");
            }
            finally{
                this.getChildren().add(sudokuPane); 
            }
                       
        });
        
        ExitGameButton.setOnAction(e->{
            exit();
        });
        
        
        
        
        //放置组件
        hbox.getChildren().add(choice);
        hbox.getChildren().add(cb);
        vBox.getChildren().add(startGameButton);
        vBox.getChildren().add(hbox);
        vBox.getChildren().add(ExitGameButton);

        HBox.setMargin(cb, new Insets(0, 0, 0, 20));
        VBox.setMargin(hbox, new Insets(0, 0, 20, 20));
        VBox.setMargin(startGameButton, new Insets(20, 0, 20, 0));

        

        bpane.setTop(imageView);
        bpane.setCenter(vBox);
        BorderPane.setAlignment(imageView, Pos.CENTER);
        BorderPane.setMargin(vBox, new Insets(20, 0, 0, 120));

        this.setStyle("-fx-background-color:white");
        
        this.getChildren().add(bpane);
    }

}
