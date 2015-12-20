package GUI;

import Data.Student4GUI;
import Exception.FileContentException;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.Scanner;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;

/**
 * 显示出来的数据由data决定，改变data的内容就可以改变显示的内容 数据的传递途径： file --> ArrayList -->
 * data(ObservableList) (文本文件和二进制文件都一样) 整个过程中间的 ArrayList 一旦形成就不会去修改
 *
 * @author H.W
 */
public class ScoreListPane extends Pane {

    final private int TEXT_FILE = 0;
    final private int BINARY_FILE = 1;
    private String theClass;
    private String lecture;
    private TableView<Student4GUI> table = new TableView<>();
    private ObservableList<Student4GUI> data = FXCollections.observableArrayList();
    private ArrayList<Student4GUI> dataFromFile = new ArrayList<>();

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

//        this.getStylesheets().add(getClass().getResource("css/style1.css").toExternalForm());
    }

    /**
     * 更新数据： data (ObverableList)
     */
    private void updateData(ArrayList<Student4GUI> list) {
        data.clear();
        for (Student4GUI s : list) {
            data.add(s);
        }
    }

    /**
     * 将data里的数据组织到ArrayList中并返回 ArrayList中装的是Student4GUI对象 注意是data里的数据，即当前数据
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

    public int getItemsSize() {
        return data.size();
    }

    /**
     * 从文件中读取数据
     *
     * @param file
     * @param type
     * @throws FileNotFoundException
     * @throws IOException
     */
    private ArrayList<Student4GUI> readFileContent(File file, int type) throws FileNotFoundException, IOException, ClassNotFoundException, ArrayIndexOutOfBoundsException, FileContentException {

        ArrayList<Student4GUI> result = new ArrayList<>();
        if (file != null) {
                
                System.out.println(theClass+" "+lecture+"");

                if (type == TEXT_FILE) {
                    Scanner input = new Scanner(file, "UTF-8");
                    String[] studentData;

                    try {
                        studentData = input.nextLine().split(",");
                        boolean bool = studentData[0].isEmpty() || studentData[1].isEmpty() || studentData[2].matches("\\D[0,]");
                        result.add(new Student4GUI(studentData[0], studentData[1], Integer.parseInt(studentData[2])));
                    } catch (Exception ex) {
                        throw new FileContentException();
                    }
                                    
                    while (input.hasNext()) {
                        studentData = input.nextLine().split(",");
                        result.add(new Student4GUI(studentData[0], studentData[1], Integer.parseInt(studentData[2])));
                        //                data.add(new Student4GUI(studentData[0], studentData[1], Integer.parseInt(studentData[2])));
                    }

                } else {
                    ObjectInputStream ois = new ObjectInputStream(new BufferedInputStream(new FileInputStream(file)));
                    result = (ArrayList<Student4GUI>) ois.readObject();

                }
            }
//        }

        return result;
    }

    /**
     * 根据输入的关键字,dataFromFile中筛选出对应的数据，然后再重新整理data(ObservableList)
     *
     * @param skeyword
     */
    private ArrayList<Student4GUI> getSearchedData(String keyWord) {
        ArrayList<Student4GUI> result = new ArrayList<>();

        //首先是预处理，去除空格
        String kw = keyWord.trim();
        System.out.println("Trim 后的结果" + kw);

        //若是空白
        if (kw.equals("")) {
            System.out.println("empty string!");
            result = dataFromFile;
        } //判断输入的是数字还是字符，若是数字，查找学号；否则查找姓名。
        else if (Character.isDigit(kw.charAt(0))) {
            for (Student4GUI s : dataFromFile) {
                System.out.println(s.getSchoolId());
                if (s.getSchoolId().contains(kw)) {
                    System.out.println("contains!!");
                    result.add(s);
                }
            }
        } else {
            for (Student4GUI s : dataFromFile) {
                if (s.getName().contains(kw)) {
                    result.add(s);
                }
            }
        }
        System.out.println("查找结果");
        for (Student4GUI s : result) {
            System.out.println(s);
        }
        return result;
    }

    /**
     * 查找带有关键字的数据
     *
     * @param keyWord
     */
    public void searchData(String keyWord) {
        updateData(getSearchedData(keyWord));
        table.setItems(data);
    }

    /**
     * 显示从文件读取的内容
     *
     * @param file
     * @param type
     * @throws FileNotFoundException
     * @throws IOException
     */
    public void displayDataFromFile(File file, int type) throws FileNotFoundException, IOException, ClassNotFoundException, FileContentException, ArrayIndexOutOfBoundsException {
        if (file != null) {
            if (type == TEXT_FILE) {
                dataFromFile = readFileContent(file, TEXT_FILE);
            } else {
                dataFromFile = readFileContent(file, BINARY_FILE);
            }
            updateData(dataFromFile);
        }
        table.setItems(data);
    }

    /**
     * @return the theClass
     */
    public String getTheClass() {
        return theClass;
    }

    /**
     * @param theClass the theClass to set
     */
    public void setTheClass(String theClass) {
        this.theClass = theClass;
    }

    /**
     * @return the lecture
     */
    public String getLecture() {
        return lecture;
    }

    /**
     * @param lecture the lecture to set
     */
    public void setLecture(String lecture) {
        this.lecture = lecture;
    }
    
    

}
