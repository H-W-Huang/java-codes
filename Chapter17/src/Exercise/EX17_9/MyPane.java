/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Exercise.EX17_9;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;

/**
 *
 * @author H.W
 */
public class MyPane extends GridPane {

    private Label name;
    private Label street;
    private Label city;
    private Label state;
    private Label zip;
    private TextField namef;
    private TextField streetf;
    private TextField cityf;
    private TextField statef;
    private TextField zipf;
    private Button[] buttons = new Button[5];
    private HBox hbox;
    private Deque<MyAddress> list;
    private MyAddress currentAddress;

    {
        name = new Label("Name: ");
        street = new Label("Street: ");
        city = new Label("City: ");
        state = new Label("State: ");
        zip = new Label("Zip: ");
        namef = new TextField();
        streetf = new TextField();
        cityf = new TextField();
        statef = new TextField();
        zipf = new TextField();
        hbox = new HBox();
        buttons[0] = new Button("Add");
        buttons[1] = new Button("First");
        buttons[2] = new Button("Next");
        buttons[3] = new Button("Previous");
        buttons[4] = new Button("Update");
        hbox.getChildren().add(buttons[0]);
        hbox.getChildren().add(buttons[1]);
        hbox.getChildren().add(buttons[2]);
        hbox.getChildren().add(buttons[3]);
        hbox.getChildren().add(buttons[4]);

        this.add(name, 0, 0);
        this.add(street, 0, 1);
        this.add(city, 0, 2);
        this.add(state, 0, 3);
        this.add(zip, 0, 4);
        this.add(namef, 1, 0);
        this.add(streetf, 1, 1);
        this.add(cityf, 1, 2);
        this.add(statef, 1, 3);
        this.add(zipf, 1, 4);
        this.add(hbox, 0, 5);

    }

    public MyPane() {

        list = new Deque<>();
        initialize();

        /**
         * add 按钮动作
         */
        buttons[0].setOnAction((ActionEvent e) -> {
            addData();
        });

        /**
         * first按钮
         */
        buttons[1].setOnAction(e -> {
            currentAddress = list.getTheLeft().getItem();
            fillBlanks(currentAddress);
        });

        /**
         * next按钮
         */
        buttons[2].setOnAction(e -> {
            currentAddress = list.getNext().getItem();
            fillBlanks(currentAddress);

        });

        /**
         * Previous按钮
         */
        buttons[3].setOnAction(e -> {
            currentAddress = list.getPrevious().getItem();
            fillBlanks(currentAddress);
        });

        buttons[4].setOnAction(e -> {
//            save2File();
        });

    }

    /**
     * 将整个链表写入文件
     */
    public void save2File() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream("Ex17_9.dat")));) {
            oos.writeObject(list);
            System.out.println("写入成功！");

        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            Logger.getLogger(MyPane.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * 初始化工作 将文件里的链表读出
     */
    public void initialize() {
        int count = 0;
        MyAddress temp = null;
        try (ObjectInputStream ois = new ObjectInputStream(new BufferedInputStream(new FileInputStream("1.dat")));) {
            list = (Deque<MyAddress>) ois.readObject();
            System.out.println("succefully read.");
            fillBlanks(list.getTheLeft().getItem());
        } catch (ClassNotFoundException ex) {
            System.out.println("找不到对象的类，读出失败。");
        } catch (IOException ex) {
//            System.out.println("读取失败！");
            ex.printStackTrace();
        }
    }

    /**
     * 填写界面空格，输出的参数为地址
     *
     * @param address
     */
    public void fillBlanks(MyAddress address) {
        namef.setText(address.getName());
        statef.setText(address.getState());
        zipf.setText(address.getZip() + "");
        cityf.setText(address.getCity());
        streetf.setText(address.getStreet());
    }

    /**
     * 填写数组操作
     */
    public void addData() {
        MyAddress temp = null;
        if (!((namef.getText().matches("\\s") || namef.getText().isEmpty())
                || (streetf.getText().matches("\\s") || streetf.getText().isEmpty())
                || (cityf.getText().matches("\\s") || cityf.getText().isEmpty())
                || (statef.getText().matches("\\s") || statef.getText().isEmpty())
                || (zipf.getText().matches("\\s") || zipf.getText().isEmpty()))
            ) {
//        if( !(namef.getText().matches("\\s") || namef.getText().isEmpty())){
            temp = new MyAddress(namef.getText(), streetf.getText(), cityf.getText(), statef.getText(), Integer.parseInt(zipf.getText()));
            System.out.println(temp);
            if (temp != null) {
                list.add2Right(temp);
                namef.setText("");
                statef.setText("");
                zipf.setText("");
                cityf.setText("");
                streetf.setText("");
            }
        }else {
            System.out.println("No address is written!");
        }
    }

}
