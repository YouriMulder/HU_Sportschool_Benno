package database;

// handy sql querries:
// ALTER SEQUENCE accounts_userID_seq RESTART WITH 1


import javax.xml.transform.Result;
import java.sql.*;

public class databaseManagement {

    private static String databaseAddress = "jdbc:postgresql://localhost/AccountManagement";
    private static String databaseUser = "postgres";
    private static String databasePassword = "Postgres123";

    // connects to the database and returns the connection
    private static Connection getDatabaseConnection() {
        // TODO fix the WARNING (Illegal reflective access by org.postgresql.jdbc.TimestampUtils)

        Connection conn = null;

        try {
            Class.forName("org.postgresql.Driver");
            conn = DriverManager.getConnection(databaseAddress, databaseUser, databasePassword);
            System.out.println("Connected to database");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return conn;
    }

    // disconnects a connection to the database
    private static void disconnectDatabase(Connection conn) {
        try {
            conn.close();
            System.out.println("Database connection closed\n");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // executes a querry in the database to return the given values
    private static ResultSet getDatabaseData(String SQLQuerry) {
        Connection conn = getDatabaseConnection();
        ResultSet rs = null;

        try {
            Statement stmt = conn.createStatement();
            rs = stmt.executeQuery(SQLQuerry);
            System.out.println("Fetched data from database");
            disconnectDatabase(conn);
        } catch(Exception e) {
            e.printStackTrace();
        }
        return rs;
    }

    // creates a account in the database by creating a new row in the table accounts
    private static boolean createDatabaseAccount(String usernameInput, String passwdInput) {
        Connection conn = getDatabaseConnection();
        try {
            Statement stmt = conn.createStatement();
            stmt.executeUpdate("INSERT INTO accounts (username, passwd) VALUES('" + usernameInput + "', '" + passwdInput + "');");
            System.out.println("Account successfully created");
            System.out.println("Next time try to login using your username(" + usernameInput + ")");
            disconnectDatabase(conn);
        } catch(Exception e) {
            e.printStackTrace();
            //return false;
        }
        return true;
    }

    // checks if the user input is a registered account
    public static String checkLoginData(String usernameInput, String passwdInput) {
        ResultSet rs = getDatabaseData("SELECT * FROM accounts");

        // default return value
        String checkMessage = "Incorrect username";
        try {
            // executes the loop with every row
            while (rs.next()) {
                String username = rs.getString("username");
                String passwd = rs.getString("passwd");

                // checks if user input is the same as a table
                if (username.equals(usernameInput)) {
                    if (passwd.equals(passwdInput)){
                        checkMessage = "Login details correct";
                        return checkMessage;
                    }
                    else {
                        checkMessage = "Incorrect password";
                        return checkMessage;
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return checkMessage;
    }


    // checks if the username is in the database
    private static boolean checkUsernameUsed(String usernameInput) {
        ResultSet rs = getDatabaseData("SELECT * FROM accounts");

        try {
            // executes the loop with every row
            while (rs.next()) {
                String username = rs.getString("username");

                // checks if current username is the same as the input
                if (usernameInput.equals(username)) {
                    return true;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public static void main(String args[]) {}
}
