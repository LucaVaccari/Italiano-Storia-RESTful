package it.castelli.ksv;

import kong.unirest.*;

import java.util.HashMap;

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

    public static String getAuthors(HashMap<String, String> queries) {
        return get("/authors", queries);
    }

    public static String getTopics(HashMap<String, String> queries) {
        return get("/topics", queries);
    }

    public static String getAllAuthors() {
        return getAuthors(new HashMap<>());
    }

    public static String getAllTopics() {
        return getTopics(new HashMap<>());
    }

    public static String postAuthor(Author author) {
        return post("/authors", new JsonObjectMapper().writeValue(author));
    }

    public static String postTopic(Topic topic) {
        return post("/topics", new JsonObjectMapper().writeValue(topic));
    }

    public static String putAuthors(Author author, HashMap<String, String> queries) {
        return put("/authors", new JsonObjectMapper().writeValue(author), queries);
    }

    public static String putTopics(Topic topic, HashMap<String, String> queries) {
        return put("/topics", new JsonObjectMapper().writeValue(topic), queries);
    }

    public static String deleteAuthors(HashMap<String, String> queries) {
        return delete("/authors", queries);
    }

    public static String deleteTopics(HashMap<String, String> queries) {
        return delete("/topics", queries);
    }
}
