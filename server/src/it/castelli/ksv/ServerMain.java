package it.castelli.ksv;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import spark.Spark;

import java.util.ArrayList;
import java.util.Arrays;

public class ServerMain {

    private static final ObjectMapper mapper = new ObjectMapper();

    public static void main(String[] args) {
        new ServerMain().run();
    }

    private static ArrayList<Author> filterAuthors(spark.Request request) {
        ArrayList<Author> authors = new ArrayList<>(Arrays.asList(DataProvider.getAllAuthors()));
        // allowed params: firstName, lastName, birthYear, deathYear, lifeYear
        for (String queryParam : request.queryParams()) {
            switch (queryParam.toLowerCase()) {
                case "firstname" -> authors = new ArrayList<>(authors.stream()
                        .filter(a -> a.getFirstName().equalsIgnoreCase(request.queryParams(queryParam)))
                        .toList());
                case "lastname" -> authors = new ArrayList<>(authors.stream()
                        .filter(a -> a.getLastName().equalsIgnoreCase(request.queryParams(queryParam)))
                        .toList());
                case "birthyear" -> authors = new ArrayList<>(authors.stream()
                        .filter(a -> a.getBirthDate().getYear() ==
                                Integer.parseInt(request.queryParams(queryParam)))
                        .toList());
                case "deathyear" -> authors = new ArrayList<>(authors.stream()
                        .filter(a -> a.getDeathDate().getYear() ==
                                Integer.parseInt(request.queryParams(queryParam)))
                        .toList());
                case "lifeyear" -> {
                    int year = Integer.parseInt(request.queryParams(queryParam));
                    authors = new ArrayList<>(authors.stream()
                            .filter(a -> year >= a.getBirthDate().getYear())
                            .filter(a -> year <= a.getDeathDate().getYear())
                            .toList());
                }
                default -> {
                }
            }
        }

        return authors;
    }

    private static ArrayList<Topic> filterTopics(spark.Request request) {
        ArrayList<Topic> topics = new ArrayList<>(Arrays.asList(DataProvider.getAllTopics()));
        // allowed params: name, year, place
        for (String queryParam : request.queryParams()) {
            switch (queryParam.toLowerCase()) {
                case "name" -> topics = new ArrayList<>(topics.stream()
                        .filter(t -> t.getName()
                                .equalsIgnoreCase(request.queryParams(queryParam).replaceAll("-", " ")))
                        .toList());
                case "year" -> {
                    int year = Integer.parseInt(request.queryParams(queryParam));
                    topics = new ArrayList<>(topics.stream()
                            .filter(t -> year >= t.getStartDate().getYear())
                            .filter(t -> year <= t.getEndDate().getYear())
                            .toList());
                }
                case "place" -> topics = new ArrayList<>(topics.stream()
                        .filter(t -> t.getPlace().equalsIgnoreCase(request.queryParams(queryParam)))
                        .toList());
            }
        }

        return topics;
    }

    public void run() {
        DataProvider.initialize();
        Spark.port(SharedData.PORT);

        // GET
        Spark.get("/authors", (request, response) -> {
            var authors = filterAuthors(request);
            System.out.println("Returning author(s)");
            return mapper.writeValueAsString(authors.toArray(new Author[0]));
        });

        Spark.get("/topics", ((request, response) -> {
            var topics = filterTopics(request);
            System.out.println("Returning topic(s)");
            return mapper.writeValueAsString(topics);
        }));

        // TODO:
        Spark.get("/authors/:id", (request, response) -> {
            return null;
        });

        Spark.get("/topics/:id", ((request, response) -> {
            return null;
        }));
        // END GET

        // POST
        Spark.post("/authors", (request, response) -> {
            try {
                DataProvider.addData(mapper.readValue(request.body(), Author.class));
                System.out.println("Author successfully added");
                return "Author successfully added";
            } catch (JsonProcessingException e) {
                e.printStackTrace();
                response.status(400);
                return "Error in request body";
            }
        });
        Spark.post("/topics", (request, response) -> {
            try {
                DataProvider.addData(mapper.readValue(request.body(), Topic.class));
                System.out.println("Topic successfully added");
                return "Topic successfully added";
            } catch (JsonProcessingException e) {
                e.printStackTrace();
                response.status(400);
                return "Error in request body";
            }
        });
        // END POST

        // PUT
        Spark.put("/authors/:id", (request, response) -> {
            //DataProvider.modifyData(request.params(":id"), mapper.readValue(request.body(), Author.class));
            System.out.println("Author successfully updated");
            return "Author successfully updated";
        });
        Spark.get("/topics/:id", ((request, response) -> {
            //DataProvider.modifyData(request.params(":id"), mapper.readValue(request.body(), Topic.class));
            System.out.println("Topic successfully updated");
            return "Topic successfully updated";
        }));
        // END PUT

        // DELETE
        Spark.delete("/authors/:id", (request, response) -> {
            //DataProvider.removeData(request.params(":id"));
            System.out.println("Author(s) removed");
            return "Author(s) removed";
        });

        Spark.get("/topics/:id", ((request, response) -> {
            //DataProvider.removeData(request.params(":id"));
            System.out.println("Topic(s) removed");
            return "Topic(s) removed";
        }));
        // END DELETE

        System.out.println("Server running on " + SharedData.PORT);
    }
}
