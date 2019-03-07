package databasOu2.controller;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
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
import java.util.ArrayList;

public class Controller {
    private Database database;
    private boolean wasInit;

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


    public Controller() {
        database = new Database();
        database.setChannelNames();
        database.setCategoryPairs();
        wasInit = false;
        database.createTriggerFunction();
    }


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


    @FXML
    public void initMenu() {

        if (!wasInit) {
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
            wasInit = true;
        }
    }

    public void openAddProgramPopup() {
        FXMLLoader loader = new FXMLLoader(getClass().getResource
                ("/databasOu2/view/addProgram.fxml"));


        try {
            Parent root = loader.load();


            AddProgramController big = loader.getController();
            big.init();
            big.setTableView(tableView);


            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void deleteProgram() {

        Program p = tableView.getSelectionModel().getSelectedItem();
        System.out.println(tableView.getSelectionModel().getSelectedItem());
        if (p == null) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("Please choose a program before deletion");
            alert.showAndWait();
        } else {
            database.deleteProgramAndBroadcasts(p.getId());
        }
    }


    public void openBroadcastPopup(ObservableList<Broadcast> broadcasts, Program p){
        FXMLLoader loader = new FXMLLoader(getClass().getResource
                ("/databasOu2/view/broadcast.fxml"));

        try {
            Parent root = loader.load();

            BroadcastController broadCtrl = loader.getController();
            broadCtrl.setProgram(p);
            broadCtrl.setBroadcasts(broadcasts);
            broadCtrl.setTableValues();

            Stage stage = new Stage();
            stage.setScene(new Scene(root, 520,900));
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
