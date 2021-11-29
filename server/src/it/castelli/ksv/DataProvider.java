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
				new Date(1949, 4, 23), "Was born and dead", opuses, new ArrayList<>()));

		addData(new Topic("Prima Guerra Mondiale", new Date(1914, 5, 24), new Date(1918, 11, 4), "Prima guerra",
				"Mondo", new ArrayList<>()));
		addData(new Topic("Decadentismo", new Date(1860, 1, 1), new Date(1910, 4, 6), "Corrente culturale " +
				"decadentista", "Europa", new ArrayList<>()));
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
				.filter(author -> author.getLastName().equalsIgnoreCase(lastName))
				.findFirst()
				.orElse(null);
	}

	public static Topic getTopic(String topicName) {
		return Arrays.stream(getAllTopics())
				.filter(topic -> topic.getName().equalsIgnoreCase(topicName))
				.findFirst()
				.orElse(null);
	}

	public static void modifyData(Entity oldE, Entity newE) {
		data.remove(oldE);
		data.add(newE);
	}
}
