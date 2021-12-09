package it.castelli.ksv.controllers;

import it.castelli.ksv.HTTPHandler;
import it.castelli.ksv.SceneManager;
import it.castelli.ksv.SceneType;
import it.castelli.ksv.entities.Author;
import it.castelli.ksv.entities.Topic;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.*;

import java.sql.Date;

public class PostController {
	@FXML
	private Button authorsButton;
	@FXML
	private Button topicsButton;
	@FXML
	private Parent authorsInputs;
	@FXML
	private Parent topicsInputs;
	@FXML
	private TextField authorFirstNameTextField;
	@FXML
	private TextField authorLastNameTextField;
	@FXML
	private TextArea authorLifeTextArea;
	@FXML
	private DatePicker authorBirthDatePicker;
	@FXML
	private DatePicker authorDeathDatePicker;
	@FXML
	private TextField topicNameTextField;
	@FXML
	private TextArea topicDescriptionTextArea;
	@FXML
	private TextField topicPlaceTextField;
	@FXML
	private DatePicker topicStartDatePicker;
	@FXML
	private DatePicker topicEndDatePicker;
	@FXML
	private Label output;

	private GetSubject getSubject = GetSubject.AUTHOR;

	@FXML
	public void onBackButton() {
		SceneManager.setScene(SceneType.MAIN);
	}

	@FXML
	public void activateAuthorInputs() {
		authorsButton.setDisable(true);
		topicsButton.setDisable(false);

		authorsInputs.setVisible(true);
		topicsInputs.setVisible(false);

		getSubject = GetSubject.AUTHOR;
	}

	@FXML
	public void activateTopicInputs() {
		topicsButton.setDisable(true);
		authorsButton.setDisable(false);

		topicsInputs.setVisible(true);
		authorsInputs.setVisible(false);

		getSubject = GetSubject.TOPIC;
	}

	@FXML
	public void sendRequest() {
		switch (getSubject) {
			case AUTHOR -> {
				Author author = new Author(
						-1,
						authorFirstNameTextField.getText().strip(),
						authorLastNameTextField.getText().strip(),
						Date.valueOf(authorBirthDatePicker.getValue()),
						Date.valueOf(authorDeathDatePicker.getValue()),
						authorLifeTextArea.getText().strip().replaceAll("'", "\"")
				);
				output.setText(HTTPHandler.postAuthor(author));
			}
			case TOPIC -> {
				Topic topic = new Topic(
						-1,
						topicNameTextField.getText().strip(),
						Date.valueOf(topicStartDatePicker.getValue()),
						Date.valueOf(topicEndDatePicker.getValue()),
						topicDescriptionTextArea.getText().strip().replaceAll("'", "\""),
						topicPlaceTextField.getText().strip()
				);
				output.setText((HTTPHandler.postTopic(topic)));
			}
		}
	}
}
