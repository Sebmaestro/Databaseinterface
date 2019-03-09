package databasOu2.controller;

import databasOu2.model.Database;
import databasOu2.model.Program;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.sql.SQLException;

/**
 * Controller for edit program
 */
public class EditProgramController {
    @FXML
    private TextField editorTextField;
    @FXML
    private TextField nameTextField;
    @FXML
    private ComboBox<String> categoryCombo;
    @FXML
    private Button editButton;
    @FXML
    private Label currentProgramLabel;

    private TableView<Program> programTableView;

    private Database database;

    private Program program;

    /**
     * Constructor
     */
    public EditProgramController() {
        try {
            database = new Database();
        } catch (ClassNotFoundException | SQLException e) {
            Controller.showPopupMessage("Database Error");
        }
        database.setChannelNames();
        database.setCategoryPairs();
    }

    /**
     * Edits the program when button is pressed
     */
    public void editPressed() {
        database.editProgram(program, nameTextField.getText(), categoryCombo
                .getValue(), editorTextField.getText());
        programTableView.refresh();
        Stage stage = (Stage) editButton.getScene().getWindow();
        stage.close();
    }

    /**
     * Gets program and inserts data in fields
     * @param program - program
     */
    public void setProgram(Program program) {
        this.program = program;

        currentProgramLabel.setText(program.getName());

        nameTextField.setText(program.getName());
        editorTextField.setText(program.getEditor());

    }

    /**
     * Gets the table
     * @param programTableView - table
     */
    public void setTable(TableView<Program> programTableView) {
        this.programTableView = programTableView;
    }

    /**
     * Gets categorylist and inserts in combobox
     * @param categoryList - list
     */
    public void setCategoryList(ObservableList<String> categoryList) {
        categoryCombo.getItems().setAll(categoryList);
    }
}
