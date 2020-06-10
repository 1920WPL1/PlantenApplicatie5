package plantenApp;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("view/ImporterenStudenten.fxml"));
        primaryStage.setTitle("Planten applicatie");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
        //schermen fixed size
        primaryStage.setResizable(false);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
