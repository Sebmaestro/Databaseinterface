package databasOu2.model;

/**
 * Holds data for broadcast
 */
public class Broadcast {
    private String programName;
    private String tagline;
    private String date;
    private String duration;
    private String imageURL;
    private int id;

    /**
     * Constructor
     * @param programName - name
     * @param tagline - tagline
     * @param dateString - date
     * @param durationString - duration
     * @param imageURL - image
     * @param id - id
     */
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

    public int getId() {
        return id;
    }

    public String getDate() {
        return date;
    }

    public String getDuration() {
        return duration;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }
}
