package databasOu2.controller;

import databasOu2.model.Database;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import databasOu2.model.Broadcast;
import databasOu2.model.Program;

import java.io.IOException;

public class BroadcastController {
    @FXML
    private TableView<Broadcast> tableViewPopup;
    @FXML
    private TableColumn<Broadcast, String> dateColumn;
    @FXML
    private TableColumn<Broadcast, String> durationColumn;
    @FXML
    private Button addBroadcastButton;
    @FXML
    private TextField programTextField;


    AddBroadcastController addBController;

    private ObservableList<Broadcast> broadcasts;

    private Broadcast broadcast;

    private Program program;

    private Database database;

    public BroadcastController(){
        System.out.println("started");

        database = new Database();
    }

    public void setProgram(Program program){
        this.program = program;
        programTextField.setText(program.getName());
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


    public void openAddBroadcastPopup() {
        FXMLLoader loader = new FXMLLoader(getClass().getResource
                ("/databasOu2/view/addBroadcast.fxml"));

        try {
            Parent root = loader.load();

            addBController = loader.getController();
            addBController.setProgram(program);
            addBController.setTable(tableViewPopup);


            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void deleteBroadcast() {
        broadcast = tableViewPopup.getSelectionModel().getSelectedItem();
        System.out.println(tableViewPopup.getSelectionModel().getSelectedItem());
        if (broadcast == null) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("Please choose a broadcast before deletion");
            alert.showAndWait();
        } else {
            database.deleteOnlyBroadcast(broadcast.getId());
            tableViewPopup.getItems().remove(broadcast);
        }
    }



}
