package databasOu2.controller;

import databasOu2.model.Broadcast;
import databasOu2.model.Database;
import databasOu2.model.Program;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.sql.SQLException;
import java.text.ParseException;

/**
 * Controller for addBroadcast
 */
public class AddBroadcastController{

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
     * Constructor
     */
    public AddBroadcastController() {
        try {
            database = new Database();
        } catch (ClassNotFoundException | SQLException e) {
            Controller.showPopupMessage("Database Error");
        }
    }

    /**
     * Gets the program
     * @param program - program
     */
    public void setProgram(Program program) {
        this.program = program;
    }

    /**
     * Gets the table
     * @param broadcastsTableView - table
     */
    public void setTable(TableView<Broadcast> broadcastsTableView) {
        this.broadcastTableView = broadcastsTableView;
    }

    /**
     * Adds a broadcast when button is clicked
     */
    public void addBroadcast() {
        Broadcast broadcast = null;
        try {
            broadcast = database.addBroadcast(program, null, starttimeTextField
                    .getText(), durationTextField.getText(), null);
            Stage stage = (Stage) addButton.getScene().getWindow();
            stage.close();
        } catch (SQLException e) {
            Controller.showPopupMessage("There is already a broadcast at the " +
                    "specified time for this channel");
        } catch (ParseException | IllegalArgumentException e){
            Controller.showPopupMessage("Wrong formatted input");
        }

        if(broadcast != null){
            broadcastTableView.getItems().add(broadcast);
        }
    }
}


