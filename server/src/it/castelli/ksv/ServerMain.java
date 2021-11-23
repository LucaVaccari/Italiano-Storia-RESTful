package it.castelli.ksv;

import com.fasterxml.jackson.databind.ObjectMapper;
import spark.Spark;

import java.sql.Date;

public class ServerMain {
	private static final int PORT = 20000;

	private static final ObjectMapper mapper = new ObjectMapper();

	public void run() {
		// TEST
		Author[] authors = new Author[2];
		authors[0] = new Author("Luigi", "Pirandello", new Date(1885, 2, 19),
				new Date(1950, 4, 23), "Was born and dead", new Opus[5], new LogicalLink[0]);
		authors[1] = new Author("Giovanni", "Verga", new Date(1885, 2, 19),
				new Date(1950, 4, 23), "Was born and dead", new Opus[5], new LogicalLink[0]);
		// END TEST

		Spark.port(PORT);

		Spark.get("/allAuthors", ((request, response) -> mapper.writeValueAsString(DataProvider.getAllAuthors())));

		Spark.get("/allTopics", ((request, response) -> {
			return mapper.writeValueAsString(DataProvider.getAllTopics());
		}));

		Spark.get("/", ((request, response) -> "Hello world"));

		Spark.get("/:authorName", ((request, response) -> {
			return "";
		}));

		Spark.get("/:authorName/:opusName", ((request, response) -> {
			return "";
		}));

		System.out.println("Server running on " + PORT);
	}

	public static void main(String[] args) {
		new ServerMain().run();
	}
}
