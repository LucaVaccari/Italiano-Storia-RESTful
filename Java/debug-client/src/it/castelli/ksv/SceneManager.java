package it.castelli.ksv;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;

public class SceneManager {
    public static final String MAIN_SCENE_FXML_PATH = "/FXMLs/mainScene.fxml";
    public static final String GET_SCENE_FXML_PATH = "/FXMLs/get.fxml";
    public static final String POST_SCENE_FXML_PATH = "/FXMLs/post.fxml";
    public static final String PUT_SCENE_FXML_PATH = "/FXMLs/put.fxml";
    public static final String DELETE_SCENE_FXML_PATH = "/FXMLs/delete.fxml";

    private static Stage primaryStage;
    private static Scene mainScene, getScene, postScene, putScene, deleteScene;

    public static void initialize(Stage primaryStage) {
        try {
            SceneManager.primaryStage = primaryStage;
            Parent root;

            URL mainSceneURL = SceneManager.class.getResource(MAIN_SCENE_FXML_PATH);
            assert mainSceneURL != null;
            root = FXMLLoader.load(mainSceneURL);
            mainScene = new Scene(root);

            URL getSceneURL = SceneManager.class.getResource(GET_SCENE_FXML_PATH);
            assert getSceneURL != null;
            root = FXMLLoader.load(getSceneURL);
            getScene = new Scene(root);

            URL postSceneURL = SceneManager.class.getResource(POST_SCENE_FXML_PATH);
            assert postSceneURL != null;
            root = FXMLLoader.load(postSceneURL);
            postScene = new Scene(root);

            URL putSceneURL = SceneManager.class.getResource(PUT_SCENE_FXML_PATH);
            assert putSceneURL != null;
            root = FXMLLoader.load(putSceneURL);
            putScene = new Scene(root);

            URL deleteSceneURL = SceneManager.class.getResource(DELETE_SCENE_FXML_PATH);
            assert deleteSceneURL != null;
            root = FXMLLoader.load(deleteSceneURL);
            deleteScene = new Scene(root);

            primaryStage.setScene(mainScene);
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void setScene(SceneType type) {
        primaryStage.setScene(switch (type) {
            case MAIN -> mainScene;
            case GET -> getScene;
            case POST -> postScene;
            case PUT -> putScene;
            case DELETE -> deleteScene;
        });
    }
}
