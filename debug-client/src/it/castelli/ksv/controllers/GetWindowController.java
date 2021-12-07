package it.castelli.ksv.controllers;

import it.castelli.ksv.HTTPHandler;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.util.Arrays;
import java.util.HashMap;

public class GetWindowController {
    private enum GetSubject {
        AUTHOR, TOPIC
    }

    @FXML
    private Parent authorInputs, topicInputs;

    @FXML
    private Label jsonOutput;
    @FXML
    private TextField authorFirstNameTextField, authorLastNameTextField, authorBirthYearTextField,
            authorDeathYearTextField, authorLifeYearTextField;
    @FXML
    private TextField topicNameTextField, topicYearTextField, topicPlaceTextField;

    private GetSubject getSubject = GetSubject.AUTHOR;

    @FXML
    private void activateAuthorInputs() {
        topicInputs.setVisible(false);
        topicInputs.setDisable(true);

        authorInputs.setVisible(true);
        topicInputs.setDisable(false);

        getSubject = GetSubject.AUTHOR;
    }

    @FXML
    private void activateTopicInputs() {
        authorInputs.setVisible(false);
        topicInputs.setDisable(true);

        topicInputs.setVisible(true);
        topicInputs.setDisable(false);

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
                jsonOutput.setText(Arrays.toString(HTTPHandler.getAuthors(filters)));
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

                jsonOutput.setText(Arrays.toString(HTTPHandler.getTopics(filters)));
            }
        }
    }
}
