package plantenApp.java.model;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import javax.swing.*;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.regex.Pattern;

/**
 * @author Bart Maes
 * generieke methodes die over verschillende login schermen gebruikt worden
 */

public class LoginMethods {

    public static Gebruiker userLoggedIn; // ingelogde gebruiker

    private static Object[] options = {"Ja", "Nee"};

    /**
     * @param wachtwoord Wachtwoord om te hashen
     * @param salt       salt meegeven
     * @return hash van het wachtwoord : array van 80 bytes
     * @throws NoSuchAlgorithmException
     * @author Jasper, Bart Maes
     * @apiNote Versleuteling van het wachtwoord tot een hash met hash algoritme SHA-512 (= sterkste beschikbaar in Java)
     */

    public static byte[] HashFromPassword(String wachtwoord, byte[] salt) throws NoSuchAlgorithmException {
        // Salt = 16 willekeurige bytes
        // Bij elke nieuw wachtwoord wordt een salt aangemaakt
        // Hash = salt + resultaat hashfunctie(salt + password)

        //Everything in a Java program not explicitly set to something by the programmer, is initialized to a zero value.
        //For int/short/byte/long that is a 0.
        byte[] hash = new byte[0];

        try {
            // MessageDigest = hash functie
            MessageDigest md = MessageDigest.getInstance("SHA-512");
            md.update(salt); // salt toevoegen aan functie
            hash = md.digest(wachtwoord.getBytes(StandardCharsets.UTF_8)); // wachtwoord toevoegen aan functie
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        return hash;
    }

    /**
     * @author Bart Maes
     * laden van scherm in aparte methode
     */
    public static void loadScreen(AnchorPane pane, Class classpath, String screenName) {
        try {
            FXMLLoader loader = new FXMLLoader(classpath.getResource(screenName));
            Parent root = loader.load();

            Scene scene = new Scene(root);
            Stage window = (Stage) pane.getScene().getWindow();
            window.setScene(scene);
            window.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * @author Bart Maes
     * splitsen van bovenstaande methode in 3 methodes om nog specifieke bijkomende on load actions toe te voegen in controller
     */

    public static FXMLLoader getLoader(Class classpath, String screenName) {
        FXMLLoader loader = new FXMLLoader(classpath.getResource(screenName));
        return loader;
    }

    public static Parent getRoot(FXMLLoader loader) throws IOException {
        Parent root = loader.load();
        return root;
    }

    public static void getScreen(AnchorPane pane, Parent root) {
        try {
            Scene scene = new Scene(root);
            Stage window = (Stage) pane.getScene().getWindow();
            window.setScene(scene);
            window.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * @author Bart Maes
     * OptionDialog om zelf waardes mee te geven: Ja/Nee ipv Yes/No
     */

    public static void OptionDialog(String message, String title, AnchorPane pane, Class className, String screenNameYes, String screenNameNo) {
        //om zelf specifieke waardes te kunnen meegeven, werken we met Option Dialog ipv Confirm Dialog
        int dialogButton = JOptionPane.showOptionDialog(null,
                message, title, JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);

        //indien ze op 'Ja' klikken
        if (dialogButton == JOptionPane.YES_OPTION) {
            loadScreen(pane, className, screenNameYes);
        }

        //indien ze op 'No' klikken
        if (dialogButton == JOptionPane.NO_OPTION) {
            loadScreen(pane, className, screenNameNo);
        }
    }

    /**
     * @author Bart Maes
     * Emailadres valideren
     */
    public static boolean isValid(String email) {
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\." +
                "[a-zA-Z0-9_+&*-]+)*@" +
                "(?:[a-zA-Z0-9-]+\\.)+[a-z" +
                "A-Z]{2,7}$";

        Pattern pat = Pattern.compile(emailRegex);
        if (email == null)
            return false;
        return pat.matcher(email).matches();
    }

    /**
     * @author Bart Maes
     * Emailadres valideren
     */

    public static void checkEmail(TextField email, Label result) {
        email.focusedProperty().addListener((arg0, oldValue, newValue) -> {
            if (!newValue) { //when focus lost
                if(!isValid(email.getText())) {
                    result.setText("Geen geldig emailadres");
                    email.setStyle("-fx-border-color: red;");
                    result.setTextFill(Color.RED);
                } else {
                    result.setText("");
                    email.setStyle("-fx-border-color: none;");
                }
            }

        });
    }

    /**
     * @author Bart Maes
     * Wachtwoord valideren
     */
    public static void checkPassword(TextField ww, Label result) {
        ww.focusedProperty().addListener((arg0, oldValue, newValue) -> {
            if (!newValue) { //when focus lost
                if(!validateWachtwoord(ww.getText())) {
                    result.setText("Het wachtwoord moet minstens 10 karakters lang zijn, \r\nminstens 1 hoofdletter, 1 kleine letter en 1 nummer bevatten.");
                    ww.setStyle("-fx-border-color: red;");
                    result.setTextFill(Color.RED);
                } else {
                    result.setText("");
                    ww.setStyle("-fx-border-color: none;");
                }
            }
        });
    }

    /** @author Matthias Vancoillie, Bart Maes (kleine aanpassingen)
     *  @Return controle wachtwoord
     *  Valideren van een wachtwoord
    Minstens 10 karakters
    Minstens 1 hoofdletter
    Minstens 1 kleine letter
    Minstens 1 nummer
     */
    public static boolean validateWachtwoord(String sWachtwoord) {
        boolean check;
        if (sWachtwoord.length() > 9) {
            check = checkPass(sWachtwoord);
        } else {
            check = false;
        }
        return check;
    }

    /**
     * @author Matthias Vancoillie
     */
    public static boolean checkPass(String sWachtwoord) {
        boolean hasNum = false;
        boolean hasCap = false;
        boolean hasLow = false;

        char c;

        for (int i = 0; i < sWachtwoord.length(); i++) {
            c = sWachtwoord.charAt(i);
            if (Character.isDigit(c)) {
                hasNum = true;
            } else if (Character.isUpperCase(c)) {
                hasCap = true;
            } else if (Character.isLowerCase(c)) {
                hasLow = true;
            }
        }
        return (hasNum && hasCap && hasLow);
    }
}
