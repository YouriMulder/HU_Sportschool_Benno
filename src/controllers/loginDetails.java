package controllers;

import database.databaseManagement;

import java.util.ArrayList;

public class loginDetails {
    /* This class is used for keeping track of the current username/user
        When de user logs in the String username == the current user
    */
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
        klanten = databaseManagement.getKlantenRow(getCurrentUsername(),0,0,0);
    }

    public static ArrayList<String> getKlantenInformation() {
        return klanten;
    }
}
