package database;

// handy sql querries:
// ALTER SEQUENCE accounts_userID_seq RESTART WITH 1


import javax.sql.rowset.CachedRowSet;
import javax.xml.transform.Result;
import java.sql.*;
import java.util.ArrayList;

public class databaseManagement {

    private static String databaseAddress = "jdbc:mysql://192.168.1.27:3306/sportschool";
    private static String databaseUser = "root";
    private static String databasePassword = "MySQL123";

    // connects to the database and returns the connection
    private static Connection getDatabaseConnection() throws Exception {
        Connection conn = null;

        Class.forName("com.mysql.jdbc.Driver");
        conn = DriverManager.getConnection(databaseAddress, databaseUser, databasePassword);
        System.out.println("Connected to database");

        return conn;
    }

    // disconnects a connection to the database
    private static void disconnectDatabase(Connection conn) throws Exception {
            conn.close();
            System.out.println("Database connection closed\n");
    }

    public static String checkLoginData(String usernameInput, String passwdInput) throws Exception {
        // default return value
        String result = "Account niet gevonden";

        // setting the username to lower case, so it won't be case sensitive
        usernameInput = usernameInput.toLowerCase();

        Connection conn = getDatabaseConnection();
        PreparedStatement statement = conn.prepareStatement("SELECT * FROM accounts");
        ResultSet rs = statement.executeQuery();

        while (rs.next()) {
            String username = rs.getString("username").toLowerCase();
            if (usernameInput.equals(username)) {
                String passwd = rs.getString("passwd");
                if (passwdInput.equals(passwd)) {
                    disconnectDatabase(conn);
                    result = "Login gegevens correct";
                    return result;
                }
                result = "Password niet correct";
                return result;
            }
        }
        disconnectDatabase(conn);
        return result;
    }


    // registration functions
    public static boolean checkUsernameUsed(String usernameInput) throws Exception{
        // setting the username to lower case, so it won't be case sensitive
        usernameInput = usernameInput.toLowerCase();

        Connection conn = getDatabaseConnection();
        PreparedStatement statement = conn.prepareStatement("SELECT username FROM accounts");
        ResultSet rs = statement.executeQuery();

        while (rs.next()) {
            String username = rs.getString("username").toLowerCase();
            System.out.println(username);
            if (usernameInput.equals(username)) {
                System.out.println("Username already exists");
                disconnectDatabase(conn);
                return true;
            }
        }
        disconnectDatabase(conn);
        return false;
    }

    public static void registerAccount(String username, String passwd, String eMail, boolean akkoord_voowaarden) throws Exception {
        String querry;
        int akkoord;
        if (akkoord_voowaarden) {
            akkoord = 1;
        } else {
            akkoord = 0;
        }
        querry =  "INSERT INTO accounts(username, passwd, eMail, akkoord_voorwaarden)" +
                "VALUES ('" + username + "', '" + passwd + "', '" + eMail +"', '" + akkoord +"')";

        Connection conn = getDatabaseConnection();
        PreparedStatement preparedStatement = conn.prepareStatement(querry);
        preparedStatement.executeUpdate();
        disconnectDatabase(conn);
    }

    public static void registerCustomer(String voornaam, String tussenvoegsel, String achternaam, String geslacht, String postcode, String huisnummer, int account_id) throws Exception {
        String querry;

        // querry if there is a tussenvoegsel
        if (tussenvoegsel.equals("")) {
            querry =  "INSERT INTO klanten(voornaam , achternaam, geslacht, postcode, huisnummer, account_id)" +
                    "VALUES ('" + voornaam + "', '" + achternaam + "', 'Man', '" + postcode +"', '" + huisnummer + "', '" + account_id + "')";

        // querry is there is not a tussenvoegsel
        } else {
            querry =  "INSERT INTO klanten(voornaam, tussenvoegsel , achternaam, geslacht, postcode, huisnummer, account_id)" +
                    "VALUES ('" + voornaam + "', '" + tussenvoegsel + "', '" + achternaam + "', '" + geslacht + "', '" + postcode +"', '" + huisnummer + "', '" + account_id + "')";
        }

        Connection conn = getDatabaseConnection();
        PreparedStatement preparedStatement = conn.prepareStatement(querry);
        preparedStatement.executeUpdate();
        disconnectDatabase(conn);
    }

    public static int getAccountID(String usernameInput) throws Exception {
        int result = 0;
        Connection conn = getDatabaseConnection();
        PreparedStatement statement = conn.prepareStatement("SELECT account_id FROM accounts WHERE username='" + usernameInput + "'");
        ResultSet rs = statement.executeQuery();

        while (rs.next()) {
            result = rs.getInt("account_id");
            disconnectDatabase(conn);
            return result;
        }
        disconnectDatabase(conn);
        return result;
    }

