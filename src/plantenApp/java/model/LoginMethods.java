package plantenApp.java.model;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * @author Bart Maes
 * generieke methodes die over verschillende login schermen gebruikt worden
 */

public class LoginMethods {

    /**
     * @param wachtwoord Wachtwoord om te hashen
     * @param salt salt meegeven
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

        try{
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
    public static void loadScreen(MouseEvent event, Class classpath, String screenName) {
        try {
            FXMLLoader loader = new FXMLLoader(classpath.getResource(screenName));
            Parent root = loader.load();

            Scene scene = new Scene(root);
            Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
            window.setScene(scene);
            window.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * @author Bart Maes
     * splitsen van bovenstaande methode om nog specifieke bijkomende on load actions toe te voegen in controller
     */

    public static FXMLLoader getLoader(Class classpath, String screenName) {
        FXMLLoader loader = new FXMLLoader(classpath.getResource(screenName));
        return loader;
    }

    public static Parent getRoot(FXMLLoader loader) throws IOException {
        Parent root = loader.load();
        return root;
    }

    public static void getScreen(MouseEvent event, Parent root) {
        try {
            Scene scene = new Scene(root);
            Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
            window.setScene(scene);
            window.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }












}
