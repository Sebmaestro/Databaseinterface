package sample.model;

import java.sql.*;

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

    /**
     * Constructor: Will initialize the database
     */
    public Database() {
        getDriver();
        connectToDatabase();
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

    public void getSomething() {
        String sqlSelect = "SELECT name FROM program";
        try {
            ResultSet rs = statement.executeQuery(sqlSelect);
            while(rs.next()) {
                System.out.println(rs.getString("name"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