    public static int getKlantID(int accountID) throws Exception {
        int result = 0;
        Connection conn = getDatabaseConnection();
        PreparedStatement statement = conn.prepareStatement("SELECT klant_id FROM accounts WHERE account_id='" + accountID + "'");
        ResultSet rs = statement.executeQuery();

        while (rs.next()) {
            result = rs.getInt("klant_id");
            disconnectDatabase(conn);
            return result;
        }
        disconnectDatabase(conn);
        return result;
    }

    public static void updateAccount_klantID(int accountID) throws Exception {
        String querry = "UPDATE accounts SET klant_id = (" +
                "SELECT klant_id FROM klanten WHERE account_id = " + accountID +")" +
                "WHERE account_id = " + accountID;

        Connection conn = getDatabaseConnection();
        PreparedStatement preparedStatement = conn.prepareStatement(querry);
        preparedStatement.executeUpdate();
        disconnectDatabase(conn);
    }

    // get tables
    public static ArrayList<String> getKlantenTable(String usernameInput, int klantID, int accountID, int abonnementID) throws Exception {
        ArrayList<String> result = new ArrayList<>(10);

        Connection conn = getDatabaseConnection();
        PreparedStatement statement = conn.prepareStatement("");
        if (!usernameInput.equals("")) {
            statement = conn.prepareStatement("SELECT * FROM klanten WHERE account_id=(SELECT account_id FROM accounts WHERE username ='" + usernameInput + "')");
        } else if (klantID != 0) {
            statement = conn.prepareStatement("SELECT * FROM klanten WHERE klant_id='" + klantID + "'");
        } else if (accountID != 0) {
            statement = conn.prepareStatement("SELECT * FROM klanten WHERE account_id='" + accountID + "'");
        } else if (abonnementID != 0) {
            statement = conn.prepareStatement("SELECT * FROM klanten WHERE abonnement_id='" + abonnementID + "'");
        }

        ResultSet rs = statement.executeQuery();

        while (rs.next()) {
            String klant_id = rs.getString("klant_id");
            String voornaam = rs.getString("voornaam");
            String tussenvoegsel = rs.getString("tussenvoegsel");
            String achternaam = rs.getString("achternaam");
            String geslacht = rs.getString("geslacht");
            String postcode = rs.getString("postcode");
            String huisnummer = rs.getString("huisnummer");
            String account_id = rs.getString("account_id");
            String abonnement_id = rs.getString("abonnement_id");
            String begeleider_id = rs.getString("begeleider_id");

            result.add(klant_id);
            result.add(voornaam);
            result.add(tussenvoegsel);
            result.add(achternaam);
            result.add(geslacht);
            result.add(postcode);
            result.add(huisnummer);
            result.add(account_id);
            result.add(abonnement_id);
            result.add(begeleider_id);
        }

        disconnectDatabase(conn);
        return result;
    }

    public static ArrayList<ArrayList> getSessiesTable(String usernameInput, int accountID) throws Exception {
        ArrayList<ArrayList> result = new ArrayList<>();

        Connection conn = getDatabaseConnection();
        PreparedStatement statement = conn.prepareStatement("");
        if (!usernameInput.equals("")) {
            statement = conn.prepareStatement("SELECT * FROM sessies WHERE account_id=(SELECT account_id FROM accounts WHERE username ='" + usernameInput + "')");
        } else if (accountID != 0) {
            statement = conn.prepareStatement("SELECT * FROM sessies WHERE account_id='" + accountID + "'");
        }

        ResultSet rs = statement.executeQuery();

        while (rs.next()) {
            ArrayList<String> innerList = new ArrayList<>();
            String sessie_id = rs.getString("sessie_id");
            String incheck_tijd = rs.getString("incheck_tijd");
            String uitcheck_tijd = rs.getString("uitcheck_tijd");
            String sessie_duur = rs.getString("sessie_duur");
            String tag_id = rs.getString("tag_id");
            String account_id = rs.getString("account_id");

            innerList.add(sessie_id);
            innerList.add(incheck_tijd);
            innerList.add(uitcheck_tijd);
            innerList.add(sessie_duur);
            innerList.add(tag_id);
            innerList.add(account_id);

            result.add(innerList);
        }

        disconnectDatabase(conn);
        return result;
    }

    public static void main(String args[]) throws Exception {
        System.out.println(getSessiesTable("Youri", 0));
    }
}
