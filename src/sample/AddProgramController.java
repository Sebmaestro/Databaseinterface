package sample;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import sample.model.Database;

import java.util.ArrayList;
import java.util.HashMap;

public class AddProgramController {
    Database database;

    @FXML
    private ComboBox<?> categoryCombo;
    @FXML
    private ComboBox<?> channelCombo;
    @FXML
    private TextField insertProgramTextField;
    @FXML
    private Button addProgramButton;

    private ArrayList<String> channelList;
    private HashMap<Integer, String> categoryList;

    public AddProgramController() {
        database = new Database();
        channelList = database.getChannelNames();
        categoryList = database.getCategorys();

        /*
        for (String s:channelList) {
            channelCombo.getItems().add(s);
        }
        */

        //categoryCombo.getItems().addAll(categoryList.)
    }

}
