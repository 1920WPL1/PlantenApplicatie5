package plantenApp;

import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import plantenApp.java.model.Gebruiker;
import plantenApp.java.model.LoginMethods;
import java.sql.SQLException;

/**@Author Bart Maes
 * beheren van profiel: overzicht van gegevens en mogelijkheid om wachtwoord te wijzigen
 * @throws SQLException
 */

public class ControllerBeheerProfiel {
    public AnchorPane anchorPane;
    public TextField txtVoornaam;
    public TextField txtNaam;
    public TextField txtEmail;
    public Button btnWachtwoordWijzigen;

    private Gebruiker user;

    /**@Author Bart Maes, Matthias Vancoillie
     * bij laden ingelogde user en zijn gegevens ophalen
     */
    public void initialize() {
        //als de gebruiker profiel wilt zien, zal hij sowieso ingelogd moeten zijn, dus ingelogde user ophalen
        user = LoginMethods.userLoggedIn;

        //gegevens van de ingelogde user ophalen uit database en wegschrijven in de tekstvelden
        txtNaam.setText(user.getAchternaam());
        txtVoornaam.setText(user.getVoornaam());
        txtEmail.setText(user.getEmail());
    }

    /**@Author Bart Maes
     * laden van scherm om wachtwoord te wijzigen vanuit profiel
     */
    public void clicked_WWWijzigenProfiel(ActionEvent actionEvent) {
        LoginMethods.loadScreen(anchorPane, getClass(), "view/WijzigWachtwoordProfiel.fxml");
    }

    /**@Author Bart Maes
     * terug naar home screen
     */
    public void click_home(MouseEvent mouseEvent) {
        LoginMethods.loadScreen(anchorPane, getClass(), "view/HoofdScherm.fxml");
    }
}
