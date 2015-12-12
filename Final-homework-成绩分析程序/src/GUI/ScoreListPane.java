/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import MyTools.Student;
import MyTools.Student4GUI;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;

/**
 *
 * @author H.W
 */
public class ScoreListPane extends Pane {

    final private int TEXT_FILE = 0;
    final private int BINARY_FILE = 1;
    private TableView<Student4GUI> table = new TableView<>();
    private ObservableList<Student4GUI> data = FXCollections.observableArrayList();

    public ScoreListPane() {

        TableColumn schoolIDCol = new TableColumn("学号");
        TableColumn nameCol = new TableColumn("姓名");
        TableColumn scoreCol = new TableColumn("成绩");
        table.getColumns().addAll(schoolIDCol, nameCol, scoreCol);

        /**
         * 对添加数据操作的实现代码 *
         */
        schoolIDCol.setCellValueFactory(new PropertyValueFactory<>("schoolId")); // 名 必须与 对应类中的变量名字一样
        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        scoreCol.setCellValueFactory(new PropertyValueFactory<>("score"));

        /**
         * *************************
         */
        table.setPrefSize(500, 600);
        schoolIDCol.prefWidthProperty().bind(table.widthProperty().multiply(0.33));
        nameCol.prefWidthProperty().bind(table.widthProperty().multiply(0.33));
        scoreCol.prefWidthProperty().bind(table.widthProperty().multiply(0.33));
        this.getChildren().add(table);
    }

    private void readFileContent(File file, int type) throws FileNotFoundException, IOException {
        data.clear();
        if (type == TEXT_FILE) {
            Scanner input = new Scanner(file);
            while (input.hasNext()) {
                String[] studentData = input.nextLine().split(",");
                data.add(new Student4GUI(studentData[0], studentData[1], Integer.parseInt(studentData[2])));
            }
        } else {
            ObjectInputStream ois = new ObjectInputStream(new BufferedInputStream(new FileInputStream(file)));
            try {
                ArrayList<Student4GUI> dataFromFile = (ArrayList<Student4GUI>) ois.readObject();
                for (Student4GUI s : dataFromFile) {
                    data.add(s);
                }
            } catch (ClassNotFoundException ex) {
                System.out.println("找不到该类");
            }
            
        }

    }

    public void addData(File file, int type) throws FileNotFoundException, IOException {
        if (file != null) {
            if (type == TEXT_FILE) {
                readFileContent(file, TEXT_FILE);
            } else {
                readFileContent(file, BINARY_FILE);
            }
        }
        table.setItems(data);
    }

    /**
     * 将data里的数据组织到ArrayList中并返回 ArrayList中装的是Student4GUI对象
     *
     * @return
     */
    public ArrayList<Student4GUI> getData() {
        ArrayList<Student4GUI> result = new ArrayList<>();
        for (Student4GUI s : data) {
            result.add(s);
        }
        return result;
    }

}
