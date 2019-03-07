package sample;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import sample.model.Broadcast;
import sample.model.Program;

import java.util.Date;

public class BroadcastController {
    @FXML
    private TableView<Broadcast> tableViewPopup;
    @FXML
    private TableColumn<Broadcast, String> dateColumn;
    @FXML
    private TableColumn<Broadcast, String> durationColumn;
    private ObservableList<Broadcast> broadcasts;

    private Program program;

    public BroadcastController(){
        System.out.println("started");

    }

    public void setProgram(Program program){
        this.program = program;
    }

    public void setTableValues(){
        if(broadcasts != null){
            dateColumn.setCellValueFactory(new
                    PropertyValueFactory<>("date"));
            durationColumn.setCellValueFactory(new
                    PropertyValueFactory<>("duration"));
            tableViewPopup.getItems().setAll(broadcasts);
        }
    }

    public void setBroadcasts(ObservableList<Broadcast> broadcasts) {
        this.broadcasts = broadcasts;
    }

}
