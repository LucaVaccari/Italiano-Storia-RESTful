package it.castelli.ksv;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Arrays;

public class DataProvider {
	private static final ArrayList<Entity> data = new ArrayList<>();

	public static void initialize() {
		// TEST
		ArrayList<Opus> opuses = new ArrayList<>();
		opuses.add(new Opus("I malavoglia", new Date(1890, 4, 23), "prosa", "verismo", "I malavoglia",
				new ArrayList<>()));

		addData(new Author("Luigi", "Pirandello", new Date(1885, 2, 19),
				new Date(1950, 4, 23), "Was born and dead", new ArrayList<>(), new ArrayList<>()));
		addData(new Author("Giovanni", "Verga", new Date(1885, 2, 19),
				new Date(1950, 4, 23), "Was born and dead", opuses, new ArrayList<>()));
		// END TEST
	}

	public static void addData(Entity element) {
		data.add(element);
	}

	public static void removeData(Entity element) {
		data.remove(element);
	}

	public static Author[] getAllAuthors() {
		ArrayList<Author> authorList = new ArrayList<>();
		for (var entity : data)
			if (entity instanceof Author)
				authorList.add((Author) entity);

		return authorList.toArray(new Author[0]);
	}

	public static Topic[] getAllTopics() {
		ArrayList<Topic> topicList = new ArrayList<>();
		for (var entity : data)
			if (entity instanceof Topic)
				topicList.add((Topic) entity);

		return topicList.toArray(new Topic[0]);
	}

	public static Author getAuthor(String lastName) {
		return Arrays.stream(getAllAuthors())
				.filter(author -> author.getLastName().equals(lastName))
				.findFirst()
				.orElse(null);
	}
}
