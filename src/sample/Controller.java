package sample;

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
import sample.model.Broadcast;
import sample.model.Database;
import sample.model.Program;

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
    }


    @FXML
    private void getDoubleClickedProgram() {

        tableView.setOnMouseClicked((MouseEvent event) -> { //Här är lamnda :)
            if (event.getButton().equals(MouseButton.PRIMARY) && event.getClickCount() == 2){
                Program p = tableView.getSelectionModel().getSelectedItem();
                openBroadcastPopup(database.getBroadcastFromProgram(p.getId(),
                        p.getName()));
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
                ("addProgram.fxml"));

        try {
            Parent root = loader.load();


            AddProgramController big = loader.getController();
            big.init();


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
        }
    }

    public void openBroadcastPopup(ObservableList<Broadcast> broadcasts){
        FXMLLoader loader = new FXMLLoader(getClass().getResource("broadcast.fxml"));

        try {
            Parent root = loader.load();

            BroadcastController pop = loader.getController();
            pop.setBroadcasts(broadcasts);
            pop.setTableValues();

            Stage stage = new Stage();
            stage.setScene(new Scene(root, 520,900));
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
