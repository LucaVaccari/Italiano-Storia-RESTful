package it.castelli.ksv.controllers;

import it.castelli.ksv.HTTPHandler;
import it.castelli.ksv.SceneManager;
import it.castelli.ksv.SceneType;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.util.Arrays;
import java.util.HashMap;

public class GetWindowController {
    @FXML
    private Parent authorInputs, topicInputs;
    @FXML
    private Label output;
    @FXML
    private TextField authorFirstNameTextField, authorLastNameTextField, authorBirthYearTextField,
            authorDeathYearTextField, authorLifeYearTextField;
    @FXML
    private TextField topicNameTextField, topicYearTextField, topicPlaceTextField;
    @FXML
    private Button authorsButton, topicsButton;

    private GetSubject getSubject = GetSubject.AUTHOR;

    @FXML
    public void onBackButton() {
        SceneManager.setScene(SceneType.MAIN);
    }

    @FXML
    private void activateAuthorInputs() {
        topicInputs.setVisible(false);
        topicInputs.setDisable(true);

        authorInputs.setVisible(true);
        topicInputs.setDisable(false);

        authorsButton.setDisable(true);
        topicsButton.setDisable(false);

        getSubject = GetSubject.AUTHOR;
    }

    @FXML
    private void activateTopicInputs() {
        authorInputs.setVisible(false);
        topicInputs.setDisable(true);

        topicInputs.setVisible(true);
        topicInputs.setDisable(false);

        authorsButton.setDisable(false);
        topicsButton.setDisable(true);

        getSubject = GetSubject.TOPIC;
    }

    @FXML
    private void sendRequest() {
        switch (getSubject) {
            case AUTHOR -> {
                HashMap<String, String> filters = new HashMap<>();
                String firstname = authorFirstNameTextField.getText();
                if (!firstname.equals("")) {
                    filters.put("firstname", firstname.toLowerCase());
                }
                String lastname = authorLastNameTextField.getText();
                if (!lastname.equals("")) {
                    filters.put("lastname", lastname.toLowerCase());
                }
                String birthyear = authorBirthYearTextField.getText();
                if (!birthyear.equals("")) {
                    filters.put("birthyear", birthyear.toLowerCase());
                }
                String deathyear = authorDeathYearTextField.getText();
                if (!deathyear.equals("")) {
                    filters.put("deathyear", deathyear.toLowerCase());
                }
                String lifeyear = authorLifeYearTextField.getText();
                if (!lifeyear.equals("")) {
                    filters.put("lifeyear", lifeyear.toLowerCase());
                }
                output.setText(Arrays.toString(HTTPHandler.getAuthors(filters)));
            }
            case TOPIC -> {
                HashMap<String, String> filters = new HashMap<>();
                String name = topicNameTextField.getText();
                if (!name.equals("")) {
                    filters.put("name", name.toLowerCase());
                }
                String year = topicYearTextField.getText();
                if (!year.equals("")) {
                    filters.put("year", year.toLowerCase());
                }
                String place = topicPlaceTextField.getText();
                if (!place.equals("")) {
                    filters.put("place", place.toLowerCase());
                }

                output.setText(Arrays.toString(HTTPHandler.getTopics(filters)));
            }
        }
    }

}
