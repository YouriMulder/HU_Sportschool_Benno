package mail;

import java.util.ArrayList;
import java.util.Properties;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.PasswordAuthentication;

import database.databaseManagement;


public class sendMail {

    final static String EmailUser = "sportschoolbenno@hotmail.com";
    final static String EmailPassword = "SBGroup4";

    // sends a e-mail to user when account is created
    public static void newAccount(String emailTo, String username, String name, String gender, String postalCode, String houseNumber) throws Exception {

            Properties props =  new Properties();
            props.put("mail.smtp.host", "smtp-mail.outlook.com");
            props.put("mail.smtp.port", "587");
            props.put("mail.smtp.ssl.trust", "smtp-mail.outlook.com");
            props.put("mail.smtp.starttls.enable", "true");
            props.put("mail.smtp.auth", "true");
            props.put("mail.smtp.connectiontimeout", "10000");

            Session session = Session.getInstance(props, new javax.mail.Authenticator() {
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(
                            EmailUser,EmailPassword);
                }
            });

            session.setDebug(true);
            InternetAddress fromAddress = new InternetAddress("sportschoolbenno@hotmail.com","Benno Bakker");
            InternetAddress toAddress = new InternetAddress(emailTo);
            String msgSubject = "Benno's Sportschool - Account aangemaakt";
            String msgBody = "Uw account is aangemaakt.\n\n" +
                    "Log nu in om een abonnement af te sluiten.\n" +
                    "Jouw gegevens:\n" +
                    "E-mail: " + emailTo + "\n" +
                    "Username: " + username + "\n" +
                    "Naam: " + name + "\n" +
                    "Geslacht: " + gender + "\n" +
                    "Postcode: " + postalCode + "\n" +
                    "Huisnummer: " + houseNumber + "\n\n" +
                    "Bij vragen kunt u ons bereiken via onze e-mail: SportschoolBenno@hotmail.com\n\n" +
                    "Wij hopen u snel te zien bij de sportschool! Veel sport plezier!";

            Message msg = new MimeMessage(session);
            msg.setFrom(fromAddress);
            msg.addRecipient(Message.RecipientType.TO,toAddress);
            msg.setSubject(msgSubject);
            msg.setText(msgBody);

            Transport transport = session.getTransport("smtp");
            transport.connect();
            transport.sendMessage(msg, msg.getAllRecipients());
    }

    // sends a e-mail to user when account is created
    public static void bennoNewAccount(String emailTo, String username, String name, String gender, String postalCode, String houseNumber) throws Exception {

        Properties props =  new Properties();
        props.put("mail.smtp.host", "smtp-mail.outlook.com");
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.ssl.trust", "smtp-mail.outlook.com");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.connectiontimeout", "10000");

        Session session = Session.getInstance(props, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(
                        EmailUser,EmailPassword);
            }
        });

        session.setDebug(true);
        InternetAddress fromAddress = new InternetAddress("sportschoolbenno@hotmail.com","Benno Bakker");
        InternetAddress toAddress = new InternetAddress("sportschoolbenno@hotmail.com");
        String msgSubject = "Benno's Sportschool - Account aangemaakt";
        String msgBody = "Er is een nieuw account gemaakt.\n\n" +
                "De gebruiker heeft de volgende gegevens gebruikt om het account aan te maken.\n" +
                "Jouw gegevens:\n" +
                "E-mail: " + emailTo + "\n" +
                "Username: " + username + "\n" +
                "Naam: " + name + "\n" +
                "Geslacht: " + gender + "\n" +
                "Postcode: " + postalCode + "\n" +
                "Huisnummer: " + houseNumber + "\n\n";

        Message msg = new MimeMessage(session);
        msg.setFrom(fromAddress);
        msg.addRecipient(Message.RecipientType.TO,toAddress);
        msg.setSubject(msgSubject);
        msg.setText(msgBody);

        Transport transport = session.getTransport("smtp");
        transport.connect();
        transport.sendMessage(msg, msg.getAllRecipients());
    }

    // sends a e-mail to user when account is created
    public static void newAbonnement(String username, int abonnementID) throws Exception {
        // gets the abonnement_id for getting the subscription row
        ArrayList<String> accountRow = databaseManagement.getKlantenRow(username,0,0,0);
        String abonnement_id = accountRow.get(8);
        // gets subscription row
        ArrayList<String> subscriptionRow = databaseManagement.getSubscriptionRow(Integer.parseInt(abonnement_id));
        String abonnement_vorm_id = subscriptionRow.get(4);
        // gets the variables to send to the user
        ArrayList<String> defaultSubscriptionRow = databaseManagement.getDefaultSubscriptionRow(abonnement_vorm_id);
        String abonnementsvorm_id = defaultSubscriptionRow.get(0);
        String abonnementsnaam = defaultSubscriptionRow.get(1);
        String beschrijving = defaultSubscriptionRow.get(2);
        String prijs = defaultSubscriptionRow.get(3);

        String emailTo = databaseManagement.getEMail(username);

        Properties props =  new Properties();
        props.put("mail.smtp.host", "smtp-mail.outlook.com");
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.ssl.trust", "smtp-mail.outlook.com");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.connectiontimeout", "10000");

        Session session = Session.getInstance(props, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(
                        EmailUser,EmailPassword);
            }
        });

        session.setDebug(true);
        InternetAddress fromAddress = new InternetAddress("sportschoolbenno@hotmail.com","Benno Bakker");
        InternetAddress toAddress = new InternetAddress(emailTo);
        String msgSubject = "Benno's Sportschool - Account aangemaakt";
        String msgBody = "Je hebt een nieuw abonnement afgesloten.\n" +
                "Je abonnementgegevens:\n" +
                "Abonnement ID: " + abonnementsvorm_id + "\n" +
                "Abonnementsnaam: " + abonnementsnaam + "\n" +
                "Beschrijving: " + beschrijving + "\n" +
                "Prijs per maand: " + prijs + "\n";

        Message msg = new MimeMessage(session);
        msg.setFrom(fromAddress);
        msg.addRecipient(Message.RecipientType.TO,toAddress);
        msg.setSubject(msgSubject);
        msg.setText(msgBody);

        Transport transport = session.getTransport("smtp");
        transport.connect();
        transport.sendMessage(msg, msg.getAllRecipients());
    }



    public static void main(String[] args){
    }
}