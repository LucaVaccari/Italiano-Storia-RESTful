package it.castelli.ksv;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import it.castelli.ksv.entities.Author;
import it.castelli.ksv.entities.Topic;
import spark.Request;
import spark.Spark;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.HashMap;

public class RestService {
	private static final ObjectMapper mapper = new ObjectMapper();

	// TODO: queries for opuses

	/**
	 * Starts the REST service, configuring the HTTP methods
	 */
	public void start() throws SQLException {
		DatabaseInterface.initialize();
		Spark.port(SharedData.PORT);

		// GET
		Spark.get("/authors", (request, response) -> {
			try {
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
			}
			catch (SQLException e) {
				e.printStackTrace();
				return "Error while returning authors: " + e.getMessage();
			}
		});

		Spark.get("/topics", ((request, response) -> {
			try {
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
			}
			catch (SQLException e) {
				e.printStackTrace();
				return "Error while returning topics: " + e.getMessage();
			}
		}));

		Spark.get("/authors/:id", (request, response) -> {
			try {
				int id = Integer.parseInt(request.params(":id"));
				return mapper.writeValueAsString(DatabaseInterface.getAuthorById(id));
			}
			catch (SQLException e) {
				e.printStackTrace();
				return "Error while returning the author: " + e.getMessage();
			}
		});

		Spark.get("/topics/:id", ((request, response) -> {
			try {
				int id = Integer.parseInt(request.params(":id"));
				return mapper.writeValueAsString(DatabaseInterface.getTopicById(id));
			}
			catch (SQLException e) {
				e.printStackTrace();
				return "Error while returning the topic: " + e.getMessage();
			}
		}));
		// END GET

		// POST
		Spark.post("/authors", (request, response) -> {
			try {
				Author author = mapper.readValue(request.body(), Author.class);
				DatabaseInterface.postAuthor(author);
				System.out.println("Author successfully added");
				return "Author successfully added";
			}
			catch (SQLException | JsonProcessingException e) {
				e.printStackTrace();
				return "Error while adding the author: " + e.getMessage();
			}
		});
		Spark.post("/topics", (request, response) -> {
			try {
				Topic topic = mapper.readValue(request.body(), Topic.class);
				DatabaseInterface.postTopic(topic);
				System.out.println("Topic successfully added");
				return "Topic successfully added";
			}
			catch (SQLException | JsonProcessingException e) {
				e.printStackTrace();
				return "Error while adding the topic: " + e.getMessage();
			}
		});
		// END POST

		// PUT
		Spark.put("/authors/:id", (request, response) -> {
			try {
				Author author = mapper.readValue(request.body(), Author.class);
				int id = Integer.parseInt(request.params(":id"));
				DatabaseInterface.putAuthor(author, id);
				System.out.println("Author successfully updated");
				return "Author successfully updated";
			}
			catch (SQLException e) {
				e.printStackTrace();
				return "Error while updating the author: " + e.getMessage();
			}
		});
		Spark.put("/topics/:id", ((request, response) -> {
			try {
				Topic topic = mapper.readValue(request.body(), Topic.class);
				int id = Integer.parseInt(request.params(":id"));
				DatabaseInterface.putTopic(topic, id);
				System.out.println("Topic successfully updated");
				return "Topic successfully updated";
			}
			catch (SQLException e) {
				e.printStackTrace();
				return "Error while updating the topic: " + e.getMessage();
			}
		}));
		// END PUT

		// DELETE
		Spark.delete("/authors/:id", (request, response) -> {
			try {
				int id = Integer.parseInt(request.params(":id"));
				DatabaseInterface.deleteAuthor(id);
				System.out.println("Author successfully removed");
				return "Author(s) removed";
			}
			catch (SQLException e) {
				e.printStackTrace();
				return "Error while deleting the author: " + e.getMessage();
			}

		});

		Spark.delete("/topics/:id", ((request, response) -> {
			try {
				int id = Integer.parseInt(request.params(":id"));
				DatabaseInterface.deleteTopic(id);
				System.out.println("Topic(s) removed");
				return "Topic(s) removed";
			}
			catch (SQLException e) {
				e.printStackTrace();
				return "Error while deleting the topic: " + e.getMessage();
			}
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





























