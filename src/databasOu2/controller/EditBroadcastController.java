package databasOu2.controller;

import databasOu2.model.Broadcast;
import databasOu2.model.Database;
import javafx.fxml.FXML;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

import java.sql.SQLException;
import java.text.ParseException;

public class EditBroadcastController {
    private TableView<Broadcast> broadcastTableView;
    private Broadcast broadcast;
    private Database database;

    @FXML
    private TextField currentStarttimeTextField;

    @FXML
    private TextField currentDurationTextField;

    public EditBroadcastController() {
        database = new Database();
    }

    public void setTable(TableView<Broadcast> broadcastTableView) {
        this.broadcastTableView = broadcastTableView;
    }

    public void editProgram(){
        try {
            database.editBroadcast(broadcast, currentStarttimeTextField.
                    getText(), currentDurationTextField.getText());
        } catch (SQLException e) {
            Controller.showPopupMessage("There is already a broadcast at the " +
                    "specified time for this channel");
        } catch (ParseException | IllegalArgumentException e) {
            Controller.showPopupMessage("Wrong formatted input");
        }
    }

    public void setBroadcast(Broadcast broadcast){
        this.broadcast = broadcast;

        currentStarttimeTextField.setText(this.broadcast.getDate());
        currentDurationTextField.setText(this.broadcast.getDuration());
    }
}
