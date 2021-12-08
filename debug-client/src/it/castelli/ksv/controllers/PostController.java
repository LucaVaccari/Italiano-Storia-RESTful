package it.castelli.ksv.controllers;

import it.castelli.ksv.SceneManager;
import it.castelli.ksv.SceneType;
import javafx.fxml.FXML;

public class PostController {
    @FXML
    public void onBackButton() {
        SceneManager.setScene(SceneType.MAIN);
    }
}
