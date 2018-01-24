package controllers;

import database.databaseManagement;

import java.util.ArrayList;

public class loginDetails {
    // all the tables in arraylist
    private static ArrayList<String> klanten;


    private static String username = null;

    public static void init() throws Exception {

        setKlantenInformation();
    }

    public static void setCurrentUsername(String usernameInput) {
        username = usernameInput;
    }

    public static String getCurrentUsername() {
        return username;
    }

    public static void setKlantenInformation() throws Exception{
        klanten = databaseManagement.getKlantenTable(getCurrentUsername(),0,0,0);
    }

    public static ArrayList<String> getKlantenInformation() {
        return klanten;
    }
}
