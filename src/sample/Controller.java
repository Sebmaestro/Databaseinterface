package sample;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.cell.PropertyValueFactory;
import sample.model.Broadcast;
import sample.model.Database;
import sample.model.Program;

import java.io.PrintStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class Controller {
    private Database database;

    @FXML private TextArea textArea;
    @FXML private Menu channels;
    @FXML private TableView<Program> tableView;
    @FXML private TableColumn<Program, String> programColumn;
    @FXML private TableColumn<Program, String> categoryColumn;
    @FXML private TableColumn<Program, String> editorColumn;


    public Controller() {
        database = new Database();
        database.setChannelNames();
        database.setCategoryPairs();

        ObservableList<Program> obList = database.getProgramsFromChannel("P3");

        for (Program p :obList) {
            System.out.println(p);
        }

        System.out.println("idiot");
        Program p = obList.get(2);

        ObservableList<Broadcast> obList2 = database.getBroadcastFromProgram(p.getId(), p.getName());

        for(Broadcast b : obList2){
            System.out.println(b);
        }
        System.out.println("Idiot2");
    }

    @FXML
    public void initMenu() {
        ArrayList<String> channelNames = database.getChannelNames();

        for (String s : channelNames) {
            MenuItem item = new MenuItem();
            item.setText(s);
            item.setOnAction(event -> { //Här är lamnda :)
                ObservableList<Program> programs = database
                        .getProgramsFromChannel(item.getText());


                programColumn.setCellValueFactory(new
                        PropertyValueFactory<>("name"));
                categoryColumn.setCellValueFactory(new
                        PropertyValueFactory<>("category"));
                editorColumn.setCellValueFactory(new
                        PropertyValueFactory<>("editor"));

                tableView.getItems().setAll(programs);

            });
            channels.getItems().add(item);
        }
    }


    @FXML
    public void setTextArea() {
        textArea.setText("Han dog");
    }
}
