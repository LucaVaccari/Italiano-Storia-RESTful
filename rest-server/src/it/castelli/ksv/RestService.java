package it.castelli.ksv;

import com.fasterxml.jackson.databind.ObjectMapper;
import spark.Request;
import spark.Spark;

import java.util.Arrays;
import java.util.HashMap;

public class RestService {
	private static final ObjectMapper mapper = new ObjectMapper();

	// TODO: get author id
	// TODO: get topic id

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
			var authors = DatabaseInterface.getAllAuthors(filterMap);
			// TODO: test
			if (filterMap.containsKey("lifeyear")) {
				int lifeYear = Integer.parseInt(filterMap.get("lifeyear"));
				authors =
						Arrays.stream(authors).filter(a -> lifeYear > a.getBirthDate().getYear() &&
								lifeYear < a.getDeathDate().getYear()).toList().toArray(new Author[0]);
			}
			System.out.println("Returning author(s)");
			return mapper.writeValueAsString(authors);
		});

		Spark.get("/topics", ((request, response) -> {
			HashMap<String, String> filterMap = generateQueryMap(request);
			var topics = DatabaseInterface.getAllTopics(filterMap);
			// TODO: test
			if (filterMap.containsKey("lifeyear")) {
				int lifeYear = Integer.parseInt(filterMap.get("lifeyear"));
				topics =
						Arrays.stream(topics).filter(a -> lifeYear > a.getStartDate().getYear() &&
								lifeYear < a.getEndDate().getYear()).toList().toArray(new Topic[0]);
			}
			System.out.println("Returning topic(s)");
			return mapper.writeValueAsString(topics);
		}));

		// TODO: implement id search
		Spark.get("/authors/:id", (request, response) -> null);

		Spark.get("/topics/:id", ((request, response) -> null));
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

		// TODO: imlement PUT
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

		// TODO: implement DELETE
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





























