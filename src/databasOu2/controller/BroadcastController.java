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
import java.sql.SQLException;

/**
 * Controller for broadcast
 */
public class BroadcastController {
    @FXML
    private TableView<Broadcast> tableViewPopup;
    @FXML
    private TableColumn<Broadcast, String> dateColumn;
    @FXML
    private TableColumn<Broadcast, String> durationColumn;
    @FXML
    private TextField programTextField;


    AddBroadcastController addBController;

    private ObservableList<Broadcast> broadcasts;

    private Broadcast broadcast;

    private Program program;

    private Database database;

    /**
     * Constructor
     */
    public BroadcastController(){
        try {
            database = new Database();
        } catch (ClassNotFoundException | SQLException e) {
            Controller.showPopupMessage("Database Error");
        }
    }

    /**
     * Gets program
     * @param program - program
     */
    public void setProgram(Program program){
        this.program = program;
        programTextField.setText(program.getName());
    }

    /**
     * Gets tablevalues and insert them in table
     */
    public void setTableValues(){
        if(broadcasts != null){
            dateColumn.setCellValueFactory(new
                    PropertyValueFactory<>("date"));
            durationColumn.setCellValueFactory(new
                    PropertyValueFactory<>("duration"));

            tableViewPopup.getItems().setAll(broadcasts);
        }
    }

    /**
     * Gets broadcast
     * @param broadcasts - broadcasts
     */
    public void setBroadcasts(ObservableList<Broadcast> broadcasts) {
        this.broadcasts = broadcasts;
    }


    /**
     * Opens addBroadcast gui when button is pressed
     */
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
            stage.setTitle("Add broadcast");
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Deletes a broadcast from the table
     */
    public void deleteBroadcast() {
        broadcast = tableViewPopup.getSelectionModel().getSelectedItem();
        //System.out.println(tableViewPopup.getSelectionModel()
        // .getSelectedItem());
        if (broadcast == null) {
            Controller.showPopupMessage("Please choose a program before deletion");
        } else {
            database.deleteOnlyBroadcast(broadcast.getId());
            tableViewPopup.getItems().remove(broadcast);
        }
    }

    /**
     * Opens the edit broadcast Gui when button is pressed
     */
    public void openEditBroadcast(){
        FXMLLoader loader = new FXMLLoader(getClass().getResource
                ("/databasOu2/view/editBroadcast.fxml"));

        try {
            broadcast = tableViewPopup.getSelectionModel().getSelectedItem();
            //System.out.println(tableViewPopup.getSelectionModel()
            // .getSelectedItem());
            if (broadcast == null) {
                Controller.showPopupMessage("Please choose a program before editing");
            } else {
                Parent root = loader.load();

                EditBroadcastController edit = loader.getController();
                edit.setTable(tableViewPopup);
                edit.setBroadcast(broadcast);

                Stage stage = new Stage();
                stage.setScene(new Scene(root));
                stage.setTitle("Edit broadcast");
                stage.show();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
