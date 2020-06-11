package plantenApp;

import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import plantenApp.java.model.LoginMethods;

/**@Author Bart Maes
 * aparte controller om in alle schermen van de andere groepen de home button op te vangen
 * dit valt dan eigenlijk weg als dit gemerged wordt met de andere groepen, dan moet dit in hun controllers terechtkomen
 */

public class ControllerGoHome {

    public AnchorPane anchorPane;

    public void click_home(MouseEvent mouseEvent) {
        LoginMethods.loadScreen(anchorPane, getClass(), "view/HoofdScherm.fxml");
    }
}
