package databasOu2.controller;

import databasOu2.model.Category;
import databasOu2.model.Database;
import databasOu2.model.Program;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

public class EditProgramController {
    @FXML
    private TextField editorTextField;
    @FXML
    private TextField nameTextField;
    @FXML
    private ComboBox<String> categoryCombo;
    @FXML
    private TextField currentProgramTextField;
    @FXML
    private Button editButton;

    private TableView<Program> programTableView;

    private Database database;

    private Program program;

    public EditProgramController() {
        database = new Database();
        database.setChannelNames();
        database.setCategoryPairs();
    }

    public void editPressed() {
        database.editProgram(program, nameTextField.getText(), categoryCombo
                .getValue(), editorTextField.getText());
        programTableView.refresh();
        Stage stage = (Stage) editButton.getScene().getWindow();
        stage.close();
    }

    public void setProgram(Program program) {
        this.program = program;

        currentProgramTextField.setText(program.getName());

        nameTextField.setText(program.getName());
        editorTextField.setText(program.getEditor());

    }

    public void setTable(TableView<Program> programTableView) {
        this.programTableView = programTableView;
    }

    public void setCategoryList(ObservableList<String> categoryList) {
        categoryCombo.getItems().setAll(categoryList);
    }
}
