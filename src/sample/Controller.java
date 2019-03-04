package sample;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import sample.model.Database;

import java.io.PrintStream;
import java.net.URL;
import java.util.ResourceBundle;

public class Controller {
    private Database database;
    @FXML private TextArea textArea;


    public Controller() {
        database = new Database();
        database.setChannelNames();
        database.setCategoryPairs();
        System.out.println(database.getProgramsFromChannel("P3"));
        System.out.println("tjena");
        System.out.println("bong");
        System.out.println("big");
    }

    @FXML
    public void setTextArea() {
        textArea.setText("Han dog");
    }
}
