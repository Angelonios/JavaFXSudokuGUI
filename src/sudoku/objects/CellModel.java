package sudoku.objects;

import javafx.beans.property.*;

public class CellModel {

    IntegerProperty number;
    int row;
    int column;
    int rightNumber;

    public CellModel(int rightNumber, int number, int row, int col){
        saveInfo(rightNumber, row, col);
        setNumber(number);
    }

    private void saveInfo(int rightNumber, int row, int col){
        this.rightNumber = rightNumber;
        this.row = row;
        this.column = col;
    }

    public final void setNumber(int number){
        this.numberProperty().set(number);
    }

    public final int getNumber(){
        if(number != null){
            return number.get();
        } else{
            return 0;
        }
    }

    public final IntegerProperty numberProperty(){
        if(number == null){
            number = new SimpleIntegerProperty(0);
        }
        return number;
    }

}
