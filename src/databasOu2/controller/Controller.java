package databasOu2.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import databasOu2.model.Broadcast;
import databasOu2.model.Database;
import databasOu2.model.Program;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

/**
 * Controller for main GUI window
 */
public class Controller implements Initializable{
    private Database database;

    @FXML
    private Menu channels;
    @FXML
    private TableView<Program> tableView;
    @FXML
    private TableColumn<Program, String> programColumn;
    @FXML
    private TableColumn<Program, String> categoryColumn;
    @FXML
    private TableColumn<Program, String> editorColumn;
    @FXML
    private TextField channelTextField;


    /**
     * Constructor
     */
    public Controller() {
        try {
            database = new Database();
        } catch (ClassNotFoundException | SQLException e) {
            showPopupMessage("Database Error");
        }
        database.setChannelNames();
        database.setCategoryPairs();
        database.createTriggerFunction();
    }

    /**
     * Initializes the menu when program is ran
     * @param location - dont know
     * @param resources - no idea
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ArrayList<String> channelNames = database.getChannelNames();

        for (String s : channelNames) {
            MenuItem item = new MenuItem();
            item.setText(s);
            item.setOnAction(event -> { //Här är lamnda igen :)
                ObservableList<Program> programs = database
                        .getProgramsFromChannel(item.getText());

                programColumn.setCellValueFactory(new
                        PropertyValueFactory<>("name"));
                categoryColumn.setCellValueFactory(new
                        PropertyValueFactory<>("category"));
                editorColumn.setCellValueFactory(new
                        PropertyValueFactory<>("editor"));

                tableView.getItems().setAll(programs);
                channelTextField.setText(item.getText());

            });
            channels.getItems().add(item);
        }
    }

    /**
     * Gets program double clicked on and opens the broadcasts
     */
    @FXML
    private void getDoubleClickedProgram() {

        tableView.setOnMouseClicked((MouseEvent event) -> { //Här är lamnda :)
            if (event.getButton().equals(MouseButton.PRIMARY) && event.getClickCount() == 2){
                Program p = tableView.getSelectionModel().getSelectedItem();
                openBroadcastPopup(database.getBroadcastFromProgram(p.getId(),
                        p.getName()), p);
            }
        });
    }

    /**
     * Opens add program GUI when button is clicked
     */
    public void openAddProgramPopup() {
        FXMLLoader loader = new FXMLLoader(getClass().getResource
                ("/databasOu2/view/addProgram.fxml"));


        try {
            Parent root = loader.load();


            AddProgramController addProgramController = loader.getController();
            addProgramController.setTableView(tableView);


            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Deletes a program
     */
    public void deleteProgram() {

        Program p = tableView.getSelectionModel().getSelectedItem();
        System.out.println(tableView.getSelectionModel().getSelectedItem());
        if (p == null) {
            showPopupMessage("Please choose a program before deletion");
        } else {
            database.deleteProgramAndBroadcasts(p.getId());
            tableView.getItems().remove(p);
        }
    }

    /**
     * Opens the broadcast GUI
     * @param broadcasts - broadcasts
     * @param program - program
     */
    public void openBroadcastPopup(ObservableList<Broadcast> broadcasts, Program program){
        FXMLLoader loader = new FXMLLoader(getClass().getResource
                ("/databasOu2/view/broadcast.fxml"));

        try {
            Parent root = loader.load();

            BroadcastController broadCtrl = loader.getController();
            broadCtrl.setProgram(program);
            broadCtrl.setBroadcasts(broadcasts);
            broadCtrl.setTableValues();

            Stage stage = new Stage();
            stage.setScene(new Scene(root, 520,900));
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Opens the edit program GUI
     */
    public void openEditProgram(){
        FXMLLoader loader = new FXMLLoader(getClass().getResource
                ("/databasOu2/view/editProgram.fxml"));


        try {

            if (tableView.getSelectionModel().getSelectedItem() == null) {
                showPopupMessage("Please choose a program before editing");
            } else {
                Parent root = loader.load();

                EditProgramController edit = loader.getController();
                edit.setProgram(tableView.getSelectionModel().getSelectedItem());
                edit.setTable(tableView);

                ObservableList<String> categoryObservable = FXCollections
                        .observableArrayList();
                categoryObservable.addAll(database.getCategoryNames());

                edit.setCategoryList(categoryObservable);

                Stage stage = new Stage();
                stage.setScene(new Scene(root));
                stage.show();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Shows a popup with a message
     * @param str - string
     */
    public static void showPopupMessage(String str) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setContentText(str);
        alert.showAndWait();
    }


}
