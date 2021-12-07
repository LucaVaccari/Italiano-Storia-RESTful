package it.castelli.ksv.controllers;

import it.castelli.ksv.entities.Author;
import it.castelli.ksv.HTTPHandler;
import javafx.fxml.FXML;

import java.sql.Date;
import java.util.Arrays;
import java.util.HashMap;

/**
 * Controller for FXMLs/mainScene.fxml
 */
public class MainSceneController {
	@FXML
	private void initialize() {
	}

	@FXML
	private void onGetButton() {
		System.out.println(Arrays.toString(HTTPHandler.getAllAuthors()));
	}

	@FXML
	private void onPostButton() {
		Author author = new Author("Giuseppe", "Ungaretti", new Date(1888, 2, 19),
				new Date(1970, 4, 23), "Was born and dead");
		System.out.println(HTTPHandler.postAuthor(author));
	}

	@FXML
	private void onPutButton() {
		System.out.println("PUT");
	}

	@FXML
	private void onDeleteButton() {
		HashMap<String, String> queries = new HashMap<>();
		queries.put("lastname", "Ungaretti");
		System.out.println(HTTPHandler.deleteAuthors(queries));
	}
}
