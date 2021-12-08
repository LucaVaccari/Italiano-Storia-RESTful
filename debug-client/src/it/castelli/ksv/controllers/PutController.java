package it.castelli.ksv.controllers;

import it.castelli.ksv.SceneManager;
import it.castelli.ksv.SceneType;
import javafx.fxml.FXML;

public class PutController {
    @FXML
    public void onBackButton() {
        SceneManager.setScene(SceneType.MAIN);
    }
}
