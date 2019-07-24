package sudoku;

import cell.interfaces.ICell;
import grid.objects.GridManager;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.input.InputEvent;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import sudoku.objects.CellModel;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;



public class Controller {

    GridManager gridManager = new GridManager();

    StackPane currentCell;
    List<CellModel> cellModels = new ArrayList<>();

    @FXML
    GridPane board;

    @FXML
    private void selectCell(InputEvent selectedCell){
        if(!selectedCell.getTarget().getClass().toString().equals("class javafx.scene.layout.StackPane")){
            return;
        }
        resetCellBackgroud();
        StackPane target = (StackPane) selectedCell.getTarget();
        if(target.getUserData().toString().equals("writable")){
            setCurrentCell(target);
        } else{
            getCellsWithSameNumber(target);
        }
    }

    private void getCellsWithSameNumber(StackPane target) {
        resetCellBackgroud();
        String number = getText(target.getChildren()).getText();
        getStackPaneList().filter(stackPane -> getText(stackPane.getChildren()).getText().equals(number))
                          .forEach(stackPane -> stackPane.setStyle("-fx-background-color: red;"));
    }

    private void resetCellBackgroud(){
        getStackPaneList().forEach(stackPane -> stackPane.setStyle(null));
    }

    private void setCurrentCell(StackPane target){
        if(currentCell == null){
            currentCell = target;
            currentCell.setStyle("-fx-border-color: red; -fx-border-width: 2.25px;");
        } else{
            currentCell.setStyle(null);
            currentCell = target;
            currentCell.setStyle("-fx-border-color: red; -fx-border-width: 2.25px;");
        }
    }

    @FXML
    private void enterNumberFromNumpad(Event buttonClicked){
        if(!buttonClicked.getSource().getClass().toString().equals("class javafx.scene.control.Button")){
            return;
        }
        Button source = (Button) buttonClicked.getSource();
        if(currentCell == null){
            return;
        } else{
            getText(currentCell.getChildren()).setText((source.getText()));
        }

    }

    @FXML
    private void initialize(){
        gridManager.makeSudoku();
        initModel(gridManager.getGrid().getCells());
        initBoard();
    }

    private void initBoard(){
        List<StackPane> listOfStackPanes = getStackPaneList().collect(Collectors.toList());
        List<Text> listOfTexts = getStackPaneList().map(stackpane -> getText(stackpane.getChildren())).collect(Collectors.toList());
        cellModels.stream().forEach(cellModel -> {
            int index = cellModels.indexOf(cellModel);
            int number = cellModel.getNumber();
            if(number == 0){
                listOfTexts.get(index).setText("");
                listOfStackPanes.get(index).setUserData("writable");
            } else{
                listOfTexts.get(index).setText(String.valueOf(number));
                listOfTexts.get(index).setStyle("-fx-font-weight: bold");
                listOfStackPanes.get(index).setUserData("fixed");
            }
        });
    }

    private void initModel(List<ICell> cells){
        cells.stream().map(this::makeCellModel).collect(Collectors.toList()).stream()
             .forEach(cellModel -> cellModel.numberProperty()
                                            .addListener((observable, oldValue, newValue) -> getStackPaneList()
                                                                                             .map(stackpane -> getText(stackpane.getChildren()))
                                                                                             .forEach(text -> text.setText(String.valueOf(cellModel.getNumber())))));
    }

    private CellModel makeCellModel(ICell cell){
        cellModels.add(new CellModel(cell.getRightNumber(),
                                     cell.getNumber(),
                                     cell.getPoint().getRow(),
                                     cell.getPoint().getCol()));
        return cellModels.get(0);
    }

    private Stream<StackPane> getStackPaneList(){
        return board.getChildren().stream().filter(thing -> thing.getClass().toString().equals("class javafx.scene.layout.StackPane"))
                                           .map(cellView -> (StackPane) cellView);
    }

    private Text getText(ObservableList<Node> children){
        return children.stream().filter(thing -> thing.getClass().toString().equals("class javafx.scene.text.Text"))
                                .map(cellText -> (Text) cellText)
                                .collect(Collectors.toList()).get(0);
    }
}
