package sample.model;

public class Program {

    private String name;
    private String category;
    private String editor;
    private String tagline;
    private String email;
    private String url;
    private int id;


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

    public String getCategory() {
        return category;
    }

    public String getEditor() {
        return editor;
    }

    public String getTagline() {
        return tagline;
    }

    public String getEmail() {
        return email;
    }

    public String getUrl() {
        return url;
    }

    @Override
    public String toString() {
        return "Name: " + name + " Category: " + category + " Editor: " +
                editor + " hej";

    }
}
