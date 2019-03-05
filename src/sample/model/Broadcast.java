package sample.model;


public class Broadcast {
    private String programName;
    private String tagline;
    private String date;
    private int duration;
    private String imageURL;
    private int id;

    public Broadcast(String programName, String tagline, String dateString, int duration, String imageURL, int id) {
        this.programName = programName;
        this.tagline = tagline;
        this.date = dateString;
        this.duration = duration;
        this.imageURL = imageURL;
        this.id = id;
    }


    @Override
    public String toString() {
        return "Programname: " + programName + " Date: " + date + " Tagline: " + tagline;
    }

    public String getDate() {
        return date;
    }

    public int getDuration() {
        return duration;
    }
}
