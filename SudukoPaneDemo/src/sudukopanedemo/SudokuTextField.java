package sudukopanedemo;

import javafx.scene.control.TextField;

/**
 * SudokuTextField作为TextField的子类
 * 仅仅添加了行（row）和列（col）两项
 * 方便获取位置
 * 
 * @author H.W
 */
public class SudokuTextField extends TextField {

    private int row = 0;
    private int col = 0;
    private boolean Initialized = false; //数值是否初始化过

    public SudokuTextField(String s) {
        super(s);
    }

    public void setRow(int r) {
        row = r;
    }

    public void setCol(int c) {
        col = c;
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }

    public void setInitialized(boolean bl) {
        Initialized = bl;
    }

    public boolean isInitialized() {
        return Initialized ;
    }
}
