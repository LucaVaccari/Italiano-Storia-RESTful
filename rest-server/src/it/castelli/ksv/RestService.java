package it.castelli.ksv;

import com.fasterxml.jackson.databind.ObjectMapper;
import it.castelli.ksv.entities.Author;
import it.castelli.ksv.entities.Topic;
import spark.Request;
import spark.Spark;

import java.util.Arrays;
import java.util.HashMap;

public class RestService {
    private static final ObjectMapper mapper = new ObjectMapper();

    // TODO: queries for opuses

    /**
     * Starts the REST service, configuring the HTTP methods
     */
    public void start() {
        DatabaseInterface.initialize();
        Spark.port(SharedData.PORT);

        // GET
        Spark.get("/authors", (request, response) -> {
            HashMap<String, String> filterMap = generateQueryMap(request);
            var authors = DatabaseInterface.getAuthors(filterMap);
            if (filterMap.containsKey("lifeyear")) {
                int lifeYear = Integer.parseInt(filterMap.get("lifeyear"));
                authors = Arrays.stream(authors).filter(a -> {
                    int birthYear = Utility.dateFromMillis(a.getBirthDate().getTime()).getYear();
                    int deathYear = Utility.dateFromMillis(a.getDeathDate().getTime()).getYear();
                    return lifeYear > birthYear && lifeYear < deathYear;
                }).toList().toArray(new Author[0]);
            }
            System.out.println("Returning author(s)");
            return mapper.writeValueAsString(authors);
        });

        Spark.get("/topics", ((request, response) -> {
            HashMap<String, String> filterMap = generateQueryMap(request);
            var topics = DatabaseInterface.getTopics(filterMap);
            if (filterMap.containsKey("year")) {
                int year = Integer.parseInt(filterMap.get("year"));
                topics = Arrays.stream(topics).filter(a -> {
                    int startYear = Utility.dateFromMillis(a.getStartDate().getTime()).getYear();
                    int endYear = Utility.dateFromMillis(a.getEndDate().getTime()).getYear();
                    return year > startYear && year < endYear;
                }).toList().toArray(new Topic[0]);
            }
            System.out.println("Returning topic(s)");
            return mapper.writeValueAsString(topics);
        }));

        Spark.get("/authors/:id", (request, response) -> {
            int id = Integer.parseInt(request.params(":id"));
            return mapper.writeValueAsString(DatabaseInterface.getAuthorById(id));
        });

        Spark.get("/topics/:id", ((request, response) -> {
            int id = Integer.parseInt(request.params(":id"));
            return mapper.writeValueAsString(DatabaseInterface.getTopicById(id));
        }));
        // END GET

        // TODO: implement POST
        // POST
        Spark.post("/authors", (request, response) -> {
            //DataProvider.addData(mapper.readValue(request.body(), Author.class));
            System.out.println("Author successfully added");
            return "Author successfully added";
        });
        Spark.post("/topics", (request, response) -> {
            //DataProvider.addData(mapper.readValue(request.body(), Topic.class));
            System.out.println("Topic successfully added");
            return "Topic successfully added";
        });
        // END POST

        // TODO: implement PUT
        // PUT
        Spark.put("/authors/:id", (request, response) -> {
            //DataProvider.modifyData(request.params(":id"), mapper.readValue(request.body(), Author.class));
            System.out.println("Author successfully updated");
            return "Author successfully updated";
        });
        Spark.put("/topics/:id", ((request, response) -> {
            //DataProvider.modifyData(request.params(":id"), mapper.readValue(request.body(), Topic.class));
            System.out.println("Topic successfully updated");
            return "Topic successfully updated";
        }));
        // END PUT

        // TODO: implement DELETE
        // DELETE
        Spark.delete("/authors/:id", (request, response) -> {
            //DataProvider.removeData(request.params(":id"));
            System.out.println("Author(s) removed");
            return "Author(s) removed";
        });

        Spark.delete("/topics/:id", ((request, response) -> {
            //DataProvider.removeData(request.params(":id"));
            System.out.println("Topic(s) removed");
            return "Topic(s) removed";
        }));
        // END DELETE

        System.out.println("Server running on " + SharedData.PORT);
    }


    /**
     * Extracts the query parameters from the request and puts them in an HashMap<String, String>
     *
     * @param request The spark.Request which should contain the query parameters
     * @return A map of query parameters
     */
    private HashMap<String, String> generateQueryMap(Request request) {
        HashMap<String, String> map = new HashMap<>();
        for (var queryParam : request.queryParams())
            map.put(queryParam.toLowerCase(), request.queryParams(queryParam).toLowerCase().replaceAll("-", " "));
        return map;
    }
}





























