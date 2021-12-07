package it.castelli.ksv;

import kong.unirest.*;

import java.util.HashMap;

/**
 * Provides simple methods for generating and sending HTTP requests to the REST service.
 */
public class HTTPHandler {
	private static final String URL = "http://localhost:" + SharedData.PORT;

	private static void addFilters(HttpRequest<?> request, HashMap<String, String> queries) {
		for (var entry : queries.entrySet()) {
			request = request.queryString(entry.getKey(), entry.getValue());
		}
	}

	private static String get(String subURL, HashMap<String, String> queries) {
		GetRequest getRequest = Unirest.get(URL + subURL);
		addFilters(getRequest, queries);
		return getRequest.asString().getBody();
	}

	private static String post(String subURL, String jsonAttachment) {
		HttpRequestWithBody postRequest = Unirest.post(URL + subURL);
		return postRequest.body(jsonAttachment).asString().getBody();
	}

	private static String put(String subURL, String jsonAttachment, HashMap<String, String> queries) {
		HttpRequestWithBody putRequest = Unirest.put(URL + subURL);
		addFilters(putRequest, queries);
		return putRequest.body(jsonAttachment).asString().getBody();
	}

	private static String delete(String subURL, HashMap<String, String> queries) {
		HttpRequestWithBody deleteRequest = Unirest.delete(URL + subURL);
		addFilters(deleteRequest, queries);
		return deleteRequest.asString().getBody();
	}

	// TODO: convert from JSON for the GET methods

	/**
	 * Sends a GET request to get a list of authors
	 *
	 * @param queries A list of filters to apply to the request (see the REST documentation)
	 * @return The list of authors in JSON
	 */
	public static String getAuthors(HashMap<String, String> queries) {
		return get("/authors", queries);
	}

	/**
	 * Sends a GET request to get a list of topics
	 *
	 * @param queries A list of filters to apply to the request (see the REST documentation)
	 * @return The list of topics in JSON
	 */
	public static String getTopics(HashMap<String, String> queries) {
		return get("/topics", queries);
	}

	/**
	 * Sends a GET request to get the list of all authors
	 *
	 * @return The list of all authors in JSON
	 */
	public static String getAllAuthors() {
		return getAuthors(new HashMap<>());
	}

	/**
	 * Sends a GET request to get the list of all topics
	 *
	 * @return The list of all topics in JSON
	 */
	public static String getAllTopics() {
		return getTopics(new HashMap<>());
	}

	/**
	 * Sends a POST request to add a new author in the database (automatically converts the author)
	 *
	 * @param author The author to add
	 * @return The server response message
	 */
	public static String postAuthor(Author author) {
		return post("/authors", new JsonObjectMapper().writeValue(author));
	}

	/**
	 * Sends a POST request to add a new topic in the database (automatically converts the topic)
	 *
	 * @param topic The topic to add
	 * @return The server response message
	 */
	public static String postTopic(Topic topic) {
		return post("/topics", new JsonObjectMapper().writeValue(topic));
	}

	/**
	 * Sends a PUT request to update the information of an author
	 *
	 * @param author  The new author information
	 * @param queries Filters to identify the author to update (see the REST documentation)
	 * @return The server response message
	 */
	public static String putAuthors(Author author, HashMap<String, String> queries) {
		return put("/authors", new JsonObjectMapper().writeValue(author), queries);
	}

	/**
	 * Sends a PUT request to update the information of a topic
	 *
	 * @param topic   The new topic information
	 * @param queries Filters to identify the topic to update (see the REST documentation)
	 * @return The server response message
	 */
	public static String putTopics(Topic topic, HashMap<String, String> queries) {
		return put("/topics", new JsonObjectMapper().writeValue(topic), queries);
	}

	/**
	 * Sends a DELETE request to delete an author
	 *
	 * @param queries Filters to identify the author to delete (see the REST documentation)
	 * @return The server response message
	 */
	public static String deleteAuthors(HashMap<String, String> queries) {
		return delete("/authors", queries);
	}

	/**
	 * Sends a DELETE request to delete an topic
	 *
	 * @param queries Filters to identify the topic to delete (see the REST documentation)
	 * @return The server response message
	 */
	public static String deleteTopics(HashMap<String, String> queries) {
		return delete("/topics", queries);
	}
}
