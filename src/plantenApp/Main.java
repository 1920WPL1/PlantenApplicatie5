package plantenApp;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    private double xOffset = 0;
    private double yOffset = 0;
    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("view/Zoekscherm.fxml"));
        primaryStage.setTitle("Planten applicatie");
        primaryStage.setScene(new Scene(root, 1278, 858));
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
