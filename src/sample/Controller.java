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
        //database.getSomething();
        //textArea.appendText("big");
        //bacon = new TextArea();

    }

    @FXML
    public void setTextArea() {
        textArea.setText("Han dog");
    }
}
