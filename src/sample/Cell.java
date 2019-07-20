package sample;

import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;

public class Cell extends StackPane {

    private int index;
    private Text number = new Text();

    public Cell(String number, int index){
        setNumber(number);
        setIndex(index);
    }

    public Cell(int index){
        setIndex(index);
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public Text getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number.setText(number);
    }
}
