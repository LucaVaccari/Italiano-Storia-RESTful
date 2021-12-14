package it.castelli.ksv.controllers;

import it.castelli.ksv.SceneManager;
import it.castelli.ksv.SceneType;
import javafx.fxml.FXML;

/**
 * Controller for FXMLs/mainScene.fxml
 */
public class MainSceneController {
    @FXML
    private void initialize() {
    }

    @FXML
    private void onGetButton() {
        SceneManager.setScene(SceneType.GET);
    }

    @FXML
    private void onPostButton() {
        SceneManager.setScene(SceneType.POST);
    }

    @FXML
    private void onPutButton() {
        SceneManager.setScene(SceneType.PUT);
    }

    @FXML
    private void onDeleteButton() {
        SceneManager.setScene(SceneType.DELETE);
    }
}
