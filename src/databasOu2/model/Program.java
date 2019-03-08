package databasOu2.model;

/**
 * Holds all the info for program
 */
public class Program {

    private String name;
    private String category;
    private String editor;
    private String tagline;
    private String email;
    private String url;
    private int id;

    /**
     * Constructor
     * @param name name
     * @param category category
     * @param editor editor
     * @param tagline big
     * @param email @snabelA
     * @param url bild
     * @param id id
     */
    public Program(String name, String category, String editor, String tagline, String email, String url, int id) {
        this.id = id;
        this.name = name;
        this.category = category;
        this.editor = editor;
        this.tagline = tagline;
        this.email = email;
        this.url = url;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEditor() {
        return editor;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setEditor(String editor) {
        this.editor = editor;
    }

    @Override
    public String toString() {
        return "Name: " + name + " Category: " + category + " Editor: " +
                editor + " hej";
    }
}
