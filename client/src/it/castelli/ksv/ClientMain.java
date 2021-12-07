package it.castelli.ksv;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;

public class ClientMain extends Application {
	public static final String MAIN_SCENE_FXML_PATH = "/FXMLs/get.fxml";

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) {
		try {
			URL resource = getClass().getResource(MAIN_SCENE_FXML_PATH);
			assert resource != null;
			Parent root = FXMLLoader.load(resource);
			Scene mainScene = new Scene(root);
			primaryStage.setScene(mainScene);
			primaryStage.show();
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}
}
