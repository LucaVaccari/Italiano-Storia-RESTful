package it.castelli.ksv.controllers;

import it.castelli.ksv.HTTPHandler;
import it.castelli.ksv.SceneManager;
import it.castelli.ksv.SceneType;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class DeleteController {
    @FXML
    private Label output;
    @FXML
    private TextField idTextField;

    private GetSubject getSubject = GetSubject.AUTHOR;

    @FXML
    public void onBackButton() {
        SceneManager.setScene(SceneType.MAIN);
    }

    @FXML
    public void activateAuthorInputs() {
        getSubject = GetSubject.AUTHOR;
    }

    @FXML
    public void activateTopicInputs() {
        getSubject = GetSubject.TOPIC;
    }

    @FXML
    public void sendRequest() {
        switch (getSubject) {
            case AUTHOR -> output.setText(HTTPHandler.deleteAuthor(Integer.parseInt(idTextField.getText())));
            case TOPIC -> output.setText(HTTPHandler.deleteTopic(Integer.parseInt(idTextField.getText())));
        }
    }
}
