package it.castelli.ksv;

import com.fasterxml.jackson.databind.ObjectMapper;
import spark.Request;
import spark.Spark;

import java.util.HashMap;

public class RestService {
	private static final ObjectMapper mapper = new ObjectMapper();

	/**
	 * Starts the REST service, configuring the HTTP methods
	 */
	public void start() {
		DatabaseInterface.initialize();
		Spark.port(SharedData.PORT);

		// GET
		Spark.get("/authors", (request, response) -> {
			var authors = DatabaseInterface.getAllAuthors(generateQueryMap(request));
			// TODO: filter result for lifeyear
			System.out.println("Returning author(s)");
			return mapper.writeValueAsString(authors);
		});

		Spark.get("/topics", ((request, response) -> {
			var topics = DatabaseInterface.getAllTopics(generateQueryMap(request));
			// TODO: filter result for year
			System.out.println("Returning topic(s)");
			return mapper.writeValueAsString(topics);
		}));

		// TODO:
		Spark.get("/authors/:id", (request, response) -> null);

		Spark.get("/topics/:id", ((request, response) -> null));
		// END GET

		// TODO:
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

		// TODO:
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

		// TODO:
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


	/**
	 * Extracts the query parameters from the request and puts them in an HashMap<String, String>
	 *
	 * @param request The spark.Request which should contain the query parameters
	 * @return A map of query parameters
	 */
	private HashMap<String, String> generateQueryMap(Request request) {
		HashMap<String, String> map = new HashMap<>();
		for (var queryParam : request.queryParams()) map.put(queryParam, request.queryParams(queryParam));
		return map;
	}
}