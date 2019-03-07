package databasOu2.model;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
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
    private ArrayList<String> categoryNames;

    private HashMap<Integer, String> categoryIdNames;
    private HashMap<String, Integer> channelNamesId;

    private HashMap<String, Integer> sebbesHashMap;

    /**
     * Constructor: Will initialize the database
     */
    public Database() {
        getDriver();
        connectToDatabase();
        channelNames = new ArrayList<>();
        categoryNames = new ArrayList<>();
        channelNamesId = new HashMap<>();
        categoryIdNames = new HashMap<>();
        sebbesHashMap = new HashMap<>();
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

    public ArrayList<String> getCategoryNames() {
        return categoryNames;
    }

    public HashMap<String, Integer> getCategoryIdNames() {
        return sebbesHashMap;
    }

    public HashMap<String, Integer> getChannelNamesId() {
        return channelNamesId;
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
                categoryNames.add(resString);
                categoryIdNames.put(resInt, resString);
                sebbesHashMap.put(resString, resInt);
                int i = 5;
            }

            System.out.println(categoryIdNames);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     *
     * @param channelName
     * @return
     */
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

    /**
     *
     * @param programId
     * @param programName
     * @return
     */
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

                Timestamp timestamp = resultSet.getTimestamp(2);
                SimpleDateFormat formatter = new SimpleDateFormat("YYYY-MM-dd HH:mm:ss");
                String dateString = formatter.format(timestamp);

                long durationTime = resultSet.getInt(3) * 1000;
                formatter = new SimpleDateFormat("HH:mm:ss");
                formatter.setTimeZone(TimeZone.getTimeZone("GMT"));

                String duration = formatter.format(new Date(durationTime));
                String url = resultSet.getString(4);
                int id = resultSet.getInt(5);

                Broadcast b = new Broadcast(programName, tagline, dateString, duration, url, id);
                broadcasts.add(b);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return broadcasts;
    }

    public Program addProgram(int channelxd, int categoryxd, String editorxd,
                           String namexd, String channel){
        Program program = null;
        try {
            int primaryKey = 0;
            statement = connection.createStatement();

            String selectQuery = "SELECT MAX(p.program_id)" +
            "FROM program p";

            ResultSet resultSet = statement.executeQuery(selectQuery);

            while(resultSet.next()) {
                primaryKey = resultSet.getInt(1);
            }

            String insertQuery = "INSERT INTO program(program_id, name, editor, " +
                    "channel, category)" +
                    " VALUES(?,?,?,?,?)";

            connection.setAutoCommit(false);
            PreparedStatement p = connection.prepareStatement(insertQuery);
            p.setInt(1, primaryKey+1);
            p.setString(2, namexd);
            p.setString(3, editorxd);
            p.setInt(4, channelxd);
            p.setInt(5, categoryxd);
            p.executeUpdate();
            connection.commit();
            program = new Program(namexd, categoryIdNames.get(categoryxd),
                    editorxd, null, null, null, primaryKey+1);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return program;
    }

    public void deleteProgramAndBroadcasts(int programID) {
        try {
            statement = connection.createStatement();

            String alterQuery =
                    "ALTER TABLE program DROP CONSTRAINT IF EXISTS delete_all" +
                    "ADD CONSTRAINT delete_all" +
                    "FOREIGN KEY (program) REFERENCES program(program_id)"+
                    "ON DELETE CASCADE";

            statement.executeUpdate(alterQuery);

            String deleteQuery =
                    "DELETE FROM program" +
                            "WHERE program_id = "+programID;

            statement.executeUpdate(deleteQuery);

        } catch (SQLException e) {
            e.printStackTrace();
        }


    }

    public void deleteOnlyBroadcast() {



    }


    public Broadcast addBroadcast(Program program, String tagline, String
            starttime,
                             String durationString, String image_url) {
        Broadcast b = null;
        int primaryKey = 0;
        try {

            statement = connection.createStatement();

            String selectQuery = "SELECT MAX(b.broadcast_id)" +
                    "FROM broadcast b";

            ResultSet resultSet = statement.executeQuery(selectQuery);

            while (resultSet.next()) {
                primaryKey = resultSet.getInt(1);
            }
            Timestamp broadcast_date = Timestamp.valueOf(starttime);

            SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss");
            formatter.setTimeZone(TimeZone.getTimeZone("GMT"));
            Date durationDate = formatter.parse(durationString);
            int duration = (int) durationDate.getTime() / 1000;

            String insertQuery = "INSERT INTO broadcast(broadcast_id, program, " +
                    "tagline, broadcast_date, duration, image_url)" +
                    " VALUES(?,?,?,?,?,?)";

            connection.setAutoCommit(false);
            PreparedStatement p = connection.prepareStatement(insertQuery);
            p.setInt(1, primaryKey + 1);
            p.setInt(2, program.getId());
            p.setString(3, tagline);
            p.setTimestamp(4, broadcast_date);
            p.setInt(5, duration);
            p.setString(6, image_url);
            p.executeUpdate();
            connection.commit();
            b = new Broadcast(program.getName(), tagline, starttime, durationString,
                    image_url, primaryKey+1);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return b;
    }


    public void createTriggerFunction(){
        try {
            statement = connection.createStatement();
            String query = "DROP TRIGGER IF EXISTS broadcast_trigger ON broadcast";
            statement.executeUpdate(query);



            statement = connection.createStatement();
            query = "CREATE OR REPLACE FUNCTION broadcast_trigger_func() " +
                    "RETURNS trigger AS " +
                    "$func$" +
                    "BEGIN " +
                    "IF ((SELECT COUNT(broadcast_id) " +
                    "FROM program JOIN broadcast ON program.program_id = broadcast.program " +
                    "WHERE (program.channel = " +
                    "(SELECT channel " +
                    "FROM program " +
                    "WHERE program_id = NEW.program) AND " +
                    "broadcast_date <= NEW.broadcast_date AND " +
                    "broadcast_date + INTERVAL '1 seconds' * duration > NEW.broadcast_date)" +
                    ") > 0) THEN RAISE EXCEPTION 'Invalid date';" +
                    "END IF;" +
                    "IF ((SELECT COUNT(broadcast_id) " +
                    "FROM program JOIN broadcast ON program.program_id = broadcast.program " +
                    "WHERE (program.channel = " +
                    "(SELECT channel " +
                    "FROM program " +
                    "WHERE program_id = NEW.program) AND " +
                    "broadcast_date < NEW.broadcast_date + INTERVAL '1 seconds' * NEW.duration AND " +
                    "broadcast_date >= NEW.broadcast_date)" +
                    ") > 0) THEN RAISE EXCEPTION 'Invalid date2';" +
                    "END IF;" +
                    "RETURN NEW;" +
                    "END " +
                    "$func$ LANGUAGE plpgsql";
            statement.executeUpdate(query);

            System.out.println("xd");

            statement= connection.createStatement();
            query = "CREATE TRIGGER broadcast_trigger " +
                    "BEFORE INSERT ON broadcast " +
                    "FOR EACH ROW EXECUTE PROCEDURE broadcast_trigger_func()";
            statement.executeUpdate(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }


    }

    public void dropTrigger(){
        try {
            statement = connection.createStatement();
            String query = "DROP TRIGGER IF EXISTS broadcast_trigger ON broadcast";
            statement.executeUpdate(query);


            statement = connection.createStatement();
            query = "DROP FUNCTION IF EXISTS broadcast_trigger_func()";
            statement.executeUpdate(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
