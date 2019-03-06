package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import sample.model.Database;

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



        /*
        for (String s:channelMap) {
            channelCombo.getItems().add(s);
        }
        */

        //categoryCombo.getItems().addAll(categoryMap.)
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ObservableList<String> programObservable = FXCollections.observableArrayList();
        ObservableList<String> categoryObservable = FXCollections
                .observableArrayList();

        programObservable.addAll(channelList);
        categoryObservable.addAll(categoryList);

        /*
        for (int i: categoryMap.keySet()) {
            categoryObservable.add(categoryMap.get(i));
        }
        */

        channelCombo.setItems(programObservable);
        categoryCombo.setItems(categoryObservable);
    }

    public void setAddProgramButton() {
        String channel = channelCombo.getValue();
        String category = categoryCombo.getValue();

        int channelID = channelMap.get(channel);
        int categoryID = categoryMap.get(category);


        System.out.println("Tjena pontus");

        database.addProgram(channelID, categoryID, insertEditorTextField
                .getText(), insertProgramTextField.getText(), channel);
    }

    public void init() {

    }


}
