package it.castelli.ksv;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import it.castelli.ksv.entities.Author;
import it.castelli.ksv.entities.Topic;
import kong.unirest.*;

import java.util.HashMap;

/**
 * Provides simple methods for generating and sending HTTP requests to the REST service.
 */
public class HTTPHandler {
    private static final String URL = "http://localhost:" + SharedData.PORT;
    private static final ObjectMapper mapper = new ObjectMapper();

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

    private static String delete(String subURL, int id) {
        HttpRequestWithBody deleteRequest = Unirest.delete(URL + subURL + "/" + id);
        return deleteRequest.asString().getBody();
    }

    /**
     * Sends a GET request to get a list of authors
     *
     * @param queries A list of filters to apply to the request (see the REST documentation)
     * @return The list of authors
     */
    public static Author[] getAuthors(HashMap<String, String> queries) {
        String json = get("/authors", queries);
        Author[] authors = new Author[0];
        try {
            authors = mapper.readValue(json, Author[].class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return authors;
    }

    /**
     * Sends a GET request to get a list of topics
     *
     * @param queries A list of filters to apply to the request (see the REST documentation)
     * @return The list of topics
     */
    public static Topic[] getTopics(HashMap<String, String> queries) {
        String json = get("/topics", queries);
        Topic[] topics = new Topic[0];
        try {
            topics = mapper.readValue(json, Topic[].class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return topics;
    }

    /**
     * Sends a GET request to get the list of all authors
     *
     * @return The list of all authors
     */
    public static Author[] getAllAuthors() {
        return getAuthors(new HashMap<>());
    }

    /**
     * Sends a GET request to get the list of all topics
     *
     * @return The list of all topics
     */
    public static Topic[] getAllTopics() {
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
     * @param id The id of the author to delete
     * @return The server response message
     */
    public static String deleteAuthor(int id) {
        return delete("/authors", id);
    }

    /**
     * Sends a DELETE request to delete an topic
     *
     * @param id The id of the topic to delete
     * @return The server response message
     */
    public static String deleteTopic(int id) {
        return delete("/topics", id);
    }
}
