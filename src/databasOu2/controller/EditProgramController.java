package databasOu2.controller;

import databasOu2.model.Program;
import javafx.scene.control.Alert;
import javafx.scene.control.TableView;

public class EditProgramController {


    private TableView<Program> programTableView;

/*
    public void galle() {
        Program program = tableView.getSelectionModel().getSelectedItem();
        System.out.println(tableView.getSelectionModel().getSelectedItem());
        if (program == null) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("Please choose a program before editing");
            alert.showAndWait();
        } else {
            database.editProgram(program);
            //tableView.getItems().remove(program);
        }
    }*/

    public void setTable(TableView<Program> programTableView) {
        this.programTableView = programTableView;
    }
}
