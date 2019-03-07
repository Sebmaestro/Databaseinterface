package databasOu2.controller;

import databasOu2.model.Broadcast;
import databasOu2.model.Database;
import databasOu2.model.Program;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ResourceBundle;

public class AddBroadcastController implements Initializable{

    @FXML
    private TextField durationTextField;
    @FXML
    private Button addButton;
    @FXML
    private TextField starttimeTextField;

    private Database database;

    private Program program;

    private TableView<Broadcast> broadcastTableView;

    /**
     *
     */
    public AddBroadcastController() {
         database = new Database();
        //database.setCategoryPairs();
        //database.setChannelNames();

    }

    public void setProgram(Program program) {
        this.program = program;
    }

    public void setTable(TableView<Broadcast> broadcastsTableView) {
        this.broadcastTableView = broadcastsTableView;
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        durationTextField.setStyle("-fx-prompt-text-fill: derive" +
                "(-fx-control-inner-background, -30%);");
        starttimeTextField.setStyle("-fx-prompt-text-fill: derive" +
                "(-fx-control-inner-background, -30%);");
    }

    public void addBroadcast() {
        Broadcast b = null;
        try {
            b = database.addBroadcast(program, null, starttimeTextField
                    .getText(), durationTextField.getText(), null);
        } catch (SQLException e) {
            showPopupMessage("There is already a broadcast at the " +
                    "specified time for this channel");
        } catch (ParseException | IllegalArgumentException e){
            showPopupMessage("Wrong formatted input");
        }
        Stage stage = (Stage) addButton.getScene().getWindow();
        stage.close();

        broadcastTableView.getItems().add(b);
    }

    private void showPopupMessage(String str) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setContentText(str);
        alert.showAndWait();
    }
}
