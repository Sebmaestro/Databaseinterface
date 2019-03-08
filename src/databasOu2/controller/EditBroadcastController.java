package databasOu2.controller;

import databasOu2.model.Broadcast;
import databasOu2.model.Database;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.sql.SQLException;
import java.text.ParseException;

/**
 * Controller for editBroadcast
 */
public class EditBroadcastController {
    private TableView<Broadcast> broadcastTableView;
    private Broadcast broadcast;
    private Database database;

    @FXML
    private TextField durationTextField;
    @FXML
    private Button editButton;
    @FXML
    private TextField starttimeTextField;

    /**
     * Constructor
     */
    public EditBroadcastController() {
        try {
            database = new Database();
        } catch (ClassNotFoundException | SQLException e) {
            Controller.showPopupMessage("Database Error");
        }
    }

    /**
     * Gets the table
     * @param broadcastTableView - table
     */
    public void setTable(TableView<Broadcast> broadcastTableView) {
        this.broadcastTableView = broadcastTableView;
    }

    /**
     * Edit the broadcast when button is pressed
     */
    public void editBroadcast(){
        try {
            database.editBroadcast(broadcast, starttimeTextField.
                    getText(), durationTextField.getText());
            broadcastTableView.refresh();
            Stage stage = (Stage) editButton.getScene().getWindow();
            stage.close();


        } catch (SQLException e) {
            Controller.showPopupMessage("There is already a broadcast at the " +
                    "specified time for this channel");
        } catch (ParseException | IllegalArgumentException e) {
            Controller.showPopupMessage("Wrong formatted input");
        }
    }

    /**
     * Gets the broadcast and sets the fields with info
     * @param broadcast - broadcast
     */
    public void setBroadcast(Broadcast broadcast){
        this.broadcast = broadcast;

        starttimeTextField.setText(this.broadcast.getDate());
        durationTextField.setText(this.broadcast.getDuration());
    }
}
