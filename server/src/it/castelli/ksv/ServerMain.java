package it.castelli.ksv;

import com.fasterxml.jackson.databind.ObjectMapper;
import spark.Spark;

public class ServerMain {
	private static final int PORT = 20000;

	private static final ObjectMapper mapper = new ObjectMapper();

	public void run() {
		DataProvider.initialize();
		Spark.port(PORT);

		Spark.get("/allAuthors", ((request, response) -> mapper.writeValueAsString(DataProvider.getAllAuthors())));

		Spark.get("/allTopics", ((request, response) -> mapper.writeValueAsString(DataProvider.getAllTopics())));

		Spark.get("/", ((request, response) -> "Hello world"));

		Spark.get("authors/:authorName",
				((request, response) -> mapper.writeValueAsString(DataProvider.getAuthor(request.params(":authorName")
						.toLowerCase()))));

		Spark.get("/:authorName/:opusName", ((request, response) -> {
			Author author = DataProvider.getAuthor(request.params(":authorName"));
			for (var opus : author.getOpuses()) {
				if (opus.getTitle()
						.replaceAll(" ", "-")
						.equalsIgnoreCase(request.params(":opusName"))) {
					return mapper.writeValueAsString(opus);
				}
			}

			return null;
		}));

		System.out.println("Server running on " + PORT);
	}

	public static void main(String[] args) {
		new ServerMain().run();
	}
}
