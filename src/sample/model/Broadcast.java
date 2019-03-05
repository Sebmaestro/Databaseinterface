package sample.model;


public class Broadcast {
    private String programName;
    private String tagline;
    private String date;
    private String duration;
    private String imageURL;
    private int id;

    public Broadcast(String programName, String tagline, String dateString, String durationString, String imageURL, int id) {
        this.programName = programName;
        this.tagline = tagline;
        this.date = dateString;
        this.duration = durationString;
        this.imageURL = imageURL;
        this.id = id;
    }


    @Override
    public String toString() {
        return "Programname: " + programName + " Date: " + date + " Duration: " + duration;
    }

    public String getDate() {
        return date;
    }

    public String getDuration() {
        return duration;
    }
}
