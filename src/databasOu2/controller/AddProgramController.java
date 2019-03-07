package databasOu2.controller;

import databasOu2.model.Program;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import databasOu2.model.Database;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.ResourceBundle;

public class AddProgramController implements Initializable{
    Database database;

    @FXML
    private ComboBox<String> categoryCombo;
    @FXML
    private ComboBox<String> channelCombo;
    @FXML
    private TextField insertProgramTextField;
    @FXML
    private TextField insertEditorTextField;
    @FXML
    private Button addProgramButton;

    private TableView<Program> tableView;

    private HashMap<String, Integer> channelMap;
    private HashMap<String, Integer> categoryMap;
    private ArrayList<String> channelList;
    private ArrayList<String> categoryList;

    public AddProgramController() {
        database = new Database();
        database.setChannelNames();
        database.setCategoryPairs();
        channelMap = database.getChannelNamesId();
        categoryMap = database.getCategoryIdNames();
        channelList = database.getChannelNames();
        categoryList = database.getCategoryNames();

        System.out.println("Biggam");


    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ObservableList<String> programObservable = FXCollections.observableArrayList();
        ObservableList<String> categoryObservable = FXCollections
                .observableArrayList();

        programObservable.addAll(channelList);
        categoryObservable.addAll(categoryList);

        channelCombo.setItems(programObservable);
        categoryCombo.setItems(categoryObservable);
    }

    public void setTableView(TableView<Program> tableView){
        this.tableView = tableView;
    }

    public void setAddProgramButton() {
        String channel = channelCombo.getValue();
        String category = categoryCombo.getValue();

        int channelID = channelMap.get(channel);
        int categoryID = categoryMap.get(category);


        System.out.println("Tjena pontus");

        Program p = database.addProgram(channelID, categoryID,
                insertEditorTextField.getText(),
                insertProgramTextField.getText(), channel);
        tableView.getItems().add(p);
        Stage stage = (Stage) addProgramButton.getScene().getWindow();
        stage.close();
    }

    public void init() {

    }


}
