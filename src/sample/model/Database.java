package sample.model;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;


import java.util.Date;


/*  Användarnamn: c5dv202_vt19_c17sal
    Databas:      c5dv202_vt19_c17sal
    Server:       postgres (postgres.cs.umu.se)
    Port:         5432 (standardport)
    Lösenord:     tRFonnU73mdF

    Hur man loggar in:

    psql -U c5dv202_vt19_c17sal -h postgres c5dv202_vt19_c17sal
 */
public class Database {

    private Connection connection;
    private Statement statement;

    private ArrayList<String> channelNames;
    private HashMap<Integer, String> categoryIdNames;
    private HashMap<String, Integer> channelNamesId;

    /**
     * Constructor: Will initialize the database
     */
    public Database() {
        getDriver();
        connectToDatabase();
        channelNames = new ArrayList<>();
        channelNamesId = new HashMap<>();
        categoryIdNames = new HashMap<>();
    }

    /**
     * Connects to the database
     */
    private void connectToDatabase() {
        try {
            String username = "c5dv202_vt19_c17sal";
            String password = "tRFonnU73mdF";
            String database = "jdbc:postgresql://postgres.cs.umu.se/";
            this.connection = DriverManager.getConnection(database, username, password);
            statement = connection.createStatement();
            //statement.executeUpdate("USE c5dv202_vt19_c17sal");
        } catch (SQLException e) {
            System.out.println("At connection "+e.getMessage());
        }
        System.out.println("Connected to database");
    }

    /**
     * Gets the driver
     */
    private void getDriver() {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            //System.out.println(e.getMessage());
        }
    }

    public ArrayList<String> getChannelNames() {
        return channelNames;
    }

    /**
     * Adds all current channel-names to the list and pairs every
     * channel-name with the corresponding channel-id in the hashmap.
     */
    public void setChannelNames(){
        try {
            statement = connection.createStatement();
            String query = "SELECT name, channel_id FROM channel";
            ResultSet resultSet = statement.executeQuery(query);
            while(resultSet.next()){
                String resString = resultSet.getString(1);
                int resInt = resultSet.getInt(2);
                channelNames.add(resString);
                channelNamesId.put(resString, resInt);
            }

            System.out.println(channelNames);
            System.out.println(channelNamesId.keySet());
            System.out.println(channelNamesId);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Adds all current channel-names to the list and pairs every
     * channel-name with the corresponding channel-id in the hashmap.
     */
    public void setCategoryPairs(){
        try {
            statement = connection.createStatement();
            String query = "SELECT name, category_id FROM category";
            ResultSet resultSet = statement.executeQuery(query);
            while(resultSet.next()){
                String resString = resultSet.getString(1);
                int resInt = resultSet.getInt(2);
                categoryIdNames.put(resInt, resString);
            }

            System.out.println(categoryIdNames);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public ObservableList<Program> getProgramsFromChannel(String channelName) {
        int channelId = channelNamesId.get(channelName);

        ObservableList<Program> programs = FXCollections.observableArrayList();

        try {
            statement = connection.createStatement();
            String query = "SELECT name, category, editor, tagline, email, " +
                    "url, program_id " +
                    "FROM program " +
                    "WHERE channel = " + channelId;

            ResultSet resultSet = statement.executeQuery(query);
            while(resultSet.next()){
                String name = resultSet.getString(1);
                int categoryId = resultSet.getInt(2);
                String category = categoryIdNames.get(categoryId);
                String editor = resultSet.getString(3);
                String tagline = resultSet.getString(4);
                String email = resultSet.getString(5);
                String url = resultSet.getString(6);
                int id = resultSet.getInt(7);

                Program p = new Program(name, category, editor, tagline, email, url, id);
                programs.add(p);

            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return programs;
    }

    public ObservableList<Broadcast> getBroadcastFromProgram(int programId, String programName){
        ObservableList<Broadcast> broadcasts = FXCollections.observableArrayList();
        try {
            statement = connection.createStatement();
            String query = "SELECT tagline, broadcast_date, duration, image_url, broadcast_id " +
                    "FROM broadcast " +
                    "WHERE program = " + programId;
            ResultSet resultSet = statement.executeQuery(query);
            while(resultSet.next()){
                String tagline = resultSet.getString(1);
                Date date = resultSet.getTimestamp(2);
                int duration = resultSet.getInt(3);
                String url = resultSet.getString(4);
                int id = resultSet.getInt(5);

                Broadcast b = new Broadcast(programName, tagline, date, duration, url, id);
                broadcasts.add(b);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return broadcasts;
    }
}
