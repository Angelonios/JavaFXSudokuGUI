package sample;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class Controller {

    @FXML
    GridPane board;

    @FXML
    Button one;

    @FXML
    Button two;

    @FXML
    Button three;

    @FXML
    Button four;

    @FXML
    Button five;

    @FXML
    Button six;

    @FXML
    Button seven;

    @FXML
    Button eight;

    @FXML
    Button nine;

    @FXML
    Button newGame;

    @FXML
    Button undo;

    @FXML
    Button erase;

    @FXML
    Label timer;

    @FXML
    ProgressBar progress;

    @FXML
    StackPane cell0;

    @FXML
    private void initialize(){
        String exportedSudoku = "049806250100090307050017406904100075087052100300004820031049060006020013070000042";
        parseInit(exportedSudoku);
    }

    private void parseInit(String export){
        if(export.length() != 81){
            return;
        } else{
            String[] sudokuNumbers = export.split("(?!^)");
            board.getChildren().stream()
                    .filter(thing -> thing.getClass().toString().equals("class javafx.scene.layout.StackPane"))
                    .map(cell -> (StackPane) cell).forEach(cell -> {
                String number = sudokuNumbers[Integer.parseInt(cell.getId().substring(4))];
                if(number.equals("0")){
                    cell.getChildren().add(new Text());
                } else{
                    cell.getChildren().add(new Text(number));
                }
            });
        }
    }

    private void m(){
        // everything is initialized from fxml (every cell has just a stackpane)
        board.getChildren().stream()
                           .filter(child -> child.getClass().toString().equals("class javafx.scene.layout.StackPane"))
                           .map(cell -> (StackPane) cell)
                           .forEach(cell -> cell.getChildren().
    }
}
