package database;

// handy sql querries:
// ALTER SEQUENCE accounts_userID_seq RESTART WITH 1


import javax.sql.rowset.CachedRowSet;
import javax.xml.transform.Result;
import java.sql.*;
import java.util.ArrayList;

public class databaseManagement {

    private static String databaseAddress = "jdbc:mysql://192.168.42.2/sportschool";
    private static String databaseUser = "Youri";
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
    public static ArrayList<String> getKlantenRow(String usernameInput, int klantID, int accountID, int abonnementID) throws Exception {
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

    public static ArrayList<ArrayList> getSessiesRows(String usernameInput, int accountID) throws Exception {
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

    public static String getSessiesDurationCol(String usernameInput, int accountID) throws Exception {
        String average = "";

        Connection conn = getDatabaseConnection();
        PreparedStatement statement = conn.prepareStatement("");
        if (!usernameInput.equals("")) {
            statement = conn.prepareStatement("SELECT SEC_TO_TIME(AVG(TIME_TO_SEC(sessie_duur))) AS sessie_duur FROM sessies WHERE account_id=(SELECT account_id FROM accounts WHERE username ='" + usernameInput + "')" +
                    "AND MONTH(sessie_duur) = MONTH(CURRENT_DATE())" +
                    "AND YEAR(sessie_duur) = YEAR(CURRENT_DATE());");
        } else if (accountID != 0) {
            statement = conn.prepareStatement("SELECT SEC_TO_TIME(AVG(TIME_TO_SEC(sessie_duur))) AS sessie_duur FROM sessies WHERE account_id='" + accountID + "'" +
                    "AND MONTH(sessie_duur) = MONTH(CURRENT_DATE())" +
                    "AND YEAR(sessie_duur) = YEAR(CURRENT_DATE());");
        }

        ResultSet rs = statement.executeQuery();

        while (rs.next()) {
            average = rs.getString("sessie_duur");
        }

        disconnectDatabase(conn);
        return average;
    }

    public static ArrayList<String> getAccountRow(String usernameInput, int accountID) throws Exception {
        ArrayList<String> result = new ArrayList<>();

        Connection conn = getDatabaseConnection();
        PreparedStatement statement = conn.prepareStatement("");
        if (!usernameInput.equals("")) {
            statement = conn.prepareStatement("SELECT * FROM accounts WHERE username ='" + usernameInput + "';");
        } else if (accountID != 0) {
            statement = conn.prepareStatement("SELECT * FROM accounts WHERE account_id='" + accountID + "'");
        }

        ResultSet rs = statement.executeQuery();

        while (rs.next()) {
            String account_id = rs.getString("account_id");
            String username = rs.getString("username");
            String email = rs.getString("email");
            String passwd = rs.getString("passwd");
            String akkoord_voorwaarden = rs.getString("akkoord_voorwaarden");
            String klant_id = rs.getString("klant_id");

            result.add(account_id);
            result.add(username);
            result.add(email);
            result.add(passwd);
            result.add(akkoord_voorwaarden);
            result.add(klant_id);
        }

        disconnectDatabase(conn);
        return result;
    }

    public static ArrayList<String> getBegeleidersRow(String begeleiderID) throws Exception {
        ArrayList<String> result = new ArrayList<>(10);

        Connection conn = getDatabaseConnection();
        PreparedStatement statement = conn.prepareStatement("");

        statement = conn.prepareStatement("SELECT * FROM begeleiders WHERE begeleider_id='" + begeleiderID + "';");

        ResultSet rs = statement.executeQuery();

        while (rs.next()) {
            String begeleider_id = rs.getString("begeleider_id");
            String voornaam = rs.getString("voornaam");
            String tussenvoegsel = rs.getString("tussenvoegsel");
            String achternaam = rs.getString("achternaam");
            String geslacht = rs.getString("geslacht");
            String rol = rs.getString("rol");
            String specialisatie = rs.getString("specialisatie");
            String contract_start = rs.getString("contract_start_datum");
            String contract_eind = rs.getString("contract_eind_datum");


            result.add(begeleider_id);
            result.add(voornaam);
            result.add(tussenvoegsel);
            result.add(achternaam);
            result.add(geslacht);
            result.add(rol);
            result.add(specialisatie);
            result.add(contract_start);
            result.add(contract_eind);
        }

        disconnectDatabase(conn);
        return result;
    }

    public static ArrayList<ArrayList> getBegeleidersTable() throws Exception {
        ArrayList<ArrayList> result = new ArrayList<>();

        Connection conn = getDatabaseConnection();
        PreparedStatement statement = conn.prepareStatement("");

        statement = conn.prepareStatement("SELECT * FROM begeleiders;");

        ResultSet rs = statement.executeQuery();

        while (rs.next()) {
            ArrayList<String> innerList = new ArrayList<>();
            String begeleider_id = rs.getString("begeleider_id");
            String voornaam = rs.getString("voornaam");
            String tussenvoegsel = rs.getString("tussenvoegsel");
            String achternaam = rs.getString("achternaam");
            String geslacht = rs.getString("geslacht");
            String rol = rs.getString("rol");
            String specialisatie = rs.getString("specialisatie");
            String contract_start = rs.getString("contract_start_datum");
            String contract_eind = rs.getString("contract_eind_datum");


            innerList.add(begeleider_id);
            innerList.add(voornaam);
            innerList.add(tussenvoegsel);
            innerList.add(achternaam);
            innerList.add(geslacht);
            innerList.add(rol);
            innerList.add(specialisatie);
            innerList.add(contract_start);
            innerList.add(contract_eind);
            result.add(innerList);
        }

        disconnectDatabase(conn);
        return result;
    }

    public static void updateKlantPersonalTrainer(String usernameInput, int klantID, int begeleiderID) throws Exception {
        String querry;
        if (!usernameInput.equals("")) {
            querry = "UPDATE klanten set begeleider_id = '" + begeleiderID + "' WHERE klant_id =( SELECT klant_id FROM accounts WHERE username ='" + usernameInput + "');";
        } else {
            querry = "UPDATE klanten set begeleider_id = '" + begeleiderID + "' WHERE klant_id ='" + klantID + "';";
        }
        Connection conn = getDatabaseConnection();
        PreparedStatement preparedStatement = conn.prepareStatement(querry);
        preparedStatement.executeUpdate();
        disconnectDatabase(conn);
    }

    public static ArrayList<String> getSubscriptionRow(int abonnementID) throws Exception {
        ArrayList<String> result = new ArrayList<>(10);

        Connection conn = getDatabaseConnection();
        PreparedStatement statement = conn.prepareStatement("");

        statement = conn.prepareStatement("SELECT * FROM abonnementen WHERE abonnement_id='" + abonnementID + "';");

        ResultSet rs = statement.executeQuery();

        while (rs.next()) {
            String abonnement_id = rs.getString("abonnement_id");
            String akkoord_voorwaarden = rs.getString("akkoord_voorwaarden");
            String abonnement_start_datum = rs.getString("abonnement_start_datum");
            String abonnement_eind_datum = rs.getString("abonnement_eind_datum");
            String abonnementsvorm_id = rs.getString("abonnementsvorm_id");
            String account_id = rs.getString("account_id");


            result.add(abonnement_id);
            result.add(akkoord_voorwaarden);
            result.add(abonnement_start_datum);
            result.add(abonnement_eind_datum);
            result.add(abonnementsvorm_id);
            result.add(account_id);
        }
        System.out.println(result);
        disconnectDatabase(conn);
        return result;
    }

    public static ArrayList<ArrayList> getDefaultSubscriptionsTable() throws Exception {
        ArrayList<ArrayList> result = new ArrayList<>();

        Connection conn = getDatabaseConnection();
        PreparedStatement statement = conn.prepareStatement("");

        statement = conn.prepareStatement("SELECT * FROM abonnementsvormen");

        ResultSet rs = statement.executeQuery();

        while (rs.next()) {
            ArrayList<String> innerList = new ArrayList<>();
            String abonnementsvorm_id = rs.getString("abonnementsvorm_id");
            String abonnementsnaam = rs.getString("abonnementsnaam");
            String beschrijving = rs.getString("beschrijving");
            String prijs = rs.getString("prijs");

            innerList.add(abonnementsvorm_id);
            innerList.add(abonnementsnaam);
            innerList.add(beschrijving);
            innerList.add(prijs);

            result.add(innerList);
        }

        disconnectDatabase(conn);
        return result;
    }

    public static ArrayList<ArrayList> getPersonalAdviceTable(String username) throws Exception {
        ArrayList<ArrayList> result = new ArrayList<>();

        Connection conn = getDatabaseConnection();
        PreparedStatement statement = conn.prepareStatement("");

        statement = conn.prepareStatement("SELECT * FROM persoonlijk_advies WHERE klant_id = (SELECT klant_id FROM klanten where account_id = (SELECT account_id FROM accounts WHERE username = '" + username +"'));");

        ResultSet rs = statement.executeQuery();

        while (rs.next()) {
            ArrayList<String> innerList = new ArrayList<>();
            String persoonlijk_advies_id = rs.getString("persoonlijk_advies_id");
            String onderwerp = rs.getString("onderwerp");
            String advies = rs.getString("advies");
            String klant_id = rs.getString("klant_id");
            String begeleider_id = rs.getString("begeleider_id");

            innerList.add(persoonlijk_advies_id);
            innerList.add(onderwerp);
            innerList.add(advies);
            innerList.add(klant_id);
            innerList.add(begeleider_id);

            result.add(innerList);
        }

        disconnectDatabase(conn);
        return result;
    }

    public static void insertSubscription(String usernameInput, int abonnementsvormID) throws Exception {
        System.out.println("Adding subscription to user");
        // adding abonnement to abonnementen table
        String querry;
        querry = "INSERT INTO abonnementen (akkoord_voorwaarden, abonnement_start_datum, abonnement_eind_datum, abonnementsvorm_id, account_id)" +
                "VALUES(1, NOW(), DATE_ADD(NOW(), INTERVAL 1 YEAR), " + abonnementsvormID +", (SELECT account_id FROM accounts WHERE username='" + usernameInput +"'));";
        Connection conn = getDatabaseConnection();
        PreparedStatement preparedStatement = conn.prepareStatement(querry);
        preparedStatement.executeUpdate();
        disconnectDatabase(conn);
        System.out.println("added abonnement to abonnementen table");

        // updates abonnement_id to the klanten table
        querry = "UPDATE klanten set abonnement_id = (SELECT abonnement_id FROM abonnementen WHERE account_id = (SELECT account_id FROM accounts WHERE username='" + usernameInput +"')) WHERE account_id = (SELECT account_id FROM accounts WHERE username='" + usernameInput + "');";
        conn = getDatabaseConnection();
        preparedStatement = conn.prepareStatement(querry);
        preparedStatement.executeUpdate();
        disconnectDatabase(conn);
        System.out.println("Abonnement_id updated in the klanten table");
    }

    public static boolean checkSubscriptionExists(String username, int abonnementID) throws Exception {
        System.out.println("Checking if user has a subscription");
        Connection conn = getDatabaseConnection();
        PreparedStatement statement = conn.prepareStatement("");
        if (!username.equals("")) {
            statement = conn.prepareStatement("SELECT * FROM abonnementen WHERE account_id = (SELECT account_id FROM accounts WHERE username='" + username + "')");
        } else {
            statement = conn.prepareStatement("SELECT * FROM abonnementen WHERE abonnement_id ='"+ abonnementID + "'");
        }
        ResultSet rs = statement.executeQuery();

        if (rs.next()) {
            disconnectDatabase(conn);
            return true;
        } else {
            disconnectDatabase(conn);
            return false;
        }
    }

    public static void removeSubscriptionRow(String username) throws Exception{
        System.out.println("Deleting subscription");
        // adding abonnement to abonnementen table
        String querry;
        querry = "UPDATE klanten set abonnement_id = NULL WHERE account_id = (SELECT account_id FROM accounts WHERE username='" + username+ "');";
        Connection conn = getDatabaseConnection();
        PreparedStatement preparedStatement = conn.prepareStatement(querry);
        preparedStatement.executeUpdate();
        disconnectDatabase(conn);

        querry = "DELETE FROM abonnementen WHERE account_id = (SELECT account_id FROM accounts WHERE username = '" + username + "');";
        conn = getDatabaseConnection();
        preparedStatement = conn.prepareStatement(querry);
        preparedStatement.executeUpdate();
        disconnectDatabase(conn);
        System.out.println("Deleted subscription");
    }

    public static ArrayList<String> getDefaultSubscriptionRow(String abonnementsvormID) throws Exception {
        ArrayList<String> result = new ArrayList<>();

        Connection conn = getDatabaseConnection();
        PreparedStatement statement = conn.prepareStatement("");

        statement = conn.prepareStatement("SELECT * FROM abonnementsvormen WHERE abonnementsvorm_id='" + abonnementsvormID + "';");

        ResultSet rs = statement.executeQuery();

        while (rs.next()) {
            String abonnementsvorm_id = rs.getString("abonnementsvorm_id");
            String abonnementsnaam = rs.getString("abonnementsnaam");
            String beschrijving = rs.getString("beschrijving");
            String prijs = rs.getString("prijs");

            result.add(abonnementsvorm_id);
            result.add(abonnementsnaam);
            result.add(beschrijving);
            result.add(prijs);
        }

        disconnectDatabase(conn);
        return result;
    }

    public static boolean checkIBANExists(String IBAN) throws Exception {
        System.out.println("Checking IBAN is registered in database");
        Connection conn = getDatabaseConnection();
        PreparedStatement statement = conn.prepareStatement("");
        statement = conn.prepareStatement("SELECT * FROM betaal_gegevens WHERE IBAN = '" + IBAN + "'");

        ResultSet rs = statement.executeQuery();

        if (rs.next()) {
            System.out.println("IBAN already exists");
            disconnectDatabase(conn);
            return true;
        } else {
            System.out.println("IBAN doesn't exists yet");
            disconnectDatabase(conn);
            return false;
        }
    }

    public static void insertIBAN(String IBAN, String username) throws Exception {
        String querry;
        // only inserts the IBAN when it isn't in the database
        if (!checkIBANExists(IBAN)) {
            System.out.println("Inserting IBAN into database");
            // querry to insert IBAN into bank_gegevens
            querry = "INSERT INTO betaal_gegevens(IBAN)" +
                    "VALUES ('" + IBAN + "')";

            Connection conn = getDatabaseConnection();
            PreparedStatement preparedStatement = conn.prepareStatement(querry);
            preparedStatement.executeUpdate();
            disconnectDatabase(conn);
        }

        System.out.println("Updating betaal_gegevens_id");
        // querry to update customer betaal_gegevens_id
        querry =  "UPDATE klanten SET betaal_gegevens_id = (SELECT betaal_gegevens_id FROM betaal_gegevens WHERE IBAN = '" + IBAN + "') WHERE account_id = (SELECT account_id FROM accounts WHERE username='" + username + "')";

        Connection conn = getDatabaseConnection();
        PreparedStatement preparedStatement = conn.prepareStatement(querry);
        preparedStatement.executeUpdate();
        disconnectDatabase(conn);
        System.out.println("betaal_gegevens updated");
    }

    public static String getIBAN(String username) throws Exception {
        System.out.println("getting IBAN");
        Connection conn = getDatabaseConnection();
        PreparedStatement statement = conn.prepareStatement("");
        statement = conn.prepareStatement("SELECT IBAN FROM betaal_gegevens WHERE betaal_gegevens_id = (SELECT betaal_gegevens_id FROM klanten WHERE account_id = (SELECT account_id FROM accounts WHERE username = '" + username +"'))");

        ResultSet rs = statement.executeQuery();

        if (rs.next()) {
            String IBAN = rs.getString("IBAN");
            disconnectDatabase(conn);
            return IBAN;
        }

        disconnectDatabase(conn);
        return "";
    }

    public static String getEMail(String username) throws Exception {
        System.out.println("getting IBAN");
        Connection conn = getDatabaseConnection();
        PreparedStatement statement = conn.prepareStatement("");
        statement = conn.prepareStatement("SELECT email FROM accounts WHERE username ='" + username + "'");

        ResultSet rs = statement.executeQuery();

        if (rs.next()) {
            String email = rs.getString("email");
            disconnectDatabase(conn);
            return email;
        }

        disconnectDatabase(conn);
        return "";
    }

    public static void insertPersonalAdviceRow(int begeleiderID, String username, String onderwerp, String advies) throws Exception {
        System.out.println("Inserting personal advice");

        String querry = "INSERT INTO persoonlijk_advies (onderwerp, advies, klant_id, begeleider_id)" +
                "VALUES ('" + onderwerp + "', '" + advies + "', (SELECT klant_id FROM klanten WHERE account_id = (SELECT account_id FROM accounts WHERE username = '" + username +"')), '" + begeleiderID + "' )";

        Connection conn = getDatabaseConnection();
        PreparedStatement preparedStatement = conn.prepareStatement(querry);
        preparedStatement.executeUpdate();
        disconnectDatabase(conn);
    }


    public static void main(String args[]) throws Exception {
    }
}
