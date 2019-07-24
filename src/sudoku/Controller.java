package sudoku;

import cell.interfaces.ICell;
import grid.objects.GridManager;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
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
    private void selectCell(Event selectedCell){
        try{
            if(currentCell == null){
                currentCell = (StackPane) selectedCell.getTarget();
                currentCell.setStyle("-fx-border-color: red; -fx-border-width: 2.25px;");
            } else{
                currentCell.setStyle(null);
                currentCell = (StackPane) selectedCell.getTarget();
                currentCell.setStyle("-fx-border-color: red; -fx-border-width: 2.25px;");
            }
        }catch (Exception e){
            System.out.println("Got an error, sir! : \n" + e.getMessage());
            e.printStackTrace();
        }
    }

    @FXML
    private void enterNumberFromNumpad(Event buttonClicked){
        System.out.println(buttonClicked.getTarget());
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
            } else{
                listOfTexts.get(index).setText(String.valueOf(number));
                listOfStackPanes.get(index).setDisable(true);
            }
        });
    }

    private void initModel(List<ICell> cells){
        cells.stream().map(this::makeCellModel).collect(Collectors.toList()).stream()
                .forEach(
                        cellModel -> cellModel.numberProperty().addListener(
                                (observable, oldValue, newValue) -> getStackPaneList().map(stackpane -> getText(stackpane.getChildren()))
                                        .forEach(text -> text.setText(String.valueOf(cellModel.getNumber())))));

    }

    private CellModel makeCellModel(ICell cell){
        cellModels.add(new CellModel(cell.getRightNumber().intValue(), cell.getNumber().intValue(), cell.getPoint().getRow().intValue(), cell.getPoint().getCol().intValue()));
        return cellModels.get(0);
    }

    private Stream<StackPane> getStackPaneList(){
        return board.getChildren().stream().filter(thing -> thing.getClass().toString().equals("class javafx.scene.layout.StackPane"))
                                           .map(cellView -> (StackPane) cellView);
    }

    private Text getText(ObservableList<Node> children){
        children.stream().forEach(child -> System.out.println(child.getClass().toString()));
        return children.stream().filter(thing -> thing.getClass().toString().equals("class javafx.scene.text.Text")).map(cellText -> (Text) cellText).collect(Collectors.toList()).get(0);
    }
}
