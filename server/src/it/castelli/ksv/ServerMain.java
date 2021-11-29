package it.castelli.ksv;

import com.fasterxml.jackson.databind.ObjectMapper;
import spark.Spark;

import java.util.ArrayList;
import java.util.Arrays;

public class ServerMain {
	private static final int PORT = 20000;

	private static final ObjectMapper mapper = new ObjectMapper();

	public static void main(String[] args) {
		new ServerMain().run();
	}

	public void run() {
		DataProvider.initialize();
		Spark.port(PORT);

		// GET
		Spark.get("/authors", (request, response) -> {
			var authors = filterAuthors(request);
			return mapper.writeValueAsString(authors.toArray(new Author[0]));
		});

		Spark.get("/topics", ((request, response) -> {
			var topics = filterTopics(request);
			return mapper.writeValueAsString(topics);
		}));
		// END GET

		// POST
		Spark.post("/authors", (request, response) -> {
			DataProvider.addData(mapper.readValue(request.body(), Author.class));
			return null;
		});
		Spark.post("/topics", (request, response) -> {
			DataProvider.addData(mapper.readValue(request.body(), Topic.class));
			return null;
		});
		// END POST

		// PUT
		Spark.put("/authors", (request, response) -> {
			var authors = filterAuthors(request);

			for (var author : authors)
				DataProvider.modifyData(author, mapper.readValue(request.body(), Author.class));

			return null;
		});
		Spark.get("/topics", ((request, response) -> {
			var topics = filterTopics(request);

			for (var topic : topics) {
				DataProvider.modifyData(topic, mapper.readValue(request.body(), Topic.class));
			}

			return null;
		}));
		// END PUT

		// DELETE
		Spark.delete("/authors", (request, response) -> {
			var authors = filterAuthors(request);
			for (var author : authors) {
				DataProvider.removeData(author);
			}

			return null;
		});

		Spark.get("/topics", ((request, response) -> {
			var topics = filterTopics(request);

			for (var topic : topics) {
				DataProvider.removeData(topic);
			}

			return null;
		}));
		// END DELETE

		System.out.println("Server running on " + PORT);
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
}
