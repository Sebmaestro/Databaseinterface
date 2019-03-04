package sample;

import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import sample.model.Database;

public class Controller {
    Database database;
    @FXML TextArea txta;

    public Controller() {
        database = new Database();
        database.setChannelNames();
        database.setCategoryPairs();
        System.out.println(database.getProgramsFromChannel("P3"));
    }


}
