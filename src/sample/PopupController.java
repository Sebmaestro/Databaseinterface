package sample;

import javafx.collections.ObservableList;
import sample.model.Broadcast;

public class PopupController {


    private ObservableList<Broadcast> broadcasts;

    public PopupController(){
        System.out.println("started");

    }


    public void setBroadcasts(ObservableList<Broadcast> broadcasts) {
        this.broadcasts = broadcasts;
        for (Broadcast b: this.broadcasts) {
            System.out.println(b);
        }
    }

}
