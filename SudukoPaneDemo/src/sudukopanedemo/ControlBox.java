/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sudukopanedemo;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;

class ControlBox extends HBox{
    private Button[] options = new Button[9];
    private int buttonValue = 0;
    
    public ControlBox(){
        this.setPadding(new Insets(10,10,10,70));
        //对按钮进行初始化
        for (int i = 0; i < 9; i++) {
            options[i] = new Button((i+1)+"");
            options[i].setPadding(new Insets(10,10,10,10));
            this.getChildren().add(options[i]);
        }
        //
        for (int i = 0; i < 9; i++) {
            options[i].setOnAction(new EventHandler<ActionEvent>(){
            public void handle(ActionEvent e){
                    //获取button的值，并赋予buttonValue
                    buttonValue = Integer.parseInt( ((Button)e.getSource()).getText() );
                    System.out.println(buttonValue);
                }
            });
        }
    }
    

    
    public int getbuttonValue(){
        return buttonValue;
    }
}


