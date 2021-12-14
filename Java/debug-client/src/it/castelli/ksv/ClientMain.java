package it.castelli.ksv;

import javafx.application.Application;
import javafx.stage.Stage;

public class ClientMain extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        SceneManager.initialize(primaryStage);
    }
}
