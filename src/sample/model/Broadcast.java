package sample.model;


import java.util.Date;

public class Broadcast {
    private String programName;
    private String tagline;
    private Date date;
    private int duration;
    private String imageURL;
    private int id;

    public Broadcast(String programName, String tagline, Date date, int duration, String imageURL, int id) {
        this.programName = programName;
        this.tagline = tagline;
        this.date = date;
        this.duration = duration;
        this.imageURL = imageURL;
        this.id = id;
    }


    @Override
    public String toString() {
        return "Programname: " + programName + " Date: " + date + " Tagline: " + tagline;
    }
}
