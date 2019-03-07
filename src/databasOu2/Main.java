package databasOu2;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        //FXMLLoader loader = new FXMLLoader(getClass().getResource("main.fxml"));

        // You first need to create a reference to your controller
        //Controller controller = new Controller();
        //loader.setController(controller);

        //Parent root = loader.load();

        // Now call the setter from the Controller.java file:
        //controller.setLowTextField("This is The Console");




        Parent root = FXMLLoader.load(getClass().getResource("view/main.fxml"));
        primaryStage.setTitle("Galle");
        primaryStage.setScene(new Scene(root, 1000, 1000));
        primaryStage.show();
    }


    public static void main(String[] args) {
        System.out.println("galle");
        launch(args);
        //new Controller();
    }
}
